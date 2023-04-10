package com.demo.account.Vo;

import lombok.Data;

@Data
public class BookListVo {
    Integer bookkeepingId;
    Integer bookkeepingTypeId;
    String bookkeepingName;
    String bookkeepingCover;


    public Integer getBookkeepingTypeId() {
        return bookkeepingTypeId;
    }

    public void setBookkeepingTypeId(Integer bookkeepingTypeId) {
        this.bookkeepingTypeId = bookkeepingTypeId;
    }

    public Integer getBookkeepingId() {
        return bookkeepingId;
    }

    public void setBookkeepingId(Integer bookkeepingId) {
        this.bookkeepingId = bookkeepingId;
    }

    public String getBookkeepingName() {
        return bookkeepingName;
    }

    public void setBookkeepingName(String bookkeepingName) {
        this.bookkeepingName = bookkeepingName;
    }

    public String getBookkeepingCover() {
        return bookkeepingCover;
    }

    public void setBookkeepingCover(String bookkeepingCover) {
        this.bookkeepingCover = bookkeepingCover;
    }
}
