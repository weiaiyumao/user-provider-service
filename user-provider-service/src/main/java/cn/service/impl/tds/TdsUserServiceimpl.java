package cn.service.impl.tds;

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

import cn.dao.tds.TdsCompanyMapper;
import cn.dao.tds.TdsUserMapper;
import cn.dao.tds.TdsUserRoleMapper;
import cn.entity.tds.TdsCompany;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserRole;
import cn.service.CreUserService;
import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.CreUserDomain;
import main.java.cn.domain.tds.TdsCompanyDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.hhtp.util.MD5Util;

@Service
public class TdsUserServiceimpl extends BaseTransactService implements TdsUserService {

	private final static Logger logger = LoggerFactory.getLogger(TdsUserServiceimpl.class);

	@Autowired
	private TdsUserMapper tdsUserMapper;

	@Autowired
	private TdsCompanyMapper tdsCompanyMapper;
	
	@Autowired
	private TdsUserRoleMapper tdsUserRoleMapper;
	
	@Autowired
	private CreUserService creUserService;

	@Override
	public BackResult<TdsUserDomain> loadById(Integer id) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		try {
			TdsUser entity = tdsUserMapper.loadById(id);
			if (null == entity) {
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS, "没有查询到用户信息");
			}
			TdsUserDomain domain = new TdsUserDomain();
			BeanUtils.copyProperties(entity, domain);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID：" + id + "查询用户信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	/**
	 * 新增用户注册
	 */
	@Transactional
	@Override
	public BackResult<Integer> save(TdsUserDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsUser tdsUser = new TdsUser();
		TransactionStatus status=this.begin();
		Integer isUserName = tdsUserMapper.isUserName(domain.getPhone());
		if (isUserName >= 1) {
			return new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "你手机号已注册");
		}
		// 注册密码加密
		if (null != domain.getPassword() || "".equals(domain.getPassword()))
			domain.setPassword(MD5Util.getInstance().getMD5Code(domain.getPassword()));
		
		
		try {
		
			//保存  tds_user   用户表
			BackResult<Integer> creUid=this.addCreUser(domain);
			if(null==creUid.getResultObj()){
				return creUid;
			}
			domain.setCreUserId(creUid.getResultObj());  //关联 cre_user 表 用户同步
			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			domain.setStatus("0");  //正常注册
			BeanUtils.copyProperties(domain,tdsUser);
			tdsUserMapper.save(tdsUser);
						
			//新用户默认为业务员角色
			TdsUserRole userRole=new TdsUserRole();
			userRole.setCreateTime(new Date());
			userRole.setUserId(tdsUser.getId());
			userRole.setRoleId(StatusType.ROLE_YWY); //业务员角色
			tdsUserRoleMapper.save(userRole);

			this.commit(status);	
			result.setResultObj(tdsUser.getId());
			
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "新增用户失败");
		}
		return result;
	}
	
	
	
    @Override
    @Transactional
	public BackResult<Integer> addCreUser(TdsUserDomain domain) {
		   BackResult<Integer> result=new BackResult<Integer>();
		    CreUserDomain creUser=new CreUserDomain();
			try {
				creUser.setUserPassword(domain.getPassword());
				creUser.setUserPhone(domain.getPhone());
				creUser.setUserName("用户名_"+domain.getPhone());//没有用户名，先默认手机号码
				creUser.setCreateTime(new Date());
				creUser.setUpdateTime(new Date());
				creUser.setNickName("nic_"+domain.getPhone());
				creUser.setLastLoginIp(domain.getLoginIp());
				BackResult<CreUserDomain> cuDomain=creUserService.findOrsaveUser(creUser);
				CreUserDomain creUserDomain=cuDomain.getResultObj();
				if(null==creUserDomain){
					 return new BackResult<>(ResultCode.RESULT_FAILED, cuDomain.getResultMsg());
				}
				  result.setResultObj(creUserDomain.getId());
			  } catch (Exception e) {
				e.printStackTrace();
			    logger.info("注册数据同步 cre_user表错误！");
			    return new BackResult<>(ResultCode.RESULT_FAILED, "CreUser表用户数据保存失败");
		 	} 
			return result;
		}
	
	
	

	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsUserMapper.deleteById(id);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID:" + id + "delete功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}


