package com.space.util;

import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;

/**
 * EduInsWait转成EduInsInstitution
 */
public class EduInsTransform {

    /**
     * EduInsWait类转换成EduInstitution类
     * @param eduInsWait
     * @return
     */
    public static EduInstitution toEduInstitution(EduInsWait eduInsWait){
        EduInstitution result = new EduInstitution();
        result.setEduInsId(eduInsWait.getEduInsId());
        result.setLocation(eduInsWait.getLocation());
        result.setPassword(eduInsWait.getPassword());
        result.setEduInsDesc(eduInsWait.getEduInsDesc());
        result.setEduInsName(eduInsWait.getEduInsName());
        result.setBankAccount(eduInsWait.getBankAccount());
        return result;


    }
}
