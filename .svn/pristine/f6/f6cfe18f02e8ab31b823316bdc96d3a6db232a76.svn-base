package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;

public interface SaleCustomerDao extends Serializable {
    
	
	/**
	 * 根据销售id查询对应客户id
	 * @param salesId
	 * @return
	 */
	List<SaleCustomer> queryBySalesId(String salesId);
	
	
	/**
	 * 根据销售和客户查询表数据
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	int queryCountBySaleAndCustomer(@Param("salesId")String salesId,@Param("userid")String userid);
	
	
	/**
	 * 根据销售和客户查询表数据
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	SaleCustomer queryBySaleAndCustomer(@Param("salesId")String salesId,@Param("userid")String userid);
	
	
	/**
	 * 跟单销售替换时，更新销售客户表
	 * @author polo
	 * 2017年6月2日
	 *
	 */
	void update(SaleCustomer saleCustomer);
	
	
	/**
	 * 插入销售客户关联表
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	void insert(SaleCustomer saleCustomer);
	
	
	/**
	 * 插入销售客户关联表
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	void insertBatch(List<SaleCustomer> list);
	
	
	
	/**
	 * 插入销售订单关联表
	 * @author polo
	 * 2017年7月1日
	 *
	 */
	void insertSaleOrderBatch(List<SaleOrder> list);
	
	
	
	
	/**
	 * 根据销售和订单号查询数据
	 * @author polo
	 * 2017年7月3日
	 *
	 */
	int queryCountBySaleAndOrder(@Param("salesId")String salesId,@Param("orderId")String orderId);
	
	
	/**
	 * 查询销售对应订单表
	 * @param salesId
	 * @param orderId
	 * @return
	 */
	SaleOrder queryBySaleAndOrder(@Param("salesId")String salesId,@Param("orderId")String orderId);
	
	
	/**
	 * 更新销售
	 * @param saleOrder
	 */
	void updateSaleOrder(SaleOrder saleOrder);
	
	
}
