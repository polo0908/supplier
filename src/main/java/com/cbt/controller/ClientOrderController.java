package com.cbt.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cbt.entity.BackUser;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.ClientOrderPo;
import com.cbt.entity.ClientOrderQcReport;
import com.cbt.entity.ClientOrderQuery;
import com.cbt.entity.ClientOrderShippingDoc;
import com.cbt.entity.ClientOrderType;
import com.cbt.entity.EmailAlertInfo;
import com.cbt.entity.MessageCenter;
import com.cbt.entity.Milestone;
import com.cbt.entity.OrderMessage;
import com.cbt.entity.ProductionPhotoTimeline;
import com.cbt.entity.User;
import com.cbt.enums.DocumentGenEnum;
import com.cbt.enums.ImgGenEnum;
import com.cbt.service.BackUserService;
import com.cbt.service.ClientDrawingsService;
import com.cbt.service.ClientOrderPoService;
import com.cbt.service.ClientOrderQcReportService;
import com.cbt.service.ClientOrderService;
import com.cbt.service.ClientOrderShippingDocService;
import com.cbt.service.CrmClientidService;
import com.cbt.service.EmailAlertInfoService;
import com.cbt.service.InvoiceInfoService;
import com.cbt.service.MessageCenterService;
import com.cbt.service.MilestoneService;
import com.cbt.service.OrderMessageService;
import com.cbt.service.PaymentPlanService;
import com.cbt.service.ProductionPhotoTimelineService;
import com.cbt.service.QualityIssuesPicService;
import com.cbt.service.ReOrderDrawingsService;
import com.cbt.service.ReOrderService;
import com.cbt.service.RfqInfoService;
import com.cbt.service.UserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.OperationFileUtil;
import com.cbt.util.SplitPage3;
import com.cbt.util.UploadAndDownloadPathUtil;
import com.cbt.util.WebCookie;


@Controller 

public class ClientOrderController {
	
  public static Logger logger = Logger.getLogger(ClientOrderController.class);
  
  
  @Resource
  private ClientOrderService clientOrderService;
  @Resource
  private UserService userService;
  @Resource
  private InvoiceInfoService invoiceInfoService;
  @Resource
  private ClientDrawingsService clientDrawingsService;
  @Resource
  private ReOrderDrawingsService reOrderDrawingsService;
  @Resource
  private ReOrderService reOrderService;
  @Resource
  private RfqInfoService rfqInfoService; 
  @Resource
  private BackUserService backUserService;
  @Resource
  private MilestoneService milestoneService;
  @Resource
  private MessageCenterService messageCenterService;
  @Resource
  private OrderMessageService orderMessageService;
  @Resource
  private QualityIssuesPicService qualityIssuesPicService;
  @Resource
  private ProductionPhotoTimelineService productionPhotoTimelineService;
  @Resource
  private CrmClientidService crmClientidService;
  @Resource
  private EmailAlertInfoService emailAlertInfoService;
  @Resource
  private PaymentPlanService paymentPlanService;
  @Resource
  private ClientOrderQcReportService clientOrderQcReportService;
  @Resource
  private ClientOrderPoService clientOrderPoService;
  @Resource
  private ClientOrderShippingDocService clientOrderShippingDocService;
  
  //订单初始类型
  private static final int ORDER_TYPE=5;
  
  private static final int PIC_STATUS = 1;   //消息是否含有图片
  
