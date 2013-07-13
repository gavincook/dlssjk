package com.greejoy.dlssjk.action;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.AreaHessianServiceCaller;

@Controller
@RequestMapping("/areas")
public class AreaAction {

	@Resource
	private AreaHessianServiceCaller areaHessianServiceCaller;
	
	@RequestMapping("/briefInfo")
	@ResponseBody
	public List<Map<String,Object>> getMonitorsBriefInfo(@RequestParam("stationId")Long stationId,
			@RequestParam("pointId")int pointId) throws MalformedURLException, ErrorServerException{
		return areaHessianServiceCaller.getAreaBriefInfo(stationId,pointId);
	}
	
}
