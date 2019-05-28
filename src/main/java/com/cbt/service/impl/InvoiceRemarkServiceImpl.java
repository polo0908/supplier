package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.InvoiceRemarkDao;
import com.cbt.entity.InvoiceRemark;
import com.cbt.service.InvoiceRemarkService;

@Service
public class InvoiceRemarkServiceImpl implements InvoiceRemarkService {
    
	@Resource
	private InvoiceRemarkDao invoiceRemarkDao;
	
	@Override
	public List<InvoiceRemark> queryRemarkByFactoryId(String factoryId) {
		return invoiceRemarkDao.queryRemarkByFactoryId(factoryId);
	}

	@Override
	public void updateRemarkByFactoryId(InvoiceRemark invoiceRemark) {
		invoiceRemarkDao.updateRemarkByFactoryId(invoiceRemark);

	}
	
	@Transactional
	@Override
	public void updateRemarkByFactoryId(Integer id,String remark)throws Exception {
		if(id == null || "".equals(id)){
			throw new Exception("未获取到ID");
		}else{
			InvoiceRemark invoiceRemark = invoiceRemarkDao.queryById(id);
			if(!(remark == null || "".equals(remark))){
				invoiceRemark.setRemark(remark);
				invoiceRemarkDao.updateRemarkByFactoryId(invoiceRemark);
			}
		}
		
	}

	@Override
	public InvoiceRemark queryById(Integer id) {
		return invoiceRemarkDao.queryById(id);
	}

}
