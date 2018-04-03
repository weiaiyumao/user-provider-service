package cn.service.tds;


/**
 * CRE 
 * @author ChuangLan
 *
 */
public interface TdsCreUserAccountService {

    
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
