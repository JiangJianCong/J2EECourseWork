package com.space.controller;


import com.space.dao.StudentRepository;
import com.space.entity.*;
import com.space.service.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 经理的控制器
 */
@Controller
@RequestMapping("/manager")
public class ManagerController {


    @Resource
    private ManagerService managerService;

    @Resource
    private PayService payService;

    @Resource
    private EduInstitutionService eduInstitutionService;

    @Resource
    private OrderService orderService;

    @Resource
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("listOfEduInsW",eduInstitutionService.getAllEduInsWait());
//        modelAndView.addObject("a","b");
        modelAndView.setViewName("manager/managerIndex");
        return modelAndView;
    }

    /**
     * 获取所有会员的路由
     * @return
     */
    @RequestMapping("/getAllUser")
    public ModelAndView getAllUser(){
//        System.out.println(userService.getAllUser());
        ModelAndView mav = new ModelAndView();
        mav.addObject("userList",userService.getAllUser());
        mav.addObject("mainPage","manager/common/userList");
        mav.addObject("mainPageKey","#userList");
        mav.setViewName("manager/managerIndex");
        return mav;
    }


    /**
     * 获取所有机构的资料
     * @return
     */
    @RequestMapping("/getAllEduIns")
    public ModelAndView getAllEduInsList(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("eduInsList",eduInstitutionService.getAllEduIns());
        mav.addObject("mainPage","manager/common/eduInsList");
        mav.addObject("mainPageKey","#eduInsList");
        mav.setViewName("manager/managerIndex");

        return mav;
    }

    /**
     * 获取所有申请机构的资料
     * @return
     */
    @RequestMapping("/getAllRegisterEduIns")
    public ModelAndView getAllEduWaitInsList(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("eduInsRegisterList",eduInstitutionService.getAllEduInsWait(1));
        mav.addObject("mainPage","manager/common/eduInsRegisterList");
        mav.addObject("mainPageKey","#eduInsRegisterList");
        mav.setViewName("manager/managerIndex");
        return mav;
    }

    /**
     * 获取所有修改机构的资料
     * @return
     */
    @RequestMapping("/getAllModifyEduIns")
    public ModelAndView getAllEduWaitInsModifyList(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("eduInsModifyList",eduInstitutionService.getAllEduInsWait(2));
        mav.addObject("mainPage","manager/common/eduInsModifyList");
        mav.addObject("mainPageKey","#eduInsModifyList");
        mav.setViewName("manager/managerIndex");
        return mav;
    }



    /**
     * 取消会员资格
     * @param id
     * @return
     */
    @RequestMapping("/cancelMember/{id}")
    public ModelAndView cancelMember(@PathVariable(value = "id") Integer id){
        boolean result = managerService.cancelMember(id);
        ModelAndView mav = new ModelAndView();

        mav.setViewName("forward:/manager/getAllUser");

        return mav;

    }


    /**
     * 经理通过申请
     */
    @RequestMapping("/appEduInsRegister/{id}")
    public ModelAndView approveEduInsRegister(@PathVariable(value = "id") Integer id) throws Exception{
        ModelAndView mav = new ModelAndView();
        managerService.approveEduInsRegister(id);
        mav.addObject("eduInsRegisterList",eduInstitutionService.getAllEduInsWait(1));
        mav.addObject("mainPage","manager/common/eduInsRegisterList");
        mav.addObject("mainPageKey","#eduInsRegisterList");
        mav.setViewName("manager/managerIndex");
        return mav;
//        managerService.approveEduInsModify()
    }

    /**
     * 经理通过修改资料
     */
    @RequestMapping("/appEduInsModify/{id}")
    public ModelAndView approveEduInsModify(@PathVariable(value = "id") Integer id) throws Exception{
        System.out.println(id);
        ModelAndView mav = new ModelAndView();
        managerService.approveEduInsModify(id);
        mav.addObject("eduInsModifyList",eduInstitutionService.getAllEduInsWait(2));
        mav.addObject("mainPage","manager/common/eduInsModifyList");
        mav.addObject("mainPageKey","#eduInsModifyList");
        mav.setViewName("manager/managerIndex");
        return mav;
    }


    /**
     * 返回退订列表,前端未做完
     * @return
     */
    @RequestMapping("/getRefundList")
    public ModelAndView getRefundList(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("refundList","退订列表");
        mav.addObject("mainPage","manager/common/orderList");
        mav.addObject("mainPageKey","#order_list");
        mav.setViewName("manager/managerIndex");
        return mav;

    }

    /**
     * 获取订单
     * @return
     */
    @RequestMapping("/getAllOrderList")
    public ModelAndView getAllUserOrder(){
        ModelAndView mav = new ModelAndView();
        mav.addObject("orderList",orderService.getAllOrder());
        mav.addObject("mainPage","manager/common/orderList");
        mav.addObject("mainPageKey","#order_list");
        mav.setViewName("manager/managerIndex");
        return mav;
    }


    /**
     * 获取机构的详细信息,包含统计信息
     * @return
     */
    @RequestMapping("/getEduIns/{id}")
    public ModelAndView getEduIns(@PathVariable(value = "id") Integer id){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = eduInstitutionService.getEduInstitutionById(id);
        mav.addObject("eduIns",eduInstitution);
        mav.addObject("payRecordList",payService.getConditionByAccount(eduInstitution.getBankAccount()));
        mav.addObject("orderList",orderService.queryOrderByInstitution(eduInstitution.getEduInsId()));
        mav.addObject("mainPage","manager/common/eduInsDetail");
        mav.addObject("mainPageKey","#edu_ins_detail");
        mav.setViewName("manager/managerIndex");
        return mav;
    }


    /**
     * 查看用户详细信息
     * @param id
     * @return
     */
    @RequestMapping("/getUser/{id}")
    public ModelAndView getUser(@PathVariable(value = "id") Integer id){
        ModelAndView mav = new ModelAndView();
//        EduInstitution eduInstitution = eduInstitutionService.getEduInstitutionById(id);
        User user = userService.getUserById(id);
        mav.addObject("user",user);
        mav.addObject("payRecordList",payService.getConditionByOrderList(user.getOrders()));
        mav.addObject("mainPage","manager/common/userDetail");
        mav.addObject("mainPageKey","#user_detail");
        mav.setViewName("manager/managerIndex");
        return mav;

    }


    /**
     * 获取全部的财务状况
     * @return
     */
    @RequestMapping("/getCondition")
    public ModelAndView getFinancialCondition(){
        ModelAndView mav = new ModelAndView();
        List<BankRecord> manager = payService.getConditionByAccount("manager");
        mav.addObject("money",payService.calculateMoney(orderService.getAllOrder()));
        mav.addObject("payRecordList",manager);
        mav.addObject("mainPage","manager/common/financialCondition");
        mav.addObject("mainPageKey","#financial_condition");
        mav.setViewName("manager/managerIndex");
        return mav;
    }


    /**
     * 支付给机构
     * @param discount
     * @param recordId
     * @return
     */
    @RequestMapping("/payToEduIns")
    public ModelAndView payToEduIns(double discount,int recordId){
        ModelAndView mav = new ModelAndView();

        BankRecord bankRecord = orderService.managerPayEduIns(discount, recordId);
        if (bankRecord!=null){
            mav.addObject("title","支付成功");
            mav.addObject("desc","支付金额："+bankRecord.getAmount()+"元成功");
            mav.addObject("mainPage","common/messagePage");
            mav.addObject("mainPageKey","#message");
            mav.setViewName("manager/managerIndex");
        }else {
            mav.addObject("errorTitle","支付失败");
            mav.addObject("errorDesc","支付失败");
            mav.addObject("mainPage","common/errorPage");
            mav.addObject("mainPageKey","#error_message");
            mav.setViewName("manager/managerIndex");
        }

//        System.out.println(recordId);
//        System.out.println(discount);

        //mav.setViewName("forward:/manager/getCondition");
        return mav;
    }


    /**
     * 退款
     * @param recordId
     * @return
     */
    @RequestMapping("/refund")
    public ModelAndView refund(int recordId){
        ModelAndView mav = new ModelAndView();
        Bank bank = new Bank();
        bank.setAccount("manager");
        bank.setPassword("123");

        BankRecord bankRecord = orderService.managerAllowRefund(bank, recordId);
        if (bankRecord!=null){
            mav.addObject("title","退款成功");
            mav.addObject("desc","退款金额："+bankRecord.getAmount()+"元成功");
            mav.addObject("mainPage","common/messagePage");
            mav.addObject("mainPageKey","#message");
            mav.setViewName("manager/managerIndex");
        }else {
            mav.addObject("errorTitle","退款失败");
            mav.addObject("errorDesc","退款失败");
            mav.addObject("mainPage","common/errorPage");
            mav.addObject("mainPageKey","#error_message");
            mav.setViewName("manager/managerIndex");
        }

//        System.out.println(recordId);
//        System.out.println(discount);

        //mav.setViewName("forward:/manager/getCondition");
        return mav;
    }


    /**
     * 获取老师列表
     * @param session
     * @return
     */
    @RequestMapping("/teacherList")
    public ModelAndView allTeacher(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");

//        if (eduInstitution==null) {
//            mav.setViewName("forward:/eduInsLoginPage");
//            return mav;
//        }

        List<Teacher> allTeacher = eduInstitutionService.getAllTeacher();
        mav.addObject("tl",allTeacher);

        mav.addObject("mainPage","manager/common/managerTeacherList");
        mav.addObject("mainPageKey","#teacher_list");
        mav.setViewName("manager/managerIndex");

        return mav;
    }

    /**
     * 学生列表
     * @param session
     * @return
     */
    @RequestMapping("/studentList")
    public ModelAndView allStudent(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");

//        if (eduInstitution==null) {
//            mav.setViewName("forward:/eduInsLoginPage");
//            return mav;
//        }


        List <Student> list = userService.getAllStudent();
        mav.addObject("sl",list);
        mav.addObject("mainPage","manager/common/managerStuList");
        mav.addObject("mainPageKey","#stu_list");
        mav.setViewName("/manager/managerIndex");

        return mav;

    }


}
