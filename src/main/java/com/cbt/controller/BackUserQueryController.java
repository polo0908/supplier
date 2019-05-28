package com.cbt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.service.BackUserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.Md5Util;
import com.cbt.util.SecurityHelper;
import com.cbt.util.SplitPage3;
import com.cbt.util.WebCookie;

@Controller
@RequestMapping("/backUser")
public class BackUserQueryController {
	@Autowired
	private BackUserService backUserService;
	
	public static Logger logger = Logger.getLogger(BackUserQueryController.class);

	/**
	 * 查询用户表数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/queryBackUserList.do")
	public String queryBackUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			String info = null;
			String pageStr = request.getParameter("page");
			String pageSizeStr = request.getParameter("pageSize");
			if (!(request.getParameter("info") == null || "".equals(request.getParameter("info")))) {
				info = request.getParameter("info");
			}
			String factoryId = WebCookie.getFactoryId(request);
			int page = 1;
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
			int pageSize = 10;
			if (pageSizeStr != null) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
			int start = (page - 1) * pageSize;

			List<BackUser> list = backUserService.queryBackUserList(info, start, pageSize,factoryId);
			int total = backUserService.queryBackUserTotal(info,factoryId);		
			SplitPage3.buildPager(request, total, pageSize, page);
			request.setAttribute("backUserList", list);
			request.setAttribute("pageSize", pageSize);
			String backUserId = WebCookie.getUserId(request); 
			BackUser backUser = backUserService.queryBackUserByUserId(backUserId);
			request.setAttribute("permission", backUser.getPermission());
			if (info != null || info != "") {
				request.setAttribute("queryinfo", info);
			}
		} catch (NumberFormatException e) {
			logger.error("======BackUserQueryController  queryBackUserList======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return "/user.jsp";
	}
   
	@RequestMapping("/jumpToAddBackUser.do")
	public String jumpToAddBackUser(HttpServletRequest request, HttpServletResponse response){	     	
		
		return "/add_backuser.jsp";
	}
	
	
	/**
	 * 添加后台用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/addBackUser.do")
	public void addBackUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		try {
			String loginUser = WebCookie.getUserName(request);
			String factoryId = WebCookie.getFactoryId(request);
			BackUser user = backUserService.queryBackUserByName(loginUser);
			if(!(user.getPermission() == 1)){
				throw new RuntimeException("无权限操作 ！");
			}
			BackUser backUser = new BackUser();
			if(request.getParameter("userName") == null || "".equals(request.getParameter("userName"))){
				throw new RuntimeException("登录用户名不能为空");
			}else{
				backUser.setUserName(request.getParameter("userName"));
			}	    
			if (!(request.getParameter("realName") == null || "".equals(request.getParameter("realName")))) {
				backUser.setRealName(request.getParameter("realName"));
			}
			if (!(request.getParameter("pwd") == null || "".equals(request.getParameter("pwd")))) {
				backUser.setPwd(request.getParameter("pwd"));
			}
			if (!(request.getParameter("tel") == null || "".equals(request.getParameter("tel")))) {
				backUser.setTel(request.getParameter("tel"));
			}
			if (!(request.getParameter("email") == null || "".equals(request.getParameter("email")))) {
				backUser.setEmail(request.getParameter("email"));
			}
			if (!(request.getParameter("remark") == null || "".equals(request.getParameter("remark")))) {
				backUser.setRemark(request.getParameter("remark"));
			}
			if (!(loginUser == null || "".equals(loginUser))) {
				backUser.setUpdateUser(loginUser);
			}
			if(!(request.getParameter("permission") == null || "".equals(request.getParameter("permission")))){
				Integer permission = Integer.parseInt(request.getParameter("permission"));	
				backUser.setPermission(permission);
			}		
			    backUser.setCreateTime(DateFormat.format());
			    backUser.setFactoryId(factoryId);
			    backUserService.insertBackUser(backUser);
		   
		} catch (Exception e) {
			logger.error("======BackUserQueryController  queryBackUserList======="+e.getMessage());
			e.printStackTrace();	
			throw new Exception(e.getMessage());
		}
		
	}
	
	
	@ResponseBody
    @RequestMapping("/checkBackUser.do")
    public String checkBackUser(HttpServletRequest request, HttpServletResponse response){
   	 String backUserName = null;    	
			if(!(request.getParameter("userName") == null || "".equals(request.getParameter("userName")))){
				backUserName = request.getParameter("userName");  	
				if(!(backUserService.queryBackUserByName(backUserName) == null )){
					throw new RuntimeException("用户已经存在！");

				}
			}
			
			return "legal";
    }
		
	/**
	 * 跳转修改后台数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/jumpToUpdateBackUser.do")
	public String jumpToUpdateBackUser(HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {
			Integer id = null;
			if (!(request.getParameter("id") == null || "".equals(request.getParameter("id")))) {
				id = Integer.valueOf(request.getParameter("id"));
			} else {
				throw new Exception("获取用户Id失败！");
			}
			BackUser userInfo = backUserService.queryBackUserById(id);
			request.setAttribute("userInfo", userInfo);
		} catch (Exception e) {
			logger.error("======BackUserQueryController  jumpToUpdateBackUser======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return "/add-editor.jsp";
	}
   
	
   /**
    * 更新后台用户数据（back_user表）
    * @param request
    * @param response
    * @throws Exception
    */
	
