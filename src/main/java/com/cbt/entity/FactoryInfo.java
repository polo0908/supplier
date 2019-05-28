package com.cbt.entity;

import java.io.Serializable;

public class FactoryInfo implements Serializable {

	  
	  
	  private Integer id;
	  private String factoryId;          //工厂Id(生成规则)
	  private String factoryName;        //工厂名
	  private String factoryLogo;        //公司logo
	  private String factoryLicense;     //工厂营业执照
	  private String factoryAdminName;   //工厂管理员名
	  private String factoryAdminEmail;  //工厂管理员邮箱
	  private String factoryAdminTel;    //工厂管理员电话
	  private String factoryAdminPwd;    //管理员密码
	  private String registerTime;       //注册日期  
	  private String updateTime;         //更新时间
	  private String factoryAddr;        //工厂地址
	  private String mainBusiness;       //主营
	  private String country;            //国家
	  private String establishmentDate;  //成立时间
	  private String factoryNumber;      //工厂人数
	  private String website;            //工厂网址
	  
	  
	  
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEstablishmentDate() {
		return establishmentDate;
	}
	public void setEstablishmentDate(String establishmentDate) {
		this.establishmentDate = establishmentDate;
	}
	public String getFactoryNumber() {
		return factoryNumber;
	}
	public void setFactoryNumber(String factoryNumber) {
		this.factoryNumber = factoryNumber;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getFactoryAddr() {
		return factoryAddr;
	}
	public void setFactoryAddr(String factoryAddr) {
		this.factoryAddr = factoryAddr;
	}
	public String getMainBusiness() {
		return mainBusiness;
	}
	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFactoryId() {
		return factoryId;
	}
	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}
	public String getFactoryName() {
		return factoryName;
	}
	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	public String getFactoryLogo() {
		return factoryLogo;
	}
	public void setFactoryLogo(String factoryLogo) {
		this.factoryLogo = factoryLogo;
	}
	public String getFactoryLicense() {
		return factoryLicense;
	}
	public void setFactoryLicense(String factoryLicense) {
		this.factoryLicense = factoryLicense;
	}
	public String getFactoryAdminName() {
		return factoryAdminName;
	}
	public void setFactoryAdminName(String factoryAdminName) {
		this.factoryAdminName = factoryAdminName;
	}
	public String getFactoryAdminEmail() {
		return factoryAdminEmail;
	}
	public void setFactoryAdminEmail(String factoryAdminEmail) {
		this.factoryAdminEmail = factoryAdminEmail;
	}
	public String getFactoryAdminTel() {
		return factoryAdminTel;
	}
	public void setFactoryAdminTel(String factoryAdminTel) {
		this.factoryAdminTel = factoryAdminTel;
	}
	public String getFactoryAdminPwd() {
		return factoryAdminPwd;
	}
	public void setFactoryAdminPwd(String factoryAdminPwd) {
		this.factoryAdminPwd = factoryAdminPwd;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	@Override
	public String toString() {
		return "FactoryInfo [id=" + id + ", factoryId=" + factoryId
				+ ", factoryName=" + factoryName + ", factoryLogo="
				+ factoryLogo + ", factoryLicense=" + factoryLicense
				+ ", factoryAdminName=" + factoryAdminName
				+ ", factoryAdminEmail=" + factoryAdminEmail
				+ ", factoryAdminTel=" + factoryAdminTel + ", factoryAdminPwd="
				+ factoryAdminPwd + ", registerTime=" + registerTime
				+ ", updateTime=" + updateTime + ", factoryAddr=" + factoryAddr
				+ ", mainBusiness=" + mainBusiness + ", country=" + country
				+ ", establishmentDate=" + establishmentDate
				+ ", factoryNumber=" + factoryNumber + ", website=" + website
				+ "]";
	}
	  
	  
	  
	  
}
