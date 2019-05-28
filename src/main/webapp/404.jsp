<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet" href="/supplier/css/bootstrap-responsive.min.css" />
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
 <link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
  <link type='image/x-ico' rel='icon' href='/supplier/img/favicon.ico' />

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<!--<link rel="stylesheet" href="css/jquery.gritter.css" />-->
<style>
    body{
        font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
    }
    .shuruming{
        margin-top: 20px;
         float: none;
        text-shadow: 0 1px 0 #ffffff;
        margin-left: 20px;
        position: relative;
    }
.shuru  input{width: 15%;}
</style>
</head>

<script type="text/javascript">


	<%String[] userinfo = WebCookie.getUser(request);%>
	var userid = '<%=userinfo != null ? userinfo[0] : ""%>';
	//用户名
	var userName = '<%=userinfo != null ? userinfo[1] : ""%>';
	//用户权限
	var permission = '<%=userinfo != null ? userinfo[2] : ""%>';
	

</script>	

<body>
<jsp:include page="base.jsp"></jsp:include>
<div id="content">
    <div id="content-header">
        <div id="breadcrumb"> <a href="#"  class="tip-bottom"><i class="icon  icon-paste"></i>客户订单管理</a> <a href="#" class="current"></a>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                   <!-- <div class="widget-title"> <span class="icon"> <i class="icon-info-sign"></i> </span>
                        <h5>Error 404</h5>
                    </div>-->
                    <div class="widget-content">
                        <div class="error_ex">
<!--                             <h1>404</h1> -->
                            <h3>Opps, You're lost.</h3>
                            <p>We can not find the page you're looking for.</p>
                            <a class="btn btn-warning btn-big" href="${ctx}/queryAllClientOrder.do">Back to Home</a> </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> 
<!--   2013 &copy; Shanghai ce melting. More Templates <a href="#" target="_blank" title="">上海策融</a>  -->
  </div>
</div>

</body>
</html>
