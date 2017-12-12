package cn.controller.tds;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.TdsFunctionRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionRoleDomain;


@RestController
@RequestMapping("/functionRole")
public class TdsFunctionRoleController {
	 
	 @Autowired
	 private TdsFunctionRoleService  tdsFunctionRoleService;
	
	 
	  /**
	   * 根据id查询
	   * @param id
	   * @return  obj
	   */
	  @RequestMapping(value="/loadById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRoleDomain> loadById(Integer id){
		   BackResult<TdsFunctionRoleDomain> result=tdsFunctionRoleService.loadById(id);
	       return result;
	  }
	  
	  
	  /**
	   * 保存
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRoleDomain> saveTdsFunction(@RequestBody TdsFunctionRoleDomain tdsFunctionRoleDomain){
		  BackResult<TdsFunctionRoleDomain> result=tdsFunctionRoleService.saveTdsFunctionRole(tdsFunctionRoleDomain);
		  return result;
	  }
	  
	  /**
	   * 修改
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRoleDomain>  update(@RequestBody TdsFunctionRoleDomain tdsFunctionRoleDomain){
		  BackResult<TdsFunctionRoleDomain> result=tdsFunctionRoleService.updateFunctionRole(tdsFunctionRoleDomain);
		  return result;
	  }
	  
	  /**
	   * 删除
	   * @param id
	   * @return
	   */
	  @RequestMapping(value="/deleteById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<Integer> deleteById(Integer id){
		  BackResult<Integer> result=tdsFunctionRoleService.deleteById(id);
		  return result;
	  }
	  
	  
	  /**
	   * 查询  
	   * @param tdsFunction
	   * @return List<>
	   */
	  @RequestMapping(value="/selectAll",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<List<TdsFunctionRoleDomain>> selectAll(@RequestBody TdsFunctionRoleDomain tdsFunctionRoleDomain){
		  BackResult<List<TdsFunctionRoleDomain>> result=tdsFunctionRoleService.selectAll(tdsFunctionRoleDomain);
		  return result;
	  }
}
