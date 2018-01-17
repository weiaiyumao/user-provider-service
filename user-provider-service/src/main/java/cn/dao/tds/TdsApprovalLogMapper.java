package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.view.TdsCustomerView;


/**
 * : tds_approval
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsApprovalLogMapper extends IBaseDao<TdsApprovalLog, Integer>{
	
	
	/**
	 * 客户审核列表分页查询
	 * @param auto
	 * @return
	 */
	List<TdsCustomerView> pageTdsApproval(TdsCustomerView view);
	
	/**
	 * 统计
	 * @param auto
	 * @return
	 */
	Integer queryCount(TdsCustomerView view);
	
	
  
}
