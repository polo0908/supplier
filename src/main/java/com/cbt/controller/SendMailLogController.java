package com.cbt.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.MessageLogTab;
import com.cbt.service.BackUserService;
import com.cbt.service.MessageLogTabService;
import com.cbt.util.DateFormat;
import com.cbt.util.WebCookie;
@Controller 

public class SendMailLogController {
  @Resource
  private MessageLogTabService messageLogTabService;
  @Resource
  private BackUserService backUserService;

  
  public static final Logger LOG = Logger.getLogger(SendMailLogController.class); 
   
    
   /**
    * 保存发件日志
    * @author polo
    * 2017年12月25日
    *
    */
   @ResponseBody
   @RequestMapping("/saveSendMailLog.do")
   public String saveSendMailLog(HttpServletRequest request,HttpServletResponse response){
	    
	      try {
			  MessageLogTab messageLogTab = new MessageLogTab();
			  messageLogTab.setSaleName(WebCookie.getUserName(request));
			  messageLogTab.setUserid(request.getParameter("customerId"));
			  if(StringUtils.isNotBlank(request.getParameter("orderId"))){
				  messageLogTab.setOrderId(request.getParameter("orderId")); 
			  }
			  if(StringUtils.isNotBlank(request.getParameter("messageType"))){
				  messageLogTab.setMessageType(Integer.parseInt(request.getParameter("messageType")));
			  }
			  messageLogTab.setCreateTime(DateFormat.format());
			  messageLogTabService.insertSelective(messageLogTab);
			  return "yes";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			LOG.error("未获取到登录信息"+e.getMessage());
			return "no";
		}
	      
    }
   
   
   
   
   
   
}
