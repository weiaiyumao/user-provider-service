package cn.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserAccountMapper;
import cn.dao.CreUserMapper;
import cn.entity.CreUser;
import cn.entity.CreUserAccount;
import cn.service.CreUserService;
import cn.utils.CommonUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.CreUserDomain;
import main.java.cn.hhtp.service.SendRequestService;
import main.java.cn.hhtp.util.MD5Util;
import net.sf.json.JSONObject;

@Service
public class CreUserServiceImpl implements CreUserService {

	private final static Logger logger = LoggerFactory.getLogger(CreUserServiceImpl.class);

	@Autowired
	private CreUserMapper creUserMapper;
	
	@Autowired
	private CreUserAccountMapper creUserAccountMapper;

	@Value("${api_host}")
	private String apiHost;

	@Value("${api_key}")
	private String apiKey;

	@Value("${activePlatformAccount}")
	private String activePlatformAccount;

//	@Autowired
//	private RedisTemplate redisTemplate;

	@Override
	public CreUser findCreUserByUserPhone(String userPhone) {
		List<CreUser> list = creUserMapper.findCreUserByUserPhone(userPhone);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public int saveCreUser(CreUser creUser) {

		int row = 0;
		
//		RedisLock lock = new RedisLock(redisTemplate, "userSave_" + creUser.getUserPhone(), 0, 0);

		try {
			CreUser user = this.findCreUserByUserPhone(creUser.getUserPhone());

			if (null != user) {
				logger.error("手机号为：" + creUser.getUserPhone() + "的账户已经注册成功");
				return 1;
			}

			creUser.setCreateTime(new Date());
			creUser.setUpdateTime(new Date());
			creUserMapper.saveCreUser(creUser);
			
		} catch (Exception e) {
			logger.error("用户手机号码：【" + creUser.getUserPhone() + "】执行数据入库操作系统异常！" + e.getMessage());
		} finally {
//			lock.unlock();
		}

		return row;
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
			logger.error("用户手机号码：【" + mobile + "】执行查询操作系统异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}

		return result;
	}

	@Transactional
	public BackResult<CreUserDomain> findOrsaveUser(CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();

		try {
			
			CreUserDomain creUserDomains = new CreUserDomain();

			CreUser user = this.findCreUserByUserPhone(creUserDomain.getUserPhone());

			if (null != user) {
				BeanUtils.copyProperties(user, creUserDomains);
				result.setResultObj(creUserDomains);
				return result;
			}
			
			// 不存在重新注册

			CreUser creUser = new CreUser();

			BeanUtils.copyProperties(creUserDomain, creUser);

			JSONObject josnObject = new JSONObject();
			String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
			String tokenValue = MD5Util.getInstance().getMD5Code(timestamp + apiKey);
			josnObject.put("account_name", creUserDomain.getUserPhone());
			josnObject.put("timestamp", timestamp);
			josnObject.put("token", tokenValue);

			logger.info("接口请求参数" + josnObject.toString());
			String responseStr = SendRequestService.getInstance().sendRequest(apiHost + activePlatformAccount,
					josnObject);
			logger.info("接口返回结果" + responseStr);

			JSONObject json = JSONObject.fromObject(responseStr);

			if (json.get("status").equals("success")) {
				JSONObject data = JSONObject.fromObject(json.get("data"));
				creUser.setClAccountId(Integer.parseInt(data.get("id").toString()));
			}

			this.saveCreUser(creUser);
			creUser = this.findCreUserByUserPhone(creUserDomain.getUserPhone());
			BeanUtils.copyProperties(creUser, creUserDomains);
			
			// 赠送5000 条
			CreUserAccount creUserAccount = new CreUserAccount();
			creUserAccount.setAccount(5000);
			creUserAccount.setCreUserId(creUser.getId());
			creUserAccount.setVersion(0);
			creUserAccount.setCreateTime(new Date());
			creUserAccount.setUpdateTime(new Date());
			creUserAccount.setDeleteStatus("0");
			creUserAccountMapper.saveCreUserAccount(creUserAccount);
			
			result.setResultObj(creUserDomains);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户手机号码：【" + creUserDomain.getUserPhone() + "】执行注册操作数据入库异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}

		return result;
	}

	@Transactional
	public BackResult<CreUserDomain> updateCreUser(CreUserDomain creUserDomain) {
		
		CreUser user = this.findCreUserByUserPhone(creUserDomain.getUserPhone());

		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		
		CreUserDomain creuserdomain = new CreUserDomain();
		
		try {
			if (null == user) {
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("用户信息不存在");
				return result;
			}
			
			BeanUtils.copyProperties(creUserDomain, user);
			
			this.updateCreUser(user);
			
			BeanUtils.copyProperties(user, creuserdomain);
			
			result.setResultObj(creuserdomain);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：【" + creUserDomain.getId() + "】执行修改操作数据入库异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		
		return result;
	}

}
