package com.space.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_teacher")
public class Teacher {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 200)
    private String teacherName;

    @Column(columnDefinition = "TEXT")
    private String teacherDesc;

    @ManyToOne
    @JoinColumn(name = "edu_ins_id")
    private EduInstitution eduInstitution;

//    @OneToMany(mappedBy = "t_edu_ins_plan")
//    private Set<EduInsPlan> course;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public EduInstitution getEduInstitution() {
        return eduInstitution;
    }

    public void setEduInstitution(EduInstitution eduInstitution) {
        this.eduInstitution = eduInstitution;
    }
}
