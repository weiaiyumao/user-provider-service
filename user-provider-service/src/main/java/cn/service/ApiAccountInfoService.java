package cn.service;

import cn.entity.ApiAccountInfo;
import main.java.cn.service.ApiAccountInfoBusService;

public interface ApiAccountInfoService extends ApiAccountInfoBusService{
	
	ApiAccountInfo findByCreUserIdAndName(Integer creUserId,String name);
	
	int save(ApiAccountInfo info);
	
	int update(ApiAccountInfo info);

}
