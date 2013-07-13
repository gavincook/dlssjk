package com.greejoy.dlssjk.action;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.MonitorHessianServiceCaller;
import com.greejoy.dlssjk.service.StationService;
import com.greejoy.rbac.domain.annotation.LoginRequired;
import com.greejoy.rbac.domain.annotation.MenuMapping;

/**
 * 监控记录
 * @author Gavin
 * @version 1.0
 * @date 2013-3-14
 */
@Controller
public class MonitorAction {

	@Resource
	private MonitorHessianServiceCaller monitorHessianServiceCaller;
	
	@Resource
	private StationService stationService;
	@RequestMapping("/monitors/briefInfo")
	@ResponseBody
	public List<Map<String,Object>> getMonitorsBriefInfo(@RequestParam("stationId")Long stationId) throws MalformedURLException, ErrorServerException{
		return monitorHessianServiceCaller.getMonitorsBriefInfo(stationId);
	}
	
	/**
	 * 获取远程监控数据
	 * @param stationId
	 * @return
	 * @throws MalformedURLException
	 * @throws ErrorServerException 
	 */
	@RequestMapping("/monitors/operate/getData")
	@ResponseBody
	public Map<String,Object> operate(@RequestParam("stationId")Long stationId,@RequestParam("monitorId")Long monitorId) throws MalformedURLException, ErrorServerException{
		Map<String,Object> monitor = monitorHessianServiceCaller.getMonitor(stationId,monitorId);
		Station station = stationService.getStation(stationId);
		Map<String,Object> m = new HashMap<String,Object>();
		m.put("monitorIp", monitor.get("ip"));
		m.put("stationIp", station.getIp());
		return m;
	}
	
	/**
	 * 远程监控
	 * @param stationId
	 * @return
	 * @throws MalformedURLException
	 */
	@LoginRequired
	@MenuMapping(name="远程监控",url="/monitors/operate",code="200008",parentCode="200000")
	@RequestMapping("/monitors/operate")
	public ModelAndView showOperatePage() throws MalformedURLException{
		return new ModelAndView("pages/dlssjk/remoteOperate/remoteOperate");
	}
}
