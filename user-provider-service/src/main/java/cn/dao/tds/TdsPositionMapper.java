package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsPosition;


/**
 * : 职位接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsPositionMapper extends IBaseDao<TdsPosition, Integer>{
	
	/**
	 * 根据父级部门查询职位
	 * @param departmentId
	 * @return
	 */
	List<TdsPosition> selectByDeparId(Integer departmentId);
  
}
