<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dlssjk/all.css"/>
<script type="text/javascript">
	$(function(){
		$("#addStation").click(function(event){
			$("#addStationDialog").dialog({
				title:'增加工作站',
				modal:true,
				width:350,
				buttonAlign:'right',
				show:'flod',
				hide: "explode",
				buttons:[{
					text:"添加",
					click:function(event){
						$("form[name='stationForm']").submit();
					}
				}]
			});
		});
		
		$(".station").click(function(){
			$("#main",window.parent.document.body).attr("src","${pageContext.request.contextPath}/stations/"+$(this).attr("data-id"));
		});
		
		//编辑模式切换
		$("#editModel").click(function(){
			$(".close").toggleClass("hide");
		});
		
		//删除工作站
		$(".deletestation").click(function(event){
			if(confirm("确认删除该工作站? 删除后不能恢复.")){
			app.getJsonData(contextPath+"/stations/"+
					$(event.currentTarget).closest("div.station").attr("data-id"),{type:"Delete"}).pipe(function(){
						alert("操作成功");
						$(event.currentTarget).closest("div.station").hide("slow");
					});
			}
			app.stopPropagation(event);
		});
	});
	
</script>
<title>工作站</title>
</head>
<body>
	<div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">工作站列表</span>
		<div class="btns">
			<span><a href="javascript:void(0)" id="editModel">编辑模式</a></span>
			<span><a href="javascript:void(0)" id="addStation">添加站点</a></span>
		</div>
	</div>
	<c:forEach items="${stations}" var="station">
	<div class="station" data-id="${station.id}">
		<img alt="logo" src="${pageContext.request.contextPath}${station.logo}" onerror="javascript:this.src='${pageContext.request.contextPath}/logo/logo.png';">
		<div class="stationDetail">
			<div class="title">${station.name}</div>
			<div class="content">IP:${station.ip}</div>
			<div class="content">负责人:${station.contact}</div>
			<div class="content">联系电话:${station.telephone}</div>
		</div>
		<div class="close hide deletestation">&times;</div>
		<c:if test="${station.isAlarm}"><div class="alarmInfo">最后报警时间:${station.lastAlarm.alarm_time }</div></c:if>
		<c:if test="${!station.isAlarm}"><div class="noAlarm">暂无报警</div></c:if>
	</div>
	</c:forEach>
	
	<div class="hide" id="addStationDialog">
		<form name="stationForm" action="${pageContext.request.contextPath}/stations/initStation" class="dialogForm" method="post" enctype="multipart/form-data">
			<div><label>IP</label><input type="text" name="station.ip"/></div>
			<div><label>端口</label><input type="text" name="station.port"/></div>
		</form>
	</div>
</body>
</html>