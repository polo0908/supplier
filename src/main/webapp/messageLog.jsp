<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.List"%>
<%@page import="com.cbt.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@page import="com.cbt.util.WebCookie"%>
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
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript">



	$(function() {
	    $('#sidebar').find('li').eq(3).addClass('active');		
	});

	
</script>
<style>
.shuruming {
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
    padding-top: 10px;
    /* width: 180px; */
    float: left;
    text-align: right;
}
.div-labe6 {
    width:20%;
}
.con {
    text-align: left;
    margin-left:25%;
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
</style>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>

	<!--sidebar-menu-->

	<!--main-container-part-->
	<div id="content">
		<div id="content-header">
		<div id="breadcrumb">
				<a href="/supplier/queryUserList.do" class="tip-bottom"><i class="icon icon-user"></i>客户管理</a>
				<a href="#" class="current">
				<c:if test="${type == 0}">订单更新客户</c:if>
				<c:if test="${type == 1}">最近邀请客户</c:if>
				<c:if test="${type == 2}">最近登录客户</c:if>
				</a>
			</div>
			<div class="container-fluid">
    <hr>
    <div class="row-fluid">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#tab1">
                <c:if test="${type == 0}">订单更新客户</c:if>
                <c:if test="${type == 1}">最近邀请客户</c:if>
                <c:if test="${type == 2}">最近登录客户</c:if>
                </a>
                </li>
              </ul>
            </div>
            <div class="widget-content tab-content" style="padding: 0px;border: none;">
            <div class="widget-content nopadding tab-pane active" id="tab1">
              <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">

                <table class="table table-bordered data-table dataTable">
                <thead>
                  <tr role="row">
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">客户ID
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">客户名
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">公司名称
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper"><span>
                      <c:if test="${type == 2}">登录时间</c:if>
                      <c:if test="${type == 0}">操作时间</c:if>
                      <c:if test="${type == 1}">邀请时间</c:if>
                      </span>
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <c:if test="${type == 0 || type == 1}">
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper"><span>操作销售</span>
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>   
                    </c:if> 
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper"><span>是否有邮箱</span>
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>   
                  </tr>
                </thead>

                <tbody role="alert" aria-live="polite" aria-relevant="all">
                <c:forEach var="obj" items="${users}" varStatus="i">
                <tr class="gradeA even">
                  <td><a <c:if test="${obj.encryptedEmail == null || obj.encryptedEmail == ''}"></c:if>
                  <c:if test="${obj.encryptedEmail != null && obj.encryptedEmail != ''}">href="https://importx.net/crm/queryByAdmin.do?userInfo=${obj.encryptedEmail}" target="_blank" style="color: #08c;text-decoration: underline;"</c:if>
                   >${obj.userid}</a></td>
                  <td><a <c:if test="${obj.encryptedEmail == null || obj.encryptedEmail == ''}"></c:if>
                  <c:if test="${obj.encryptedEmail != null && obj.encryptedEmail != ''}">href="https://importx.net/crm/queryByAdmin.do?userInfo=${obj.encryptedEmail}" target="_blank" style="color: #08c;text-decoration: underline;"</c:if>
                   >${obj.userName}</a></td>
                  <td>${obj.companyName}</td>
                  <td>
                  <c:if test="${type == 0 || type == 1}">${obj.operationTime != null ?fn:substring(obj.operationTime,0,fn:indexOf(obj.operationTime,".")):""}</c:if>
                  <c:if test="${type == 2}">${obj.loginTime != null ?fn:substring(obj.loginTime,0,fn:indexOf(obj.loginTime,".")):""}</c:if>
                  </td>
                  <c:if test="${type == 0 || type == 1}">
                  <td>${obj.saleName}</td>     
                  </c:if>
                  <td>
                  <c:if test="${obj.loginEmail == null || obj.loginEmail == ''}">无</c:if>
                  <c:if test="${obj.loginEmail != null && obj.loginEmail != ''}">有</c:if>
                  </td>
                 </tr>         
                </c:forEach>
                </tbody>
              </table>
                  <div class="fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix" style="border-bottom:none;">
                  <div class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers" id="DataTables_Table_0_paginate">
<%--                     ${pager} --%>
                  </div>
                </div>
              </div>
            </div>           
           </div>
          </div>
        </div>
  </div>
 </div>
			
		</div>
	</div>
	

	<!--end-main-container-part-->

	<!--Footer-part-->

	<div class="row-fluid">
		<div id="footer" class="span12">

		</div>
	</div>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>	
</body>
</html>