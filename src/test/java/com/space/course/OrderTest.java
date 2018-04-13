package com.space.course;

import com.space.config.OrderEndPayTimeAutoBean;
import com.space.dao.*;
import com.space.entity.*;
import com.space.service.OrderService;
import com.space.service.UserService;
import com.space.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {



    @Resource
    private OrderRepository orderRepository;

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private OrderService orderService;

    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    @Resource
    private BankRecordRepository bankRecordRepository;

    @Resource
    private UserService userService;

    @Resource
    private UserRepository userRepository;

    @Value("${managerBankAccount}")
    private String managerAccount;

    @Test
    public void contextLoads() {


        Student student1 = studentRepository.findByStudentId("991");
        Student student2 = studentRepository.findByStudentId("992");
        Student student3 = studentRepository.findByStudentId("993");


        Set<Student> students = null;
        students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Order order = new Order();
        order.setOrderId("order2");

        order.setStudents(students);
        orderRepository.save(order);
    }

    @Test
    public void testTime(){
        Order one = orderRepository.findOne(11);
        System.out.println(one.getOrderDate());
        System.out.println(DateUtil.formatDate(one.getOrderDate(),"yyyy-MM-dd hh:mm:ss"));
        System.out.println(one);

    }

    @Test
    public void testAuto(){

    }


    @Test
    public void cancelOrderTest(){
        Order order = orderService.findOrderById(10);
        orderService.cancelOrder(order,"abc");

//        System.out.println(order);

    }
    @Test
    public void testYML(){
        System.out.println(managerAccount);
    }

    @Test
    public void testBankRecord(){

        Order order = orderRepository.findOne(21);
        System.out.println(order);
    }


    @Test
    public void testGetOrderIdList(){
        List orderIdByOrderStatus = orderRepository.findIdByOrderStatus("0");
        System.out.println(orderIdByOrderStatus);
    }


    @Test
    public void testManagerRefund(){
        Order order = orderRepository.findOne(30);
        Bank bank = new Bank();
        bank.setAccount("manager");
        bank.setPassword("123");
        BankRecord bankRecord = orderService.managerAllowRefund(bank, 14);
        System.out.println(bankRecord);

    }


    @Test
    public void testMakeOrderBySite(){
        Order order = new Order();

        User user = new User();
        user.setAccount("aa");

        order.setUser(user);
        orderService.makeOrderBySite(order);
        System.out.println(order);

//        EduInsPlan eduInsPlan = new EduInsPlan();
//        eduInsPlan.setId(3);
//
//        Set<Student> students = new HashSet<>();
//        Student student = studentRepository.findOne(13);
//        students.add(student);
//
//        order.setUser(user);
//        order.setEduInsPlan(eduInsPlan);
//        order.setStudents(students);
//        orderService.makeOrderBySite(order);
//
//        Order order1 = orderService.makeOrderBySite(order);
//        System.out.println(order1);
    }
}
