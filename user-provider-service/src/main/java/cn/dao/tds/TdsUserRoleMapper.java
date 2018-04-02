package cn.dao.tds;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUserRole;


/**
 * : tds_user_role
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserRoleMapper extends IBaseDao<TdsUserRole, Integer>{
	
	
	
	/**
	 * 根据用户id 禁用，解禁
	 * @param tur
	 * @return
	 */
	Integer upStatusById(@Param("id")Integer id,@Param("status")String status);
	
	/**
	 * 批量插入
	 * @param checkboxRole
	 * @param tdsUserRole
	 * @return
	 */
	Integer saveRoleByUser(@Param("arr") List<TdsUserRole> checkboxRole);
	
	/**
	 * 账号配置列表
	 * @param auto
	 * @return
	 */
    List<TdsUserRole> queryRoleIsStatus(TdsUserRole auto);
    
    
    /**
     * 获取总数
     */
	Integer queryCount(TdsUserRole auto);
	
	
	/**
	 * 根据用户id删除角色
	 * @param userId
	 * @return
	 */
	Integer deleteByUserId(@Param("userId")Integer userId);
	
	
	
	/**
	 * 根据用户id查询对应的角色
	 * @param userId
	 * @return
	 */
	Map<String, Object> getRoleNameByUsreId(@Param("userId") Integer userId);
	
	
}
