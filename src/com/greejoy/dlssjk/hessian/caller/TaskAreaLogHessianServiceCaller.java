package com.greejoy.dlssjk.hessian.caller;

import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.caucho.hessian.client.HessianProxyFactory;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.hessian.service.TaskAreaLogHessianService;
import com.greejoy.dlssjk.utils.DomainFactory;
import com.greejoy.support.mybatis.Criteria;
import com.greejoy.support.mybatis.Restrictions;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-3-19
 */
@Component
public class TaskAreaLogHessianServiceCaller {
	
	private HessianProxyFactory proxyFactory = new HessianProxyFactory();
	@Resource
	private DomainFactory domainFactory;
	
	/**
	 * 
	 * @param stationId
	 * @param areaId
	 * @param date
	 * @param type D M Y
	 * @return
	 * @throws MalformedURLException
	 * @throws ErrorServerException 
	 */
	public List<Map<String,Object>> getTaskAreaLogs(Long stationId,int areaId,String date,String type) throws MalformedURLException, ErrorServerException {
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		criteria.add(Restrictions.eq("area_id",areaId));
		if(type.equals("H")){
			criteria.add(Restrictions.group("DATE_FORMAT(taskarealog_time,'%Y-%m-%d %H')"));
			if(date!=null&&!date.equals(""))
			  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y-%m-%d')", date));
			criteria.add(Restrictions.column("DATE_FORMAT(taskarealog_time,'%Y-%m-%d (%H时)') as formate_time"));
		}else if(type.equals("D")){
			if(date!=null&&!date.equals(""))
			  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y-%m')", date));
			criteria.add(Restrictions.group("DATE_FORMAT(taskarealog_time,'%Y-%m-%d')"));
			criteria.add(Restrictions.column("DATE_FORMAT(taskarealog_time,'%Y-%m-%d') as formate_time"));
		}else if(type.equals("M")){
			if(date!=null&&!date.equals(""))
			  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y')", date));
			criteria.add(Restrictions.group("DATE_FORMAT(taskarealog_time,'%Y-%m')"));
			criteria.add(Restrictions.column("DATE_FORMAT(taskarealog_time,'%Y-%m') as formate_time"));
		}else if(type.equals("Y"))
			criteria.add(Restrictions.group("DATE_FORMAT(titaskarealog_timeme,'%Y')"));
		criteria.add(Restrictions.order("time", "DESC"));
		return taskAreaLogHessianService.getTaskAreaLogs(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getTaskAreaLogs(Long stationId,Map<String,Object> params) throws MalformedURLException, ErrorServerException {
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		Integer cp =Integer.parseInt(params.get("cp").toString());
		Integer ps =Integer.parseInt( params.get("ps").toString());
		criteria.add(Restrictions.limit(((cp-1)*ps)+"",ps+""));
		criteria.add(Restrictions.column("areatable.area_name"));
		criteria.add(Restrictions.column("pointtable.point_name"));
		criteria.add(Restrictions.column("monitortable.monitor_name"));
		criteria.add(Restrictions.column("DATE_FORMAT(taskarealog.taskarealog_time,'%Y-%m-%d %H:%c:%s') as logtime"));
		criteria.add(Restrictions.column("DATE_FORMAT(taskarealog.taskarealog_time,'%Y-%m-%d (%H时)') as format_time"));
		if(params.get("groupby")!=null)
			criteria.add(Restrictions.group(params.get("groupby").toString()));
		if(params.get("time")!=null){
			String type = (String)params.get("type");
			if(type==null||"D".equals(type)){
			  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog.taskarealog_time,'%Y-%m-%d')", params.get("time").toString()));
			}else if("M".equals(type)){
		      criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog.taskarealog_time,'%Y-%m')", params.get("time").toString()));
			}else if("Y".equals(type)){
				 criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog.taskarealog_time,'%Y')", params.get("time").toString()));
			}
		}
		if(params.get("areaIds")!=null)
			criteria.add(Restrictions.in("areatable.area_id", (Collection<Object>)params.get("areaIds")));
		return taskAreaLogHessianService.getTaskAreaLogs(criteria);
	}
	
	@SuppressWarnings("unchecked")
	public Long getTaskAreaLogsCounts(Long stationId,Map<String,Object> params) throws MalformedURLException, ErrorServerException {
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		if(params.get("time")!=null)
			criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog.taskarealog_time,'%Y-%m-%d')", params.get("time").toString()));
		if(params.get("areaIds")!=null)
			criteria.add(Restrictions.in("area_id", (Collection<Object>)params.get("areaIds")));
		return taskAreaLogHessianService.getTaskAreaLogsCounts(criteria);
	}
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByHour(Long stationId,String date) throws MalformedURLException, ErrorServerException{
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		if(date!=null&&!date.equals(""))
		  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y-%m-%d')", date));
		return taskAreaLogHessianService.getTaskAreaLogsDateByHour(criteria);
	}
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByDay(Long stationId,String date) throws MalformedURLException, ErrorServerException{
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		if(date!=null&&!date.equals(""))
		  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y-%m')", date));
		return taskAreaLogHessianService.getTaskAreaLogsDateByDay(criteria);
	}
	
	public  List<Map<String, Object>> getTaskAreaLogsDateByMonth(Long stationId,String date) throws MalformedURLException, ErrorServerException{
		TaskAreaLogHessianService taskAreaLogHessianService = (TaskAreaLogHessianService)proxyFactory.create(TaskAreaLogHessianService.class,
				domainFactory.buildUrl(stationId,HessianServiceType.TaskAreaLog));
		Criteria criteria = new Criteria();
		if(date!=null&&!date.equals(""))
		  criteria.add(Restrictions.eq("DATE_FORMAT(taskarealog_time,'%Y')", date));
		return taskAreaLogHessianService.getTaskAreaLogsDateByMonth(criteria);
	}
}
