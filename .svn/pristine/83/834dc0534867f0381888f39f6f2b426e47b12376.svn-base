package com.cbt.entity;

import java.io.Serializable;
/**
* @ClassName: ClientOrder 
* @Description: 客户历史订单
 */
public class InvoiceInfo implements Serializable{
 
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	  private Integer id;
	  private String userid;    //客户id
	  private String salesId;   //销售id
	  private String orderId;   //订单编号
      private String invoiceId; //发票编号
	  private Integer amountUnit; //金额单元(关联amount_unit id)
	  private Double amount;      //发票金额
      private Double productPrice; //产品价格
      private Double moldPrice;    //模板价格
      private Double shippingFee;   //运费
      private Integer transactionType; //交易类型
      private String createTime;  //信息更新时间
	  private String updateTime;  //信息更新时间
	  private String invoicePath; //Invoice下载路径
	  private Double otherPrice;  //其他金额
	  private String comment;     //备注说明    
	  private String factoryId;   //工厂id
	  private Integer invoiceRemarkId;  //付款银行备注表Id
	  
	  //虚拟字段
	  private double sumAmountActual;  //实际付款总金额
	  private double amountActual;     //最近付款金额
	  private String paymentTime;   //最近付款时间
	  
    
	  
	  
	public double getAmountActual() {
		return amountActual;
	}
	public void setAmountActual(double amountActual) {
		this.amountActual = amountActual;
	}
	public double getSumAmountActual() {
		return sumAmountActual;
	}
	public void setSumAmountActual(double sumAmountActual) {
		this.sumAmountActual = sumAmountActual;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getInvoiceRemarkId() {
		return invoiceRemarkId;
	}
	public void setInvoiceRemarkId(Integer invoiceRemarkId) {
		this.invoiceRemarkId = invoiceRemarkId;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public Double getMoldPrice() {
		return moldPrice;
	}
	public void setMoldPrice(Double moldPrice) {
		this.moldPrice = moldPrice;
	}
	public Double getShippingFee() {
		return shippingFee;
	}
	public void setShippingFee(Double shippingFee) {
		this.shippingFee = shippingFee;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
		
	}
	public String getInvoicePath() {
		return invoicePath;
	}
	public void setInvoicePath(String invoicePath) {
		this.invoicePath = invoicePath;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getAmountUnit() {
		return amountUnit;
	}
	public void setAmountUnit(Integer amountUnit) {
		this.amountUnit = amountUnit;
	}

	
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Double getOtherPrice() {
		return otherPrice;
	}
	public void setOtherPrice(Double otherPrice) {
		this.otherPrice = otherPrice;
	}
	public Integer getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(Integer transactionType) {
		this.transactionType = transactionType;
	}

	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
			this.updateTime = updateTime;
		
	}
	@Override
	public String toString() {
		return "InvoiceInfo [id=" + id + ", userid=" + userid + ", salesId="
				+ salesId + ", orderId=" + orderId + ", invoiceId=" + invoiceId
				+ ", amountUnit=" + amountUnit + ", amount=" + amount
				+ ", productPrice=" + productPrice + ", moldPrice=" + moldPrice
				+ ", shippingFee=" + shippingFee + ", transactionType="
				+ transactionType + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", invoicePath=" + invoicePath
				+ ", otherPrice=" + otherPrice + ", comment=" + comment
				+ ", factoryId=" + factoryId + ", invoiceRemarkId="
				+ invoiceRemarkId + ", sumAmountActual=" + sumAmountActual
				+ ", amountActual=" + amountActual + ", maxPaymentTime="
				+ paymentTime + "]";
	}

	

	
	  

	
	

	
	
	
}
