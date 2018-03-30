package cn.dao.tds;




import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsRole;


/**
 * : tds_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsRoleMapper extends IBaseDao<TdsRole, Integer>{
	
	
	List<TdsRole> pageByRole(@Param("roleName")String roleName,@Param("pageNumber")Integer pageNumber,@Param("numPerPage")Integer numPerPage);
	
	Integer queryCount(@Param("roleName")String roleName);
	
	String getRoleNameByUsreId(@Param("userId") Integer userId);
}
