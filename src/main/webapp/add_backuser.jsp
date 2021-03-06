<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.cbt.util.WebCookie"%>
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
<link rel="stylesheet" href="/suppliersupplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
  <link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript">

	
    var flag = true;
    var flag1 = true;
    
    $(function(){
    	
       $('#sidebar').find('li').eq(2).addClass('active');
    	
    })

	
    //检查用户名是否合格 
		function checkUserName(){
    	    var userName = $("#userName").val();  		
    		$.ajax({
				url:"/supplier/backUser/checkBackUser.do",
				data:{
					"userName" : userName
					  },
				type:"post",
				dataType:"text",
				success:function(res){										
					flag1 = true;
					},
			    error:function(){
			    	
			    	easyDialog.open({
			    		  container : {
			    		    content : '用户已经存在'
			    		  },
			    		  overlay : false,
			    		  autoClose : 1000
			    		});
			    	flag1 = false;
			    }
			});
    		
    		
	    }
	 	
    
    
    //提交注册
	function submitForm() {
    	
    	flag = true;
		var preUrl = "${pageContext.request.contextPath}";
		var userName = $("#userName").val();
		var realName = $("#realName").val();
		var pwd = $("#pwd").val();
		var email = $("#email").val();
		var tel = $("#tel").val();
		var remark = $("#remark").val();
		var permission = $('#permission').val();
		if(permission == null || permission == ""){
			permission = 2;
		}
		
		if (userName == null || userName == "" || pwd == null || pwd == "" || permission == null || permission == "") {
			easyDialog.open({
	    		  container : {
	    		    content : '信息未填写完整，不能提交'
	    		  },
	    		  overlay : false,
	    		  autoClose : 1000
	    		});
			flag = false;
			return false;
		} else {

			if (realName.length > 12) {
				easyDialog.open({
		    		  container : {
		    		    content : '真实姓名输入不合法，请重新输入'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
				flag = false;
				return false;
			}
			var psdReg = /^[0-9a-zA-Z_]{6,15}$/;//密码正则
			if (!psdReg.test(pwd)) {
				
				easyDialog.open({
		    		  container : {
		    			header : 'Prompt message',
		    		    content : '请输入6-15位含有数字或者英文字母的密码'
		    		  },
		    		  overlay : false,
		    		  autoClose :2000
		    		});
				flag = false;
				return false;
			}
           
			if(email != null && email != ""){
				var regGeneral = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+/;   //验证邮箱是否合法
				if (!regGeneral.test(email)) {
					easyDialog.open({
			    		  container : {
			    		    content : 'Email输入不合法'
			    		  },
			    		  overlay : false,
			    		  autoClose : 1000
			    		});
					flag = false;
					return false;
				}
			}
			
            
			if(tel != null && tel != ""){
				var regTel = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;//国内电话正则
				var regPhone = /^1[34578]\d{9}$/;//国内手机正则
				if (!(regTel.test(tel) || regPhone.test(tel))) {
					easyDialog.open({
			    		  container : {
			    		    content : '联系方式输入不合法'
			    		  },
			    		  overlay : false,
			    		  autoClose : 1000
			    		});
					flag = false;
					return false;
				}	
			}
			flag = true;
			if(!(flag && flag1)){
				return;
			}

			$.ajax({
				type : "post",
				datatype : "json",
				url : "/supplier/backUser/addBackUser.do",
				data : {
					"userName" : userName,
					"realName" : realName,
					"pwd" : pwd,
					"email" : email,
					"tel" : tel,
					"remark" : remark == null ? "" : remark,
					"permission" : 	permission	
				},
				success : function(data) {
					easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : '提交成功'
			    		  },
			    		  overlay : false,
			    		  autoClose : 1000
			    		});
					setTimeout(backUser,1000);
				},
				error : function() {
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '提交失败 '
						  },
						  overlay : false,
						  autoClose : 1000
						});
				}
			});

		}
	}
	
	//返回用户管理界面
	function backUser(){
		window.location.href = "/supplier/backUser/queryBackUserList.do";		
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

.w-red {
    color: #d14;
    font-weight: 700;
    font-size: 16px;
    margin-left: 5px;
}
</style>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/backUser/queryBackUserList.do" class="tip-bottom"><i class="icon icon-user"></i>用户管理</a>
				<a href="#" class="current">添加用户</a>
			</div>
			<h1 style="font-size: 26px;">添加用户</h1>
		</div>
		<div class="container-fluid">
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon-pencil"></i>
							</span>
							<h5>添加用户</h5>
						</div>
						<div class="widget-content nopadding">
							<form id="form-wizard" class="form-horizontal ui-formwizard"
								method="post" onclick="return false;">
								<div id="form-wizard-1" class="step ui-formwizard-content"
									style="display: block;">
									<div class="control-group">
										<label class="control-label">登录名</label>
										<div class="controls">
											<input id="userName" type="text" name="userName" class="ui-wizard-content" onBlur="checkUserName()">
											<span class="w-red">*</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">真实姓名</label>
										<div class="controls">
											<input id="realName" type="text" name="realName" class="ui-wizard-content">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">密码</label>
										<div class="controls">
											<input id="pwd" type="password" name="pwd" class="ui-wizard-content">
											<span class="w-red">*</span>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">邮箱</label>
										<div class="controls">
											<input id="email" type="text" name="email" class="ui-wizard-content">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">电话号码</label>
										<div class="controls">
											<input id="tel" type="text" name="tel" class="ui-wizard-content">
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">用户权限</label>
										<div class="controls">
											<select name="permission" id="permission" class="ui-wizard-content">
											  <option value="2">普通用户</option>
											  <option value="1">管理员用户</option>
											</select>
										</div>
									</div>
									<div class="control-group">
										<label class="control-label">Remark</label>
										<div class="controls">
											<textarea id="remark" name="remark" style="height: 72px; width: 50%;"></textarea>
										</div>
									</div>
			

								</div>
								<div class="form-actions">
									<a href="#" class="btn btn-primary" onclick="backUser();">Back</a>
									<button class="btn btn-success" onclick="submitForm()">Save</button>
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