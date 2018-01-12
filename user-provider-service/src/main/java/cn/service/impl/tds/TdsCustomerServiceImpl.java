package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsAttornLogMapper;
import cn.dao.tds.TdsCompanyMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.dao.tds.TdsUserDepartmentMapper;
import cn.dao.tds.TdsUserDiscountMapper;
import cn.dao.tds.TdsUserMapper;
import cn.dao.tds.TdsUserRoleMapper;
import cn.entity.tds.TdsAttornLog;
import cn.entity.tds.TdsCompany;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserDepartment;
import cn.entity.tds.TdsUserDiscount;
import cn.entity.tds.TdsUserRole;
import cn.entity.tds.view.TdsCustomerView;
import cn.service.tds.TdsCustomerService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAttornLogDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsUserDiscountDomain;
import main.java.cn.hhtp.util.MD5Util;

@Service
public class TdsCustomerServiceImpl extends BaseTransactService implements TdsCustomerService {

	private final static Logger logger = LoggerFactory.getLogger(TdsCustomerServiceImpl.class);

	@Autowired
	private TdsUserMapper tdsUserMapper;

	@Autowired
	private TdsCompanyMapper tdsCompanyMapper;

	@Autowired
	private TdsUserDepartmentMapper tdsUserDepartmentMapper;

	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;

	@Autowired
	private TdsAttornLogMapper tdsAttornLogMapper;

	@Autowired
	private TdsUserRoleMapper tdsUserRoleMapper;

	@Autowired
	private TdsUserDiscountMapper tdsUserDiscountMapper;

