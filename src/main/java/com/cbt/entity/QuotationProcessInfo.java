package com.cbt.entity;

import java.io.Serializable;
/**
 * 报价单工艺信息
 * @author polo
 * @time 2017.8.22
 */
public class QuotationProcessInfo implements Serializable {

	private static final long serialVersionUID = -8570984871157528433L;

	private Integer id;//报价商品id
	
	private Integer quotationPriceId;//报价价格表id
	
	private String processName;    //工艺名

	private Double quantity;       //工艺量
	
	private Double processPrice;  //工艺价格

	private Double processFactoryPrice;  //工艺初始价格
	
	
	
	public Double getProcessFactoryPrice() {
		return processFactoryPrice;
	}

	public void setProcessFactoryPrice(Double processFactoryPrice) {
		this.processFactoryPrice = processFactoryPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuotationPriceId() {
		return quotationPriceId;
	}

	public void setQuotationPriceId(Integer quotationPriceId) {
		this.quotationPriceId = quotationPriceId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getProcessPrice() {
		return processPrice;
	}

	public void setProcessPrice(Double processPrice) {
		this.processPrice = processPrice;
	}

	@Override
	public String toString() {
		return "QuotationProcessInfo [id=" + id + ", quotationPriceId="
				+ quotationPriceId + ", processName=" + processName
				+ ", quantity=" + quantity + ", processPrice=" + processPrice
				+ ", processFactoryPrice=" + processFactoryPrice + "]";
	}
	
	
	
}
