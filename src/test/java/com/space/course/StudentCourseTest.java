package com.space.course;

import com.space.dao.EduInsPlanRepository;
import com.space.dao.StudentRepository;
import com.space.entity.EduInsPlan;
import com.space.entity.Student;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentCourseTest {

    @Resource
    private StudentRepository studentRepository;

    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    @Test
    public void contextLoads() {
        EduInsPlan course1 = new EduInsPlan();
        course1.setCourseId("course1");
        course1.setCourseName("courseName1");

        EduInsPlan course2 = new EduInsPlan();
        course2.setCourseId("course2");
        course2.setCourseName("courseName2");

        EduInsPlan course3 = new EduInsPlan();
        course3.setCourseId("course3");
        course3.setCourseName("courseName3");

        eduInsPlanRepository.save(course1);
        eduInsPlanRepository.save(course2);
        eduInsPlanRepository.save(course3);

        Student student1 = new Student();
        student1.setStudentId("student1");
        student1.setStudentName("studentName1");

        Student student2 = new Student();
        student2.setStudentId("student2");
        student2.setStudentName("studentName2");

        Student student3 = new Student();
        student3.setStudentId("student3");
        student3.setStudentName("studentName3");

        Set<EduInsPlan> courses = null;
        courses = new HashSet<>();
        courses.add(course1);
        courses.add(course2);
        student1.setCourse(courses);
        studentRepository.save(student1);



        courses = new HashSet<>();
        courses.add(course2);
        courses.add(course3);
        student2.setCourse(courses);
        studentRepository.save(student2);

        studentRepository.save(student3);



    }

    @Test
    public void Test() throws Exception{


        EduInsPlan eduInsPlan = eduInsPlanRepository.findOne(39);

        Student student1 = studentRepository.findOne(25);
        Student student2 = studentRepository.findOne(26);
        Student student3 = studentRepository.findOne(27);
        Student student4 = studentRepository.findOne(28);

        Set<Student> students = new HashSet<>();
        students.add(student1);
        students.add(student2);
        students.add(student3);
        students.add(student4);



        for (Student student:students){
            student.setCourse((Set<EduInsPlan>) eduInsPlan);
        }


        System.out.println(eduInsPlan);

    }


}
