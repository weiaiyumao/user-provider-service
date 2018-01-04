package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.view.TdsCustomerView;
import main.java.cn.domain.page.PageAuto;


/**
 * : tds_approval
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsApprovalLogMapper extends IBaseDao<TdsApprovalLog, Integer>{
	
	
	List<TdsCustomerView> pageTdsApproval(PageAuto auto);
	
	Integer queryCount(PageAuto auto);
	
	
  
}
