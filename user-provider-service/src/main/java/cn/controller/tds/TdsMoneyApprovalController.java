package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsMoneyApprovalService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCommissionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsSerualInfoDomain;

@RestController
@RequestMapping("/moneyApproval")
public class TdsMoneyApprovalController {

	@Autowired
	private TdsMoneyApprovalService tdsMoneyApprovalService;

	/**
	 * 流水分页查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/pageTdsSerualInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsSerualInfoDomain>> pageTdsSerualInfo(@RequestBody TdsSerualInfoDomain domain) {
		return tdsMoneyApprovalService.pageTdsSerualInfo(domain);
	}
	
	
	/**
	 * 佣金分页查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/pageCommission", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCommissionDomain>> pageCommission(@RequestBody TdsCommissionDomain domain) {
		return tdsMoneyApprovalService.pageTdsCommission(domain);
	}

	/**
	 * 客户列表下单
	 * 
	 * @param id
	 * @return obj
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
		domain.setApprovalType("1");
		BackResult<Integer> result = tdsMoneyApprovalService.approvalByUpStatus(domain, appRemarks);
		return result;
	}

	/**
	 * 2出账审核操作 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	 * 
	 * @return obj
	 */
	@RequestMapping(value = "/approvalByUpStatusOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> approvalByUpStatusOut(@RequestBody TdsMoneyApprovalDomain domain, String appRemarks) {
		domain.setApprovalType("2");
		BackResult<Integer> result = tdsMoneyApprovalService.approvalByUpStatus(domain, appRemarks);
		return result;
	}

	/**
	 * 3退款审核操作 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	 * 
	 * @return obj
	 */
	@RequestMapping(value = "/approvalByUpStatusBack", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> approvalByUpStatusBack(@RequestBody TdsMoneyApprovalDomain domain, String appRemarks) {
		domain.setApprovalType("3");
		BackResult<Integer> result = tdsMoneyApprovalService.approvalByUpStatus(domain, appRemarks);
		return result;
	}

	/**
	 * 进账分页查询
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/pageApprovalByUpStatusGo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageApprovalByUpStatusGo(
			@RequestBody TdsMoneyApprovalDomain domain) {
		domain.setApprovalType("1");
		return tdsMoneyApprovalService.pageMoneyApprovalAll(domain);
	}

	/**
	 * 出账分页查询
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/pageApprovalByUpStatusOut", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageApprovalByUpStatusOut(
			@RequestBody TdsMoneyApprovalDomain domain) {
		domain.setApprovalType("2");
		return tdsMoneyApprovalService.pageMoneyApprovalAll(domain);
	}

	/**
	 * 退账分页查询
	 * 
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/pageApprovalByUpStatusBack", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageApprovalByUpStatusBack(
			@RequestBody TdsMoneyApprovalDomain domain) {
		domain.setApprovalType("3");
		return tdsMoneyApprovalService.pageMoneyApprovalAll(domain);
	}

}
