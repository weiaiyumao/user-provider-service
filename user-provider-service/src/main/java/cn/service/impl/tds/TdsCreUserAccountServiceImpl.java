package cn.service.impl.tds;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserAccountMapper;
import cn.entity.CreUserAccount;
import cn.service.tds.TdsCreUserAccountService;
import main.java.cn.common.BackResult;
import main.java.cn.common.ProductTypeID;
import main.java.cn.enums.TdsEnum.ACCOUNT;

@SuppressWarnings("unchecked")
@Service
public class TdsCreUserAccountServiceImpl implements TdsCreUserAccountService {

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;

	@Autowired
	private TdsCreUserAccountService tdsCreUserAccountService;

	@Override
	@Transactional
	public BackResult<Integer> addByUserId(CreUserAccount creUserAccount) throws Exception {
		try {
			creUserAccount.setType("add");
			creUserAccountMapper.addByUserId(creUserAccount);
		} catch (Exception e) {
			throw new Exception();
		}
		return BackResult.ok();
	}

	@Override
	@Transactional
	public BackResult<Integer> subByUserId(CreUserAccount creUserAccount) throws Exception {
		try {
			creUserAccount.setType("sub");
			creUserAccountMapper.subByUserId(creUserAccount);
		} catch (Exception e) {
			 throw new Exception();
		}
		return BackResult.ok();
	}
	
	

	@Override
	public void addOrSubCreUserAccount(Integer creUserId, Integer pnameId, Integer number,String algorType) throws Exception {
		CreUserAccount cua = new CreUserAccount();
			
			 cua.setCreUserId(creUserId);
			 cua.setUpdateTime(new Date());
         
			if (pnameId == ProductTypeID.ACCOUNT) {
				cua.setAccount(number);
			}

			if (pnameId == ProductTypeID.APIACCOUNT) {
				cua.setApiAccount(number);
			}

			if (pnameId == ProductTypeID.RQACCOUNT) {
				cua.setRqAccount(number);
			}
            
			
			if (algorType.equals(ACCOUNT.SUB.getType())) {
				tdsCreUserAccountService.subByUserId(cua);
			}
			
			if (algorType.equals(ACCOUNT.ADD.getType())) {
				tdsCreUserAccountService.addByUserId(cua);
			}
	}

}
