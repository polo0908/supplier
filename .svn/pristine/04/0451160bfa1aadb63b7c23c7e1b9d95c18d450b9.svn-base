package com.cbt.service;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;

public interface QuotationProductionService extends Serializable {

	
	
	/**
	 * 根据id查询产品
	 * @param id
	 * @return
	 */
	public QuotationProductionBean queryProductionById(Integer id);	
	
	/**
	 * 根据报价单id查询
	 * @author polo
	 * 2017年5月26日
	 *
	 */
	public List<QuotationProductionBean> queryByQuotationInfoId(Integer quotationInfoId);
	
	
	
	/**
	 * 根据产品表id查询
	 * @author polo
	 * 2017年5月26日
	 *
	 */
	public List<QuotationProductionPriceBean> queryByProductionId(Integer productionId);
	
	
	/**
	 * 增加报价产品
	 * @param quotationInfoId
	 * @param priceList
	 * @param quotationProductionBean
	 * @return
	 * @throws Exception
	 */
	public List<QuotationProductionBean> insertQuotationProduction(Integer quotationInfoId,String priceList,QuotationProductionBean quotationProductionBean)throws Exception;
	
	
    /**
	 * 根据产品id删除(并且删除价格)
	 * @param ProductionId
	 */
	public void deleteProductionById(Integer productionId,Integer quotationInfoId)throws Exception;
	
	/**
	 * 更新报价产品
	 * @param quotationProductionBean
	 */
	public void updateQuotationProduction(QuotationProductionBean quotationProductionBean);
	
	/**
	 * 更新报价产品价格
	 * @param list
	 */
	public void updateQuotationPriceBatch(List<QuotationProductionPriceBean> list);
	
	
	/**
	 * 用于已发送报价单更新产品价格
	 * @param quotationInfoId
	 * @param priceList
	 */
	public List<QuotationProductionBean> updateQuotationProduction(Integer quotationInfoId,String priceList,QuotationProductionBean quotationProductionBean)throws Exception;
	
	
	
	/**
	 * 根据产品查询工艺
	 * @param ProductionId
	 * @return
	 */
	public List<QuotationProcessInfo> queryProcessByProductionId(Integer priceId);
	
	/**
	 * 根据价格表Id查询数据
	 * @param priceId
	 * @return
	 */
	public QuotationProductionPriceBean queryByPriceId(Integer priceId);
	
	
	
	/**
	 * 根据id查询工艺 
	 *  2017/12/27
	 * @Title queryProcessById 
	 * @Description TODO
	 * @param processId
	 * @return
	 * @return QuotationProcessInfo
	 */
	public QuotationProcessInfo queryProcessById(Integer processId);
	
	
	/**
	 * 根据id删除工艺
	 * @Title deleteProcess 
	 * @Description TODO
	 * @param id
	 * @return void
	 */
	public void deleteProcess(Integer id);
}
