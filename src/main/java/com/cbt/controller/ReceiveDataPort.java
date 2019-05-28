package com.cbt.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.NbMailCountry;
import com.cbt.entity.PaymentPlan;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.entity.ShippingInfo;
import com.cbt.entity.User;
import com.cbt.entity.UserFactoryRelation;
import com.cbt.entity.UserRelationEmail;
import com.cbt.service.BackUserService;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ClientOrderService;
import com.cbt.service.FactoryUserRelationService;
import com.cbt.service.InvoiceInfoService;
import com.cbt.service.NbMailCountryService;
import com.cbt.service.QuotationService;
import com.cbt.service.SaleCustomerService;
import com.cbt.service.UserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.HttpClientDownloadUtil;
import com.cbt.util.JsonUtil;
import com.cbt.util.SerializeUtil;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.UtilFuns;
import com.sun.star.uno.RuntimeException;

/**
 * 获取数据接口
 * 动态获取数据，并存入数据库
 * @author polo
 *
 */
@Controller
@RequestMapping("/port")
public class ReceiveDataPort {
	
	
	private static final String FACTORY_ID = "f1010";  //凯融默认工厂ID
	private static final int ORDER_SOURCE = 3;         //ERP拉取订单
	private static final int TRANSACTION_TYPE = 1;     //老客户
	private static final int QUOTATION_STATUS = 1;     //报价中状态
     //订单初始类型
	private static final int ORDER_TYPE=5;

//	private static final int TEMP_USER=2;
	
	  @Resource
	  private ClientOrderService clientOrderService;
	  @Resource
	  private ClientDrawingsService clientDrawingsService;
	  @Resource
	  private UserService userService;
	  @Resource
	  private SaleCustomerService saleCustomerService;
	  @Resource
	  private QuotationService quotationService;
	  @Resource
	  private InvoiceInfoService invoiceInfoService;
	  @Resource
	  private BackUserService backUserService;
	  @Resource
	  private FactoryUserRelationService factoryUserRelationService;
	  @Resource
	  private NbMailCountryService nbMailCountryService;
	  
	  /**
	   * 获取ERP订单数据（订单号、创建时间、金额、客户id）(NBMail订单)
	   * @author polo
	   * 2017年5月22日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/receiveAllData.do")	  
	   public String queryAllClientOrder(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		  
	      /* 设置格式为text/json    */
          response.setContentType("text/json"); 
          /*设置字符集为'UTF-8'*/
          response.setCharacterEncoding("UTF-8");
		  //获取接收的数据
		  String jsonStr = request.getParameter("result");
		  
		  jsonStr = URLDecoder.decode(jsonStr, "utf-8"); 
		  //将json数据反序列化为map格式
		  Map<Object,Object> map = SerializeUtil.JsonToMap(jsonStr);
		  
                  
//		  /**
//		   * 解析ClientOrder数据
//		   * 
//		   */
//		  //转换为list对象 
		  List<Object> list = (List<Object>)map.get("invoice");	
		  //存放对象的list1集合
		  List<Object> list1 = new ArrayList<Object>();
		  //循环list先将底层map转换为json字符串，再反序列化为map
		  //实例：{amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426}		  	
		  for (Object c: list) {	
			  ClientOrder clientOrder = new ClientOrder();
			  String str = null;
			  str = c.toString();
			  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);	  

			  if(!(cl.get("amount") == null || "".equals(cl.get("amount")))){
				  
				  Double d = Double.parseDouble (cl.get("amount").toString());
				  clientOrder.setAmount(d); 
			  }else{
				  clientOrder.setAmount(0.0);
			  }
			  if(!(cl.get("createtime") == null || "".equals(cl.get("createtime")))){
				  clientOrder.setCreateTime((String) cl.get("createtime")); 
			  }

			  if(!(cl.get("projectid") == null || "".equals(cl.get("projectid")))){
				  clientOrder.setOrderId((String) cl.get("projectid"));
			  }else{
				  continue;
			  }			  			 
	
