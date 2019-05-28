<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
<%String[] userinfo = WebCookie.getUser(request);%>
<!DOCTYPE html>
<html lang="en">

<head>
<title>login</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-login.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />	

<script type="text/javascript" src="js/jquery-1.10.2.js"></script>
<script type="text/javascript">
<%-- var userName = '<%=userinfo != null ? userinfo[1] : ""%>'; --%>
	$(function(){
		
		$("#username").val('<%=userinfo != null ? userinfo[1] : ""%>');
		$("#password").val('<%=userinfo != null ? userinfo[4] : ""%>');
		$(document).keyup(
				function(event) {
					if (event.keyCode == 13) {
						login();
					}
				});
	});


	function login() {
		var username = $("#username").val();
		var password = $("#password").val();
		if(username == null || username ==""){
			alert("请输入用户名");
			return false;
		}
		if(password == null || password ==""){
			alert("请输入密码");
			return false;
		}
		$('#login').css({"background-color":"#bbb"}).attr("disabled","true");	
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/backUser/login.do",
			data : {
				"username" : username,
				"password" : password
			},
			success : function(data) {
				$('#login').css({"background-color":"#08c"}).removeAttr("disabled");	
				window.location.href = "/supplier/queryAllClientOrder.do";
			},
			error : function() {
				$('#login').css({"background-color":"#08c"}).removeAttr("disabled");	
				alert("用户名密码错误或用户不存在！");
			}
		});

		//document.getElementById("loginform").submit();
		//window.location.href = "${ctx}/queryAllClientOrder.do";
	}
</script>
<style>
.btn-success {
	color: #fff;
	background-color: #08c;
}

.btn-success:hover {
	color: #fff;
	background-color: #04c;
}
.control-group .w-logo-font{font-size: 20px;margin-left: 20px;}
</style>
</head>
<body style="height: 60%;">
	<div id="loginbox">
		<form id="loginform" class="form-vertical"
			action="/supplier/backUser/login.do" method="post">
			<div class="control-group normal_text">
				<h3>
					<img src="img/logo6.png" alt="Logo" />
					<span class="w-logo-font">外贸订单管理工厂端</span>
				</h3>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_lg" style="background-color: #08c;"><i
							class="icon-user"></i></span> <input id="username" name="username"
							type="text" placeholder="Username" />
					</div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls">
					<div class="main_input_box">
						<span class="add-on bg_ly" style="background-color: #28b779;"><i
							class="icon-lock"></i></span> <input id="password" name="password"
							type="password" placeholder="Password" />
					</div>
				</div>
			</div>
			<!-- <div class="checkbox cl">
                    <div class="icheckbox_squaremini-blue checked" style="position: relative;">
                        <input id="remember_28849176" class="checkbox_eula" name="remember_28849176" type="checkbox" checked="checked" style="position: absolute; opacity: 0;">
                       &lt;!&ndash; <ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; border: 0px; opacity: 0; background: rgb(255, 255, 255);"></ins>&ndash;&gt;
                    </div>
                        <lable class="text_remember font12" for="remember_28849176">记住密码</lable>
                </div>-->

			<div class="form-actions">
				<!-- <span class="pull-left"><a href="#" class="flip-link btn btn-info" id="to-recover">Lost password?</a></span>-->
				
				<span class="pull-right"><a href="https://importx.net/f_register.jsp" class="btn btn-warning" > 注册</a></span>
				<span class="pull-right"  style="margin-right:20px;"><a href="#" id="login"
					onclick="login()" class="btn btn-success"> 登录</a></span>
				
			</div>
		</form>

	</div>

</body>

</html>
