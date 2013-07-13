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
import com.greejoy.dlssjk.hessian.caller.PointHessianServiceCaller;

/**
 * 监控点action
 * @author Gavin
 * @version 1.0
 * @date 2013-3-16
 */
@Controller
@RequestMapping("/points")
public class PointAction {

	@Resource
	private PointHessianServiceCaller pointHessianServiceCaller;
	
	@RequestMapping("/briefInfo")
	@ResponseBody
	public List<Map<String,Object>> getPointBriefInfo(@RequestParam("stationId")Long stationId,
			@RequestParam("monitorId")int monitorId) throws MalformedURLException, ErrorServerException{
		return pointHessianServiceCaller.getPointBriefInfo(stationId, monitorId);
	}
	
}
