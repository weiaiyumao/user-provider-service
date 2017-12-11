package cn.service;


import java.util.List;

import cn.entity.TdsFunction;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.service.tds.TdsFunctionBusService;

public interface TdsFunctionService extends TdsFunctionBusService{
     
	 BackResult<TdsFunction> loadById(Integer id); 
    
	 BackResult<TdsFunctionDomain> saveTdsFunction(TdsFunctionDomain entity);
   	
	 BackResult<Integer> deleteById(Integer id);
    
	 BackResult<TdsFunctionDomain> updateTdsFunction(TdsFunctionDomain entity);
     
	 BackResult<List<TdsFunctionDomain>> selectAll(TdsFunctionDomain entity);
}
