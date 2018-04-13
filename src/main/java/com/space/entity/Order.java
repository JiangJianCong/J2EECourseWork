package com.space.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_order")
public class Order {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 200)
    private String orderId;


    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;



    @Column(length = 200)
    private String eduInsId;


    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Set<BankRecord> bankRecord;



    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Column(length = 200)
    private String orderStatus;  //0为未付款 1为已付款 2为超时订单 3为申请退款状态 4为退款完成

    private double consume;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private EduInsPlan eduInsPlan;


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinTable(name = "order_stu",joinColumns = {@JoinColumn(name = "order_id")})
    private Set<Student> students;


    public Set<BankRecord> getBankRecord() {
        return bankRecord;
    }

    public void setBankRecord(Set<BankRecord> bankRecord) {
        this.bankRecord = bankRecord;
    }

    @JsonSerialize(using=CustomDateTimeSerializer.class)
    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getEduInsId() {
        return eduInsId;
    }

    public void setEduInsId(String eduInsId) {
        this.eduInsId = eduInsId;
    }



    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getConsume() {
        return consume;
    }

    public void setConsume(double consume) {
        this.consume = consume;
    }


    public EduInsPlan getEduInsPlan() {
        return eduInsPlan;
    }

    public void setEduInsPlan(EduInsPlan eduInsPlan) {
        this.eduInsPlan = eduInsPlan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}
