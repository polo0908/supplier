package com.cbt.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class WebCookie {
	public static String cookie(HttpServletRequest request, String cookiev) {
		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (Cookie cookie2 : cookie) {
				if (cookie2.getName().equals(cookiev)) {
					return cookie2.getValue();
				}
			}
		}
		return null;
	}
	public static String getSessionValue(HttpServletRequest request,String cookiev){
		HttpSession session = request.getSession();
		Object val = session.getAttribute(cookiev);
        if(val == null || "".equals(val)){
        	return null;
        }
		return val.toString();
	}
	public static String cookieValue(HttpServletRequest request, String cookiev) {
		Cookie[] cookie = request.getCookies();
		if (cookie != null) {
			for (Cookie cookie2 : cookie) {
				// LOG.warn("cookie:"+cookie2.getName()+","+cookie2.getValue());
				if (cookie2.getName().equals(cookiev)) {
					return cookie2.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 根据名字获取cookie
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = ReadCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie;
		} else {
			return null;
		}
	}

	/**
	 *  * 将cookie封装到Map里面  * @param request  * @return  
	 */
	private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}

	/**
	 * 判断用户是否登录
	 */
	public static String[] getUser(HttpServletRequest request) {
		String user = WebCookie.getSessionValue(request, "backuser");
		if(user == null || "".equals(user)){
			user = WebCookie.cookie(request, "backuser");
		}		
		String[] userInfo = null;
		if (user != null) {
			try {
				user = SecurityHelper.decrypt("backuser", user);
				userInfo = user.split("&");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return userInfo;
		
	}
	
	//获取cookie用户
	// 从cookies中获取用户的信息
	public static String getUserId(HttpServletRequest request){
		
			String userStr = WebCookie.getSessionValue(request, "backuser");
			if(userStr == null || "".equals(userStr)){
				userStr = WebCookie.cookie(request, "backuser");
			}		
			String salesId = "";
			if (!"".equals(userStr) && userStr != null) {
				
				try {
					userStr = SecurityHelper.decrypt("backuser", userStr);
					String[] userInfo = userStr.split("&");
					salesId = userInfo[0];
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} 
	
	     return salesId;		
	}
	// 从cookies中获取用户的信息
	public static String getUserName(HttpServletRequest request){
		
		String userName = "";
		String userStr = WebCookie.getSessionValue(request, "backuser");
		if(userStr == null || "".equals(userStr)){
			userStr = WebCookie.cookie(request, "backuser");
		}		
		if (!"".equals(userStr) && userStr != null) {
			try {
				userStr = SecurityHelper.decrypt("backuser", userStr);
				String[] userInfo = userStr.split("&");
				userName = userInfo[1];
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return userName;		
	}
	// 从cookies中获取工厂信息
	public static String getFactoryId(HttpServletRequest request){
		
		String factoryId = "";
		String userStr = WebCookie.getSessionValue(request, "backuser");
		if(userStr == null || "".equals(userStr)){
			userStr = WebCookie.cookie(request, "backuser");
		}		
		if (!"".equals(userStr) && userStr != null) {
			try {
				userStr = SecurityHelper.decrypt("backuser", userStr);
				String[] userInfo = userStr.split("&");
				factoryId = userInfo[3];
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		return factoryId;		
	}
	// 从cookies中获取用户的信息
	public static Integer getPermission(HttpServletRequest request){
		
		
		Integer permission = 0;
		String userStr = WebCookie.getSessionValue(request, "permission");
		if(userStr == null || "".equals(userStr)){
			userStr = WebCookie.cookie(request, "permission");
		}		
		if (!"".equals(userStr) && userStr != null) {
			permission = Integer.parseInt(userStr);
		}  
		
		return permission;		
	}
	// 从cookies中获取用户密码的信息
	public static String getPwd(HttpServletRequest request){
		
		String pwd = "";
		String userStr = WebCookie.getSessionValue(request, "backuser");
		if(userStr == null || "".equals(userStr)){
			userStr = WebCookie.cookie(request, "backuser");
		}		
		if (!"".equals(userStr) && userStr != null) {
			try {
				userStr = SecurityHelper.decrypt("backuser", userStr);
				String[] userInfo = userStr.split("&");
				pwd = userInfo[4];
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		
		return pwd;		
	}
	
}
