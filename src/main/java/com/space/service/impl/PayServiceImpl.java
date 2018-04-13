package com.space.service.impl;

import com.space.config.OrderEndPayTimeAutoBean;
import com.space.dao.BankRecordRepository;
import com.space.dao.BankRepository;
import com.space.dao.OrderRepository;
import com.space.dao.UserRepository;
import com.space.entity.Bank;
import com.space.entity.BankRecord;
import com.space.entity.Order;
import com.space.entity.User;
import com.space.service.PayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service("payService")
public class PayServiceImpl implements PayService {

    @Resource
    private BankRepository bankRepository;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private BankRecordRepository bankRecordRepository;

    @Resource
    private OrderEndPayTimeAutoBean orderEndPayTimeAutoBean;

    @Value("${managerBankAccount}")
    private String managerBankAccount;


    /**
     * 支付过程
     * @param oid
     * @param bank
     * @return
     */
    @Override
    @Transactional
    public String payByOnline(int oid, Bank bank) {

        Order order = orderRepository.findOne(oid);
        Bank fromBank = bankRepository.findByAccountAndPassword(bank.getAccount(),bank.getPassword());

        if (fromBank==null){
            return "1";//账户密码错误
        }
        if (fromBank.getBalance() < order.getConsume()){//余额不足
            return "2";//余额不足
        }

        if (!order.getOrderStatus().equals("0")){
            return "3";//由于已付款或者超时造成付款失败
        }
        String toBankAccount = order.getEduInsPlan().getEduInstitution().getBankAccount();
        Bank toBank = bankRepository.queryBankByAccount(managerBankAccount);

        if (toBank==null){
            return "4";//机构银行账户不存在
        }

        if (!this.pay(fromBank,toBank,order.getConsume())){
            return "1";
        }


//        fromBank.setBalance(fromBank.getBalance()-order.getConsume());
//        bankRepository.save(fromBank);
//
//        toBank.setBalance(toBank.getBalance()+order.getConsume());
//        bankRepository.save(toBank);
        orderEndPayTimeAutoBean.removeOrderEndPayTimeList(order.getId());

        Set<BankRecord> bankRecords = order.getBankRecord();
        Iterator<BankRecord> it = bankRecords.iterator();
        while (it.hasNext()){
            BankRecord bankRecord = it.next();
            if (bankRecord.getType().equals("1")){//用户付款
                bankRecord.setFinish(true);
                bankRecord.setFromAccount(bank.getAccount());

                //新增一个记录
                BankRecord manToIns = new BankRecord();
                manToIns.setOrder(order);
                manToIns.setFromAccount(bankRecord.getToAccount());
                manToIns.setToAccount(order.getEduInsPlan().getEduInstitution().getBankAccount());
                manToIns.setFinish(false);
                manToIns.setAmount(bankRecord.getAmount());
                manToIns.setType("3");
                bankRecordRepository.save(manToIns);
                break;
            }
        }
        order.setOrderStatus("1");//付款成功



        User user = order.getUser();

        user.setConsumeRecord(user.getConsumeRecord() + order.getConsume());
//        int vip = (int) (user.getConsumeRecord() / 500);
        user.setPoint((int) (user.getPoint() + order.getConsume()/2));

        orderRepository.save(order);
        userRepository.save(user);


        return "5";
    }


    /**
     * 转账
     * @param fromBank
     * @param toBank
     * @param balance
     * @return
     */
    @Transactional
    public boolean pay(Bank fromBank,Bank toBank,double balance){

        Bank fb = bankRepository.findByAccountAndPassword(fromBank.getAccount(),fromBank.getPassword());
        if (fb==null){
            return false;
        }

        Bank tb = bankRepository.queryBankByAccount(toBank.getAccount());
        if (tb==null){
            return false;
        }

        fb.setBalance(fb.getBalance()-balance);
        bankRepository.save(fb);

        tb.setBalance(tb.getBalance()+balance);
        bankRepository.save(tb);

        return true;
    }

    /**
     * 转账接口
     * @param fromBank 出发账号
     * @param toBank 收钱账号
     * @param balance 收款数
     * @return
     */
    @Override
    public boolean pay(Bank fromBank, String toBank, double balance) {
        Bank fb = bankRepository.findByAccountAndPassword(fromBank.getAccount(),fromBank.getPassword());
        if (fb==null){
            return false;
        }

        Bank tb = bankRepository.queryBankByAccount(toBank);

        if (tb==null){
            return false;
        }

        if (fb.getBalance()<balance){
            return false;
        }

        fb.setBalance(fb.getBalance()-balance);
        bankRepository.save(fb);

        tb.setBalance(tb.getBalance()+balance);
        bankRepository.save(tb);

        return true;
    }

