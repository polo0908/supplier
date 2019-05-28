package com.cbt.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.entity.FactoryInfo;
import com.cbt.service.BackUserService;
import com.cbt.service.FactoryInfoService;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.WebCookie;

@Controller
public class FactoryInfoController {
       @Resource
	   private FactoryInfoService factoryInfoService;
	   	@Resource
	   	private BackUserService backUserService; 
	   	
		public static Logger logger = Logger.getLogger(FactoryInfoController.class);
	   
	   /**
	    * 查询工厂信息
	    * @param request
	    * @param response
	    * @return
	 * @throws Exception 
	    */
	   @RequestMapping("/showFactoryInfo.do")
	   public String showFactoryInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   
		   try {
			String userId = WebCookie.getUserId(request);
			   if(userId == null || "".equals(userId)){
				   throw new RuntimeException("未获取到用户信息");
			   }
			   BackUser backUser = backUserService.queryBackUserByUserId(userId);
			   Integer permission = backUser.getPermission();
			   if(permission != 1){
				   throw new RuntimeException("您没有权限");
			   }
			   String factoryId = WebCookie.getFactoryId(request);
			   FactoryInfo factoryInfo = factoryInfoService.queryByFactoryId(factoryId);
			   request.setAttribute("factoryInfo", factoryInfo);
			   
			   
		} catch (Exception e) {
			logger.error("=======showFactoryInfo Exception=========:"+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		   
		   
		   
		   return "factory_info.jsp";
	   }

	   
	   /**
	    * 更新工厂数据
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	   @RequestMapping("/updateFactoryInfo.do")
	   public String updateFactoryInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   
		   try {
			String factoryId = WebCookie.getFactoryId(request);
			   String userId = WebCookie.getUserId(request);
			   if(userId == null || "".equals(userId)){
				   throw new RuntimeException("未获取到用户信息");
			   }
			   BackUser backUser = backUserService.queryBackUserByUserId(userId);
			   Integer permission = backUser.getPermission();
			   if(permission != 1){
				   throw new RuntimeException("您没有权限");
			   }
			   FactoryInfo factoryInfo = factoryInfoService.queryByFactoryId(factoryId);
			   String factoryName = request.getParameter("factoryName");
			   String tel = request.getParameter("tel");
			   String mainBusiness = request.getParameter("mainBusiness");
			   String factoryAddr = request.getParameter("factoryAddr");
			   String establishmentDate = request.getParameter("establishmentDate");
			   String country = request.getParameter("selectCountry");
			   String factoryNumber = request.getParameter("factoryNumber");
			   String website = request.getParameter("website");
			   factoryInfo.setFactoryName(factoryName);
			   factoryInfo.setFactoryAdminTel(tel);
			   factoryInfo.setMainBusiness(mainBusiness);
			   factoryInfo.setFactoryAddr(factoryAddr);
			   factoryInfo.setCountry(country);
			   factoryInfo.setEstablishmentDate(establishmentDate);
			   factoryInfo.setFactoryNumber(factoryNumber);
			   factoryInfo.setWebsite(website);
			   factoryInfo.setUpdateTime(DateFormat.format());
			   factoryInfoService.updateFactoryInfo(factoryInfo);
		} catch (Exception e) {
			logger.error("=============updateFactoryInfo  Exception=========:"+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	   
		   return "factory_info.jsp";
	   }
	   
	   
	   /**
	    * 更新logo图片
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	   @ResponseBody
	   @RequestMapping("/updateFactoryLogo.do")
	   public String updateFactoryLogo(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   
		   FactoryInfo factoryInfo = null;
		   
			   try {
				String factoryId = WebCookie.getFactoryId(request);
				   String userId = WebCookie.getUserId(request);
				   if(userId == null || "".equals(userId)){
					   throw new RuntimeException("未获取到用户信息");
				   }
				   BackUser backUser = backUserService.queryBackUserByUserId(userId);
				   Integer permission = backUser.getPermission();
				   if(permission != 1){
					   throw new RuntimeException("您没有权限");
				   }
				   factoryInfo = factoryInfoService.queryByFactoryId(factoryId);
				    //获取logo存放路径，按照工厂id单独存放   				
//					ServletContext sctx = request.getSession().getServletContext();				
					String path = UploadAndDownloadPathUtil.getLogoAndLicensePath() + factoryId;
					File file = new File(path);
					if  (!file.exists()  && !file .isDirectory())      
					{         
					    file .mkdir();     
					}  	  	  
					//调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
					Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path+File.separator);
					
					if(!(request.getParameter("logoName") == null || "".equals(request.getParameter("logoName")))){
						String logoName = request.getParameter("logoName");
						factoryInfo.setFactoryLogo(UploadAndDownloadPathUtil.getStaticLogoAndLicensePath() + factoryId + "/" +logoName);
						factoryInfo.setUpdateTime(DateFormat.format());
						factoryInfoService.updateFactoryInfo(factoryInfo);
					}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("=============updateFactoryLogo  Exception=========:"+e.getMessage());
			throw new Exception(e.getMessage());
			}
		
		   
		   return factoryInfo.getFactoryLogo();
	   }
	   
	   /**
	    * 更新license图片
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	   @ResponseBody
	   @RequestMapping("/updateFactoryLicense.do")
	   public String updateFactoryLicense(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   
		   FactoryInfo factoryInfo = null;
		   try {
			   String factoryId = WebCookie.getFactoryId(request);
			   String userId = WebCookie.getUserId(request);
			   if(userId == null || "".equals(userId)){
				   throw new RuntimeException("未获取到用户信息");
			   }
			   BackUser backUser = backUserService.queryBackUserByUserId(userId);
			   Integer permission = backUser.getPermission();
			   if(permission != 1){
				   throw new RuntimeException("您没有权限");
			   }
			   factoryInfo = factoryInfoService.queryByFactoryId(factoryId);
			   //获取logo存放路径，按照工厂id单独存放   						
				String path = UploadAndDownloadPathUtil.getLogoAndLicensePath() + factoryId;
				File file = new File(path);
				if  (!file.exists()  && !file .isDirectory())      
				{         
				    file .mkdir();     
				}  	  

				//调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
				Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path+File.separator);
			   if(!(request.getParameter("licenseName") == null || "".equals(request.getParameter("licenseName")))){
				   String licenseName = request.getParameter("licenseName");
				   factoryInfo.setFactoryLicense(UploadAndDownloadPathUtil.getStaticLogoAndLicensePath() + factoryId + "/" + licenseName);
				   factoryInfo.setUpdateTime(DateFormat.format());
				   factoryInfoService.updateFactoryInfo(factoryInfo);
			   }
		   } catch (Exception e) {   
			   e.printStackTrace();
			   logger.error("=============updateFactoryLicense  Exception=========:"+e.getMessage());
			   throw new Exception(e.getMessage());
		   }			
		   
		   return factoryInfo.getFactoryLicense();
	   }
	   
	 
	    

		   
 }
