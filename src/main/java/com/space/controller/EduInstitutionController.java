package com.space.controller;


import com.space.entity.EduInsPlan;
import com.space.entity.EduInsRecord;
import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import com.space.service.EduInstitutionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 教育机构的控制器
 */
@RestController
@RequestMapping("/eduIns")
public class EduInstitutionController {

    @Resource
    private EduInstitutionService eduInstitutionService;

    /**
     * 教育机构注册
     * @param eduInsWait 等待审核的教育机构
     * @return
     */
    @RequestMapping("/register")
    public EduInsWait eduInsRegister(EduInsWait eduInsWait){
        EduInsWait temp = eduInsWait;
        EduInsWait result = eduInstitutionService.EduInsRegister(temp);
        return result;
    }


    /**
     * 教育机构更新
     */
    @RequestMapping("/update")
    public boolean eduInsUpdate(EduInsWait eduInsWait){
        System.out.println(eduInsWait);
        boolean result = eduInstitutionService.EduInsModify(eduInsWait);
        return result;
    }

    /**
     * 发布计划
     * @param plan
     */
    @RequestMapping("/issuePlan")
    public String issuePlan(EduInsPlan plan){
        String result = eduInstitutionService.issuePlan(plan);
        return result;


    }

    /**
     * 更新发布成功的计划
     * @param plan
     */
    @RequestMapping("/updatePlan")
    public boolean updatePlan(EduInsPlan plan){
        boolean b = eduInstitutionService.modifyPlan(plan);
        return b;
    }

    /**
     * 上课记录登记
     * @param eduInsRecord
     */
    @RequestMapping("/courseCheck")
    public boolean courseRecord(EduInsRecord eduInsRecord){
        boolean result = eduInstitutionService.checkIn(eduInsRecord);
        return result;
    }


//    /**
//     * 根据教育机构的id查询教育机构资料
//     * @param eduInsId
//     */
//    public void queryEduInsById(String eduInsId){
//
//    }
}
