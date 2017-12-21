package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsFunction;


/**
 * : tds_function
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsFunctionMapper extends IBaseDao<TdsFunction, Integer>{
	
	/**
	 * 根据用户id模块加载
	 * @param userid
	 * @return
	 */
	List<TdsFunction> moduleLoadingByUsreId(Integer userid);
	
	
	/**
	 * 根据父级的id查询下列所有的子级
	 * @param parentId
	 * @return
	 */
	List<Integer> queryfunByParentId(@Param("parentId") Integer parentId);
	
	
	//List<TdsFunction> selectByParentIdInfo();
	
}
