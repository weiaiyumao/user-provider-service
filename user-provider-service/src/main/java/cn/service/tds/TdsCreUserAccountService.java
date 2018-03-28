package cn.service.tds;

import cn.entity.CreUserAccount;
import main.java.cn.common.BackResult;

/**
 * CRE 
 * @author ChuangLan
 *
 */
public interface TdsCreUserAccountService {

	
	/**
	 * 产品条数递增
	 * @param creUserAccount
	 * @return
	 * @throws Exception 
	 */
    BackResult<Integer> addByUserId(CreUserAccount creUserAccount) throws Exception;
	
    
    
    /**
	 * 产品条数递减
	 * @param creUserAccount
	 * @return
     * @throws Exception 
	 */
    BackResult<Integer> subByUserId(CreUserAccount creUserAccount) throws Exception;
    
    /**
     *  * 根据cre 用户id更改数量
	 * @param creUserId  cre用户id
	 * @param pnameId    产品id
	 * @param number     条数
	 * @param algorType   add or sub
     * @throws Exception 
     */
    void addOrSubCreUserAccount(Integer creUserId,Integer pnameId,Integer number,String algorType) throws Exception;
}
