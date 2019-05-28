package com.cbt.entity;

import java.io.Serializable;
import java.util.List;

public class OrderMessage implements Serializable {
	
	
   private Integer id;
   private Integer messageCenterId;             //客户消息中心ID
   private String  messageDetails;              //订单消息回复消息内容
   private String  messageSendTime;             //订单消息回复时间
   private Integer picStatus;                   //是否有图片(0.不存在 1.存在)
   private Integer customerOrFactory;           //客户消息还是工厂回复（1、客户 2、工厂）
   private String  factoryId;                   //工厂ID
   private String  salesId;                     //销售ID
   private Integer readStatus;                  //未读：0   已读：1
   private String  messageReadTime;             //消息阅读时间
   private Integer targetPriceReply;            //是否是目标价回复 0：no 1:yes 
   private String  attachmentPath;              //消息附件地址
   
   
   private String username;                     //销售名
   private List<QualityIssuesPic> qualityIssuesPic;      //图片信息
   private String loginEmail;                            //登录邮箱
   
   
public Integer getTargetPriceReply() {
	return targetPriceReply;
}
public void setTargetPriceReply(Integer targetPriceReply) {
	this.targetPriceReply = targetPriceReply;
}
public String getAttachmentPath() {
	return attachmentPath;
}
public void setAttachmentPath(String attachmentPath) {
	this.attachmentPath = attachmentPath;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public List<QualityIssuesPic> getQualityIssuesPic() {
	return qualityIssuesPic;
}
public void setQualityIssuesPic(List<QualityIssuesPic> qualityIssuesPic) {
	this.qualityIssuesPic = qualityIssuesPic;
}
public String getMessageReadTime() {
	return messageReadTime;
}
public void setMessageReadTime(String messageReadTime) {
	this.messageReadTime = messageReadTime;
}
public Integer getReadStatus() {
	return readStatus;
}
public void setReadStatus(Integer readStatus) {
	this.readStatus = readStatus;
}
public String getFactoryId() {
	return factoryId;
}
public void setFactoryId(String factoryId) {
	this.factoryId = factoryId;
}
public String getSalesId() {
	return salesId;
}
public void setSalesId(String salesId) {
	this.salesId = salesId;
}

public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public Integer getMessageCenterId() {
	return messageCenterId;
}
public void setMessageCenterId(Integer messageCenterId) {
	this.messageCenterId = messageCenterId;
}
public String getMessageDetails() {
	return messageDetails;
}
public void setMessageDetails(String messageDetails) {
	this.messageDetails = messageDetails;
}
public String getMessageSendTime() {
	return messageSendTime;
}
public void setMessageSendTime(String messageSendTime) {
	this.messageSendTime = messageSendTime;
}
public Integer getPicStatus() {
	return picStatus;
}
public void setPicStatus(Integer picStatus) {
	this.picStatus = picStatus;
}

public Integer getCustomerOrFactory() {
	return customerOrFactory;
}
public void setCustomerOrFactory(Integer customerOrFactory) {
	this.customerOrFactory = customerOrFactory;
}
public String getLoginEmail() {
	return loginEmail;
}
public void setLoginEmail(String loginEmail) {
	this.loginEmail = loginEmail;
}
@Override
public String toString() {
	return "OrderMessage [id=" + id + ", messageCenterId=" + messageCenterId
			+ ", messageDetails=" + messageDetails + ", messageSendTime="
			+ messageSendTime + ", picStatus=" + picStatus
			+ ", customerOrFactory=" + customerOrFactory + ", factoryId="
			+ factoryId + ", salesId=" + salesId + ", readStatus=" + readStatus
			+ ", messageReadTime=" + messageReadTime + ", targetPriceReply="
			+ targetPriceReply + ", attachmentPath=" + attachmentPath
			+ ", username=" + username + ", qualityIssuesPic="
			+ qualityIssuesPic + ", loginEmail=" + loginEmail + "]";
}
   
   

   
   
   
   
   
}
