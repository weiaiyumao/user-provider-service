package cn.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserMapper;
import cn.entity.CreUser;
import cn.service.CreUserService;
import cn.utils.CommonUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.CreUserDomain;

@Service
public class CreUserServiceImpl implements CreUserService{
	
	private final static Logger logger = LoggerFactory.getLogger(CreUserServiceImpl.class);

	@Autowired
	private CreUserMapper creUserMapper;
	
	@Override
	public CreUser findCreUserByUserPhone(String userPhone) {
		List<CreUser> list = creUserMapper.findCreUserByUserPhone(userPhone);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public int saveCreUser(CreUser creUser) {
		creUser.setCreateTime(new Date());
		creUser.setUpdateTime(new Date());
		return creUserMapper.saveCreUser(creUser);
	}

	@Override
	public int updateCreUser(CreUser creUser) {
		return creUserMapper.updateCreUser(creUser);
	}

	@Override
	public BackResult<CreUserDomain> findbyMobile(String mobile) {
		
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		
		try {
			CreUser user = this.findCreUserByUserPhone(mobile);
			
			if (null == user) {
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("该用户不存在！");
				return result;
			}
			
			CreUserDomain creUserDomain = new CreUserDomain();
			
			BeanUtils.copyProperties(user, creUserDomain);
			
			result.setResultObj(creUserDomain);
		} catch (Exception e) {
			logger.error("用户手机号码：【"+mobile+"】执行查询操作系统异常！"+e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}
		
		return result;
	}

	@Transactional
	public BackResult<CreUserDomain> findOrsaveUser(CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		
		try {
			
			result = this.findbyMobile(creUserDomain.getUserPhone());
			
			if (result.getResultCode().equals(ResultCode.RESULT_SUCCEED)) {
				return result;
			}
			
			// 不存在重新注册
			
			CreUser creUser = new CreUser();
			
			BeanUtils.copyProperties(creUserDomain, creUser);
			
			this.saveCreUser(creUser);
			
			creUser = this.findCreUserByUserPhone(creUserDomain.getUserPhone());
			
			CreUserDomain creUserDomains = new CreUserDomain();
			BeanUtils.copyProperties(creUser, creUserDomains);
			result.setResultObj(creUserDomains);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户手机号码：【"+creUserDomain.getUserPhone()+"】执行注册操作数据入库异常！"+e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}
		
		return result;
	}
	
}
