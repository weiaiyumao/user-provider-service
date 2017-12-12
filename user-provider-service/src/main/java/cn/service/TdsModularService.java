package cn.service;

import java.util.List;

import cn.entity.TdsModular;
import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsModularDomain;
import main.java.cn.service.tds.TdsModularBusService;

/**
 * : tds_modular
 * 
 * 
 * @author Gen
 */
public interface TdsModularService extends TdsModularBusService{
	
	      BackResult<TdsModular> loadById(Integer id); 
	    
	      BackResult<TdsModularDomain> saveTdsModular(TdsModularDomain entity);
	   	
	      BackResult<Integer> deleteById(Integer id);
	    
	      BackResult<TdsModularDomain> updateTdsModular(TdsModularDomain entity);
	      
	      BackResult<List<TdsModularDomain>> selectAll(TdsModularDomain entity);
}
