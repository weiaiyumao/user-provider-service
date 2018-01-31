package cn.entity.tds;

import java.io.Serializable;

/**
 * : 用户绑定信息
 * 
 * 
 * @author Gen
 */
public class TdsUserBankApy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8327341882746568033L;

	//用户详细信息表 : id 	
	private Integer id; 
	
	//关联id : user_id 	
	private Integer userId; 
	
	//开户人 : bank_openr 	
	private String bankOpenr; 
	
	//银行卡号 : bank_no 	
	private String bankNo; 
	
	//银行卡 : bank_name 	
	private String bankName; 
	
	//支付宝姓名人 : alipayr 	
	private String alipayr; 
	
	//支付账号 : alipay_name 	
	private String alipayName; 
	
	//用户手机号码 : user_phone 	
	private String userPhone; 
	
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
	 * 用户详细信息表 : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 用户详细信息表 : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 关联id : user_id
	 * 
	 * @return 
	 */
	public Integer getUserId () {
		return userId;
	}
	
	/**
	 * 关联id : user_id
	 * 
	 * @return 
	 */
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	/**
	 * 开户人 : bank_openr
	 * 
	 * @return 
	 */
	public String getBankOpenr () {
		return bankOpenr;
	}
	
	/**
	 * 开户人 : bank_openr
	 * 
	 * @return 
	 */
	public void setBankOpenr (String bankOpenr) {
		this.bankOpenr = bankOpenr;
	}
	/**
	 * 银行卡号 : bank_no
	 * 
	 * @return 
	 */
	public String getBankNo () {
		return bankNo;
	}
	
	/**
	 * 银行卡号 : bank_no
	 * 
	 * @return 
	 */
	public void setBankNo (String bankNo) {
		this.bankNo = bankNo;
	}
	/**
	 * 银行卡 : bank_name
	 * 
	 * @return 
	 */
	public String getBankName () {
		return bankName;
	}
	
	/**
	 * 银行卡 : bank_name
	 * 
	 * @return 
	 */
	public void setBankName (String bankName) {
		this.bankName = bankName;
	}
	/**
	 * 支付宝姓名人 : alipayr
	 * 
	 * @return 
	 */
	public String getAlipayr () {
		return alipayr;
	}
	
	/**
	 * 支付宝姓名人 : alipayr
	 * 
	 * @return 
	 */
	public void setAlipayr (String alipayr) {
		this.alipayr = alipayr;
	}
	/**
	 * 支付账号 : alipay_name
	 * 
	 * @return 
	 */
	public String getAlipayName () {
		return alipayName;
	}
	
	/**
	 * 支付账号 : alipay_name
	 * 
	 * @return 
	 */
	public void setAlipayName (String alipayName) {
		this.alipayName = alipayName;
	}
	/**
	 * 用户手机号码 : user_phone
	 * 
	 * @return 
	 */
	public String getUserPhone () {
		return userPhone;
	}
	
	/**
	 * 用户手机号码 : user_phone
	 * 
	 * @return 
	 */
	public void setUserPhone (String userPhone) {
		this.userPhone = userPhone;
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
