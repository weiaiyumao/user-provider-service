package cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.service.CreUserAccountService;
import main.java.cn.domain.BackResult;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.domain.UserAccountDomain;

@RestController("/user")
public class UserAccountController {

	@Autowired
	private CreUserAccountService creUserAccountService;

	/**
	 * 查询账户余额
	 * @param mobile
	 * @return
	 */
	@GetMapping("/findbyMobile")
	public BackResult<UserAccountDomain> findbyMobile(String mobile) {
		BackResult<UserAccountDomain> result = creUserAccountService.findByMobile(mobile);
		return result;
	}
	
	/**
	 * 充值或者退款
	 * @param trdOrderDomain
	 * @return
	 */
	@GetMapping("/rechargeOrRefunds")
	public BackResult<Boolean> rechargeOrRefunds(TrdOrderDomain trdOrderDomain) {
		BackResult<Boolean> result = creUserAccountService.rechargeOrRefunds(trdOrderDomain);
		return result;
	}
}
