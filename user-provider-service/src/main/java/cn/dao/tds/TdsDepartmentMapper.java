package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.tds.TdsDepartment;
import cn.entity.tds.TdsFunction;
import cn.entity.tds.view.UserRoleDepartmentView;



@Mapper
public interface TdsDepartmentMapper extends IBaseDao<TdsDepartment, Integer>{
	
	
	/**
	 * 根据条件查询用户信息权限，角色，部门
	 * @param entity
	 * @return
	 */
	List<UserRoleDepartmentView> pageUserRoleDepartmentView(UserRoleDepartmentView pageDto);
	
	/**
	 * 获取总数
	 * @return
	 */
	Integer queryCount(UserRoleDepartmentView pageDto);
	
/*	
	*//**
	 * 根据userId 加载权限
	 * @param userid
	 * @return
	 *//*
	List<TdsFunction> funByuserId(Integer userId);
	*/
	
	/**
	 * 根据用户id获取对应部门
	 * @param userId
	 * @return
	 */
	String selectDepartmentRoleByUserId(Integer userId);
	
}
