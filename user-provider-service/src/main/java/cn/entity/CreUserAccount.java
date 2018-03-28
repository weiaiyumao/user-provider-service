package cn.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户账户表
 * @author ChuangLan
 *
 */
public class CreUserAccount implements Serializable{
	
	private static final long serialVersionUID = 6056767184016299480L;

	private Integer id;
	
	private Integer creUserId;
	
	private Integer account;
	
	private Integer apiAccount;

	private Integer rqAccount;
	
	private String deleteStatus;
	
	private Integer version;
	
	private Date createTime;
	
	private Date updateTime;
	
	
	private String type;
	
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreUserId() {
		return creUserId;
	}

	public void setCreUserId(Integer creUserId) {
		this.creUserId = creUserId;
	}

	public Integer getAccount() {
		return account;
	}

	public void setAccount(Integer account) {
		this.account = account;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getApiAccount() {
		return apiAccount;
	}

	public void setApiAccount(Integer apiAccount) {
		this.apiAccount = apiAccount;
	}

	public Integer getRqAccount() {
		return rqAccount;
	}

	public void setRqAccount(Integer rqAccount) {
		this.rqAccount = rqAccount;
	}
}
