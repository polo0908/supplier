package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ClientDrawingsDao;
import com.cbt.dao.RfqInfoDao;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.RfqInfo;
import com.cbt.entity.RfqInfoQuery;
import com.cbt.service.RfqInfoService;

@Service
public class RfqInfoServiceImpl implements RfqInfoService {

	@Resource
	private RfqInfoDao rfqInfoDao;
	@Resource
	private ClientDrawingsDao clientDrawingsDao;

	@Override
	public List<RfqInfoQuery> queryAllRfqInfo(RfqInfoQuery rfqInfoQuery) {
		List<RfqInfoQuery> list = rfqInfoDao.queryAllRfqInfo(rfqInfoQuery);
		return list;
	}

	@Override
	public int totalAmount(RfqInfoQuery rfqInfoQuery) {
		int total = rfqInfoDao.totalAmount(rfqInfoQuery);
		return total;
	}

	@Override
	public RfqInfo queryById(Integer id) {
		RfqInfo rfqInfo = rfqInfoDao.queryById(id);
		return rfqInfo;
	}
    
	@Transactional
	@Override
	public void updateById(RfqInfo rfqInfo,ClientDrawings clientDrawings)throws Exception{
		rfqInfoDao.updateById(rfqInfo);
		clientDrawingsDao.insertClientDrawing(clientDrawings);
	}

	@Override
	public List<RfqInfoQuery> queryAllRfqInfoAdmin(RfqInfoQuery rfqInfoQuery) {
		return rfqInfoDao.queryAllRfqInfoAdmin(rfqInfoQuery);
	}

	@Override
	public int totalAmountAdmin(RfqInfoQuery rfqInfoQuery) {
		return rfqInfoDao.totalAmountAdmin(rfqInfoQuery);
	}

	@Transactional
	@Override
	public void updateById(RfqInfo rfqInfo) {
		rfqInfoDao.updateById(rfqInfo);
	}




}
