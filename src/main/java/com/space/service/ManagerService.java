package com.space.service;

import com.space.entity.EduInstitution;

/**
 * 经理功能的接口
 */
public interface ManagerService {

    /**
     * 根据用户的userId修改是否会员权限
     * @param userId
     * @return
     */
    public boolean cancelMember(int userId);

    /**
     * 审批机构注册信息
     * @param id
     * @return
     */
    public boolean approveEduInsRegister(int id);

    /**
     * 审批修改信息
     * @param id
     * @return
     */
    public boolean approveEduInsModify(int id);
}
