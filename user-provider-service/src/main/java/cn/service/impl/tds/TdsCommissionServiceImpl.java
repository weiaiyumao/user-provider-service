package cn.service.impl.tds;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsCommissionMapper;
import cn.entity.tds.TdsCommission;
import cn.service.tds.TdsCommissionService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;


@Service
public class TdsCommissionServiceImpl implements TdsCommissionService {
    
	private final static Logger logger = LoggerFactory.getLogger(TdsCommissionServiceImpl.class);
	
	@Autowired
	private TdsCommissionMapper tdsCommissionMapper;
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BackResult<Integer> addCommission(String status,Integer userId, String commissonMoney, String ordrr) {
		TdsCommission tdsCommission = new TdsCommission();
		tdsCommission.setCreateTime(new Date());
		tdsCommission.setUpdateTime(new Date());
		try {
			tdsCommission.setSerialMoney(commissonMoney); // 佣金
			tdsCommission.setSerialNumber(OrderNo.getSerial16());// 重新生成佣金流水号
			tdsCommission.setOrderNumber(ordrr); // 订单号不变
			tdsCommission.setUserId(userId);
			tdsCommission.setCommStatus(status);// 处理中
			tdsCommissionMapper.save(tdsCommission);
		} catch (Exception e) {
			logger.error("佣金save功能信息出现系统异常：" + e.getMessage());
			return BackResult.error("佣金保存失败");
		}
		return BackResult.ok(1);
	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BackResult<Integer> upCommStatus(String status, String order) {
		 TdsCommission tdsCommiss = new TdsCommission();
		try {
			tdsCommiss.setUpdateTime(new Date());
			tdsCommiss.setCommStatus(status);// 已到账
			tdsCommiss.setOrderNumber(order);
			Integer i=tdsCommissionMapper.upCommStatus(tdsCommiss);
		 } catch (Exception e) {
			 logger.error("佣金更改状态功能信息出现系统异常：" + e.getMessage());
			 return BackResult.error("佣金更改失败");
		 }
		return BackResult.ok(1);
	}
	
	

}
