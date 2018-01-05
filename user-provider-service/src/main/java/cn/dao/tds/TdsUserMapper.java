package cn.dao.tds;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import cn.entity.tds.TdsUser;
import main.java.cn.domain.page.PageAuto;


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
    List<TdsUser> pageSelectAll(PageAuto dto);
    
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
    
    
    /**
     * 内部角色添加注册
     * @param tdsUser
     * @return
     */
    Integer addBackAdminiUser(TdsUser tdsUser);
    
    
    /**
     * 密码修改
     * @param userId
     * @param newPass
     * @return
     */
    Integer upPassWord(@Param("userId")Integer userId,@Param("newPass")String newPass);
    
    
    /**
     * 头像更新地址
     * @param id
     * @param hedehref
     * @return
     */
    Integer updateHeadImg(@Param("id")Integer id,@Param("hedehref")String hedehref);
    
}
