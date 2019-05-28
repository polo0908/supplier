package com.cbt.service;

import java.util.List;

import com.cbt.entity.ClientDrawings;
import com.cbt.entity.ClientOrder;
import com.cbt.entity.ReOrder;
import com.cbt.entity.RfqInfo;
import com.cbt.entity.RfqInfoQuery;

public interface RfqInfoService {
	/**
	 * 查询订单总数
	 * 根据关键字查询
	 * @param rfqInfoQuery
	 * @return
	 */
	public List<RfqInfoQuery> queryAllRfqInfo(RfqInfoQuery rfqInfoQuery);
	
	
	/**
	 * 查询新图纸询盘总数
	 * @return
	 */
	public int totalAmount(RfqInfoQuery rfqInfoQuery);
	
	/**
	 * 查询订单总数(管理员查询)
	 * 根据关键字查询
	 * @param rfqInfoQuery
	 * @return
	 */
	public List<RfqInfoQuery> queryAllRfqInfoAdmin(RfqInfoQuery rfqInfoQuery);
	
	/**
	 * 查询新图纸询盘总数(管理员查询)
	 * @return
	 */
	public int totalAmountAdmin(RfqInfoQuery rfqInfoQuery);
	
	
	/**
	 * 根据id查询新图纸信息
	 * @param id
	 * @return
	 */
	public RfqInfo queryById(Integer id);
	
	/**
	 * 更新图纸信息(插入client_drawing表数据)
	 * @param rfqInfo
	 */
	public void updateById(RfqInfo rfqInfo,ClientDrawings clientDrawings)throws Exception;
	/**
	 * 更新图纸信息
	 * @param rfqInfo
	 */
	public void updateById(RfqInfo rfqInfo);
}
