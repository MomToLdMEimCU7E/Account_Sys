package com.demo.account.service.impl;

import com.demo.account.common.Result;
import com.demo.account.entity.HealthInfo;
import com.demo.account.mapper.HealthMapper;
import com.demo.account.service.HealthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class HealthServiceImpl implements HealthService {
    @Resource
    HealthMapper healthMapper;

    @Override
    public Result<?> createHealth(HealthInfo healthInfo) {
        return Result.success(healthMapper.insert(healthInfo));
        //return Result.success(healthMapper.insertHealth(healthInfo.getUid(), healthInfo.getDetail(), healthInfo.getCheckCategory(), healthInfo.getPerson(), healthInfo.getTestTime(), healthInfo.getEnclosure()));
    }

    @Override
    public Result<?> updateHealth(HealthInfo healthInfo) {
        return Result.success(healthMapper.updateById(healthInfo));
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
