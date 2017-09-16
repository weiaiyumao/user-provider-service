package cn.service;

import cn.entity.CreUserAccount;

/**
 * CreUserService
 * 
 * @author ChuangLan
 *
 */
public interface CreUserAccountService {

	CreUserAccount findCreUserAccountByUserId(Integer creUserId);

	int saveCreUserAccount(CreUserAccount creUserAccount);

	int updateCreUserAccount(CreUserAccount creUserAccount);
}
