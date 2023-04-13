package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.Health;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface HealthMapper extends BaseMapper<Health> {
    @Select("select * from health where health_id = #{id}")
    Health getHealth(Integer id);

    @Select("select * from health where uid = #{id}")
    List<Health> getHealthList(Integer id);

    @Insert("insert into health (uid, detail, check_category, person, time, enclosure) values (#{uid}, #{detail}, #{check}, #{person}, #{time}, #{enclosure})")
    Integer insertHealth(Integer uid, String detail, String check, String person, Timestamp time, String enclosure);
}
