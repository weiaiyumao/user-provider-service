package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsMoneyApprovalBack;


/**
 * : 退款审核mapper接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsMoneyApprovalBackMapper extends IBaseDao<TdsMoneyApprovalBack, Integer>{
	
	
	/**
	 * 退款 分页 条件查询
	 * @param entity
	 * @return
	 */
	List<TdsMoneyApprovalBack> pageApprovalBack(TdsMoneyApprovalBack entity);
	
	
	

	/**
	 * 获取总数
	 * @param entity
	 * @param selected
	 * @return
	 */
	Integer queryCount(TdsMoneyApprovalBack entity);
	
	
	/**
	 * 退款审核状态更新改变
	 * @param id
	 * @param approvalStatus
	 * @return
	 */
	Integer upBackStatus(@Param("id")Integer id,@Param("approvalStatus")String approvalStatus);
  
}
