package cn.entity.tds;

import java.io.Serializable;

/**
 * : 出账审核
 * 
 * 
 * @author liuh
 */
public class TdsCarryOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5393461990832266203L;

	//提现id : id 	
	private Integer id; 
	
	//用户名称	
	private Integer userName; 
	
	//提现流水 : carry_serial 	
	private String carrySerial; 
	
	//提现订单 : carry_order 	
	private String carryOrder; 
	
	//提现方式  1对公转账银行卡 2支付宝 : carry_type 	
	private String carryType; 
	
	//提现方式账号 : carry_type_name 	
	private String carryTypeName; 
	
	//提现金额 : carr_money 	
	private String carrMoney; 
	
	//提现状态  1处理中 2已到账 3被驳回 : carr_status 	
	private String carrStatus; 
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserName() {
		return userName;
	}

	public void setUserName(Integer userName) {
		this.userName = userName;
	}

	public String getCarrySerial() {
		return carrySerial;
	}

	public void setCarrySerial(String carrySerial) {
		this.carrySerial = carrySerial;
	}

	public String getCarryOrder() {
		return carryOrder;
	}

	public void setCarryOrder(String carryOrder) {
		this.carryOrder = carryOrder;
	}

	public String getCarryType() {
		return carryType;
	}

	public void setCarryType(String carryType) {
		this.carryType = carryType;
	}

	public String getCarryTypeName() {
		return carryTypeName;
	}

	public void setCarryTypeName(String carryTypeName) {
		this.carryTypeName = carryTypeName;
	}

	public String getCarrMoney() {
		return carrMoney;
	}

	public void setCarrMoney(String carrMoney) {
		this.carrMoney = carrMoney;
	}

	public String getCarrStatus() {
		return carrStatus;
	}

	public void setCarrStatus(String carrStatus) {
		this.carrStatus = carrStatus;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreater() {
		return creater;
	}

	public void setCreater(Integer creater) {
		this.creater = creater;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	} 
	

	

}

