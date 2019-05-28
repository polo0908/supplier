package com.cbt.entity;

import java.io.Serializable;

public class MessageCenter implements Serializable {
  
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String  orderId;             //订单id
	private String  factoryId;           //工厂id
	private String  userid;              //客户id
	private String  messageTitle;        //消息标题
	private Integer messageType;         //消息类型（1、交期消息 2、质量问题消息 3、其他消息 4、报价留言消息）
	private Integer quotationInfoId;     //报价单id
	    
	private String maxSendTime;          //最近发送时间
    private String quotationId;          //报价单号

	
	

	public String getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	public String getMaxSendTime() {
		return maxSendTime;
	}
	public Integer getQuotationInfoId() {
		return quotationInfoId;
	}
	public void setQuotationInfoId(Integer quotationInfoId) {
		this.quotationInfoId = quotationInfoId;
	}
	public void setMaxSendTime(String maxSendTime) {
		this.maxSendTime = maxSendTime;
	}
	public Integer getMessageType() {
		return messageType;
	}
	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
@Override
public String toString() {
	return "MessageCenter [id=" + id + ", orderId=" + orderId + ", factoryId="
			+ factoryId + ", userid=" + userid + ", messageTitle="
			+ messageTitle + ", messageType=" + messageType
			+ ", quotationInfoId=" + quotationInfoId + ", maxSendTime="
			+ maxSendTime + ", quotationId=" + quotationId + "]";
}
	
	
	
}
