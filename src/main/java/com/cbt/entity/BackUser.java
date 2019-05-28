package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

/**
 * @ClassName: BackUser
 * @Description: 后台用户
 */
public class BackUser implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 795316582L;
	private Integer id;
	private String backUserid; // 用户ID
	private String userName; // 用户名
	private String realName; // 真实姓名
	private String pwd;
	private String email;
	private String tel;
	private String createTime;
	private String remark;
	private String updateUser;
	private String updateTime;
	private Integer permission;
	
	private String factoryId; //工厂id

	
	
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

	public String getBackUserid() {
		return backUserid;
	}

	public void setBackUserid(String backUserid) {
		this.backUserid = backUserid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		try {
			this.createTime = DateFormat.formatDate2(createTime);
		} catch (ParseException e) {
			e.printStackTrace();
			this.createTime = createTime;
		}
		
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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



	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	@Override
	public String toString() {
		return "BackUser [id=" + id + ", backUserid=" + backUserid
				+ ", userName=" + userName + ", realName=" + realName
				+ ", pwd=" + pwd + ", email=" + email + ", tel=" + tel
				+ ", createTime=" + createTime + ", remark=" + remark
				+ ", updateUser=" + updateUser + ", updateTime=" + updateTime
				+ ", permission=" + permission + ", factoryId=" + factoryId
				+ "]";
	}

}
