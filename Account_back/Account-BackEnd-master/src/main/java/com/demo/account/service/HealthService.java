package com.demo.account.service;

import com.demo.account.common.Result;
import com.demo.account.entity.Health;

public interface HealthService {
    Result<?> createHealth(Health health);
    Result<?> updateHealth(Health health);
    Result<?> deleteHealth(Integer healthId);
    Result<?> getHealth(Integer healthId);
    Result<?> getHealthList(Integer uid);
}
