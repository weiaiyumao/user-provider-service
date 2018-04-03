package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsMoneyApprovalService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsApprovalOutDomain;
import main.java.cn.domain.tds.TdsApprovalOutQueryDomain;
import main.java.cn.domain.tds.TdsCommissionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsSerualInfoDomain;

@RestController
@RequestMapping("/moneyApproval")
public class TdsMoneyApprovalController{

	@Autowired
	private TdsMoneyApprovalService tdsMoneyApprovalService;

	/**
	 * 流水分页查询
	 * 
	 * @param id
	 * @return obj
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pageTdsSerualInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsSerualInfoDomain>> pageTdsSerualInfo(@RequestBody TdsSerualInfoDomain domain) throws Exception {
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsMoneyApprovalService.pageTdsSerualInfo(domain);
	}
	
	
	/**
	 * 佣金分页查询
	 * 
	 * @param id
	 * @return obj
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pageCommission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCommissionDomain>> pageCommission(@RequestBody TdsCommissionDomain domain) throws Exception {
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsMoneyApprovalService.pageTdsCommission(domain);
	}

	/**
	 * 客户列表下单
	 * 
	 * @param id
	 * @return obj
	 * @throws Exception 
	 */
	@RequestMapping(value = "/downAddOrder", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> downAddOrder(@RequestBody TdsMoneyApprovalDomain domain) {
		BackResult<Integer> result = tdsMoneyApprovalService.downAddOrder(domain);
		return result;
	}

	/**
	 * 1进账审核操作 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	 * 
	 * @return obj
	 */
	@RequestMapping(value = "/approvalByUpStatusGo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> approvalByUpStatusGo(@RequestBody TdsMoneyApprovalDomain domain, String appRemarks) {
		BackResult<Integer> result = tdsMoneyApprovalService.approvalByUpStatusGo(domain, appRemarks);
		return result;
	}
	
	
	
	/**
	 * 进账分页查询
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/pageApprovalByUpStatusGo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageApprovalByUpStatusGo(
			@RequestBody TdsMoneyApprovalDomain domain) {
		return tdsMoneyApprovalService.pageApprovalByUpStatusGo(domain);
		
	}
	

	
	/**
	 * 出账分页查询
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/pageApprovalByUpStatusOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsApprovalOutDomain>> pageApprovalByUpStatusOut(
			@RequestBody TdsApprovalOutQueryDomain domain) {
		return tdsMoneyApprovalService.pageApprovalByUpStatusOut(domain);
	}
	
	/**
	 * 出账审核状态修改
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/updatePageApprovalByUpStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updatePageApprovalByUpStatus(String userId,String tdsCarryId,String status,String remarks,String order) {
		return tdsMoneyApprovalService.updatePageApprovalByUpStatus(userId,tdsCarryId,status,remarks,order);
	}


}