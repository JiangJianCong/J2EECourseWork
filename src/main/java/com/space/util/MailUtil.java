package com.space.util;

import com.space.entity.User;
import org.hibernate.annotations.Source;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;


    private static final String TITLE_SIGN_UP = "[邮件标题]";

    private static final String CONTENT = "[邮件内容]";

    public void userValidate(User user,String token){


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("910455361@qq.com");
        message.setTo("910455361@qq.com");
        message.setSubject("主题：简单邮件");
        message.setText("测试邮件内容");

        mailSender.send(message);

    }

}
