package com.space.controller;


import com.space.entity.EduInsPlan;
import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import com.space.entity.User;
import com.space.service.EduInstitutionService;
import com.space.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.description;


/**
 * 首页跳转的
 */
@Controller
public class IndexController {

    @Resource
    private EduInstitutionService eduInstitutionService;

    @Resource
    private UserService userService;
    /**
     * 访问网页主页
     * @return
     */
    @RequestMapping(value = {"/index","/",""})
    public ModelAndView root(HttpSession session){
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("user");
        EduInstitution eduInstitution= (EduInstitution) session.getAttribute("eduIns");
        if (user!=null){
            mav.addObject("user",userService.getUserById(user.getId()));
        }else if (eduInstitution!=null){
            eduInstitution = eduInstitutionService.getEduInstitutionById(eduInstitution.getId());
            mav.addObject("eduIns",eduInstitution);
        }



        mav.addObject("courseList",eduInstitutionService.getAllEduInsPlan());
        mav.addObject("mainPage","/common/courseList");
        mav.addObject("mainPageKey","#course_list");
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/getCourse/{id}")
    public ModelAndView getOneCourse(@PathVariable(value = "id") Integer id,HttpSession session){
        ModelAndView mav = new ModelAndView();
        EduInsPlan course = eduInstitutionService.getEduInsPlanById(id);

        User user = (User) session.getAttribute("user");
        EduInstitution eduInstitution= (EduInstitution) session.getAttribute("eduIns");
        if (user!=null){
            mav.addObject("user",userService.getUserById(user.getId()));
        }else if (eduInstitution!=null){
            mav.addObject("eduIns",eduInstitution);
        }

        if (course==null){
            mav.addObject("errorTitle","找不到相关课程");
            mav.addObject("errorDesc","访问错误");

            mav.addObject("mainPage","/common/errorPage");
            mav.addObject("mainPageKey","#error_message");
            mav.setViewName("index");
            return mav;
        }
        mav.addObject("course",course);
        mav.addObject("mainPage","/common/courseDetail");
        mav.addObject("mainPageKey","#course_detail");
        mav.setViewName("index");
        return mav;
    }

    /**
     * 获取所有教育机构的信息
     * @return
     */
    @RequestMapping("/getAllEduInsList")
    public ModelAndView getAllEduInsList(HttpSession session){
        ModelAndView mav = new ModelAndView();


        User user = (User) session.getAttribute("user");
        EduInstitution eduInstitution= (EduInstitution) session.getAttribute("eduIns");
        if (user!=null){
            mav.addObject("user",userService.getUserById(user.getId()));
        }else if (eduInstitution!=null){
            mav.addObject("eduIns",eduInstitution);
        }


        mav.addObject("eduInsList",eduInstitutionService.getAllEduIns());
        mav.addObject("mainPage","/common/eduInsList");
        mav.addObject("mainPageKey","#edu_ins_list");
        mav.setViewName("index");

        return mav;
    }
    @RequestMapping("/login")
    public String login(){
        return "user/userLogin";
    }

    /**
     * 用户登陆
     * @param user
     */
    @RequestMapping("/userLogin")
    public ModelAndView userLogin(User user,HttpSession session){
        ModelAndView mav = new ModelAndView();
        User result = userService.userLogin(user);
        User userSession = (User) session.getAttribute("user");
        if (userSession!=null){
            mav.setViewName("forward:/userView/");
        }else {

            if (null!=result ){
                if (result.isRegister()){
                    session.setAttribute("user",result);
                    mav.setViewName("forward:/userView/");

                }else {
                    mav.addObject("errorMessage","还没验证邮箱");
                    mav.setViewName("user/userLogin");
                }


            }else {
                mav.addObject("errorMessage","用户名或密码错误");
                mav.setViewName("user/userLogin");
            }
        }
        return mav;
    }

    /**
     * 用户注册
     * @param user
     */
    @RequestMapping("/userRegister")
    public ModelAndView userRegister(User user){
        ModelAndView mav = new ModelAndView();
        boolean result = userService.userRegister(user);
        System.out.println(user);
        if (result){

            mav.setViewName("forward:/userView/");
        }else {
            mav.addObject("errorMessage","账户重复");
            mav.setViewName("user/userRegister");
        }

        return mav;
    }

    /**
     * 检查邮件
     * @param token
     * @param userAccount
     * @return
     */
    @RequestMapping("/check_email")
    public ModelAndView userCheckEmail(String token,String userAccount){
        ModelAndView mav = new ModelAndView();

        User user = userService.checkEmail(userAccount,token);
        if (user!=null){
            mav.addObject("user",user);
        }
        mav.setViewName("common/registerSuccess");
        return mav;
    }

    /**
     * 机构登陆
     * @param account
     * @param password
     * @return
     */
    @RequestMapping("/eduInstitutionLogin")
    public ModelAndView eduInsLogin(String account, String password, HttpSession session){
        ModelAndView mav = new ModelAndView();
        System.out.println(account+password);
        EduInstitution result = eduInstitutionService.eduInsLogin(account, password);
        EduInstitution eduInstitution = (EduInstitution) session.getAttribute("eduIns");
        if (eduInstitution!=null){
            mav.setViewName("forward:/eduInsView/");
        }else {
            System.out.println(result);
            if (null != result) {
                session.setAttribute("eduIns", result);
                mav.setViewName("forward:/eduInsView/");
            } else {
                mav.addObject("errorMessage", "用户名或密码错误");
                mav.setViewName("eduInstitution/eduInsLogin");
            }
        }
        return mav;
    }


    @RequestMapping("/eduInsRegister")
    public ModelAndView eduInsRegister(EduInsWait eduInsWait){
        ModelAndView mav = new ModelAndView();
        eduInsWait.setWaitType(1);
        EduInsWait result = eduInstitutionService.EduInsRegister(eduInsWait);
        if (result!=null){
            System.out.println("注册成功");
            mav.addObject("eduIns",result);
            mav.addObject("message","机构编号:" + result.getEduInsId());

            mav.setViewName("eduInstitution/registerSuccess");
        }else {
            mav.addObject("errorMessage","机构重复");
            mav.setViewName("eduInstitution/eduInsRegisterPage");
        }

        return mav;
    }

    /**
     * 管理员登陆
     * @param account
     * @param password
     * @param session
     * @return
     */
    @RequestMapping("/managerLogin")
    public ModelAndView userLogin(String account, String password,HttpSession session){
        ModelAndView mav = new ModelAndView();


        String managerSession = (String) session.getAttribute("manager");
        if (managerSession!=null){
            mav.setViewName("forward:/manager/");
        }else {

            if (account.equals("manager") && password.equals("123")){
                session.setAttribute("manager",account);
                mav.setViewName("forward:/manager/");
            }else {
                mav.addObject("errorMessage","用户名或密码错误");
                mav.setViewName("manager/managerLogin");
            }
        }
        return mav;
    }



    @RequestMapping("/eduInsLoginPage")
    public String eduInsLogin(){
        return "/eduInstitution/eduInsLogin";
    }


    @RequestMapping("/eduInsRegisterPage")
    public String eduInsRegisterPage(){
        return "/eduInstitution/eduInsRegisterPage";
    }



    @RequestMapping("/managerLoginPage")
    public String managerLogin(){
        return "/manager/managerLogin";
    }


    @RequestMapping("/userRegisterPage")
    public String register(){
        return "user/userRegister";
    }




}
