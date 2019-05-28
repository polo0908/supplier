<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.cbt.util.WebCookie"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%String[] userinfo = WebCookie.getUser(request);%>

<link type='image/x-ico' rel='icon' href='/supplier/img/favicon.ico' />
<!-- <script type="text/javascript " src ="/supplier/js/jquery-1.8.3.min.js"></script> -->
<!-- <script type="text/javascript" src="/supplier/js/placeholder.js"></script> -->
<script type="text/javascript">



// $(function(){
	
//     if(permission != 1){
//       	$('#sidebar ul').children("li:eq(4)").hide();
//       	$('#sidebar ul').children("li:eq(6)").hide();
//       }	 
    
//     if(userid != null && userid != ''){
// 	$.post("/supplier/queryMessageCount.do", 
// 			function(result){		     
// 		     if(result.state == 0){
// 		    	if(result.data == 0){
// 		    	  $('#menu-messages').find("span:last").hide();
// 		    	}else{
// 		          $('#menu-messages').find("span:last").text(result.data);	
// 		    	}
		    	 
// 		     }else{
// 		    	 $('#menu-messages').find("span:last").hide();
// 		     }
// 		  });		
//     }	

// })
// function show_message(){
// 	window.location = "/supplier/queryMessage.do";
// }



</script>
	<div id="header">
		<h1>
			<a href="#">上海策融</a>
		</h1>
	</div>
	<div id="user-nav" class="navbar navbar-inverse">
		<ul class="nav">
			<li class="dropdown" id="profile-messages"><a title="" href="#"
				data-toggle="dropdown" data-target="#profile-messages"
				class="dropdown-toggle"><i class="icon icon-user"></i> <span
					class="text"><%=userinfo != null ? userinfo[1] : ""%></span></a></li>
<!-- 			<li class="dropdown" id="menu-messages"> -->
<!--      	<a href="#" data-toggle="dropdown" data-target="#menu-messages" class="dropdown-toggle"><i class="icon icon-envelope"></i> <span class="text" onclick="show_message()">Messages</span> <span class="label label-important" id="unread_message_count"></span></a> -->
<!--             </li>		 -->
		</ul>
	</div>
	<div id="search">
		<a title="" href="/supplier/login.jsp"><i class="icon icon-share-alt"></i> <span
			class="text">Logout</span></a>
	</div>
	<div id="sidebar">
		<!--<a href="#" class="visible-phone"><i class="icon-paste"></i> 历史订单</a>-->
		  <ul>
			<li><a href="/supplier/queryAllClientOrder.do"><i
					class="icon icon-paste"></i> <span>我的订单</span></a></li>
			<li><a href="/supplier/queryAllReOrder.do"><i
					class="icon icon-copy"></i> <span>待处理订单</span></a></li>
<!-- 			<li><a href="/supplier/queryAllRfqInfo.do"><i -->
<!-- 					class="icon icon-picture"></i> <span>新图纸询盘</span></a></li> -->
<!-- 			 <li><a href="/supplier/queryAllQuotation.do"><i class="icon icon-file"></i>  -->
<!-- 			      <span>报价中心</span></a></li>		 -->
<!-- 			<li><a href="/supplier/queryMessage.do"><i class="icon icon-user"></i> <span>消息中心</span></a></li> -->
			<c:if test="${sessionScope.permission == 1}">
			<li><a href="/supplier/backUser/queryBackUserList.do"><i
					class="icon icon-user"></i> <span>用户管理</span></a></li>
			</c:if>
			<c:if test="${sessionScope.permission != 1}">
			<li style="display:none;"></li>
			</c:if>
			<li><a href="/supplier/queryUserList.do"><i
					class="icon icon-user"></i> <span>客户管理</span></a></li>
			<c:if test="${sessionScope.permission == 1}">
			<li><a href="/supplier/showFactoryInfo.do"><i
					class="icon icon-user"></i> <span>个人中心</span></a></li>
			</c:if>	
			<c:if test="${sessionScope.permission != 1}">
			<li style="display:none;"></li>
			</c:if>	
			<li><a href="/supplier/backUser/resetPassword.do"><i
					class="icon icon-key"></i> <span>重置密码</span></a></li>
	   </ul>
	</div>