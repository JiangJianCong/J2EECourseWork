package com.space.dao;

import com.space.entity.EduInsPlan;
import com.space.entity.EduInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 机构计划的repository接口
 */
public interface EduInsPlanRepository extends JpaRepository<EduInsPlan,Integer>,JpaSpecificationExecutor<EduInsPlan>{




    /**
     * 查询id
     * @param courseId
     * @return
     */
    @Query(value = "select id from t_edu_ins_plan where course_id=?1",nativeQuery = true)
    public Integer findIdByCourseId(String courseId);



    /**
     * 根据课程编号查询课程
     * @param courseId
     * @return
     */
    @Query(value = "select * from t_edu_ins_plan where course_id=?1",nativeQuery = true)
    public EduInsPlan findAllByCourseId(String courseId);


    /**
     * 根据id查询
     * @param courseId
     * @return
     */
    EduInsPlan findByCourseId(String courseId);

    /**
     * 根据courseId判断是否存在此课程
     * @param courseId
     * @return
     */
    @Query(value = "select course_id from t_edu_ins_plan where course_id=?1",nativeQuery = true)
    String findCourseIdByCourseId(String courseId);


    /**
     * 根据表中edu_ins查询所有课程
     * @param id
     * @return
     */
    @Query(value = "select * from t_edu_ins_plan where edu_ins=?1",nativeQuery = true)
    List<EduInsPlan> findAllByEduInstitutionId(int id);


    @Modifying
    @Transactional
    @Query(value = "update stu_cou set team = ?3 where  stu_id = ?1 AND course_id = ?2",nativeQuery = true)
    void allotTeam(int stuId,int courseId,String team);

    @Query(value = "select id from t_edu_ins_plan",nativeQuery = true)
    List<Integer> getIdList();

    @Query(value = "select team from stu_cou where stu_id=?1 and course_id=?2",nativeQuery = true)
    String teamIsNull (int stuId,int couId);


}
