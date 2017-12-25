package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsAccountBankMapper;
import cn.entity.tds.TdsAccountBank;
import cn.service.tds.TdsAccountBankService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageAuto;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAccountBankDomain;

@Service
public class TdsAccountBankServiceImpl implements TdsAccountBankService {
	private final static Logger logger = LoggerFactory.getLogger(TdsAccountBankServiceImpl.class);

	@Autowired
	private TdsAccountBankMapper tdsAccountBankMapper;

	@Override
	public BackResult<PageDomain<TdsAccountBankDomain>> pageTdsAccountBank(String likeName,
			Integer currentPage, Integer numPerPage, Integer selected) {
		    BackResult<PageDomain<TdsAccountBankDomain>> result = new BackResult<PageDomain<TdsAccountBankDomain>>();
		    PageDomain<TdsAccountBankDomain> pageListDomain =null;
			List<TdsAccountBankDomain> listDomain = new ArrayList<TdsAccountBankDomain>();
		try {
			PageAuto auto=new PageAuto(currentPage,numPerPage);
			Integer count=tdsAccountBankMapper.queryCount(likeName,selected);//获取总数
			List<TdsAccountBank> list = tdsAccountBankMapper.pageTdsAccountBank(likeName,auto.getPageNumber(),auto.getNumPerPage(),selected);
			if (list.size() > 0 && list != null) {
				
			
				//定义对象用于转换
				TdsAccountBankDomain tdsDomain = null;
				for (TdsAccountBank obj : list) {
					tdsDomain = new TdsAccountBankDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					//增加至List<TdsAccountBankDomain>
					listDomain.add(tdsDomain);
				}
				//构造计算分页参数
				pageListDomain=new PageDomain<TdsAccountBankDomain>(currentPage, numPerPage, count);
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
	public BackResult<Integer> update(TdsAccountBankDomain domain,Integer loginUserId) {
		BackResult<Integer> result=new BackResult<Integer>();
		TdsAccountBank  bank=new TdsAccountBank();
		try {
			domain.setUpdater(loginUserId);//修改人
			BeanUtils.copyProperties(domain, bank);
		    tdsAccountBankMapper.update(bank);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("《入账银行管理》查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("编辑修改失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> isDisableById(Integer id) {
		 BackResult<Integer> result=new BackResult<Integer>();
		try {
			tdsAccountBankMapper.isDisableById(id);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("《入账银行管理》停用功能功能出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("停用失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> save(TdsAccountBankDomain domain,Integer loginUserId) {
		 BackResult<Integer> result=new BackResult<Integer>();
		 TdsAccountBank  bank=new TdsAccountBank();
			try {
				domain.setCreater(loginUserId);//新增人
				BeanUtils.copyProperties(domain, bank);
				tdsAccountBankMapper.save(bank);
				result.setResultObj(1);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("《入账银行管理》新增功能功能出现系统异常：" + e.getMessage());
				result.setResultCode(ResultCode.RESULT_FAILED);
				result.setResultMsg("新增失败");
			}
			return result;
	}
	
	
	

}
