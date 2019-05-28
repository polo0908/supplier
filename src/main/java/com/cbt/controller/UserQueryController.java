package com.cbt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.User;
import com.cbt.entity.UserRelationEmail;
import com.cbt.service.BackUserService;
import com.cbt.service.FactoryUserRelationService;
import com.cbt.service.UserRelationEmailService;
import com.cbt.service.UserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.NbMailAddressUtil;
import com.cbt.util.SplitPage3;
import com.cbt.util.WebCookie;

@Controller
public class UserQueryController {
    
	public static final String domain = NbMailAddressUtil.getDomainName();

	public static Logger logger = Logger.getLogger(UserQueryController.class); 
	@Resource
	private UserService userService;	
	
	@Resource
	private BackUserService backUserService;	
	@Resource
	private FactoryUserRelationService factoryUserRelationService;	
	@Resource
	private UserRelationEmailService userRelationEmailService;	

	@RequestMapping("/queryUserList.do")
	public String queryUserList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String info = null;
			String pageStr = request.getParameter("page");
			String pageSizeStr = request.getParameter("pageSize");
			String factoryId = WebCookie.getFactoryId(request);
			String salesId = WebCookie.getUserId(request); 
			BackUser backUser = backUserService.queryBackUserByUserId(salesId);
			Integer permission = backUser.getPermission();
			if(permission == null || "".equals(permission)){
				permission = 2;
			}
			
			if (!(request.getParameter("info") == null || "".equals(request.getParameter("info")))) {
				info = request.getParameter("info");
			}
			int page = 1;
			if (pageStr != null) {
				page = Integer.parseInt(pageStr);
			}
			int pageSize = 10;
			if (pageSizeStr != null) {
				pageSize = Integer.parseInt(pageSizeStr);
			}
			int start = (page - 1) * pageSize;

			List<User> list = null;
			int total = 0;
			
			//检查用户权限如果有管理员权限，查询工厂所有用户数据
			if(permission == 1){
				list = userService.queryUserListAdmin(info, start, pageSize, salesId, factoryId);
				total = userService.queryUserTotalAdmin(info, salesId, factoryId);
			}else{
				list = userService.queryUserList(info, start, pageSize, salesId,factoryId);
				total = userService.queryUserTotal(info, salesId,factoryId);
			}

			

//		    String content=
//		    		  "Dear Sir/Madam,<br>"+
//		    		  "We are currently using a CRM (customer relationship management) system to manage orders.  You will be able to get weekly updates, QC reports and shipping documents from there.<br>"+	    				  
//		    		  "Welcome to login our CRM system:<br>"+
//		    		  "Once you login our CRM system, you can find all of the related information about all of our cooperated projects.<br>"+
//		    		  "<a href='"+domain+"'>'"+domain+"'</a> (You can log in to change your password)<br>"+
//		    		  "Best regards<br>";
//		    content = URLEncoder.encode(content, "utf-8");
			
