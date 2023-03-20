package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Surplus {
    @TableId(type = IdType.AUTO)
    private Integer surplusId;
    private Integer uid;
    private String amount;
    private Timestamp timestamp;

    public Integer getSurplusId() {
        return surplusId;
    }

    public void setSurplusId(Integer surplusId) {
        this.surplusId = surplusId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
