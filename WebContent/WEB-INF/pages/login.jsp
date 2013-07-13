<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.8.3.js"></script>
<title>电力双视监控</title>
<script>

//***********************************************<公用方法(提取到公用js文件中)>*******************************************************

/**
 * reset all the field of form,call like $("#loginForm").reset();
 */
$.fn.reset = function(){
	$(':input',this)  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected'); 
};
 
 /**
  * ajax submit form,use like this:
  *	<p>  $("#loginForm").ajaxSubmitForm("login/validate",
  *			 function(result) {
  *		        // todo the code when success
  *	             }, 
  *	         function(result) {
  *		        // todo the code when failure
  *	        });
  *</p>
  * @param url        : the form submit url
  * @param successFun : when ajax submit form success,also the response message 
  *                     is success(means:the success propertity of responesText is true),
  *                     call the successFun with the responesText parameter
  * @param failureFun : if not call the successFun,then call the failureFun with responesText parameter 
  */
 $.fn.ajaxSubmitForm = function(url,successFun,failureFun,errorFun){
		$.ajax({
			url:url,
			data:$(this).serialize(),
			type:'post',
			dataType:'json',
			success:function(result){
				if(result.success){
					successFun(result);
				}
				else
					failureFun(result);
			},
			error:function(XMLHttpRequest, textStatus, errorThrown){
				errorFun(XMLHttpRequest, textStatus, errorThrown);
			}
		});
 };
 $.href=function(url,type){
	 if(typeof(type)=="undefined"||type=='current')
	  window.location.href=url;
	 else
		 if(type=="new")
			 window.open(url);
 };
$(function(){
	$("#submit").click(function(){
		
	$("#loginForm").ajaxSubmitForm("${pageContext.request.contextPath}/user/login/validate",
			function(result) {
		if("${from}")
			$.href("${from}");
		else
			$.href("${pageContext.request.contextPath}/index");
			}, 
			function(result) {
				if(!result.success){
					alert("用户名或密码错误");
				}
			});
		
		});

		$("#reset").click(function() {
			$("#loginForm").reset();
		});
	});
</script>
<style type="text/css">
	.logindiv{
		background:url('${pageContext.request.contextPath}/css/images/login.png');
		background-position:0px 0px;
		width:800px;
		height:510px;
		margin-left:-400px;
		margin-top:-250px;
		position:absolute;
		top:50%;
		left:50%;
	}

	.bg{
	    background-color:#0066ca;
	}

	.title{
		font-size: 50px;
        font-family: 楷体;
		color: white;
		text-align: center;
	}

	.loginForm{
		margin-left: 200px;
		margin-top: 100px;
	}
	.loginItem{
		margin-top: 50px;
	}

	.input{
		width: 300px;
		border-radius: 10px;
		text-align: center;
		background-color: #fff;
	}

	.textLabel{
		color: white;
		font-size: 20px;
		font-family: 楷体;
	}
	
	.btns{
		margin-left: 80px;
	}

	.btnblue{
		width:110px;
		height:38px;
		background:url('${pageContext.request.contextPath}/css/images/login.png');
		background-position:-20px -533px;
	    border:none;
		border-radius: 10px;
		color:white;
		font-size: 16px;
		font-family: 楷体;
	}

	.btnblue:hover{
		background-position:-20px -583px;
	}

	.btnblue:active{
		background-position:-20px -632px;
	}

	.reset{
		margin-left:30px;
	}

	.logo{
		float: left;
		margin-top: 100px;
	}
</style>
</head>
<body class="bg">

<div class="logindiv">
	<div class="title">电力双视监控平台</div>
	<img class="logo" src="${pageContext.request.contextPath}/css/images/logo.png"/>
	<form class="loginForm" id="loginForm">
		<div class="loginItem">
			<span class="textLabel">用户名</span>
			<input type="text" name="user.userName" class="input">
		</div>
		<div class="loginItem">
			<span class="textLabel">密&nbsp;&nbsp;码</span>
			<input type="password" name="user.password"  class="input">
		</div>
		<div class="loginItem btns">
			<input type="button" class="btnblue" id="submit" value="登&nbsp;&nbsp;录">
			<input type="reset" class="reset btnblue"  value="重&nbsp;&nbsp;置">
		</div>
	</form>
  </div>
	<%-- <img alt="logo" src="${pageContext.request.contextPath}/css/images/logo.png" 
	style="position: absolute;top: 15%;left: 10%;">
	<div>
		<div  style="margin-top:15%;margin-left:auto;margin-right:auto;width:600px;font-size:40px;text-align: center;font-family: 楷体;">
			电力双视监控平台
		</div>
	    <div style="margin-top:20px;margin-left:auto;margin-right:auto;width:300px; height:200px;">
	    ${info}
	    <form id="loginForm" class="loginForm">
	       <p> <span>用户名：</span><input type="text" name="user.userName" value="system_user" style="width: 215px;"/> </p>
	     <p>  <span>密&nbsp;&nbsp;码：</span><input  name="user.password" style="width: 215px;" type="password" value="system_user"/> <span class="errorImg"></span><span
							class="errorMsg"></span>  </p>
							<div style="text-align: center;"><input  type="button" id="submit" class="btn" value="登录"/>
							<input  type="button" id="reset" class="btn" value="重置"/></div>
	     </form>
	     </div>
	</div> --%>
</body>
</html>