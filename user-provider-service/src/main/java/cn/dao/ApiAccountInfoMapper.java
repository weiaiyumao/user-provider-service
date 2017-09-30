package cn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.ApiAccountInfo;

@Mapper
public interface ApiAccountInfoMapper {

	List<ApiAccountInfo> findByCreUserIdAndName(Integer creUserId,String name);
	
	List<ApiAccountInfo> findByCreUserId(Integer creUserId);
	
	int save(ApiAccountInfo info);
	
	int update(ApiAccountInfo info);
}
