package cn.entity.tds;

import java.io.Serializable;

/**
 * : 价格管理表
 * 
 * 
 * @author Gen
 */
public class TdsProductMoney implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5425447206263652412L;

	//id : id 	
	private Integer id; 
	
	//产品关联id : product_id 	
	private Integer productId; 
	
	//起充量金额(元) : start_money 	
	private Integer startMoney; 
	
	//售价每条价格 : number_money 	
	private String numberMoney; 
	
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
	
	// : spare1 	
	private String spare1; 
	
	// : spare2 	
	private String spare2; 
	

	/**
	 * id : id
	 * 
	 * @return 
	 */
	public Integer getId () {
		return id;
	}
	
	/**
	 * id : id
	 * 
	 * @return 
	 */
	public void setId (Integer id) {
		this.id = id;
	}
	/**
	 * 产品关联id : product_id
	 * 
	 * @return 
	 */
	public Integer getProductId () {
		return productId;
	}
	
	/**
	 * 产品关联id : product_id
	 * 
	 * @return 
	 */
	public void setProductId (Integer productId) {
		this.productId = productId;
	}
	/**
	 * 起充量金额(元) : start_money
	 * 
	 * @return 
	 */
	public Integer getStartMoney () {
		return startMoney;
	}
	
	/**
	 * 起充量金额(元) : start_money
	 * 
	 * @return 
	 */
	public void setStartMoney (Integer startMoney) {
		this.startMoney = startMoney;
	}
	/**
	 * 售价每条价格 : number_money
	 * 
	 * @return 
	 */
	public String getNumberMoney () {
		return numberMoney;
	}
	
	/**
	 * 售价每条价格 : number_money
	 * 
	 * @return 
	 */
	public void setNumberMoney (String numberMoney) {
		this.numberMoney = numberMoney;
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
	/**
	 *  : spare1
	 * 
	 * @return 
	 */
	public String getSpare1 () {
		return spare1;
	}
	
	/**
	 *  : spare1
	 * 
	 * @return 
	 */
	public void setSpare1 (String spare1) {
		this.spare1 = spare1;
	}
	/**
	 *  : spare2
	 * 
	 * @return 
	 */
	public String getSpare2 () {
		return spare2;
	}
	
	/**
	 *  : spare2
	 * 
	 * @return 
	 */
	public void setSpare2 (String spare2) {
		this.spare2 = spare2;
	}


  
}
