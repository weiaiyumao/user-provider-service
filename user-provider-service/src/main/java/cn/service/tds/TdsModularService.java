package cn.service.tds;


import java.util.Map;


import cn.entity.tds.TdsModular;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
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
	      
	      
	    /**
	  	 * 查询模块列表<分页>
	  	 * @param name  模块名称
	  	 * @return
	  	 */
	      BackResult<PageDomain<Map<String,Object>>> pageByModular(String name,BasePageParam basePageParam);
	     
	      
	      
}
