package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
	 * 根据id 修改角色与用户的信息 
	 * @param tur
	 * @return
	 */
	Integer upStatusById(TdsUserRole tur);
	
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
	
}
