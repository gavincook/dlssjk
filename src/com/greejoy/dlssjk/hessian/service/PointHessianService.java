package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控点(面)Hessian服务接口
 * @author Gavin
 * @version 1.0
 * @date 2013-3-3
 * @modified 2013-03-10
 */
public interface PointHessianService {

	/**
	 * 获取监控器下的监控点
	 * @param monitorId
	 * @return
	 * [{id,monitor_id,name,amb_temp,distance,point_number}]
	 */
	public List<Map<String,Object>> getPoints(Criteria criteria);
	
	/**
	 * 获取监控点的简略信息
	 * @param monitorId
	 * @return
	 * {id,name}
	 */
	public List<Map<String,Object>> getPointsBriefInfo(Criteria criteria);
	
	/**
	 * 获取监控点的详细信息
	 * @param pointId
	 * @return
	 * {id,monitor_id,name,amb_temp,distance,point_number}
	 */
	public Map<String,Object> getPoint(int pointId);
	
}
