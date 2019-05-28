package com.cbt.controller;


import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.AmountUnit;
import com.cbt.entity.BankInfo;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryInfo;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.InvoicePaymentRemark;
import com.cbt.entity.InvoiceProduct;
import com.cbt.entity.InvoiceRemark;
import com.cbt.entity.PaymentPlan;
import com.cbt.print.InvoicePrintTemplate;
import com.cbt.service.AmountUnitService;
import com.cbt.service.BankInfoService;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ClientOrderService;
import com.cbt.service.FactoryInfoService;
import com.cbt.service.InvoiceInfoService;
import com.cbt.service.InvoicePaymentRemarkService;
import com.cbt.service.InvoiceProductService;
import com.cbt.service.InvoiceRemarkService;
import com.cbt.service.PaymentPlanService;
import com.cbt.service.ShippingInfoService;
import com.cbt.service.UserService;
import com.cbt.util.Base64;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.UtilFuns;
import com.cbt.util.WebCookie;

@Controller

public class InvoiceInfoController {
	
	public static Logger logger = Logger.getLogger(InvoiceInfoController.class);	
	@Resource
	private ClientOrderService clientOrderService;
	@Resource
	private InvoiceInfoService invoiceInfoService;
	@Resource
	private ClientDrawingsService clientDrawingsService;
	@Resource
	private PaymentPlanService paymentPlanService;
	@Resource
	private AmountUnitService amountUnitService;
	@Resource
	private BankInfoService bankInfoService;
	@Resource
	private InvoiceProductService invoiceProductService;
	@Resource
	private FactoryInfoService factoryInfoService;
	@Resource
	private UserService userService;
	@Resource
	private ShippingInfoService shippingInfoService;
	@Resource
	private InvoiceRemarkService invoiceRemarkService;
	@Resource
	private InvoicePaymentRemarkService invoicePaymentRemarkService;
	
	
	private static final int ORDER_SOURCE_ERP = 3;
	
	
	
    /**
     * 查询产品数据
     * @Title queryDrawingInfo 
     * @Description
     * @param request
     * @param response
     * @return
     * @throws Exception
     * @return String
     */
	@RequestMapping("/queryDrawingInfo.do")
	public String queryDrawingInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String factoryId = WebCookie.getFactoryId(request);
			String orderId = request.getParameter("orderId");
			orderId = Base64Encode.getFromBase64(orderId);
			List<ClientDrawings> list = clientDrawingsService.queryListByOrderId(orderId);
			List<AmountUnit> amountUnit = amountUnitService.queryAmountUnit();
			if(factoryId == null || "".equals(factoryId)){
				throw new RuntimeException("未获取到工厂ID");
			}

			List<InvoiceRemark> invoiceRemarks = invoiceRemarkService.queryRemarkByFactoryId(factoryId);
			InvoicePaymentRemark invoicePaymentRemark = invoicePaymentRemarkService.queryRemarkByFactoryId(factoryId);
			String invoiceId = "";
			String str = orderId.replace("SHS","");
			List<InvoiceInfo> invoiceInfos = invoiceInfoService.queryInvoiceByOrderId(orderId);
			if(invoiceInfos.size() == 0){ 
				invoiceId = "INV"+ str+"A";
			}else{
				String lastStrChange = "";
				int maxSum = 0;
				for (InvoiceInfo invoiceInfo : invoiceInfos) {
					int sum = 0;
					if(!(invoiceInfo.getInvoiceId() == null || "".equals(invoiceInfo.getInvoiceId()))){
						String lastStr = invoiceInfo.getInvoiceId().substring(invoiceInfo.getInvoiceId().length()-1,invoiceInfo.getInvoiceId().length());
						String sbu = UtilFuns.stringToAscii(lastStr);
					    sum = Integer.parseInt(sbu.toString());
					    if(sum >= maxSum){
					    	maxSum = sum;
					    }
					}
				}
				
				if(maxSum < 65){
				      maxSum = 65;
			    	  lastStrChange = UtilFuns.asciiToString(maxSum+"");
			       }else{
			    	  maxSum = maxSum+1; 
			    	  lastStrChange = UtilFuns.asciiToString(maxSum+"");
			       }					 
				
				invoiceId =  "INV"+ str+lastStrChange;
			}
	
			
			
