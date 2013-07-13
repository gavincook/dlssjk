package com.greejoy.dlssjk.hessian.caller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.hessian.service.MonitorHessianService;
import com.greejoy.dlssjk.service.StationService;
import com.greejoy.dlssjk.utils.DomainFactory;
import com.greejoy.support.mybatis.Criteria;
import com.greejoy.support.mybatis.Restrictions;

/**
 * 监控点数据
 * @author Gavin
 * @version 1.0
 * @date 2013-3-16
 */
@Component
public class MonitorHessianServiceCaller {
	
	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	@Resource
	private StationService stationService;
	
	/**
	 * 
	 * @param stationId 平台工作站id
	 * @return
	 * @throws MalformedURLException
	 * @throws ErrorServerException 
	 */
	public List<Map<String,Object>> getMonitorsBriefInfo(Long stationId) throws MalformedURLException, ErrorServerException {
		MonitorHessianService monitorHessianService = (MonitorHessianService)proxyFactory.create(MonitorHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Monitor));
		Station station = stationService.getStation(stationId);
		System.out.println(station.getIp()+"..."+station.getStationId());
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("station_id",station.getStationId()));
		return monitorHessianService.getMonitorsBriefInfo(criteria);
	}
	
	/**
	 * 
	 * @param stationId 平台工作站id
	 * @return
	 * @throws MalformedURLException
	 * @throws ErrorServerException 
	 */
	public Map<String,Object> getMonitor(Long stationId,Long monitorId) throws MalformedURLException, ErrorServerException {
		MonitorHessianService monitorHessianService = (MonitorHessianService)proxyFactory.create(MonitorHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Monitor));
		return monitorHessianService.getMonitor(Integer.parseInt(Long.toString(monitorId)));
	}
}
