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

import com.alibaba.fastjson.JSONObject;

import cn.dao.CreUserAccountMapper;
import cn.dao.CreUserMapper;
import cn.entity.CreUser;
import cn.entity.CreUserAccount;
import cn.redis.RedisClient;
import cn.service.CreUserService;
import cn.utils.CommonUtils;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.CreUserDomain;
import main.java.cn.hhtp.service.SendRequestService;
import main.java.cn.hhtp.util.MD5Util;

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

	@Autowired  
    protected RedisClient redisClinet;  

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
	public synchronized BackResult<CreUserDomain> findOrsaveUser(CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();

		try {
			
			CreUserDomain creUserDomains = new CreUserDomain();

			CreUser user = this.findCreUserByUserPhone(creUserDomain.getUserPhone());

			if (null != user) {
				// 修改最后登录时间 和登录ＩＰ
				user.setLastLoginIp(creUserDomain.getLastLoginIp());
				user.setLastLoginTime(new Date());
				this.updateCreUser(user);
				BeanUtils.copyProperties(user, creUserDomains);
				result.setResultObj(creUserDomains);
				return result;
			} else {
				// 不存在重新注册
				String register_key = "USER_IP_" + creUserDomain.getLastLoginIp().replace(".", "");
				String count  = redisClinet.get(register_key);
				
				if (count == null || count.equals("") || count.equals("null")) {
					count = "0";
					redisClinet.set(register_key, "1",DateUtils.getMiao());
				}
				
				if (Integer.parseInt(redisClinet.get(register_key)) <= 2) {
					CreUser creUser = new CreUser();

					BeanUtils.copyProperties(creUserDomain, creUser);
					creUser.setLastLoginIp(creUserDomain.getLastLoginIp());
					creUser.setLastLoginTime(new Date());

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

					JSONObject json = JSONObject.parseObject(responseStr);

					if (json.get("status").equals("success")) {
						JSONObject data = JSONObject.parseObject(json.get("data").toString());
						creUser.setClAccountId(Integer.parseInt(data.get("id").toString()));
					}

					this.saveCreUser(creUser);
					creUser = this.findCreUserByUserPhone(creUserDomain.getUserPhone());
					BeanUtils.copyProperties(creUser, creUserDomains);
					
					// 赠送5000 条
					CreUserAccount creUserAccount = new CreUserAccount();
					creUserAccount.setAccount(5000); // 充值默认送5000
					creUserAccount.setCreUserId(creUser.getId());
					creUserAccount.setApiAccount(30); // 默认api账户0条
					creUserAccount.setRqAccount(10);
					creUserAccount.setTcAccount(5);
					creUserAccount.setFcAccount(5);
					creUserAccount.setMsAccount(5);
					creUserAccount.setVersion(0);
					creUserAccount.setCreateTime(new Date());
					creUserAccount.setUpdateTime(new Date());
					creUserAccount.setDeleteStatus("0");
					creUserAccountMapper.saveCreUserAccount(creUserAccount);
					
					result.setResultObj(creUserDomains);
					
					// 同一ＩＰ　次数加　１　
					int redisCount = Integer.parseInt(count) + 1;
					redisClinet.set(register_key, String.valueOf(redisCount),DateUtils.getMiao());
				} else {
					result.setResultCode(ResultCode.RESULT_REGISTERFAILED);
					result.setResultMsg("IP地址超过注册限制");
				}
				
			}
			
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
			
			user.setUserEmail(creUserDomain.getUserEmail());
			
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

	@Override
	public BackResult<CreUserDomain> findById(Integer id) {
		
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		
		try {
			CreUser user = creUserMapper.findById(id);
			
			if (null == user) {
				result.setResultMsg("用户信息不存在，或者已经删除请联系客服");
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
			} else {
				CreUserDomain domain = new CreUserDomain();
				BeanUtils.copyProperties(user, domain);
				result.setResultMsg("成功");
				result.setResultObj(domain);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：【" + id + "】获取用户信息异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	@Transactional
	public BackResult<CreUserDomain> updateCreUser(String userPhone, String email) {
		CreUser user = this.findCreUserByUserPhone(userPhone);

		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();
		
		CreUserDomain creuserdomain = new CreUserDomain();
		
		try {
			if (null == user) {
				result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
				result.setResultMsg("用户信息不存在");
				return result;
			}
			
			user.setUserEmail(email);
			
			this.updateCreUser(user);
			
			BeanUtils.copyProperties(user, creuserdomain);
			
			result.setResultObj(creuserdomain);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户手机号：【" + userPhone + "】执行修改操作数据入库异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		
		return result;
	}

	@Transactional
	public BackResult<CreUserDomain> activateUser(CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();

		try {
			
			CreUserDomain creUserDomains = new CreUserDomain();

			CreUser user = this.findCreUserByUserPhone(creUserDomain.getUserPhone());

			if (null != user) {
				BeanUtils.copyProperties(user, creUserDomains);
				result.setResultObj(creUserDomains);
				result.setResultMsg("账户已经激活");
				return result;
			} else {
				// 不存在重新注册
				CreUser creUser = new CreUser();

				BeanUtils.copyProperties(creUserDomain, creUser);
				creUser.setLastLoginIp(creUserDomain.getLastLoginIp());
				creUser.setLastLoginTime(new Date());

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

				JSONObject json = JSONObject.parseObject(responseStr);

				if (json.get("status").equals("success")) {
					JSONObject data = JSONObject.parseObject(json.get("data").toString());
					creUser.setClAccountId(Integer.parseInt(data.get("id").toString()));
				}

				this.saveCreUser(creUser);
				creUser = this.findCreUserByUserPhone(creUserDomain.getUserPhone());
				BeanUtils.copyProperties(creUser, creUserDomains);
				
				// 赠送5000 条
				CreUserAccount creUserAccount = new CreUserAccount();
				creUserAccount.setAccount(5000); // 充值默认送5000
				creUserAccount.setCreUserId(creUser.getId());
				creUserAccount.setApiAccount(30); // 默认api账户30条
				creUserAccount.setRqAccount(10); // 默认账户二次清洗10条
				creUserAccount.setTcAccount(5);
				creUserAccount.setFcAccount(5);
				creUserAccount.setMsAccount(5);
				creUserAccount.setVersion(0);
				creUserAccount.setCreateTime(new Date());
				creUserAccount.setUpdateTime(new Date());
				creUserAccount.setDeleteStatus("0");
				creUserAccountMapper.saveCreUserAccount(creUserAccount);
				
				result.setResultObj(creUserDomains);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户手机号码：【" + creUserDomain.getUserPhone() + "】执行注册操作数据入库异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}

		return result;
	}

	@Override
	public BackResult<CreUserDomain> activateUserZzt(CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = new BackResult<CreUserDomain>();

		try {
			
			CreUserDomain creUserDomains = new CreUserDomain();

			CreUser user = this.findCreUserByUserPhone(creUserDomain.getUserPhone());

			if (null != user) {
				BeanUtils.copyProperties(user, creUserDomains);
				result.setResultObj(creUserDomains);
				result.setResultMsg("账户已经激活");
				return result;
			} else {
				// 不存在重新注册
				CreUser creUser = new CreUser();

				BeanUtils.copyProperties(creUserDomain, creUser);
				creUser.setLastLoginIp(creUserDomain.getLastLoginIp());
				creUser.setLastLoginTime(new Date());
				this.saveCreUser(creUser);
				creUser = this.findCreUserByUserPhone(creUserDomain.getUserPhone());
				BeanUtils.copyProperties(creUser, creUserDomains);
				
				// 赠送5000 条
				CreUserAccount creUserAccount = new CreUserAccount();
				creUserAccount.setAccount(5000); // 充值默认送5000
				creUserAccount.setCreUserId(creUser.getId());
				creUserAccount.setApiAccount(0); // 默认api账户0条
				creUserAccount.setRqAccount(10); // 默认账户二次清洗10条
				creUserAccount.setTcAccount(5);
				creUserAccount.setFcAccount(5);
				creUserAccount.setMsAccount(5);
				creUserAccount.setVersion(0);
				creUserAccount.setCreateTime(new Date());
				creUserAccount.setUpdateTime(new Date());
				creUserAccount.setDeleteStatus("0");
				creUserAccountMapper.saveCreUserAccount(creUserAccount);
				
				result.setResultObj(creUserDomains);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户手机号码：【" + creUserDomain.getUserPhone() + "】执行注册操作数据入库异常！" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据入库失败");
		}

		return result;
	}

}
