package cn.entity.tds;

import java.io.Serializable;

/**
 * : 用户改价折扣表
 * 
 * 
 * @author Gen
 */
public class TdsUserDiscount implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6687730149749088266L;

	//折扣id : id 	
	private Integer id; 
	
	//关联用户id : user_id 	
	private Integer userId; 
	
	//起充金额 : start_money 	
	private Integer startMoney; 
	
	//起充折扣  **% : start_discount 	
	private String startDiscount; 
	
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
	 * 折扣id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 折扣id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 关联用户id : user_id
	 * 
	 * @return 
	 */
	public Integer getUserId () {
		return userId;
	}
	
	/**
	 * 关联用户id : user_id
	 * 
	 * @return 
	 */
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	/**
	 * 起充金额 : start_money
	 * 
	 * @return 
	 */
	public Integer getStartMoney () {
		return startMoney;
	}
	
	/**
	 * 起充金额 : start_money
	 * 
	 * @return 
	 */
	public void setStartMoney (Integer startMoney) {
		this.startMoney = startMoney;
	}
	/**
	 * 起充折扣  **% : start_discount
	 * 
	 * @return 
	 */
	public String getStartDiscount () {
		return startDiscount;
	}
	
	/**
	 * 起充折扣  **% : start_discount
	 * 
	 * @return 
	 */
	public void setStartDiscount (String startDiscount) {
		this.startDiscount = startDiscount;
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
