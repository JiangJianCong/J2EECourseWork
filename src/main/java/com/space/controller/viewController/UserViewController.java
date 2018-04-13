package com.space.controller.viewController;

import com.space.entity.*;
import com.space.service.EduInstitutionService;
import com.space.service.OrderService;
import com.space.service.PayService;
import com.space.service.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户的路由
 */
@Controller
@RequestMapping("/userView")
public class UserViewController {

    @Resource
    private UserService userService;

    @Resource
    private OrderService orderService;

    @Resource
    private EduInstitutionService eduInstitutionService;

    @Resource
    private PayService payService;

    @RequestMapping(value = {"/",""})
    public ModelAndView index(HttpSession session){
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("user");

        if (user!=null){
            user = userService.getUserById(user.getId());
//            System.out.println(user);
            mav.addObject("mainPage","user/userData");
            mav.addObject("mainPageKey","#user_data");
            mav.addObject("user",userService.getUserById(user.getId()));
            mav.setViewName("user/userMain");

        }else {
            mav.setViewName("user/UserLogin");
        }
        return  mav;
    }

//    /**
//     * 用户注册
//     * @param user
//     */
//    @RequestMapping("/register")
//    public ModelAndView userRegister(User user){
//        ModelAndView mav = new ModelAndView();
//        boolean result = userService.userRegister(user);
//        System.out.println(user);
//        if (result){
//            mav.addObject("user",user);
//            mav.setViewName("index");
//        }else {
//            mav.addObject("errorMessage","账户重复");
//            mav.setViewName("user/userRegister");
//        }
//
//        return mav;
//    }

//    /**
//     * 用户登陆
//     * @param user
//     */
//    @RequestMapping("/login")
//    public ModelAndView userLogin(User user,HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        User result = userService.userLogin(user);
//        User userSession = (User) session.getAttribute("user");
//        if (userSession!=null){
//            mav.setViewName("forward:/userView/");
//        }else {
//
//            if (null!=result){
//                session.setAttribute("user",result);
//                mav.setViewName("forward:/userView/");
//            }else {
//                mav.addObject("errorMessage","用户名或密码错误");
//                mav.setViewName("user/userLogin");
//            }
//
//        }
//        return mav;
//    }


    /**
     * 用户注销
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String userLogout(HttpSession session){
        session.removeAttribute("user");
        return "user/userLogin";
    }




    /**
     * 根据名字查询
     * @param name
     */
    @RequestMapping("/queryByName")
    public void queryUserByName(String name){

    }

//    /**
//     * 获取用户所有订单
//     * @return
//     */
//    @RequestMapping("/getOrderList")
//    public ModelAndView getOrderListPage(HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        if (session.getAttribute("user")!=null){
//            User user = userService.getUserById(((User) session.getAttribute("user")).getId());
//            mav.addObject("orderList",user.getOrders());
//            mav.addObject("mainPage","user/userOrderList");
//            mav.addObject("mainPageKey","#order_list");
//            mav.setViewName("user/userMain");
//
//        }else {
//            mav.addObject("errorMessage","尚未登陆");
//            mav.setViewName("user/userLogin");
//        }
//        return mav;
//    }




    /**
     * 获取学生的点到情况
     * @return
     */
    @RequestMapping("/getCourseCondition")
    public ModelAndView getCourseCondition(HttpSession session,String studentId){
        ModelAndView mav = new ModelAndView();

        if (session.getAttribute("user")!=null){
//            String studentId = "151250070";
            List<EduInsRecord> list = eduInstitutionService.getAllCourseRecordByStudentId(studentId);
            if (list==null){
                mav.addObject("errorMessage","不存在该生");
                mav.setViewName("userView/chooseStuPage");

                return mav;
            }
            mav.addObject("recordList",list);
            mav.addObject("mainPage","/user/userCourseCondition");
            mav.addObject("mainPageKey","#course_condition");
            mav.setViewName("user/userMain");


        }else {
            mav.addObject("errorMessage","尚未登陆");
            mav.setViewName("user/userLogin");
        }

        return mav;
    }


    /**
     * 选学生列表
     * @return
     */
    @RequestMapping("/chooseStuPage")
    public ModelAndView choosePage(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("mainPage","/user/userChooseStuPage");
        mav.addObject("mainPageKey","#stu_choose");
        mav.setViewName("user/userMain");
        return mav;
    }


