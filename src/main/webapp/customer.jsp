<%@page import="java.util.List"%>
<%@page import="com.cbt.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.cbt.util.WebCookie"%>
<%
	List<User> userList = (List<User>) request.getAttribute("userList");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ImportX</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<script src="js/jquery-1.8.3.min.js"></script>
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet" href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
 	<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript">

    var pageSize = '<%=request.getParameter("pageSize")%>';

	$(function() {
		
	   $('#sidebar').find('li').eq(3).addClass('active');	
   	   
   	   $('.center').find('input:first').css({"display":"none"});
		
		$('#send_mail_div').hide();
		if (pageSize != null && parseFloat(pageSize) != "NaN"  && pageSize != '') {
			$("#pageSize").find("option[value='"+pageSize+"']").attr("selected",true);
		}
     
	});

	function doQuery() {
		var info = $("#queryinfo").val();
		var pageSize = $("#pageSize").val();
		var param = "";
		if (!(info == null || info == "")
				&& !(pageSize == null || pageSize == "")) {
			param = "info=" + info + "&pageSize=" + pageSize;
		} else if (!(info == null || info == "")) {
			param = "info=" + info;
		} else if (!(pageSize == null || pageSize == "")) {
			param = "pageSize=" + pageSize;
		}
		if (param.length == 0) {
			window.location.href = "/supplier/queryUserList.do";
		} else {
			window.location.href = "/supplier/queryUserList.do?" + param;
		}
	}

	//邀请登录
	function generateTheLink(customerId) {
		$('#makeupCo').val("");
		$('#makeupCoSe').empty();
		$('#copy_div1').show();
		if(customerId == null || customerId == ""){
			easyDialog.open({
				  container : {
				    content : '获取ID失败 '
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return false;
		}
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryUserEmail.do",
			data : {
				"customerId" : customerId
			},
			success : function(result) {
				if(result.state == 0){
				   var user = result.data;
				   $('#customer_select').val(user.userid);
				   var email = user.email;
				   var loginEmail = user.loginEmail;
				   if(email == null || email == "undefined"){
					   email = "";
				   }			  
				   if(loginEmail == null || loginEmail == "undefined"){
					   loginEmail = "";
				   }
				   if(email == loginEmail){
					   $('#makeupCoSe').append("<option value='' selected></option>"); 
					   $('#makeupCoSe').append("<option value='"+loginEmail+"'>"+loginEmail+"</option>");  
				   }else{
					   $('#makeupCoSe').append("<option value='' selected></option>"); 
					   $('#makeupCoSe').append("<option value='"+loginEmail+"'>"+loginEmail+"</option>");
					   $('#makeupCoSe').append("<option value='"+email+"'>"+email+"</option>"); 
				   }
				  
				}else{
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '获取失败，请重试 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
				}
				
			},
			error : function() {

				easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '获取失败，请重试 '
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
				
			}
		});
	}
	//关闭邀请登录
	function closeDialog1(){
		$('#copy_div1').css({"display":"none"});	
	}
	
	//查询登录日志
	function gotoLog(){
		window.location = "/supplier/queryLoginLog.do";
	}
	//发送邮件并保存邮箱信息
// 	function sendMail(userid){
// 	  window.location.href="http://192.168.1.62:8080/NBEmail/helpServlet?action=inviteLogin&className=ExternalInterfaceServlet&&eid="+userid;			
// 	}
	
	//获取并显示客户详情框
	function customer_details(customerId){
		
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryCustomerInfo.do",
			data : {
				"customerId" : customerId
			},
			success : function(result) {
				var user = result.user;
				var factoryUserRelation = result.factoryUserRelation;
				$('#customerId').text(user.userid);
				$('#customerName').text(user.userName == null ? "" : user.userName);
				$('#customerEmail').text(user.email);
				$('#customerTel').text(user.tel == null ? " " : user.tel);
				$('#copy_text').text(factoryUserRelation.factoryUserRemark == null ? "" : factoryUserRelation.factoryUserRemark);
				$('#customerDetialDialog').show();
			},
			error : function() {
				easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '获取失败，请重试 '
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
			}
		});	
	}
	//隐藏客户详情显示框
	function closeDialog() {
		clear_input();
		$('#customerDetialDialog').hide();
	}
	
	
	//查看历史订单
	function queryClientOrder(customerId){
		window.location.href = "/supplier/queryAllClientOrder.do?userName="+customerId;				
	}
	
	//修改备注信息
	function update_remark(){
		var customerId = $('#customerId').text();
		var remark = $('#copy_text').val();
		$.post("/supplier/updateRemark.do",
				{
			  "customerId":customerId,
			   "remark" : remark
			    },
				function(result){
			     if(result.state == 0){
			    	 easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '修改成功'
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			    	 setTimeout(show,1000);
			     }else{
			    	 alert(result.message);
			     }
			    	
			    	
			   });
		
	}
	function show(){
		$('#customerDetialDialog').hide();
	}
	
	//双击清空input框
	function clear_input(){
	   $('#makeupCo').val('');
	   $('#makeupCo1').val('');
	}
	
	
	//添加关联邮箱
	function addCustomerEmail(customerId){
		
		$('#copy_div2').show();
		if(customerId == null || customerId == ""){
			easyDialog.open({
				  container : {
				    content : '获取ID失败 '
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return false;
		}
		
		
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryEmailByUserid.do",
			data : {
				"userid" : customerId
			},
			success : function(result) {
				if(result.state == 0){
				   var userRelationEmails = result.data;
				   $('#customer_select').val(customerId);
				   $('#makeupCoSe1').empty();
				   var tl = userRelationEmails.length;
				   for(var i=0;i<tl;i++){
					   if(i == 0){
					   $('#makeupCoSe1').append("<option value='"+userRelationEmails[i].email+"' selected>"+userRelationEmails[i].email+"</option>");   
					   }else{
					   $('#makeupCoSe1').append("<option value='"+userRelationEmails[i].email+"'>"+userRelationEmails[i].email+"</option>");   
					   }				     
				   }				   
			  
				}else{
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '获取失败，请重试 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
				}
				
			}
		});	
	}
	
	
	/*
	 *添加客户子邮箱
	 */
	function add_email(){
		 
		 var customerId = $('#customer_select').val();
		 if(customerId == null || customerId == ""){
				easyDialog.open({
					  container : {
					    content : '获取ID失败 '
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
				return false;
			}
		 
		 
		 var loginEmail = $('#makeupCo1').val();
		 if(loginEmail == null || loginEmail == ""){
			   
			   easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : ' * 邮箱不能为空'
		    		  },
		    		  autoClose : 1000
		    		});
				return false; 
		   }
			 //验证邮箱是否合法
	 	   var szReg=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+/;     	    	   
	 	   if(!(loginEmail == null || loginEmail.length==0)){
	 		   var bChk = szReg.test(loginEmail);
				
				if(!bChk){
					easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : ' * 邮箱格式不正确'
			    		  },
			    		  autoClose : 1000
			    		});
					return false;
				}
			   }    
		
			$.ajax({
				type : "post",
				datatype : "json",
				url : "/supplier/addEmailByUserid.do",
				data : {
					"userid" : customerId,
					"email" : loginEmail
				},
				success : function(result) {
					if(result.state == 0){
					   var userRelationEmails = result.data;
					   $('#customer_select').val(customerId);
					   $('#makeupCoSe1').empty();
					   var tl = userRelationEmails.length;
					   for(var i=0;i<tl;i++){
						   if(i == 0){
						   $('#makeupCoSe1').append("<option value='"+userRelationEmails[i].email+"' selected>"+userRelationEmails[i].email+"</option>");   
						   }else{
						   $('#makeupCoSe1').append("<option value='"+userRelationEmails[i].email+"'>"+userRelationEmails[i].email+"</option>");   
						   }						   
					   }
					   easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '添加成功 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
					   setTimeout(closeDialog2,1000);
				       $('#makeupCo1').val('');
					}else{
						easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '获取失败，请重试 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
					}
					
				}
			});		    	   
	}
	
	//关闭添加子邮箱
	function closeDialog2(){
		clear_input();
		$('#copy_div2').css({"display":"none"});	
	}
	
	
	
	
	
	//新建报价
	function createQuotation(userid){
		window.location = "/supplier/toCreateQuotation.do?userid="+userid;
	}
	
	
	
	
	
	
	
