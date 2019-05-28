package com.cbt.dao;

import java.util.List;

import com.cbt.entity.InvoiceProduct;

public interface InvoiceProductDao {

	/**
	 * 批量插入对象InvoiceProduct(产品)
	 * 
	 * @param list
	 */
	void insertInvoiceProduct(List<InvoiceProduct> list);

	
	/**
	 * 根据InvoiceId(发票编号)查询InvoiceProduct(产品)
	 * 
	 * @param InvoiceId
	 * @return
	 */
	List<InvoiceProduct> queryByInvoiceId(String invoiceId);
	
	
	/**
	 * 根ProductId(产品ID)查询InvoiceProduct(产品)
	 * 
	 * @param id
	 * @return
	 */
	InvoiceProduct queryById(String id);
	
	
	/**
	 * 根InvoiceId(发票编号)删除InvoiceProduct(产品)
	 * @param invoiceId
	 */
	void deleteByInvoiceId(String invoiceId);
	 
	
	/**
	 * 根ProductId(产品ID)删除InvoiceProduct(产品)
	 * @param id
	 */
	void deleteById(Integer id);
	
}
