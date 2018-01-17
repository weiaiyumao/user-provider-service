package cn.dao.tds;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.tds.TdsCreUserAccountLog;


/**
 * : tds_cre_user_account_log
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsCreUserAccountLogMapper{
	
    TdsCreUserAccountLog loadById(String id);
	
	void insert(TdsCreUserAccountLog insertParam);
	
}
