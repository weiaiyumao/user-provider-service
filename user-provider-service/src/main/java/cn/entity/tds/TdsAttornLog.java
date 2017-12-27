package cn.entity.tds;

import java.io.Serializable;

/**
 * : 客户列表转让实体
 * 
 * 
 * @author Gen
 */
public class TdsAttornLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4640389429027046920L;

	//转让id : id 	
	private Integer id; 
	
	//用户id : user_id 	
	private Integer userId; 
	
	//操作驳回注册原因 : att_remarks 	
	private String attRemarks; 
	
	//删除状态：0正常，1已删 : is_deleted 	
	private String isDeleted; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//备注1 : spare1 	
	private String spare1; 
	
	//转让给-用户id : attorn_user_id 	
	private Integer attornUserId; 
	
	//转让给-用户姓名 : attorn_user_name 	
	private String attornUserName; 
	

	/**
	 * 转让id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 转让id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 用户id : user_id
	 * 
	 * @return 
	 */
	public Integer getUserId () {
		return userId;
	}
	
	/**
	 * 用户id : user_id
	 * 
	 * @return 
	 */
	public void setUserId (Integer userId) {
		this.userId = userId;
	}
	/**
	 * 操作驳回注册原因 : att_remarks
	 * 
	 * @return 
	 */
	public String getAttRemarks () {
		return attRemarks;
	}
	
	/**
	 * 操作驳回注册原因 : att_remarks
	 * 
	 * @return 
	 */
	public void setAttRemarks (String attRemarks) {
		this.attRemarks = attRemarks;
	}
	/**
	 * 删除状态：0正常，1已删 : is_deleted
	 * 
	 * @return 
	 */
	public String getIsDeleted () {
		return isDeleted;
	}
	
	/**
	 * 删除状态：0正常，1已删 : is_deleted
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
	 * 备注1 : spare1
	 * 
	 * @return 
	 */
	public String getSpare1 () {
		return spare1;
	}
	
	/**
	 * 备注1 : spare1
	 * 
	 * @return 
	 */
	public void setSpare1 (String spare1) {
		this.spare1 = spare1;
	}
	/**
	 * 转让给-用户id : attorn_user_id
	 * 
	 * @return 
	 */
	public Integer getAttornUserId () {
		return attornUserId;
	}
	
	/**
	 * 转让给-用户id : attorn_user_id
	 * 
	 * @return 
	 */
	public void setAttornUserId (Integer attornUserId) {
		this.attornUserId = attornUserId;
	}
	/**
	 * 转让给-用户姓名 : attorn_user_name
	 * 
	 * @return 
	 */
	public String getAttornUserName () {
		return attornUserName;
	}
	
	/**
	 * 转让给-用户姓名 : attorn_user_name
	 * 
	 * @return 
	 */
	public void setAttornUserName (String attornUserName) {
		this.attornUserName = attornUserName;
	}


  
}
