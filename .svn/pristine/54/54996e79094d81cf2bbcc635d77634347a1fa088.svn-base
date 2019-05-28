package com.cbt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ClientDrawingsDao;
import com.cbt.dao.ClientOrderDao;
import com.cbt.dao.UpdateDrawingDao;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.UpdateDrawing;
import com.cbt.service.ClientDrawingsService;

@Service
public class ClientDrawingsServiceImpl implements ClientDrawingsService {

	
	private static final Integer ORDER_SOURCE = 3;
	@Resource
	private ClientDrawingsDao clientDrawingsDao;
	@Resource
	private UpdateDrawingDao updateDrawingDao;
	@Resource
	private ClientOrderDao clientOrderDao;

	@Transactional
	@Override
	public List<ClientDrawings> updateClientDrawings(ClientDrawings clientDrawing,UpdateDrawing updateDrawing)throws Exception {
		

		  clientDrawingsDao.updateClientDrawings(clientDrawing);		
		  //如果上传图纸，更新图纸
		  if(!(updateDrawing == null || "".equals(updateDrawing))){
			  updateDrawingDao.insertUpdateDrawing(updateDrawing);
		  }
		  
		  return clientDrawingsDao.queryListByOrderId(clientDrawing.getOrderId());

	}

	@Override
	public List<ClientDrawings> queryListByOrderId(String orderId) {
	    List<ClientDrawings> clientDrawings = clientDrawingsDao.queryListByOrderId(orderId);
		return clientDrawings;
	}

	@Transactional
	@Override
	public void insertClientDrawings(List<Object> list) {
		clientDrawingsDao.insertClientDrawings(list);
		
	}

	
	@Transactional
	@Override
	public List<ClientDrawings> insertClientDrawing(ClientDrawings clientDrawing, UpdateDrawing updateDrawing,String orderId) throws Exception{
	   
	  clientDrawingsDao.insertClientDrawing(clientDrawing);		
	  //插入图纸更新信息
	  updateDrawing.setDrawingId(clientDrawing.getId());		  
	  updateDrawingDao.insertUpdateDrawing(updateDrawing);	  
	  
	  //更新订单价格
	  ClientOrder clientOrder = clientOrderDao.queryByOrderId(orderId);
	  if(!(ORDER_SOURCE.equals(clientOrder.getOrderSource()))){
		  List<ClientDrawings> clientDrawings = clientDrawingsDao.queryListByOrderId(orderId);
		  Double amount = 0.0;
		  for (ClientDrawings clientDrawings2 : clientDrawings) {
			  amount += new BigDecimal(clientDrawings2.getUnitPrice()).multiply(new BigDecimal(clientDrawings2.getQuantity()))
					  .add(new BigDecimal(clientDrawings2.getMoldPrice())).doubleValue();
		  }
		  amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		  clientOrder.setAmount(amount);
		  clientOrderDao.updateClientOrder(clientOrder);	 
	  }
	  	  
	  
	  return clientDrawingsDao.queryListByOrderId(orderId);	
		
	}
    
	@Transactional
	@Override
	public List<ClientDrawings> deleteClientDrawing(Integer id,String orderId)throws Exception {
		clientDrawingsDao.deleteClientDrawing(id);
		updateDrawingDao.deleteById(id);
		
		 //更新订单价格
		 ClientOrder clientOrder = clientOrderDao.queryByOrderId(orderId);
		 if(!(ORDER_SOURCE.equals(clientOrder.getOrderSource()))){
		 List<ClientDrawings> clientDrawings = clientDrawingsDao.queryListByOrderId(orderId);
		  Double amount = 0.0;
		  for (ClientDrawings clientDrawings2 : clientDrawings) {
			  amount += new BigDecimal(clientDrawings2.getUnitPrice()).multiply(new BigDecimal(clientDrawings2.getQuantity()))
					  .add(new BigDecimal(clientDrawings2.getMoldPrice())).doubleValue();
		  }
		  amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		  clientOrder.setAmount(amount);
		  clientOrderDao.updateClientOrder(clientOrder);	
		 }
		  return clientDrawingsDao.queryListByOrderId(orderId);
	}

	@Override
	public ClientDrawings queryById(Integer id) {
		ClientDrawings clientDrawings = clientDrawingsDao.queryById(id);
		return clientDrawings;
	}

	@Transactional
	@Override
	public void insertDrawings(ArrayList<Map<Object, Object>> ArrayList) {
		clientDrawingsDao.insertDrawings(ArrayList);
		
	}

	@Transactional
	@Override
	public void insertClientDrawing(ClientDrawings clientDrawing) {

		 clientDrawingsDao.insertClientDrawing(clientDrawing);;
	}

}
