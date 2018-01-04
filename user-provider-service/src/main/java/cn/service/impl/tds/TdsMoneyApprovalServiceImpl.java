package cn.service.impl.tds;

import java.util.Date;

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
import cn.entity.tds.TdsRole;
import cn.entity.tds.TdsSerualInfo;
import cn.service.tds.TdsMoneyApprovalService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsMoneyApprovalDomain;
import main.java.cn.domain.tds.TdsRoleDomain;

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
	 * 1进账审核 2出账审核 3退款审核
	 */
	@Override
	public BackResult<Integer> billApproval(Integer approvalType) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {

			switch (approvalType) {

			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("审核功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据审核失败");

		}
		return result;
	}

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
		
		Integer yonjing=0; //佣金
		
		TransactionStatus status = this.begin();
		try {
			domain.setApprovalType("1"); // 进入进账审核
			domain.setApprovalStatus("0"); // 待审核
			domain.setBilling("0"); // 未开票
			domain.setOrderNumber(ordrr);
			domain.setSerialNumber(serial);
			domain.setCreateTiem(new Date()); // 下单 订单时间
			BeanUtils.copyProperties(domain, tds);
			// 保存进账审核
			tdsMoneyApprovalMapper.save(tds);

			// 进入流水明细保存
			TdsSerualInfo tdsSerual = new TdsSerualInfo();
			tdsSerual.setCreateTime(new Date());
			tdsSerual.setOrderNumber(ordrr);
			tdsSerual.setSerialNumber(serial);
			tdsSerual.setSerialStatus("1"); // 处理中
			tdsSerual.setSerialType("5");// 进账
			tdsSerual.setSerialMoney("500");// TODO//流水金额
			tdsSerual.setBeforeMoney("600");//之前金额
			tdsSerualInfoMapper.save(tdsSerual);
			
			// 佣金列表保存
			TdsCommission tdsComm = new TdsCommission();
			tdsComm.setCreateTime(new Date());
			tdsComm.setOrderNumber(ordrr);
			tdsComm.setSerialNumber(serial);
			tdsComm.setCommStatus("1"); // 处理中
			tdsComm.setSerialMoney("500");//到账金额
			tdsComm.setBeforeMoney("600");//之前金额
			tdsCommissionMapper.save(tdsComm);
			result.setResultObj(1);
			this.commit(status);
		} catch (Exception e) {
			e.printStackTrace();
			this.rollback(status);
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Transactional
	@Override
	public BackResult<Integer> approvalByUpStatus(TdsMoneyApprovalDomain domain,String appRemarks) {
		BackResult<Integer> result=new BackResult<Integer>();
		TransactionStatus status=this.begin();
		domain.setUpdateTime(new Date());
		TdsMoneyApproval  tds=new TdsMoneyApproval();
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsMoneyApprovalMapper.approvalByUpStatus(tds);
			//审核(0待审核 1已审核 3驳回 4到账 5线下开票 6 充账 ) 
			if(null!=tds.getApprovalStatus() && "3".equals(tds.getApprovalStatus())){
			   //进行驳回操作
				switch(tds.getApprovalType()){
				case "1":
					//进账
					tdsApprovalLogMapper.save(new TdsApprovalLog(domain.getUserId(),"进账驳回",appRemarks,new Date(),domain.getOrderNumber()));
					break;
				case "2":
					//出账
					tdsApprovalLogMapper.save(new TdsApprovalLog(domain.getUserId(),"出账驳回",appRemarks,new Date(),domain.getOrderNumber()));
					break;
				case "3":
					//退款
					tdsApprovalLogMapper.save(new TdsApprovalLog(domain.getUserId(),"退款驳回",appRemarks,new Date(),domain.getOrderNumber()));
					break;
				}
			}
			result.setResultObj(1);
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

}
