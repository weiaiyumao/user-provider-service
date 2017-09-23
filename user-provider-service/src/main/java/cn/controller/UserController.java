package cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.CreUserService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.CreUserDomain;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CreUserService creUserService;

	/**
	 * 查询用户信息
	 * @param mobile
	 * @return
	 */
	@RequestMapping("/findbyMobile")
	public BackResult<CreUserDomain> findbyMobile(String mobile) {
		BackResult<CreUserDomain> result = creUserService.findbyMobile(mobile);
		return result;
	}
	
	@RequestMapping(value = "/findOrsaveUser", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> saveUser(@RequestBody CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = creUserService.findOrsaveUser(creUserDomain);
		return result;
	}
	
	@RequestMapping(value = "/updateCreUser", method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> updateCreUser(@RequestBody CreUserDomain creUserDomain) {
		BackResult<CreUserDomain> result = creUserService.updateCreUser(creUserDomain);
		return result;
	}
	
	
	
}
