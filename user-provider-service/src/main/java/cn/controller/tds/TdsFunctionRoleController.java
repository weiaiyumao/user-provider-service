package cn.controller.tds;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.TdsFunctionRole;
import cn.service.TdsFunctionRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;


@RestController
@RequestMapping("/functionRole")
public class TdsFunctionRoleController {
	 private final static Logger logger = LoggerFactory.getLogger(TdsFunctionRoleController.class);
	 
	 @Autowired
	 private TdsFunctionRoleService  tdsFunctionRoleService;
	
	 
	  /**
	   * 根据id查询
	   * @param id
	   * @return  obj
	   */
	  @RequestMapping(value="/loadById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRole> loadById(Integer id){
		  BackResult<TdsFunctionRole> result=new BackResult<TdsFunctionRole>();
		  try {
			  TdsFunctionRole tds=tdsFunctionRoleService.loadById(id);
			result.setResultObj(tds);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		  
	    return result;
	  }
	  
	  
	  /**
	   * 保存
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRole> saveTdsFunction(@RequestBody TdsFunctionRole tdsFunctionRole){
		  BackResult<TdsFunctionRole> result=new BackResult<TdsFunctionRole>();
		  try {
			  tdsFunctionRoleService.saveTdsFunctionRole(tdsFunctionRole);
		  } catch (Exception e) {
			e.printStackTrace();
			logger.error("功能:save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		 }
		  return result;
	  }
	  
	  /**
	   * 修改
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRole>  update(@RequestBody TdsFunctionRole tdsFunctionRole){
		  BackResult<TdsFunctionRole> result=new BackResult<TdsFunctionRole>();
		  try {
			  tdsFunctionRoleService.updateFunctionRole(tdsFunctionRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" +tdsFunctionRole.getId() + "update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		  return result;
	  }
	  
	  /**
	   * 删除
	   * @param id
	   * @return
	   */
	  @RequestMapping(value="/deleteById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionRole> deleteById(Integer id){
		  BackResult<TdsFunctionRole> result=new BackResult<TdsFunctionRole>();
		  try {
			  tdsFunctionRoleService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" +id + "delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		  return result;
	  }
	  
	  
	  /**
	   * 查询  
	   * @param tdsFunction
	   * @return List<>
	   */
	  @RequestMapping(value="/selectAll",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<List<TdsFunctionRole>> selectAll(@RequestBody TdsFunctionRole tdsFunctionRole){
		  BackResult<List<TdsFunctionRole>> result=new BackResult<List<TdsFunctionRole>>();
		  try {
		     List<TdsFunctionRole> list=tdsFunctionRoleService.selectAll(tdsFunctionRole);
		     result.setResultObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		  return result;
	  }
}
