package com.space.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_score")
public class StuScore {

    @Id
    @GeneratedValue
    private int id;

    @Column(length =  200)
    private String courseId;

    @Column(length = 200)
    private String studentId;

    private int score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
