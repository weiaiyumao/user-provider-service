package cn.service.impl.tds;


import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsHomeMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.CreUserAccount;
import cn.entity.tds.TdsUser;
import cn.service.CreUserAccountService;
import cn.service.TrdOrderService;
import cn.service.tds.TdsHomeService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;

@Service
public class TdsHomeServiceImpl implements  TdsHomeService {
	
	private final static Logger logger = LoggerFactory.getLogger(TdsHomeServiceImpl.class);
	
	@Autowired
	private TdsHomeMapper tdsHomeMapper;
	
	@Autowired
	private TrdOrderService trdOrderService;
	
	@Autowired
	private CreUserAccountService creUserAccountService;
	
	@Autowired
    private TdsUserMapper tdsUserMapper;
	
	@Override
	public BackResult<Map<String, Object>> countByUserId(Integer userId) {
		BackResult<Map<String, Object>> result=new BackResult<>();
		try {
			Map<String, Object> map=tdsHomeMapper.countByUserId(userId);
			
			TdsUser tdsUser=tdsUserMapper.loadById(userId);
			
			CreUserAccount account=creUserAccountService.findCreUserAccountByUserId(tdsUser.getCreUserId());
			BigDecimal b1 = new BigDecimal(account.getAccount()==null?0:account.getAccount());
			BigDecimal b2 = new BigDecimal(account.getApiAccount()==null?0:account.getApiAccount());
			BigDecimal b3 = new BigDecimal(account.getRqAccount()==null?0:account.getRqAccount());
			map.put("countNumber",b1.add(b2).add(b3));//产品总条数
			result.setResultObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("首页统计出现异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}
	
	

	
	@Override
	public BackResult<Integer> getAccByNumber(Integer userId, Integer pnameId) {
		BackResult<Integer> result=new BackResult<Integer>();
		Integer  countNumber=0;
		try {
			TdsUser tdsUser=tdsUserMapper.loadById(userId);
			CreUserAccount account=creUserAccountService.findCreUserAccountByUserId(tdsUser.getCreUserId());
			//实号检测
			if(pnameId==1){
				countNumber=account.getAccount()==null?0:account.getAccount();//实号检测目前账户剩余条数
			 }else if(pnameId==2){
				countNumber=account.getRqAccount()==null?0:account.getRqAccount();//账户二次清洗剩余条数
		  	 }else if(pnameId==3){
				countNumber=account.getApiAccount()==null?0:account.getApiAccount();  //api账户剩余条数
			 }
			result.setResultObj(countNumber);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("产品剩余条数查询出现异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		
		return result;
	}

	

	@Override
	public BackResult<String> recharge(Integer creUserId,Integer productsId,Integer number,BigDecimal money,String payType,String type) {
		BackResult<String> result=new BackResult<String>();
		try {
			
			//String types="1";   //1 充值 2 退款
			//String payTypes="1"; //交易类型：1支付宝，2银联 3创蓝充值,
			result=trdOrderService.recharge(creUserId, productsId, number, money, payType, type);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("首页充值支付异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}



	
	
	

}
