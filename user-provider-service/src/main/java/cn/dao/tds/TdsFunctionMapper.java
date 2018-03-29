package cn.dao.tds;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsFunction;
import cn.entity.tds.view.TdsFunMoView;


/**
 * : tds_function
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsFunctionMapper extends IBaseDao<TdsFunction, Integer>{
	
	
	/**
	 * 根据父级的id查询下列所有的子级
	 * @param parentId
	 * @return
	 */
	List<Integer> queryfunByParentId(@Param("parentId") Integer parentId);
	
	
	/**
	 * 分页条件查询
	 * @param view
	 * @return
	 */
	List<TdsFunMoView> pageTdsFunction(TdsFunMoView view);
	
	
	
	Integer queryCount(@Param("funName")String funName);
	
	
	/**
	 * 根据id获取对象
	 * @param id
	 * @return
	 */
	TdsFunMoView loadByIdView(@Param("id")Integer id);
	
	
	
	/**
	 * 加载所有的功能
	 */
	List<TdsFunction> queryFunction(@Param("parentId")Integer parentId);
	
	
	/**
	 * 查询权限列表<分页>
	 * @param name  模块名称
	 * @return
	 */
	List<Map<String,Object>> pageByFunction(@Param("name")String name,@Param("pageNumber")Integer pageNumber,@Param("numPerPage")Integer numPerPage);
   	
	
	
	/**
	 * 根据用户加载角色对应权限
	 * @param userId
	 * @return
	 */
	List<TdsFunction> loadingByUsreIdRole(@Param("userId")Integer userId);
	
	
	
	/**
	 * 根据角色查询权限功能
	 * @param roleId
	 * @return
	 */
	List<TdsFunction> loadingBydRoleId(@Param("roleId") Integer roleId);
	
	
	List<TdsFunction> selectAll(TdsFunction fun);
	
}
