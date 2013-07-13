<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dlssjk/all.css"/>
<title>监控记录</title>
<style type="text/css">
.alarm td{
background: rgb(255, 211, 211);
}

.selectors{
	margin-top:5px;
	display:inline-block;
}
body{
	overflow-x:hidden; 
}
.selectors .selector{
	float:left;
	margin-right:10px;
}

.selectors .selector div{
	display:inline-block;
}

.selectors .selector span{
	display:inline-block;
	text-align: right;
	margin-right:10px;
}
</style>
<script type="text/javascript">
var stationId = $("#currentStation",window.parent.document).attr("data-id");
	$(function(){
		if(!stationId){
			alert("请先选择站点");
			$(".currentstation",window.parent.document).trigger("click");
			return false;
		}
		
		$("button").button();
		app.selector("div:not(#commonSelect)");
		//加载云台下拉框
		 app.getJsonData(contextPath+"/monitors/briefInfo",{stationId:stationId}).pipe(function(monitors){
			app.selector({
				html:$("#commonSelect").html(),
				data:{data:monitors},
				targetSelector:"#monitorSelect",
				multiple:false,
				click:showPoints});
		});
	
		$("#monitorRecord").flexigrid({
			url: contextPath+'/record/monitor/search',
			dataType: 'json',
			params:[
			{name:'stationId',
				value:stationId}
			],
			singleSelect:true,
			colModel : [
				{display: '时间', name : 'format_time', width : "10%", sortable : true, align: 'center'},
				{display: '环境温度', name : 'taskpointlog_minTemp', width : "10%", sortable : true, align: 'center'},
				{display: '最高温度', name : 'max_temp', width : "10%", sortable : true, align: 'center'},
				{display: '最低温度', name : 'min_temp', width :"10%", sortable : false, align: 'center'},
				{display: '状态', name : 'alarm_id', width :"10%", sortable : false, align: 'center',renderer:function(row,td,tr){
					if(row.alarm_id!=null)
						 $(tr).toggleClass("alarm").removeClass("erow");
					return row.alarm_id==null?"正常":"<a style=\"color:red;\" href=\""+row.alarm_id+"\">报警</a>";
				}},
				{display: '区域信息', name : 'area_name', width :"38%", sortable : false, align: 'center',renderer:function(row){
					return row.monitor_name+"——"+row.point_name+"——"+row.area_name;
				}}
				
				],
			sortname: "id",
			sortorder: "asc",
			usepager: true,
			title: '监控记录',
			useRp: true,
			rp: 15,
			showTableToggleBtn: true,
			height:  $(window).height()-190
		});  		
	});
	
	//加载监控点下拉框,由云台下拉选择触发
	function showPoints(event,ui){
		app.getJsonData(contextPath+"/points/briefInfo",{stationId:stationId,monitorId:ui.value}).pipe(function(points){
			app.selector({
				html:$("#commonSelect").html(),
				data:{data:points},
				targetSelector:"#pointSelect",
				multiple:false,
				click:showAreas});
			//清空下拉区域下拉框
			//showAreas(event,{value:-1});
		});
	}
	
	//加载监控区域拉框,由监控点下拉选择触发
	function showAreas(event,ui){
		app.getJsonData(contextPath+"/areas/briefInfo",{stationId:stationId,pointId:ui.value}).pipe(function(areas){
			app.selector({
				html:$("#commonSelect").html(),
				data:{data:areas},
				targetSelector:"#areaSelect"});
		}); 
	}
	
	function search(){
		var areas = $("select","#areaSelect").multiselect("getChecked");
		
		var areaIds =[];
		$.each(areas,function(index,e){
			areaIds.push({name:"areaIds",value:$(e).val()});
		});
		jQuery.ajaxSettings.traditional = true; 
		var params = areaIds||[];
		params.push({name:'stationId',value:stationId});
		params.push({name:"time",value:$("#time").val()});
		$("#monitorRecord").flexOptions({
			url: contextPath+'/record/monitor/search',
			dataType: 'json',
			newp:1,
			params:params
		}).flexReload();
		//$("#monitorRecord").flexReload();
	}
</script>
</head>
<body>

<div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">温度报表</span>
    </div>
  
  <div>
  	<div class="selector"><span class="span3" style="margin-right:225px">时间</span><input type="text" style="width: 225px;" id="time" onclick="WdatePicker()"></div>
  </div>
  <div class="selectors">
  	<div class="selector"><span class="span3">云台</span><div id="monitorSelect"></div></div>
  	<div class="selector" ><span class="span3">监控点</span><div id="pointSelect"><select></select></div></div>
  	<div class="selector" ><span class="span3">监控区域</span><div id="areaSelect"><select></select></div></div>
  	<div class="selector"><button type="button" onclick="search()">搜索</button></div>
  </div>


<table id="monitorRecord"></table>

<div id="commonSelect" style="display: none" >
	<select multiple="multiple" style="display:none;">
		{{#each data}}
		<option value="{{id}}">{{name}}</option>
		{{/each}}
	</select>
</div>

</body>
</html>