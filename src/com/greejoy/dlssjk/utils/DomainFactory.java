package com.greejoy.dlssjk.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.greejoy.dlssjk.config.AppConfig;
import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.HessianServiceType;
import com.greejoy.dlssjk.service.StationService;
import com.reeham.component.ddd.model.ModelContainer;

/**
 * 域模型工厂(用于将Hessian服务返回的map转换为域模型)
 * @author Gavin
 * @version 1.0
 * @date 2013-3-4
 */
@Component
public class DomainFactory {

	@Resource
	private  ModelContainer modelContainer;
	@Resource
	private  StationService stationService;
	@Resource
	private AppConfig appConfig;
	public  <T> T convertMapToDomain(Map<String,Object> m,Class<T> domainClass) throws Exception  {
		return convertMapToDomain(m,domainClass,false);
	}
	
	public  <T> T convertMapToDomain(Map<String,Object> m,Class<T> domainClass,boolean resolveInject) throws Exception  {
		if(m==null)
			throw new NullPointerException("The map used to convert domain must not be null.");
		if(domainClass==null)
			throw new NullPointerException("Must appoint a Domain Class to convert.");
		T domain = domainClass.newInstance();
		Field f;
		Pattern pattern;
		Matcher matcher;
		for(String key:m.keySet()){
			try{
				Object value=m.get(key);
				pattern = Pattern.compile("_\\w");
				matcher = pattern.matcher(key);
				if(matcher.find())//将类似station_name转换为stationName
					key=key.replaceFirst("_\\w", matcher.group().substring(1).toUpperCase());
				f = domainClass.getDeclaredField(key);
				if(f!=null){
					if(!f.isAccessible())
						f.setAccessible(true);
					f.set(domain, value);
				}
			}catch(Exception e){//无该字段时,直接不处理
				e.printStackTrace();
			}
		}
		if(resolveInject)
			domain = modelContainer.enhanceModel(domain);
		return domain;
	}
	
	public <T> List<T> convertMapToDomain(List<Map<String,Object>> list,
			Class<T> domainClass,boolean resolveInject) throws Exception{
		List<T> domainList = new ArrayList<T>();
		for(Map<String,Object> m:list){
			domainList.add(convertMapToDomain(m, domainClass,resolveInject));
		}
		return domainList;
	}
	/**
	 * 根据工作站id构造Hessian服务访问路径(此处是指平台的工作站)
	 * @param stationId
	 * @param domainClass
	 * @return
	 */
	public String buildUrl(Long stationId,Class<?> domainClass){
		Station station = stationService.getStation(stationId);
		return buildUrl(station,domainClass);
	}
	
	public String buildUrl(Long stationId,HessianServiceType type) throws ErrorServerException{
		Station station = stationService.getStation(stationId);
		return buildUrl(station,type);
	}
	
	public String buildUrl(Station station,HessianServiceType type) throws ErrorServerException{
		if(!station.getAccessable()){//!Ping.ping(station.getIp())){
			throw new ErrorServerException("can't access the target server");
		}
		return "http://"+station.getIp()+":"+station.getPort()+"/"
				+appConfig.getConfig("station.app")
				+"/"+appConfig.getConfig("remote.servlet")
				+appConfig.getHessianServiceEndPoint(type);
	}
	
	/**
	 * 根据工作站构造Hessian服务访问路径(此处指平台工作站)
	 * @param station
	 * @param domainClass
	 * @return
	 */
	public String buildUrl(Station station,Class<?> domainClass){
		return "http://"+station.getIp()+":"+station.getPort()+"/"
				+appConfig.getConfig("station.app")
				+"/"+appConfig.getConfig("remote.servlet")
				+appConfig.getHessianServiceEndPoint(HessianServiceType.valueOf(domainClass.getSimpleName()));
	}
	
	/**
	 * 根据ip和port构造Hessian服务访问路径
	 * @param ip
	 * @param port
	 * @param domainClass
	 * @return
	 */
	public String buildUrl(String ip,String port,Class<?> domainClass){
		return "http://"+ip+":"+port+"/"
				+appConfig.getConfig("station.app")
				+"/"+appConfig.getConfig("remote.servlet")
				+appConfig.getHessianServiceEndPoint(HessianServiceType.valueOf(domainClass.getSimpleName()));
	}
}