			  if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){
				 String s = cl.get("eid").toString();
				 clientOrder.setUserid(s);
			  }else{
				 continue;
			  }
			  clientOrder.setFactoryId(FACTORY_ID);
			  clientOrder.setOrderSource(ORDER_SOURCE);

              list1.add(clientOrder);
		   }
		  clientOrderService.insertClientOrders(list1);
		  
		  return "SUCCESS";
	  }
	  
	  
	  
	  
	  /**
	   * 获取ERP订单数据（订单号、创建时间、金额、客户id）(ERP订单)
	   * @author polo
	   * 2017年6月26日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/importERPOrderByYear.do")	  
	   public String importERPOrderByYear(@RequestParam Map<String,String> map2) throws UnsupportedEncodingException{
		  
		  try {
			  String jsonStr = map2.get("Invoiceinfo1");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);          
			  
			  /**
			   * 解析ClientOrder数据
			   * 
			   */
			  //存放对象的list1集合
			  List<Object> list1 = new ArrayList<Object>();
			  //循环list先将底层map转换为json字符串，再反序列化为map
			  //实例：{amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426}		  	
			  for (Object c: list) {	
				  ClientOrder clientOrder = new ClientOrder();
				  SaleCustomer saleCustomer = new SaleCustomer();
				  SaleCustomer saleCustomer2 = new SaleCustomer();
			  	  SaleOrder saleOrder = new SaleOrder();
			  	  SaleOrder saleOrder2 = new SaleOrder();
				  FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
				  UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
				  List<SaleCustomer> list2 = new ArrayList<SaleCustomer>();
				  List<SaleOrder> list3 = new ArrayList<SaleOrder>();
				  
				  
				  String salesId = null;
			  	  String salesId1 = null;
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> map = SerializeUtil.JsonToMap(str);	  
			 
				    String projectid = map.get("projectid").toString();
					String eid = map.get("eid").toString();
					if(projectid == null || "".equals(projectid) || eid == null || "".equals(eid)){
						continue;
					}else{
						projectid = projectid.toUpperCase();
						int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
						if(count == 0){
							factoryUserRelation.setFactoryId(FACTORY_ID);
							factoryUserRelation.setUserid(eid);
							userFactoryRelation.setFactoryId(FACTORY_ID);
							userFactoryRelation.setUserid(eid);
						}else{
							factoryUserRelation = null;
							userFactoryRelation = null;
						}
					}
					Double amount = 0.0;
					if(!(map.get("amount") == null || "".equals(map.get("amount")))){
						amount = Double.parseDouble(map.get("amount").toString());
					}
					Double actualAmount = 0.0;
					if(!(map.get("sumacount") == null || "".equals(map.get("sumacount")))){							  
						  actualAmount = Double.parseDouble(map.get("sumacount").toString());					  
					  }
					
					String createTime = map.get("createtime").toString();	  	
					String salesName = map.get("customerManager").toString();
					String salesName1 = map.get("merchandManager1").toString();
					
					BackUser backUser = backUserService.queryBackUserByName(salesName);
					BackUser backUser1 = backUserService.queryBackUserByName(salesName1);
					
					if(!(backUser == null || "".equals(backUser))){
						salesId = backUser.getBackUserid();
					}
					if(!(backUser1 == null || "".equals(backUser1))){
						salesId1 = backUser1.getBackUserid();
					}
					clientOrder.setSalesName(salesName1);
					clientOrder.setOrderId(projectid);
					clientOrder.setUserid(eid);
					clientOrder.setCreateTime(createTime);
					clientOrder.setAmount(amount);
					clientOrder.setActualAmount(actualAmount);
					clientOrder.setFactoryId(FACTORY_ID);
					clientOrder.setOrderSource(ORDER_SOURCE);
					clientOrder.setOrderTypeId(ORDER_TYPE);
					
					if(!(salesId == null || "".equals(salesId))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId, eid);
					    if(count == 0){
					    	saleCustomer.setSalesId(salesId);
					    	saleCustomer.setUserid(eid);
					    	list2.add(saleCustomer);
					    }
					    
					    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId, projectid);
					    if(count1 == 0){
						     saleOrder.setOrderId(projectid);
							 saleOrder.setSalesId(salesId);
							 list3.add(saleOrder);
					    }

					}
					if(!(salesId1 == null || "".equals(salesId1))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId1, eid);
						if(count == 0){
							saleCustomer2.setSalesId(salesId1);
							saleCustomer2.setUserid(eid);
							if(!(salesId1.equals(salesId))){
								list2.add(saleCustomer2);
							}
						}
						
					    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId1, projectid);
					    if(count1 == 0){
						     saleOrder2.setOrderId(projectid);
							 saleOrder2.setSalesId(salesId1);
							if(!(salesId1.equals(salesId))){
								list3.add(saleOrder2);
							}
					     }
					}
					clientOrderService.insertClientOrder(clientOrder, projectid, list2,factoryUserRelation,userFactoryRelation,list3);
			   }
			  
			  return "SUCCESS";
			  
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "NO";
		} catch (Exception e) {
			e.printStackTrace();
			return "NO";
		}

	  }
	  
	  	  
	  
	  
	  
	  
	  
	  /**
	   * 导入客户关联订单表(停用 2017.07.03)
	   * @param map2
	   * @return
	   * @throws
	   */
	  @ResponseBody
	  @RequestMapping("/importSaleOrder.do")	  
	   public String importSaleOrder(@RequestParam Map<String,String> map2){
		  
		  try {
			  String jsonStr = map2.get("Invoiceinfo1");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);          			  
			  /**
			   * 解析ClientOrder数据
			   * 
			   */
			  //存放对象的list1集合
			  List<SaleOrder> list1 = new ArrayList<SaleOrder>();
			  //循环list先将底层map转换为json字符串，再反序列化为map
			  //实例：{amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426}		  	
			  for (Object c: list) {	
				  String salesId = null;
			  	  String salesId1 = null;
			  	  SaleOrder saleOrder = new SaleOrder();
			  	  SaleOrder saleOrder2 = new SaleOrder();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> map = SerializeUtil.JsonToMap(str);	  			 
				  String projectid = map.get("projectid").toString();
				  if(projectid == null || "".equals(projectid)){
					  continue;
				  }
				String salesName = map.get("customerManager").toString();
				String salesName1 = map.get("merchandManager1").toString();
				
				BackUser backUser = backUserService.queryBackUserByName(salesName);
				BackUser backUser1 = backUserService.queryBackUserByName(salesName1);
				
				if(!(backUser == null || "".equals(backUser))){
					salesId = backUser.getBackUserid();
				}
				if(!(backUser1 == null || "".equals(backUser1))){
					salesId1 = backUser1.getBackUserid();
				}
			    if(!(salesId == null || "".equals(salesId))){
				  saleOrder.setOrderId(projectid);
				  saleOrder.setSalesId(salesId);
				  list1.add(saleOrder);
			    }
			    if(!(salesId1 == null || "".equals(salesId1))){
			    	saleOrder2.setOrderId(projectid);
			    	saleOrder2.setSalesId(salesId1);
			    	list1.add(saleOrder2);
			    }
			  }
			  saleCustomerService.insertSaleOrderBatch(list1);	
			  
			  return "YES";
		  } catch (NumberFormatException e) {
				e.printStackTrace();
				return "NO";
			} catch (Exception e) {
				e.printStackTrace();
				return "NO";
			}	  
	  }
	  
	  
	  
	  /**
	   * 通过项目号导入图纸数据(ERP数据)
	   * @author polo
	   * 2017年6月6日
	   *
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importDrawing.do",method = RequestMethod.POST)	  
	  public String importDrawing(@RequestParam Map<String,String> map){
		  
		  try {
			  String jsonStr = map.get("Mould");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);
			  List<Object> drawings = new ArrayList<Object>();
			  /**
			   * 解析sale_customer数据
			   * 
			   */
			  for (Object c: list) {	
				  ClientDrawings clientDrawing = new ClientDrawings();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);	  
			      
				  if(!(cl.get("caseno") == null || "".equals(cl.get("caseno")))){                  
					  String orderId = cl.get("caseno").toString().toUpperCase();    
					  clientDrawing.setOrderId(orderId);
			      }else{
			    	  continue;
			      }
				  
				  if(!(cl.get("customercode") == null || "".equals(cl.get("customercode")))){
					  String eid = cl.get("customercode").toString();
					  clientDrawing.setUserid(eid);
				  }else{
					  continue;  
				  }
				  
				  if(!(cl.get("drawingname") == null || "".equals(cl.get("drawingname")))){
//					  String drawingsName = cl.get("drawingname").toString();
//					  clientDrawing.setDrawingsName(drawingsName);
				  }
				  
				  if(!(cl.get("amount") == null || "".equals(cl.get("amount")))){
					  Integer quantity = Integer.parseInt(cl.get("amount").toString());
					  clientDrawing.setQuantity(quantity);
				  }
				  
				  if(!(cl.get("price") == null || "".equals(cl.get("price")))){
					  Double unitPrice = Double.parseDouble(cl.get("price").toString());
					  clientDrawing.setUnitPrice(unitPrice);
				  }
				  if(!(cl.get("moldprice") == null || "".equals(cl.get("moldprice")))){
					  Double moldPrice = Double.parseDouble(cl.get("moldprice").toString());
					  clientDrawing.setMoldPrice(moldPrice);
				  }
				  
				  if(!(cl.get("product") == null || "".equals(cl.get("product")))){
                      String productName = cl.get("product").toString();
					  clientDrawing.setProductName(productName);
				  }
				  
				  if(!(cl.get("id") == null || "".equals(cl.get("id")))){
					  Integer erpMoldId = Integer.parseInt(cl.get("id").toString());
					  clientDrawing.setErpMoldId(erpMoldId);
				  }
				  clientDrawing.setDrawingState("NO");
				  
				  drawings.add(clientDrawing);			  
			  }
			  clientDrawingsService.insertClientDrawings(drawings);
			  
			  
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		  
		  
		  
		  	return "success";
	  }
	  
	  
	  
	  
	  
	   /**
	    * 更新订单金额数据（如果不存在则新增）ERP系统
	    * @author polo
	    * 2017年5月22日
	    *
	    */
	  @ResponseBody
	  @RequestMapping("/updateClientOrderBatch.do")	  
	   public String updateClienOrderBatch(HttpServletRequest request, HttpServletResponse response) throws Exception{
		  	
		  
		  
	      try {
			/* 设置格式为text/json    */
			  response.setContentType("text/json"); 
			  /*设置字符集为'UTF-8'*/
			  response.setCharacterEncoding("UTF-8");
			  //获取接收的数据
			  String jsonStr = request.getParameter("result");
			  
			  jsonStr = URLDecoder.decode(jsonStr, "utf-8"); 
			  //将json数据反序列化为map格式
			  Map<Object,Object> map = SerializeUtil.JsonToMap(jsonStr);
			           
			  /**
			   * 解析ClientOrder数据
			   * 
			   */
			  //转换为list对象 
			  List<Object> list = (List<Object>)map.get("invoice");	
			  //循环list先将底层map转换为json字符串，再反序列化为map
			  //实例：{amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426}		  	
			  for (Object c: list) {	
				  ClientOrder clientOrder = new ClientOrder();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);	  

				  if(!(cl.get("projectid") == null || "".equals(cl.get("projectid")))){                  
					  ClientOrder  clientOrder1 = clientOrderService.queryByOrderId((String)cl.get("projectid"));
					  
						  if(clientOrder1 == null || "".equals(clientOrder1)){	
							  
							  clientOrder.setOrderId(cl.get("projectid").toString().toUpperCase());	
							  if(!(cl.get("createtime") == null || "".equals(cl.get("createtime")))){
								  clientOrder.setCreateTime((String) cl.get("createtime")); 
							  }
							  			 					  
							  if(!(cl.get("amount") == null || "".equals(cl.get("amount")))){							  
								  Double d = Double.parseDouble (cl.get("amount").toString());
								  clientOrder.setAmount(d); 
							  }else{
								  clientOrder.setAmount(0.0);
							  }
							  
							  if(!(cl.get("sumacount") == null || "".equals(cl.get("sumacount")))){							  
								  Double actualAmount = Double.parseDouble (cl.get("sumacount").toString());
								  clientOrder.setActualAmount(actualAmount);
							  }else{
								  clientOrder.setActualAmount(0.0);
							  }
							  
							  if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){
									 String s = cl.get("eid").toString();
									 clientOrder.setUserid(s);
								  }else{
									 continue;
								  }
							  clientOrder.setFactoryId(FACTORY_ID);
							  clientOrder.setOrderSource(ORDER_SOURCE);
							  clientOrder.setOrderTypeId(5);
							  clientOrderService.insertClientOrder(clientOrder);
							  
						  }else{
							  if(!(cl.get("sumacount") == null || "".equals(cl.get("sumacount")))){							  
								  Double actualAmount = Double.parseDouble(cl.get("sumacount").toString());
								  clientOrder1.setActualAmount(actualAmount);
							  }
							  if(!(cl.get("amount") == null || "".equals(cl.get("amount")))){							  
								  Double amount = Double.parseDouble(cl.get("amount").toString());
								  clientOrder1.setAmount(amount);
							  }
							  clientOrderService.updateClientOrder(clientOrder1);
						  }
				  }else{
					  continue;
				  }
			   }
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}  
		  	  
		  
		  return "success";
	  }
	  
	  
	  
	  /**
	   * 插入（sale_customer）获取销售客户关联数据 （NBMail系统）
	   * @author polo
	   * 2017年5月22日
	   *
	   */
	  @ResponseBody
	  @RequestMapping(value ="/insertSaleCustomer.do",method = RequestMethod.POST)	  	  
	   public String insertSaleCustomer(@RequestParam Map<String,String> map) throws Exception{
		  
		  
		  try {
			  String jsonStr = map.get("EmailClient");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);
			  /**
			   * 解析sale_customer数据
			   * 
			   */
			  for (Object c: list) {	
				  SaleCustomer saleCustomer = new SaleCustomer();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);	  
			      String saleId = null;
			      String eid = null;
			      
				  if(!(cl.get("saleId") == null || "".equals(cl.get("saleId")) || cl.get("eid") == null || "".equals(cl.get("eid")))){                  
					  saleId = cl.get("saleId").toString();    
					  eid = cl.get("eid").toString(); 
					  int count = saleCustomerService.queryCountBySaleAndCustomer(saleId, eid);
					  if(count == 0){
						  saleCustomer.setSalesId(saleId);
						  saleCustomer.setUserid(eid);
						  saleCustomerService.insert(saleCustomer);
					  }else{
						  continue;
					  }
			     }
				  if(!(cl.get("saleId1") == null || "".equals(cl.get("saleId1")) || cl.get("eid") == null || "".equals(cl.get("eid")))){                  
					  saleId = cl.get("saleId1").toString();  
					  eid = cl.get("eid").toString(); 
					  int count = saleCustomerService.queryCountBySaleAndCustomer(saleId, eid);
					  if(count == 0){
						  saleCustomer.setSalesId(saleId);
						  saleCustomer.setUserid(eid);
						  saleCustomerService.insert(saleCustomer);
					  }else{
						  continue;
					  }
				  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		} 		  
		  return "success";
	  }
	  
	  
	  
	  /**
	   * 获取最新销售客户关联关系（NBMail系统）
	   * @author polo
	   * 2017年5月22日
	   *
	   */
	  @ResponseBody
	  @RequestMapping(value ="/saleCustomerSYNC.do",method = RequestMethod.POST)	  
	  public String saleCustomerSYNC(@RequestParam Map<String,String> map){

	    try {	
	    	  SaleCustomer saleCustomer = new SaleCustomer();
	          String saleId = map.get("saleId");    //新的销售id
	          String saleId1 = map.get("saleId1");  //旧的销售id
	          String userid = map.get("erpid");		  
              String projectId = map.get("projectId").toUpperCase();  //项目号
	          SaleOrder saleOrder = new SaleOrder();
	          List<SaleOrder> list = new ArrayList<SaleOrder>();
	          
	          if(!(saleId == null || "".equals(saleId) || userid == null || "".equals(userid))){
	        	  SaleCustomer saleCustomer1 = saleCustomerService.queryBySaleAndCustomer(saleId, userid);
				  if(saleCustomer1 == null || "".equals(saleCustomer1)){
					  saleCustomer.setSalesId(saleId);
					  saleCustomer.setUserid(userid);
					  saleCustomerService.insert(saleCustomer);
				  }
				  
				    SaleOrder saleOrder1 = saleCustomerService.queryBySaleAndOrder(saleId1, projectId);
				    if(saleOrder1 == null || "".equals(saleOrder1)){
					     saleOrder.setOrderId(projectId);
						 saleOrder.setSalesId(saleId);
						 list.add(saleOrder);
						 saleCustomerService.insertSaleOrderBatch(list);
				    }else{
				    	saleOrder1.setSalesId(saleId);
				    	saleCustomerService.updateSaleOrder(saleOrder1);
				    }
				  
	           }	 
	          
	          
	        
	          return "success";
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	            return "Error";
	        }

	  }
	  
	  
	 
	  
	  
	  
	  
	  
	  /**
	   * 通过项目号导入订单（订单存在，导入关联关系）(停用 2017.07.03)
	   * @author polo
	   * 2017年5月27日
	   *
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importERPOrder.do",method = RequestMethod.POST)	  
	  public String importERPOrder(@RequestParam Map<String,String> map){
		  
		  	try {
		  		String salesId = null;
		  		String salesId1 = null;
				ClientOrder clientOrder = new ClientOrder();
				SaleCustomer saleCustomer = new SaleCustomer();
				SaleCustomer saleCustomer2 = new SaleCustomer();
				List<SaleCustomer> list = new ArrayList<SaleCustomer>();
		  	    SaleOrder saleOrder = new SaleOrder();
		  	    SaleOrder saleOrder2 = new SaleOrder();
			    FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
			    UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
			    List<SaleOrder> list3 = new ArrayList<SaleOrder>();
				
				
				String projectid = map.get("projectid");
				String eid = map.get("eid");
				if(projectid == null || "".equals(projectid) || eid == null || "".equals(eid)){
					throw new RuntimeException("订单号和eid不能为空");
				}else{
					
					int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
					if(count == 0){
						factoryUserRelation.setFactoryId(FACTORY_ID);
						factoryUserRelation.setUserid(eid);
						userFactoryRelation.setFactoryId(FACTORY_ID);
						userFactoryRelation.setUserid(eid);
					}
				}
				Double amount = 0.0;
				if(!(map.get("amount") == null || "".equals(map.get("amount")))){
					amount = Double.parseDouble(map.get("amount"));
				}
				Double actualAmount = 0.0;
				if(!(map.get("sumacount") == null || "".equals(map.get("sumacount")))){							  
					  actualAmount = Double.parseDouble(map.get("sumacount").toString());					  
				  }
				
				String createTime = map.get("createtime");	  	
				String salesName = map.get("customerManager");
				String salesName1 = map.get("merchandManager1");
				
				BackUser backUser = backUserService.queryBackUserByName(salesName);
				BackUser backUser1 = backUserService.queryBackUserByName(salesName1);
				if(!(backUser == null || "".equals(backUser))){
					salesId = backUser.getBackUserid();
				}
				if(!(backUser1 == null || "".equals(backUser1))){
					salesId1 = backUser1.getBackUserid();
				}
				
				clientOrder.setOrderId(projectid);
				clientOrder.setUserid(eid);
				clientOrder.setCreateTime(createTime);
				clientOrder.setAmount(amount);
				clientOrder.setActualAmount(actualAmount);
				clientOrder.setFactoryId(FACTORY_ID);
				clientOrder.setOrderSource(ORDER_SOURCE);
				clientOrder.setOrderTypeId(ORDER_TYPE);
				
				if(!(salesId == null || "".equals(salesId))){
					int count = saleCustomerService.queryCountBySaleAndCustomer(salesId, eid);
				    if(count == 0){
				    	saleCustomer.setSalesId(salesId);
				    	saleCustomer.setUserid(eid);
				    	list.add(saleCustomer);
				    }
				    
				    
				    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId, projectid);
				    if(count1 == 0){
					     saleOrder.setOrderId(projectid);
						 saleOrder.setSalesId(salesId);
						 list3.add(saleOrder);
				    }
				}
				if(!(salesId1 == null || "".equals(salesId1))){
					int count = saleCustomerService.queryCountBySaleAndCustomer(salesId1, eid);
					if(count == 0){
						saleCustomer2.setSalesId(salesId1);
						saleCustomer2.setUserid(eid);
						list.add(saleCustomer2);
					}
					
					  int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId1, projectid);
					    if(count1 == 0){
					        saleOrder2.setOrderId(projectid);
							 saleOrder2.setSalesId(salesId1);
							if(!(salesId1.equals(salesId))){
								list3.add(saleOrder2);
							}
					    }
				}
				clientOrderService.insertClientOrder(clientOrder, projectid, list,factoryUserRelation,userFactoryRelation,list3);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	  	
		  	return "success";
	  }
	  
	  
	  /**
	   * 导入内部报价单
	   * Date:2017/06/20
	   * Author:polo
	   * @param map
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importQuotation.do",method = RequestMethod.POST)	  
	  public String importQuotation(@RequestParam Map<String,String> map,HttpServletRequest request, HttpServletResponse response){
		  
		  try {
			  
			  String jsonStr = map.get("ForeignQuotation");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);
			  /**
			   * 解析报价单list
			   * 
			   */
			  Integer quotationBeanId = null;
			  for (int i=0;i<list.size();i++){

				  Object c = list.get(i);
				  String salesId = null;
			  	  String salesId1 = null;
				  QuotationBean quotation = new QuotationBean();
				  QuotationProductionBean productionBean = new QuotationProductionBean();
				  QuotationProductionPriceBean priceBean = new QuotationProductionPriceBean();
			  	  SaleOrder saleOrder = new SaleOrder();
			  	  SaleOrder saleOrder2 = new SaleOrder();
			      List<SaleOrder> list3 = new ArrayList<SaleOrder>();
				  SaleCustomer saleCustomer = new SaleCustomer();
				  SaleCustomer saleCustomer2 = new SaleCustomer();
				  List<SaleCustomer> list2 = new ArrayList<SaleCustomer>();
				  List<QuotationProcessInfo> processInfos = new ArrayList<QuotationProcessInfo>();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);
				  
				  String projectId = null;
				  if(!(cl.get("projectId") == null || "".equals(cl.get("projectId")))){	
					  projectId = cl.get("projectId").toString().toUpperCase();
					  quotation.setProjectId(cl.get("projectId").toString()); 
				  }else{
					 continue;
				  }
				  String eid = null;
				  if(!(cl.get("erpcid") == null || "".equals(cl.get("erpcid")))){	
					  eid = cl.get("erpcid").toString();
					  quotation.setUserId(cl.get("erpcid").toString());
				  }
				  if(!(cl.get("customername") == null || "".equals(cl.get("customername")))){	
					  quotation.setCustomerName(cl.get("customername").toString());
				  }
				  if(!(cl.get("projectdesce") == null || "".equals(cl.get("projectdesce")))){	
					  quotation.setQuotationSubject(cl.get("projectdesce").toString());
				  }
				  if(!(cl.get("createtime") == null || "".equals(cl.get("createtime")))){	
					  quotation.setCreateTime(cl.get("createtime").toString());
				  }
				  String salesName = null;
				  if(!(cl.get("salename") == null || "".equals(cl.get("salename")))){	
					  salesName = cl.get("salename").toString();
					  quotation.setSaleName(cl.get("salename").toString());
				  }
				  String salesName1 = null; 
				  if(!(cl.get("quoter") == null || "".equals(cl.get("quoter")))){	
					  salesName1 = cl.get("quoter").toString();
					  quotation.setQuoter(cl.get("quoter").toString());
				  }
				  if(!(cl.get("telephone") == null || "".equals(cl.get("telephone")))){	
					  quotation.setQuoterTel(cl.get("telephone").toString());
				  }
				  if(!(cl.get("email") == null || "".equals(cl.get("email")))){	
					  quotation.setQuoterEmail(cl.get("email").toString());
				  }
				  if(!(cl.get("approvaltime") == null || "".equals(cl.get("approvaltime")))){	
					  quotation.setQuotationDate(DateFormat.formatDate1(cl.get("approvaltime").toString()));
				  }
				  if(!(cl.get("rfqId") == null || "".equals(cl.get("rfqId")))){	
					  quotation.setRfqId(Integer.parseInt(cl.get("rfqId").toString()));
				  }
				  if(!(cl.get("dollarExchangeRate") == null || "".equals(cl.get("dollarExchangeRate")))){	
					  quotation.setExchangeRateCNY(Double.parseDouble(cl.get("dollarExchangeRate").toString()));
				  }
				  if(!(cl.get("euroExchangeRate") == null || "".equals(cl.get("euroExchangeRate")))){	
					  quotation.setExchangeRateEUR(Double.parseDouble(cl.get("euroExchangeRate").toString()));
				  }
				  if(!(cl.get("sterlingExchangeRate") == null || "".equals(cl.get("sterlingExchangeRate")))){	
					  quotation.setExchangeRateGBP(Double.parseDouble(cl.get("sterlingExchangeRate").toString()));
				  }
				  if(!(cl.get("aggregateprice") == null || "".equals(cl.get("aggregateprice")))){	
					  quotation.setQuotationPriceRange(cl.get("aggregateprice").toString());
				  }
				  if(!(cl.get("exchangeUpdateDate") == null || "".equals(cl.get("exchangeUpdateDate")))){	
					  quotation.setExchangeUpdateDate(cl.get("exchangeUpdateDate").toString());
				  }else{
					  quotation.setExchangeUpdateDate(DateFormat.currentDate());
				  }
			  
				  
				  quotation.setCurrency("USD");
				  quotation.setFactoryId(FACTORY_ID);
				  quotation.setIncoTerm("FOB Shanghai");
				  quotation.setQuotationValidity(30);
				  quotation.setReadStatus(0);
				  quotation.setQuotationStatus(QUOTATION_STATUS);
				  
				    BackUser backUser = backUserService.queryBackUserByName(salesName);
					BackUser backUser1 = backUserService.queryBackUserByName(salesName1);
					if(!(backUser == null || "".equals(backUser))){
						salesId = backUser.getBackUserid();
						quotation.setQuoterTel(backUser.getTel());
						quotation.setQuoterEmail(backUser.getEmail());
					}
					if(!(backUser1 == null || "".equals(backUser1))){
						salesId1 = backUser1.getBackUserid();
					}
					
				  
					if(!(salesId == null || "".equals(salesId))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId, eid);
					    if(count == 0){
					    	saleCustomer.setSalesId(salesId);
					    	saleCustomer.setUserid(eid);
					    	list2.add(saleCustomer);
					    }
					    
					    
					    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId, projectId);
					    if(count1 == 0){
						     saleOrder.setOrderId(projectId);
							 saleOrder.setSalesId(salesId);
							 list3.add(saleOrder);
					    }
					}
					if(!(salesId1 == null || "".equals(salesId1))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId1, eid);
						if(count == 0){
							saleCustomer2.setSalesId(salesId1);
							saleCustomer2.setUserid(eid);
							list2.add(saleCustomer2);
						}
						
						  int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId1, projectId);
						    if(count1 == 0){
						         saleOrder2.setOrderId(projectId);
								 saleOrder2.setSalesId(salesId1);
								if(!(salesId1.equals(salesId))){
									list3.add(saleOrder2);
								}
						    }
					}
					
					
				  
				  //产品名称
				  Double moldPrice = 0.0;
				  if(!(cl.get("moldfee") == null || "".equals(cl.get("moldfee")))){	
					  productionBean.setMoldPrice(Double.parseDouble(cl.get("moldfee").toString()) * (1+0.1));
					  Double moldProfitRate = 0.1;
					  productionBean.setMoldFactoryPrice(Double.parseDouble(cl.get("moldfee").toString()));
					  moldPrice = Double.parseDouble(cl.get("moldfee").toString());
					  productionBean.setMoldProfitRate(moldProfitRate);
					  productionBean.setProductProfitRate(0.1);
				  }
				  if(!(cl.get("productname") == null || "".equals(cl.get("productname")))){	
					  productionBean.setProductName(cl.get("productname").toString());
					  priceBean.setProductName(cl.get("productname").toString());
				  }
				  if(!(cl.get("materialName") == null || "".equals(cl.get("materialName")))){	
					  productionBean.setMaterial(cl.get("materialName").toString());
				  }
				  if(!(cl.get("notes") == null || "".equals(cl.get("notes")))){	
					  productionBean.setProductNotes(cl.get("notes").toString());
				  }
				  //材料耗损
				  if(!(cl.get("materiallossrate") == null || "".equals(cl.get("materiallossrate")))){	
					  productionBean.setMaterialLoss((Double.parseDouble(cl.get("materiallossrate").toString()))/100);
				  }
				  //材料单价
				  if(!(cl.get("materialprice") == null || "".equals(cl.get("materialprice")))){	
					  productionBean.setMaterialUnitPrice(Double.parseDouble(cl.get("materialprice").toString()));
				  }
				  //产品价格			  
				  if(!(cl.get("unitprice") == null || "".equals(cl.get("unitprice")))){						 
					  productionBean.setFactoryPrice(Double.parseDouble(cl.get("unitprice").toString()));
				  }
				  //产品穴数
				  if(!(cl.get("numberPoints") == null || "".equals(cl.get("numberPoints")))){						 
					  productionBean.setPoint(Integer.parseInt(cl.get("numberPoints").toString()));
				  }
				  //产品报废率
				  if(!(cl.get("percentageofscrap") == null || "".equals(cl.get("percentageofscrap")))){						 
					  productionBean.setRejectionRate(Double.parseDouble(cl.get("percentageofscrap").toString())/100);
				  }
				  
				  //产品图
				  if(!(cl.get("drawingPath") == null || "".equals(cl.get("drawingPath")))){
					  String compressName = HttpClientDownloadUtil.getImg((String)cl.get("drawingPath"));
//					  String staticPath = UploadAndDownloadPathUtil.getQuotationPath() + "img" + File.separator + compressName;
//					  if(StringUtils.isBlank(compressName)){
//						  staticPath = ""; 
//					  }
					  productionBean.setProductImgCompress(compressName);
				  }
				  
				  
				  
				  
