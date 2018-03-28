package cn.service.tds;

import main.java.cn.common.BackResult;

/**
 * 佣金服务
 * @author ChuangLan
 *
 */
public interface TdsCommissionService {

	
	
	
	/**
	 * 新增一条佣金信息
	 * @param status
	 * @param userId
	 * @param money
	 * @param ordrr
	 * @return
	 */
	BackResult<Integer> addCommission(String status,Integer userId, String money, String order);
	
	
	/**
	 * 更新佣金
	 * @param status
	 * @param order
	 * @return
	 */
	BackResult<Integer> upCommStatus(String status,String order);
	
}
