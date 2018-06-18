package com.space.course;


import com.space.dao.BankRepository;
import com.space.dao.OrderRepository;
import com.space.entity.Bank;
import com.space.entity.BankRecord;
import com.space.entity.Order;
import com.space.service.OrderService;
import com.space.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceTest {

    @Resource
    private PayService payService;

    @Resource
    private OrderRepository orderRepository;

    @Resource
    private BankRepository bankRepository;

    @Resource
    private OrderService orderService;
    @Test
    public void contextLoads(){

    }

    @Test
    public void testBank() {

        Bank byAccountAndPassword = bankRepository.findByAccountAndPassword("abcde", "11111");

        Bank abcde = bankRepository.queryBankByAccount("abcde");

        System.out.println("abcde:"+abcde);

        System.out.println(byAccountAndPassword);
    }

    @Test
    public void testMoney(){

        List<Order> allOrder = orderService.getAllOrder();
        double v = payService.calculateMoney(allOrder);
        System.out.println(v);
    }
}
