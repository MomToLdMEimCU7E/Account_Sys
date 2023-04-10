package com.demo.account.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Budget2 {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer bookkeepingId;
    private String budget;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookkeepingId() {
        return bookkeepingId;
    }

    public void setBookkeepingId(Integer bookkeepingId) {
        this.bookkeepingId = bookkeepingId;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }
}
