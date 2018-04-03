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
import cn.dao.tds.TdsUserCustomerMapper;
import cn.entity.tds.TdsApprovalLog;
import cn.entity.tds.TdsCommission;
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsMoneyApprovalBack;
import cn.entity.tds.TdsSerualInfo;
import cn.entity.tds.TdsUserCustomer;
import cn.service.tds.TdsCommissionService;
import cn.service.tds.TdsCreUserAccountService;
import cn.service.tds.TdsMoneyApprovalBackService;
import cn.service.tds.TdsSerualService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalBackDomain;
import main.java.cn.enums.TdsEnum.ACCOUNT;
import main.java.cn.enums.TdsEnum.COMMISSIONSTATUS;
import main.java.cn.enums.TdsEnum.SERUALTYPE;
import main.java.cn.enums.TdsEnum.STATUS;

@SuppressWarnings("all")
@Service
public class TdsMoneyApprovalBackServiceImpl extends BaseTransactService implements TdsMoneyApprovalBackService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalBackServiceImpl.class);

	@Autowired
	private TdsMoneyApprovalBackMapper tdsMoneyApprovalBackMapper;

	@Autowired
	private TdsApprovalLogMapper tdsApprovalLogMapper;

	@Autowired
	private TdsMoneyApprovalGoMapper tdsMoneyApprovalGoMapper;

	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;

	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;

	@Autowired
	private TdsSerualService tdsSerualService;
	
	@Autowired
	private TdsCommissionService tdsCommissionService;
	
	
	@Autowired
	private TdsCreUserAccountService tdsCreUserAccountService;

	@Override
	public BackResult<PageDomain<TdsMoneyApprovalBackDomain>> pageApprovalBack(TdsMoneyApprovalBackDomain domain) {
		BackResult<PageDomain<TdsMoneyApprovalBackDomain>> result = new BackResult<PageDomain<TdsMoneyApprovalBackDomain>>();
		PageDomain<TdsMoneyApprovalBackDomain> pageListDomain = null;
		List<TdsMoneyApprovalBackDomain> listDomain = new ArrayList<TdsMoneyApprovalBackDomain>();
		try {
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
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			return BackResult.error("数据集合查询失败");
		}
		return result;
	}

	/**
	 * 退款订单申请
	 */
	@Transactional
	@Override
	public BackResult<Integer> backOrderMoney(TdsMoneyApprovalBackDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		TdsMoneyApprovalBack appBack = new TdsMoneyApprovalBack();
		TransactionStatus status = this.begin();
		// 退款订单号码
		String ordrr = OrderNo.getOrderNo16();
		// 退款流水
		String serial = OrderNo.getSerial16();
		// 获取下单号
		try {
			//TODO 每个用户只能退一次，审核通过则可继续退款
		//	tdsMoneyApprovalBackMapper.selectAll(entity);

			TdsUserCustomer tdsUserCustomer = tdsUserCustomerMapper.loadByUserId(domain.getUserId());
			// 剩余佣金
			domain.setOrderNumber(ordrr);
			domain.setSerialNumber(serial);
			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			domain.setSerualMoney(tdsUserCustomer.getOverplusCommission());
			domain.setApprovalStatus(StatusType.APPROVAL_STATUS_0); // 待审核

			// 根据产品和用户id获取该用户最近下订单审核通过并且已到账记录
			List<TdsMoneyApproval> list = tdsMoneyApprovalGoMapper.queryByOrderByUser(domain.getUserId(),domain.getPnameId());

			if (list.size() == 0 || null == list) {
				throw new Exception("没有查到对应的产品下单记录,不可退款"); 
			}

			Double backMoney = 0.0;// 退款下单涉及佣金
			Integer backNum = domain.getBackNumber(); // 退款数量;
			for (TdsMoneyApproval moneyApp : list) {
				if (backNum == moneyApp.getNumber()) {
					backMoney += Double.valueOf(moneyApp.getCommissonMoney());
					break;
				}
				// 退款数量大于下单成功的数量
				if (backNum > moneyApp.getNumber()) {
					backMoney += Double.valueOf(moneyApp.getCommissonMoney()); // 每笔下单应得佣金累加
					backNum -= moneyApp.getNumber();// 减掉当前订单数量累减
					continue;
				}

				// 退款数量小于下单成功的数量
				if (backNum < moneyApp.getNumber()) {
					Double numMultiple = (double) (moneyApp.getNumber() / backNum);
					backMoney += Double.valueOf(moneyApp.getCommissonMoney()) / numMultiple; // 应得当前佣金
					break;
				}

			}

			domain.setBackNumberCommission(backMoney.toString());
			BeanUtils.copyProperties(domain, appBack);
			tdsMoneyApprovalBackMapper.save(appBack); // 保存退款信息审核
             
			//保存流水
			result = tdsSerualService.addSerual(STATUS.HANDLE.getCode(), SERUALTYPE.BACK.getCode(),
					appBack.getUserId(), appBack.getBackMoney(), ordrr);

			if (!result.getResultCode().equals(ResultCode.RESULT_SUCCEED)) {
				throw new Exception(result.getResultMsg()); 
			}

			// 佣金 1处理中 2已到账 3被驳回 4.已提现 5.已扣除
			result = tdsCommissionService.addCommission(COMMISSIONSTATUS.HANDLE.getCode(),domain.getUserId(),domain.getBackNumberCommission(), ordrr);

			if (!result.getResultCode().equals(ResultCode.RESULT_SUCCEED)) {
				throw new Exception(result.getResultMsg()); 
			}

			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			this.rollback(status);
			logger.error("退款审核功能操作功能信息出现系统异常：" + e.getMessage());
			return BackResult.error("数据退款失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> approvalByUpStatusBack(TdsMoneyApprovalBackDomain domain, String appRemarks) {
		
		TransactionStatus statusTran = this.begin();
		
		domain.setUpdateTime(new Date());
		// 流水状态 1处理中 2已处理 3被驳回 :
		String serialStatus = "1";
				
		//佣金状态 1处理中 2已到账 3被驳回 4.已提现 5.已退款
		String commisStatus="1";

		TdsMoneyApprovalBack appBack = new TdsMoneyApprovalBack();
		BeanUtils.copyProperties(domain, appBack);
		try {
			if (null != appBack.getApprovalStatus() && !"".equals(appBack.getApprovalStatus())) {

				// 通过
				if (StatusType.APPROVAL_STATUS_1.equals(appBack.getApprovalStatus())) {

					tdsMoneyApprovalBackMapper.upBackStatus(appBack.getId(), StatusType.APPROVAL_STATUS_1);
                    
					
					if((Double.valueOf(domain.getSerualMoney()))<(Double.valueOf(domain.getBackNumberCommission()))){
						throw new Exception("扣除失败,剩余佣金不能小于扣除佣金"); 
					}
					// 更新退款佣金余额
					Integer isSub = tdsUserCustomerMapper.subMoneyAndCommission(appBack.getUserId(),Double.valueOf(domain.getBackNumberCommission()));

					if (isSub < 1) {
						throw new Exception("佣金扣除失败!"); 
					}

					//退款审核通过扣除剩余数量
					tdsCreUserAccountService.addOrSubCreUserAccount(domain.getCreUserId(),domain.getPnameId(),domain.getBackNumber(),ACCOUNT.SUB.getType());
					
					serialStatus=STATUS.PROCESSED.getCode();
					
					commisStatus=COMMISSIONSTATUS.BACK.getCode();  //扣除

				} else if (StatusType.APPROVAL_STATUS_2.equals(appBack.getApprovalStatus())) {
					// 驳回原因 记录
					tdsApprovalLogMapper.save(new TdsApprovalLog(appBack.getUserId(), "退款驳回", appRemarks, new Date(),
							appBack.getOrderNumber()));

					// 并操作驳回更新
					tdsMoneyApprovalBackMapper.upBackStatus(appBack.getId(), StatusType.APPROVAL_STATUS_2);
					
					// 更新流水明细驳回状态
					serialStatus=STATUS.REJECT.getCode();
					
					commisStatus=COMMISSIONSTATUS.REJECT.getCode();

				} else {
					return BackResult.error(ResultCode.RESULT_PARAM_EXCEPTIONS, "传参错误");
				}

				// 更新流水号状态
				tdsSerualService.upSerialByStatus(appBack.getOrderNumber(),serialStatus);
				
				// 更新佣金列表
				tdsCommissionService.upCommStatus(commisStatus,appBack.getOrderNumber());

				this.commit(statusTran);
			}

		} catch (Exception e) {
			this.rollback(statusTran);
			logger.error("退款功能操作出现系统异常：" + e.getMessage());
			return BackResult.error("退款审核功能操作失败");
		}
		return BackResult.ok(1);
	}

}
