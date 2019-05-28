package com.cbt.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.ClientDrawingsDao;
import com.cbt.dao.ClientOrderDao;
import com.cbt.dao.FactoryUserRelationDao;
import com.cbt.dao.MilestoneDao;
import com.cbt.dao.ReOrderDao;
import com.cbt.dao.RfqInfoDao;
import com.cbt.dao.SaleCustomerDao;
import com.cbt.dao.UpdateDrawingDao;
import com.cbt.dao.UserFactoryRelationDao;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.ClientOrderQuery;
import com.cbt.entity.ClientOrderType;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.Milestone;
import com.cbt.entity.ReOrder;
import com.cbt.entity.RfqInfo;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.entity.UpdateDrawing;
import com.cbt.entity.UserFactoryRelation;
import com.cbt.service.ClientOrderService;
import com.cbt.util.DateFormat;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.WebCookie;
@Service
public class ClientOrderServiceImpl implements ClientOrderService {

	
	private static final Integer ORDER_SOURCE = 3;   //订单来源
	
	@Resource
	private ClientOrderDao clientOrderDao;
	@Resource
	private RfqInfoDao rfqInfoDao;
	@Resource
	private ReOrderDao reOrderDao;
	@Resource
	private MilestoneDao milestoneDao;
	@Resource
	private SaleCustomerDao saleCustomerDao;
	@Resource
	private ClientDrawingsDao clientDrawingsDao;
	@Resource
	private UpdateDrawingDao updateDrawingDao;
	@Resource
	private FactoryUserRelationDao factoryUserRelationDao;
	@Resource
	private UserFactoryRelationDao userFactoryRelationDao;



	

	@Override
	public int totalAmount(ClientOrderQuery clientOrderQuery) {
		int totalAmount = clientOrderDao.totalAmount(clientOrderQuery);
		return totalAmount;
	}
	
	@Override
	public List<ClientOrderQuery> queryListByDate(ClientOrderQuery clientOrderQuery) {
		List<ClientOrderQuery> c = clientOrderDao.queryListByDate(clientOrderQuery);
		return c;
	}
	
	@Override
	public ClientOrder queryByOrderId(String orderId) {
		
		ClientOrder clientOrder = clientOrderDao.queryByOrderId(orderId);
		return clientOrder;
	}


	@Override
	public ClientOrder queryById(Integer id) {
		ClientOrder clientOrder = clientOrderDao.queryById(id);
		return clientOrder;
	}

