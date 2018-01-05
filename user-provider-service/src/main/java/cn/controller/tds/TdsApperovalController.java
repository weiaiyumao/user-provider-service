package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsApprovalService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;

@RestController
@RequestMapping("/approval")
public class TdsApperovalController {
	
	@Autowired
	private TdsApprovalService tdsApprovalService;
	

	/**
	 * 客户审核查询<分页>
	 * @param auto
	 * @return
	 */
	@RequestMapping(value = "/pageTdsApproval", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsApproval(@RequestBody PageAuto auto){
		return tdsApprovalService.pageTdsApproval(auto);
	}
	
	
	/**
	 * 客户审核操作
	 * @param auto
	 * @return
	 */
	@RequestMapping(value = "/isAgree", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> isAgree(Integer isAgree,Integer userId,String reas){
		return tdsApprovalService.isAgree(isAgree,userId,reas);
	}
	
	
}
