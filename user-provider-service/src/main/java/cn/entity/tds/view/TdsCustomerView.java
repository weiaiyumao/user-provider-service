package cn.entity.tds.view;

import java.io.Serializable;
import java.util.Date;

import main.java.cn.domain.page.BasePageParam;

/**
 * 客户列表视图
 * 
 * @author ChuangLan
 *
 */
public class TdsCustomerView extends BasePageParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2880707780947168367L;
	
	private Integer userId; // 客户id

	private String userName; // 客户名称

	private Integer departId; // 部门id

	private String departName; // 部门名称

	private String contact;

	private String phone;

	private Date create_time;

	private String comUrl;

	private String source; // 来源
	
	private String comName;

	// 最近充值时间 : last_money_time
	private java.util.Date lastMoneyTime;

	// 累积充值金额 : sum_money
	private String sumMoney;

	// 累积返佣金 : sum_commission
	private String sumCommission;
	
	
	public String getComName() {
		return comName;
	}
	
	public void setComName(String comName) {
		this.comName = comName;
	}

	public java.util.Date getLastMoneyTime() {
		return lastMoneyTime;
	}

	public void setLastMoneyTime(java.util.Date lastMoneyTime) {
		this.lastMoneyTime = lastMoneyTime;
	}

	public String getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(String sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getSumCommission() {
		return sumCommission;
	}

	public void setSumCommission(String sumCommission) {
		this.sumCommission = sumCommission;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDepartId() {
		return departId;
	}

	public void setDepartId(Integer departId) {
		this.departId = departId;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

    public String getComUrl() {
		return comUrl;
	}
    
    public void setComUrl(String comUrl) {
		this.comUrl = comUrl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
