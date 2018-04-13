package com.space.entity;


import org.aspectj.weaver.ast.Or;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_eduIns")
public class EduInstitution {

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

    @Column(length = 300)
    private String eduInsName;

    @Column(length = 200)
    private String bankAccount;



    @OneToMany(mappedBy = "eduInstitution", fetch = FetchType.EAGER)
    private Set<EduInsPlan> eduInsPlans;

    public Set<EduInsPlan> getEduInsPlans() {
        return eduInsPlans;
    }

    public void setEduInsPlans(Set<EduInsPlan> eduInsPlans) {
        this.eduInsPlans = eduInsPlans;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
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
        return "EduInstitution{" +
                "id=" + id +
                ", eduInsId='" + eduInsId + '\'' +
                ", eduInsDesc='" + eduInsDesc + '\'' +
                ", password='" + password + '\'' +
                ", location='" + location + '\'' +
                ", eduInsName='" + eduInsName + '\'' +
                ", bankAccount='" + bankAccount + '\'' +
                '}';
    }
}

