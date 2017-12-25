package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsAccountBank;


/**
 * : 入账银行表接口
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsAccountBankMapper extends IBaseDao<TdsAccountBank, Integer>{
	
	/**
	 * 入账银行管理列表<分页>
	 * @return
	 */
	List<TdsAccountBank> pageTdsAccountBank(@Param("likeName")String likeName,@Param("pageNumber")Integer pageNumber,@Param("numPerPage")Integer numPerPage,@Param("selected")Integer selected);
	
	/**
	 * 根据id进行停用
	 * @param id
	 * @return
	 */
	Integer isDisableById(Integer id);
	
	
	/**
	 * 根据条件查询所有
	 */
   Integer queryCount(@Param("likeName")String likeName,@Param("selected")Integer selected);
}
