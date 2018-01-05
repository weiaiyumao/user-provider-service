package cn.controller.tds;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsAccountBankService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAccountBankDomain;


@RestController
@RequestMapping("/accounBank")
public class TdsAccounBankController {


	@Autowired
	private TdsAccountBankService tdsAccountBankService;

	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> save(@RequestBody TdsAccountBankDomain tdsAccountBankDomain,Integer loginUserId) {
		BackResult<Integer> result =tdsAccountBankService.save(tdsAccountBankDomain,loginUserId);
		return result;
	}

	/**
	 * 修改编辑
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> update(@RequestBody TdsAccountBankDomain tdsAccountBankDomain,Integer loginUserId) {
		BackResult<Integer> result=tdsAccountBankService.update(tdsAccountBankDomain,loginUserId);
		return result;
	}

	/**
	 * 根据id进行停用
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/isDisableById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> isDisableById(Integer id) {
		BackResult<Integer> result = tdsAccountBankService.isDisableById(id);
		return result;
	}

	
 
    /**
     * <分页>条模糊查询	
     * @param sname  关键字
     * @param currentPage  
     * @param numPerPage
     * @param selected  0：全部  1：简称 2：账号名称 3：开户银行 4：开户账号
     * @return
     */
	@RequestMapping(value = "/pageTdsAccountBank", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<PageDomain<TdsAccountBankDomain>> pageTdsAccountBank(String likeName,
			Integer currentPage, Integer numPerPage, Integer selected){
		BackResult<PageDomain<TdsAccountBankDomain>> result=tdsAccountBankService.pageTdsAccountBank(likeName, currentPage, numPerPage, selected);
        return result;
	}

	
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsAccountBankDomain> loadById(Integer id){
		BackResult<TdsAccountBankDomain> result=tdsAccountBankService.loadById(id);
        return result;
	}
	
}
