package cn.service.tds;



import main.java.cn.service.tds.TdsDepartmentBusService;

/**
 * 部门服务接口
 * @author ChuangLan
 *
 */
public interface TdsDepartmentService extends TdsDepartmentBusService{
	
	
	/**
	 * 根据用户id获取对应部门
	 * @param userId
	 * @return
	 */
	String  selectDepartmentRoleByUserId(Integer userId);
	

}
