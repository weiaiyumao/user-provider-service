package cn.dao;

import cn.entity.CreUserAccount;

public interface CreUserAccountMapper {
	
	CreUserAccount findCreUserAccountByUserId(Integer creUserId);
	
	int saveCreUserAccount(CreUserAccount creUserAccount);
	
	int updateCreUserAccount(CreUserAccount creUserAccount);
	
}
