package com.space.entity;


import javax.persistence.*;

@Entity
@Table(name = "t_eduInsWait")
public class EduInsWait {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 200)
    private String eduInsId;

    @Column(columnDefinition="TEXT")
    private String eduInsDesc;


    @Column(length = 200)
    private String password;

    @Column(length = 300)
    private String location;

    @Column(length = 200)
    private String bankAccount;

    @Column(length = 300)
    private String eduInsName;

    private int waitType;//1为注册 2为修改

    public int getWaitType() {
        return waitType;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setWaitType(int waitType) {
        this.waitType = waitType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEduInsName() {
        return eduInsName;
    }

    public void setEduInsName(String eduInsName) {
        this.eduInsName = eduInsName;
    }

    public String getEduInsId() {
        return eduInsId;
    }

    public void setEduInsId(String eduInsId) {
        this.eduInsId = eduInsId;
    }

    public String getEduInsDesc() {
        return eduInsDesc;
    }

    public void setEduInsDesc(String eduInsDesc) {
        this.eduInsDesc = eduInsDesc;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "EduInsWait{" +
                "id=" + id +
                ", eduInsId='" + eduInsId + '\'' +
                ", eduInsDesc='" + eduInsDesc + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                ", eduInsName='" + eduInsName + '\'' +
                ", waitType=" + waitType +
                '}';
    }
}
