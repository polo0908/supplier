<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
<%@page import="com.cbt.util.DateFormat"%>
<%String[] userinfo = WebCookie.getUser(request);%>
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
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link rel="stylesheet" href="/supplier/css/drawing.css" />
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
<!--<link rel="stylesheet" href="css/jquery.gritter.css" />-->
<link rel="stylesheet" href="/supplier/css/style.css" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />
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

.btn-left2 {
	left: 92%;
	position: absolute;
	padding: 4px 20px;
	top: 58px;
}
</style>

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript">
     
	var customer_name = '<%=request.getParameter("userName")%>';
	var beginDate_select = '<%=request.getParameter("beginDate")%>';
	var endDate_select = '<%=request.getParameter("endDate")%>';
	   
    
	//页面加载时获取订单是否处理，如已跟进则不能点击
	$(function() {
		
		$('#sidebar').find('li').eq(1).addClass('active');
		
		if(customer_name != null &&  customer_name != '' && customer_name != 'null'){
			$('#userName').val(customer_name);
		}
		if(beginDate_select != null &&  beginDate_select != '' && beginDate_select != 'null'){
			$('#beginDate').val(beginDate_select);
		}
		if(endDate_select != null &&  endDate_select != '' && endDate_select != 'null'){
			$('#endDate').val(endDate_select);
		}
		
		
		var tl = $("#reOrderTbody").find("tr").length;
		for (var i = 0; i < tl; i++) {
			var drawingState = $("#reOrderTbody tr:eq(" + i + ")").children(
					"td:eq(5)").text();
// 			console.log(drawingState);
			var followUp = $("#reOrderTbody tr:eq(" + i + ")").children(
					"td:eq(6)").text();
			var historyId = $("#reOrderTbody tr:eq(" + i + ")").children(
					"td:eq(7)").text();
			
			if (drawingState == "操作中") {
				$("#reOrderTbody tr:eq(" + i + ")").children("td:eq(8)").find("button:eq(0)").attr("disabled", true).css({
					background : "#888"
				});
			}
			if (!(historyId == "" || historyId == null || historyId == "undefined")) {
				$("#reOrderTbody tr:eq(" + i + ")").children("td:eq(8)").find("button:eq(1)").css({"display":"none"}).attr("disabled", true).css({background : "#888"});		;
				$("#reOrderTbody tr:eq(" + i + ")").children("td:eq(8)").find("button:eq(0)").css({"display":"none"});
				$("#reOrderTbody tr:eq(" + i + ")").children("td:eq(8)").find("button:eq(2)").css({"display":"none"});
				$("#reOrderTbody tr:eq(" + i + ")").children("td:eq(8)").find("a:last").css({"display":"block"});				
		    }
		}		
     
	});

	//跟进弹框
	function showFollowUp(id) {
		$('#dialog2').show().on('click', '.weui_btn_dialog', function() {
			$('#dialog2').off('click').hide();
		});

		var a = $("#followId").val(id);
	}

	//处理跟进事件
	//当未处理时，当前登录用户即跟进人
	var user = '<%=userinfo != null ? userinfo[1] : ""%>';
	function dispose() {
		var id = $("#followId").val();
		var reOrderState = $('#reOrderState' + id).text();
		var followUp = $('#followUp' + id).text();
		var description = $('#description').val();

		$.post("/supplier/updateReOrderById.do", {
			"id" : id,
			"reOrderState" : reOrderState,
			"followUp" : followUp,
			"description" : description
		}, function(data) {

			if (!(reOrderState == "操作中")) {
				$('#reOrderState' + id).text("操作中");
				$('#followUp' + id).text(user);
				$('#dispose' + id).attr("disabled", true).css({
					background : "#888"
				});
			}
		});

	}

	function disclose() {
		$('#dialog2').hide();
	}

	//获取当前时间，格式YYYY-MM-DD 
	function getDay(day) {
		var today = new Date();

		var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24
				* day;

		today.setTime(targetday_milliseconds); //注意，这行是关键代码    

		var tYear = today.getFullYear();
		var tMonth = today.getMonth();
		var tDate = today.getDate();
		tMonth = doHandleMonth(tMonth + 1);
		tDate = doHandleMonth(tDate);
		return tYear + "-" + tMonth + "-" + tDate;
	}
	function doHandleMonth(month) {
		var m = month;
		if (month.toString().length == 1) {
			m = "0" + month;
		}
		return m;
	}

	/*
	 * 选择今日、昨日、一周、一月事件
	 */
	function today() {
		$("#date_ul li").css({
			color : "#666"
		});
		$('#beginDate').val(getDay(0));
		$('#endDate').val(getDay(0));
		$('#date_ul li:eq(0)').css({
			color : "#08c"
		});
	}
	function yesterday() {
		$("#date_ul li").css({
			color : "#666"
		});
		$('#beginDate').val(getDay(-1));
		$('#endDate').val(getDay(0));
		$('#date_ul li:eq(1)').css({
			color : "#08c"
		});
	}
	function lastWeek() {
		$("#date_ul li").css({
			color : "#666"
		});
		$('#beginDate').val(getDay(-7));
		$('#endDate').val(getDay(0));
		$('#date_ul li:eq(2)').css({
			color : "#08c"
		});
	}
	function lastMonth() {
		$("#date_ul li").css({
			color : "#666"
		});
		$('#beginDate').val(getDay(-30));
		$('#endDate').val(getDay(0));
		$('#date_ul li:eq(3)').css({
			color : "#08c"
		});
	}

	//还原按钮选中信息 
	function changeColor() {
		$("#date_ul li").css({
			color : "#666"
		});
	}

	//根据日期进行查询
	function queryByDate() {
		var userName = $('#userName').val();
		console
		var beginDate = $('#beginDate').val();
		var endDate = $('#endDate').val();
		//		   window.location = "${ctx}/queryAllRfqInfo.do?userName="+userName+"&beginDate="+beginDate+"&endDate="+endDate;   
		$.ajax({
			url : "/supplier/queryAllReOrder.do",
			data : {
				"userName" : userName,
				"beginDate" : beginDate,
				"endDate" : endDate
			},
			type : "get",
			dataType : "text",
			success : function() {
				window.location = "/supplier/queryAllReOrder.do?userName="
						+ userName + "&beginDate=" + beginDate + "&endDate="
						+ endDate;
			},
			error : function() {
				easyDialog.open({
					container : {
						header : '  Prompt message',
						content : '  Data is not legal, Please try again !'
					},
					drag : false,
					overlay : false,
					autoClose : 1000
				});
			}
		});
	}

	/*
	 *根据订单号展现所有图纸信息
	 */
	function reOrder(id) {
		var base = new Base64();
		id = base.encode(id);
		window.location.href = "/supplier/queryReOrderDrawings.do?id=" + id;

	}
	/*
	 * 生成订单
	 */
	 function create_order(id){
		var base = new Base64();
		id = base.encode(id);
		window.location.href = "/supplier/queryReOrderById.do?id=" + id;
	}
	
	//查看历史订单
	function queryClientOrder(customerId){
		window.location.href = "/supplier/queryAllReOrder.do?userName="+customerId;				
	}
	
	/*
	 * 关闭订单
	 */
	 function close_order(id){
		 if (confirm("是否关闭此订单？")){
		 $.post("/supplier/closeReOrderById.do", 
				 { "id": id },
				 function(data){
					 window.location.href =  "/supplier/queryAllReOrder.do";
					 
				 });
		 }
	}
	 
	
