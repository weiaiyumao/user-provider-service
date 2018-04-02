package cn.service.tds;



import java.util.Map;

import cn.entity.tds.TdsRole;
import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsRoleBusService;

/**
 * : 角色服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsRoleService extends TdsRoleBusService{
	
	BackResult<TdsRole> loadById(Integer id); 
    
	
 	
	BackResult<Integer> deleteById(Integer id);
	
	
}
