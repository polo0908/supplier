package com.cbt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.InvoicePaymentRemarkDao;
import com.cbt.entity.InvoicePaymentRemark;
import com.cbt.service.InvoicePaymentRemarkService;

@Service
public class InvoicePaymentRemarkServiceImpl implements InvoicePaymentRemarkService {

	@Resource
	private InvoicePaymentRemarkDao invoicePaymentRemarkDao;
	
	@Override
	public InvoicePaymentRemark queryRemarkByFactoryId(String factoryId) {
		return invoicePaymentRemarkDao.queryRemarkByFactoryId(factoryId);
	}

	@Override
	public void updateRemarkByFactoryId(InvoicePaymentRemark invoicePaymentRemark) {
        invoicePaymentRemarkDao.updateRemarkByFactoryId(invoicePaymentRemark);
	}

}
