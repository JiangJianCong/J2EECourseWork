package com.space.controller;

import com.space.entity.User;
import com.space.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/accountManager")
public class UserAccountController {

    @Resource
    private AccountService accountService;

    @RequestMapping("/say")
    public String say(){
//        System.out.println(accountService.findByAccAndPsd("abcd","def").getId());
        return accountService.findByAccAndPsd("abc","def").getPassword();
//        return accountService.getAllUser().get(0).getAccount();

    }

    /**
     * 登陆
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request){
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        User user = accountService.findByAccAndPsd(account,password);
        if (user ==null){
            return "无此账号";
        }
        return "账号"+user.getAccount() +";密码:"+user.getPassword();
    }

    /**
     * 注册
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public String register(HttpServletRequest request){
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        if(accountService.register(account,password)){
            return "注册成功";
        }
        return "注册失败";
    }
}
