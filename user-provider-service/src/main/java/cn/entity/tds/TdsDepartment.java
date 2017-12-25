package cn.entity.tds;

import java.io.Serializable;

/**
 * : 部门实体
 * 
 * 
 * @author Gen
 */
public class TdsDepartment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4535091220533151548L;

	//部门id : id 	
	private Integer id; 
	
	//部门名称 : Name 	
	private String name; 
	
	//备注 : remarks 	
	private String remarks; 
	
	//父级部门id : parent_id 	
	private Integer parentId; 
	
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
	
	
	//部门类 : type 	
    private String type; 
	
    
    public String getType() {
		return type;
	}
    public void setType(String type) {
		this.type = type;
	}

	/**
	 * 部门id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 部门id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 部门名称 : Name
	 * 
	 * @return 
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * 部门名称 : Name
	 * 
	 * @return 
	 */
	public void setName (String name) {
		this.name = name;
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
	 * 父级部门id : parent_id
	 * 
	 * @return 
	 */
	public Integer getParentId () {
		return parentId;
	}
	
	/**
	 * 父级部门id : parent_id
	 * 
	 * @return 
	 */
	public void setParentId (Integer parentId) {
		this.parentId = parentId;
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
