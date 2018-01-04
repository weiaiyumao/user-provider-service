package cn.entity.tds;

import java.io.Serializable;

/**
 * :职位实体
 * 
 * 
 * @author Gen
 */
public class TdsPosition implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2144715334521690987L;

	//职位id : id 	
	private Integer id; 
	
	//职位名称 : name 	
	private String name; 
	
	//部门 : department_id 	
	private Integer departmentId; 
	
	//备注 : remarks 	
	private String remarks; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//修改时间 : update_time 	
	private java.util.Date updateTime; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//修改者 : updater 	
	private Integer updater; 
	
	
	//排序 : sort 	
	private String sort; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	

	/**
	 * 职位id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 职位id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 职位名称 : name
	 * 
	 * @return 
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * 职位名称 : name
	 * 
	 * @return 
	 */
	public void setName (String name) {
		this.name = name;
	}
	/**
	 * 部门 : department_id
	 * 
	 * @return 
	 */
	public Integer getDepartmentId () {
		return departmentId;
	}
	
	/**
	 * 部门 : department_id
	 * 
	 * @return 
	 */
	public void setDepartmentId (Integer departmentId) {
		this.departmentId = departmentId;
	}
	/**
	 * 备注 : remarks
	 * 
	 * @return 
	 */
	public String getRemarks () {
		return remarks;
	}
	
	/**
	 * 备注 : remarks
	 * 
	 * @return 
	 */
	public void setRemarks (String remarks) {
		this.remarks = remarks;
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
	 * 排序 : sort
	 * 
	 * @return 
	 */
	public String getSort () {
		return sort;
	}
	
	/**
	 * 排序 : sort
	 * 
	 * @return 
	 */
	public void setSort (String sort) {
		this.sort = sort;
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
