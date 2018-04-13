package com.space.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="t_user")
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(length = 200)
    private String account;

    @Column(length = 200)
    private String password;

    @Column(length = 200)
    private String email;

    @Column(length = 200)
    private String userId;//用户编号，自动生成

    @Column(length = 300)
    private String userName;

    @Column(length = 200)
    private String sex;

    @Column(columnDefinition = "TEXT")
    private String userDesc;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    private boolean memberStatus = true;

    private int point;

    private double consumeRecord;

    private boolean register = false;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserDesc() {
        return userDesc;
    }

    public void setUserDesc(String userDesc) {
        this.userDesc = userDesc;
    }

    public boolean isMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(boolean memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getConsumeRecord() {
        return consumeRecord;
    }

    public void setConsumeRecord(double consumeRecord) {
        this.consumeRecord = consumeRecord;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", sex='" + sex + '\'' +
                ", userDesc='" + userDesc + '\'' +
                ", orders=" + orders +
                ", memberStatus=" + memberStatus +
                ", point=" + point +
                ", consumeRecord=" + consumeRecord +
                ", register=" + register +
                ", token='" + token + '\'' +
                '}';
    }
}
