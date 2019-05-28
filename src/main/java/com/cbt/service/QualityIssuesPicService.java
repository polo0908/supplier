package com.cbt.service;

import java.io.Serializable;
import java.util.List;
import com.cbt.entity.QualityIssuesPic;

public interface QualityIssuesPicService extends Serializable {

	/**
	 * 根据消息中心id查询图片信息
	 * @param messageCenterId
	 * @return
	 */
	List<QualityIssuesPic> queryByOrderMessageId(Integer orderMessageId);

}
