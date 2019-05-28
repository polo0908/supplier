package com.cbt.controller;
import java.util.ArrayList;
import java.util.List;


import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.entity.MessageCenter;
import com.cbt.entity.OrderMessage;
import com.cbt.entity.QualityIssuesPic;
import com.cbt.service.BackUserService;
import com.cbt.service.FactoryMessageService;
import com.cbt.service.MessageCenterService;
import com.cbt.service.OrderMessageService;
import com.cbt.service.QualityIssuesPicService;
import com.cbt.util.DateFormat;
import com.cbt.util.Md5Util;
import com.cbt.util.SecurityHelper;
import com.cbt.util.SplitPage3;
import com.cbt.util.WebCookie;

@Controller 

public class MessageCenterController {
  @Resource
  private MessageCenterService messageCenterService;
  @Resource
  private BackUserService backUserService;
  @Resource
  private OrderMessageService orderMessageService;
  @Resource
  private FactoryMessageService factoryMessageService;
  @Resource
  private QualityIssuesPicService qualityIssuesPicService;
  
  //静态常量（消息读取状态、（消息类型）工厂消息）
  private static final int UNREAD_STATUS = 0;
  private static final int FACTORY_MESSAGE = 2;
  private static final int PIC_STATUS = 1;
  
  public static final Logger LOG = Logger.getLogger(MessageCenterController.class); 
   
    
   /**
    * 查询未读消息数
    * @author polo
    * 2017年5月15日
    *
    */
   @ResponseBody
   @RequestMapping("/queryMessageCount.do")
   public Integer queryMessageCount(HttpServletRequest request,HttpServletResponse response){
	   
	   try {
	  	     String factoryId = WebCookie.getFactoryId(request);
	  		 String salesId = WebCookie.getUserId(request); 
	  		 Integer permission = 0;
	  		 int total = 0;
	  		 if(salesId == null || "".equals(salesId)){	  	            	
	  	            salesId = request.getParameter("backUserId");
		            BackUser user = backUserService.queryBackUserByUserId(salesId);
		            if(user == null || "".equals(user)){
		            	return 0;
		            }
		            // 保存cookie token
					String now = String.valueOf(System.currentTimeMillis());
					String token = Md5Util.encoder(now);
					Cookie tokenCookie = new Cookie("token1", now + "|" + token);
					tokenCookie.setPath("/");
					response.addCookie(tokenCookie);

					String userName = user.getUserName();
					permission = user.getPermission();
					if(permission == null){
						permission = 0;
					}
					factoryId = user.getFactoryId();
					if(factoryId == null){
						factoryId = "";
						LOG.info("factoryId:未获取到工厂信息");
					}
					String pwd = user.getPwd();
					String str = user.getBackUserid() + "&" + userName + "&" + permission + "&" + factoryId + "&" + pwd;
					
					 //客户登录信息存放到session
			  		 HttpSession session = request.getSession();
			  		 session.setAttribute("backuser",SecurityHelper.encrypt("backuser", str));
			  		 session.setMaxInactiveInterval(60*60*24*365);

			  		 session.setAttribute("permission",permission);
			  		 session.setMaxInactiveInterval(60*60*24*365);
					
			  		  //存放cookie
			   		  Cookie userCookie = new Cookie("backuser",SecurityHelper.encrypt("backuser", str));           
			   		  userCookie.setPath("/");
			   		  userCookie.setMaxAge(60*60*24*365);
			   		  response.addCookie(userCookie);
			   		  //存放用户权限
			   		  Cookie pCookie = new Cookie("permission",permission.toString());           
			   		  userCookie.setPath("/");
			   		  userCookie.setMaxAge(60*60*24*365);
			   		  response.addCookie(pCookie);
			   		  total = messageCenterService.total(salesId, factoryId, permission);
	  	            }else{
	  	 	 		 BackUser backUser = backUserService.queryBackUserByUserId(salesId);
	  			     permission = backUser.getPermission();		
	  			     total = messageCenterService.total(salesId, factoryId, permission);
	  	            }
 
		     
		     return total;   
	    } catch (Exception e) { 
	   	  e.printStackTrace();
	   	LOG.error("=========MessageCenterController =====queryMessageCount======"+e.getMessage());
	   	 return 0;
	   }    
    }
   
