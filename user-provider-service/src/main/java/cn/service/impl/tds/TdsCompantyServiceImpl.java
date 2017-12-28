package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsCompanyMapper;
import cn.entity.tds.TdsCompany;
import cn.service.tds.TdsCompanyService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.tds.TdsCompanyDomain;

/**
 * 公司服务接口实现
 * 
 * @author ChuangLan
 *
 */
@Service
public class TdsCompantyServiceImpl implements TdsCompanyService{
   
	private final static Logger logger = LoggerFactory.getLogger(TdsCompantyServiceImpl.class);
	
	@Autowired
	private TdsCompanyMapper tdsCompanyMapper;
	
	@Override
	public BackResult<List<TdsCompanyDomain>> selectCompanyAll() {
		BackResult<List<TdsCompanyDomain>> result=new BackResult<List<TdsCompanyDomain>>();
		List<TdsCompanyDomain>  listDomain=new ArrayList<TdsCompanyDomain>();
		try {
			List<TdsCompany> list=tdsCompanyMapper.selectCompanyAll();
			if(list.size()>0 && list!=null){
				TdsCompanyDomain tdsDomain=null;
	          for(TdsCompany obj:list){
	        	 tdsDomain=new TdsCompanyDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          result.setResultObj(listDomain);
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
	public BackResult<TdsCompanyDomain> loadComById(Integer id) {
		 BackResult<TdsCompanyDomain> result=new BackResult<TdsCompanyDomain>();
		 TdsCompanyDomain comDomain=new TdsCompanyDomain();
		 try {
			 TdsCompany obj=tdsCompanyMapper.loadById(id);
			 BeanUtils.copyProperties(obj,comDomain);
			 result.setResultObj(comDomain);
		} catch (BeansException e) {
			e.printStackTrace();
			logger.error("查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		 return result;
	}



	
}
