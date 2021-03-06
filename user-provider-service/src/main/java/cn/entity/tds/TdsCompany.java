package cn.entity.tds;

import java.io.Serializable;

/**
 * : 公司表
 * 
 * 
 * @author Gen
 */
public class TdsCompany implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6882297040264996678L;

	//公司id : id 	
	private Integer id; 
	
	//公司名称 : com_name 	
	private String comName; 
	
	//公司网址 : com_url 	
	private String comUrl; 
	
	//营业执照 : com_business 	
	private String comBusiness; 
	
	//公司地址 : com_address 	
	private String comAddress; 
	
	//公司邮箱 : com_email 	
	private String comEmail; 
	
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
	
	//备注 : remarks 	
	private String remarks; 
	

	/**
	 * 公司id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 公司id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 公司名称 : com_name
	 * 
	 * @return 
	 */
	public String getComName () {
		return comName;
	}
	
	/**
	 * 公司名称 : com_name
	 * 
	 * @return 
	 */
	public void setComName (String comName) {
		this.comName = comName;
	}
	/**
	 * 公司网址 : com_url
	 * 
	 * @return 
	 */
	public String getComUrl () {
		return comUrl;
	}
	
	/**
	 * 公司网址 : com_url
	 * 
	 * @return 
	 */
	public void setComUrl (String comUrl) {
		this.comUrl = comUrl;
	}
	/**
	 * 营业执照 : com_business
	 * 
	 * @return 
	 */
	public String getComBusiness () {
		return comBusiness;
	}
	
	/**
	 * 营业执照 : com_business
	 * 
	 * @return 
	 */
	public void setComBusiness (String comBusiness) {
		this.comBusiness = comBusiness;
	}
	/**
	 * 公司地址 : com_address
	 * 
	 * @return 
	 */
	public String getComAddress () {
		return comAddress;
	}
	
	/**
	 * 公司地址 : com_address
	 * 
	 * @return 
	 */
	public void setComAddress (String comAddress) {
		this.comAddress = comAddress;
	}
	/**
	 * 公司邮箱 : com_email
	 * 
	 * @return 
	 */
	public String getComEmail () {
		return comEmail;
	}
	
	/**
	 * 公司邮箱 : com_email
	 * 
	 * @return 
	 */
	public void setComEmail (String comEmail) {
		this.comEmail = comEmail;
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


  
}
