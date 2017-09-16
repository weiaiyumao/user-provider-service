package cn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.CreUserAccountMapper;
import cn.entity.CreUserAccount;
import cn.service.CreUserAccountService;

@Service
public class CreUserAccountServiceImpl implements CreUserAccountService{

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;
	
	@Override
	public CreUserAccount findCreUserAccountByUserId(Integer creUserId) {
		return creUserAccountMapper.findCreUserAccountByUserId(creUserId);
	}

	@Override
	public int saveCreUserAccount(CreUserAccount creUserAccount) {
		return creUserAccountMapper.saveCreUserAccount(creUserAccount);
	}

	@Override
	public int updateCreUserAccount(CreUserAccount creUserAccount) {
		return creUserAccountMapper.updateCreUserAccount(creUserAccount);
	}

}
