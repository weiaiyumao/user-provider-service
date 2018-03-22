package cn.service.impl.tds;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.dao.tds.TdsHomeMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.CreUserAccount;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserBankApy;
import cn.service.CreUserAccountService;
import cn.service.TrdOrderService;
import cn.service.tds.TdsHomeService;
import cn.service.tds.TdsUserBankApyService;
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
	
	
	@Autowired
	private TdsUserBankApyService tdsUserBankApyService;
	
	
	
	
	@Override
	public BackResult<Map<String, Object>> countByUserId(Integer userId) {
		
		    BackResult<Map<String, Object>> result=new BackResult<>();
		    TdsUserBankApy userbank=new TdsUserBankApy();
		try {
			
			Map<String, Object> map=tdsHomeMapper.countByUserId(userId);
			
			
		    userbank=tdsUserBankApyService.loadByUserId(userId);
		    
		    
		    //默认
			if(null==map){
				map=new HashMap<>();
				map.put("sumMoney",0);
				map.put("counts",0);
				map.put("overplusCommission",0);
			}		
			
			//返回绑定信息
			map.put("bindingObj",JSON.toJSON(userbank));
			
			TdsUser tdsUser=tdsUserMapper.loadById(userId);
			
			CreUserAccount account=creUserAccountService.findCreUserAccountByUserId(tdsUser.getCreUserId());
			
			BigDecimal b1 = new BigDecimal(null==account.getAccount()?0:account.getAccount());
			
			BigDecimal b2 = new BigDecimal(null==account.getApiAccount()?0:account.getApiAccount());
			
			BigDecimal b3 = new BigDecimal(null==account.getRqAccount()?0:account.getRqAccount());
			
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
				countNumber=null==account.getAccount()?0:account.getAccount();//实号检测目前账户剩余条数
			 }else if(pnameId==2){
				countNumber=null==account.getRqAccount()?0:account.getRqAccount();//账户二次清洗剩余条数
		  	 }else if(pnameId==3){
				countNumber=null==account.getApiAccount()?0:account.getApiAccount();  //api账户剩余条数
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
