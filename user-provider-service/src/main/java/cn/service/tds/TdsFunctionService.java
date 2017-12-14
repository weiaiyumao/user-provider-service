package cn.service.tds;

import java.util.List;

import main.java.cn.common.BackResult;
import main.java.cn.domain.tds.TdsFunctionDomain;
import main.java.cn.service.tds.TdsFunctionBusService;

public interface TdsFunctionService extends TdsFunctionBusService {

	BackResult<TdsFunctionDomain> loadById(Integer id);

	BackResult<TdsFunctionDomain> saveTdsFunction(TdsFunctionDomain entity);

	BackResult<Integer> deleteById(Integer id);

	BackResult<TdsFunctionDomain> updateTdsFunction(TdsFunctionDomain entity);

	BackResult<List<TdsFunctionDomain>> selectAll(TdsFunctionDomain entity);

	
}
