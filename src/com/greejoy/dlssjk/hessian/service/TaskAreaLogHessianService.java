package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控区域任务执行情况Hessian服务
 * @author Gavin
 * @version 1.0
 * @date 2013-3-12
 */
public interface TaskAreaLogHessianService {

	/**
	 * 获取监控区域任务执行日志
	 * @param criteria
	 * @return
	 * {id,taskpointlog_id,record_type,min_temp,max_temp,alarm_temp,time,area_id}
	 */
	public List<Map<String,Object>> getTaskAreaLogs(Criteria criteria);
	
	/**
	 * 返回总记录数
	 * @param criteria
	 * @return
	 */
	public Long getTaskAreaLogsCounts(Criteria criteria);
	
	/**
	 * 根据id获取监控区域任务执行日志
	 * @param id
	 * @return
	 * {id,taskpointlog_id,record_type,min_temp,max_temp,alarm_temp,time,area_id}
	 */
	public Map<String,Object> getTaskAreaLog(String id);
	
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByHour(Criteria criteria);
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByDay(Criteria criteria);
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByMonth(Criteria criteria);
}
