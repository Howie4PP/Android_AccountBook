package com.example.shenhaichen.capstone_project_accountbook.bean;

/**
 * Created by shenhaichen on 22/12/2017.
 */

public class DetailAccount {
    private String id;
    private int image;
    private String category;
    private String payment;
    private String amount;
    private String comment;
    private String style;
    private String date;
    private String currency;

    public DetailAccount(String id, int image, String category, String payment,
                         String amount, String comment, String style, String date, String currency) {
        this.id = id;
        this.image = image;
        this.category = category;
        this.payment = payment;
        this.amount = amount;
        this.comment = comment;
        this.style = style;
        this.date = date;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
