package com.space.controller;

import com.space.entity.*;
import com.space.service.EduInstitutionService;
import com.space.service.OrderService;
import com.space.service.PayService;
import com.space.service.UserService;
import com.space.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @Resource
    private PayService payService;

    @Resource
    private EduInstitutionService eduInstitutionService;

    @Value("${managerBankAccount}")
    private String managerAccount;


    /**
     * 用户下订单
     * @param stuList
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("/userMakeOrder") //根据bySite判断在哪里进行下单
    public ModelAndView makeOrder(String bySite,String choCourseList,String stuList,Order order,HttpSession session){
        ModelAndView mav = new ModelAndView();
//        System.out.println(choCourseList);
//        System.out.println(choCourseList);
        String []   classList=null;
        if (StringUtil.isNotEmpty(choCourseList)){

            classList = choCourseList.substring(0, choCourseList.length() - 1).split(",");
        }
//        System.out.println(classList);

        boolean isBySite = false;
        if (bySite.equals("t")){
            isBySite = true;
        }
        User user = (User) session.getAttribute("user");
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (user!=null || eduInstitution!=null){
            String s = stuList.substring(0,stuList.length()-1);
            String[] split = s.split(",");
            Set<Student> studentSet = new HashSet<Student>();
            EduInsPlan course = eduInstitutionService.getEduInsPlanById(order.getEduInsPlan().getId());
            for (int i=0;i<split.length;i++){
                String[] stuS = split[i].split("-");
                Student student = new Student();
                student.setStudentId(stuS[0]);
                student.setStudentName(stuS[1]);

                Set<EduInsPlan> courses = new HashSet<>();
                courses.add(course);
                student.setCourse(courses);

                studentSet.add(student);
            }
            Set<Student> students = userService.addStudent(studentSet);




            order.setStudents(students);

            Order result = new Order();
            if (isBySite){
                result = orderService.makeOrderBySite(order);
                mav.addObject("successMessage","订单: "+result.getOrderId()+"下单成功,"+"请支付:"+result.getConsume()+"元");

                mav.addObject("backLink","/eduInsView/getAllCourseList");
                mav.addObject("mainPage","/eduInstitution/successPage");
                mav.addObject("mainPageKey","#success_message");
                mav.setViewName("eduInstitution/eduInstitutionMain");

            }else {
                order.setUser(user);
                result = orderService.makeOrderByOnline(order);
                mav.addObject("order",result);
                mav.addObject("mainPage","/user/userPay");
                mav.addObject("mainPageKey","#user_pay");
                mav.setViewName("user/userMain");
            }

            if (result.getStudents().size() <=3 ){
                int courseId = result.getEduInsPlan().getId();//
                Iterator<Student> studentIterator = result.getStudents().iterator();
                int i = 0;
                while (studentIterator.hasNext()){
                    int stuId = studentIterator.next().getId();
                    if (i<3){
                        String courseTeam = classList[i];
                        i++;
//                        System.out.println(stuId);
                        orderService.allotTeam(stuId,courseId,courseTeam);
                    }

                }

            }


        }else {
            mav.addObject("errorMessage","尚未登陆，不能下单");
            mav.setViewName("user/userLogin");
        }
//        mav.setViewName("index");
        return mav;
    }
    /**
     * 付钱
     * @param oid
     * @param bank
     * @return
     */
    @RequestMapping("/userPay")
    public ModelAndView userPay(Integer oid, Bank bank,HttpSession session){
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("user")==null){
            mav.addObject("errorMessage","尚未登陆");
            mav.setViewName("user/userLogin");
            return mav;
        }else {

            String message = "";
            switch (payService.payByOnline(oid, bank)) {
                case "1" : message = "用户银行账号错误";
                        break;
                case "2" : message = "余额不足";
                    break;
                case "3" : message = "超时付款，请重新下单";
                    break;
                case "4" : message = "机构银行错误";
                    break;
                default:message="付款成功";
            }
            mav.addObject("payMessage",message);
            mav.addObject("bank",bank);

            mav.setViewName("forward:/order/userOrderDetail/"+oid);
            return mav;
        }


    }

    /**
     * 订单详细
     * @param session
     * @return
     */
    @RequestMapping("/userOrderDetail/{id}")
    public ModelAndView orderDetail(@PathVariable(value = "id") Integer id, HttpSession session){
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if (user!=null){
            Order order = orderService.findOrderById(id);

            if (order!=null){
                mav.addObject("order",order);
                mav.addObject("mainPage","user/userPay");
                mav.addObject("mainPageKey","#user_pay");
                mav.setViewName("user/userMain");
            }else{
                mav.addObject("errorTitle","订单查询错误");
                mav.addObject("errorDesc","不存在此订单");
                mav.addObject("mainPage","common/errorPage");
                mav.addObject("mainPageKey","#error_message");
                mav.setViewName("user/userMain");

            }

        }else {
            mav.addObject("errorMessage","尚未登陆");
            mav.setViewName("user/UserLogin");
        }
        return mav;
    }

    /**
     * 获取用户所有订单
     * @return
     */
    @RequestMapping("/getUserOrderList")
    public ModelAndView getOrderListPage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        if (session.getAttribute("user")!=null){
            User user = userService.getUserById(((User) session.getAttribute("user")).getId());
            mav.addObject("orderList",user.getOrders());
            mav.addObject("mainPage","user/userOrderList");
            mav.addObject("mainPageKey","#order_list");
            mav.setViewName("user/userMain");

        }else {
            mav.addObject("errorMessage","尚未登陆");
            mav.setViewName("user/userLogin");
        }
        return mav;
    }


    /**
     * 用户退款的路由
     * @param session
     * @param bankAccount 收款账号
     * @return
     */
    @RequestMapping("/userRefund")
    public ModelAndView userRefund(HttpSession session,String bankAccount,Integer oid){
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("user");

        if (user!=null){
            Order order = orderService.findOrderById(oid);
            BankRecord bankRecord = orderService.cancelOrder(order, bankAccount);

            mav.addObject("title","申请退款成功");
            mav.addObject("desc","申请退款:"+bankRecord.getAmount()+"元成功");
            mav.addObject("mainPage","common/messagePage");
            mav.addObject("mainPageKey","#message");
            mav.setViewName("user/userMain");

        }else {
            mav.addObject("errorMessage","尚未登陆");
            mav.setViewName("user/userLogin");
        }
        return mav;
    }


    /**
     * 管理员允许退款
     * @param session
     * @param bank
     * @param bankRecordId
     * @return
     */
    @RequestMapping("/manager/allowRefund")
    public ModelAndView managerAllowRefund(HttpSession session,Bank bank,Integer bankRecordId){
        ModelAndView mav = new ModelAndView();


        BankRecord bankRecord = orderService.managerAllowRefund(bank, bankRecordId);
        if (bankRecord==null){
            //账号密码错误
            mav.addObject("errorTitle","银行错误");
            mav.addObject("errorDesc","账号资金不足或者账号密码错误");
            mav.addObject("mainPage","common/errorPage");
            mav.addObject("mainPageKey","#error_message");


        }else {
            mav.addObject("backLink","/manager/getRefundList");
            mav.addObject("title","批准退款成功");
            mav.addObject("desc","退款:"+bankRecord.getAmount()+"元");
            mav.addObject("mainPage","common/messagePage");
            mav.addObject("mainPageKey","#message");

            //正常操作
        }

        mav.setViewName("manager/managerIndex");
        return mav;
    }
}
