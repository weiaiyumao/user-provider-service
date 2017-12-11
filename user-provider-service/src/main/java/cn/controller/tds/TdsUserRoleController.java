package cn.controller.tds;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.entity.TdsRole;
import cn.entity.TdsUserRole;
import cn.service.TdsRoleService;
import cn.service.TdsUserRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;

@RestController
@RequestMapping("/userRole")
public class TdsUserRoleController {

	private final static Logger logger = LoggerFactory.getLogger(TdsUserRoleController.class);

	@Autowired
	private  TdsUserRoleService  tdsUserRoleService;

	
	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRole> loadById(Integer id) {
		BackResult<TdsUserRole> result = new BackResult<TdsUserRole>();
		try {
			TdsUserRole tds = tdsUserRoleService.loadById(id);
			result.setResultObj(tds);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}

		return result;
	}

	/**
	 * 保存
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRole> saveTdsFunction(@RequestBody TdsUserRole tdsUserRole) {
		BackResult<TdsUserRole> result = new BackResult<TdsUserRole>();
		try {
			tdsUserRoleService.saveTdsUserRole(tdsUserRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能:save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	/**
	 * 修改
	 * 
	 * @param tdsFunction
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRole> update(@RequestBody TdsUserRole tdsUserRole) {
		BackResult<TdsUserRole> result = new BackResult<TdsUserRole>();
		try {
			tdsUserRoleService.updateTdsUserRole(tdsUserRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" + tdsUserRole.getId() + "update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsUserRole> deleteById(Integer id) {
		BackResult<TdsUserRole> result = new BackResult<TdsUserRole>();
		try {
			tdsUserRoleService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" + id + "delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}

	/**
	 * 查询
	 * 
	 * @param tdsFunction
	 * @return List<>
	 */
	@RequestMapping(value = "/selectAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<List<TdsUserRole>> selectAll(@RequestBody TdsUserRole tdsUserRole) {
		BackResult<List<TdsUserRole>> result = new BackResult<List<TdsUserRole>>();
		try {
			List<TdsUserRole> list = tdsUserRoleService.selectAll(tdsUserRole);
			result.setResultObj(list);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("系统异常");
		}
		return result;
	}
}