			SplitPage3.buildPager(request, total, pageSize, page);
			request.setAttribute("userList", list);
			request.setAttribute("pageSize", pageSize);
//			request.setAttribute("content", content);
			if (info != null || info != "") {
				request.setAttribute("queryinfo", info);
			}
		} catch (Exception e) {
			logger.error("======UserQueryController  queryUserList======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		
		return "customer.jsp";
	}

	@ResponseBody
	@RequestMapping("/queryUserById.do")
	public User queryUserById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		try {
			String customerId = request.getParameter("customerId");
			if (customerId == null || "".equals(customerId)) {
				throw new Exception("获取ID失败");
			}

			user = userService.queryByUserId(customerId);
		} catch (Exception e) {
			logger.error("======UserQueryController  queryUserById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return user;
	}
	
	/**
	 * 查询客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/queryCustomerInfo.do")
	public Map<Object,Object> queryCustomerInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		Map<Object,Object> map = new HashMap<Object,Object>();
		try {
			String factoryId = WebCookie.getFactoryId(request);
			String customerId = request.getParameter("customerId");
			if (customerId == null || "".equals(customerId)) {
				throw new Exception("获取ID失败");
			}
			if (factoryId == null || "".equals(factoryId)) {
				throw new Exception("获取工厂ID");
			}
			user = userService.queryByUserId(customerId);
			FactoryUserRelation factoryUserRelation = factoryUserRelationService.queryByFactoryIdAndUserid(factoryId, customerId);			
			map.put("user", user);
			map.put("factoryUserRelation", factoryUserRelation);
			
			
		} catch (Exception e) {
			logger.error("======UserQueryController  queryCustomerInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/updateRemark.do")
	public JsonResult<String> updateRemark(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		try {
			String factoryId = WebCookie.getFactoryId(request);
			String customerId = request.getParameter("customerId");
			if(customerId == null || "".equals(customerId)) {
				throw new Exception("获取ID失败");
			}
			if(factoryId == null || "".equals(factoryId)) {
				throw new Exception("获取工厂ID");
			}
			String remark = request.getParameter("remark");
			FactoryUserRelation factoryUserRelation = factoryUserRelationService.queryByFactoryIdAndUserid(factoryId, customerId);
			if(!(request.getParameter("remark") == null || "".equals(request.getParameter("remark")))){				
				factoryUserRelation.setFactoryUserRemark(remark);
				factoryUserRelationService.updateRemark(factoryUserRelation);
			}
			
			return new JsonResult<String>(0,"success");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("======UserQueryController  updateRemark======="+e.getMessage());
			return new JsonResult<String>(1,e.getMessage());
		}

	}
	
    /**
     * 查询用户邮箱
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
	@ResponseBody
	@RequestMapping("/queryUserEmail.do")
	public JsonResult<User> queryUserEmail(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		try {
			String customerId = request.getParameter("customerId");
			user = userService.queryByUserId(customerId);
		} catch (Exception e) {
			logger.error("======UserQueryController  queryUserEmail======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
        
		return new JsonResult<User>(user);
	}

	/**
	 * 查询用户 登录token
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryUserTokenById.do")
	public String queryUserTokenById(HttpServletRequest request, HttpServletResponse response) {
		String customerId = request.getParameter("customerId");
		User user = userService.queryByUserId(customerId);
		String token = domain;
		token += user.getToken() == null ? "" : user.getToken();
		return token;
	}
	
	
	
	
	/**
	 * 根据用户ID查询所有子邮箱
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryEmailByUserid.do")
	public JsonResult<List<UserRelationEmail>> queryEmailByUserid(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = request.getParameter("userid");
			List<UserRelationEmail> userRelationEmails = userRelationEmailService.queryByUserId(userid);
			return new JsonResult<List<UserRelationEmail>>(userRelationEmails);
			
		} catch (Exception e) {
			logger.error("======UserQueryController  queryEmailByUserid======="+e.getMessage());
			e.printStackTrace();
			return new JsonResult<List<UserRelationEmail>>(1, e.getMessage());
		}
	}
	
	
	/**
	 * 根据用户Id添加子邮箱
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addEmailByUserid.do")
	public JsonResult<List<UserRelationEmail>> addEmailByUserid(HttpServletRequest request, HttpServletResponse response) {
		
		try {
			String userid = request.getParameter("userid");
			String email = request.getParameter("email");
			List<UserRelationEmail> userRelationEmails = userRelationEmailService.insert(userid, email);
			return new JsonResult<List<UserRelationEmail>>(userRelationEmails);
			
		} catch (Exception e) {
			logger.error("======UserQueryController  addEmailByUserid======="+e.getMessage());
			e.printStackTrace();
			return new JsonResult<List<UserRelationEmail>>(1, e.getMessage());
		}
		
	}
	

	
	
	
	
	/**
	 * 查询最近有修改订单的30天的客户
	 * @Title queryUserByOrderUpdate 
	 * @Description TODO
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult<List<UserRelationEmail>>
	 */
	@RequestMapping("/queryUserByOrderUpdate.do")
	public String queryUserByOrderUpdate(HttpServletRequest request, HttpServletResponse response) {				
		List<User> users = userService.queryUserByOrderUpdate();
		String loginEmail = "";
		String pwd = "";
		for (User user : users) {
			loginEmail = user.getLoginEmail();
			pwd = user.getPwd();
			if(StringUtils.isNotBlank(loginEmail)){
				String userInfo = Base64Encode.getBase64(loginEmail + "&" + pwd);
				user.setEncryptedEmail(userInfo);
			}else{
				user.setEncryptedEmail("");
			}
			
		}
		request.setAttribute("users", users);
		request.setAttribute("type", 0);
		return "messageLog.jsp";
	}
	/**
	 * 查询最近邀请的30天的客户
	 * @Title queryUserByInvitation 
	 * @Description TODO
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult<List<UserRelationEmail>>
	 */
	@RequestMapping("/queryUserByInvitation.do")
	public String queryUserByInvitation(HttpServletRequest request, HttpServletResponse response) {				
		List<User> users = userService.queryUserByInvitation();	
		String loginEmail = "";
		String pwd = "";
		for (User user : users) {
			loginEmail = user.getLoginEmail();
			pwd = user.getPwd();
			if(StringUtils.isNotBlank(loginEmail)){
				String userInfo = Base64Encode.getBase64(loginEmail + "&" + pwd);
				user.setEncryptedEmail(userInfo);
			}else{
				user.setEncryptedEmail("");
			}
			
		}
		request.setAttribute("users", users);
		request.setAttribute("type", 1);
		return "messageLog.jsp";
	}
	/**
	 * 查询最近登录的30天的客户
	 * @Title queryUserByLogin 
	 * @Description TODO
	 * @param request
	 * @param response
	 * @return
	 * @return JsonResult<List<UserRelationEmail>>
	 */
	@RequestMapping("/queryUserByLogin.do")
	public String queryUserByLogin(HttpServletRequest request, HttpServletResponse response) {				
		List<User> users = userService.queryUserByLogin();	
		String loginEmail = "";
		String pwd = "";
		for (User user : users) {
			loginEmail = user.getLoginEmail();
			pwd = user.getPwd();
			if(StringUtils.isNotBlank(loginEmail)){
				String userInfo = Base64Encode.getBase64(loginEmail + "&" + pwd);
				user.setEncryptedEmail(userInfo);
			}else{
				user.setEncryptedEmail("");
			}
			
		}
		request.setAttribute("users", users);
		request.setAttribute("type", 2);
		return "messageLog.jsp";
	}
	
	
	
}
