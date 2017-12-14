package cn.controller.tds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsFunctionService;
import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.TdsUserDomain;


@RestController
@RequestMapping("/userLogin")
public class TdsUserLoginController {

	

	@Autowired
	private TdsUserService tdsUserService;
	
	@Autowired
	private TdsFunctionService tdsFunctionService;
	
	/**
	 * 登录
	 * @param tdsUserDomain
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserDomain> login(@RequestBody TdsUserDomain tdsUserDomain) {
		BackResult<TdsUserDomain> result=tdsUserService.login(tdsUserDomain);
		return result;
	}
	
	/**
	 * 模块加载
	 * @param userId
	 * @return
	 */
	@RequestMapping("/moduleLoadingByUsreId")
	public BackResult<List<TdsFunctionDomain>> moduleLoadingByUsreId(@RequestParam("userId")Integer userId){
		  return tdsFunctionService.moduleLoadingByUsreId(userId);
	}
	
}
