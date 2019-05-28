package com.cbt.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cbt.entity.InvoiceInfo;


public interface InvoiceInfoDao {
	  
    /**
     * 单个插入对象
     * @param InvoiceInfo
     */
     void insertInvoiceInfo(InvoiceInfo invoiceInfo);
     
     /**
      * 根据发票编号查询发票详情
      * @param InvoiceId
      * @return
      */
     InvoiceInfo queryByInvoiceId(@Param("invoiceId")String invoiceId,@Param("factoryId")String factoryId);
     
     /**
      * 根据id查询发票信息
      * @param id
      * @return
      */
     InvoiceInfo queryById(Integer id);
     
     /**
      * 根据OrderId查询所有发票的信息
      * @param orderId
      * @return
      */
     List<InvoiceInfo> queryInvoiceByOrderId(String orderId);
    
	 /**
	  * 更新InvoiceInfo数据
	  * @param clientOrder
	  */
     void updateInvoiceInfo(InvoiceInfo invoiceInfo);
     
     /**
      * 根据发票的ID删除当前发票信息
      * @param id
      */
     void deleteInvoiceById(Integer id);
     
     
     
     /**
      * 批量插入对象
      * @param list
      */
      void insertInvoiceBatch(List<InvoiceInfo> list);
     
}
