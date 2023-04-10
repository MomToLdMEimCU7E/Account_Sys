package com.demo.account.Po;

import lombok.Data;

@Data
public class CreateBookPo {
    private Integer uid;
    private Integer bookkeepingTypeId;
    private String bookkeepingCover;
    private String bookkeepingName;
    private String customedFundsId;
    private Integer extraMember1;
    private Integer extraMember2;
    private String budget;

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getBookkeepingTypeId() {
        return bookkeepingTypeId;
    }

    public void setBookkeepingTypeId(Integer bookkeepingTypeId) {
        this.bookkeepingTypeId = bookkeepingTypeId;
    }

    public String getBookkeepingCover() {
        return bookkeepingCover;
    }

    public void setBookkeepingCover(String bookkeepingCover) {
        this.bookkeepingCover = bookkeepingCover;
    }

    public String getBookkeepingName() {
        return bookkeepingName;
    }

    public void setBookkeepingName(String bookkeepingName) {
        this.bookkeepingName = bookkeepingName;
    }

    public String getCustomedFundsId() {
        return customedFundsId;
    }

    public void setCustomedFundsId(String customedFundsId) {
        this.customedFundsId = customedFundsId;
    }

    public Integer getExtraMember1() {
        return extraMember1;
    }

    public void setExtraMember1(Integer extraMember1) {
        this.extraMember1 = extraMember1;
    }

    public Integer getExtraMember2() {
        return extraMember2;
    }

    public void setExtraMember2(Integer extraMember2) {
        this.extraMember2 = extraMember2;
    }
}
