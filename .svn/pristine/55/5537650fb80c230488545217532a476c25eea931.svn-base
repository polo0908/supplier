package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.CrmClientidDao;
import com.cbt.entity.CrmClientid;
import com.cbt.service.CrmClientidService;

@Service
public class CrmClientidServiceImpl implements CrmClientidService {
  
	@Resource
	private CrmClientidDao crmClientidDao;
	
	@Override
	public List<CrmClientid> queryByUserid(String userid) {
		return crmClientidDao.queryByUserid(userid);
	}

}
