package com.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderQuery;



public interface ReOrderDao {
    
	/**
	 * 查询订单总数
	 * @return
	 */
	int totalAmount(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询全部订单
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	List<ReOrderQuery> queryAllReOrder(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询订单总数(管理员查询)
	 * @return
	 */
	int totalAmountAdmin(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	List<ReOrderQuery> queryAllReOrderAdmin(ReOrderQuery reOrderQuery);
	
	/**
	 * 根据reOrderId查询
	 * @param id
	 * @return
	 */
	ReOrder queryById(Integer id);	
	
	/**
	 * 更新订单信息
	 * @param rfqInfo
	 */
	void updateById(ReOrder reOrder);
	
	
	
	
}
