package com.cbt.dao;

import java.util.List;

import com.cbt.entity.AmountUnit;

public interface AmountUnitDao {

	/**
	 * 查询所有金额单元
	 * @return
	 */
	List<AmountUnit> queryAmountUnit();
	
	/**
	 * 根据id查询币种（从0开始）
	 * @param id
	 * @return
	 */
	AmountUnit queryById(Integer id);
	
	
	/**
	 * 根据货币简写查询
	 * @param currencyShorthand
	 * @return
	 */
	AmountUnit queryByCurrencyShorthand(String currencyShorthand);
	
	
	/**
	 * 更新汇率
	 * @param amountUnit
	 */
	void updateRate(AmountUnit amountUnit); 
}
