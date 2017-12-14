package cn.service.tds;

import java.util.List;

import cn.entity.tds.TdsUserRole;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsUserRoleDomain;
import main.java.cn.service.tds.TdsUserRoleBusService;

/**
 * : tds_user_role
 * 
 * 
 * @author Gen
 */
public interface TdsUserRoleService extends  TdsUserRoleBusService{
	
	BackResult<TdsUserRole> loadById(Integer id); 
    
	BackResult<TdsUserRoleDomain> saveTdsUserRole(TdsUserRoleDomain entity);
 	
	BackResult<Integer> deleteById(Integer id);
  
	BackResult<TdsUserRoleDomain> updateTdsUserRole(TdsUserRoleDomain entity);
    
	BackResult<List<TdsUserRoleDomain>> selectAll(TdsUserRoleDomain entity);
}
