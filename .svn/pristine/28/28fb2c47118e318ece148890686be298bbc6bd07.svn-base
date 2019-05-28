package com.cbt.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.ClientOrderQuery;
import com.cbt.entity.ClientOrderType;
import com.cbt.entity.FactoryUserRelation;
import com.cbt.entity.Milestone;
import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;
import com.cbt.entity.UserFactoryRelation;

public interface ClientOrderService {
	
	
	/**
	 * 查询订单总数
	 * @return
	 */
	public int totalAmount(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ClientOrderQuery> queryListByDate(ClientOrderQuery clientOrderQuery);
	
	
	
	/**
	 * 查询全部订单
	 * 根据用户名、时间节点查询(根据客户,时间排序)
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ClientOrderQuery> queryListByDateOrderByUser(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询订单总数(管理员查询)
	 * @return
	 */
	public int totalAmountAdmin(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ClientOrderQuery> queryListByDateAdmin(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询(根据客户,时间排序)
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ClientOrderQuery> queryListByDateAdminOrderByUser(ClientOrderQuery clientOrderQuery);
	

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public ClientOrder queryById(Integer id);
	
	/**
	 * 根据订单号查询客户订单详情
	 * @param orderId
	 * @return
	 */	
	public ClientOrder queryByOrderId(String orderId);	
    
    /**
     * 单个插入对象
     * @param ClientOrder
     */
    public void insertClientOrder(ClientOrder clientOrder,Milestone milestone,String reOrderId,
			String drawingId,String clientDrawings,HttpServletRequest request) throws Exception;
    /**
     * 批量插入对象
     * @param list
     */
    public void insertClientOrders(List<Object> list);

    
	 /**
	  * 更新clientOrder po、invoice、qc、shipping数据
	  * @param clientOrder
	  */
    public void updateClientOrder(ClientOrder clientOrder);
    
    
    
    /**
     * 更新clientOrder数据（主要用于更新出货时间、海运提单时间、申报时间）
     * @param clientOrder
     */
    public void updateClientOrder(ClientOrder clientOrder,Milestone milestone3)throws Exception;
    
    
    
    
    /**
     * 单个插入对象
     * @param ClientOrder
     */
    public void insertClientOrder(ClientOrder clientOrder);
    
    
    /**
     * 插入订单时导入客户销售关联关系（主要用于同步erp订单）
     * @author polo
     * 2017年5月27日
     *
     */
    public void insertClientOrder(ClientOrder clientOrder,String orderId,List<SaleCustomer> list,FactoryUserRelation factoryUserRelation,UserFactoryRelation userFactoryRelation,List<SaleOrder> list3)throws Exception;
    
    
    
    /**
     * 插入订单时导入客户销售关联关系（主要用于导入erp订单）
     * @author polo
     * 2017年5月27日
     *
     */
    public void insertClientOrder(List<Object> clientOrders,List<SaleCustomer> list)throws Exception;
    
    /**
     * 插入订单时导入客户销售关联关系（主要用于导入erp订单）
     * @author polo
     * 2017年6月26日
     *
     */
    public void insertSaleCustomer(List<SaleCustomer> list)throws Exception;
    
    
    
    
    /**
     * 查询所有订单类型
     * @return
     */
    public List<ClientOrderType> queryAllType();
    
    
    /**
     * 根据id查询
     * @return
     */
   public ClientOrderType queryTypeById(Integer id);
   
   
   
   
   
   
   /**
    * 插入订单时导入客户销售关联关系（主要用于同步NBMail转跟单同步项目）
    * @author polo
    * 2017年7月25日
    *
    */
   public void insertClientOrder(ClientOrder clientOrder,String orderId,List<SaleCustomer> list,FactoryUserRelation factoryUserRelation,UserFactoryRelation userFactoryRelation,
		   List<SaleOrder> list3,ClientDrawings clientDrawing)throws Exception;
   
   
   
   
   
    
}
