package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsStateInfoSerrvice;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsStateInfoDomain;

@RestController
@RequestMapping("/state")
public class TdsStateInfoController {

	@Autowired
	private TdsStateInfoSerrvice tdsStateInfoService;

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> update(@RequestBody TdsStateInfoDomain tdsStateInfoDomain) {
		BackResult<Integer> result = tdsStateInfoService.update(tdsStateInfoDomain);
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
		BackResult<Integer> result = tdsStateInfoService.deleteById(id);
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return 分页<>
	 */
	@RequestMapping(value = "/pageTdsStateInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsStateInfoDomain>> pageTdsStateInfo(@RequestBody PageAuto auto) {
		BackResult<PageDomain<TdsStateInfoDomain>> result = tdsStateInfoService.pageTdsStateInfo(auto);
		return result;
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> save(Integer userId,@RequestBody TdsStateInfoDomain tdsStateInfoDomain){
		BackResult<Integer> result = tdsStateInfoService.addState(userId,tdsStateInfoDomain);
		return result;
	}
}
