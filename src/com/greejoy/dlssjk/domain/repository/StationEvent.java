package com.greejoy.dlssjk.domain.repository;

import com.greejoy.dlssjk.domain.Station;
import com.reeham.component.ddd.annotation.Introduce;
import com.reeham.component.ddd.annotation.Send;
import com.reeham.component.ddd.message.DomainMessage;

/**
 * 站点事件发送
 * @author Gavin
 * @version 1.0
 * @date 2013-2-12
 */
@Introduce("message")
public class StationEvent {

	@Send("saveOrUpdateStation")
	public DomainMessage saveOrUpdate(Station station){
		return new DomainMessage(station);
	}
	
	
}
