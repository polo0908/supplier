<%@page import="java.util.List"%>
<%@page import="com.cbt.entity.User"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	    $('#sidebar').find('li').eq(4).addClass('active');
		
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

	//生成客户链接
	function closeDialog(){
		$('#copy_div').hide();		
	}
	
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
				<a href="#" class="current">登录日志</a>
			</div>
			<div class="container-fluid">
    <hr>
    <div class="row-fluid">
        <div class="span12">
          <div class="widget-box">
            <div class="widget-title">
              <ul class="nav nav-tabs">
                <li class="active"><a data-toggle="tab" href="#tab1">登录成功</a></li>
                <li><a data-toggle="tab" href="#tab2">登录失败</a></li>

              </ul>
            </div>
            <div class="widget-content tab-content" style="padding: 0px;border: none;">
            <div class="widget-content nopadding tab-pane active" id="tab1">
              <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">

                <table class="table table-bordered data-table dataTable">
                <thead>
                  <tr role="row">
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">ID
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">邮箱
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">用户名
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">登录时间
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                      <div class="DataTables_sort_wrapper">登录IP
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 250px;">
                      <div class="DataTables_sort_wrapper">登录状态
                        <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                      </div>
                    </th>
                    
                  </tr>
                </thead>

                <tbody role="alert" aria-live="polite" aria-relevant="all">
                <c:forEach var="obj" items="${log1}" varStatus="i">
                <tr class="gradeA even">
                  <td>${i.index}</td>
                  <td>${obj.loginEmail}</td>
                  <td>${obj.username}</td>
                  <td>${obj.loginTime}</td>
                  <td>${obj.loginIp }</td>
                  <td class="center">
                    <span>成功登录</span>
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
              <div class="widget-content nopadding tab-pane" id="tab2">
                <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">

                  <table class="table table-bordered data-table dataTable" id="DataTables_Table_0">
                    <thead>
                    <tr role="row">
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Rendering engine: activate to sort column descending" style="width: 130px;">
                        <div class="DataTables_sort_wrapper">ID
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Browser: activate to sort column ascending" style="width: 130px;">
                        <div class="DataTables_sort_wrapper">邮箱
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending" style="width: 130px;">
                        <div class="DataTables_sort_wrapper">用户名
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                        <div class="DataTables_sort_wrapper">登录失败时间
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 250px;">
                        <div class="DataTables_sort_wrapper">登录IP
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 250px;">
                        <div class="DataTables_sort_wrapper">错误信息
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                      <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending" style="width: 130px;">
                        <div class="DataTables_sort_wrapper">Operation
                          <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                        </div>
                      </th>
                    </tr>
                    </thead>

                    <tbody role="alert" aria-live="polite" aria-relevant="all"><tr class="gradeA odd">
                    <c:forEach var="obj" items="${log2}" varStatus="i">
                      <tr class="gradeA even">
                      <td>${i.index}</td>
                      <td>${obj.loginEmail}</td>
                      <td>${obj.username}</td>
                      <td>${obj.loginFailTime}</td>
                      <td>${obj.loginIp }</td>
                      <td class="center">  
                        ${obj.errorInfo }               
                      </td>
                      <td class="center"><span>登录失败</span></td>
                    </tr>
                    </c:forEach>
                    </tbody>
                  </table>
                    <div class="fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix" style="border-bottom:none;">
                  <div class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers" id="DataTables_Table_0_paginate">
<%--                     ${pager2} --%>
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