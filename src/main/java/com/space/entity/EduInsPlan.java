package com.space.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_eduInsPlan")
public class EduInsPlan {

    @Id
    @GeneratedValue
    private int id;

    private int weekTimes;

    private int weeks;

    @Column(length = 300)
    private String courseName;

    private double price;

    @Column(columnDefinition = "TEXT")
    private String courseDesc;

    private int maxPeople;


    @ManyToOne
    @JoinColumn(name = "teacher")
    private Teacher teacher;

    @Column(length = 300)
    private String startDate;

    @Column(length = 300)
    private String courseId;//课程编号



    @ManyToOne
    @JoinColumn(name = "edu_ins")
    private EduInstitution eduInstitution;



    public EduInstitution getEduInstitution() {
        return eduInstitution;
    }

    public void setEduInstitution(EduInstitution eduInstitution) {
        this.eduInstitution = eduInstitution;
    }


    @ManyToMany(fetch = FetchType.EAGER,mappedBy = "course")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> student;


    public Set<Student> getStudent() {
        return student;
    }

    public void setStudent(Set<Student> student) {
        this.student = student;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getWeekTimes() {
        return weekTimes;
    }

    public void setWeekTimes(int weekTimes) {
        this.weekTimes = weekTimes;
    }

    public int getWeeks() {
        return weeks;
    }

    public void setWeeks(int weeks) {
        this.weeks = weeks;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
    }


    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
