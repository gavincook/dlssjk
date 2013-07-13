package com.greejoy.dlssjk.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.greejoy.dlssjk.hessian.HessianServiceType;

/**
 * 配置类(配置文件:app.config)
 * @author Gavin
 * @version 1.0
 * @date 2013-3-4
 */
@Component
public class AppConfig {

	private  Map<String,String> configMap = new HashMap<String,String>();
	
	public AppConfig() throws IOException{
		Properties properties = new Properties();
		properties.load(this.getClass().getClassLoader().getResourceAsStream("app.config"));
		for(Object key:properties.keySet()){
			configMap.put(key.toString(), properties.get(key).toString());
		}
	}
	
	public String getConfig(String key){
		return configMap.get(key);
	}
	
	/**
	 * 获取Hessian服务的URI
	 * @param type
	 * @return
	 */
	public String getHessianServiceEndPoint(HessianServiceType type){
		return configMap.get("hessian.service."+type);
	}
}
