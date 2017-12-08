package cn.dao;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.TdsUserRole;


/**
 * : tds_user_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserRoleMapper{
	
    TdsUserRole loadById(Integer id);
	
	void insert(TdsUserRole entity);
	
	void deleteById(Integer id);
	
	void update(TdsUserRole entity);
}
