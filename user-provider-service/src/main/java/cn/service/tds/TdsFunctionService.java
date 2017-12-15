package cn.service.tds;


import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsFunctionBusService;

/**
 * 功能服务接口
 * @author ChuangLan
 *
 */
public interface TdsFunctionService extends TdsFunctionBusService {



	BackResult<Integer> deleteById(Integer id);

	

	
}
