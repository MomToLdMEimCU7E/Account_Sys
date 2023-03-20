package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.AccountType;
import org.apache.ibatis.annotations.Select;


public interface AccountTypeMapper extends BaseMapper<AccountType> {

    @Select("select account_detail_type_name from account_details_type where account_detail_type_id = #{accountDetailId}")
    String getTypeName(Integer accountDetailId);
}
