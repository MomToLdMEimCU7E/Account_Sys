package com.demo.account.Vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentVo {
    private Integer paymentId;
    private Integer uid;
    private Integer bookkeepingId;
    private String accountDetailName;
    private Integer accountDetailId;
    private String amount;
    private Timestamp time;
    private String fundId;
    private String customedFundId;
    private String comment;
    private String enclosure;

    public PaymentVo(Integer paymentId, Integer uid, Integer bookkeepingId, String accountDetailName, Integer accountDetailId, String amount, Timestamp time, String fundId, String customedFundId, String comment, String enclosure) {
        this.paymentId = paymentId;
        this.uid = uid;
        this.bookkeepingId = bookkeepingId;
        this.accountDetailName = accountDetailName;
        this.accountDetailId = accountDetailId;
        this.amount = amount;
        this.time = time;
        this.fundId = fundId;
        this.customedFundId = customedFundId;
        this.comment = comment;
        this.enclosure = enclosure;
    }

    public Integer getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Integer accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBookkeepingId() {
        return bookkeepingId;
    }

    public void setBookkeepingId(Integer bookkeepingId) {
        this.bookkeepingId = bookkeepingId;
    }

    public String getAccountDetailName() {
        return accountDetailName;
    }

    public void setAccountDetailName(String accountDetailName) {
        this.accountDetailName = accountDetailName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getFundId() {
        return fundId;
    }

    public void setFundId(String fundId) {
        this.fundId = fundId;
    }

    public String getCustomedFundId() {
        return customedFundId;
    }

    public void setCustomedFundId(String customedFundId) {
        this.customedFundId = customedFundId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }
}
