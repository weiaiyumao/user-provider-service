package cn.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TdsUserRoleMapper;
import cn.entity.TdsUserRole;
import cn.service.TdsUserRoleService;


@Service
public class TdsUserRoleServiceImp implements  TdsUserRoleService {

	@Autowired
	private TdsUserRoleMapper tdsUserRoleMapper;
	
	@Override
	public TdsUserRole loadById(Integer id) {
		return tdsUserRoleMapper.loadById(id);
	}

	@Override
	public Integer saveTdsUserRole(TdsUserRole entity) {
		entity.setCreateTime(new Date());
		return tdsUserRoleMapper.save(entity);
	}

	@Override
	public Integer deleteById(Integer id) {
		return tdsUserRoleMapper.deleteById(id);
	}

	@Override
	public Integer updateTdsUserRole(TdsUserRole entity) {
		entity.setUpdateTime(new Date());
		return tdsUserRoleMapper.update(entity);
	}

	@Override
	public List<TdsUserRole> selectAll(TdsUserRole entity) {
		return tdsUserRoleMapper.selectAll(entity);
	}
     
	

	
	

	

}
