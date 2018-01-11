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
import cn.dao.tds.TdsMoneyApprovalGoMapper;
import cn.dao.tds.TdsSerualInfoMapper;
import cn.dao.tds.TdsUserDiscountMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsCommission;
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsSerualInfo;
import cn.entity.tds.TdsUserDiscount;
import cn.service.tds.TdsMoneyApprovalService;
import cn.utils.BeanHelper;
import cn.utils.DateUtils;
import cn.utils.MoneyCommission;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCommissionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsSerualInfoDomain;

@Service
public class TdsMoneyApprovalServiceImpl extends BaseTransactService implements TdsMoneyApprovalService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalServiceImpl.class);

	@Autowired
	private TdsMoneyApprovalGoMapper tdsMoneyApprovalGoMapper;

	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;

	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;

	@Autowired
	private TdsCommissionMapper tdsCommissionMapper;

	@Autowired
	private TdsUserDiscountMapper tdsUserDiscountMapper;
	
	
	/**
	 * 客户管理--下单
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

		TransactionStatus status = this.begin();

		try {
			domain.setApprovalType(StatusType.APPROVAL_TYPE_GO); // 进入 <进账审核>
			domain.setApprovalStatus(StatusType.APPROVAL_STATUS_0); // 待审核
			domain.setBilling(StatusType.APPROVAL_BILLING_OFF); // 未开票
			domain.setOrderNumber(ordrr); // 下单 -订单号
			domain.setSerialNumber(serial); // 下单 -流水号
			domain.setCreateTime(new Date()); // 下单 订单时间
			domain.setUpdateTime(new Date());
			domain.setPayment("1");//进账类型
			BeanUtils.copyProperties(domain, tds);
			// 保存进账审核
			tdsMoneyApprovalGoMapper.save(tds);

			// 进入流水明细保存
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
			tdsSerual.setCreateTime(new Date());
			tdsSerual.setUpdateTime(new Date());
			tdsSerual.setOrderNumber(ordrr); // 保存下单-订单号
			tdsSerual.setSerialNumber(serial); // 保存下单-流水号
			tdsSerual.setSerialStatus("1"); // 处理中
			// 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType("5");// 下单进账类型
			tdsSerual.setUserId(tds.getUserId());
			tdsSerual.setCreater(domain.getCreater());
			tdsSerual.setSerialMoney(domain.getSumMoney());// 下单金额 涉及金额
			tdsSerualInfoMapper.save(tdsSerual);
			result.setResultObj(1);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("客户管理下单功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}
	

	@Transactional
	@Override
	public BackResult<Integer> approvalByUpStatusGo(TdsMoneyApprovalDomain domain, String appRemarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		domain.setUpdateTime(new Date());
		TdsMoneyApproval tdsMoApp = new TdsMoneyApproval();
		String rej = null;
		try {
			BeanHelper.beanHelperTrim(domain); // 去掉空格
			BeanUtils.copyProperties(domain, tdsMoApp);
			if ("2".equals(tdsMoApp.getApprovalStatus()))
				rej = "进账驳回";
			result=this.approvalByUpStatusGo(tdsMoApp, appRemarks, rej);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}
	
	

	/**
	 * 进账审核 -- 通过,驳回,到账,线下开票 操作
	 * @param tdsMoApp
	 * @param appRemarks
	 *            原因
	 * @param rej
	 *            xx驳回
	 * @return
	 */
	public BackResult<Integer> approvalByUpStatusGo(TdsMoneyApproval tdsMoApp, String appRemarks, String rej) {
		BackResult<Integer> result = new BackResult<Integer>();
		// 流水状态 1处理中 2已处理 3被驳回 : serial_status
		String serual = "1";
		
		
		//获取当前进账审核状态信息
		TdsMoneyApproval isStatus=tdsMoneyApprovalGoMapper.loadById(tdsMoApp.getId());
		
		try {
			if (null != tdsMoApp.getApprovalStatus() && !"".equals(tdsMoApp.getApprovalStatus())) {
				// 通过
				if (StatusType.APPROVAL_STATUS_1.equals(tdsMoApp.getApprovalStatus())) {
					tdsMoneyApprovalGoMapper.update(tdsMoApp);
					// 审核通过 等待金额 到账时间 再次确认
					serual = "1";//流水状态 1处理中  
				}

				// 驳回
				if (StatusType.APPROVAL_STATUS_2.equals(tdsMoApp.getApprovalStatus())) {
					// 驳回原因 记录
					tdsApprovalLogMapper.save(new TdsApprovalLog(tdsMoApp.getUserId(), rej, appRemarks, new Date(),
							tdsMoApp.getOrderNumber()));
					// 并操作驳回更新
					tdsMoneyApprovalGoMapper.update(tdsMoApp);
					// 更新流水明细驳回状态
					serual = "3";
				}
                
				//财务审核到账时间确认，更新状态和流水状态
				// 确认到账
				if (StatusType.APPROVAL_STATUS_3.equals(tdsMoApp.getApprovalStatus())) {
					tdsMoApp.setArriveTime(new Date());// 插入到账时间
					
					//判断是否通过初次审核
					if(!StatusType.APPROVAL_STATUS_1.equals(isStatus.getApprovalStatus())){
						return new BackResult<>(ResultCode.RESULT_FAILED,"没有通过初次审核不可操作");
					}
					
					tdsMoApp.setApprovalStatus(StatusType.APPROVAL_STATUS_1);
					tdsMoneyApprovalGoMapper.update(tdsMoApp);
					serual = "2"; // 到账已确认 流水状态显示处理成功  佣金可提现
					String disc="";//折扣比例
					
					TdsCommission tdsCommiss=new TdsCommission();
					TdsUserDiscount discount=tdsUserDiscountMapper.getDiscount(tdsMoApp.getUserId(),tdsMoApp.getSumMoney());
					if(null!=discount && !"".equals(discount.getStartDiscount())){
						disc=discount.getStartDiscount();
						tdsCommiss.setSerialMoney(String.valueOf(MoneyCommission.getCommission(tdsMoApp.getSumMoney(),disc)));
					}else{
						tdsCommiss.setSerialMoney("0");  //没有折扣不给佣金
					}
					//金额成功到账。佣金列表增加一条数据
					tdsCommiss.setSerialNumber(OrderNo.getSerial16());// 重新生成佣金流水号
					tdsCommiss.setOrderNumber(tdsMoApp.getOrderNumber()); //订单号不变
					tdsCommiss.setUserId(tdsMoApp.getUserId());
					//获取该用户的佣金金额
					tdsCommiss.setCreateTime(new Date());
					tdsCommiss.setUpdateTime(new Date());
					tdsCommiss.setCommStatus("2");//已到账
					tdsCommissionMapper.save(tdsCommiss);
					
				}
				
				
				// 开票
				if (StatusType.APPROVAL_STATUS_4.equals(tdsMoApp.getApprovalStatus())) {
					//判断是否通过初次审核
					if(!StatusType.APPROVAL_STATUS_1.equals(isStatus.getApprovalStatus())){
						return new BackResult<>(ResultCode.RESULT_FAILED,"没有通过初次审核不可操作");
					}
					tdsMoApp.setBilling(StatusType.APPROVAL_BILLING_ON);
					tdsMoApp.setApprovalStatus(StatusType.APPROVAL_STATUS_1);
					tdsMoneyApprovalGoMapper.update(tdsMoApp);
					result.setResultObj(1);
					return result;
				}
				
				//更新流水号状态
			    //upSerialStatus(tdsMoApp.getOrderNumber(), tdsMoApp.getSerialNumber(), serual);
				TdsSerualInfo tdsSerual = new TdsSerualInfo();
				tdsSerual.setUpdateTime(new Date());
				tdsSerual.setOrderNumber(tdsMoApp.getOrderNumber()); // -订单号
				tdsSerual.setSerialNumber(tdsMoApp.getSerialNumber()); // -流水号
				// 流水状态 1处理中 2已处理 3被驳回 : serial_status
				tdsSerual.setSerialStatus(serual);
				tdsSerualInfoMapper.upSerialByStatus(tdsSerual);
			    }
			   result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进账审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");

		}
		return result;
	}
	
	
	
	
	
	
	
	
	@Override
	public BackResult<PageDomain<TdsMoneyApprovalDomain>> pageMoneyApprovalGo(TdsMoneyApprovalDomain domain) {
		BackResult<PageDomain<TdsMoneyApprovalDomain>> result = new BackResult<PageDomain<TdsMoneyApprovalDomain>>();
		PageDomain<TdsMoneyApprovalDomain> pageListDomain = null;
		List<TdsMoneyApprovalDomain> listDomain = new ArrayList<TdsMoneyApprovalDomain>();
		try {
			BeanHelper.beanHelperTrim(domain); // 去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsMoneyApproval tdsMoApp = new TdsMoneyApproval();
			BeanUtils.copyProperties(domain, tdsMoApp);
			Integer count = tdsMoneyApprovalGoMapper.queryCount(tdsMoApp);// 获取总数
			List<TdsMoneyApproval> list = tdsMoneyApprovalGoMapper.pageMoneyApprovalGo(tdsMoApp);
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
	public BackResult<PageDomain<TdsSerualInfoDomain>> pageTdsSerualInfo(TdsSerualInfoDomain domain) {
		BackResult<PageDomain<TdsSerualInfoDomain>> result = new BackResult<PageDomain<TdsSerualInfoDomain>>();
		PageDomain<TdsSerualInfoDomain> pageListDomain = null;
		List<TdsSerualInfoDomain> listDomain = new ArrayList<TdsSerualInfoDomain>();
		try {
			
			if (null != domain.getStatTime() && !domain.getStatTime().equals("")) {
				Date endTime = DateUtils.addDay(domain.getStatTime(), 1);
				domain.setStatTime(domain.getStatTime()); // 开始时间
				domain.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
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
			if (null != domain.getStatTime() && !domain.getStatTime().equals("")) {
				Date endTime = DateUtils.addDay(domain.getStatTime(), 1);
				domain.setStatTime(domain.getStatTime()); // 开始时间
				domain.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsCommission tdsComm = new TdsCommission();
			BeanUtils.copyProperties(domain, tdsComm);
			Integer count = tdsCommissionMapper.queryCount(tdsComm);// 获取总数
			List<TdsCommission> list = tdsCommissionMapper.pageTdsCommission(tdsComm);
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

	
//
//	/**
//	 * 更新流水号状态
//	 * @param order
//	 * @param serialNo
//	 * @param serualParm
//	 */
//	public  BackResult<Integer> upSerialStatus(String order, String serialNo, String serualParm) {
//		// 根据流水号和订单号更新流水明细状态
//		BackResult<Integer> result=new BackResult<Integer>();
//		TdsSerualInfo tdsSerual = new TdsSerualInfo();
//		try {
//			tdsSerual.setUpdateTime(new Date());
//			tdsSerual.setOrderNumber(order); // -订单号
//			tdsSerual.setSerialNumber(serialNo); // -流水号
//			// 流水状态 1处理中 2已处理 3被驳回 : serial_status
//			tdsSerual.setSerialStatus(serualParm);
//			tdsSerualInfoMapper.upSerialByStatus(tdsSerual);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("流水号状态更新功能信息出现系统异常：" + e.getMessage());
//			result.setResultCode(ResultCode.RESULT_FAILED);
//			result.setResultMsg("数据修改失败");
//		}
//		   return result;
//	}
	
	
	

}
