package cn.service.tds;

import java.util.List;
import java.util.Map;

import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsAccountBankBusService;

/**
 * 入账银行管理服务接口
 * 
 * @author ChuangLan
 *
 */
public interface TdsAccountBankService extends TdsAccountBankBusService {
	
	
	/**
	 * 根据id进行停用
	 * @param id
	 * @return
	 */
	BackResult<Integer> isDisableById(Integer id);
	
	
	
	//获取id,银行名称
	BackResult<List<Map<String,Object>>> selectAllBankName();

}
