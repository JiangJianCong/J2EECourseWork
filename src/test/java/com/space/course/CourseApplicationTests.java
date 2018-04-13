package com.space.course;

import com.space.entity.EduInstitution;
import com.space.service.ManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseApplicationTests {
	@Resource
	ManagerService managerService;



	@Test
	public void contextLoads() {
	}

	@Test
	public void testApproveRegister(){

		EduInstitution eduInstitution = new EduInstitution();
		eduInstitution.setEduInsId("VLZ5197");
		eduInstitution.setPassword("23456");
		eduInstitution.setEduInsName("申请的教育");
		eduInstitution.setEduInsDesc("申请的描述");
		eduInstitution.setLocation("十七");
		eduInstitution.setId(8);
		managerService.approveEduInsRegister(8);
		//System.out.println("123");
	}

	@Test
	public void testApproveModify(){
		EduInstitution eduInstitution = new EduInstitution();
		eduInstitution.setId(7);
		eduInstitution.setPassword("11111");
		eduInstitution.setEduInsName("申请的教育3");
		eduInstitution.setEduInsDesc("申请的描述3");
		eduInstitution.setLocation("十七3");
		eduInstitution.setEduInsId("JCC8772");
//		managerService.approveEduInsModify(eduInstitution);

	}




}
