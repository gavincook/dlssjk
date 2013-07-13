package com.greejoy.dlssjk.action;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greejoy.dlssjk.domain.Station;
import com.greejoy.dlssjk.hessian.caller.StationHessianServiceCaller;
import com.greejoy.dlssjk.service.StationService;
import com.greejoy.rbac.domain.annotation.MenuMapping;
import com.greejoy.support.spring.annotation.FormParam;
import com.greejoy.utils.MessageUtils;
import com.greejoy.utils.ParamUtils;

/**
 * 工作站控制类
 * 
 * @author Gavin
 * @version 1.0
 * @create 2013-1-15
 * @modified 2013-3-12
 */
@Controller
@RequestMapping("/stations")
public class StationAction {

	@Resource
	private StationService stationService;
	@Resource
	private StationHessianServiceCaller stationHessianServiceCaller;

	/**
	 * 显示所有的站点信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("")
	@MenuMapping(name="工作站点",url="/stations",code="200002",parentCode="200000")
	public ModelAndView stations(HttpServletRequest request) {
		return new ModelAndView("pages/dlssjk/stations", "stations", stationService.getStations(ParamUtils
				.getDefaultParamMap()));
	}

	/**
	 * 根据ip和port从远程站点上读取数据来初始化平台工作站信息
	 * 
	 * @param station
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/initStation")
	public void initStation(@FormParam("station") Station station, HttpServletRequest request,
			HttpServletResponse response)throws Exception {
		List<Station> stationsOnStation = stationHessianServiceCaller.getStationsByIp(station.getIp(),
				station.getPort() + "");
		for (Station stationOnStation : stationsOnStation)
			stationOnStation.saveOrUpdate(true);//同步保存
		
			response.sendRedirect(request.getContextPath()+"/stations");
	}

	/**
	 * 显示工作站详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ModelAndView station(@PathVariable("id") Long id,HttpServletRequest request) {
		return new ModelAndView("pages/dlssjk/station", "station", stationService.getStation(id));
	}
	
	/**
	 * 删除工作站
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> deleteStation(@PathVariable("id") Long id,HttpServletRequest request) {
		 stationService.delete(id);
		 return MessageUtils.getMapMessage(true);
	}
	
	/**
	 * 获取默认的工作站(暂时取第一个)
	 * @param request
	 * @return
	 */
	@RequestMapping("/defaultstation")
	@ResponseBody
	public Map<String,Object> getDefaultStation(HttpServletRequest request){
		List<Station> stations = stationService.getStations(ParamUtils.getDefaultParamMap());
		Map<String,Object> m = MessageUtils.getMapMessage(true);
		if(stations.size()>0){
			m.put("name", stations.get(0).getName());
			m.put("id", stations.get(0).getId());
		}
		return m;
	}
	/**
	 * 获取工作站下拉数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/getStations")
	@ResponseBody
	public List<Map<String,Object>> getStations(HttpServletRequest request){
		return MessageUtils.covertDomain2Map(stationService.getStations(ParamUtils.getDefaultParamMap()),"id","name");
	}
	
	/**
	 * 更新工作站
	 * @return
	 */
	@RequestMapping("/updateStation")
	public ModelAndView updateStation(@FormParam("station") Station station){
		Station oldStation = stationService.getStation(station.getId());	
		oldStation.setLogo(station.getLogo());
		oldStation.saveOrUpdate(true);
		return new ModelAndView("pages/dlssjk/station", "station", oldStation);
	}
}
