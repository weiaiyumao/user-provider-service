package cn.service.tds;




import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsApprovalOutDomain;
import main.java.cn.domain.tds.TdsApprovalOutQueryDomain;
import main.java.cn.service.tds.TdsMoneyApprovalBusService;

/**
 * 财务进账审核外服务接口
 * @author ChuangLan
 *
 */
public interface TdsMoneyApprovalService extends TdsMoneyApprovalBusService{
	
	public BackResult<PageDomain<TdsApprovalOutDomain>> pageMoneyApprovalOut(TdsApprovalOutQueryDomain domain);
	
	public BackResult<Integer> updatePageApprovalByUpStatus(String userId,String tdsCarryId,String status,String remarks);
	
}
