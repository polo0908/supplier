package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import com.cbt.entity.ClientOrderShippingDoc;

public interface ClientOrderShippingDocService extends Serializable {

	
	
	/**
	 * 根据订单号查询
	 * @author polo
	 * 2017年8月3日
	 */
	public List<ClientOrderShippingDoc> queryByClientOrderId(String orderId);
	
	
	/**
	 * 根据id查询下载路径
	 * @param id
	 * @return
	 */
	public ClientOrderShippingDoc queryById(Integer id);
	
	/**
	 * 批量插入shipping报告
	 * @param list
	 */
	public void insertBatch(List<ClientOrderShippingDoc> list);
	
	
	
	
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 更新运输时间
	 * @param clientOrderShippingDoc
	 */
	public void updateShippingDoc(ClientOrderShippingDoc clientOrderShippingDoc);
	
	/**
	 * 插入shipping报告
	 * @param list
	 */
	public void insert(ClientOrderShippingDoc clientOrderShippingDoc);
	
	
	
	/**
	 * 根据起运时间和订单号查询（判断是否存在重复shipping数据）
	 * @return
	 */
	public int queryBySailDateAndOrderId(String sailDate,String orderId);
}