	@RequestMapping("/updateBackUser.do")
	public void updateBackUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			BackUser userInfo = getUserInfo(request);
			backUserService.updateBackUser(userInfo);
		} catch (Exception e) {
			logger.error("======BackUserQueryController  jumpToUpdateBackUser======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	private BackUser getUserInfo(HttpServletRequest request) throws Exception {
		//获取session用户
    	BackUser backUser = null;
		try {
			String loginUser = WebCookie.getUserName(request);
			BackUser user = backUserService.queryBackUserByName(loginUser);
//        System.out.println(user);
			if(!(user.getPermission() == 1)){
				throw new RuntimeException("无权限操作 ！");
			}
			backUser = new BackUser();
			
			Integer id = null;
			if (!(request.getParameter("id") == null || "".equals(request.getParameter("id")))) {
				id = Integer.parseInt(request.getParameter("id"));
			} else {
				throw new Exception("获取Id失败！");
			}
			backUser.setId(id);			
			
			String userId = null;					
			if (!(request.getParameter("userId") == null || "".equals(request.getParameter("userId")))) {
				userId = request.getParameter("userId");
			} else {
				throw new Exception("获取用户Id失败！");
			}
			backUser.setBackUserid(userId);
			if (!(request.getParameter("userName") == null || "".equals(request.getParameter("userName")))) {
				backUser.setUserName(request.getParameter("userName"));
			}
			if (!(request.getParameter("realName") == null || "".equals(request.getParameter("realName")))) {
				backUser.setRealName(request.getParameter("realName"));
			}
			if (!(request.getParameter("pwd") == null || "".equals(request.getParameter("pwd")))) {
				backUser.setPwd(request.getParameter("pwd"));
			}
			if (!(request.getParameter("tel") == null || "".equals(request.getParameter("tel")))) {
				backUser.setTel(request.getParameter("tel"));
			}
			if (!(request.getParameter("email") == null || "".equals(request.getParameter("email")))) {
				backUser.setEmail(request.getParameter("email"));
			}
			if (!(request.getParameter("remark") == null || "".equals(request.getParameter("remark")))) {
				backUser.setRemark(request.getParameter("remark"));
			}
			if(!(request.getParameter("permission") == null || "".equals(request.getParameter("permission")))){
				Integer permission = Integer.parseInt(request.getParameter("permission"));	
				backUser.setPermission(permission);
			}	
			if (!(loginUser == null || "".equals(loginUser))) {
				backUser.setUpdateUser(loginUser);
			}
			Date updateTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			backUser.setUpdateTime(sdf.format(updateTime));
		} catch (Exception e) {
			logger.error("======BackUserQueryController  getUserInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return backUser;
	}
	
	/**
	 * 更新后台用户密码
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/resetBackUserPwd.do")
	public void resetBackUserPwd(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userId = null;
			if (!(request.getParameter("userId") == null || "".equals(request.getParameter("userId")))) {
				userId = request.getParameter("userId");
			} else {
				throw new Exception("获取用户Id失败！");
			}
			BackUser userInfo = backUserService.queryBackUserByUserId(userId);

			String oldPwd = "";
			if (!(request.getParameter("oldPwd") == null || "".equals(request.getParameter("oldPwd")))) {
				oldPwd = request.getParameter("oldPwd");
			} else {
				throw new Exception("获取原始密码失败！");
			}
			
			String password = "";
			if (!(request.getParameter("password") == null || "".equals(request.getParameter("password")))) {
				password = request.getParameter("password");
			} else {
				throw new Exception("获取新密码失败！");
			}

			if(!(oldPwd.equals(userInfo.getPwd()))){
				throw new Exception("原始密码输入错误！");
			}

			userInfo.setPwd(password);
			backUserService.updateBackUser(userInfo);
			
			
			 //session过期
	          HttpSession session = request.getSession();
	          session.invalidate();
			
	  		  //cookie清空
	   		  Cookie userCookie = new Cookie("backuser",null);           
	   		  userCookie.setPath("/");
	   		  userCookie.setMaxAge(0);
	   		  response.addCookie(userCookie);

			
		} catch (Exception e) {
			logger.error("======BackUserQueryController  resetBackUserPwd======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	
	/**
	 * 删除后台用户
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/deleteBackUser.do")
	public void deleteBackUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String backUserId = WebCookie.getUserId(request); 
			BackUser backUser = backUserService.queryBackUserByUserId(backUserId);
			if(backUser.getPermission() != 1){
				throw new Exception("请联系管理员操作！");
			}
			Integer id = null;
			String userIdStr = request.getParameter("id");
			if (userIdStr == null || "".equals(userIdStr)) {
				throw new Exception("获取用户Id失败！");
			} else {
				id = Integer.valueOf(userIdStr);
			}
			backUserService.deleteBackUserById(id);
		} catch (Exception e) {
			logger.error("======BackUserQueryController  deleteBackUser======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	
	/**
	 * 登录入口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/login.do")
	public String toLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			BackUser user = backUserService.login(username, password);
			request.setAttribute("user", user);
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
			String factoryId = user.getFactoryId();
			if(factoryId == null){
				factoryId = "";
				logger.info("factoryId:未获取到工厂信息");
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

	  		 
			
		} catch (Exception e) {
			logger.error("======BackUserQueryController  toLogin======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		// 使用request对象的getSession()获取session，如果session不存在则创建一个
		// HttpSession session = request.getSession();
		// 将数据存储到session中
		// session.setAttribute("user", user);

		return "/login.jsp";
	}

	
	
	
	
	
	
	/**
	 * nbmail登录入口
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/nbmailLogin.do")
	public String nbmailLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String userInfo = request.getParameter("userInfo");
			String info = Base64Encode.getFromBase64(userInfo);
			String[] split = info.split(",");
			String username = null;
			String password = null;
			if(split != null && split.length != 0){
				username = split[0];
				password = split[1];
			}
			BackUser user = backUserService.login(username, password);
			request.setAttribute("user", user);
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
			String factoryId = user.getFactoryId();
			if(factoryId == null){
				factoryId = "";
				logger.info("factoryId:未获取到工厂信息");
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
              
	   		  response.sendRedirect("https://importx.net/supplier/queryAllClientOrder.do");
	  		  return "登录成功";			
		} catch (Exception e) {
			logger.error("======BackUserQueryController  toLogin======="+e.getMessage());
			e.printStackTrace();
			return "登录失败";	
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 重置后台用户密码
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/resetPassword.do")
	public String resetPassword(HttpServletRequest request, HttpServletResponse response) throws Exception {


		String userId = WebCookie.getUserId(request);

		if (!"".equals(userId) && userId != null) {
			request.setAttribute("userId",userId);
			return "/reset.jsp";
		} else {
			throw new Exception("获取用户失败!");
		}
	}

	
	
	
	
}
