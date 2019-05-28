package com.cbt.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ClientDrawingsDao;
import com.cbt.dao.UpdateDrawingDao;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.UpdateDrawing;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.UpdateDrawingService;

@Service
public class UpdateDrawingServiceImpl implements UpdateDrawingService {

	@Resource
	private UpdateDrawingDao updateDrawingDao;

	@Override
	public UpdateDrawing queryById(Integer id) {
		UpdateDrawing updateDrawing = updateDrawingDao.queryById(id);
		return updateDrawing;
	}

	@Override
	public List<UpdateDrawing> queryListByDrawingId(Integer drawingId) {
		List<UpdateDrawing> list = updateDrawingDao.queryListByDrawingId(drawingId);
		return list;
	}

	@Transactional
	@Override
	public void updateDrawing(UpdateDrawing updateDrawing) {
		updateDrawingDao.updateDrawing(updateDrawing);
		
	}

	@Transactional
	@Override
	public void insertUpdateDrawings(List<UpdateDrawing> list) {
		updateDrawingDao.insertUpdateDrawings(list);
		
	}
	
	
	@Transactional
	@Override
	public void insertUpdateDrawing(UpdateDrawing updateDrawing) {
		updateDrawingDao.insertUpdateDrawing(updateDrawing);
		
	}

	
	@Transactional
	@Override
	public void deleteById(Integer drawingId) {
		updateDrawingDao.deleteById(drawingId);
		
	}


}
