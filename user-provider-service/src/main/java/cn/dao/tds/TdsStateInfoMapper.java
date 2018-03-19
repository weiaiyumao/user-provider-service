package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.tds.TdsStateInfo;


/**
 * : 状态库接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsStateInfoMapper extends IBaseDao<TdsStateInfo, Integer>{
	
	
	/**
	 * 分页查询
	 * @param tds
	 * @return
	 */
	List<TdsStateInfo> pageTdsStateInfo(TdsStateInfo info);
  
	
	/**
	 * 条件获取总数
	 * @param tds
	 * @return
	 */
	Integer queryCount(TdsStateInfo info);


}
