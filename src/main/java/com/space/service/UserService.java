package com.space.service;

import com.space.entity.Student;
import com.space.entity.User;

import java.util.List;
import java.util.Set;

/**
 * 学院的服务器类
 */
public interface UserService {

    /**
     * 用户注册功能
     * @param user
     * @return
     */
    public boolean userRegister(User user);

    /**
     * 用户登陆功能
     * @param user
     * @return
     */
    public User userLogin(User user);

    /**
     * 用户修改个人资料
     * @param user
     * @return
     */
    public boolean userModifyData(User user);

    /**
     * 根据用户名字查询用户相关信息
     * @param name
     * @return
     */
    public User queryUserDataByName(String name);

    /**
     * 获取所有用户的列表
     * @return
     */
    public List<User> getAllUser();

    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    public User getUserById(int id);

    /**
     * 新增学生
     * @param students
     * @return
     */
    public Set<Student> addStudent(Set<Student> students);

    /**
     * 寄邮件
     * @param user
     * @return
     */
    public boolean sendMail(User user,String token);

    /**
     * 邮箱验证
     * @param userAccount
     * @param token
     * @return
     */
    public User checkEmail(String userAccount,String token);

    /**
     * 根据学号找id
     * @param id
     * @return
     */
    public Student getStudentById(String id);

    /**
     * 获取所有学生
     * @return
     */
    public List<Student> getAllStudent();
}
