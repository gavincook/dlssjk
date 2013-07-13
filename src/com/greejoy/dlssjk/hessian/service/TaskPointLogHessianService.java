package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控点任务执行情况Hessian服务
 * @author Gavin
 * @version 1.0
 * @date 2013-3-12
 */
public interface TaskPointLogHessianService {

	/**
	 * 获取监控点任务执行情况
	 * @param criteria
	 * @return
	 * {id,tasklog_id,task_name,point_id,time,min_temp,max_temp,alarm_flag,snap_name}
	 */
	public List<Map<String,Object>> getTaskPointLogs(Criteria criteria);
	
	/**
	 * 根据id获取具体的监控点任务执行日志
	 * @param id
	 * @return
	 * {id,tasklog_id,task_name,point_id,time,min_temp,max_temp,alarm_flag,snap_name}
	 */
	public Map<String, Object> getTaskPointLog(String id);
	
}
