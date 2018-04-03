package cn.controller.tds;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.tds.TdsFunction;
import cn.service.tds.TdsFunctionService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;

@RestController
@RequestMapping("/function")
public class TdsFunctionController {

	@Autowired
	private TdsFunctionService tdsFunctionService;

	
	
    /**
     * 分页模块查询
     * @param name
     * @param currentPage
     * @param numPerPage
     * @return
    * @throws Exception 
     */
	 @RequestMapping(value = "/pageByModular", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	 BackResult<PageDomain<Map<String,Object>>> pageByModular(String name,@RequestBody BasePageParam basePageParam) throws Exception{
		 BeanHelper.beanHelperTrim(name);
		 if(null==basePageParam.getCurrentPage())basePageParam.setCurrentPage(1);
		 if(null==basePageParam.getNumPerPage())basePageParam.setNumPerPage(10);
		 return tdsFunctionService.pageByModular(name,basePageParam);
	 }
	
	
	 
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsFunctionDomain>> selectAll(@RequestBody TdsFunctionDomain entity){
		BackResult<List<TdsFunctionDomain>> result = tdsFunctionService.selectAll(entity);
		return result;
	}
	
	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> saveTdsFunction(@RequestBody TdsFunctionDomain tdsFunctionDomain) throws Exception {
		BeanHelper.beanHelperTrim(tdsFunctionDomain);
		BackResult<Integer> result = tdsFunctionService.saveTdsFunction(tdsFunctionDomain);
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> update(@RequestBody TdsFunctionDomain tdsFunctionDomain) throws Exception {
		BeanHelper.beanHelperTrim(tdsFunctionDomain);
		BackResult<Integer> result = tdsFunctionService.updateTdsFunction(tdsFunctionDomain);
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
		BackResult<Integer> result = tdsFunctionService.deleteById(id);
		return result;
	}

	/**
	 * 层级查询
	 * @param tdsFunction
	 * @return List<>
	 */
	@RequestMapping(value = "/queryFunction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsFunctionDomain>> queryFunction() {
		BackResult<List<TdsFunctionDomain>> result = tdsFunctionService.queryFunction();
		return result;
	}

	@RequestMapping(value = "/pageByFunction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<Map<String, Object>>> pageByFunction(String name, @RequestBody BasePageParam basePageParam,String url)
			throws Exception {
		 BeanHelper.beanHelperTrim(name);
		 if(null==basePageParam.getCurrentPage())basePageParam.setCurrentPage(1);
		 if(null==basePageParam.getNumPerPage())basePageParam.setNumPerPage(10);
		return tdsFunctionService.pageByFunction(name, basePageParam,url);
	}
	
	
	@RequestMapping(value = "/loadingByUsreIdRole", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsFunctionDomain>> loadingByUsreIdRole(Integer userId){
		return tdsFunctionService.loadingByUsreIdRole(userId);
	}
	

	
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsFunctionDomain> loadById(Integer id){
		return tdsFunctionService.loadById(id);
	}
	
	
	@RequestMapping(value="/saveModular",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> saveModular(@RequestBody TdsFunctionDomain tdsFunctionDomain){
		return tdsFunctionService.saveModular(tdsFunctionDomain);
	}
	
	
	@RequestMapping(value = "/updateModular", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updateModular(String name,Integer selectedId,Integer newId,String arrModulars) throws Exception {
		TdsFunction  tds=new TdsFunction();
		tds.setId(selectedId);
		tds.setName(name);
		tds.setParentId(newId);
		tds.setRemarks("MODUL");
		//如果不选，则默认为父级模块
		if(null !=newId && newId==1){  //index
			tds.setParentId(0);  //标记为父类
		}
		return tdsFunctionService.update(tds);
	}
	
	
	
	
	
	
	
	
	
}
