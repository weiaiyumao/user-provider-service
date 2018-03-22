package cn.service.tds;

import cn.entity.tds.TdsUserBankApy;
import main.java.cn.service.tds.TdsUserBankApyBusService;

/**
 * 
 * 用户关联绑定银行，支付，手机服务接口
 * @author ChuangLan
 *
 */
public interface TdsUserBankApyService extends TdsUserBankApyBusService{
	
	/**
	 * 根据用户id获取信息
	 * @param id
	 * @return
	 */
	TdsUserBankApy  loadByUserId(Integer userId);
    
}
