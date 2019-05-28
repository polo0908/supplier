package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.ClientOrderPoDao;
import com.cbt.entity.ClientOrderPo;
import com.cbt.service.ClientOrderPoService;

@Service
public class ClientOrderPoServiceImpl implements ClientOrderPoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private ClientOrderPoDao clientOrderPoDao;
	
	@Override
	public List<ClientOrderPo> queryByClientOrderId(String orderId) {
		return clientOrderPoDao.queryByClientOrderId(orderId);
	}

	@Override
	public String queryById(Integer id) {
		return clientOrderPoDao.queryById(id);
	}

	@Override
	public void insertBatch(List<ClientOrderPo> list) {
		clientOrderPoDao.insertBatch(list);

	}

	@Override
	public void insert(ClientOrderPo clientOrderPo) {
		clientOrderPoDao.insert(clientOrderPo);

	}

	@Override
	public void deleteById(Integer id) {
		clientOrderPoDao.deleteById(id);
	}

}