</script>

</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="#" class="tip-bottom"><i class="icon icon-copy"></i>ReOrder</a>
				<a href="#" class="current"></a>
			</div>
			<div class="shuru">
				<div class="shuruming">
					<input type="text" class="span11" id="userName"
						placeholder="客户ID/客户名称 "> <input type="text"
						data-date="01-02-2013" data-date-format="dd-mm-yyyy"
						id="beginDate" placeholder="<%=DateFormat.currentDate()%>" value=""
						onChange="changeColor()"
						onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						class="datepicker span11" style="margin-left: 5%;">&nbsp;<span
						style="font-size: 14px;">至</span>&nbsp;<input type="text"
						id="endDate" data-date="01-02-2013" data-date-format="dd-mm-yyyy"
						value="" onChange="changeColor()"
						onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						placeholder="<%=DateFormat.currentDate()%>" class="datepicker span12">
				</div>
				<div class="wenzi">
					<ul id="date_ul">
						<li onclick="today()" style="cursor: pointer;">今日</li>
						<li onclick="yesterday()" style="cursor: pointer;">昨日</li>
						<li onclick="lastWeek()" style="cursor: pointer;">最近7天</li>
						<li onclick="lastMonth()" style="cursor: pointer;">最近30天</li>
					</ul>
				</div>
				<button class="btn btn-info btn-left" style="padding: 4px 20px;"
					onclick="queryByDate()">查询</button>
				<a href="/supplier/toCreateClientOrder.do"><button
						class="btn btn-primary btn-left2">新建</button></a>
			</div>
			<!--<h1 style="font-size: 26px;">共2个订单，合计$8,000</h1>-->
		</div>
		<div class="container-fluid">
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-content nopadding">
							<div id="DataTables_Table_0_wrapper" class="dataTables_wrapper"
								role="grid">
								<table class="table table-bordered data-table dataTable"
									id="DataTables_Table_0">
									<thead>
										<tr role="row">
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="Rendering engine: activate to sort column descending">
												<div class="DataTables_sort_wrapper">
													客户ID <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-sort="ascending"
												aria-label="Rendering engine: activate to sort column descending">
												<div class="DataTables_sort_wrapper">
													客户名称 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending">
								 				<div class="DataTables_sort_wrapper">
													下单时间 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
