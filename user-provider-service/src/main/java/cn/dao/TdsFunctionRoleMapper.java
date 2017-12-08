package cn.dao;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.TdsFunctionRole;


/**
 * : tds_function_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsFunctionRoleMapper{
	
    TdsFunctionRole loadById(Integer id);
	
	void insert(TdsFunctionRole entity);
	
	void deleteById(Integer id);
	
	void update(TdsFunctionRole entity);
}
