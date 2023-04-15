package com.demo.account.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.demo.account.Po.CreateBookPo;
import com.demo.account.Po.InPo;
import com.demo.account.Po.PayPo;
import com.demo.account.Po.UpdateBookPo;
import com.demo.account.Vo.FundIconListVo;
import com.demo.account.Vo.IncomeVo;
import com.demo.account.Vo.MonthAmountVo;
import com.demo.account.Vo.PaymentVo;
import com.demo.account.common.Result;
import com.demo.account.entity.*;
import com.demo.account.exception.BizException;
import com.demo.account.mapper.*;
import com.demo.account.service.BookService;
import com.demo.account.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Resource
    private BookMapper bookMapper;

    @Resource
    private IncomePaymentMapper incomePaymentMapper;

    @Resource
    private BudgetMapper budgetMapper;

    @Resource
    Budget2Mapper budget2Mapper;

    @Resource
    AccountDetailsMapper accountDetailsMapper;

    @Resource
    AccountDetailsTypeMapper accountDetailsTypeMapper;

    @Override
    public List<BasicFund> selectAllBasicFund() {
        return bookMapper.selectAllBasicFunds();
    }

    @Override
    public HashMap<String,String> getExpenditureType(int uid, String bookKeepingName,String bookKeepingTypeName) {
        HashMap<String,String> l=getPaymentType(uid,bookKeepingName,"O",bookKeepingTypeName);
        return l;
    }

    @Override
    public HashMap<String,String> getIncomeType(int uid, String bookKeepingName,String bookKeepingTypeName) {
        HashMap<String,String> l=getPaymentType(uid,bookKeepingName,"I",bookKeepingTypeName);
        return l;
    }


    /*遇到的问题：如果要提供三个模板的话那么那张bookkeeping_type的前三项就无法进行更改，因为所有用户都要用到这三条记录
                 如果这时候要对模板进行修改的话就产生了问题。
                 1.如果不存在模板的话，只需要更该对应模板的bookkeeping_type_funds_id项的值，
                 如果有用户自定义的款项就再在customed_fund表添加记录就可以完成。
                 此时对于新建一个模板也只需要在bookkeeping_type表中添加一条记录，然后按照上述规则更新customed_fund表即可。
                 添加的时候还要保证一个用户的一个账本在bookkeeping_type表中的类型名称字段(bookkeeping_type_name)要互不相同,不同用户间可以存在相同。
                 2.如果存在模板的话，由于前三项不可更改，那么对于默认模板的更改操作可以认为是新建模板操作，这样子就需要用户把支出的项目、收入的项目全部更改完毕，
                 然后命名完成(命名规则同上)，在bookkeeping_type表中添加一条记录即可。对于用户自定义的一些款项同样在customed_fund表添加记录就可以完成。
                 但这种更改模式不符合常识，所以如果需要做成第一种情况的前端效果的话就需要，增加一张表，那一张表就拿来存系统的模板不变动，用户对模板的更改
                 完成后只需要在另一张表中存入一条记录即可，接下来对模板的修改操作都会反映在这一张表上
               * */
    @Override
    public String outSettingChange(int uid, String bookKeepingName, String bookKeepingTypeName, JSONObject json) {
        List<String> l=(List<String>)json.get("json");
        StringBuilder basicFunds= new StringBuilder(); // 用于记录更新后的款项字符序列
        List<BookKeeping> bookKeeping=bookMapper.selectByUidAndName(uid,bookKeepingName);
        for (BookKeeping i:bookKeeping){
            int k=i.getBookkeeping_type_id();
            String temp=bookMapper.selectBookkeepingTypeName(k);
            if (Objects.equals(temp, bookKeepingTypeName)) {
                for (String j : l) {
                    if (j.contains("BO")) {
                        basicFunds.append(j).append("-");
                    } else {
                        //把自定义的款项添加到customed_funds表中
                        //自定义的款项是“CO****”格式
                        bookMapper.insertCFund("CO",uid,j.substring(2),k);
                    }
                }
                String ls=bookMapper.selectBookkeepingTypeList(i.getBookkeeping_type_id());
                String boLs=ls.substring(ls.indexOf("BI"));
                basicFunds.append(boLs);
                bookMapper.updateBookKeepingTypeList(String.valueOf(basicFunds),k);
            }
        }
        return "success";
    }

    @Override
    public String inSettingChange(int uid, String bookKeepingName, String bookKeepingTypeName, JSONObject json) {
        List<String> l=(List<String>)json.get("json");
        StringBuilder basicFunds= new StringBuilder(); // 用于记录更新后的款项字符序列
        List<BookKeeping> bookKeeping=bookMapper.selectByUidAndName(uid,bookKeepingName);
        for (BookKeeping i:bookKeeping){
            int k=i.getBookkeeping_type_id();
            String temp=bookMapper.selectBookkeepingTypeName(k);
            if (Objects.equals(temp, bookKeepingTypeName)) {
                for (String j : l) {
                    if (j.contains("BI")) {
                        basicFunds.append("-").append(j);
                    } else {
                        //把自定义的款项添加到customed_funds表中
                        //自定义的款项是“CI****”格式 ***是款项名称
                        bookMapper.insertCFund("CI",uid,j.substring(2),k);
                    }
                }
                String ls=bookMapper.selectBookkeepingTypeList(i.getBookkeeping_type_id());
                String biLs=ls.substring(0,ls.indexOf("BI")-1);
                bookMapper.updateBookKeepingTypeList(biLs+basicFunds,k);
            }
        }
        return "success";
    }

    @Override
    public String bookkeepingPayment(PayPo payPo)
    {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(payPo.getUid(),payPo.getBookKeepingName());
        bookMapper.insertIntoPayment(payPo.getUid(), bookKeepingId, payPo.getAccountId(), payPo.getAmount(), timestamp, payPo.getFundId(), payPo.getCustomedFundId(), payPo.getComment(), payPo.getEnclosure());
        return "success";
    }

    @Override
    public String bookkeepingIncome(InPo inPo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(inPo.getUid(),inPo.getBookKeepingName());
        bookMapper.insertIntoIncome(inPo.getUid(), bookKeepingId, inPo.getAccountId(), inPo.getAmount(), timestamp, inPo.getFundId(), inPo.getCustomedFundId(), inPo.getComment(), inPo.getEnclosure());
        return "success";
    }

    @Override
    public List<JSONObject> selectBookkeepingIncome(int uid, String bookKeepingName) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Income> ls=incomePaymentMapper.selectAllIncome(bookKeepingId);
        List<JSONObject> lsobject=new ArrayList<>();
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }
        for (Income i:ls){
            JSONObject object=new JSONObject();
            AccountDetail ac=incomePaymentMapper.selectAccountDetails(i.getAccountDetailId());
            object.put("时间",i.getTime());
            object.put("备注",i.getComment());
            object.put("金额",i.getAmount());
            String fundName=bfm.get(i.getFundId());
            if (fundName!=null){
                object.put("款项名称",fundName);
                object.put("款项类型","基本");
            }else {
                String customName=bookMapper.selectCustomedFundName(i.getCustomedFundId());
                object.put("款项名称",customName);
                object.put("款项类型","自定义");
            }
            object.put("账户名称",incomePaymentMapper.getAccountTypeName(ac.getAccountDetailType()));
            lsobject.add(object);
        }
        return lsobject;
    }

    @Override
    public List<JSONObject> selectBookkeepingPayment(int uid, String bookKeepingName) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Payment> ls=incomePaymentMapper.selectAllPayment(bookKeepingId);
        List<JSONObject> lsobject=new ArrayList<>();
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }
        for (Payment i:ls){
            JSONObject object=new JSONObject();
            AccountDetail ac=incomePaymentMapper.selectAccountDetails(i.getAccountDetailId());
            object.put("时间",i.getTime());
            object.put("备注",i.getComment());
            object.put("金额","-"+i.getAmount());
            String fundName=bfm.get(i.getFundId());
            if (fundName!=null){
                object.put("款项名称",fundName);
                object.put("款项类型","基本");
            }else {
                String customName=bookMapper.selectCustomedFundName(i.getCustomedFundId());
                object.put("款项名称",customName);
                object.put("款项类型","自定义");
            }
            object.put("账户名称",incomePaymentMapper.getAccountTypeName(ac.getAccountDetailType()));
            lsobject.add(object);
        }
        return lsobject;
    }

    @Override
    public String bookkeepingAdd(int uid, String bookKeepingName, String bookKeepingCover, Integer extraMember1, Integer extraMember2,
                                 String template,String bookKeepingTypeName) {
        List<String> bookKeepingNameList=bookMapper.selectUserBookkeeping(uid);
        if (bookKeepingNameList.contains(bookKeepingName)) throw new BizException("-1","账本名不能重复");
        int typeId=bookMapper.generalTypeId()+1;
        bookMapper.insertIntoBookKeeping(uid,bookKeepingName,bookKeepingCover,extraMember1,extraMember2,typeId);
        bookMapper.insertIntoBookkeepingType(typeId,bookKeepingTypeName,template);
        return "success";
    }

    @Override
    public String bookkeepingChange(int uid, String bookKeepingName,String bookKeepingNameNew, String bookKeepingCover, Integer extraMember1, Integer extraMember2) {
        List<String> bookKeepingNameList=bookMapper.selectUserBookkeeping(uid);
        if (!bookKeepingNameList.contains(bookKeepingName)) throw new BizException("-1","账本名不存在");
        bookKeepingNameList.remove(bookKeepingName);
        if (bookKeepingNameList.contains(bookKeepingNameNew)) throw new BizException("-1","新的账本名重名");
        bookMapper.changeBookKeeping(uid, bookKeepingName, bookKeepingNameNew,bookKeepingCover, extraMember1, extraMember2);
        return "success";
    }

    @Override
    public List<String> bookkeepingTypeNamesFind(int uid, String bookKeepingName) {
        List<BookKeeping> ls=bookMapper.selectByUidAndName(uid,bookKeepingName);
        System.out.println(ls);
        List<String> typeNames=new ArrayList<>();
        for (BookKeeping i: ls){
            typeNames.add(bookMapper.selectBookkeepingTypeName(i.getBookkeeping_type_id()));
        }
        return typeNames;
    }

    //用于收入款项统计的私有方法用于 countWeekIncome countMonthIncome countYearIncome
    private void incomeFundMapCreate(String fundName,Income i,HashMap<String,Integer> custom,HashMap<String,Integer> funds){
        if (fundName!=null){ // 如果是基础款项
            if (!funds.containsKey(fundName))
                funds.put(fundName,Integer.parseInt(i.getAmount()));
            else
                funds.put(fundName,funds.get(fundName)+Integer.parseInt(i.getAmount()));
        }else { // 如果是自定义款项
            String customName=bookMapper.selectCustomedFundName(i.getCustomedFundId());
            if (!custom.containsKey(customName))
                custom.put(customName,Integer.parseInt(i.getAmount()));
            else
                custom.put(customName,custom.get(customName)+Integer.parseInt(i.getAmount()));
        }
    }

    private void  paymentFundMapCreate(String fundName,Payment i,HashMap<String,Integer> custom,HashMap<String,Integer> funds){
        if (fundName!=null){ // 如果是基础款项
            if (!funds.containsKey(fundName))
                funds.put(fundName,Integer.parseInt(i.getAmount()));
            else
                funds.put(fundName,funds.get(fundName)+Integer.parseInt(i.getAmount()));
        }else { // 如果是自定义款项
            String customName=bookMapper.selectCustomedFundName(i.getCustomedFundId());
            if (!custom.containsKey(customName))
                custom.put(customName,Integer.parseInt(i.getAmount()));
            else
                custom.put(customName,custom.get(customName)+Integer.parseInt(i.getAmount()));
        }
    }
    @Override
    public HashMap<String,HashMap<String, Integer>> countWeekIncome(int uid, String bookKeepingName, String nowTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Income> ls=incomePaymentMapper.selectAllIncome(bookKeepingId); // 获取对应账本的收入记录
        HashMap<String,Integer> hm= new HashMap<>();
        //初始化 hm
        String[] weeks={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        String format="yyyy-MM-dd HH:mm:ss";
        for (String i:weeks){
            hm.put(i,0);
        }

        // 获取当前日期所对应那一周的范围 例如今天是 2023年4月3日 那么一周对应的日期范围是 [2023-04-03 00:00:00 2023-04-09 23:59:59]
        List<String> startEndTimeList=DateUtils.getStartAndEndTime(DateUtils.strToSqlDate(nowTime,format));
        Timestamp startTime=DateUtils.strToSqlDate(startEndTimeList.get(0),format);
        Timestamp endTime=DateUtils.strToSqlDate(startEndTimeList.get(1),format);

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for (Income i:ls){
            String time= DateUtils.dateToStr(i.getTime(),format);

            // 判断这个时间是星期几
            String week=DateUtils.getWeek(time,format);

            // 判断支出记录的时间是否在这个区间内
            boolean whetherInThisMonth=DateUtils.isEffectiveDate(i.getTime(),startTime,endTime);

            // 如果记录中的时间是对应区间中的,就获取hp中对应星期的值并且更改
            if (whetherInThisMonth){
                Integer temp=hm.get(week)+Integer.parseInt(i.getAmount());
                hm.put(week,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BI*所对应的款项名称
                incomeFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("周收入总和",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public HashMap<String,HashMap<String, Integer>>  countMonthIncome(int uid, String bookKeepingName, String startTime, String endTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Income> ls=incomePaymentMapper.selectAllIncome(bookKeepingId);
        HashMap<String,Integer> hm= new HashMap<>();
        String format="yyyy-MM-dd HH:mm:ss";
        int maxDayOfMonth=DateUtils.getDaysOfMonth(DateUtils.strToSqlDate(startTime,format));
        for (Integer i=1;i<=maxDayOfMonth;i++){
            hm.put(i.toString(),0);
        }

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for(Income i:ls){
            boolean whetherInThisMonth=DateUtils.isEffectiveDate(i.getTime(),DateUtils.strToSqlDate(startTime,format),DateUtils.strToSqlDate(endTime,format));
            if (whetherInThisMonth){
                String day=DateUtils.getMonthDay2Set(DateUtils.dateToStr(i.getTime(),format),"day").toString();
                Integer temp=hm.get(day)+Integer.parseInt(i.getAmount());
                hm.put(day,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BI*所对应的款项名称
                incomeFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("当月每天收入",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public HashMap<String,HashMap<String, Integer>> countYearIncome(int uid, String bookKeepingName, String startTime, String endTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Income> ls=incomePaymentMapper.selectAllIncome(bookKeepingId);
        HashMap<String,Integer> hm= new HashMap<>();
        String format="yyyy-MM-dd HH:mm:ss";
        for (Integer i=1;i<=12;i++){
            hm.put(i.toString(),0);
        }

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for(Income i:ls){
            boolean whetherInThisYear=DateUtils.isEffectiveDate(i.getTime(),DateUtils.strToSqlDate(startTime,format),DateUtils.strToSqlDate(endTime,format));
            if (whetherInThisYear){
                String month=DateUtils.getMonthDay2Set(DateUtils.dateToStr(i.getTime(),format),"month").toString();
                Integer temp=hm.get(month)+Integer.parseInt(i.getAmount());
                hm.put(month,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BI*所对应的款项名称
                incomeFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("当年每月收入",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> countWeekPayment(int uid, String bookKeepingName, String nowTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Payment> ls=incomePaymentMapper.selectAllPayment(bookKeepingId); // 获取对应账本的收入记录
        HashMap<String,Integer> hm= new HashMap<>();
        //初始化 hm
        String[] weeks={"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        String format="yyyy-MM-dd HH:mm:ss";
        for (String i:weeks){
            hm.put(i,0);
        }

        // 获取当前日期所对应那一周的范围 例如今天是 2023年4月3日 那么一周对应的日期范围是 [2023-04-03 00:00:00 2023-04-09 23:59:59]
        List<String> startEndTimeList=DateUtils.getStartAndEndTime(DateUtils.strToSqlDate(nowTime,format));
        Timestamp startTime=DateUtils.strToSqlDate(startEndTimeList.get(0),format);
        Timestamp endTime=DateUtils.strToSqlDate(startEndTimeList.get(1),format);

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for (Payment i:ls){
            String time= DateUtils.dateToStr(i.getTime(),format);

            // 判断这个时间是星期几
            String week=DateUtils.getWeek(time,format);

            // 判断支出记录的时间是否在这个区间内
            boolean whetherInThisMonth=DateUtils.isEffectiveDate(i.getTime(),startTime,endTime);

            // 如果记录中的时间是对应区间中的,就获取hp中对应星期的值并且更改
            if (whetherInThisMonth){
                Integer temp=hm.get(week)+Integer.parseInt(i.getAmount());
                hm.put(week,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BO*所对应的款项名称
                paymentFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("周支出总和",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> countMonthPayment(int uid, String bookKeepingName, String startTime, String endTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Payment> ls=incomePaymentMapper.selectAllPayment(bookKeepingId);
        HashMap<String,Integer> hm= new HashMap<>();
        String format="yyyy-MM-dd HH:mm:ss";
        int maxDayOfMonth=DateUtils.getDaysOfMonth(DateUtils.strToSqlDate(startTime,format));
        for (Integer i=1;i<=maxDayOfMonth;i++){
            hm.put(i.toString(),0);
        }

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for(Payment i:ls){
            boolean whetherInThisMonth=DateUtils.isEffectiveDate(i.getTime(),DateUtils.strToSqlDate(startTime,format),DateUtils.strToSqlDate(endTime,format));
            if (whetherInThisMonth){
                String day=DateUtils.getMonthDay2Set(DateUtils.dateToStr(i.getTime(),format),"day").toString();
                Integer temp=hm.get(day)+Integer.parseInt(i.getAmount());
                hm.put(day,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BO*所对应的款项名称
                paymentFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("当月每天支出",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> countYearPayment(int uid, String bookKeepingName, String startTime, String endTime) {
        //int bookKeepingTypeId=bookMapper.findBookKeepingTypeIdInConditions(uid,bookKeepingName,bookKeepingTypeName);
        //int bookKeepingId=bookMapper.findBookKeepingId(uid,bookKeepingName,bookKeepingTypeId);
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        List<Payment> ls=incomePaymentMapper.selectAllPayment(bookKeepingId);
        HashMap<String,Integer> hm= new HashMap<>();
        String format="yyyy-MM-dd HH:mm:ss";
        for (Integer i=1;i<=12;i++){
            hm.put(i.toString(),0);
        }

        HashMap<String,Integer> funds=new HashMap<>(); // 记录基础款项的HashMap
        HashMap<String,Integer> custom=new HashMap<>(); // 记录自定义款项的HashMap
        List<BasicFund> basicFunds=bookMapper.selectAllBasicFunds();
        HashMap<String,String> bfm=new HashMap<>(); // 记录基础款项的HashMap
        // 给bfm赋值
        for (BasicFund i:basicFunds){
            bfm.put(i.getFund_id(),i.getFund_name());
        }

        for(Payment i:ls){
            boolean whetherInThisYear=DateUtils.isEffectiveDate(i.getTime(),DateUtils.strToSqlDate(startTime,format),DateUtils.strToSqlDate(endTime,format));
            if (whetherInThisYear){
                String month=DateUtils.getMonthDay2Set(DateUtils.dateToStr(i.getTime(),format),"month").toString();
                Integer temp=hm.get(month)+Integer.parseInt(i.getAmount());
                hm.put(month,temp);
                /*-------------------------*/
                String fundName=bfm.get(i.getFundId()); //表示BI*所对应的款项名称
                paymentFundMapCreate(fundName,i,custom,funds);
            }
        }
        //System.out.println(hm);
        HashMap<String,HashMap<String, Integer>> result=new HashMap<>();
        result.put("当年每月支出",hm);
        result.put("基础款项总和",funds);
        result.put("自定义款项总和",custom);
        System.out.println(result);
        return result;
    }

    @Override
    public List<String> selectUserBookkeeping(int uid) {
        return bookMapper.selectUserBookkeeping(uid);
    }

    private boolean monthInputCheck(String month){
        String[] months={"jan","feb","mar","apr","may","jun","jul","aug","sept","oct","nov","dec"};
        for (String i: months){
            if (Objects.equals(i, month)){
                return true;
            }
        }
        return false;
    }
    @Override
    public JSONObject getBookkeepingBudget(int uid, String bookKeepingName,String month) {
        int bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        boolean validOrNot;
        validOrNot=monthInputCheck(month);
        JSONObject object=new JSONObject();
        if (validOrNot){
            Budget budget=budgetMapper.selectBookkeepingBudget(bookKeepingId);
            JSONObject budgetMonth=(JSONObject) JSONObject.toJSON(budget);
            object.put("该月预算为",budgetMonth.get(month));
        }else {
            throw new BizException("-1","月份的输入不正确应为jan/feb/mar/apr/may/jun/jul/aug/sept/oct/nov/dec其中之一");
        }
        return object;
    }

    @Override
    public String changeBookkeepingBudget(int uid, String bookKeepingName, String month, String budget) {
        int bookKeepingId;
        try{
            bookKeepingId=bookMapper.selectBookkeepingId(uid,bookKeepingName);
        }catch (Exception e){
            throw new BizException("-1","没找到该账簿");
        }
        boolean validOrNot;
        validOrNot=monthInputCheck(month);
        if (validOrNot){
            budgetMapper.updateBudget(month,budget,bookKeepingId);
        }else {
            throw new BizException("-1","月份的输入不正确应为jan/feb/mar/apr/may/jun/jul/aug/sept/oct/nov/dec其中之一");
        }
        return "success";
    }

    @Override
    public List<JSONObject> getPartRecordOfIncomeAndPayment(int uid, String bookKeepingName, String classification,List<JSONObject> allPayment,List<JSONObject> allIncome) {
        String[] ys={"餐饮","蔬菜","水果","零食"}; // 饮食大类
        String[] yl={"通讯","社交","旅行","购物","娱乐"}; // 娱乐大类
        String[] jt={"快递","交通"}; // 交通大类
        String[] sh={"服饰","家庭","住房","日用","运动"}; // 生活大类
        String[] gz={"工资","年终奖","收款"}; // 工资大类
        String[] lc={"分红","理财"}; // 理财大类
        String[] rq={"借入","礼金","红包"}; // 人情大类
        String[] other={"生活费","其它","租金"}; // 其它大类
        String[] whichClassification;
        switch (classification){
            case "饮食": whichClassification=ys;break;
            case "娱乐": whichClassification=yl;break;
            case "交通": whichClassification=jt;break;
            case "生活": whichClassification=sh;break;
            case "工资": whichClassification=gz;break;
            case "理财": whichClassification=lc;break;
            case "人情": whichClassification=rq;break;
            case "其它": whichClassification=other;break;
            default: throw new BizException("-1","类别输入错误可选输入有{饮食/娱乐/交通/生活/工资/理财/人情/其它}");
        }
        List<JSONObject> result=new ArrayList<>();
        if (classification.equals("其它")){
            for (JSONObject i:allPayment){
                if (i.get("款项类型").toString().equals("自定义")){
                    result.add(i);
                }
            }
            for (JSONObject i:allIncome){
                if (i.get("款项类型").toString().equals("自定义")){
                    result.add(i);
                }
            }
        }
        for (JSONObject i: allPayment){
            for (String j:whichClassification){
                if (i.get("款项名称").toString().equals(j)){
                    result.add(i);
                }
            }
        }
        for (JSONObject i:allIncome){
            for (String j:whichClassification){
                if (i.get("款项名称").toString().equals(j)){
                    result.add(i);
                }
            }
        }
        return result;
    }

    @Override
    public String deleteBookkeeping(int uid, String bookKeepingName) {
        try{
            bookMapper.selectBookkeepingId(uid,bookKeepingName);
        }catch (Exception e){
            throw new BizException("-1","没找到该账簿");
        }
        bookMapper.deleteBookkeeping(uid, bookKeepingName);
        return "success";
    }

    @Override
    public String deleteCustomFund(String customFundId) {
        List<String> list=bookMapper.selectAllCustomFundId();
        if (!list.contains(customFundId)) throw new BizException("-1","自定义款项不存在");
        bookMapper.deleteCustomFund(customFundId);
        return "success";
    }

    @Override
    public Result<?> getBookList(Integer uid) {
        return Result.success(bookMapper.getBookList(uid));
    }

    @Override
    public Result<?> updateBook(UpdateBookPo updateBookPo) {
        bookMapper.updateBook(updateBookPo.getBookkeepingCover(), updateBookPo.getBookkeepingName(), updateBookPo.getBookkeepingId());
        budget2Mapper.updateBook(updateBookPo.getBudget(), updateBookPo.getBookkeepingId());
        return Result.success();
    }

    @Override
    public Result<?> getBudget(Integer bookkeepingId) {
        return Result.success(budget2Mapper.getBudget(bookkeepingId));
    }

    @Override
    public Result<?> createBook(CreateBookPo createBookPo) {
        bookMapper.createBook(createBookPo.getBookkeepingId(), createBookPo.getUid(), createBookPo.getBookkeepingTypeId(), createBookPo.getBookkeepingCover(), createBookPo.getBookkeepingName(), createBookPo.getCustomedFundsId(), createBookPo.getExtraMember1(), createBookPo.getExtraMember2());
        Integer bookId = bookMapper.selectBookkeepingId(createBookPo.getUid(), createBookPo.getBookkeepingName());
        bookMapper.generateDate1(createBookPo.getUid(), bookId);
        bookMapper.generateDate2(createBookPo.getUid(), bookId);
        return Result.success();
    }

    @Override
    public Result<?> detectBudget(Integer bookkeepingId) {
        Budget2 res = budget2Mapper.selectOne(Wrappers.<Budget2>lambdaQuery().eq(Budget2::getBookkeepingId, bookkeepingId));
        if (res == null){
            budget2Mapper.createBudget(bookkeepingId, "0");
        }
        return Result.success();
    }

    @Override
    public Result<?> getFundIcon(Integer bookkeepingTypeId) {
        String FundIdString = bookMapper.getFundIdString(bookkeepingTypeId);
        String[] id = FundIdString.split("-");
        List<FundIconListVo> fundIconListVoListIn = new ArrayList<>();
        List<FundIconListVo> fundIconListVoListOut = new ArrayList<>();
        List<List<FundIconListVo>> result = new ArrayList<>();
        for (int i = 0; i < id.length; i++) {
            if(id[i].charAt(1) == 'I'){
                fundIconListVoListIn.add(bookMapper.getIcon(id[i]));
            } else if(id[i].charAt(1) == 'O'){
                fundIconListVoListOut.add(bookMapper.getIcon(id[i]));
            }

        }

        result.add(fundIconListVoListIn);
        result.add(fundIconListVoListOut);
        return Result.success(result);
    }

    @Override
    public Result<?> getMonthAmount(String year, String month, Integer bookkeepingId) {

        String sql = "'" + year + "-" + month + "-%d'";
        List<MonthAmountVo> income = bookMapper.getIncomeMonth(sql, bookkeepingId);
        List<MonthAmountVo> pay = bookMapper.getPayMonth(sql, bookkeepingId);
        List<MonthAmountVo> res = new ArrayList<>();
        for (int i = 0; i < income.size(); i++) {
            double sum = Double.parseDouble(income.get(i).getSum()) - Double.parseDouble(pay.get(i).getSum());
            income.get(i).setSum(String.valueOf(sum));
        }
        for (int i = 10; i < income.size(); i++) {
            res.add(income.get(i));
        }
        for (int i = 0; i < 10; i++) {
            res.add(income.get(i));
        }
        Collections.reverse(res);
        return Result.success(res);
    }

    @Override
    public Result<?> getIncomeList(Integer bookkeepingId) {
//        List<Payment> incomeList = incomePaymentMapper.getIncomeList(bookkeepingId);
//        List<IncomeVo> incomeVoList = new ArrayList<>();
//        for (int i = 0; i < incomeList.size(); i++) {
//            String fundName = bookMapper.getFundName(incomeList.get(i).getFundId());
//            String accountName = accountDetailsTypeMapper.getTypeName(accountDetailsMapper.getDetailTypeId(incomeList.get(i).getAccountDetailId()));
//            IncomeVo incomeVo = new IncomeVo(incomeList.get(i).getIncomeId(),incomeList.get(i).getUid(),incomeList.get(i).getBookkeepingId(),accountName,incomeList.get(i).getAccountDetailId(),incomeList.get(i).getAmount(),incomeList.get(i).getTime(),fundName,null,incomeList.get(i).getComment(),incomeList.get(i).getEnclosure());
//            incomeVoList.add(incomeVo);
//        }
//        return Result.success(incomeVoList);
        return null;
    }

    @Override
    public Result<?> getPaymentList(Integer bookkeepingId) {
        List<Payment> paymentList = incomePaymentMapper.getPaymentList(bookkeepingId);
        List<PaymentVo> paymentVoList = new ArrayList<>();
        for (int i = 0; i < paymentList.size(); i++) {
            String fundName = bookMapper.getFundName(paymentList.get(i).getFundId());
            String fundIcon = bookMapper.getFundIcon(paymentList.get(i).getFundId());
            String accountName = accountDetailsTypeMapper.getTypeName(accountDetailsMapper.getDetailTypeId(paymentList.get(i).getAccountDetailId()));
            String amount = "-" + paymentList.get(i).getAmount();
            PaymentVo paymentVo = new PaymentVo(paymentList.get(i).getPaymentId(),paymentList.get(i).getUid(),paymentList.get(i).getBookkeepingId(),accountName,paymentList.get(i).getAccountDetailId(),amount,paymentList.get(i).getTime(),fundName,paymentList.get(i).getCustomedFundId(),paymentList.get(i).getComment(),paymentList.get(i).getEnclosure(),fundIcon);
            paymentVoList.add(paymentVo);
        }

        List<Payment> incomeList = incomePaymentMapper.getIncomeList(bookkeepingId);
        List<PaymentVo> incomeVoList = new ArrayList<>();
        for (int i = 0; i < incomeList.size(); i++) {
            String fundName = bookMapper.getFundName(incomeList.get(i).getFundId());
            String fundIcon = bookMapper.getFundIcon(paymentList.get(i).getFundId());
            String accountName = accountDetailsTypeMapper.getTypeName(accountDetailsMapper.getDetailTypeId(incomeList.get(i).getAccountDetailId()));
            PaymentVo paymentVo = new PaymentVo(incomeList.get(i).getPaymentId(),incomeList.get(i).getUid(),incomeList.get(i).getBookkeepingId(),accountName,incomeList.get(i).getAccountDetailId(),incomeList.get(i).getAmount(),incomeList.get(i).getTime(),fundName,null,incomeList.get(i).getComment(),incomeList.get(i).getEnclosure(),fundIcon);
            incomeVoList.add(paymentVo);
        }

        paymentVoList.addAll(incomeVoList);

        paymentVoList.sort((t1, t2) ->t2.getTime().compareTo(t1.getTime()));

        return Result.success(paymentVoList);
    }

    @Override
    public Result<?> getMonthPayment(Integer bookkeepingId, String year, String month) {
        String date = "'" + year + "-" + month + "%'";

        java.text.SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM");
        Date date1 = null;
        try {
            date1 = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String res = bookMapper.getMonthPayment(date, bookkeepingId);

//        String Sdate = year + month + "-01 00:00:00";
//
//        if (month.equals("12")){
//            year = String.valueOf(Integer.parseInt(year) + 1);
//            month = "01";
//        } else {
//            month = String.valueOf(Integer.parseInt(month) + 1);
//        }
//
//        String Edate = year + month + "-01 00:00:00";
//
//        LambdaQueryWrapper<Payment> queryWrapper = new LambdaQueryWrapper<>();
//
//        queryWrapper.eq(Payment::getBookkeepingId, bookkeepingId);
//        queryWrapper.ge(Payment::getTime, Sdate);
//        queryWrapper.le(Payment::getTime, Edate);

//        paymentMapper.selectList(queryWrapper);

        return Result.success(res);
    }

    @Override
    public String discern(String type) {
        String clothes = "衣 连衣裙 裙装 衬衫 长裙 裙子 衬衣 裙 短裙 牛仔裤 粉色 裤 紧身 无袖 波点 短裤 上衣 T恤 外套 T恤衫 背心 夹克 围巾 领带 球鞋 西装 西服 白衬衫 毛衣 袜子 皮鞋 反领 羽绒服 皮衣 高领 披肩 大衣 斗篷 披风 一顶 蝴蝶结 文胸 胸罩 内衣 丝袜 太阳镜 安全套 鞋子 口红 吊带 内裤 裤子 拖鞋 手帕 紧身裤 燕尾服 高跟鞋 平底鞋 鞋 凉鞋 人字拖 运动鞋 袜 靴子 头盔 靴 雨披 雨衣 窗帘布 红尾冬 项链 戒指 手镯 腰带 手链 耳环 首饰 胸针 带子 珠子 蜜蜡 金饰 珍珠 绫衣 饰物 头饰 假发 发饰 流苏";
        String daily = "洗漱 盥洗 洗澡 做饭 洗衣服 就寝 冲凉 床铺 洗脸 手边 洗衣粉 肥皂 牙膏 阻燃剂 纯净水 蒸馏水 苏打水 黄油 明矾 氯化钾 牙刷 刮胡刀 口香糖 刀片 电动牙刷 安全套 螺丝起子 鞋底 巢蛛 药膏 香精 蜂蜜 漱口水 凡士林 果汁 毛巾 纸巾 枕头 抹布 纱布 水壶 毯子 纸袋 垫子 洗发水 化妆品 膏 香皂 洗发精 洗头水 红花油 增稠剂 杀菌剂 醋酸 止痛剂 含氟 橙汁 HMTD 发胶 唇膏 口红 彩色笔 闪粉 胸针 小妖精 润体 德武雷瓦 皮厄县 清洁 洁净 保温 洗涤 保鲜 清洁卫生 净化水 环保型 煮食 省水 洗衣机 冰箱 滚筒 电风扇 微波炉 电饭煲 热水器 油烟机 洗碗机 小家电 窗帘 玻璃窗 沙发 遮阳板 帘子 地板 纱窗 橱柜 衣柜 隔板 厨房 浴室 客厅 卧室 卫生间 烤箱 吧台 壁炉 阁楼 锅 煮 油锅 饭 锅盖 茶杯 煎 汤 蒸 盛在 碗 炭火 碟子 茶壶 瓶 南瓜 松饼 茶盘 勺 铃铛 筷子 剪刀 铁板 锣 手把 手帕 虾子 木鱼 床板 烤炉 冰块 水槽 托盘 刀 匕首 斧头 刀子 小刀 利刃 杖 短刀 斧 弓 吸尘器 咖啡机 电扇 圆筒 滚珠轴承 扫帚 钳子 扫把 手杖 铲子 扇子 拖把 两把 清洁剂 洗涤剂 油漆 消毒剂 涂料 漂白剂 活性炭 护理 保健 照护 医疗 医护 看护 卫生保健 理疗 牙科 护理人员 护肤品 彩妆 护肤 面膜 保养品 香水 美容 护发 资生堂 美白 保湿 丰胸 美肤 化妆 穿衣 化妆师 妆容 美甲 发型师 上色 美发 剪发 变装 美体 整形 医学美容 美容美发 皮具 美容院 修脚 手作 乳液 精油 面霜 香氛 指甲油 沐浴露 巧克力 防晒霜 防晒 防晒品 染发 粉底 防晒油 洗面奶 润肤乳 柠檬 可伶可俐 H2O 丝袜 迷你裙 文胸 围巾 发饰 裸色 眼影 腮红 唇彩 眼妆 假睫毛 睫毛膏 珠光 晕染 底妆 眼线 眼线笔 家具 红木家具 家居 饰品 布料 家居用品 工艺品 家私 厨具 灯具 不锈钢 照明 玻璃 灯泡 节能灯 照明设备 瓷砖 冷气机 电子零件 被子 棉被 被褥 床 柜子 棺材 席子 用布 草席 鞋子 软垫 床垫 地毯 皮革 手工艺品 彩色玻璃 银器 电子产品 家用电器 家电 电器产品 数码产品 电动工具 电子元件 消费性 手机 智能手机 笔记本电脑 电脑 笔记本 iPhone4 新手机 个人电脑 U盘 笔记型电脑 计算机 机器 电脑系统 微电脑 打印机 电脑软件 电视 电视节目 有线电视 TV 有线 无线 电影频道 数码 卫星电视 电视新闻 音响 音响系统 音效 扬声器 音响设备 音箱 汽车音响 声效 音响效果 舞台灯光 卫生 环境卫生 公共卫生 卫生局 安全卫生 卫生部门 食品卫生 卫生巾 纸尿裤 尿布 保鲜膜 湿巾 护垫 隐形眼镜 棉质 内衬 润滑剂 外阴部 止血剂 tuberculosis implantation 厕纸 手纸 卫生纸 餐巾纸 路标 公共厕所 咸酥鸡 香蕉水 册子 ";
        String education = "学校 幼儿园 私立学校 小学 中小学 教会学校 该校 补习学校 公立学校 夜校 中学 初级中学 高级中学 中学校长 师范学校 高中 二中 初中 幼稚园 中心小学 附属小学 大学 学院 理工学院 理工大学 国立大学 大学教授 州立大学 商学院 法学院 神学院 专科学校 工程学院 学院院长 分校 师范学院 医学院 技术 核心技术 控制技术 电子技术 关键技术 技术开发 工艺技术 纳米技术 专利技术 半导体技术 职业 业余 篮球 排球 篮球员 职业培训 专业技能 技术型 社会工作 艾文涛 培训 专业培训 培训班 技术培训 培训课程 业务培训 辅导 研修 晋升 晋升为 升任 升迁 升进 获授 进升 官拜 续任 出掌 博物馆 美术馆 历史博物馆 展览馆 国家博物馆 图书馆 艺术馆 档案馆 陈列馆 该馆 分馆 阅览室 图书室 国家图书馆 书库 科学馆 科技馆 青少年宫 文化馆 市图书馆 天文馆 大学城 博览会 展览会 交易会 展销会 世博会 展 洽谈会 展会 西博会 哈洽会 少年宫 活动中心 实验学校 老年大学 特殊教育 八中 育才 在线教育 VIPKID 幼教 医美 classroom 穗序 Shajapur Conques 季莫菲 桑翁 自学 学习 自修 半工半读 钻研 学 画画 研习 研读 听课 研究 科学研究 深入研究 研究成果 分子生物学 所研究 学术研究 生物学 研究课题 研究组 本科 专科 高职 院校 艺术类 二批 普通高中 理科 中专 研究生 全科 临床医学 各科 五年制 三年级 高三 一年级 高二 高中学生 年级 四年级 五年级 托儿所 学前班 初中班 小班 小学部 男女生 沙朗通 石羊镇 胡卢巴 吉翁县 pulchre 高等教育 基础教育 教育 高等院校 中等教育 医学教育 普通教育 精英教育 幼儿教育 英语教育 初等教育 学前教育 成人教育 留学 游学 求学 深造 进修 念书 就学 出国 完成学业 归国 学术 社会科学 法学 人文科学 自然科学 学术性 社会学 历史学 教学研究 学分 课程 文凭 双学位 专业课程 本科课程 四年制 预科 及格 中职 学位 学士学位 硕士学位 硕士 专业学位 毕业证书 哲学博士 教席 专业 专业人才 专业领域 专业知识 专精 特长 管理学 初级 挂科 米朗博县 jeau Auneau Mirambeau 奥诺县 Ez2Dj Loiron Attichy 阿蒂希县 高分 分数 最高分 满分 低分 总分 零分 平均分 优等生 五颗星 四颗星 评分 四星 颗星 评语 体育 足球 体育运动 竞技 体育产业 体育部 棋牌 青少 体育新闻 橄榄球 棒球 美式足球 冰球 拳击 网球 高尔夫球 高球 足球运动 踢球 手球 国际足球 足球界 体操 斯诺克 田径 花样滑冰 乒乓球 击剑 论文 学术论文 博士论文 文章 科学论文 期刊 毕业论文 学术著作 专著 奖项 大奖 奖 奥斯卡奖 特别奖 得奖者 格莱美奖 新人奖 奥斯卡金像奖 获奖者 科研 人才培养 工程技术 科研机构 基础科学 科研工作 科研项目 教研 考试 笔试 入学考试 面试 测验 口试 应考 考试成绩 司法考试 中考 课程内容 科目 学程 英语课程 选修 奖学金 助学金 资助 奖助学金 奖助金 奖助 公费 出国深造 班级 全校 班上 班主任 高年级 毕业班 本校 低年级 六年级 二年级 讲座 专题讲座 系列讲座 大讲堂 工作坊 讲堂 演讲会 知识讲座 学术活动 讲演 建筑 建筑物 建筑群 建筑风格 宗教建筑 公共建筑 古建筑 园林 建筑设计 高层建筑 艺术 表演艺术 当代艺术 美术 艺术创作 戏剧 绘画 摄影艺术 视觉艺术 书画艺术 计算机 电脑 计算机系统 电子计算机 计算机技术 计算机程序 嵌入式 电脑系统 电脑程式 计算机软件 作文 题目 作文题 试题 高考作文 课文 习作 唐诗 短文 议论文 选择题 考题 题型 数学题 大题 作答 问答题 填空题 填空 是非题 简答题 逻辑推理 应试者 较难 算式 第四步 小标题 本节 学科考试 有出 哈莱因 晃盖 试卷 考卷 阅卷 卷子 出题 习题 作业 检修 试运转 整地 电焊 工作台 防冲 配线 抢筑 夜航 实践 课堂教学 实践经验 素质教育 知行合一 道德教育 方法论 创造性 普及教育 求职 求职者 招聘 婚恋 海归 相亲 应征者 招工 复读生 猎头 实习 应聘 受训 兼职 见习 外派 就业 劳动力 就业机会 失业 就业率 失业者 劳工市场 就业者 就业人数 家庭收入 ";
        String food = "吃 吃饭 主食 肉类 水果 米饭 配菜 蔬果 鸡肉 稻米 食粮 凉拌菜 佐料 杂粮 木薯 正餐 牛羊肉 马铃薯 鱼肉 肉食 荞麦 马肉 米 饭 饺子 粥 锅 豆腐 炊 排骨 煎 包子 蒸 青菜 鸡 鸡汤 年糕 稀饭 葱 火锅 鸭蛋 香喷喷 面条 煎饼 面食 面包 调料 炸鸡 炒面 酱料 春卷 咖喱 酱 奶油 番茄酱 花生酱 奶茶 卤肉 馒头 豆豉 西瓜 蜜饯 烧饼 萝卜 鸡腿 燕窝 芝麻 臭豆腐 糖 辣椒 黄豆 冰棒 黄瓜 山药 中汤 巧克力 乳酪 饼干 冰淇淋 奶酪 披萨 薄饼 糖果 甜点 蜂蜜 甜品 香肠 面粉 罐头 起司 吐司 果酱 玉米 小麦 大豆 作物 棉花 蔬菜 农作物 豆类 谷物 甘蔗 大蒜 油菜 白糖 花生 稻谷 菜籽 油菜籽 甜菜 红薯 香菇 地瓜 冬瓜 洋葱 芋头 绿豆 芹菜 茄子 番薯 卷心菜 肉桂 菠萝 大枣 红萝卜 红豆 果蔬 农产品 瓜果 猪肉 叶菜 蔬菜水果 鸡蛋 糖水 白菜 韭菜 大白菜 苋菜 野菜 莴苣 苦瓜 山楂 大葱 柿子 刺蕊 胡萝卜 猪肝 生菜 西兰花 葡萄柚 柠檬 芥末 青椒 核桃 丝瓜 西红柿 无花果 草莓 坚果 哈密瓜 豌豆 毛豆 鹌鹑蛋 南瓜 豆子 黑胡椒 松露 椒 酥 花椒 莲子 豆 大黄 柿 笋 喜鹊 隐鳍 锥果 泡果 Benfeld 短蕊 长瓣 斑籽 零食 葡萄 橄榄油 果汁 苹果 苹果公司 微软 谷歌 iPhone iPad 诺基亚 Nexus 平板 亚马逊 小米 Surface Google 三星 Apple Lumia 黑莓 苹果电脑 三星公司 WP 香蕉 椰子 梨 麻 杏 梨子 香 桃 桃子 樱桃 石榴 莲 枇杷 蚕 柚子 木棉 薄荷 蓝莓 蘑菇 凤梨 雏菊 橙子 花椰菜 红辣椒 杏仁 小鸡 向日葵 皱果 茶树 柑橘 枸杞 薰衣草 白葡萄酒 番红花 肉 鱿鱼 鸭肉 腌 兔肉 虾子 肉片 火鸡 猪 羊 山羊 鸭 猫 鸭子 青蛙 屎 绵羊 母牛 兔 野猪 肥猪 水牛 蚯蚓 泥鳅 猪头 牛肉 猪油 羊肉 大米 肉品 牛排 猪血 砂糖 清汤 猪脚 牛 驴 蹄 熊 鹿 和牛 象 紫菊 羊喜 濶濑 双药芒 食用油 小龙虾 生姜 醋 红糖 煨 干果 牦牛 兔子 驼 小羊 骆驼 牛羊 公鸡 鸵鸟 蛋 鸡爪 鹌鹑 卤汁 鳝鱼 食料 鹅 鹭 龙虾 甲鱼 雀 鳗鱼 螺蛳 金针菜 鲍螺 鱼 鲫鱼 虾 小鱼 螃蟹 石斑鱼 鲑鱼 鲨鱼 金鱼 海龟 鳄鱼 墨鱼 沙丁鱼 大鱼 鲟鱼 鲈鱼 草鱼 鲤鱼 青鱼 莲藕 白鱼 田螺 海鳗 鳟鱼 荷叶 锦鲤 燕子 虎头 牛角 毛蟹 白水 海带 鲅鱼 小虾 糖渍 鲶鱼 金枪鱼 三须 蟹 蛤蜊 海参 螯 对虾 海胆 鲎 鳖 寄居蟹 鲍鱼 沙蟹 科仿 海螺 蝴蝶 蜻蜓 天鹅 榧螺 褐带 沫蝉 毛蛏 牛栓藤 牛栓 斯坦达脂 蟹蛛科花 晃盖 管吻 重寄生 牡蛎 鲱鱼 熏制 加蛋 鳕鱼 贝类 海产 奶制品 乳制品 食品 肉制品 酸奶 调味品 牛奶 海产品 植物油 家禽 豆制品 水产品 宠物食品 鱼油 奶品 罐头食品 奶 饮料 可乐 豆浆 汽水 饮品 鲜奶 味精 糖浆 橙汁 苏打水 雪糕 红茶 干酪 绿茶 麦片 麦芽 鸡精 黄油 果酒 伏特加 甜酒 肉汁 苹果酒 豆奶 雀巢 牛乳 冷饮 矿泉水 啤酒 咖啡 口香糖 酒水 水 井水 泉水 沙子 石灰 冷水 溪水 热水 冰 水银 池水 泥 盐 出水 取水 污泥 水缸 山泉水 硫磺 青草 茶 茶叶 酒 普洱茶 美酒 乌龙茶 烧酒 人参 桂花 米酒 可口可乐 珍珠奶茶 百事可乐 软糖 红酒 咖啡豆 雪碧 啤 姜味 希伯尼安斯 Conli Ins15TD Seille Nagaon 崎顶并 孔利耶 蒙蒂耶尔县 科洛涅县 Saulx 瓦朗赛县 Amance 特兰县 Geoirs 雷西县 Messei 葡萄酒 烈酒 糕点 冰激凌 鸡尾酒 佳酿 酒品 香槟酒 白酒 五粮液 食品饮料 贵州茅台 名酒 龙头 泸州老窖 茅台 白马股 龙头股 片仔癀 酒业 白酒业 水井坊 白马 黄金珠宝 家电行业 古井贡酒 黄酒 古越龙山 致癌 绍兴酒 甲酸 贝因美 塑化剂 方便面 酱油 咸鸭蛋 金针菇 威士忌 白兰地 老酒 调味料 香料 香辛料 虾酱 干货 食盐 明矾 油 卤 桐油 硫 盐巴 蔗糖 盐水 氯化钠 麦芽糖 土 锑 蛋黄 鱼露 麻油 豆瓣酱 川菜 蚝油 梅干菜 糖醋 豆腐干 燃面 菱角 猪腰 毛花 Guapore Rupununi 粘毛 科艾麻 辣椒油 麻酱 焖 桂皮 拌成 SE9 马雷科 阿提斯鲁夫尔谷 少脉 Plectotropis Lembron 峭腹 XC610PA 汽油 木炭 糖蜜 用油 重油 加水 矿物油 蓖麻油 腊肉 鱼片 竹叶 广式 大块 奶浆 柿叶 腊肠 咸鱼 蒜蓉 腊味 豆芽 牛肉干 火腿 肉丝 鱼汤 鱼丸 熏鱼 Tessy 隆热 轮盾 Royans Cubzac soils 奥坦 胸丽鱼 韦尔丹 sert Alappuzha ronde 弯果 沙莱县 烤鸭 涮羊肉 小笼包 小菜 粤菜 炸酱面 西餐 炒饭 烧味 小笼 水煮鱼 中餐 正宗 烧烤 露天 熟食 烤肉 小食 大排档 海鲜 街边 夜宵 早餐 小吃店 野餐 饮食店 烤串 凉面 食店 BBQ 料理 点心 家常菜 日本料理 泰式 薯片 朱古力 午餐肉 布丁 棒棒糖 蛋糕 松饼 面饼 饼 曲奇饼 盘子 生日蛋糕 爆米花 咖哩 三明治 葡萄干 蜜糖 粉条 千层饼 沙拉 照烧 卷饼 罗勒 椰枣 可可 卡达 沙律 莫拉 马兰 多拉 酪梨 慕斯 Chondrostoma 无茎 齿瓣 succeeded 棒果 刺粉 胡卢巴 瘤果 篦齿 尖萼 香简草 Tiruvanamalai 拉艾 细穗 毛序 沙冰 冰品 玉米片 麻辣烫 马卡龙 薯条 芝士 肉丸 皮蛋 酱汁 毛料 烤饼 猪排 火腿蛋 肉馅";
        String traffic = "汽车 电动车 电动汽车 商用车 丰田 乘用车 新车 重卡 汽车行业 宝马 皮卡 沃尔沃 小型车 广汽 北汽 保时捷 上海汽车 上海通用 国内汽车 整车厂 火车 大巴 公交车 铁轨 大巴车 火车站 电车 卡车 长途客车 坐火车 火车车厢 坐车 平板车 长途车 吊车 乘火车 长途 高速路 大卡车 一列 公交 出租车 公共汽车 私家车 的士 BRT 班车 大客车 面包车 机动车道 空车 无轨电车 电单车 车 小车 车子 轿车 越野车 小轿车 三轮车 摩托车 一辆车 吉普车 皮卡车 车种 大车 车车 自行车 脚踏车 单车 轮椅 骑车 轮滑 骑行 电瓶车 滑板 人力车 自由车 工程车 滑板车 手推车 脚踏船 轻轨 地铁 有轨电车 捷运系统 地下铁路 高速铁路 单轨 高铁 支线 延伸线 绿线 高架铁路 该线 轻铁 蓝线 交通系统 公交系统 单轨铁路 巴士 缆车 国铁 城铁 胶轮 铁路车辆 步行 徒步 绕行 南行 东行 行走 左转 西行 徒步走 右转 路程 跑步 北行 小径 晨练 绕圈 沿路 轨道交通 号线 地铁线 地铁站 一号线 二号线 地下铁道 四号线 船 船只 渔船 船队 艇 大船 商船 货船 运输船 货轮 小船 船上 拖船 驳船 客船 救生艇 海船 舰船 客轮 木船 渡轮 渡船 轮渡 游船 汽船 码头 邮轮 游轮 轮船 船班 交通船 竹筏 水翼船 内河 外港 龙舟 龙船 龙舟竞渡 竞渡 赛龙舟 龙舟赛 龙舟竞赛 舞狮 舞龙 锣鼓 龙灯 二龙 拔河 咸水歌 鲤鱼 水上 划船 醒狮 红船 奇石 独木舟 小舟 筏 舢舨 牛车 舢板 橡皮艇 江边 浮木 钓竿 湖上 原木 空中交通 民航 空管 管制中心 交通网络 空防 民用航空 机坪 物资供应 客货运输 无线电通信 CDGVAL 乌卢姆 隆圭永 K1077 Nashik 芒翁 瓦万 谢利谢 Nanded 飞机 直升机 客机 滑翔机 军用飞机 水上飞机 运输机 航机 航空器 战机 民航机 波音 舰载机 飞艇 无人机 货机 军机 机尾 三架 直升飞机 战斗机 装甲车 两架 UH 舰载 数架 载具 民用飞机 巡航导弹 低空 人造卫星 反潜机 反舰导弹 军用 长程 喷气式 反舰 ";
        String entertain = "玩 玩玩 玩儿 玩游戏 耍 跳舞 玩起 玩乐 爱玩 摆弄 游玩 解闷 好玩 唱歌 打游戏 打闹 把玩 作乐 打发 玩电脑 娱乐 综艺 影视娱乐 影音 数码 电视 影视 传媒 华谊兄弟 多媒体 消闲 爱奇艺 星梦 电玩 文化娱乐 文化传媒 ATV 跨媒体 天映 汇彩 嬉戏 玩耍 戏水 嬉闹 散步 嬉水 依偎 池塘 乘凉 野餐 徜徉 湖边 划船 撒欢 放风筝 席地而坐 林间 游戏 该游戏 格斗游戏 动作游戏 RPG 电子游戏 新游戏 手游 冒险游戏 小游戏 电脑游戏 手机游戏 MMORPG 单机游戏 本作 玩法 PS3 单人游戏 街机版 街机 电影 影片 经典电影 电视剧 本片 音乐剧 恐怖片 动画片 歌舞片 喜剧 该片 纪录片 舞台剧 喜剧片 动画电影 恐怖电影 此片 短片 爱情片 电影版 剧 连续剧 剧集 偶像剧 电视剧集 古装剧 影视剧 影集 新剧 韩剧 情景喜剧 此剧 武侠剧 本剧 该剧 系列剧 动漫 ACG 动漫画 Cosplay 原创 二次元 创意 同人 创意设计 COSPLAY 文化产业 文创 少儿 周边产品 儿童剧 插画 插图 原画 作画 绘本 漫画作品 漫画版 原案 小说作品 素描 鸟山明 CLAMP 原作者 画集 四格 封面设计 画册 手绘 绘师 美术设计 音乐 音乐创作 流行音乐 古典音乐 电子音乐 舞蹈 流行乐 摇滚音乐 摇滚 流行歌曲 爵士乐 声乐 舞曲 现代舞 音乐作品 饶舌 乡村音乐 说唱 管弦乐 电音 歌曲 曲目 新歌 经典歌曲 歌词 曲子 曲 名曲 英文歌曲 乐曲 歌 抒情歌曲 情歌 新曲 一曲 此曲 成名曲 老歌 相声 评书 曲艺 戏曲 京剧 昆曲 话剧 川剧 皮影戏 小品 越剧 中国戏曲 评剧 说书 昆剧 小戏 粤曲 民乐 闽剧 侯宝林 歌剧 芭蕾舞剧 剧作 戏剧 交响乐 经典作品 咏叹调 轻歌剧 名剧 舞剧 海顿 威尔第 喜歌剧 芭蕾 莫扎特 豫剧 歌仔戏 黄梅戏 民间艺术 潮剧 刘兰芳 评话 话本 弹词 秦腔 单弦 皮影 汉剧 足球 篮球 体育 足球联赛 足球运动 足球队 排球 网球 踢球 青年队 手球 国际足球 足球界 篮球联赛 橄榄球 冰球 U19 板球 U16 拳击 棒球 美式足球 高尔夫球 高球 田径 曲棍球 男子篮球 乒乓球 篮球比赛 斯诺克 铁人三项 篮球运动 击剑 球 进球 皮球 罚球 一球 头球 球门 好球 界外球 任意球 将球 射门 乌龙球 两球 三球 个球 远射 致胜球 头槌 半场 跑步 慢跑 爬山 溜冰 跳绳 骑车 锻炼身体 远足 踢足球 晨练 滑板 滑水 骑行 健步 骑马 绕圈 户外运动 划艇 慢速 运动 体育运动 思潮 文学运动 社会活动 妇女解放 工人运动 群众运动 全民运动 爱国运动 女权 体育锻炼 群众性 民主自由 青年运动 立宪运动 民族运动 和平主义 女权运动 社会变革 桌游 杜彬 反恐精英 Dota 移动游戏 泡泡堂 战棋 逍遥游 红白机 wp7 NDS 网龙 游戏类 MOBA AVG 小漠 Sangrur 玩具 玩偶 公仔 糖果 娃娃 宠物 零食 文具 泰迪熊 毛绒玩具 小精灵 积木 儿童玩具 人偶 玩具车 卡通人物 日常用品 洋娃娃 雪糕 按摩 理疗 调理 美容 洗头 淋浴 泡茶 推拿 洗浴 指压 热敷 穿刺 美发 按摩师 泡澡 眼部 理发 洗脸 塑形 泰式 泡脚 带水 伦氏 萨莱县 三须 Monton 圣埃卢瓦 莫勒伊县 凡塘 teaumeillant 芒翁 Juhel Beauvoisin 沙唐瓦县 Fousseret 库塞县 短果 沙莱县 雷西县 喝茶 饮茶 喝咖啡 品茶 喝酒 打牌 吃火锅 闲聊 请客 打麻将 进餐 下午茶 饭后 品茗 朋友家 闲谈 看戏 佐食 聚会 家庭聚会 宴会 派对 舞会 饭局 社交活动 生日会 聚餐 晚宴 结婚典礼 宴席 party 餐会 PARTY 举行宴会 演讲会 举办活动 聚会地点 守夜 婚礼 生日派对 酒会 时装秀 万圣节 婚宴 夜店 庆功宴 旅行 探险 旅程 旅途 自驾 自驾游 历险 短途旅行 出游 环游世界 漫游 背包客 徒步旅行 自助游 长途旅行 旅行者 环游 冒险 周游 壮游 表演 演出 现场表演 演奏 精彩表演 排练 演唱 表演者 歌舞 编舞 弹唱 芭蕾舞 舞者 舞姿 即兴 自弹自唱 歌唱 合唱 独唱 才艺 国标舞 歌谣 花儿 街舞 器乐 流行曲 红歌 rap 摄影 摄影家 美术 照相 摄像 绘画 艺术摄影 写生 水彩 版画 速写 平面设计 微距 纪实 视觉艺术 张照堂 摄影者 聊天 交谈 聊天室 聊 调情 通话 寒暄 发短信 相谈甚欢 相熟 对谈 听歌 虚拟现实 VR AR 体感 虚拟实境 三维 智能家居 互动式 头戴式 人工智能 人机 交互式 TNUMBERD 触屏 人工智慧 掌上电脑 Adreno200 锡尔弗 P8400";
        if(clothes.contains(type)){
            return "BO11";
        }else if(daily.contains(type)){
            return "BO3";
        }else if (education.contains(type)){
            return "BO14";
        }else if ((food.contains(type))){
            return "BO1";
        }else if (traffic.contains(type)){
            return "BO4";
        }else if (entertain.contains(type)){
            return "BO9";
        }else return null;
    }


    private HashMap<String,String> getPaymentType(int uid, String bookKeepingName, String type, String bookKeepingTypeName){
        List<BookKeeping> bookKeeping=bookMapper.selectByUidAndName(uid,bookKeepingName);
        HashMap<String,String> l=new HashMap<>();
        for (BookKeeping i: bookKeeping){
            String temp=bookMapper.selectBookkeepingTypeName(i.getBookkeeping_type_id());
            // 找到对应的模板类型的处理
            if (Objects.equals(temp, bookKeepingTypeName)){
                String ls=bookMapper.selectBookkeepingTypeList(i.getBookkeeping_type_id());
                String[] split=ls.split("-");
                for (String j: split){
                    //通过type 判断是支出还是收入 O为支出 I为收入
                    if (j.contains(type)){
                        l.put(j,bookMapper.selectBasicFundName(j));
                    }
                }
                // 从用户自定义款项中找是否存在，存在加入，不存在不做处理
                List<CustomFund> lcf=bookMapper.selectCustomFund(uid,i.getBookkeeping_type_id());
                if (lcf!=null && !lcf.isEmpty()){
                    for (CustomFund j:lcf){
                        if (j.getCustomed_fund_id().contains(type)){
                            l.put(j.getCustomed_fund_id(),j.getCustomed_fund_name());
                        }
                    }
                }
            }
        }
        return l;
    }


}
