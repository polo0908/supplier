package com.cbt.entity;

import java.io.Serializable;

/**
* @ClassName: ClientDrawings 
* @Description: 客户图纸信息
 */
public class ReOrderDrawings implements Serializable{
	

    private Integer id;
	private Integer reOrderId;//新订单id
	private String userId; //用户名称
	private String projectName;//项目名称
	private String drawingsName;//图纸名称
	private String drawingsPath;//图纸路径
	private double moldPrice;//模板价格
	private double unitPrice;//产品单价
	private Integer quantity;//产品数量
	private Integer oldOrderId;//原订单id
	private String drawingState;//图纸状态
	private String updateTime; //图纸更新时间
	private String productName; //产品名称
	private String status; //图纸状态(Quoted 询价 Ordered 订单）
	private String factoryId;   //工厂Id
	
	

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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDrawingState() {
		return drawingState;
	}
	public void setDrawingState(String drawingState) {
		this.drawingState = drawingState;
	}
	public Integer getReOrderId() {
		return reOrderId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setReOrderId(Integer reOrderId) {
		this.reOrderId = reOrderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getOldOrderId() {
		return oldOrderId;
	}
	public void setOldOrderId(Integer oldOrderId) {
		this.oldOrderId = oldOrderId;
	}

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getDrawingsName() {
		return drawingsName;
	}
	public void setDrawingsName(String drawingsName) {
		this.drawingsName = drawingsName;
	}

	
	public String getDrawingsPath() {
		return drawingsPath;
	}
	public void setDrawingsPath(String drawingsPath) {
		this.drawingsPath = drawingsPath;
	}
	public double getMoldPrice() {
		return moldPrice;
	}
	public void setMoldPrice(double moldPrice) {
		this.moldPrice = moldPrice;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "ReOrderDrawings [id=" + id + ", reOrderId=" + reOrderId
				+ ", userId=" + userId + ", projectName=" + projectName
				+ ", drawingsName=" + drawingsName + ", drawingsPath="
				+ drawingsPath + ", moldPrice=" + moldPrice + ", unitPrice="
				+ unitPrice + ", quantity=" + quantity + ", oldOrderId="
				+ oldOrderId + ", drawingState=" + drawingState
				+ ", updateTime=" + updateTime + ", productName=" + productName
				+ ", status=" + status + ", factoryId=" + factoryId + "]";
	}


	



	
	
	
}
