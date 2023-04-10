package com.demo.account.Po;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class InPo {
    Integer uid;
    String bookKeepingName;
    Integer accountId;
    String amount;
    String time;
    String fundId;
    String customedFundId;
    String comment;
    String enclosure;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getBookKeepingName() {
        return bookKeepingName;
    }

    public void setBookKeepingName(String bookKeepingName) {
        this.bookKeepingName = bookKeepingName;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
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
