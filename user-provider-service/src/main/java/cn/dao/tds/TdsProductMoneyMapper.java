package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsProductMoney;


/**
 * : tds_product_money
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsProductMoneyMapper extends IBaseDao<TdsProductMoney, Integer>{
	
	
	
	/**
	 * 根据产品id获取价格集合
	 * @param id
	 * @return
	 */
	List<TdsProductMoney> queryPnameByPro(@Param("pnameId") Integer pnameId);
	
  
}
