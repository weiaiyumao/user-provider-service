package cn.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.common.SysCode;
import cn.redis.DistributedLock;
import cn.service.CreUserAccountService;
import main.java.cn.domain.UserAccountDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.ApiAccountInfoMapper;
import cn.entity.ApiAccountInfo;
import cn.redis.RedisClient;
import cn.service.ApiAccountInfoService;
import cn.utils.CommonUtils;
import cn.utils.UUIDTool;
import main.java.cn.common.BackResult;
import main.java.cn.common.RedisKeys;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;
import redis.clients.jedis.JedisPool;

@Service
public class ApiAccountInfoServiceImpl implements ApiAccountInfoService {

	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoServiceImpl.class);

	@Autowired
	private ApiAccountInfoMapper apiAccountInfoMapper;
	
	@Autowired
	private CreUserAccountService ceUserAccountService;
	
	@Autowired
	private RedisClient redisClient;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private RedisTemplate<String, ApiAccountInfoDomain> redisTemplate;

	@Override
	public ApiAccountInfo findByCreUserIdAndName(Integer creUserId, String name) {

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

				info.setCreUserId(Integer.valueOf(creUserId));
				info.setName("S" + UUIDTool.getInstance().getUUID8());
				// 检测数据库是否存在重复
				List<ApiAccountInfo> Infolist = apiAccountInfoMapper.findByCreUserIdAndName(info.getCreUserId(),
						info.getName());

				if (CommonUtils.isNotEmpty(Infolist)) {
					info.setPassword("pwd" + UUIDTool.getInstance().getUUID8());
					info.setCreateTime(new Date());
					apiAccountInfoMapper.save(info);
					list = apiAccountInfoMapper.findByCreUserId(Integer.valueOf(creUserId));
				} else {
					list = Infolist;
				}

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

	@Transactional
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

			domain.setVersion(list.get(0).getVersion());
			apiAccountInfoMapper.updateDomain(domain);

			result.setResultObj(domain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户id执行修改api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}

		return result;
	}

	@Override
	public BackResult<ApiAccountInfoDomain> findByNameAndPwd(String name, String password) {

		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();

		try {
			List<ApiAccountInfo> list = apiAccountInfoMapper.findByNameAndPwd(name, password);

			if (CommonUtils.isNotEmpty(list)) {
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
				result.setResultMsg("账户信息不存在或者已经删除，请联系客服");
				return result;
			}

			ApiAccountInfoDomain domain = new ApiAccountInfoDomain();

			BeanUtils.copyProperties(list.get(0), domain);

			result.setResultMsg("获取成功");
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("根据用户名：" + name + "获取用户api账户信息异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}

		return result;
	}

    @Override
    public ApiAccountInfoDomain getApiAccountInfoByName(String name,String password) {

	    // 先从redis中获取
        String skey = RedisKeys.getInstance().getAPIAccountInfoKey(name);
        ApiAccountInfoDomain domain = redisTemplate.opsForValue().get(skey);
        if (null == domain) {
            List<ApiAccountInfo> list = apiAccountInfoMapper.findByNameAndPwd(name, password);
            if (CommonUtils.isNotEmpty(list)) {
                return null;
            }
            domain = new ApiAccountInfoDomain();
            BeanUtils.copyProperties(list.get(0), domain);
            redisTemplate.opsForValue().set(skey, domain, 30 * 60, TimeUnit.SECONDS);
        }

        return domain;
    }

    @Override
	public BackResult<Integer> checkApiAccount(String apiName, String password, String ip,
			int checkCount,String mobile,String type) {

        String lockName = RedisKeys.getInstance().getkhApifunUserKey(mobile);
        DistributedLock lock = new DistributedLock(jedisPool);
        // 加锁
        String identifier = lock.lockWithTimeout(lockName, 10L, 3 * 1000);
		BackResult<Integer> result = new BackResult<Integer>();
		try {

            // 处理加锁业务
            if (null != identifier) {
                // 1、检测api账户信息
                ApiAccountInfoDomain info = this.getApiAccountInfoByName(apiName, password);
                if (null == info) {
                    result.setResultCode(ResultCode.RESULT_API_NOTACCOUNT);
                    result.setResultMsg("API商户信息不存在，或者已经删除请联系数据中心客户人员！");
                    return result;
                }

                // 2、检测api账户ip绑定信息 如果商户设置了ip则进行验证 反之不验证改参数
                if (!CommonUtils.isNotString(info.getBdIp())) {

                    Boolean fag = false;
                    String[] ips = info.getBdIp().split(",");
                    for (String str : ips) {
                        if (str.equals(ip)) {
                            fag = true;
                        }
                    }

                    if (!fag) {
                        result.setResultCode(ResultCode.RESULT_API_NOTIPS);
                        result.setResultMsg("API商户绑定的IP地址验证校验失败！");
                        return result;
                    }

                }

                // 3、检测账户条数信息
                UserAccountDomain account = ceUserAccountService.loadCreUserAccountByUserId(info.getCreUserId());

                if (null == account) {
                    result.setResultCode(ResultCode.RESULT_API_NOTACCOUNT);
                    result.setResultMsg("API商户信息不存在，或者已经删除请联系数据中心客户人员！");
                    logger.error("用户id为：" + info.getCreUserId() + "的账户出现数据完整性异常");
                    return result;
                }

                // 4、将key 加入keys队列 定时任务定时结算条数
                String KHAPIcountKeys = RedisKeys.getInstance().getKHAPIcountKeys();
                String keys = redisClient.get(KHAPIcountKeys);
                if (CommonUtils.isNotString(keys)) {
                    redisClient.set(KHAPIcountKeys, info.getCreUserId().toString() + ",");
                } else {

                    // 查看队列中是否存在
                    String[] khkey = keys.split(",");

                    Boolean fag = false;
                    for (String string : khkey) {
                        if (string.equals(info.getCreUserId().toString())) {
                            // 存在
                            fag = true;
                        }
                    }

                    // 不存在
                    if (!fag) {
                        redisClient.set(KHAPIcountKeys, keys + info.getCreUserId().toString() + ",");
                    }

                }

                // 5、条数检测
                if (type.equals(SysCode.kh_api_type)) {
                    if (account.getApiAccount() < checkCount) {
                        result.setResultCode(ResultCode.RESULT_API_NOTCOUNT);
                        result.setResultMsg("API商户信息剩余可消费条数为：" + account.getApiAccount() + "本次执行消费：" + checkCount + "无法执行消费，请充值！");
                        return result;
                    }
                } else if (type.equals(SysCode.rq_api_type)) {
                    if (account.getRqAccount() < checkCount) {
                        result.setResultCode(ResultCode.RESULT_API_NOTCOUNT);
                        result.setResultMsg("账户二次清洗API商户信息剩余可消费条数为：" + account.getRqAccount() + "本次执行消费：" + checkCount + "无法执行消费，请充值！");
                        return result;
                    }
                }

                result.setResultObj(info.getCreUserId());
                result.setResultMsg("账户检测正常");
            } else {
                result.setResultCode(ResultCode.RESULT_API_NOTCONCURRENT);
                result.setResultMsg("正在计算中");
            }


		} catch (Exception e) {
			e.printStackTrace();
			logger.error("检测API账户[" + apiName + "]信息出现系统异常" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		} finally {
            lock.releaseLock(lockName, identifier); // 销毁锁
        }

		return result;
	}

	@Deprecated
	@Override
	public BackResult<Integer> checkRqApiAccount(String apiName, String password, String ip,int checkCount) {
		BackResult<Integer> result = new BackResult<Integer>();
//		try {
//
//			// 1、检测账户二次清洗api账户信息
//			List<ApiAccountInfo> list = apiAccountInfoMapper.findByNameAndPwd(apiName, password);
//			if (CommonUtils.isNotEmpty(list)) {
//				result.setResultCode(ResultCode.RESULT_API_NOTACCOUNT);
//				result.setResultMsg("账户二次清洗API商户信息不存在，或者已经删除请联系数据中心客户人员！");
//				return result;
//			}
//
//			// 2、检测账户二次清洗api账户ip绑定信息 如果商户设置了ip则进行验证 反之不验证改参数
//			if (!CommonUtils.isNotString(list.get(0).getBdIp())) {
//
//				//
//				Boolean fag = false;
//
//				String[] ips = list.get(0).getBdIp().split(",");
//
//				for (String str : ips) {
//
//					if (str.equals(ip)) {
//						fag = true;
//					}
//
//				}
//
//				if (!fag) {
//					result.setResultCode(ResultCode.RESULT_API_NOTIPS);
//					result.setResultMsg("账户二次清洗API商户绑定的IP地址验证校验失败！");
//					return result;
//				}
//
//			}
//
//			// 从redis中获取可以使用的条数 如果没有取数据库中的
//			String RQAPIcountKey = RedisKeys.getInstance().getRQAPIcountKey(list.get(0).getCreUserId().toString());
//			String count = redisClient.get(RQAPIcountKey);
//
//			if (CommonUtils.isNotString(count)) {
//				// 3、检测剩余可消费条数信息
//				List<CreUserAccount> listUserAccount = creUserAccountMapper.findCreUserAccountByUserId(list.get(0).getCreUserId());
//
//				if (CommonUtils.isNotEmpty(listUserAccount)) {
//					result.setResultCode(ResultCode.RESULT_API_NOTACCOUNT);
//					result.setResultMsg("账户二次清洗API商户信息不存在，或者已经删除请联系数据中心客户人员！");
//					logger.error("用户id为：" + list.get(0).getCreUserId() + "的账户出现数据完整性异常");
//					return result;
//				}
//
//				if (listUserAccount.get(0).getRqAccount() < checkCount) {
//					result.setResultCode(ResultCode.RESULT_API_NOTCOUNT);
//					result.setResultMsg("账户二次清洗API商户信息剩余可消费条数为：" + listUserAccount.get(0).getApiAccount() + "本次执行消费：" + checkCount + "无法执行消费，请充值！");
//					return result;
//				}
//
//
//				// 将key 加入keys队列
//				String RQAPIcountKeys = RedisKeys.getInstance().getRQAPIcountKeys();
//				String keys = redisClient.get(RQAPIcountKeys);
//				if (CommonUtils.isNotString(keys)) {
//					redisClient.set(RQAPIcountKeys, list.get(0).getCreUserId().toString() + ",");
//				} else {
//					redisClient.set(RQAPIcountKeys, keys + list.get(0).getCreUserId().toString() + ",");
//				}
//
//				// 设置剩余条数到redis
//				redisClient.set(RQAPIcountKey, listUserAccount.get(0).getRqAccount().toString());
//			} else {
//				if (Integer.valueOf(count) < checkCount) {
//					result.setResultCode(ResultCode.RESULT_API_NOTCOUNT);
//					result.setResultMsg("账户二次清洗API商户信息剩余可消费条数为：" + count + "本次执行消费：" + checkCount + "无法执行消费，请充值！");
//					return result;
//				}
//			}
//
//
//
//			result.setResultObj(list.get(0).getCreUserId());
//			result.setResultMsg("账户检测正常");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("检测账户二次清洗API账户[" + apiName + "]信息出现系统异常" + e.getMessage());
//			result.setResultCode(ResultCode.RESULT_FAILED);
//			result.setResultMsg("系统异常");
//		}
//
		return result;
	}



}
