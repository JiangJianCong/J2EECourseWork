package com.space.service.impl;

import com.space.dao.UserRepository;
import com.space.entity.User;
import com.space.service.AccountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户账号管理的接口的实现类
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {


    @Resource
    private UserRepository userRepository;

    /**
     * 根据账号密码寻找此账号
     * @param account
     * @param password
     * @return
     */
    public User findByAccAndPsd(String account, String password) {
        User result = userRepository.getUserByActAndPsd(account,password);
        if (result==null){
            return null;
        }else{
            return result;
        }
    }

    @Override
    /**
     * 获取所有用户的信息
     */
    public List<User> getAllUser() {
        ArrayList<User> result = (ArrayList<User>) userRepository.findAll();
        //System.out.println(result.size());
        return result;
    }

    @Override
    /**
     * 注册账号
     */
    public boolean register(String account,String password) {
        String ac = userRepository.getAccount(account);
        System.out.println(ac);
        if (ac==null){
            //写入数据库
            User user = new User();
            user.setAccount(account);
            user.setPassword(password);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
