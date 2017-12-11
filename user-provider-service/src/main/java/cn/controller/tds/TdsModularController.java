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

import cn.entity.TdsModular;
import cn.service.TdsModularService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;

@RestController
@RequestMapping("/modular")
public class TdsModularController {

	private final static Logger logger = LoggerFactory.getLogger(TdsModularController.class);

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
		BackResult<TdsModular> result = new BackResult<TdsModular>();
		try {
			TdsModular tds = tdsModularService.loadById(id);
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
	public BackResult<TdsModular> saveTdsFunction(@RequestBody TdsModular tdsModular) {
		BackResult<TdsModular> result = new BackResult<TdsModular>();
		try {
			tdsModularService.saveTdsModular(tdsModular);
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
	public BackResult<TdsModular> update(@RequestBody TdsModular tdsModular) {
		BackResult<TdsModular> result = new BackResult<TdsModular>();
		try {
			tdsModularService.updateTdsModular(tdsModular);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能id：" + tdsModular.getId() + "update功能信息出现系统异常：" + e.getMessage());
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
	public BackResult<TdsModular> deleteById(Integer id) {
		BackResult<TdsModular> result = new BackResult<TdsModular>();
		try {
			tdsModularService.deleteById(id);
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
	public BackResult<List<TdsModular>> selectAll(@RequestBody TdsModular tdsModular) {
		BackResult<List<TdsModular>> result = new BackResult<List<TdsModular>>();
		try {
			List<TdsModular> list = tdsModularService.selectAll(tdsModular);
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
