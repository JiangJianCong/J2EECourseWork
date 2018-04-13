package com.space.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_bank_record")
public class BankRecord {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "oid")
    private Order order;

    @Column(length = 200)
    private String fromAccount;

    @Column(length = 200)
    private String toAccount;

    private double amount;

    private boolean finish;

    @Column(length = 100)
    private String type; //1为用户待付款 2为用户线下付款 3发款给机构 4退款

    public double getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
