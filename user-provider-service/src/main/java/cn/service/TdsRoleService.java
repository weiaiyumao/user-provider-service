package cn.service;

import java.util.List;

import cn.entity.TdsRole;
import main.java.cn.service.tds.TdsRoleBusService;

/**
 * : tds_role
 * 
 * 
 * @author Gen
 */
public interface TdsRoleService extends TdsRoleBusService{
	
	TdsRole loadById(Integer id); 
    
    Integer saveTdsRole(TdsRole entity);
 	
    Integer deleteById(Integer id);
  
    Integer updateTdsRole(TdsRole entity);
    
    List<TdsRole> selectAll(TdsRole entity);
}
