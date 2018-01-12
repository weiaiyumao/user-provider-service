package cn.dao.tds;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUserCustomer;
import cn.entity.tds.view.TdsCustomerView;
import main.java.cn.domain.page.PageAuto;


/**
 * : tds_user_customer
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserCustomerMapper extends IBaseDao<TdsUserCustomer, Integer>{
	
	/**
	 * 客户列表展示
	 * @param auto
	 * @return
	 */
	List<TdsCustomerView> pageTdsCustomer(PageAuto auto);
	
	
	/**
	 * 根据条件获取总数
	 * @param auto
	 * @return
	 */
	Integer queryCount(PageAuto auto);
	
	

    /**
     * 根据客服id查询客服列表信息
     * @param id
     * @return
     */
	TdsCustomerView loadByIdView(@Param("id") Integer id);
	
	
	/**
	 * 充值，下单到账金额佣金改变
	 * @param sumMoney
	 * @param sumCommission
	 * @param userId
	 * @param updateTime
	 * @return
	 */
	Integer addMoneyAndCommission(@Param("sumMoney")String sumMoney,@Param("sumCommission")String sumCommission,@Param("userId")Integer userId,@Param("updateTime")Date updateTime);
  
}
