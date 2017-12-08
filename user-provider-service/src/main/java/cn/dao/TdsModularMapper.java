package cn.dao;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.TdsModular;


/**
 * : tds_modular
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsModularMapper{
	
    TdsModular loadById(Integer id);
	
	void insert(TdsModular entity);
	
	void deleteById(Integer id);
	
	void update(TdsModular entity);
}
