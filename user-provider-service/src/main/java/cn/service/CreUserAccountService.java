package cn.service;

import cn.entity.CreUserAccount;
import main.java.cn.domain.UserAccountDomain;
import main.java.cn.service.UserAccountBusService;

/**
 * CreUserService
 * 
 * @author ChuangLan
 *
 */
public interface CreUserAccountService extends UserAccountBusService{

	/**
	 * 从数据库中获取
	 * @param creUserId
	 * @return
	 */
	CreUserAccount findCreUserAccountByUserId(Integer creUserId);

	/**
	 * 从缓存中获取
	 * @param creUserId
	 * @return
	 */
	UserAccountDomain loadCreUserAccountByUserId(Integer creUserId);

	int saveCreUserAccount(CreUserAccount creUserAccount);

	int updateCreUserAccount(CreUserAccount creUserAccount);
}
