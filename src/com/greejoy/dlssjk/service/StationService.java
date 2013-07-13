package com.greejoy.dlssjk.service;

import java.util.List;
import java.util.Map;

import com.greejoy.dlssjk.domain.Station;

/**
 * 站点服务类
 * @author Gavin
 * @version 1.0
 * @date 2013-2-12
 */
public interface StationService {
	
	/**
	 * 根据参数查询工作站点
	 * @param params
	 * @return
	 */
	public List<Station> getStations(Map<String,Object> params);
	
	/**
	 * 获取工作站详情
	 * @param id
	 * @return
	 */
	public Station getStation(Long id);
	
	/**
	 * 删除工作站
	 * @param id
	 */
	public void delete(Long id);
}
