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
$(function () {
	$("button").button();
	 $("#queryType").multiselect({
		 multiple: false,
		 selectedText: "选择#项,共 #项 ",
		 noneSelectedText: '请选择',
		 checkAllText:'全选',
		 uncheckAllText: '全不选',
		 click:changeQueryType
		}); 
	if(!stationId){
		alert("请先选择站点");
		$(".currentstation",window.parent.document).trigger("click");
		return false;
	}
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
		
});

//加载监控点下拉框,由云台下拉选择触发
function showPoints(event,ui){
	app.getJsonData(contextPath+"/points/briefInfo",{stationId:stationId,monitorId:ui.value}).pipe(function(points){
		app.selector({
			html:$("#commonSelect").html(),
			data:{data:points},
			targetSelector:"#pointSelect",
			multiple:false,
			click:showAreas
			/* beforeclose:showAreas */});
		//清空下拉区域下拉框
		//showAreas(event,{value:-1});
	});
}

//加载监控区域拉框,由监控点下拉选择触发
function showAreas(event,ui){
	/* var points = $(this).multiselect("getChecked");
	var pointIds =[];
	$.each(points,function(index,e){
		pointIds.push($(e).val());
	}); */
	jQuery.ajaxSettings.traditional = true; 
	app.getJsonData(contextPath+"/areas/briefInfo",{stationId:stationId,pointId:ui.value}).pipe(function(areas){
		app.selector({
			html:$("#commonSelect").html(),
			data:{data:areas},
			targetSelector:"#areaSelect"});
	}); 
}

function changeQueryType(event,ui){
	if(ui.value==1)
		$("#timeDiv").html('<input type="text" id="time" class="Wdate" onclick="WdatePicker()">');
	else if(ui.value==2)
		$("#timeDiv").html("<input type=\"text\" id=\"time\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt:'yyyy-MM'})\">");
	else if(ui.value==3)
		$("#timeDiv").html("<input type=\"text\" id=\"time\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt:'yyyy'})\">");
}

function search(){
	var areas = $("select","#areaSelect").multiselect("getChecked");
	if(app.assertZero(areas.length,"请选择监控区域"))
		return false;
	var areaIds =[];
	$.each(areas,function(index,e){
		areaIds.push($(e).val());
	});
	var queryType = $("#queryType").multiselect("getChecked");
	queryType=$(queryType).val();
	queryType=(queryType==1?'D':(queryType==2?'M':'Y'));
	jQuery.ajaxSettings.traditional = true; 
	app.getJsonData(contextPath+"/report/temperatureChart/search",{
		type:'Post',
		"areaIds":areaIds,
		queryType:queryType,
		date:$("#time").val(),
		stationId:stationId}).pipe(function(json){
			var data=new Array() ,tempdata=new Array(),ambTemp=new Array();
			var temp = json[0].area_id;
			var areaname = json[0].area_name;
		    $.each(json,function(index,e){
		    	if(temp==e.area_id){
		    		tempdata.push([e.time,e.max_temp]);
		    		ambTemp.push([e.time,e.taskpointlog_minTemp]);
		    	}else{
		    		if(data.length==0){
		    			data.push({name:"环境温度",data:ambTemp});
		    			ambTemp=new Array();
		    		}
		    		data.push({name:e.area_name,data:tempdata});
		    		tempdata = new Array();
		    		tempdata.push([e.time,e.max_temp]);
		    	}
		    		temp = e.area_id;
		    		areaname = e.area_name;
		    });
		    if(data.length==0){
    			data.push({name:"环境温度",data:ambTemp});
    		}
			data.push({name:areaname,data:tempdata});
			console.log(data);
			 chart=  new Highcharts.Chart({
		            chart: {
		                renderTo: 'container',
		                type: 'spline',
		                marginRight: 130,
		                marginBottom: 25,
		                zoomType: 'x'
		            },
		            title: {
		                text: '温度报表',
		                x: -20 //center
		            },
		            xAxis: {
		            	 type: 'datetime',
	            		 dateTimeLabelFormats: { // don't display the dummy year
	            	        day: '%m-%e ',
							month:'%y-%m'
	                     }
		            },
		            yAxis: {
		                title: {
		                    text: 'Temperature (°C)'
		                },
		                plotLines: [{
		                    value: 0,
		                    width: 1,
		                    color: '#808080'
		                }]
		            },
		            tooltip: {
		                formatter: function() {
		                        return '<b>'+ this.series.name +'</b><br/>'+
		                        new Date(this.x).toLocaleString() +'>>'+ this.y +'°C';
		                }
		            },
		            legend: {
		                layout: 'vertical',
		                align: 'right',
		                verticalAlign: 'top',
		                x: -10,
		                y: 100,
		                borderWidth: 0
		            },
		            series: data
		        });
		});
};
</script>
</head>
<body>

	<div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">监控曲线</span>
    </div>
   <div>
    <div style="display: inline-block;">
  	  <select id="queryType">
  		<option value="1">按日</option>
  		<option value="2">按月</option>
  		<option value="3">按年</option>
  	  </select>
  	</div>
  	<div style="display: inline-block;"><span class="span3">时间</span>
  		<div style="display: inline-block;" id="timeDiv">
  			<input type="text" id="time" class="Wdate" onclick="WdatePicker()">
  		</div>
  	</div>
  </div>
  <div class="selectors">
  	<div class="selector"><span class="span3">云台</span><div id="monitorSelect"></div></div>
  	<div class="selector" ><span class="span3">监控点</span><div id="pointSelect"><select></select></div></div>
  	<div class="selector" ><span class="span3">监控区域</span><div id="areaSelect"><select></select></div></div>
  	<div class="selector"><button type="button" onclick="search()">搜索</button></div>
  </div>
<div id="container"></div>

<div id="commonSelect" style="display: none" >
		<select multiple="multiple" style="display:none;">
			{{#each data}}
			<option value="{{id}}">{{name}}</option>
			{{/each}}
		</select>
	</div>
</body>
</html>