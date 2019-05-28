package com.cbt.dao;

import java.util.List;



import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ClientOrder;
import com.cbt.entity.ClientOrderQuery;
import com.cbt.entity.ClientOrderType;





public interface ClientOrderDao {

	/**
	 * 查询订单总数
	 * @return
	 */
	int totalAmount(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询全部订单
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	List<ClientOrderQuery> queryListByDate(ClientOrderQuery clientOrderQuery);
	
	
	/**
	 * 查询全部订单
	 * 根据用户名、时间节点查询(根据客户,时间排序)
	 * @param clientOrderQuery
	 * @return
	 */
	List<ClientOrderQuery> queryListByDateOrderByUser(ClientOrderQuery clientOrderQuery);
	
	
	/**
	 * 查询订单总数(管理员查询)
	 * @return
	 */
	int totalAmountAdmin(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	List<ClientOrderQuery> queryListByDateAdmin(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询(根据客户,时间排序)
	 * @param clientOrderQuery
	 * @return
	 */
	List<ClientOrderQuery> queryListByDateAdminOrderByUser(ClientOrderQuery clientOrderQuery);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	ClientOrder queryById(Integer id);
    
	/**
	 * 根据订单号查询客户订单详情
	 * @param orderId
	 * @return
	 */	
	 ClientOrder queryByOrderId(String orderId);	
    
    /**
     * 单个插入对象
     * @param ClientOrder
     */
     void insertClientOrder(ClientOrder clientOrder);
    /**
     * 批量插入对象
     * @param list
     */
     void insertClientOrders(List<Object> list);
    
	 /**
	  * 更新clientOrder po、invoice、qc、shipping数据
	  * @param clientOrder
	  */
     void updateClientOrder(ClientOrder clientOrder);
     
      
     /**
      * 批量更新金额
      * @author polo
      * 2017年5月22日
      *
      */
     void updateBatch(List<ClientOrder> list);
     
     
     
     
     
     
     
     
     /**
      * 查询所有订单类型
      * @return
      */
     List<ClientOrderType> queryAllType();
     
     
     /**
      * 根据id查询
      * @return
      */
     ClientOrderType queryTypeById(Integer id);
     
     
}
