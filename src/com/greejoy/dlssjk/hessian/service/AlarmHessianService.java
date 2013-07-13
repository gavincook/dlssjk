package com.greejoy.dlssjk.hessian.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.greejoy.support.mybatis.Criteria;

/**
 * 报警记录Hessian服务
 * @author Gavin
 * @version 1.0
 * @date 2013-3-21
 */
public interface AlarmHessianService {

	public List<Map<String,Object>> getAlarmRecords(Criteria criteria);
	
	/**
	 * 返回总记录数
	 * @param criteria
	 * @return
	 */
	public Long getAlarmRecordsCounts(Criteria criteria);
	
	public Map<String,Object> getAlarmRecord(int alarmId);
	
	public byte[] getAlarmFile(int alarmId) throws  IOException;
	
	public byte[] getAlarmFile(String path) throws  IOException;
	
}
