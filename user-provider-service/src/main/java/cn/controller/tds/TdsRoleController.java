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
import cn.service.TdsRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;


@RestController
@RequestMapping("/role")
public class TdsRoleController {

	private final static Logger logger = LoggerFactory.getLogger(TdsRoleController.class);

	@Autowired
	private TdsRoleService tdsRoleService;

	/**
	 * 根据id查询
	 * 
	 * @param id
	 * @return obj
	 */
	@RequestMapping(value = "/loadById", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BackResult<TdsRole> loadById(Integer id) {
		BackResult<TdsRole> result = new BackResult<TdsRole>();
		try {
			TdsRole tds = tdsRoleService.loadById(id);
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
	public BackResult<TdsRole> saveTdsFunction(@RequestBody TdsRole tdsRole) {
		BackResult<TdsRole> result = new BackResult<TdsRole>();
		try {
			tdsRoleService.saveTdsRole(tdsRole);
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
	public BackResult<TdsRole> update(@RequestBody TdsRole rdsRole) {
		BackResult<TdsRole> result = new BackResult<TdsRole>();
		try {
			tdsRoleService.updateTdsRole(rdsRole);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" + rdsRole.getId() + "update功能信息出现系统异常：" + e.getMessage());
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
	public BackResult<TdsRole> deleteById(Integer id) {
		BackResult<TdsRole> result = new BackResult<TdsRole>();
		try {
			tdsRoleService.deleteById(id);
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
	public BackResult<List<TdsRole>> selectAll(@RequestBody TdsRole tdsRole) {
		BackResult<List<TdsRole>> result = new BackResult<List<TdsRole>>();
		try {
			List<TdsRole> list = tdsRoleService.selectAll(tdsRole);
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
