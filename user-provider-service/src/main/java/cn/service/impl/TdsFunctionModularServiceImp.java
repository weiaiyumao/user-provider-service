package cn.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TdsModularMapper;
import cn.entity.TdsModular;
import cn.service.TdsModularService;


@Service
public class TdsFunctionModularServiceImp implements  TdsModularService {
     
	@Autowired
	private TdsModularMapper tdsModularMapper;
	
	@Override
	public TdsModular loadById(Integer id) {
		return tdsModularMapper.loadById(id);
	}

	@Override
	public Integer saveTdsModular(TdsModular entity) {
		entity.setCreateTime(new Date());
		return tdsModularMapper.save(entity);
	}

	@Override
	public Integer deleteById(Integer id) {
		return tdsModularMapper.deleteById(id);
	}

	@Override
	public Integer updateTdsModular(TdsModular entity) {
		entity.setUpdateTime(new Date());
		return tdsModularMapper.update(entity);
	}

	@Override
	public List<TdsModular> selectAll(TdsModular entity) {
		return tdsModularMapper.selectAll(entity);
	}


	
	

	

}
