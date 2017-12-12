package cn.dao;


import org.apache.ibatis.annotations.Mapper;

import cn.entity.TdsUser;
import cn.utils.IBaseDao;


/**
 * : tds_user
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserMapper extends IBaseDao<TdsUser, Integer>{
	
  
}
