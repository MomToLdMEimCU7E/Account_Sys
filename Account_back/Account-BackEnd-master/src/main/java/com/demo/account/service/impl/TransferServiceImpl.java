package com.demo.account.service.impl;

import com.demo.account.common.Result;
import com.demo.account.entity.Transfer;
import com.demo.account.mapper.TransferMapper;
import com.demo.account.service.TransferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TransferServiceImpl implements TransferService {

    @Resource
    TransferMapper transferMapper;

    @Override
    public Result<?> Transfer(Transfer transfer) {
        transferMapper.insert(transfer);
        return Result.success();
    }
}
