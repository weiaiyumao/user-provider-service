package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

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
	 * 根据角色id获取姓名
	 * @param roleId
	 * @param userName
	 * @return
	 */
	PageAuto queryByRoleId(Integer roleId,String userName);
	
}
