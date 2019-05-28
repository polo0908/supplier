package com.cbt.entity;

import java.io.Serializable;

public class ShippingInfo implements Serializable {
	
	private Integer id; //主键id
	private String userid; //客户id
	private Integer cid; //NBemail获取的客户id
	private String userName;//客户姓名
	private String job; //客户职位
	private String companyName; //公司名称
	private String otherName1; //公司别名1
	private String otherName2; //公司别名2
	private String registerEmail; //注册邮箱
	private String contactEmail; //联系邮箱
	private String telephone1; //固话1
	private String telephone2; //固话2
	private String mobilePhone1; //手机1
	private String mobilePhone2; //手机2
	private String fax; //传真
	private String detailedAddress; //详细地址
	private String address1; //地址1
	private String address2; //地址2
	private String country; //国家
	private String state; //州/省
	private String city; //城市
	private String postcode; //邮编
	private String siteUrl; //客户网站
	private String updateTime; //信息更新时间
	
	
	
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

	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getOtherName1() {
		return otherName1;
	}
	public void setOtherName1(String otherName1) {
		this.otherName1 = otherName1;
	}
	public String getOtherName2() {
		return otherName2;
	}
	public void setOtherName2(String otherName2) {
		this.otherName2 = otherName2;
	}
	public String getRegisterEmail() {
		return registerEmail;
	}
	public void setRegisterEmail(String registerEmail) {
		this.registerEmail = registerEmail;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getTelephone1() {
		return telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getTelephone2() {
		return telephone2;
	}
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public String getMobilePhone1() {
		return mobilePhone1;
	}
	public void setMobilePhone1(String mobilePhone1) {
		this.mobilePhone1 = mobilePhone1;
	}
	public String getMobilePhone2() {
		return mobilePhone2;
	}
	public void setMobilePhone2(String mobilePhone2) {
		this.mobilePhone2 = mobilePhone2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getDetailedAddress() {
		return detailedAddress;
	}
	public void setDetailedAddress(String detailedAddress) {
		this.detailedAddress = detailedAddress;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	@Override
	public String toString() {
		return "ShippingInfo [id=" + id + ", userid=" + userid + ", cid=" + cid
				+ ", userName=" + userName + ", job=" + job + ", companyName="
				+ companyName + ", otherName1=" + otherName1 + ", otherName2="
				+ otherName2 + ", registerEmail=" + registerEmail
				+ ", contactEmail=" + contactEmail + ", telephone1="
				+ telephone1 + ", telephone2=" + telephone2 + ", mobilePhone1="
				+ mobilePhone1 + ", mobilePhone2=" + mobilePhone2 + ", fax="
				+ fax + ", detailedAddress=" + detailedAddress + ", address1="
				+ address1 + ", address2=" + address2 + ", country=" + country
				+ ", state=" + state + ", city=" + city + ", postcode="
				+ postcode + ", siteUrl=" + siteUrl + ", updateTime="
				+ updateTime + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
	  
	
}
