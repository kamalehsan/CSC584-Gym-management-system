package com.gsm.model;

import java.sql.Timestamp;

public class Renew {
    private int id;
    private int memberId;
    private double totalAmount;
    private Timestamp renewDate;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(Timestamp renewDate) {
        this.renewDate = renewDate;
    }
}
