package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsModular;


/**
 * : tds_modular
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsModularMapper extends IBaseDao<TdsModular, Integer>{
	
	
	List<TdsModular> moduleLoadingByUsreId(@Param("userId")Integer userId);
   
}
