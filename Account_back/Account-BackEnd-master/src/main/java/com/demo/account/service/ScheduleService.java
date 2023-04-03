package com.demo.account.service;

import com.demo.account.common.Result;
import com.demo.account.entity.Schedule;

import java.util.Date;

public interface ScheduleService {
    Result<?> AddSchedule(Schedule schedule);
    Result<?> UpdateSchedule(Schedule schedule, Integer scheduleId);
    Result<?> DeleteSchedule(Integer scheduleId);
    Result<?> GetSchedules(Integer uid);
    Result<?> GetDaySchedules(Integer uid, String date);
    Result<?> CheckTime(Integer uid, String date);
}
