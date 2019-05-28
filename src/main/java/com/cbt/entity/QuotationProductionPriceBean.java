package com.cbt.entity;

import java.io.Serializable;

/**
 * 报价单商品价格
 * @author polo
 * @time 2017.3.28
 */
public class QuotationProductionPriceBean implements Serializable{

	private static final long serialVersionUID = 528634311012160360L;

	private Integer id;                 //报价商品价格id
	
	private Integer productionInfoId;    //报价商品id
	
	private String productName;          //商品名
	
	private Double productPrice;         //产品价格
	
	private Integer quantity;           //商品最小订量	
	
	private String updateTime;          //更新时间
	
	private Double totalProfitRate;     //总利润率
	
	private Double materialProfitRate;  //材料费利润率
	
	private Double cifPrice;            //CIF价格	
	
	private Double materialPrice;       //材料价格
	
    private Double processPrice;        //工艺价格
    
	private Double materialWeight;      //材料重量
	
	private String weightUnit;          //重量单位
	
	//虚拟字段   （模具利润率改变后产品价格）
	private Double productChangePrice;
	//(模具利润率改变后总利润率变化)
	private Double totalProfitChangeRate;
	
	
	
	
	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public Double getProductChangePrice() {
		return productChangePrice;
	}

	public void setProductChangePrice(Double productChangePrice) {
		this.productChangePrice = productChangePrice;
	}

	public Double getTotalProfitChangeRate() {
		return totalProfitChangeRate;
	}

	public void setTotalProfitChangeRate(Double totalProfitChangeRate) {
		this.totalProfitChangeRate = totalProfitChangeRate;
	}

	public Double getCifPrice() {
		return cifPrice;
	}

	public void setCifPrice(Double cifPrice) {
		this.cifPrice = cifPrice;
	}

	public Double getMaterialPrice() {
		return materialPrice;
	}

	public void setMaterialPrice(Double materialPrice) {
		this.materialPrice = materialPrice;
	}

	public Double getProcessPrice() {
		return processPrice;
	}

	public void setProcessPrice(Double processPrice) {
		this.processPrice = processPrice;
	}

	public Double getMaterialWeight() {
		return materialWeight;
	}

	public void setMaterialWeight(Double materialWeight) {
		this.materialWeight = materialWeight;
	}

	public Double getTotalProfitRate() {
		return totalProfitRate;
	}

	public void setTotalProfitRate(Double totalProfitRate) {
		this.totalProfitRate = totalProfitRate;
	}

	public Double getMaterialProfitRate() {
		return materialProfitRate;
	}

	public void setMaterialProfitRate(Double materialProfitRate) {
		this.materialProfitRate = materialProfitRate;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getProductionInfoId() {
		return productionInfoId;
	}

	public void setProductionInfoId(Integer productionInfoId) {
		this.productionInfoId = productionInfoId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Double price() {
		return productPrice;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "QuotationProductionPriceBean [id=" + id + ", productionInfoId="
				+ productionInfoId + ", productName=" + productName
				+ ", productPrice=" + productPrice + ", quantity=" + quantity
				+ ", updateTime=" + updateTime + ", totalProfitRate="
				+ totalProfitRate + ", materialProfitRate="
				+ materialProfitRate + ", cifPrice=" + cifPrice
				+ ", materialPrice=" + materialPrice + ", processPrice="
				+ processPrice + ", materialWeight=" + materialWeight
				+ ", weightUnit=" + weightUnit + ", productChangePrice="
				+ productChangePrice + ", totalProfitChangeRate="
				+ totalProfitChangeRate + "]";
	}
	
	
	
}
