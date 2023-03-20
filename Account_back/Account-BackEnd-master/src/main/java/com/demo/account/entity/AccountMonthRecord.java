package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class AccountMonthRecord {
    @TableId(type = IdType.AUTO)
    private Integer accountMonthId;
    private Integer accountId;
    private Integer uid;
    private String month;
    private String netAssets;
    private String assets;
    private String debt;

    public String getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(String netAssets) {
        this.netAssets = netAssets;
    }

    public String getAssets() {
        return assets;
    }

    public void setAssets(String assets) {
        this.assets = assets;
    }

    public String getDebt() {
        return debt;
    }

    public void setDebt(String debt) {
        this.debt = debt;
    }

    public Integer getAccountMonthId() {
        return accountMonthId;
    }

    public void setAccountMonthId(Integer accountMonthId) {
        this.accountMonthId = accountMonthId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


}
