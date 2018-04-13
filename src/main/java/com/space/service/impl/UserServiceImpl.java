package com.space.service.impl;

import com.space.dao.StudentRepository;
import com.space.dao.UserRepository;
import com.space.entity.Student;
import com.space.entity.User;
import com.space.service.UserService;
import com.space.util.StringUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * 用户服务器的接口的实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private StudentRepository studentRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;



    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public boolean userRegister(User user) {
        String ac = userRepository.getAccount(user.getAccount());
        user.setUserId(user.getAccount());
        if (ac==null){
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            userRepository.save(user);
            this.sendMail(user,token);
            return true;
        }
        return false;
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public User userLogin(User user) {

        User result = userRepository.getUserByActAndPsd(user.getAccount(),user.getPassword());
        if (result==null){
            return null;
        }else{
            return result;
        }
    }

    /**
     * 用户修改信息
     * @param user
     * @return
     */
    @Override
    public boolean userModifyData(User user) {

        User before = userRepository.findOne(user.getId());
        if (before!=null){
            before.setUserName(user.getUserName());
            before.setSex(user.getSex());
            before.setUserDesc(user.getUserDesc());
            User result = userRepository.save(before);
            return result!=null;
        }
        return false;
    }

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    @Override
    public User queryUserDataByName(String name) {
        User result;
        if (StringUtil.isNotEmpty(name)){
            result = userRepository.findUserByUserName(name);
            if (result!=null){
                result.setPassword("");
                return result;
            }

        }
        return null;
    }



    /**
     * 获取所有用户的列表
     * @return
     */
    @Override
    public List<User> getAllUser() {
        List<User> allUser = userRepository.findAll();
        return allUser;
    }

    /**
     * 根据id获取用户资料
     * @param id
     * @return
     */
    @Override
    public User getUserById(int id) {
        return userRepository.findOne(id);
    }

    /**
     * 新增学生
     * @param students
     * @return
     */
    @Override
    public Set<Student> addStudent(Set<Student> students) {
        Set<Student> result = new HashSet<Student>();
        Iterator<Student> it = students.iterator();
//        System.out.println(it);
        while (it.hasNext()){
            Student student = it.next();
            Student save = studentRepository.findByStudentId(student.getStudentId());

            if (save==null){
                result.add(studentRepository.save(student));
            }else {
                save.setCourse(student.getCourse());
                result.add(studentRepository.save(save));
            }
        }
        return result;
    }

    @Override
    public boolean sendMail(User user,String token) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);

        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        try {
            String title = "注册验证";

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "GBK");
            helper.setFrom(from);
            helper.setTo(user.getEmail());
            helper.setSubject(title);
            String link = "localhost:8888/check_email?userAccount="+user.getUserName()+"&token="+user.getToken();
            String content = "<a href='"+link+"'>点击验证邮箱"+link+"</a>";
            String message = String.format(content, user.getAccount(), link, link, user.getEmail());
            helper.setText(message, true);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            System.out.println("失败");
        }
        return false;
    }

    /**
     * 检查注册邮箱的注视
     * @param userAccount
     * @param token
     * @return
     */
    @Override
    public User checkEmail(String userAccount, String token) {

        User byAccountAndToken = userRepository.findByAccountAndToken(userAccount, token);
        if (byAccountAndToken!=null){
            byAccountAndToken.setRegister(true);

        }
        userRepository.save(byAccountAndToken);
        return byAccountAndToken;
    }

    /**
     * 根据学号查询学生
     * @param id
     * @return
     */
    @Override
    public Student getStudentById(String id) {
        return studentRepository.findByStudentId(id);
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }


}
