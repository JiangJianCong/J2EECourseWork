package com.space.dao;


import com.space.entity.EduInsPlan;
import com.space.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 学生jpa
 */
public interface StudentRepository extends JpaRepository<Student,Integer> , JpaSpecificationExecutor<Student>{

    /**
     * 根据学生学号寻找
     * @param studentId
     * @return
     */
    Student findByStudentId(String studentId);

    /**
     * 根据Studentid寻找Studentid
     * @param studentId
     * @return
     */
    @Query(value = "select student_id from t_student where student_id=?1",nativeQuery = true)
    String findStudentIdByStudentId(String studentId);

    /**
     * 根据id查找
     * @param id
     * @return
     */
    Student findById (int id);
}
