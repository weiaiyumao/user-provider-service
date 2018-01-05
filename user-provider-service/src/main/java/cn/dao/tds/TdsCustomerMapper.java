package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.view.TdsCustomerView;
import main.java.cn.domain.page.PageAuto;

@Mapper
public interface TdsCustomerMapper {

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
    TdsCustomerView loadById(@Param("id") Integer id);
	
}
