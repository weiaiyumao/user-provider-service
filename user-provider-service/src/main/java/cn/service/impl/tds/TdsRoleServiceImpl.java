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
import cn.dao.tds.TdsRoleMapper;
import cn.entity.tds.TdsFunction;
import cn.entity.tds.TdsRole;
import cn.service.tds.TdsRoleService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.TdsRoleDomain;

@Service
public class TdsRoleServiceImpl implements TdsRoleService {

	private final static Logger logger = LoggerFactory.getLogger(TdsRoleServiceImpl.class);

	@Autowired
	private TdsRoleMapper tdsRoleMapper;

	@Autowired
	private TdsFunctionMapper tdsFunctionMapper;

	@Override
	public BackResult<TdsRole> loadById(Integer id) {
		BackResult<TdsRole> result = new BackResult<TdsRole>();
		try {
			TdsRole entity = tdsRoleMapper.loadById(id);
			result.setResultObj(entity);
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
	public BackResult<TdsRoleDomain> saveTdsRole(TdsRoleDomain domain) {
		BackResult<TdsRoleDomain> result = new BackResult<TdsRoleDomain>();
		TdsRole tds = new TdsRole();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsRoleMapper.save(tds);
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
			Integer i = tdsRoleMapper.deleteById(id);
			if (i <= 0) {
				new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "没有信息");
			}
			result.setResultObj(i);
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
	public BackResult<TdsRoleDomain> updateTdsRole(TdsRoleDomain domain) {
		BackResult<TdsRoleDomain> result = new BackResult<TdsRoleDomain>();
		domain.setUpdateTime(new Date());
		TdsRole tds = new TdsRole();
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsRoleMapper.update(tds);
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
	public BackResult<List<TdsRoleDomain>> selectAll(TdsRoleDomain domain) {
		BackResult<List<TdsRoleDomain>> result = new BackResult<List<TdsRoleDomain>>();
		TdsRole tds = new TdsRole();
		List<TdsRoleDomain> listDomain = new ArrayList<TdsRoleDomain>();
		try {
			BeanUtils.copyProperties(domain, tds);
			List<TdsRole> list = tdsRoleMapper.selectAll(tds);
			if (list.size() > 0 && list != null) {
				TdsRoleDomain tdsDomain = null;
				for (TdsRole obj : list) {
					tdsDomain = new TdsRoleDomain();
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
	public BackResult<List<TdsFunctionDomain>> loadingBydRoleId(Integer roleId) {
		BackResult<List<TdsFunctionDomain>> result = new BackResult<List<TdsFunctionDomain>>();
		try {

			// 获取所有的父 ，子 菜单
			List<TdsFunction> list = tdsFunctionMapper.loadingBydRoleId(roleId);
			List<TdsFunctionDomain> domain = new ArrayList<TdsFunctionDomain>();
			TdsFunctionDomain obj = null;
			for (TdsFunction item : list) {
				obj = new TdsFunctionDomain();
				BeanUtils.copyProperties(item, obj);
				domain.add(obj);
			}

			// 根据一级菜单id查询所有的菜单
			List<TdsFunctionDomain> listDomain = new ArrayList<TdsFunctionDomain>();

			for (TdsFunctionDomain menuVo : domain) {
				// 一级菜单
				// 获取所有的父
				if (menuVo.getParentId() == 0) {
					// 递归
					List<TdsFunctionDomain> iterateMenus = recursion(domain, menuVo.getId());
					menuVo.setTdsFunctions(iterateMenus);
					listDomain.add(menuVo);
				}

			}

			result.setResultObj(listDomain);

		} catch (Exception e) {
			logger.error("模块功能信息出现系统异常：" + e.getMessage());
		}
		return result;
	}

	/**
	 * 递归循环
	 * 
	 * @param list
	 * @param pid
	 * @return
	 */
	public List<TdsFunctionDomain> recursion(List<TdsFunctionDomain> list, Integer pid) {
		List<TdsFunctionDomain> result = new ArrayList<TdsFunctionDomain>();
		// 获取父亲的
		for (TdsFunctionDomain itme : list) {
			Integer moId = itme.getId(); // 获取菜单id
			Integer parentid = itme.getParentId();// 获取菜单的父id
			if (parentid != 0) {
				if (parentid.equals(pid)) {
					List<TdsFunctionDomain> iterateMenu = recursion(list, moId);
					itme.setTdsFunctions(iterateMenu);
					result.add(itme);
				}
			}
		}
		return result;
	}

	@Override
	public BackResult<PageDomain<TdsRoleDomain>> pageByRole(String roleName, BasePageParam basePageParam) {
		BackResult<PageDomain<TdsRoleDomain>> result = new BackResult<PageDomain<TdsRoleDomain>>();
		PageDomain<TdsRoleDomain> pageListDomain = null;
		List<TdsRoleDomain> listDomain = new ArrayList<TdsRoleDomain>();
		try {

			Integer cur = basePageParam.getCurrentPage() <= 0 ? 1 : basePageParam.getCurrentPage();
			basePageParam.setPageNumber((cur - 1) * basePageParam.getNumPerPage());

			Integer count = tdsRoleMapper.queryCount(roleName);// 获取总数
			List<TdsRole> list = tdsRoleMapper.pageByRole(roleName, basePageParam.getPageNumber(),
					basePageParam.getNumPerPage());

			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsRoleDomain tdsDomain = null;
				for (TdsRole obj : list) {
					tdsDomain = new TdsRoleDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsRoleDomain>(basePageParam.getCurrentPage(),
						basePageParam.getNumPerPage(), count);
				pageListDomain.setTlist(listDomain);
				result.setResultObj(pageListDomain);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}

}
