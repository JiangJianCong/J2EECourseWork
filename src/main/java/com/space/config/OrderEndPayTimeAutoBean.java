package com.space.config;


import com.space.entity.Order;
import com.space.service.OrderService;
import com.space.util.DateUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;

//自动装载
@Component
public class OrderEndPayTimeAutoBean extends Thread{

    @Resource
    private OrderService orderService;
    //新建全局变量,订单记录将存放在这里
    List<Integer> orderList = new ArrayList<Integer>();

    //服务启动自动加载
    @PostConstruct
    public void init() {
        Map<String, Object> param = new HashMap<String, Object>();
        orderList = orderService.findOrderIdByOrderStatus("0");
    //获取数据库存在的订单截止时间记录


    //开始线程
        this.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 1; i--) {
            try {
                //每秒运行
                sleep(1000 * 5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            if (orderList!=null){
                Iterator<Integer> it = orderList.iterator();
                while (it.hasNext()){
                    int oid = it.next();
                    Order order = orderService.findOrderById(oid);
                    Date beforeDate = order.getOrderDate();
                    int s = (int) DateUtil.calculateDif(beforeDate);
                    int min = s/60;
                    if (min > 1 ) {
                        //订单自动取消
                        System.out.println(order.getOrderId()+" 过时订单取消");
                        order.setOrderStatus("2");//过时订单
                        orderService.saveOrder(order);

                        it.remove();
                    }
                }
            }else {
                System.out.println("无需轮询");
            }
        }
    }


    //list中添加数据,创建订单是调用
    public void addOrderEndPayTimeList (int id){

        System.out.println("创建订单："+id);
        orderList.add(id);
    }
    //list中删除数据,完成支付或者取消订单时调用
    public void removeOrderEndPayTimeList (int id){
        System.out.println("id："+id+" ,支付成功，申请删除轮询订单");
        if (orderList.contains(id)) {
            List<Integer> removeList = new ArrayList<Integer>();
            removeList.add(id);
            orderList.removeAll(removeList);
            System.out.println("轮询列表删除");
        }
    }
}
