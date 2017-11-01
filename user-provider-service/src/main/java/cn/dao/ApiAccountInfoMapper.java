package cn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.ApiAccountInfo;
import main.java.cn.domain.ApiAccountInfoDomain;

@Mapper
public interface ApiAccountInfoMapper {

	List<ApiAccountInfo> findByCreUserIdAndName(Integer creUserId,String name);
	
	List<ApiAccountInfo> findByCreUserId(Integer creUserId);
	
	int save(ApiAccountInfo info);
	
	int update(ApiAccountInfo info);
	
	int updateDomain(ApiAccountInfoDomain info);
	
	List<ApiAccountInfo> findByNameAndPwd(String name, String password);
}
