package cn.entity.tds;

import java.io.Serializable;

/**
 * : 用户产品数量表
 * 
 * 
 * @author Gen
 */
public class TdsUserPname implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6629037541657285440L;

	//id : id 	
	private Integer id; 
	
	//关联用户 : user_id 	
	private Integer userId; 
	
	//产品id : pname_id 	
	private Integer pnameId; 
	
	//产品数量 : number 	
	private Integer number; 
	
	//产品价格 : money 	
	private String money; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//修改时间 : update_time 	
	private java.util.Date updateTime; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//修改者 : updater 	
	private Integer updater; 
	
	//备注 : remarks 	
	private String remarks; 
	

	/**
	 * id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 关联用户 : user_id
	 * 
	 * @return 
	 */
	public Integer getUserId () {
		return userId;
	}
	
	/**
	 * 关联用户 : user_id
	 * 
	 * @return 
	 */
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	/**
	 * 产品id : pname_id
	 * 
	 * @return 
	 */
	public Integer getPnameId () {
		return pnameId;
	}
	
	/**
	 * 产品id : pname_id
	 * 
	 * @return 
	 */
	public void setPnameId (Integer pnameId) {
		this.pnameId = pnameId;
	}
	/**
	 * 产品数量 : number
	 * 
	 * @return 
	 */
	public Integer getNumber () {
		return number;
	}
	
	/**
	 * 产品数量 : number
	 * 
	 * @return 
	 */
	public void setNumber (Integer number) {
		this.number = number;
	}
	/**
	 * 产品价格 : money
	 * 
	 * @return 
	 */
	public String getMoney () {
		return money;
	}
	
	/**
	 * 产品价格 : money
	 * 
	 * @return 
	 */
	public void setMoney (String money) {
		this.money = money;
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