//			      Integer quantity = 0;
				  if(!(cl.get("orders") == null || "".equals(cl.get("orders")))){	
//					  quantity = Integer.parseInt(cl.get("orders").toString());
					  priceBean.setQuantity(Integer.parseInt(cl.get("orders").toString()));
				  }
				  //材料总价
				  Double materialAllprice = 0.0;
				  if(!(cl.get("materialAllprice") == null || "".equals(cl.get("materialAllprice")))){
					  materialAllprice = Double.parseDouble(cl.get("materialAllprice").toString());
					  priceBean.setMaterialPrice(Double.parseDouble(cl.get("materialAllprice").toString()));
				  }
				  //材料重量
//				  Double materialWeight = 0.0;
				  if(!(cl.get("materialconsumption") == null || "".equals(cl.get("materialconsumption")))){	
//					  materialWeight = Double.parseDouble(cl.get("materialconsumption").toString());
					  priceBean.setMaterialWeight(Double.parseDouble(cl.get("materialconsumption").toString()));
				  }
				  //产品价格（增加后价格）
				  Double unitPrice = 0.0;
				  if(!(cl.get("unitprice") == null || "".equals(cl.get("unitprice")))){	
					  unitPrice = Double.parseDouble(cl.get("unitprice").toString())* 1.1;
					  priceBean.setProductPrice(unitPrice);					  
				  }
				  //工艺价格
				  Double processPriceAll = 0.0;
				  if(!(cl.get("processPriceAll") == null || "".equals(cl.get("processPriceAll")))){	
					  processPriceAll = Double.parseDouble(cl.get("processPriceAll").toString());
					  priceBean.setProcessPrice(processPriceAll);
				  }
				  priceBean.setTotalProfitRate(0.1);
				  priceBean.setMaterialProfitRate(0.00);
	              
