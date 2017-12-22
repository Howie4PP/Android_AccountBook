package com.example.shenhaichen.capstone_project_accountbook.bean;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class AccountBookInfo {
    private int amount;
    private String category;
    private int payment;
    private String comment;
    private int style;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
