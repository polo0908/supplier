package com.cbt.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cbt.dao.QuotationDao;
import com.cbt.dao.QuotationProductionDao;
import com.cbt.entity.QuotationBean;
import com.cbt.entity.QuotationProcessInfo;
import com.cbt.entity.QuotationProductionBean;
import com.cbt.entity.QuotationProductionPriceBean;
import com.cbt.service.QuotationProductionService;
import com.cbt.util.DateFormat;

@SuppressWarnings("serial")
@Service
public class QuotationProductionServiceImpl implements QuotationProductionService {
   
	@Resource
	QuotationProductionDao quotationProductionDao;
	
	@Resource
	QuotationDao quotationDao;
	
	@Override
	public List<QuotationProductionBean> queryByQuotationInfoId(
			Integer quotationInfoId) {
		return quotationProductionDao.queryByQuotationInfoId(quotationInfoId);
	}

	@Override
	public List<QuotationProductionPriceBean> queryByProductionId(
			Integer ProductionId) {
		return quotationProductionDao.queryByProductionId(ProductionId);
	}

	@Override
	public void updateQuotationProduction(
			QuotationProductionBean quotationProductionBean) {
		quotationProductionDao.updateQuotationProduction(quotationProductionBean);
		
	}

	@Override
	public void updateQuotationPriceBatch(
			List<QuotationProductionPriceBean> list) {
		quotationProductionDao.updateQuotationPriceBatch(list);
		
	}

	
	
