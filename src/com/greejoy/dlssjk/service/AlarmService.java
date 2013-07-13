package com.greejoy.dlssjk.service;

import java.net.MalformedURLException;
import java.util.Map;

import com.greejoy.dlssjk.exception.ErrorServerException;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-3-30
 */
public interface AlarmService {

	public boolean isAlarm() throws MalformedURLException, ErrorServerException;
	
	
	public Map<Long,Long> showAlarm();
}
