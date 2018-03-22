package cn.service.tds;




import java.math.BigDecimal;
import java.util.Map;

import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsHomeBusService;

/**
 * : 首页服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsHomeService extends TdsHomeBusService{
	
	
	/**
	 * 统计客户数量，累计充值金额，剩余佣金
	 * @param userId
	 * @return
	 */
	BackResult<Map<String, Object>> countByUserId(Integer userId);
	
	
	/**
	 * 充值申请
	 * @param TrdOrderDomain
	 * @return
	 */
	BackResult<String> recharge(Integer creUserId,Integer productsId,Integer number,BigDecimal money,String payType,String type);
	
	
	/**
	 * 根据产品id，用户id获取对应剩余条数
	 * @param userId
	 * @param pnameId
	 * @return
	 */
	BackResult<Integer> getAccByNumber(Integer userId,Integer pnameId);
	

	
}