  private static final int EXPECTED = 0;   //里程碑预计完成
  /**
   * 查询client_order表数据,返回前台展示 
   * @param request
   * @param response
   * @return
   * @throws Exception 
   */   
   @RequestMapping("/queryAllClientOrder.do")
   public String queryAllClientOrder(HttpServletRequest request, HttpServletResponse response) throws Exception{


	   try {
		    ClientOrderQuery clientOrderQuery = getClientOrder(request);
		    Integer pageSize = clientOrderQuery.getPageSize();
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
			List<ClientOrderQuery> list = null;
			int total = 0;
			
			//检查用户权限如果有管理员权限，查询所有订单数据
		   if(permission == 1){
			   list = clientOrderService.queryListByDateAdmin(clientOrderQuery);
			   total = clientOrderService.totalAmountAdmin(clientOrderQuery);
		   }else{
			   list = clientOrderService.queryListByDate(clientOrderQuery);
			   total = clientOrderService.totalAmount(clientOrderQuery); 
		   }
		   List<ClientOrderType> types = clientOrderService.queryAllType();
		   
		   SplitPage3.buildPager(request, total, pageSize, page);
		   List<EmailAlertInfo> emailAlertInfos = emailAlertInfoService.queryAll();
		   
		   List<Integer> poCounts = new ArrayList<Integer>();
		   List<Integer> qcCounts = new ArrayList<Integer>();
		   List<Integer> shippingCounts = new ArrayList<Integer>();
		   //获取qc、shipping信息
		   for (ClientOrder clientOrder : list) {
			   List<ClientOrderPo> pos = clientOrderPoService.queryByClientOrderId(clientOrder.getOrderId());
			   poCounts.add(pos.size());
			   List<ClientOrderQcReport> qcReports = clientOrderQcReportService.queryByClientOrderId(clientOrder.getOrderId());
			   qcCounts.add(qcReports.size());
			   List<ClientOrderShippingDoc> shippingReports = clientOrderShippingDocService.queryByClientOrderId(clientOrder.getOrderId());
			   shippingCounts.add(shippingReports.size());
		   }

		   request.setAttribute("types", types);
		   request.setAttribute("clientOrder", list);
		   request.setAttribute("emailAlertInfos", emailAlertInfos);
		   request.setAttribute("poCounts", poCounts);
		   request.setAttribute("qcCounts", qcCounts);
		   request.setAttribute("shippingCounts", shippingCounts);
		} catch (Exception e) {
			logger.error("======ClientOrderController  queryAllClientOrder======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    
	   
	   
	   return "index-background.jsp";
   }
   
   /**
    * 获取前台输入数据
    * @param request
    * @return
    */
     private ClientOrderQuery getClientOrder(HttpServletRequest request){
    	 ClientOrderQuery clientOrderQuery = new ClientOrderQuery();
  	     String str1 = request.getParameter("page");
  	     String str2 = request.getParameter("pageSize");
  	     String factoryId = WebCookie.getFactoryId(request);
  		 String salesId = WebCookie.getUserId(request); 
  		
    	 if(!(request.getParameter("userName") ==null || "".equals(request.getParameter("userName")))){
 	    	clientOrderQuery.setUserName(request.getParameter("userName"));
 	    }
 		if(!(request.getParameter("beginDate") ==null || "".equals(request.getParameter("beginDate")))){
 	
				clientOrderQuery.setBeginDate(request.getParameter("beginDate"));
		
 		}
 		if(!(request.getParameter("endDate") ==null || "".equals(request.getParameter("endDate")))){
				clientOrderQuery.setEndDate(request.getParameter("endDate"));
 		}
 		
 		if(!(request.getParameter("orderTypeId") ==null || "".equals(request.getParameter("orderTypeId")))){
				clientOrderQuery.setOrderTypeId(Integer.parseInt(request.getParameter("orderTypeId")));
 		}	    
	   
	    int page = 1;
		if(str1 != null) {
			page = Integer.parseInt(str1);
		}
		int pageSize = 10;		
		if(str2 != null) {
			pageSize = Integer.parseInt(str2);
		}
 		int start = (page-1) * pageSize;

 		clientOrderQuery.setSalesId(salesId);
		clientOrderQuery.setStart(start);
		clientOrderQuery.setPageSize(pageSize);
		clientOrderQuery.setFactoryId(factoryId);
		
	    return clientOrderQuery;
     }
     
     
     
     /**
      * 查询客户订单（根据客户排序）
      * @author polo
      * 2017年6月1日
      *
      */
     @RequestMapping("/queryAllOrderByUser.do")
     public String queryAllOrderByUser(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 	 
  	   try {
		    ClientOrderQuery clientOrderQuery = getClientOrder(request);
		    Integer pageSize = clientOrderQuery.getPageSize();
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
			List<ClientOrderQuery> list = null;
			int total = 0;
			
			//检查用户权限如果有管理员权限，查询所有订单数据
		   if(permission == 1){
			   list = clientOrderService.queryListByDateAdminOrderByUser(clientOrderQuery);
			   total = clientOrderService.totalAmountAdmin(clientOrderQuery);
		   }else{
			   list = clientOrderService.queryListByDateOrderByUser(clientOrderQuery);
			   total = clientOrderService.totalAmount(clientOrderQuery); 
		   }

		   SplitPage3.buildPager(request, total, pageSize, page);
		   
		   request.setAttribute("clientOrder", list);
		} catch (Exception e) {
			logger.error("======ClientOrderController  queryAllClientOrder======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
   
	   
	   
	   return "index-background.jsp";
     }
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
     
 /**
  * (单独用于clientOrder po文件更新)   
  * client_order数据库更新
  * 根据orderId 生成订单文件夹
  * 将文件夹打包
  * @param request
  * @param response
  * @return
 * @throws Exception 
  */    
     @ResponseBody
     @RequestMapping("/updateClientOrderPo.do")
     public JsonResult<ClientOrder> updateClientOrderPo(HttpServletRequest request, HttpServletResponse response){
    	 
    		try {
 
				Integer id = Integer.parseInt(request.getParameter("clientOrderId"));   		 
				ClientOrder clientOrder = clientOrderService.queryById(id); 		 
				String orderId = clientOrder.getOrderId();
				String path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + orderId + File.separator;
				if(!(request.getParameter("poName") == null || "".equals(request.getParameter("poName")))){
				 String poName = request.getParameter("poName");   		 
				 File file = new File(path);
				 if  (!file.exists()  && !file .isDirectory())      
				 {         
					 file .mkdir();     
				 }  	    	  
			  	  try {
							//调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
							Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
							clientOrder.setPoPath(multiFileUpload.get(poName));
							clientOrder.setPoUpdateTime(DateFormat.format());
							clientOrder.setOldOrNewPo("new");
							
					   } catch (IllegalStateException e) {			
							e.printStackTrace();
					   } catch (IOException e) {			
							e.printStackTrace();
					   }
			  	  }  
  	  
  	 /**
  	  * 对生成的文件夹压缩成rar文件
  	  */
//  	 byte[] buffer = new byte[1024]; 
//  	 String strZipPath = UploadAndDownloadPathUtil.getCompressedUpLoadAndDownLoadPath() + id +"&"+ orderId + ".rar";
//  	 
//  	 try {    
//				 ZipOutputStream out = new ZipOutputStream(new FileOutputStream(    
//				         strZipPath));    
//				 // 需要同时下载的两个文件result.txt ，source.txt    
//				 File file = new File(path);
//				 File[] file1 = file.listFiles();          
//				 for (int i = 0; i < file1.length; i++) {    
//				     FileInputStream fis = new FileInputStream(file1[i]);    
//				     out.putNextEntry(new ZipEntry(file1[i].getName()));    
//				     //设置压缩文件内的字符编码，不然会变成乱码    
//				     out.setEncoding("GBK");    
//				     int len;    
//				     // 读入需要下载的文件的内容，打包到zip文件    
//				     while ((len = fis.read(buffer)) > 0) {    
//				         out.write(buffer, 0, len);    
//				     }    
//				     out.closeEntry();    
//				     fis.close();    
//				 }    
//				 out.close();      
//       } catch (Exception e) {    
//				logger.error(e.getMessage()); 
//				e.printStackTrace();
//       }    
  	 
  	 clientOrderService.updateClientOrder(clientOrder);
     return new JsonResult<ClientOrder>(clientOrder);
	} catch (Exception e) {
	logger.error("======ClientOrderController  updateClientOrder======="+e.getMessage());
	e.printStackTrace();
	return new JsonResult<ClientOrder>(1,"更新失败");
	}  	 
	    	         
 } 
     

     
     
     
     
     
     @RequestMapping("/toCreateClientOrder.do")
     public String toCreateClientOrder(HttpServletRequest request, HttpServletResponse response){
         
    	 return "newOrder2.jsp";
     }
     
     
     /**
      * 获取客户姓名
      * @param request
      * @param response
      * @return
      */
     @ResponseBody
     @RequestMapping("/getUserName.do")     
     public JsonResult<Map<Object, Object>> getUserName(HttpServletRequest request, HttpServletResponse response){
    	 
		try {
			Map<Object, Object> map = null;
			String userid = request.getParameter("userid");
			 if(!(userid == null || "".equals(userid))){
			  map = userService.queryByUser(userid); 
			 }else{
				 throw new RuntimeException("未获取到客户信息！"); 
			 }
			
			 return new JsonResult<Map<Object,Object>>(map);
			 
		} catch (Exception e) {
			logger.error("======ClientOrderController  getUserName======="+e.getMessage());
			return new JsonResult<Map<Object,Object>>(1,"未获取到客户信息");
		}

     }
     
     
     /**
      * 检查订单号
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
     @ResponseBody
     @RequestMapping("/checkOrderId.do")
     public String checkOrderId(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try {
			String orderId = null;    	
				if(!(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId")))){
					orderId = request.getParameter("orderId");  	
					if(!(clientOrderService.queryByOrderId(orderId) == null )){
						throw new RuntimeException("orderId 已经存在！");
					}
				}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
			
			return "legal";
     }
     
     
     /**
      * 新建订单（client_order）
      * @param request
      * @param response
      * @return
      * @throws Exception
      */
     @ResponseBody
     @RequestMapping("/saveClientOrder.do")
     public JsonResult<String> saveClientOrder(HttpServletRequest request, HttpServletResponse response){
    	
    	 
    	try {
			ClientOrder clientOrder = new ClientOrder(); 
			Milestone milestone = new Milestone();
			String factoryId = WebCookie.getFactoryId(request);
			String salename = WebCookie.getUserName(request);
			clientOrder.setFactoryId(factoryId);
			String orderId = null;    	
			if(!(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId")))){
				orderId = request.getParameter("orderId");  	
				if(clientOrderService.queryByOrderId(orderId) != null && !"".equals(clientOrderService.queryByOrderId(orderId))){
					throw new RuntimeException("orderId 已经存在！");
				}else{
					clientOrder.setOrderId(orderId);	
				} 			
			}
			String poNumber = null;
			if(!(request.getParameter("poNumber") == null || "".equals(request.getParameter("poNumber")))){
				poNumber = request.getParameter("poNumber");
				clientOrder.setPoNumber(poNumber);
			}
			if(!(request.getParameter("projectName") == null || "".equals(request.getParameter("projectName")))){
				clientOrder.setProjectName(request.getParameter("projectName"));
			}
			 String userid = null;
			if(!(request.getParameter("userid") == null || "".equals(request.getParameter("userid")))){
				userid = request.getParameter("userid");
				clientOrder.setUserid(userid);
			}
			if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){
				clientOrder.setCreateTime(request.getParameter("createTime"));
				milestone.setMilestoneDate(DateFormat.formatDate1(request.getParameter("createTime")));
				milestone.setCreateTime(DateFormat.format());
				milestone.setDelayStatus(0);
				milestone.setProductionPhotoStatus(0);
				milestone.setMilestoneName("Start");
				milestone.setExpectedOrActually(1);
				milestone.setOrderId(orderId);
			}
//			if(!(request.getParameter("deliveryTime") == null || "".equals(request.getParameter("deliveryTime")))){
//				clientOrder.setDeliveryTime(request.getParameter("deliveryTime"));
//				milestone1.setMilestoneDate(DateFormat.formatDate1(request.getParameter("deliveryTime")));
//				milestone1.setCreateTime(DateFormat.format());
//				milestone1.setDelayStatus(0);
//				milestone1.setProductionPhotoStatus(0);
//				milestone1.setMilestoneName("Finished");
//				milestone1.setExpectedOrActually(0);
//				milestone1.setOrderId(orderId);
//			}
			if(!(request.getParameter("outputTime") == null || "".equals(request.getParameter("outputTime")))){
				clientOrder.setOutputTime(request.getParameter("outputTime"));
			}
			if(!(request.getParameter("arrivalDate") == null || "".equals(request.getParameter("arrivalDate")))){
				clientOrder.setArrivalDate(request.getParameter("arrivalDate"));
			}
			if(!(request.getParameter("amount") == null || "".equals(request.getParameter("amount")))){
				clientOrder.setAmount(Double.parseDouble(request.getParameter("amount")));
			}
			if(!(request.getParameter("orderStatus") == null || "".equals(request.getParameter("orderStatus")))){
				clientOrder.setOrderStatus(Integer.parseInt(request.getParameter("orderStatus")));
			}else{
				 clientOrder.setOrderStatus(0);
			}
			if(!(request.getParameter("orderTypeId") == null || "".equals(request.getParameter("orderTypeId")))){
				clientOrder.setOrderTypeId(Integer.parseInt(request.getParameter("orderTypeId")));
			}
			 
			clientOrder.setSalesName(WebCookie.getUserName(request));
			clientOrder.setOrderSource(0);
			clientOrder.setOrderTypeId(ORDER_TYPE);
			clientOrder.setUpdateTime(DateFormat.format());
			clientOrder.setSalesName(salename);
			
			String reOrderId = request.getParameter("reOrderId");   		 
			String drawingId = request.getParameter("drawingId");   		 
			String poPath = request.getParameter("poPath");   		 
			String qcPath = request.getParameter("qcPath");
			String shippingPath = request.getParameter("shippingPath");
			String clientDrawings = request.getParameter("clientDrawings");
			
			  if(!(poPath == null || "".equals(poPath))){  		 	
					clientOrder.setPoPath(poPath);
					clientOrder.setPoUpdateTime(DateFormat.format());
					clientOrder.setOldOrNewPo("new");	
				  }  
			    if(!(qcPath == null || "".equals(qcPath))){
					 clientOrder.setQcReportPath (qcPath);
					 clientOrder.setQcUpdateTime(DateFormat.format());
					 clientOrder.setOldOrNewQc("new");				 
			      }    
				if(!(shippingPath == null || "".equals(shippingPath))){	
					 clientOrder.setShippingDocPath(shippingPath);
					 clientOrder.setShippingUpdateTime(DateFormat.format());
					 clientOrder.setOldOrNewShipping("new");
			       } 				 				
			
			 clientOrderService.insertClientOrder(clientOrder, milestone, reOrderId, drawingId,clientDrawings,request);				
	    	 return new JsonResult<String>(0,"success");	
			
			} catch (Exception e) {
				logger.error("======ClientOrderController  saveClientOrder======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<String>(1,"submit failed");	
			}
 	   

     }
     
     /**
      * 查询订单详情的内容
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
     @RequestMapping("/querDetails.do")
     public String queryClientOrderDetails(HttpServletRequest request, HttpServletResponse response) throws Exception{
         try {
			 String orderId = request.getParameter("orderId"); 
			 orderId = Base64Encode.getFromBase64(orderId);
			 String userid = request.getParameter("userid"); 
			 userid = Base64Encode.getFromBase64(userid);
			 ClientOrder clientOrder = clientOrderService.queryByOrderId(orderId);
			 List<ClientDrawings> list = clientDrawingsService.queryListByOrderId(orderId);
			 List<Milestone> milestones = milestoneService.queryByOrderId(orderId);
             List<ProductionPhotoTimeline> list1 = productionPhotoTimelineService.queryByOrderIdGroupByDate(orderId);
             List<List<ProductionPhotoTimeline>> productionPhotoTimelines = new ArrayList<List<ProductionPhotoTimeline>>();
			 for (ProductionPhotoTimeline pr : list1) {
				List<ProductionPhotoTimeline> productionPhotoTimeline = productionPhotoTimelineService.queryByUploadDate(orderId, pr.getUploadDate());
		    	List<ProductionPhotoTimeline> document = productionPhotoTimelineService.queryDocumentByUploadDate(orderId, pr.getUploadDate());
	        	if(productionPhotoTimeline != null && productionPhotoTimeline.size() !=0){
	        		productionPhotoTimelines.add(productionPhotoTimeline);	
	        	}
	        	if(document != null && document.size() !=0){
	        		productionPhotoTimelines.add(document);	
	        	}
			 }
             
			List<Integer> counts = new ArrayList<Integer>(); 
		    List<List<OrderMessage>> orderMessagess = new ArrayList<List<OrderMessage>>();
//		    List<List<List<QualityIssuesPic>>> qualityIssuesPicss = new ArrayList<List<List<QualityIssuesPic>>>();
//		    List<List<QualityIssuesPic>> qualityIssuesPics = new ArrayList<List<QualityIssuesPic>>();
//		    List<QualityIssuesPic> qualityIssuesPic = new ArrayList<QualityIssuesPic>();
			List<MessageCenter> messageCenters = messageCenterService.queryMessageByOrderId(orderId);
			int count = 0;
			for (MessageCenter messageCenter : messageCenters) {
				if(!(messageCenter.getMessageType() == null || "".equals(messageCenter.getMessageType()))){
					  count = messageCenterService.totalByMessageType(messageCenter.getId(), messageCenter.getMessageType());
					  counts.add(count);
				 }
				List<OrderMessage> orderMessages = orderMessageService.queryByMessageCenterId(messageCenter.getId());
				orderMessagess.add(orderMessages);
				for (OrderMessage orderMessage : orderMessages) {
					 if(orderMessage.getPicStatus() == PIC_STATUS){
						  orderMessage.setQualityIssuesPic(qualityIssuesPicService.queryByOrderMessageId(orderMessage.getId()));
					  }			  
				}
				
//				 qualityIssuesPicss.add(qualityIssuesPics);
				 orderMessagess.add(orderMessages);
			}
			 
			 //获取上传的文件名	
			 String poName = null;
//			 String qcName = null;
//			 String shippingDocName = null;
			 if(!(clientOrder.getPoPath() == null || "".equals(clientOrder.getPoPath()))){
			        File tempFile =new File(clientOrder.getPoPath().trim());  			        
			        poName = tempFile.getName();  			          
			 }
			 
			 
			 //获取po、qc、shipping报告
			 List<ClientOrderPo> pos = clientOrderPoService.queryByClientOrderId(orderId);
			 for (ClientOrderPo po : pos) {
				 if(StringUtils.isNotBlank(po.getPoPath())){
					 po.setPoPath(new File(po.getPoPath().trim()).getName()); 
				 }
			 }
             List<ClientOrderQcReport> qcReports = clientOrderQcReportService.queryByClientOrderId(orderId);
             for (ClientOrderQcReport clientOrderQcReport : qcReports) {
            	 if(StringUtils.isNotBlank(clientOrderQcReport.getQcReportPath())){
                	 clientOrderQcReport.setQcReportPath(new File(clientOrderQcReport.getQcReportPath().trim()).getName()); 
            	 }
			 }
             List<ClientOrderShippingDoc> shippingDocs = clientOrderShippingDocService.queryByClientOrderId(orderId);
             for (ClientOrderShippingDoc shippingDoc : shippingDocs) {
            	 if(StringUtils.isNotBlank(shippingDoc.getShippingDocPath())){
            		 shippingDoc.setShippingDocPath(new File(shippingDoc.getShippingDocPath().trim()).getName()); 
            	 }
			 }
			 
			 
			 List<ClientOrderType> types = clientOrderService.queryAllType();			 			 
			 User user = userService.queryByUserId(userid);
			 
			 
			 //获取第一次付款时间
			 String firstPayment = paymentPlanService.selectFirstPayment(orderId);
			 
			 request.setAttribute("types", types);
			 request.setAttribute("user", user);
			 request.setAttribute("clientOrder", clientOrder);
			 request.setAttribute("list", list);
			 request.setAttribute("list1", list1);                 //查询图片根据上传时间去重后结果
			 request.setAttribute("productionPhotoTimeline", productionPhotoTimelines);
			 request.setAttribute("milestones", milestones);
			 request.setAttribute("pos", pos);
			 request.setAttribute("qcReports", qcReports);
			 request.setAttribute("shippingDocs", shippingDocs);
			 request.setAttribute("firstPayment", firstPayment);
			 
			 
		   request.setAttribute("messageCenters", messageCenters);
		   request.setAttribute("counts", counts);
		   request.setAttribute("orderMessages", orderMessagess);
//		   request.setAttribute("qualityIssuesPicss", qualityIssuesPicss);
		} catch (Exception e) {
			logger.error("======ClientOrderController  queryClientOrderDetails======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    	 return "orderDetails.jsp";
     }
     
     
     
     /**
      * 保存里程碑
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
     @ResponseBody
     @RequestMapping("/saveMilestone.do")
     public  Map<Object,Object> saveMilestone(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 
    	 Map<Object,Object> map = new HashMap<Object, Object>();
    	 Milestone milestone = new Milestone();
    	 try {
			 String milestoneName = request.getParameter("milestoneName");
			 String milestoneDate = request.getParameter("milestoneDate");
			 String orderId = request.getParameter("orderId");	
			 String expectedOrActually = request.getParameter("expectedOrActually");			 
			 if(!(request.getParameter("delayState") == null || "".equals(request.getParameter("delayState")))){
				 milestone.setDelayStatus(2);   
			 }else{
				 milestone.setDelayStatus(0);   
			 }
			 milestone.setMilestoneName(milestoneName);
			 milestone.setMilestoneDate(milestoneDate);
			 milestone.setOrderId(orderId);
			 milestone.setCreateTime(DateFormat.format());  	 
			 milestone.setProductionPhotoStatus(0); 
			 map = milestoneService.insert(milestone,expectedOrActually, orderId);
			 
		} catch (Exception e) {
			logger.error("======ClientOrderController  saveMilestone======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    	 
    	return map; 
     }
     
     /**
      * 保存里程碑完成
      * @param request
      * @param response
      * @return
      * @throws Exception 
      */
     @ResponseBody
     @RequestMapping("/saveMilestoneComplete.do")
     public  Map<Object,Object> saveMilestoneComplete(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 
    	 Map<Object,Object> map = new HashMap<Object, Object>();
    	 Milestone milestone = new Milestone();
    	 try {
    		 String id = request.getParameter("id");	
    		 String orderId = request.getParameter("orderId");	
    		 String expectedOrActually = request.getParameter("expectedOrActually");
    		 if(id == null || "".equals(id)){
    			 throw new Exception("未获取到Id");
    		 }else{
    			 milestone = milestoneService.queryById(Integer.parseInt(id));
    		 }
    		 milestone.setProductionPhotoStatus(0);
    		 milestone.setCreateTime(DateFormat.format());
    		 milestone.setMilestoneDate(DateFormat.currentDate());
    		 map = milestoneService.insert(milestone,expectedOrActually, orderId);   		
    		 
    	 } catch (Exception e) {
 			logger.error("======ClientOrderController  saveMilestoneComplete======="+e.getMessage());
 			e.printStackTrace();
    		 throw new Exception(e.getMessage());
    	 }
    	 
    	 return map; 
     }
     
     /**
      * 更新里程碑完成
      * @param request
      * @param response
      * @throws Exception
      */
     @RequestMapping("/updateMilestoneComplete.do")
     public void updateMilestoneComplete(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 
    	 try {
			Integer id = null;
			 if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
				 throw new RuntimeException("未获取到id");
			 }else{
				 id = Integer.parseInt(request.getParameter("id"));
			 }
			 Milestone milestone = milestoneService.queryById(id);
			 milestone.setDelayStatus(2);
			 milestoneService.updateMilestone(milestone);
			 
		} catch (Exception e) {
 			logger.error("======ClientOrderController  updateMilestoneComplete======="+e.getMessage());
 			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    	 
     }
     
    
     
     
     /**
      * 更新里程碑(时间、里程碑名)
      * @param request
      * @param response
      * @throws Exception
      */
     @ResponseBody
     @RequestMapping("/updateMilestone.do")
     public List<Milestone> updateMilestone(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 Milestone milestone = null;
    	 try {
    		 Integer id = null;
    		 if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
    			 throw new RuntimeException("未获取到id");
    		 }else{
    			 id = Integer.parseInt(request.getParameter("id"));
    		 }
    		 milestone = milestoneService.queryById(id);
    		 if(!(request.getParameter("milestoneName") == null || "".equals(request.getParameter("milestoneName")))){
    			 milestone.setMilestoneName(request.getParameter("milestoneName"));
    		 }
    		 if(!(request.getParameter("milestoneDate") == null || "".equals(request.getParameter("milestoneDate")))){
    			 milestone.setMilestoneDate(request.getParameter("milestoneDate"));
    		 }
    		 milestoneService.updateMilestone(milestone);
    		 List<Milestone> milestones = milestoneService.queryByOrderId(milestone.getOrderId());
    		 return milestones;
    	 } catch (Exception e) {
    			logger.error("======ClientOrderController  updateMilestoneDelay======="+e.getMessage());
     			e.printStackTrace();
    			throw new Exception(e.getMessage());
    	 }
    	
     }
     
     
     
     
     
     
     
     
     
     
     
     /**
      * 更新里程碑延期
      * @param request
      * @param response
      * @throws Exception
      */
     @ResponseBody
     @RequestMapping("/updateMilestoneDelay.do")
     public Milestone updateMilestoneDelay(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 Milestone milestone = null;
    	 try {
    		 Integer id = null;
    		 if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
    			 throw new RuntimeException("未获取到id");
    		 }else{
    			 id = Integer.parseInt(request.getParameter("id"));
    		 }
    		 milestone = milestoneService.queryById(id);
    		 if(!(request.getParameter("delayDate") == null || "".equals(request.getParameter("delayDate")))){
    			 milestone.setDelayDate(request.getParameter("delayDate"));
    		 }
    		 if(!(request.getParameter("delayRemark") == null || "".equals(request.getParameter("delayRemark")))){
    			 milestone.setDelayRemark(request.getParameter("delayRemark"));
    		 }
    		 milestone.setDelayStatus(1);
    		 milestoneService.updateMilestone(milestone);
    		 
    	 } catch (Exception e) {
    			logger.error("======ClientOrderController  updateMilestoneDelay======="+e.getMessage());
     			e.printStackTrace();
    			throw new Exception(e.getMessage());
    	 }
    	 
    	 return milestone;
     }
     
     
     /**
      * 删除里程碑
      * @param request
      * @param response
      * @return
      * @throws Exception
      */
     @ResponseBody
     @RequestMapping("/deleteMilestoneById.do")
     public JsonResult<List<Milestone>> deleteMilestoneById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	 try {
			Integer id = null;
			 if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
				 throw new Exception("未获取到id");
			 }else{
				 id = Integer.parseInt(request.getParameter("id"));
			 }
			 String orderId = request.getParameter("orderId");
			 List<Milestone> milestones = milestoneService.queryByOrderId(orderId, id);
			 
	    	 return new JsonResult<List<Milestone>>(0,"删除成功",milestones);
		} catch (Exception e) {
			logger.error("======ClientOrderController  deleteMilestoneById======="+e.getMessage());
			e.printStackTrace();
			return new JsonResult<List<Milestone>>(1,"删除失败");
		}
    	 
     }
     
     
     
     
     /**
      * 保存周报图片
      * @param request
      * @param response
      * @return
      * @throws Exception
      */
     @ResponseBody
     @RequestMapping("/saveProductionPhoto.do")
     public JsonResult<Map<Object,Object>> saveProductionPhoto(HttpServletRequest request, HttpServletResponse response) throws Exception{  
    	    
    	     List<ProductionPhotoTimeline> productionPhotos = null;
    	     Map<Object, Object> map = null;
    	    try {
				String orderId = null;
				if(request.getParameter("orderId1") == null || "".equals(request.getParameter("orderId1"))){
					throw new RuntimeException("未获取到orderId");
				}else{
					orderId = request.getParameter("orderId1");
				}
	            String pictureNames = request.getParameter("pictureNames");
	            String remark = "";
				if(!(request.getParameter("remark") == null || "".equals(request.getParameter("remark")))){
			        	remark = request.getParameter("remark");
			    }
			   productionPhotos = new ArrayList<ProductionPhotoTimeline>();				 
				 if(!(pictureNames == null || "".equals(pictureNames))){
				        
						String[] split = pictureNames.split(",");
						for (int i = 0; i < split.length; i++) {						
						ProductionPhotoTimeline p = new ProductionPhotoTimeline(); 
						String photoName = split[i];
						String extension = FilenameUtils.getExtension(photoName);
	 		         	//根据文件后缀判断类型
						//如何是文档类型只能有一个
						//图片类型可能为多个
	 				    if(DocumentGenEnum.getGen(extension) != null){
	 				    	
		 				    	ProductionPhotoTimeline productionPhotoTimeline = productionPhotoTimelineService.queryByOrderAndStatus(orderId,DateFormat.currentDate());
		 				    	if(productionPhotoTimeline == null || "".equals(productionPhotoTimeline)){
		 				    		p.setUploadDate(DateFormat.currentDate());
									p.setRemarks(remark);
									p.setPhotoName("");
									p.setOrderId(orderId);
			 				    	p.setDocumentPath(UploadAndDownloadPathUtil.getMilestonePath() + orderId + File.separator + photoName);
			 				    	p.setIsDocument(1);
			 				    	p.setInputSales(WebCookie.getUserName(request));
			 				    	productionPhotos.add(p);
		 				    	}else{
		 				    		productionPhotoTimeline.setUploadDate(DateFormat.currentDate());
		 				    		productionPhotoTimeline.setRemarks(remark);
		 				    		productionPhotoTimeline.setDocumentPath(UploadAndDownloadPathUtil.getMilestonePath() + orderId + File.separator + photoName);
		 				    		productionPhotoTimeline.setIsDocument(1);
		 				    		productionPhotoTimeline.setInputSales(WebCookie.getUserName(request));
		 				    		productionPhotoTimelineService.updateDocumentPath(productionPhotoTimeline);
		 				    	}
	 				    	
	 				    }else if(ImgGenEnum.getGen(extension) != null){
	 						String photoNameCompress = OperationFileUtil.changeFilenameZip(photoName);
							p.setPhotoPath(UploadAndDownloadPathUtil.getStaticMilestonePath() + orderId + "/" + photoName);
							p.setUploadDate(DateFormat.currentDate());
							p.setRemarks(remark);
							p.setPhotoName("");
							p.setOrderId(orderId);
							p.setIsDocument(0);
							p.setPhotoPathCompress(UploadAndDownloadPathUtil.getStaticMilestonePath() + orderId + "/" + photoNameCompress);
							p.setInputSales(WebCookie.getUserName(request));
							productionPhotos.add(p);
	 				    }		
					}       
				 }	
		    map = productionPhotoTimelineService.insert(orderId, productionPhotos);
		    return new JsonResult<Map<Object,Object>>(0,"保存成功",map);
			} catch (Exception e) {
				logger.error("======ClientOrderController  saveProductionPhoto======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<Map<Object,Object>>(1,"保存失败");
			}  
			   
     }
     
     
     
     
     /**
      * 根据订单和上传日期查询图片
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
     @ResponseBody
     @RequestMapping("/queryPhotoByUploadDate.do")
     public List<ProductionPhotoTimeline> queryPhotoByUploadDate(HttpServletRequest request, HttpServletResponse response) throws Exception{   
  	   List<ProductionPhotoTimeline> productionPhotoTimelines = null;
		try {
			   if(request.getParameter("uploadDate") == null || "".equals(request.getParameter("uploadDate"))){
				   throw new RuntimeException("upload date can not empty!");
			   }
			   if(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))){
				   throw new RuntimeException("orderId date can not empty!");
			   }
			   String uploadDate = request.getParameter("uploadDate");
			   String orderId = request.getParameter("orderId");
			   productionPhotoTimelines = productionPhotoTimelineService.queryByUploadDate(orderId, uploadDate);
			   
		} catch (NumberFormatException e) {
			logger.error("======ClientOrderController  queryPhotoByMilestoneId======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
  	   
  	   return productionPhotoTimelines;
     }
     
     
     
     /**
      * 根据订单号查询weekly report
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
     @ResponseBody
     @RequestMapping("/queryPhotoByOrderId.do")
     public JsonResult<Map<Object,Object>> queryPhotoByOrderId(HttpServletRequest request, HttpServletResponse response){   
  	   List<ProductionPhotoTimeline> productionPhotoTimelines = null;
		try {
			   if(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))){
				   throw new RuntimeException("orderId can not empty!");
			   }
			   String orderId = request.getParameter("orderId");
			   productionPhotoTimelines = productionPhotoTimelineService.queryByOrderIdGroupByDate(orderId);
			   
			    Map<Object,Object> map = new HashMap<Object, Object>();
		        List<List<ProductionPhotoTimeline>> list2 = new ArrayList<List<ProductionPhotoTimeline>>();
		        for (ProductionPhotoTimeline productionPhotoTimeline : productionPhotoTimelines) {
		        	if(productionPhotoTimeline.getIsDocument() == 0){
			        	List<ProductionPhotoTimeline> productionPhoto = productionPhotoTimelineService.queryByUploadDate(orderId, productionPhotoTimeline.getUploadDate());
			        	if(productionPhoto != null && productionPhoto.size() !=0){
			            	list2.add(productionPhoto);	
			        	}
		        	}else if(productionPhotoTimeline.getIsDocument() == 1){
			        	List<ProductionPhotoTimeline> document = productionPhotoTimelineService.queryDocumentByUploadDate(orderId, productionPhotoTimeline.getUploadDate());
			        	if(document != null && document.size() !=0){
			        		list2.add(document);	
			        	}
		        	}

				}

		        map.put("productionPhotoes", productionPhotoTimelines);       
		        map.put("productionPhotoTimelines", list2);       
			   
			   return new JsonResult<Map<Object,Object>>(map);			   
		} catch (NumberFormatException e) {
			logger.error("======ClientOrderController  queryPhotoByMilestoneId======="+e.getMessage());
			e.printStackTrace();
			return new JsonResult<Map<Object,Object>>(1,"查询失败");
		}
  	   
     }
     
     
     
     /**
      * 批量更新weekly report 备注
      * @param request
      * @param response
      * @return
      */
     @ResponseBody
     @RequestMapping("/updateWeeklyReportRemark.do")
     public JsonResult<String> updateWeeklyReportRemark(HttpServletRequest request, HttpServletResponse response){ 
    	 
    	   try {
		   	if(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))){
				   throw new RuntimeException("orderId can not empty!");
			   }
			   String orderId = request.getParameter("orderId");
			   String uploadDate = request.getParameter("uploadDate");
			   String remark = request.getParameter("remark");		   
			   List<ProductionPhotoTimeline> productionPhotos = productionPhotoTimelineService.queryByUploadDate(orderId, uploadDate);
			   for (ProductionPhotoTimeline productionPhotoTimeline : productionPhotos) {
				   productionPhotoTimeline.setRemarks(remark);
			   }
			   productionPhotoTimelineService.updateRemarkBatch(productionPhotos);
			   return new JsonResult<String>(0,"更新成功");
		} catch (Exception e) {
			logger.error("======ClientOrderController  updateWeeklyReportRemark======="+e.getMessage());
			e.printStackTrace();
			   return new JsonResult<String>(1,"更新失败");
		}
		   
    	 
     }
     
     
     
     
     