//				  Double processChangePrice = 0.0;
			         //产品总价
//			 	  Double totalPrice = new BigDecimal(unitPrice).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
				    //材料价格
//				   Double materialPrice = new BigDecimal(materialAllprice).multiply(new BigDecimal((1+0.05))).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();	
				
//				   if(processPriceAll != 0.0){					
						//工艺变化价格百分比
//					    processPriceAll = new BigDecimal(processPriceAll).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
//						processChangePrice = new BigDecimal(totalPrice).subtract(new BigDecimal(materialPrice)).subtract(new BigDecimal(processPriceAll)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue(); 
//						processChangePrice = new BigDecimal(processChangePrice).divide(new BigDecimal(processPriceAll),2,RoundingMode.HALF_UP).doubleValue();
//				  }else{
						//如果不存在工艺，利润率全部加在材料上。
//						Double materialChangePrice = new BigDecimal(unitPrice).subtract(new BigDecimal(unitPrice/1.1)).subtract(new BigDecimal(materialAllprice)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
//						Double materialProfitRate = new BigDecimal(materialChangePrice).divide(new BigDecimal(materialAllprice),2,RoundingMode.HALF_UP).doubleValue();
//						priceBean.setMaterialProfitRate(materialProfitRate);
//				  }
				  StringBuffer processList = new StringBuffer();
				  if(!(cl.get("processName1") == null || "".equals(cl.get("processName1")))){	
					  QuotationProcessInfo process1 = new QuotationProcessInfo();
					  process1.setProcessName(cl.get("processName1").toString());
					  processList.append(cl.get("processName1").toString());
					  if(!(cl.get("processPrice1") == null || "".equals(cl.get("processPrice1")))){	
						  process1.setProcessFactoryPrice(Double.parseDouble(cl.get("processPrice1").toString()));
//						  process1.setProcessPrice(Double.parseDouble(cl.get("processPrice1").toString()) * (1+processChangePrice));
					  }
					  if(!(cl.get("processquantity1") == null || "".equals(cl.get("processquantity1")))){	
						  process1.setQuantity(Double.parseDouble(cl.get("processquantity1").toString()));
					  }
					  processInfos.add(process1);
				  }
				  if(!(cl.get("processName2") == null || "".equals(cl.get("processName2")))){	
					  QuotationProcessInfo process2 = new QuotationProcessInfo();
					  process2.setProcessName(cl.get("processName2").toString());
					  processList.append(","+ cl.get("processName2").toString());
					  if(!(cl.get("processPrice2") == null || "".equals(cl.get("processPrice2")))){	
						  process2.setProcessFactoryPrice(Double.parseDouble(cl.get("processPrice2").toString()));
//						  process2.setProcessPrice(Double.parseDouble(cl.get("processPrice2").toString()) * (1+processChangePrice));
					  }
					  if(!(cl.get("processquantity2") == null || "".equals(cl.get("processquantity2")))){	
						  process2.setQuantity(Double.parseDouble(cl.get("processquantity2").toString()));
					  }
					  processInfos.add(process2);
				  }
				  if(!(cl.get("processName3") == null || "".equals(cl.get("processName3")))){	
					  QuotationProcessInfo process3 = new QuotationProcessInfo();
					  process3.setProcessName(cl.get("processName3").toString());
					  processList.append(","+ cl.get("processName3").toString());
					  if(!(cl.get("processPrice3") == null || "".equals(cl.get("processPrice3")))){	
						  process3.setProcessFactoryPrice(Double.parseDouble(cl.get("processPrice3").toString()));
//						  process3.setProcessPrice(Double.parseDouble(cl.get("processPrice3").toString()) * (1+processChangePrice));
					  }
					  if(!(cl.get("processquantity3") == null || "".equals(cl.get("processquantity3")))){	
						  process3.setQuantity(Double.parseDouble(cl.get("processquantity3").toString()));
					  }
					  processInfos.add(process3);
				  }
				  if(!(cl.get("processName4") == null || "".equals(cl.get("processName4")))){	
					  QuotationProcessInfo process4 = new QuotationProcessInfo();
					  process4.setProcessName(cl.get("processName4").toString());
					  processList.append(","+ cl.get("processName4").toString());
					  if(!(cl.get("processPrice4") == null || "".equals(cl.get("processPrice4")))){	
						  process4.setProcessFactoryPrice(Double.parseDouble(cl.get("processPrice4").toString()));
//						  process4.setProcessPrice(Double.parseDouble(cl.get("processPrice4").toString()) * (1+processChangePrice));
					  }
					  if(!(cl.get("processquantity4") == null || "".equals(cl.get("processquantity4")))){	
						  process4.setQuantity(Double.parseDouble(cl.get("processquantity4").toString()));
					  }
					  processInfos.add(process4);
				  }
				  if(!(cl.get("processName5") == null || "".equals(cl.get("processName5")))){	
					  QuotationProcessInfo process5 = new QuotationProcessInfo();
					  process5.setProcessName(cl.get("processName5").toString());
					  processList.append(","+ cl.get("processName5").toString());
					  if(!(cl.get("processPrice5") == null || "".equals(cl.get("processPrice5")))){	
						  process5.setProcessFactoryPrice(Double.parseDouble(cl.get("processPrice5").toString()));
//						  process5.setProcessPrice(Double.parseDouble(cl.get("processPrice5").toString()) * (1+processChangePrice));
					  }
					  if(!(cl.get("processquantity5") == null || "".equals(cl.get("processquantity5")))){	
						  process5.setQuantity(Double.parseDouble(cl.get("processquantity5").toString()));
					  }
					  processInfos.add(process5);
				  }
				 quotationService.insertQuotation(quotation, productionBean, priceBean,list2,list3,processInfos);
				 QuotationBean quotationBean = quotationService.queryByProjectId(quotation.getProjectId());
				 quotationBeanId = quotationBean.getId();
			   }  
			  
				if(quotationBeanId!=null){
					return "https://importx.net/supplier/toModifyQuotation.do?quotationInfoId="+Base64Encode.getBase64(quotationBeanId.toString());
				}else{
					return "NO";
				}
			
		    } catch (NumberFormatException e) {
				e.printStackTrace();
				return "error";
			} catch (Exception e) {
				e.printStackTrace();
				return "error";
			}	  
		}
	  
	  
	  
	  /**
	   * 导入erp发票数据
	   * Date:2017/06/21
	   * Author:polo
	   * @param map
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  @ResponseBody
	  @RequestMapping(value ="/importInvoiceInfo.do",method = RequestMethod.POST)	  
	  public String importInvoiceInfo(@RequestParam Map<String,String> map){
		  
		  try {
			  
			  String jsonStr = map.get("Invoiceinfo1");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);
			  /**
			   * 解析报价单list
			   * 
			   */
