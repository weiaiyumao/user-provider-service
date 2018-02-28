package cn.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import main.java.cn.common.RedisKeys;
import main.java.cn.domain.UserAccountDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;

import cn.dao.CreUserAccountMapper;
import cn.dao.CreUserMapper;
import cn.dao.TrdOrderMapper;
import cn.entity.CreUser;
import cn.entity.CreUserAccount;
import cn.entity.TrdOrder;
import cn.service.TrdOrderService;
import cn.utils.CommonUtils;
import cn.utils.Constant;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.hhtp.util.HttpUtil;
import main.java.cn.hhtp.util.MD5Util;
import main.java.cn.sms.util.ChuangLanSmsUtil;

@Service
public class TrdOrderServiceImpl implements TrdOrderService {

	private final static Logger logger = LoggerFactory.getLogger(TrdOrderServiceImpl.class);

	@Autowired
	private TrdOrderMapper trdOrderMapper;

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;

	@Autowired
	private CreUserMapper creUserMapper;

	@Autowired
	private RedisTemplate<String, UserAccountDomain> redisTemplate;

	@Value("${alipay_appid}")
	private String alipayAppid;

	@Value("${alipay_payurl}")
	private String alipayPayurl;

	@Value("${alipay_publickey}")
	private String alipayPublickey;

	@Value("${alipay_privatekey}")
	private String alipayPrivatekey;

	@Value("${alipay_callbackurl}")
	private String alipayCallbackurl;
	
	@Value("${api_host}")
	private String apiHost;

	@Value("${api_key}")
	private String apiKey;

	@Value("${order}")
	private String orderUrl;
	
