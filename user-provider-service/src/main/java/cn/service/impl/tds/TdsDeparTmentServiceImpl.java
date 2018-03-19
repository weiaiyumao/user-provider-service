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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import cn.dao.tds.TdsDepartmentMapper;
import cn.dao.tds.TdsFunctionMapper;
import cn.dao.tds.TdsFunctionRoleMapper;
import cn.dao.tds.TdsRoleMapper;
import cn.dao.tds.TdsUserDepartmentMapper;
import cn.dao.tds.TdsUserMapper;
import cn.dao.tds.TdsUserRoleMapper;
import cn.entity.tds.TdsDepartment;
import cn.entity.tds.TdsFunction;
import cn.entity.tds.TdsFunctionRole;
import cn.entity.tds.TdsRole;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserDepartment;
import cn.entity.tds.TdsUserRole;
import cn.entity.tds.view.UserRoleDepartmentView;
import cn.service.tds.TdsDepartmentService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsDepartmentDomain;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.domain.tds.UserRoleDepartmentViewDomain;
import main.java.cn.hhtp.util.MD5Util;

@Service
public class TdsDeparTmentServiceImpl extends BaseTransactService implements TdsDepartmentService {

	private final static Logger logger = LoggerFactory.getLogger(TdsDeparTmentServiceImpl.class);

	@Autowired
	private TdsDepartmentMapper tdsDepartmentMapper;

	@Autowired
	private TdsUserMapper tdsUserMapper;

	@Autowired
	private TdsUserDepartmentMapper tdsUserDepartmentMapper;

	@Autowired
	private TdsUserRoleMapper tdsUserRoleMapper;

	@Autowired
	private TdsRoleMapper tdsRoleMapper;

	@Autowired
	private TdsFunctionMapper tdsFunctionMapper;

	@Autowired
	private TdsFunctionRoleMapper tdsFunctionRoleMapper;

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
	public BackResult<PageDomain<UserRoleDepartmentViewDomain>> pageUserRoleDepartmentView(UserRoleDepartmentViewDomain domain) {
		
		BackResult<PageDomain<UserRoleDepartmentViewDomain>> result = new BackResult<PageDomain<UserRoleDepartmentViewDomain>>();
		PageDomain<UserRoleDepartmentViewDomain> listDomain = null;
		
		List<UserRoleDepartmentViewDomain> list = new ArrayList<UserRoleDepartmentViewDomain>();
		
		UserRoleDepartmentView view=new UserRoleDepartmentView();
		try {

			BeanUtils.copyProperties(domain, view);
			
		    // yyyy-mm-dd 天数加1
			if (null != view.getStatTime() && !view.getStatTime().equals("")) {
				Date endTime = DateUtils.addDay(view.getStatTime(), 1);
				view.setStatTime(view.getStatTime()); // 开始时间
				view.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer count = tdsDepartmentMapper.queryCount(view);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			Integer cur = view.getCurrentPage() <= 0 ? 1 : view.getCurrentPage();
			view.setPageNumber((cur - 1) * view.getNumPerPage());
			
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

	@Transactional
	@Override
	public BackResult<Integer> addUserConfig(String name, String passWord, String phone, Integer departmentId,
			Integer positionId, Integer comId, Integer[] arrRoles, Integer loginUserId) {
		TransactionStatus status = this.begin();
		BackResult<Integer> result = new BackResult<Integer>();
		TdsUser tds = new TdsUser();
		TdsUser isUserPhone = tdsUserMapper.loadByPhone(phone);
		if (null != isUserPhone && phone.equals(isUserPhone.getPhone())) {
			return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "账号已存在");
		}
		
//		if (null != isUserPhone && name.equals(isUserPhone.getName())) {
//			return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "输入的用户名已经存在");
//		}

		try {
			tds.setCreateTime(new Date());
			tds.setComId(comId);
			tds.setCreater(loginUserId); 
			tds.setPassword(MD5Util.getInstance().getMD5Code(passWord));
			tds.setSource(StatusType.ADD_ADMIN);
			tds.setName(name);
			tds.setPhone(phone);
			tdsUserMapper.addBackAdminiUser(tds); // 用户信息保存

			// 部门信息保存
			TdsUserDepartment tdsUserDepartment = new TdsUserDepartment();
			tdsUserDepartment.setUserId(tds.getId());
			tdsUserDepartment.setCreateTime(new Date());
			tdsUserDepartment.setDepartId(departmentId);
			tdsUserDepartment.setCreater(loginUserId);
			tdsUserDepartment.setPositionId(positionId);
			tdsUserDepartmentMapper.save(tdsUserDepartment);

			// 角色信息保存
			List<TdsUserRole> list = new ArrayList<>();
			for (Integer item : arrRoles) {
				TdsUserRole tdsUserRole = new TdsUserRole();
				tdsUserRole.setUserId(tds.getId());
				tdsUserRole.setCreater(loginUserId);
				tdsUserRole.setCreateTime(new Date());
				tdsUserRole.setRoleId(item);
				list.add(tdsUserRole);
			}

			tdsUserRoleMapper.saveRoleByUser(list);
			result.setResultObj(1);
			this.commit(status);// 事务提交
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status); // 回滚
			logger.error("添加账号查功能出现系统异常" + e.getMessage());
			return new BackResult<>(ResultCode.RESULT_FAILED, "数据落地异常");

		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> addCustomPermissions(String soleName, Integer loginUserId, Integer[] arrfuns) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		try {
			TdsRole trole = new TdsRole();
			trole.setCreater(loginUserId);
			trole.setUpdater(loginUserId);
			trole.setCreateTime(new Date());
			trole.setUpdateTime(new Date());
			trole.setIsDefault(StatusType.CUSTOM_ROLES);
			trole.setRoleName(soleName);
			tdsRoleMapper.save(trole);

			List<TdsFunctionRole> listFunRole = new ArrayList<TdsFunctionRole>();
			// 子级数组是否存入id，直接从子级赋值
			if (arrfuns.length > 0 || null != arrfuns) {
				for (Integer item : arrfuns) {
					// 保存至Liat<>
					listFunRole.add(new TdsFunctionRole(item, trole.getId(), new Date(), loginUserId));
				}

			}
			tdsFunctionRoleMapper.addArrByfunId(listFunRole);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("自定义角色添加功能出现系统异常" + e.getMessage());
			return new BackResult<>(ResultCode.RESULT_FAILED, "数据落地异常");
		}

		return result;
	}

	
	
	@Override
	public BackResult<Integer> addFun(TdsFunctionDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsFunction tds = new TdsFunction();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsFunctionMapper.save(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("自定义权限功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

}
