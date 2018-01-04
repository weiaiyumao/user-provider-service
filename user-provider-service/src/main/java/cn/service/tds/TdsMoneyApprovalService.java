package cn.service.tds;



import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsMoneyApprovalBusService;

/**
 * 财务审核记录表服务接口
 * @author ChuangLan
 *
 */
public interface TdsMoneyApprovalService extends TdsMoneyApprovalBusService{
	
	
	/**
	 * 1进账审核  2出账审核 3退款审核
	 * @return
	 */
	BackResult<Integer>  billApproval(Integer approvalType);
	
}
