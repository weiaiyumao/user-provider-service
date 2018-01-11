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
import cn.dao.tds.TdsMoneyApprovalBackMapper;
import cn.dao.tds.TdsMoneyApprovalGoMapper;
import cn.dao.tds.TdsSerualInfoMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsMoneyApprovalBack;
import cn.entity.tds.TdsSerualInfo;
import cn.service.tds.TdsMoneyApprovalBackService;
import cn.utils.BeanHelper;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalBackDomain;

@Service
public class TdsMoneyApprovalBackServiceImpl extends BaseTransactService implements TdsMoneyApprovalBackService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalBackServiceImpl.class);

	// @Autowired
	// private TdsMoneyApprovalGoMapper tdsMoneyApprovalGoMapper;
	//
	// @Autowired
	// private TdsApprovalLogMapper tdsApprovalLogMapper;
	//
	// @Autowired
	// private TdsSerualInfoMapper tdsSerualInfoMapper;
	//
	// @Autowired
	// private TdsCommissionMapper tdsCommissionMapper;
	//
	// @Autowired
	// private TdsUserDiscountMapper tdsUserDiscountMapper;
	//
	// @Autowired
	// private TdsUserMapper tdsUserMapper;

	@Autowired
	private TdsMoneyApprovalBackMapper tdsMoneyApprovalBackMapper;

	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;

	@Autowired
	private TdsMoneyApprovalGoMapper tdsMoneyApprovalGoMapper;
	
	
	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;

	@Override
	public BackResult<PageDomain<TdsMoneyApprovalBackDomain>> pageApprovalBack(TdsMoneyApprovalBackDomain domain) {
		BackResult<PageDomain<TdsMoneyApprovalBackDomain>> result = new BackResult<PageDomain<TdsMoneyApprovalBackDomain>>();
		PageDomain<TdsMoneyApprovalBackDomain> pageListDomain = null;
		List<TdsMoneyApprovalBackDomain> listDomain = new ArrayList<TdsMoneyApprovalBackDomain>();
		try {
			BeanHelper.beanHelperTrim(domain); // 去掉空格
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			TdsMoneyApprovalBack tdsMoApp = new TdsMoneyApprovalBack();
			BeanUtils.copyProperties(domain, tdsMoApp);
			Integer count = tdsMoneyApprovalBackMapper.queryCount(tdsMoApp);// 获取总数
			List<TdsMoneyApprovalBack> list = tdsMoneyApprovalBackMapper.pageApprovalBack(tdsMoApp);
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsMoneyApprovalBackDomain tdsDomain = null;
				for (TdsMoneyApprovalBack obj : list) {
					tdsDomain = new TdsMoneyApprovalBackDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsMoneyApprovalBackDomain>(domain.getCurrentPage(),
						domain.getNumPerPage(), count);
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

	/**
	 * 出账账操作 0待审核 1已审核 2驳回
	 * 
	 * @param tdsMoApp
	 * @param appRemarks
	 *            原因
	 * @param rej
	 *            xx驳回
	 * @return
	 */
	@Transactional
	public BackResult<Integer> approvalByUpStatusOutOrBack(TdsMoneyApproval tdsMoApp, String appRemarks, String rej) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus status = this.begin();
		// 流水状态 1处理中 2已处理 3被驳回 : serial_status
		String serual = "1";

		try {
			if (null != tdsMoApp.getApprovalStatus() && !"".equals(tdsMoApp.getApprovalStatus())) {
				// 通过
				if (StatusType.APPROVAL_STATUS_1.equals(tdsMoApp.getApprovalStatus())) {
					// tdsMoneyApprovalGoMapper.update(tdsMoApp);
					// 审核通过 等待金额 到账时间 再次确认
					serual = "1";// 流水状态 1处理中
				}

				// 驳回
				if (StatusType.APPROVAL_STATUS_2.equals(tdsMoApp.getApprovalStatus())) {
					// 驳回原因 记录
					tdsApprovalLogMapper.save(new TdsApprovalLog(tdsMoApp.getUserId(), rej, appRemarks, new Date(),
							tdsMoApp.getOrderNumber()));
					// 并操作驳回更新
					// tdsMoneyApprovalGoMapper.update(tdsMoApp);
					// 更新流水明细驳回状态
					serual = "3";
				}
				// 更新流水号状态
				// upSerialStatus(tdsMoApp.getOrderNumber(),
				// tdsMoApp.getSerialNumber(), serual);

			}
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("出账审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	/**
	 * 退款验证是否符合退款要求，检验上一单是否已经付款 approval_status是否是3到账状态，
	 * 到账可退款，没到不给于退款，并且驳回，标志驳回原因 并记录流水明细，退款类型，状态改为 serial_type 3
	 * 
	 */

	/**
	 * 退款订单申请
	 */
	@Transactional
	@Override
	public BackResult<Integer> backApproval(TdsMoneyApprovalBackDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		// 退款订单号码
		String ordrr = OrderNo.getOrderNo16();
		// 退款流水
		String serial = OrderNo.getSerial16();
		TransactionStatus status = this.begin();
		TdsMoneyApprovalBack appBack = new TdsMoneyApprovalBack();
		try {
			
			//通过获取的下单订单号，得到之前下单信息
			TdsMoneyApproval goTdsApp=tdsMoneyApprovalGoMapper.queryByOrderByUser(domain.getGoOrderNumber(),domain.getUserId());
            if(null==goTdsApp){
            	return new BackResult<>(ResultCode.RESULT_FAILED,"没有这条订单记录");
            }
			
			// 获取下单号
			domain.setOrderNumber(ordrr);
			domain.setSerialNumber(serial);
			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			domain.setApprovalStatus(StatusType.APPROVAL_STATUS_0); // 待审核
			domain.setGoPname(goTdsApp.getPname());  //保存下单产品名称
			domain.setGoPnameNumber(goTdsApp.getNumber()); //保存下单产品数量
			domain.setGoSumMoney(goTdsApp.getSumMoney()); //保存下单设计总金额
			BeanUtils.copyProperties(domain, appBack);
			tdsMoneyApprovalBackMapper.save(appBack); //保存退款信息审核

			// 进入流水明细保存
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
			tdsSerual.setCreateTime(new Date());
			tdsSerual.setUpdateTime(new Date());
			tdsSerual.setOrderNumber(ordrr); // 保存退款单-订单号
			tdsSerual.setSerialNumber(serial); // 保存退款-流水号
			tdsSerual.setSerialStatus("1"); // 处理中
			// 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType("3");// 退款类型
			tdsSerual.setUserId(appBack.getUserId());
			tdsSerual.setCreater(appBack.getCreater());
			tdsSerual.setSerialMoney(appBack.getBackSumMoney());// 退款金额 涉及金额
			tdsSerualInfoMapper.save(tdsSerual);		
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("退款审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据退款失败");
		}
		return result;
	}

}
