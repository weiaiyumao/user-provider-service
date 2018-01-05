package cn.dao.tds;

import java.util.List;

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
	
	
}
