package cn.entity.tds;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * :流水明细表
 * 
 * 
 * @author Gen
 */
public class TdsSerualInfo extends BasePageParam implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1626991264903161443L;

	//流水id : id 	
	private Integer id; 
	
	//流水号 : serial_number 	
	private String serialNumber; 
	
	//订单号 : order_number 	
	private String orderNumber; 
	
	//流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type 	
	private String serialType; 
	
	//关联用户id : user_id 	
	private Integer userId; 
	
	//流水金额 : serial_money 	
	private String serialMoney; 
	
	//之前金额 : before_money 	
	private String beforeMoney; 
	
	//流水状态 1处理中 2已到账 3被驳回 : serial_status 	
	private String serialStatus; 
	
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
	 * 流水id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 流水id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
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
	 * 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
	 * 
	 * @return 
	 */
	public String getSerialType () {
		return serialType;
	}
	
	/**
	 * 流水类型：1佣金;2提现，3退款，4充值，5进账 6出账 : serial_type
	 * 
	 * @return 
	 */
	public void setSerialType (String serialType) {
		this.serialType = serialType;
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
	 * 流水金额 : serial_money
	 * 
	 * @return 
	 */
	public String getSerialMoney () {
		return serialMoney;
	}
	
	/**
	 * 流水金额 : serial_money
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
	 * 流水状态 1处理中 2已到账 3被驳回 : serial_status
	 * 
	 * @return 
	 */
	public String getSerialStatus () {
		return serialStatus;
	}
	
	/**
	 * 流水状态 1处理中 2已到账 3被驳回 : serial_status
	 * 
	 * @return 
	 */
	public void setSerialStatus (String serialStatus) {
		this.serialStatus = serialStatus;
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
