package com.cbt.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderDrawings;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ReOrderDrawingsService;
import com.cbt.service.ReOrderService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;

@Controller
public class ReOrderDrawingsController {
    
	@Resource
	private ReOrderDrawingsService reOrderDrawingsService;
	@Resource
	private ReOrderService reOrderService;
	@Resource
	private ClientDrawingsService clientDrawingsService;
	
	public static Logger logger = Logger.getLogger(ReOrderDrawingsController.class);	
	
//	public static final String oldDrawingPath = UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath();
	
	
	 @RequestMapping("/queryReOrderDrawings.do")
	 public String queryReOrderDrawings(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 
		 try {
		     Integer reOrderId = Integer.parseInt(Base64Encode.getFromBase64(request.getParameter("id")));
			 List<ReOrderDrawings> list = reOrderDrawingsService.queryReOrderDrawings(reOrderId);
			 ReOrder reOrder = reOrderService.queryById(reOrderId);
			   for (ReOrderDrawings c : list) {
			        /*
			         *去除时间节点，获取图纸名 
			         */
				     String dt = c.getDrawingsName();
				     String[] dts = dt.split("&");
				     String drawingName = dts[0];
					 c.setDrawingsName(drawingName);	
			     }
			 request.setAttribute("reOrder", reOrder);
			 request.setAttribute("reOrderDrawings", list);
		} catch (Exception e) {
			logger.error("======ReOrderDrawingsController  queryReOrderDrawings======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		 
		 
		 return "project.jsp";
	 }
	 
	 /**
	   * 根据id更新图纸信息
	   * @throws IOException 
	   * @throws IllegalStateException 
	   */
	  @ResponseBody
	  @RequestMapping("/saveReOrderDrawings.do")
	  public JsonResult<List<ReOrderDrawings>>  saveReOrderDrawings(HttpServletRequest request, HttpServletResponse response) throws Exception{
		   
		 //多条插入clientDrawings表
		 //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
	      List<ReOrderDrawings> reOrderDrawing = null;
		try {
			  Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
			  Integer reOrderId = Integer.parseInt(request.getParameter("reOrderId"));
			  ReOrderDrawings reOrderDrawings = new ReOrderDrawings();
			  if(!(request.getParameter("drawingName") == null || "".equals(request.getParameter("drawingName")))){
			  String drawingName = request.getParameter("drawingName");
			  //图纸名通过&连接时间节点显示
			  drawingName = multiFileUpload.get(drawingName);
			  reOrderDrawings.setDrawingsName(drawingName);
			  }

			  if(!(request.getParameter("productName") == null || "".equals(request.getParameter("productName")))){
				  reOrderDrawings.setProductName(request.getParameter("productName"));  
			  }
			  if(!(request.getParameter("unitPrice") == null || "".equals(request.getParameter("unitPrice")))){
				  reOrderDrawings.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice")));  
			  }
			  if(!(request.getParameter("moldPrice") == null || "".equals(request.getParameter("moldPrice")))){
				  reOrderDrawings.setMoldPrice(Double.parseDouble(request.getParameter("moldPrice")));  
			  }
			  if(!(request.getParameter("quantity") == null || "".equals(request.getParameter("quantity")))){
				  reOrderDrawings.setQuantity(Integer.parseInt(request.getParameter("quantity")));  
			  }
			  reOrderDrawings.setReOrderId(reOrderId);
			  reOrderDrawings.setDrawingsPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
			  reOrderDrawings.setDrawingState("YES");
			  reOrderDrawings.setStatus("Ordered");
			  
			  reOrderDrawingsService.insertReOrderDrawing(reOrderDrawings);
			  
			  reOrderDrawing = reOrderDrawingsService.queryReOrderDrawings(reOrderId);
		} catch (Exception e) {
			logger.error("======ReOrderDrawingsController  saveReOrderDrawings======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		  return new JsonResult<List<ReOrderDrawings>>(reOrderDrawing);
	  }
	  
	  /**
	   * 执行删除单条操作
	   * 返回List数据
	   * @param request
	   * @param response
	   * @return
	 * @throws Exception 
	   */
	  @RequestMapping("/deleteReOrderDrawingsById.do")
	  @ResponseBody  
	  public JsonResult<List<ReOrderDrawings>> deleteReOrderDrawingsById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		  List<ReOrderDrawings> list = null;
		try {
			Integer id = null;
			  if(!(request.getParameter("id") == null || "".equals(request.getParameter("id")))){
				  id = Integer.parseInt(request.getParameter("id"));
			  }

			  ReOrderDrawings r = reOrderDrawingsService.queryById(id);
			  Integer reOrderId = r.getReOrderId();
			  reOrderDrawingsService.deleteReOrderDrawing(id);
			   
			  list = reOrderDrawingsService.queryReOrderDrawings(reOrderId);
		} catch (Exception e) {
			logger.error("======ReOrderDrawingsController  deleteReOrderDrawingsById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		  
		  return new JsonResult<List<ReOrderDrawings>>(list);
	  }
	  
	  
	 @ResponseBody
	 @RequestMapping("/queryReOrderDrawingsById.do")
	 public JsonResult<ReOrderDrawings> queryReOrderDrawingsById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 ReOrderDrawings reOrderDrawings = null;
		try {
			if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
			    	throw new RuntimeException("请选择一个图纸！");
			  }	
			 
			 Integer id = Integer.parseInt(Base64Encode.getFromBase64(request.getParameter("id")));
			 reOrderDrawings = reOrderDrawingsService.queryById(id);
			 request.setAttribute("reOrderDrawings", reOrderDrawings);
		} catch (Exception e) {
			logger.error("======ReOrderDrawingsController  queryReOrderDrawingsById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		 
		 return new JsonResult<ReOrderDrawings>(reOrderDrawings);
	 }
	 
	 /**
	  * 更新reorder对应图纸信息
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception 
	  */
	 @ResponseBody
	 @RequestMapping("/updateReOrderDrawingsById.do") 
	  public JsonResult<List<ReOrderDrawings>> updateReOrderDrawingsById(HttpServletRequest request, HttpServletResponse response) throws Exception{
	    
		  List<ReOrderDrawings> list = null;
		try {
			if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
			    	throw new RuntimeException("请选择一个图纸！");
			    }	        
			    Integer id = Integer.parseInt(request.getParameter("id"));
			    ReOrderDrawings reOrderDrawings = reOrderDrawingsService.queryById(id);
			   //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
			    try {
					Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
					  if(!(request.getParameter("drawingName1") == null || "".equals(request.getParameter("drawingName1")))){
						  String drawingName = request.getParameter("drawingName1");
						  //图纸名通过&连接时间节点显示
						  drawingName = multiFileUpload.get(drawingName);
						  reOrderDrawings.setDrawingsName(drawingName);
						  reOrderDrawings.setDrawingsPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
						  reOrderDrawings.setUpdateTime(DateFormat.format());
						  }
			    } catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
   
			     if(!(request.getParameter("productName1") == null || "".equals(request.getParameter("productName1")))){
			    	 reOrderDrawings.setProductName(request.getParameter("productName1"));  
				  }
				  if(!(request.getParameter("unitPrice1") == null || "".equals(request.getParameter("unitPrice1")))){
					  reOrderDrawings.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice1")));  
				  }
				  if(!(request.getParameter("moldPrice1") == null || "".equals(request.getParameter("moldPrice1")))){
					  reOrderDrawings.setMoldPrice(Double.parseDouble(request.getParameter("moldPrice1")));  
				  }
				  if(!(request.getParameter("quantity1") == null || "".equals(request.getParameter("quantity1")))){
					  reOrderDrawings.setQuantity(Integer.parseInt(request.getParameter("quantity1")));  
				  }

			
				  
				  reOrderDrawingsService.updateReOrderDrawing(reOrderDrawings);  
				 
				  list = reOrderDrawingsService.queryReOrderDrawings(reOrderDrawings.getReOrderId());
		} catch (Exception e) {
			logger.error("======ReOrderDrawingsController  updateReOrderDrawingsById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
			  return new JsonResult<List<ReOrderDrawings>>(list);
	  }
	 
	 
}
