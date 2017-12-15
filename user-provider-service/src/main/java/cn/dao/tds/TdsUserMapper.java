package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUser;
import cn.entity.tds.view.TdsUserView;


/**
 * : tds_user
 * 
 * 
 * @author Gen
 */
@Mapper
public interface TdsUserMapper extends IBaseDao<TdsUser, Integer>{
	
	
	/**
	 * 
	 * @param tdsUser
	 * @param currentPage  当前页
	 * @param numPerPage   每页显示多行
	 * @return
	 */
    List<TdsUser> pageSelectAll(TdsUserView dto);
    
    /**
     * 根据参数查询总条数
     * @param tds
     * @return
     */
    Integer quertCount(TdsUser tds);
    
    
    /**
     * 根据手机号码查询用户信息
     * @param phone
     * @return
     */
    TdsUser loadByPhone(String phone);
    
    /**
     * 登陆
     * @param name
     * @param password
     * @return
     */
    TdsUser  login(@Param("name")String name,@Param("password")String password);
    
    /**
     * 检验用户
     * @param name
     * @return
     */
    Integer  isUserName(@Param("name")String name);
}
