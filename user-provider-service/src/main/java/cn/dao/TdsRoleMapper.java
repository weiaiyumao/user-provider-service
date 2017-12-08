package cn.dao;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.TdsRole;


/**
 * : tds_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsRoleMapper{
	
    TdsRole loadById(Integer id);
	
	void insert(TdsRole entity);
	
	void deleteById(Integer id);
	
	void update(TdsRole entity);
}
