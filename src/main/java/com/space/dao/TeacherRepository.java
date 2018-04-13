package com.space.dao;


import com.space.entity.Bank;
import com.space.entity.EduInstitution;
import com.space.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 银行账户的repository的接口
 */
public interface TeacherRepository extends JpaRepository<Teacher,Integer> , JpaSpecificationExecutor<Teacher>{

    @Query(value = "select * from t_teacher where edu_ins_id = ?1",nativeQuery = true)
    public List<Teacher> findAllByEduInstitution(int id);
}
