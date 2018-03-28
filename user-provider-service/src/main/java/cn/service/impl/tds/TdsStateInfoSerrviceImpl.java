package cn.service.impl.tds;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsEnumMapper;
import cn.dao.tds.TdsProductMoneyMapper;
import cn.dao.tds.TdsStateInfoMapper;
import cn.entity.tds.TdsEnum;
import cn.entity.tds.TdsProductMoney;
import cn.entity.tds.TdsStateInfo;
import cn.service.tds.TdsStateInfoSerrvice;
import cn.utils.BeanHelper;
import cn.utils.DateUtils;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsEnumDomain;
import main.java.cn.domain.tds.TdsProductMoneyDomain;
import main.java.cn.domain.tds.TdsStateInfoDomain;

@Service
public class TdsStateInfoSerrviceImpl implements TdsStateInfoSerrvice {
	
	private final static Logger logger = LoggerFactory.getLogger(TdsStateInfoSerrviceImpl.class);

	@Autowired
	private TdsStateInfoMapper tdsStateInfoMapper;
	
	@Autowired
	private TdsProductMoneyMapper tdsProductMoneyMapper;
	
	@Autowired
	private TdsEnumMapper tdsEnumMapper;
	
	
	
	@Transactional
	@Override
	public BackResult<Integer> update(TdsStateInfoDomain domain) {
		BackResult<Integer> result = new BackResult<Integer>();
		domain.setUpdateTime(new Date());
		TdsStateInfo tds = new TdsStateInfo();
		try {
			BeanUtils.copyProperties(domain, tds);
			tdsStateInfoMapper.update(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}
    
	@Transactional
	@Override
	public BackResult<Integer> deleteById(Integer id) {
		BackResult<Integer> result = new BackResult<Integer>();
		try {
			tdsStateInfoMapper.deleteById(id);
			result.setResultObj(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("delete功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据删除失败");
		}
		return result;
	}
	
	
	@Override
	public BackResult<PageDomain<TdsStateInfoDomain>> pageTdsStateInfo(TdsStateInfoDomain domain) {
		
		BackResult<PageDomain<TdsStateInfoDomain>> result = new BackResult<PageDomain<TdsStateInfoDomain>>();
		
		PageDomain<TdsStateInfoDomain> listDomain = null;
		
		TdsStateInfo state=new TdsStateInfo();
		
		List<TdsStateInfoDomain> list = new ArrayList<TdsStateInfoDomain>();
		try {
			
			BeanUtils.copyProperties(domain, state);
			// yyyy-mm-dd 天数加1
			BeanHelper.beanHelperTrim(state);  //去掉空格
			
			if (null != state.getStatTime() && !"".equals(state.getStatTime())) {
				Date endTime = DateUtils.addDay(state.getStatTime(), 1);
				state.setEndTime(DateUtils.formatDate(endTime)); // 结束时间
			}
			
			Integer count = tdsStateInfoMapper.queryCount(state);
			if (count == 0) {
				return new BackResult<>(ResultCode.RESULT_DATA_EXCEPTIONS, "没有匹配数据");
			}
			
			Integer cur=state.getCurrentPage()<=0?1:state.getCurrentPage();
			
			state.setPageNumber((cur-1)*state.getNumPerPage());
			
			List<TdsStateInfo> pageList = tdsStateInfoMapper.pageTdsStateInfo(state);
			
			TdsStateInfoDomain obj = null;
			
			for (TdsStateInfo item : pageList) {
				obj = new TdsStateInfoDomain();
				BeanUtils.copyProperties(item, obj);
				list.add(obj);
			}
			
			listDomain = new PageDomain<TdsStateInfoDomain>(state.getCurrentPage(), state.getNumPerPage(),count);
			listDomain.setTlist(list);
			result.setResultObj(listDomain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("分页查询功能出现系统异常：" + e.getMessage());
			return new BackResult<PageDomain<TdsStateInfoDomain>>(ResultCode.RESULT_FAILED, "数据落地异常");
		}
		return result;
	}

	@Override
	@Transactional
	public BackResult<Integer> addState(Integer userId, TdsStateInfoDomain domain) {
		   BackResult<Integer> result=new BackResult<Integer>();
		   TdsStateInfo  tds=new TdsStateInfo();
		try {
			domain.setCreateTime(new Date());
			domain.setUpdateTime(new Date());
			BeanUtils.copyProperties(domain,tds);
			tdsStateInfoMapper.save(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("状态库新增功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Override
	public BackResult<TdsStateInfoDomain> loadById(Integer id) {
		BackResult<TdsStateInfoDomain> result = new BackResult<TdsStateInfoDomain>();
		try {
			TdsStateInfoDomain domain = new TdsStateInfoDomain();
			TdsStateInfo entity = tdsStateInfoMapper.loadById(id);
			BeanUtils.copyProperties(entity, domain);
			result.setResultObj(domain);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
		}
		return result;
	}

	@Override
	@Transactional
	public BackResult<Integer> addProductTable(TdsProductMoneyDomain domain) {
		   BackResult<Integer> result=new BackResult<Integer>();
		   TdsProductMoney  tds=new TdsProductMoney();
		   domain.setCreateTime(new Date());
		   domain.setUpdateTime(new Date());
		try {
			BeanUtils.copyProperties(domain,tds);
			tdsProductMoneyMapper.save(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("状态库新增功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}
	
	
	

	
	
	/**
	 * 获取enum表对象
	 */
	@Override
	public BackResult<List<TdsEnumDomain>> queryByTypeCode(String codeName) {
		
		BackResult<List<TdsEnumDomain>> result=new BackResult<List<TdsEnumDomain>>();
		List<TdsEnumDomain>  listDomain=new ArrayList<TdsEnumDomain>();
		try {
			List<TdsEnum> list=tdsEnumMapper.queryByTypeCode(codeName);
			if(list.size()>0 && list!=null){
				TdsEnumDomain tdsDomain=null;
	          for(TdsEnum obj:list){
	        	 tdsDomain=new TdsEnumDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          result.setResultObj(listDomain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("项目列表信息查询出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		
		return result;
	}

	@Override
	public BackResult<List<TdsProductMoneyDomain>> queryPnameByPro(Integer pnameId) {
		BackResult<List<TdsProductMoneyDomain>> result=new BackResult<List<TdsProductMoneyDomain>>();
		List<TdsProductMoneyDomain>  listDomain=new ArrayList<TdsProductMoneyDomain>();
		try {
			List<TdsProductMoney> list=tdsProductMoneyMapper.queryPnameByPro(pnameId);
			if(list.size()>0 && list!=null){
				TdsProductMoneyDomain tdsDomain=null;
	          for(TdsProductMoney obj:list){
	        	 tdsDomain=new TdsProductMoneyDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          result.setResultObj(listDomain);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("项目列表信息查询出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		
		return result;
	}

	@Override
	public BackResult<Integer> deleteByProId(Integer id) {
		 BackResult<Integer> result=new BackResult<Integer>();
		try {
			tdsProductMoneyMapper.deleteById(id);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("状态库新增功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}


	
	

}
