package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUserCustomer;
import cn.entity.tds.view.TdsCustomerView;


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
	List<TdsCustomerView> pageTdsCustomer(TdsCustomerView view);
	
	
	/**
	 * 根据条件获取总数
	 * @param auto
	 * @return
	 */
	Integer queryCount(TdsCustomerView view);
	
	

    /**
     * 根据客服id查询客服列表信息
     * @param id
     * @return
     */
	TdsCustomerView loadByIdView(@Param("userId") Integer userId);
	
	
	/**
	 * 充值，下单到账金额佣金改变,累积佣金累加
	 * @param sumMoney
	 * @param sumCommission
	 * @param userId
	 * @param updateTime
	 * @return
	 */
	Integer addMoneyAndCommission(TdsUserCustomer entity);
  
	
	/**
	 * 改变剩余佣金
	 * @param userId
	 * @param overplusCommission
	 * @return
	 */
	Integer subMoneyAndCommission(@Param("userId")Integer userId,@Param("overplusCommission")String overplusCommission);
	
	/**
	 * 查询该用户是否存在
	 * @param userId
	 * @return
	 */
	Integer queryIsUserId(@Param("userId")Integer userId);
	
	
	/**
	 * 根据用户查询对象
	 * @param userId
	 * @return
	 */
	TdsUserCustomer loadByUserId(@Param("userId")Integer userId);
	
}
