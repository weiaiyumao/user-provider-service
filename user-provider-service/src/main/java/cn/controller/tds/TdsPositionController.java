package cn.controller.tds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.service.tds.TdsCompanyService;
import cn.service.tds.TdsPositionService;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsCompanyDomain;
import main.java.cn.domain.tds.TdsPositionDomain;


@RestController
@RequestMapping("/position")
public class TdsPositionController {

	
	@Autowired
	private TdsPositionService tdsPositionService;
	
	@Autowired
	private TdsCompanyService tdsCompanyService;
	
	/**
	 * 根据父级部门获取职务
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/selectByDeparId", method = RequestMethod.POST)
	public BackResult<List<TdsPositionDomain>> selectByDeparId(Integer departmentId){
		return tdsPositionService.selectByDeparId(departmentId);
	}
	
	
	
	/**
	 * 获取所有公司列表
	 * @param departmentId
	 * @return
	 */
	@RequestMapping(value = "/selectCompanyAll", method = RequestMethod.POST)
	public BackResult<List<TdsCompanyDomain>> selectCompanyAll(){
		return tdsCompanyService.selectCompanyAll();
	}
	
	

	
}
