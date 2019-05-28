package com.cbt.service;


import java.util.List;

import com.cbt.entity.AmountUnit;

public interface AmountUnitService {

	/**
	 * 查询所有金额单元
	 * @return
	 */
	public List<AmountUnit> queryAmountUnit();
	
	/**
	 * 根据id查询币种（从0开始）
	 * @param id
	 * @return
	 */
	public AmountUnit queryById(Integer id);
	
	
	/**
	 * 根据货币简写查询
	 * @param currencyShorthand
	 * @return
	 */
	public AmountUnit queryByCurrencyShorthand(String currencyShorthand);
	
	
	/**
	 * 更新汇率
	 * @param amountUnit
	 */
	public void updateRate(AmountUnit amountUnit); 
}
