<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/uniform.css" />
<link rel="stylesheet" href="/supplier/css/upload.css" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript">


$(function(){
	
	$('#sidebar').find('li').eq(0).addClass('active');
	
})

//上传发票
function upload_invoice(orderId,invoiceId){
	window.location = "/supplier/queryInvoiceInfo.do?invoiceId="
		+ invoiceId + "&orderId=" + orderId;		
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
</style>

</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllClientOrder.do" class="tip-bottom"><i
					class="icon  icon-paste"></i>客户订单管理</a> <a href="#"
					class="tip-bottom" data-original-title="">发票上传</a> <a
					href="#" onclick="upload_invoice('${invoiceInfo.orderId}','${invoiceInfo.invoiceId}');" class="tip-bottom" data-original-title="">上传发票</a>
				<a href="#" class="current">发票详情</a>
			</div>
			<div class="shuru">
				<div class="shuruming">
					<p class="bianhao">项目编号 : ${invoiceInfo.orderId}</p>
					<p class="bianhao2">发票编号 : ${invoiceInfo.invoiceId}</p>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<hr style="clear: both;">
			<div class="row-fluid">
				<div class="div-span">
					<p class="bianhao">发票总金额: ${s} ${amountUnit.currency}</p>
					<p class="bianhao2">银行: ${bankInfo.bank}</p>
				</div>
				<!-- <div class="span">
                <p class="span-p1">发票总金额: 840.0000美金</p>
                <span>银行: 中行（工融）</span>
            </div>-->
			</div>
			<div class="row-fluid fluid-div">
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
												aria-label="Platform(s): activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													预计资金 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													预计日期 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
										</tr>
									</thead>
									<tbody role="alert" aria-live="polite" aria-relevant="all">
									    <c:forEach var="obj" items="${paymentPlans}" varStatus="i">
										<tr class="gradeA odd tbody-td">
											<td>${obj.amount}</td>
											<td>${obj.paymentTime}</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
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
