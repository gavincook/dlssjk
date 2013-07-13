package com.greejoy.dlssjk.action;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greejoy.dlssjk.exception.ErrorServerException;
import com.greejoy.dlssjk.hessian.caller.AlarmHessianServiceCaller;
import com.greejoy.dlssjk.hessian.caller.TaskAreaLogHessianServiceCaller;
import com.greejoy.dlssjk.service.AlarmService;
import com.greejoy.rbac.domain.annotation.MenuMapping;
import com.greejoy.utils.MessageUtils;
import com.greejoy.utils.ParamUtils;

/**
 * @author Gavin
 * @version 1.0
 * @date 2013-3-20
 */
@Controller
@RequestMapping("/record")
public class RecordAction {

	@Resource
	private TaskAreaLogHessianServiceCaller taskAreaLogHessianServiceCaller;
	@Resource
	private AlarmHessianServiceCaller alarmHessianServiceCaller;
	@Resource
	private AlarmService alarmService;
	/**
	 * 监控记录
	 * @return
	 */
	@MenuMapping(name="监控记录",url="/record/monitor",code="200003",parentCode="200000")
	@RequestMapping("/monitor")
	public ModelAndView monitorRecord(){
		return new ModelAndView("pages/dlssjk/monitorRecord");
	}
	
	@RequestMapping("/monitor/search")
	@ResponseBody
	public Map<String,Object> getMonitorRecord(@RequestParam("stationId")Long stationId,HttpServletRequest request
			) throws MalformedURLException, NumberFormatException, ErrorServerException{
		String time =request.getParameter("time");
		String[] areaIds = request.getParameterValues("areaIds");
		Map<String,Object> m = ParamUtils.getParamsMap(request);
		if(time!=null&&time!="")
		  m.put("time", time);
		Collection<String> areas = new ArrayList<String>();
		if(areaIds!=null){
			for(String id:areaIds){
				areas.add(id);
			}
			m.put("areaIds", areas);
		}
		return MessageUtils.toFlexGridPager(taskAreaLogHessianServiceCaller.getTaskAreaLogs(stationId,m),
				Integer.parseInt(m.get("cp").toString()), taskAreaLogHessianServiceCaller.getTaskAreaLogsCounts(stationId, m));
	}
	
	/**
	 * 报警记录
	 * @return
	 */
	@MenuMapping(name="报警记录",url="/record/alarm",code="200004",parentCode="200000")
	@RequestMapping("/alarm")
	public ModelAndView alarmRecord(){
		return new ModelAndView("pages/dlssjk/alarmRecord");
	}
	
	
	@RequestMapping("/alarm/search")
	@ResponseBody
	public Map<String,Object> getAlarmRecord(@RequestParam("stationId")Long stationId,HttpServletRequest request) throws MalformedURLException, NumberFormatException, ErrorServerException{
		Map<String,Object> m = ParamUtils.getParamsMap(request);
		return MessageUtils.toFlexGridPager(alarmHessianServiceCaller.getAlarms(stationId, m),
				Integer.parseInt(m.get("cp").toString()), alarmHessianServiceCaller.getAlarmsCounts(stationId, m));
	}
	
	@RequestMapping("/alarm/files/{stationId}/{alarmId}")
	public void getAlarmFile(@PathVariable("stationId")Long stationId,@PathVariable("alarmId")int alarmId,HttpServletResponse response) throws Exception{
		byte[] data = alarmHessianServiceCaller.getAlarmFile(alarmId, stationId);
		OutputStream out= response.getOutputStream();
		response.setContentType("jpeg/gif");
		out.write(data);
		out.close();
	}
	
	@RequestMapping("/alarm/isAlarm")
	@ResponseBody
	public Map<Object,Object> isAlarm() throws MalformedURLException, ErrorServerException{
		Map<Object,Object> m = new HashMap<Object,Object>();
		m.put("isAlarm", alarmService.isAlarm());
		m.putAll(alarmService.showAlarm());
		return m;
	}
}
