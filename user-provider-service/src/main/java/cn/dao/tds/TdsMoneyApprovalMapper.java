package cn.dao.tds;

import org.apache.ibatis.annotations.Mapper;

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
	 * 审核操作
	 * @param id  列表id
	 * @param orderNumber  订单号
	 * @param approvalType  审核类型 ，入进账审核为1  出账为2 退款3
	 * @param approvalStatus  审核操作 0待审核  1已审核 3驳回  4到账 5线下开票 6 充账
	 * @return
	 */
	Integer approvalByUpStatus(TdsMoneyApproval entity);
	
  
}
