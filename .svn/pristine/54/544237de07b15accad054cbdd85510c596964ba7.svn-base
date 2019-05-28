package com.cbt.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cbt.entity.Progress;
import com.cbt.util.OperationFileUtil;
import com.sun.star.io.IOException;


@Controller
@SessionAttributes("status")  
public class FileUploadController {

	
	@RequestMapping("/progress.do") 
	public void uploadFile(HttpServletRequest request,HttpServletResponse response, 
	            @RequestParam("file") CommonsMultipartFile file) throws IOException, java.io.IOException { 
	   response.setContentType("text/html"); 
	   response.setCharacterEncoding("GBK");  
	   String basePath = "G:\\apache-tomcat-7.0.57\\wtpwebapps\\logoAndLicense\\"; 
	   
	   PrintWriter out; 
	   Map<String, String> map = new HashMap<String, String>();
	   
	   if (file.getSize() > 0) { 
	     //文件上传的位置可以自定义 
		  map = OperationFileUtil.multiFileUpload1(request, basePath);
	   } 
	   out = response.getWriter(); 
	   if (!map.isEmpty()) { 
	    out.print("1"); 
	   } else { 
	    out.print("2"); 
	   } 
	} 
	
	
	@RequestMapping(value = "/progressStatus", method = RequestMethod.POST )
	@ResponseBody
	public Progress initCreateInfo(Map<String, Object> model) {
		Progress status = (Progress) model.get("status");
		if(status==null){
			return status;
		}
		return status;
	}
}
