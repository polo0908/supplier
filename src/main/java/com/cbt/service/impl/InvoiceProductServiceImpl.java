package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.InvoiceProductDao;
import com.cbt.entity.InvoiceProduct;
import com.cbt.service.InvoiceProductService;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

	@Resource
	private InvoiceProductDao invoiceProductDao;
    
	@Transactional
	@Override
	public void insertInvoiceProduct(List<InvoiceProduct> list) {
		invoiceProductDao.insertInvoiceProduct(list);

	}
    
	@Override
	public List<InvoiceProduct> queryByInvoiceId(String invoiceId) {
		return invoiceProductDao.queryByInvoiceId(invoiceId);
	}

	@Override
	public InvoiceProduct queryById(String id) {
		return invoiceProductDao.queryById(id);
	}

	@Transactional
	@Override
	public void deleteByInvoiceId(String invoiceId) {
		invoiceProductDao.deleteByInvoiceId(invoiceId);
		
	}

	@Transactional
	@Override
	public void deleteById(Integer id) {
		invoiceProductDao.deleteById(id);
		
	}

}
