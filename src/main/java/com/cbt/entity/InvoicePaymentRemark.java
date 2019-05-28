package com.cbt.entity;

import java.io.Serializable;

public class InvoicePaymentRemark implements Serializable {
      
	private Integer id;
	private String paymentRemark;     //付款方式
	private String factoryId;         //工厂ID
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPaymentRemark() {
		return paymentRemark;
	}
	public void setPaymentRemark(String paymentRemark) {
		this.paymentRemark = paymentRemark;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	@Override
	public String toString() {
		return "InvoicePaymentRemark [id=" + id + ", paymentRemark="
				+ paymentRemark + ", factoryId=" + factoryId + "]";
	}
	
	
	
}
