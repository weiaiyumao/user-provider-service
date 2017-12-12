package cn.entity;

import java.io.Serializable;

/**
 * : 分销分账用户表
 * 
 * 
 * @author Gen
 */
public class TdsUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 158026506145271289L;
	
	// 用户id : id
	private Integer id;

	// 名称，昵称 : name
	private String name;

	// 公司名称 : com_name
	private String comName;

	// 公司网址 : com_url
	private String comUrl;

	// 联系人 : contact
	private String contact;

	// 手机号码 : phone
	private String phone;

	// 密码 : password
	private String password;

	// 邀请码 : invitedcode
	private String invitedcode;

	// 备注 : remarks
	private String remarks;

	// 创建者 : creater
	private Integer creater;

	// 修改者 : updater
	private Integer updater;

	// 删除状态：0正常，1已删除 : is_deleted
	private String isDeleted;

	// 修改时间 : update_time
	private java.util.Date updateTime;

	// 创建时间 : create_time
	private java.util.Date createTime;

	// : token
	private String token;

	// IP地址 : ip
	private String ip;

	// 最近登录时间 : last_login_time
	private java.util.Date lastLoginTime;

	// 版本号 : version
	private Integer version;

	// 头像图片地址 : hedehref
	private String hedehref;

	/**
	 * 用户id : id
	 * 
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 用户id : id
	 * 
	 * @return
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 名称，昵称 : name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称，昵称 : name
	 * 
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 公司名称 : com_name
	 * 
	 * @return
	 */
	public String getComName() {
		return comName;
	}

	/**
	 * 公司名称 : com_name
	 * 
	 * @return
	 */
	public void setComName(String comName) {
		this.comName = comName;
	}

	/**
	 * 公司网址 : com_url
	 * 
	 * @return
	 */
	public String getComUrl() {
		return comUrl;
	}

	/**
	 * 公司网址 : com_url
	 * 
	 * @return
	 */
	public void setComUrl(String comUrl) {
		this.comUrl = comUrl;
	}

	/**
	 * 联系人 : contact
	 * 
	 * @return
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * 联系人 : contact
	 * 
	 * @return
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * 手机号码 : phone
	 * 
	 * @return
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 手机号码 : phone
	 * 
	 * @return
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 密码 : password
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 密码 : password
	 * 
	 * @return
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 邀请码 : invitedcode
	 * 
	 * @return
	 */
	public String getInvitedcode() {
		return invitedcode;
	}

	/**
	 * 邀请码 : invitedcode
	 * 
	 * @return
	 */
	public void setInvitedcode(String invitedcode) {
		this.invitedcode = invitedcode;
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

	/**
	 * 创建者 : creater
	 * 
	 * @return
	 */
	public Integer getCreater() {
		return creater;
	}

	/**
	 * 创建者 : creater
	 * 
	 * @return
	 */
	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	/**
	 * 修改者 : updater
	 * 
	 * @return
	 */
	public Integer getUpdater() {
		return updater;
	}

	/**
	 * 修改者 : updater
	 * 
	 * @return
	 */
	public void setUpdater(Integer updater) {
		this.updater = updater;
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
	 * 修改时间 : update_time
	 * 
	 * @return
	 */
	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * 修改时间 : update_time
	 * 
	 * @return
	 */
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 创建时间 : create_time
	 * 
	 * @return
	 */
	public java.util.Date getCreateTime() {
		return createTime;
	}

	/**
	 * 创建时间 : create_time
	 * 
	 * @return
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * : token
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * : token
	 * 
	 * @return
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * IP地址 : ip
	 * 
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * IP地址 : ip
	 * 
	 * @return
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 最近登录时间 : last_login_time
	 * 
	 * @return
	 */
	public java.util.Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * 最近登录时间 : last_login_time
	 * 
	 * @return
	 */
	public void setLastLoginTime(java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 版本号 : version
	 * 
	 * @return
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * 版本号 : version
	 * 
	 * @return
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * 头像图片地址 : hedehref
	 * 
	 * @return
	 */
	public String getHedehref() {
		return hedehref;
	}

	/**
	 * 头像图片地址 : hedehref
	 * 
	 * @return
	 */
	public void setHedehref(String hedehref) {
		this.hedehref = hedehref;
	}

}
