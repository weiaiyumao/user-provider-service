package cn.service.tds;
import main.java.cn.common.BackResult;
import main.java.cn.service.tds.TdsUserBusService;

/**
 * : 用户服务接口
 * 
 * 
 * @author Gen
 */
public interface TdsUserService extends  TdsUserBusService{

	/**
	 * 修改密码
	 * 
	 * @param usedPass
	 *            旧密码
	 * @param newPass
	 *            新密码
	 * @param userId
	 *            修改密码用户
	 * @return
	 */
	public BackResult<Integer> upPassWord(String usedPass, String newPass,Integer userId);
	
	
	
}
