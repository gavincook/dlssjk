<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%--引入核心js和css --%>
<script type="text/javascript">
     <%-- 上下文路径--%>
     var contextPath = "${pageContext.request.contextPath}"; 
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/jquery-ui-1.9.2.custom.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/handlebars-1.0.rc.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/flexigrid/flexigrid.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ztree.extend.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/multiselect/jquery.multiselect.filter.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/multiselect/jquery.multiselect.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validate/jquery.formValidation-1.0.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/highcharts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/ui/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/ui/css/ext/jquery-ui-1.9.2.ext.min.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/ui/flexigrid/css/flexigrid.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/ui/ztree/css/zTreeStyle.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/icon/icon.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/js/validate/css/tip-darkgray.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/ui/multiselect/jquery.multiselect.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jquery/ui/multiselect/jquery.multiselect.filter.css"/>
<script type="text/javascript">
<!--
function checkNewOrder(){
	$.ajax({
		 url : "${pageContext.request.contextPath}/record/alarm/isAlarm",
         type:'post',
         dataType:'json',
         success:function(json){
        	 if(json.isAlarm){
        		asplay('${pageContext.request.contextPath}/flashsound/alarm.mp3');
        		$("#alarmInfo").show("slow");
        	 }
         },
         error:function(){
        	// alert("错误");
         }
	});
	}
	setInterval(checkNewOrder, 50000);
//-->
function getFlashObject(movieName)
{
	if (window.document[movieName])
	{
		return window.document[movieName];
	}
	if (navigator.appName.indexOf("Microsoft Internet")==-1)
	{
		if (document.embeds && document.embeds[movieName])
			return document.embeds[movieName];
	}
	else // if (navigator.appName.indexOf("Microsoft Internet")!=-1)
	{
		return document.getElementById(movieName);
	}
}
function asplay(c){
	var asound = getFlashObject("asound");
	if(asound){
		asound.SetVariable("f",c);
		asound.GotoFrame(1);
	}
}
function asstop(){
	var asound = getFlashObject("asound");
	if(asound){
		asound.SetVariable("f",'start.mp3');
		asound.GotoFrame(1);
	}
}
</script>
