package com.demo.account.Vo;

import lombok.Data;

@Data
public class GetMonthPaymentVo {
    String amount;
    String month;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
