package cn.dao.tds;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsModular;


/**
 * :模块mapper接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsModularMapper extends IBaseDao<TdsModular, Integer>{
	
	/**
	 * 加载模块
	 * @param userId
	 * @return
	 */
	List<TdsModular> moduleLoadingByUsreId(@Param("userId")Integer userId);
	
	
	/**
	 * 查询模块列表<分页>
	 * @param name  模块名称
	 * @return
	 */
	List<Map<String,Object>> pageByModular(@Param("name")String name,@Param("pageNumber")Integer pageNumber,@Param("numPerPage")Integer numPerPage);
	
	/**
	 * 获取总数
	 * @return
	 */
	Integer queryCount(@Param("name")String name);
	
	
	List<TdsModular> queryModular(@Param("parentId")String parentId);
   
}
