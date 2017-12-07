package cn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.CreUserAccount;

@Mapper
public interface CreUserAccountMapper {
	
	List<CreUserAccount> findCreUserAccountByUserId(Integer creUserId);
	
	int saveCreUserAccount(CreUserAccount creUserAccount);
	
	int updateCreUserAccount(CreUserAccount creUserAccount);
}
