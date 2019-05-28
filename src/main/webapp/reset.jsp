<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.cbt.util.WebCookie"%>
<%String[] userinfo = WebCookie.getUser(request);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ImportX</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link type='image/x-ico' rel='icon' href='/supplier/img/favicon.ico' />	
<link rel="stylesheet" href="/supplier/css/easydialog.css" />

<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>	
<script type="text/javascript">


    $(function(){
    	  $('#sidebar').find('li').eq(5).addClass('active');	
    	
    })
	
	function doModifyPwd(userid) {
		var userId = userid;
		var oldPwd = $("#oldpassword").val();
		var newPwd = $("#newpassword").val();
		var confirmPwd = $("#confirmpassword").val();
		if (userId == null || userId == "") {
			
			easyDialog.open({
				container : {
					content : ' 获取用户Id失败,请重新登录后修改'
				},
				overlay : false,
				autoClose : 1000
			});
			return false;
		}
		if (oldPwd == null || oldPwd == "") {
			easyDialog.open({
				container : {
					content : ' 请输入旧的密码'
				},
				overlay : false,
				autoClose : 1000
			});
			return false;
		}
		if (newPwd != confirmPwd) {
			easyDialog.open({
				container : {
					content : ' 两次的密码输入的不一致'
				},
				overlay : false,
				autoClose : 1000
			});
			return false;
		}
		var psdReg = /^[0-9a-zA-Z_]{6,15}$/;//密码正则
		if (!psdReg.test(newPwd)) {
			
			easyDialog.open({
				container : {
					header : 'Prompt message',
					content : ' 请输入6-15位含有数字和英文字母的密码'
				},
				overlay : false,
				autoClose : 2000
			});
			return false;
		}

		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/backUser/resetBackUserPwd.do",
			data : {
				"userId" : userId,
				"oldPwd" : oldPwd,
				"password" : newPwd
			},
			success : function(data) {
				easyDialog.open({
					container : {
						content : '修改成功,请重新登录'
					},
					overlay : false,
					autoClose : 1000
				});
				setTimeout(loading,1000);				
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				easyDialog.open({
					container : {
					    header : 'Prompt message',
						content : '修改失败'
					},
					overlay : false,
					autoClose : 1000
				});
			}
		});

	}
    function loading(){
    	window.location.href = "/supplier";
    }
    
    
</script>
<style>
.shuruming {
	margin-top: 20px;
	float: none;
	text-shadow: 0 1px 0 #ffffff;
	margin-left: 20px;
	position: relative;
}

.shuru  input {
	width: 15%;
}

.controls {
	color: #555;
	font-size: 14px;
}
</style>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/backUser/resetPassword.do" class="tip-bottom"><i class="icon icon-user"></i>重置密码</a>
				<a href="#" class="current"></a>
			</div>
			<h1 style="font-size: 26px;">重置密码</h1>
		</div>
		<div class="container-fluid">
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-pencil"></i>
							</span>
							<h5>重置密码</h5>
						</div>
						<div class="widget-content nopadding">
							<form id="form-wizard" class="form-horizontal ui-formwizard"
								method="post" novalidate="novalidate" onclick="return false;">
								<div id="form-wizard-1" class="step ui-formwizard-content"
									style="display: block;">
									<%-- <div style="display:none;">
										<label class="control-label">User Id</label>
										<div class="controls">
											<input id="id" type="password" name="id" value="${id}"
												class="ui-wizard-content">
										</div>
									</div> --%>
									<div class="control-group">
										<label class="control-label">Username</label>
										<div class="controls" style="padding: 15px 6px;">
											<%=userinfo != null ? userinfo[1] : ""%>
											<!--  <input id="username" type="text" name="username" class="ui-wizard-content">-->
										</div>
									</div>

									<div class="control-group">
										<label class="control-label">Old password</label>
										<div class="controls">
											<input id="oldpassword" type="password" name="oldpassword"
												class="ui-wizard-content">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">New password</label>
										<div class="controls">
											<input id="newpassword" type="password" name="newpassword"
												class="ui-wizard-content">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Confirm password</label>
										<div class="controls">
											<input id="confirmpassword" type="password"
												name="confirmpassword" class="ui-wizard-content">
										</div>
									</div>
								</div>
								<div class="form-actions">

									<input id="next"
										class="btn btn-primary ui-wizard-content ui-formwizard-button"
										value="Save" style="padding: 4px 20px;width:45px;"
										onclick="doModifyPwd(<%=userinfo != null ? userinfo[0] : ""%>)">
									<div id="status"></div>
								</div>
								<div id="submitted"></div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">

		</div>
	</div>
</body>
</html>