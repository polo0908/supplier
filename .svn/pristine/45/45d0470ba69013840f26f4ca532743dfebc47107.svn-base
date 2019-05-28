package com.cbt.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbt.entity.BackUser;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.RfqInfo;
import com.cbt.entity.RfqInfoQuery;
import com.cbt.entity.User;
import com.cbt.service.BackUserService;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.RfqInfoService;
import com.cbt.service.UserService;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.SplitPage;
import com.cbt.util.SplitPage3;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.WebCookie;




@Controller 
public class RfqInfoController {
	
  @Resource
  private RfqInfoService rfqInfoService;
  @Resource
  private UserService userService;
  @Resource
  private ClientDrawingsService clientDrawingsService;
  @Resource
  private BackUserService backUserService;
  
  
public static Logger logger = Logger.getLogger(RfqInfoController.class); 
  /**
   * 获取 rfq_info表数据，返回前台显示
   * @param request
   * @param response
   * @return
 * @throws Exception 
   */
  @RequestMapping("/queryAllRfqInfo.do")
  public String queryAllRfqInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
	   
	     try {
			RfqInfoQuery rfqInfoQuery = getRfqInfo(request);
			 Integer pageSize = rfqInfoQuery.getPageSize();
			 String str1 = request.getParameter("page");
			 String salesId = WebCookie.getUserId(request); 
			 BackUser backUser = backUserService.queryBackUserByUserId(salesId);
			 Integer permission = backUser.getPermission();
		     if(permission == null || "".equals(permission)){
					permission = 2;
				}
			 
			    int page = 1;
				if(str1 != null) {
					page = Integer.parseInt(str1);
				}
			List<RfqInfoQuery> list	= null;
			int total = 0;
			
			//检查用户权限如果有管理员权限，查询所有新图纸询盘数据
			if(permission == 1){
				list = rfqInfoService.queryAllRfqInfoAdmin(rfqInfoQuery);
				total = rfqInfoService.totalAmountAdmin(rfqInfoQuery);
			}else{
				list = rfqInfoService.queryAllRfqInfo(rfqInfoQuery);
				total = rfqInfoService.totalAmount(rfqInfoQuery);
			}

			for (RfqInfo r : list) {
				 String dt = r.getDrawingName();
			     String[] dts = dt.split("&");
			     String drawingName = dts[0]+"."+FilenameUtils.getExtension(dt);
				 r.setDrawingName(drawingName);
			}				
			SplitPage3.buildPager(request, total, pageSize, page);
			request.setAttribute("rfqInfoQuery", list);
		} catch (Exception e) {
			logger.error("======RfqInfoController  queryAllRfqInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
	   return "new-drawings.jsp";
  }
  
  /**
   * 获取前台输入数据
   * @param request
   * @return
   */
    private RfqInfoQuery getRfqInfo(HttpServletRequest request){
    	
    	//根据rfqInfoQuery对象查询
	     RfqInfoQuery rfqInfoQuery = new RfqInfoQuery();
	     String str1 = request.getParameter("page");
	     String str2 = request.getParameter("pageSize");
	     String factoryId = WebCookie.getFactoryId(request);
	     
	    if(!(request.getParameter("userName") ==null || "".equals(request.getParameter("userName")))){
		 rfqInfoQuery.setUserName(request.getParameter("userName"));
	    }

		if(!(request.getParameter("beginDate") ==null || "".equals(request.getParameter("beginDate")))){
			rfqInfoQuery.setBeginDate(request.getParameter("beginDate"));
		}
		if(!(request.getParameter("endDate") ==null || "".equals(request.getParameter("endDate")))){
			rfqInfoQuery.setEndDate(request.getParameter("endDate"));
		}		   
		rfqInfoQuery.setFactoryId(factoryId);
		
	    int page = 1;
		if(str1 != null) {
			page = Integer.parseInt(str1);
		}
		int pageSize = 10;		
		if(str2 != null) {
			pageSize = Integer.parseInt(str2);
		}
		int start = (page-1) * pageSize;
		String salesId = WebCookie.getUserId(request);
		rfqInfoQuery.setSalesId(salesId);
		rfqInfoQuery.setStart(start);
		rfqInfoQuery.setPageSize(pageSize);
		rfqInfoQuery.setFactoryId(factoryId);
		
		
    	return rfqInfoQuery;
    	
    }
    
    
    /**
     * 更新图纸信息和跟进
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */   
    @RequestMapping("/updateRfqById.do")
    public String updateRfqById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try {
			//获取session用户
			String followUp = WebCookie.getUserName(request); 
			Integer id = null;
			if(!(request.getParameter("id") == null || "".equals(request.getParameter("id")))){
				id = Integer.parseInt(request.getParameter("id"));
			}
			
			RfqInfo rfqInfo = rfqInfoService.queryById(id);
			try {
				Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, UploadAndDownloadPathUtil.getNewDrawingUpLoadAndDownLoadPath());
				if(!(request.getParameter("drawingName") == null || "".equals(request.getParameter("drawingName")))){
					  String drawingName = request.getParameter("drawingName");
					  drawingName = multiFileUpload.get(drawingName);
					  rfqInfo.setDrawingName(drawingName);
					  rfqInfo.setDrawingPath(UploadAndDownloadPathUtil.getNewDrawingUpLoadAndDownLoadPath());
					  rfqInfo.setUpdateTime(DateFormat.format());
				}			      
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(!(request.getParameter("description") == null || "".equals(request.getParameter("description")))){	    	
				rfqInfo.setFollowComment(request.getParameter("description"));
			}	    
			rfqInfo.setDrawingState(2);
			rfqInfo.setFollowUp(followUp);    
			
			rfqInfoService.updateById(rfqInfo);
		} catch (Exception e) {
			logger.error("======RfqInfoController  updateRfqById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	    
	    
    	return "new-drawings.jsp";
    	
    }
    
    /**
     * 关闭订单(设置reOrderState = "3")
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("/closeRfqById.do")
    public String closeRfqById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//获取session用户
    	try {
			String followUp = WebCookie.getUserName(request);   	 
			Integer id = Integer.parseInt(request.getParameter("id"));
			RfqInfo rfqInfo = rfqInfoService.queryById(id);    	  
			rfqInfo.setDrawingState(3);
			rfqInfo.setFollowUp(followUp);
			
			rfqInfoService.updateById(rfqInfo);
		} catch (Exception e) {
			logger.error("======RfqInfoController  closeRfqById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    	
    	
    	return "new-drawings.jsp";
    	
    }
    
    
    @RequestMapping("/queryRfqById.do")
    public String queryRfqById(HttpServletRequest request, HttpServletResponse response) throws Exception{
   	    Integer id = Integer.parseInt(request.getParameter("id"));
    	RfqInfo rfqInfo = rfqInfoService.queryById(id);
    	/*
         *去除时间节点，获取图纸名 
         */
	     String dt = rfqInfo.getDrawingName();
	     String[] dts = dt.split("&");
	     String drawingName = dts[0]+"."+FilenameUtils.getExtension(dt);
	     rfqInfo.setDrawingName(drawingName);
	     String createTime = rfqInfo.getCreateTime();
	     String followTime = rfqInfo.getFollowTime();
	     String pat1 = "yyyy-MM-dd HH:mm:ss" ;
	     SimpleDateFormat sdf1 = new SimpleDateFormat(pat1) ;
	     Date d1 = null ;
	     Date d2 = null ;
	        try{    
	          d1 = sdf1.parse(createTime) ;   // 将给定的字符串中的日期提取出来    
	          rfqInfo.setCreateTime(sdf1.format(d1)); 
	          if(!(followTime == null || "".equals(followTime))){
	          d2 = sdf1.parse(followTime);	  
	          rfqInfo.setFollowTime(sdf1.format(d2));  
	          }
	          
	        }catch(Exception e){            // 如果提供的字符串格式有错误，则进行异常处理    
				logger.error("======RfqInfoController  queryRfqById======="+e.getMessage());
				e.printStackTrace();
				throw new Exception(e.getMessage());     // 打印异常信息    
	        }    
	     
	     
    	 request.setAttribute("rfqInfo", rfqInfo);
    	    	
    	return "details.jsp";
    	
    }
    
    /**
     * 查询新图纸信息
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
	 @RequestMapping("/queryRfqInfoById.do")
	 public String queryRfqInfoById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			 RfqInfo rfqInfo = rfqInfoService.queryById(id);
			 User user = userService.queryByUserId(rfqInfo.getUserid());		  
			 
			 request.setAttribute("user", user);
			 request.setAttribute("rfqInfo", rfqInfo);
		} catch (Exception e) {
			logger.error("======RfqInfoController  queryRfqInfoById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}		 
		 
		 return "newOrder_drawings2.jsp";
	 }

	 /**
	  * 更新rfqInfo的同时，插入client_drawings表
	  * 进行转订单时更新
	  * @param request
	  * @param response
	  * @return
	 * @throws Exception 
	  */
	 @ResponseBody
	 @RequestMapping("/updateRfqInfoById.do") 
	  public JsonResult<ClientDrawings> updateRfqInfoById(HttpServletRequest request, HttpServletResponse response) throws Exception{
		 
		    ClientDrawings clientDrawings = null;
			try {
				Integer id = null;
				if(!(request.getParameter("id") == null || "".equals(request.getParameter("id")))){
					 id = Integer.parseInt(request.getParameter("id"));
				}
				 
				 RfqInfo rfqInfo = rfqInfoService.queryById(id);
				 clientDrawings = new ClientDrawings();
				 
             //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
				try {
					Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
					if(!(request.getParameter("drawingName1") == null || "".equals(request.getParameter("drawingName1")))){
						  String drawingName = request.getParameter("drawingName1");
						  //图纸名通过&连接时间节点显示
						  drawingName = multiFileUpload.get(drawingName);
						  rfqInfo.setDrawingName(drawingName);
						  clientDrawings.setDrawingsName(drawingName);
						  clientDrawings.setDrawingsPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
						  }
				} catch (IllegalStateException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				} 
   

				 
				 if(!(request.getParameter("productName1") == null || "".equals(request.getParameter("productName1")))){
					 rfqInfo.setProductName(request.getParameter("productName1"));  
					 clientDrawings.setProductName(request.getParameter("productName1"));
				  }
				  if(!(request.getParameter("unitPrice1") == null || "".equals(request.getParameter("unitPrice1")))){
					  clientDrawings.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice1")));  
				  }
				  if(!(request.getParameter("moldPrice1") == null || "".equals(request.getParameter("moldPrice1")))){
					  clientDrawings.setMoldPrice(Double.parseDouble(request.getParameter("moldPrice1")));  
				  }
				  if(!(request.getParameter("quantity1") == null || "".equals(request.getParameter("quantity1")))){
					  clientDrawings.setQuantity(Integer.parseInt(request.getParameter("quantity1")));  
				  }
				  if(!(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId")))){
					  clientDrawings.setOrderId(request.getParameter("orderId"));  
				  }

				  
				  if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){
					  clientDrawings.setQuoteTime(request.getParameter("createTime"));  
				  }
				  String updateTime = DateFormat.format();
				  clientDrawings.setUpdateTime(updateTime);
				  clientDrawings.setDrawingState("YES");
				  
				  rfqInfoService.updateById(rfqInfo,clientDrawings); 
			} catch (Exception e) {
				logger.error("======RfqInfoController  updateRfqInfoById======="+e.getMessage());
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
			  return new JsonResult<ClientDrawings>(clientDrawings);
	  }
    
    
  
}
