package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.sql.Timestamp;

public class Transfer {
    @TableId(type = IdType.AUTO)
    private Integer transferId;
    private Integer uid;
    private Integer sourceAccountDetailId;
    private Integer destinationAccountDetailId;
    private String amount;
    private Timestamp time;

    public Integer getTransferId() {
        return transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSourceAccountDetailId() {
        return sourceAccountDetailId;
    }

    public void setSourceAccountDetailId(Integer sourceAccountDetailId) {
        this.sourceAccountDetailId = sourceAccountDetailId;
    }

    public Integer getDestinationAccountDetailId() {
        return destinationAccountDetailId;
    }

    public void setDestinationAccountDetailId(Integer destinationAccountDetailId) {
        this.destinationAccountDetailId = destinationAccountDetailId;
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
}
