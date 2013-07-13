package com.greejoy.dlssjk.hessian.caller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.hessian.service.AreaHessianService;
import com.greejoy.dlssjk.utils.DomainFactory;
import com.greejoy.support.mybatis.Criteria;
import com.greejoy.support.mybatis.Restrictions;

/**
 * 
 * @author Gavin
 * @version 1.0
 * @date 2013-3-16
 */
@Component
public class AreaHessianServiceCaller {

	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	
	public List<Map<String,Object>> getAreaBriefInfo(Long stationId,int pointId) throws MalformedURLException, ErrorServerException {
		AreaHessianService areaHessianService = (AreaHessianService)proxyFactory.create(AreaHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Area));
		//Station station = stationService.getStation(stationId);
		Criteria criteria = new Criteria();
		//Restrictions.eq("station_id",station.getStationId());
		criteria.add(Restrictions.eq("point_id",pointId));
		return areaHessianService.getAreasBriefInfo(criteria);
	}
	
	
}
