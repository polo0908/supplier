package com.cbt.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cbt.entity.BackUser;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.NbMailCountry;
import com.cbt.entity.PaymentPlan;
import com.cbt.entity.QuotationBean;
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
import com.cbt.util.DateFormat;
import com.cbt.util.JsonUtil;
import com.cbt.util.SerializeUtil;
import com.sun.star.uno.RuntimeException;

/**
 * 获取数据接口
 * 动态获取数据，并存入数据库
 * @author polo
 *
 */
@Controller
@RequestMapping("/port")
public class ReceiveCurrentDataByHourPort {
	
	
	private static final String FACTORY_ID = "f1010";  //凯融默认工厂ID
	private static final int ORDER_SOURCE = 3;         //ERP拉取订单
	private static final int TRANSACTION_TYPE = 1;     //老客户
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
	   * 获取ERP订单数据（定时同步消息队列）(ERP订单)
	   * @author polo
	   * 2017年7月7日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/importERPOrderByCurrentHour.do")	  
	   public String importERPOrderByCurrentHour(@RequestParam Map<String,String> map2) throws UnsupportedEncodingException{
		  
		  try {
			  String jsonStr = map2.get("ItemCase");
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
	   * 通过项目号导入图纸数据
	   * @author polo
	   * 2017年7月7日
	   *
	   */
	  @ResponseBody
	  @RequestMapping(value ="/importDrawingByCurrentHour.do",method = RequestMethod.POST)	  
	  public String importDrawing(@RequestParam Map<String,String> map2){
		  
		  try {
			  
			  String jsonStr = map2.get("Mould");
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
					  String orderId = cl.get("caseno").toString();    
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
					  String drawingsName = cl.get("drawingname").toString();
					  clientDrawing.setDrawingsName(drawingsName);
				  }
				  
				  if(!(cl.get("amount") == null || "".equals(cl.get("amount")))){
					  Integer quantity = Integer.parseInt(cl.get("amount").toString());
					  clientDrawing.setQuantity(quantity);
				  }
				  
				  if(!(cl.get("price") == null || "".equals(cl.get("price")))){
					  Double unitPrice = Double.parseDouble(cl.get("price").toString());
					  clientDrawing.setUnitPrice(unitPrice);
				  }
				  
				  if(!(cl.get("product") == null || "".equals(cl.get("product")))){
                      String productName = cl.get("product").toString();
					  clientDrawing.setProductName(productName);
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
	   * 导入erp发票数据
	   * Date:2017/06/21
	   * Author:polo
	   * @param map
	   * @return
	   */
	  @SuppressWarnings("unchecked")
	  @ResponseBody
	  @RequestMapping(value ="/importInvoiceInfoByCurrentHour.do",method = RequestMethod.POST)	  
	  public String importInvoiceInfo(@RequestParam Map<String,String> map2){
		  
		  try {
			  String jsonStr = map2.get("Invoiceinfo1");
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
					  invoiceInfo.setOrderId(cl.get("projectId").toString());
					  paymentPlan.setOrderId(cl.get("projectId").toString());
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
				  if(!(cl.get("ifdate") == null || "".equals(cl.get("ifdate")))){		
					  paymentPlan.setPaymentTime(cl.get("ifdate").toString());
				  }
				  if(!(cl.get("iimoney") == null || "".equals(cl.get("iimoney")))){		
					  paymentPlan.setAmount(Double.parseDouble(cl.get("iimoney").toString()));
				  }
				  if(!(cl.get("ifmoney") == null || "".equals(cl.get("ifmoney")))){		
					  paymentPlan.setAmountActual(Double.parseDouble(cl.get("ifmoney").toString()));
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
	  @RequestMapping(value ="/importCustomerByCurrentHour.do",method = RequestMethod.POST)	  
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
				  user.setPwd("123456");				  
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
			  }
	       
			  userService.insertUser(list2, list3, list1,list4,list5);
			  System.out.println("=============更新成功=============");
			  return "SUCCESS";
			} catch (Exception e) {
				e.printStackTrace();
				return "更新失败"+e.getMessage();
			}
	  }
     	  
	  
	  
	  
	  
	  
	  
	  
}
