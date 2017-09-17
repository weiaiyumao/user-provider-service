package cn.dao;

import cn.entity.CreUser;

public interface CreUserMapper {

	CreUser findByUserId(Integer clAccountId);
	
	CreUser findCreUserByUserPhone(String userPhone);
	
	int saveCreUser(CreUser creUser);
	
	int updateCreUser(CreUser creUser);
}