   /**
    * 查询消息
    * @author polo
    * 2017年5月15日
    *
    */
   @RequestMapping("/queryMessage.do")
   public String queryMessage(HttpServletRequest request,HttpServletResponse response){
	   
	   try {
	  	            String factoryId = WebCookie.getFactoryId(request);
	  	            String salesId = WebCookie.getUserId(request);
	  	            
	  	            if(salesId == null || "".equals(salesId)){	  	            	
	  	            salesId = request.getParameter("backUserId");
		            BackUser user = backUserService.queryBackUserByUserId(salesId);
		            if(user == null || "".equals(user)){
		            	return "";
		            }
		            // 保存cookie token
					String now = String.valueOf(System.currentTimeMillis());
					String token = Md5Util.encoder(now);
					Cookie tokenCookie = new Cookie("token1", now + "|" + token);
					tokenCookie.setPath("/");
					response.addCookie(tokenCookie);

					String userName = user.getUserName();
					Integer permission = user.getPermission();
					if(permission == null){
						permission = 0;
					}
					factoryId = user.getFactoryId();
					if(factoryId == null){
						factoryId = "";
						LOG.info("factoryId:未获取到工厂信息");
					}
					String pwd = user.getPwd();
					String str = user.getBackUserid() + "&" + userName + "&" + permission + "&" + factoryId + "&" + pwd;
					
					 //客户登录信息存放到session
			  		 HttpSession session = request.getSession();
			  		 session.setAttribute("backuser",SecurityHelper.encrypt("backuser", str));
			  		 session.setMaxInactiveInterval(60*60*24*365);

			  		 session.setAttribute("permission",permission);
			  		 session.setMaxInactiveInterval(60*60*24*365);
					
			  		  //存放cookie
			   		  Cookie userCookie = new Cookie("backuser",SecurityHelper.encrypt("backuser", str));           
			   		  userCookie.setPath("/");
			   		  userCookie.setMaxAge(60*60*24*365);
			   		  response.addCookie(userCookie);
			   		  //存放用户权限
			   		  Cookie pCookie = new Cookie("permission",permission.toString());           
			   		  userCookie.setPath("/");
			   		  userCookie.setMaxAge(60*60*24*365);
			   		  response.addCookie(pCookie);
		   
	  	            }
 		
	  	   String str1 = request.getParameter("page");
	  	   String str2 = request.getParameter("pageSize");
	  	   
		   if(factoryId == null || "".equals(factoryId)){
			   throw new RuntimeException("未获取到工厂ID");
		   }
		   if(salesId == null || "".equals(salesId)){
			   throw new RuntimeException("未获取到用户ID");
		   }	
		   int page = 1;
			if(str1 != null) {
				page = Integer.parseInt(str1);
			}
			int pageSize = 10;		
			if(str2 != null) {
				pageSize = Integer.parseInt(str2);
			}
	 		int start = (page-1) * pageSize;
		   		   
		   BackUser backUser = backUserService.queryBackUserByUserId(salesId);
		   Integer permission = backUser.getPermission();		   
		   List<MessageCenter> messageCenters = messageCenterService.queryMessageByUserId(salesId, factoryId, permission, start, pageSize);
		   List<Integer> counts = new ArrayList<Integer>();
		   List<List<OrderMessage>> orderMessages = new ArrayList<List<OrderMessage>>();
//		   List<List<List<QualityIssuesPic>>> qualityIssuesPicss = new ArrayList<List<List<QualityIssuesPic>>>();
//		   List<List<QualityIssuesPic>> qualityIssuesPics = new ArrayList<List<QualityIssuesPic>>();
//		   List<QualityIssuesPic> qualityIssuesPic = new ArrayList<QualityIssuesPic>();
				   
		   int count = 0;		   
		   for (MessageCenter messageCenter : messageCenters) {
			  if(!(messageCenter.getMessageType() == null || "".equals(messageCenter.getMessageType()))){
				  count = messageCenterService.totalByMessageType(messageCenter.getId(), messageCenter.getMessageType());
				  counts.add(count);
			  }
			  List<OrderMessage> orderMessage = orderMessageService.queryByMessageCenterId(messageCenter.getId());	
			  for (OrderMessage orderMessage2 : orderMessage) {
				  if(orderMessage2.getPicStatus() == PIC_STATUS){
					  orderMessage2.setQualityIssuesPic(qualityIssuesPicService.queryByOrderMessageId(orderMessage2.getId()));
				  }			  
			  }
			  orderMessages.add(orderMessage);
		   }
		   int total = 0;
		   if(permission == 1){
			   total = messageCenterService.totalMessageByFactoryIdAdmin(factoryId);
		   }else{
			   total = messageCenterService.totalMessageByFactoryId(salesId, factoryId);
		   }		   
		   SplitPage3.buildPager(request, total, pageSize, page);		   
           
		   request.setAttribute("messageCenters", messageCenters);
		   request.setAttribute("counts", counts);
		   request.setAttribute("orderMessages", orderMessages);
//		   request.setAttribute("qualityIssuesPicss", qualityIssuesPicss);
		   
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("=========MessageCenterController =====queryMessage======"+e.getMessage());
		}
         return "message_center.jsp";
   }
   
