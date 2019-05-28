package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cbt.dao.NbMailCountryDao;
import com.cbt.entity.NbMailCountry;
import com.cbt.service.NbMailCountryService;

@Service

public class NbMailCountryServiceImpl implements NbMailCountryService {
   
	@Resource
	private NbMailCountryDao nbMailCountryDao;

	@Override
	public NbMailCountry queryById(Integer id) {
		NbMailCountry country = nbMailCountryDao.queryById(id);
		return country;
	}

	@Override
	public List<NbMailCountry> queryAll() {
		List<NbMailCountry> list = nbMailCountryDao.queryAll();
		return list;
	}

	@Override
	public NbMailCountry queryByCountry(String country) {
		NbMailCountry nbMailCountry = nbMailCountryDao.queryByCountry(country);
		return nbMailCountry;
	}





}
