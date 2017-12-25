package cn.dao.tds;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsUserDepartment;


/**
 * : 用户与部门表
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserDepartmentMapper extends IBaseDao<TdsUserDepartment,Integer>{
	
	  
	/**
	 * 根据userId 修改部门
	 * @param tdsUserDepartment
	 * @return
	 */
	  public Integer updateByUserId(TdsUserDepartment tdsUserDepartment);
	
}
