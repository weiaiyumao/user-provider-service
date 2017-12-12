package cn.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.TdsUserRoleMapper;
import cn.entity.TdsUserRole;
import cn.service.TdsUserRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsUserRoleDomain;


@Service
public class TdsUserRoleServiceImp implements  TdsUserRoleService {
    
	private final static Logger logger = LoggerFactory.getLogger(TdsUserRoleServiceImp.class);
	
	@Autowired
	private TdsUserRoleMapper tdsUserRoleMapper;
	
	@Override
	public BackResult<TdsUserRole> loadById(Integer id) {
		BackResult<TdsUserRole> result=new BackResult<TdsUserRole>();
		try {
			TdsUserRole entity=tdsUserRoleMapper.loadById(id);
			result.setResultObj(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	@Override
	public BackResult<TdsUserRoleDomain> saveTdsUserRole(TdsUserRoleDomain domain) {
		  BackResult<TdsUserRoleDomain> result=new BackResult<TdsUserRoleDomain>();
		  TdsUserRole  tds=new TdsUserRole();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsUserRoleMapper.save(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
		
	}

	@Override
	public BackResult<Integer> deleteById(Integer id) {
		     BackResult<Integer> result=new BackResult<Integer>();
			try {
				tdsUserRoleMapper.deleteById(id);
				result.setResultObj(id);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("delete功能信息出现系统异常：" + e.getMessage());
				result.setResultCode(ResultCode.RESULT_FAILED);
				result.setResultMsg("数据删除失败");
			}
			return result;
		
	}

	@Override
	public BackResult<TdsUserRoleDomain> updateTdsUserRole(TdsUserRoleDomain domain) {
		BackResult<TdsUserRoleDomain> result=new BackResult<TdsUserRoleDomain>();
		domain.setUpdateTime(new Date());
		TdsUserRole  tds=new TdsUserRole();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsUserRoleMapper.update(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	@Override
	public BackResult<List<TdsUserRoleDomain>> selectAll(TdsUserRoleDomain domain) {
		BackResult<List<TdsUserRoleDomain>> result=new BackResult<List<TdsUserRoleDomain>>();
		TdsUserRole tds=new TdsUserRole();
		List<TdsUserRoleDomain>  listDomain=new ArrayList<TdsUserRoleDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsUserRole> list=tdsUserRoleMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsUserRoleDomain tdsDomain=null;
	          for(TdsUserRole obj:list){
	        	 tdsDomain=new TdsUserRoleDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          result.setResultObj(listDomain);
			}
	
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}
     
	

	
	

	

}
