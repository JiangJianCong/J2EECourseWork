package com.space.course;

import com.space.entity.EduInsPlan;
import com.space.entity.EduInsRecord;
import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import com.space.service.EduInstitutionService;
import com.space.service.ManagerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EduInstitutionServiceTests {

	@Resource
	EduInstitutionService eduInstitutionService;



	@Test
	public void contextLoads() {
	}



	@Test
	public void testEduInsRegister(){
		EduInsWait eduInstitution = new EduInsWait();
		eduInstitution.setPassword("23456");
		eduInstitution.setEduInsName("申请的教育");
		eduInstitution.setEduInsDesc("申请的描述");
		eduInstitution.setLocation("十七");
		eduInstitutionService.EduInsRegister(eduInstitution);
	}

	@Test
	public void testEduInsRegister2(){
		EduInsWait eduInstitution = new EduInsWait();
		eduInstitution.setPassword("11111");
		eduInstitution.setEduInsName("申请的教育3");
		eduInstitution.setEduInsDesc("申请的描述3");
		eduInstitution.setLocation("十七3");
		eduInstitutionService.EduInsRegister(eduInstitution);
	}


	@Test
	public void testIssuePlan(){
		EduInsPlan eduInsPlan = new EduInsPlan();
		eduInsPlan.setCourseDesc("测试课程");
//		eduInsPlan.setEduInsId("JCC8772");
		eduInsPlan.setMaxPeople(20);
		eduInsPlan.setPrice(200);
		eduInsPlan.setStartDate("2018-09-09");
//		eduInsPlan.setTeacherId("abc");
		eduInsPlan.setWeeks(5);
		eduInsPlan.setWeekTimes(3);
		eduInstitutionService.issuePlan(eduInsPlan);
	}

	@Test
	public void testIssuePlan2(){
		EduInsPlan eduInsPlan = new EduInsPlan();
		eduInsPlan.setCourseId("JM86855");
		eduInsPlan.setCourseDesc("测试课程");
//		eduInsPlan.setEduInsId("JCC8772");
		eduInsPlan.setMaxPeople(20);
		eduInsPlan.setPrice(200);
		eduInsPlan.setStartDate("2018-09-09");
//		eduInsPlan.setTeacherId("abc");
		eduInsPlan.setWeeks(5);
		eduInsPlan.setWeekTimes(3);
		eduInstitutionService.issuePlan(eduInsPlan);
	}

	@Test
	public void testModifyPlan(){
		EduInsPlan eduInsPlan = new EduInsPlan();
		eduInsPlan.setId(1);
		eduInsPlan.setCourseId("JM86855");
		eduInsPlan.setCourseDesc("测试修改课程");
//		eduInsPlan.setEduInsId("JCC8772");
		eduInsPlan.setMaxPeople(3);
		eduInsPlan.setPrice(200);
		eduInsPlan.setStartDate("2018-09-09");
//		eduInsPlan.setTeacherId("abc");
		eduInsPlan.setWeeks(5);
		eduInsPlan.setWeekTimes(3);
		eduInstitutionService.issuePlan(eduInsPlan);
	}


	/**
	 * 学生上课登记的情况
	 */
	@Test
	public void testCheckIn(){
		EduInsRecord eduInsRecord = new EduInsRecord();
//		eduInsRecord.setCourseId("KM57741");
		eduInsRecord.setDate("2018-02-25");
		eduInsRecord.setEduInsId("JCC8772");
		eduInsRecord.setStudentId("STU001");
		eduInstitutionService.checkIn(eduInsRecord);


	}

	@Test
	public void testaa(){
		EduInstitution eduIns = eduInstitutionService.getEduInstitutionById(7);
		Set<EduInsPlan> eduInsPlans = eduIns.getEduInsPlans();

		System.out.println(eduInsPlans);
	}
}