//			  List<InvoiceInfo> invoices = new ArrayList<InvoiceInfo>();
//			  List<PaymentPlan> payments = new ArrayList<PaymentPlan>();
			  for (Object c: list) {	
				  InvoiceInfo invoiceInfo = new InvoiceInfo();
				  PaymentPlan paymentPlan = new PaymentPlan();
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> cl = SerializeUtil.JsonToMap(str);
				  
	
				  if(!(cl.get("projectId") == null || "".equals(cl.get("projectId")))){		
					  invoiceInfo.setOrderId(cl.get("projectId").toString().toUpperCase());
					  paymentPlan.setOrderId(cl.get("projectId").toString().toUpperCase());
				  }else{
					  continue;
				  }
				  if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){		
					  invoiceInfo.setUserid(cl.get("eid").toString());
				  }else{
					  continue;
				  }

				  if(!(cl.get("iInvNo") == null || "".equals(cl.get("iInvNo")))){	
					  invoiceInfo.setInvoiceId(cl.get("iInvNo").toString());
					  paymentPlan.setInvoiceId(cl.get("iInvNo").toString());
				  }else{
					  continue;
				  }
				  if(!(cl.get("iSum") == null || "".equals(cl.get("iSum")))){		
					  invoiceInfo.setAmount(Double.parseDouble(cl.get("iSum").toString()));
				  }
				  if(!(cl.get("imoneytype") == null || "".equals(cl.get("imoneytype")))){		
					  invoiceInfo.setAmountUnit(Integer.parseInt(cl.get("imoneytype").toString()));
				  }
				  if(!(cl.get("icreatedate") == null || "".equals(cl.get("icreatedate")))){		
					  invoiceInfo.setCreateTime(cl.get("icreatedate").toString());
				  }
				  if(!(cl.get("ifdate") == null || "".equals(cl.get("ifdate")))){		
					  paymentPlan.setPaymentTime(cl.get("ifdate").toString());
				  }
				  if(!(cl.get("iidate") == null || "".equals(cl.get("iidate")))){		
					  paymentPlan.setPredictPaymentTime(cl.get("iidate").toString());
				  }
				  if(!(cl.get("iimoney") == null || "".equals(cl.get("iimoney")))){		
					  paymentPlan.setAmount(Double.parseDouble(cl.get("iimoney").toString()));
				  }
				  if(!(cl.get("ifmoney") == null || "".equals(cl.get("ifmoney")))){		
					  paymentPlan.setAmountActual(Double.parseDouble(cl.get("ifmoney").toString()));
				  }
			      if(!(cl.get("invoiceId") == null || "".equals(cl.get("invoiceId")))){		
			    	  paymentPlan.setErpInvoiceId(Integer.parseInt(cl.get("invoiceId").toString()));
				  }
				  
				  invoiceInfo.setFactoryId(FACTORY_ID);
				  invoiceInfo.setTransactionType(TRANSACTION_TYPE);
				  invoiceInfo.setSalesId(null);
				  	
			     invoiceInfoService.insertInvoiceInfo(invoiceInfo, paymentPlan);
			  }	  
	  
			  return "YES";
		    } catch (NumberFormatException e) {
				e.printStackTrace();
				return "NO";
			} catch (Exception e) {
				e.printStackTrace();
				return "NO";
			}	  
	  
	  }
	  
	  
	  /**
	   * 同步NBMail 客户数据
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importCustomer.do",method = RequestMethod.POST)	  
	   public String importCustomer(@RequestParam Map<String,String> map){
	 		  
        try {
			  //将json数据反序列化为map格式
			  String jsonStr = map.get("Customer");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);
		       //创建存放对象的list集合
			  List<Object> list1 = new ArrayList<Object>();
			  List<Object> list2 = new ArrayList<Object>();
			  List<Object> list3 = new ArrayList<Object>();
			  List<Object> list4 = new ArrayList<Object>();
			  List<Object> list5 = new ArrayList<Object>();
			  
			  for (Object c : list) {
				 String str = null;
				 FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
				 UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
				 UserRelationEmail relationEmail = new UserRelationEmail();
				 str = c.toString();
		         //循环list先将底层map转换为json字符串，再反序列化为map
				 Map<Object, Object> cl = SerializeUtil.JsonToMap(str);
	             User user = new User();
	             ShippingInfo s = new ShippingInfo();
	             String eid = null;
	             if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){
	            	  eid = cl.get("eid").toString();
					  User u = userService.queryByUserId(cl.get("eid").toString());
					  if(!(u == null || "".equals(u))){
							 continue; 
					   }
					  user.setUserid(cl.get("eid").toString());
					  s.setUserid(cl.get("eid").toString());
				  }		
				  if(!(cl.get("firstName") == null || "".equals(cl.get("firstName")))){
					  user.setUserName((String) cl.get("firstName"));
					  s.setUserName((String) cl.get("firstName"));
				  }
				  if(!(cl.get("firstJob") == null || "".equals(cl.get("firstJob")))){
					  s.setJob((String) cl.get("firstJob"));	
				  }
				  if(!(cl.get("gsFullName") == null || "".equals(cl.get("gsFullName")))){	
					  user.setCompanyName((String) cl.get("gsFullName"));
					  s.setCompanyName((String) cl.get("gsFullName"));	
				  }
				  if(!(cl.get("email1") == null || "".equals(cl.get("email1")))){
					  user.setEmail((String) cl.get("email1"));
					  user.setLoginEmail((String) cl.get("email1"));
					  s.setRegisterEmail((String) cl.get("email1"));
				  }
				  if(!(cl.get("email2") == null || "".equals(cl.get("email2")))){
					  user.setEmail1((String) cl.get("email2"));
					  s.setContactEmail((String) cl.get("email2"));	
				  }
				  if(!(cl.get("address") == null || "".equals(cl.get("address")))){
					  s.setDetailedAddress((String) cl.get("address"));				  
				  }
				  if(!(cl.get("address1") == null || "".equals(cl.get("address1")))){
					  s.setAddress1((String) cl.get("address1"));				  
				  }
				  if(!(cl.get("address2") == null || "".equals(cl.get("address2")))){
					  s.setAddress2((String) cl.get("address2"));				  
				  }
				  if(!(cl.get("country") == null || "".equals(cl.get("country")))){
					  Integer id = Integer.parseInt((String)cl.get("country"));
					  NbMailCountry country = nbMailCountryService.queryById(id);				  
					  s.setCountry(country.getCountry());				  
				  }
				  if(!(cl.get("state") == null || "".equals(cl.get("state")))){
					  s.setState((String) cl.get("state"));				  
				  }
				  if(!(cl.get("city") == null || "".equals(cl.get("city")))){
					  s.setCity((String) cl.get("city"));				  
				  }
				  if(!(cl.get("postCode") == null || "".equals(cl.get("postCode")))){
					  s.setPostcode((String) cl.get("postCode"));				  
				  }
				  if(!(cl.get("tel2") == null || "".equals(cl.get("tel2")))){
					  s.setMobilePhone1((String) cl.get("tel2"));				  
				  }
				  if(!(cl.get("fax") == null || "".equals(cl.get("fax")))){
					  s.setFax((String) cl.get("fax"));				  
				  }
				  
				  if(!(cl.get("encryptedEmail") == null || "".equals(cl.get("encryptedEmail")))){
					  user.setEncryptedEmail((String)cl.get("encryptedEmail"));
				  }

				  if(!(cl.get("tel") == null || "".equals(cl.get("tel")))){
					  user.setTel((String) cl.get("tel"));
					  s.setTelephone1((String) cl.get("tel"));	
				  }                 
				  
				  if(!(eid == null || "".equals(eid))){
					int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
					if(count == 0){
						factoryUserRelation.setFactoryId(FACTORY_ID);
						factoryUserRelation.setUserid(eid);
						userFactoryRelation.setFactoryId(FACTORY_ID);
						userFactoryRelation.setUserid(eid);
						list1.add(factoryUserRelation);
						list3.add(userFactoryRelation);
					}
				 }
				//密码为随机数通过Md5加密
				  user.setPwd(UtilFuns.randToString(8));				  
				  user.setUpdateTime(DateFormat.format());	
			      user.setToken("");
			      
			      //用户账号关联表
				  relationEmail.setEmail(user.getLoginEmail());
				  relationEmail.setPwd(user.getPwd());
				  relationEmail.setUserid(user.getUserid());
				  relationEmail.setUsername(user.getUserName());
			      
			      
				  list2.add(user);
				  s.setUpdateTime(DateFormat.format());
				  list4.add(s);
				  list5.add(relationEmail);
			  }
	       
			  userService.insertUser(list2, list3, list1,list4,list5);
			  System.out.println("=============更新成功=============");
			  return "SUCCESS";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败"+e.getMessage();
			}
	  }
      
	  
	  
	  
	  /**
	   * 更新报价单状态
	   * Date:2017/07/12
	   * Author:polo
	   * @param map
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping(value ="/updateQuotationStatus.do",method = RequestMethod.POST)	  
	  public String updateQuotationStatus(@RequestParam Map<String,String> map){
		  
		  String projectId = null;
		  if(!(map.get("projectId") == null || "".equals(map.get("projectId")))){
			  projectId = map.get("projectId");
		  }else{
			  return "error";
		  }
		  QuotationBean quotationBean = quotationService.queryByProjectId(projectId);
		  if(!(map.get("feedBack") == null || "".equals(map.get("feedBack")))){
			  if(!(quotationBean == null || "".equals(quotationBean))){
				  quotationBean.setQuotationStatus(2);
				  quotationService.updateQuotation(quotationBean);
			  }
		  }		  
		  return "success";
	  }
	  
	  /**
	   * 接收NBMail新报价客户
	   * Date:2017/07/06
	   * Author:polo
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importNewCustomer.do",method = RequestMethod.POST)	  
	   public String importNewCustomer(@RequestParam Map<String,String> cl){
	 		  
        try {
		       //创建存放对象的list集合
			  List<Object> list1 = new ArrayList<Object>();
			  List<Object> list2 = new ArrayList<Object>();
			  List<Object> list3 = new ArrayList<Object>();
			  List<Object> list4 = new ArrayList<Object>();
			  List<Object> list5 = new ArrayList<Object>();
			 FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
			 UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
			 UserRelationEmail relationEmail = new UserRelationEmail();
             User user = new User();
             ShippingInfo s = new ShippingInfo();
             String eid = null;
                 if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){
            	  eid = cl.get("eid").toString();			
				  user.setUserid(cl.get("eid").toString());
				  s.setUserid(eid);
				  }else{
					  return "eid不能为空";
				  }
				  if(!(cl.get("firstName") == null || "".equals(cl.get("firstName")))){
					  user.setUserName((String) cl.get("firstName"));
					  s.setUserName((String) cl.get("firstName"));
				  }
				  if(!(cl.get("firstJob") == null || "".equals(cl.get("firstJob")))){
					  s.setJob((String) cl.get("firstJob"));
				  }
				  if(!(cl.get("gsFullName") == null || "".equals(cl.get("gsFullName")))){	
					  user.setCompanyName((String) cl.get("gsFullName"));
					  s.setCompanyName((String) cl.get("gsFullName"));	
				  }
				  if(!(cl.get("email1") == null || "".equals(cl.get("email1")))){
					  user.setEmail((String) cl.get("email1"));
					  user.setLoginEmail((String) cl.get("email1"));
					  s.setRegisterEmail((String) cl.get("email1"));
				  }
				  if(!(cl.get("email2") == null || "".equals(cl.get("email2")))){
					  user.setEmail1((String) cl.get("email2"));
					  s.setContactEmail((String) cl.get("email2"));
				  }
               
				  if(!(cl.get("tel") == null || "".equals(cl.get("tel")))){
					  user.setTel((String) cl.get("tel"));
					  s.setTelephone1((String) cl.get("tel"));
				  } 
				  if(!(cl.get("address") == null || "".equals(cl.get("address")))){
					  s.setDetailedAddress((String) cl.get("address"));				  
				  }
				  if(!(cl.get("address1") == null || "".equals(cl.get("address1")))){
					  s.setAddress1((String) cl.get("address1"));				  
				  }
				  if(!(cl.get("address2") == null || "".equals(cl.get("address2")))){
					  s.setAddress2((String) cl.get("address2"));				  
				  }
				  if(!(cl.get("country") == null || "".equals(cl.get("country")))){
					  Integer id = Integer.parseInt((String)cl.get("country"));
					  NbMailCountry country = nbMailCountryService.queryById(id);				  
					  s.setCountry(country.getCountry());				  
				  }
				  if(!(cl.get("state") == null || "".equals(cl.get("state")))){
					  s.setState((String) cl.get("state"));				  
				  }
				  if(!(cl.get("city") == null || "".equals(cl.get("city")))){
					  s.setCity((String) cl.get("city"));				  
				  }
				  if(!(cl.get("postCode") == null || "".equals(cl.get("postCode")))){
					  s.setPostcode((String) cl.get("postCode"));				  
				  }
				  if(!(cl.get("tel2") == null || "".equals(cl.get("tel2")))){
					  s.setMobilePhone1((String) cl.get("tel2"));				  
				  }
				  if(!(cl.get("fax") == null || "".equals(cl.get("fax")))){
					  s.setFax((String) cl.get("fax"));				  
				  }
				  
				  
				  Integer tempUser = null;
				  if(!(cl.get("old") == null || "".equals(cl.get("old")))){
					  tempUser = Integer.parseInt(cl.get("old").toString());
					  user.setTempUser(tempUser);
				  }                 
				  
				  if(!(eid == null || "".equals(eid))){
					int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
					if(count == 0){
						factoryUserRelation.setFactoryId(FACTORY_ID);
						factoryUserRelation.setUserid(eid);
						userFactoryRelation.setFactoryId(FACTORY_ID);
						userFactoryRelation.setUserid(eid);
						list1.add(factoryUserRelation);
						list3.add(userFactoryRelation);
					}
				 }
				  
				  user.setPwd(UtilFuns.randToString(8));				  
				  user.setUpdateTime(DateFormat.format());	
			      user.setToken("");
	
			      
			      //用户账号关联表
				  relationEmail.setEmail(user.getLoginEmail());
				  relationEmail.setPwd(user.getPwd());
				  relationEmail.setUserid(user.getUserid());
				  relationEmail.setUsername(user.getUserName());
			      list5.add(relationEmail);
			      
				  list2.add(user);
				  s.setUpdateTime(DateFormat.format());
	              list4.add(s);
				  
				  User u = userService.queryByUserId(eid);
				  if(!(u == null || "".equals(u))){
					  u.setTempUser(tempUser);
					  u.setPwd(UtilFuns.randToString(8));
					  u.setLoginEmail(user.getLoginEmail());
					  u.setUserName(user.getUserName());
					  userService.updateCustomer(u);
				   }else{
					  userService.insertUser(list2, list3, list1,list4,list5);   
				   }
			  System.out.println("=============更新成功=============");
			  return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败"+e.getMessage();
		}
		  
	  }
	  
	  
	  
	  
	  /**
	   * 更新邀请登录客户邮箱
	   * Date:2017/07/14
	   * Author:polo
	   */
	  @ResponseBody
	  @RequestMapping(value ="/updateLoginEmail.do",method = RequestMethod.POST)	  
	   public String updateLoginEmail(@RequestParam Map<String,String> cl){
	 		  
        try {
		       //创建存放对象的list集合
			  List<Object> list1 = new ArrayList<Object>();
			  List<Object> list2 = new ArrayList<Object>();
			  List<Object> list3 = new ArrayList<Object>();
			  List<Object> list4 = new ArrayList<Object>();
			  List<Object> list5 = new ArrayList<Object>();
			 FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
			 UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
			 UserRelationEmail relationEmail = new UserRelationEmail();
             User user = new User();
             ShippingInfo s = new ShippingInfo();
             String eid = null;
                 if(!(cl.get("eid") == null || "".equals(cl.get("eid")))){
            	  eid = cl.get("eid").toString();			
				  user.setUserid(cl.get("eid").toString());
				  s.setUserid(eid);
				  }else{
					  return "eid不能为空";
				  }
				  if(!(cl.get("firstName") == null || "".equals(cl.get("firstName")))){
					  user.setUserName((String) cl.get("firstName"));
					  s.setUserName((String) cl.get("firstName"));
				  }
				  if(!(cl.get("firstJob") == null || "".equals(cl.get("firstJob")))){	
					  s.setJob((String) cl.get("firstJob"));
				  }
				  if(!(cl.get("gsFullName") == null || "".equals(cl.get("gsFullName")))){	
					  user.setCompanyName((String) cl.get("gsFullName"));
					  s.setCompanyName((String) cl.get("gsFullName"));	
				  }
				  String loginEmail = null;
				  if(!(cl.get("loginEmail") == null || "".equals(cl.get("loginEmail")))){
					  loginEmail = (String) cl.get("loginEmail");
					  user.setEmail(loginEmail);
					  user.setLoginEmail(loginEmail);
					  s.setRegisterEmail(loginEmail);
				  }
				  String encryptedEmail = null;
				  if(!(cl.get("encryptedEmail") == null || "".equals(cl.get("encryptedEmail")))){
					  encryptedEmail = (String)cl.get("encryptedEmail");
					  user.setEncryptedEmail(encryptedEmail);
				  }
				  if(!(cl.get("email2") == null || "".equals(cl.get("email2")))){
					  user.setEmail1((String) cl.get("email2"));
					  s.setContactEmail((String) cl.get("email2"));
				  }


				  if(!(cl.get("tel") == null || "".equals(cl.get("tel")))){
					  user.setTel((String) cl.get("tel"));
					  s.setTelephone1((String) cl.get("tel"));
				  } 
				  if(!(cl.get("address") == null || "".equals(cl.get("address")))){
					  s.setDetailedAddress((String) cl.get("address"));				  
				  }
				  if(!(cl.get("address1") == null || "".equals(cl.get("address1")))){
					  s.setAddress1((String) cl.get("address1"));				  
				  }
				  if(!(cl.get("address2") == null || "".equals(cl.get("address2")))){
					  s.setAddress2((String) cl.get("address2"));				  
				  }
				  if(!(cl.get("country") == null || "".equals(cl.get("country")))){
					  Integer id = Integer.parseInt((String)cl.get("country"));
					  NbMailCountry country = nbMailCountryService.queryById(id);				  
					  s.setCountry(country.getCountry());				  
				  }
				  if(!(cl.get("state") == null || "".equals(cl.get("state")))){
					  s.setState((String) cl.get("state"));				  
				  }
				  if(!(cl.get("city") == null || "".equals(cl.get("city")))){
					  s.setCity((String) cl.get("city"));				  
				  }
				  if(!(cl.get("postCode") == null || "".equals(cl.get("postCode")))){
					  s.setPostcode((String) cl.get("postCode"));				  
				  }
				  if(!(cl.get("tel2") == null || "".equals(cl.get("tel2")))){
					  s.setMobilePhone1((String) cl.get("tel2"));				  
				  }
				  if(!(cl.get("fax") == null || "".equals(cl.get("fax")))){
					  s.setFax((String) cl.get("fax"));				  
				  }				 				  
                 user.setTempUser(1);
            
				  
				  if(!(eid == null || "".equals(eid))){
					int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
					if(count == 0){
						factoryUserRelation.setFactoryId(FACTORY_ID);
						factoryUserRelation.setUserid(eid);
						userFactoryRelation.setFactoryId(FACTORY_ID);
						userFactoryRelation.setUserid(eid);
						list1.add(factoryUserRelation);
						list3.add(userFactoryRelation);
					}
				 }
				  
				  user.setPwd(UtilFuns.randToString(8));				  
				  user.setUpdateTime(DateFormat.format());	
			      user.setToken("");
			      
			      
			      //用户账号关联表
				  relationEmail.setEmail(user.getLoginEmail());
				  relationEmail.setPwd(user.getPwd());
				  relationEmail.setUserid(user.getUserid());
				  relationEmail.setUsername(user.getUserName());
			      list5.add(relationEmail);
			      
	              list2.add(user);
			      s.setUpdateTime(DateFormat.format());
			      list4.add(s);
	              
				  User u = userService.queryByUserId(eid);
				  if(!(u == null || "".equals(u))){
					  if(!(loginEmail == null || "".equals(loginEmail))){
						  u.setLoginEmail(loginEmail);
					  }
					  if(!(encryptedEmail == null || "".equals(encryptedEmail))){
						  u.setEncryptedEmail(encryptedEmail);
					  }
					  u.setTempUser(1);
					  userService.updateCustomer(u);
				   }else{
					   userService.insertUser(list2, list3, list1,list4,list5); 
				   }
			  System.out.println("=============更新成功=============");
			  return "SUCCESS";
		} catch (Exception e) {
			e.printStackTrace();
			return "更新失败"+e.getMessage();
		}
		  
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  /**
	   * 获取NBMail订单数据（订单号、创建时间、金额、客户id）(NBMail订单)
	   * @author polo
	   * 2017年7月25日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/importNBMailOrder.do")	  
	   public String importNBMailOrder(@RequestParam Map<String,String> map2) throws UnsupportedEncodingException{
		  
		  try {
			  String jsonStr = map2.get("Mould");
			  List<Object> list = (List<Object>)JsonUtil.jsonToObject(jsonStr, Object.class);          
			  
			  /**
			   * 解析ClientOrder数据
			   * 
			   */
			  //存放对象的list1集合
			  List<Object> list1 = new ArrayList<Object>();
			  //循环list先将底层map转换为json字符串，再反序列化为map
			  //实例：{amount=3541.00, createTime=2015-10-25 09:56:01.33, customerId=34, caseStatus=14, saildate=, orderId=SHS11426}		  	
			  for (Object c: list) {	
				  ClientOrder clientOrder = new ClientOrder();
				  SaleCustomer saleCustomer = new SaleCustomer();
				  SaleCustomer saleCustomer2 = new SaleCustomer();
			  	  SaleOrder saleOrder = new SaleOrder();
			  	  SaleOrder saleOrder2 = new SaleOrder();
				  FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
				  UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
				  List<SaleCustomer> list2 = new ArrayList<SaleCustomer>();
				  List<SaleOrder> list3 = new ArrayList<SaleOrder>();
				  ClientDrawings clientDrawing = new ClientDrawings();
				  
				  String salesId = null;
			  	  String salesId1 = null;
				  String str = null;
				  str = c.toString();
				  Map<Object, Object> map = SerializeUtil.JsonToMap(str);	  
			 
				    String projectid = map.get("projectid").toString().toUpperCase();
					String eid = map.get("eid").toString();
					if(projectid == null || "".equals(projectid) || eid == null || "".equals(eid)){
						continue;
					}else{
						
						int count = factoryUserRelationService.queryCount(FACTORY_ID, eid);
						if(count == 0){
							factoryUserRelation.setFactoryId(FACTORY_ID);
							factoryUserRelation.setUserid(eid);
							userFactoryRelation.setFactoryId(FACTORY_ID);
							userFactoryRelation.setUserid(eid);
						}else{
							factoryUserRelation = null;
							userFactoryRelation = null;
						}
					}

					
					
					if(!(map.get("createtime") == null || "".equals(map.get("createtime")))){
						clientOrder.setCreateTime(map.get("createtime").toString());
						clientDrawing.setUpdateTime(map.get("createtime").toString());
					}
					
					BackUser backUser = null;
					if(!(map.get("customerManager") == null || "".equals(map.get("customerManager")))){
					   String salesName = map.get("customerManager").toString();
					   backUser = backUserService.queryBackUserByName(salesName);
					}
					
					BackUser backUser1 = null;
					if(!(map.get("merchandManager1") == null || "".equals(map.get("merchandManager1")))){
						String salesName1 = map.get("merchandManager1").toString();
						backUser1 = backUserService.queryBackUserByName(salesName1);
						clientOrder.setSalesName(salesName1);
					}					
					
					if(!(backUser == null || "".equals(backUser))){
						salesId = backUser.getBackUserid();
					}
					if(!(backUser1 == null || "".equals(backUser1))){
						salesId1 = backUser1.getBackUserid();
					}
					
				  if(!(map.get("dollarExchangeRate") == null || "".equals(map.get("dollarExchangeRate")))){	
					  clientOrder.setExchangeRateCNY(Double.parseDouble(map.get("dollarExchangeRate").toString()));
				  }
				  if(!(map.get("euroExchangeRate") == null || "".equals(map.get("euroExchangeRate")))){	
					  clientOrder.setExchangeRateEUR(Double.parseDouble(map.get("euroExchangeRate").toString()));
				  }
				  if(!(map.get("sterlingExchangeRate") == null || "".equals(map.get("sterlingExchangeRate")))){	
					  clientOrder.setExchangeRateGBP(Double.parseDouble(map.get("sterlingExchangeRate").toString()));
				  }
				  if(!(map.get("aggregateprice") == null || "".equals(map.get("aggregateprice")))){	
					  clientOrder.setAmount(Double.parseDouble(map.get("aggregateprice").toString()));
				  }
					
					
				  
					clientOrder.setOrderId(projectid);
					clientOrder.setUserid(eid);
					clientOrder.setFactoryId(FACTORY_ID);
					clientOrder.setOrderSource(2);
					clientOrder.setOrderTypeId(ORDER_TYPE);
					
					if(!(salesId == null || "".equals(salesId))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId, eid);
					    if(count == 0){
					    	saleCustomer.setSalesId(salesId);
					    	saleCustomer.setUserid(eid);
					    	list2.add(saleCustomer);
					    }
					    
					    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId, projectid);
					    if(count1 == 0){
						     saleOrder.setOrderId(projectid);
							 saleOrder.setSalesId(salesId);
							 list3.add(saleOrder);
					    }

					}
					if(!(salesId1 == null || "".equals(salesId1))){
						int count = saleCustomerService.queryCountBySaleAndCustomer(salesId1, eid);
						if(count == 0){
							saleCustomer2.setSalesId(salesId1);
							saleCustomer2.setUserid(eid);
							if(!(salesId1.equals(salesId))){
								list2.add(saleCustomer2);
							}
						}
						
					    int count1 = saleCustomerService.queryCountBySaleAndOrder(salesId1, projectid);
					    if(count1 == 0){
						     saleOrder2.setOrderId(projectid);
							 saleOrder2.setSalesId(salesId1);
							if(!(salesId1.equals(salesId))){
								list3.add(saleOrder2);
							}
					     }
					}
					
					
					   clientDrawing.setOrderId(projectid);
					   clientDrawing.setUserid(eid);
					  
					  
