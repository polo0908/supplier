package com.cbt.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.cbt.entity.AmountUnit;
import com.cbt.entity.BackUser;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.InvoiceInfo;
import com.cbt.entity.PaymentPlan;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.entity.UserFactoryRelation;
import com.cbt.service.AmountUnitService;
import com.cbt.service.BackUserService;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ClientOrderService;
import com.cbt.service.FactoryUserRelationService;
import com.cbt.service.InvoiceInfoService;
import com.cbt.service.PaymentPlanService;
import com.cbt.service.SaleCustomerService;
import com.cbt.service.UserService;
import com.cbt.util.Client;
import com.cbt.util.JsonUtil;
import com.cbt.util.SerializeUtil;
import com.cbt.util.WebCookie;
import com.sun.star.uno.RuntimeException;

/**
 * 提供给其他系统的接口
 * @author polo
 * 2017年5月23日
 */
@Controller
@RequestMapping("/port")
public class SendDataPort {
	
	  private static final String FACTORY_ID = "f1010";  //凯融默认工厂ID
	  private static final Integer ORDER_SOURCE = 3;
	  private static final Integer ADMIN = 1;
	  
	  
	  public static Logger LOG = Logger.getLogger(SendDataPort.class);
	  
	  
	  @Resource
	  private ClientOrderService clientOrderService;
	    
	  @Resource
	  private SaleCustomerService saleCustomerService;
	  
	  @Resource
	  private BackUserService backUserService;
	  
	  @Resource
	  private FactoryUserRelationService factoryUserRelationService;
	  
	  @Resource
	  private InvoiceInfoService invoiceInfoService;
	  
