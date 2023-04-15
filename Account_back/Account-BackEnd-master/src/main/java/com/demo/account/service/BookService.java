package com.demo.account.service;

import com.alibaba.fastjson.JSONObject;
import com.demo.account.Po.CreateBookPo;
import com.demo.account.Po.InPo;
import com.demo.account.Po.PayPo;
import com.demo.account.Po.UpdateBookPo;
import com.demo.account.common.Result;
import com.demo.account.entity.BasicFund;
import com.demo.account.entity.Budget;
import com.demo.account.entity.Income;
import com.demo.account.entity.Payment;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public interface BookService {

    HashMap<String,String> getExpenditureType(int uid,String bookKeepingName,String bookKeepingTypeName);

    HashMap<String,String> getIncomeType(int uid, String bookKeepingName, String bookKeepingTypeName);

    String outSettingChange(int uid, String bookKeepingName,String bookKeepingTypeName,JSONObject json);

    String inSettingChange(int uid, String bookKeepingName,String bookKeepingTypeName,JSONObject json);

    List<BasicFund> selectAllBasicFund();

    String bookkeepingPayment(PayPo payPo);

    String bookkeepingIncome(InPo inPo);

    String bookkeepingAdd(int uid,String bookKeepingName,String bookKeepingCover,Integer extraMember1,Integer extraMember2,String template,String bookKeepingTypeName);

    String bookkeepingChange(int uid,String bookKeepingName,String bookKeepingNameNew,String bookKeepingCover,Integer extraMember1,Integer extraMember2);

    List<String> bookkeepingTypeNamesFind(int uid ,String bookKeepingName);

    List<JSONObject> selectBookkeepingIncome(int uid,String bookKeepingName);

    List<JSONObject> selectBookkeepingPayment(int uid, String bookKeepingName);

    List<String> selectUserBookkeeping(int uid);

    HashMap<String,HashMap<String, Integer>> countWeekIncome(int uid, String bookKeepingName,String nowTime);

    HashMap<String,HashMap<String, Integer>>  countMonthIncome(int uid, String bookKeepingName,String startTime,String endTime);

    HashMap<String,HashMap<String, Integer>> countYearIncome(int uid, String bookKeepingName,String startTime,String endTime);

    HashMap<String,HashMap<String, Integer>> countWeekPayment(int uid, String bookKeepingName,String nowTime);

    HashMap<String,HashMap<String, Integer>>  countMonthPayment(int uid, String bookKeepingName,String startTime,String endTime);

    HashMap<String,HashMap<String, Integer>> countYearPayment(int uid, String bookKeepingName,String startTime,String endTime);

    JSONObject getBookkeepingBudget(int uid,String bookKeepingName,String month);

    String changeBookkeepingBudget(int uid,String bookKeepingName,String month,String budget);

    List<JSONObject> getPartRecordOfIncomeAndPayment(int uid,String bookKeepingName,String classification,List<JSONObject> allPayment,List<JSONObject> allIncome);

    String deleteBookkeeping(int uid,String bookKeepingName);

    String deleteCustomFund(String customFundId);

    Result<?> getBookList(Integer uid);

    Result<?> updateBook(UpdateBookPo updateBookPo);

    Result<?> getBudget(Integer bookkeepingId);

    Result<?> createBook(CreateBookPo createBookPo);

    Result<?> detectBudget(Integer bookkeepingId);

    Result<?> getFundIcon(Integer bookkeepingTypeId);

    Result<?> getMonthAmount(String year, String month, Integer bookkeepingId);

    Result<?> getIncomeList(Integer bookkeepingId);

    Result<?> getPaymentList(Integer bookkeepingId);

    Result<?> getMonthPayment(Integer bookkeepingId, String year, String month);

    String discern(String type);
 }
