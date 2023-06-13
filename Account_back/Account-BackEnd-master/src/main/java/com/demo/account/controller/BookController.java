package com.demo.account.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.demo.account.Po.CreateBookPo;
import com.demo.account.Po.InPo;
import com.demo.account.Po.PayPo;
import com.demo.account.Po.UpdateBookPo;
import com.demo.account.Vo.DiscernVo;
import com.demo.account.Vo.GetPaymentRankVo;
import com.demo.account.Vo.ReturnVo;
import com.demo.account.common.Result;
import com.demo.account.entity.*;
import com.demo.account.exception.BizException;
import com.demo.account.exception.ResultBody;
import com.demo.account.mapper.*;
import com.demo.account.service.BookService;
import com.demo.account.utils.ChineseNumToArabicNumUtil;
import com.demo.account.utils.IndexUtil;
import com.hankcs.hanlp.restful.HanLPClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/book")
public class BookController {
    private BookService bookService;


    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @Resource
    BookMapper bookMapper;
    @Resource
    AccountDetailsMapper accountDetailsMapper;
    @Resource
    IncomeMapper incomeMapper;
    @Resource
    PaymentMapper paymentMapper;
    @Resource
    IncomePaymentMapper incomePaymentMapper;

    /**
     * @param uid 用户id
     * @param bookKeepingName 账本名
     * @return  List<String> 表示支出款项类型的列表
     * 功能：在”记一笔”支出页面中可以根据账本的类型渲染出对应的款项类型
     */
    @RequestMapping(method = RequestMethod.GET,value = "/out")
    ResultBody getExpenditureType(int uid, String bookKeepingName){
        List<String> list=bookService.bookkeepingTypeNamesFind(uid,bookKeepingName);
        if (list.size()==0) throw new BizException("-1","账本不存在");
        String bookKeepingTypeName=list.get(0);
        HashMap<String,String> map=bookService.getExpenditureType(uid,bookKeepingName,bookKeepingTypeName);
        return ResultBody.success(map);
    }

