package cn.controller.tds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.tds.TdsUserRole;
import cn.service.tds.TdsUserRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsUserRoleDomain;

@RestController
@RequestMapping("/userRole")
public class TdsUserRoleController {


	@Autowired
	private TdsUserRoleService tdsUserRoleService;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRole> loadById(Integer id) {
		BackResult<TdsUserRole> result = tdsUserRoleService.loadById(id);
		return result;
	}

	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRoleDomain> saveTdsFunction(@RequestBody TdsUserRoleDomain tdsUserRoleDomain) {
		BackResult<TdsUserRoleDomain> result = tdsUserRoleService.saveTdsUserRole(tdsUserRoleDomain);
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRoleDomain> update(@RequestBody TdsUserRoleDomain tdsUserRoleDomain) {
		BackResult<TdsUserRoleDomain> result = tdsUserRoleService.updateTdsUserRole(tdsUserRoleDomain);
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
		BackResult<Integer> result = tdsUserRoleService.deleteById(id);
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return List<>
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsUserRoleDomain>> selectAll(@RequestBody TdsUserRoleDomain tdsUserRoleDomain) {
		BackResult<List<TdsUserRoleDomain>> result = tdsUserRoleService.selectAll(tdsUserRoleDomain);
		return result;
	}
	
	/**
	 * 根据用户id修改账号状态
	 * @param tdsUserRoleDomain
	 * @return
	 */
	@RequestMapping(value="/upStatusById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> upStatusById(Integer id,String status){
	    return tdsUserRoleService.upStatusById(id,status);
	}
	
	
	/**
	 * 账号配置列表显示
	 * @param tdsUserRoleDomain
	 * @return
	 */
	@RequestMapping(value="/queryRoleIsStatus",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<PageAuto>> queryRoleIsStatus(@RequestBody PageAuto auto){
		return tdsUserRoleService.queryRoleIsStatus(auto);
	}
	
}
