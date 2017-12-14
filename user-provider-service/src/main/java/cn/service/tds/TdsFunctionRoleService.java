package cn.service.tds;


import java.util.List;

import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionRoleDomain;
import main.java.cn.service.tds.TdsFunctionRoleBusService;


/**
 * : tds_function_role
 * 
 * 
 * @author Gen
 */
public interface TdsFunctionRoleService extends TdsFunctionRoleBusService{
	    
	  
	   BackResult<TdsFunctionRoleDomain> loadById(Integer id); 
	    
	   BackResult<TdsFunctionRoleDomain> saveTdsFunctionRole(TdsFunctionRoleDomain entity);
	   	
	   BackResult<Integer> deleteById(Integer id);
	    
	   BackResult<TdsFunctionRoleDomain> updateFunctionRole(TdsFunctionRoleDomain entity);
	    
	   BackResult<List<TdsFunctionRoleDomain>> selectAll(TdsFunctionRoleDomain entity);
}
