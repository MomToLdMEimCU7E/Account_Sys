package com.demo.account.Vo;

import lombok.Data;

@Data
public class AssetsAndDebtsVo {
    private Integer assets;
    private Integer debts;
    private Integer netAssets;

    public Integer getAssets() {
        return assets;
    }

    public void setAssets(Integer assets) {
        this.assets = assets;
    }

    public Integer getDebts() {
        return debts;
    }

    public void setDebts(Integer debts) {
        this.debts = debts;
    }

    public Integer getNetAssets() {
        return netAssets;
    }

    public void setNetAssets(Integer netAssets) {
        this.netAssets = netAssets;
    }
}
