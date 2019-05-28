package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.SaleCustomerDao;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.service.SaleCustomerService;

@Service
public class SaleCustomerServiceImpl implements SaleCustomerService {
    
	@Resource
	private SaleCustomerDao saleCustomerDao;
	@Override
	public List<SaleCustomer> queryBySalesId(String salesId) {
		return saleCustomerDao.queryBySalesId(salesId);
	}
	@Override
	public int queryCountBySaleAndCustomer(String salesId, String userid) {
		return saleCustomerDao.queryCountBySaleAndCustomer(salesId, userid);
	}
	@Override
	public void insert(SaleCustomer saleCustomer) {
		saleCustomerDao.insert(saleCustomer);
		
	}
	@Override
	public void update(SaleCustomer saleCustomer) {
		saleCustomerDao.update(saleCustomer);
		
	}
	@Override
	public SaleCustomer queryBySaleAndCustomer(String salesId, String userid) {
		return saleCustomerDao.queryBySaleAndCustomer(salesId, userid);
	}
	@Override
	public void insertSaleOrderBatch(List<SaleOrder> list) {
		saleCustomerDao.insertSaleOrderBatch(list);
		
	}
	@Override
	public int queryCountBySaleAndOrder(String salesId, String orderId) {
		return saleCustomerDao.queryCountBySaleAndOrder(salesId, orderId);
	}
	@Override
	public SaleOrder queryBySaleAndOrder(String salesId, String orderId) {		
		return saleCustomerDao.queryBySaleAndOrder(salesId, orderId);
	}
	@Override
	public void updateSaleOrder(SaleOrder saleOrder) {
		saleCustomerDao.updateSaleOrder(saleOrder);
		
	}

}
