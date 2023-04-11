package com.demo.account.Vo;

import lombok.Data;

import java.util.Date;

@Data
public class MonthAmountVo {
    private String sum;
    private String date;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
