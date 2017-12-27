package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsCompany;

public interface TdsCompanyMapper  extends IBaseDao<TdsCompany, Integer> {
	
	
	/**
	 * 根据公司名称查询
	 * @param comName
	 * @return
	 */
	TdsCompany getComName(@Param("comName")String comName);
	
	
	/**
	 * 根据公司地址查询
	 * @param comName
	 * @return
	 */
	Integer getComUrl(@Param("comUrl")String comUrl);
	
	/**
	 * 新增公司信息 返回主键id
	 * @param entity
	 * @return
	 */
	//Integer addCom(TdsCompany entity);
	
	
	List<TdsCompany> selectCompanyAll();

}
