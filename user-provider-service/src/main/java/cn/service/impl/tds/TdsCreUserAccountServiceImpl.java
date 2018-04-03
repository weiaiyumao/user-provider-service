package cn.service.impl.tds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.dao.CreUserAccountMapper;
import cn.entity.CreUserAccount;
import cn.service.tds.TdsCreUserAccountService;
import main.java.cn.common.ProductTypeID;
import main.java.cn.enums.TdsEnum.ACCOUNT;

@Service
public class TdsCreUserAccountServiceImpl implements TdsCreUserAccountService {

	@Autowired
	private CreUserAccountMapper creUserAccountMapper;


	@Override
	public void addOrSubCreUserAccount(Integer creUserId, Integer pnameId, Integer number,String algorType) throws Exception {
		    CreUserAccount cua = new CreUserAccount();
			cua.setCreUserId(creUserId);
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
				//cua.setType("sub");
				Integer i=creUserAccountMapper.subByUserId(cua);
				if(i<1)throw new Exception("扣除失败"); 
			}
			if (algorType.equals(ACCOUNT.ADD.getType())) {
				//cua.setType("add");
				Integer i=creUserAccountMapper.addByUserId(cua);
				if(i<1)throw new Exception("新增失败"); 
			}
	}

}
