package cn.entity.tds;

import java.io.Serializable;

/**
 * : tds_enum
 * 
 * 
 * @author Gen
 */
public class TdsEnum implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -441506941726029464L;

	//状态id : id 	
	private Integer id; 
	
	//状态名 : sl_name 	
	private String slName; 
	
	//代码 : type_code 	
	private String typeCode; 
	
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
	
	
	private String fieldValue;
	
	
	public String getFieldValue() {
		return fieldValue;
	}
	
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * 状态id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 状态id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 状态名 : sl_name
	 * 
	 * @return 
	 */
	public String getSlName () {
		return slName;
	}
	
	/**
	 * 状态名 : sl_name
	 * 
	 * @return 
	 */
	public void setSlName (String slName) {
		this.slName = slName;
	}
	/**
	 * 代码 : type_code
	 * 
	 * @return 
	 */
	public String getTypeCode () {
		return typeCode;
	}
	
	/**
	 * 代码 : type_code
	 * 
	 * @return 
	 */
	public void setTypeCode (String typeCode) {
		this.typeCode = typeCode;
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


  
}
