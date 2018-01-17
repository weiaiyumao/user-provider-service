package cn.dao.tds;

import java.util.Date;
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
public interface TdsMoneyApprovalGoMapper extends IBaseDao<TdsMoneyApproval, Integer>{
	
	
	/**
	 * 进账 分页 条件查询
	 * @param entity
	 * @return
	 */
	List<TdsMoneyApproval> pageMoneyApprovalGo(TdsMoneyApproval entity);
	
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
	List<TdsMoneyApproval> queryByOrderByUser(@Param("userId")Integer userId,@Param("pnameId")Integer pnameId);
	
	
	String isStatus(@Param("userId")Integer userId,@Param("orderNumber")String orderNumber);
	
	
	/**
	 * 状态修改
	 */
     Integer upApprovalStatus(@Param("approvalStatus")String approvalStatus,@Param("id")Integer id,@Param("arriveTime")Date arriveTime);
     
     /**
      * 线下开票
      * @param id
      * @param billing
      * @return
      */
     Integer upBilling(@Param("id")Integer id,@Param("billing")String billing);
}
