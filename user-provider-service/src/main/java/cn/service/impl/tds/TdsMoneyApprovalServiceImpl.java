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

import cn.dao.tds.TdsApprovalLogMapper;
import cn.dao.tds.TdsCommissionMapper;
import cn.dao.tds.TdsMoneyApprovalMapper;
import cn.dao.tds.TdsSerualInfoMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsCommission;
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsSerualInfo;
import cn.service.tds.TdsMoneyApprovalService;
import cn.utils.BeanHelper;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCommissionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsSerualInfoDomain;

@Service
public class TdsMoneyApprovalServiceImpl extends BaseTransactService implements TdsMoneyApprovalService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalServiceImpl.class);

	@Autowired
	private TdsMoneyApprovalMapper tdsMoneyApprovalMapper;

	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;

	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;

	@Autowired
	private TdsCommissionMapper tdsCommissionMapper;

	
	/**
	 * 客户管理下单
	 */
	@Transactional
	@Override
	public BackResult<Integer> downAddOrder(TdsMoneyApprovalDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsMoneyApproval tds = new TdsMoneyApproval();
		// 订单号码
		String ordrr = OrderNo.getOrderNo16();
		// 流水
		String serial = OrderNo.getSerial16();

		//Integer yonjing = 0; // 佣金

		TransactionStatus status = this.begin();
		try {
			domain.setApprovalType("1"); // 进入 <进账审核>
			domain.setApprovalStatus("0"); // 待审核
			domain.setBilling("0"); // 未开票
			domain.setOrderNumber(ordrr);
			domain.setSerialNumber(serial);
			domain.setCreateTime(new Date()); // 下单 订单时间
			BeanUtils.copyProperties(domain, tds);
			// 保存进账审核
			tdsMoneyApprovalMapper.save(tds);

			// 进入流水明细保存
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
			tdsSerual.setCreateTime(new Date());
			tdsSerual.setOrderNumber(ordrr);
			tdsSerual.setSerialNumber(serial);
			tdsSerual.setSerialStatus("1"); // 处理中
			//流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType("5");// 进账
			tdsSerual.setSerialMoney("500");// TODO//流水金额
			tdsSerual.setBeforeMoney("600");// 之前金额
			tdsSerualInfoMapper.save(tdsSerual);

			// 佣金列表保存
			TdsCommission tdsComm = new TdsCommission();
			tdsComm.setCreateTime(new Date());
			tdsComm.setOrderNumber(ordrr);
			tdsComm.setSerialNumber(serial);
			tdsComm.setCommStatus("1"); // 处理中
			tdsComm.setSerialMoney("500");// 到账金额
			tdsComm.setBeforeMoney("600");// 之前金额
			tdsCommissionMapper.save(tdsComm);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("客户管理下单功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> approvalByUpStatus(TdsMoneyApprovalDomain domain, String appRemarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		domain.setUpdateTime(new Date());
		TdsMoneyApproval tdsMoApp = new TdsMoneyApproval();
		String rej = null;
		try {
			BeanUtils.copyProperties(domain, tdsMoApp);
			// 审核类型 1进账审核 2出账审核 3退款审核 approvalType
			// 审核(0待审核  1已审核 2驳回  3到账 4线下开票  5 充账 )approvalStatus
			if (null != tdsMoApp.getApprovalType() && !"".equals(tdsMoApp.getApprovalType())) {
				switch (tdsMoApp.getApprovalType()) {
				case "1":
					// 进入 进账审核列表操作
					// 如果是驳回操作更改logs
					if ("2".equals(tdsMoApp.getApprovalStatus()))
						rej = "进账驳回";
					return this.approvalByUpStatusAll(tdsMoApp, appRemarks, rej);
				case "2":
					// 出账
					if ("2".equals(tdsMoApp.getApprovalStatus()))
						rej = "出账驳回";
					return this.approvalByUpStatusAll(tdsMoApp, appRemarks,rej);
				case "3":
					// 退款
					if ("2".equals(tdsMoApp.getApprovalStatus()))
						rej = "退款驳回";
					return this.approvalByUpStatusAll(tdsMoApp, appRemarks,rej);
				}
			}

			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	/**
	 * 通过和驳回操作
	 * 
	 * @param tdsMoApp
	 * @param appRemarks
	 *            原因
	 * @param rej
	 *            xx驳回
	 * @return
	 */
	@Transactional
	public BackResult<Integer> approvalByUpStatusAll(TdsMoneyApproval tdsMoApp, String appRemarks, String rej) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		try {
			if (null != tdsMoApp.getApprovalStatus() && !"".equals(tdsMoApp.getApprovalStatus())) {
				// 通过
				if ("1".equals(tdsMoApp.getApprovalStatus())) {
					tdsMoneyApprovalMapper.update(tdsMoApp);

				}
				if ("2".equals(tdsMoApp.getApprovalStatus())) {
					// 驳回原因 logs
					tdsApprovalLogMapper.save(new TdsApprovalLog(tdsMoApp.getUserId(), rej, appRemarks, new Date(),
							tdsMoApp.getOrderNumber()));
					//并操作驳回
					tdsMoneyApprovalMapper.update(tdsMoApp);
				}
			}
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("进账,出账，退款审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");

		}
		return result;
	}

	@Override
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageMoneyApprovalAll(TdsMoneyApprovalDomain domain) {
		BackResult<PageDomain<TdsMoneyApprovalDomain>> result = new BackResult<PageDomain<TdsMoneyApprovalDomain>>();
		PageDomain<TdsMoneyApprovalDomain> pageListDomain = null;
		List<TdsMoneyApprovalDomain> listDomain = new ArrayList<TdsMoneyApprovalDomain>();
		try {
			BeanHelper.beanHelperTrim(domain);  //去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsMoneyApproval tdsMoApp=new TdsMoneyApproval();
			BeanUtils.copyProperties(domain, tdsMoApp);
			Integer count = tdsMoneyApprovalMapper.queryCount(tdsMoApp);// 获取总数
			List<TdsMoneyApproval> list = tdsMoneyApprovalMapper.pageMoneyApprovalAll(tdsMoApp);
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsMoneyApprovalDomain tdsDomain = null;
				for (TdsMoneyApproval obj : list) {
					tdsDomain = new TdsMoneyApprovalDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsMoneyApprovalDomain>(domain.getCurrentPage(), domain.getNumPerPage(),
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

	
	
	@Override
	public BackResult<Integer> backOrder(TdsMoneyApprovalDomain domain) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	@Override
	public BackResult<PageDomain<TdsSerualInfoDomain>> pageTdsSerualInfo(TdsSerualInfoDomain domain) {
		BackResult<PageDomain<TdsSerualInfoDomain>> result = new BackResult<PageDomain<TdsSerualInfoDomain>>();
		PageDomain<TdsSerualInfoDomain> pageListDomain = null;
		List<TdsSerualInfoDomain> listDomain = new ArrayList<TdsSerualInfoDomain>();
		try {
			BeanHelper.beanHelperTrim(domain);  //去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsSerualInfo tdsSerual=new TdsSerualInfo();
			BeanUtils.copyProperties(domain, tdsSerual);
			Integer count = tdsSerualInfoMapper.queryCount(tdsSerual);// 获取总数
			List<TdsSerualInfo> list = tdsSerualInfoMapper.pageTdsSerualInfo(tdsSerual);
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsSerualInfoDomain tdsDomain = null;
				for (TdsSerualInfo obj : list) {
					tdsDomain = new TdsSerualInfoDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsSerualInfoDomain>(domain.getCurrentPage(), domain.getNumPerPage(),
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

	
	
	@Override
	public BackResult<PageDomain<TdsCommissionDomain>> pageTdsCommission(TdsCommissionDomain domain) {
		BackResult<PageDomain<TdsCommissionDomain>> result = new BackResult<PageDomain<TdsCommissionDomain>>();
		PageDomain<TdsCommissionDomain> pageListDomain = null;
		List<TdsCommissionDomain> listDomain = new ArrayList<TdsCommissionDomain>();
		try {
			BeanHelper.beanHelperTrim(domain);  //去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsCommission tdsComm=new TdsCommission();
			BeanUtils.copyProperties(domain, tdsComm);
			Integer count = tdsCommissionMapper.queryCount(tdsComm);// 获取总数
			List<TdsCommission> list =tdsCommissionMapper.pageTdsCommission(tdsComm);
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsCommissionDomain tdsDomain = null;
				for (TdsCommission obj : list) {
					tdsDomain = new TdsCommissionDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsCommissionDomain>(domain.getCurrentPage(), domain.getNumPerPage(),
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
}
