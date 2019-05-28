package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import com.cbt.entity.ClientOrderPo;

public interface ClientOrderPoService extends Serializable {

	
	
	
	/**
	 * 根据订单号查询
	 * @author polo
	 * 2017年8月3日
	 */
	public List<ClientOrderPo> queryByClientOrderId(String orderId);
	
	/**
	 * 根据id查询下载路径
	 * @param id
	 * @return
	 */
	public String queryById(Integer id);
	
	
	/**
	 * 批量插入po报告
	 * @param list
	 */
	public void insertBatch(List<ClientOrderPo> list);
	
	/**
	 * 插入po报告
	 * @param list
	 */
	public void insert(ClientOrderPo clientOrderPo);
	
	
	
	
	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteById(Integer id);
}

