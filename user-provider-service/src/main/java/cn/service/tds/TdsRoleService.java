package cn.service.tds;

import java.util.List;

import cn.entity.tds.TdsRole;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsRoleDomain;
import main.java.cn.service.tds.TdsRoleBusService;

/**
 * : tds_role
 * 
 * 
 * @author Gen
 */
public interface TdsRoleService extends TdsRoleBusService{
	
	BackResult<TdsRole> loadById(Integer id); 
    
	BackResult<TdsRoleDomain> saveTdsRole(TdsRoleDomain entity);
 	
	BackResult<Integer> deleteById(Integer id);
  
    BackResult<TdsRoleDomain> updateTdsRole(TdsRoleDomain entity);
    
    BackResult<List<TdsRoleDomain>> selectAll(TdsRoleDomain entity);
}
