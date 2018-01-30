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

import cn.dao.tds.TdsCarryMapper;
import cn.dao.tds.TdsUserCustomerMapper;
import cn.entity.tds.TdsCarry;
import cn.entity.tds.TdsUserCustomer;
import cn.service.tds.TdsMoneyApprovalCarryService;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsCarryDomain;

@Service
public class TdsMoneyApprovalCarryServiceImpl extends BaseTransactService implements TdsMoneyApprovalCarryService{

	private final static Logger logger = LoggerFactory.getLogger(TdsMoneyApprovalCarryServiceImpl.class);

	
	@Autowired
	private TdsCarryMapper tdsCarryMapper;
	
	
	@Autowired
	private TdsUserCustomerMapper tdsUserCustomerMapper;
	


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
			//时间+1天
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
					tdsDomain = new TdsCarryDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				// 构造计算分页参数
				pageListDomain = new PageDomain<TdsCarryDomain>(domain.getCurrentPage(),
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



	
	@Override
	public BackResult<Map<String, Object>> getCarryByUserId(Integer userId) {
		BackResult<Map<String, Object>> result=null;
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			result = new BackResult<Map<String, Object>>();
			TdsUserCustomer tdsUserCust=tdsUserCustomerMapper.loadByUserId(userId);
			if(null!=tdsUserCust){
				map.put("sumComm", null==tdsUserCust.getSumCommission()?0:tdsUserCust.getSumCommission());  //佣金累计
				map.put("carryComm",null==tdsUserCust.getOverplusCommission()?0:tdsUserCust.getOverplusCommission());//可提佣金
			}
			
			result.setResultObj(map);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}
	
	
	

}
