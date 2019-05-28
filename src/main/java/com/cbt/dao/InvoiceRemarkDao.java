package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.InvoiceRemark;

public interface InvoiceRemarkDao extends Serializable {

	/**
	 * 根据工厂id查询备注
	 * @param factoryId
	 * @return
	 */
	List<InvoiceRemark> queryRemarkByFactoryId(String factoryId);
	
	
	/**
	 * 根据工厂id更新remark
	 * @param invoiceRemark
	 */
	void updateRemarkByFactoryId(InvoiceRemark invoiceRemark);
	
	
	
	/**
	 * 根据id查询备注信息
	 * @author polo
	 * 2017年5月4日
	 *
	 */
	InvoiceRemark queryById(Integer id);
}
