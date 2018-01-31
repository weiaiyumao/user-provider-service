package cn.service.impl.tds;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsSerualInfoMapper;
import cn.entity.tds.TdsSerualInfo;
import cn.service.tds.TdsSerualService;
import cn.utils.OrderNo;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;


@Service
public class TdsSerualServiceImpl implements TdsSerualService {
	
	
	private final static Logger logger = LoggerFactory.getLogger(TdsSerualServiceImpl.class);
	
	
	@Autowired
	private TdsSerualInfoMapper tdsSerualInfoMapper;
	
	@Override
	public BackResult<Integer> addSerual(String status, String type, Integer userId, String money,String ordrr){
		 BackResult<Integer> result=new BackResult<Integer>();
		TdsSerualInfo tdsSerual = new TdsSerualInfo();
		tdsSerual.setCreateTime(new Date());
		tdsSerual.setUpdateTime(new Date());
		tdsSerual.setUserId(userId);
		tdsSerual.setCreater(userId);
		try {
			tdsSerual.setSerialNumber(OrderNo.getSerial16()); // 进账流水号
			tdsSerual.setOrderNumber(ordrr); // 保存下单-订单号
			tdsSerual.setSerialStatus(status); // 处理中
			// 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
			tdsSerual.setSerialType(type);// 下单进账类型
			tdsSerual.setSerialMoney(money);// 金额
			tdsSerualInfoMapper.save(tdsSerual);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("流水明细save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据save失败");
		}
		return result;
	}

}
