package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控器hessian服务接口
 * @author Gavin
 * @version 1.0
 * @date 2013-3-3
 * @modified 2013-03-10
 */
public interface MonitorHessianService {

	/**
	 * 获取工作站下的监控器
	 * @param criteria 查询条件
	 * @return
	 * {id,station_id,name,ip,port,description}
	 */
	public List<Map<String,Object>> getMonitors(Criteria criteria);
	
	/**
	 * 获取监控器的简略信息
	 * @param  criteria
	 * @return
	 * {id,name}
	 */
	public List<Map<String,Object>> getMonitorsBriefInfo(Criteria criteria);
	
	/**
	 * 获取监控器的详细信息
	 * @param monitorId
	 * @return
	 * {id,station_id,name,id,port,description}
	 */
	public Map<String,Object> getMonitor(int monitorId);
	
}
