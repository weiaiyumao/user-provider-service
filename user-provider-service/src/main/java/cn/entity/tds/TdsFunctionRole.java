package cn.entity.tds;

import java.io.Serializable;

/**
 * : 功能与角色实体
 * 
 * 
 * @author Gen
 */
public class TdsFunctionRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1721506749029644804L;

	// : id 	
	private Integer id; 
	
	//功能id : fun_id 	
	private Integer funId; 
	
	//角色id : role_id 	
	private Integer roleId; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//修改时间 : update_time 	
	private java.util.Date updateTime; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//修改者 : updater 	
	private Integer updater; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	

	/**
	 *  : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 *  : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 功能id : fun_id
	 * 
	 * @return 
	 */
	public Integer getFunId () {
		return funId;
	}
	
	/**
	 * 功能id : fun_id
	 * 
	 * @return 
	 */
	public void setFunId (Integer funId) {
		this.funId = funId;
	}
	/**
	 * 角色id : role_id
	 * 
	 * @return 
	 */
	public Integer getRoleId () {
		return roleId;
	}
	
	/**
	 * 角色id : role_id
	 * 
	 * @return 
	 */
	public void setRoleId (Integer roleId) {
		this.roleId = roleId;
	}
	/**
	 * 创建时间 : create_time
	 * 
	 * @return 
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}
	
	/**
	 * 创建时间 : create_time
	 * 
	 * @return 
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 修改时间 : update_time
	 * 
	 * @return 
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}
	
	/**
	 * 修改时间 : update_time
	 * 
	 * @return 
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 创建者 : creater
	 * 
	 * @return 
	 */
	public Integer getCreater () {
		return creater;
	}
	
	/**
	 * 创建者 : creater
	 * 
	 * @return 
	 */
	public void setCreater (Integer creater) {
		this.creater = creater;
	}
	/**
	 * 修改者 : updater
	 * 
	 * @return 
	 */
	public Integer getUpdater () {
		return updater;
	}
	
	/**
	 * 修改者 : updater
	 * 
	 * @return 
	 */
	public void setUpdater (Integer updater) {
		this.updater = updater;
	}
	/**
	 * 删除状态：0正常，1已删除 : is_deleted
	 * 
	 * @return 
	 */
	public String getIsDeleted () {
		return isDeleted;
	}
	
	/**
	 * 删除状态：0正常，1已删除 : is_deleted
	 * 
	 * @return 
	 */
	public void setIsDeleted (String isDeleted) {
		this.isDeleted = isDeleted;
	}


  
}
