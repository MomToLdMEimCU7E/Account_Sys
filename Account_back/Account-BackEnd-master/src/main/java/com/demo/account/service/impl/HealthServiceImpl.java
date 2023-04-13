package com.demo.account.service.impl;

import com.demo.account.common.Result;
import com.demo.account.entity.Health;
import com.demo.account.mapper.HealthMapper;
import com.demo.account.service.HealthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HealthServiceImpl implements HealthService {
    @Resource
    HealthMapper healthMapper;

    @Override
    public Result<?> createHealth(Health health) {
        //return Result.success(healthMapper.insert(health));
        return Result.success(healthMapper.insertHealth(health.getUid(),health.getDetail(),health.getCheckCategory(),health.getPerson(),health.getTime(),health.getEnclosure()));
    }

    @Override
    public Result<?> updateHealth(Health health) {
        return Result.success(healthMapper.updateById(health));
    }

    @Override
    public Result<?> deleteHealth(Integer healthId) {
        return Result.success(healthMapper.deleteById(healthId));
    }

    @Override
    public Result<?> getHealth(Integer healthId) {
        return Result.success(healthMapper.getHealth(healthId));
    }

    @Override
    public Result<?> getHealthList(Integer uid) {
        return Result.success(healthMapper.getHealthList(uid));
    }
}
