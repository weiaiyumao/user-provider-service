package cn.entity.tds;

import java.io.Serializable;
import java.util.List;


/**
 * : 功能实体
 * 
 * 
 * @author Gen
 */
public class TdsFunction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8772115761370124672L;

	//功能id : id 	
	private Integer id; 
	
	//功能地址 : url 	
	private String url; 
	
	//功能名称 : name 	
	private String name; 
	
	//备注 : remarks 	
	private String remarks; 
	
	//父级模块id : parent_id 	
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
	
	
	private List<TdsFunction>  tdsFunctions;
	
	
	public List<TdsFunction> getTdsFunctions() {
		return tdsFunctions;
	}
	
	public void setTdsFunctions(List<TdsFunction> tdsFunctions) {
		this.tdsFunctions = tdsFunctions;
	}

	

	/**
	 * 功能id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 功能id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 功能地址 : url
	 * 
	 * @return 
	 */
	public String getUrl () {
		return url;
	}
	
	/**
	 * 功能地址 : url
	 * 
	 * @return 
	 */
	public void setUrl (String url) {
		this.url = url;
	}
	/**
	 * 功能名称 : name
	 * 
	 * @return 
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * 功能名称 : name
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
	 * 父级模块id : parent_id
	 * 
	 * @return 
	 */
	public Integer getParentId () {
		return parentId;
	}
	
	/**
	 * 父级模块id : parent_id
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
