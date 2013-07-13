package com.greejoy.dlssjk.domain;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.greejoy.base.domain.BaseDomain;
import com.greejoy.dlssjk.domain.repository.StationEvent;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.AlarmHessianServiceCaller;
import com.greejoy.utils.ParamUtils;
import com.reeham.component.ddd.annotation.Model;

/**
 * 工作站点的实体域
 * @author Gavin
 * @version 1.0
 * @date 2013-2-12
 */
@Model
public class Station extends BaseDomain{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6678931829716178066L;
	
	private String name;
	
	private String ip;
	
	private Integer port;
	
	private String gps;
	
	private String description;
	
	private String address;
	
	private String contact;
	
	private String logo;

	private String telephone;
	
	private Integer stationId;
	
	private Boolean isAlarm;
	
	private Map<String,Object> lastAlarm;
	
	private Long lastConnectTime;
	
	private Boolean accessable = true;
	
	@Resource
	private StationEvent stationEvent;
	@Resource
	private AlarmHessianServiceCaller alarmHessianServiceCaller;
	
	public void saveOrUpdate(boolean sync){//同步或异步操作
		if(sync)
			stationEvent.saveOrUpdate(this).getEventResult();
		else
			stationEvent.saveOrUpdate(this);
	}
	
	
	/*****************************start getter and setter*****************************/
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * @return the gps
	 */
	public String getGps() {
		return gps;
	}

	/**
	 * @param gps the gps to set
	 */
	public void setGps(String gps) {
		this.gps = gps;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the contact
	 */
	public String getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * @return the logo
	 */
	public String getLogo() {
		return logo;
	}

	/**
	 * @param logo the logo to set
	 */
	public void setLogo(String logo) {
		this.logo = logo;
	}

	/**
	 * @return the telephone
	 */
	public String getTelephone() {
		return telephone;
	}

	/**
	 * @param telephone the telephone to set
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	/**
	 * @return the stationId
	 */
	public Integer getStationId() {
		return stationId;
	}


	/**
	 * @param stationId the stationId to set
	 */
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}


	/**
	 * @return the isAlarm
	 * @throws MalformedURLException 
	 * @throws ErrorServerException 
	 */
	public Boolean getIsAlarm() {
		if(lastConnectTime==null||System.currentTimeMillis()-lastConnectTime>500000){
			lastConnectTime = System.currentTimeMillis();
			Map<String,Object> params = ParamUtils.getDefaultParamMap();
			params.put("ps", 1);
			params.put("time_order", "desc");
			try{
				List<Map<String,Object>> alarmRecords = alarmHessianServiceCaller.getAlarms(this.getId(), params);
				accessable = true;
				if(alarmRecords.size()>0){
					this.isAlarm = true;
					return true;
				}else{
					this.isAlarm = false;
					return false;
				}
			}catch (Exception e) {
				System.out.println("有异常");
				accessable = false;
				this.isAlarm = false;
				return false;
			}
		}
		return isAlarm;
	}

	
	/**
	 * @param isAlarm the isAlarm to set
	 */
	public void setIsAlarm(Boolean isAlarm) {
		this.isAlarm = isAlarm;
	}


	/**
	 * @return the alarmTime
	 * @throws MalformedURLException 
	 * @throws ErrorServerException 
	 */
	public Map<String,Object> getLastAlarm() throws MalformedURLException, ErrorServerException {
		if(isAlarm==null||!this.isAlarm){
			System.out.println("muyou.....");
			return null;
		}
		System.out.println("执行");
		Map<String,Object> params = ParamUtils.getDefaultParamMap();
		params.put("ps", 1);
		params.put("time_order", "desc");
		List<Map<String,Object>> alarmRecords = alarmHessianServiceCaller.getAlarms(this.getId(), params);
		if(alarmRecords.size()>0)
			return alarmRecords.get(0);
		else
			return null;
	
	}


	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setLastAlarm(Map<String,Object> lastAlarm) {
		this.lastAlarm = lastAlarm;
	}


	/**
	 * @return the accessable
	 */
	public Boolean getAccessable() {
		return accessable;
	}


	/**
	 * @param accessable the accessable to set
	 */
	public void setAccessable(Boolean accessable) {
		this.accessable = accessable;
	}
	
	/******************************end getter and setter*****************************/
	
}
