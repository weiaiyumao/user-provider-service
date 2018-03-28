package cn.controller.tds;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsMoneyApprovalCarryService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCarryDomain;

@RestController
@RequestMapping("/carry")
public class TdsCarryController{

	
	@Autowired
	private TdsMoneyApprovalCarryService tdsMoneyApprovalCarryService;
	
	
	@RequestMapping(value = "/pageTdsCarry", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCarryDomain>> pageTdsCarry(@RequestBody TdsCarryDomain domain) throws Exception{
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsMoneyApprovalCarryService.pageTdsCarry(domain);
	}
	
	
	@RequestMapping(value = "/getCarryByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Map<String,Object>> getCarryByUserId(Integer userId){
		return tdsMoneyApprovalCarryService.getCarryByUserId(userId);
	}
	
	
	@RequestMapping(value = "/getSubCarry", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> getSubCarry(@RequestBody TdsCarryDomain domain, String type,String overCommiss){
		return tdsMoneyApprovalCarryService.getSubCarry(domain.getUserId(),domain.getCarrMoney(),type,overCommiss);
	}

}