     /**
      * 根据id删除图片
      * @param request
      * @param response
      * @return
      */
     @ResponseBody
     @RequestMapping("/deleteProductionPhoto.do")
     public JsonResult<List<ProductionPhotoTimeline>> deleteProductionPhoto(HttpServletRequest request, HttpServletResponse response){
    	 try {
			Integer id = null;
			 if(request.getParameter("id") == null || "".equals(request.getParameter("id"))){
					throw new RuntimeException("未获取到id");
			 }else{
				 id= Integer.parseInt(request.getParameter("id"));
			 }
		     if(request.getParameter("uploadDate") == null || "".equals(request.getParameter("uploadDate"))){
				   throw new RuntimeException("upload date can not empty!");
			   }
			 if(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId"))){
				   throw new RuntimeException("orderId date can not empty!");
			   }
			   String uploadDate = request.getParameter("uploadDate");
			   String orderId = request.getParameter("orderId");
			 
			 
			 List<ProductionPhotoTimeline> list = productionPhotoTimelineService.queryByUploadDate(orderId, uploadDate, id);
			 return new JsonResult<List<ProductionPhotoTimeline>>(0,"success",list); 
		} catch (NumberFormatException e) {
			e.printStackTrace();
			logger.error("======ClientOrderController  deleteProductionPhoto======="+e.getMessage());
			return new JsonResult<List<ProductionPhotoTimeline>>(1,e.getMessage()); 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("======ClientOrderController  deleteProductionPhoto======="+e.getMessage());
			return new JsonResult<List<ProductionPhotoTimeline>>(1,e.getMessage()); 
		}     	 
   	  	    	 
     }
     
     
     
