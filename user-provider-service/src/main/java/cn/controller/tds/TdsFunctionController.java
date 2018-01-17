package cn.controller.tds;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsFunctionService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunMoViewDomain;
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
	  @RequestMapping(value="/loadByIdView",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<TdsFunMoViewDomain> loadByIdView(Integer id){
			 BackResult<TdsFunMoViewDomain> result=tdsFunctionService.loadByIdView(id);
	         return result;
	   }
	  
	  
	  /**
	   * 保存
	   * @param tdsFunction
	   * @return
	 * @throws Exception 
	   */
	  @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<Integer> saveTdsFunction(@RequestBody TdsFunctionDomain tdsFunctionDomain) throws Exception{
		  BeanHelper.beanHelperTrim(tdsFunctionDomain);
		  BackResult<Integer>  result=tdsFunctionService.saveTdsFunction(tdsFunctionDomain);
		  return result;
	  }
	  
	  /**
	   * 修改
	   * @param tdsFunction
	   * @return
	 * @throws Exception 
	   */
	  @RequestMapping(value="/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<Integer>  update(@RequestBody TdsFunctionDomain tdsFunctionDomain) throws Exception{
		  BeanHelper.beanHelperTrim(tdsFunctionDomain);
	      BackResult<Integer>  result=tdsFunctionService.updateTdsFunction(tdsFunctionDomain);
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
	  @RequestMapping(value="/pageTdsFunction",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	  public BackResult<PageDomain<TdsFunMoViewDomain>> pageTdsFunction(@RequestBody TdsFunMoViewDomain domain){
		  BackResult<PageDomain<TdsFunMoViewDomain>> result=tdsFunctionService.pageTdsFunction(domain);
		  return result;
	  }
	  
	  
}
