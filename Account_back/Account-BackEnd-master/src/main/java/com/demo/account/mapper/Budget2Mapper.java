package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.Budget2;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface Budget2Mapper extends BaseMapper<Budget2> {
    @Select("select budget from budget2 where bookkeeping_id = #{bookkeepingId}")
    String getBudget(Integer bookkeepingId);

    @Update("update budget2 set budget = #{budget} where bookkeeping_id = #{id}")
    void updateBook(String budget, Integer id);

    @Insert("insert into budget2 set bookkeeping_id = #{id}, set budget = #{budget}")
    void createBudget(Integer id, String budget);
}
