package com.cbt.entity;

import java.io.Serializable;

public class ClientOrderPo implements Serializable {

	
	/**
	 * @author polo
	 * 2017年9月1日
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private Integer id;    
	  private String orderId;               //订单号
	  private String poPath;                //po报告下载路径
	  private String uploadDate;            //更新时间
	  private String oldOrNewPo;            //PO是否点击下载
	  
	  
	  
	  
	  
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
	public String getPoPath() {
		return poPath;
	}
	public void setPoPath(String poPath) {
		this.poPath = poPath;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getOldOrNewPo() {
		return oldOrNewPo;
	}
	public void setOldOrNewPo(String oldOrNewPo) {
		this.oldOrNewPo = oldOrNewPo;
	}
	@Override
	public String toString() {
		return "ClientOrderPo [id=" + id + ", orderId=" + orderId + ", poPath="
				+ poPath + ", uploadDate=" + uploadDate + ", oldOrNewPo="
				+ oldOrNewPo + "]";
	}
	  
	  
	  
	
	  
}
