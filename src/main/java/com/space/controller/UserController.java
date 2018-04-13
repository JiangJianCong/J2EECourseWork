package com.space.controller;

import com.space.entity.User;
import com.space.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     *
     * @param user
     * @return
     */
    @RequestMapping("/update")
    public String userUpdateData(User user){

        boolean result = userService.userModifyData(user);
        if (result){
            return "success";
        }
        return "";
    }



}
