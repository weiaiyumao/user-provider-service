package cn.service.impl;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import cn.dao.TdsFunctionMapper;
import cn.entity.TdsFunction;
import cn.service.TdsFunctionService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsFunctionDomain;


@Service
public class TdsFunctionServiceImpl implements  TdsFunctionService {
    
	private final static Logger logger = LoggerFactory.getLogger(TdsFunctionServiceImpl.class);
	
	@Autowired
	private TdsFunctionMapper tdsFunctionMapper;
	

	@Override
	public BackResult<TdsFunctionDomain> loadById(Integer id) {
		 BackResult<TdsFunctionDomain> result=new BackResult<TdsFunctionDomain>();
		try {
			TdsFunctionDomain  domain=new TdsFunctionDomain();
			TdsFunction entity=tdsFunctionMapper.loadById(id);
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

	@Override
	public BackResult<TdsFunctionDomain> saveTdsFunction(TdsFunctionDomain domain) {
		   BackResult<TdsFunctionDomain> result=new BackResult<TdsFunctionDomain>();
		   TdsFunction  tds=new TdsFunction();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsFunctionMapper.save(tds);
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
			tdsFunctionMapper.deleteById(id);
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
	public BackResult<TdsFunctionDomain> updateTdsFunction(TdsFunctionDomain domain) {
		BackResult<TdsFunctionDomain> result=new BackResult<TdsFunctionDomain>();
		domain.setUpdateTime(new Date());
		TdsFunction  tds=new TdsFunction();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsFunctionMapper.update(tds);
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
	public BackResult<List<TdsFunctionDomain>> selectAll(TdsFunctionDomain domain) {
		BackResult<List<TdsFunctionDomain>> result=new BackResult<List<TdsFunctionDomain>>();
		TdsFunction tds=new TdsFunction();
		List<TdsFunctionDomain>  listDomain=new ArrayList<TdsFunctionDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsFunction> list=tdsFunctionMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsFunctionDomain tdsDomain=null;
	          for(TdsFunction obj:list){
	        	 tdsDomain=new TdsFunctionDomain();
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
