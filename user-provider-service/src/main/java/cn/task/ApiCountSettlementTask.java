package cn.task;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.entity.CreUserAccount;
import cn.redis.RedisClient;
import cn.service.CreUserAccountService;
import cn.utils.CommonUtils;
import cn.utils.DateUtils;
import main.java.cn.common.RedisKeys;

/**
 * API接口结算任务
 * 
 * @author ChuangLan
 *
 */
@Component
@Configuration
@EnableScheduling
public class ApiCountSettlementTask {

	private final static Logger logger = LoggerFactory.getLogger(ApiCountSettlementTask.class);

	@Autowired
	private RedisClient redisClient;

	@Autowired
	private CreUserAccountService creUserAccountService;
	
	@Value("${cljobchange}")
	String cljobchange;


	/**
	 * 空号API结算任务
	 * 
	 * @throws IOException
	 */
	@Scheduled(cron = "0 0/2 * * * ?")
	public void settlementKHApiCount() throws IOException {
		
		if (cljobchange.equals("produced")) {
			logger.info("<<<<<<<<<<开始空号API结算定时任务>>>>>>>>>>");

			try {
				// 获取keys队列中需要结算的用户
				String KHAPIcountKeys = RedisKeys.getInstance().getKHAPIcountKeys();
				String keys = redisClient.get(KHAPIcountKeys);
				if (!CommonUtils.isNotString(keys)) {
					// 循环扣费
					String[] userIds = keys.split(",");

					for (String userId : userIds) {
						CreUserAccount account = creUserAccountService.findCreUserAccountByUserId(Integer.parseInt(userId));
						// 获取redis中的剩余可以使用的条数
						String count = redisClient.get(RedisKeys.getInstance().getKHAPIcountKey(userId));
						logger.info(
								"》》》》》用户[" + userId + "]本次结算空号API账户剩余：" + account.getApiAccount() + "条，本次结算：" + (account.getApiAccount() - Integer.parseInt(count)) + "条");
						if (!CommonUtils.isNotString(count)) {
							account.setApiAccount(Integer.parseInt(count));
							account.setUpdateTime(DateUtils.getCurrentDateTime());
						}
					
						creUserAccountService.updateCreUserAccount(account);
						logger.info("》》》》》用户[" + userId + "]本次结算完成");
						
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("空号API结算定时任务>>>>>>>>>>出现系统异常：" + e.getMessage());
			}

			logger.info("<<<<<<<<<<结束空号API结算定时任务>>>>>>>>>>");
		}
		
	}

	/**
	 * 账户二次清洗API结算任务
	 * 
	 * @throws IOException
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void settlementRQApiCount() throws IOException {
		
		if (cljobchange.equals("produced")) { 
			logger.info("<<<<<<<<<<-----开始账户二次清洗API结算定时任务----->>>>>>>>>>");

			try {
				// 获取keys队列中需要结算的用户
				String RQAPIcountKeys = RedisKeys.getInstance().getRQAPIcountKeys();
				String keys = redisClient.get(RQAPIcountKeys);
				if (!CommonUtils.isNotString(keys)) {
					// 循环扣费
					String[] userIds = keys.split(",");

					for (String userId : userIds) {
						CreUserAccount account = creUserAccountService.findCreUserAccountByUserId(Integer.parseInt(userId));
						// 获取redis中的剩余可以使用的条数
						String count = redisClient.get(RedisKeys.getInstance().getRQAPIcountKey(userId));
						logger.info(
								"》》》》》用户[" + userId + "]本次结算账户二次清洗API账户剩余：" + account.getRqAccount() + "条，本次结算：" + (account.getApiAccount() - Integer.parseInt(count)) + "条");
						if (!CommonUtils.isNotString(count)) {
							account.setRqAccount(Integer.parseInt(count));
							account.setUpdateTime(DateUtils.getCurrentDateTime());
						}
						
						creUserAccountService.updateCreUserAccount(account);
						logger.info("》》》》》用户[" + userId + "]本次结算完成");
						
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("二次清洗API结算定时任务>>>>>>>>>>出现系统异常：" + e.getMessage());
			}

			logger.info("<<<<<<<<<<结束账户二次清洗API结算定时任务>>>>>>>>>>");
		}
		
	}
}
