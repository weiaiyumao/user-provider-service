package cn.controller.tds;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCompanyDomain;
import main.java.cn.domain.tds.TdsUserDomain;

@RestController
@RequestMapping("/tdsUser")
public class TdsUserController {


	@Autowired
	private TdsUserService tdsUserService;
	
	

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserDomain> loadById(Integer id) {
		BackResult<TdsUserDomain> result = tdsUserService.loadById(id);
		return result;
	}

	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> save(@RequestBody TdsUserDomain tdsUserDomain) {
		BackResult<Integer> result = tdsUserService.save(tdsUserDomain);
		return result;
	}

	
	/**
	 * 修改编辑头像
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/updateHeadImg", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updateHeadImg(Integer id,String hedehref) {
	    BackResult<Integer> result = tdsUserService.updateHeadImg(id,hedehref);
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = tdsUserService.deleteById(id);
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return List<>
	 */
	@RequestMapping(value = "/pageSelectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsUserDomain>> selectAll(@RequestBody TdsUserDomain tdsUserDomain, Integer pageSize,
			Integer curPage) {
		BackResult<PageDomain<TdsUserDomain>> result = tdsUserService.pageSelectAll(tdsUserDomain,pageSize,curPage);
		return result;
	}
	
	
	/**
	 * 根据手机号码查询对象
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/loadByPhone", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserDomain> loadByPhone(String phone){
		return tdsUserService.loadByPhone(phone);
	}
	
	
	
	/**
	 * 修改密码
	 * 
	 * @param usedPass
	 *            旧密码
	 * @param newPass
	 *            新密码
	 * @param userId
	 *            修改密码用户
	 * @return
	 */
	@RequestMapping(value = "/upPassWord", method = RequestMethod.POST)
	public BackResult<Integer> upPassWord(String usedPass, String newPass,Integer userId) {
		return tdsUserService.upPassWord(usedPass, newPass, userId);
	}
	
	
	
	
	
	
	/**
	 * 编辑个人用户信息
	 * @param domain
	 * @param  0： 个人 1：位企业   11： 个人企业信息编辑
	 * @return
	 */
	@RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
	public BackResult<Integer> editUserInfo(@RequestBody TdsUserDomain domain){
		return tdsUserService.editUserInfo(domain);
	}
	
	
	
	
	/**
	 * 编辑企业信息
	 * 
	 * @param token
	 * @param request
	 * @param response
	 * @param domain
	 * @param userId
	 *            用户id
	 * @param userName
	 *            用户名
	 * @param phone
	 *            手机
	 * @param contact
	 *            联系人
	 * @return   
	 */
	@RequestMapping(value = "/editComInfo", method = RequestMethod.POST)
	public BackResult<Integer> editComInfo(@RequestBody TdsCompanyDomain domain,Integer userId,String userName,String phone,String contact){
		return tdsUserService.editComInfo(domain, userId, userName, phone, contact);
	}

	
	
	
	
}
