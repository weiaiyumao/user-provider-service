package cn.dao.tds;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

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
	List<TdsAccountBank> pageTdsAccountBank(TdsAccountBank tds);
	
	/**
	 * 根据id进行停用
	 * @param id
	 * @return
	 */
	Integer isDisableById(Integer id);
	
	
	/**
	 * 根据条件查询所有
	 */
   Integer queryCount(TdsAccountBank tds);
   
   
   //获取id,银行名称
   List<Map<String,Object>> selectAllBankName();
   
   
}
