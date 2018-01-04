package cn.service.tds;




import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
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
	
	
	/**
	 * 审核操作
	 * @param id  列表id
	 * @param orderNumber  订单号
	 * @param approvalType  审核类型 ，入进账审核为1  出账为2 退款3
	 * @param approvalStatus  审核操作 0待审核  1已审核 3驳回  4到账 5线下开票 6 充账
	 * @param loginUserId  登录用户 操作人
	 * @return
	 */
	BackResult<Integer>  approvalByUpStatus(TdsMoneyApprovalDomain domain,String appRemarks);
	
}
