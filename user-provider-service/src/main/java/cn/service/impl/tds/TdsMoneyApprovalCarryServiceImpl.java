package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import cn.dao.tds.TdsCarryMapper;
import cn.dao.tds.TdsUserBankApyMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.entity.tds.TdsCarry;
import cn.entity.tds.TdsUserBankApy;
import cn.entity.tds.TdsUserCustomer;
import cn.service.tds.TdsMoneyApprovalCarryService;
import cn.service.tds.TdsSerualService;
import cn.utils.DateUtils;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCarryDomain;

@Service
public class TdsMoneyApprovalCarryServiceImpl extends BaseTransactService implements TdsMoneyApprovalCarryService {

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalCarryServiceImpl.class);

	@Autowired
	private TdsCarryMapper tdsCarryMapper;

	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;

	@Autowired
	private TdsUserBankApyMapper tdsUserBankApyMapper;
	
	@Autowired
	private TdsSerualService tdsSerualService;

	@Override
	public BackResult<PageDomain<TdsCarryDomain>> pageTdsCarry(TdsCarryDomain domain) {
		BackResult<PageDomain<TdsCarryDomain>> result = new BackResult<PageDomain<TdsCarryDomain>>();
		PageDomain<TdsCarryDomain> pageListDomain = null;
		List<TdsCarryDomain> listDomain = new ArrayList<TdsCarryDomain>();
		TdsCarry tds = new TdsCarry();
		try {
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			BeanUtils.copyProperties(domain, tds);
			// 时间+1天
			if (null != tds.getStatTime() && !"".equals(tds.getStatTime())) {
				Date endTime = DateUtils.addDay(tds.getStatTime(), 1);
				tds.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			Integer count = tdsCarryMapper.queryCount(tds);// 获取总数
			List<TdsCarry> list = tdsCarryMapper.pageTdsCarry(tds);
			if (list.size() > 0 && list != null) {
				// 定义对象用于转换
				TdsCarryDomain tdsDomain = null;
				for (TdsCarry obj : list) {
					// 后四位为*
					// String startNo = obj.getBankNo().substring(0, 9);
					// String endNo = obj.getBankNo().substring(15);
					tdsDomain = new TdsCarryDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					// tdsDomain.setBankNo(startNo + " **** " + endNo);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsCarryDomain>(domain.getCurrentPage(), domain.getNumPerPage(), count);
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
	public BackResult<Map<String, Object>> getCarryByUserId(Integer userId) {
		BackResult<Map<String, Object>> result = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			result = new BackResult<Map<String, Object>>();
			TdsUserCustomer tdsUserCust = tdsUserCustomerMapper.loadByUserId(userId);
			map.put("sumComm", null == tdsUserCust.getSumCommission() ? 0 : tdsUserCust.getSumCommission()); // 佣金累计
			map.put("carryComm", null == tdsUserCust.getOverplusCommission() ? 0 : tdsUserCust.getOverplusCommission());// 可提佣金

			TdsUserBankApy tdsUserBankPay = tdsUserBankApyMapper.loadByUserId(userId);
			map.put("bankName", null == tdsUserBankPay ? "" : tdsUserBankPay.getBankName()); // 银行简称
			map.put("bankNo", null == tdsUserBankPay? "" : tdsUserBankPay.getBankNo());// 银行卡号
			result.setResultObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	
	
	/**
	 * type 1:对公打款 2：支付宝打款
	 */
	@Override
	public BackResult<Integer> getSubCarry(Integer userId, String carrMoney, String type) {
		BackResult<Integer> result = new BackResult<Integer>();
		
		TransactionStatus status=this.begin();
		try {
			// 提现订单号码
			String ordrr = OrderNo.getOrderNo16();
			// 提现流水
			String serial = OrderNo.getSerial16();

			TdsCarry obj = new TdsCarry();
			obj.setCreateTime(new Date());
			obj.setUpdateTime(new Date());
			obj.setCreater(userId);// 提现人
			obj.setUpdater(userId);// 提现人
			obj.setCarrStatus("1");// 处理中，待审核
			obj.setCarrySerial(serial);
			obj.setCarryOrder(ordrr);
			obj.setUserId(userId);
			obj.setCarrMoney(carrMoney);

			TdsUserBankApy tdsUserBankPay = tdsUserBankApyMapper.loadByUserId(userId);
			switch (type) {
			// 对公打款
			case "1":
				if (null != tdsUserBankPay && !"".equals(tdsUserBankPay.getBankNo())) {
					String startNo = tdsUserBankPay.getBankNo().substring(0, 9);
					String endNo = tdsUserBankPay.getBankNo().substring(15);
					obj.setCarryTypeName(startNo + " **** " + endNo); // 4位加****
					obj.setCarryType(tdsUserBankPay.getBankName());
					tdsCarryMapper.save(obj);
				} else {
					return new BackResult<>(ResultCode.RESULT_FAILED, "请绑定银行卡");
				}
				break;
			// 支付打款
			case "2":
				if (null != tdsUserBankPay && !"".equals(tdsUserBankPay.getAlipayName())) {
					obj.setCarryType("支付宝");
					obj.setCarryTypeName(tdsUserBankPay.getAlipayName());
					tdsCarryMapper.save(obj);
				} else {
					return new BackResult<>(ResultCode.RESULT_FAILED, "请绑定支付宝");
				}
				break;
			default:
				return new BackResult<>(ResultCode.RESULT_FAILED, "参数输入异常");
			}

			//保存流水明细
			result=tdsSerualService.addSerual("1", "2", userId, carrMoney, ordrr);
            if(result.getResultCode().equals(ResultCode.RESULT_FAILED)){
            	return new BackResult<>(result.getResultCode(), result.getResultMsg());
            }
            
            
            
			result.setResultObj(1);
			
            this.commit(status);
            
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("提现列表功能出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("提现列表功能失败");
		} 
		return result;
	}

}
