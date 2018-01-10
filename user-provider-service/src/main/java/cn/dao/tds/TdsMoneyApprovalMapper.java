package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsMoneyApproval;


/**
 * : tds_money_approval
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsMoneyApprovalMapper extends IBaseDao<TdsMoneyApproval, Integer>{
	
	
	/**
	 * 进账 出账 退款 分页 条件查询
	 * @param entity
	 * @return
	 */
	List<TdsMoneyApproval> pageMoneyApprovalAll(TdsMoneyApproval entity);
	
	/**
	 * 获取总数
	 * @param entity
	 * @param selected
	 * @return
	 */
	Integer queryCount(TdsMoneyApproval entity);
	
	
	/**
	 * 根据订单查询是否符合退款要求
	 * @param orderNumber
	 * @param userId
	 * @return
	 */
	TdsMoneyApproval queryByOrderByUser(@Param("orderNumber")String orderNumber,@Param("userId")Integer userId);
	
}
