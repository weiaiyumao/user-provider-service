package cn.service.tds;



import java.util.List;
import java.util.Map;

import cn.entity.tds.TdsUserRole;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsUserRoleDomain;
import main.java.cn.service.tds.TdsUserRoleBusService;

/**
 * : 用户与角色服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsUserRoleService extends  TdsUserRoleBusService{
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	BackResult<TdsUserRole> loadById(Integer id); 
 	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	BackResult<Integer> deleteById(Integer id);
	
	
	/**
	 * 账号配置列表显示
	 * @param auto
	 * @return
	 */
	BackResult<PageDomain<TdsUserRoleDomain>> queryRoleIsStatus(TdsUserRoleDomain domain);
	
	
	/**
	 * 根据角色名称获取用户信息
	 * @param roleId
	 * @return
	 */
	BackResult<List<Map<String,String>>> queryUserByRoleName(String contact);
  
}
