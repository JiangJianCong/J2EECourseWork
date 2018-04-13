package com.space.service;

import com.space.entity.*;

import java.util.List;

/**
 * 教育机构的service
 */
public interface EduInstitutionService {


    /**
     * login
     * @param account
     * @param password
     * @return
     */
    public EduInstitution eduInsLogin(String account,String password);

    /**
     * 教育机构注册申请
     * @param data
     * @return
     */
    public EduInsWait EduInsRegister(EduInsWait data);

    /**
     * 教育机构资料修改申请
     * @param data
     * @return
     */
    public boolean EduInsModify(EduInsWait data);

    /**
     * 教育机构发布教学计划
     * @param plan
     * @return
     */
    public String issuePlan (EduInsPlan plan);

    /**
     * 教育机构修改教学计划
     * @param plan
     * @return
     */
    public boolean modifyPlan(EduInsPlan plan);

    /**
     * 点名状况
     * @param eduInsRecord
     * @return
     */
    public boolean checkIn(EduInsRecord eduInsRecord);

    /**
     * 根据id查询教育机构的资料
     * @param eduInsId
     * @return
     */
    public EduInstitution queryEduInstitutionByEduInsId(String eduInsId);

    /**
     * 获取所有待修改的教育机构
     * @return
     */
    public List<EduInsWait> getAllEduInsWait(int type);

    /**
     * 获取所有教育机构的资料
     * @return
     */
    public List<EduInstitution> getAllEduIns();

    /**
     * 查询等待修改中的资料
     * @param eduInsId
     * @return
     */
    public EduInsWait getEduInsWaitByEduInsId(String eduInsId);


    /**
     * 根据机构Id获取已经发布的课程
     * @param eduInsId
     * @return
     */
    public List<EduInsPlan> getAllEduInsPlanByEduInsId(String eduInsId);


    /**
     * 根据用户机构id获取机构的点到情况
     * @param eduInsId
     * @return
     */
    public List<EduInsRecord> getAllCourseRecordByEduInsId(String eduInsId);


    /**
     * 根据学生id获取机构的点到情况
     * @param studentId
     * @return
     */
    public List<EduInsRecord> getAllCourseRecordByStudentId(String studentId);


    /**
     * 获取所有课程
     * @return
     */
    public List<EduInsPlan> getAllEduInsPlan();

    /**
     * 根据id获取课程基本信息
     * @param id
     * @return
     */
    public EduInsPlan getEduInsPlanById(int id);

    /**
     * 根据id获取教育机构的详细信息
     * @param id
     * @return
     */
    public EduInstitution getEduInstitutionById(int id);

    /**
     * 获取所有老师
     * @return
     */
    public List<Teacher> getAllTeacher();

    /**
     * 根据机构id获取所有老师
     * @param id
     * @return
     */
    public List<Teacher> getTeacherByEduInsId(int id);

    /**
     * 机构新增一个老师
     * @param teacher
     * @return
     */
    public boolean addTeacher(Teacher teacher);

    /**
     * 成绩登记
     * @param stuScore
     * @return
     */
    public String makeGradeRecord(StuScore stuScore);

    /**
     * 根据id查询成绩列表
     * @param id
     * @return
     */
    public List<StuScore> getEduInsGradeListById(int id);

    /**
     * 根据学生的Id查询学生的课程列表
     * @param studentId
     * @return
     */
    public List<StuScore> getStudentGradeByStudentId(String studentId);



}
