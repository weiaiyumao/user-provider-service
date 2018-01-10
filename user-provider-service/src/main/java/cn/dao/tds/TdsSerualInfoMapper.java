package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsSerualInfo;


/**
 * :流水明细mapper接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsSerualInfoMapper extends IBaseDao<TdsSerualInfo, Integer>{
	
	/**
	 * 分页集合条件查询
	 * @return
	 */
	List<TdsSerualInfo> pageTdsSerualInfo(TdsSerualInfo entity);
	
	
	/**
	 * 获取总数
	 * @param entity
	 * @return
	 */
	Integer queryCount(TdsSerualInfo entity);
	
	
	/*
	 * 修改状态
	 */
	Integer upSerialByStatus(TdsSerualInfo entity);
	
    
}
