package com.cbt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ReOrderDrawingsDao;
import com.cbt.entity.ReOrderDrawings;
import com.cbt.service.ReOrderDrawingsService;

@Service
public class ReOrderDrawingsServiceImpl implements ReOrderDrawingsService {
    @Resource
    private ReOrderDrawingsDao reOrderDrawingsDao;

	@Override
	public List<ReOrderDrawings> queryReOrderDrawings(Integer reOrderId) {
		List<ReOrderDrawings> list = reOrderDrawingsDao.queryReOrderDrawings(reOrderId);
		return list;
	}

	@Override
	public ReOrderDrawings queryById(Integer id) {
		ReOrderDrawings reOrderDrawing = reOrderDrawingsDao.queryById(id);
		return reOrderDrawing;
	}

	@Override
	public List<ReOrderDrawings> queryByIds(Integer[] ids) {
		List<ReOrderDrawings> list = reOrderDrawingsDao.queryByIds(ids);
		return list;
	}

	@Transactional
	@Override
	public void insertReOrderDrawings(List<ReOrderDrawings> list) {
		reOrderDrawingsDao.insertReOrderDrawings(list);
		
	}

	
	@Transactional
	@Override
	public void insertReOrderDrawing(ReOrderDrawings reOrderDrawings) {
		reOrderDrawingsDao.insertReOrderDrawing(reOrderDrawings);
		
	}

	@Transactional
	@Override
	public void deleteReOrderDrawing(Integer id) {
		reOrderDrawingsDao.deleteReOrderDrawing(id);
		
	}

	@Transactional
	@Override
	public void updateReOrderDrawing(ReOrderDrawings reOrderDrawings) {
		reOrderDrawingsDao.updateReOrderDrawing(reOrderDrawings);
		
	}

    
    

}
