package cn.entity.tds;

import java.io.Serializable;
import java.util.Date;

/**
 * : 客服审核操作记录表
 * 
 * 
 * @author Gen
 */
public class TdsApprovalLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4889205320779499052L;

	//审核id : id 	
	private Integer id; 
	
	//用户id : user_id 	
	private Integer userId; 
	
	//审核状态; 1:同意申请 0:驳回注册 : app_status 	
	private String appStatus; 
	
	//操作驳回注册原因 : app_remarks 	
	private String appRemarks; 
	
	//删除状态：0正常，1已删 : is_deleted 	
	private String isDeleted; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	
	//备注1 : spare1 	
	private String spare1; 
	

	/**
	 * 审核id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 审核id : id
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
	 * 审核状态; 1:同意申请 0:驳回注册 : app_status
	 * 
	 * @return 
	 */
	public String getAppStatus () {
		return appStatus;
	}
	
	/**
	 * 审核状态; 1:同意申请 0:驳回注册 : app_status
	 * 
	 * @return 
	 */
	public void setAppStatus (String appStatus) {
		this.appStatus = appStatus;
	}
	/**
	 * 操作驳回注册原因 : app_remarks
	 * 
	 * @return 
	 */
	public String getAppRemarks () {
		return appRemarks;
	}
	
	/**
	 * 操作驳回注册原因 : app_remarks
	 * 
	 * @return 
	 */
	public void setAppRemarks (String appRemarks) {
		this.appRemarks = appRemarks;
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
	 * 
	 * @param userId 用户
	 * @param appStatus  xx
	 * @param appRemarks  原因
	 * @param createTime  时间
	 * @param spare1  备注
	 */
	public TdsApprovalLog(Integer userId, String appStatus, String appRemarks, Date createTime,String spare1) {
		this.userId = userId;
		this.appStatus = appStatus;
		this.appRemarks = appRemarks;
		this.createTime = createTime;
		this.spare1=spare1;
	}

   public TdsApprovalLog(){
	   
   }
  
}
