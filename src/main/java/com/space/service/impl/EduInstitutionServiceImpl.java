package com.space.service.impl;

import com.space.dao.*;
import com.space.entity.*;
import com.space.service.EduInstitutionService;
import com.space.util.DateUtil;
import com.space.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 教育机构的实现类
 */
@Service("eduInstitutionService")
public class EduInstitutionServiceImpl implements EduInstitutionService {

    @Resource
    private EduInsWaitRepository eduInsWaitRepository;

    @Resource
    private EduInstitutionRepository eduInstitutionRepository;

    @Resource
    private EduInsPlanRepository eduInsPlanRepository;

    @Resource
    private EduInsRecordRepository eduInsRecordRepository;

    @Resource
    private TeacherRepository teacherRepository;


    @Resource
    private StuScoreRepository stuScoreRepository;



    /**
     *
     * @param account
     * @param password
     * @return
     */
    @Override
    public EduInstitution eduInsLogin(String account, String password) {
        EduInstitution result = eduInstitutionRepository.findByEduInsIdAndPassword(account, password);
        return result;
    }

    /**
     * 教育机构注册
     * @param data
     * @return
     */
    @Override
    public EduInsWait EduInsRegister(EduInsWait data) {
        if(null!=eduInsWaitRepository.findByEduInsName(data.getEduInsName()) || null!=eduInstitutionRepository.findByEduInsName(data.getEduInsName())){
            return null;
        }
        String udid = StringUtil.createUDID();
        while (eduInsWaitRepository.findSameId(udid)!=null || null!=eduInstitutionRepository.findSameId(udid)){
            udid = StringUtil.createUDID();
        }
        data.setEduInsId(udid);
        eduInsWaitRepository.save(data);
        return data;
    }

    /**
     * 教育机构修改资料
     * @param data
     * @return
     */
    @Override
    public boolean EduInsModify(EduInsWait data) {
        EduInsWait result = eduInsWaitRepository.save(data);
        return result !=null;
    }

    /**
     * 发布一个计划
     * @param plan
     * @return
     */
    @Override
    public String issuePlan(EduInsPlan plan) {
        String courseId = StringUtil.createCourseID();
        String eduInsId = plan.getEduInstitution().getEduInsId();

        while(null!=eduInsPlanRepository.findCourseIdByCourseId(courseId)){
            courseId = StringUtil.createCourseID();
        }
        plan.setCourseId(courseId);
        if (eduInsPlanRepository.save(plan)!=null){
            return plan.getCourseId();
        }
        return "发布失败";
    }

    /**
     * 修改一个计划
     * @param plan
     * @return
     */
    public boolean modifyPlan(EduInsPlan plan) {

        int id = eduInsPlanRepository.findIdByCourseId(plan.getCourseId());
        plan.setId(id);
        if (eduInsPlanRepository.save(plan)!=null){
            return true;
        }
        return false;
    }

    /**
     * 上课情况登记
     * @param eduInsRecord
     * @return
     */
    @Override
    public boolean checkIn(EduInsRecord eduInsRecord) {
        String courseId = eduInsRecord.getEduInsPlan().getCourseId();
        EduInsPlan course = eduInsPlanRepository.findAllByCourseId(courseId);
        eduInsRecord.setEduInsPlan(course);
        eduInsRecord.setEduInsId(course.getEduInstitution().getEduInsId());
        if (null!=eduInsRecordRepository.save(eduInsRecord)){
            return true;
        }
        return false;
    }

    /**
     * 根据
     * @param eduInsId
     * @return
     */
    @Override
    public EduInstitution queryEduInstitutionByEduInsId(String eduInsId) {
        EduInstitution result = eduInstitutionRepository.findByEduInsId(eduInsId);
        return result;
    }

    /**
     * 获取所有待审核的资料教育机构
     * @return
     */
    @Override
    public List<EduInsWait> getAllEduInsWait(int type) {
        return eduInsWaitRepository.findAllByWaitType(type);
    }


    /**
     * 获取所有用户列表
     * @return
     */
    @Override
    public List<EduInstitution> getAllEduIns() {
        return eduInstitutionRepository.findAll();
    }

