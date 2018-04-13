package com.space.controller.viewController;

import com.space.entity.*;
import com.space.service.EduInstitutionService;
import com.space.service.OrderService;
import com.space.service.PayService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

/**
 * 渲染视图的controller
 */
@Controller
    @RequestMapping("/eduInsView")
public class EduInsViewController {

    @Resource
    private EduInstitutionService eduInstitutionService;

    @Resource
    private OrderService orderService;

    @Resource
    private PayService payService;


    /**
     * 访问首页
     * @return
     */
    @RequestMapping("/")
    public ModelAndView index(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
        if (eduIns!=null) {


            EduInstitution eduInstitution = eduInstitutionService.queryEduInstitutionByEduInsId(eduIns.getEduInsId());
            EduInsWait eduInsWait = eduInstitutionService.getEduInsWaitByEduInsId(eduInstitution.getEduInsId());
            mav.addObject("eduInsWait", eduInsWait);
            mav.addObject("eduInstitution", eduInstitution);
            mav.addObject("mainPage", "eduInstitution/eduInsData");
            mav.addObject("mainPageKey", "#edu_ins_data");
            mav.setViewName("eduInstitution/eduInstitutionMain");
        }else {
            mav.setViewName("forward:/eduInsLoginPage");
        }
        return mav;
    }


//    /**
//     * 登陆
//     * @param account
//     * @param password
//     * @return
//     */
//    @RequestMapping("/login")
//    public ModelAndView eduInsLogin(String account, String password, HttpSession session){
//        EduInstitution result = eduInstitutionService.eduInsLogin(account, password);
//        System.out.println(result);
//        ModelAndView mav = new ModelAndView();
//        if (null!=result){
//            session.setAttribute("eduIns",result);
//            mav.addObject("eduInstitution",result);
//            mav.addObject("mainPage","eduInstitution/eduInsData");
//            mav.addObject("mainPageKey","#edu_ins_data");
//            mav.setViewName("eduInstitution/eduInstitutionMain");
//        }else {
//            mav.addObject("error","账号或密码错误");
//            mav.setViewName("eduInstitution/eduInsLogin");
//        }
//        return mav;
//    }

    /**
     * 机构推销登陆
     * @return
     */
    @RequestMapping("/logout")
    public String Logout(HttpSession session){
        session.removeAttribute("eduIns");

        return "eduInstitution/eduInsLogin";
    }

