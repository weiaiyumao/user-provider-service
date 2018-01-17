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
import cn.entity.tds.TdsMoneyApproval;
import cn.entity.tds.TdsMoneyApprovalBack;
import cn.entity.tds.TdsSerualInfo;
import cn.entity.tds.TdsUserCustomer;
import cn.service.tds.TdsMoneyApprovalBackService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.common.StatusType;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsMoneyApprovalBackDomain;

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
	
//	@Autowired
//	private TdsCommissionMapper tdsCommissionMapper;
	
	
	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;
    
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
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
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
		//获取下单号
		try {
			

			TdsUserCustomer tdsUserCustomer=tdsUserCustomerMapper.loadByUserId(domain.getUserId());
			//剩余佣金
			domain.setOrderNumber(ordrr);
			domain.setSerialNumber(serial);
			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			domain.setSerualMoney(tdsUserCustomer.getOverplusCommission());
			domain.setApprovalStatus(StatusType.APPROVAL_STATUS_0); // 待审核
		
			
				
			//根据产品和用户id获取该用户最近下订单审核通过并且已到账记录
			List<TdsMoneyApproval> list=tdsMoneyApprovalGoMapper.queryByOrderByUser(domain.getUserId(),domain.getPnameId());
			
			if(list.size()==0 || null==list){
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "没有查到对应的下单记录");
			}
			Double backMoney=0.0;//退款下单涉及佣金
			Integer backNum=domain.getBackNumber(); //退款数量;
			for(TdsMoneyApproval moneyApp:list){
				 if(backNum==moneyApp.getNumber()){
					 backMoney+=Double.valueOf(moneyApp.getCommissonMoney()); 
			    	 break;
			     }
				// 退款数量大于下单成功的数量
			     if(backNum>moneyApp.getNumber()){
			    	 backMoney+=Double.valueOf(moneyApp.getCommissonMoney());  //每笔下单应得佣金累加  
			    	 backNum-=moneyApp.getNumber();//减掉当前订单数量累减
			    	 continue;
			     }
			   
			     // 退款数量小于下单成功的数量
			     if(backNum<moneyApp.getNumber()){
			    	 Double numMultiple=(double)(moneyApp.getNumber()/backNum);  
			    	 backMoney+=Double.valueOf(moneyApp.getCommissonMoney())/numMultiple; //应得当前佣金
			    	 break;
			     }
			     
			}
			
			domain.setBackNumberCommission(backMoney.toString());
			BeanUtils.copyProperties(domain, appBack);
			tdsMoneyApprovalBackMapper.save(appBack); //保存退款信息审核
			

			// 进入流水明细保存
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
			tdsSerual.setCreateTime(new Date());
			tdsSerual.setUpdateTime(new Date());
			tdsSerual.setOrderNumber(ordrr); // 保存退款单-订单号
			tdsSerual.setSerialNumber(OrderNo.getSerial16()); // 保存退款-流水号
			tdsSerual.setSerialStatus("1"); // 处理中
			// 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType("3");// 退款类型
			tdsSerual.setUserId(appBack.getUserId());
			tdsSerual.setCreater(appBack.getCreater());
			tdsSerual.setSerialMoney(appBack.getBackMoney());// 退款金额 涉及金额
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

	
	
	@Override
	public BackResult<Integer> approvalByUpStatusBack(TdsMoneyApprovalBackDomain domain, String appRemarks) {
		BackResult<Integer> result = new BackResult<Integer>();
		TransactionStatus statusTran = this.begin();
		domain.setUpdateTime(new Date());
		// 流水状态 1处理中（待审核） 2已处理(已审核) 3被驳回 : serial_status
		String status = "1";
		TdsMoneyApprovalBack appBack = new TdsMoneyApprovalBack();
		BeanUtils.copyProperties(domain, appBack);
		try {
			if (null != appBack.getApprovalStatus() && !"".equals(appBack.getApprovalStatus())) {
				// 通过
				if (StatusType.APPROVAL_STATUS_1.equals(appBack.getApprovalStatus())) {

					tdsMoneyApprovalBackMapper.upBackStatus(appBack.getId(), StatusType.APPROVAL_STATUS_1);
					
					//通过扣除佣金数量
					System.out.println("=====通过则扣除佣金和数量====");  //TODO
					Double commiss=Double.valueOf(domain.getSerualMoney())-Double.valueOf(domain.getBackNumberCommission());
					
					// 更新退款佣金余额
				   Integer isSub=tdsUserCustomerMapper.subMoneyAndCommission(appBack.getUserId(), commiss.toString());
				   if(isSub<1){
					   return new BackResult<>(ResultCode.RESULT_FAILED, "佣金扣除失败！");
				   }
					//TODO 退款审核通过扣除剩余数量
				   
					
					status = "2";
				}else if(StatusType.APPROVAL_STATUS_2.equals(appBack.getApprovalStatus())) {
					// 驳回原因 记录
					tdsApprovalLogMapper.save(new TdsApprovalLog(appBack.getUserId(), "退款驳回", appRemarks, new Date(),
							appBack.getOrderNumber()));
					// 并操作驳回更新
					tdsMoneyApprovalBackMapper.upBackStatus(appBack.getId(), StatusType.APPROVAL_STATUS_2);
					// 更新流水明细驳回状态
					 status = "3";
				}else{
					result.setResultCode(ResultCode.RESULT_PARAM_EXCEPTIONS);
					result.setResultMsg("传参错误!");
					return result;
				}
				
				// 更新流水号状态
				TdsSerualInfo tdsSerual = new TdsSerualInfo();
				tdsSerual.setUpdateTime(new Date());
				tdsSerual.setOrderNumber(appBack.getOrderNumber()); // -订单号
				tdsSerual.setSerialNumber(appBack.getSerialNumber()); // -流水号
				// 流水状态 1处理中 2已处理 3被驳回 : serial_status
				tdsSerual.setSerialStatus(status);
				tdsSerualInfoMapper.upSerialByStatus(tdsSerual);
                this.commit(statusTran);
                result.setResultObj(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(statusTran);
			logger.error("审核功能操作功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

}