    /**
     * 返回修改中的资料
     * @param eduInsId
     * @return
     */
    @Override
    public EduInsWait getEduInsWaitByEduInsId(String eduInsId) {
        EduInsWait eduInsWait = eduInsWaitRepository.findEduInsWaitByEduInsId(eduInsId);
        if (eduInsWait!=null){
            return eduInsWait;
        }
        return null;
    }

    /**
     * 根据机构的Id查询机构的课程列表
     * @param eduInsId
     * @return
     */
    @Override
    public List<EduInsPlan> getAllEduInsPlanByEduInsId(String eduInsId) {
        EduInstitution eduInstitution = eduInstitutionRepository.findByEduInsId(eduInsId);
        List<EduInsPlan> allCourse = eduInsPlanRepository.findAllByEduInstitutionId(eduInstitution.getId());
        return allCourse;



    }

    /**
     * 根据机构id获取点到列表
     * @param eduInsId
     * @return
     */
    @Override
    public List<EduInsRecord> getAllCourseRecordByEduInsId(String eduInsId) {
        return eduInsRecordRepository.findAllByEduInsId(eduInsId);
    }

    /**
     * 根据学生id查询点到情况
     * @param studentId
     * @return
     */
    @Override
    public List<EduInsRecord> getAllCourseRecordByStudentId(String studentId) {
        return eduInsRecordRepository.findAllByStudentId(studentId);
    }

    /**
     * 获取所有课程
     * @return
     */
    @Override
    public List<EduInsPlan> getAllEduInsPlan() {
        return eduInsPlanRepository.findAll();
    }

    /**
     * 根据id获取课程信息
     * @param id
     * @return
     */
    @Override
    public EduInsPlan getEduInsPlanById(int id) {
        EduInsPlan course = eduInsPlanRepository.findOne(id);
        if (course!=null){
            return course;
        }else {
            return null;
        }
    }

    /**
     * 根据id获取教育机构的详细信息
     * @param id
     * @return
     */
    @Override
    public EduInstitution getEduInstitutionById(int id) {
        return eduInstitutionRepository.findOne(id);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public List<Teacher> getTeacherByEduInsId(int id) {
        return teacherRepository.findAllByEduInstitution(id);
    }

    @Override
    public boolean addTeacher(Teacher teacher) {
        return teacherRepository.save(teacher)!=null;
    }

    @Override
    public String makeGradeRecord(StuScore stuScore) {
        if (stuScoreRepository.findByStudentIdAndCourseId(stuScore.getStudentId(),stuScore.getCourseId())!=null){
            return "已经登记过，请不要重复登记";
        }
        EduInsPlan course = eduInsPlanRepository.findByCourseId(stuScore.getCourseId());
        course.getStartDate();
        try {
            Date startDate = DateUtil.formatString(course.getStartDate(), "yyyy-MM-dd");
            int dayDif = (int) -(DateUtil.calculateDif(startDate)/(60*60*24));//负数的时候就是已经开始上课,正数就是还没上课

            if (dayDif > 0){
                return "课程还没有开始";
            }else {
                if ((-dayDif)>course.getWeeks()){
                    StuScore save = stuScoreRepository.save(stuScore);
                    if (save!=null){
                        return "登记成功";
                    }
                }else {
                    return "课程还没结束，登记失败";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }






        return "登记失败";

    }


    /**
     * 根据id查询机构成绩
     * @param id
     * @return
     */
    @Override
    public List<StuScore> getEduInsGradeListById(int id) {
        EduInstitution eduIns = eduInstitutionRepository.findOne(id);
        Set<EduInsPlan> eduInsPlans = eduIns.getEduInsPlans();
        List<StuScore> list = new ArrayList<>();
        Iterator it =  eduInsPlans.iterator();
        while (it.hasNext()){
            EduInsPlan course = (EduInsPlan) it.next();
            String courseId = course.getCourseId();
            List<StuScore> gradeList = stuScoreRepository.findAllByCourseId(courseId);
            for (StuScore score:gradeList){
                list.add(score);
            }
        }
        return list;
    }

    @Override
    public List<StuScore> getStudentGradeByStudentId(String studentId) {
        return stuScoreRepository.findAllByStudentId(studentId);
    }


//    public static void main(String[] args) {
////        EduInstitutionServiceImpl a = new EduInstitutionServiceImpl();
//        System.out.println(createUDID());
//    }
}
