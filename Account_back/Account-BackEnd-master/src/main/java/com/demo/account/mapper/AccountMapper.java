package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.Vo.AccountListVo;
import com.demo.account.entity.Account;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AccountMapper extends BaseMapper<Account> {
    @Select("select account_id, account_type_id, account_name, comment from account where uid = #{uid}")
    List<AccountListVo> getAccountList(Integer uid);
}
