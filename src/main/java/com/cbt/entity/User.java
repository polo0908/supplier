package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

/**
 * @ClassName: User
 * @Description: 客户用户信息
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -556283251L;
	private Integer id;
	private String userid; //用户id
	private String userName; //用户名
	private String pwd; //密码
	private String tel; //电话
	private String email; //邮箱
	private String email1; //邮箱
	private String loginEmail; //登录邮箱
	private String updateTime; //更新时间
	private String token; //用户登录令牌
	private String createTime; //客户录入时间
	private String companyName; //客户公司
	private Integer tempUser;    //是否是临时用户：0，正式用户 1：临时用户
	private String encryptedEmail;  //NBMail加密邮箱
    
	private SaleCustomer saleCustomer;
	private String salesId;
	private String minTime;    //最早下单时间
	private String maxTime;    //最近下单时间
	
	//操作销售（虚拟字段  用于查询邀请登录、修改订单统计）
	private String saleName;
	private String loginTime;    //登录时间
	private String operationTime;   //更新时间
	


	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(String operationTime) {
		this.operationTime = operationTime;
	}

	public String getSaleName() {
		return saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public String getEncryptedEmail() {
		return encryptedEmail;
	}

	public void setEncryptedEmail(String encryptedEmail) {
		this.encryptedEmail = encryptedEmail;
	}

	public Integer getTempUser() {
		return tempUser;
	}

	public void setTempUser(Integer tempUser) {
		this.tempUser = tempUser;
	}

	public String getMinTime() {
		return minTime;
	}

	public void setMinTime(String minTime) {
		this.minTime = minTime;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public SaleCustomer getSaleCustomer() {
		return saleCustomer;
	}

	public void setSaleCustomer(SaleCustomer saleCustomer) {
		this.saleCustomer = saleCustomer;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		try {
			this.updateTime = DateFormat.formatDate2(updateTime);
		} catch (ParseException e) {
			e.printStackTrace();
			this.updateTime = updateTime;
		}
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userid=" + userid + ", userName="
				+ userName + ", pwd=" + pwd + ", tel=" + tel + ", email="
				+ email + ", email1=" + email1 + ", loginEmail=" + loginEmail
				+ ", updateTime=" + updateTime + ", token=" + token
				+ ", createTime=" + createTime + ", companyName=" + companyName
				+ ", tempUser=" + tempUser + ", encryptedEmail="
				+ encryptedEmail + ", saleCustomer=" + saleCustomer
				+ ", salesId=" + salesId + ", minTime=" + minTime
				+ ", maxTime=" + maxTime + ", saleName=" + saleName
				+ ", loginTime=" + loginTime + ", operationTime="
				+ operationTime + "]";
	}

}
