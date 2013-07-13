package com.greejoy.dlssjk.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.repository.StationRepository;
import com.reeham.component.ddd.annotation.OnEvent;

@Component
public class StationEventHandler {

	@Resource
	private StationRepository stationRepository;
	
	@OnEvent("saveOrUpdateStation")
	public void saveOrUpdate(Station station){
		System.out.println(station.getStationId()+"sssssssssssssssssssssssss");
		if(station.getId()==null)
			stationRepository.save(station);
		else
			stationRepository.update(station);
	}
	
}
