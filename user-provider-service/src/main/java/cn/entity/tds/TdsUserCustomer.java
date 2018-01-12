package cn.entity.tds;

import java.io.Serializable;

/**
 * : 用户关联客户列表
 * 
 * 
 * @author Gen
 */
public class TdsUserCustomer implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7429700763526781330L;

	//客户列表id : id 	
	private Integer id; 
	
	//关联userid : user_id 	
	private Integer userId; 
	
	//最近充值时间 : last_money_time 	
	private java.util.Date lastMoneyTime; 
	
	//累积充值金额 : sum_money 	
	private String sumMoney; 
	
	//累积返佣金 : sum_commission 	
	private String sumCommission; 
	
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
	

	/**
	 * 客户列表id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 客户列表id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 关联userid : user_id
	 * 
	 * @return 
	 */
	public Integer getUserId () {
		return userId;
	}
	
	/**
	 * 关联userid : user_id
	 * 
	 * @return 
	 */
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	/**
	 * 最近充值时间 : last_money_time
	 * 
	 * @return 
	 */
	public java.util.Date getLastMoneyTime () {
		return lastMoneyTime;
	}
	
	/**
	 * 最近充值时间 : last_money_time
	 * 
	 * @return 
	 */
	public void setLastMoneyTime (java.util.Date lastMoneyTime) {
		this.lastMoneyTime = lastMoneyTime;
	}
	/**
	 * 累积充值金额 : sum_money
	 * 
	 * @return 
	 */
	public String getSumMoney () {
		return sumMoney;
	}
	
	/**
	 * 累积充值金额 : sum_money
	 * 
	 * @return 
	 */
	public void setSumMoney (String sumMoney) {
		this.sumMoney = sumMoney;
	}
	/**
	 * 累积返佣金 : sum_commission
	 * 
	 * @return 
	 */
	public String getSumCommission () {
		return sumCommission;
	}
	
	/**
	 * 累积返佣金 : sum_commission
	 * 
	 * @return 
	 */
	public void setSumCommission (String sumCommission) {
		this.sumCommission = sumCommission;
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


  
}
