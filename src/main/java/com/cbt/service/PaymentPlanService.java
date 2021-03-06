package com.cbt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cbt.entity.PaymentPlan;

public interface PaymentPlanService {
   
	  
    /**
     * 单个插入对象
     * @param PaymentPlan
     */
     void insertPaymentPlan(PaymentPlan paymentPlan);
     
     
     /**
      * 批量插入对象
      * @param ArrayList
      */
    void insertPaymentPlans(List<PaymentPlan> list); 
    
    /**
     * 根据invoiceId查询到账计划
     * @param invoiceId
     * @return
     */
    public List<PaymentPlan> queryPaymentPlan(String invoiceId);
    
    /**
     * 查询付款计划最晚时间
     */   
    public String queryLastPaymentTime(String invoiceId);
    
    /**
     * 根据invoiceId删除付款计划
     * @param invoiceId
     */
    public void deleteByInvoiceId(String invoiceId);
    
    
    /**
     * 查询第一次付款时间
     * @param orderId
     * @return
     */
   public String selectFirstPayment(String orderId);
   
   
   /**
    * 根据erpid查询
    * @Title queryByErpInvoiceId 
    * @Description TODO
    * @param erpInvoiceId
    * @return
    * @return PaymentPlan
    */
   public PaymentPlan queryByErpInvoiceId(Integer erpInvoiceId);
}