    /**
     * 根据银行账号查询财务状况
     * @param eduInsAccount
     * @return
     */
    @Override
    public List<BankRecord> getConditionByAccount(String eduInsAccount) {
        return bankRecordRepository.findAllByFromAccountOrToAccount(eduInsAccount,eduInsAccount);
    }

    @Override
    public List<BankRecord> getConditionByOrderList(Set<Order> orders) {

        List<BankRecord> result = new ArrayList<>();
        for (Order order : orders){
            Set<BankRecord> bankRecords = order.getBankRecord();
            Iterator<BankRecord> it = bankRecords.iterator();
            while (it.hasNext()){
                BankRecord bankRecord = it.next();
                result.add(bankRecord);
            }

        }


        return result;
    }

    @Override
    public List<BankRecord> getConditionByUser(Set<Order> orders) {
        List<BankRecord> result = new ArrayList<>();
        for (Order order : orders){
            Set<BankRecord> bankRecords = order.getBankRecord();
            Iterator<BankRecord> it = bankRecords.iterator();
            while (it.hasNext()){
                BankRecord bankRecord = it.next();
                if (bankRecord.getType().equals("1") || bankRecord.getType().equals("2") || bankRecord.getType().equals("4")){
                    //付款订单
                    //退款订单
                    result.add(bankRecord);
                }
            }

        }


        return result;
    }

    @Override
    public double calculateMoneyByUser(Set<Order> orders) {
        double result = 0;
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()){
            Set<BankRecord> bankRecords = orderIterator.next().getBankRecord();
            Iterator<BankRecord> brit = bankRecords.iterator();
            while (brit.hasNext()){
                BankRecord br = brit.next();
                if (br.isFinish()){
                    if (br.getType().equals("1") || br.getType().equals("2")){//用户付款
                        result += br.getAmount();
                    }else if ( br.getType().equals("4")){//退款
                        result -= br.getAmount();
                    }
                }
            }
        }

        DecimalFormat df   = new DecimalFormat("######0.00");
        result = Double.parseDouble(df.format(result));
        return result;
    }

    /**
     * 计算转了多少钱
     * @param orders
     * @return
     */
    @Override
    public double calculateMoney(List<Order> orders) {
        double result = 0;
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()){
            Set<BankRecord> bankRecords = orderIterator.next().getBankRecord();
            Iterator<BankRecord> brit = bankRecords.iterator();
            while (brit.hasNext()){
                BankRecord br = brit.next();
                if (br.isFinish()){
                    if (br.getType().equals("1")){//用户付款
                        result += br.getAmount();
                    }else if (br.getType().equals("3")|| br.getType().equals("4")){//退款
                        result -= br.getAmount();
                    }
                }
            }
        }

        DecimalFormat df   = new DecimalFormat("######0.00");
        result = Double.parseDouble(df.format(result));
        return result;
    }

    @Override
    public List<BankRecord> getConditionByEduIns(List<Order> orders) {
        List<BankRecord> result = new ArrayList<>();
        for (Order order : orders){
            Set<BankRecord> bankRecords = order.getBankRecord();
            Iterator<BankRecord> it = bankRecords.iterator();
            while (it.hasNext()){
                BankRecord bankRecord = it.next();
                if (bankRecord.getType().equals("2") || bankRecord.getType().equals("3")){
                    //2为线下订单
                    //退款订单
                    result.add(bankRecord);
                }
            }

        }
        return result;
    }

    @Override
    public double calculateEduInsMoney(List<Order> orders) {
        double result = 0;
        Iterator<Order> orderIterator = orders.iterator();
        while(orderIterator.hasNext()){
            Set<BankRecord> bankRecords = orderIterator.next().getBankRecord();
            Iterator<BankRecord> brit = bankRecords.iterator();
            while (brit.hasNext()){
                BankRecord br = brit.next();
                if (br.isFinish()){
                    if (br.getType().equals("2") || br.getType().equals("3")){//用户付款
                        result += br.getAmount();
                    }
                }
            }
        }

        DecimalFormat df   = new DecimalFormat("######0.00");
        result = Double.parseDouble(df.format(result));
        return result;
    }


}
