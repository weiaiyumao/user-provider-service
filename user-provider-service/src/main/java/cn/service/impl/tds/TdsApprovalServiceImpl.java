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

import cn.dao.tds.TdsApprovalLogMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.dao.tds.TdsUserMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsUser;
import cn.entity.tds.TdsUserCustomer;
import cn.entity.tds.view.TdsCustomerView;
import cn.service.tds.TdsApprovalService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCustomerViewDomain;

/**
 * 客户审核服务接口实现
 * 
 * @author ChuangLan
 *
 */
@Service
public class TdsApprovalServiceImpl extends BaseTransactService implements TdsApprovalService {

	private final static Logger logger = LoggerFactory.getLogger(TdsApprovalServiceImpl.class);

	@Autowired
	private TdsApprovalLogMapper tdsApprovalMapper;

	@Autowired
	private TdsUserMapper tdsUserMapper;
	
	

	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;

	@Override
	public BackResult<PageDomain<TdsCustomerViewDomain>> pageTdsApproval(PageAuto auto) {
		BackResult<PageDomain<TdsCustomerViewDomain>> result = new BackResult<PageDomain<TdsCustomerViewDomain>>();
		PageDomain<TdsCustomerViewDomain> listDomain = null;
		List<TdsCustomerViewDomain> list = new ArrayList<TdsCustomerViewDomain>();
		try {

		   // yyyy-mm-dd 天数加1
			if (null != auto.getStatTime() && !"".equals(auto.getStatTime())) {
				Date endTime = DateUtils.addDay(auto.getStatTime(), 1);
				auto.setStatTime(auto.getStatTime()); // 开始时间
				auto.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			
			// 客户注册审核，第注册成功 is_deleted 默认为2
			// 查询isDeleted为2的客户信息
			Integer count = tdsApprovalMapper.queryCount(auto);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			Integer cur = auto.getCurrentPage() <= 0 ? 1 : auto.getCurrentPage();
			auto.setPageNumber((cur - 1) * auto.getNumPerPage());

			List<TdsCustomerView> pageList = tdsApprovalMapper.pageTdsApproval(auto);
			if (pageList.size() <= 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "目前还没有账号权限信息");
			}
			TdsCustomerViewDomain obj = null;
			for (TdsCustomerView item : pageList) {
				obj = new TdsCustomerViewDomain();
				BeanUtils.copyProperties(item, obj);
				list.add(obj);
			}

			listDomain = new PageDomain<TdsCustomerViewDomain>(auto.getCurrentPage(), auto.getNumPerPage(), count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<TdsCustomerViewDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	public BackResult<Integer> isAgree(Integer isAgree, Integer userId, String reas) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		try {
			if (isAgree == 0 || "0".equals(isAgree)) {
				// 客户列表开账户
				// 客户注册审核，第注册成功 is_deleted 默认为 2 
				// 同意is_deleted 为0
				TdsUser tUser = new TdsUser();
				tUser.setIsDeleted("0");
				tUser.setId(userId);
				tdsUserMapper.update(tUser);
				
				
				//客户列表开通账号，审核通过，列表用户消费数据新增
				TdsUserCustomer userCust=new TdsUserCustomer();
				userCust.setUserId(userId);
				userCust.setCreateTime(new Date());
				userCust.setUpdateTime(new Date());
				userCust.setSumCommission("0");
				userCust.setSumMoney("0");
				userCust.setOverplusCommission("0");
				userCust.setLastMoneyTime(new Date());
			    tdsUserCustomerMapper.save(userCust);
				
				
			} else {
				// 注册驳回
				TdsApprovalLog tdsAppro = new TdsApprovalLog();
				tdsAppro.setUserId(userId);// 驳回的用户
				tdsAppro.setAppRemarks(reas);// 原因
				tdsAppro.setAppStatus("注册驳回");
				tdsAppro.setCreateTime(new Date());
				if (null == reas || "".equals(reas))
					tdsAppro.setAppRemarks("无驳回原因");

				tdsApprovalMapper.save(tdsAppro); // log 保存

				// 更新is_deleted 为2  注册驳回 是否直接删除，is_deleted
				// 已驳回is_deleted 为3
				TdsUser tUser = new TdsUser();
				tUser.setIsDeleted("3");
				tUser.setId(userId);
				tdsUserMapper.update(tUser);
			}
			result.setResultObj(1);
			commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			rollback(status);
			logger.error("客户审核操作功能错误：" + e.getMessage());
			return new BackResult<Integer>(ResultCode.RESULT_FAILED, "数据落地异常");
		}

		return result;
	}

}
