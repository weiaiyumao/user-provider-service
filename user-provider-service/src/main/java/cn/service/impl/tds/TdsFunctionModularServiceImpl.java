package cn.service.impl.tds;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dao.tds.TdsModularMapper;
import cn.entity.tds.TdsModular;
import cn.service.tds.TdsModularService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ResultCode;
import main.java.cn.domain.page.BasePageParam;
import main.java.cn.domain.page.PageDomain;
import main.java.cn.domain.tds.TdsModularDomain;


@Service
public class TdsFunctionModularServiceImpl implements  TdsModularService {
     
	private final static Logger logger = LoggerFactory.getLogger(TdsFunctionModularServiceImpl.class);
	
	
	@Autowired
	private TdsModularMapper tdsModularMapper;
	
	@Override
	public BackResult<TdsModular> loadById(Integer id) {
		BackResult<TdsModular> result=new BackResult<TdsModular>();
		try {
			TdsModular entity=tdsModularMapper.loadById(id);
			result.setResultObj(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("功能ID：" + id + "查询功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据查询失败");
			
		}
		return result;
	}

	@Override
	public BackResult<Integer> saveTdsModular(TdsModularDomain domain) {
		  BackResult<Integer> result=new BackResult<Integer>();
		   TdsModular  tds=new TdsModular();
		try {
			
			TdsModular tdsModula=tdsModularMapper.loadById(domain.getId());
			tds.setParentId(domain.getId());
			//如果选择的是第一级模块名则为父级模块
			if(null!=tdsModula && "第一级".equals(tdsModula.getName())){
				tds.setParentId(0);  //标记为父类
			}
			tds.setName(domain.getName());
			tds.setCreateTime(new Date());
			tds.setUpdateTime(new Date());
			
			//如果不选，则默认为父级模块
			if(null==domain.getId() || "".equals(domain.getId())){
				tds.setParentId(0);  //标记为父类
			}
			
			tdsModularMapper.save(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("save功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据保存失败");
		}
		return result;
	}

	@Override
	public BackResult<Integer> deleteById(Integer id) {
		   BackResult<Integer> result=new BackResult<Integer>();
		try {
			tdsModularMapper.deleteById(id);
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
	public BackResult<Integer> updateTdsModular(String name,Integer selectedId,Integer newId) {
		BackResult<Integer> result=new BackResult<Integer>();
		TdsModular  tds=new TdsModular();
		try {
			tds.setId(selectedId);
			tds.setName(name);
			tds.setParentId(newId);
			//如果不选，则默认为父级模块
			if(null !=newId && newId==1){  //index
				tds.setParentId(0);  //标记为父类
			}
			tdsModularMapper.update(tds);
			result.setResultObj(1);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("update功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据修改失败");
		}
		return result;
	}

	@Override
	public BackResult<List<TdsModularDomain>> selectAll(TdsModularDomain domain) {
		BackResult<List<TdsModularDomain>> result=new BackResult<List<TdsModularDomain>>();
		TdsModular tds=new TdsModular();
		List<TdsModularDomain>  listDomain=new ArrayList<TdsModularDomain>();
		try {
			BeanUtils.copyProperties(domain,tds);
			List<TdsModular> list=tdsModularMapper.selectAll(tds);
			if(list.size()>0 && list!=null){
				TdsModularDomain tdsDomain=null;
	          for(TdsModular obj:list){
	        	 tdsDomain=new TdsModularDomain();
	        	 BeanUtils.copyProperties(obj,tdsDomain);
	        	 listDomain.add(tdsDomain);
				}
	          
	         //排序
//	         if(null!=domain.getParentId() && 0==domain.getParentId()){
//	             Collections.sort(listDomain, new Comparator<TdsModularDomain>() {
//	 				@Override
//	 				public int compare(TdsModularDomain o1, TdsModularDomain o2) {
//	 					int o=0;
//	 					if(null!=o1.getSort()){
//	 						o=o1.getSort()-o2.getSort();
//	 					}
//	 					return o;
//	 				 }  
//	 	          });  
//	         }
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

	
	/**
	 * 
	 * name  模块名称
	 * currentPage 当前页
	 * numPerPage 显示多少行
	 */
	@Override
	public BackResult<PageDomain<Map<String, Object>>> pageByModular(String name,BasePageParam basePageParam) {
		BackResult<PageDomain<Map<String, Object>>> result =new  BackResult<PageDomain<Map<String, Object>>>();
		PageDomain<Map<String, Object>> pageListDomain = null;
		try {
			if(null==basePageParam.getCurrentPage())basePageParam.setCurrentPage(1);
			if(null==basePageParam.getNumPerPage())basePageParam.setNumPerPage(10);
			Integer count=tdsModularMapper.queryCount(name);
			Integer cur = basePageParam.getCurrentPage() <= 0 ? 1 : basePageParam.getCurrentPage();
			List<Map<String,Object>> listMap = tdsModularMapper.pageByModular(name, (cur - 1) *basePageParam.getNumPerPage(), basePageParam.getNumPerPage());
			// 构造计算分页参数
			pageListDomain = new PageDomain<>( basePageParam.getCurrentPage(),basePageParam.getNumPerPage(),count);
			pageListDomain.setTlist(listMap);
			result.setResultObj(pageListDomain);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("模块功能信息出现系统异常：" + e.getMessage());
			result.setResultCode(ResultCode.RESULT_FAILED);
			result.setResultMsg("数据集合查询失败");
		}
		return result;
	}



	
	

	

}
