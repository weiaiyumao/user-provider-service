package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsCustomerService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;

@RestController
@RequestMapping("/customer")
public class TdsCustomerController {
	
	@Autowired
	private TdsCustomerService tdsCustomerService;
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> update(Integer loginUserId,@RequestBody PageAuto auto,Integer upUserId,Integer[] arrRoles){
		return tdsCustomerService.update(loginUserId,auto,upUserId,arrRoles);
	}

	
	@RequestMapping(value = "/pageTdsCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsCustomer(@RequestBody PageAuto auto){
		return tdsCustomerService.pageTdsCustomer(auto);
	}
	
	
	@RequestMapping(value = "/attorn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageAuto> attorn(PageAuto auto){
		return tdsCustomerService.attorn(auto);
	}
	
	
	@RequestMapping(value = "/addTdsCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addTdsCustomer(@RequestBody PageAuto auto, Integer loginUserId,Integer[] arrRoles){
		return tdsCustomerService.addTdsCustomer(auto,loginUserId,arrRoles);
	}
}