    /**
     * 打开计划界面
     * @return
     */
    @RequestMapping("/issuePlanPage")
    public ModelAndView eduInsIssuePlanPage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
//        System.out.println(eduInstitutionService.getTeacherByEduInsId(eduIns.getId()));
        mav.addObject("teacherList",eduInstitutionService.getTeacherByEduInsId(eduIns.getId()));
        mav.addObject("mainPage","eduInstitution/eduInsIssuePlan");
        mav.addObject("mainPageKey","#edu_ins_issue_plan");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }

    /**
     * 实行计划
     * @param eduInsPlan
     * @param session
     * @return
     */
    @RequestMapping("/makePlan")
    public ModelAndView eduInsMakePlan(EduInsPlan eduInsPlan,HttpSession session){
        System.out.println(eduInsPlan.getTeacher().getId());
        ModelAndView mav = new ModelAndView();
        EduInstitution sessionEdu = (EduInstitution) session.getAttribute("eduIns");
        EduInstitution eduInstitution = eduInstitutionService.getEduInstitutionById(sessionEdu.getId());
        eduInsPlan.setEduInstitution(eduInstitution);
        String plan = eduInstitutionService.issuePlan(eduInsPlan);
        mav.addObject("successMessage","发布成功，课程代码："+plan);
        mav.addObject("mainPage","eduInstitution/successPage");
        mav.addObject("mainPageKey","#success_message");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }

    /**
     * 获取所有课程的列表
     * @return
     */
    @RequestMapping("/getAllCourseList")
    public ModelAndView getAllCourseList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
        if (eduIns==null){
            mav.setViewName("forward:/eduInsLogin");
            return mav;
        }
        List<EduInsPlan> courseList = eduInstitutionService.getAllEduInsPlanByEduInsId(eduIns.getEduInsId());
        mav.addObject("courseList",courseList);
        mav.addObject("mainPage","eduInstitution/eduInsCourseList");
        mav.addObject("mainPageKey","#course_list");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }


    /**
     * 得到课程的细节
     * @param id
     * @return
     */
    @RequestMapping("/getCourseDetail/{id}")
    public ModelAndView getCourseDetail(@PathVariable(value = "id") Integer id, HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInsPlan course = eduInstitutionService.getEduInsPlanById(id);

        EduInstitution eduInstitution= (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution==null){
            mav.setViewName("forward:/eduInsLoginPage");
            return mav;
        }


        if (course==null){
            mav.addObject("errorTitle","找不到相关课程");
            mav.addObject("errorDesc","访问错误");
            mav.addObject("mainPage","/common/errorPage");
            mav.addObject("mainPageKey","#error_message");
            mav.setViewName("eduInstitution/eduInstitutionMain");
            return mav;
        }
        mav.addObject("course",course);
        mav.addObject("mainPage","eduInstitution/courseDetail");
        mav.addObject("mainPageKey","#course_detail");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }


    /**
     * 进入上课登记页面
     * @return
     */
    @RequestMapping("/courseRecordPage")
    public ModelAndView courseRecordPage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
        eduIns = eduInstitutionService.getEduInstitutionById(eduIns.getId());
        mav.addObject("courseList",eduIns.getEduInsPlans());
        mav.addObject("mainPage","/eduInstitution/eduInsRecord");
        mav.addObject("mainPageKey","#record");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }



    /**
     * 进行登记
     * @return
     */
    @RequestMapping("/makeRecord")
    public ModelAndView makeRecord(EduInsRecord record){

        ModelAndView mav =  new ModelAndView();
        boolean b = eduInstitutionService.checkIn(record);
        mav.addObject("successMessage","登记成功");
        mav.addObject("backLink","/eduInsView/courseRecordPage");
        mav.addObject("mainPage","eduInstitution/successPage");
        mav.addObject("mainPageKey","#success_message");
        mav.setViewName("eduInstitution/eduInstitutionMain");

        return mav;
    }


    /**
     * 打开成绩登记页面
     * @param session
     * @return
     */
    @RequestMapping("/gradeRecordPage")
    public ModelAndView gradeRecordPage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
        eduIns = eduInstitutionService.getEduInstitutionById(eduIns.getId());
        mav.addObject("courseList",eduIns.getEduInsPlans());
        mav.addObject("mainPage","/eduInstitution/eduInsGradeRecord");
        mav.addObject("mainPageKey","#record");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }

    /**
     * 进行登记
     * @return
     */
    @RequestMapping("/makeGradeRecord")
    public ModelAndView makeGradeRecord(StuScore record){
//        System.out.println(record);

        ModelAndView mav =  new ModelAndView();
//        boolean b = eduInstitutionService.checkIn(record);
        String s = eduInstitutionService.makeGradeRecord(record);
        mav.addObject("successMessage",s);
        mav.addObject("backLink","/eduInsView/gradeRecordPage");
        mav.addObject("mainPage","eduInstitution/successPage");
        mav.addObject("mainPageKey","#success_message");
        mav.setViewName("eduInstitution/eduInstitutionMain");

        return mav;
    }

    /**
     * 返回学生成绩,所有课程
     * @return
     */
    @RequestMapping("/getGradePage")
    public ModelAndView getGradePage(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution==null){
            mav.setViewName("forward:/eduInsLoginPage");
        }
        List<StuScore> list = eduInstitutionService.getEduInsGradeListById(eduInstitution.getId());
        mav.addObject("gradeList",list);
        mav.addObject("mainPage","eduInstitution/eduInsGradePage");
        mav.addObject("mainPageKey","#grade_page");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }


    /**
     * 获取机构的点到情况
     * @return
     */
    @RequestMapping("/getCourseCondition")
    public ModelAndView getCourseCondition(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution==null){
            mav.setViewName("forward:/eduInsLogin");
        }

        String eduInsId = eduInstitution.getEduInsId();
        List<EduInsRecord> list = eduInstitutionService.getAllCourseRecordByEduInsId(eduInsId);
        mav.addObject("recordList",list);
        mav.addObject("mainPage","/eduInstitution/eduInsCourseCondition");
        mav.addObject("mainPageKey","#course_condition");
        mav.setViewName("eduInstitution/eduInstitutionMain");

        return mav;
    }

    /**
     * 机构下订单的界面
     * @return
     */
    @RequestMapping("/makeOrderPage")
    public ModelAndView makeOrderPage(HttpSession session){
        ModelAndView mav=new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution!=null){
            eduInstitution = eduInstitutionService.getEduInstitutionById(eduInstitution.getId());
            Set<EduInsPlan> courseList = eduInstitution.getEduInsPlans();

            mav.addObject("courseList",courseList);
            mav.addObject("mainPage","eduInstitution/eduInsMakeOrderPage");
            mav.addObject("mainPageKey","#order_page");
            mav.setViewName("eduInstitution/eduInstitutionMain");
        }else {
            mav.setViewName("forward:/eduInsLoginPage");
        }
        return mav;
    }

