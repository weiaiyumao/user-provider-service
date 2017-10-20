package cn.entity;

import java.io.Serializable;
import java.util.Date;

public class MessageInfo implements Serializable{

	private static final long serialVersionUID = 119828393867864778L;
	private String moblie;	
	private String delivrd;
	private Date reportTime;
	public String getMoblie() {
		return moblie;
	}
	public void setMoblie(String moblie) {
		this.moblie = moblie;
	}
	public String getDelivrd() {
		return delivrd;
	}
	public void setDelivrd(String delivrd) {
		this.delivrd = delivrd;
	}
	public Date getReportTime() {
		return reportTime;
	}
	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}

	

}