</script>
<style>
/*正在发送中*/
*{ padding:0; margin:0;}
.layer {
  background: rgba(0, 0, 0, 0.4);
  display: none;
  height: 100%;
  left: 0;
  position: fixed;
  top: 0;
  width: 100%;
}
.layer_com {
  background: rgba(0, 0, 0, 0.8) url(/supplier/img/loading.gif) no-repeat center 15px;
  border-radius: 8px;
  height: 50px;
  left: 45%;
  opacity: 0.8;
  position: absolute;
  text-align: center;
  top: 40%;
  width: 120px;
  z-index: 6000;
}
.layer_com p {
  color: #fff;
  font-size: 20px;
  padding-top: 15px;
}
.weui_dialog{z-index:1000;}




.shuruming {
	display:block;
	width:100%;
	margin-top: 20px;
	float: none;
	text-shadow: 0 1px 0 #ffffff;
	margin-left: 20px;
	/* 	position: relative; */
	
}

.shuru  input {
	width: 15%;
}

.dataTables_length label {
	float: left;
}

.dataTables_length .div-label {
	float: left;
	margin-left: 0.625em;
}

.dataTables_length .select2-container {
	float: left;
	margin-left: 0.625em;
	margin-bottom: 0.3em;
}
.cont-label {
    font-weight: bold;
    /* padding-top: 10px; */
    /* width: 180px; */
    float: left;
    text-align: right;
}
.div-labe6 {
    width:18%;
}
.weui_dialog{width:30%;}
.con {
    text-align: left;
    margin-left:20%;
    padding-right:5%;
}
.weui_dialog{
	border:1px solid #ddd;
}
.waimian2 {
    font-size: 14px;
    padding: 20px;
    margin-top: 20px;
}
.text-copy{
	width:100%;
}
.btn-primary:hover, .btn-primary:focus, .btn-primary:active, .btn-primary.active, .btn-primary.disabled, .btn-primary[disabled] {
    color: #fff;
    background-color: #04c;
}
.z-note{
    font-size: 18px;
    color: red;
    float: unset;
    margin-top: 19px;
    margin-left: 20px;
}
.clearfix:before,.clearfix:after{content:"";display:table;}
.clearfix:after{clear:both;}
.clearfix{zoom:1;} 
.btns{float:left;}
#queryinfo{float:left;margin-right:2%;}
.shuruming{overflow:hidden;}
</style>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>

	<!--main-container-part-->
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryUserList.do" class="tip-bottom"><i class="icon icon-user"></i>客户管理</a>
				<a href="#" class="current"></a>
			</div>
			<div class="shuru">
				<div class="shuruming clearfix">
					<input id="queryinfo" type="text" class="span11"
						value="${queryinfo}" placeholder="客户ID/公司名称" style="width: 25%;">
						<div class="btns clearfix">
							<button class="btn btn-info" style="padding: 4px; left: 34%;" onclick="doQuery()">查询</button>						
                        <% if(WebCookie.getPermission(request) == 1){%>     	
                        	<a href="#" style="color:#fff;"><button class="btn btn-primary" style="padding: 4px; left: 40%;" onclick="gotoLog()">登录日志</button></a>	
                        	<a href="/supplier/queryUserByOrderUpdate.do" style="color:#fff;"><button class="btn btn-primary " style="padding: 4px; left: 49%;">30天有修改的客户</button></a>	
                        	<a href="/supplier/queryUserByInvitation.do" style="color:#fff;"><button class="btn btn-primary " style="padding: 4px; left: 59%;">30天有发出邀请的客户</button></a>	
                        	<a href="/supplier/queryUserByLogin.do" style="color:#fff;"><button class="btn btn-primary " style="padding: 4px; left: 71%;">30天有登录的客户</button></a>	
						<%}%>
						</div>
						
					<!-- <button class="btn btn-info btn-left"
						style="padding: 4px 20px; left: 41%;">添加用户</button> -->

				</div>
			</div>
			<div class="z-note">注意：ERP客户发送邮件之前需要在NBEmail录入客户邮箱，否则会导致发送失败 </div>
			<div class="container-fluid">
				<hr>
				<div class="row-fluid">
					<div class="span12">
						<div class="widget-box">

							<div class="widget-content nopadding">
								<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper"
									role="grid">
									<div class="">
										<div id="DataTables_Table_0_length" class="dataTables_length">
											<label>Show </label> <select id="pageSize"
												class="select2-container" name="pageSize"
												onchange="doQuery()">
												<option value="10">10</option>
												<option value="25">25</option>
												<option value="50">50</option>
												<option value="100">100</option>
											</select> <label class="div-label">entries</label>
										</div>
									</div>
									<!-- <div class="">
										<div id="DataTables_Table_0_length" class="dataTables_length">
											<label>Show <div class="select2-container" id="s2id_autogen1">
													<a href="#" onclick="return false;" class="select2-choice">
														<span>10</span> <abbr class="select2-search-choice-close"
														style="display: none;"> </abbr>
														<div>
															<b></b>
														</div>
													</a>
													<div class="select2-drop select2-offscreen">
														<div class="select2-search">
															<input type="text" autocomplete="off"
																class="select2-input" tabindex="0">
														</div>
														<ul class="select2-results">
														</ul>
													</div>
												</div>
												<select size="1" name="DataTables_Table_0_length"
												aria-controls="DataTables_Table_0" style="display: none;">
													<option value="10" selected="selected">10</option>
													<option value="25">25</option>
													<option value="50">50</option>
													<option value="100">100</option>
											</select> entries
											</label>
										</div>
									</div> -->
									<table class="table table-bordered data-table dataTable"
										id="DataTables_Table_0">
										<thead>
											<tr role="row">
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1" aria-sort="ascending"
													aria-label="Rendering engine: activate to sort column descending">
													<div class="DataTables_sort_wrapper">
														客户ID <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Platform(s): activate to sort column ascending">
													<div class="DataTables_sort_wrapper">
														姓名 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Platform(s): activate to sort column ascending">
													<div class="DataTables_sort_wrapper">
														公司名称 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