    /**
     * @param uid 用户id
     * @param bookKeepingName 账本名
     * @return List<String> 表示收入款项类型的列表
     *功能：在”记一笔”收入页面中可以根据账本的类型渲染出对应的款项类型
     */
    @RequestMapping(method = RequestMethod.GET,value = "/in")
    ResultBody getIncomeType(int uid,String bookKeepingName){
        List<String> list=bookService.bookkeepingTypeNamesFind(uid,bookKeepingName);
        if (list.size()==0) throw new BizException("-1","账本不存在");
        String bookKeepingTypeName=list.get(0);
        HashMap<String,String> map=bookService.getIncomeType(uid,bookKeepingName,bookKeepingTypeName);
        return ResultBody.success(map);
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账本名
     * @param json 支出款项的json数据
     * @return "success"
     * 功能：修改账本模板的支出项目,用于”记一笔”支出的设置页面
     */
    @RequestMapping(method = RequestMethod.POST,value = "/setting_add_out")
    ResultBody outSettingChange(int uid, String bookKeepingName,@RequestBody JSONObject json){
        List<String> list=bookService.bookkeepingTypeNamesFind(uid,bookKeepingName);
        if (list.size()==0) throw new BizException("-1","账本不存在");
        String bookKeepingTypeName=list.get(0);
        bookService.outSettingChange(uid, bookKeepingName, bookKeepingTypeName, json);
        return ResultBody.success("设置成功");
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账本名
     * @param json 收入款项的json数据
     * @return "success"
     * 功能：修改账本模板的收入项目,用于”记一笔”收入的设置页面
     */
    @RequestMapping(method = RequestMethod.POST,value = "/setting_add_in")
    ResultBody inSettingChange(int uid, String bookKeepingName,@RequestBody JSONObject json){
        List<String> list=bookService.bookkeepingTypeNamesFind(uid,bookKeepingName);
        if (list.size()==0) throw new BizException("-1","账本不存在");
        String bookKeepingTypeName=list.get(0);
        bookService.inSettingChange(uid, bookKeepingName, bookKeepingTypeName, json);
        return ResultBody.success("设置成功");
    }

    /**
     *
     * @return 基本款项表，用于名称和编号的对照
     */
    @RequestMapping(method = RequestMethod.GET,value = "/basic")
    ResultBody getAll(){
        List<BasicFund> list=bookService.selectAllBasicFund();
        return ResultBody.success(list);
    }

    /**
     *
     * @return "success"
     * 功能：添加一笔支出
     */
    @RequestMapping(method = RequestMethod.POST,value = "/out")
    ResultBody bookkeepingPayment(PayPo payPo){

        bookService.bookkeepingPayment(payPo);
        return ResultBody.success("添加成功");
    }


    /**
     *
     * @return "success"
     * 功能：添加一笔收入
     */
    @RequestMapping(method = RequestMethod.POST,value = "/in")
    ResultBody bookkeepingIncome(InPo inPo){

        bookService.bookkeepingIncome(inPo);
        return ResultBody.success("添加成功");
    }


    /**
     * @return "success"
     *功能：添加一个账本
     */
    @RequestMapping(method = RequestMethod.POST,value = "/new")
    ResultBody bookkeepingAdd(int uid,String bookKeepingName,String bookKeepingCover,Integer extraMember1,Integer extraMember2,
                          String template,String bookKeepingTypeName){
        bookService.bookkeepingAdd(uid, bookKeepingName, bookKeepingCover, extraMember1, extraMember2,template,bookKeepingTypeName);
        return ResultBody.success("添加成功");
    }

    /**
     * @return "success"
     *功能：修改账本设置
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/change")
    ResultBody bookkeepingChange(int uid,String bookKeepingName,String bookKeepingNameNew,String bookKeepingCover,Integer extraMember1,Integer extraMember2){
        bookService.bookkeepingChange(uid,bookKeepingName,bookKeepingNameNew,bookKeepingCover,extraMember1,extraMember2);
        return ResultBody.success("修改成功");
    }

    /**
     * @return List<String>
     *功能：获取账本对应模板的名字
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getTypeNames")
    ResultBody bookkeepingTypeNamesFind(int uid,String bookKeepingName){
        List<String> list=bookService.bookkeepingTypeNamesFind(uid,bookKeepingName);
        return  ResultBody.success(list);
    }

    /**
     *
     * @param uid
     * @return 账本名称的列表
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getBookNames")
    ResultBody selectUserBookkeeping(int uid){
        return ResultBody.success(bookService.selectUserBookkeeping(uid));
    }

    /**
     *
     * @return List<Income'sJSONObject>
     * 功能：获取对于账本的收入明细
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getIncome")
    ResultBody selectBookkeepingIncome(int uid, String bookKeepingName){
        return ResultBody.success(bookService.selectBookkeepingIncome(uid, bookKeepingName));
    }

    /**
     *
     * @return List<Payment'sJSONObject>
     * 功能：获取对于账本的支出明细
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getPayment")
        ResultBody selectBookkeepingPayment(int uid, String bookKeepingName){
        return ResultBody.success(bookService.selectBookkeepingPayment(uid, bookKeepingName));
    }

    /**
     *
     * @param nowTime 用于查询一周账单,提供查询时的时间,在按月/年查询时可为空
     * @param startTime 用于查询一个月/年的账单,提供该月/年开始时间,在按周查询时可为空
     * @param endTime 用于查询一个月/年的账单,提供该月/年结束时间,在按周查询时可为空
     * @param getWhat 用于区别你查询的是周/月/年 提供的输入为{week,month,year}
     * @return HashMap<String,Integer> or HashMap<Integer,Integer>
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getPeriodIncome")
    ResultBody getPeriodIncome(int uid, String bookKeepingName,
                               String nowTime,String startTime,String endTime,String getWhat){
        try{
            switch (getWhat){
                case "week":
                    return ResultBody.success(bookService.countWeekIncome(uid, bookKeepingName, nowTime));
                case "month":
                    return ResultBody.success(bookService.countMonthIncome(uid, bookKeepingName, startTime, endTime));
                case "year":
                    return ResultBody.success(bookService.countYearIncome(uid, bookKeepingName, startTime, endTime));
                default:
                    return ResultBody.error("getWhat参数错误 提供的输入应为week/month/year}");
            }
        }catch (Exception e){
          throw new BizException("-1","日期格式不对,应为yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     *
     * @param nowTime 用于查询一周账单,提供查询时的时间,在按月/年查询时可为空
     * @param startTime 用于查询一个月/年的账单,提供该月/年开始时间,在按周查询时可为空
     * @param endTime 用于查询一个月/年的账单,提供该月/年结束时间,在按周查询时可为空
     * @param getWhat 用于区别你查询的是周/月/年 提供的输入为{week,month,year}
     * @return HashMap<String,Integer> or HashMap<Integer,Integer>
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getPeriodPayment")
    ResultBody getPeriodPayment(int uid, String bookKeepingName,
                               String nowTime,String startTime,String endTime,String getWhat){
        try{
            switch (getWhat){
                case "week":
                    return ResultBody.success(bookService.countWeekPayment(uid, bookKeepingName, nowTime));
                case "month":
                    return ResultBody.success(bookService.countMonthPayment(uid, bookKeepingName, startTime, endTime));
                case "year":
                    return ResultBody.success(bookService.countYearPayment(uid, bookKeepingName, startTime, endTime));
                default:
                    return ResultBody.error("getWhat参数错误 提供的输入应为week/month/year}");
            }
        }catch (Exception e){
            throw new BizException("-1","日期格式不对,应为yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账簿名称
     * @param month 月份
     * @return 功能是获取账簿指定月份的预算
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getBudget1")
    ResultBody getBookkeepingBudget(int uid, String bookKeepingName,String month){
        return ResultBody.success(bookService.getBookkeepingBudget(uid, bookKeepingName,month));
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账簿名称
     * @param month 月份
     * @param budget 预算
     * @return 功能是改变账簿某月的预算
     */
    @RequestMapping(method = RequestMethod.PUT,value = "/changeBudget")
    ResultBody changeBudget(int uid,String bookKeepingName,String month,String budget){
        bookService.changeBookkeepingBudget(uid, bookKeepingName, month, budget);
        return ResultBody.success("更新成功");
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账簿名称
     * @param classification 类别
     * @return 功能是根据几个大类查询,支出收入记录
     */
    @RequestMapping(method = RequestMethod.GET,value = "/getPartRecord")
    ResultBody getPartRecordOfIncomeAndPayment(int uid,String bookKeepingName,String classification){
        List<JSONObject> allPayment=bookService.selectBookkeepingPayment(uid,bookKeepingName);
        List<JSONObject> allIncome=bookService.selectBookkeepingIncome(uid, bookKeepingName);
        return ResultBody.success(bookService.getPartRecordOfIncomeAndPayment(uid,bookKeepingName,classification,allPayment,allIncome));
    }

    /**
     *
     * @param uid 用户id
     * @param bookKeepingName 账簿名称
     * @return 功能是删除指定账簿
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteBookkeeping")
    ResultBody deleteBookkeeping(int uid,String bookKeepingName){
        bookService.deleteBookkeeping(uid, bookKeepingName);
        return ResultBody.success("删除成功");
    }

    /**
     *
     * @param customFundId 自定义款项ID
     * @return 功能是删除自定义款项
     */
    @RequestMapping(method = RequestMethod.DELETE,value = "/deleteCustomFund")
    ResultBody deleteCustomFund(String customFundId){
        bookService.deleteCustomFund(customFundId);
        return ResultBody.success("删除成功");
    }

    @PutMapping("/updateIncome")
    @ResponseBody
    Result<?> updateIncome(@RequestBody Income income){
        incomeMapper.updateById(income);
        return Result.success();
    }

    @PutMapping("/updatePayment")
    @ResponseBody
    Result<?> updatePayment(@RequestBody Payment payment){
        paymentMapper.updateById(payment);
        return Result.success();
    }

    @GetMapping("/getBookList")
    @ResponseBody
    Result<?> getBookList(@RequestParam Integer uid){
        return Result.success(bookService.getBookList(uid));
    }

    @PostMapping("/updateBook")
    @ResponseBody
    Result<?> updateBook(@RequestBody UpdateBookPo updateBookPo){
        return Result.success(bookService.updateBook(updateBookPo));
    }

    @GetMapping("/getBudget")
    @ResponseBody
    Result<?> getBudget(@RequestParam Integer bookkeepingId){
        return bookService.getBudget(bookkeepingId);
    }

    @PostMapping("/createBook")
    @ResponseBody
    Result<?> createBook(@RequestBody CreateBookPo createBookPo){
        return Result.success(bookService.createBook(createBookPo));
    }

    @PostMapping("/detectBudget")
    @ResponseBody
    Result<?> detectBudget(@RequestParam Integer bookkeepingId){
        return Result.success(bookService.detectBudget(bookkeepingId));
    }

    @GetMapping("/getFundIcon")
    @ResponseBody
    Result<?> getFundIcon(@RequestParam Integer bookkeepingTypeId){
        return Result.success(bookService.getFundIcon(bookkeepingTypeId));
    }

    @DeleteMapping("/deleteBook")
    @ResponseBody
    Result<?> deleteBook(@RequestParam Integer bookkeepingId){

        return Result.success(bookMapper.deleteBook(bookkeepingId));
    }

    @GetMapping("/getMonthAmount")
    @ResponseBody
    Result<?> getMonthAmount(@RequestParam String year, @RequestParam String month, @RequestParam Integer bookkeepingId){
        return Result.success(bookService.getMonthAmount(year, month, bookkeepingId));
    }

//    @GetMapping("/getIncomeList")
//    @ResponseBody
//    Result<?> getIncomeList(@RequestParam Integer bookkeepingId){
//        return Result.success(bookService.getIncomeList(bookkeepingId));
//    }

    @GetMapping("/getPaymentList")
    @ResponseBody
    Result<?> getPaymentList(@RequestParam Integer bookkeepingId){
        return Result.success(bookService.getPaymentList(bookkeepingId));
    }

    @PostMapping("/createIncome")
    @ResponseBody
    Result<?> createIncome(@RequestBody Income income){
        if (income.getAccountDetailId() != null){
            AccountDetails accountDetails = accountDetailsMapper.getAccountDetails(income.getAccountDetailId());
            accountDetails.setBalance(String.valueOf(Double.parseDouble(accountDetails.getBalance()) + Double.parseDouble(income.getAmount())));
        }

        return Result.success(incomeMapper.insert(income));
    }

    @PostMapping("/createPayment")
    @ResponseBody
    Result<?> createPayment(@RequestBody Payment payment){
        if (payment.getAccountDetailId() != null){
            AccountDetails accountDetails = accountDetailsMapper.getAccountDetails(payment.getAccountDetailId());
            accountDetails.setBalance(String.valueOf(Double.parseDouble(accountDetails.getBalance()) - Double.parseDouble(payment.getAmount())));
        }

        return Result.success(paymentMapper.insert(payment));
    }

    @GetMapping("/getMonthPayment")
    @ResponseBody
    Result<?> getMonthPayment(@RequestParam Integer bookkeepingId, @RequestParam String year, @RequestParam String month){
        return Result.success(bookService.getMonthPayment(bookkeepingId, year, month));

    }

    @GetMapping("/getPaymentRank")
    @ResponseBody
    Result<?> getPaymentRank(@RequestParam Integer bookkeepingId, @RequestParam String year, @RequestParam String month){
        String sql =year + "-" + month;
        List<GetPaymentRankVo> getPaymentRankVoList = bookMapper.getPaymentRank(sql,bookkeepingId);

        //getPaymentRankVoList.remove(getPaymentRankVoList.size() - 1);
        for (int i = 0; i < getPaymentRankVoList.size(); i++) {
            getPaymentRankVoList.get(i).setFundName(bookMapper.getFundName(getPaymentRankVoList.get(i).getFundName()));
        }
        return Result.success(getPaymentRankVoList);
    }

    @GetMapping("/getFundPaymentList")
    @ResponseBody
    Result<?> getFundPaymentList(@RequestParam Integer bookkeepingId, @RequestParam String fundId){
        char type = fundId.charAt(1);

        if(type == 'I'){
            return Result.success(incomePaymentMapper.getFundIncomeList(bookkeepingId, fundId));
        } else if (type == 'O'){
            return Result.success(incomePaymentMapper.getFundPaymentList(bookkeepingId, fundId));
        }else return Result.error("01", "查询失败");

//        switch (type){
//            case 'I':return Result.success(incomePaymentMapper.getFundIncomeList(bookkeepingId, fundId));break;
//            case 'O':return Result.success(incomePaymentMapper.getFundPaymentList(bookkeepingId, fundId));break;
//            default:return Result.error("01", "查询失败");
//        }

    }

    @PostMapping("/test")
    @ResponseBody
    Result<?> test(@RequestParam String str){
        HanLPClient client = new HanLPClient("https://hanlp.hankcs.com/api", null); // Replace null with your auth

        Map<String, List> map = new HashMap<>();
        try {
            map = client.parse(str);
         
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<List<List>> list = new ArrayList<>();
        List<List<String>> words = new ArrayList<>();

        Iterator<Map.Entry<String,List>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,List> entry = iterator.next();
            if(entry.getKey().equals("tok/fine")){
                words = entry.getValue();
            }
            if(entry.getKey().equals("ner/ontonotes")){
                list = entry.getValue();
                break;
            }
        }

        List<List> amountList;
        amountList = list.get(0);
        String fundId = null;
        String amount = null;
        String date = null;
        String year = null, month = null, day = null;
        if (words.size() > 0){
            for (int i = 0; i < words.get(0).size(); i++) {
                fundId = bookService.discern(words.get(0).get(i));
                if (!StringUtils.isEmpty(fundId)) break;
            }
        }


        for (int i = 0; i < amountList.size(); i++) {
            if(amountList.get(i).get(1).equals("MONEY")){

                if (amountList.get(i).get(0).toString().endsWith("元") || amountList.get(i).get(0).toString().endsWith("块")){
                    amount = amountList.get(i).get(0).toString().substring(0,amountList.get(i).get(0).toString().length() - 1);
                }else if (amountList.get(i).get(0).toString().endsWith("块钱")){
                    amount = amountList.get(i).get(0).toString().substring(0,amountList.get(i).get(0).toString().length() - 2);
                }
                if(!StringUtils.isEmpty(amount)){
                    if (!StringUtils.isNumeric(amount)) {
                        amount = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(amount));
                    }
                }

            }else if (amountList.get(i).get(1).equals("DATE")){
                String dateString = amountList.get(i).get(0).toString();

                if (dateString.endsWith("号") || dateString.endsWith("日")){
                    dateString = dateString.substring(0,dateString.length() - 1);
                    Integer yueIndex = dateString.lastIndexOf("月");
                    Integer nianIndex = dateString.lastIndexOf("年");
                    Calendar now = Calendar.getInstance();

                    if (nianIndex == -1){
                        year = String.valueOf(now.get(Calendar.YEAR));
                        if (yueIndex == -1){//既没有年也没有月，例如 28号
                            month = String.valueOf(now.get(Calendar.MONTH));
                            day = dateString;
                        } else { //没有年，有月，例如12月28号
                            day = dateString.substring(yueIndex + 1);
                            month = dateString.substring(0,yueIndex);
                        }
                    } else {//年月日齐全，2023年12月28号
                        year = dateString.substring(0, nianIndex);
                        month = dateString.substring(nianIndex + 1, yueIndex);
                        day = dateString.substring(yueIndex + 1);
                    }

                }

                if (!StringUtils.isNumeric(year)){
                    year = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(year));
                }
                if (!StringUtils.isNumeric(month)){
                    month = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(month));
                }
                if (!StringUtils.isNumeric(day)){
                    day = String.valueOf(ChineseNumToArabicNumUtil.chineseNumToArabicNum(day));
                }

                date = year + "-" + month + "-" + day;
            }
        }

        DiscernVo discernVo = new DiscernVo(date, amount,fundId, year, month, day);
        return Result.success(discernVo);
    }

