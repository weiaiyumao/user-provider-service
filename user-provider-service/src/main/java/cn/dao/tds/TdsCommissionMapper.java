package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsCommission;


/**
 * : tds_commission
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsCommissionMapper extends IBaseDao<TdsCommission, Integer>{
	
  
	/**
	 * 佣金分页查询列表
	 * @param entity
	 * @return
	 */
	List<TdsCommission> pageTdsCommission(TdsCommission entity);
	
	/**
	 * 获取总数
	 * @param entity
	 * @return
	 */
	Integer queryCount(TdsCommission entity);
}
