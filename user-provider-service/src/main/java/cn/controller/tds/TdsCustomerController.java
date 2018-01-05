package cn.controller.tds;

import java.util.List;

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
import main.java.cn.domain.tds.TdsAttornLogDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsUserDiscountDomain;

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
	public BackResult<Integer> attorn(@RequestBody TdsAttornLogDomain domain){
		return tdsCustomerService.attorn(domain);
	}
	
	
	@RequestMapping(value = "/addTdsCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addTdsCustomer(@RequestBody PageAuto auto, Integer loginUserId,Integer[] arrRoles){
		return tdsCustomerService.addTdsCustomer(auto,loginUserId,arrRoles);
	}
	
	
	@RequestMapping(value = "/loadById", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsCustomerViewDomain> loadById(Integer id){
		return tdsCustomerService.loadById(id);
	}
	
	
	
	//====改价
	@RequestMapping(value = "/updatePrice", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updatePrice(@RequestBody TdsUserDiscountDomain domain){
		return tdsCustomerService.updatePrice(domain);
	}
	
	@RequestMapping(value = "/selectAllByUserId", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsUserDiscountDomain>> selectAllByUserId(Integer userId){
		return tdsCustomerService.selectAllByUserId(userId);
	}
	
	@RequestMapping(value = "/addTdsUserDiscount", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addTdsUserDiscount(@RequestBody TdsUserDiscountDomain domain){
		return tdsCustomerService.addTdsUserDiscount(domain);
	}
	
	
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> deleteById(Integer id){
		return tdsCustomerService.deleteById(id);
	}
	//====end
}
