package cn.entity.tds;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * : 退款金额审核
 * 
 * 
 * @author Gen
 */
public class TdsMoneyApprovalBack  extends BasePageParam implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5884881277306268911L;
	

	//退款审核id : id 	
	private Integer id; 
	
	//关联用户id : user_id 	
	private Integer userId; 
	
	//客户名称 : user_name 	
	private String userName; 
	
	//流水号 : serial_number 	
	private String serialNumber; 
	
	//订单号 : order_number 	
	private String orderNumber; 
	
	//退款审核(0待审核 1已审核 2驳回 3到账 4线下开票 ) : approval_status 	
	private String approvalStatus; 
	
	//关联下单订单号 : go_order_number 	
	private String goOrderNumber; 
	
	//下单金额,涉及金额 : go_sum_money 	
	private String goSumMoney; 
	
	//下单产品名称 : go_pname 	
	private String goPname; 
	
	//下单产品数量 : go_pname_number 	
	private Integer goPnameNumber; 
	
	//退款产品数量 : back_pname_number 	
	private Integer backPnameNumber; 
	
	//退款金额 : back_sum_money 	
	private String backSumMoney; 
	
	//退款原因描述 : back_remarks 	
	private String backRemarks; 
	
	//剩余数量 : plus_number 	
	private Integer plusNumber; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	
	//退单时间 : create_time 	
	private java.util.Date createTime; 
	
	//操作更新时间 : update_time 	
	private java.util.Date updateTime; 
	
	//退单人，创建人 : creater 	
	private Integer creater; 
	
	//操作更新人 : updater 	
	private Integer updater; 
	

	/**
	 * 退款审核id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 退款审核id : id
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
	 * 客户名称 : user_name
	 * 
	 * @return 
	 */
	public String getUserName () {
		return userName;
	}
	
	/**
	 * 客户名称 : user_name
	 * 
	 * @return 
	 */
	public void setUserName (String userName) {
		this.userName = userName;
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
	 * 退款审核(0待审核 1已审核 2驳回 3到账 4线下开票 ) : approval_status
	 * 
	 * @return 
	 */
	public String getApprovalStatus () {
		return approvalStatus;
	}
	
	/**
	 * 退款审核(0待审核 1已审核 2驳回 3到账 4线下开票 ) : approval_status
	 * 
	 * @return 
	 */
	public void setApprovalStatus (String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	/**
	 * 关联下单订单号 : go_order_number
	 * 
	 * @return 
	 */
	public String getGoOrderNumber () {
		return goOrderNumber;
	}
	
	/**
	 * 关联下单订单号 : go_order_number
	 * 
	 * @return 
	 */
	public void setGoOrderNumber (String goOrderNumber) {
		this.goOrderNumber = goOrderNumber;
	}
	/**
	 * 下单金额,涉及金额 : go_sum_money
	 * 
	 * @return 
	 */
	public String getGoSumMoney () {
		return goSumMoney;
	}
	
	/**
	 * 下单金额,涉及金额 : go_sum_money
	 * 
	 * @return 
	 */
	public void setGoSumMoney (String goSumMoney) {
		this.goSumMoney = goSumMoney;
	}
	/**
	 * 下单产品名称 : go_pname
	 * 
	 * @return 
	 */
	public String getGoPname () {
		return goPname;
	}
	
	/**
	 * 下单产品名称 : go_pname
	 * 
	 * @return 
	 */
	public void setGoPname (String goPname) {
		this.goPname = goPname;
	}
	/**
	 * 下单产品数量 : go_pname_number
	 * 
	 * @return 
	 */
	public Integer getGoPnameNumber () {
		return goPnameNumber;
	}
	
	/**
	 * 下单产品数量 : go_pname_number
	 * 
	 * @return 
	 */
	public void setGoPnameNumber (Integer goPnameNumber) {
		this.goPnameNumber = goPnameNumber;
	}
	/**
	 * 退款产品数量 : back_pname_number
	 * 
	 * @return 
	 */
	public Integer getBackPnameNumber () {
		return backPnameNumber;
	}
	
	/**
	 * 退款产品数量 : back_pname_number
	 * 
	 * @return 
	 */
	public void setBackPnameNumber (Integer backPnameNumber) {
		this.backPnameNumber = backPnameNumber;
	}
	/**
	 * 退款金额 : back_sum_money
	 * 
	 * @return 
	 */
	public String getBackSumMoney () {
		return backSumMoney;
	}
	
	/**
	 * 退款金额 : back_sum_money
	 * 
	 * @return 
	 */
	public void setBackSumMoney (String backSumMoney) {
		this.backSumMoney = backSumMoney;
	}
	/**
	 * 退款原因描述 : back_remarks
	 * 
	 * @return 
	 */
	public String getBackRemarks () {
		return backRemarks;
	}
	
	/**
	 * 退款原因描述 : back_remarks
	 * 
	 * @return 
	 */
	public void setBackRemarks (String backRemarks) {
		this.backRemarks = backRemarks;
	}
	/**
	 * 剩余数量 : plus_number
	 * 
	 * @return 
	 */
	public Integer getPlusNumber () {
		return plusNumber;
	}
	
	/**
	 * 剩余数量 : plus_number
	 * 
	 * @return 
	 */
	public void setPlusNumber (Integer plusNumber) {
		this.plusNumber = plusNumber;
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
	 * 退单时间 : create_time
	 * 
	 * @return 
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}
	
	/**
	 * 退单时间 : create_time
	 * 
	 * @return 
	 */
	public void setCreateTime (java.util.Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 操作更新时间 : update_time
	 * 
	 * @return 
	 */
	public java.util.Date getUpdateTime () {
		return updateTime;
	}
	
	/**
	 * 操作更新时间 : update_time
	 * 
	 * @return 
	 */
	public void setUpdateTime (java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 退单人，创建人 : creater
	 * 
	 * @return 
	 */
	public Integer getCreater () {
		return creater;
	}
	
	/**
	 * 退单人，创建人 : creater
	 * 
	 * @return 
	 */
	public void setCreater (Integer creater) {
		this.creater = creater;
	}
	/**
	 * 操作更新人 : updater
	 * 
	 * @return 
	 */
	public Integer getUpdater () {
		return updater;
	}
	
	/**
	 * 操作更新人 : updater
	 * 
	 * @return 
	 */
	public void setUpdater (Integer updater) {
		this.updater = updater;
	}


  
}
