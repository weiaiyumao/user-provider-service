package cn.service.tds;



import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsFunctionRoleBusService;


/**
 * : 功能与角色服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsFunctionRoleService extends TdsFunctionRoleBusService{
	    
	  
	    
	   	
	   BackResult<Integer> deleteById(Integer id);
	    
	 
}
