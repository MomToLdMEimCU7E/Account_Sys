package com.demo.account.service;

import com.demo.account.common.Result;
import com.demo.account.entity.Account;
import com.demo.account.entity.AccountDetails;

public interface AccountService {
    Result<?> AddAccount(Account account);
    Result<?> UpdateAccount(Account account, Integer accountId);
    Result<?> DeleteAccount(Integer accountId);
    Result<?> GetBalanceList(Integer uid);
    Result<?> AddAccountDetail(AccountDetails accountDetails);
    Result<?> UpdateAccountDetail(AccountDetails accountDetails);
    Result<?> DeleteAccountDetail(Integer accountDetailId);
    Result<?> GetAccountList(Integer uid);
    Result<?> GenerateRecords(Integer accountId);
    Result<?> GetAssetsAndDebts(Integer uid);

}
