package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ReOrderDao;
import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderQuery;
import com.cbt.service.ReOrderService;

@Service
public class ReOrderServiceImpl implements ReOrderService {

	@Resource
	private ReOrderDao reOrderDao;

	@Override
	public int totalAmount(ReOrderQuery reOrderQuery) {
		int total = reOrderDao.totalAmount(reOrderQuery);
		return total;
	}

	@Override
	public List<ReOrderQuery> queryAllReOrder(ReOrderQuery reOrderQuery) {
		List<ReOrderQuery> list = reOrderDao.queryAllReOrder(reOrderQuery);
		return list;
	}

	@Override
	public ReOrder queryById(Integer id) {
		ReOrder reOrder = reOrderDao.queryById(id);
		return reOrder;
	}

	@Transactional
	@Override
	public void updateById(ReOrder reOrder) {
		reOrderDao.updateById(reOrder);
		
	}

	@Override
	public int totalAmountAdmin(ReOrderQuery reOrderQuery) {
		return reOrderDao.totalAmountAdmin(reOrderQuery);
	}

	@Override
	public List<ReOrderQuery> queryAllReOrderAdmin(ReOrderQuery reOrderQuery) {
		return reOrderDao.queryAllReOrderAdmin(reOrderQuery);
	}
	
	

}
