package com.cbt.entity;

import java.io.Serializable;

public class InvoiceRemark implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;                  
	private String factortId;       //工厂ID
	private String remark;          //发票银行信息备注
	private String remark1;         //发票备注
	private String remarkBank;      //银行
	
	
	public String getRemarkBank() {
		return remarkBank;
	}
	public void setRemarkBank(String remarkBank) {
		this.remarkBank = remarkBank;
	}
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFactortId() {
		return factortId;
	}
	public void setFactortId(String factortId) {
		this.factortId = factortId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "InvoiceRemark [id=" + id + ", factortId=" + factortId
				+ ", remark=" + remark + ", remark1=" + remark1
				+ ", remarkBank=" + remarkBank + "]";
	}
	
	
	
	
}
