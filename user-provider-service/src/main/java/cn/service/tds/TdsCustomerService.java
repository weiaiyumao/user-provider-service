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
	 * 删除
	 * @param id
	 * @return
	 */
	BackResult<Integer> deleteById(Integer id);
	
}
