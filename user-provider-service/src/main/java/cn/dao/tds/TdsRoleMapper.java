package cn.dao.tds;



import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsFunction;
import cn.entity.tds.TdsRole;


/**
 * : tds_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsRoleMapper extends IBaseDao<TdsRole, Integer>{
	
	/**
	 * 根据角色查询权限功能
	 * @param roleId
	 * @return
	 */
	List<TdsFunction> queryfunByRoleId(@Param("roleId") Integer roleId);
	
	
	
	
}
