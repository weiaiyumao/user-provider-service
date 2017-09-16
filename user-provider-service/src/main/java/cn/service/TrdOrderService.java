package cn.service;

import cn.entity.TrdOrder;

/**
 * CreUserService
 * @author ChuangLan
 *
 */
public interface TrdOrderService {
	
	TrdOrder findByOrderNo(String orderNo);
	
	TrdOrder findByClOrderNo(String clOrderNo);

	int saveTrdOrder(TrdOrder trdOrder);
	
	int updateTrdOrder(TrdOrder trdOrder);
}
