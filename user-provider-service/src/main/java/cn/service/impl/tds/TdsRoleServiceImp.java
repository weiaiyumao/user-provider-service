package cn.service.impl.tds;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsRoleMapper;
import cn.entity.tds.TdsRole;
import cn.service.tds.TdsRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsRoleDomain;

@Service
public class TdsRoleServiceImp implements  TdsRoleService {
	
	private final static Logger logger = LoggerFactory.getLogger(TdsRoleServiceImp.class);

	@Autowired
	private TdsRoleMapper  tdsRoleMapper;
	
	@Override
	public BackResult<TdsRole> loadById(Integer id) {
		BackResult<TdsRole> result=new BackResult<TdsRole>();
		try {
			TdsRole entity=tdsRoleMapper.loadById(id);
			result.setResultObj(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<TdsRoleDomain> saveTdsRole(TdsRoleDomain domain) {
		   BackResult<TdsRoleDomain> result=new BackResult<TdsRoleDomain>();
		   TdsRole  tds=new TdsRole();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsRoleMapper.save(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		 BackResult<Integer> result=new BackResult<Integer>();
		try {
			tdsRoleMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据删除失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<TdsRoleDomain> updateTdsRole(TdsRoleDomain domain) {
		BackResult<TdsRoleDomain> result=new BackResult<TdsRoleDomain>();
		domain.setUpdateTime(new Date());
		TdsRole  tds=new TdsRole();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsRoleMapper.update(tds);
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
	public BackResult<List<TdsRoleDomain>> selectAll(TdsRoleDomain domain) {
		BackResult<List<TdsRoleDomain>> result=new BackResult<List<TdsRoleDomain>>();
		TdsRole tds=new TdsRole();
		List<TdsRoleDomain>  listDomain=new ArrayList<TdsRoleDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsRole> list=tdsRoleMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsRoleDomain tdsDomain=null;
	          for(TdsRole obj:list){
	        	 tdsDomain=new TdsRoleDomain();
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
