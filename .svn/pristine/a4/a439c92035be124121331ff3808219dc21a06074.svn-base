package com.cbt.dao;

import java.util.List;
import com.cbt.entity.RfqInfo;
import com.cbt.entity.RfqInfoQuery;

public interface RfqInfoDao {
    
	/**
	 * 查询订单总数
	 * 根据关键字查询
	 * @param rfqInfoQuery
	 * @return
	 */
	List<RfqInfoQuery> queryAllRfqInfo(RfqInfoQuery rfqInfoQuery);
	
	/**
	 * 查询新图纸询盘总数
	 * @return
	 */
	int totalAmount(RfqInfoQuery rfqInfoQuery);
	/**
	 * 查询订单总数(管理员查询)
	 * 根据关键字查询
	 * @param rfqInfoQuery
	 * @return
	 */
	List<RfqInfoQuery> queryAllRfqInfoAdmin(RfqInfoQuery rfqInfoQuery);
	
	/**
	 * 查询新图纸询盘总数(管理员查询)
	 * @return
	 */
	int totalAmountAdmin(RfqInfoQuery rfqInfoQuery);
	
	/**
	 * 根据id查询新图纸信息
	 * @param id
	 * @return
	 */
	RfqInfo queryById(Integer id);
	
	/**
	 * 更新图纸信息
	 * @param rfqInfo
	 */
	void updateById(RfqInfo rfqInfo);
}
