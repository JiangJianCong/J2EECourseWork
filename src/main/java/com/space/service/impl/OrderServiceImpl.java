package com.space.service.impl;

import com.space.config.OrderEndPayTimeAutoBean;
import com.space.dao.*;
import com.space.entity.*;
import com.space.service.OrderService;
import com.space.service.PayService;
import com.space.util.DateUtil;
import com.space.util.StringUtil;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("orderService")
/**
 * orderService的实现类
 */
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    @Resource
    private BankRepository bankRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private OrderEndPayTimeAutoBean orderEndPayTimeAutoBean;

    @Resource
    private PayService payService;

    @Resource
    private BankRecordRepository bankRecordRepository;

    @Value("${managerBankAccount}")
    private String managerBankAccount;

    /**
     * 根据用户查询相关订单
     * @param userId
     * @return
     */
    @Override
    public List<Order> queryOrderByUser(String userId) {
        if (StringUtil.isEmpty(userId)){
            return null;
        }

        List<Order> result = orderRepository.queryOrdersByUserId(userId);
        return result;
    }

    /**
     * 根据教育机构id查询相关订单
     * @param eduInsId
     * @return
     */
    @Override
    public List<Order> queryOrderByInstitution(String eduInsId) {
        if (StringUtil.isEmpty(eduInsId)){
            return null;
        }
        List<Order> result = orderRepository.queryOrdersByEdcInsId(eduInsId);
        return result;
    }

    /**
     * 网上下订单
     * @param order
     * @return
     */
    @Override
    public Order makeOrderByOnline(Order order) {
        Order result = null;
        BankRecord bankRecord = new BankRecord();
        String createOrderId = StringUtil.createOrderId();
        if (StringUtil.isEmpty(orderRepository.queryOrderIdByOrderId(createOrderId))){
            order.setOrderId(createOrderId);//设置订单id
            order.setOrderStatus("0");//订单状态0就是未付款


            order.setUser(userRepository.getOne(order.getUser().getId()));
            order.setEduInsPlan(eduInsPlanRepository.getOne(order.getEduInsPlan().getId()));


            double consume = order.getEduInsPlan().getPrice() * order.getStudents().size();
            order.getUser().getConsumeRecord();
            double discount =10- 0.5*((int)order.getUser().getConsumeRecord() / 500);
            if (discount <=7){
                discount = 7;
            }
            if (order.getUser().isMemberStatus()){
                 consume = consume* discount /10;
            }


            bankRecord.setToAccount(managerBankAccount);
            bankRecord.setFinish(false);
            bankRecord.setAmount(consume);

            bankRecord.setType("1");//待付款


            order.setConsume(consume);
            order.setOrderDate(new Date());

            result=orderRepository.save(order);
            bankRecord.setOrder(order);
            bankRecord = bankRecordRepository.save(bankRecord);


            //把下单的写进监听循环
            orderEndPayTimeAutoBean.addOrderEndPayTimeList(result.getId());

            return result;
        }

        return result;
    }

    /**
     * 线下支付，由教育机构输入信息
     * @param order
     * @return
     */
    @Override
    public Order makeOrderBySite(Order order) {
        Order result = null;
        BankRecord bankRecord = new BankRecord();
        String createOrderId = StringUtil.createOrderId();
        if (StringUtil.isEmpty(orderRepository.queryOrderIdByOrderId(createOrderId))){

            order.setOrderId(createOrderId);//设置订单id
            order.setOrderStatus("1");//订单状态1就是已付款

            order.setEduInsPlan(eduInsPlanRepository.findOne(order.getEduInsPlan().getId()));
            double consume = order.getEduInsPlan().getPrice() * order.getStudents().size();

            User isExist = null;
            if (order.getUser()!=null){
                isExist = userRepository.findByAccount(order.getUser().getAccount());
                System.out.println(isExist);
                order.setUser(isExist);
            }

            if (isExist !=null){//存在此会员

//                userRepository.findByAccount(order.getUser().getAccount());
                System.out.println(isExist);

                order.getUser().setId(isExist.getId());

                double discount =10- 0.5*((int)order.getUser().getConsumeRecord() / 500);
                if (discount <=7){
                    discount = 7;
                }
                if (order.getUser().isMemberStatus()){
                    consume = consume* discount /10;
                }
                order.setConsume(consume);
            }
            bankRecord.setFromAccount("无");
            bankRecord.setToAccount("无");
            bankRecord.setFinish(true);
            bankRecord.setAmount(consume);
            bankRecord.setType("2");//线下付款

            order.setConsume(consume);
            order.setOrderDate(new Date());


//            System.out.println(order);
            result=orderRepository.save(order);

            bankRecord.setOrder(order);
            bankRecord = bankRecordRepository.save(bankRecord);



//            //把下单的写进监听循环
//            orderEndPayTimeAutoBean.addOrderEndPayTimeList(result.getId());

            return result;
        }

        return result;
//        String createOrderId = StringUtil.createOrderId();
//        if (StringUtil.isEmpty(orderRepository.queryOrderIdByOrderId(createOrderId))){
//            int courseId = order.getEduInsPlan().getId();
//            EduInsPlan course = eduInsPlanRepository.findOne(courseId);
//            order.setEduInsPlan(course);
//            //此处应有倒计时
//            order.setOrderId(createOrderId);//设置订单id
//            order.setOrderStatus("0");//订单状态1就是已付款
//
//
//            Order save = orderRepository.save(order);
//            return save;
//        }
//        return null;
    }

    /**
     * 取消订单
     * @param order
     * @return
     */
    @Override
    @Transactional
    public BankRecord cancelOrder(Order order,String userBankAccount) {

        BankRecord bankRecord = new BankRecord();
//        orderRepository.updateOrder("3",order.getOrderId());
        //下面是判断时间来判断是否退钱或者少退
        String starDate = order.getEduInsPlan().getStartDate();

        try {
            Date date = DateUtil.formatString(starDate, "yyyy-MM-dd");
//			System.out.println(formatDate(date,"yyyy-MM-dd"));
            double consume = order.getConsume();
            int dayDif = (int) -(DateUtil.calculateDif(date)/(60*60*24));
            if (dayDif<=0){
                //一分钱不退
                order.setOrderStatus("4");
                bankRecord.setAmount(0);
            }else {
                double backBalance = consume ;
                if (dayDif<14){
                    backBalance = consume * dayDif / 14;
                }

//                System.out.println(dayDif+"--------------"+consume+"------------------"+backBalance);
                DecimalFormat df   = new DecimalFormat("######0.00");
                backBalance = Double.parseDouble(df.format(backBalance));
                order.setOrderStatus("3");//申请退款中，等待管理员审核


//                Iterator<BankRecord> iterator = order.getBankRecord().iterator();
//                while (iterator.hasNext()){
//                    iterator.next().setType("4");
//                    iterator.next().setFinish(true);
//                    bankRecordRepository.save(iterator.next());
//                    break;
//                }

                bankRecord.setType("4");
                bankRecord.setAmount(backBalance);
                bankRecord.setFromAccount("manager");
                bankRecord.setFinish(false);
                bankRecord.setToAccount(userBankAccount);
                bankRecord.setOrder(order);

                bankRecordRepository.save(bankRecord);
            }

            orderRepository.save(order);


        } catch (Exception e) {
            e.printStackTrace();
        }

        //先释放课程的空位
//
//        double money = 1000;
//        String eduInsBank = "123";
//        String userBank = "456";
//        Bank fromAccount = bankRepository.queryBankByAccount(eduInsBank);
//        if ((fromAccount.getBalance()-money) < 0){//机构账户不够钱扣
//            return false;
//        }
//        fromAccount.setBalance(fromAccount.getBalance()-money);
//        bankRepository.save(fromAccount);
//
//        Bank toAccount = bankRepository.queryBankByAccount(userBank);
//        toAccount.setBalance(toAccount.getBalance()+money);
//        bankRepository.save(toAccount);
        return bankRecord;
    }

    /**
     * 根据订单id查询某个订单
     * @param id
     * @return
     */
    @Override
    public Order findOrderById(Integer id) {
        return orderRepository.findOne(id);
    }

    /**
     * 根据状态寻找订单
     * @param orderStatus
     * @return
     */
    @Override
    public List<Order> findOrderByOrderStatus(String orderStatus) {
        return orderRepository.findAllByOrderStatus(orderStatus);
    }

    @Override
    public List findOrderIdByOrderStatus(String orderStatus) {
        return orderRepository.findIdByOrderStatus(orderStatus);
    }

    /**
     * 更新订单
     * @param order
     */
    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    /**
     * 管理员允许退款
     * @param bank
     * @param BRId
     * @return 银行账号不行就会return null;
     */
    @Override
    public BankRecord managerAllowRefund(Bank bank, int BRId) {
        BankRecord bankRecord = bankRecordRepository.findOne(BRId);

        if (!payService.pay(bank,bankRecord.getToAccount(),bankRecord.getAmount())){
            return null;
        }

        bankRecord.setFromAccount(bank.getAccount());
        bankRecord.setFinish(true);
        bankRecordRepository.save(bankRecord);
        //修改订单状态
        Order order = bankRecord.getOrder();
        order.setOrderStatus("4");
        orderRepository.save(order);
        return bankRecord;
    }

    /**
     * 获取所有订单
     * @return
     */
    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }


    @Override
    public BankRecord managerPayEduIns(double discount, int recordId) {
        BankRecord result = bankRecordRepository.findOne(recordId);
        if (result!=null){
            String eduInsAcc = result.getToAccount();
            String managerAcc = result.getFromAccount();
            double afterDiscount = discount * result.getAmount();
            Bank managerBank = new Bank();
            managerBank.setAccount(managerAcc);
            managerBank.setPassword("123");
            boolean pay = payService.pay(managerBank, eduInsAcc, afterDiscount);
            if (pay){
                result.setAmount(afterDiscount);
                result.setFinish(true);
                bankRecordRepository.save(result);
            }
        }
        return result;
    }

    @Override
    public void allotTeam(int stuId, int courseId, String team) {
        eduInsPlanRepository.allotTeam(stuId, courseId, team);
    }


}
