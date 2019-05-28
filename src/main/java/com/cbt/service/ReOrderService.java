package com.cbt.service;

import java.util.List;

import com.cbt.entity.ReOrder;
import com.cbt.entity.ReOrderQuery;

public interface ReOrderService {
	/**
	 * 查询订单总数
	 * @return
	 */
	public int totalAmount(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询全部订单
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ReOrderQuery> queryAllReOrder(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询订单总数(管理员查询)
	 * @return
	 */
	public int totalAmountAdmin(ReOrderQuery reOrderQuery);
	
	/**
	 * 查询全部订单(管理员查询)
	 * 根据用户名、时间节点查询
	 * @param clientOrderQuery
	 * @return
	 */
	public List<ReOrderQuery> queryAllReOrderAdmin(ReOrderQuery reOrderQuery);
	
	/**
	 * 根据reOrderId查询
	 * @param id
	 * @return
	 */
	public ReOrder queryById(Integer id);
	
	
	/**
	 * 更新订单信息
	 * @param rfqInfo
	 */
	public void updateById(ReOrder reOrder);
	
	
}
