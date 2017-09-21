package cn.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.CreUser;

@Mapper
public interface CreUserMapper {

	CreUser findByUserId(Integer clAccountId);
	
	List<CreUser> findCreUserByUserPhone(String userPhone);
	
	int saveCreUser(CreUser creUser);
	
	int updateCreUser(CreUser creUser);
}
