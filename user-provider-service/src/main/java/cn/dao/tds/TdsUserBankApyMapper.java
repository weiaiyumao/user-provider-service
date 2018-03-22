package cn.dao.tds;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsUserBankApy;


/**
 * : tds_user_bank_apy
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserBankApyMapper extends IBaseDao<TdsUserBankApy, Integer>{
	
  
	/**
	 * 根据用户id获取用户绑定信息
	 * @param userId
	 * @return
	 */
	TdsUserBankApy loadByUserId(Integer userId);
	
	/**
	 * 根据用户id修改
	 * @param userId
	 */
	void updateByUserId(TdsUserBankApy entity);
}
