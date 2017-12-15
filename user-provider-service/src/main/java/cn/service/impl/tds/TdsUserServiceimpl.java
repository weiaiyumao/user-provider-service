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
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsUserMapper;
import cn.entity.tds.TdsUser;
import cn.entity.tds.view.TdsUserView;
import cn.service.tds.TdsUserService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.hhtp.util.MD5Util;

@Service
public class TdsUserServiceimpl implements TdsUserService {

	private final static Logger logger = LoggerFactory.getLogger(TdsUserServiceimpl.class);

	@Autowired
	private TdsUserMapper tdsUserMapper;

	@Override
	public BackResult<TdsUserDomain> loadById(Integer id) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		try {
			TdsUser entity = tdsUserMapper.loadById(id);
			if(null==entity){
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS,"没有查询到用户信息");
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
    
	@Transactional
	@Override
	public BackResult<TdsUserDomain> save(TdsUserDomain domain) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		TdsUser tds = new TdsUser();
		TdsUser tdsUser = tdsUserMapper.loadByPhone(domain.getPhone());
		domain.setCreateTime(new Date());
		domain.setUpdateTime(new Date());
		// 注册加密
		if(null!=domain.getPassword() || "".equals(domain.getPassword()))
		domain.setPassword(MD5Util.getInstance().getMD5Code(domain.getPassword()));
		try {
			if (null != tdsUser) {
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS,"用户手机号码已存在");
			}
			BeanUtils.copyProperties(domain, tds);
			tdsUserMapper.save(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED,"数据落地异常");
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
			return new BackResult<Integer>(ResultCode.RESULT_FAILED,"数据落地异常");
		}
		return result;
	}
    
	@Transactional
	@Override
	public BackResult<TdsUserDomain> update(TdsUserDomain domain) {
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		domain.setUpdateTime(new Date());
		TdsUser tds = new TdsUser();
		//是否已经删除
		TdsUser isDeUser = tdsUserMapper.loadById(domain.getId());
		try {
			if(null!=isDeUser && isDeUser.getIsDeleted().equals("1")){
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS,"你修改的用户已删除");
			}
			BeanUtils.copyProperties(domain, tds);
			tdsUserMapper.update(tds);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED,"数据落地异常");
		}
		return result;
	}

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
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS,"该用户没有订单信息");
			}
			//TODO
			//new TdsUserView(curPage, pageSize)增加条件查询 new 
			List<TdsUser> pageList = tdsUserMapper.pageSelectAll(new TdsUserView(curPage, pageSize));
			for (TdsUser tds : pageList) {
				TdsUserDomain obj = new TdsUserDomain();
				BeanUtils.copyProperties(tds, obj);
				list.add(obj);
			}
			listDomain = new PageDomain<TdsUserDomain>(curPage,pageSize, count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<TdsUserDomain>>(ResultCode.RESULT_FAILED,"数据落地异常");
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
		BackResult<TdsUserDomain> result=new BackResult<TdsUserDomain>();
		try {

			Integer isUserName=0;
			isUserName=tdsUserMapper.isUserName(tdsUserDomain.getName());
			if(isUserName<1){
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS,"你还没有注册账号");
			}
			TdsUser isUser=tdsUserMapper.login(tdsUserDomain.getName(), tdsUserDomain.getPassword());
			if(null==isUser)
				return new BackResult<TdsUserDomain>(ResultCode.RESULT_DATA_EXCEPTIONS,"登录失败");
				
			isUser.setLastLoginTime(tdsUserDomain.getLastLoginTime());
			isUser.setLoginIp(tdsUserDomain.getLoginIp());
			tdsUserMapper.update(isUser);
			BeanUtils.copyProperties(isUser, tdsUserDomain);
			result.setResultObj(tdsUserDomain);
			
		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("登录功能出现系统异常：" + e.getMessage());
			return new BackResult<TdsUserDomain>(ResultCode.RESULT_FAILED,"数据落地异常");
		}
		 return result;
	}


}
