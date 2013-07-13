package com.greejoy.dlssjk.service.impl;

import java.net.MalformedURLException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.greejoy.dlssjk.domain.Alarm;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.service.AlarmService;
import com.reeham.component.ddd.model.ModelContainer;
import com.reeham.component.ddd.model.ModelUtils;
/**
 * @author Gavin
 * @version 1.0
 * @date 2013-3-30
 */
@Service
public class AlarmServiceImpl implements AlarmService,BeanPostProcessor{

	@Resource
	private ModelContainer modelContainer;
	
	@Override
	public boolean isAlarm() throws MalformedURLException, ErrorServerException{
		Alarm alarm = (Alarm) modelContainer.getModel(ModelUtils.asModelKey(Alarm.class, 1L));
		if(alarm==null){
			modelContainer.addModel(ModelUtils.asModelKey(Alarm.class, 1L), new Alarm());//默认存储一个Alarm的实体
		}
		return alarm.isAlarm();
	}


	@Override
	public Map<Long, Long> showAlarm() {
		Alarm alarm = (Alarm) modelContainer.getModel(ModelUtils.asModelKey(Alarm.class, 1L));
		if(alarm==null){
			modelContainer.addModel(ModelUtils.asModelKey(Alarm.class, 1L), new Alarm());//默认存储一个Alarm的实体
		}
		return alarm.showAlarm();
	}


	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}


	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		modelContainer.addModel(ModelUtils.asModelKey(Alarm.class, 1L), new Alarm());//默认存储一个Alarm的实体
		return bean;
	}

}
