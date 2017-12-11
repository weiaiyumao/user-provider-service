package cn.service;


import java.util.List;

import cn.entity.TdsFunctionRole;
import main.java.cn.service.tds.TdsFunctionRoleBusService;


/**
 * : tds_function_role
 * 
 * 
 * @author Gen
 */
public interface TdsFunctionRoleService extends TdsFunctionRoleBusService{
	    
	  
	    TdsFunctionRole loadById(Integer id); 
	    
	    Integer saveTdsFunctionRole(TdsFunctionRole entity);
	   	
	    Integer deleteById(Integer id);
	    
	    Integer updateFunctionRole(TdsFunctionRole entity);
	    
	    List<TdsFunctionRole> selectAll(TdsFunctionRole entity);
}
