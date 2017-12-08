package cn.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import cn.entity.TdsFunction;


/**
 * : tds_function
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsFunctionMapper{
	
    TdsFunction loadById(Integer id);
	
	List<TdsFunction> selectByList(TdsFunction queryParam);
	
	void insert(TdsFunction insertParam);
	
	void deleteById(Integer id);
	
	void update(TdsFunction insertParam);
}
