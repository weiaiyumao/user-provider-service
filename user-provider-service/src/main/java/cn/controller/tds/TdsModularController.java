package cn.controller.tds;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.tds.TdsModular;
import cn.service.tds.TdsModularService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsModularDomain;

@RestController
@RequestMapping("/modular")
public class TdsModularController {


	@Autowired
	private TdsModularService tdsModularService;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsModular> loadById(Integer id) {
			BackResult<TdsModular> result = tdsModularService.loadById(id);
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
	public BackResult<Integer> saveTdsFunction(@RequestBody TdsModularDomain tdsModularDomain) throws Exception {
		  BeanHelper.beanHelperTrim(tdsModularDomain);
		  BackResult<Integer> result = tdsModularService.saveTdsModular(tdsModularDomain);
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
	public BackResult<Integer> update(String name,Integer selectedId,Integer newId,String arrModulars) throws Exception {
		BackResult<Integer> result =tdsModularService.updateTdsModular(name,selectedId,newId,arrModulars);
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
		BackResult<Integer> result = tdsModularService.deleteById(id);
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return List<>
	 * @throws Exception 
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsModularDomain>> selectAll(@RequestBody TdsModularDomain tdsModularDomain) throws Exception {
		BeanHelper.beanHelperTrim(tdsModularDomain);
		BackResult<List<TdsModularDomain>> result =tdsModularService.selectAll(tdsModularDomain);
		return result;
	}
	
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
		 return tdsModularService.pageByModular(name,basePageParam);
	 }
	 
	 
	 @RequestMapping(value = "/queryModular", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	 BackResult<List<TdsModularDomain>> queryModular(){
		 return tdsModularService.queryModular();
	 }

	 
}
