package com.cbt.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.UpdateDrawing;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ClientOrderService;
import com.cbt.service.UpdateDrawingService;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.UploadAndDownloadPathUtil;




@RequestMapping("/drawing")
@Controller 
public class ClientDrawingsController {
	
  @Resource
  private ClientDrawingsService clientDrawingsService;
  @Resource
  private ClientOrderService clientOrderService;
  @Resource
  private UpdateDrawingService updateDrawingService;
  
  public static Logger logger = Logger.getLogger(ClientDrawingsController.class);
  
  
  /**
   * 根据订单号查询图纸信息
   * @param queryDrawingsById
   * @return
   */
  @ResponseBody
  @RequestMapping("/queryDrawingsById.do")
  public JsonResult<ClientDrawings> queryDrawingsById(HttpServletRequest request, HttpServletResponse response){
	  ClientDrawings clientDrawings = null;
	try {
		Integer id = Integer.parseInt(request.getParameter("id"));
		  clientDrawings = clientDrawingsService.queryById(id);
	} catch (Exception e) {
		logger.error("======ClientDrawingsController  queryDrawingsById======="+e.getMessage());
		e.printStackTrace();
	}
	  
	  return new JsonResult<ClientDrawings>(clientDrawings);
  }
  
  
  /**
   * 插入clientDrawings
 * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("/saveClientDrawings.do")
  public JsonResult<List<ClientDrawings>>  saveClientDrawings(HttpServletRequest request, HttpServletResponse response) throws Exception{
	   
	  
      List<ClientDrawings> list = null;
	try {
		//调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
		  String path = UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath();
		  ClientDrawings clientDrawing = new ClientDrawings();
		  UpdateDrawing updateDrawing = new UpdateDrawing();
		  
		  if(!(request.getParameter("drawingName") == null || "".equals(request.getParameter("drawingName")))){
		  String drawingName = request.getParameter("drawingName");
		  //图纸名通过&连接时间节点显示
		  clientDrawing.setDrawingsName(drawingName);
		  updateDrawing.setDrawingName(drawingName);
		  updateDrawing.setDrawingPath(path);
		  updateDrawing.setUpdateTime(DateFormat.format());
		  }
		  String orderId = null;
		  if(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))){
			 throw new RuntimeException("未获取到orderId");
		  }else{
			  orderId = request.getParameter("orderId");
			  clientDrawing.setOrderId(orderId);  
		  }
		  
		  Double price = 0.0;
		  if(!(request.getParameter("productName") == null || "".equals(request.getParameter("productName")))){
		  clientDrawing.setProductName(request.getParameter("productName"));  
		  }
		  Double unitPrice = 0.0;
		  if(!(request.getParameter("unitPrice") == null || "".equals(request.getParameter("unitPrice")))){
			  unitPrice = Double.parseDouble(request.getParameter("unitPrice"));
			  clientDrawing.setUnitPrice(unitPrice);  
		  }
		  Double moldPrice = 0.0;
		  if(!(request.getParameter("moldPrice") == null || "".equals(request.getParameter("moldPrice")))){
			  moldPrice = Double.parseDouble(request.getParameter("moldPrice"));
			  clientDrawing.setMoldPrice(moldPrice);  
		  }
		  Integer quantity = 0;
		  if(!(request.getParameter("quantity") == null || "".equals(request.getParameter("quantity")))){
			  quantity = Integer.parseInt(request.getParameter("quantity"));
			  clientDrawing.setQuantity(quantity);  
		  }
		  if(!(request.getParameter("userid") == null || "".equals(request.getParameter("userid")))){
			  clientDrawing.setUserid(request.getParameter("userid"));  
		  }
		  if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){
			  clientDrawing.setQuoteTime(request.getParameter("createTime"));  
		  }
		  price = new BigDecimal(unitPrice).multiply(new BigDecimal(quantity)).add(new BigDecimal(moldPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		  
		  String updateTime = DateFormat.format();
		  clientDrawing.setUserid(request.getParameter("userid"));
		  clientDrawing.setUpdateTime(updateTime);
		  clientDrawing.setDrawingsPath(path);
		  clientDrawing.setDrawingState("YES");
		  clientDrawing.setStatus("Ordered");
		  
		  list = clientDrawingsService.insertClientDrawing(clientDrawing, updateDrawing, orderId);
	  
	} catch (Exception e) {
		logger.error("======ClientDrawingsController  saveClientDrawings======="+e.getMessage());
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}
	  return new JsonResult<List<ClientDrawings>>(list);
  }
  
  

  @RequestMapping("/deleteDrawingsById.do")
  @ResponseBody  
  public JsonResult<List<ClientDrawings>> deleteDrawingsById(HttpServletRequest request, HttpServletResponse response) throws Exception{
	  
	  List<ClientDrawings> list = null;
	try {
		Integer id = null;
		  if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
			 
			  throw new RuntimeException("未获取到图纸");
		  }else{
			  id = Integer.parseInt(request.getParameter("id"));
		  }

		  ClientDrawings c = clientDrawingsService.queryById(id);
		  String orderId = c.getOrderId();		   
		  list = clientDrawingsService.deleteClientDrawing(id,orderId);
	} catch (Exception e) {
		logger.error(e.getMessage());
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}
	  

	  return new JsonResult<List<ClientDrawings>>(list);
  }
  
  
  /**
   * 订单详情页更新图纸（单个产品更新数据）
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @ResponseBody
  @RequestMapping("/updateDrawingsById.do") 
  public JsonResult<List<ClientDrawings>> updateDrawingsById(HttpServletRequest request, HttpServletResponse response) throws Exception{
	
	     List<ClientDrawings> list = null;
	     UpdateDrawing updateDrawing = new UpdateDrawing();
		try {
			Integer id = Integer.parseInt(request.getParameter("id"));
			 
			 ClientDrawings clientDrawing = clientDrawingsService.queryById(id);
			 if(!(request.getParameter("productName1") == null || "".equals(request.getParameter("productName1")))){
			  clientDrawing.setProductName(request.getParameter("productName1"));  
			  }
			  if(!(request.getParameter("unitPrice1") == null || "".equals(request.getParameter("unitPrice1")))){
				  clientDrawing.setUnitPrice(Double.parseDouble(request.getParameter("unitPrice1")));  
			  }
			  if(!(request.getParameter("moldPrice1") == null || "".equals(request.getParameter("moldPrice1")))){
				  clientDrawing.setMoldPrice(Double.parseDouble(request.getParameter("moldPrice1")));  
			  }
			  if(!(request.getParameter("quantity1") == null || "".equals(request.getParameter("quantity1")))){
				  clientDrawing.setQuantity(Integer.parseInt(request.getParameter("quantity1")));  
			  }
			  if(!(request.getParameter("drawingName1") == null || "".equals(request.getParameter("drawingName1")))){
				  String drawingName = request.getParameter("drawingName1");
				  //图纸名通过&连接时间节点显示
				  clientDrawing.setDrawingsName(drawingName);
				  
				  updateDrawing.setDrawingName(drawingName);
				  updateDrawing.setDrawingPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
				  updateDrawing.setUpdateTime(DateFormat.format());
				  updateDrawing.setDrawingId(id);
				 }
			  if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){
				  clientDrawing.setQuoteTime(request.getParameter("createTime"));  
			  }			  		  
			  String updateTime = DateFormat.format();
			  clientDrawing.setUpdateTime(updateTime);				  
			  clientDrawing.setDrawingsPath(UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath());
			  
			  list = clientDrawingsService.updateClientDrawings(clientDrawing,updateDrawing);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());	
		}
		  return new JsonResult<List<ClientDrawings>>(list);
  }
  
  
  
  /**
   * 插入clientDrawings(12.22)(未使用)
   * modify:2017/06/22
   * @throws Exception 
   */
  @ResponseBody
  @RequestMapping("/createClientDrawings.do")
  public JsonResult<List<ClientDrawings>>  createClientDrawings(HttpServletRequest request, HttpServletResponse response) throws Exception{
	  
	  List<ClientDrawings> list1 = null;
	try {
		ClientDrawings clientDrawing = new ClientDrawings();
		UpdateDrawing updateDrawing = new UpdateDrawing();
		
		  if(request.getParameter("orderId" ) == null  || "".equals(request.getParameter("orderId"))){
			  throw new RuntimeException("orderId不能为空！");
		  }
		  String orderId = request.getParameter("orderId");
		  clientDrawing.setOrderId(orderId);  
		  String userid = null;
		  if(!(request.getParameter("userid") == null || "".equals(request.getParameter("userid")))){
			  userid = request.getParameter("userid");  
		  }
		  String createTime = null;
		  if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){
			  createTime = request.getParameter("createTime");  
		  }
  
		  //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
		  String path = UploadAndDownloadPathUtil.getOldDrawingUpLoadAndDownLoadPath();
		  Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload1(request, path);
		 //获取前台封装的图纸对象
			 String clientDrawings = request.getParameter("clientDrawings");
//			 ArrayList<Map<Object, Object>> list = new ArrayList<Map<Object,Object>>();
			 
			 String status = "Ordered";              //初始为报价状态
			 if(clientDrawings.endsWith(",")){
				 clientDrawings = clientDrawings.substring(0,clientDrawings.length()-1);
				}
			 
			 	  String[] split = clientDrawings.split(",");
			 	  
			    Double totalPrice = 0.0;	  
				for (int i = 0; i < split.length; i++) {
					String[] split2 = split[i].split("%");
//					Map<Object,Object> map= new HashMap<Object,Object>();
//					List<UpdateDrawing> updateDrawings = new ArrayList<UpdateDrawing>();
					
					String productName = null;
					
					if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
						productName = split2[0].replaceFirst(".", "");
					}
					Double price = 0.00;
					if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
						price = Double.valueOf(split2[1].replaceFirst(".", ""));
					}
					Integer quantity = 0;
					if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
						quantity = Integer.valueOf(split2[2].replaceFirst(".", ""));	
					}
					Double mold = 0.00;
					if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
						mold = Double.valueOf(split2[3].replaceFirst(".", ""));
					}
					String drawingName = null;
					if(!(split2[4].replaceFirst(".", "") == null || "".equals(split2[4].replaceFirst(".", "")))){
						drawingName = split2[4].replaceFirst(".", "");
						if(!(multiFileUpload.get(drawingName) == null || "".equals(multiFileUpload.get(drawingName)))){
							drawingName = multiFileUpload.get(drawingName);
						}
						
					}			 
					totalPrice = new BigDecimal(price).multiply(new BigDecimal(quantity)).add(new BigDecimal(mold)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					
					
					if(!(("".equals(productName) || productName == null)&& ("".equals(price) || price == null)
						 &&	("".equals(quantity) || quantity == null) && ("".equals(mold) || mold == null)
							&& ("".equals(drawingName) || drawingName == null))){
						clientDrawing.setDrawingsName(drawingName);
						clientDrawing.setDrawingsPath(path);
						clientDrawing.setMoldPrice(mold);
						clientDrawing.setProductName(productName);
						clientDrawing.setQuantity(quantity);
						clientDrawing.setUnitPrice(price);
						clientDrawing.setOrderId(orderId);
						clientDrawing.setUserid(userid);
						clientDrawing.setQuoteTime(createTime);
						clientDrawing.setUpdateTime(DateFormat.format());
						clientDrawing.setStatus(status);
						if( drawingName != null || "".equals(drawingName)){
							clientDrawing.setDrawingState("YES");
						}else{
							clientDrawing.setDrawingState("NO");
						}
						
						//插入更新的图纸数据（update_drawing）
						if( drawingName != null || "".equals(drawingName)){
							updateDrawing.setDrawingName(drawingName);
							updateDrawing.setDrawingPath(path);
							updateDrawing.setUpdateTime(DateFormat.format());
							updateDrawing.setDrawingId(clientDrawing.getId());	
						}
						
						clientDrawingsService.insertClientDrawing(clientDrawing, updateDrawing, orderId);
						
					}
					
				}
		  
		  list1 = clientDrawingsService.queryListByOrderId(orderId);
	} catch (Exception e) {
		logger.error(e.getMessage());
		e.printStackTrace();
		throw new Exception(e.getMessage());
	}

	  return new JsonResult<List<ClientDrawings>>(list1);
  }
  
}
