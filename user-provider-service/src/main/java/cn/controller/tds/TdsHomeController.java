package cn.controller.tds;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.tds.TdsUserBankApy;
import cn.service.tds.TdsHomeService;
import cn.service.tds.TdsUserBankApyService;
import cn.utils.BeanHelper;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsUserBankApyDomain;


@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/home")
public class TdsHomeController {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TdsHomeController.class);

	@Autowired
	private TdsHomeService tdsHomeService;

	@Autowired
	private TdsUserBankApyService tdsUserBankApyService;
	
	/**
	 * 统计客户数量，累计充值金额，剩余佣金,总条数
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/countByUserId", method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	BackResult<Map<String, Object>> countByUserId(Integer userId) {
		return tdsHomeService.countByUserId(userId);
	}

	/**
	 * 获取对应的产品剩余数量
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/getAccByNumber", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	BackResult<Integer> getAccByNumber(Integer userId, Integer pnameId) {
		return tdsHomeService.getAccByNumber(userId, pnameId);
	}
	
	
	/**
	 * 修改
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/updateByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> updateByUserId(@RequestBody TdsUserBankApyDomain tdsUserBankApyDomain){
		try {
			 BeanHelper.beanHelperTrim(tdsUserBankApyDomain);
			 tdsUserBankApyService.updateByUserId(tdsUserBankApyDomain);
		} catch (Exception e) {
			logger.error("修改用户关联绑定异常",e.getMessage());
			return BackResult.error("修改用户关联绑定异常");
		}
		return BackResult.ok(true);
	}
	
	
	/**
	 * 新增
	 * @param domain
	 * @return
	 */
	@RequestMapping(value = "/addBankApy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<Integer> add(@RequestBody TdsUserBankApyDomain domain){
		try {
			tdsUserBankApyService.add(domain);
		} catch (Exception e) {
			logger.error("新增用户关联绑定异常",e.getMessage());
			return  BackResult.error("新增用户关联绑定异常");
		}
		return BackResult.ok(true);
	}
	
	
	
	/**
	 * 根据用户id查询
	 * @return
	 */
	@RequestMapping(value = "/loadBankApyByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserBankApyDomain> loadByUserId(Integer userId){
		BackResult<TdsUserBankApyDomain> result=new BackResult<>();
		TdsUserBankApyDomain domain=new TdsUserBankApyDomain();
		try {
			TdsUserBankApy entity=tdsUserBankApyService.loadByUserId(userId);
			BeanUtils.copyProperties(entity, domain);
			result.setResultObj(domain);
		} catch (Exception e) {
			logger.error("查询用户关联绑定异常",e.getMessage());
			return BackResult.error("新增用户关联绑定异常");
		}
		return result;
	}
}
