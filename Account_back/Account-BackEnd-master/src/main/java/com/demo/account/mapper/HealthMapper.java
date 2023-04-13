package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.HealthInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface HealthMapper extends BaseMapper<HealthInfo> {
    @Select("select * from healthinfo where health_id = #{id}")
    HealthInfo getHealth(Integer id);

    @Select("select * from healthinfo where uid = #{id}")
    List<HealthInfo> getHealthList(Integer id);

    @Insert("insert into healthinfo (uid, health_detail, check_category, test_person, test_time, enclosure) values (#{uid}, #{detail}, #{check}, #{person}, #{time}, #{enclosure})")
    Integer insertHealth(Integer uid, String detail, String check, String person, String time, String enclosure);
}
