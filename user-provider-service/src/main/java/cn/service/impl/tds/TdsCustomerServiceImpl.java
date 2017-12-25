package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsCompanyMapper;
import cn.dao.tds.TdsCustomerMapper;
import cn.dao.tds.TdsUserDepartmentMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.tds.TdsCompany;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserDepartment;
import cn.entity.tds.view.TdsCustomerView;
import cn.service.tds.TdsCustomerService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;
import main.java.cn.domain.tds.TdsUserDomain;
import main.java.cn.hhtp.util.MD5Util;

@Service
public class TdsCustomerServiceImpl  extends BaseTransactService  implements TdsCustomerService   {

	private final static Logger logger = LoggerFactory.getLogger(TdsCustomerServiceImpl.class);

	@Autowired
	private TdsUserMapper tdsUserMapper;

	@Autowired
	private TdsCompanyMapper tdsCompanyMapper;

	@Autowired
	private TdsUserDepartmentMapper tdsUserDepartmentMapper;
	
	@Autowired
	private TdsCustomerMapper tdsCustomerMapper;

	@Transactional
	@Override
	public BackResult<TdsUserDomain> update(TdsUserDomain domain, Integer departmentId, String comUrl) {
		domain.setUpdateTime(new Date());
		TdsUser tds = new TdsUser();
		BackResult<TdsUserDomain> result = new BackResult<TdsUserDomain>();
		TransactionStatus status=this.begin();
		if (null != domain.getPassword() || "".equals(domain.getPassword()))
			domain.setPassword(MD5Util.getInstance().getMD5Code(domain.getPassword()));
		try {
			//根据用户id获取公司id;
			TdsUser tur=tdsUserMapper.loadById(domain.getId());
			if (null != comUrl || !"".equals(comUrl)) {
				// 网址修改
				TdsCompany com = new TdsCompany();
				com.setComUrl(comUrl);
				com.setId(tur.getComId());
				com.setUpdateTime(new Date());
				com.setUpdater(domain.getUpdater());
				tdsCompanyMapper.update(com);
			}
			if (null != departmentId) {
				// 部门修改
				TdsUserDepartment tdsUd = new TdsUserDepartment();
				tdsUd.setUserId(domain.getId());
				tdsUd.setDepartId(departmentId);
				tdsUd.setUpdater(domain.getUpdater());
				tdsUd.setUpdateTime(new Date());
				tdsUserDepartmentMapper.updateByUserId(tdsUd);
			}
			// 用户信息修改
			BeanUtils.copyProperties(domain, tds);
			tdsUserMapper.update(tds);
			result.setResultObj(domain);
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
		    PageDomain<TdsCustomerViewDomain> pageListDomain =null;
			List<TdsCustomerViewDomain> listDomain = new ArrayList<TdsCustomerViewDomain>();
		try {
			//PageAuto auto=new PageAuto(currentPage,numPerPage);
			if (null != auto.getCreateTime() || "".equals(auto.getCreateTime())) {
				Date endTime = DateUtils.addDay(auto.getCreateTime(), 1);
				auto.setStatTime(auto.getStatTime()); // 开始时间
				auto.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer count=tdsCustomerMapper.queryCount(auto);//获取总数
			List<TdsCustomerView> list = tdsCustomerMapper.pageTdsCustomer(auto);
			if (list.size() > 0 && list != null) {
				
				//定义对象用于转换
				TdsCustomerViewDomain tdsDomain = null;
				for (TdsCustomerView obj : list) {
					tdsDomain = new TdsCustomerViewDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				//构造计算分页参数
				pageListDomain=new PageDomain<TdsCustomerViewDomain>(auto.getCurrentPage(), auto.getNumPerPage(),count);
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


	@Override
	public BackResult<PageAuto> attorn(PageAuto auto) {
		   
		
		return null;
	}
	
	
	

}
