package cn.service.tds;

import java.util.Map;

import main.java.cn.common.BackResult;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.service.tds.TdsFunctionBusService;

/**
 * 功能服务接口
 * 
 * @author ChuangLan
 *
 */
public interface TdsFunctionService extends TdsFunctionBusService {

	BackResult<Integer> deleteById(Integer id);
	
	

	/**
	 * 查询权限列表<分页>
	 * 
	 * @param name
	 *            模块名称
	 * @return
	 */
	BackResult<PageDomain<Map<String, Object>>> pageByFunction(String name, BasePageParam basePageParam);
	
	
}
