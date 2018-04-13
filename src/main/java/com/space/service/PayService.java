package com.space.service;

import com.space.entity.Bank;
import com.space.entity.BankRecord;
import com.space.entity.Order;

import java.util.List;
import java.util.Set;

/**
 * 网上支付的接口
 */
public interface PayService {
    /**
     * 网上支付
     * @param oid
     * @param bank
     * @return 1 账户密码错误 2 ：余额不足 3：超时付款 4：机构银行账户不存在
     */
    public String payByOnline(int oid, Bank bank);

    /**
     * 支付接口
     * @param fromBank
     * @param toBank
     * @param balance
     * @return
     */
    public boolean pay(Bank fromBank,Bank toBank,double balance);

    /**
     * 转账接口
     * @param fromBank 出发账号
     * @param toBank 收钱账号
     * @param balance 收款数
     * @return
     */
    public boolean pay(Bank fromBank,String toBank,double balance);

    /**
     * 根据账号查询财务状况
     * @param eduInsAccount
     * @return
     */
    public List<BankRecord> getConditionByAccount(String eduInsAccount);

    /**
     * 从订单获取消费的信息
     * @param orders
     * @return
     */
    public List<BankRecord> getConditionByOrderList(Set<Order> orders);

    /**
     * 从用户订单获取消费的信息
     * @param orders
     * @return
     */
    public List<BankRecord> getConditionByUser(Set<Order> orders);


    /**
     * 计算出管理员的利润
     * @param orders
     * @return
     */
    public double calculateMoneyByUser(Set<Order> orders);

    /**
     * 计算出管理员的利润
     * @param orders
     * @return
     */
    public double calculateMoney(List<Order> orders);


    /**
     * 获取财务状况
     * @param orders
     * @return
     */
    public List<BankRecord> getConditionByEduIns(List<Order> orders);

    /**
     * 计算机构的财务状况
     * @param orders
     * @return
     */
    public double calculateEduInsMoney(List<Order> orders);


}
