package com.greejoy.dlssjk.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.repository.StationRepository;
import com.greejoy.dlssjk.service.StationService;
import com.reeham.component.ddd.model.ModelContainer;
import com.reeham.component.ddd.model.ModelLoader;
import com.reeham.component.ddd.model.ModelUtils;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-2-12
 */
@Service
public class StationServiceImpl implements StationService,ModelLoader{

	@Resource
	private StationRepository stationRepository;
	
	@Resource
	private ModelContainer modelContainer;
	
	@Override
	public Object loadModel(Object id) {
		return stationRepository.getStation((Long)id);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Station> getStations(Map<String,Object> params) {
		return modelContainer.identifiersToModels((List)stationRepository.getStations(params), Station.class, this);
	}

	@Override
	public Station getStation(Long id) {
		return  (Station) modelContainer.getModel(ModelUtils.asModelKey(Station.class, id), this);
	}

	@Override
	public void delete(Long id) {
		modelContainer.removeModel(ModelUtils.asModelKey(Station.class, id));
		stationRepository.delete(id);		
	}
	
	
}
