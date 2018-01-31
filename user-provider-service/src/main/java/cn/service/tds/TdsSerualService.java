package cn.service.tds;

import main.java.cn.common.BackResult;

/**
 * : 流水
 * 
 * 
 * @author Gen
 */
public interface TdsSerualService {
	
	
	
	/**
	 * 保存流水明细表
	 * @param status 流水状态 1处理中 2已处理 3被驳回
	 * @param type   流水类型：1佣金;2提现，3退款，4充值，5进账 6出账
	 * @param userId 用户userId
	 * @param money  设计流水金额 
	 * @return
	 * @throws Exception 
	 */
	BackResult<Integer> addSerual(String status,String type,Integer userId,String money,String ordrr);
	
}