    @PostMapping("/train")
    @ResponseBody
    Result<?> train(MultipartFile file) throws Exception{
        // 火车票识别
        String url = "https://api.textin.com/robot/v1.0/api/train_ticket";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-app-id
        // 示例代码中 x-ti-app-id 非真实数据

        String appId = "a284096f26dca94b3c0780c4d536c476";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-secret-code
        // 示例代码中 x-ti-secret-code 非真实数据
        String secretCode = "c39a8795a75d92d437498dd64b62cf83";
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";

        if (file.isEmpty()){
            return Result.error("01", "fail");
        }

        String originFileName = file.getOriginalFilename();

        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                "\\src\\main\\resources\\static\\images\\";
        String path = pre + fileName;
        try {
            file.transferTo(new File(path));

        } catch (IOException e){
            e.printStackTrace();
        }


        try {
            byte[] imgData = readfile(path); // image
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            out = new DataOutputStream(conn.getOutputStream());
            out.write(imgData);
            out.flush();
            out.close();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        String res =    result.replaceAll("\"", "");
        String price = null;
        String date = null;
        String year = null, month = null, day = null;

        price = IndexUtil.getString(res, "price");
        date = IndexUtil.getString(res, "乘车时间");

        String dateString = date.substring(0, date.indexOf(" "));

        String[] array = dateString.split("-");
        year = array[0];
        month = array[1];
        day = array[2];


        return Result.success(new DiscernVo(date, price, "BO4", year, month, day));
//        res = " [\n" + res + "]";
//        JSONArray jsonArray = JSONArray.parseArray(res);
//        ReturnVo returnVo = jsonArray.getObject(0, ReturnVo.class);
//
//        System.out.println(res);
//        return Result.success(res);

//        ReturnVo returnVo = (ReturnVo) jsonArray.get(0);
//
//        String price = null;
//        String date = null;
//        String year = null, month = null, day = null;
//        for (int i = 0; i < returnVo.getResultVo().getItemLists().size(); i++) {
//            if (returnVo.getResultVo().getItemLists().get(i).getKey().equals("price")){
//                price = returnVo.getResultVo().getItemLists().get(i).getValue();
//            }
//            if (returnVo.getResultVo().getItemLists().get(i).getKey().equals("departure_date")){
//                date = returnVo.getResultVo().getItemLists().get(i).getValue();
//            }
//        }
////        date = date.substring(0,10);
//        String[] array = date.split("-");
//        year = array[0];
//        month = array[1];
//        day = array[2];
//
//        DiscernVo discernVo = new DiscernVo(date, price, "BO4", year, month, day);
//        return Result.success(discernVo);
    }

    @PostMapping("/receipt")
    @ResponseBody
    Result<?> receipt(MultipartFile file) throws Exception{
        // 小票识别
        String url = "https://api.textin.com/robot/v1.0/api/receipt";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-app-id
        // 示例代码中 x-ti-app-id 非真实数据

        String appId = "a284096f26dca94b3c0780c4d536c476";
        // 请登录后前往 “工作台-账号设置-开发者信息” 查看 x-ti-secret-code
        // 示例代码中 x-ti-secret-code 非真实数据
        String secretCode = "c39a8795a75d92d437498dd64b62cf83";
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";

        if (file.isEmpty()){
            return Result.error("01", "fail");
        }

        String originFileName = file.getOriginalFilename();

        String ext = originFileName.substring(originFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString().replace("-","");
        String fileName = uuid + ext;
        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String pre = applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() +
                "\\src\\main\\resources\\static\\images\\";
        String path = pre + fileName;
        try {
            file.transferTo(new File(path));

        } catch (IOException e){
            e.printStackTrace();
        }


        try {
            byte[] imgData = readfile(path); // image
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type", "application/octet-stream");
            conn.setRequestProperty("x-ti-app-id", appId);
            conn.setRequestProperty("x-ti-secret-code", secretCode);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST"); // 设置请求方式
            out = new DataOutputStream(conn.getOutputStream());
            out.write(imgData);
            out.flush();
            out.close();
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        String res = result.replaceAll("\"", "");
        String price = null;
        String date = null;
        String year = null, month = null, day = null;
        price = IndexUtil.getString(res, "money");
        date = IndexUtil.getString(res,"date");
        Calendar now = Calendar.getInstance();
        year = String.valueOf(now.get(Calendar.YEAR));

        String dateString = date.substring(0,date.indexOf(" "));
        String[] array = dateString.split("-");
        month = array[0];
        day = array[1];
        return Result.success(new DiscernVo(date, price, "BO1", year, month, day));



//        res = " [\n" + res + "]";
//        JSONArray jsonArray = JSONArray.parseArray(res);
//
//
//        return Result.success(jsonArray);
    }

    public static byte[] readfile(String path)
    {
        String imgFile = path;
        InputStream in = null;
        byte[] data = null;
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
