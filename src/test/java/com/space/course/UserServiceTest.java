package com.space.course;

import com.space.entity.Student;
import com.space.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void contextLoads() {
//        userService.addStudent()

        Set<Student> studentSet = new HashSet<>();
        Student student = new Student();
        student.setStudentId("9911");
        student.setStudentName("健聪");

        Student student1 = new Student();
        student1.setStudentId("9915");
        student1.setStudentName("蒋健");

        Student student2 = new Student();
        student2.setStudentId("9916");
        student2.setStudentName("蒋聪");

        studentSet.add(student);
        studentSet.add(student1);
        studentSet.add(student2);

        Set<Student> students = userService.addStudent(studentSet);
        System.out.println(students);


    }
}
