<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="common/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/ui/layout/jquery.layout-latest.js"></script>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jquery/ui/layout/layout-default-latest.css" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/pages/index.js"></script>
<title>主页</title>
<style type="text/css">
.stationlist li {
	color: rgb(68, 165, 230);
	font-size: 20px;
	margin: 5px 10px 5px -30px;
	list-style: none;
	cursor: pointer;
}

.stationlist li.active {
	background-color: rgb(68, 165, 230);
	color: white;
}

.stationdialog {
	position: absolute;
	width: 500px;
	left: 50%;
	margin-left: -250px;
	top: 120px;
	background-color: white;
	z-index: 999;
}

.stationdialog .btns {
	float: right;
}

.currentstation {
	height: 20px;
	margin-right: 20px;
	font-size: 20px;
	color: #121647;
	cursor: pointer;
}

#sound{
	height:0;
}

.logo{
	float:left;
}
</style>
</head>
<body>
	<div id="stationselect" style="display: none">
		<div
			style="position: absolute; width: 100%; height: 100%; background-color: black; z-index: 999; opacity: 0.4;">
		</div>

		<div class="stationdialog">
			<ul id="stationlist" class="stationlist">
			</ul>
			<div class="btns">
				<button type="button" class="confirm">确认</button>
				<button type="button" class="return">返回</button>
			</div>
		</div>
	</div>
	<iframe id="main" name="main" class="ui-layout-center" width="100%"
		height="600" frameborder="0" scrolling="auto"
		src="${pageContext.request.contextPath}/user"></iframe>
	<!-- http://ui.operamasks.org/demos/grid/simple.html -->
	
	<div class="ui-layout-north" style="padding: 3px">
	
	<div id="sound" >
	<object style="height:0px;width:0px;" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="0" height="0" id="asound" align="absmiddle">
			<param name="allowScriptAccess" value="sameDomain" />
			<param name="movie" value="${pageContext.request.contextPath}/flashsound/asound.swf" /><param name="quality" value="high" /><embed src="${pageContext.request.contextPath}/flashsound/asound.swf" quality="high" width="0" height="0" name="asound" align="absmiddle" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" />
	</object>
</div>
		<div
			style="background:url('${pageContext.request.contextPath}/css/images/bg.png');width:100%;height:100%;overflow:hidden;text-align:center;">
			<img class="logo" src="${pageContext.request.contextPath}/css/images/logo.png"/>
			<span
				style="color: #e6e6e6; font-family: serif; font-size: 40px; float: left;margin-top: 20px;">电力双视监控平台</span>
				<span id="alarmInfo" style="display:none;color: red;font-size: 20px;margin: 80px 0 0 30px;float:left;">
					<a style="text-decoration: none;color: red;" href="${pageContext.request.contextPath}/stations" target="main">有新的报警信息，请尽快查看!</a>
				</span>
			<div style="float: right; text-align: right;">
				<div>
					<span class="currentstation">当前站点: <span
						id="currentStation" data-id="${defaultStation.id}">${defaultStation.name}</span>
					</span>
				</div>
				<div style="margin-top: 60px;">
					<span style="margin-right: 40px; font-size: 20px; color: #121647;">欢迎您:
						${currentUser.userName} <span
						style="padding-left: 10px; color: #475699;">|</span> <a
						href="${pageContext.request.contextPath}/user/loginOut"
						style="text-decoration: none; color: #AC294E; padding-left: 10px;">退出</a>
					</span>
				</div>
			</div>
		</div>
	</div>
	<div class="ui-layout-west">

		<div id="menu">
			<c:forEach items="${menus}" var="s">
				<h3 id="${s.id}">${s.menuName}</h3>
				<ul></ul>
			</c:forEach>
		</div>
	</div>
</body>
</html>