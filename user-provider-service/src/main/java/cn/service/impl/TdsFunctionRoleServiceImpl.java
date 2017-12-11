package cn.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TdsFunctionRoleMapper;
import cn.entity.TdsFunctionRole;
import cn.service.TdsFunctionRoleService;


@Service
public class TdsFunctionRoleServiceImpl implements  TdsFunctionRoleService {

	@Autowired
	private TdsFunctionRoleMapper tdsFunctionRoleMapper;

	
	@Override
	public TdsFunctionRole loadById(Integer id) {
		return tdsFunctionRoleMapper.loadById(id);
	}

	@Override
	public Integer saveTdsFunctionRole(TdsFunctionRole entity) {
		entity.setCreateTime(new Date());
		return tdsFunctionRoleMapper.save(entity);
	}

	@Override
	public Integer deleteById(Integer id) {
		return tdsFunctionRoleMapper.deleteById(id);
	}

	@Override
	public Integer updateFunctionRole(TdsFunctionRole entity) {
		entity.setUpdateTime(new Date());
		return tdsFunctionRoleMapper.update(entity);
	}

	@Override
	public List<TdsFunctionRole> selectAll(TdsFunctionRole entity) {
		return tdsFunctionRoleMapper.selectAll(entity);
	}


	

}
