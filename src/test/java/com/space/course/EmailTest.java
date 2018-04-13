package com.space.course;

import com.space.entity.User;
import com.space.service.UserService;
import com.space.util.MailUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {

    @Resource
    private UserService userService;

    @Test
    public void contextLoads(){

    }

    @Test
    public void testSendEmail(){

        User user = new User();
        user.setUserName("space");
        user.setEmail("910455361@qq.com");
        userService.sendMail(user,"123");
    }
}
