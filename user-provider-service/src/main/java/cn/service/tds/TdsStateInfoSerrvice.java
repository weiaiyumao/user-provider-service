package cn.service.tds;

import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsStateInfoBusSerrvice;


/**
 * 状态库服务接口
 * @author ChuangLan
 *
 */
public interface TdsStateInfoSerrvice extends TdsStateInfoBusSerrvice{
	
	
	

	/**
	 * 根据id删除
	 * @param domain
	 * @return
	 */
	BackResult<Integer> deleteById(Integer id);

}