//					  if(!(map.get("drawingname") == null || "".equals(map.get("drawingname")))){
//						  String drawingsName = map.get("drawingname").toString();
//						  clientDrawing.setDrawingsName(drawingsName);
//					  }
					  
					  if(!(map.get("amount") == null || "".equals(map.get("amount")))){
						  Integer quantity = Integer.parseInt(map.get("amount").toString());
						  clientDrawing.setQuantity(quantity);
					  }
					  
					  if(!(map.get("price") == null || "".equals(map.get("price")))){
						  Double unitPrice = Double.parseDouble(map.get("price").toString());
						  clientDrawing.setUnitPrice(unitPrice);
					  }
					  if(!(map.get("moldprice") == null || "".equals(map.get("moldprice")))){
						  Double moldPrice = Double.parseDouble(map.get("moldprice").toString());
						  clientDrawing.setMoldPrice(moldPrice);
					  }
					  
					  if(!(map.get("product") == null || "".equals(map.get("product")))){
	                      String productName = map.get("product").toString();
						  clientDrawing.setProductName(productName);
					  }
					
					
					clientOrderService.insertClientOrder(clientOrder, projectid, list2,factoryUserRelation,userFactoryRelation,list3,clientDrawing);
			   }
			  
			  return "SUCCESS";
			  
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "NO";
		} catch (Exception e) {
			e.printStackTrace();
			return "NO";
		}

	  }  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
}
