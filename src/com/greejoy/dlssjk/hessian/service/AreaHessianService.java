package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控区域Hessian服务接口
 * @author Gavin
 * @version 1.0
 * @date 2013-3-3
 * @modified 2013-03-10
 */
public interface AreaHessianService {

	/**
	 * 获取监控点下的监控区域
	 * @return
	 * [{id,point_id,monitor_id,name,normal_temp,serious_temp,critical_temp,description}]
	 */
	public List<Map<String,Object>> getAreas(Criteria criteria);
	
	/**
	 * 获取监控区域的简略信息
	 * @param pointId
	 * @return
	 * {id,name}
	 */
	public List<Map<String,Object>> getAreasBriefInfo(Criteria criteria);
	
	/**
	 * 获取监控区域详细信息
	 * @param areaId
	 * @return
	 * {id,point_id,monitor_id,name,normal_temp,serious_temp,critical_temp,description}
	 */
	public Map<String,Object> getArea(int areaId);
	
}