	@Override
	public TrdOrder findByOrderNo(String orderNo) {
		List<TrdOrder> list = trdOrderMapper.findByOrderNo(orderNo);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public TrdOrder findByClOrderNo(String clOrderNo) {
		List<TrdOrder> list = trdOrderMapper.findByClOrderNo(clOrderNo);
		return CommonUtils.isNotEmpty(list) ? null : list.get(0);
	}

	@Override
	public int saveTrdOrder(TrdOrder trdOrder) {
		return trdOrderMapper.saveTrdOrder(trdOrder);
	}

	@Override
	public int updateTrdOrder(TrdOrder trdOrder) {
		return trdOrderMapper.updateTrdOrder(trdOrder);
	}

	@Transactional
	public synchronized void payCallBack(String orderNo, String status, String traOrder) {

		// 检测订单完整度
		TrdOrder order = this.findByOrderNo(orderNo);

		if (null == order) {
			logger.error("订单号：" + orderNo + "数据库不存在改数据");
		}

		if ("TRADE_SUCCESS".equals(status)) {

			// 根据根据回调状态修改 订单状态
			order.setStatus(Constant.TRD_ORDER_STATUS_SUCCEED);
			order.setTradeNo(traOrder); // 第三方支付号
			order.setPayTime(new Date()); // 交易成功时间
			order.setUpdateTime(new Date()); 
			this.updateTrdOrder(order);
			
			// 客户账户条数增加
			List<CreUserAccount> accountList = creUserAccountMapper.findCreUserAccountByUserId(order.getCreUserId());
			CreUserAccount account = accountList.get(0);
			
			if(orderNo.substring(0,4).equals("CLRQ")) {
				// 账号二次清洗充值回调
                account.setRqAccount(account.getAccount() + order.getNumber());
			}
			if (orderNo.substring(0,4).equals("CLSH")) {
				// 空号检测充值回调
				account.setAccount(account.getAccount() + order.getNumber());
			}
			if (orderNo.substring(0,4).equals("CLKH")) {
                // 空号API充值回调
                account.setApiAccount(account.getApiAccount() + order.getNumber());
            }

			creUserAccountMapper.updateCreUserAccount(account);

			// 更新redis客户的账户余额信息
			UserAccountDomain domain = new UserAccountDomain();
			String skey = RedisKeys.getInstance().getAPIAccountKey(account.getCreUserId());
			domain = redisTemplate.opsForValue().get(skey);
			if (null == domain) {
				BeanUtils.copyProperties(account, domain);
				redisTemplate.opsForValue().set(skey, domain, 30 * 60, TimeUnit.SECONDS);
			} else {

				if(orderNo.substring(0,4).equals("CLRQ")) {
					// 账号二次清洗充值回调
					domain.setRqAccount(domain.getRqAccount() + order.getNumber());
				}

				if (orderNo.substring(0,4).equals("CLSH")) {
					// 空号检测充值回调
					domain.setApiAccount(domain.getApiAccount() + order.getNumber());
				}

				if (orderNo.substring(0,4).equals("CLKH")) {
					// 空号API充值回调
					account.setAccount(account.getAccount() + order.getNumber());
				}

				// 更新redis
				redisTemplate.opsForValue().set(skey, domain, 30 * 60, TimeUnit.SECONDS);
			}

			// 发送短信 提示 客户充值 成功
			CreUser user = creUserMapper.findById(order.getCreUserId());
			ChuangLanSmsUtil.getInstance().sendSmsByMobileForRecharge(user.getUserPhone(), order.getNumber());
			
			try {
				// 给erp 推送下单成功消息
				JSONObject jsonAccount = new JSONObject();
				String timestamp = String.valueOf(System.currentTimeMillis()).substring(0, 10);
				String tokenValue = MD5Util.getInstance().getMD5Code(timestamp + apiKey);
				jsonAccount.put("account_name", user.getUserPhone());
				jsonAccount.put("money", order.getMoney());
				jsonAccount.put("amount", order.getNumber());
				jsonAccount.put("bank", "5");
				jsonAccount.put("pay_mode", "1");
				jsonAccount.put("pay_date", String.valueOf(order.getPayTime().getTime()).substring(0,10));
				jsonAccount.put("remark", "手机号：" + user.getUserPhone() + "客户充值成功");
				jsonAccount.put("sequence", order.getTradeNo());
				jsonAccount.put("timestamp", timestamp);
				jsonAccount.put("token", tokenValue);
				logger.info("下单成功,请求参数:" + jsonAccount);
				String responseStr = HttpUtil.createHttpPost(apiHost + orderUrl, jsonAccount);
				logger.info("下单成功,请求结果:" + responseStr);
				JSONObject json = JSONObject.parseObject(responseStr);
				if (json.get("status").equals("success")) {
					// erp 请求成功 记录erpid
					JSONObject data = JSONObject.parseObject(json.get("data").toString());
					order.setClOrderNo("ERPID_" + data.get("id").toString());
					this.updateTrdOrder(order);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("订单号：" + orderNo + "请求erp下单数据异常：" + e.getMessage());
			}
			
		} else {
			// 根据根据回调状态修改 订单状态
			order.setStatus(Constant.TRD_ORDER_STATUS_FAILED);
			this.updateTrdOrder(order);
		}

	}

	@Transactional
	public synchronized BackResult<String> recharge(Integer creUserId,Integer productsId,Integer number,BigDecimal money,String payType,String type) {
		
		BackResult<String> result = new BackResult<String>();
		try {

			String orderCode = "";
			String subjectName = "";
			String timestamp = String.valueOf(System.currentTimeMillis());
			// 生成订单
			TrdOrder order = new TrdOrder();
			order.setCreUserId(creUserId);
			order.setProductsId(productsId);
			
			// 空号检测产品
			if (productsId == 1) {
				order.setNumber(500000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(950));
				orderCode = "CLSH_";
				subjectName="创蓝实号检测产品";
			} 
			
			if (productsId == 2) {
				order.setNumber(5000000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(9000));
				orderCode = "CLSH_";
				subjectName="创蓝实号检测产品";
			} 
			
			if (productsId == 3) {
				order.setNumber(10000000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(16000));
				orderCode = "CLSH_";
				subjectName="创蓝实号检测产品";
			} 
			
			if (productsId == 4) {
				order.setNumber(number);  
				BigDecimal b1 = new BigDecimal(number);   
				BigDecimal b2 = new BigDecimal(0.002);  
				order.setMoney(b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP));
				orderCode = "CLSH_";
				subjectName="创蓝实号检测产品";
			}
			
			// 账号二次清洗产品
			if (productsId == 5) {
				order.setNumber(100000 * 1);
				order.setMoney(new BigDecimal(1900)); // 充值金额1900---10万条
				orderCode = "CLRQ_";
				subjectName="创蓝账号二次清洗";
			}
			
			if (productsId == 6) {
				order.setNumber(100000 * 5);
				order.setMoney(new BigDecimal(9000)); // 充值金额9000---50万条
				orderCode = "CLRQ_";
				subjectName="创蓝账号二次清洗";
			}
			
			if (productsId == 7) {
				order.setNumber(100000 * 10);
				order.setMoney(new BigDecimal(16000)); // 充值金额16000---100万条
				orderCode = "CLRQ_";
				subjectName="创蓝账号二次清洗";
			}
			
			if (productsId == 8) {
				order.setNumber(number);  
				BigDecimal b1 = new BigDecimal(number);   
				BigDecimal b2 = new BigDecimal(0.02);  // 单价2分钱一条
				order.setMoney(b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP));
				orderCode = "CLRQ_";
				subjectName="创蓝账号二次清洗";
			}

			// 空号API产品
			if (productsId == 9) {
				order.setNumber(4000000 * 1);
				order.setMoney(new BigDecimal(19000)); // 充值金额19000---400万条 9.5
				orderCode = "CLKH_";
				subjectName="创蓝空号API产品";
			}

			if (productsId == 10) {
				order.setNumber(10000000 * 1);
				order.setMoney(new BigDecimal(45000)); // 充值金额45000---1000万条 9
				orderCode = "CLKH_";
				subjectName="创蓝空号API产品";
			}

			if (productsId == 11) {
				order.setNumber(20000000 * 1);
				order.setMoney(new BigDecimal(80000)); // 充值金额80000---2000万条 8
				orderCode = "CLKH_";
				subjectName="创蓝空号API产品";
			}
			
			order.setPayType(payType);
			order.setType(type);
			order.setOrderNo(orderCode + timestamp);
			order.setStatus(Constant.TRD_ORDER_STATUS_PROCESSING);
			order.setDeleteStatus("0");
			order.setVersion(0);
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			this.saveTrdOrder(order);
			
			// 向支付宝发送请求 生成二维码
			AlipayClient alipayClient = new DefaultAlipayClient(alipayPayurl, alipayAppid, alipayPrivatekey,"json","utf-8", alipayPublickey, "RSA2");
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); // 创建API对应的request类
			String subject = subjectName;
			String storeId = "创蓝数据";
			request.setBizContent("{" + "    \"out_trade_no\":\""+order.getOrderNo()+"\"," + "    \"total_amount\":\""+order.getMoney()+"\","
					+ "    \"subject\":\""+subject+"\"," + "    \"store_id\":\""+storeId+"\","
							+ "    \"timeout_express\":\"90m\"}");// 设置业务参数

			request.setNotifyUrl(alipayCallbackurl);
			
			logger.info("支付申请参数：{out_trade_no:"+order.getOrderNo()+"total_amount:"+order.getMoney()+"subject:"+subject+"store_id:"+storeId+"}");
			
			AlipayTradePrecreateResponse response = alipayClient.execute(request);

			if (response.isSuccess()) {
				
				JSONObject responseBody = JSONObject.parseObject(response.getBody());
				
				JSONObject json = JSONObject.parseObject(responseBody.get("alipay_trade_precreate_response").toString());
				
				if (json.get("code").equals("10000")) {
					
					JSONObject resultObj = new JSONObject();
					resultObj.put("payUrl", json.get("qr_code").toString());
					resultObj.put("orderNo", order.getOrderNo());
					result.setResultObj(resultObj.toString());
					
				}
				
			} else {
				logger.error("申请支付异常：【" + response.getBody() + "】");
				result.setResultCode(ResultCode.RESULT_BUSINESS_EXCEPTIONS);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("申请支付系统异常：【" + e.getMessage() + "】");
			result.setResultCode(ResultCode.RESULT_FAILED);
		}
		
		return result;
	}

	@Override
	public BackResult<TrdOrderDomain> findOrderInfoByOrderNo(String orderNo) {
		
		BackResult<TrdOrderDomain> result = new BackResult<TrdOrderDomain>();
		
		TrdOrder order = this.findByOrderNo(orderNo);
		
		if (null == order) {
			result.setResultCode(ResultCode.RESULT_DATA_EXCEPTIONS);
			result.setResultMsg("不存在该订单");
		} else {
			
			TrdOrderDomain trdOrderDomain = new TrdOrderDomain();
			BeanUtils.copyProperties(order, trdOrderDomain);
			result.setResultObj(trdOrderDomain);
		}
		
		return result;
	}
	
}
