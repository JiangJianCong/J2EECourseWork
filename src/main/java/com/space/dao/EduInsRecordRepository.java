package com.space.dao;

import com.space.entity.EduInsRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 上课登记的记录的repository接口
 */
public interface EduInsRecordRepository extends JpaRepository<EduInsRecord,Integer>,JpaSpecificationExecutor<EduInsRecord> {


    /**
     * 根据机构Id查询机构的点到情况
     * @param eduInsId
     * @return
     */
    @Query(value = "select * from t_edu_ins_record where edu_ins_id = ?1",nativeQuery = true)
    public List<EduInsRecord> findAllByEduInsId(String eduInsId);


    /**
     * 根据学生的Id查询点到情况
     * @param studentId
     * @return
     */
    @Query(value = "select * from t_edu_ins_record where student_id=?1",nativeQuery = true)
    public List<EduInsRecord> findAllByStudentId(String studentId);
}
