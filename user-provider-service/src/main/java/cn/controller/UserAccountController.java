package cn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.service.CreUserAccountService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.ErpTradeDomain;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.domain.UserAccountDomain;

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {
	
	@Autowired
	private CreUserAccountService creUserAccountService;

	/**
	 * 查询账户余额
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/findbyMobile")
	public BackResult<UserAccountDomain> findbyMobile(HttpServletRequest request, HttpServletResponse response,String mobile) {
		BackResult<UserAccountDomain> result = creUserAccountService.findByMobile(mobile);
		return result;
	}
	
	/**
	 * 充值或者退款
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping("/rechargeOrRefunds")
	public BackResult<ErpTradeDomain> rechargeOrRefunds(HttpServletRequest request, HttpServletResponse response,TrdOrderDomain trdOrderDomain) {
		BackResult<ErpTradeDomain> result = creUserAccountService.rechargeOrRefunds(trdOrderDomain);
		return result;
	}
	
	/**
	 * 查询消费记录
	 * @param request
	 * @param response
	 * @param creUserId
	 * @return
	 */
	@RequestMapping("/findTrdOrderByCreUserId")
	public BackResult<List<TrdOrderDomain>> findTrdOrderByCreUserId(HttpServletRequest request, HttpServletResponse response,Integer creUserId){
		BackResult<List<TrdOrderDomain>> result = creUserAccountService.findTrdOrderByCreUserId(creUserId);
		return result;
	}
	
	/**
	 * 消费条数
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping("/consumeAccount")
	public BackResult<Boolean> consumeAccount(String creUserId,String count) {
		BackResult<Boolean> result = creUserAccountService.consumeAccount(creUserId, count);
		return result;
	}
	
	public static void main(String[] args) {
		
	}
}
