package cn.entity.tds;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * :
 * 
 * 财务审核记录表
 * 
 * @author Gen
 */
public class TdsMoneyApproval extends BasePageParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8573809568466941132L;

	// 财务审核表 : id
	private Integer id;

	// 关联用户id : user_id
	private Integer userId;

	// 流水号 : serial_number
	private String serialNumber;

	// 订单号 : order_number
	private String orderNumber;

	// 客户名称 : user_name
	private String userName;

	// 产品名称 : pname
	private String pname;

	// 产品数量 : number
	private Integer number;

	// 产品价格 : money
	private String money="0";

	// 涉及金额，总金额 : sum_money
	private String sumMoney="0";

	// 付款类型1进账 2出账 3退款 : payment
	private String payment;

	// 关联银行id : bank_id
	private String bankId;

	// 订单时间 : create_tiem
   private java.util.Date createTime;

	// 到账时间 : arrive_time
	private java.util.Date arriveTime;

	// 开票状态 0未开票 1已开票 : billing
	private String billing;

	// 审核(0待审核 1已审核 2驳回 3到账 4线下开票 5 充账 )
	private String approvalStatus;

	// 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	private String approvalType;

	// 删除状态：0正常，1已删除 : is_deleted
	private String isDeleted;

	// 备注 : remarks
	private String remarks;

	// 修改者 : updater
	private Integer updater;
	// 修改时间 : update_time
	private java.util.Date updateTime;
	
	private String commissonMoney="0";

	
	private String lessName;

	// 下单人，创建人
	private Integer creater;

	//关联产品id : pname_id 	
	private Integer pnameId; 
	
	
	public String getCommissonMoney() {
		return commissonMoney;
	}
	
	public void setCommissonMoney(String commissonMoney) {
		this.commissonMoney = commissonMoney;
	}
	
	/**
	 * 关联产品id : pname_id
	 * 
	 * @return 
	 */
	public Integer getPnameId () {
		return pnameId;
	}
	
	/**
	 * 关联产品id : pname_id
	 * 
	 * @return 
	 */
	public void setPnameId (Integer pnameId) {
		this.pnameId = pnameId;
	}
	
	public String getLessName() {
		return lessName;
	}
	
	public void setLessName(String lessName) {
		this.lessName = lessName;
	}
	
	/**
	 * 下单人，创建人
	 * 
	 * @return
	 */
	public Integer getCreater() {
		return creater;
	}

	/**
	 * 下单人，创建人
	 * 
	 * @param creater
	 */
	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 财务审核表 : id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 财务审核表 : id
	 * 
	 * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 关联用户id : user_id
	 * 
	 * @return
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 关联用户id : user_id
	 * 
	 * @return
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * 流水号 : serial_number
	 * 
	 * @return
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * 流水号 : serial_number
	 * 
	 * @return
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * 订单号 : order_number
	 * 
	 * @return
	 */
	public String getOrderNumber() {
		return orderNumber;
	}

	/**
	 * 订单号 : order_number
	 * 
	 * @return
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	/**
	 * 客户名称 : user_name
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 客户名称 : user_name
	 * 
	 * @return
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 产品名称 : pname
	 * 
	 * @return
	 */
	public String getPname() {
		return pname;
	}

	/**
	 * 产品名称 : pname
	 * 
	 * @return
	 */
	public void setPname(String pname) {
		this.pname = pname;
	}

	/**
	 * 产品数量 : number
	 * 
	 * @return
	 */
	public Integer getNumber() {
		return number;
	}

	/**
	 * 产品数量 : number
	 * 
	 * @return
	 */
	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * 产品价格 : money
	 * 
	 * @return
	 */
	public String getMoney() {
		return money;
	}

	/**
	 * 产品价格 : money
	 * 
	 * @return
	 */
	public void setMoney(String money) {
		this.money = money;
	}

	/**
	 * 涉及金额，总金额 : sum_money
	 * 
	 * @return
	 */
	public String getSumMoney() {
		return sumMoney;
	}

	/**
	 * 涉及金额，总金额 : sum_money
	 * 
	 * @return
	 */
	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

	/**
	 * 付款类型1进账 2出账 3退款 : payment
	 * 
	 * @return
	 */
	public String getPayment() {
		return payment;
	}

	/**
	 * 付款类型1进账 2出账 3退款 : payment
	 * 
	 * @return
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}

	/**
	 * 关联银行id : bank_id
	 * 
	 * @return
	 */
	public String getBankId() {
		return bankId;
	}

	/**
	 * 关联银行id : bank_id
	 * 
	 * @return
	 */
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	/**
	 * 订单时间 : create_tiem
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * 订单时间 : create_tiem
	 * 
	 * @return
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 到账时间 : arrive_time
	 * 
	 * @return
	 */
	public java.util.Date getArriveTime() {
		return arriveTime;
	}

	/**
	 * 到账时间 : arrive_time
	 * 
	 * @return
	 */
	public void setArriveTime(java.util.Date arriveTime) {
		this.arriveTime = arriveTime;
	}

	/**
	 * 开票状态 0未开票 1已开票 : billing
	 * 
	 * @return
	 */
	public String getBilling() {
		return billing;
	}

	/**
	 * 开票状态 0未开票 1已开票 : billing
	 * 
	 * @return
	 */
	public void setBilling(String billing) {
		this.billing = billing;
	}

	/**
	 * 审核(0待审核 1已审核 2驳回 3到账 4线下开票 5 充账 )
	 * 
	 * @return
	 */
	public String getApprovalStatus() {
		return approvalStatus;
	}

	/**
	 * 审核(0待审核 1已审核 2驳回 3到账 4线下开票 5 充账 )
	 * 
	 * @return
	 */
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	/**
	 * 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	 * 
	 * @return
	 */
	public String getApprovalType() {
		return approvalType;
	}

	/**
	 * 审核类型 1进账审核 2出账审核 3退款审核 : approval_type
	 * 
	 * @return
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	/**
	 * 删除状态：0正常，1已删除 : is_deleted
	 * 
	 * @return
	 */
	public String getIsDeleted() {
		return isDeleted;
	}

	/**
	 * 删除状态：0正常，1已删除 : is_deleted
	 * 
	 * @return
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * 备注 : remarks
	 * 
	 * @return
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * 备注 : remarks
	 * 
	 * @return
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
