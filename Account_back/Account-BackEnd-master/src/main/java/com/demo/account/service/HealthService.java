package com.demo.account.service;

import com.demo.account.common.Result;
import com.demo.account.entity.HealthInfo;

public interface HealthService {
    Result<?> createHealth(HealthInfo healthInfo);
    Result<?> updateHealth(HealthInfo healthInfo);
    Result<?> deleteHealth(Integer healthId);
    Result<?> getHealth(Integer healthId);
    Result<?> getHealthList(Integer uid);
}
