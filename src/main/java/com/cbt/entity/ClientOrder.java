package com.cbt.entity;

import java.io.Serializable;

/**
* @ClassName: ClientOrder 
* @Description: 客户历史订单
 */
public class ClientOrder implements Serializable{

	/**
	 * @fieldName serialVersionUID
	 * @fieldType long
	 * @Description TODO
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String userid;
	private String orderId;//订单号
	private Double amount; //发票金额
	private Integer orderStatus; //订单状态
	private String createTime; //创建时间
	private String outputTime; //出库时间
	private String poPath;//PO下载路径
//	private String invoicePath;//Invoice下载路径
	private String qcReportPath;//QC_Report下载路径
	private String shippingDocPath; //shipping_Doc下载路径
	private Integer orderSource;  //订单来源(0:历史订单 1:reOrder订单 2:新图纸询盘 3:ERP导入订单)
	private String invoiceIds;  //发票编号集合
	private String poUpdateTime; //PO上传时间
	private String qcUpdateTime; //PO上传时间
	private String shippingUpdateTime; //PO上传时间
	private String oldOrNewPo;   //PO是否点击下载
	private String oldOrNewQc;   //QC是否点击下载
	private String oldOrNewShipping;   //Shipping是否点击下载
	private String poNumber;     //客户PO号
	private String deliveryTime;  //交期时间
	private String factoryId;     //工厂id
	private String arrivalDate;   //到达日期  
	private String BLAvailableDate; //海运提单时间
	private String ISFDate;         //申报时间
	private Double actualAmount;    //实际到账金额
	private Integer orderTypeId;      //订单类型
	private String orderRequest;      //客户原始需求
	private Double exchangeRateCNY;    //人民币汇率	
	private Double exchangeRateEUR;    //欧元汇率	
	private Double exchangeRateGBP;    //英镑汇率
	private String salesName;          //销售名
	private String projectName;        //项目名
	private String paymentReceived;    //付款时间
	
	
	private String updateTime;         //增加更新时间      2017/12/25
	
	private User user;
	private String userName;
	
	private SaleCustomer saleCustomer;
	private String salesId;

	private String orderType;        //项目类型
	
	
	
    
	
	
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getPaymentReceived() {
		return paymentReceived;
	}
	public void setPaymentReceived(String paymentReceived) {
		this.paymentReceived = paymentReceived;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public Double getExchangeRateCNY() {
		return exchangeRateCNY;
	}
	public void setExchangeRateCNY(Double exchangeRateCNY) {
		this.exchangeRateCNY = exchangeRateCNY;
	}
	public Double getExchangeRateEUR() {
		return exchangeRateEUR;
	}
	public void setExchangeRateEUR(Double exchangeRateEUR) {
		this.exchangeRateEUR = exchangeRateEUR;
	}
	public Double getExchangeRateGBP() {
		return exchangeRateGBP;
	}
	public void setExchangeRateGBP(Double exchangeRateGBP) {
		this.exchangeRateGBP = exchangeRateGBP;
	}
	public String getOrderRequest() {
		return orderRequest;
	}
	public void setOrderRequest(String orderRequest) {
		this.orderRequest = orderRequest;
	}
	public Integer getOrderTypeId() {
		return orderTypeId;
	}
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}
	public Double getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(Double actualAmount) {
		this.actualAmount = actualAmount;
	}
	public Double getAmount() {
		return amount;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public String getBLAvailableDate() {
		return BLAvailableDate;
	}
	public void setBLAvailableDate(String bLAvailableDate) {
		BLAvailableDate = bLAvailableDate;
	}
	public String getISFDate() {
		return ISFDate;
	}
	public void setISFDate(String iSFDate) {
		ISFDate = iSFDate;
	}
	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
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
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getPoUpdateTime() {
		return poUpdateTime;
	}
	public void setPoUpdateTime(String poUpdateTime) {
		
			this.poUpdateTime = poUpdateTime;

		
	}
	public String getQcUpdateTime() {
		return qcUpdateTime;
	}
	public void setQcUpdateTime(String qcUpdateTime) {

		 this.qcUpdateTime = qcUpdateTime;
			
	}
	public String getShippingUpdateTime() {
		return shippingUpdateTime;
	}
	public void setShippingUpdateTime(String shippingUpdateTime) {

			this.shippingUpdateTime = shippingUpdateTime;

	}
    
	public String getOldOrNewPo() {
		return oldOrNewPo;
	}
	public void setOldOrNewPo(String oldOrNewPo) {
		this.oldOrNewPo = oldOrNewPo;
	}
	public String getOldOrNewQc() {
		return oldOrNewQc;
	}
	public void setOldOrNewQc(String oldOrNewQc) {
		this.oldOrNewQc = oldOrNewQc;
	}
	public String getOldOrNewShipping() {
		return oldOrNewShipping;
	}
	public void setOldOrNewShipping(String oldOrNewShipping) {
		this.oldOrNewShipping = oldOrNewShipping;
	}
	public String getInvoiceIds() {
		return invoiceIds;
	}
	public void setInvoiceIds(String invoiceIds) {
		this.invoiceIds = invoiceIds;
	}
	public Integer getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCreateTime() {

		return createTime;
	}
	public void setCreateTime(String createTime) {		
	     this.createTime = createTime;
		
	}
	public String getOutputTime() {

		return outputTime;
	}
	public void setOutputTime(String outputTime) {
		this.outputTime = outputTime;
		
	}
	public String getPoPath() {
		return poPath;
	}
	public void setPoPath(String poPath) {
		this.poPath = poPath;
	}

	public String getQcReportPath() {
		return qcReportPath;
	}
	public void setQcReportPath(String qcReportPath) {
		this.qcReportPath = qcReportPath;
	}
	public String getShippingDocPath() {
		return shippingDocPath;
	}
	public void setShippingDocPath(String shippingDocPath) {
		this.shippingDocPath = shippingDocPath;
	}
	@Override
	public String toString() {
		return "ClientOrder [id=" + id + ", userid=" + userid + ", orderId="
				+ orderId + ", amount=" + amount + ", orderStatus="
				+ orderStatus + ", createTime=" + createTime + ", outputTime="
				+ outputTime + ", poPath=" + poPath + ", qcReportPath="
				+ qcReportPath + ", shippingDocPath=" + shippingDocPath
				+ ", orderSource=" + orderSource + ", invoiceIds=" + invoiceIds
				+ ", poUpdateTime=" + poUpdateTime + ", qcUpdateTime="
				+ qcUpdateTime + ", shippingUpdateTime=" + shippingUpdateTime
				+ ", oldOrNewPo=" + oldOrNewPo + ", oldOrNewQc=" + oldOrNewQc
				+ ", oldOrNewShipping=" + oldOrNewShipping + ", poNumber="
				+ poNumber + ", deliveryTime=" + deliveryTime + ", factoryId="
				+ factoryId + ", arrivalDate=" + arrivalDate
				+ ", BLAvailableDate=" + BLAvailableDate + ", ISFDate="
				+ ISFDate + ", actualAmount=" + actualAmount + ", orderTypeId="
				+ orderTypeId + ", orderRequest=" + orderRequest
				+ ", exchangeRateCNY=" + exchangeRateCNY + ", exchangeRateEUR="
				+ exchangeRateEUR + ", exchangeRateGBP=" + exchangeRateGBP
				+ ", salesName=" + salesName + ", projectName=" + projectName
				+ ", paymentReceived=" + paymentReceived + ", updateTime="
				+ updateTime + ", user="+ user + ", userName=" + userName + ", saleCustomer="
				+ saleCustomer + ", salesId=" + salesId + ", orderType="
				+ orderType + "]";
	}


	

	
	

	
	

	
	

	
	
	
}