<!-- 												<th class="ui-state-default" role="columnheader" -->
<!-- 													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" -->
<!-- 													colspan="1" -->
<!-- 													aria-label="Engine version: activate to sort column ascending"> -->
<!-- 													<div class="DataTables_sort_wrapper"> -->
<!-- 														联系电话 <span -->
<!-- 															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span> -->
<!-- 													</div> -->
<!-- 												</th> -->
<!-- 												<th class="ui-state-default" role="columnheader" -->
<!-- 													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" -->
<!-- 													colspan="1" -->
<!-- 													aria-label="Engine version: activate to sort column ascending"> -->
<!-- 													<div class="DataTables_sort_wrapper"> -->
<!-- 														邮箱 <span -->
<!-- 															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span> -->
<!-- 													</div> -->
<!-- 												</th>				 -->
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending">
													<div class="DataTables_sort_wrapper">
														第一次下单时间 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending">
													<div class="DataTables_sort_wrapper">
														最近下单时间<span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending">
													<div class="DataTables_sort_wrapper">
														是否有邮箱<span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending"
													style="width: 270px;">
													<div class="DataTables_sort_wrapper">
														操作 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
											</tr>
										</thead>

										<%
											for (int i = 0; i < userList.size(); i++) {
											
										%>
										<tr class="gradeA even">
											<td><%=userList.get(i).getUserid()%></td>
											<td><%=userList.get(i).getUserName() == null ? "" : userList.get(i).getUserName()%></td>
											<td><%=userList.get(i).getCompanyName() == null ? "" : userList.get(i).getCompanyName()%></td>
<%-- 											<td><%=userList.get(i).getTel() == null ? "" : userList.get(i).getTel()%></td> --%>
<!-- 											<td><a href="#" class="a2" style="color: #08c; text-decoration: underline;" -->
<%-- 											onclick="customer_details('<%=userList.get(i).getUserid()%>');"><%=userList.get(i).getLoginEmail()%></a></td> --%>
											<td><%=userList.get(i).getMinTime() == null ? "" : userList.get(i).getMinTime()%></td>
											<td><%=userList.get(i).getMaxTime() == null ? "" : userList.get(i).getMaxTime()%></td>
											<td><%=(userList.get(i).getLoginEmail() == null || "".equals(userList.get(i).getLoginEmail())) ? "无" : "有"%></td>
											<td class="center">
<%-- 											<a href="http://112.64.174.34:43887/NBEmail/helpServlet?action=inviteLogin&className=ExternalInterfaceServlet&&eid=<%=userList.get(i).getUserid()%>&&content=<%=request.getAttribute("content")%>" target="_blank" "><button class="btn btn-primary">邀请登录</button></a> --%>
											<a href="/supplier/port/sendMail.do?userid=<%=userList.get(i).getUserid()%>" target="_blank""><button class="btn btn-primary">邀请登录</button></a>
											<button class="btn btn-primary" onclick="queryClientOrder('<%=userList.get(i).getUserid()%>')">查看订单</button>
<%-- 											<button class="btn btn-primary" onclick="addCustomerEmail('<%=userList.get(i).getUserid()%>')">添加邮箱</button> --%>
<%-- 											<button class="btn btn-primary" onclick="createQuotation('<%=userList.get(i).getUserid()%>')">新建报价</button> --%>
											</td>
										</tr>
								         <%
												}
										 %>

										</tbody>
									</table>
		
	
	    <!-- 选择邮箱邀请登录 -->								
		<div class="weui_dialog" id="copy_div1" style="display: none;">
		<input id="customer_select" value="" type="hidden"> 
			<div class="waimian2" >
											<div class="control-group" id="copy_div"
												style="margin-bottom: 50px;">
												<label class="cont-label div-labe6">选择邮箱 : </label>
												<div class="con div-controls">
													<script language="javascript">
														function changeF() {
															document
																	.getElementById('makeupCo').value = document
																	.getElementById('makeupCoSe').options[document
																	.getElementById('makeupCoSe').selectedIndex].value;
														}
													</script>

													<span
														style="position: absolute; border: 1pt solid #c1c1c1; overflow: hidden; width: 220px; height:25px; clip: rect(-1px, 220px, 220px, 170px);">
														<select name="makeupCoSe" id="makeupCoSe"
														style="width: 220px; height: 27px; margin: -1px;"
														onChange="changeF();">
													
															<OPTION id="99999" VALUE="" SELECTED>
													</select>
													</span> <span
														style="position: absolute; border-top: 1pt solid #c1c1c1; border-left: 1pt solid #c1c1c1; border-bottom: 1pt solid #c1c1c1; width: 200px; height: 25px;">
														<input type="text" name="makeupCo" id="makeupCo"
														value="" placeholder="请选择或输入"
														style="width: 186px; height: 17px; border: 0pt;"ondblclick="clear_input();">
													</span>
													<div class="layer" style="display:block;" id="send_mail_div"><div class="layer_com"><p>正在发送</p></div></div>
												</div>
												<button class="btn btn-primary" style="padding: 4px 20px;position: absolute;right: 8%;" onclick="sendMail()">发送</button>
													
											</div>
										</div>
			                           <a class="close-reveal-modal" href="#" onclick="closeDialog1()">×</a>

		         </div>
		         
		         
	    <!-- 添加邮箱 (已停用)-->								
		<div class="weui_dialog" id="copy_div222" style="display: none;">
			<div class="waimian2" >
											<div class="control-group" id="copy_div3"
												style="margin-bottom: 50px;">
												<label class="cont-label div-labe6">新增子邮箱: </label>
												<div class="con div-controls">
													<script language="javascript">
