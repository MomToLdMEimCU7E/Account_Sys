package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class AccountDetailsType {
    @TableId(type = IdType.AUTO)
    private Integer accountDetailTypeId;
    private String accountDetailTypeName;

    public Integer getAccountDetailTypeId() {
        return accountDetailTypeId;
    }

    public void setAccountDetailTypeId(Integer accountDetailTypeId) {
        this.accountDetailTypeId = accountDetailTypeId;
    }

    public String getAccountDetailTypeName() {
        return accountDetailTypeName;
    }

    public void setAccountDetailTypeName(String accountDetailTypeName) {
        this.accountDetailTypeName = accountDetailTypeName;
    }
}