	@Transactional
	@Override
	public void insertClientOrder(ClientOrder clientOrder,Milestone milestone,String reOrderId,
			String drawingId,String clientDrawings,HttpServletRequest request) throws Exception {
		
		
		//创建订单和销售关联关系
		SaleOrder saleOrder = new SaleOrder();
		List<SaleOrder> list = new ArrayList<SaleOrder>();
		clientOrderDao.insertClientOrder(clientOrder);	
		Integer permission = WebCookie.getPermission(request);
		String saleId = WebCookie.getUserId(request);
		if(permission != 1){
			int count = saleCustomerDao.queryCountBySaleAndOrder(clientOrder.getOrderId(), saleId);
			if(count == 0){
				saleOrder.setOrderId(clientOrder.getOrderId());
				saleOrder.setSalesId(saleId);
				list.add(saleOrder);
				saleCustomerDao.insertSaleOrderBatch(list);	
			}
		}
		
		//reOrderDrawing转历史订单时，批量插入到clientDrawing
		if(!(reOrderId == null || "".equals(reOrderId))){
			Integer id = Integer.parseInt(reOrderId); 
			ReOrder reOrder = reOrderDao.queryById(id);
			reOrder.setHistoryId(clientOrder.getId());
			reOrderDao.updateById(reOrder);    //更新reOrder表 对应历史订单id       	       	
			clientOrder.setOrderSource(1);
			clientOrderDao.updateClientOrder(clientOrder);    //更新clientOrder 来源        				
		}
		
		//新图纸询盘转历史订单时，更新图纸信息
		if(!(drawingId == null || "".equals(drawingId))){
			Integer id = Integer.parseInt(drawingId); 
			RfqInfo rfqInfo = rfqInfoDao.queryById(id);          
			rfqInfo.setHistoryId(clientOrder.getId());
			rfqInfoDao.updateById(rfqInfo);
			clientOrder.setOrderSource(2);           			
			clientOrderDao.updateClientOrder(clientOrder);    //更新clientOrder 来源
		}
	
		if(milestone != null){			
			milestoneDao.insert(milestone);
		}					  
		
		
		
		ClientDrawings clientDrawing = new ClientDrawings();
		UpdateDrawing updateDrawing = new UpdateDrawing();		 
		 String status = "Ordered";              //初始为报价状态
		 if(clientDrawings.endsWith(",")){
			 clientDrawings = clientDrawings.substring(0,clientDrawings.length()-1);
			}
		 
		 	  String[] split = clientDrawings.split(",");
		 	  
		    Double totalPrice = 0.0;	  
			for (int i = 0; i < split.length; i++) {
				String[] split2 = split[i].split("%");
				String productName = null;
				
				if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
					productName = split2[0].replaceFirst(".", "");
				}
				Double price = 0.00;
				if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
					price = Double.valueOf(split2[1].replaceFirst(".", ""));
				}
				Integer quantity = 0;
				if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
					quantity = Integer.valueOf(split2[2].replaceFirst(".", ""));	
				}
				Double mold = 0.00;
				if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
					mold = Double.valueOf(split2[3].replaceFirst(".", ""));
				}
				String drawingName = null;
				if(!(split2[4].replaceFirst(".", "") == null || "".equals(split2[4].replaceFirst(".", "")))){
					drawingName = split2[4].replaceFirst(".", "");					
				}			 
				totalPrice += new BigDecimal(price).multiply(new BigDecimal(quantity)).add(new BigDecimal(mold)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();				
				
				if(!(("".equals(productName) || productName == null)&& ("".equals(price) || price == null)
					 &&	("".equals(quantity) || quantity == null) && ("".equals(mold) || mold == null)
						&& ("".equals(drawingName) || drawingName == null))){
					clientDrawing.setDrawingsName(drawingName);
					clientDrawing.setDrawingsPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
					clientDrawing.setMoldPrice(mold);
					clientDrawing.setProductName(productName);
					clientDrawing.setQuantity(quantity);
					clientDrawing.setUnitPrice(price);
					clientDrawing.setOrderId(clientOrder.getOrderId());
					clientDrawing.setUserid(clientOrder.getUserid());
					clientDrawing.setQuoteTime(clientOrder.getCreateTime());
					clientDrawing.setUpdateTime(DateFormat.format());
					clientDrawing.setStatus(status);
					if( drawingName != null || "".equals(drawingName)){
						clientDrawing.setDrawingState("YES");
					}else{
						clientDrawing.setDrawingState("NO");
					}
								
					  clientDrawingsDao.insertClientDrawing(clientDrawing);		
		  					  
						//插入更新的图纸数据（update_drawing）
						if( drawingName != null || "".equals(drawingName)){
							updateDrawing.setDrawingName(drawingName);
							updateDrawing.setDrawingPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
							updateDrawing.setUpdateTime(DateFormat.format());
							updateDrawing.setDrawingId(clientDrawing.getId());			  
							updateDrawingDao.insertUpdateDrawing(updateDrawing);	
						}
					  	  
				}
			}
		
			  if(!(ORDER_SOURCE.equals(clientOrder.getOrderSource()))){
				  List<ClientDrawings> clientDrawingss = clientDrawingsDao.queryListByOrderId(clientOrder.getOrderId());
				  Double amount = 0.0;
				  for (ClientDrawings clientDrawings2 : clientDrawingss) {
					  amount += new BigDecimal(clientDrawings2.getUnitPrice()).multiply(new BigDecimal(clientDrawings2.getQuantity()))
							  .add(new BigDecimal(clientDrawings2.getMoldPrice())).doubleValue();
				  }
				  amount = new BigDecimal(amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				  clientOrder.setAmount(amount);
				  clientOrderDao.updateClientOrder(clientOrder);	 
			  }
		
		
		
		
		
		
		
		
		
		
		
		
	}

	@Override
	public void insertClientOrders(List<Object> list) {
		clientOrderDao.insertClientOrders(list);
		
	}

	@Transactional
	@Override
	public void updateClientOrder(ClientOrder clientOrder) {
		clientOrderDao.updateClientOrder(clientOrder);
		
	}

	@Override
	public int totalAmountAdmin(ClientOrderQuery clientOrderQuery) {
		return clientOrderDao.totalAmountAdmin(clientOrderQuery);
	}

	@Override
	public List<ClientOrderQuery> queryListByDateAdmin(
			ClientOrderQuery clientOrderQuery) {

		return clientOrderDao.queryListByDateAdmin(clientOrderQuery);
	}


    /**
     * 更新clientOrder数据（主要用于更新出货时间、海运提单时间、申报时间）
     * @param clientOrder
     */
	@Transactional
	@Override
	public void updateClientOrder(ClientOrder clientOrder, Milestone milestone3)throws Exception {
		
		if(!(clientOrder.getCreateTime() == null || "".equals(clientOrder.getCreateTime()))){
			Milestone milestone1 = milestoneDao.queryByName(clientOrder.getOrderId(), "Start", 1);
			if(!(milestone1 == null || "".equals(milestone1))){
				milestone1.setMilestoneDate(clientOrder.getCreateTime());
				milestoneDao.updateMilestone(milestone1);
			}else{
				Milestone m = new Milestone();
				m.setMilestoneDate(clientOrder.getCreateTime());
				m.setCreateTime(DateFormat.format());
				m.setDelayStatus(0);
				m.setProductionPhotoStatus(0);
				m.setMilestoneName("Start");
				m.setExpectedOrActually(1);
				m.setOrderId(clientOrder.getOrderId());
				milestoneDao.insert(m);
			}
		}
		if(!(clientOrder.getDeliveryTime() == null || "".equals(clientOrder.getDeliveryTime()))){
			Milestone milestone2 = milestoneDao.queryByName(clientOrder.getOrderId(), "Finished" , 0);
			if(milestone2 != null){
				milestone2.setMilestoneDate(clientOrder.getDeliveryTime());
				milestoneDao.updateMilestone(milestone2);
			}else{
				milestoneDao.insert(milestone3);
			}
		}
		if(!(clientOrder.getOutputTime() == null || "".equals(clientOrder.getOutputTime()))){
			
	        Milestone milestone =  milestoneDao.queryByName(clientOrder.getOrderId(), "Finished", 1);
//			clientOrder.setISFDate(DateFormat.calDate(clientOrder.getOutputTime(),2));
//			clientOrder.setBLAvailableDate(DateFormat.calDate(clientOrder.getOutputTime(),14));
			
			if(milestone == null || "".equals(milestone)){
		        Milestone milestone11 = new Milestone();
				milestone11.setMilestoneDate(clientOrder.getOutputTime());
				milestone11.setCreateTime(DateFormat.format());
				milestone11.setDelayStatus(0);
				milestone11.setProductionPhotoStatus(0);
				milestone11.setMilestoneName("Finished");
				milestone11.setOrderId(clientOrder.getOrderId());
				milestone11.setExpectedOrActually(1);
	     		milestoneDao.insert(milestone11); 
	     	 }else{
	     		milestone.setMilestoneDate(clientOrder.getOutputTime());
	     		milestoneDao.updateMilestone(milestone);
	     	 }
		}
//		if(!(clientOrder.getArrivalDate() == null || "".equals(clientOrder.getArrivalDate()))){
//			String arrivalDate = clientOrder.getArrivalDate();
//			String BLAvailableDate = clientOrder.getBLAvailableDate();
//			int compare_state = DateFormat.compare_date(arrivalDate,BLAvailableDate);
//			if(compare_state == -1){
//				clientOrder.setBLAvailableDate(arrivalDate);
//			}else{
//				clientOrder.setBLAvailableDate(BLAvailableDate);
//			}
//		}
		clientOrderDao.updateClientOrder(clientOrder);
		
		
	}

	@Override
	public void insertClientOrder(ClientOrder clientOrder) {
		clientOrderDao.insertClientOrder(clientOrder);
		
	}

	
	@Transactional
	@Override
	public void insertClientOrder(ClientOrder clientOrder,String orderId,List<SaleCustomer> list,FactoryUserRelation factoryUserRelation,UserFactoryRelation userFactoryRelation,List<SaleOrder> list3) throws Exception{
		
		 ClientOrder clientOrder1 = clientOrderDao.queryByOrderId(orderId);
		 if(clientOrder1 == null || "".equals(clientOrder1)){
			 clientOrderDao.insertClientOrder(clientOrder);
		 }
		 if(list.size() != 0){
	         saleCustomerDao.insertBatch(list); 
		 }
         if(!(factoryUserRelation == null || "".equals(factoryUserRelation))){
        	 factoryUserRelationDao.insert(factoryUserRelation);
         }
         if(!(userFactoryRelation == null || "".equals(userFactoryRelation))){
        	 userFactoryRelationDao.insert(userFactoryRelation);
         }
         if(list3.size() != 0){
        	 saleCustomerDao.insertSaleOrderBatch(list3);
         }
		 
	}

	@Override
	public List<ClientOrderQuery> queryListByDateOrderByUser(
			ClientOrderQuery clientOrderQuery) {
		return clientOrderDao.queryListByDateOrderByUser(clientOrderQuery);
	}

	@Override
	public List<ClientOrderQuery> queryListByDateAdminOrderByUser(
			ClientOrderQuery clientOrderQuery) {
		return clientOrderDao.queryListByDateAdminOrderByUser(clientOrderQuery);
	}

	
	@Transactional
	@Override
	public void insertClientOrder(List<Object> clientOrders,List<SaleCustomer> list) throws Exception {
		clientOrderDao.insertClientOrders(clientOrders);
		saleCustomerDao.insertBatch(list);
		
	}

	@Override
	public void insertSaleCustomer(List<SaleCustomer> list) throws Exception {
		saleCustomerDao.insertBatch(list);		
	}

	@Override
	public List<ClientOrderType> queryAllType() {
		
		return clientOrderDao.queryAllType();
	}

	@Override
	public ClientOrderType queryTypeById(Integer id) {
		return clientOrderDao.queryTypeById(id);
	}

	
	
	@Transactional
	@Override
	public void insertClientOrder(ClientOrder clientOrder, String orderId,
			List<SaleCustomer> list, FactoryUserRelation factoryUserRelation,
			UserFactoryRelation userFactoryRelation, List<SaleOrder> list3,
			ClientDrawings clientDrawing) throws Exception {
		
		 
		 ClientOrder clientOrder1 = clientOrderDao.queryByOrderId(orderId);
		 if(clientOrder1 == null || "".equals(clientOrder1)){
			 clientOrderDao.insertClientOrder(clientOrder);
		 }
		 if(list.size() != 0){
	         saleCustomerDao.insertBatch(list); 
		 }
         if(!(factoryUserRelation == null || "".equals(factoryUserRelation))){
        	 factoryUserRelationDao.insert(factoryUserRelation);
         }
         if(!(userFactoryRelation == null || "".equals(userFactoryRelation))){
        	 userFactoryRelationDao.insert(userFactoryRelation);
         }
         if(list3.size() != 0){
        	 saleCustomerDao.insertSaleOrderBatch(list3);
         }
         clientDrawingsDao.insertClientDrawing(clientDrawing);
         
	}








}
