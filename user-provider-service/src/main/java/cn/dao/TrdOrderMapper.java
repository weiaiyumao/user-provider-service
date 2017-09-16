package cn.dao;

import java.util.List;

import cn.entity.TrdOrder;

public interface TrdOrderMapper {
	
	List<TrdOrder> findByOrderNo(String orderNo);
	
	List<TrdOrder> findByClOrderNo(String clOrderNo);

	int saveTrdOrder(TrdOrder trdOrder);
	
	int updateTrdOrder(TrdOrder trdOrder);
}
