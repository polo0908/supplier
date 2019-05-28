package com.cbt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.InvoiceProduct;
import com.cbt.entity.PaymentPlan;

public interface InvoiceInfoService {
   
    /**
     * 单个插入对象
     * @param InvoiceInfo
     */
     public void insertInvoiceInfo(String invoiceId,String factoryId,String orderId,InvoiceInfo invoiceInfo,
 			String inRemark,String invoiceProduct,Integer id,HttpServletRequest request,
 			HttpServletResponse response,String paymentRemark)throws Exception;
     
     /**
      * 根据发票编号查询发票详情
      * @param InvoiceId
      * @return
      */
     public InvoiceInfo queryByInvoiceId(String InvoiceId,String factoryId);
     
     /**
      * 根据id查询发票信息
      * @param id
      * @return
      */
     public InvoiceInfo queryById(Integer id);
     
     
     /**
      * 根据OrderId查询所有发票的信息
      * @param orderId
      * @return
      */
     List<InvoiceInfo> queryInvoiceByOrderId(String orderId);
     
     
	 /**
	  * 更新InvoiceInfo数据
	  * @param clientOrder
	  */
     public void updateInvoiceInfo(String factoryId,String orderId,InvoiceInfo invoiceInfo,String inRemark,String invoiceProduct,Integer id,
 			HttpServletRequest request, HttpServletResponse response,String paymentRemark)throws Exception;
     
     
     /**
	  * 更新InvoiceInfo数据
	  * @param clientOrder
	  */
     public void updateInvoiceInfo(InvoiceInfo invoiceInfo);
     
     
     /**
      * 根据发票的ID删除当前发票信息
      * @param id
      */
     public void deleteInvoiceById(Integer id);
     
     
     /**
      * 插入ERP导入数据
      */
     public void insertInvoiceInfo(InvoiceInfo invoiceInfo,PaymentPlan paymentPlan)throws Exception;
     
     
     
}
