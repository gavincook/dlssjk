<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控记录</title>
<style type="text/css">
.alarm td{
background: rgb(255, 211, 211);
}
.general td{
background: rgb(255, 255, 255);
}
.serious td{
background: rgb(116, 166, 221);
}
.emergent td{
background: rgb(226, 124, 133);
}
</style>
<script type="text/javascript">
var stationId = $("#currentStation",window.parent.document).attr("data-id");
	$(function(){
		$(".alarmStatus").live("click",function(event){
			var alarmId=$(this).attr("data-id");
			$("#alarmFile").dialog({
				modal:true,
				title:'报警图片',
				buttonAlign:'center',
				show:'flod',
				hide: "explode",
				width:600,
				height:400,
				create:function(){
					$("#snap").attr("src",contextPath+"/record/alarm/files/"+stationId+"/"+alarmId);
				},
				buttons:[
				         {text:'取消',click:function(){
				        	 $("#userForm").reset();
				        	 $(this).dialog("close");	 
				         }}
				         ]
			
			});
		});
		
		if(!stationId){
			alert("请先选择站点");
			$(".currentstation",window.parent.document).trigger("click");
			return false;
		}
		$("#monitorRecord").flexigrid({
			url: contextPath+'/record/alarm/search',
			dataType: 'json',
			params:[
			{name:'stationId',
				value:stationId}
			],
			singleSelect:true,
			colModel : [
				{display: '时间', name : 'alarm_time', width : "12%", sortable : true, align: 'center'},
				{display: '报警设置温度', name : 'alarm_temp', width : "12%", sortable : true, align: 'center'},
				{display: '报警实际温度', name : 'alarm_realTemp', width : "12%", sortable : true, align: 'center'},
				{display: '报警级别', name : 'alarm_type', width :"15%", sortable : false, align: 'center',renderer:function(row,td,tr){
					var status = "<a data-id=\""+row.id+"\" class=\"alarmStatus\" href=\"javascript:void(0)\">";
					if(row.alarm_type==0){
						 $(tr).toggleClass("general").removeClass("erow");
						 status += "一般";
					}else if(row.alarm_type==1){
						 $(tr).toggleClass("serious").removeClass("erow");
						status+="严重";
					}else if(row.alarm_type==2){
						 $(tr).toggleClass("emergent").removeClass("erow");
						status+="紧急";
					}
					return status+"</a>";
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
			height:  $(window).height()-123
		});  		
	});
</script>
</head>
<body>
<table id="monitorRecord"></table>
<div class="hide" id="alarmFile">
<img id="snap"/>
</div>
</body>
</html>