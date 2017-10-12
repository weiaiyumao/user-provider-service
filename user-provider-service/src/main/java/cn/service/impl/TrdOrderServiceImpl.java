package cn.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import main.java.cn.sms.util.ChuangLanSmsUtil;
import net.sf.json.JSONObject;

@Service
public class TrdOrderServiceImpl implements TrdOrderService {

	private final static Logger logger = LoggerFactory.getLogger(TrdOrderServiceImpl.class);

	@Autowired
	private TrdOrderMapper trdOrderMapper;

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;

	@Autowired
	private CreUserMapper creUserMapper;

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
			account.setAccount(account.getAccount() + order.getNumber());
			creUserAccountMapper.updateCreUserAccount(account);

			// 发送短信 提示 客户充值 成功
			CreUser user = creUserMapper.findById(order.getCreUserId());
			ChuangLanSmsUtil.getInstance().sendSmsByMobileForRecharge(user.getUserPhone(), order.getNumber());
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
			
			String orderNo = "CLSH_" + System.currentTimeMillis();
			// 生成订单
			TrdOrder order = new TrdOrder();
			order.setCreUserId(creUserId);
			order.setProductsId(productsId);
			
			
			if (productsId == 1) {
				order.setNumber(500000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(950));
			} 
			
			if (productsId == 2) {
				order.setNumber(5000000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(9000));
			} 
			
			if (productsId == 3) {
				order.setNumber(10000000);  // 根据条数计算 具体金额
				order.setMoney(new BigDecimal(16000));
			} 
			
			if (productsId == 4) {
				order.setNumber(number);  
				BigDecimal b1 = new BigDecimal(number);   
				BigDecimal b2 = new BigDecimal(0.002);  
				order.setMoney(b1.multiply(b2).setScale(2,BigDecimal.ROUND_HALF_UP));
			}
			
			
			order.setPayType(payType);
			order.setType(type);
			order.setOrderNo(orderNo);
			order.setStatus(Constant.TRD_ORDER_STATUS_PROCESSING);
			order.setDeleteStatus("0");
			order.setVersion(0);
			order.setCreateTime(new Date());
			order.setUpdateTime(new Date());
			this.saveTrdOrder(order);
			
			// 向支付宝发送请求 生成二维码
			AlipayClient alipayClient = new DefaultAlipayClient(alipayPayurl, alipayAppid, alipayPrivatekey,"json","utf-8", alipayPublickey, "RSA2");
			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest(); // 创建API对应的request类
			String subject = "创蓝实号检测产品";
			String storeId = "创蓝数据";
			request.setBizContent("{" + "    \"out_trade_no\":\""+orderNo+"\"," + "    \"total_amount\":\""+order.getMoney()+"\","
					+ "    \"subject\":\""+subject+"\"," + "    \"store_id\":\""+storeId+"\","
							+ "    \"timeout_express\":\"90m\"}");// 设置业务参数

			request.setNotifyUrl(alipayCallbackurl);
			
			logger.info("支付申请参数：{out_trade_no:"+orderNo+"total_amount:"+order.getMoney()+"subject:"+subject+"store_id:"+storeId+"}");
			
			AlipayTradePrecreateResponse response = alipayClient.execute(request);

			if (response.isSuccess()) {
				
				JSONObject responseBody = JSONObject.fromObject(response.getBody());
				
				JSONObject json = JSONObject.fromObject(responseBody.get("alipay_trade_precreate_response"));
				
				if (json.get("code").equals("10000")) {
					
					JSONObject resultObj = new JSONObject();
					resultObj.put("payUrl", json.get("qr_code").toString());
					resultObj.put("orderNo", orderNo);
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
