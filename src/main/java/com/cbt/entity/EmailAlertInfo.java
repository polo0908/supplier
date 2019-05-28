package com.cbt.entity;

import java.io.Serializable;

public class EmailAlertInfo implements Serializable {

	/**
	 * @author polo
	 * 2017年6月6日
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String emailType;      //邮件类型
	private String emailContent;    //邮件内容
	private String emailTitle;      //邮件标题
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public String getEmailTitle() {
		return emailTitle;
	}
	public void setEmailTitle(String emailTitle) {
		this.emailTitle = emailTitle;
	}
	@Override
	public String toString() {
		return "EmailAlertInfo [id=" + id + ", emailType=" + emailType
				+ ", emailContent=" + emailContent + ", emailTitle="
				+ emailTitle + "]";
	}
	
	
	
	
}
