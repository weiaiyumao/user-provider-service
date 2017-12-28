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

import cn.dao.tds.TdsCompanyMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.tds.TdsCompany;
import cn.entity.tds.TdsUser;
import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
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
		TdsUser tds = new TdsUser();

		Integer isUserName = 0;
		isUserName = tdsUserMapper.isUserName(domain.getPhone());
		if (isUserName >= 1) {
			return new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "你手机号已注册");
		}
		// 注册加密
		if (null != domain.getPassword() || "".equals(domain.getPassword()))
			domain.setPassword(MD5Util.getInstance().getMD5Code(domain.getPassword()));
		try {

			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			BeanUtils.copyProperties(domain, tds);
			tdsUserMapper.save(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsUserMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户ID:" + id + "delete功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	// //TODO
	// @Transactional
	// @Override
	// public BackResult<TdsUserDomain> update(TdsUserDomain domain) {
	// BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
	// domain.setUpdateTime(new Date());
	// TdsUser tds = new TdsUser();
	// try {
	// // 如果修改的是密码，进行加密
	// if (!"".equals(domain.getPassword()) || null != domain.getPassword()) {
	// domain.setPassword(MD5Util.getInstance().getMD5Code(domain.getPassword()));
	// }
	// BeanUtils.copyProperties(domain, tds);
	// tdsUserMapper.update(tds); //用户信息保存
	// result.setResultObj(domain);
	// } catch (Exception e) {
	// e.printStackTrace();
	// logger.error("update功能信息出现系统异常：" + e.getMessage());
	// return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED, "数据落地异常");
	// }
	// return null;
	// }

	@Override
	public BackResult<PageDomain<TdsUserDomain>> pageSelectAll(TdsUserDomain domain, Integer pageSize,
			Integer curPage) {
		BackResult<PageDomain<TdsUserDomain>> result = new BackResult<PageDomain<TdsUserDomain>>();
		PageDomain<TdsUserDomain> listDomain = new PageDomain<TdsUserDomain>();
		List<TdsUserDomain> list = new ArrayList<TdsUserDomain>();
		TdsUser tuser = new TdsUser();
		Integer count = 0;
		try {
			BeanUtils.copyProperties(domain, tuser);
			count = tdsUserMapper.quertCount(tuser);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "该用户没有订单信息");
			}
			// TODO
			PageAuto auto = new PageAuto(curPage, pageSize);
			List<TdsUser> pageList = tdsUserMapper.pageSelectAll(auto);
			for (TdsUser tds : pageList) {
				TdsUserDomain obj = new TdsUserDomain();
				BeanUtils.copyProperties(tds, obj);
				list.add(obj);
			}
			listDomain = new PageDomain<TdsUserDomain>(curPage, pageSize, count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<TdsUserDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

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
			result.setResultObj(tdsUserDomain);

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

			String isNewPass = MD5Util.getInstance().getMD5Code(newPass);

			if (tdsUser.getPassword().equals(isNewPass)) {
				return new BackResult<Integer>(ResultCode.RESULT_DATA_EXCEPTIONS, "请不要已旧密码更改新密码");
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
	public BackResult<Integer> editUserInfo(TdsUserDomain domain) {
		BackResult<Integer>  result=new BackResult<Integer>();
        domain.setUpdateTime(new Date());
        TdsUser tdsUser=new TdsUser();
		try {
			BeanUtils.copyProperties(domain, tdsUser);
			tdsUser.setCustomerType(0); //个人编辑信息
			tdsUserMapper.update(tdsUser); // 用户信息保存
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("个人编辑功能信息出现系统异常：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}
	
    
	@Transactional
	@Override
	public BackResult<Integer> editComInfo(TdsCompanyDomain domain,Integer userId,String userName,String phone,String contact) {
		BackResult<Integer>  result=new BackResult<Integer>();
		TransactionStatus status=this.begin();
        TdsCompany tdsCom=new TdsCompany();
        TdsUser  loadUser=tdsUserMapper.loadById(userId);
		try {
			// 判断是否已经注册公司名和地址,如果为null 新增公司表，
			TdsCompany isTdsCompany = tdsCompanyMapper.getComName(domain.getComName());
			if (null != isTdsCompany && domain.getComName().equals(isTdsCompany.getComName())) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "公司名已经存在");
			}
				
			BeanUtils.copyProperties(domain, tdsCom);
			tdsCom.setCreateTime(new Date());
			tdsCom.setUpdateTime(new Date());
			Integer isSave = tdsCompanyMapper.save(tdsCom);
			// 保存成功赋值公司id
			if (isSave > 0){
				if(null!=loadUser.getCustomerType() && 0==loadUser.getCustomerType() || 11==loadUser.getCustomerType()){
					loadUser.setCustomerType(11); //编辑个人和企业
                }else{
					loadUser.setCustomerType(1); //编辑企业
				}
				loadUser.setId(userId);
				loadUser.setUserName(userName);
				loadUser.setPhone(phone);
				loadUser.setContact(contact);
				loadUser.setComId(tdsCom.getId());
				loadUser.setCustomerType(1);  //只编辑了公司企业信息
				tdsUserMapper.update(loadUser); // 用户信息保存
			}					
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

}
