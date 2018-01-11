package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsMoneyApprovalBackService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalBackDomain;

@RestController
@RequestMapping("/moneyApprovalBack")
public class TdsMoneyApprovalBackController{

	@Autowired
	private TdsMoneyApprovalBackService tdsMoneyApprovalBackService;


	

	/**
	 * 退款审核分页列表
	 * 
	 * @param domain
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pageApprovalBack", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsMoneyApprovalBackDomain>> pageApprovalBack(
			@RequestBody TdsMoneyApprovalBackDomain domain) throws Exception {
		BeanHelper.beanHelperTrim(domain);
		return tdsMoneyApprovalBackService.pageApprovalBack(domain);
		
	}
	
}