			request.setAttribute("invoiceId", invoiceId);
			request.setAttribute("amountUnit", amountUnit);
			request.setAttribute("orderId", orderId);
			request.setAttribute("clientDrawings", list);
			request.setAttribute("invoiceRemarks", invoiceRemarks);
			request.setAttribute("remark", invoicePaymentRemark.getPaymentRemark());
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  queryDrawingInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		return "invoice_upload.jsp";
	}

	@RequestMapping("/jumpToUpdateInvoice.do")
	public String jumpToUpdateInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			InvoiceRemark invoiceRemark = new InvoiceRemark();
			String orderId = request.getParameter("orderId");
			String invoiceId = request.getParameter("invoiceId");
			orderId = Base64Encode.getFromBase64(orderId);
			invoiceId = Base64Encode.getFromBase64(invoiceId);
			String factoryId = WebCookie.getFactoryId(request);
			if(factoryId == null || "".equals(factoryId)){
				throw new RuntimeException("未获取到工厂ID");
			}
			List<InvoiceRemark> invoiceRemarks = invoiceRemarkService.queryRemarkByFactoryId(factoryId);
			InvoicePaymentRemark invoicePaymentRemark = invoicePaymentRemarkService.queryRemarkByFactoryId(factoryId);
			InvoiceInfo invoiceInfo = invoiceInfoService.queryByInvoiceId(invoiceId,factoryId);
			List<InvoiceProduct> invoiceProducts = invoiceProductService.queryByInvoiceId(invoiceId);
			List<AmountUnit> amountUnit = amountUnitService.queryAmountUnit();
			Integer invoiceRemarkId = invoiceInfo.getInvoiceRemarkId();
            if(!(invoiceRemarkId == null || "".equals(invoiceRemarkId))){
            	invoiceRemark = invoiceRemarkService.queryById(invoiceRemarkId);
             }
						
			request.setAttribute("invoiceInfo", invoiceInfo);
			request.setAttribute("orderId", orderId);
			request.setAttribute("invoiceId", invoiceId);
			request.setAttribute("currency", invoiceInfo.getAmountUnit());
			request.setAttribute("invoiceProducts", invoiceProducts);
			request.setAttribute("amountUnit", amountUnit);
			request.setAttribute("invoiceRemarks", invoiceRemarks);
			request.setAttribute("invoiceRemark", invoiceRemark);
			request.setAttribute("remark", invoicePaymentRemark.getPaymentRemark());
			
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  jumpToUpdateInvoice======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return "invoice_edit.jsp";
	}

	
	/**
	 * 根据订单号查询发票
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/queryInvoiceByOrderId.do")
	public String queryInvoiceByOrderId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String orderId = request.getParameter("orderId");
			orderId = Base64Encode.getFromBase64(orderId);
			List<InvoiceInfo> invoiceInfos = invoiceInfoService.queryInvoiceByOrderId(orderId);
			Double total = 0.00;		
			List<AmountUnit> list = new ArrayList<AmountUnit>();

			for (InvoiceInfo invoiceInfo : invoiceInfos) {
				
				AmountUnit amountUnit = amountUnitService.queryById(invoiceInfo.getAmountUnit());
				Double exchangeRate = amountUnit.getExchangeRate();
				
				Double productPrice = invoiceInfo.getProductPrice();
				if(productPrice == null || "".equals(productPrice)){
				   productPrice = 0.00;
				}

				Double otherPrice = invoiceInfo.getOtherPrice();
				if(otherPrice == null || "".equals(otherPrice)){
					otherPrice = 0.00;
					}
				Double shippingFee = invoiceInfo.getShippingFee();
				if(shippingFee == null || "".equals(shippingFee)){
					shippingFee = 0.00;
					}
				Double moldPrice = invoiceInfo.getMoldPrice();
				if(moldPrice == null || "".equals(moldPrice)){
					moldPrice = 0.00;
					}
				Double sum = invoiceInfo.getAmount() / exchangeRate;
				sum = new BigDecimal(sum).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();				
				total +=  sum;
				
				list.add(amountUnit);
				
				
				//发票上传更新时间转换Date类型
				if(!(invoiceInfo.getCreateTime() == null || "".equals(invoiceInfo.getCreateTime()))){
					invoiceInfo.setCreateTime(DateFormat.formatDate1(invoiceInfo.getCreateTime()));
				}
				if(!(invoiceInfo.getUpdateTime() == null || "".equals(invoiceInfo.getUpdateTime()))){
					invoiceInfo.setUpdateTime(DateFormat.formatDate1(invoiceInfo.getUpdateTime()));
				}
				
			}
			Double f1 = new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


			request.setAttribute("amountUnit", list);
			request.setAttribute("invoiceInfos", invoiceInfos);
			request.setAttribute("orderId", orderId);
			request.setAttribute("total", f1);
			request.setAttribute("currency", "USD");
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  queryInvoiceByOrderId======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return "bigInvoice.jsp";
	}

	/**
	 * 保存发票信息 invoiceInfo
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveInvoiceInfo.do")
	public JsonResult<String> saveInvoiceInfo(HttpServletRequest request, HttpServletResponse response){
		
		
		try {
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			Integer id = null;
			String paymentRemark = null;
			// 从cookies中获取用户的信息
			String salesId = WebCookie.getUserId(request);// cookie取登录用户
			if(salesId == null || "".equals(salesId)){
				throw new Exception("获取用户失败!");
			}
            String factoryId = WebCookie.getFactoryId(request);
            if(!(request.getParameter("remarkId") == null || "".equals(request.getParameter("remarkId")))){
            	id = Integer.parseInt(request.getParameter("remarkId"));       	
            }              
            invoiceInfo.setFactoryId(factoryId);
			invoiceInfo.setSalesId(salesId);
			String orderId = null;
			if (!(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId")))) {
				orderId = request.getParameter("orderId");
				invoiceInfo.setOrderId(orderId);
			}else {
				throw new Exception("订单号不存在!");
			}
			ClientOrder clientOrder = clientOrderService.queryByOrderId(orderId);
			String userid = clientOrder.getUserid();
			invoiceInfo.setUserid(userid);

			String invoiceId = null;
			if (!(request.getParameter("invoiceId") == null || "".equals(request.getParameter("invoiceId")))) {
				invoiceId = request.getParameter("invoiceId");
				invoiceInfo.setInvoiceId(invoiceId);
			}else {
				throw new Exception("请输入发票号!");
			}
			if (!(request.getParameter("amountUnit") == null || "".equals(request.getParameter("amountUnit")))) {
				invoiceInfo.setAmountUnit(Integer.parseInt(request.getParameter("amountUnit")));
			}
			
			Double productPrice = 0.0;
			if (!(request.getParameter("productPrice") == null || "".equals(request.getParameter("productPrice")))) {
				productPrice = Double.parseDouble(request.getParameter("productPrice"));
				invoiceInfo.setProductPrice(productPrice);
			}else{
				invoiceInfo.setProductPrice(0.0);
			}
			
			Double otherPrice = 0.0;
			if (!(request.getParameter("otherPrice") == null || "".equals(request.getParameter("otherPrice")))) {
				otherPrice = Double.parseDouble(request.getParameter("otherPrice"));
				invoiceInfo.setOtherPrice(otherPrice);
			}else{
				invoiceInfo.setOtherPrice(0.0);
			}
			
			Double shippingFee = 0.0;
			if (!(request.getParameter("shippingFee") == null || "".equals(request.getParameter("shippingFee")))) {
				shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
				invoiceInfo.setShippingFee(shippingFee);
			}else{
				invoiceInfo.setShippingFee(0.0);
			}
			
			
			Double moldPriceSum = 0.0;
			if (!(request.getParameter("moldPriceSum") == null || "".equals(request.getParameter("moldPriceSum")))) {
				moldPriceSum = Double.parseDouble(request.getParameter("moldPriceSum"));
				invoiceInfo.setMoldPrice(moldPriceSum);
			}else{
				invoiceInfo.setMoldPrice(0.0);
			}
			if (!(request.getParameter("comment") == null || "".equals(request.getParameter("comment")))) {
				invoiceInfo.setComment(request.getParameter("comment"));
			}
			String inRemark = null;
			if (!(request.getParameter("invoiceRemark") == null || "".equals(request.getParameter("invoiceRemark")))) {
				inRemark = request.getParameter("invoiceRemark");
			}	

			if (!(request.getParameter("paymentRemark") == null || "".equals(request.getParameter("paymentRemark")))) {
				paymentRemark = request.getParameter("paymentRemark");
			}
			
			Double amount = new BigDecimal(productPrice).add(new BigDecimal(otherPrice)).add(new BigDecimal(shippingFee)).add(new BigDecimal(moldPriceSum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			invoiceInfo.setAmount(amount);

			
			invoiceInfo.setCreateTime(DateFormat.format());
			invoiceInfo.setUpdateTime(DateFormat.format());
		    invoiceInfo.setInvoiceRemarkId(id);	

			// 获取invoice对应的产品数据
			// 批量插入invoice_product		
			String invoiceProduct = request.getParameter("invoiceProduct");

			String str = orderId.replace("SHS","");
			List<InvoiceInfo> invoiceInfos = invoiceInfoService.queryInvoiceByOrderId(orderId);
			if(invoiceInfos.size() == 0){ 
				invoiceId = "INV"+ str+"A";
			}else{
				String lastStrChange = "";
				int maxSum = 0;
				for (InvoiceInfo inv : invoiceInfos) {
					int sum = 0;
					if(!(inv.getInvoiceId() == null || "".equals(inv.getInvoiceId()))){
						String lastStr = inv.getInvoiceId().substring(inv.getInvoiceId().length()-1,inv.getInvoiceId().length());
						String sbu = UtilFuns.stringToAscii(lastStr);
					    sum = Integer.parseInt(sbu.toString());
					    if(sum >= maxSum){
					    	maxSum = sum;
					    }
					}
				}
				
				if(maxSum < 65){
				      maxSum = 65;
			    	  lastStrChange = UtilFuns.asciiToString(maxSum+"");
			       }else{
			    	  maxSum = maxSum+1; 
			    	  lastStrChange = UtilFuns.asciiToString(maxSum+"");
			       }					 
				invoiceId =  "INV"+ str+lastStrChange;				
			}
			invoiceInfo.setInvoiceId(invoiceId);			
			invoiceInfoService.insertInvoiceInfo(invoiceId, factoryId, orderId, invoiceInfo, inRemark, invoiceProduct, id, request, response,paymentRemark);			
			request.setAttribute("invoiceInfo", invoiceInfo);
			
			
			return new JsonResult<String>(0,"保存成功",invoiceId);			
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<String>(1,"保存失败");
		}

	}
	
   /**
    * 更新发票信息  invoiceInfo
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
	@ResponseBody
	@RequestMapping("/updateInvoiceInfos.do")
	public JsonResult<String> updateInvoiceInfos(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String invoiceId = null;
			Integer id = null;
			String paymentRemark = null;
			String inRemark = null;
			String factoryId = WebCookie.getFactoryId(request);
            if(factoryId == null || "".equals(factoryId)){
            	throw new RuntimeException("未获取到工厂id");
            }
	            
            if(!(request.getParameter("remarkId") == null || "".equals(request.getParameter("remarkId")))){
            	id = Integer.parseInt(request.getParameter("remarkId"));      	
            }    
			if (!(request.getParameter("invoiceId") == null || "".equals(request.getParameter("invoiceId")))) {
				invoiceId = request.getParameter("invoiceId");
			}else{				
				throw new Exception("发票号不存在!");
			}
			InvoiceInfo invoiceInfo = invoiceInfoService.queryByInvoiceId(invoiceId,factoryId);
			
			String orderId = null;
			ClientOrder clientOrder = new ClientOrder();
			if (request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))) {
				throw new Exception("未找到订单号");
			}else{
				orderId = request.getParameter("orderId");
				clientOrder = clientOrderService.queryByOrderId(orderId);
			}
			if (!(request.getParameter("amountUnit") == null || "".equals(request.getParameter("amountUnit")))) {
				invoiceInfo.setAmountUnit(Integer.parseInt(request.getParameter("amountUnit")));
			}
			
			Double productPrice = 0.0;
			if (!(request.getParameter("productPrice") == null || "".equals(request.getParameter("productPrice")))) {
				productPrice = Double.parseDouble(request.getParameter("productPrice"));
				invoiceInfo.setProductPrice(Double.parseDouble(request.getParameter("productPrice")));
			}
			Double otherPrice = 0.0;
			if (!(request.getParameter("otherPrice") == null || "".equals(request.getParameter("otherPrice")))) {
				otherPrice = Double.parseDouble(request.getParameter("otherPrice"));
				invoiceInfo.setOtherPrice(Double.parseDouble(request.getParameter("otherPrice")));
			}
			Double shippingFee = 0.0;
			if (!(request.getParameter("shippingFee") == null || "".equals(request.getParameter("shippingFee")))) {
				shippingFee = Double.parseDouble(request.getParameter("shippingFee"));
				invoiceInfo.setShippingFee(Double.parseDouble(request.getParameter("shippingFee")));
			}
			Double moldPriceSum = 0.0;
			if (!(request.getParameter("moldPriceSum") == null || "".equals(request.getParameter("moldPriceSum")))) {
				moldPriceSum = Double.parseDouble(request.getParameter("moldPriceSum"));
				invoiceInfo.setMoldPrice(Double.parseDouble(request.getParameter("moldPriceSum")));
			}else{
				invoiceInfo.setMoldPrice(0.0);
			}
			if (!(request.getParameter("comment") == null || "".equals(request.getParameter("comment")))) {
				invoiceInfo.setComment(request.getParameter("comment"));
			}
			if (!(request.getParameter("invoiceRemark") == null || "".equals(request.getParameter("invoiceRemark")))) {
				inRemark = request.getParameter("invoiceRemark");
			}
			if (!(request.getParameter("paymentRemark") == null || "".equals(request.getParameter("paymentRemark")))) {
				paymentRemark = request.getParameter("paymentRemark");
			}
			Double totalAmount = 0.0;
			if (!(request.getParameter("totalAmount") == null || "".equals(request.getParameter("totalAmount")))) {
				totalAmount = Double.parseDouble(request.getParameter("totalAmount"));
			}
			
			if(!(clientOrder.getOrderSource() == ORDER_SOURCE_ERP)){
//				Double amount = new BigDecimal(productPrice).add(new BigDecimal(otherPrice)).add(new BigDecimal(shippingFee)).add(new BigDecimal(moldPriceSum)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				invoiceInfo.setAmount(totalAmount);
			}
			
			
			invoiceInfo.setUpdateTime(DateFormat.format());
			invoiceInfo.setInvoiceRemarkId(id);	
			String invoiceProduct = request.getParameter("invoiceProduct");
		    invoiceInfoService.updateInvoiceInfo(factoryId, orderId, invoiceInfo, inRemark, invoiceProduct, id, request, response, paymentRemark);		    
			request.setAttribute("invoiceInfo", invoiceInfo);
			return new JsonResult<String>(0,"保存成功");
		} catch (Exception e) {
			logger.error("========updateInvoiceInfos========="+new String(e.getMessage().getBytes("iso-8859-1"),"utf-8"));
			e.printStackTrace();
			return new JsonResult<String>(1,"保存失败");
		}
		
	}
	
    /**
     * 查询发票信息
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
	@RequestMapping("/queryInvoiceInfo.do")
	public String queryInvoiceInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try {
			String factoryId = WebCookie.getFactoryId(request);
			String invoiceId = request.getParameter("invoiceId");
			invoiceId = Base64Encode.getFromBase64(invoiceId);
			InvoiceInfo invoiceInfo = invoiceInfoService.queryByInvoiceId(invoiceId,factoryId);
			ClientOrder clientOrder = clientOrderService.queryByOrderId(invoiceInfo.getOrderId());
			request.setAttribute("clientOrder", clientOrder);
			request.setAttribute("invoiceInfo", invoiceInfo);
			List<BankInfo> bankInfo = bankInfoService.queryBankInfo();
			request.setAttribute("bankInfo", bankInfo);
			
			List<PaymentPlan> paymentPlans = paymentPlanService.queryPaymentPlan(invoiceId);
			if( paymentPlans.size() != 0){
	        	for(int i = 0;i < paymentPlans.size(); i++){
	        		if(i == 0){
	        		request.setAttribute("amount1",paymentPlans.get(i).getAmount());
	        		request.setAttribute("paymentTime1",paymentPlans.get(i).getPredictPaymentTime());
	        		request.setAttribute("bank",paymentPlans.get(i).getPaymentBank());
	        		}
	        		if(i == 1){
	        			request.setAttribute("amount2",paymentPlans.get(i).getAmount());
	        			request.setAttribute("paymentTime2",paymentPlans.get(i).getPredictPaymentTime());
	        		}
	        		if(i == 2){
	        			request.setAttribute("amount3",paymentPlans.get(i).getAmount());
	        			request.setAttribute("paymentTime3",paymentPlans.get(i).getPredictPaymentTime());
	        		}
	        		if(i == 3){
	        			request.setAttribute("amount4",paymentPlans.get(i).getAmount());
	        			request.setAttribute("paymentTime4",paymentPlans.get(i).getPredictPaymentTime());
	        		}
	        		if(i == 4){
	        			request.setAttribute("amount5",paymentPlans.get(i).getAmount());
	        			request.setAttribute("paymentTime5",paymentPlans.get(i).getPredictPaymentTime());
	        		}
	        		if(i == 5){
	        			request.setAttribute("amount6",paymentPlans.get(i).getAmount());
	        			request.setAttribute("paymentTime6",paymentPlans.get(i).getPredictPaymentTime());
	        		}
	        	}
	        }

		} catch (Exception e) {
		  logger.error("======InvoiceInfoController  queryInvoiceInfo======="+e.getMessage());	
          e.printStackTrace();
          throw new Exception(e.getMessage());
		}

		return "upload_invoice.jsp";

	}

	/**
	 * 更新invoice_info表 transaction_type 批量插入payment_plan表 更新clientOrder
	 * invoice_path
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateInvoiceInfo.do")
	public String updateInvoiceInfo(HttpServletRequest request, HttpServletResponse response) {

		try {
			String factoryId = WebCookie.getFactoryId(request);
			String invoiceId = request.getParameter("invoiceId");
			if(invoiceId == null || "".equals(invoiceId)){
				throw new RuntimeException("invoiceId不能为空");
			}
			List<PaymentPlan> list = new ArrayList<PaymentPlan>();
			InvoiceInfo invoiceInfo = invoiceInfoService.queryByInvoiceId(invoiceId,factoryId);
			
			if (!(request.getParameter("transactionType") == null || "".equals(request.getParameter("transactionType")))) {
				invoiceInfo.setTransactionType(Integer.parseInt(request.getParameter("transactionType")));
				invoiceInfo.setUpdateTime(DateFormat.format());
			}
			String orderId = invoiceInfo.getOrderId();
			Integer bank = Integer.parseInt(request.getParameter("bank"));
			String amount1 = request.getParameter("amount1");
			String paymentTime1 = request.getParameter("paymentTime1");
			String amount2 = request.getParameter("amount2");
			String paymentTime2 = request.getParameter("paymentTime2");
			String amount3 = request.getParameter("amount3");
			String paymentTime3 = request.getParameter("paymentTime3");
			String amount4 = request.getParameter("amount4");
			String paymentTime4 = request.getParameter("paymentTime4");
			String amount5 = request.getParameter("amount5");
			String paymentTime5 = request.getParameter("paymentTime5");
			String amount6 = request.getParameter("amount6");
			String paymentTime6 = request.getParameter("paymentTime6");

			if (!(amount1 == null || "".equals(amount1) || paymentTime1 == null || "".equals(paymentTime1))) {
				PaymentPlan p1 = new PaymentPlan();
				p1.setInvoiceId(invoiceId);
				p1.setOrderId(orderId);
				p1.setPaymentBank(bank);
				p1.setPredictPaymentTime(paymentTime1);
				p1.setAmount(Double.parseDouble(amount1));
				list.add(p1);
			}
			if (!(amount2 == null || "".equals(amount2) || paymentTime2 == null || "".equals(paymentTime2))) {
				PaymentPlan p2 = new PaymentPlan();
				p2.setInvoiceId(invoiceId);
				p2.setOrderId(orderId);
				p2.setPaymentBank(bank);
				p2.setPredictPaymentTime(paymentTime2);
				p2.setAmount(Double.parseDouble(amount2));
				list.add(p2);
			}
			if (!(amount3 == null || "".equals(amount3) || paymentTime3 == null || "".equals(paymentTime3))) {
				PaymentPlan p3 = new PaymentPlan();
				p3.setInvoiceId(invoiceId);
				p3.setOrderId(orderId);
				p3.setPaymentBank(bank);
				p3.setPredictPaymentTime(paymentTime3);
				p3.setAmount(Double.parseDouble(amount3));
				list.add(p3);
			}
			if (!(amount4 == null || "".equals(amount4) || paymentTime4 == null || "".equals(paymentTime4))) {
				PaymentPlan p4 = new PaymentPlan();
				p4.setInvoiceId(invoiceId);
				p4.setOrderId(orderId);
				p4.setPaymentBank(bank);
				p4.setPredictPaymentTime(paymentTime4);
				p4.setAmount(Double.parseDouble(amount4));
				list.add(p4);
			}
			if (!(amount5 == null || "".equals(amount5) || paymentTime5 == null || "".equals(paymentTime5))) {
				PaymentPlan p5 = new PaymentPlan();
				p5.setInvoiceId(invoiceId);
				p5.setOrderId(orderId);
				p5.setPaymentBank(bank);
				p5.setPredictPaymentTime(paymentTime5);
				p5.setAmount(Double.parseDouble(amount5));
				list.add(p5);
			}
			if (!(amount6 == null || "".equals(amount6) || paymentTime6 == null || "".equals(paymentTime6))) {
				PaymentPlan p6 = new PaymentPlan();
				p6.setInvoiceId(invoiceId);
				p6.setOrderId(orderId);
				p6.setPaymentBank(bank);
				p6.setPredictPaymentTime(paymentTime6);
				p6.setAmount(Double.parseDouble(amount6));
				list.add(p6);
			}
			
			invoiceInfoService.updateInvoiceInfo(invoiceInfo);
			request.setAttribute("invoiceInfo", invoiceInfo);
			List<PaymentPlan> list1 = paymentPlanService.queryPaymentPlan(invoiceId);
			if(list1.size() != 0){
				paymentPlanService.deleteByInvoiceId(invoiceId);
			}	
			
			if(list.size() != 0){
			 paymentPlanService.insertPaymentPlans(list);
			}
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  updateInvoiceInfo======="+e.getMessage());	
			logger.equals(e.getMessage());
			e.printStackTrace();
		}		

		return "invoice.jsp";
	}

	/**
	 * 根据invoiceId获取发票信息展示
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/listInvoiceInfo.do")
	public String listInvoiceInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String factoryId = WebCookie.getFactoryId(request);
			String invoiceId = request.getParameter("invoiceId");
			invoiceId = Base64Encode.getFromBase64(invoiceId);
			InvoiceInfo invoiceInfo = invoiceInfoService.queryByInvoiceId(invoiceId,factoryId);
			List<PaymentPlan> list = paymentPlanService.queryPaymentPlan(invoiceId);
			
			//发票总额 （当前货币单元）
			Double productPrice = invoiceInfo.getProductPrice();
			Double otherPrice = invoiceInfo.getOtherPrice();
			Double moldPrice = invoiceInfo.getMoldPrice();
			Double shippingFee = invoiceInfo.getShippingFee();
			if(productPrice == null || "".equals(productPrice)){
				productPrice = 0.0;
			}
			if(otherPrice == null || "".equals(otherPrice)){
				otherPrice = 0.0;
			}
			if(moldPrice == null || "".equals(moldPrice)){
				moldPrice = 0.0;
			}
			if(shippingFee == null || "".equals(shippingFee)){
				shippingFee = 0.0;
			}
			Double s = productPrice + otherPrice + moldPrice + shippingFee;
			Integer bank = 0;
			for (PaymentPlan paymentPlan : list) {
				bank = paymentPlan.getPaymentBank();
			}
				
			request.setAttribute("s", s);
			request.setAttribute("invoiceInfo", invoiceInfo);
			BankInfo bankInfo = bankInfoService.queryById(bank);
			request.setAttribute("bankInfo", bankInfo);
			AmountUnit amountUnit = amountUnitService.queryById(invoiceInfo.getAmountUnit());
			request.setAttribute("amountUnit", amountUnit);

			// 查询最晚付款计划
			List<PaymentPlan> paymentPlans = paymentPlanService.queryPaymentPlan(invoiceId);
			request.setAttribute("paymentPlans", paymentPlans);
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  listInvoiceInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return "invoice.jsp";
	}

	
	  //用于更新发票pdf信息
	@ResponseBody
	@RequestMapping("/updateInvoicePdf.do")
	public JsonResult<String> updateInvoicePdf(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			if(!(StringUtils.isBlank(request.getParameter("invoiceInfoId")))){
				Integer invoiceInfoId = Integer.parseInt(request.getParameter("invoiceInfoId"));
				InvoiceInfo invoiceInfo = invoiceInfoService.queryById(invoiceInfoId);
				String invoiceName = request.getParameter("invoiceName");
				String path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + invoiceInfo.getOrderId() + File.separator;
				 File file = new File(path);
				 if  (!file.exists()  && !file .isDirectory())      
				 {         
					 file .mkdir();     
				 }  	    	  
				 Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
				if(!(multiFileUpload == null || multiFileUpload.size() == 0)){
					String invoicePath = multiFileUpload.get(invoiceName);
					invoiceInfo.setInvoicePath(invoicePath);
					invoiceInfoService.updateInvoiceInfo(invoiceInfo);
				}
				return new JsonResult<String>(0,"success");	 
			}else{
				logger.info(">>>>>>>>>>>>>>>>>InvoiceInfoController_updateInvoicePdf_exception--:invoice不能为空");
				return new JsonResult<String>(1,"failed");	
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
	 		logger.info(">>>>>>>>>>>>>>>>>InvoiceInfoController_updateInvoicePdf_exception--:--"+e);
	 		return new JsonResult<String>(1,"failed");	
		} catch (IllegalStateException e) {
			e.printStackTrace();
	 		logger.info(">>>>>>>>>>>>>>>>>InvoiceInfoController_updateInvoicePdf_exception--:--"+e);
	 		return new JsonResult<String>(1,"failed");	
		} catch (IOException e) {
			e.printStackTrace();
	 		logger.info(">>>>>>>>>>>>>>>>>InvoiceInfoController_updateInvoicePdf_exception--:--"+e);
	 		return new JsonResult<String>(1,"failed");	
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	@Transactional(rollbackFor=Exception.class)
	@RequestMapping("/deleteInvoice.do")
	public void deleteInvoice(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String id = request.getParameter("id");
			if (id == null || "".equals(id)) {
				throw new Exception("获取发票Id失败");
			}
			String orderId = request.getParameter("orderId");
			if(!(orderId == null || "".equals(orderId))){
			invoiceInfoService.deleteInvoiceById(Integer.valueOf(id));			
			}			
			String invoiceId = request.getParameter("invoiceId");
			if(!(invoiceId == null || "".equals(invoiceId))){
			invoiceProductService.deleteByInvoiceId(invoiceId);
			}
			
		} catch (Exception e) {
			logger.error("======InvoiceInfoController  listInvoiceInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}
	
	/**
	 * 查询汇率
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping("/queryExchangeRate.do")
	public JsonResult<Double> queryExchangeRate(HttpServletRequest request, HttpServletResponse response) {
		String amountUnit = request.getParameter("amountUnit");
		if(amountUnit == null || "".equals(amountUnit)){
			throw new RuntimeException("金额单元错误！");
		}
		Double exchangeRate = 1.0;
		if(!(amountUnit == null || "".equals(amountUnit))){
			AmountUnit amountUnit2 = amountUnitService.queryById(Integer.parseInt(amountUnit));
			exchangeRate = amountUnit2.getExchangeRate();
			
			request.setAttribute("exchangeRate", exchangeRate);
		}
		
		return new JsonResult<Double>(exchangeRate);
	}
	
	
	/**
	 * 根据id下载pdf信息
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "fileDownload_pdf.do")
	public void fileDownloadPdf(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 Integer id = null;

    	try {
   		    String factoryId = WebCookie.getFactoryId(request);
   		    if(factoryId == null || "".equals(factoryId)){
   		    	throw new RuntimeException("未获取到工厂信息");	
   		    }
    		if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
    			throw new RuntimeException("未获取到ID信息");
    		}else{
    			id = Integer.parseInt(request.getParameter("id"));
    		}
			InvoiceInfo invoiceInfo = invoiceInfoService.queryById(id);
			
		    if(invoiceInfo.getInvoicePath().trim() == null || "".equals(invoiceInfo.getInvoicePath())){
		    	throw new RuntimeException("下载路径不存在");
		    }else{
		    	String fileName = invoiceInfo.getInvoicePath();
		    	OperationFileUtil.download(fileName);
		    }
		} catch (Exception e) {
            logger.error(e.getMessage());
			e.printStackTrace();
			throw new Exception();
		}
				
	}
	
	
	/**
	 * 获取银行付款备注信息
	 * @author polo
	 * 2017年5月4日
	 */
	@ResponseBody
	@RequestMapping("/queryInvoiceRemark.do")
	public JsonResult<InvoiceRemark> queryInvoiceRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer id = null;
		try {
			id = null;
			if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
				throw new RuntimeException("未获取到ID信息");
			}else{
				id = Integer.parseInt(request.getParameter("id"));
			}
			InvoiceRemark invoiceRemark = invoiceRemarkService.queryById(id);			
	        return new JsonResult<InvoiceRemark>(invoiceRemark);			
		} catch (Exception e) {
			 logger.error("========queryInvoiceRemark======="+e.getMessage());
			e.printStackTrace();
			return new JsonResult<InvoiceRemark>(1,"获取失败");
		}
		

		
	}
	
	
	
	
	
	

}
