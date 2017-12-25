package cn.entity.tds;

import java.io.Serializable;

/**
 * : 用户表
 * 
 * 
 * @author Gen
 */
public class TdsUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4341215329087743136L;

	//用户id : id 	
	private Integer id; 
	
	//公司id : com_id 	
	private Integer comId; 
	
	//名称，昵称 : name 	
	private String name; 
	
	//联系人 : contact 	
	private String contact; 
	
	//手机号码 : phone 	
	private String phone; 
	
	//密码 : password 	
	private String password; 
	
	//邀请码 : invitedcode 	
	private String invitedcode; 
	
	//备注 : remarks 	
	private String remarks; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//修改者 : updater 	
	private Integer updater; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	
	//修改时间 : update_time 	
	private java.util.Date updateTime; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	// : token 	
	private String token; 
	
	//IP地址 : login_ip 	
	private String loginIp; 
	
	//最近登录时间 : last_login_time 	
	private java.util.Date lastLoginTime; 
	
	//版本号 : version 	
	private Integer version; 
	
	//头像图片地址 : hedehref 	
	private String hedehref; 
	
	//来源 : source 	
	private String source; 
	
	//账号状态 0：正常 1：禁用 : status 	
	private String status; 
	
	//注册来源 0:用户注册添加  1：管理员添加 : register_source 	
	private String registerSource; 
	
 
	/**
	 * 用户id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 用户id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 公司id : com_id
	 * 
	 * @return 
	 */
	public Integer getComId () {
		return comId;
	}
	
	/**
	 * 公司id : com_id
	 * 
	 * @return 
	 */
	public void setComId (Integer comId) {
		this.comId = comId;
	}
	/**
	 * 名称，昵称 : name
	 * 
	 * @return 
	 */
	public String getName () {
		return name;
	}
	
	/**
	 * 名称，昵称 : name
	 * 
	 * @return 
	 */
	public void setName (String name) {
		this.name = name;
	}
	/**
	 * 联系人 : contact
	 * 
	 * @return 
	 */
	public String getContact () {
		return contact;
	}
	
	/**
	 * 联系人 : contact
	 * 
	 * @return 
	 */
	public void setContact (String contact) {
		this.contact = contact;
	}
	/**
	 * 手机号码 : phone
	 * 
	 * @return 
	 */
	public String getPhone () {
		return phone;
	}
	
	/**
	 * 手机号码 : phone
	 * 
	 * @return 
	 */
	public void setPhone (String phone) {
		this.phone = phone;
	}
	/**
	 * 密码 : password
	 * 
	 * @return 
	 */
	public String getPassword () {
		return password;
	}
	
	/**
	 * 密码 : password
	 * 
	 * @return 
	 */
	public void setPassword (String password) {
		this.password = password;
	}
	/**
	 * 邀请码 : invitedcode
	 * 
	 * @return 
	 */
	public String getInvitedcode () {
		return invitedcode;
	}
	
	/**
	 * 邀请码 : invitedcode
	 * 
	 * @return 
	 */
	public void setInvitedcode (String invitedcode) {
		this.invitedcode = invitedcode;
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
	 *  : token
	 * 
	 * @return 
	 */
	public String getToken () {
		return token;
	}
	
	/**
	 *  : token
	 * 
	 * @return 
	 */
	public void setToken (String token) {
		this.token = token;
	}
	/**
	 * IP地址 : login_ip
	 * 
	 * @return 
	 */
	public String getLoginIp () {
		return loginIp;
	}
	
	/**
	 * IP地址 : login_ip
	 * 
	 * @return 
	 */
	public void setLoginIp (String loginIp) {
		this.loginIp = loginIp;
	}
	/**
	 * 最近登录时间 : last_login_time
	 * 
	 * @return 
	 */
	public java.util.Date getLastLoginTime () {
		return lastLoginTime;
	}
	
	/**
	 * 最近登录时间 : last_login_time
	 * 
	 * @return 
	 */
	public void setLastLoginTime (java.util.Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 版本号 : version
	 * 
	 * @return 
	 */
	public Integer getVersion () {
		return version;
	}
	
	/**
	 * 版本号 : version
	 * 
	 * @return 
	 */
	public void setVersion (Integer version) {
		this.version = version;
	}
	/**
	 * 头像图片地址 : hedehref
	 * 
	 * @return 
	 */
	public String getHedehref () {
		return hedehref;
	}
	
	/**
	 * 头像图片地址 : hedehref
	 * 
	 * @return 
	 */
	public void setHedehref (String hedehref) {
		this.hedehref = hedehref;
	}
	/**
	 * 来源 : source
	 * 
	 * @return 
	 */
	public String getSource () {
		return source;
	}
	
	/**
	 * 来源 : source
	 * 
	 * @return 
	 */
	public void setSource (String source) {
		this.source = source;
	}
	/**
	 * 账号状态 0：正常 1：禁用 : status
	 * 
	 * @return 
	 */
	public String getStatus () {
		return status;
	}
	
	/**
	 * 账号状态 0：正常 1：禁用 : status
	 * 
	 * @return 
	 */
	public void setStatus (String status) {
		this.status = status;
	}
	/**
	 * 注册来源 0:用户注册添加  1：管理员添加 : register_source
	 * 
	 * @return 
	 */
	public String getRegisterSource () {
		return registerSource;
	}
	
	/**
	 * 注册来源 0:用户注册添加  1：管理员添加 : register_source
	 * 
	 * @return 
	 */
	public void setRegisterSource (String registerSource) {
		this.registerSource = registerSource;
	}


  
}
