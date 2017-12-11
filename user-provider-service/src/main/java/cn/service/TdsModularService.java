package cn.service;

import java.util.List;

import cn.entity.TdsModular;
import main.java.cn.service.tds.TdsModularBusService;

/**
 * : tds_modular
 * 
 * 
 * @author Gen
 */
public interface TdsModularService extends TdsModularBusService{
	
	      TdsModular loadById(Integer id); 
	    
	      Integer saveTdsModular(TdsModular entity);
	   	
	      Integer deleteById(Integer id);
	    
	      Integer updateTdsModular(TdsModular entity);
	      
	      List<TdsModular> selectAll(TdsModular entity);
}
