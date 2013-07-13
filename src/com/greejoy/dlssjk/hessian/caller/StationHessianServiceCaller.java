package com.greejoy.dlssjk.hessian.caller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.hessian.service.StationHessianService;
import com.greejoy.dlssjk.utils.DomainFactory;
import com.greejoy.support.mybatis.Criteria;

@Component
public class StationHessianServiceCaller {

	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	
	/**
	 * 	根据ip和port获取工作站列表(用于初始化平台工作站信息)
	 * @param ip
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public List<Station> getStationsByIp(String ip,String port) throws Exception{
		StationHessianService stationHessianService = (StationHessianService)proxyFactory.create(StationHessianService.class,
				domainFactory.buildUrl(ip,port,Station.class));
		return domainFactory.convertMapToDomain(stationHessianService.getStations(new Criteria()), Station.class,true);
	}
	
}
