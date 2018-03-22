package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import cn.dao.tds.TdsMoneyApprovalOutMapper;
import cn.dao.tds.TdsSerualInfoMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.dao.tds.TdsUserDiscountMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsCommission;
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsSerualInfo;
import cn.entity.tds.TdsUserCustomer;
import cn.entity.tds.TdsUserDiscount;
import cn.service.tds.TdsMoneyApprovalService;
import cn.service.tds.TdsSerualService;
import cn.utils.BeanHelper;
import cn.utils.DateUtils;
import cn.utils.MoneyCommission;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsApprovalOutDomain;
import main.java.cn.domain.tds.TdsApprovalOutQueryDomain;
import main.java.cn.domain.tds.TdsCommissionDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsSerualInfoDomain;

@Service
public class TdsMoneyApprovalServiceImpl extends BaseTransactService implements TdsMoneyApprovalService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalServiceImpl.class);

	@Autowired
	private TdsMoneyApprovalGoMapper tdsMoneyApprovalGoMapper;
	
	@Autowired
	private TdsMoneyApprovalOutMapper tdsMoneyApprovalOutMapper;

	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;

	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;

	@Autowired
	private TdsCommissionMapper tdsCommissionMapper;

	@Autowired
	private TdsUserDiscountMapper tdsUserDiscountMapper;

	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;
	
	
	
	@Autowired
	private TdsSerualService tdsSerualService;
	
	
	
	private  static String isOrderNumber="";
	

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

		TransactionStatus status = this.begin();
		try {
			domain.setApprovalType(StatusType.APPROVAL_TYPE_GO); // 进入 <进账审核>
			domain.setApprovalStatus(StatusType.APPROVAL_STATUS_0); // 待审核
			domain.setBilling(StatusType.APPROVAL_BILLING_OFF); // 未开票
			domain.setOrderNumber(ordrr); // 下单 -订单号
			domain.setSerialNumber(OrderNo.getSerial16()); // 下单 -进账审核流水号
			domain.setCreateTime(new Date()); //下单 订单时间
			domain.setUpdateTime(new Date());
			domain.setPayment("1");// 进账类型
			
			

			// 查询折扣
			TdsUserDiscount discount = tdsUserDiscountMapper.getDiscount(domain.getUserId(), domain.getSumMoney());
			if (null != discount && !"".equals(discount.getStartDiscount())) {
				String disc = discount.getStartDiscount();
				domain.setCommissonMoney(String.valueOf(MoneyCommission.getCommission(domain.getSumMoney(), disc)));
			} else {
				domain.setCommissonMoney("0"); // 没有折扣不给佣金
			}

			BeanUtils.copyProperties(domain, tds);
			
			// 保存进账审核
			tdsMoneyApprovalGoMapper.save(tds);

			//保存流水明细
			result=tdsSerualService.addSerual("1", "5", tds.getUserId(), domain.getSumMoney(), ordrr);
			if(result.getResultCode().equals(ResultCode.RESULT_FAILED)){
            	return new BackResult<>(result.getResultCode(), result.getResultMsg());
            }
			// TODO
			// 佣金列表增加一条数据
			TdsCommission tdsCommiss = new TdsCommission();
			tdsCommiss.setSerialMoney(domain.getCommissonMoney()); // 佣金
			tdsCommiss.setSerialNumber(OrderNo.getSerial16());// 重新生成佣金流水号
			tdsCommiss.setOrderNumber(ordrr); // 订单号不变
			tdsCommiss.setUserId(domain.getUserId());
			tdsCommiss.setCreateTime(new Date());
			tdsCommiss.setUpdateTime(new Date());
			tdsCommiss.setCommStatus("1");// 处理中
			tdsCommissionMapper.save(tdsCommiss);

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

	@Transactional
	@Override
	public BackResult<Integer> approvalByUpStatusGo(TdsMoneyApprovalDomain domain, String appRemarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		domain.setUpdateTime(new Date());
		TdsMoneyApproval tdsMoApp = new TdsMoneyApproval();
		try {
			BeanHelper.beanHelperTrim(domain); // 去掉空格
			BeanUtils.copyProperties(domain, tdsMoApp);
			result = this.approvalByUpStatusGo(tdsMoApp, appRemarks);
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
	 * 
	 * @param tdsMoApp
	 * @param appRemarks
	 *            原因
	 * @param rej
	 *            xx驳回
	 * @return
	 */
	public BackResult<Integer> approvalByUpStatusGo(TdsMoneyApproval tdsMoApp, String appRemarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		// 流水状态 1处理中 2已处理 3被驳回 : serial_status
		// 佣金状态 1处理中 2已到账 3被驳回 4.已提现
		String status = "1";

		// 获取当前进账审核状态信息
		String isStatus = tdsMoneyApprovalGoMapper.isStatus(tdsMoApp.getUserId(),tdsMoApp.getOrderNumber());
		
		if(null==isStatus){
			return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "审核数据空");
		}
			
		TdsCommission tdsCommiss = new TdsCommission();
		try {
			// 审核(0待审核 1已审核 2驳回 3到账 4线下开票)
			if (null != tdsMoApp.getApprovalStatus() && !"".equals(tdsMoApp.getApprovalStatus())) {

				switch (tdsMoApp.getApprovalStatus()) {
				case StatusType.APPROVAL_STATUS_1:
					tdsMoneyApprovalGoMapper.upApprovalStatus(StatusType.APPROVAL_STATUS_1,tdsMoApp.getId(),null);
					status = "1";
					break;
				case StatusType.APPROVAL_STATUS_2:
					// 驳回原因 记录
					tdsApprovalLogMapper.save(new TdsApprovalLog(tdsMoApp.getUserId(), "进账驳回", appRemarks, new Date(),
							tdsMoApp.getOrderNumber()));
					// 并操作驳回更新
					tdsMoneyApprovalGoMapper.upApprovalStatus(StatusType.APPROVAL_STATUS_2,tdsMoApp.getId(),null);

					status = "3";
					break;
				case StatusType.APPROVAL_STATUS_3:
					
					status = "2";
					
					if(isOrderNumber.equals(tdsMoApp.getOrderNumber())){
						return new BackResult<>(ResultCode.RESULT_FAILED, "重复订单提交到账次数+1");
					 }
					
					// 判断是否通过初次审核
					if (!StatusType.APPROVAL_STATUS_1.equals(isStatus)) {
						return new BackResult<>(ResultCode.RESULT_FAILED, "没有通过初次审核不可操作");
					}
					
					tdsMoneyApprovalGoMapper.upApprovalStatus(StatusType.APPROVAL_STATUS_1,tdsMoApp.getId(),new Date());

					// 判断下单成功是否有用户存在，不存在则新增一条
					Integer isUser = tdsUserCustomerMapper.queryIsUserId(tdsMoApp.getUserId());
					
					TdsUserCustomer userCust = new TdsUserCustomer();
					userCust.setUserId(tdsMoApp.getUserId());
					userCust.setSumCommission(tdsMoApp.getCommissonMoney());
					userCust.setSumMoney(tdsMoApp.getSumMoney());
					userCust.setLastMoneyTime(new Date());
					userCust.setOverplusCommission(tdsMoApp.getCommissonMoney());
					
					if (isUser == 1) {
						// 到账成功，客户列表更新累积消费充值金额，和佣金（提取和未处理不做计算）加上剩余佣金额
						userCust.setUpdateTime(new Date());
						tdsUserCustomerMapper.addMoneyAndCommission(userCust);
						
					} else if (isUser == 0) {
						// 新增一条记录统计
						userCust.setCreateTime(new Date());
						tdsUserCustomerMapper.save(userCust);
						
					} else {
						return new BackResult<>(ResultCode.RESULT_FAILED, "该用户存在重复异常数据");
					}
					
					
					//TODO 通过成功 保存数量 
					
					
					
					isOrderNumber=tdsMoApp.getOrderNumber();
					
					break;
				case StatusType.APPROVAL_STATUS_4:

					// 判断是否通过初次审核
					if (!StatusType.APPROVAL_STATUS_1.equals(isStatus)) {
						return new BackResult<>(ResultCode.RESULT_FAILED, "没有通过初次审核不可操作");
					}
					
					//更新开票
					tdsMoneyApprovalGoMapper.upBilling(tdsMoApp.getId(),StatusType.APPROVAL_BILLING_ON);
					
					result.setResultObj(1);
					
					return result;
				default:
					result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
					result.setResultMsg("传参错误!");
					return result;
				}
				// 更新流水号状态
				TdsSerualInfo tdsSerual = new TdsSerualInfo();
				tdsSerual.setUpdateTime(new Date());
				tdsSerual.setOrderNumber(tdsMoApp.getOrderNumber()); // -订单号
				// 流水状态 1处理中 2已处理 3被驳回 : serial_status
				tdsSerual.setSerialStatus(status);
				tdsSerualInfoMapper.upSerialByStatus(tdsSerual);

				// 更新佣金列表
				tdsCommiss.setUpdateTime(new Date());
				tdsCommiss.setCommStatus(status);// 已到账
				tdsCommiss.setOrderNumber(tdsMoApp.getOrderNumber());
				tdsCommissionMapper.upCommStatus(tdsCommiss);
				result.setResultObj(1);

			} else {
				result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
				result.setResultMsg("操作状态不能为空，传参错误!");
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("进账审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");

		}
		return result;
	}

	@Override
	public BackResult<PageDomain<TdsApprovalOutDomain>> pageApprovalByUpStatusOut(TdsApprovalOutQueryDomain domain) {
		BackResult<PageDomain<TdsApprovalOutDomain>> result = new BackResult<PageDomain<TdsApprovalOutDomain>>();
		PageDomain<TdsApprovalOutDomain> pageListDomain = null;
	//	List<TdsApprovalOutDomain> listDomain = new ArrayList<TdsApprovalOutDomain>();
		try {
			BeanHelper.beanHelperTrim(domain); // 去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			Integer count = tdsMoneyApprovalOutMapper.queryCount(domain);// 获取总数
			List<TdsApprovalOutDomain> list = tdsMoneyApprovalOutMapper.pageMoneyApprovalOut(domain);
			if (list.size() > 0 && list != null) {
//				// 定义对象用于转换
//				TdsApprovalOutDomain tdsDomain = null;
//				for (TdsApprovalOutDomain obj : list) {
//					tdsDomain = new TdsApprovalOutDomain();
//					BeanUtils.copyProperties(obj, tdsDomain);
//					listDomain.add(tdsDomain);
//				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsApprovalOutDomain>(domain.getCurrentPage(), domain.getNumPerPage(),
						count);
				pageListDomain.setTlist(list);
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

	@Transactional
	@Override
	public BackResult<Integer> updatePageApprovalByUpStatus(String userId, String tdsCarryId, String status,String remarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		if(StringUtils.isBlank(userId)){
			result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
			result.setResultMsg("用户ID不能为空，传参错误!");
			return result;
		}
		if(StringUtils.isBlank(tdsCarryId)){
			result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
			result.setResultMsg("出账记录ID不能为空，传参错误!");
			return result;
		}
		String carrStatus = null;
		Map<String,String> param = new HashMap<>();
		param.put("userId", userId);
		param.put("tdsCarryId", tdsCarryId);
		if("pass".equals(status)){  //审核通过
			carrStatus = "2";
			param.put("carrStatus", carrStatus);
			//修改提现状态以及调整佣金金额
			Integer carryCount = tdsMoneyApprovalOutMapper.upCarryStatus(param);
			if(carryCount!=2){
				return new BackResult<>(ResultCode.RESULT_FAILED, "操作失败,佣金余额不足");
			}
			//修改流水明细表的状态
			Integer serualCount = tdsMoneyApprovalOutMapper.upSerualStatus(param);
			if(serualCount!=1){
				return new BackResult<>(ResultCode.RESULT_FAILED, "操作失败,流水明细表状态修改失败");
			}
			
			result.setResultCode(ResultCode.RESULT_SUCCEED);
			result.setResultMsg("操作成功");
			result.setResultObj(1);
		}else if("rebut".equals(status)){ //审核驳回
			carrStatus = "3";
			param.put("carrStatus", carrStatus);
			param.put("remarks", remarks);
			//修改提现状态以及调整佣金金额
			Integer carryCount = tdsMoneyApprovalOutMapper.upCarryStatusRebut(param);
			if(carryCount!=1){
				return new BackResult<>(ResultCode.RESULT_FAILED, "操作失败,佣金余额不足");
			}
			//修改流水明细表的状态
			Integer serualCount = tdsMoneyApprovalOutMapper.upSerualStatus(param);
			if(serualCount!=1){
				return new BackResult<>(ResultCode.RESULT_FAILED, "操作失败,流水明细表状态修改失败");
			}
			
			result.setResultCode(ResultCode.RESULT_SUCCEED);
			result.setResultMsg("操作成功");
			result.setResultObj(1);
		}else{
			result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
			result.setResultMsg("状态传参错误!");
		}
		return result;
	}

}
