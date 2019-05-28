package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.ClientOrderQcReport;

public interface ClientOrderQcReportService extends Serializable {

	
	
	
	/**
	 * 根据订单号查询
	 * @author polo
	 * 2017年8月3日
	 */
	public List<ClientOrderQcReport> queryByClientOrderId(String orderId);
	
	
	/**
	 * 根据id查询下载路径
	 * @param id
	 * @return
	 */
	public String queryById(Integer id);
	
	
	/**
	 * 批量插入qc报告
	 * @param list
	 */
	public void insertBatch(List<ClientOrderQcReport> list);
	
	
	
	
	/**
	 * 根据id删除并且删除文件
	 * @param id
	 */
	public void deleteById(Integer id);
	
	/**
	 * 插入qc报告
	 * @param list
	 */
	public void insert(ClientOrderQcReport clientOrderQcReport);
}

