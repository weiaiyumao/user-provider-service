package cn.service.impl.tds;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.tds.TdsUserBankApyMapper;
import cn.entity.tds.TdsUserBankApy;
import cn.service.tds.TdsUserBankApyService;
import main.java.cn.domain.tds.TdsUserBankApyDomain;

@Service
public class TdsUserBankApyServiceImpl implements TdsUserBankApyService {

	
	@Autowired
	private TdsUserBankApyMapper tdsUserBankApyMapper;
	
	@Override
	@Transactional
	public  Integer add(TdsUserBankApyDomain domain) {
		TdsUserBankApy entity=new TdsUserBankApy();
		BeanUtils.copyProperties(domain, entity);
		return tdsUserBankApyMapper.save(entity);
	}

	@Override
	@Transactional
	public void updateByUserId(TdsUserBankApyDomain domain) {
		TdsUserBankApy entity=new TdsUserBankApy();
		BeanUtils.copyProperties(domain, entity);
	    tdsUserBankApyMapper.updateByUserId(entity);
	}

	@Override
	public  TdsUserBankApy loadByUserId(Integer userId) {
		TdsUserBankApy entity=tdsUserBankApyMapper.loadByUserId(userId);
		return entity;
	}

}
