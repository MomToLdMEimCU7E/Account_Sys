package com.demo.account.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.demo.account.Vo.BalanceVo;
import com.demo.account.entity.AccountDetails;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AccountDetailsMapper extends BaseMapper<AccountDetails> {
    @Select("select account_detail_id, balance from account_details where uid = #{uid}")
    List<BalanceVo> getBalanceList(Integer uid);

    @Select("select sum(balance) from account_details where account_detail_type_id in (1,2,3,4,7,8,9,10,11,13) and uid = #{uid}")
    Integer getAssets(Integer uid);

    @Select("select sum(balance) from account_details where account_detail_type_id in (5,6,12) and uid = #{uid}")
    Integer getDebts(Integer uid);

    @Select("select account_detail_Type_id from account_details where account_detail_id = #{accountDetailId}")
    Integer getDetailTypeId(Integer accountDetailId);


}
