package cn.dao.tds;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUserDiscount;


/**
 * :个人折扣价
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserDiscountMapper extends IBaseDao<TdsUserDiscount, Integer>{
	
  
	
	/**
	 * 根据充值的金额获取折扣
	 * @param userId  用户折扣id
	 * @param startMoney  充值金额
	 * @return
	 */
	TdsUserDiscount getDiscount(@Param("userId")Integer userId,@Param("startMoney")String startMoney);
}
