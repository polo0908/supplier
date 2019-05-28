package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.ClientOrderShippingDoc;

public interface ClientOrderShippingDocDao extends Serializable {
  
	/**
	 * 根据订单号查询
	 * @author polo
	 * 2017年8月3日
	 */
	List<ClientOrderShippingDoc> queryByClientOrderId(String orderId);
	
	
	
	/**
	 * 根据id查询下载路径
	 * @param id
	 * @return
	 */
	ClientOrderShippingDoc queryById(Integer id);
	
	
	/**
	 * 批量插入shipping报告
	 * @param list
	 */
	void insertBatch(List<ClientOrderShippingDoc> list);
	
	/**
	 * 插入shipping报告
	 * @param list
	 */
	void insert(ClientOrderShippingDoc clientOrderShippingDoc);
	
	
	
	
	/**
	 * 根据id删除
	 * @param id
	 */
	void deleteById(Integer id);
	
	
	/**
	 * 更新运输时间
	 * @param clientOrderShippingDoc
	 */
	void updateShippingDoc(ClientOrderShippingDoc clientOrderShippingDoc);
	
	
	
	/**
	 * 根据起运时间和订单号查询（判断是否存在重复shipping数据）
	 * @return
	 */
	int queryBySailDateAndOrderId(@Param("shippingStartDate")String sailDate,@Param("orderId")String orderId);
	
	
	
	
}
