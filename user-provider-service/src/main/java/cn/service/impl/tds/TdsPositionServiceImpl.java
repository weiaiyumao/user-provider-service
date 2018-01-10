package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsPositionMapper;
import cn.entity.tds.TdsPosition;
import cn.service.tds.TdsPositionService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsPositionDomain;

/**
 * 客户审核服务接口实现
 * 
 * @author ChuangLan
 *
 */
@Service
public class TdsPositionServiceImpl extends BaseTransactService implements TdsPositionService {

	private final static Logger logger = LoggerFactory.getLogger(TdsPositionServiceImpl.class);

	
	@Autowired
	private TdsPositionMapper  tdsPositionMapper;
	
	@Override
	public BackResult<List<TdsPositionDomain>> selectByDeparId(Integer departmentId) {
		BackResult<List<TdsPositionDomain>> result=new BackResult<List<TdsPositionDomain>>();
		List<TdsPositionDomain>  listDomain=new ArrayList<TdsPositionDomain>();
		try {
			List<TdsPosition> list=tdsPositionMapper.selectByDeparId(departmentId);
			if(list.size()>0 && list!=null){
				TdsPositionDomain tdsDomain=null;
	          for(TdsPosition obj:list){
	        	 tdsDomain=new TdsPositionDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          result.setResultObj(listDomain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
			
		}
		return result;
	}

	

}
