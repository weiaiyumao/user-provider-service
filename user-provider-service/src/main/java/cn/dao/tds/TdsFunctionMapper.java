package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.tds.TdsFunction;


/**
 * : tds_function
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsFunctionMapper extends IBaseDao<TdsFunction, Integer>{
	
	/**
	 * 模块加载
	 * @param userid
	 * @return
	 */
	List<TdsFunction> moduleLoadingByUsreId(Integer userid);
	
	
}
