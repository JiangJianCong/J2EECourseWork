package com.space.dao;

import com.space.entity.EduInsWait;
import com.space.entity.EduInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 教育机构的repository接口
 */
public interface EduInstitutionRepository extends JpaRepository<EduInstitution,Integer>,JpaSpecificationExecutor<EduInstitution> {

    @Query(value = "select edu_ins_id from t_edu_ins where edu_ins_id = ?1",nativeQuery = true)
    public String findSameId(String eduInsId);

    @Query(value = "select * from t_edu_ins where edu_ins_name = ?1",nativeQuery = true)
    public EduInstitution findByEduInsName(String eduIncName);

    @Query(value = "select * from t_edu_ins where edu_ins_id = ?1",nativeQuery = true)
    public EduInstitution findByEduInsId(String eduInsId);

    @Query(value = "select * from t_edu_ins where edu_ins_id = ?1 and password=?2",nativeQuery = true)
    public EduInstitution findByEduInsIdAndPassword(String eduInsId,String password);
}
