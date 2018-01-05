package cn.controller.tds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsMoneyApprovalService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;

@RestController
@RequestMapping("/moneyApproval")
public class TdsMoneyApprovalController {

	
	@Autowired
	private TdsMoneyApprovalService tdsMoneyApprovalService;
	
	
	  /**
	   * 客户列表下单
	   * @param id
	   * @return  obj
	   */
	  @RequestMapping(value="/downAddOrder",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<Integer> downAddOrder(@RequestBody TdsMoneyApprovalDomain domain){
			 BackResult<Integer> result=tdsMoneyApprovalService.downAddOrder(domain);
	         return result;
	   }
	  
	
}
