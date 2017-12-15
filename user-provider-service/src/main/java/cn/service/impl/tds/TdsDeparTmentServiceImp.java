package cn.service.impl.tds;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.dao.tds.TdsDepartmentMapper;
import cn.entity.tds.TdsDepartment;
import cn.entity.tds.TdsFunction;
import cn.entity.tds.view.UserRoleDepartmentView;
import cn.service.tds.TdsDepartmentService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsDepartmentDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.UserRoleDepartmentViewDomain;

@Service
public class TdsDeparTmentServiceImp implements TdsDepartmentService {

	private final static Logger logger = LoggerFactory.getLogger(TdsDeparTmentServiceImp.class);

	@Autowired
	private TdsDepartmentMapper tdsDepartmentMapper;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@Override
	public BackResult<List<TdsDepartmentDomain>> selectAll(TdsDepartmentDomain domain) {
		BackResult<List<TdsDepartmentDomain>> result = new BackResult<List<TdsDepartmentDomain>>();
		TdsDepartment tds = new TdsDepartment();
		List<TdsDepartmentDomain> listDomain = new ArrayList<TdsDepartmentDomain>();
		try {
			BeanUtils.copyProperties(domain, tds);
			List<TdsDepartment> list = tdsDepartmentMapper.selectAll(tds);
			if (list.size() > 0 && list != null) {
				TdsDepartmentDomain tdsDomain = null;
				for (TdsDepartment obj : list) {
					tdsDomain = new TdsDepartmentDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				result.setResultObj(listDomain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}

	@Override
	public BackResult<PageDomain<UserRoleDepartmentViewDomain>> pageUserRoleDepartmentView(String departName,
			String roleName, String createTime, String contact, Integer currentPage, Integer numPerPage) {
		BackResult<PageDomain<UserRoleDepartmentViewDomain>> result = new BackResult<PageDomain<UserRoleDepartmentViewDomain>>();
		PageDomain<UserRoleDepartmentViewDomain> listDomain = null;
		List<UserRoleDepartmentViewDomain> list = new ArrayList<UserRoleDepartmentViewDomain>();
		try {
			UserRoleDepartmentView view = new UserRoleDepartmentView(currentPage, numPerPage);
			view.setName(departName);
			view.setRole_name(roleName);
			view.setContact(contact);
			view.setStatime(null); // 开始时间
			view.setEndtime(null); // 结束时间
			Integer count = tdsDepartmentMapper.queryCount(view);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			List<UserRoleDepartmentView> pageList = tdsDepartmentMapper.pageUserRoleDepartmentView(view);
			if (pageList.size() <= 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			UserRoleDepartmentViewDomain obj = null;
			for (UserRoleDepartmentView item : pageList) {
				obj = new UserRoleDepartmentViewDomain();
				BeanUtils.copyProperties(item, obj);
				list.add(obj);
			}
			listDomain = new PageDomain<UserRoleDepartmentViewDomain>(view.getCurrentPage(), view.getNumPerPage(),
					count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<UserRoleDepartmentViewDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	public BackResult<List<TdsFunctionDomain>> funByuserId(Integer usreId) {
		BackResult<List<TdsFunctionDomain>> result = new BackResult<List<TdsFunctionDomain>>();
		List<TdsFunctionDomain> listDomain = new ArrayList<TdsFunctionDomain>();
		try {
			List<TdsFunction> list = tdsDepartmentMapper.funByuserId(usreId);

			if (list.size() <= 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有权限信息");
			}
			TdsFunctionDomain tdsDomain = null;
			for (TdsFunction obj : list) {
				tdsDomain = new TdsFunctionDomain();
				BeanUtils.copyProperties(obj, tdsDomain);
				listDomain.add(tdsDomain);
			}
			result.setResultObj(listDomain);
		} catch (Exception e) {
			logger.error("查询功能出现系统异常" + e.getMessage());
			return new BackResult<List<TdsFunctionDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}

		return result;
	}

}
