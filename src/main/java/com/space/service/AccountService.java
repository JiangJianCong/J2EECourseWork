package com.space.service;

import com.space.entity.User;

import java.util.List;

/**
 * 账户管理的接口
 */
public interface AccountService {
    /**
     * 根据用户名和密码查找用户
     * @param account
     * @param password
     * @return
     */
    public User findByAccAndPsd(String account, String password);

    /**
     * 获取所有用户的列表
     * @return
     */
    public List<User> getAllUser();

    /**
     * 注册账户
     * @return
     */
    public boolean register(String account,String password);
}
