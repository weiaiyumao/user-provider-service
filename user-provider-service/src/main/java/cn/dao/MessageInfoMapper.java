package cn.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cn.entity.MessageInfo;

@Mapper
public interface MessageInfoMapper {
	
	int save(Map<String,Object> info);
	
	MessageInfo findByMobile(Map<String,Object> moblie);
	
	int saveList(List<MessageInfo> info);
}
