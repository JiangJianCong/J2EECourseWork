package com.space.dao;


import com.space.entity.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 订单的repository的接口
 */
public interface OrderRepository extends JpaRepository<Order,Integer>,JpaSpecificationExecutor<Order>{

    /**
     * 根据userId查询所有相关的订单
     * @param userId
     * @return
     */
    @Query(value = "select * from t_order where user_id = ?1",nativeQuery = true)
    public List<Order> queryOrdersByUserId(String userId);


    /**
     * 根据教学机构的Id查询相关订单
     * @param eduInsId
     * @return
     */
    @Query(value = "select * from t_order where edu_ins_id = ?1", nativeQuery = true)
    public List<Order> queryOrdersByEdcInsId(String eduInsId);

    /**
     * 查询是否重复订单id
     * @param orderId
     * @return
     */
    @Query(value = "select order_id from t_order where order_id = ?1",nativeQuery = true)
    public String queryOrderIdByOrderId(String orderId);


    @Modifying
    @Transactional
    @Query(value = "update t_order set order_status = ?1 where  order_id = ?2",nativeQuery = true)
    public void updateOrder(String status,String orderId);

    /**
     * 根据订单状态查询不同订单
     * @param orderStatus
     * @return
     */
    public List<Order> findAllByOrderStatus(String orderStatus);

    /**
     * 根据订单状态查询不同id
     * @param orderStatus
     * @return
     */
    @Query(value = "select id from t_order where order_status = ?1",nativeQuery = true)
    public List findIdByOrderStatus(String orderStatus);




}
