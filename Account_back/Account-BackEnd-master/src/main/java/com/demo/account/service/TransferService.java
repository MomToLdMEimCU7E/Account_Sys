package com.demo.account.service;

import com.demo.account.common.Result;
import com.demo.account.entity.Transfer;

public interface TransferService {
    Result<?> Transfer(Transfer transfer);
}
