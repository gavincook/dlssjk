<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp"%>
<!DOCTYPE html>
<html style="background-color: #fff;">
<head>
<title>云台实时操控</title>
<script>
	var connectionOK = false;
	var stationId = $("#currentStation", window.parent.document)
			.attr("data-id");
	$(function() {
		var stationIp = null;
		var monitorIp = null;
		var irActiveX = document.getElementById("irActiveX");
		var ccdActiveX = document.getElementById("ccdActiveX");
		var stationPort = "9908";
		var monitorPort = "9905";
		if (!stationId) {
			alert("请先选择站点");
			$(".currentstation", window.parent.document).trigger("click");
			return false;
		}

		app.selector("div:not(#commonSelect)");
		//加载云台下拉框
		app.getJsonData(contextPath + "/monitors/briefInfo", {
			stationId : stationId
		}).pipe(function(monitors) {
			app.selector({
				html : $("#commonSelect").html(),
				data : {
					data : monitors
				},
				targetSelector : "#monitorSelect",
				multiple : false,
				click : connectMonitor
			});
		});

		function connectMonitor(event, ui) {
			var v = ui.value;
			app.getJsonData(contextPath + "/monitors/operate/getData", {
				stationId : stationId,
				monitorId : v
			}).pipe(
					function(data) {
						//	console.log(data);
						stationIp = data.stationIp;
						monitorIp = data.monitorIp;
						connect(data.stationIp, stationPort, data.monitorIp,
								monitorPort);
					});

			//window.self.location.href = contextPath+"/monitors/operate?id=" + v + "&_random=" + Math.random();
		}

		function connect(stationIP, stationPort, monitorIP, monitorPort) {
			/* 	var connectionStr = monitorIP + ":" + monitorPort + "&admin@a";
				var irConnectionStr = "IRZ:" + stationIP + ":" + stationPort;
				var ccdConnectionStr = "CCDZ:" + stationIP + ":" + stationPort;
				var ret = irActiveX["ocxConn"](connectionStr);//ir 工作站连接
			    alert(ret+" "+connectionStr + " " + irConnectionStr + " " + ccdConnectionStr);
				if(ret == 0)
					ret = ccdActiveX["ocxConn"](connectionStr);//ccd 工作站连接
			    alert(ret+connectionStr);
				if(ret == 0)
					ret = irActiveX["ocxVideoStart"](irConnectionStr);//ir视频
			    alert(ret+irConnectionStr);
				if(ret == 0)
					ret = ccdActiveX["ocxVideoStart"](ccdConnectionStr);//ccd 视频
			    alert(ret+ccdConnectionStr);
				if(ret != 0) {
					alert("连接工作站失败");
					return;
				}
				$("#ccdStatus").html("已连接");
				$("#irStatus").html("已连接");
				
			 */

			var strConn = stationIP + ":" + stationPort + "&admin@123356";
			var strStartIr = "IRV:" + monitorIP + ":" + monitorPort;
			var strStartCcd="CCDZ:" + monitorIP + ":" + monitorPort;
			var retVal = irActiveX.ocxConn(strConn);//连接到工作站，并登录；格式：工作站IP:端口号&用户名@密码
			retVal = irActiveX.ocxVideoStart(strStartIr);
			var retValccd = ccdActiveX.ocxConn(strConn);//连接到工作站，并登录；格式：工作站IP:端口号&用户名@密码
			retValccd = ccdActiveX.ocxVideoStart(strStartCcd);
			connectionOK = true;
		}

		$("#startButton").click(function() {
			connect(stationIp, stationPort, monitorIp, monitorPort);
		});
		
		$("#stopButton").click(function() {
			if(ActiveXocxIsOcxConn(irActiveX) == 1){
				irActiveX.ocxPTZControl(4, 0);
			}else if(ActiveXocxIsOcxConn(ccdActiveX) == 1){
				ccdActiveX.ocxPTZControl(4, 0);
			}
		});

		
		$("#leftButton").click(function() {
			if (connectionOK) {
				irActiveX.ocxPTZControl(5, 10);
				ccdActiveX.ocxPTZControl(5, 10);
				//irActiveX["ocxPTZControl"](5, 1);
				//ccdActiveX["ocxPTZControl"](5, 1);
			}
		});
		$("#rightButton").click(function() {
			if (connectionOK) {
				irActiveX.ocxPTZControl(7, 10);
				ccdActiveX.ocxPTZControl(7, 10);
			}
		});

		$("#upButton").click(function() {
			if (connectionOK) {
				irActiveX.ocxPTZControl(1, 16);
				ccdActiveX.ocxPTZControl(1, 16);
			}
		});
		$("#downButton").click(function() {
			if (connectionOK) {
				irActiveX.ocxPTZControl(3, 16);
				ccdActiveX.ocxPTZControl(3, 16);
			}
		});

		$("#ccdFarButton").click(function() {
			connectionOK && ccdActiveX.ocxPTZControl(10, 0);
		});

		$("#ccdNearButton").click(function() {
			connectionOK && ccdActiveX.ocxPTZControl(12, 0);
		});

		$("#irFarButton").click(function() {
			connectionOK && irActiveX.ocxPTZControl(10, 0);
		});

		$("#irNearButton").click(function() {
			connectionOK && irActiveX.ocxPTZControl(12, 0);
		});

		$("#irAutoButton").click(function() {
			connectionOK && irActiveX.ocxIRControl(2);
		});
	});
	
	function ActiveXocxIsOcxConn(ActiveXObj) {
		var nConn = ActiveXObj.ocxIsOcxConn();
	    return nConn;
	}
