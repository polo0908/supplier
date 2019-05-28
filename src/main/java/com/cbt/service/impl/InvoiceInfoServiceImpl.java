package com.cbt.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.AmountUnitDao;
import com.cbt.dao.ClientOrderDao;
import com.cbt.dao.FactoryInfoDao;
import com.cbt.dao.InvoiceInfoDao;
import com.cbt.dao.InvoicePaymentRemarkDao;
import com.cbt.dao.InvoiceProductDao;
import com.cbt.dao.InvoiceRemarkDao;
import com.cbt.dao.PaymentPlanDao;
import com.cbt.dao.ShippingInfoDao;
import com.cbt.dao.UserDao;
import com.cbt.entity.AmountUnit;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryInfo;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.InvoicePaymentRemark;
import com.cbt.entity.InvoiceProduct;
import com.cbt.entity.InvoiceRemark;
import com.cbt.entity.PaymentPlan;
import com.cbt.entity.ShippingInfo;
import com.cbt.entity.User;
import com.cbt.print.InvoicePrintTemplate;
import com.cbt.service.InvoiceInfoService;

@Service

public class InvoiceInfoServiceImpl implements InvoiceInfoService {
   
	private static final Integer ORDER_SOURCE = 3;
	
	@Resource
	private InvoiceInfoDao invoiceInfoDao;
	@Resource
	private AmountUnitDao amountUnitDao;
	@Resource
	private ClientOrderDao clientOrderDao;
	@Resource
	private InvoiceProductDao invoiceProductDao;
	@Resource
	private UserDao userDao;
	@Resource
	private ShippingInfoDao shippingInfoDao;
	@Resource
	private FactoryInfoDao factoryInfoDao;
	@Resource
	private InvoiceRemarkDao invoiceRemarkDao;
	@Resource
	private InvoicePaymentRemarkDao invoicePaymentRemarkDao;
	@Resource
	private PaymentPlanDao paymentPlanDao;

	
    /**
     * 根据发票id,工厂id,订单号  
     */
    @Transactional
	@Override
	public void insertInvoiceInfo(String invoiceId,String factoryId,String orderId,InvoiceInfo invoiceInfo,
			String inRemark,String invoiceProduct,Integer id,HttpServletRequest request, HttpServletResponse response,String paymentRemark)throws Exception{
		
		InvoiceRemark invoiceRemark = new InvoiceRemark();
		 if(id == null || "".equals(id)){
         	throw new RuntimeException("未获取到ID");
         }else{
         	invoiceRemark = invoiceRemarkDao.queryById(id);
         }
		 if(!(inRemark == null || "".equals(inRemark))){
			 invoiceRemark.setRemark(inRemark);	
			 invoiceRemarkDao.updateRemarkByFactoryId(invoiceRemark);
		 }		
		 
		 InvoicePaymentRemark invoicePaymentRemark = invoicePaymentRemarkDao.queryRemarkByFactoryId(factoryId);
		 invoicePaymentRemark.setPaymentRemark(paymentRemark);
		 invoicePaymentRemarkDao.updateRemarkByFactoryId(invoicePaymentRemark);
		 
		InvoiceInfo invoiceInfo1 = invoiceInfoDao.queryByInvoiceId(invoiceId,factoryId);
		if (invoiceInfo1 == null || "".equals(invoiceInfo1)) {
			invoiceInfoDao.insertInvoiceInfo(invoiceInfo);
		}else{
			throw new RuntimeException("invoiceId已存在");
		}	
		
		
		// 获取invoice对应的产品数据
		// 批量插入invoice_product
		List<InvoiceProduct> list = new ArrayList<InvoiceProduct>();
		if (invoiceProduct.endsWith(",")) {
			if(invoiceProduct.length()>1){
			 invoiceProduct = invoiceProduct.substring(0, invoiceProduct.length() - 1);
			 
			 String[] split = invoiceProduct.split(",");
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split("%");
					InvoiceProduct p = new InvoiceProduct();
					String productName = null;
					if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
						productName = split2[0].replaceFirst(".", "");
					}
					Double price = 0.00;
					if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
						price = Double.parseDouble(split2[1].replaceFirst(".", ""));
					}
					Integer quantity = 0;
					if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
						quantity = Integer.valueOf(split2[2].replaceFirst(".", ""));
					}
					Double moldPrice = 0.00;
					if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
						moldPrice = Double.parseDouble(split2[3].replaceFirst(".", ""));
					}
					p.setInvoiceId(invoiceId);
					p.setProductName(productName);
					p.setQuantity(quantity);
					p.setUnitPrice(price);
					p.setMoldPrice(moldPrice);
					list.add(p);
				}
			}
			
		}
		List<InvoiceProduct> invoiceProducts = invoiceProductDao.queryByInvoiceId(invoiceId);
		if(invoiceProducts.size() != 0){
		   invoiceProductDao.deleteByInvoiceId(invoiceId);	
		}	
		if(list.size() != 0){
			invoiceProductDao.insertInvoiceProduct(list);
		}	
		
		 invoiceProducts = invoiceProductDao.queryByInvoiceId(invoiceId);
        /*
         *根据发票生成pdf文件（现生成excel编辑、转成pdf jacob） 
         */
			ClientOrder clientOrder = clientOrderDao.queryByOrderId(orderId);
			String remark = invoiceRemark.getRemark();
			String remark1 = invoiceRemark.getRemark1();
			User user = userDao.queryByUserId(clientOrder.getUserid());
			ShippingInfo shippingInfo = shippingInfoDao.queryByUserId(clientOrder.getUserid());
			FactoryInfo factoryInfo = factoryInfoDao.queryByFactoryId(factoryId); 			
			String invoicePath = InvoicePrintTemplate.printExcel(request.getSession().getServletContext().getRealPath(File.separator),response,user,factoryInfo,shippingInfo,clientOrder,invoiceInfo,invoiceProducts,remark,remark1,paymentRemark);
			invoiceInfo.setInvoicePath(invoicePath);
			invoiceInfoDao.updateInvoiceInfo(invoiceInfo);		
	
	}

	@Override
	public InvoiceInfo queryByInvoiceId(String invoiceId,String factoryId) {
		InvoiceInfo invoiceInfo = invoiceInfoDao.queryByInvoiceId(invoiceId,factoryId);
		return invoiceInfo;
	}

	
	@Transactional
	@Override
	public void updateInvoiceInfo(String factoryId,String orderId,InvoiceInfo invoiceInfo,String inRemark,String invoiceProduct,Integer id,
			HttpServletRequest request, HttpServletResponse response,String paymentRemark)throws Exception{
		
			invoiceInfoDao.updateInvoiceInfo(invoiceInfo);
			
			InvoiceRemark invoiceRemark = new InvoiceRemark();
			 if(id == null || "".equals(id)){
	        	throw new RuntimeException("未获取到ID");
	        }else{
	        	invoiceRemark = invoiceRemarkDao.queryById(id);
	        }
			 if(!(inRemark == null || "".equals(inRemark))){
				 invoiceRemark.setRemark(inRemark);	
				 invoiceRemarkDao.updateRemarkByFactoryId(invoiceRemark);
			 }		
			 
			 InvoicePaymentRemark invoicePaymentRemark = invoicePaymentRemarkDao.queryRemarkByFactoryId(factoryId);
			 invoicePaymentRemark.setPaymentRemark(paymentRemark);
			 invoicePaymentRemarkDao.updateRemarkByFactoryId(invoicePaymentRemark);
		 		 			
			
		

		// 获取invoice对应的产品数据
		// 批量插入invoice_product
		List<InvoiceProduct> invoiceProducts = invoiceProductDao.queryByInvoiceId(invoiceInfo.getInvoiceId());
		if(invoiceProducts.size() != 0){
		   invoiceProductDao.deleteByInvoiceId(invoiceInfo.getInvoiceId());	
		}			
		List<InvoiceProduct> list1 = new ArrayList<InvoiceProduct>();
		//判断invoiceProduct不为空
		if(invoiceProduct.length() != 0){
		if (invoiceProduct.endsWith(",")) {
			invoiceProduct = invoiceProduct.substring(0, invoiceProduct.length() - 1);
		}

		String[] split = invoiceProduct.split(",");
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split("%");
			InvoiceProduct p = new InvoiceProduct();
			String productName = null;
			if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
				productName = split2[0].replaceFirst(".", "");
			}
			Double price = 0.00;
			if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
				price = Double.parseDouble(split2[1].replaceFirst(".", ""));
			}
			Integer quantity = 0;
			if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
				quantity = Integer.valueOf(split2[2].replaceFirst(".", ""));
			}
			Double moldPrice = 0.00;
			if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
				moldPrice = Double.parseDouble(split2[3].replaceFirst(".", ""));
			}
			p.setInvoiceId(invoiceInfo.getInvoiceId());
			p.setProductName(productName);
			p.setQuantity(quantity);
			p.setUnitPrice(price);
			p.setMoldPrice(moldPrice);
			list1.add(p);				
		   }
     }
		if(list1.size() != 0){
		   invoiceProductDao.insertInvoiceProduct(list1);	
		}		
		
		   /*
         *根据发票生成pdf文件（现生成excel编辑、转成pdf jacob） 
         */			
			ClientOrder clientOrder = clientOrderDao.queryByOrderId(orderId);
			String userid = clientOrder.getUserid();
			String remark = invoiceRemark.getRemark();
			String remark1 = invoiceRemark.getRemark1();
			User user = userDao.queryByUserId(userid);
			ShippingInfo shippingInfo = shippingInfoDao.queryByUserId(userid);
			FactoryInfo factoryInfo = factoryInfoDao.queryByFactoryId(factoryId); 			
			String invoicePath = InvoicePrintTemplate.printExcel(request.getSession().getServletContext().getRealPath(File.separator),response,user,factoryInfo,shippingInfo,clientOrder,invoiceInfo,invoiceProducts,remark,remark1,paymentRemark);		
			invoiceInfo.setInvoicePath(invoicePath);
			invoiceInfoDao.updateInvoiceInfo(invoiceInfo);			

	}

	@Override
	public List<InvoiceInfo> queryInvoiceByOrderId(String orderId) {
		return invoiceInfoDao.queryInvoiceByOrderId(orderId);
	}

	@Override
	public void deleteInvoiceById(Integer id) {
		invoiceInfoDao.deleteInvoiceById(id);
		
	}

	@Override
	public InvoiceInfo queryById(Integer id) {
		return invoiceInfoDao.queryById(id);
	}

	@Override
	public void updateInvoiceInfo(InvoiceInfo invoiceInfo) {
		invoiceInfoDao.updateInvoiceInfo(invoiceInfo);
		
	}

	
	@Transactional
	@Override
	public void insertInvoiceInfo(InvoiceInfo invoiceInfo,PaymentPlan paymentPlan) throws Exception {
		
		InvoiceInfo invoiceInfo2 = invoiceInfoDao.queryByInvoiceId(invoiceInfo.getInvoiceId(), invoiceInfo.getFactoryId());
		if(invoiceInfo2 == null || "".equals(invoiceInfo2)){
			invoiceInfoDao.insertInvoiceInfo(invoiceInfo);
		}else{
			invoiceInfo2.setAmountActual(invoiceInfo.getAmountActual());
			invoiceInfo2.setAmount(invoiceInfo.getAmount());
			invoiceInfoDao.updateInvoiceInfo(invoiceInfo2);
		}
		
		PaymentPlan payment = paymentPlanDao.queryByErpInvoiceId(paymentPlan.getErpInvoiceId());
		if(payment == null || "".equals(payment)){
			paymentPlanDao.insertPaymentPlan(paymentPlan);
		}else{
			paymentPlanDao.updatePaymentPlan(paymentPlan);
		}

	}

}
