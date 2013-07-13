package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

public interface StationHessianService {

	/**
	 * 获取所有工作站点
	 * @return
	 */
	public List<Map<String,Object>> getStations(Criteria criteria);
	
	/**
	 * 获取第一个工作站点
	 * @return
	 */
	public Map<String,Object> getStation(Criteria criteria); 
	
}
