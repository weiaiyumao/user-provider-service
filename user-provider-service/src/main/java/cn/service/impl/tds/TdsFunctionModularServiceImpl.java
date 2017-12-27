package cn.service.impl.tds;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsModularMapper;
import cn.entity.tds.TdsModular;
import cn.service.tds.TdsModularService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsModularDomain;


@Service
public class TdsFunctionModularServiceImpl implements  TdsModularService {
     
	private final static Logger logger = LoggerFactory.getLogger(TdsFunctionModularServiceImpl.class);
	
	
	@Autowired
	private TdsModularMapper tdsModularMapper;
	
	@Override
	public BackResult<TdsModular> loadById(Integer id) {
		BackResult<TdsModular> result=new BackResult<TdsModular>();
		try {
			TdsModular entity=tdsModularMapper.loadById(id);
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
	public BackResult<TdsModularDomain> saveTdsModular(TdsModularDomain domain) {
		  BackResult<TdsModularDomain> result=new BackResult<TdsModularDomain>();
		   TdsModular  tds=new TdsModular();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsModularMapper.save(tds);
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
			tdsModularMapper.deleteById(id);
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
	public BackResult<TdsModularDomain> updateTdsModular(TdsModularDomain domain) {
		BackResult<TdsModularDomain> result=new BackResult<TdsModularDomain>();
		domain.setUpdateTime(new Date());
		TdsModular  tds=new TdsModular();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsModularMapper.update(tds);
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
	public BackResult<List<TdsModularDomain>> selectAll(TdsModularDomain domain) {
		BackResult<List<TdsModularDomain>> result=new BackResult<List<TdsModularDomain>>();
		TdsModular tds=new TdsModular();
		List<TdsModularDomain>  listDomain=new ArrayList<TdsModularDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsModular> list=tdsModularMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsModularDomain tdsDomain=null;
	          for(TdsModular obj:list){
	        	 tdsDomain=new TdsModularDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          //排序
//	          Collections.sort(listDomain, new Comparator<TdsModularDomain>() {
//				@Override
//				public int compare(TdsModularDomain o1, TdsModularDomain o2) {
//					return o1.getSort()-o2.getSort();
//				 }  
//	          });  
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
