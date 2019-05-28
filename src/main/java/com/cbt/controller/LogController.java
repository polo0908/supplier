package com.cbt.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cbt.entity.LoginLog;
import com.cbt.service.LoginLogService;
import com.cbt.util.SplitPage3;
import com.cbt.util.SplitPage;


@Controller
public class LogController {
	@Resource
	private LoginLogService loginLogService;
	
	public static Logger logger = Logger.getLogger(LogController.class);	
	
	@RequestMapping("/queryLoginLog.do")
	public String queryLoginLog(HttpServletRequest request, HttpServletResponse response) {
		
		   try {
			String str1 = request.getParameter("page");
			   String str2 = request.getParameter("pageSize");
			   
			    int page = 1;
				if(str1 != null) {
					page = Integer.parseInt(str1);
				}
			    int pageSize = 10;   
				if(str2 != null) {
					pageSize = Integer.parseInt(str2);
				}
				int start = (page-1) * pageSize; 
			 Integer total1 = loginLogService.queryTotal1();
			 List<LoginLog> log1 = loginLogService.querySuccessTop();
			 SplitPage3.buildPager(request, total1, pageSize, page);
			 request.setAttribute("log1", log1);
			 
			 Integer total2 = loginLogService.queryTotal2();
			 List<LoginLog> log2 = loginLogService.queryFailTop();
			 SplitPage.buildPager(request, total2, pageSize, page);
			 request.setAttribute("log2", log2);
		} catch (Exception e) {
			logger.equals(e.getMessage());
			e.printStackTrace();
		}
		 
		 
		return "log.jsp";
	}
}
