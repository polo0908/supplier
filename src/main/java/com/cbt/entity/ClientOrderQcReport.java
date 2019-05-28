package com.cbt.entity;

import java.io.Serializable;

public class ClientOrderQcReport implements Serializable {

	
	/**
	 * @author polo
	 * 2017年8月3日
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private Integer id;    
	  private String orderId;               //订单号
	  private String qcReportPath;          //qc报告下载路径
	  private String uploadDate;            //更新时间
	  private String oldOrNewQc;            //QC是否点击下载
	  
	  
	  
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getQcReportPath() {
		return qcReportPath;
	}
	public void setQcReportPath(String qcReportPath) {
		this.qcReportPath = qcReportPath;
	}

	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getOldOrNewQc() {
		return oldOrNewQc;
	}
	public void setOldOrNewQc(String oldOrNewQc) {
		this.oldOrNewQc = oldOrNewQc;
	}
	@Override
	public String toString() {
		return "ClientOrderQcReport [id=" + id + ", orderId=" + orderId
				+ ", qcReportPath=" + qcReportPath + ", uploadDate="
				+ uploadDate + ", oldOrNewQc=" + oldOrNewQc + "]";
	}
	  
	  
	  
}
