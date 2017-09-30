package cn.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.ApiAccountInfoMapper;
import cn.entity.ApiAccountInfo;
import cn.service.ApiAccountInfoService;
import cn.utils.CommonUtils;
import cn.utils.UUIDTool;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;

@Service
public class ApiAccountInfoServiceImpl implements ApiAccountInfoService{

	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoServiceImpl.class);
	
	@Autowired
	private ApiAccountInfoMapper apiAccountInfoMapper;
	
	@Override
	public ApiAccountInfo findByCreUserIdAndName(Integer creUserId,String name) {
		
		List<ApiAccountInfo> list = apiAccountInfoMapper.findByCreUserIdAndName(creUserId, name);
		
		if (CommonUtils.isNotEmpty(list)) {
			return null;
		}
		
		return list.get(0);
	}

	@Override
	public int save(ApiAccountInfo info) {
		return apiAccountInfoMapper.save(info);
	}

	@Override
	public int update(ApiAccountInfo info) {
		return apiAccountInfoMapper.update(info);
	}

	@Transactional
	public BackResult<ApiAccountInfoDomain> findByCreUserId(String creUserId) {
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		
		try {
			List<ApiAccountInfo> list = apiAccountInfoMapper.findByCreUserId(Integer.valueOf(creUserId));
			
			ApiAccountInfo info = new ApiAccountInfo();
			
			if (CommonUtils.isNotEmpty(list)) {
				// 由于用户第一次进来是没有api 账户信息的
				logger.info("用户ID:【" + creUserId + "】创建api账户信息记录");
				
				Boolean fag = Boolean.FALSE;
				
				do {
					info.setCreUserId(Integer.valueOf(creUserId));
					info.setName("S" + UUIDTool.getInstance().getUUID8());
					// 检测数据库是否存在重复
					List<ApiAccountInfo> Infolist = apiAccountInfoMapper.findByCreUserIdAndName(info.getCreUserId(), info.getName());
					
					if (CommonUtils.isNotEmpty(Infolist)) {
						fag = Boolean.TRUE;
					}
					
				} while (fag);
				
				info.setPassword("pwd" + UUIDTool.getInstance().getUUID8());
				info.setCreateTime(new Date());
				apiAccountInfoMapper.save(info);
				list = apiAccountInfoMapper.findByCreUserId(Integer.valueOf(creUserId));
			} 
			
			info = list.get(0); 
			
			ApiAccountInfoDomain domain = new ApiAccountInfoDomain();
			
			BeanUtils.copyProperties(info, domain);
			
			result.setResultObj(domain);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户id执行查询api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("数据完整性异常");
		}
		
		return result;
	}

	@Override
	public BackResult<ApiAccountInfoDomain> updateApiAccountInfo(ApiAccountInfoDomain domain) {
		
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		
		try {
			
			List<ApiAccountInfo> list = apiAccountInfoMapper.findByCreUserId(domain.getCreUserId());
			
			if (CommonUtils.isNotEmpty(list)) {
				logger.info("不存在改用户账户信息");
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("数据完整性异常");
				return result;
			}
			
			ApiAccountInfo info = list.get(0);
			
			BeanUtils.copyProperties(domain, info);
			
			apiAccountInfoMapper.update(info);
			
			BeanUtils.copyProperties(info, domain);
			
			result.setResultObj(domain);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户id执行修改api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		
		return result;
	}

}