<!-- 											<th class="ui-state-default" role="columnheader" tabindex="0" -->
<!-- 												aria-controls="DataTables_Table_0" rowspan="1" colspan="1" -->
<!-- 												aria-label="Platform(s): activate to sort column ascending"> -->
<!-- 												<div class="DataTables_sort_wrapper"> -->
<!-- 													发票编号 <span -->
<!-- 														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span> -->
<!-- 												</div> -->
<!-- 											</th> -->
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													图纸 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													预估金额 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													状态 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													跟进销售 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													操作 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
										</tr>
									</thead>
									<tbody role="alert" aria-live="polite" aria-relevant="all"
										id="reOrderTbody">
										<c:forEach var="obj" items="${reOrder}" varStatus="i">
											<tr class="gradeA odd" id="tr${i.index}">
												<td><a href="#" class="a2" style="color: #08c; text-decoration: underline;"
														onclick="queryClientOrder('${obj.userid}')">${obj.userid}</a></td>
												<td><a href="#" class="a2" style="color: #08c; text-decoration: underline;"
														onclick="queryClientOrder('${obj.userid}')">${obj.userName}</a></td>
												<td>
												<fmt:parseDate value="${obj.createTime}" pattern="yyyy-MM-dd" var="masterDate"/>
												<fmt:formatDate value="${masterDate}" pattern="yyyy-MM-dd" />
												</td>
<%-- 												<td>${obj.invoiceId}</td> --%>
												<td class="center"><a href="#"
													onclick="reOrder('${obj.id}');">${obj.drawingNames}</a></td>
												<td><span>$</span>${obj.totalAmount}</td>
												<td id="reOrderState${obj.id}">${obj.reOrderState == 1 ? "未操作" : "操作中"}</td>
												<td id="followUp${obj.id}">${obj.followUp}</td>
												<td style="display:none">${obj.historyId}</td>
												<td class="center">
												<a href="#"><button
															class="btn btn-success" id="dispose${obj.id}"
															onclick="showFollowUp('${obj.id}')">处理</button></a>
															<a href="#"><button class="btn btn-info"
															onclick="create_order('${obj.id}')">生成订单</button></a>
															<a href="#"><button class="btn btn-danger"
															onclick="close_order('${obj.id}')">关闭订单</button></a>
															<a href="#" style="display:none;"><button class="btn btn-danger">已结束</button></a>	
															</td>									
											</tr>
										</c:forEach>
									</tbody>
								</table>
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
			<div class="weui_dialog_alert" id="dialog2" style="display: none;">
				<div class="weui_mask"></div>
				<div class="weui_dialog">
					<div class="weui_dialog_hd">
						<strong class="weui_dialog_title">跟进处理</strong>
					</div>
					<textarea class="weui_textarea" placeholder="备注" rows="4"
						id="description" name="description"></textarea>
					<a class="close-reveal-modal" href="javascript:void(0);"
						onclick="disclose()">×</a>
					<!--<a class="close-reveal-modal">×</a>-->
					<div class="weui_dialog_ft">
						<input name="poName" id="followId" value="" type="hidden">
						<a href="javascript:;" class="weui_btn_dialog primary"
							onclick="dispose()">跟进</a>
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
				<div  class="control-group" >
					<label class="cont-label div-labe6">复制区域 : </label>
					<div class="con div-controls">
						<textarea id="copy_text" cols=50 rows=5 onChange="clip.setText(this.value)"></textarea>
					</div>
				</div>
				<div class="control-group" style="margin-top: 10px;">
					<button id="copyLinkBtn" class="btn btn-primary" onclick="generateTheLink()">生成客户链接</button>
				</div>
			</div>
			<a class="close-reveal-modal" href="javascript:void(0);"
				onclick="closeDialog()">×</a>

		</div>
	</div>
	
	<div class="row-fluid">
		<div id="footer" class="span12">

		</div>
	</div>
</body>
</html>