	/**
	 * 更新报价产品
	 */
	@Transactional
	@Override
	public List<QuotationProductionBean> updateQuotationProduction(Integer quotationInfoId,String priceList,QuotationProductionBean quotationProductionBean)throws Exception{
		
		if(!(quotationProductionBean == null || "".equals(quotationProductionBean))){
			quotationProductionDao.updateQuotationProduction(quotationProductionBean);
		}
		
		if (priceList.endsWith(",")) {
			if(priceList.length()>1){
			priceList = priceList.substring(0, priceList.length() - 1);			 
			 String[] split = priceList.split(",");
			 List<QuotationProductionPriceBean> list = new ArrayList<QuotationProductionPriceBean>();
//			 List<QuotationProductionPriceBean> list1 = new ArrayList<QuotationProductionPriceBean>();
//			 Double totalPrice = 0.0;
//			 Double materialPrice = 0.0;
//			 Double processChangePrice = 0.0;
//			 Double materialChangePrice = 0.0;
				for (int i = 0; i < split.length; i++) {
					QuotationProductionPriceBean quotationProductionPriceBean = new QuotationProductionPriceBean();
					String[] split2 = split[i].split("%");
					Integer priceId = null;
					if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
						priceId = Integer.parseInt(split2[0].replaceFirst(".", ""));
					}
					Double price = 0.00;
					if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
						price = Double.parseDouble(split2[1].replaceFirst(".", ""));
					}
					Integer quantity = null;
					if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
						quantity = Integer.parseInt(split2[2].replaceFirst(".", ""));
					}
					Double totalProfit = 0.0;
					if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
						totalProfit = Double.parseDouble(split2[3].replaceFirst(".", ""));
					}					
				
					
					if(!(priceId == null || "".equals(priceId))){
						quotationProductionPriceBean = quotationProductionDao.queryByPriceId(priceId);
						quotationProductionPriceBean.setProductName(quotationProductionBean.getProductName());
						quotationProductionPriceBean.setProductionInfoId(quotationProductionBean.getId());
						quotationProductionPriceBean.setProductPrice(price);
						quotationProductionPriceBean.setQuantity(quantity);	
						quotationProductionPriceBean.setTotalProfitRate(totalProfit);
//						quotationProductionPriceBean.setMaterialProfitRate(materialProfitRate);
						quotationProductionPriceBean.setUpdateTime(DateFormat.format());
						
						
						//产品总价
//						totalPrice = new BigDecimal(price).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
						//材料价格
//						materialPrice = new BigDecimal(quotationProductionPriceBean.getMaterialPrice()).multiply(new BigDecimal((1+materialProfitRate))).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
//						List<QuotationProcessInfo> processList = quotationProductionDao.queryProcessByProductionId(priceId);
//						if(processList.size() != 0){
//							Double processAllPrice = new BigDecimal(quotationProductionPriceBean.getProcessPrice()).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
//							//工艺变化价格百分比
//							processChangePrice = new BigDecimal(totalPrice).subtract(new BigDecimal(materialPrice)).subtract(new BigDecimal(processAllPrice)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue(); 
//							processChangePrice = new BigDecimal(processChangePrice).divide(new BigDecimal(quotationProductionPriceBean.getProcessPrice()),2,RoundingMode.HALF_UP).doubleValue();
//							for (QuotationProcessInfo quotationProcessInfo : processList) {							
//								quotationProcessInfo.setProcessPrice(new BigDecimal(quotationProcessInfo.getProcessFactoryPrice()*(1+processChangePrice)).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue());
//							}				
//							quotationProductionDao.updateProcessBatch(processList);							
//						}else{				
							//如果不存在工艺，利润率全部加在材料上。
//							materialChangePrice = new BigDecimal(price).subtract(new BigDecimal(quotationProductionBean.getFactoryPrice())).subtract(new BigDecimal(quotationProductionPriceBean.getMaterialPrice())).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
//							materialProfitRate = new BigDecimal(materialChangePrice).divide(new BigDecimal(quotationProductionPriceBean.getMaterialPrice()),2,RoundingMode.HALF_UP).doubleValue();
//							quotationProductionPriceBean.setMaterialProfitRate(materialProfitRate);
//						}
						
						list.add(quotationProductionPriceBean);
						
					}else{
						
						quotationProductionPriceBean.setProductName(quotationProductionBean.getProductName());
						quotationProductionPriceBean.setProductionInfoId(quotationProductionBean.getId());
						quotationProductionPriceBean.setProductPrice(price);
						quotationProductionPriceBean.setQuantity(quantity);	
						quotationProductionPriceBean.setTotalProfitRate(totalProfit);
//						quotationProductionPriceBean.setMaterialProfitRate(materialProfitRate);
						quotationProductionPriceBean.setUpdateTime(DateFormat.format());				
						
						

						if(list.size() != 0){
							Double weight = list.get(0).getMaterialWeight();
							Double materialAmount = (list.get(0).getMaterialPrice() * quantity) / list.get(0).getQuantity();
							Double processPrice = (list.get(0).getProcessPrice() * quantity) / list.get(0).getQuantity();
							quotationProductionPriceBean.setMaterialWeight(weight);	
							quotationProductionPriceBean.setMaterialPrice(materialAmount);	
							quotationProductionPriceBean.setProcessPrice(processPrice);
							
							//产品总价
//							totalPrice = new BigDecimal(price).multiply(new BigDecimal(quantity)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
							//材料价格
//							materialPrice = new BigDecimal(quotationProductionPriceBean.getMaterialPrice()).multiply(new BigDecimal((1+materialProfitRate))).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
							List<QuotationProcessInfo> processList = quotationProductionDao.queryProcessByProductionId(list.get(0).getId());
							if(processList.size() != 0){
								quotationProductionDao.insertQuotationPrice(quotationProductionPriceBean);
								//工艺变化价格百分比
//								processChangePrice = new BigDecimal(totalPrice).subtract(new BigDecimal(materialPrice)).subtract(new BigDecimal(quotationProductionPriceBean.getProcessPrice())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); 
//								processChangePrice = new BigDecimal(processChangePrice).divide(new BigDecimal(quotationProductionPriceBean.getProcessPrice()),2,RoundingMode.HALF_UP).doubleValue();
								for (QuotationProcessInfo quotationProcessInfo : processList) {
									quotationProcessInfo.setId(null);
									quotationProcessInfo.setQuotationPriceId(quotationProductionPriceBean.getId());
//									quotationProcessInfo.setProcessPrice(new BigDecimal(quotationProcessInfo.getProcessFactoryPrice()*(1+processChangePrice)).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue());
								}				
								quotationProductionDao.insertQuotationProcessBatch(processList);			
							}else{				
								//如果不存在工艺，利润率全部加在材料上。
//								materialChangePrice = new BigDecimal(totalPrice).subtract(new BigDecimal(quotationProductionBean.getFactoryPrice())).subtract(new BigDecimal(quotationProductionPriceBean.getMaterialPrice())).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
//								materialProfitRate = new BigDecimal(materialChangePrice).divide(new BigDecimal(quotationProductionPriceBean.getProcessPrice()),2,RoundingMode.HALF_UP).doubleValue();
//								quotationProductionPriceBean.setMaterialProfitRate(materialProfitRate);
								quotationProductionDao.insertQuotationPrice(quotationProductionPriceBean);
							}
							
						}						
					}					
					
				}
				if(list.size() != 0){
					quotationProductionDao.updateQuotationPriceBatch(list);
				}
				
			}		
		}
		
		
		//更新总价格
		QuotationBean quotationBean = quotationDao.queryById(quotationInfoId);
		List<QuotationProductionBean> quotationProductions = quotationProductionDao.queryByQuotationInfoId(quotationInfoId);
		Double minPriceAmount = 0.00;
		Double maxPriceAmount = 0.00;
		for (QuotationProductionBean quotationProductionBean2 : quotationProductions) {
			List<QuotationProductionPriceBean> priceBeans = quotationProductionDao.queryByProductionId(quotationProductionBean2.getId());
			quotationProductionBean2.setQuotationProductionPriceBeanList(priceBeans);	
			Map<String, Double> maxMinPrice = quotationProductionDao.queryMinMaxPriceByProductId(quotationProductionBean2.getId());
			Double minPrice = 0.0;
			Double maxPrice = 0.0;
			if(!(maxMinPrice == null || maxMinPrice.size() == 0)){
			    minPrice = maxMinPrice.get("minPrice"); 
	            maxPrice = maxMinPrice.get("maxPrice"); 
			}
	        minPriceAmount +=minPrice;
	        maxPriceAmount +=maxPrice;
		}

		  if(minPriceAmount.equals(maxPriceAmount)){
          	quotationBean.setQuotationPriceRange(maxPriceAmount.toString());
          }else{
          	quotationBean.setQuotationPriceRange(minPriceAmount+"~"+maxPriceAmount);
          }
        quotationDao.updateQuotation(quotationBean);	
		
		
		return quotationProductions;
	}

	@Override
	public QuotationProductionBean queryProductionById(Integer id) {
		return quotationProductionDao.queryProductionById(id);
	}

	
	@Transactional
	@Override
	public List<QuotationProductionBean> insertQuotationProduction(Integer quotationInfoId, String priceList,QuotationProductionBean quotationProductionBean) throws Exception {

		if(!(quotationProductionBean == null || "".equals(quotationProductionBean))){
			quotationProductionDao.insertQuotationProduction(quotationProductionBean);
		}
		
		if (priceList.endsWith(",")) {
			if(priceList.length()>1){
			priceList = priceList.substring(0, priceList.length() - 1);			 
			 String[] split = priceList.split(",");
			 List<QuotationProductionPriceBean> list = new ArrayList<QuotationProductionPriceBean>();
				for (int i = 0; i < split.length; i++) {
					QuotationProductionPriceBean quotationProductionPriceBean = new QuotationProductionPriceBean();
					String[] split2 = split[i].split("%");
					Double price = 0.00;
					if(!(split2[0].replaceFirst(".", "") == null || "".equals(split2[0].replaceFirst(".", "")))){
						price = Double.parseDouble(split2[0].replaceFirst(".", ""));
					}
					Integer quantity = null;
					if(!(split2[1].replaceFirst(".", "") == null || "".equals(split2[1].replaceFirst(".", "")))){
						quantity = Integer.parseInt(split2[1].replaceFirst(".", ""));
					}
					Double totalProfit = 0.0;
					if(!(split2[2].replaceFirst(".", "") == null || "".equals(split2[2].replaceFirst(".", "")))){
						totalProfit = Double.parseDouble(split2[2].replaceFirst(".", ""));
					}
					Double materialProfitRate = 0.0;
					if(!(split2[3].replaceFirst(".", "") == null || "".equals(split2[3].replaceFirst(".", "")))){
						materialProfitRate = Double.parseDouble(split2[3].replaceFirst(".", ""));
					}
					
						quotationProductionPriceBean.setProductName(quotationProductionBean.getProductName());
						quotationProductionPriceBean.setProductionInfoId(quotationProductionBean.getId());
						quotationProductionPriceBean.setProductPrice(price);
						quotationProductionPriceBean.setQuantity(quantity);	
						quotationProductionPriceBean.setTotalProfitRate(totalProfit);
						quotationProductionPriceBean.setMaterialProfitRate(materialProfitRate);
						quotationProductionPriceBean.setUpdateTime(DateFormat.format());
						list.add(quotationProductionPriceBean);				
					
				}
				if(list.size() != 0){
					quotationProductionDao.insertQuotationPriceBatch(list);
				}
			}		
		}
		
		
		//更新总价格
		QuotationBean quotationBean = quotationDao.queryById(quotationInfoId);
		List<QuotationProductionBean> quotationProductions = quotationProductionDao.queryByQuotationInfoId(quotationInfoId);
		Double minPriceAmount = 0.00;
		Double maxPriceAmount = 0.00;
		for (QuotationProductionBean quotationProductionBean2 : quotationProductions) {
			List<QuotationProductionPriceBean> priceBeans = quotationProductionDao.queryByProductionId(quotationProductionBean2.getId());
			quotationProductionBean2.setQuotationProductionPriceBeanList(priceBeans);	
			Map<String, Double> maxMinPrice = quotationProductionDao.queryMinMaxPriceByProductId(quotationProductionBean2.getId());
			Double minPrice = 0.0;
			Double maxPrice = 0.0;
			if(!(maxMinPrice == null || maxMinPrice.size() == 0)){
			    minPrice = maxMinPrice.get("minPrice"); 
	            maxPrice = maxMinPrice.get("maxPrice"); 
			}
	        minPriceAmount +=minPrice;
	        maxPriceAmount +=maxPrice;
		}

		  if(minPriceAmount.equals(maxPriceAmount)){
          	quotationBean.setQuotationPriceRange(maxPriceAmount.toString());
          }else{
          	quotationBean.setQuotationPriceRange(minPriceAmount+"~"+maxPriceAmount);
          }
        quotationDao.updateQuotation(quotationBean);					
		
		
		return quotationProductions;
	}

	
	@Transactional
	@Override
	public void deleteProductionById(Integer productionId,Integer quotationInfoId) throws Exception{	
		quotationProductionDao.deleteProductionById(productionId);
		quotationProductionDao.deletePriceByProductionId(productionId);
		
		
		//更新总价格
		QuotationBean quotationBean = quotationDao.queryById(quotationInfoId);
		if(!(quotationBean == null || "".equals(quotationBean))){
			List<QuotationProductionBean> quotationProductions = quotationProductionDao.queryByQuotationInfoId(quotationInfoId);
			Double minPriceAmount = 0.00;
			Double maxPriceAmount = 0.00;
			for (QuotationProductionBean quotationProductionBean2 : quotationProductions) {
				List<QuotationProductionPriceBean> priceBeans = quotationProductionDao.queryByProductionId(quotationProductionBean2.getId());
				quotationProductionBean2.setQuotationProductionPriceBeanList(priceBeans);	
				Map<String, Double> maxMinPrice = quotationProductionDao.queryMinMaxPriceByProductId(quotationProductionBean2.getId());
				Double minPrice = 0.0;
				Double maxPrice = 0.0;
				if(!(maxMinPrice == null || maxMinPrice.size() == 0)){
				    minPrice = maxMinPrice.get("minPrice"); 
		            maxPrice = maxMinPrice.get("maxPrice"); 
				}
		        minPriceAmount +=minPrice;
		        maxPriceAmount +=maxPrice;
			}

			  if(minPriceAmount.equals(maxPriceAmount)){
	          	quotationBean.setQuotationPriceRange(maxPriceAmount.toString());
	          }else{
	          	quotationBean.setQuotationPriceRange(minPriceAmount+"~"+maxPriceAmount);
	          }
		    quotationDao.updateQuotation(quotationBean);	  
		}		
	}

	@Override
	public List<QuotationProcessInfo> queryProcessByProductionId(Integer priceId) {
		return quotationProductionDao.queryProcessByProductionId(priceId);
	}

	@Override
	public QuotationProductionPriceBean queryByPriceId(Integer priceId) {
		return quotationProductionDao.queryByPriceId(priceId);
	}

	@Override
	public QuotationProcessInfo queryProcessById(Integer processId) {
		return quotationProductionDao.queryProcessById(processId);
	}

	@Override
	public void deleteProcess(Integer id) {
		quotationProductionDao.deleteProcess(id);
		
	}

	
	
}
