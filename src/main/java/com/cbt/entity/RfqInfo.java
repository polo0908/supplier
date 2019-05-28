package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

/**
 * @ClassName: rfqInfo
 * @Description: 新询盘记录
 */
public class RfqInfo implements Serializable {

	private Integer id;
	private String userid;// 用户id
	private String productName;// 产品名称
	private String drawingPath;// 图纸路径
	private String requiredTime;// 交货时间
	private String comment; // 描述
	private String createTime;// 图纸创建时间
	private String followUp;// 跟进销售
	private Integer drawingState;//状态（未处理1、已跟进2、已关闭3）
	private String drawingName;// 图纸名称
    private String updateTime; //图纸更新时间
	private String followTime; // 跟进时间
	private String followComment; // 跟进描述
	private String status; // 图纸状态(Quoted 询价 Ordered 订单）
	private Integer historyId; // 历史订单id
    private String factoryId;  //工厂id
	
	private User user;
	private String userName;
	
	private SaleCustomer saleCustomer;
	private String salesId;
	
	
	

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}

	public String getFollowTime() {
		return followTime;
	}

	public void setFollowTime(String followTime) {
		try {
			this.followTime = DateFormat.formatDate2(followTime);
		} catch (ParseException e) {
			e.printStackTrace();
			this.followTime = followTime;
		}

	}

	public String getFollowComment() {
		return followComment;
	}

	public void setFollowComment(String followComment) {
		this.followComment = followComment;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDrawingPath() {
		return drawingPath;
	}

	public void setDrawingPath(String drawingPath) {
		this.drawingPath = drawingPath;
	}

	public String getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(String requiredTime) {

			this.requiredTime = requiredTime;
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
		try {
			this.createTime = DateFormat.formatDate2(createTime);
		} catch (ParseException e) {
			e.printStackTrace();
			this.createTime = createTime;
		}

	}

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public Integer getDrawingState() {
		return drawingState;
	}

	public void setDrawingState(Integer drawingState) {
		this.drawingState = drawingState;
	}

	public String getDrawingName() {
		return drawingName;
	}

	public void setDrawingName(String drawingName) {
		this.drawingName = drawingName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "RfqInfo [id=" + id + ", userid=" + userid + ", productName="
				+ productName + ", drawingPath=" + drawingPath
				+ ", requiredTime=" + requiredTime + ", comment=" + comment
				+ ", createTime=" + createTime + ", followUp=" + followUp
				+ ", drawingState=" + drawingState + ", drawingName="
				+ drawingName + ", updateTime=" + updateTime + ", followTime="
				+ followTime + ", followComment=" + followComment + ", status="
				+ status + ", historyId=" + historyId + ", factoryId="
				+ factoryId + ", user=" + user + ", userName=" + userName
				+ ", saleCustomer=" + saleCustomer + ", salesId=" + salesId
				+ "]";
	}

}
