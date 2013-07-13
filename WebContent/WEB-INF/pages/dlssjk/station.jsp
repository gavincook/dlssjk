<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/dlssjk/all.css"/>
	<title>${station.name}</title>
	<script type="text/javascript">
		$(function(){
			$("#changeLogo").click(function(event){
				$("#changeLogoDialog").dialog({
					title:'更换Logo',
					modal:true,
					width:350,
					buttonAlign:'right',
					show:'flod',
					hide: "explode",
					buttons:[{
						text:"保存",
						click:function(event){
							$("form[name='stationForm']").submit();
						}
					}]
				});
			});
		});
	</script>
</head>
<body>

  <div class="headbar ui-accordion-header ui-state-active ">
		<span class="bartitle">工作站列表</span>
		<div class="btns">
			<span><a href="javascript:void(0)" id="changeLogo">更换Logo</a></span>
		</div>
  </div>
	<div class="stationDetails">
		<img alt="logo" class="logo" src="${pageContext.request.contextPath}${station.logo}" onerror="javascript:this.src='${pageContext.request.contextPath}/logo/logo.png';">
		<div class="content">
			<div class="title">${station.name }</div>
			<div><span>IP:</span>${station.ip }</div>
			<div><span>gps:</span>${station.gps }</div>
			<div><span>负责人:</span>${station.contact }</div>
			<div><span>联系电话:</span>${station.telephone }</div>
		</div>
		<div style="position: absolute;right: 5px;bottom: 10px"><a href="${pageContext.request.contextPath}/stations">工作站列表</a></div>
	</div>
	<div class="hide" id="changeLogoDialog">
		<form name="stationForm" action="${pageContext.request.contextPath}/stations/updateStation" class="dialogForm" method="post" enctype="multipart/form-data">
			<div><label>Logo</label><input type="file" name="station.logo"/>
				<input type="hidden" name="station.id" value="${station.id}"/>
			</div>
			
		</form>
	</div>	
</body>
</html>