package cn.controller;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.utils.DateUtils;
import main.java.cn.service.ApiAccountInfoBusService;

@RestController
@RequestMapping("/apiAccountInfo")
public class ApiAccountInfoController {
	private final static Logger logger = LoggerFactory.getLogger(ApiAccountInfoController.class);
	
	@Autowired
	private ApiAccountInfoBusService apiAccountInfoBusService;
	
//	@RequestMapping("/save")
//	public BackResult<Boolean> save(ApiAccountInfoDomain info) {
//		
//		BackResult<Boolean> result = new BackResult<Boolean>();
//		try {
//			
//			int save(ApiAccountInfo info);
//			
//			trdOrderService.payCallBack(outTrdOrder, orderStatus,traOrder);
//			result.setResultObj(Boolean.TRUE);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
//		
//		return result;
//	}
	
	public static void main(String[] args) {
		Calendar calendar = Calendar.getInstance();
		int year = 2017;
		int month = 0;
		int date = 1;
		calendar.set(year, month, date);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int minDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		
		for (int i = minDay; i <= maxDay; i++) {
			calendar.set(year, month, i);

			logger.info("开始执行" + DateUtils.formatDate(calendar.getTime(), "yyyy-MM-dd") + "的数据");
			
		}
	}
}
