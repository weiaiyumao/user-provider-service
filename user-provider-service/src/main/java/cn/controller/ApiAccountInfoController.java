package cn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.ApiAccountInfoDomain;
import main.java.cn.service.ApiAccountInfoBusService;

@RestController
@RequestMapping("/apiAccountInfo")
public class ApiAccountInfoController {
	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoController.class);
	
	@Autowired
	private ApiAccountInfoBusService apiAccountInfoBusService;
	
	@RequestMapping("/findByCreUserId")
	public BackResult<ApiAccountInfoDomain> findByCreUserId(String creUserId) {
		
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		
		try {
			result = apiAccountInfoBusService.findByCreUserId(creUserId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：" + creUserId + "查询api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		} 
		
		return result;
	}
	
	@RequestMapping("/updateApiAccountInfo")
	public BackResult<ApiAccountInfoDomain> updateApiAccountInfo(@RequestBody ApiAccountInfoDomain domain) {
		
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		
		try {
			result = apiAccountInfoBusService.updateApiAccountInfo(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：" + domain.getCreUserId() + "修改api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		} 
		
		return result;
	}

	@RequestMapping("/findByNameAndPwd")
	public BackResult<ApiAccountInfoDomain> findByNameAndPwd(String name, String password) {
		
		BackResult<ApiAccountInfoDomain> result = new BackResult<ApiAccountInfoDomain>();
		
		try {
			result = apiAccountInfoBusService.findByNameAndPwd(name, password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：" + name + "查询api账户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		} 
		
		return result;
	}
	
	
	@RequestMapping("/checkApiAccount")
	public BackResult<Integer> checkApiAccount(String apiName, String password, String ip,
											   int checkCount){
		return apiAccountInfoBusService.checkApiAccount(apiName, password, ip, checkCount);
	}

	@RequestMapping("/checkRqApiAccount")
	public BackResult<Integer> checkRqApiAccount(String apiName, String password, String ip,
											   int checkCount){
		return apiAccountInfoBusService.checkRqApiAccount(apiName, password, ip, checkCount);
	}
}
