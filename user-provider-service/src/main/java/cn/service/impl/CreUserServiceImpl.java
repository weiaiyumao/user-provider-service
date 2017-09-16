package cn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.CreUserMapper;
import cn.entity.CreUser;
import cn.service.CreUserService;

@Service
public class CreUserServiceImpl implements CreUserService{

	@Autowired
	private CreUserMapper creUserMapper;
	
	@Override
	public CreUser findCreUserByUserPhone(String userPhone) {
		return creUserMapper.findCreUserByUserPhone(userPhone);
	}

	@Override
	public int saveCreUser(CreUser creUser) {
		return creUserMapper.saveCreUser(creUser);
	}

	@Override
	public int updateCreUser(CreUser creUser) {
		return creUserMapper.updateCreUser(creUser);
	}
	
}
