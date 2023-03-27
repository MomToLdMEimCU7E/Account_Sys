package com.demo.account.controller;

import com.demo.account.common.Result;
import com.demo.account.entity.Account;
import com.demo.account.entity.AccountDetails;
import com.demo.account.service.AccountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/addAccount")
    @ApiOperation("添加账户")
    @ResponseBody
    public Result<?> addAccount(@RequestBody Account account){
        accountService.AddAccount(account);
        return Result.success();
    }

    @PutMapping("/updateAccount")
    @ApiOperation("修改账户")
    @ResponseBody
    public Result<?> updateAccount(@RequestBody Account account, @RequestParam Integer accountId){
        accountService.UpdateAccount(account, accountId);
        return Result.success();
    }

    @DeleteMapping("/deleteAccount")
    @ApiOperation("删除账户")
    @ResponseBody
    public Result<?> deleteAccount(@RequestParam Integer accountId){
        accountService.DeleteAccount(accountId);
        return Result.success();
    }

    @GetMapping("/getBalanceList")
    @ApiOperation("根据accountId获取账户余额列表")
    @ResponseBody
    public Result<?> getBalanceList(@RequestParam Integer accountId)
    {
        return Result.success(accountService.GetBalanceList(accountId));
    }

    @PostMapping("/addAccountDetail")
    @ApiOperation("添加账户内容")
    @ResponseBody
    public Result<?> addAccountDetail(@RequestBody AccountDetails accountDetails){
        accountService.AddAccountDetail(accountDetails);
        return Result.success();
    }

    @PutMapping("/updateAccountDetail")
    @ApiOperation("修改账户内容")
    @ResponseBody
    public Result<?> updateAccountDetail(@RequestBody AccountDetails accountDetails, @RequestParam Integer accountDetailsId){
        accountService.UpdateAccountDetail(accountDetails,accountDetailsId);

        return Result.success();
    }

    @PutMapping("/deleteAccountDetail")
    @ApiOperation("删除账户内容")
    @ResponseBody
    public Result<?> deleteAccountDetail(@RequestParam Integer accountDetailsId){
        accountService.DeleteAccountDetail(accountDetailsId);

        return Result.success();
    }

    @GetMapping("/getAccountList")
    @ApiOperation("获取用户账户列表")
    @ResponseBody
    public Result<?> getAccountList(@RequestParam Integer uid){

        return Result.success(accountService.GetAccountList(uid));
    }

    @GetMapping("/getAssetsAndDebt")
    @ApiOperation("获取资产和负债")
    @ResponseBody
    public Result<?> getAssetsAndDebt(@RequestParam Integer uid){
        return Result.success(accountService.GetAssetsAndDebts(uid));
    }
}
