package cn.service;

import java.util.List;

import cn.entity.TdsUserRole;
import main.java.cn.service.tds.TdsUserRoleBusService;

/**
 * : tds_user_role
 * 
 * 
 * @author Gen
 */
public interface TdsUserRoleService extends  TdsUserRoleBusService{
	
	TdsUserRole loadById(Integer id); 
    
    Integer saveTdsUserRole(TdsUserRole entity);
 	
    Integer deleteById(Integer id);
  
    Integer updateTdsUserRole(TdsUserRole entity);
    
    List<TdsUserRole> selectAll(TdsUserRole entity);
}
