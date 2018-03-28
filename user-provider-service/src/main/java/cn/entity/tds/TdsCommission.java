package cn.entity.tds;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * : 佣金列表实体
 * 
 * 
 * @author Gen
 */
public class TdsCommission extends BasePageParam implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4898850630086232457L;

	//佣金id : id 	
	private Integer id; 
	
	//关联用户id : user_id 	
	private Integer userId; 
	
	//流水号 : serial_number 	
	private String serialNumber; 
	
	//订单号 : order_number 	
	private String orderNumber; 
	
	//到账金额 : serial_money 	
	private String serialMoney; 
	
	//之前金额 : before_money 	
	private String beforeMoney; 
	
	//佣金状态 1处理中 2已到账 3被驳回 : comm_status 	
	private String commStatus; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//处理时间  审核时间 : update_time 	
	private java.util.Date updateTime; 
	
	//更新人，操作人 : updater 	
	private Integer updater; 
	

	/**
	 * 佣金id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 佣金id : id
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
	 * 流水号 : serial_number
	 * 
	 * @return 
	 */
	public String getSerialNumber () {
		return serialNumber;
	}
	
	/**
	 * 流水号 : serial_number
	 * 
	 * @return 
	 */
	public void setSerialNumber (String serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * 订单号 : order_number
	 * 
	 * @return 
	 */
	public String getOrderNumber () {
		return orderNumber;
	}
	
	/**
	 * 订单号 : order_number
	 * 
	 * @return 
	 */
	public void setOrderNumber (String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * 到账佣金金额 : serial_money
	 * 
	 * @return 
	 */
	public String getSerialMoney () {
		return serialMoney;
	}
	
	/**
	 * 到账佣金金额 : serial_money
	 * 
	 * @return 
	 */
	public void setSerialMoney (String serialMoney) {
		this.serialMoney = serialMoney;
	}
	/**
	 * 之前金额 : before_money
	 * 
	 * @return 
	 */
	public String getBeforeMoney () {
		return beforeMoney;
	}
	
	/**
	 * 之前金额 : before_money
	 * 
	 * @return 
	 */
	public void setBeforeMoney (String beforeMoney) {
		this.beforeMoney = beforeMoney;
	}
	/**
	 * 佣金状态 1处理中 2已到账 3被驳回 : comm_status
	 * 
	 * @return 
	 */
	public String getCommStatus () {
		return commStatus;
	}
	
	/**
	 * 佣金状态 1处理中 2已到账 3被驳回 4.已提现 5.已退款
	 * 
	 * @return 
	 */
	public void setCommStatus (String commStatus) {
		this.commStatus = commStatus;
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
	 * 处理时间  审核时间 : update_time
	 * 
	 * @return 
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}
	
	/**
	 * 处理时间  审核时间 : update_time
	 * 
	 * @return 
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 更新人，操作人 : updater
	 * 
	 * @return 
	 */
	public Integer getUpdater () {
		return updater;
	}
	
	/**
	 * 更新人，操作人 : updater
	 * 
	 * @return 
	 */
	public void setUpdater (Integer updater) {
		this.updater = updater;
	}


  
}
