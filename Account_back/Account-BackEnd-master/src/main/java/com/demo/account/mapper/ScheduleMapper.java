package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.Schedule;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ScheduleMapper extends BaseMapper<Schedule> {
    @Select("select * from schedule where uid = #{uid}")
    List<Schedule> getScheduleList(Integer uid);
}
