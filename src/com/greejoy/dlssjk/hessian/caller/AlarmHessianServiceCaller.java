package com.greejoy.dlssjk.hessian.caller;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.hessian.service.AlarmHessianService;
import com.greejoy.dlssjk.utils.DomainFactory;
import com.greejoy.support.mybatis.Criteria;
import com.greejoy.support.mybatis.Restrictions;

/**
 * 
 * @author Gavin
 * @version 1.0
 * @date 2013-3-21
 */
@Component
public class AlarmHessianServiceCaller {


	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	
	public List<Map<String,Object>> getAlarms(Long stationId,Map<String,Object> params) throws MalformedURLException, ErrorServerException {
		AlarmHessianService alarmHessianService = (AlarmHessianService)proxyFactory.create(AlarmHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Alarm));
		Criteria criteria = new Criteria();
		Integer cp =Integer.parseInt(params.get("cp").toString());
		Integer ps =Integer.parseInt( params.get("ps").toString());
		criteria.add(Restrictions.limit(((cp-1)*ps)+"",ps+""));
		if(params.get("time_order")!=null){
			criteria.add(Restrictions.order("alarm_time",""+ params.get("time_order")));
		}
		return alarmHessianService.getAlarmRecords(criteria);
	}
	
	public Long getAlarmsCounts(Long stationId,Map<String,Object> params) throws MalformedURLException, ErrorServerException {
		AlarmHessianService alarmHessianService = (AlarmHessianService)proxyFactory.create(AlarmHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Alarm));
		Criteria criteria = new Criteria();
		return alarmHessianService.getAlarmRecordsCounts(criteria);
	}
	
	public byte[] getAlarmFile(int alarmId,Long stationId) throws Exception{
		AlarmHessianService alarmHessianService = (AlarmHessianService)proxyFactory.create(AlarmHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.Alarm));
		return alarmHessianService.getAlarmFile(alarmId);
	}
}
