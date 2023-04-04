package com.demo.account.Vo;

import lombok.Data;

@Data
public class BalanceVo {
    public Integer accountDetailId;
    public String accountDetailName;
    public String balance;
    public String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(Integer accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public String getAccountDetailName() {
        return accountDetailName;
    }

    public void setAccountDetailName(String accountDetailName) {
        this.accountDetailName = accountDetailName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
