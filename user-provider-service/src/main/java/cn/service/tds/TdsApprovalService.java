package cn.service.tds;

import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsApprovalBusService;


/**
 * 客户审核服务接口
 * @author ChuangLan
 *
 */
public interface TdsApprovalService   extends TdsApprovalBusService{
	
	
	/**
	 * 客户审核操作
	 * @param isAgree 0:同意：2：驳回
	 * @param userId  用户id
	 * @param reas    驳回原因
	 * @return
	 */
	BackResult<Integer> isAgree(Integer isAgree,Integer userId,String reas);

}
