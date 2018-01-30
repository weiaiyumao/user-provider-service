package cn.dao.tds;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import cn.entity.tds.TdsCarry;


/**
 * : tds_carry
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsCarryMapper extends IBaseDao<TdsCarry, Integer>{
	
	
	/**
	 * 提现分页
	 * @param carry
	 * @return
	 */
	List<TdsCarry> pageTdsCarry(TdsCarry carry);
	
	
	/**
	 * 总数
	 * @param carry
	 * @return
	 */
	Integer queryCount(TdsCarry carry);
	
	
}