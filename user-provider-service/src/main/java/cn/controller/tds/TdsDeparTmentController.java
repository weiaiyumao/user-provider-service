package cn.controller.tds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsDepartmentService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsDepartmentDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.UserRoleDepartmentViewDomain;

@RestController
@RequestMapping("/super")
public class TdsDeparTmentController {

	@Autowired
	private TdsDepartmentService tdsDepartmentService;

	/**
	 * 查询用户角色部门
	 * 
	 * @param departName
	 * @param roleName
	 * @param createTime
	 * @param contact
	 * @param currentPage
	 * @param numPerPage
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/pageUserRoleDepartmentView", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<UserRoleDepartmentViewDomain>> pageUserRoleDepartmentView(@RequestBody PageAuto auto) throws Exception {
		BeanHelper.beanHelperTrim(auto);  //去掉空格
		return tdsDepartmentService.pageUserRoleDepartmentView(auto);
	}

	/**
	 * 根据用户查询权限
	 * 
	 * @param usreId
	 * @return
	 */
	@RequestMapping(value = "/funByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsFunctionDomain>> funByuserId(Integer usreId) {
		return tdsDepartmentService.funByuserId(usreId);
	}

	/**
	 * 查询所有部门列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsDepartmentDomain>> selectAll(TdsDepartmentDomain domain) {
		return tdsDepartmentService.selectAll(domain);
	}

	@RequestMapping(value = "/addUserConfig", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addUserConfig(String name,String passWord,String phone, Integer departmentId, Integer positionId,
			Integer comId, Integer[] arrRoles,Integer loginUserId) {
		return tdsDepartmentService.addUserConfig(name,passWord,phone, departmentId, positionId, comId, arrRoles,loginUserId);
	}
	
	
	@RequestMapping(value = "/addCustomPermissions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addCustomPermissions(String soleName,Integer loginUserId,Integer[] arrfuns){
		return tdsDepartmentService.addCustomPermissions(soleName,loginUserId,arrfuns);
	}
	
	@RequestMapping(value = "/addFun", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> addFun(@RequestBody TdsFunctionDomain domain){
		return tdsDepartmentService.addFun(domain);
	}

}
