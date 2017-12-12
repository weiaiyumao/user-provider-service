package cn.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.dao.TdsUserMapper;
import cn.entity.TdsUser;
import cn.service.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsUserDomain;


@Service
public class TdsUserServiceimpl implements TdsUserService{

	private final static Logger logger = LoggerFactory.getLogger(TdsUserServiceimpl.class);
	
	private TdsUserMapper tdsUserMapper;
	
	@Override
	public BackResult<TdsUserDomain> loadById(Integer id) {
		BackResult<TdsUserDomain> result=new BackResult<TdsUserDomain>();
		try {
			TdsUser entity=tdsUserMapper.loadById(id);
			TdsUserDomain domain=new TdsUserDomain();
			BeanUtils.copyProperties(entity,domain);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：" + id + "查询用户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	@Override
	public BackResult<TdsUserDomain> save(TdsUserDomain domain) {
		   BackResult<TdsUserDomain> result=new BackResult<TdsUserDomain>();
		   TdsUser  tds=new TdsUser();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsUserMapper.save(tds);
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
			tdsUserMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID:" + id + "delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据删除失败");
		}
		return result;
	}

	@Override
	public BackResult<TdsUserDomain> update(TdsUserDomain domain) {
		BackResult<TdsUserDomain> result=new BackResult<TdsUserDomain>();
		domain.setUpdateTime(new Date());
		TdsUser  tds=new TdsUser();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsUserMapper.update(tds);
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
	public BackResult<List<TdsUserDomain>> selectAll(TdsUserDomain entity) {
		
		return null;
	}


}
