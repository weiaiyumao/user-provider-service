package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsFunctionMapper;
import cn.dao.tds.TdsModularMapper;
import cn.entity.tds.TdsFunction;
import cn.entity.tds.TdsModular;
import cn.entity.tds.view.TdsCustomerView;
import cn.service.tds.TdsFunctionService;
import cn.utils.CommonUtils;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.TdsModularDomain;

@Service
public class TdsFunctionServiceImpl implements TdsFunctionService {

	private final static Logger logger = LoggerFactory.getLogger(TdsFunctionServiceImpl.class);

	@Autowired
	private TdsFunctionMapper tdsFunctionMapper;
	
	@Autowired
	private TdsModularMapper tdsModularMapper;

	@Override
	public BackResult<TdsFunctionDomain> loadById(Integer id) {
		BackResult<TdsFunctionDomain> result = new BackResult<TdsFunctionDomain>();
		try {
			TdsFunctionDomain domain = new TdsFunctionDomain();
			TdsFunction entity = tdsFunctionMapper.loadById(id);
			BeanUtils.copyProperties(entity, domain);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");

		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<TdsFunctionDomain> saveTdsFunction(TdsFunctionDomain domain) {
		BackResult<TdsFunctionDomain> result = new BackResult<TdsFunctionDomain>();
		TdsFunction tds = new TdsFunction();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsFunctionMapper.save(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsFunctionMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据删除失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<TdsFunctionDomain> updateTdsFunction(TdsFunctionDomain domain) {
		BackResult<TdsFunctionDomain> result = new BackResult<TdsFunctionDomain>();
		domain.setUpdateTime(new Date());
		TdsFunction tds = new TdsFunction();
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsFunctionMapper.update(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	@Override
	public BackResult<PageDomain<TdsFunctionDomain>> pageTdsFunction(TdsFunctionDomain domain) {
		BackResult<PageDomain<TdsFunctionDomain>> result = new BackResult<PageDomain<TdsFunctionDomain>>();
		PageDomain<TdsFunctionDomain> pageListDomain = null;
		List<TdsFunctionDomain> listDomain = new ArrayList<TdsFunctionDomain>();
//		try {
//			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
//			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
//			Integer count = tdsUserCustomerMapper.queryCount(auto);// 获取总数
//			List<TdsCustomerView> list = tdsUserCustomerMapper.pageTdsCustomer(auto);
//			if (list.size() > 0 && list != null) {
//
//				// 定义对象用于转换
//				TdsCustomerViewDomain tdsDomain = null;
//				for (TdsCustomerView obj : list) {
//					tdsDomain = new TdsCustomerViewDomain();
//					BeanUtils.copyProperties(obj, tdsDomain);
//					listDomain.add(tdsDomain);
//				}
//				// 构造计算分页参数
//				pageListDomain = new PageDomain<TdsCustomerViewDomain>(auto.getCurrentPage(), auto.getNumPerPage(),
//						count);
//				pageListDomain.setTlist(listDomain);
//				result.setResultObj(pageListDomain);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("查询功能信息出现系统异常：" + e.getMessage());
//			result.setResultCode(ResultCode.RESULT_FAILED);
//			result.setResultMsg("数据集合查询失败");
//		}
		return null;
	}

	
	
	@Override
	public BackResult<List<TdsModularDomain>> moduleLoadingByUsreId(Integer userId) {
		BackResult<List<TdsModularDomain>> result = new BackResult<List<TdsModularDomain>>();
		try {
			List<TdsModularDomain> listDomain = new ArrayList<TdsModularDomain>();
			List<TdsModular> list=tdsModularMapper.moduleLoadingByUsreId(userId);
			if (CommonUtils.isNotEmpty(list)) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "用户id没有模块加载列表");
			}

			TdsModularDomain tdsDomain = null;
			for (TdsModular obj : list) {
				tdsDomain = new TdsModularDomain();
				BeanUtils.copyProperties(obj, tdsDomain);
				listDomain.add(tdsDomain);
			}
			result.setResultObj(listDomain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("模块加载系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("模块查询失败");
		}

		return result;
	}

}
