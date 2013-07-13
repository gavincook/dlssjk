package com.greejoy.dlssjk.action;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.TaskAreaLogHessianServiceCaller;
import com.greejoy.dlssjk.service.MonitorRecordService;
import com.greejoy.rbac.domain.annotation.MenuMapping;
import com.greejoy.utils.MessageUtils;
import com.greejoy.utils.ParamUtils;

/**
 * 报表类
 * @author Gavin
 * @version 1.0
 * @date 2013-3-16
 */
@Controller
@RequestMapping("/report")
public class ReportAction {

	@Resource
	private MonitorRecordService monitorRecordService;
	@Resource
	private TaskAreaLogHessianServiceCaller taskAreaLogHessianServiceCaller;
	
	@MenuMapping(name="温度报表",url="/report/temperatureReport",code="200005",parentCode="200000")
	@RequestMapping("/temperatureReport")
	public ModelAndView temparyReport(){
		return new ModelAndView("pages/dlssjk/temperatureReport");
	}
	
	/**
	 * 
	 * @param pointIds
	 * @param stationId
	 * @return
	 * @throws MalformedURLException
	 * @throws ErrorServerException 
	 */
	@RequestMapping("/temperatureReport/search")
	@ResponseBody
	public Map<String,Object> temparyReportSearch(@RequestParam("points")Integer[] pointIds,
			@RequestParam("stationId") Long stationId,@RequestParam("date")String date,
			@RequestParam("queryType")String type) throws MalformedURLException, ErrorServerException{
		Map<String,Object> m =  MessageUtils.toFlexGridPager(
				monitorRecordService.getAreaLogsByPoints(stationId, pointIds,date,type),1,100L);
		m.put("logtime", monitorRecordService.getArealogsDate(stationId,date, type));
		return m;
				
	}
	
	@MenuMapping(name="监控曲线图",url="/report/temperatureChart",code="200006",parentCode="200000")
	@RequestMapping("/temperatureChart")
	public String temperatureChart(){
		return "pages/dlssjk/temperatureChart";
	}
	
	@RequestMapping("/temperatureChart/search")
	@ResponseBody
	public List<Map<String,Object>> temperatureChartSearch(@RequestParam("areaIds")Integer[] areaIds,
			@RequestParam("stationId") Long stationId,@RequestParam("date")String date,HttpServletRequest request,
			@RequestParam("queryType")String type) throws MalformedURLException, ErrorServerException{
		Map<String,Object> m = ParamUtils.getParamsMap(request);
		m.put("groupby", "areatable.area_id,time");
		m.put("time", date);
		m.put("type", type);
		m.put("ps", Integer.MAX_VALUE);
		Collection<String> areas = new ArrayList<String>();
		for(Integer id:areaIds){
			areas.add(id+"");
		}
		m.put("areaIds", areas);
		return taskAreaLogHessianServiceCaller.getTaskAreaLogs(stationId,m);
	}
}
