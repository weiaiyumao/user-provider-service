package cn.entity.tds;

import java.io.Serializable;

/**
 * :状态库实体
 * 
 * 
 * @author Gen
 */
public class TdsStateInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 452809758580524306L;

	//状态库信息详细表 : id 	
	private Integer id; 
	
	//用户id : userid 	
	private Integer userid; 
	
	//状态名 : sname 	
	private String sname; 
	
	//状态库id_运营商 : state_ope 	
	private String stateOpe; 
	
	//状态库id_属性 : state_pro 	
	private String statePro; 
	
	//录入人 : rinput 	
	private String rinput; 
	
	//创建者 : creater 	
	private Integer creater; 
	
	//创建时间_录入时间 : create_time 	
	private java.util.Date createTime; 
	
	//修改时间 : update_time 	
	private java.util.Date updateTime; 
	
	//修改者 : updater 	
	private Integer updater; 
	
	//删除状态：0正常，1已删除 : is_deleted 	
	private String isDeleted; 
	
	//备注 : remarks 	
	private String remarks; 
	

	/**
	 * 状态库信息详细表 : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 状态库信息详细表 : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
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
	 * 状态名 : sname
	 * 
	 * @return 
	 */
	public String getSname () {
		return sname;
	}
	
	/**
	 * 状态名 : sname
	 * 
	 * @return 
	 */
	public void setSname (String sname) {
		this.sname = sname;
	}
	/**
	 * 状态库id_运营商 : state_ope
	 * 
	 * @return 
	 */
	public String getStateOpe () {
		return stateOpe;
	}
	
	/**
	 * 状态库id_运营商 : state_ope
	 * 
	 * @return 
	 */
	public void setStateOpe (String stateOpe) {
		this.stateOpe = stateOpe;
	}
	/**
	 * 状态库id_属性 : state_pro
	 * 
	 * @return 
	 */
	public String getStatePro () {
		return statePro;
	}
	
	/**
	 * 状态库id_属性 : state_pro
	 * 
	 * @return 
	 */
	public void setStatePro (String statePro) {
		this.statePro = statePro;
	}
	/**
	 * 录入人 : rinput
	 * 
	 * @return 
	 */
	public String getRinput () {
		return rinput;
	}
	
	/**
	 * 录入人 : rinput
	 * 
	 * @return 
	 */
	public void setRinput (String rinput) {
		this.rinput = rinput;
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
	 * 创建时间_录入时间 : create_time
	 * 
	 * @return 
	 */
	public java.util.Date getCreateTime () {
		return createTime;
	}
	
	/**
	 * 创建时间_录入时间 : create_time
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


  
}