	@Transactional
	@Override
	public BackResult<Integer> update(Integer loginUserId, PageAuto auto, Integer upUserId, Integer[] arrRoles) {
		TransactionStatus status = this.begin();
		BackResult<Integer> result = new BackResult<Integer>();
		TdsUser tds = new TdsUser();
		try {

			// TdsUser isPhone = tdsUserMapper.loadByPhone(auto.getPhone());
			// if (null != isPhone &&
			// auto.getPhone().equals(isPhone.getPhone())) {
			// return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS,
			// "手机号码已注册过");
			// }
			//
			// Integer isTdsCom = tdsCompanyMapper.getComUrl(auto.getComUrl());
			// if (isTdsCom > 0) {
			// return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS,
			// "公司地址已经存在");
			// }

			// 根据用户id获取公司id;
			TdsUser tur = tdsUserMapper.loadById(upUserId);

			if (null != auto.getComUrl() && !"".equals(auto.getComUrl())) {
				// 网址修改
				TdsCompany com = new TdsCompany();
				com.setComUrl(auto.getComUrl());
				com.setId(tur.getComId());
				com.setUpdateTime(new Date());
				com.setUpdater(loginUserId);
				tdsCompanyMapper.update(com);
			}
			if (null != auto.getDepartId()) {
				// 部门修改
				TdsUserDepartment tdsUd = new TdsUserDepartment();
				tdsUd.setUserId(upUserId);
				tdsUd.setDepartId(auto.getDepartId());
				tdsUd.setUpdater(loginUserId);
				tdsUd.setUpdateTime(new Date());
				tdsUserDepartmentMapper.updateByUserId(tdsUd);
			}

			if (arrRoles.length > 0) {
				tdsUserRoleMapper.deleteByUserId(upUserId);
				// 保存角色用户关系
				List<TdsUserRole> list = new ArrayList<>();
				for (Integer item : arrRoles) {
					TdsUserRole tdsUserRole = new TdsUserRole();
					tdsUserRole.setUserId(upUserId);
					tdsUserRole.setCreater(loginUserId);
					tdsUserRole.setUpdater(loginUserId);
					tdsUserRole.setCreateTime(new Date());
					tdsUserRole.setRoleId(item);
					list.add(tdsUserRole);
				}

				tdsUserRoleMapper.saveRoleByUser(list);
			}

			// 用户信息修改
			tds.setPassword(MD5Util.getInstance().getMD5Code(auto.getPassWord()));
			tds.setPhone(auto.getPhone());
			tds.setContact(auto.getContact());
			tds.setUserName(auto.getCustomerName());
			tds.setId(upUserId);
			tdsUserMapper.update(tds);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			this.rollback(status);
			e.printStackTrace();
			logger.error("编辑修改功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	@Override
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsCustomer(PageAuto auto) {
		BackResult<PageDomain<TdsCustomerViewDomain>> result = new BackResult<PageDomain<TdsCustomerViewDomain>>();
		PageDomain<TdsCustomerViewDomain> pageListDomain = null;
		List<TdsCustomerViewDomain> listDomain = new ArrayList<TdsCustomerViewDomain>();
		try {
		
			if (null != auto.getCreateTime() || "".equals(auto.getCreateTime())) {
				Date endTime = DateUtils.addDay(auto.getCreateTime(), 1);
				auto.setStatTime(auto.getStatTime()); // 开始时间
				auto.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer cur = auto.getCurrentPage() <= 0 ? 1 : auto.getCurrentPage();
			auto.setPageNumber((cur - 1) * auto.getNumPerPage());
			Integer count = tdsUserCustomerMapper.queryCount(auto);// 获取总数
			List<TdsCustomerView> list = tdsUserCustomerMapper.pageTdsCustomer(auto);
			if (list.size() > 0 && list != null) {

				// 定义对象用于转换
				TdsCustomerViewDomain tdsDomain = null;
				for (TdsCustomerView obj : list) {
					tdsDomain = new TdsCustomerViewDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsCustomerViewDomain>(auto.getCurrentPage(), auto.getNumPerPage(),
						count);
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

	
	@Transactional
	@Override
	public BackResult<Integer> attorn(TdsAttornLogDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsAttornLog tdsAtt = new TdsAttornLog();
		TransactionStatus status=this.begin();
		domain.setCreateTime(new Date());
		try {
			//记录log
			BeanUtils.copyProperties(domain, tdsAtt);
			tdsAttornLogMapper.save(tdsAtt);
			//修改父级用户id
            TdsUser tdsUser=new TdsUser();
            tdsUser.setId(tdsAtt.getUserId());
            tdsUser.setParentUserId(tdsAtt.getAttornUserId());//数据转移
			tdsUserMapper.update(tdsUser);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> addTdsCustomer(PageAuto auto, Integer loginUserId, Integer[] arrRoles) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		try {
			//
			TdsUser isPhone = tdsUserMapper.loadByPhone(auto.getPhone());
			if (null != isPhone && auto.getPhone().equals(isPhone.getPhone())) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "手机号码已注册过");
			}

			Integer isTdsCom = tdsCompanyMapper.getComUrl(auto.getComUrl());
			if (isTdsCom > 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "公司地址已经存在");
			}
			TdsCompany tdsCom = new TdsCompany();
			// tdsCom.setComName(auto.getCustomerName()); //客户名称--公司名称
			tdsCom.setComUrl(auto.getComUrl());
			tdsCompanyMapper.save(tdsCom);

			TdsUser tdsUser = new TdsUser();
			tdsUser.setUserName(auto.getCustomerName()); // 客户名称
			tdsUser.setPassword(MD5Util.getInstance().getMD5Code(auto.getPassWord())); // 密码
			tdsUser.setPhone(auto.getPhone()); // 手机号码
			tdsUser.setContact(auto.getContact()); // 联系人
			tdsUser.setSource(StatusType.ADD_ADMIN); // 注册来源
			tdsUser.setIsDeleted("2");//客户注册审核，第注册成功 is_deleted 默认为 2 
			tdsUser.setParentUserId(loginUserId); // 归属父id
			tdsUser.setComId(tdsCom.getId());
			tdsUser.setCreater(loginUserId);
			tdsUserMapper.save(tdsUser); // 用户信息保存

			// 保存部门用户关系
			TdsUserDepartment tdsUserDepa = new TdsUserDepartment();
			tdsUserDepa.setUserId(tdsUser.getId()); // 注册成功返回用户id
			tdsUserDepa.setDepartId(auto.getDepartId()); // 部门
			tdsUserDepartmentMapper.save(tdsUserDepa);

			// 保存角色用户关系
			List<TdsUserRole> list = new ArrayList<>();
			for (Integer item : arrRoles) {
				TdsUserRole tdsUserRole = new TdsUserRole();
				tdsUserRole.setUserId(tdsUser.getId());
				tdsUserRole.setCreater(loginUserId);
				tdsUserRole.setCreateTime(new Date());
				tdsUserRole.setRoleId(item);
				list.add(tdsUserRole);
			}
			tdsUserRoleMapper.saveRoleByUser(list);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("新增功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("新增功能失败");
		}

		return result;
	}

	@Override
	public BackResult<TdsCustomerViewDomain> loadById(Integer id) {
		BackResult<TdsCustomerViewDomain> result = new BackResult<TdsCustomerViewDomain>();
		TdsCustomerViewDomain comDomain = new TdsCustomerViewDomain();
		try {
			TdsCustomerView obj = tdsUserCustomerMapper.loadByIdView(id);
			if (null == obj) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "该对象没信息");
			}
			BeanUtils.copyProperties(obj, comDomain);
			result.setResultObj(comDomain);
		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}

	
	@Override
	public BackResult<Integer> updatePrice(TdsUserDiscountDomain domain) {
		BackResult<Integer> result=new BackResult<Integer>();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		TdsUserDiscount  tds=new TdsUserDiscount();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsUserDiscountMapper.update(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	@Override
	public BackResult<List<TdsUserDiscountDomain>> selectAllByUserId(Integer userId) {
		BackResult<List<TdsUserDiscountDomain>> result = new BackResult<List<TdsUserDiscountDomain>>();
		List<TdsUserDiscountDomain> listDomain = new ArrayList<TdsUserDiscountDomain>();
		try {
			TdsUserDiscount tds = new TdsUserDiscount();
			tds.setUserId(userId);
			List<TdsUserDiscount> list = tdsUserDiscountMapper.selectAll(tds);
			if (list.size() > 0 && list != null) {
				TdsUserDiscountDomain tdsDomain = null;
				for (TdsUserDiscount obj : list) {
					tdsDomain = new TdsUserDiscountDomain();
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
	public BackResult<Integer> addTdsUserDiscount(TdsUserDiscountDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsUserDiscount tds = new TdsUserDiscount();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain, tds);
			// 增加一条框，是否有值输入，则进行保存
			if (null != tds.getStartMoney() && null != tds.getStartDiscount() && !"".equals(tds.getStartMoney())
					&& !"".equals(tds.getStartDiscount())) {
				tdsUserDiscountMapper.save(tds);
			}else{
				return new BackResult<>(ResultCode.RESULT_PARAM_EXCEPTIONS, "请输入完整起充量和折扣");
			}
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsUserDiscountMapper.deleteById(id);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID:" + id + "delete功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

}
