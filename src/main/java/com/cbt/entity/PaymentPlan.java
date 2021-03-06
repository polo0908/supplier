package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

public class PaymentPlan implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	  private String orderId;       //订单编号
	  private String invoiceId;     //发票编号
      private Integer paymentBank; //付款银行
	  private String paymentTime;  //实际付款时间
	  private Double amount;       //付款金额
	  private Double amountActual; //实际到账金额  
	  private String predictPaymentTime;//预计付款时间
	
	  private Integer erpInvoiceId;  //erp invoice id
	  
	  
	  
	public Integer getErpInvoiceId() {
		return erpInvoiceId;
	}
	public void setErpInvoiceId(Integer erpInvoiceId) {
		this.erpInvoiceId = erpInvoiceId;
	}
	public String getPredictPaymentTime() {
		return predictPaymentTime;
	}
	public void setPredictPaymentTime(String predictPaymentTime) {
		this.predictPaymentTime = predictPaymentTime;
	}
	public Double getAmountActual() {
		return amountActual;
	}
	public void setAmountActual(Double amountActual) {
		this.amountActual = amountActual;
	}

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getPaymentBank() {
		return paymentBank;
	}
	public void setPaymentBank(Integer paymentBank) {
		this.paymentBank = paymentBank;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		try {
			this.paymentTime = DateFormat.formatDate1(paymentTime);
		} catch (ParseException e) {
			e.printStackTrace();
			this.paymentTime = paymentTime;
		}
		
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "PaymentPlan [id=" + id + ", orderId=" + orderId
				+ ", invoiceId=" + invoiceId + ", paymentBank=" + paymentBank
				+ ", paymentTime=" + paymentTime + ", amount=" + amount
				+ ", amountActual=" + amountActual + ", predictPaymentTime="
				+ predictPaymentTime + ", erpInvoiceId=" + erpInvoiceId + "]";
	}

	  
	
	  
	  
}
