package cn.controller.tds;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.TdsRole;
import cn.service.TdsRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsRoleDomain;


@RestController
@RequestMapping("/role")
public class TdsRoleController {


	@Autowired
	private TdsRoleService tdsRoleService;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsRole> loadById(Integer id) {
			BackResult<TdsRole> result = tdsRoleService.loadById(id);
		   return result;
	}

	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsRoleDomain> saveTdsFunction(@RequestBody TdsRoleDomain tdsRoleDomain) {
		BackResult<TdsRoleDomain> result =tdsRoleService.saveTdsRole(tdsRoleDomain);
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsRoleDomain> update(@RequestBody TdsRoleDomain tdsRoleDomain) {
		BackResult<TdsRoleDomain> result=tdsRoleService.updateTdsRole(tdsRoleDomain);
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
		BackResult<Integer> result = tdsRoleService.deleteById(id);
		return result;
	}

	
	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return List<>
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsRoleDomain>> selectAll(@RequestBody TdsRoleDomain tdsRoleDomain) {
		BackResult<List<TdsRoleDomain>> result =tdsRoleService.selectAll(tdsRoleDomain);
		return result;
	}
	
}
