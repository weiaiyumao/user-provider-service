package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
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
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsAccountBankDomain;

@Service
public class TdsAccountBankServiceImpl implements TdsAccountBankService {
	private final static Logger logger = LoggerFactory.getLogger(TdsAccountBankServiceImpl.class);

	@Autowired
	private TdsAccountBankMapper tdsAccountBankMapper;

	@Override
	public BackResult<PageDomain<TdsAccountBankDomain>> pageTdsAccountBank(TdsAccountBankDomain domain) {
		    BackResult<PageDomain<TdsAccountBankDomain>> result = new BackResult<PageDomain<TdsAccountBankDomain>>();
		    PageDomain<TdsAccountBankDomain> pageListDomain =null;
			List<TdsAccountBankDomain> listDomain = new ArrayList<TdsAccountBankDomain>();
			TdsAccountBank tdsAccBank=new TdsAccountBank();
		try {
			Integer cur = domain.getCurrentPage() <= 0 ? 1 : domain.getCurrentPage();
			domain.setPageNumber((cur - 1) * domain.getNumPerPage());
			BeanUtils.copyProperties(domain, tdsAccBank);
			Integer count=tdsAccountBankMapper.queryCount(tdsAccBank);//获取总数
			List<TdsAccountBank> list = tdsAccountBankMapper.pageTdsAccountBank(tdsAccBank);
			if (list.size() > 0 && list != null) {
				//定义对象用于转换
				TdsAccountBankDomain tdsDomain = null;
				for (TdsAccountBank obj : list) {
					tdsDomain = new TdsAccountBankDomain();
					BeanUtils.copyProperties(obj, tdsDomain);
					listDomain.add(tdsDomain);
				}
				//构造计算分页参数
				pageListDomain=new PageDomain<TdsAccountBankDomain>(domain.getCurrentPage(),domain.getNumPerPage(), count);
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
	public BackResult<Integer> update(TdsAccountBankDomain domain) {
		BackResult<Integer> result=new BackResult<Integer>();
		TdsAccountBank  bank=new TdsAccountBank();
		try {
			domain.setUpdateTime(new Date());
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
			tdsAccountBankMapper.deleteById(id);
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
	public BackResult<Integer> save(TdsAccountBankDomain domain) {
		 BackResult<Integer> result=new BackResult<Integer>();
		 TdsAccountBank  bank=new TdsAccountBank();
			try {	
				domain.setCreateTime(new Date());
				domain.setUpdateTime(new Date());
				BeanUtils.copyProperties(domain, bank);
				tdsAccountBankMapper.save(bank);
				result.setResultObj(1);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("《入账银行管理》新增功能出现系统异常：" + e.getMessage());
				result.setResultCode(ResultCode.RESULT_FAILED);
				result.setResultMsg("新增失败");
			}
			return result;
	}
   
	/**
	 * 编辑银行入账管理
	 * id
	 */
	@Override
	public BackResult<TdsAccountBankDomain> loadById(Integer id) {
		 BackResult<TdsAccountBankDomain> result=new BackResult<TdsAccountBankDomain>();
		try {
			TdsAccountBankDomain tdsDomain=new TdsAccountBankDomain();
			TdsAccountBank tdsAccountBank=tdsAccountBankMapper.loadById(id);
			BeanUtils.copyProperties(tdsAccountBank, tdsDomain);
			result.setResultObj(tdsDomain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("入账银行管理停用功能出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("获取对象失败");
		}
		return result;
	}
	
	
	

}
