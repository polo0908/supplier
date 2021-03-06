package com.cbt.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.enums.DocumentGenEnum;
import com.cbt.enums.ImgGenEnum;
import com.cbt.service.ClientOrderService;
import com.cbt.util.DateFormat;
import com.cbt.util.ImageCompressUtil;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;

@Controller
public class UploadAttachmentController {

	
	
   public static Logger logger = Logger.getLogger(UploadAttachmentController.class);

   @Resource
   private ClientOrderService clientOrderService;
   
 	/**
 	 * 上传文件返回文件
 	 * @param request
 	 * @param response
 	 * @return
 	 */
    @ResponseBody
 	@RequestMapping("/uploadAttachment.do")
 	public JsonResult<String> uploadAttachment(HttpServletRequest request,HttpServletResponse response){
 		
 	 try {
 		 String path = "";
 		 if(!(StringUtils.isBlank(request.getParameter("orderId")))){
 	        String orderId = request.getParameter("orderId");
 	        path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + orderId + File.separator;
 		 }else{
 			path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + DateFormat.currentDateString() + File.separator; 
 		 }
		 File file = new File(path);
		 if  (!file.exists()  && !file .isDirectory())      
		 {         
			 file .mkdir();     
		 }  	    	  
 		Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
 		String fileNamePath = "";
 		if(!(multiFileUpload == null || multiFileUpload.size() == 0)){
 			 Set<String> keySet = multiFileUpload.keySet();
 			   for (String key : keySet) {	
 				  String fileName = multiFileUpload.get(key);
 				  fileNamePath = fileName;
 			   }
 		} 
 		
 		return new JsonResult<String>(0,"success",fileNamePath);	
 	} catch (IllegalStateException e) {
 		e.printStackTrace();
 		logger.info(">>>>>>>>>>>>>>>>>UploadAttachmentController_uploadAttachment_exception--:--"+e);
 		return new JsonResult<String>(1,"failed");	
 	} catch (IOException e) {
 		e.printStackTrace();
 		logger.info(">>>>>>>>>>>>>>>>>UploadAttachmentController_uploadAttachment_exception--:--"+e);
 		return new JsonResult<String>(1,"failed");	
 	} catch (ParseException e) {
 		e.printStackTrace();
 		logger.info(">>>>>>>>>>>>>>>>>UploadAttachmentController_uploadAttachment_exception--:--"+e);
 		return new JsonResult<String>(1,"failed");	
	}
 			
 	}

 
 
 
 	/**
 	 * 上传文件返回文件名（加时间节点显示）
 	 * @param request
 	 * @param response
 	 * @return
 	 */
    @ResponseBody
 	@RequestMapping("/uploadAttachmentAndChangeName.do")
 	public JsonResult<String> uploadAttachmentAndChangeName(HttpServletRequest request,HttpServletResponse response){
 		
 	 try {
 		 String drawingName = request.getParameter("selectDrawingName");
 		 String path = UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath();
		 File file = new File(path);
		 if  (!file.exists()  && !file .isDirectory())      
		 {         
			 file .mkdir();     
		 }  	    	  
 		Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, path);
 		String fileName = "";
 		if(!(multiFileUpload == null || multiFileUpload.size() == 0)){
 			fileName = multiFileUpload.get(drawingName);
 		} 
 		
 		return new JsonResult<String>(0,"success",fileName);	
 	} catch (IllegalStateException e) {
 		e.printStackTrace();
 		logger.info(">>>>>>>>>>>>>>>>>UploadAttachmentController_uploadAttachmentAndChangeName_exception--:--"+e);
 		return new JsonResult<String>(1,"failed");	
 	} catch (IOException e) {
 		e.printStackTrace();
 		logger.info(">>>>>>>>>>>>>>>>>UploadAttachmentController_uploadAttachmentAndChangeName_exception--:--"+e);
 		return new JsonResult<String>(1,"failed");	
 	} 
 			
 	}
    
    
    
    /**
     * 上传里程碑图片
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @ResponseBody
    @RequestMapping("/uploadMultipleFiles.do")
    public JsonResult<String> uploadMultipleFiles(HttpServletRequest request,HttpServletResponse response) throws Exception{
 	   
 	    try {
 	    	String orderId = request.getParameter("orderId1");
 			String path = UploadAndDownloadPathUtil.getMilestonePath() + orderId + File.separator;
 			File file = new File(path);
 			if  (!file.exists()  && !file .isDirectory())      
 			{         
 			 file .mkdir();     
 			}  	   		   
 			 //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
 			Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, path+File.separator);
 			String picNames = "";
 			if(!(multiFileUpload == null || multiFileUpload.size() == 0)){
 				 Set<String> keySet = multiFileUpload.keySet();
 				   for (String key : keySet) {	 					   
 					   String extension = FilenameUtils.getExtension(key);
 					   
 					   //根据文件后缀判断类型
 					   if(DocumentGenEnum.getGen(extension) != null){
 						  picNames = multiFileUpload.get(key);
 					   }else if(ImgGenEnum.getGen(extension) != null){
 						   String pic = multiFileUpload.get(key);  
	 					   String photoCompressName = "";
	 				        if(pic != null && !"".equals(pic)){
	 				        	photoCompressName = OperationFileUtil.changeFilenameZip(pic);
	 				        }		
	 				        String photoPath = path + pic;
	 	                    String photoPathCompress = path + photoCompressName;
	 				        ImageCompressUtil.saveMinPhoto(photoPath, photoPathCompress, 215, 0.9d);
 	 					 
 	 				       picNames += pic + ","; 
 					  }					  
 				   }
 				   if(picNames.endsWith(",")){
 					  picNames = picNames.substring(0,picNames.length()-1);
 				   }				   			        
 				   
 			}
 			return new JsonResult<String>(0,"success",picNames);
 		} catch (IllegalStateException e) {
 			e.printStackTrace();
 			logger.error("======UploadAttachmentController====== uploadMultipleFiles============"+e.getMessage());
 			return new JsonResult<String>(1,"upload failed");
 		} catch (IOException e) {
 			e.printStackTrace();
 			logger.error("======UploadAttachmentController====== uploadMultipleFiles============"+e.getMessage());	
 			return new JsonResult<String>(1,"upload failed");
 		}	
 	   
    }
    
    
}
