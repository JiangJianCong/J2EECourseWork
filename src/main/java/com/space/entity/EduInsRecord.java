package com.space.entity;

import javax.persistence.*;

/**
 * 上课记录登记
 */
@Entity
@Table(name = "t_eduInsRecord")
public class EduInsRecord {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 300)
    private String date;

    @Column(length = 200)
    private String studentId;

    @ManyToOne
    @JoinColumn(name ="course_id")
    private EduInsPlan eduInsPlan;


    @Column(length = 200)
    private String eduInsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEduInsId() {
        return eduInsId;
    }

    public void setEduInsId(String eduInsId) {
        this.eduInsId = eduInsId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public EduInsPlan getEduInsPlan() {
        return eduInsPlan;
    }

    public void setEduInsPlan(EduInsPlan eduInsPlan) {
        this.eduInsPlan = eduInsPlan;
    }
}
