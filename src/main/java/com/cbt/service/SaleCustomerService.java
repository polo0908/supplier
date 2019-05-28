package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.SaleCustomer;
import com.cbt.entity.SaleOrder;

public interface SaleCustomerService extends Serializable {

	
	/**
	 * 根据销售id查询对应客户id
	 * @param salesId
	 * @return
	 */
	public List<SaleCustomer> queryBySalesId(String salesId);
	
	
	
	/**
	 * 根据销售和客户查询表数据
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	public int queryCountBySaleAndCustomer(String salesId,String userid);
	
	
	
	/**
	 * 插入销售客户关联表
	 * @author polo
	 * 2017年5月22日
	 *
	 */
	public void insert(SaleCustomer saleCustomer);
	
	
	
	
	/**
	 * 跟单销售替换时，更新销售客户表
	 * @author polo
	 * 2017年6月2日
	 *
	 */
	public void update(SaleCustomer saleCustomer);
	
	
	/**
	 * 根据销售和客户查询表数据
	 * @author polo
	 * 2017年6月27日
	 *
	 */
	public SaleCustomer queryBySaleAndCustomer(String salesId,String userid);
	
	
	
	
	/**
	 * 插入销售订单关联表
	 * @author polo
	 * 2017年7月1日
	 *
	 */
	public void insertSaleOrderBatch(List<SaleOrder> list);
	
	
	/**
	 * 根据销售和订单号查询数据
	 * @author polo
	 * 2017年7月3日
	 *
	 */
	public int queryCountBySaleAndOrder(String salesId,String orderId);
	
	/**
	 * 查询销售对应订单表
	 * @param salesId
	 * @param orderId
	 * @return
	 */
	public SaleOrder queryBySaleAndOrder(String salesId,String orderId);
	
	/**
	 * 更新销售
	 * @param saleOrder
	 */
	public void updateSaleOrder(SaleOrder saleOrder);
}
