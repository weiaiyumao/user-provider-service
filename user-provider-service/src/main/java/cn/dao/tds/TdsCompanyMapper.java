package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsCompany;

public interface TdsCompanyMapper  extends IBaseDao<TdsCompany, Integer> {
	
	
	
	/**
	 * 根据公司地址查询
	 * @param comName
	 * @return
	 */
	TdsCompany getComUrlAndComName(@Param("comUrl")String comUrl,@Param("comName")String comName);
	
	/**
	 * 新增公司信息 返回主键id
	 * @param entity
	 * @return
	 */
	//Integer addCom(TdsCompany entity);
	
	
	List<TdsCompany> selectCompanyAll();
	
	
	/**
	 * 根据userid查询公司id进表查询
	 * @param userId
	 * @return
	 */
	TdsCompany queryComByUserId(@Param("userId")Integer userId);

}
