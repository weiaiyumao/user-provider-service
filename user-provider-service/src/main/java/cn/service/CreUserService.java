package cn.service;

import cn.entity.CreUser;

/**
 * CreUserService
 * @author ChuangLan
 *
 */
public interface CreUserService {
	
	CreUser findCreUserByUserPhone(String userPhone);
	
	int saveCreUser(CreUser creUser);
	
	int updateCreUser(CreUser creUser);
}
