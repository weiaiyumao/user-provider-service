package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.tds.TdsStateInfo;
import main.java.cn.domain.page.PageAuto;


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
	List<TdsStateInfo> pageTdsStateInfo(PageAuto auto);
  
	
	/**
	 * 条件获取总数
	 * @param tds
	 * @return
	 */
	Integer queryCount(PageAuto auto);


}
