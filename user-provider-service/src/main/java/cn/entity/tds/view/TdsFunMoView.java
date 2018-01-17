package cn.entity.tds.view;

import java.io.Serializable;

import main.java.cn.domain.page.BasePageParam;

/**
 * 功能列表展示视图
 * 
 * @author ChuangLan
 *
 */
public class TdsFunMoView  extends BasePageParam implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2124306109717356908L;


	private Integer id;
	
	private String funName;
	
	private String url;
	
	private String moName;
	
	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMoName() {
		return moName;
	}

	public void setMoName(String moName) {
		this.moName = moName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	

}
