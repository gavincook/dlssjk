package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控任务Hessian服务接口
 * @author Gavin
 * @version 1.0
 * @date 2013-3-3
 */
public interface TaskHessianService {

	/**
	 * 获取监控任务
	 * @return
	 * {id,monitor_id,name,description,alarm_record_flag,record_time,
	 * runtime,active_flag,detect_type}
	 */
	public List<Map<String,Object>> getTasks(Criteria criteria);
	
	/**
	 * 获取监控任务简略信息
	 * @param filter
	 * @return
	 * {id,name}
	 */
	public List<Map<String,Object>> getTasksBriefInfo(Criteria criteria);
	
	/**
	 * 获取任务详情
	 * @param taskId
	 * @return
	 */
	public Map<String,Object> getTask(int taskId);
}
