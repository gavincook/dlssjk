package com.greejoy.dlssjk.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.greejoy.dlssjk.domain.Station;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-2-12
 */
@Repository
public interface StationRepository{
	
	public List<Long> getStations(Map<String,Object> params);
	
	public Station getStation(@Param("id")Long id);
	
	public void save(@Param("station") Station station);
	
	public void update(@Param("station") Station station);
	
	public void delete(@Param("id") Long id);
	
}
