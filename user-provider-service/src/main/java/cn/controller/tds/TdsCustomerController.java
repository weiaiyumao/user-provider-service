package cn.controller.tds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsCustomerService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAttornLogDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsUserDiscountDomain;

@RestController
@RequestMapping("/customer")
public class TdsCustomerController {
	
	@Autowired
	private TdsCustomerService tdsCustomerService;
	
	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updateCustomer(@RequestBody TdsCustomerViewDomain domain, Integer loginUserId, String passWord) throws Exception{
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsCustomerService.updateCustomer(domain,loginUserId,passWord);
	}

	
	@RequestMapping(value = "/pageTdsCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsCustomer(@RequestBody TdsCustomerViewDomain domain) throws Exception{
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsCustomerService.pageTdsCustomer(domain);
	}
	
	
	
	@RequestMapping(value = "/attorn", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> attorn(@RequestBody TdsAttornLogDomain domain){
		return tdsCustomerService.attorn(domain);
	}
	
	
	@RequestMapping(value = "/addTdsCustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addTdsCustomer(@RequestBody TdsCustomerViewDomain domain, Integer loginUserId, String passWord) throws Exception{
		BeanHelper.beanHelperTrim(domain);  //去掉空格
		return tdsCustomerService.addTdsCustomer(domain,loginUserId,passWord);
	}
	
	
	@RequestMapping(value = "/loadByIdView", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsCustomerViewDomain> loadByIdView(Integer userId){
		return tdsCustomerService.loadByIdView(userId);
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
	
	
	/**
	 * 客户审核操作
	 * @param auto
	 * @return
	 */
	@RequestMapping(value = "/isAgree", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> isAgree(Integer isAgree,Integer userId,String reas){
		return tdsCustomerService.isAgree(isAgree,userId,reas);
	}
}
