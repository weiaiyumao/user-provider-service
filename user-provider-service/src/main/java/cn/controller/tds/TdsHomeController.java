package cn.controller.tds;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsHomeService;
import main.java.cn.common.BackResult;

@RestController
@RequestMapping("/home")
public class TdsHomeController {

	@Autowired
	private TdsHomeService tdsHomeService;

	/**
	 * 统计客户数量，累计充值金额，剩余佣金,总条数
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/countByUserId", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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
}
