package cn.service.impl.tds;

import java.net.InetAddress;
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

import cn.dao.tds.TdsApprovalLogMapper;
import cn.dao.tds.TdsAttornLogMapper;
import cn.dao.tds.TdsCompanyMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.dao.tds.TdsUserDepartmentMapper;
import cn.dao.tds.TdsUserDiscountMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsAttornLog;
import cn.entity.tds.TdsCompany;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserCustomer;
import cn.entity.tds.TdsUserDepartment;
import cn.entity.tds.TdsUserDiscount;
import cn.entity.tds.view.TdsCustomerView;
import cn.service.tds.TdsCustomerService;
import cn.service.tds.TdsUserService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAttornLogDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsUserDiscountDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.enums.TdsEnum.CUSTOMERSTYPE;
import main.java.cn.enums.TdsEnum.USERSTATUS;
import main.java.cn.hhtp.util.MD5Util;


@SuppressWarnings("unchecked")
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
	private TdsUserDiscountMapper tdsUserDiscountMapper;
	
	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;
	
	
	@Autowired
	private TdsUserService tdsUserService;

	
	
	@Transactional
	@Override
	public BackResult<Integer> updateCustomer(TdsCustomerViewDomain domain, Integer loginUserId, String passWord) {
		TransactionStatus status = this.begin();
		TdsUser tds = new TdsUser();
		try {

			TdsUser tur = tdsUserMapper.loadById(domain.getUserId());
			
		    if(tur.getPhone().equals(domain.getPhone())){
		    	
		    	tds.setPhone(null);  //不做修改
		    	
		     }else{
		    	 
		    	 TdsUser isPhone = tdsUserMapper.loadByPhone(domain.getPhone());
		    	 
		    	 if(null==isPhone){
		    		 
		    		 tds.setPhone(domain.getPhone());
		    		 
		    	 }else{
		    		 return BackResult.error("手机号码重复注册");
		    	 }
		    	 
		     }
		    
			tds.setContact(domain.getContact());
			tds.setUserName(domain.getUserName());
			tds.setId(domain.getUserId());
			tds.setUpdater(loginUserId);
			tds.setUpdateTime(new Date());
			tdsUserMapper.update(tds);
			
			//根据用户id获取公司id;
			TdsCompany com = new TdsCompany();
			com.setComUrl(domain.getComUrl());
			com.setId(tur.getComId());
			com.setComName(domain.getComName());
			com.setUpdateTime(new Date());
			com.setUpdater(loginUserId);
			tdsCompanyMapper.update(com);

			// 部门修改
			TdsUserDepartment tdsUd = new TdsUserDepartment();
			tdsUd.setUserId(domain.getUserId());
			tdsUd.setDepartId(domain.getDepartId());
			tdsUd.setUpdater(loginUserId);
			tdsUd.setUpdateTime(new Date());
			tdsUserDepartmentMapper.updateByUserId(tdsUd);
			
			this.commit(status);
		} catch (Exception e) {
			this.rollback(status);
			logger.error("编辑修改功能信息出现系统异常：" + e.getMessage());
			return BackResult.error();
		}
		return BackResult.ok(1);
	}

	
	
	@Override
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsCustomer(TdsCustomerViewDomain domain) {
		BackResult<PageDomain<TdsCustomerViewDomain>> result = new BackResult<PageDomain<TdsCustomerViewDomain>>();
		PageDomain<TdsCustomerViewDomain> pageListDomain = null;
		List<TdsCustomerViewDomain> listDomain = new ArrayList<TdsCustomerViewDomain>();
		TdsCustomerView tdsCusView=new TdsCustomerView();
		
		try {
			
			if (null != domain.getStatTime() && !"".equals(domain.getStatTime())) {
				Date endTime = DateUtils.addDay(domain.getStatTime(), 1);
				domain.setStatTime(domain.getStatTime()); // 开始时间
				domain.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			
			BeanUtils.copyProperties(domain, tdsCusView);
			
			Integer count = tdsUserCustomerMapper.queryCount(tdsCusView);// 获取总数
			
			List<TdsCustomerView> list = tdsUserCustomerMapper.pageTdsCustomer(tdsCusView);
			
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsCustomerViewDomain tdsDomain = null;
				for (TdsCustomerView obj : list) {
					tdsDomain = new TdsCustomerViewDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsCustomerViewDomain>(domain.getCurrentPage(), domain.getNumPerPage(),
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
		TdsAttornLog tdsAtt = new TdsAttornLog();
		TransactionStatus status = this.begin();
		domain.setCreateTime(new Date());
		try {
			// 记录log
			BeanUtils.copyProperties(domain, tdsAtt);
			tdsAttornLogMapper.save(tdsAtt);
			// 修改父级用户id
			TdsUser tdsUser = new TdsUser();
			tdsUser.setId(tdsAtt.getUserId());
			tdsUser.setParentUserId(tdsAtt.getAttornUserId());// 数据转移
			tdsUserMapper.update(tdsUser);
			this.commit(status);
		} catch (Exception e) {
			this.rollback(status);
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			return BackResult.error();
		}
		return BackResult.ok(1);
	}

	@Transactional
	@Override
	public BackResult<Integer> addTdsCustomer(TdsCustomerViewDomain domain, Integer loginUserId, String passWord) {
		TransactionStatus status = this.begin();
		try {
			TdsUser isPhone = tdsUserMapper.loadByPhone(domain.getPhone());
			if (null != isPhone && domain.getPhone().equals(isPhone.getPhone())) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "手机号码已注册过");
			}

			// 新增用户的时候判断公司名字和地址是否存在，存在则修改，否则更新信息
			TdsCompany isTdsCom = tdsCompanyMapper.getComUrlAndComName(domain.getComUrl(), domain.getComName());
			TdsCompany tdsCom = new TdsCompany();
			
			if (null == isTdsCom) {
				// 保存
				tdsCom.setComUrl(domain.getComUrl());
				tdsCom.setComName(domain.getComName());
				tdsCom.setCreateTime(new Date());
				tdsCom.setCreater(loginUserId);
				tdsCompanyMapper.save(tdsCom);
			} else {
				// 更新
				tdsCom.setId(isTdsCom.getId());
				tdsCom.setUpdateTime(new Date());
				tdsCom.setUpdater(loginUserId);
				tdsCompanyMapper.update(tdsCom);
			}

			String md5Pass=MD5Util.getInstance().getMD5Code(passWord);
			
			//=======
			TdsUserDomain userDomain=new TdsUserDomain();
			
			userDomain.setPassword(md5Pass);
			
			userDomain.setPhone(domain.getPhone());
			
			userDomain.setLoginIp(InetAddress.getLocalHost().toString());
			
			//保存creuser表数据
			BackResult<Integer> creUid=tdsUserService.addCreUser(userDomain);
			
			if(null==creUid.getResultObj()){
				 throw new Exception(creUid.getResultMsg());
			}
					
				
			TdsUser tdsUser = new TdsUser();
			
			tdsUser.setCreUserId(creUid.getResultObj());
			
			tdsUser.setUserName(domain.getUserName()); // 客户名称
			
			tdsUser.setPassword(md5Pass); // 密码
			
			tdsUser.setPhone(domain.getPhone()); // 手机号码
			
			tdsUser.setContact(domain.getContact()); // 联系人
			
			tdsUser.setSource(StatusType.ADD_ADMIN); // 注册来源
			
			
			// 0：正常  1：申请中  2：驳回
			tdsUser.setStatus(USERSTATUS.PLEASE.getCode());
			
			tdsUser.setParentUserId(loginUserId); // 归属父id
			
			tdsUser.setComId(tdsCom.getId());
			
			tdsUser.setCreater(loginUserId);
			
			tdsUser.setCreateTime(new Date());
			
			
			tdsUserMapper.save(tdsUser); //用户信息保存
					
			// 保存部门用户关系
			TdsUserDepartment tdsUserDepa = new TdsUserDepartment();
			tdsUserDepa.setUserId(tdsUser.getId()); //注册成功返回用户id
			tdsUserDepa.setDepartId(domain.getDepartId());//部门
			tdsUserDepa.setCreater(loginUserId);
			tdsUserDepa.setCreateTime(new Date());
			tdsUserDepa.setUpdateTime(new Date());
			tdsUserDepartmentMapper.save(tdsUserDepa);
			this.commit(status);
		} catch (Exception e) {
			this.rollback(status);
			logger.error("新增功能信息出现系统异常：" + e.getMessage());
			BackResult.error("新增客户信息失败");
		}
		return BackResult.ok(1);
	}

	@Override
	public BackResult<TdsCustomerViewDomain> loadByIdView(Integer userId) {
		BackResult<TdsCustomerViewDomain> result = new BackResult<TdsCustomerViewDomain>();
		TdsCustomerViewDomain comDomain = new TdsCustomerViewDomain();
		try {
			TdsCustomerView obj = tdsUserCustomerMapper.loadByIdView(userId);
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
	@Transactional
	public BackResult<Integer> updatePrice(TdsUserDiscountDomain domain) {
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		TdsUserDiscount tds = new TdsUserDiscount();
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsUserDiscountMapper.update(tds);
		} catch (Exception e) {
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			return BackResult.error();
		}
		return BackResult.ok(1);
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
	@Transactional
	public BackResult<Integer> addTdsUserDiscount(TdsUserDiscountDomain domain) {
		TdsUserDiscount tds = new TdsUserDiscount();
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain, tds);
			// 增加一条框，是否有值输入，则进行保存
			if (null != tds.getStartMoney() && null != tds.getStartDiscount() && !"".equals(tds.getStartMoney())
					&& !"".equals(tds.getStartDiscount())) {
				tdsUserDiscountMapper.save(tds);
			} else {
				 return BackResult.error("请输入完整起充量和折扣");
			}
		} catch (Exception e) {
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			return BackResult.error();
		}
		return BackResult.ok(1);
	}

	@Override
	@Transactional
	public BackResult<Integer> deleteById(Integer id) {
		try {
			tdsUserDiscountMapper.deleteById(id);
		} catch (Exception e) {
			logger.error("用户ID:" + id + "delete功能信息出现系统异常：" + e.getMessage());
			return BackResult.error();
		}
		 return BackResult.ok(1);
	}
	
	
	
	@Override
	public BackResult<Integer> isAgree(String isAgree, Integer userId, String reas) {
		TransactionStatus status = this.begin();
		try {
			
			if (isAgree.equals(CUSTOMERSTYPE.NORMAL.getCode())) {
				TdsUser tUser = new TdsUser();
				tUser.setId(userId);
			    tUser.setStatus(USERSTATUS.PASST.getCode());  //0：正常  1：申请中  2：驳回
				tdsUserMapper.update(tUser);		
				
				//客户审核通过，累计消费插入数据
				TdsUserCustomer isUserCustomer = tdsUserCustomerMapper.loadByUserId(userId);
				if(isUserCustomer==null){
					TdsUserCustomer userCust=new TdsUserCustomer();
					userCust.setUserId(userId);
				    tdsUserCustomerMapper.save(userCust);	
				}
				
			} else {
				//驳回
				TdsApprovalLog tdsAppro = new TdsApprovalLog();
				tdsAppro.setUserId(userId);// 驳回的用户
				tdsAppro.setAppRemarks(reas);// 原因
				tdsAppro.setAppStatus("注册驳回");
				tdsAppro.setCreateTime(new Date());
				if (null == reas || "".equals(reas))
					tdsAppro.setAppRemarks("无驳回原因");
				
				tdsApprovalLogMapper.save(tdsAppro); //log保存
				TdsUser tUser = new TdsUser();
			    tUser.setStatus(USERSTATUS.REJ.getCode());  //0：正常  1：申请中  2：驳回
				tUser.setId(userId);
				tdsUserMapper.update(tUser);
			  }
			    commit(status);
		} catch (Exception e) {
			rollback(status);
			logger.error("客户审核操作功能错误：" + e.getMessage());
			return BackResult.error();
		}
		   return BackResult.ok(1);
	}

}
