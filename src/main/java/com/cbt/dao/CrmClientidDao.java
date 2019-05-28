package com.cbt.dao;

import java.io.Serializable;
import java.util.List;

import com.cbt.entity.CrmClientid;

public interface CrmClientidDao extends Serializable {

	
	/**
	 * 根据客户查询所有订单号
	 * @author polo
	 * 2017年5月5日
	 *
	 */
	List<CrmClientid> queryByUserid(String userid);
}