	  @Resource
	  private AmountUnitService amountUnitService;
	  
	  
	  @Resource
	  private PaymentPlanService paymentPlanService;
	  /**
	   * 交期时间同步到采购系统接口
	   * @author polo
	   * 2017年5月22日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/deliveryDateSYNC.do")	  
	  public void deliveryDateSYNC(HttpServletRequest request,HttpServletResponse response){

	    try {	  
	       String orderId = request.getParameter("orderId");
	       String deliveryDate = request.getParameter("deliveryDate");	
	       String poUpdateTime = null;
           ClientOrder clientOrder =  clientOrderService.queryByOrderId(orderId);
           String poPath = clientOrder.getPoPath();
           if(!(poPath == null || "".equals(poPath))){
        	   poUpdateTime = clientOrder.getPoUpdateTime();
           }
           
              Map<String,String> map = new HashMap<String, String>();
              map.put("orderId", orderId);
              map.put("deliveryDate", deliveryDate);
              map.put("poUpdateTime", poUpdateTime);
              Client.sendPost("http://192.168.1.189:8084/Test/project/updateCRMProject", map);
	  
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }

	  }
	  
	  
	  /**
	   * 发送订单号到ERP导入数据
	   * @author polo
	   * 2017年5月27日
	   *
	   */
	  @ResponseBody
	  @RequestMapping("/sendOrderId2ERP.do")	 
	  public JsonResult<String> sendOrderId2ERP(HttpServletRequest request,HttpServletResponse response){
		    
				String orderId = request.getParameter("orderId");
				if(orderId == null || "".equals(orderId)){
					throw new RuntimeException("订单号不能为空");
				}
				String customerEmail = request.getParameter("customerEmail");				
				
				    
				    PrintWriter out1 = null;
			        BufferedReader in1 = null;
			        String result2 = "";
			        try {    
			            URL realUrl1 = new URL("http://112.64.174.34:33169/ERP-NBEmail/helpServlet?action=getinformation&className=ExternalinterfaceServlet");
			            // 打开和URL之间的连接
			            URLConnection conn1 = realUrl1.openConnection();
			            // 设置通用的请求属性
			           // conn1.setRequestProperty("accept", "*/*");
					    conn1.setRequestProperty("connection", "Keep-Alive");
			            conn1.setRequestProperty("user-agent",
			                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			            // 发送POST请求必须设置如下两行
			            conn1.setDoOutput(true);
			            conn1.setDoInput(true);
			            // 获取URLConnection对象对应的输出流
			            out1 = new PrintWriter(conn1.getOutputStream());
			            // 发送请求参数
			            out1.print("result="+"&&projectId="+orderId+"&&email="+customerEmail);
			            // flush输出流的缓冲
			            out1.flush();
			            // 定义BufferedReader输入流来读取URL的响应
			            in1 = new BufferedReader(
			                    new InputStreamReader(conn1.getInputStream()));
			            String line;
			            while ((line = in1.readLine()) != null) {
			                result2 += line;
			            }
	
			            if(result2 == null || "".equals(result2)){
			            	 return new JsonResult<String>(0,"未查询订单");    	
			            }else{

							    Map<Object, Object> map1 = SerializeUtil.JsonToMap(result2);
			            	    Object obj = map1.get("invoice");
								String str = null;
								str = obj.toString();
								Map<Object, Object> map = SerializeUtil.JsonToMap(str);
								
							    ClientOrder clientOrder = new ClientOrder();
								SaleCustomer saleCustomer = new SaleCustomer();
								SaleCustomer saleCustomer2 = new SaleCustomer();
								SaleCustomer saleCustomer4 = new SaleCustomer();
								List<SaleCustomer> list = new ArrayList<SaleCustomer>();
								SaleOrder saleOrder = new SaleOrder();
						  	    SaleOrder saleOrder2 = new SaleOrder();
							    FactoryUserRelation factoryUserRelation = new FactoryUserRelation();
							    UserFactoryRelation userFactoryRelation = new UserFactoryRelation();
							    List<SaleOrder> list3 = new ArrayList<SaleOrder>();
								
								
								String projectid = map.get("projectid").toString();
								String eid = map.get("eid").toString();
								if(projectid == null || "".equals(projectid) || eid == null || "".equals(eid)){
									throw new RuntimeException("项目号、客户id不能为空");
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
									amount = Double.parseDouble(map.get("amount").toString());
								}	
								Double actualAmount = 0.0;
								if(!(map.get("sumacount") == null || "".equals(map.get("sumacount")))){							  
									  actualAmount = Double.parseDouble(map.get("sumacount").toString());					  
								  }
								String createTime = (String)map.get("createtime");	  
								String salesId = null;
								String salesId1 = null;
								String salesName = (String)map.get("customerManager");
								String salesName1 = (String)map.get("merchandManager1");
								BackUser b = backUserService.queryBackUserByName(salesName);
								BackUser b1 = backUserService.queryBackUserByName(salesName1);
								if(!(b == null || "".equals(b))){
									salesId = b.getBackUserid();
								}
								if(!(b1 == null || "".equals(b1))){
									salesId1 = b1.getBackUserid();
								}
								
								
								String factoryId = WebCookie.getFactoryId(request);
								
								/*
								 * 当然登录销售如果不是管理员（将加入客户销售关联表）
								 */
								String loginSale = WebCookie.getUserId(request);
								BackUser backUser = backUserService.queryBackUserByUserId(loginSale);
								Integer permission = backUser.getPermission();
								if(permission == null || "".equals(permission)){
									permission = 2;
								}
								
								clientOrder.setFactoryId(factoryId);
								clientOrder.setOrderId(projectid);
								clientOrder.setUserid(eid);
								clientOrder.setCreateTime(createTime);
								clientOrder.setAmount(amount);
								clientOrder.setActualAmount(actualAmount);
								clientOrder.setOrderStatus(0);
								clientOrder.setOrderTypeId(5);
								clientOrder.setOrderSource(ORDER_SOURCE);
								
								if(!(salesId == null || "".equals(salesId))){
									SaleCustomer saleCustomer1 = saleCustomerService.queryBySaleAndCustomer(salesId, eid);
								    if(saleCustomer1 == null || "".equals(saleCustomer1)){
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
									SaleCustomer saleCustomer3 = saleCustomerService.queryBySaleAndCustomer(salesId1, eid);
									if(saleCustomer3 == null || "".equals(saleCustomer3)){
										saleCustomer2.setSalesId(salesId1);
										saleCustomer2.setUserid(eid);
										if(!(salesId1.equals(salesId))){
											list.add(saleCustomer2);
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
								
								if(permission != ADMIN){
									if(!(loginSale == null || "".equals(loginSale))){
										SaleCustomer saleCustomer5 = saleCustomerService.queryBySaleAndCustomer(loginSale, eid);
										if(saleCustomer5 == null || "".equals(saleCustomer5)){
											saleCustomer4.setSalesId(loginSale);
											saleCustomer4.setUserid(eid);
											list.add(saleCustomer4);
										}
									}
								}
								
								clientOrderService.insertClientOrder(clientOrder, projectid, list ,factoryUserRelation,userFactoryRelation,list3);
							  
			            	
			            	return new JsonResult<String>(0,"success");   
			            }
			            	            
			       
			        } catch (Exception e) {
			            System.out.println("发送 POST 请求出现异常！"+e);
			            e.printStackTrace();
			            return new JsonResult<String>(1,"发送失败");
			        }
			        
			        
			        //使用finally块来关闭输出流、输入流
			        finally{
			            try{
			                if(out1!=null){
			                    out1.close();
			                }
			                if(in1!=null){
			                    in1.close();
			                }
			            }
			            catch(IOException ex){
			                ex.printStackTrace();
			            }
								
			        }
	  }
	  
	  
	  
	  
	  
	  
	  /**
	   * 同步Invoice到ERP
	   */
	  @ResponseBody
	  @RequestMapping("/invoiceToERPSYNC.do")	 
	  public String invoiceToERPSYNC(HttpServletRequest request,HttpServletResponse response){
		    
				String orderId = request.getParameter("orderId");
				if(orderId == null || "".equals(orderId)){
					throw new RuntimeException("订单号不能为空");
				}
				String invoiceId = request.getParameter("invoiceId");
				InvoiceInfo invoice = invoiceInfoService.queryByInvoiceId(invoiceId, FACTORY_ID);
				List<PaymentPlan> payments = paymentPlanService.queryPaymentPlan(invoiceId);  	
				String obj = JSONArray.toJSONString(payments);
				Integer amountUnit = invoice.getAmountUnit();
				if(amountUnit == 0){
					amountUnit = 1;
				}else if(amountUnit == 1){
					amountUnit = 2;
				}else if(amountUnit == 2){
					amountUnit = 3;
				}else if(amountUnit == 3){
					amountUnit = 5;
				}
				
				    PrintWriter out1 = null;
			        BufferedReader in1 = null;
			        String result2 = "";
			        try {    
			            URL realUrl1 = new URL("http://112.64.174.34:33169/ERP-NBEmail/helpServlet?action=modifyinvoice&className=ItCaseIdServlet");
			            // 打开和URL之间的连接
			            URLConnection conn1 = realUrl1.openConnection();
			            // 设置通用的请求属性
			           // conn1.setRequestProperty("accept", "*/*");
					    conn1.setRequestProperty("connection", "Keep-Alive");
			            conn1.setRequestProperty("user-agent",
			                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			            // 发送POST请求必须设置如下两行
			            conn1.setDoOutput(true);
			            conn1.setDoInput(true);
			            // 获取URLConnection对象对应的输出流
			            out1 = new PrintWriter(conn1.getOutputStream());
			            // 发送请求参数
			            out1.print("result="+"&&projectId="+orderId+"&&invoiceId="+invoiceId+"&&amount="+invoice.getAmount() + "&&amountUnit="+amountUnit + "&&payment="+obj);
			            // flush输出流的缓冲
			            out1.flush();
			            // 定义BufferedReader输入流来读取URL的响应
			            in1 = new BufferedReader(
			                    new InputStreamReader(conn1.getInputStream()));
			            String line;
			            while ((line = in1.readLine()) != null) {
			                result2 += line;
			            }
		            	return "success";   

			        } catch (Exception e) {
			            System.out.println("发送 POST 请求出现异常！"+e);
			            e.printStackTrace();
			            return "error";
			        }
			        
			        
			        //使用finally块来关闭输出流、输入流
			        finally{
			            try{
			                if(out1!=null){
			                    out1.close();
			                }
			                if(in1!=null){
			                    in1.close();
			                }
			            }
			            catch(IOException ex){
			                ex.printStackTrace();
			            }
								
			        }
	  }
	  
	  
	  
	  /**
	   * 获取汇率
	   * @param request
	   * @param response
	   */
	  @ResponseBody
	  @RequestMapping("/getExchangeRate.do")	  
	  public String getExchangeRate(HttpServletRequest request,HttpServletResponse response){

	    try {	
	    	
//	    	  String str =  request.getParameter("str");
	    	  Map<String,String> map = new HashMap<String, String>();
	    	  List<AmountUnit> amountUnits = amountUnitService.queryAmountUnit();
              for (AmountUnit amountUnit : amountUnits) {
            	  map.put(amountUnit.getCurrencyShorthand(), amountUnit.getExchangeRate().toString());
			  }
              map.put("updateDate", amountUnits.get(0).getUpdateDate());
              String obj = SerializeUtil.ObjToJson(map);
              LOG.info("汇率信息" + obj);  
              return obj;	  
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	            return "false";
	        }

	  }
	  
	  
	  
	  
	  
	  
	  /**
	   * 发送订单状态同步到NBMail
	   * @param request
	   * @param response
	   */
	  @ResponseBody
	  @RequestMapping("/sendOrderStatus.do")	  
	  public void sendOrderStatus(HttpServletRequest request,HttpServletResponse response){
		  
		  try {
			  String orderId = request.getParameter("orderId");		  
			  ClientOrder clientOrder = clientOrderService.queryByOrderId(orderId);
			  
			  if(clientOrder.getOrderStatus() == 1){
				  String message = Client.sendMessage("http://112.64.174.34:43887/NBEmail/helpServlet?action=endOfProject&className=ExternalInterfaceServlet", "&&orderId="+orderId);
				  if("YES".equals(message)){
                      LOG.info("同步客户状态成功" + message);  
                  }else{
                	  LOG.info("同步客户状态失败" + message);   
                  }
			  }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("同步订单状态失败=="+e.getMessage());
		}

		  
	  }
	  
	  /**
	   * 发送客户状态同步到NBMail(根据是否有invoice判断是新客户还是老客户)
	   * @param request
	   * @param response
	   */
	  @ResponseBody
	  @RequestMapping("/sendCustomerStatus.do")	  
	  public void sendCustomerStatus(HttpServletRequest request,HttpServletResponse response){
		  
		  try {
			  String orderId = request.getParameter("orderId");		  
			  List<InvoiceInfo> list = invoiceInfoService.queryInvoiceByOrderId(orderId);			  
			  if(list.size() != 0){		      
			      String message = Client.sendMessage("http://192.168.1.62:8080/NBEmail/helpServlet?action=inquiriesOldAndNewCustomers&className=ExternalInterfaceServlet", "&&orderId="+orderId);			      
                  if("YES".equals(message)){
                      LOG.info("同步客户状态成功" + message);  
                  }else{
                	  LOG.info("同步客户状态失败" + message);   
                  }

			  }
		} catch (Exception e) {
			e.printStackTrace();
			LOG.info("同步客户状态失败=="+e.getMessage());
		}

		  
	  }
	  
	  
	  
	  
	  
	  
	  
}
