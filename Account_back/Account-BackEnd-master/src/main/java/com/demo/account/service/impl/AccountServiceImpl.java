package com.demo.account.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.account.Vo.AccountListVo;
import com.demo.account.Vo.AssetsAndDebtsVo;
import com.demo.account.Vo.BalanceVo;
import com.demo.account.common.Result;
import com.demo.account.entity.Account;
import com.demo.account.entity.AccountDetails;
import com.demo.account.mapper.AccountDetailsMapper;
import com.demo.account.mapper.AccountDetailsTypeMapper;
import com.demo.account.mapper.AccountMapper;
import com.demo.account.mapper.AccountTypeMapper;
import com.demo.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    AccountMapper accountMapper;
    @Resource
    AccountDetailsMapper accountDetailsMapper;
    @Resource
    AccountTypeMapper accountTypeMapper;
    @Resource
    AccountDetailsTypeMapper accountDetailsTypeMapper;

    @Override
    public Result<?> AddAccount(Account account) {
        accountMapper.insert(account);
        return Result.success();
    }

    @Override
    public Result<?> UpdateAccount(Account account, Integer accountId) {
        accountMapper.update(account, Wrappers.<Account>lambdaQuery().eq(Account::getAccountId, accountId));
        return Result.success();
    }

    @Override
    public Result<?> DeleteAccount(Integer accountId) {
        accountMapper.deleteById(accountId);
        return Result.success();
    }


    @Override
    public Result<?> GetBalanceList(Integer uid) {
        List<BalanceVo> balanceVoList;
        balanceVoList = accountDetailsMapper.getBalanceList(uid);
        balanceVoList.forEach(balanceVo -> {
            balanceVo.setAccountDetailName(accountDetailsTypeMapper.getTypeName(accountDetailsMapper.getDetailTypeId(balanceVo.getAccountDetailId())));
            balanceVo.setIcon(accountDetailsTypeMapper.getIcon(accountDetailsMapper.getDetailTypeId(balanceVo.getAccountDetailId())));
        });
        return Result.success(balanceVoList);
    }

    @Override
    public Result<?> AddAccountDetail(AccountDetails accountDetails) {
        accountDetailsMapper.insert(accountDetails);
        return Result.success();
    }

    @Override
    public Result<?> UpdateAccountDetail(AccountDetails accountDetails) {
        accountDetailsMapper.updateById(accountDetails);

        //accountDetailsMapper.update(accountDetails, Wrappers.<AccountDetails>lambdaQuery().eq(AccountDetails::getAccountDetailId, accountDetailId));
        return Result.success();
    }

    @Override
    public Result<?> DeleteAccountDetail(Integer accountDetailId) {
        accountDetailsMapper.deleteById(accountDetailId);
        return Result.success();
    }

    @Override
    public Result<?> GetAccountList(Integer uid) {
        List<AccountListVo> accountListVos;
        accountListVos = accountMapper.getAccountList(uid);
        return Result.success(accountListVos);
    }

    @Override
    public Result<?> GenerateRecords(Integer accountId) {
        return null;
    }

    @Override
    public Result<?> GetAssetsAndDebts(Integer uid) {
        Integer assets = accountDetailsMapper.getAssets(uid);
        Integer debts = accountDetailsMapper.getDebts(uid);

        if (assets == null)
        {
            assets = 0;
        }
        if (debts == null)
        {
            debts = 0;
        }
        Integer netAssets = assets - debts;




        AssetsAndDebtsVo assetsAndDebtsVo = new AssetsAndDebtsVo();
        assetsAndDebtsVo.setAssets(assets);
        assetsAndDebtsVo.setDebts(debts);
        assetsAndDebtsVo.setNetAssets(netAssets);

        return Result.success(assetsAndDebtsVo);
    }


}
