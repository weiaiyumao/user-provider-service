package cn.dao.tds;


import java.util.List;

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
   	
}
