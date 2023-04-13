package com.demo.account.Vo;

import lombok.Data;

@Data
public class GetPaymentRankVo {
    private String sum;
    private String fundName;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }
}
