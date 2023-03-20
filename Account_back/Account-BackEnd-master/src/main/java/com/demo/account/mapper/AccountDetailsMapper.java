package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.Vo.BalanceVo;
import com.demo.account.entity.AccountDetails;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AccountDetailsMapper extends BaseMapper<AccountDetails> {
    @Select("select account_detail_id, balance from account_details where account_id = #{accountId}")
    List<BalanceVo> getBalanceList(Integer accountId);


}
