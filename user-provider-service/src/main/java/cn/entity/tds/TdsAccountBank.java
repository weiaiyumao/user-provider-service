package cn.entity.tds;

import java.io.Serializable;

/**
 * : 入账银行表
 * 
 * 
 * @author Gen
 */
public class TdsAccountBank implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5396571272148992121L;

	//入账银行id : id 	
	private Integer id; 
	
	//简称名 : less_name 	
	private String lessName; 
	
	//账号名称 : account_name 	
	private String accountName; 
	
	//银行类型  0：对公 1：对私 : bank_type 	
	private String bankType; 
	
	//开发银行 : bank_name 	
	private String bankName; 
	
	//开户账号号 : open_account 	
	private String openAccount; 
	
	//停用 0：不停 1：停用 : is_disable 	
	private String isDisable; 
	
	//用户id : userid 	
	private Integer userid; 
	
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
	
	//备用1 : spare1 	
	private String spare1; 
	
	//备用2 : spare2 	
	private String spare2; 
	
	//备用3 : spare3 	
	private Integer spare3; 
	
	//备用4 : spare4 	
	private Integer spare4; 
	
	//备用5 : spare5 	
	private java.util.Date spare5; 
	

	/**
	 * 入账银行id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 入账银行id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 简称名 : less_name
	 * 
	 * @return 
	 */
	public String getLessName () {
		return lessName;
	}
	
	/**
	 * 简称名 : less_name
	 * 
	 * @return 
	 */
	public void setLessName (String lessName) {
		this.lessName = lessName;
	}
	/**
	 * 账号名称 : account_name
	 * 
	 * @return 
	 */
	public String getAccountName () {
		return accountName;
	}
	
	/**
	 * 账号名称 : account_name
	 * 
	 * @return 
	 */
	public void setAccountName (String accountName) {
		this.accountName = accountName;
	}
	/**
	 * 银行类型  0：对公 1：对私 : bank_type
	 * 
	 * @return 
	 */
	public String getBankType () {
		return bankType;
	}
	
	/**
	 * 银行类型  0：对公 1：对私 : bank_type
	 * 
	 * @return 
	 */
	public void setBankType (String bankType) {
		this.bankType = bankType;
	}
	/**
	 * 开发银行 : bank_name
	 * 
	 * @return 
	 */
	public String getBankName () {
		return bankName;
	}
	
	/**
	 * 开发银行 : bank_name
	 * 
	 * @return 
	 */
	public void setBankName (String bankName) {
		this.bankName = bankName;
	}
	/**
	 * 开户账号号 : open_account
	 * 
	 * @return 
	 */
	public String getOpenAccount () {
		return openAccount;
	}
	
	/**
	 * 开户账号号 : open_account
	 * 
	 * @return 
	 */
	public void setOpenAccount (String openAccount) {
		this.openAccount = openAccount;
	}
	/**
	 * 停用 0：不停 1：停用 : is_disable
	 * 
	 * @return 
	 */
	public String getIsDisable () {
		return isDisable;
	}
	
	/**
	 * 停用 0：不停 1：停用 : is_disable
	 * 
	 * @return 
	 */
	public void setIsDisable (String isDisable) {
		this.isDisable = isDisable;
	}
	/**
	 * 用户id : userid
	 * 
	 * @return 
	 */
	public Integer getUserid () {
		return userid;
	}
	
	/**
	 * 用户id : userid
	 * 
	 * @return 
	 */
	public void setUserid (Integer userid) {
		this.userid = userid;
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
	/**
	 * 备用1 : spare1
	 * 
	 * @return 
	 */
	public String getSpare1 () {
		return spare1;
	}
	
	/**
	 * 备用1 : spare1
	 * 
	 * @return 
	 */
	public void setSpare1 (String spare1) {
		this.spare1 = spare1;
	}
	/**
	 * 备用2 : spare2
	 * 
	 * @return 
	 */
	public String getSpare2 () {
		return spare2;
	}
	
	/**
	 * 备用2 : spare2
	 * 
	 * @return 
	 */
	public void setSpare2 (String spare2) {
		this.spare2 = spare2;
	}
	/**
	 * 备用3 : spare3
	 * 
	 * @return 
	 */
	public Integer getSpare3 () {
		return spare3;
	}
	
	/**
	 * 备用3 : spare3
	 * 
	 * @return 
	 */
	public void setSpare3 (Integer spare3) {
		this.spare3 = spare3;
	}
	/**
	 * 备用4 : spare4
	 * 
	 * @return 
	 */
	public Integer getSpare4 () {
		return spare4;
	}
	
	/**
	 * 备用4 : spare4
	 * 
	 * @return 
	 */
	public void setSpare4 (Integer spare4) {
		this.spare4 = spare4;
	}
	/**
	 * 备用5 : spare5
	 * 
	 * @return 
	 */
	public java.util.Date getSpare5 () {
		return spare5;
	}
	
	/**
	 * 备用5 : spare5
	 * 
	 * @return 
	 */
	public void setSpare5 (java.util.Date spare5) {
		this.spare5 = spare5;
	}


  
}
