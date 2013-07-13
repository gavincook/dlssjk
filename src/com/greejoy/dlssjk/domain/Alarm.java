package com.greejoy.dlssjk.domain;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.AlarmHessianServiceCaller;
import com.greejoy.dlssjk.service.StationService;
import com.greejoy.utils.ParamUtils;
import com.reeham.component.ddd.annotation.Model;

/**
 * 报警域
 * @author Gavin
 * @version 1.0
 * @date 2013-3-28
 */
@Model
public class Alarm {

	@Resource
	private AlarmHessianServiceCaller alarmHessianServiceCaller;
	@Resource
	private StationService stationService;
	
	private Map<Long,Long> alarmRecord = new HashMap<Long,Long>();
	private Map<Long,Long> oldAlarmRecord = new HashMap<Long,Long>();
	private boolean isAlarm = false;
	public boolean isAlarm() throws MalformedURLException, ErrorServerException{
		Map<String,Object> params = ParamUtils.getDefaultParamMap();
		params.put("ps", 1000);
		List<Station> stations = stationService.getStations(params);
		alarmRecord.clear();
		
		for(Station station:stations){
			Long counts = alarmHessianServiceCaller.getAlarmsCounts(station.getId(), null);
			if(oldAlarmRecord.containsKey(station.getId())){
				if(oldAlarmRecord.get(station.getId())!=null&&counts>oldAlarmRecord.get(station.getId())){
					alarmRecord.put(station.getId(),counts);
					isAlarm = true;
				}
			}
				oldAlarmRecord.put(station.getId(), counts);
		}
		
		if(alarmRecord.keySet().size()==0)
			isAlarm = false;
		return isAlarm;
	}
	
	
	public Map<Long,Long> showAlarm(){
		return alarmRecord;
	}
}
