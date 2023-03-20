package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class AccountType {
    @TableId(type = IdType.AUTO)
    private Integer accountTypeId;
    private String accountTypeName;
    private String accountDetailsId;

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getAccountDetailsId() {
        return accountDetailsId;
    }

    public void setAccountDetailsId(String accountDetailsId) {
        this.accountDetailsId = accountDetailsId;
    }
}
