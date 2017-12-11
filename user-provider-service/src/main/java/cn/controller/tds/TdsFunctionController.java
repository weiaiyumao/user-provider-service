package cn.controller.tds;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.TdsFunction;
import cn.service.TdsFunctionService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsFunctionDomain;


@RestController
@RequestMapping("/function")
public class TdsFunctionController {
     
	  @Autowired
	  private TdsFunctionService  tdsFunctionService;
	  
	  /**
	   * 根据id查询
	   * @param id
	   * @return  obj
	   */
	  @RequestMapping(value="/loadById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunction> loadById(Integer id){
			  BackResult<TdsFunction> result=tdsFunctionService.loadById(id);
	          return result;
	   }
	  
	  
	  /**
	   * 保存
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionDomain> saveTdsFunction(@RequestBody TdsFunctionDomain tdsFunctionDomain){
		  BackResult<TdsFunctionDomain>  result=tdsFunctionService.saveTdsFunction(tdsFunctionDomain);
		  return result;
	  }
	  
	  /**
	   * 修改
	   * @param tdsFunction
	   * @return
	   */
	  @RequestMapping(value="/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunctionDomain>  update(@RequestBody TdsFunctionDomain TdsFunctionDomain){
	      BackResult<TdsFunctionDomain>  result=tdsFunctionService.updateTdsFunction(TdsFunctionDomain);
		  return result;
	  }
	  
	  /**
	   * 删除
	   * @param id
	   * @return
	   */
	  @RequestMapping(value="/deleteById",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<Integer> deleteById(Integer id){
		  BackResult<Integer> result=tdsFunctionService.deleteById(id);
		  return result;
	  }
	  
	  
	  /**
	   * 查询  
	   * @param tdsFunction
	   * @return List<>
	   */
	  @RequestMapping(value="/selectAll",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<List<TdsFunctionDomain>> selectAll(@RequestBody TdsFunctionDomain tdsFunctionDomain){
		  BackResult<List<TdsFunctionDomain>> result=tdsFunctionService.selectAll(tdsFunctionDomain);
		  return result;
	  }
	  
	  
}
