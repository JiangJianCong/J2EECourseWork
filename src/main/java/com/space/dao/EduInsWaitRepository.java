package com.space.dao;

import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 教育机构等待审核的repository接口
 */
public interface EduInsWaitRepository extends JpaRepository<EduInsWait,Integer>,JpaSpecificationExecutor<EduInsWait> {

    @Query(value = "select edu_ins_id from t_edu_ins_wait where edu_ins_id = ?1",nativeQuery = true)
    public String findSameId(String eduInsId);

    @Query(value = "select * from t_edu_ins_wait where edu_ins_name = ?1",nativeQuery = true)
    public EduInsWait findByEduInsName(String eduIncName);

    /**
     * 查询是修改机构资料还是注册机构资料
     * @param type
     * @return
     */
    @Query(value = "select * from t_edu_ins_wait where wait_type=?1",nativeQuery = true)
    public List<EduInsWait> findAllByWaitType(int type);

    /**
     * 根据id查询机构修改的信息
     * @param eduInsId
     * @return
     */
    @Query(value = "select * from t_edu_ins_wait where edu_ins_id=?1",nativeQuery = true)
    public EduInsWait findEduInsWaitByEduInsId(String eduInsId);
}
