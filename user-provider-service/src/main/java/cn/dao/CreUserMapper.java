package cn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.CreUser;

@Mapper
public interface CreUserMapper {

	CreUser findByUserId(Integer clAccountId);
	
	CreUser findById(Integer id);
	
	List<CreUser> findAll();
	
	List<CreUser> findCreUserByUserPhone(String userPhone);
	
	int saveCreUser(CreUser creUser);
	
	int updateCreUser(CreUser creUser);
	
	int updateCreUserClAccountId(Map<String,Object> params);
}
