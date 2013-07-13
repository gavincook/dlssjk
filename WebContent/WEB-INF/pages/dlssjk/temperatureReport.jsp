<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dlssjk/all.css"/>
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

.tempTable{
	border: solid 1px rgb(146, 196, 219);
	border-collapse: collapse;
	white-space:nowrap; 
	width:100%;
	overflow-x: auto;
	display:block;
}

.tempTable tr{
	border: solid 1px rgb(146, 196, 219);
	line-height: 30px;
	text-align: center;
}

.tempTable tr td{
	padding:0 5px;
}
.tempTable tr th{
	border: solid 1px rgb(146, 196, 219);
}
.greybackground{
	background-color: rgb(225, 227, 230);
}

.tableData{
	overflow-y:auto;
}


</style>
<title>温度报表</title>
<script type="text/javascript">
var stationId = $("#currentStation",window.parent.document).attr("data-id");
	$(function(){
		$("button").button();
		app.selector("div:not(#commonSelect)");
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
		//加载云台下拉框
		 app.getJsonData(contextPath+"/monitors/briefInfo",{stationId:stationId}).pipe(function(monitors){
			app.selector({
				html:$("#commonSelect").html(),
				data:{data:monitors},
				targetSelector:"#monitorSelect",
				multiple:false,
				click:showPoints});
		});
		
		 $(".pagination span:not(.currentPage)").live("click",function(event){
				search.call(this,$(event.target).attr("pageIndex"));
			});
	});
	
	//加载监控点下拉框,由云台下拉选择触发
	function showPoints(event,ui){
		app.getJsonData(contextPath+"/points/briefInfo",{stationId:stationId,monitorId:ui.value}).pipe(function(points){
			app.selector({
				html:$("#commonSelect").html(),
				data:{data:points},
				targetSelector:"#pointSelect",
				multiple:true,
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
	
	function search(pageIndex){
		var points = $("select","#pointSelect").multiselect("getChecked");
		var queryType = $("#queryType").multiselect("getChecked");
		queryType=$(queryType).val();
		queryType=(queryType==1?'H':(queryType==2?'D':'M'));
		if(app.assertZero(points.length,"请选择监控点"))
			return false;
		var pointIds =[];
		$.each(points,function(index,e){
			pointIds.push($(e).val());
		});
		jQuery.ajaxSettings.traditional = true; 
		app.getJsonData(contextPath+"/report/temperatureReport/search",{
			type:'Post',
			"points":pointIds,
			queryType:queryType,
			date:$("#time").val(),
			stationId:stationId}).pipe(function(json){
				json.layout = {height:$(window).height()-120};
				app.render($("#tempTableTemplate").html(),{data:json},"#tempTable",true);
	    });
	};
	
	function changeQueryType(event,ui){
		if(ui.value==1)
			$("#timeDiv").html('<input type="text" id="time" class="Wdate" onclick="WdatePicker()">');
		else if(ui.value==2)
			$("#timeDiv").html("<input type=\"text\" id=\"time\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt:'yyyy-MM'})\">");
		else if(ui.value==3)
			$("#timeDiv").html("<input type=\"text\" id=\"time\" class=\"Wdate\" onclick=\"WdatePicker({dateFmt:'yyyy'})\">");
	}
	Handlebars.registerHelper('render', function(data) {
		 var tr = "";
		 $.each(data.logtime,function(i,logtime){
			 tr+="<tr "+((i%2==0)?"class='greybackground'":"")+" ><td>"+logtime.logdate+"</td>"; 
			 $.each(data.rows,function(index1,point){
				 if(point.areas.length>0){
				   $.each(point.areas,function(index2,area){
					  if(area.arealogs[0]){
						  if(area.arealogs[0].formate_time==logtime.logdate){
							  if(index1==0&&index2==0){
								  tr+="<td>"+ area.arealogs[0].taskpointlog_minTemp+"&#176;C</td>";
							  }
						  		tr+="<td>"+ area.arealogs[0].max_temp+"&#176;C</td>";
						  		area.arealogs.shift();
						  }else{
							  tr+="<td>暂无记录&nbsp;</td>"; 
						  }
					  }else{
						  tr+="<td>暂无记录&nbsp;</td>"; 
					  }
				   });
				 }
				 else{
					  tr+="<td>暂无记录</td>"; 
				 }
			 });
			 tr+="</tr>";
		 });
		  return new Handlebars.SafeString(tr);
		});
	
	
</script>
</head>
<body>
	<div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">温度报表</span>
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
  	<!-- <div class="selector" ><span class="span3">监控区域</span><div id="areaSelect"><select></select></div></div> -->
  	<div class="selector"><button type="button" onclick="search()">搜索</button></div>
  </div>
  
  
	<div id="commonSelect" style="display: none" >
		<select multiple="multiple" style="display:none;">
			{{#each data}}
			<option value="{{id}}">{{name}}</option>
			{{/each}}
		</select>
	</div>
	
	<div id="tempTable">
		<span style="float:left;margin:100px 0  0 400px;font-size: 20px;color:rgb(47, 149, 206);">请选择条件进行查询</span>
	</div>
	
	<script id="tempTableTemplate" type="text/x-handlebars-template">
	<div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">温度报表</span>
	</div>
	<table class="tempTable" style="height:{{data.layout.height}}px">
	 <tbody class="tableHeader">	  
	  <tr>
		<th rowspan="2">时间</th>
		<th rowspan="2">环境温度</th>
		 {{#each data.rows}}
		 <th colspan="{{areas.length}}">{{name}}</th>
		 {{/each}}
      </tr>
	  <tr>
		 {{#each data.rows}}
		   {{#if areas.length}}	
		     {{#each areas}}
		     <th>{{name}}</th>
             {{/each}}
		   {{else}}
			 <th></th>
           {{/if}}
		 {{/each}}
      </tr>
      </tbody>
	  <tbody>
		{{render data}}
	  </tbody>
	</table>
	</script>
</body>
</html>