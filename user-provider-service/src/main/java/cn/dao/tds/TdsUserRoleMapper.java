package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserRole;
import main.java.cn.domain.page.PageAuto;


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
    List<PageAuto> queryRoleIsStatus(PageAuto auto);
    
    
    /**
     * 获取总数
     */
	Integer queryCount(PageAuto auto);
	
	
	/**
	 * 根据用户id删除角色
	 * @param userId
	 * @return
	 */
	Integer deleteByUserId(@Param("userId")Integer userId);
	
	
	/**
	 * 根据角色id or 联系人 获取用户信息
	 * @param roleId
	 * @return
	 */
	List<TdsUser> queryUserByRoleName(@Param("roleName")String roleName,@Param("contact")String contact);
}
