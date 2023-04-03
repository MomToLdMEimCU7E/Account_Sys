package com.demo.account.controller;

import com.demo.account.common.Result;
import com.demo.account.entity.Schedule;
import com.demo.account.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    ScheduleService scheduleService;

    @ResponseBody
    @ApiOperation("添加计划")
    @PostMapping("/addSchedule")
    public Result<?> addSchedule (@RequestBody Schedule schedule) {
        scheduleService.AddSchedule(schedule);
        return Result.success();
    }

    @ResponseBody
    @ApiOperation("修改计划")
    @PutMapping("/updateSchedule")
    public Result<?> updateSchedule (@RequestBody Schedule schedule, @RequestParam Integer scheduleId) {
        scheduleService.UpdateSchedule(schedule, scheduleId);
        return Result.success();
    }

    @ResponseBody
    @ApiOperation("删除")
    @DeleteMapping("/deleteSchedule")
    public Result<?> deleteSchedule (@RequestParam Integer scheduleId) {
        scheduleService.DeleteSchedule(scheduleId);
        return Result.success();
    }

    @ResponseBody
    @ApiOperation("获取列表")
    @GetMapping("/getScheduleList")
    public Result<?> getScheduleList (@RequestParam Integer uid) {

        return Result.success(scheduleService.GetSchedules(uid));


    }

    @ResponseBody
    @ApiOperation("根据日期获取列表")
    @GetMapping("/getDayScheduleList")
    public Result<?> getDayScheduleList (@RequestParam Integer uid, @RequestParam String date) {

        return Result.success(scheduleService.GetDaySchedules(uid, date));


    }
}
