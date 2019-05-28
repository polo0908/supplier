package com.cbt.service.impl;

import java.util.List;








import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.PaymentPlanDao;
import com.cbt.entity.PaymentPlan;
import com.cbt.service.PaymentPlanService;

@Service
  
public class PaymentPlanServiceImpl implements PaymentPlanService {
   
	@Resource
	  private PaymentPlanDao paymentPlanDao;

	@Override
	public void insertPaymentPlan(PaymentPlan paymentPlan) {
		paymentPlanDao.insertPaymentPlan(paymentPlan);
		
	}

	@Override
	public void insertPaymentPlans(List<PaymentPlan> list) {
		paymentPlanDao.insertPaymentPlans(list);
		
	}

	@Override
	public List<PaymentPlan> queryPaymentPlan(String invoiceId) {
		List<PaymentPlan> list = paymentPlanDao.queryPaymentPlan(invoiceId);
		return list;
	}

	@Override
	public String queryLastPaymentTime(String invoiceId) {
		String lastPaymentTime = paymentPlanDao.queryLastPaymentTime(invoiceId);
		return lastPaymentTime;
	}

	@Override
	public void deleteByInvoiceId(String invoiceId) {
		paymentPlanDao.deleteByInvoiceId(invoiceId);
		
	}

	@Override
	public String selectFirstPayment(String orderId) {
		return paymentPlanDao.selectFirstPayment(orderId);
	}

	@Override
	public PaymentPlan queryByErpInvoiceId(Integer erpInvoiceId) {
		return paymentPlanDao.queryByErpInvoiceId(erpInvoiceId);
	}


	
	
	
	
	
}
