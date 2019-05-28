package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.AmountUnitDao;
import com.cbt.entity.AmountUnit;
import com.cbt.service.AmountUnitService;

@Service

public class AmountUnitServiceImpl implements AmountUnitService {
   
	@Resource
	private AmountUnitDao amountUnitDao;

	@Override
	public List<AmountUnit> queryAmountUnit() {
		List<AmountUnit> list = amountUnitDao.queryAmountUnit();
		return list;
	}

	@Override
	public AmountUnit queryById(Integer id) {
		AmountUnit amountUnit = amountUnitDao.queryById(id);
		return amountUnit;
	}

	@Override
	public AmountUnit queryByCurrencyShorthand(String currencyShorthand) {
		return amountUnitDao.queryByCurrencyShorthand(currencyShorthand);
	}

	@Override
	public void updateRate(AmountUnit amountUnit) {
		amountUnitDao.updateRate(amountUnit);
	}



}
