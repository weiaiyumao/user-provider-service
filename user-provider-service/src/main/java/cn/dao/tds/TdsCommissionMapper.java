package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	
	
	/**
	 * 查询剩余（佣金）余额
	 * @return
	 */
	String queryBySumMoney(@Param("userId") Integer userId);
	
	
	/**
	 * 更新状态
	 * @param entity
	 * @return
	 */
	Integer upCommStatus(TdsCommission entity);
	
}
