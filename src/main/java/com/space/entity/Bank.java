package com.space.entity;

import javax.persistence.*;

@Entity
@Table(name = "t_bank")
public class Bank {

    @Id
    @GeneratedValue
    private int id;

    @Column(length = 200)
    private String account;

    @Column(length = 200)
    private String password;

    private double balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