// 														function changeF1() {
// 															document
// 																	.getElementById('makeupCo1').value = document
// 																	.getElementById('makeupCoSe1').options[document
// 																	.getElementById('makeupCoSe1').selectedIndex].value;
// 														}
													</script>

													<span
														style="position: absolute; border: 1pt solid #c1c1c1; overflow: hidden; width: 220px; height:25px; clip: rect(-1px, 220px, 220px, 170px);">
														<select name="makeupCoSe1" id="makeupCoSe1"
														style="width: 220px; height: 27px; margin: -1px;"
														onChange="changeF1();">												
															<OPTION id="99999" VALUE="" SELECTED>
													</select>
													</span> <span
														style="position: absolute; border-top: 1pt solid #c1c1c1; border-left: 1pt solid #c1c1c1; border-bottom: 1pt solid #c1c1c1; width: 200px; height: 25px;">
														<input type="text" name="makeupCo1" id="makeupCo1"
														value="" placeholder="请输入"
														style="width: 186px; height: 17px; border: 0pt;"ondblclick="clear_input();">														
													</span>	
													<button style="position: relative;float: right;right: 85px;" onclick="add_email()">添加</button>									
												</div>
													
											</div>
										</div>
			                           <a class="close-reveal-modal" href="#" onclick="closeDialog2()">×</a>

		         </div>
									
									<div
										class="fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix">
										<div class="dataTables_filter" id="DataTables_Table_0_filter">
										</div>
										<div
											class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers"
											id="DataTables_Table_0_paginate">${pager2}</div>
									</div>

								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<div class="weui_dialog_alert" id="customerDetialDialog"
		style="display: none;">
		<div class="weui_mask"></div>
		<div class="weui_dialog">
			<div class="waimian2">
				<div class="control-group">
					<label class="cont-label div-labe6">客户ID : </label>
					<div class="con div-controls">
						<span id="customerId"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="cont-label div-labe6">客户姓名 : </label>
					<div class="con div-controls">
						<span id="customerName"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="cont-label div-labe6">邮箱 : </label>
					<div class="con div-controls">
						<span id="customerEmail"></span>
					</div>
				</div>
				<div class="control-group">
					<label class="cont-label div-labe6">联系方式 : </label>
					<div class="con div-controls">
						<span id="customerTel"></span>
					</div>
				</div>
				<div  class="control-group" style="margin-top: 40px;">
					<label class="cont-label div-labe6">备注信息: </label>
					<div class="con div-controls">
						<textarea id="copy_text" cols=50 rows=5></textarea>
					</div>
				</div>
				<div class="control-group" style="margin-top: 10px;">
					<button id="copyLinkBtn" class="btn btn-primary" onclick="update_remark()">修改备注信息</button>
				</div>
			</div>
			<a class="close-reveal-modal" href="#" onclick="closeDialog();">×</a>

		</div>
	</div>

	<!--end-main-container-part-->
   
	<!--Footer-part-->

	<div class="row-fluid">
		<div id="footer" class="span12">
		
		</div>
	</div>
</body>
</html>