package com.greejoy.dlssjk.hessian.service;

import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 监控任务Hessian服务接口
 * @author Gavin
 * @version 1.0
 * @date 2013-3-7
 */
public interface TaskLogHessianService {

	/**
	 * 获取监控任务执行情况
	 * @param taskId
	 * @param filter
	 * @return
	 * {id,time,status,monitor_id,task_id,task_name}
	 */
	public List<Map<String,Object>> getTaskLogs(Criteria criteria);
	
	/**
	 * 根据id获取监控任务执行情况
	 * @param id
	 * @return
	 */
	public Map<String,Object> getTaskLog(String id);
	
}
