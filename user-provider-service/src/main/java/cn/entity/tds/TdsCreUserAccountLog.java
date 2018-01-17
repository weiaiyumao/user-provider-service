package cn.entity.tds;

import java.io.Serializable;

/**
 * : tds_cre_user_account_log
 * 
 * 
 * @author Gen
 */
public class TdsCreUserAccountLog implements Serializable{
	
	private static final long serialVersionUID = -2440941442463595629L;

	//主键id : id 	
	private Integer id; 
	
	//用户id : cre_user_id 	
	private Integer creUserId; 
	
	//调整的条数 : number 	
	private Integer number; 
	
	//调整的账户 1：空号检测 2空号API 3账户二次清洗 : type 	
	private String type; 
	
	//操作人id : creater 	
	private String creater; 
	
	//创建时间 : create_time 	
	private java.util.Date createTime; 
	

	/**
	 * 主键id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * 主键id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 用户id : cre_user_id
	 * 
	 * @return 
	 */
	public Integer getCreUserId () {
		return creUserId;
	}
	
	/**
	 * 用户id : cre_user_id
	 * 
	 * @return 
	 */
	public void setCreUserId (Integer creUserId) {
		this.creUserId = creUserId;
	}
	/**
	 * 调整的条数 : number
	 * 
	 * @return 
	 */
	public Integer getNumber () {
		return number;
	}
	
	/**
	 * 调整的条数 : number
	 * 
	 * @return 
	 */
	public void setNumber (Integer number) {
		this.number = number;
	}
	/**
	 * 调整的账户 1：空号检测 2空号API 3账户二次清洗 : type
	 * 
	 * @return 
	 */
	public String getType () {
		return type;
	}
	
	/**
	 * 调整的账户 1：空号检测 2空号API 3账户二次清洗 : type
	 * 
	 * @return 
	 */
	public void setType (String type) {
		this.type = type;
	}
	/**
	 * 操作人id : creater
	 * 
	 * @return 
	 */
	public String getCreater () {
		return creater;
	}
	
	/**
	 * 操作人id : creater
	 * 
	 * @return 
	 */
	public void setCreater (String creater) {
		this.creater = creater;
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


}
