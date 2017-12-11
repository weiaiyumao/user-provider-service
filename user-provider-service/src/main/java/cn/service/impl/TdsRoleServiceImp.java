package cn.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TdsRoleMapper;
import cn.entity.TdsRole;
import cn.service.TdsRoleService;

@Service
public class TdsRoleServiceImp implements  TdsRoleService {

	@Autowired
	private TdsRoleMapper  tdsRoleMapper;
	
	@Override
	public TdsRole loadById(Integer id) {
		return tdsRoleMapper.loadById(id);
	}

	@Override
	public Integer saveTdsRole(TdsRole entity) {
		entity.setCreateTime(new Date());
		return tdsRoleMapper.save(entity);
	}

	@Override
	public Integer deleteById(Integer id) {
		return tdsRoleMapper.deleteById(id);
	}

	@Override
	public Integer updateTdsRole(TdsRole entity) {
		entity.setUpdateTime(new Date());
		return tdsRoleMapper.update(entity);
	}

	@Override
	public List<TdsRole> selectAll(TdsRole entity) {
		return tdsRoleMapper.selectAll(entity);
	}
     
	


	
	

	

}
