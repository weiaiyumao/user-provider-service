package cn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.CreUserAccountService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.ErpTradeDomain;
import main.java.cn.domain.TrdOrderDomain;
import main.java.cn.domain.UserAccountDomain;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCreUserAccountLogDomain;
import main.java.cn.domain.tds.TdsUserAccountInfoDomain;

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
	@RequestMapping(value = "/findbyMobile", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
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
	public BackResult<ErpTradeDomain> rechargeOrRefunds(HttpServletRequest request, HttpServletResponse response,@RequestBody TrdOrderDomain trdOrderDomain) {
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
	 * 查询消费记录<分页>
	 * @param request
	 * @param response
	 * @param creUserId
	 * @param numPerPage
	 * @param currentPage
	 * @return
	 */
	@RequestMapping("/pageFindTrdOrderByCreUserId")
	public BackResult<PageDomain<TrdOrderDomain>> pageFindTrdOrderByCreUserId(HttpServletRequest request, HttpServletResponse response,Integer creUserId,Integer pageSize,Integer pageNum){
		BackResult<PageDomain<TrdOrderDomain>> result = creUserAccountService.pageFindTrdOrderByCreUserId(creUserId, pageSize, pageNum);
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
	
	/**
	 * 消费API条数
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping("/consumeApiAccount")
	public BackResult<Boolean> consumeApiAccount(String creUserId,String count) {
		BackResult<Boolean> result = creUserAccountService.consumeApiAccount(creUserId, count);
		return result;
	}

	/**
	 * 消费账户二次清洗条数
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping("/consumeRqApiAccount")
	public BackResult<Boolean> consumeRqApiAccount(String creUserId,String count) {
		BackResult<Boolean> result = creUserAccountService.consumeRqApiAccount(creUserId, count);
		return result;
	}
	
	/**
	 * 获取账户信息详情
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping(value = "/findTdsUserAccountInfoDomainByMobile", method = RequestMethod.POST)
	public BackResult<TdsUserAccountInfoDomain> findTdsUserAccountInfoDomainByMobile(String mobile) {
		BackResult<TdsUserAccountInfoDomain> result = creUserAccountService.findTdsUserAccountInfoDomainByMobile(mobile);
		return result;
	}
	
	/**
	 * 代理商系统修改客户账户信息
	 * @param trdOrderDomain
	 * @return
	 */
	@RequestMapping(value = "/updateUserAccountBytds", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Boolean> updateUserAccountByTds(@RequestBody TdsCreUserAccountLogDomain domain) {
		BackResult<Boolean> result = creUserAccountService.updateUserAccountByTds(domain);
		return result;
	}	
	
	public static void main(String[] args) {
		
	}
	
	
}
