package com.cbt.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbt.entity.ClientOrderShippingDoc;
import com.cbt.service.ClientOrderShippingDocService;
import com.cbt.util.DateFormat;


/**
 * 同步工融出运单数据
 * @author polo
 * 2017年5月23日
 */
@Controller
@RequestMapping("/shippingPort")
public class ReceiveShippingDataPort {
	

	  
	  
	  public static Logger LOG = Logger.getLogger(ReceiveShippingDataPort.class);
	  
	  
	  @Resource
	  private ClientOrderShippingDocService clientOrderShippingDocService;

	  /**
	   * 接收工融出运单项目出运数据
	   * @author polo
	   * 2017年9月19日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/syncShippingDate.do")	  
	  public String syncShippingDate(HttpServletRequest request,HttpServletResponse response){

	    try {	  
	       String orderIds = request.getParameter("orderIds");
	       String saildate = request.getParameter("saildate");	
	       String arriveDate = request.getParameter("arriveDate");
	       String destinationPort = request.getParameter("destinationPort");
	       String blAvailableDate = null;
	       if(StringUtils.isNotBlank(saildate)){           			  
  			blAvailableDate = DateFormat.calDate(saildate,14);
  		   }			  
		  if(StringUtils.isNotBlank(arriveDate)){                
  			  int compare_state = DateFormat.compare_date(arriveDate,blAvailableDate);
  			  if(compare_state == -1){
  				  blAvailableDate = arriveDate;
  			  }
		  }
	       orderIds = orderIds.substring(0, orderIds.length() - 1);
	       String[] split = orderIds.split(",");
	       
	       //去除订单号重复的数据
	       List<String> list = new ArrayList<String>();
	        for (int i=0; i<split.length; i++) {
	            if(!list.contains(split[i])) {
	                list.add(split[i]);
	            }
	        }
	       int count = 0;	    
	       List<ClientOrderShippingDoc> shippingDocs = new ArrayList<ClientOrderShippingDoc>();
		   	for (String str : list) {
		  	  ClientOrderShippingDoc clientOrderShippingDoc = new ClientOrderShippingDoc();
	   		  String orderId = str; 
	   		  if(StringUtils.isNotBlank(orderId)){
	   			count = clientOrderShippingDocService.queryBySailDateAndOrderId(saildate, orderId);
	   			if(count == 0){
	   				clientOrderShippingDoc.setOrderId(orderId);
	   				clientOrderShippingDoc.setShippingStartDate(saildate);
	   				clientOrderShippingDoc.setShippingArrivalDate(arriveDate);
	   				clientOrderShippingDoc.setBLAvailableDate(blAvailableDate);
	   				clientOrderShippingDoc.setDestinationPort(destinationPort);
	   				shippingDocs.add(clientOrderShippingDoc);
	   			}
	   		  }
	   	   }	   	          
	   	  if(shippingDocs != null && shippingDocs.size() != 0){
	   		clientOrderShippingDocService.insertBatch(shippingDocs);
	   		return "shipping date sync:success insert"; 
	   	  }else{
	   		return "shipping date sync:no order"; 
	   	  }	   	  
	  
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
            return "shipping date sync:failed insert"; 
        }

	  }
	  

	  
}
