package cn.dao.tds;

import java.io.Serializable;
import java.util.List;

/**
 * 公共mapp接口
 * @author ChuangLan
 *
 * @param <T>
 * @param <PK>
 */
public interface IBaseDao<T extends Serializable,PK extends Serializable> {
	

	T loadById(Integer PK); 
   
	Integer save(T entity);
 	
	Integer deleteById(Integer PK);
  
	Integer update(T entity);
    
    List<T> selectAll(T entity);
    
}
