package com.space.course;

import com.space.dao.EduInsPlanRepository;
import com.space.dao.EduInstitutionRepository;
import com.space.dao.StudentRepository;
import com.space.entity.EduInsPlan;
import com.space.entity.EduInstitution;
import com.space.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EduInsCourseTest {


    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    @Resource
    private EduInstitutionRepository eduInstitutionRepository;


    @Test
    public void contextLoads() {

        EduInstitution eduInstitution1 = new EduInstitution();
        eduInstitution1.setEduInsId("ABCDEF");
        eduInstitution1.setEduInsName("ABCDEF");
        eduInstitution1.setId(13);


        EduInsPlan course5 = new EduInsPlan();
        course5.setCourseId("course5");
        course5.setCourseName("courseName5");
        course5.setEduInstitution(eduInstitution1);

        EduInsPlan course6 = new EduInsPlan();
        course6.setCourseId("course6");
        course6.setCourseName("courseName6");
        course6.setEduInstitution(eduInstitution1);

        EduInsPlan course7 = new EduInsPlan();
        course7.setCourseId("course7");
        course7.setCourseName("courseName7");
        course7.setEduInstitution(eduInstitution1);

        eduInsPlanRepository.save(course5);
        eduInsPlanRepository.save(course6);
        eduInsPlanRepository.save(course7);







    }

    @Test
    public void Test() throws Exception{

//        Student student = studentRepository.findByStudentId("student1");
//        System.out.println(student.getStudentId()+student.getStudentName());

//        EduInsPlan eduInsPlan = eduInsPlanRepository.findByCourseId("course5");
//        System.out.println(eduInsPlan);

        EduInstitution eduInstitution = eduInstitutionRepository.findByEduInsId("ABCDEF");
        System.out.println(eduInstitution);
    }


    @Test
    public void getAllCourseTest(){
        List<EduInsPlan> all = eduInsPlanRepository.findAll();
        System.out.println(all);
    }


}
