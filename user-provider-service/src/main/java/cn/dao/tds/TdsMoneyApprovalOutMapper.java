package cn.dao.tds;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import main.java.cn.domain.tds.TdsApprovalOutDomain;
import main.java.cn.domain.tds.TdsApprovalOutQueryDomain;


/**
 * : tds_carry
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsMoneyApprovalOutMapper extends IBaseDao<TdsApprovalOutDomain, Integer>{
	
	
	/**
	 * 出账 分页 条件查询
	 * @param entity
	 * @return
	 */
	List<TdsApprovalOutDomain> pageMoneyApprovalOut(TdsApprovalOutQueryDomain entity);
	
	/**
	 * 获取总数
	 * @param entity
	 * @param selected
	 * @return
	 */
	Integer queryCount(TdsApprovalOutQueryDomain entity);
	
	
	/**
	 * 出账审核状态修改及佣金调整
	 */
     Integer upCarryStatus(Map<String,String> param);
     
     /**
 	 * 修改流水表的状态-通过
 	 */
    Integer upSerualStatus(Map<String,String> param);
    
    
    /**
 	 * 修改流水表的状态-驳回
 	 */
    Integer upCarryStatusRebut(Map<String,String> param);
}
