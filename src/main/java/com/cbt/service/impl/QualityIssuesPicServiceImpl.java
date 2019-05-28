package com.cbt.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cbt.dao.QualityIssuesPicDao;
import com.cbt.entity.QualityIssuesPic;
import com.cbt.service.QualityIssuesPicService;

@Service
public class QualityIssuesPicServiceImpl implements QualityIssuesPicService {

	@Resource
	 private QualityIssuesPicDao qualityIssuesPicDao;
	
	@Override
	public List<QualityIssuesPic> queryByOrderMessageId(Integer orderMessageId) {
		return qualityIssuesPicDao.queryByOrderMessageId(orderMessageId);
	}

}