   /**
    * 查询消息详情
    * @author polo
    * 2017年5月15日
    *
    */
   @ResponseBody
   @RequestMapping("/queryMessageDetails.do")
   public JsonResult<Integer> queryMessageDetails(HttpServletRequest request,HttpServletResponse response){
	     
	   try {
		   String salesId = WebCookie.getUserId(request);
		   String factoryId = WebCookie.getFactoryId(request);
		   Integer messageCenterId = null;
		   if(request.getParameter("messageCenterId") == null || "".equals(request.getParameter("messageCenterId"))){
			   throw new RuntimeException("未获取到消息");
		   }else{
			   messageCenterId = Integer.parseInt(request.getParameter("messageCenterId"));
		   }	   
		   orderMessageService.updateOrderMessage(messageCenterId);
		   BackUser backUser = backUserService.queryBackUserByUserId(salesId);
		   Integer permission = backUser.getPermission();	
		   int total = messageCenterService.total(salesId, factoryId, permission);	
		   
		   return new JsonResult<Integer>(total);
		} catch (NumberFormatException e) {			
			e.printStackTrace();
			LOG.error("=========MessageCenterController =====queryMessageDetails======"+e.getMessage());
		   return new JsonResult<Integer>(1,"消息中心ID为空");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("=========MessageCenterController =====queryMessageDetails======"+e.getMessage());
			return new JsonResult<Integer>(1,"查询失败");
	    }
   }
   
   
   /**
    * 保存工厂回复消息
    * @author polo
    * 2017年5月17日
    *
    */
   @ResponseBody
   @RequestMapping("/saveFactoryMessage.do")
   public JsonResult<OrderMessage> saveFactoryMessage(HttpServletRequest request,HttpServletResponse response){
	   
	   try {
		   Integer messageCenterId = null;
		   OrderMessage orderMessage = new OrderMessage();
		   
		   if(request.getParameter("messageCenterId") == null || "".equals(request.getParameter("messageCenterId"))){
			   throw new RuntimeException("消息ID不存在");
		   }else{
			   messageCenterId = Integer.parseInt(request.getParameter("messageCenterId"));
		   }
		   orderMessage.setMessageCenterId(messageCenterId);
		   orderMessage.setMessageDetails(request.getParameter("messageDetails"));	   
		   orderMessage.setMessageSendTime(DateFormat.format());
		   orderMessage.setReadStatus(UNREAD_STATUS);	   
		   orderMessage.setSalesId(WebCookie.getUserId(request));
		   orderMessage.setFactoryId(WebCookie.getFactoryId(request));
		   orderMessage.setPicStatus(0);
		   orderMessage.setCustomerOrFactory(FACTORY_MESSAGE);
		   
		   return new JsonResult<OrderMessage>(orderMessageService.insert(request,orderMessage));
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return new JsonResult<OrderMessage>(1,"订单消息ID不存在");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<OrderMessage>(1,"保存失败");
		}
   }
   
   
   
   
   
   
   
}
