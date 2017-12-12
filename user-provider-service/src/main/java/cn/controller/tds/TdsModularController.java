package cn.controller.tds;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.TdsModular;
import cn.service.TdsModularService;
import main.java.cn.common.BackResult;
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
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsModularDomain> saveTdsFunction(@RequestBody TdsModularDomain tdsModularDomain) {
		  BackResult<TdsModularDomain> result = tdsModularService.saveTdsModular(tdsModularDomain);
		  return result;
	}

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsModularDomain> update(@RequestBody TdsModularDomain tdsModularDomain) {
		BackResult<TdsModularDomain> result =tdsModularService.updateTdsModular(tdsModularDomain);
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
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsModularDomain>> selectAll(@RequestBody TdsModularDomain tdsModularDomain) {
		BackResult<List<TdsModularDomain>> result =tdsModularService.selectAll(tdsModularDomain);
		return result;
	}

}
