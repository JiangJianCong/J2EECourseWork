package com.space.service.impl;

import com.space.dao.EduInsWaitRepository;
import com.space.dao.EduInstitutionRepository;
import com.space.dao.UserRepository;
import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import com.space.entity.User;
import com.space.service.ManagerService;
import com.space.util.EduInsTransform;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("managerService")
public class ManagerServiceImpl implements ManagerService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private EduInstitutionRepository eduInstitutionRepository;

    @Resource
    private EduInsWaitRepository eduInsWaitRepository;


    /**
     * 取消会员资格
     * @param userId
     * @return
     */
    @Override
    public boolean cancelMember(int userId) {

        User one = userRepository.findOne(userId);
        one.setMemberStatus(false);
        userRepository.save(one);
        return true;
    }

    /**
     * 批准注册申请
     * @param id
     * @return
     */
    @Override
    public boolean approveEduInsRegister(int id) {
        EduInsWait eduInsWait = eduInsWaitRepository.findOne(id);
        EduInstitution result = eduInstitutionRepository.save(EduInsTransform.toEduInstitution(eduInsWait));
        if (result==null){
            return false;
        }
        eduInsWaitRepository.delete(id);
        return true;

        //先从前端传等待区的id，然后获取等待区的资料，写入审核之后的区域，最后删除等待区的
    }

    /**
     * 批准修改资料的申请
     * @param id
     * @return
     */
    @Override
    public boolean approveEduInsModify(int id) {
        EduInsWait afterModify = eduInsWaitRepository.getOne(id);
        EduInstitution beforeModify = eduInstitutionRepository.findByEduInsId(afterModify.getEduInsId());
        EduInstitution result = EduInsTransform.toEduInstitution(afterModify);
        result.setId(beforeModify.getId());
        result.setPassword(beforeModify.getPassword());
        result = eduInstitutionRepository.save(result);
        if (result==null){
            return false;
        }
        eduInsWaitRepository.delete(id);
        return true;
    }
}
