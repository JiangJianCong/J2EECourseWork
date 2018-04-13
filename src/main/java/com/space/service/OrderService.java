package com.space.service;

import com.space.entity.Bank;
import com.space.entity.BankRecord;
import com.space.entity.Order;

import java.util.List;

/**
 * 订单的service的接口
 */
public interface OrderService {

    /**
     * 根据用户id查询订单
     * @param userId
     * @return
     */
    public List<Order> queryOrderByUser(String userId);

    /**
     * 根据教育机构id查询订单
     * @param eduInsId
     * @return
     */
    public List<Order> queryOrderByInstitution(String eduInsId);

    /**
     * 线上下订单，用户自行下订单
     * @param order
     * @return
     */
    public Order makeOrderByOnline(Order order);

    /**
     * 线下报名，免去线上支付部分
     * @param order
     * @return
     */
    public Order makeOrderBySite(Order order);

    /**
     * 取消订单
     * @param order
     * @return
     */
    public BankRecord cancelOrder(Order order, String userBankAccount);


    /**
     * 根据id查询某个订单
     * @param id
     * @return
     */
    public Order findOrderById(Integer id);

    /**
     * 根据状态寻找订单
     * @param orderStatus
     * @return
     */
    public List<Order> findOrderByOrderStatus(String orderStatus);

    /**
     * 根据状态查询订单的id
     * @param orderStatus
     * @return
     */
    public List<Integer> findOrderIdByOrderStatus(String orderStatus);

    /**
     * 更新保存订单
     * @param order
     */
    public void saveOrder(Order order);


    /**
     * 管理员允许退款
     * @param bank
     * @param BRId
     * @return
     */
    public BankRecord managerAllowRefund(Bank bank,int BRId);


    /**
     * 获取所有订单
     * @return
     */
    public List<Order> getAllOrder();


    /**
     * 根据id查找订单和折扣
     * @param discount
     * @param recordId
     * @return
     */
    public BankRecord managerPayEduIns(double discount,int recordId);


    /**
     * 分配班级
     * @param stuId
     * @param courseId
     * @param team
     */
    public void allotTeam(int stuId,int courseId,String team);

}
