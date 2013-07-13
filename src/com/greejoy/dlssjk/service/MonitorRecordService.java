package com.greejoy.dlssjk.service;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.AreaHessianServiceCaller;
import com.greejoy.dlssjk.hessian.caller.PointHessianServiceCaller;
import com.greejoy.dlssjk.hessian.caller.TaskAreaLogHessianServiceCaller;

/**
 * 监控记录服务类
 * @author Gavin
 * @version 1.0
 * @date 2013-3-19
 */
@Service
public class MonitorRecordService {

	@Resource
	private PointHessianServiceCaller pointHessianServiceCaller;
	
	@Resource
	private AreaHessianServiceCaller areaHessianServiceCaller;
	@Resource
	private TaskAreaLogHessianServiceCaller taskAreaLogHessianServiceCaller;
	  
	public List<Map<String,Object>> getAreaLogsByPoints(Long stationId,Integer pointIds[],String date,String type) throws  ErrorServerException, MalformedURLException{
		List<Map<String,Object>> arealogs= new ArrayList<Map<String,Object>>();
		for(Integer pointId:pointIds){
			Map<String,Object> m = pointHessianServiceCaller.getPoint(stationId, pointId);
			List<Map<String,Object>> areas = areaHessianServiceCaller.getAreaBriefInfo(stationId, pointId);
			for(Map<String,Object> area:areas){
				Integer areaId = Integer.parseInt(area.get("id").toString());
				area.put("arealogs", taskAreaLogHessianServiceCaller.getTaskAreaLogs(stationId, areaId,date,type));
			}
			m.put("areas", areas);
			arealogs.add(m);
		}
		return arealogs;
	}
	
	public List<Map<String,Object>> getArealogsDate(Long stationId,String date,String type) throws MalformedURLException, ErrorServerException{
		if(type.equals("H")){
			return taskAreaLogHessianServiceCaller.getTaskAreaLogsDateByHour(stationId,date);
		}else if(type.equals("D")){
			return taskAreaLogHessianServiceCaller.getTaskAreaLogsDateByDay(stationId,date);
		}else if(type.equals("M")){
			return taskAreaLogHessianServiceCaller.getTaskAreaLogsDateByMonth(stationId,date);
		}
		return null;
	}
}
