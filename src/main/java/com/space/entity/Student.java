package com.space.entity;

import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_student")
public class Student {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 200,unique = true)
    private String studentId;

    @Column(length = 200)
    private String studentName;

    @Column(columnDefinition = "TEXT")
    private String studentDesc;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "stu_cou",joinColumns = {@JoinColumn(name = "stu_id")})
    private Set<EduInsPlan> course;


    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "students")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Order> orders;


    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentDesc() {
        return studentDesc;
    }

    public void setStudentDesc(String studentDesc) {
        this.studentDesc = studentDesc;
    }

    public Set<EduInsPlan> getCourse() {
        return course;
    }

    public void setCourse(Set<EduInsPlan> course) {
        this.course = course;
    }


    //    @OneToMany(mappedBy = "t_edu_ins_plan")
//    private Set<EduInsPlan> course;



}
