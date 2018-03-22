package cn.service.tds;



import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsCustomerBusService;

/**
 * 客户列表服务
 * @author ChuangLan
 *
 */
public interface TdsCustomerService  extends TdsCustomerBusService{
	
	/**
	 * 客户审核操作
	 * @param isAgree 0:同意：2：驳回
	 * @param userId  用户id
	 * @param reas    驳回原因
	 * @return
	 */
	BackResult<Integer> isAgree(Integer isAgree,Integer userId,String reas);
	
    
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	BackResult<Integer> deleteById(Integer id);
	
}
