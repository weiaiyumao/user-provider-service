package cn.service.impl.tds;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsFunctionRoleMapper;
import cn.entity.tds.TdsFunctionRole;
import cn.service.tds.TdsFunctionRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsFunctionRoleDomain;


@Service
public class TdsFunctionRoleServiceImpl implements  TdsFunctionRoleService {
	
	private final static Logger logger = LoggerFactory.getLogger(TdsFunctionRoleServiceImpl.class);

	@Autowired
	private TdsFunctionRoleMapper tdsFunctionRoleMapper;

	
	@Override
	public BackResult<TdsFunctionRoleDomain> loadById(Integer id) {
		BackResult<TdsFunctionRoleDomain> result=new BackResult<TdsFunctionRoleDomain>();
		try {
			TdsFunctionRoleDomain domain=new TdsFunctionRoleDomain();
			TdsFunctionRole entity=tdsFunctionRoleMapper.loadById(id);
			BeanUtils.copyProperties(entity,domain);
			result.setResultObj(domain);
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
	public BackResult<TdsFunctionRoleDomain> saveTdsFunctionRole(TdsFunctionRoleDomain domain) {
		   BackResult<TdsFunctionRoleDomain> result=new BackResult<TdsFunctionRoleDomain >();
		   TdsFunctionRole  tds=new TdsFunctionRole();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsFunctionRoleMapper.save(tds);
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
			tdsFunctionRoleMapper.deleteById(id);
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
	public BackResult<TdsFunctionRoleDomain> updateFunctionRole(TdsFunctionRoleDomain domain) {
		BackResult<TdsFunctionRoleDomain> result=new BackResult<TdsFunctionRoleDomain>();
		TdsFunctionRole  tds=new TdsFunctionRole();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsFunctionRoleMapper.update(tds);
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
	public BackResult<List<TdsFunctionRoleDomain>> selectAll(TdsFunctionRoleDomain domain) {
		BackResult<List<TdsFunctionRoleDomain>> result=new BackResult<List<TdsFunctionRoleDomain>>();
		TdsFunctionRole tds=new TdsFunctionRole();
		List<TdsFunctionRoleDomain>  listDomain=new ArrayList<TdsFunctionRoleDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsFunctionRole> list=tdsFunctionRoleMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsFunctionRoleDomain tdsDomain=null;
	          for(TdsFunctionRole obj:list){
	        	 tdsDomain=new TdsFunctionRoleDomain();
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
