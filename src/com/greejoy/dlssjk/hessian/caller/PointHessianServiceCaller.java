package com.greejoy.dlssjk.hessian.caller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.hessian.service.PointHessianService;
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
public class PointHessianServiceCaller {

	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	
	public List<Map<String,Object>> getPointBriefInfo(Long stationId,int monitorId) throws MalformedURLException, ErrorServerException {
		PointHessianService pointHessianService = (PointHessianService)proxyFactory.create(PointHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Point));
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("monitor_id",monitorId));
		return pointHessianService.getPointsBriefInfo(criteria);
	}
	
	public Map<String,Object> getPoint(Long stationId,int pointId) throws MalformedURLException, ErrorServerException{
		PointHessianService pointHessianService = (PointHessianService)proxyFactory.create(PointHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Point));
		return pointHessianService.getPoint(pointId);
	}
}
