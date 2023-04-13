package com.demo.account.controller;

import com.demo.account.common.Result;
import com.demo.account.entity.HealthInfo;
import com.demo.account.service.HealthService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/health")
public class HealthController {
    @Resource
    HealthService healthService;

    @PostMapping("/createHealth")
    @ResponseBody
    Result<?> createHealth(@RequestBody HealthInfo healthInfo){
        return Result.success(healthService.createHealth(healthInfo));
    }

    @PutMapping("/updateHealth")
    @ResponseBody
    Result<?> updateHealth(@RequestBody HealthInfo healthInfo){
        return Result.success(healthService.updateHealth(healthInfo));
    }

    @DeleteMapping("/deleteHealth")
    @ResponseBody
    Result<?> deleteHealth(@RequestParam Integer healthId){
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
