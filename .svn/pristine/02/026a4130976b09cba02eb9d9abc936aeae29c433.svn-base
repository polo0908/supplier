package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cbt.dao.ClientOrderQcReportDao;
import com.cbt.entity.ClientOrderQcReport;
import com.cbt.service.ClientOrderQcReportService;
import com.cbt.util.OperationFileUtil;


@Service
public class ClientOrderQcReportServiceImpl implements ClientOrderQcReportService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
    private ClientOrderQcReportDao clientOrderQcReportDao;
	
	@Override
	public List<ClientOrderQcReport> queryByClientOrderId(String orderId) {		
		return clientOrderQcReportDao.queryByClientOrderId(orderId);
	}

	@Override
	public void insertBatch(List<ClientOrderQcReport> list) {
		clientOrderQcReportDao.insertBatch(list);

	}

	
	@Transactional
	@Override
	public void deleteById(Integer id) {
		String path = clientOrderQcReportDao.queryById(id);
		clientOrderQcReportDao.deleteById(id);
		OperationFileUtil.deleteFile(path);

	}

	@Override
	public String queryById(Integer id) {
		return clientOrderQcReportDao.queryById(id);
	}

	@Override
	public void insert(ClientOrderQcReport clientOrderQcReport) {
		clientOrderQcReportDao.insert(clientOrderQcReport);
		
	}

}
