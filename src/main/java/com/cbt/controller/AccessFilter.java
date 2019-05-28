package com.cbt.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cbt.util.Md5Util;


/**
 * 验证用户是否登录
 * @author polo
 *
 */
public class AccessFilter implements Filter{

	public void destroy() {
		
		
	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;	
	     //除了拦截login.jsp  其他的都拦截
		StringBuffer url = request.getRequestURL();
		 String path = url.toString();
		 if(path.endsWith(".jsp") || path.endsWith(".do")){
			 //放过login.jsp
			 if(path.endsWith("login.jsp")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("fileDownload_quotation_attachment.do")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("fileDownload_drawing.do")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("queryMessage.do")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("toModifyQuotation.do")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.endsWith("queryMessageCount.do")){
				 chain.doFilter(req, res);
				 return;
			 }
			 if(path.indexOf("/crm/")>0){
				 chain.doFilter(req, res);
				 return;
			 }  
			 if(path.indexOf("/steelPlate/")>0){
				 chain.doFilter(req, res);
				 return;
			 }  
			 //放过/backUser/*.do
			 if(path.indexOf("/backUser/")>0 || path.indexOf("/port/")>0 || path.indexOf("/shippingPort/")>0){
				 chain.doFilter(req, res);
				 return;
			 }
			 //如果没有登录就跳转到登录界面
			 processAccessControl(request,response,chain);
			 return;
		 }
		 chain.doFilter(req, res);
		 
	}



	/**
	 * 处理访问控制
	 * @throws IOException 
	 * @throws ServletException 
	 * 
	 */
	private void processAccessControl(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		//检查Cookie中是否有Token 没有就去登录
		Cookie[] cookies = request.getCookies();
		Cookie token = null;
		if(cookies != null){
			for(Cookie cookie :cookies){
				if(cookie.getName().equals("token1")){
					token = cookie;
					break;
				}			
			}
		}
		
		if(token == null){
			//如果没有找到，就重定向到登录界面
			String path = request.getContextPath();
			String login = path+"/login.jsp";
			response.sendRedirect(login);
			return;
		}
		//处理token是否合格
		String value = token.getValue();
		String[] data = value.split("\\|");
		String time = data[0];
		String md5 = data[1];
//		System.out.println(Md5Util.encoder(time));
		if(md5.equals(Md5Util.encoder(time))){
			chain.doFilter(request, response);
			return;
		}
		//如果token验证不合理，去登录页面
		String path = request.getContextPath();
		String login = path+"/login.jsp";
		response.sendRedirect(login);
		return;
		
	}

	public void init(FilterConfig arg0) throws ServletException {
	
		
	}
   
}
