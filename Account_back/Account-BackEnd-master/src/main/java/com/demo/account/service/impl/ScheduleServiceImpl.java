package com.demo.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.account.common.Result;
import com.demo.account.entity.Schedule;
import com.demo.account.mapper.ScheduleMapper;
import com.demo.account.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Result<?> GetSchedules(Integer uid) {
        return Result.success(scheduleMapper.getScheduleList(uid));

    }

    @Override
    public Result<?> GetDaySchedules(Integer uid, String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<Schedule> scheduleList;
        scheduleList = scheduleMapper.getScheduleList(uid);

        List<Schedule> dayScheduleList = new ArrayList<>();

        scheduleList.forEach(schedule -> {
            if (sdf.format(schedule.getDate()).compareTo(date) == 0){
                dayScheduleList.add(schedule);
            }
        });

        return Result.success(dayScheduleList);
    }

    @Override
    public Result<?> CheckTime(Integer uid, String date) {
        List<Schedule> scheduleList;
        scheduleList = scheduleMapper.getScheduleList(uid);
        return null;

    }
}
