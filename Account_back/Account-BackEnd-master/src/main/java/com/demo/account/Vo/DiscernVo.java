package com.demo.account.Vo;

import lombok.Data;

@Data
public class DiscernVo {
    private String date;
    private String amount;
    private String fundId;
    private String year;
    private String month;
    private String day;

    public DiscernVo(String date, String amount, String fundId, String year, String month, String day) {
        this.date = date;
        this.amount = amount;
        this.fundId = fundId;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
