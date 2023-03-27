package com.demo.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.account.common.Result;
import com.demo.account.entity.Schedule;
import com.demo.account.mapper.ScheduleMapper;
import com.demo.account.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    ScheduleMapper scheduleMapper;

    @Override
    public Result<?> AddSchedule(Schedule schedule) {
        scheduleMapper.insert(schedule);
        return Result.success();
    }

    @Override
    public Result<?> UpdateSchedule(Schedule schedule, Integer scheduleId) {
        scheduleMapper.update(schedule, Wrappers.<Schedule>lambdaQuery().eq(Schedule::getScheduleId, scheduleId));
        return Result.success();
    }

    @Override
    public Result<?> DeleteSchedule(Integer scheduleId) {
        scheduleMapper.deleteById(scheduleId);
        return Result.success();

    }
}
