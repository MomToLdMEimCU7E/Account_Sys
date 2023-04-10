package com.demo.account.Po;

import lombok.Data;

@Data
public class UpdateBookPo {
    Integer BookkeepingId;
    String BookkeepingName;
    String BookkeepingCover;
    String budget;

    public Integer getBookkeepingId() {
        return BookkeepingId;
    }

    public void setBookkeepingId(Integer bookkeepingId) {
        BookkeepingId = bookkeepingId;
    }

    public String getBookkeepingName() {
        return BookkeepingName;
    }

    public void setBookkeepingName(String bookkeepingName) {
        BookkeepingName = bookkeepingName;
    }

    public String getBookkeepingCover() {
        return BookkeepingCover;
    }

    public void setBookkeepingCover(String bookkeepingCover) {
        BookkeepingCover = bookkeepingCover;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
