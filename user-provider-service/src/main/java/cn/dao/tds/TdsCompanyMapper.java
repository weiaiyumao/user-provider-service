package cn.dao.tds;

import cn.entity.tds.TdsCompany;

public interface TdsCompanyMapper  extends IBaseDao<TdsCompany, Integer> {
	
	
	/**
	 * 根据公司名称查询
	 * @param comName
	 * @return
	 */
	TdsCompany getComName(String comName);
	
	/**
	 * 新增公司信息 返回主键id
	 * @param entity
	 * @return
	 */
	Integer addCom(TdsCompany entity);

}
