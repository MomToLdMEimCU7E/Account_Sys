package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.entity.AccountDetailsType;
import org.apache.ibatis.annotations.Select;

public interface AccountDetailsTypeMapper extends BaseMapper<AccountDetailsType> {
    @Select("select account_detail_type_name from account_details_type where account_detail_type_id = #{accountDetailTypeId}")
    String getTypeName(Integer accountDetailTypeId);

    @Select("select icon from account_details_type where account_detail_type_id = #{accountDetailTypeId}")
    String getIcon(Integer accountDetailTypeId);
}
