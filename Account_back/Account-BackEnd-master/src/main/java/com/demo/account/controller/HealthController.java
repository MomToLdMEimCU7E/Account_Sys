package com.demo.account.controller;

import com.demo.account.common.Result;
import com.demo.account.entity.Health;
import com.demo.account.service.HealthService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Resource
    HealthService healthService;

    @PostMapping("/createHealth")
    @ResponseBody
    Result<?> createHealth(Health health){
        return Result.success(healthService.createHealth(health));
    }

    @PutMapping("/updateHealth")
    @ResponseBody
    Result<?> updateHealth(Health health){
        return Result.success(healthService.updateHealth(health));
    }

    @DeleteMapping("/deleteHealth")
    @ResponseBody
    Result<?> deleteHealth(Integer healthId){
        return Result.success(healthService.deleteHealth(healthId));
    }

    @GetMapping("/getHealth")
    @ResponseBody
    Result<?> getHealth(Integer healthId){
        return Result.success(healthService.getHealth(healthId));
    }

    @GetMapping("/getHealthList")
    @ResponseBody
    Result<?> getHealthList(Integer uid){
        return Result.success(healthService.getHealthList(uid));
    }
}
