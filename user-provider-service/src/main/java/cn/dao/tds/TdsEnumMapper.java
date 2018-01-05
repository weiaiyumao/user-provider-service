package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsEnum;


/**
 * : tds_enum
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsEnumMapper{
	
	
	/**
	 * 根据code名称获取相应的类型
	 * @param codeName
	 * @return
	 */
	List<TdsEnum> queryByTypeCode(@Param("codeName") String codeName);
	
  
}
