package cn.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.TrdOrder;

@Mapper
public interface TrdOrderMapper {
	
	List<TrdOrder> findByOrderNo(String orderNo);
	
	List<TrdOrder> findByClOrderNo(String clOrderNo);
	
	List<TrdOrder> findByCreUserId(Integer creUserId);
	
	int saveTrdOrder(TrdOrder trdOrder);
	
	int updateTrdOrder(TrdOrder trdOrder);
	
    List<TrdOrder> pageFindTrdOrderByCreUserId(Map<String,Object> map);
    
    Integer quertCountTrdOrder(Integer creUserId);
    
    BigDecimal getSumMoney(Integer creUserId);
    
    BigDecimal quertCountTrdOrderByProductsId(Integer creUserId,String productids);
}
