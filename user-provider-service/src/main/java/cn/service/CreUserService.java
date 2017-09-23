package cn.service;

import cn.entity.CreUser;
import main.java.cn.service.UserBusService;

/**
 * CreUserService
 * @author ChuangLan
 *
 */
public interface CreUserService extends UserBusService{
	
	CreUser findCreUserByUserPhone(String userPhone);
	
	int saveCreUser(CreUser creUser);
	
	int updateCreUser(CreUser creUser);
	
}
