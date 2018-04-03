package cn.service.tds;

import java.util.Map;

import cn.entity.tds.TdsFunction;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
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
	BackResult<PageDomain<Map<String, Object>>> pageByFunction(String name, BasePageParam basePageParam,String url);
	
	
	/**
	 * 查询模块
	 * @param name
	 * @param basePageParam
	 * @return
	 */
	BackResult<PageDomain<Map<String, Object>>> pageByModular(String name,BasePageParam basePageParam);
	
	
	
	/**
	 * 根据id获取功能信息
	 * @param id
	 * @return
	 */
    BackResult<TdsFunctionDomain> loadById(Integer id);
   
   
   /**
	 * 修改模块
	 * @return
      */
    BackResult<Integer> update(TdsFunction entity);
}