    @RequestMapping("/getStudentCourseList")
    public ModelAndView getCourseListByStudentId(String studentId){
        ModelAndView mav = new ModelAndView();
        Student result = userService.getStudentById(studentId);
        if (result!=null ) {
            mav.addObject("student",result);
            mav.addObject("courseList",result.getCourse());
            mav.addObject("mainPage","/user/userCourse");
            mav.addObject("mainPageKey","#user_course");
        }else {
            mav.addObject("errorTitle","获取课程列表失败");
            mav.addObject("errorDesc","获取课程列表失败");
            mav.addObject("mainPage","/common/errorPage");
            mav.addObject("mainPageKey","#error_message");

        }
        mav.setViewName("user/userMain");
        return mav;
    }


    /**
     * 获取财务状况
     * @return
     */
    @RequestMapping("/financialCondition")
    public ModelAndView getConsumeRecord(HttpSession session){
        ModelAndView mav = new ModelAndView();

        Integer userId = ((User) session.getAttribute("user")).getId();
        User user = userService.getUserById(userId);
        List<BankRecord> result = payService.getConditionByUser(user.getOrders());


        if (result!=null ) {
            mav.addObject("money",payService.calculateMoneyByUser(user.getOrders()));
            mav.addObject("payRecordList",result);
            mav.addObject("mainPage","/user/userConsumeRecord");
            mav.addObject("mainPageKey","#user_consume_record");
        }else {
            mav.addObject("errorTitle","获取财务状况失败");
            mav.addObject("errorDesc","原因不明");
            mav.addObject("mainPage","/common/errorPage");
            mav.addObject("mainPageKey","#error_message");

        }
        mav.setViewName("user/userMain");
        return mav;

    }


    /**
     * 获取成绩列表
     * @param session
     * @return
     */
    @RequestMapping("/getGrade")
    public ModelAndView getGrade(HttpSession session,String studentId){
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if (user==null){
            mav.setViewName("forward:/userView");
        }
        List<StuScore> list = eduInstitutionService.getStudentGradeByStudentId(studentId);
        mav.addObject("gradeList",list);
        mav.addObject("mainPage","eduInstitution/eduInsGradePage");
        mav.addObject("mainPageKey","#grade_page");
        mav.setViewName("user/userMain");

        return mav;

    }

//    /**
//     * 用户下订单
//     * @param stuList
//     * @param order
//     * @param session
//     * @return
//     */
//    @RequestMapping("/makeOrder")
//    public ModelAndView makeOrder(String stuList,Order order,HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        User user = (User) session.getAttribute("user");
//        if (user!=null){
//            String s = stuList.substring(0,stuList.length()-1);
//            String[] split = s.split(",");
//            Set<Student> studentSet = new HashSet<Student>();
//            for (int i=0;i<split.length;i++){
//                String[] stuS = split[i].split("-");
//                Student student = new Student();
//                student.setStudentId(stuS[0]);
//                student.setStudentName(stuS[1]);
//                studentSet.add(student);
//            }
//            Set<Student> students = userService.addStudent(studentSet);
//            order.setStudents(students);
//            order.setUser(user);
//            Order result = orderService.makeOrderByOnline(order);
//            mav.addObject("order",result);
//            mav.addObject("mainPage","/user/userPay");
//            mav.addObject("mainPageKey","#user_pay");
//            mav.setViewName("user/userMain");
//        }else {
//            mav.addObject("errorMessage","尚未登陆，不能下单");
//            mav.setViewName("user/userLogin");
//        }
//        return mav;
//    }

//    /**
//     * 订单详细
//     * @param session
//     * @return
//     */
//    @RequestMapping("/orderDetail/{id}")
//    public ModelAndView orderDetail(@PathVariable(value = "id") Integer id,HttpSession session){
//        ModelAndView mav = new ModelAndView();
//        User user = (User) session.getAttribute("user");
//        if (user!=null){
//            Order order = orderService.findOrderById(id);
//
//            if (order!=null){
//                mav.addObject("order",order);
//                mav.addObject("mainPage","user/userPay");
//                mav.addObject("mainPageKey","#user_pay");
//                mav.setViewName("user/userMain");
//            }else{
//                mav.addObject("errorTitle","订单查询错误");
//                mav.addObject("errorDesc","不存在此订单");
//                mav.addObject("mainPage","common/errorPage");
//                mav.addObject("mainPageKey","#error_message");
//                mav.setViewName("user/userMain");
//
//            }
//
//        }else {
//            mav.addObject("errorMessage","尚未登陆");
//            mav.setViewName("user/UserLogin");
//        }
//        return mav;
//    }
}
