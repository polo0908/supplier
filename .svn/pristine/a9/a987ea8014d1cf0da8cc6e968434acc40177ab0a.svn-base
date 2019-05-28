package com.cbt.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cbt.entity.BackUser;
import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderDrawings;
import com.cbt.entity.ReOrderQuery;
import com.cbt.entity.User;
import com.cbt.service.BackUserService;
import com.cbt.service.ReOrderDrawingsService;
import com.cbt.service.ReOrderService;
import com.cbt.service.UserService;
import com.cbt.util.Base64Encode;
import com.cbt.util.DateFormat;
import com.cbt.util.SplitPage;
import com.cbt.util.SplitPage3;
import com.cbt.util.WebCookie;


@Controller 
public class ReOrderController {
  @Resource
  private ReOrderService reOrderService;
  @Resource
  private UserService userService;
  @Resource
  private ReOrderDrawingsService reOrderDrawingsService;
  @Resource
  private BackUserService backUserService;
  
  public static Logger logger = Logger.getLogger(ReOrderController.class);
  /**
   * 获取 reorder表数据，返回前台显示
   * @param request
   * @param response
   * @return
 * @throws Exception 
   */
  @RequestMapping("/queryAllReOrder.do")
  public String queryAllRfqInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
	   
	     try {
			ReOrderQuery reOrderQuery = getReOrder(request);
			 Integer pageSize = reOrderQuery.getPageSize();
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

			List<ReOrderQuery> list = null;
			int total = 0;
			
			//检查用户权限如果有管理员权限，查询所有再订购数据
			if(permission == 1){
				list = reOrderService.queryAllReOrderAdmin(reOrderQuery);
				total = reOrderService.totalAmountAdmin(reOrderQuery);
			}else{
				list = reOrderService.queryAllReOrder(reOrderQuery);
				total = reOrderService.totalAmount(reOrderQuery);
			}
			
			//显示图纸名
			for (ReOrder reOrderQuery2 : list) {
				String drawingNames = "";
				List<ReOrderDrawings> reOrderDrawings = reOrderDrawingsService.queryReOrderDrawings(reOrderQuery2.getId());
				if(!(reOrderDrawings == null || reOrderDrawings.size() == 0)){
					   int tl = reOrderDrawings.size();
					   if(tl != 0){
						   for (int i = 0; i < tl; i++) {											
					            /*
					             *去除时间节点，获取图纸名 
					             */
							     String dt = reOrderDrawings.get(i).getDrawingsName();
							     if(!(dt == null || "".equals(dt.trim()))){
							    	   String[] dts = dt.split("&");
									   String drawingName = dts[0];			
									   //获取图纸名显示、只显示两个，超过后以...显示
			                           if(i <= 1){
			                        	   drawingNames += drawingName+",";	  
			                           }else{
			                        	   drawingNames = drawingNames.substring(0,drawingNames.length()-1);
			                        	   drawingNames = drawingNames + "...";
			                           }
					  
							     }
							     
						    }
						   if(drawingNames.endsWith(",")){
								 drawingNames = drawingNames.substring(0,drawingNames.length()-1);
						   }	 
					   }
				}
				reOrderQuery2.setDrawingNames(drawingNames);
			}
			
			
			
			

			SplitPage3.buildPager(request, total, pageSize, page);
			request.setAttribute("reOrder", list);
		} catch (Exception e) {
			logger.error("======ReOrderController  queryAllRfqInfo======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

		
	   return "reOrder_order.jsp";
  }
  
  /**
   * 获取前台输入数据
   * @param request
   * @return
   */
    private ReOrderQuery getReOrder(HttpServletRequest request){
    	
    	//根据rfqInfoQuery对象查询
    	 ReOrderQuery reOrderQuery = new ReOrderQuery();
	     String str1 = request.getParameter("page");
	     String str2 = request.getParameter("pageSize");
	     String factoryId = WebCookie.getFactoryId(request);
		 String salesId = WebCookie.getUserId(request); 
		 
	    if(!(request.getParameter("userName") ==null || "".equals(request.getParameter("userName")))){
	    	reOrderQuery.setUserName(request.getParameter("userName"));
	    }
		if(!(request.getParameter("beginDate") ==null || "".equals(request.getParameter("beginDate")))){
			reOrderQuery.setBeginDate(request.getParameter("beginDate"));
		}
		if(!(request.getParameter("endDate") ==null || "".equals(request.getParameter("endDate")))){
			reOrderQuery.setEndDate(request.getParameter("endDate"));
		}		   
		reOrderQuery.setFactoryId(factoryId);
		
	    int page = 1;
		if(str1 != null) {
			page = Integer.parseInt(str1);
		}
		int pageSize = 10;		
		if(str2 != null) {
			pageSize = Integer.parseInt(str2);
		}
		int start = (page-1) * pageSize;
		reOrderQuery.setSalesId(salesId);
		reOrderQuery.setStart(start);
		reOrderQuery.setPageSize(pageSize);
		reOrderQuery.setFactoryId(factoryId);
    	
    	return reOrderQuery;
    	
    }
    
    /**
     * 更新reOrder数据
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("/updateReOrderById.do")
    public String updateReOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try {
			//获取session用户
			String followUp = WebCookie.getUserName(request);   	 
			Integer id = Integer.parseInt(request.getParameter("id"));
			ReOrder reOrder = reOrderService.queryById(id);
			if(!(request.getParameter("description") == null || "".equals(request.getParameter("description")))){	    	
				reOrder.setFollowComment(request.getParameter("description"));
			}
			long time = System.currentTimeMillis();
			Date date = new Date(time);    	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String followTime = sdf.format(date);
			reOrder.setFollowTime(followTime);
			
			reOrder.setReOrderState(2);
			reOrder.setFollowUp(followUp);
			reOrderService.updateById(reOrder);
		} catch (Exception e) {
			logger.error("======ReOrderController  updateReOrderById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}	    
	    
    	return "reOrder_order.jsp";
    	
    }
    
    //关闭订单(设置reOrderState = "3")
    @RequestMapping("/closeReOrderById.do")
    public String closeReOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	//获取session用户
    	try {
			String followUp = WebCookie.getUserName(request);   	 
			Integer id = Integer.parseInt(request.getParameter("id"));
			ReOrder reOrder = reOrderService.queryById(id);   	
			
			reOrder.setFollowTime(DateFormat.format());
			reOrder.setFollowUp(followUp);
			reOrder.setReOrderState(3);
			reOrderService.updateById(reOrder);
		} catch (Exception e) {
			logger.error("======ReOrderController  closeReOrderById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}	    
    	
    	return "reOrder_order.jsp";
    	
    }
    
    /**
     * 根据id查询reOrder订单
     * id关联reOrder-drawings reOrderId
     * @param request
     * @param response
     * @return
     * @throws Exception 
     */
    @RequestMapping("/queryReOrderById.do")
    public String queryReOrderById(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	try {
			Integer id = Integer.parseInt(Base64Encode.getFromBase64(request.getParameter("id")));
			ReOrder reOrder = reOrderService.queryById(id);

			List<ReOrderDrawings> list = reOrderDrawingsService.queryReOrderDrawings(id);
			User user = userService.queryByUserId(reOrder.getUserid());
			request.setAttribute("user", user);
			request.setAttribute("reOrder", reOrder);
			request.setAttribute("list", list);
		} catch (Exception e) {
			logger.error("======ReOrderController  queryReOrderById======="+e.getMessage());
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		
    	
    	return "newOrder_reOrder2.jsp";
    }
	
}
