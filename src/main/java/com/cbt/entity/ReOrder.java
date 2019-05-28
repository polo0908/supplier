package com.cbt.entity;

import java.io.Serializable;
import java.text.ParseException;

import com.cbt.util.DateFormat;

/**
 * @ClassName: ReOrder
 * @Description: 再订购记录
 */
public class ReOrder implements Serializable {

	private int id; // 再订购订单号
	private String userid;

	private String requiredTime;// 询盘时间
	private String comments;// 描述
	private Integer reOrderState;// 订单状态（1未处理、2已跟进、3已结束）
	private Double totalAmount;// 预计总金额
	private String createTime;// 订单创建时间
	private String followUp; // 跟进销售
	// private String invoiceId; //发票编号
	private String followTime; // 跟进时间
	private String followComment; // 跟进描述
	private Integer historyId; // 历史订单id
	private String factoryId;  //工厂id

	//虚拟字段
	private User user;
	private String userName;

	private SaleCustomer saleCustomer;
	private String salesId;
	private String drawingNames;      //全部图纸名
	
	

	public String getDrawingNames() {
		return drawingNames;
	}

	public void setDrawingNames(String drawingNames) {
		this.drawingNames = drawingNames;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
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

	public String getFollowTime() {
		return followTime;
	}

	public Integer getHistoryId() {
		return historyId;
	}

	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
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

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRequiredTime() {
		return requiredTime;
	}

	public void setRequiredTime(String requiredTime) {

			this.requiredTime = requiredTime;

	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Integer getReOrderState() {
		return reOrderState;
	}

	public void setReOrderState(Integer reOrderState) {
		this.reOrderState = reOrderState;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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

	@Override
	public String toString() {
		return "ReOrder [id=" + id + ", userid=" + userid + ", requiredTime="
				+ requiredTime + ", comments=" + comments + ", reOrderState="
				+ reOrderState + ", totalAmount=" + totalAmount
				+ ", createTime=" + createTime + ", followUp=" + followUp
				+ ", followTime=" + followTime + ", followComment="
				+ followComment + ", historyId=" + historyId + ", factoryId="
				+ factoryId + ", user=" + user + ", userName=" + userName
				+ ", saleCustomer=" + saleCustomer + ", salesId=" + salesId
				+ ", drawingNames=" + drawingNames + "]";
	}

}
