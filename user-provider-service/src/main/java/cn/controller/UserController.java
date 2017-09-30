package cn.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public BackResult<CreUserDomain> findbyMobile(HttpServletRequest request, HttpServletResponse response,String mobile) {
		
		
		BackResult<CreUserDomain> result = creUserService.findbyMobile(mobile);
		return result;
	}
	
	@RequestMapping(value = "/findOrsaveUser", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> saveUser(HttpServletRequest request, HttpServletResponse response,@RequestBody CreUserDomain creUserDomain) {
		
		BackResult<CreUserDomain> result = creUserService.findOrsaveUser(creUserDomain);
		return result;
	}
	
	@RequestMapping(value = "/updateCreUser", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<CreUserDomain> updateCreUser(HttpServletRequest request, HttpServletResponse response,@RequestBody CreUserDomain creUserDomain) {
		
		BackResult<CreUserDomain> result = creUserService.updateCreUser(creUserDomain);
		return result;
	}
	
	
	
}
