package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.InvoiceRemark;

public interface InvoiceRemarkService extends Serializable {

	
	/**
	 * 根据工厂id查询备注
	 * @param factoryId
	 * @return
	 */
	public List<InvoiceRemark> queryRemarkByFactoryId(String factoryId);
	
	
	/**
	 * 根据工厂id更新remark
	 * @param invoiceRemark
	 */
	public void updateRemarkByFactoryId(InvoiceRemark invoiceRemark);
	
	
	/**
	 * 根据工厂id更新remark
	 * @param invoiceRemark
	 */
	public void updateRemarkByFactoryId(Integer id,String remark)throws Exception;
	
	
	
	/**
	 * 根据id查询备注信息
	 * @author polo
	 * 2017年5月4日
	 *
	 */
	public InvoiceRemark queryById(Integer id);
}
