package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Income {
    @TableId(type = IdType.AUTO)
    private Integer incomeId;
    private Integer uid;
    private Integer bookkeepingId;
    private Integer accountDetailId;
    private String amount;
    private Timestamp time;
    private String fundId;
    private String customedFundId;
    private String comment;
    private String enclosure;

    public int getIncomeId() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId = incomeId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBookkeepingId() {
        return bookkeepingId;
    }

    public void setBookkeepingId(int bookkeepingId) {
        this.bookkeepingId = bookkeepingId;
    }

    public Integer getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Integer accountDetailId) {
        this.accountDetailId = accountDetailId;
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
