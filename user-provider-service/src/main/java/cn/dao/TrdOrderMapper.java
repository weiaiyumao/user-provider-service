package cn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.TrdOrder;

@Mapper
public interface TrdOrderMapper {
	
	List<TrdOrder> findByOrderNo(String orderNo);
	
	List<TrdOrder> findByClOrderNo(String clOrderNo);
	
	List<TrdOrder> findByCreUserId(Integer creUserId);
	
	int saveTrdOrder(TrdOrder trdOrder);
	
	int updateTrdOrder(TrdOrder trdOrder);
}