</script>


<style type="text/css" media="all">
#toolbar div select,input {
	margin: 0 8px;
}

#toolbar div input {
	-webkit-appearance: none;
}

.selectAll {
	width: 87px;
	text-align: center;
}

#InfoTable1 button {
	float: left;
	margin-left: 0px;
	margin-right: 10px;
	height: 25px;
	background: #D0E5F5;
}

#InfoTable1 div {
	float: left;
}

#InfoTable1 tr td div img {
	border: 1px solid #07b0ad;
}
</style>
</head>
<body>
	<div id="toolbar" class="space" style="height: auto;">
		<div style="float: left; width: 242px;">
			<p style="width: 150px; text-align: right; float: left;">请选择监控云台：</p>
			<div id="monitorSelect"></div>
		</div>
	</div>
	<div style="margin: 5px auto; text-align: center; width: 100%;">
		<h3 style="text-align: center; font: 15px bold;">监控实时情况(请点击图像进行查看)</h3>
	</div>
	<div
		style="position: absolute; border-left: 3px solid #ccc; left: 245px; top: 0; bottom: 0;"></div>
	<table id="InfoTable1" class="manage-table space"
		style="margin-top: 2px; text-align: center;">
		<tr>
			<td style="width: 50%;">
				<div>
					<OBJECT id="ccdActiveX" name="ccdActiveX"
						style="LEFT: 600px; TOP: 100px; width: 384px; height: 288px;"
						classid="clsid:1A784032-A2BA-4DF3-BE2E-E2D5B2257920"
						codebase="${pageContext.request.contextPath}/MrActiveX.cab#version=1,0,0,2"></OBJECT>
				</div>
			</td>
			<td>
				<div>
					<OBJECT id="irActiveX" name="irActiveX"
						style="LEFT: 100px; TOP: 100px; width: 384px; height: 288px;"
						classid="clsid:1A784032-A2BA-4DF3-BE2E-E2D5B2257920"
						codebase="${pageContext.request.contextPath}/MrActiveX.cab#version=1,0,0,2"></OBJECT>
				</div>
			</td>
		</tr>
		<tr>
			<td style="text-align: left;" colspan="2">
				<div style="padding-left: 5px; width: 25%; text-align: left;">
					<p>可见光监测</p>

					<p id="ccdStatus"></p>

					<p></p>

					<p>&nbsp;</p>

					<p>
						<button id="ccdFarButton">FAR</button>
					</p>
					<p>
						<button
							style="background: url(../css/images/photograph.jpg) no-repeat; width: 50px; height: 26px;"></button>
					</p>
					<p>
						<button id="ccdNearButton">NEAR</button>
					</p>
				</div>
				<div
					style="text-align: center; width: 30%; margin: 59px auto 0px 0px;">
					<button id="upButton"
						style="float: none; padding: 0 20px; background: url(../css/images/up.jpg) no-repeat;">&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<br />
					<button id="leftButton"
						style="float: none; margin-right: 0px; padding: 0 20px; background: url(../css/images/left.jpg) no-repeat;">
						&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<button id="stopButton" style="float: none; margin: 0px -4px;">&nbsp;Stop&nbsp;</button>
					<button id="rightButton"
						style="float: none; margin-left: 0px; padding: 0 20px; background: url(../css/images/right.jpg) no-repeat;">
						&nbsp;&nbsp;&nbsp;&nbsp;</button>
					<br />
					<button id="downButton"
						style="float: none; padding: 0 20px; background: url(../css/images/down.jpg) no-repeat;">&nbsp;&nbsp;&nbsp;&nbsp;</button>
				</div>
				<div
					style="padding-left: 5px; width: 43%; text-align: left; margin-bottom: 15px;">
					<p style="margin-bottom: 47px;">红外线监测</p>

					<p id="irStatus"></p>

					<p></p>

					<p></p>

					<p>
						<button id="irFarButton">FAR</button>
					</p>
					<p>
						<button
							style="background: url(../css/images/photograph.jpg) no-repeat; width: 50px; height: 26px;"></button>
					</p>
					<p>
						<button id="irNearButton">NEAR</button>
					</p>
					<p>
						<button id="irAutoButton">AUTOFOCUS</button>
					</p>
				</div>
			</td>
	</table>

	<div id="commonSelect" style="display: none">
		<select multiple="multiple"> {{#each data}}
			<option value="{{id}}">{{name}}</option> {{/each}}
		</select>
	</div>
</body>
</html>