//    /**
//     * 下订单
//     * @param order
//     * @return
//     */
//    @RequestMapping("/makeOrder")
//    public ModelAndView makeOrder(Order order,HttpSession session){
//        ModelAndView mav=new ModelAndView();
//        order.setEduInsId("JCC8772");
//        boolean b = orderService.makeOrderBySite(order);
//        String resultMessage = "下订单失败";
//        if (b){
//            resultMessage = "下订单成功";
//        }
//        mav.addObject("successMessage",resultMessage);
//        mav.addObject("backLink","/eduInsView/makeOrderPage");
//        mav.addObject("mainPage","eduInstitution/successPage");
//        mav.addObject("mainPageKey","#success_message");
//        mav.setViewName("eduInstitution/eduInstitutionMain");
//        return mav;
//    }


    /**
     * 返回订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    public ModelAndView getOrderList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduIns = (EduInstitution) session.getAttribute("eduIns");
        if (eduIns!=null){
            List<Order> orderList = orderService.queryOrderByInstitution(eduIns.getEduInsId());
            mav.addObject("orderList",orderList);
            mav.addObject("mainPage","eduInstitution/eduInsOrderList");
            mav.addObject("mainPageKey","#order_list");
            mav.setViewName("eduInstitution/eduInstitutionMain");
        }else {
            mav.setViewName("forward:/eduInsLoginPage");
        }
        return mav;
    }


    /**
     * 获取所有教师列表
     * @param session
     * @return
     */
    @RequestMapping("/getTeacherList")
    public ModelAndView getTeacherList(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution!=null){
            eduInstitution = eduInstitutionService.getEduInstitutionById(eduInstitution.getId());
            List<Teacher> teachers = eduInstitutionService.getTeacherByEduInsId(eduInstitution.getId());
            mav.addObject("teacherList",teachers);
            mav.addObject("mainPage","eduInstitution/eduInsTeacherList");
            mav.addObject("mainPageKey","#teacher_list");
            mav.setViewName("eduInstitution/eduInstitutionMain");
        }else {
            mav.setViewName("forward:/eduInsLoginPage");
        }
        return mav;
    }

    /**
     * 新增老师
     * @param teacher
     * @param session
     * @return
     */
    @RequestMapping("/addTeacher")
    public ModelAndView addTeacher(Teacher teacher,HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution!=null){
            eduInstitution = eduInstitutionService.getEduInstitutionById(eduInstitution.getId());
            teacher.setEduInstitution(eduInstitution);
            eduInstitutionService.addTeacher(teacher);
            mav.setViewName("forward:/eduInsView/getTeacherList");
        }else {
            mav.setViewName("forward:/eduInsLoginPage");
        }
        return mav;
    }


    /**
     *
     * @return
     */
    @RequestMapping("/financialCondition")
    public ModelAndView getFinancialCondition(HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution==null){
            mav.setViewName("forward:/eduInsLoginPage");
            return mav;
        }

        eduInstitution = eduInstitutionService.getEduInstitutionById(eduInstitution.getId());
        List<Order> orders = orderService.queryOrderByInstitution(eduInstitution.getEduInsId());
        List<BankRecord> conditionList = payService.getConditionByEduIns(orders);

        mav.addObject("payRecordList",conditionList);
        mav.addObject("money",payService.calculateEduInsMoney(orders));
        mav.addObject("mainPage","eduInstitution/eduInsFinancialCondition");
        mav.addObject("mainPageKey","#financial_condition");
        mav.setViewName("eduInstitution/eduInstitutionMain");

        return mav;
    }


    @RequestMapping("/getCourseStudent")
    public ModelAndView getCourseStudent(int courseId){
        ModelAndView mav = new ModelAndView();

        EduInsPlan eduInsPlan = eduInstitutionService.getEduInsPlanById(courseId);
        mav.addObject("course",eduInsPlan);
        mav.addObject("mainPage","common/courseStudent");
        mav.addObject("mainPageKey","#course_student");
        mav.setViewName("eduInstitution/eduInstitutionMain");
        return mav;
    }





}
