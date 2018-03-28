package cn.service.impl.tds;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsSerualInfoMapper;
import cn.entity.tds.TdsSerualInfo;
import cn.service.tds.TdsSerualService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;

/**
 * 流水明细
 * @author ChuangLan
 *
 */


@Service
public class TdsSerualServiceImpl implements TdsSerualService {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TdsSerualServiceImpl.class);
	
	
	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BackResult<Integer> addSerual(String status, String type, Integer userId, String money,String ordrr){
		TdsSerualInfo tdsSerual = new TdsSerualInfo();
		tdsSerual.setCreateTime(new Date());
		tdsSerual.setUpdateTime(new Date());
		try {
			tdsSerual.setSerialNumber(OrderNo.getSerial16()); // 进账流水号
			tdsSerual.setOrderNumber(ordrr); // 保存下单-订单号
			tdsSerual.setSerialStatus(status);
			// 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType(type);// 下单进账类型
			tdsSerual.setSerialMoney(money);// 金额
			tdsSerualInfoMapper.save(tdsSerual);
		} catch (Exception e) {
			logger.error("流水明细保存功能信息出现系统异常：" + e.getMessage());
			return BackResult.error("流水保存失败");
		}
		return BackResult.ok();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public BackResult<Integer> upSerialByStatus(String order,String status) {
		
		     TdsSerualInfo tdsSerual = new TdsSerualInfo();
		     
			try {
				tdsSerual.setUpdateTime(new Date());
				tdsSerual.setOrderNumber(order); // -订单号
				// 流水状态 1处理中 2已处理 3被驳回 :
				tdsSerual.setSerialStatus(status);
			    Integer is=tdsSerualInfoMapper.upSerialByStatus(tdsSerual);
			    if(is<1)throw new Exception(); 
			} catch (Exception e) {
				logger.error("流水更新状态功能信息出现系统异常;");
				return BackResult.error("流水更新失败");
			}
			return BackResult.ok();
	}
	
}
