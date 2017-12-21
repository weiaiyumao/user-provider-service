package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsFunctionRole;



@Mapper
public interface TdsFunctionRoleMapper extends IBaseDao<TdsFunctionRole, Integer>{
	
	
	
	   /**
	    * 通过父级id循环插入子级
	    * @param entity
	    * @return
	    */
	   Integer addArrByfunId(@Param("arr") List<TdsFunctionRole> arr);
	
}
