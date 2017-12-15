package cn.service.tds;


import cn.entity.tds.TdsModular;
import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsModularBusService;

/**
 * :模块服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsModularService extends TdsModularBusService{
	
	      BackResult<TdsModular> loadById(Integer id); 
	    
	   	
	      BackResult<Integer> deleteById(Integer id);
	    
	     
}