//	@Override
//	public BackResult<PageDomain<TdsUserDomain>> pageSelectAll(TdsUserDomain domain, Integer pageSize,
//			Integer curPage) {
//		BackResult<PageDomain<TdsUserDomain>> result = new BackResult<PageDomain<TdsUserDomain>>();
//		PageDomain<TdsUserDomain> listDomain = new PageDomain<TdsUserDomain>();
//		List<TdsUserDomain> list = new ArrayList<TdsUserDomain>();
//		TdsUser tuser = new TdsUser();
//		Integer count = 0;
//		try {
//			BeanUtils.copyProperties(domain, tuser);
//			count = tdsUserMapper.quertCount(tuser);
//			if (count == 0) {
//				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "该用户没有订单信息");
//			}
//			PageAuto auto = new PageAuto(curPage, pageSize);
//			List<TdsUser> pageList = tdsUserMapper.pageSelectAll(auto);
//			for (TdsUser tds : pageList) {
//				TdsUserDomain obj = new TdsUserDomain();
//				BeanUtils.copyProperties(tds, obj);
//				list.add(obj);
//			}
//			listDomain = new PageDomain<TdsUserDomain>(curPage, pageSize, count);
//			listDomain.setTlist(list);
//			result.setResultObj(listDomain);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("分页查询功能出现系统异常：" + e.getMessage());
//			return new BackResult<PageDomain<TdsUserDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
//		}
//		return result;
//	}

	@Override
	public BackResult<List<TdsUserDomain>> selectAll(TdsUserDomain entity) {
		return null;
	}

	@Transactional
	@Override
	public BackResult<TdsUserDomain> login(TdsUserDomain tdsUserDomain) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		try {

			Integer isUserName = 0;
			isUserName = tdsUserMapper.isUserName(tdsUserDomain.getPhone());
			if (isUserName < 1) {
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED, "你还没有注册账号");
			}
			TdsUser isUser = tdsUserMapper.login(tdsUserDomain.getPhone(), tdsUserDomain.getPassword());
			if (null == isUser)
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED, "登录失败");
			// 最近登录时间
			isUser.setLastLoginTime(new Date());
			// 最近登录ip
			isUser.setLoginIp(tdsUserDomain.getLoginIp());
			tdsUserMapper.update(isUser);
			BeanUtils.copyProperties(isUser, tdsUserDomain);
			// 登录结果返回基本信息
			TdsUserDomain user = new TdsUserDomain();
			user.setId(tdsUserDomain.getId());
			user.setUserName(tdsUserDomain.getUserName());
			user.setName(tdsUserDomain.getName());
			user.setPhone(tdsUserDomain.getPhone());
			user.setHedehref(tdsUserDomain.getHedehref());
			result.setResultObj(user);

		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("登录功能出现系统异常：" + e.getMessage());
			return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	public BackResult<TdsUserDomain> loadByPhone(String phone) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		try {
			TdsUser tdsUser = tdsUserMapper.loadByPhone(phone);
			if (null == tdsUser) {
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS, "获取手机用户信息失败");
			}
			TdsUserDomain domain = new TdsUserDomain();
			BeanUtils.copyProperties(tdsUser, domain);
			result.setResultObj(domain);
		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("根据用户电话获取信息系统异常：" + e.getMessage());
			return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	public BackResult<Integer> upPassWord(String usedPass, String newPass, Integer userId) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			// 获取更改用户信息
			TdsUser tdsUser = tdsUserMapper.loadById(userId);
			if (null == tdsUser) {
				return new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "该用户不存在");
			}
			// 判断原密码是否正确
			if (!MD5Util.getInstance().getMD5Code(usedPass).equals(tdsUser.getPassword())) {
				return new BackResult<Integer>(ResultCode.RESULT_FAILED, "原密码错误");
			}
			// 新密码加密
			String isNewPass = MD5Util.getInstance().getMD5Code(newPass);

			if (tdsUser.getPassword().equals(isNewPass)) {
				return new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "请不要再次已原密码更改新密码");
			}
			tdsUserMapper.upPassWord(userId, isNewPass); // 密码更改
			result.setResultObj(1);

		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("根据用户修改密码功能异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	@Transactional
	public BackResult<Integer> editUserInfo(TdsUserDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		domain.setUpdateTime(new Date());
		TdsUser tdsUser = new TdsUser();
		try {
			BeanUtils.copyProperties(domain, tdsUser);
			tdsUserMapper.update(tdsUser); // 用户信息保存
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("个人编辑功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	@Transactional
	public BackResult<Integer> editComInfo(TdsCompanyDomain domain, Integer userId, String userName, String phone,
			String contact) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		TdsCompany tdsCom = new TdsCompany();
		TdsUser loadUser = tdsUserMapper.loadById(userId);
		try {
			BeanUtils.copyProperties(domain, tdsCom);
			// 判断用户是否第一次编辑企业,进行保存
			if (null == loadUser.getComId()) {
				tdsCom.setUpdateTime(new Date());
				tdsCom.setCreateTime(new Date());
				tdsCompanyMapper.save(tdsCom);
				loadUser.setComId(tdsCom.getId());  //保存进用户表
			} else {
				// 修改状态
				tdsCom.setUpdateTime(new Date());
				tdsCom.setId(loadUser.getComId());
				tdsCompanyMapper.update(tdsCom);
			}

			// 保存成功赋值公司id
			loadUser.setId(userId);
			loadUser.setUserName(userName);
			loadUser.setPhone(phone);
			loadUser.setContact(contact);
			tdsUserMapper.update(loadUser); // 用户信息保存

			result.setResultObj(1);
			commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(status);
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	@Transactional
	public BackResult<Integer> updateHeadImg(Integer id, String hedehref) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsUserMapper.updateHeadImg(id, hedehref);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("头像编辑功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}


}
