package com.space.dao;

import com.space.entity.StuScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 学生成绩的repostory的接口
 */
public interface StuScoreRepository extends JpaRepository<StuScore,Integer>,JpaSpecificationExecutor<StuScore> {

    StuScore findByStudentIdAndCourseId(String studentId,String courseId);

    /**
     * 根据课程id查询列表
     * @param courseId
     * @return
     */
    List<StuScore> findAllByCourseId(String courseId);

    /**
     * 根据学生id查询
     * @param studentId
     * @return
     */
    List<StuScore> findAllByStudentId(String studentId);
}