     /**
      * 处理上传po、invoice、qc、shipping文件    
      * client_order数据库更新
      * 根据id&orderId 生成订单文件夹
      * 将文件夹打包
      * @param request
      * @param response
      * @return
     * @throws Exception 
      */
         @RequestMapping("/updateClientOrderById.do")
         public String updateClientOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception{
        	 
        		try {
        			Milestone milestone2 = new Milestone();
        			
					Integer id = Integer.parseInt(request.getParameter("clientOrderId"));   		 
					ClientOrder clientOrder = clientOrderService.queryById(id); 		 
					String orderId = clientOrder.getOrderId();
					
					String poNumber = null;
					if(!(request.getParameter("poNumber") == null || "".equals(request.getParameter("poNumber")))){
						poNumber = request.getParameter("poNumber");
						clientOrder.setPoNumber(poNumber);
					}
					
					if(!(request.getParameter("createTime") == null || "".equals(request.getParameter("createTime")))){					    		 
						clientOrder.setCreateTime(request.getParameter("createTime"));
					}  
					if(!(request.getParameter("outputTime") == null || "".equals(request.getParameter("outputTime")))){        	    
						clientOrder.setOutputTime(request.getParameter("outputTime"));					    
					} 
					if(!(request.getParameter("deliveryTime") == null || "".equals(request.getParameter("deliveryTime")))){
						clientOrder.setDeliveryTime(request.getParameter("deliveryTime"));
						milestone2.setMilestoneDate(DateFormat.formatDate1(request.getParameter("deliveryTime")));
						milestone2.setCreateTime(DateFormat.format());
						milestone2.setDelayStatus(0);
						milestone2.setProductionPhotoStatus(0);
						milestone2.setMilestoneName("Finished");
						milestone2.setExpectedOrActually(0);
						milestone2.setOrderId(orderId);
						
					}
					if(!(request.getParameter("arrivalDate") == null || "".equals(request.getParameter("arrivalDate")))){
						clientOrder.setArrivalDate(request.getParameter("arrivalDate"));
					}
					if(!(request.getParameter("paymentReceived") == null || "".equals(request.getParameter("paymentReceived")))){
						clientOrder.setPaymentReceived(request.getParameter("paymentReceived"));
					}
					if(!(request.getParameter("projectName") == null || "".equals(request.getParameter("projectName")))){
						clientOrder.setProjectName(request.getParameter("projectName"));
					}
					if(!(request.getParameter("orderStatus") == null || "".equals(request.getParameter("orderStatus")))){
						clientOrder.setOrderStatus(Integer.parseInt(request.getParameter("orderStatus")));    		   
					}  
					if(!(request.getParameter("orderTypeId") == null || "".equals(request.getParameter("orderTypeId")))){
						clientOrder.setOrderTypeId(Integer.parseInt(request.getParameter("orderTypeId")));
					}
					if(!(request.getParameter("orderRequest") == null || "".equals(request.getParameter("orderRequest")))){
						clientOrder.setOrderRequest(request.getParameter("orderRequest"));
					}
					clientOrder.setSalesName(WebCookie.getUserName(request));  
					clientOrder.setUpdateTime(DateFormat.format());
      
     	     clientOrderService.updateClientOrder(clientOrder,milestone2);
     	 
		} catch (Exception e) {
			logger.error("======ClientOrderController  updateClientOrderById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception();
		}
        	 
        	 
        	 return "index-background.jsp";        
    	 } 
     
     
         
         /**
          * 首页选择更新项目类型
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/updateOrderTypeById.do")
         public JsonResult<String> updateOrderTypeById(HttpServletRequest request, HttpServletResponse response){
        	 
        	    try {
					Integer id = Integer.parseInt(request.getParameter("clientOrderId"));   		 
					ClientOrder clientOrder = clientOrderService.queryById(id); 	
					if(!(StringUtils.isBlank(request.getParameter("orderTypeId")))){
						Integer orderTypeId = Integer.parseInt(request.getParameter("orderTypeId"));
						clientOrder.setOrderTypeId(orderTypeId);
						clientOrderService.updateClientOrder(clientOrder);
					}					
					return new JsonResult<String>(0,"success");
				} catch (NumberFormatException e) {
					logger.error("======ClientOrderController  updateOrderTypeById======="+e.getMessage());
					e.printStackTrace();
					return new JsonResult<String>(0,"更新失败");
				}

				
				
         }
       
         /**
          * 根据id删除QcReport
          * @param request
          * @param response
          * @return
          * @throws IOException
          */
         @ResponseBody
         @RequestMapping("/deleteQcReport.do")
         public JsonResult<String> deleteQcReport(HttpServletRequest request, HttpServletResponse response){
        	try {
				Integer id = null;
				if(!(request.getParameter("qcReportId") == null || "".equals(request.getParameter("qcReportId")))){
					id = Integer.parseInt(request.getParameter("qcReportId")); 
				} 
				clientOrderQcReportService.deleteById(id);	
				return new JsonResult<String>(0,"删除成功");
			} catch (Exception e) {
				logger.error("======ClientOrderController  deleteQcReport======="+e.getMessage());
				e.printStackTrace();    		
	            return new JsonResult<String>(1,"删除失败");
			}

         }
     
         /**
          * 根据id删除PoReport
          * @param request
          * @param response
          * @return
          * @throws IOException
          */
         @ResponseBody
         @RequestMapping("/deletePoReport.do")
         public JsonResult<String> deletePoReport(HttpServletRequest request, HttpServletResponse response){
        	 try {
        		 Integer id = null;
        		 if(!(request.getParameter("poId") == null || "".equals(request.getParameter("poId")))){
        			 id = Integer.parseInt(request.getParameter("poId")); 
        		 } 		 
        		 clientOrderPoService.deleteById(id);
        		 return new JsonResult<String>(0,"删除成功");
        	 } catch (Exception e) {
        		 logger.error("======ClientOrderController  deleteQcReport======="+e.getMessage());
        		 e.printStackTrace();
        		 return new JsonResult<String>(1,"删除失败");
        	 }
         }
         
         /**
          * 根据id删除ShippingDoc
          * @param request
          * @param response
          * @return
          * @throws IOException
          */
         @ResponseBody
         @RequestMapping("/deleteShippingDoc.do")
         public JsonResult<String> deleteShippingDoc(HttpServletRequest request, HttpServletResponse response){
        	 try {
        		 Integer id = null;
        		 if(!(request.getParameter("shippingDocId") == null || "".equals(request.getParameter("shippingDocId")))){
        			 id = Integer.parseInt(request.getParameter("shippingDocId")); 
        		 } 		 
        		 clientOrderShippingDocService.deleteById(id);
        		 return new JsonResult<String>(0,"删除成功");
        		 
        	 } catch (Exception e) {
        		 logger.error("======ClientOrderController  deleteQcReport======="+e.getMessage());
        		 e.printStackTrace();
        		 return new JsonResult<String>(1,"删除失败");
        	 }
         }
         
         
         
         
         
         /**
          * 根据里程碑名查询是否存在
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/queryByMilestoneName.do")
         public JsonResult<String> queryByMilestoneName(HttpServletRequest request, HttpServletResponse response){
        	 
        	     try {
					 String orderId = null;
					 String milestoneName = "";
					 if(!(request.getParameter("orderId") == null || "".equals(request.getParameter("orderId")))){
						 orderId = request.getParameter("orderId"); 
					 }else{
						 throw new RuntimeException("未获取到订单号");
					 }
					 if(!(request.getParameter("milestoneName") == null || "".equals(request.getParameter("milestoneName")))){
						 milestoneName = request.getParameter("milestoneName");
					 }
					 Milestone milestone = milestoneService.queryByName(orderId, milestoneName, EXPECTED); 
					 if(milestone == null || "".equals(milestone)){
						 return new JsonResult<String>(0,"里程碑未重复");
					 }else{
						 return new JsonResult<String>(0,"里程碑存在",milestone.getId().toString());
					 }
				} catch (Exception e) {
					logger.error("======ClientOrderController  queryByMilestoneName======="+e);
					e.printStackTrace();
					return new JsonResult<String>(1,"获取失败");
				}
        	 
         }
         
         
         /**
          * 查询po报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/queryPo.do")
         public JsonResult<List<ClientOrderPo>> queryPo(HttpServletRequest request, HttpServletResponse response){
        	 
        	 try {
				if(StringUtils.isNotBlank(request.getParameter("orderId"))){
					 String orderId = request.getParameter("orderId");
					 List<ClientOrderPo> pos = clientOrderPoService.queryByClientOrderId(orderId);
					 for (ClientOrderPo po : pos) {
						 po.setPoPath(FilenameUtils.getName(po.getPoPath()));
					 }
					 return new JsonResult<List<ClientOrderPo>>(pos);
				 }else{
					 return new JsonResult<List<ClientOrderPo>>(1,"未获取到订单号"); 
				 }
			} catch (Exception e) {
				logger.error("======ClientOrderController  queryPo======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<List<ClientOrderPo>>(1,"查询失败");
			}
        	 
        	 
         }
         
         /**
          * 查询qc报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/queryQcReports.do")
         public JsonResult<List<ClientOrderQcReport>> queryQcReports(HttpServletRequest request, HttpServletResponse response){
        	 
        	 try {
				if(StringUtils.isNotBlank(request.getParameter("orderId"))){
					 String orderId = request.getParameter("orderId");
					 List<ClientOrderQcReport> qcReports = clientOrderQcReportService.queryByClientOrderId(orderId);
					 for (ClientOrderQcReport clientOrderQcReport : qcReports) {
						 clientOrderQcReport.setQcReportPath(FilenameUtils.getName(clientOrderQcReport.getQcReportPath()));
					 }
					 return new JsonResult<List<ClientOrderQcReport>>(qcReports);
				 }else{
					 return new JsonResult<List<ClientOrderQcReport>>(1,"未获取到订单号"); 
				 }
			} catch (Exception e) {
				logger.error("======ClientOrderController  queryQcReports======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<List<ClientOrderQcReport>>(1,"查询失败");
			}
        	 
        	 
         }
         
         /**
          * 查询shipping报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/queryShippingReports.do")
         public JsonResult<List<ClientOrderShippingDoc>> queryShippingReports(HttpServletRequest request, HttpServletResponse response){
        	 
        	 try {
				if(StringUtils.isNotBlank(request.getParameter("orderId"))){
					 String orderId = request.getParameter("orderId");
					 List<ClientOrderShippingDoc> shippingDocs = clientOrderShippingDocService.queryByClientOrderId(orderId);
					 
						 for (ClientOrderShippingDoc shippingDoc : shippingDocs) {
			            	 if(StringUtils.isNotBlank(shippingDoc.getBLPath())){
			            		 shippingDoc.setBLPath(new File(shippingDoc.getBLPath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getInvoicePath())){
			            		 shippingDoc.setInvoicePath(new File(shippingDoc.getInvoicePath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getPackingListPath())){
			            		 shippingDoc.setPackingListPath(new File(shippingDoc.getPackingListPath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getFormAPath())){
			            		 shippingDoc.setFormAPath(new File(shippingDoc.getFormAPath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getPackagingPath())){
			            		 shippingDoc.setPackagingPath(new File(shippingDoc.getPackagingPath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getBLCopyPath())){
			            		 shippingDoc.setBLCopyPath(new File(shippingDoc.getBLCopyPath().trim()).getName());
			            	 }
			            	 if(StringUtils.isNotBlank(shippingDoc.getOtherPath())){
			            		 shippingDoc.setOtherPath(new File(shippingDoc.getOtherPath().trim()).getName());
			            	 }
			     
						 } 
					 return new JsonResult<List<ClientOrderShippingDoc>>(shippingDocs);
				 }else{
					 return new JsonResult<List<ClientOrderShippingDoc>>(1,"未获取到订单号"); 
				 }
			} catch (Exception e) {
				logger.error("======ClientOrderController  queryShippingReports======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<List<ClientOrderShippingDoc>>(1,"查询失败");
			}
        	 
        	 
         }

         /**
          * 查询shipping报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/queryShippingById.do")
         public JsonResult<ClientOrderShippingDoc> queryShippingById(HttpServletRequest request, HttpServletResponse response){
        	 
        	 try {
				if(StringUtils.isNotBlank(request.getParameter("id"))){
					 Integer id = Integer.parseInt(request.getParameter("id"));
					 ClientOrderShippingDoc shippingDoc = clientOrderShippingDocService.queryById(id);
					 if(StringUtils.isNotBlank(shippingDoc.getBLCopyPath())){
						 shippingDoc.setBLCopyPath(new File(shippingDoc.getBLCopyPath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getBLPath())){
						 shippingDoc.setBLPath(new File(shippingDoc.getBLPath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getFormAPath())){
						 shippingDoc.setFormAPath(new File(shippingDoc.getFormAPath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getInvoicePath())){
						 shippingDoc.setInvoicePath(new File(shippingDoc.getInvoicePath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getPackingListPath())){
						 shippingDoc.setPackingListPath(new File(shippingDoc.getPackingListPath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getPackagingPath())){
						 shippingDoc.setPackagingPath(new File(shippingDoc.getPackagingPath().trim()).getName()); 	 
					 }
					 if(StringUtils.isNotBlank(shippingDoc.getOtherPath())){
						 shippingDoc.setOtherPath(new File(shippingDoc.getOtherPath().trim()).getName()); 	 
					 }

					 return new JsonResult<ClientOrderShippingDoc>(shippingDoc);
				 }else{
					 return new JsonResult<ClientOrderShippingDoc>(1,"未获取到shipping"); 
				 }
			} catch (Exception e) {
				logger.error("======ClientOrderController  queryShippingReports======="+e.getMessage());
				e.printStackTrace();
				return new JsonResult<ClientOrderShippingDoc>(1,"查询失败");
			}
        	 
        	 
         }
         
         
         
     
         
         
         /**
          * 增加PO报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/insertPo.do")
         public JsonResult<ClientOrderPo> insertPo(HttpServletRequest request, HttpServletResponse response){
        	 
        		try {
                    String orderId = request.getParameter("orderId"); 	
                    ClientOrderPo po = new ClientOrderPo();
    				String path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + orderId + File.separator;
    		  	    if(!(request.getParameter("poName") == null || "".equals(request.getParameter("poName")))){
    				 String poName = request.getParameter("poName");
    				 File file = new File(path);
    				 if  (!file.exists()  && !file .isDirectory())      
    				 {         
    					 file .mkdir();     
    				 }  	 
    				  	  
    				 try {
    					 //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
    					 Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
    					 po.setPoPath (multiFileUpload.get(poName));
    					 po.setUploadDate(DateFormat.currentDate());
    					 po.setOldOrNewPo("new");
    					 po.setOrderId(orderId);
    					 
    				 } catch (IllegalStateException e) {	
    					logger.error(e.getMessage());
    					 e.printStackTrace();
    				 } catch (IOException e) {			
    					logger.error(e.getMessage());
    					 e.printStackTrace();
    				 }
      	      }    	 
      	        clientOrderPoService.insert(po);
         return new JsonResult<ClientOrderPo>(po);
    	} catch (Exception e) {
    	logger.error("======ClientOrderController  insertQcReport======="+e.getMessage());
    	e.printStackTrace();
    	return new JsonResult<ClientOrderPo>(1,"保存失败");
    	}  	 
    	    	         
     } 
         
         
         
         
         
         
         
         
         
         /**
          * 增加质量报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/insertQcReport.do")
         public JsonResult<ClientOrderQcReport> insertQcReport(HttpServletRequest request, HttpServletResponse response){
        	 
        		try {
                    String orderId = request.getParameter("orderId"); 	
                    ClientOrderQcReport qcReport = new ClientOrderQcReport();
//                    List<ClientOrderQcReport> list = new ArrayList<ClientOrderQcReport>();
    				String path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + orderId + File.separator;
    		  	    if(!(request.getParameter("qcName") == null || "".equals(request.getParameter("qcName")))){
    				 String qcName = request.getParameter("qcName");
    				 File file = new File(path);
    				 if  (!file.exists()  && !file .isDirectory())      
    				 {         
    					 file .mkdir();     
    				 }  	 
    				  	  
    				 try {
    					 //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
    					 Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);
    					 qcReport.setQcReportPath (multiFileUpload.get(qcName));
    					 qcReport.setUploadDate(DateFormat.currentDate());
    					 qcReport.setOldOrNewQc("new");
    					 qcReport.setOrderId(orderId);
//    					 list.add(qcReport);
    					 
    				 } catch (IllegalStateException e) {	
    					logger.error(e.getMessage());
    					 e.printStackTrace();
    				 } catch (IOException e) {			
    					logger.error(e.getMessage());
    					 e.printStackTrace();
    				 }
      	      }    	 
      	        clientOrderQcReportService.insert(qcReport);
      	        System.out.println(qcReport);
         return new JsonResult<ClientOrderQcReport>(qcReport);
    	} catch (Exception e) {
    	logger.error("======ClientOrderController  insertQcReport======="+e.getMessage());
    	e.printStackTrace();
    	return new JsonResult<ClientOrderQcReport>(1,"保存失败");
    	}  	 
    	    	         
     } 
         
         
         /**
          * 增加shipping报告
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/insertShippingDoc.do")
         public JsonResult<ClientOrderShippingDoc> insertShippingDoc(HttpServletRequest request, HttpServletResponse response){
        	 
        		try {
                    String orderId = request.getParameter("orderId"); 	
                    ClientOrderShippingDoc shippingDoc = new ClientOrderShippingDoc();
    				shippingDoc.setOrderId(orderId);
    				if(StringUtils.isNotBlank(request.getParameter("shippingStartDate"))){
          			  String shippingStartDate = request.getParameter("shippingStartDate");
          			  shippingDoc.setShippingStartDate(shippingStartDate);            			  
          			  shippingDoc.setBLAvailableDate(DateFormat.calDate(shippingStartDate,14));
	      			  }
	      			  if(StringUtils.isNotBlank(request.getParameter("ISFDate"))){
	      				  shippingDoc.setISFDate(request.getParameter("ISFDate"));
	      			  }
      			  
	      			  if(StringUtils.isNotBlank(request.getParameter("shippingArrivalDate"))){
	      				  String shippingArrivalDate = request.getParameter("shippingArrivalDate"); 
	                        shippingDoc.setShippingArrivalDate(shippingArrivalDate);
	                        
	          			  String BLAvailableDate = shippingDoc.getBLAvailableDate();
	          			  int compare_state = DateFormat.compare_date(shippingArrivalDate,BLAvailableDate);
	          			  if(compare_state == -1){
	          				  shippingDoc.setBLAvailableDate(shippingArrivalDate);
	          			  }else{
	          				  shippingDoc.setBLAvailableDate(BLAvailableDate);
	          			  }
	      			  }
      	        clientOrderShippingDocService.insert(shippingDoc);
		         return new JsonResult<ClientOrderShippingDoc>(shippingDoc);
		    	} catch (Exception e) {
		    	logger.error("======ClientOrderController  insertShippingDoc======="+e.getMessage());
		    	e.printStackTrace();
		    	return new JsonResult<ClientOrderShippingDoc>(1,"保存失败");
		    	}  	 
    	    	         
     }   
     
         
         /**
          * 增加运输计划
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/updateShippingDoc.do")
         public JsonResult<List<ClientOrderShippingDoc>> updateShippingDoc(HttpServletRequest request, HttpServletResponse response){
        	 
        		try {
        			ClientOrderShippingDoc shippingDoc = new ClientOrderShippingDoc();
        			List<ClientOrderShippingDoc> shippingDocs = new ArrayList<ClientOrderShippingDoc>();
        			if(StringUtils.isNotBlank(request.getParameter("id"))){       			  
	        			  Integer shippingDocId = Integer.parseInt(request.getParameter("id"));
	        			  shippingDoc = clientOrderShippingDocService.queryById(shippingDocId);
	        			  if(StringUtils.isNotBlank(request.getParameter("shippingStartDate"))){
	            			  String shippingStartDate = request.getParameter("shippingStartDate");
	            			  shippingDoc.setShippingStartDate(shippingStartDate);            			  
	            			  shippingDoc.setBLAvailableDate(DateFormat.calDate(shippingStartDate,14));
	        			  }
	        			  if(StringUtils.isNotBlank(request.getParameter("ISFDate"))){
	        				  shippingDoc.setISFDate(request.getParameter("ISFDate"));
	        			  }
	        			  if(StringUtils.isNotBlank(request.getParameter("destinationPort"))){
	        				  shippingDoc.setDestinationPort(request.getParameter("destinationPort"));
	        			  }
	        			  
	        			  if(StringUtils.isNotBlank(request.getParameter("shippingArrivalDate"))){
	        				  String shippingArrivalDate = request.getParameter("shippingArrivalDate"); 
	                          shippingDoc.setShippingArrivalDate(shippingArrivalDate);
	                          
	            			  String BLAvailableDate = shippingDoc.getBLAvailableDate();
	            			  int compare_state = DateFormat.compare_date(shippingArrivalDate,BLAvailableDate);
	            			  if(compare_state == -1){
	            				  shippingDoc.setBLAvailableDate(shippingArrivalDate);
	            			  }else{
	            				  shippingDoc.setBLAvailableDate(BLAvailableDate);
	            			  }
	        			  }
	        			  clientOrderShippingDocService.updateShippingDoc(shippingDoc); 
	        			  shippingDocs = clientOrderShippingDocService.queryByClientOrderId(shippingDoc.getOrderId());
        			      return  new JsonResult<List<ClientOrderShippingDoc>>(shippingDocs);
        		   }else{
                        String orderId = request.getParameter("orderId"); 	
	       				shippingDoc.setOrderId(orderId);
	       				if(StringUtils.isNotBlank(request.getParameter("shippingStartDate"))){
	             			  String shippingStartDate = request.getParameter("shippingStartDate");
	             			  shippingDoc.setShippingStartDate(shippingStartDate);            			  
	             			  shippingDoc.setBLAvailableDate(DateFormat.calDate(shippingStartDate,14));
	   	      			  }
	   	      			  if(StringUtils.isNotBlank(request.getParameter("ISFDate"))){
	   	      				  shippingDoc.setISFDate(request.getParameter("ISFDate"));
	   	      			  }
		   	     		  if(StringUtils.isNotBlank(request.getParameter("destinationPort"))){
	        				  shippingDoc.setDestinationPort(request.getParameter("destinationPort"));
	        			  } 
	   	      			  if(StringUtils.isNotBlank(request.getParameter("shippingArrivalDate"))){
	   	      				  String shippingArrivalDate = request.getParameter("shippingArrivalDate"); 
	   	                        shippingDoc.setShippingArrivalDate(shippingArrivalDate);
	   	                        
	   	          			  String BLAvailableDate = shippingDoc.getBLAvailableDate();
	   	          			  int compare_state = DateFormat.compare_date(shippingArrivalDate,BLAvailableDate);
	   	          			  if(compare_state == -1){
	   	          				  shippingDoc.setBLAvailableDate(shippingArrivalDate);
	   	          			  }else{
	   	          				  shippingDoc.setBLAvailableDate(BLAvailableDate);
	   	          			  }
	   	      			  }
         	           clientOrderShippingDocService.insert(shippingDoc);
         	           shippingDocs = clientOrderShippingDocService.queryByClientOrderId(orderId);
         	           return  new JsonResult<List<ClientOrderShippingDoc>>(shippingDocs);
        		   }  
    		  	    
		    	} catch (Exception e) {
		    	   logger.error("======ClientOrderController  updateShippingDoc======="+e.getMessage());
		    	   e.printStackTrace();
		    	   return  new JsonResult<List<ClientOrderShippingDoc>>(1,"更新失败");  
		    	}  	 
    	    	         
     }   
         
         
         /**
          * 增加运输计划
          * @param request
          * @param response
          * @return
          */
         @ResponseBody
         @RequestMapping("/updateShippingFile.do")
         public JsonResult<ClientOrderShippingDoc> updateShippingFile(HttpServletRequest request, HttpServletResponse response){
        	 
        		try {
        			if(StringUtils.isNotBlank(request.getParameter("shippingId"))){       			  
	        			  Integer shippingDocId = Integer.parseInt(request.getParameter("shippingId"));
	        			  ClientOrderShippingDoc shippingDoc = clientOrderShippingDocService.queryById(shippingDocId);
	        			  String orderId = request.getParameter("orderId"); 		        			  
	        			  String path = UploadAndDownloadPathUtil.getClientOrderUpLoadAndDownLoadPath() + orderId + File.separator;
	      		  	      if(!(request.getParameter("shippingName") == null || "".equals(request.getParameter("shippingName")))){
	      				  String shippingName = request.getParameter("shippingName");
	      				  File file = new File(path);
	      				  if  (!file.exists()  && !file .isDirectory())      
	      				  {         
	      					 file .mkdir();     
	      				  }  	 
	      				  	  
	      				  //调用保存文件的帮助类进行保存文件(文件上传，form表单提交)
	      				  Map<String, String> multiFileUpload = OperationFileUtil.multiFileUpload(request, path);	 
	      				  String filePath = multiFileUpload.get(shippingName);
	    				  String uploadDate = DateFormat.currentDate();	        			  
	        			  String fileType = request.getParameter("fileType");	        			  
	        			  switch (fileType) {
										case "1":	
											shippingDoc.setBLCopyPath(filePath);
											shippingDoc.setBLCopyUploadDate(uploadDate);
											shippingDoc.setOldOrNewBLCopy("new");
											break;				
										case "2":	
											shippingDoc.setBLPath(filePath);
											shippingDoc.setBLUploadDate(uploadDate);
											shippingDoc.setOldOrNewBL("new");
											break;				
										case "3":	
											shippingDoc.setInvoicePath(filePath);
											shippingDoc.setInvoiceUploadDate(uploadDate);
											shippingDoc.setOldOrNewInvoice("new");
											break;				
										case "4":	
											shippingDoc.setPackingListPath(filePath);
											shippingDoc.setPackingListUploadDate(uploadDate);
											shippingDoc.setOldOrNewPackingList("new");
											break;				
										case "5":	
											shippingDoc.setFormAPath(filePath);
											shippingDoc.setFormAUploadDate(uploadDate);
											shippingDoc.setOldOrNewFormA("new");
											break;				
										case "6":	
											shippingDoc.setPackagingPath(filePath);
											shippingDoc.setPackagingUploadDate(uploadDate);
											shippingDoc.setOldOrNewPackaging("new");
											break;				
										case "7":	
											shippingDoc.setOtherPath(filePath);
											shippingDoc.setOtherUploadDate(uploadDate);
											shippingDoc.setOldOrNewOther("new");
											break;				
										default:
											break;
										}	
	        			  clientOrderShippingDocService.updateShippingDoc(shippingDoc); 
	        			  
	        				 if(StringUtils.isNotBlank(shippingDoc.getBLCopyPath())){
	    						 shippingDoc.setBLCopyPath(new File(shippingDoc.getBLCopyPath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getBLPath())){
	    						 shippingDoc.setBLPath(new File(shippingDoc.getBLPath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getFormAPath())){
	    						 shippingDoc.setFormAPath(new File(shippingDoc.getFormAPath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getInvoicePath())){
	    						 shippingDoc.setInvoicePath(new File(shippingDoc.getInvoicePath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getPackingListPath())){
	    						 shippingDoc.setPackingListPath(new File(shippingDoc.getPackingListPath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getPackagingPath())){
	    						 shippingDoc.setPackagingPath(new File(shippingDoc.getPackagingPath().trim()).getName()); 	 
	    					 }
	    					 if(StringUtils.isNotBlank(shippingDoc.getOtherPath())){
	    						 shippingDoc.setOtherPath(new File(shippingDoc.getOtherPath().trim()).getName()); 	 
	    					 }
	      		  	      } 
        			      return  new JsonResult<ClientOrderShippingDoc>(shippingDoc);
        		   }else{
        			      return  new JsonResult<ClientOrderShippingDoc>(1,"未获取到运输表ID");  
        		   }  
	      	      		  	      
		    	} catch (Exception e) {
		    	   logger.error("======ClientOrderController  updateShippingDoc======="+e.getMessage());
		    	   e.printStackTrace();
		    	   return  new JsonResult<ClientOrderShippingDoc>(1,"更新失败");  
		    	}	 
    	    	         
     }   
         
         
         
         
     
 }
