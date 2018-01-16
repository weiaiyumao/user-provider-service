package cn.entity.tds;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * :银行入账
 * 
 * 
 * @author Gen
 */
public class TdsAccountBank extends BasePageParam implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3033211713682451381L;

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
	
	// : uid 	
	private Integer uid; 
	

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
	/**
	 *  : uid
	 * 
	 * @return 
	 */
	public Integer getUid () {
		return uid;
	}
	
	/**
	 *  : uid
	 * 
	 * @return 
	 */
	public void setUid (Integer uid) {
		this.uid = uid;
	}


  
}
