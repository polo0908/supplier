<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
    <%
    Object amount = request.getAttribute("amount1");
    %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/supplier/css/bootstrap-responsive.min.css" />
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/uniform.css" />
<link rel="stylesheet" href="/supplier/css/upload.css" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
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
</style>
<script type="text/javascript" src="js/number.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript"
	src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-form.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript">  
	
    $(function(){
    	
    	$('#sidebar').find('li').eq(0).addClass('active');		
    })	
	
	//更新clientOrder invoice信息
	function doUpload_invoice() {

		var path = $("#file_upload").val();
		sppath = path.split("\\");
		var invoiceName = sppath[sppath.length - 1];
		$('#fileName').text(invoiceName);
		$('#getInvoicePath').val(invoiceName);
		if (path.length == 0) {
			easyDialog.open({
				container : {
					header : '  Prompt message',
					content : '  Please select a file !'
				},
				drag : false,
				overlay : false,
				autoClose : 1000
			});

			return false;
		}

		//	发送ajax请求,提交form表单    
		$("#file_upload_id")
				.ajaxSubmit(
						{
							type : "post",
							url : "/supplier/updateClientOrder.do",
							dataType : "text",
							success : function(result) {
								easyDialog
										.open({
											container : {
												content : 'upload complete'
											},
											drag : false,
											overlay : false,
											autoClose : 1000
										});
							},
							error : function() {
								easyDialog
										.open({
											container : {
												header : '  Prompt message',
												content : '  Upload failed. Please try again'
											},
											drag : false,
											overlay : false,
											autoClose : 2000
										});
							}
						});
	}
	//生成到款计划信息
	function saveBankInfo(invoiceId) {
		var transactionType = $('#transactionType').val();
		var bank = $('#bank_select').val();
		var amount1 = $('#amount_input1').val();
		var amount2 = $('#amount_input2').val();
		var amount3 = $('#amount_input3').val();
		var amount4 = $('#amount_input4').val();
		var amount5 = $('#amount_input5').val();
		var amount6 = $('#amount_input6').val();
		var paymentTime1 = $('#date_input1').val();
		var paymentTime2 = $('#date_input2').val();
		var paymentTime3 = $('#date_input3').val();
		var paymentTime4 = $('#date_input4').val();
		var paymentTime5 = $('#date_input5').val();
		var paymentTime6 = $('#date_input6').val();

		$('#save_btn').css({"background-color":"#666"}).attr("disabled","true");
		
		$.post("/supplier/updateInvoiceInfo.do", {
			"invoiceId" : invoiceId,
			"transactionType" : transactionType,
			"bank" : bank,
			"amount1" : amount1,
			"paymentTime1" : paymentTime1,
			"amount2" : amount2,
			"paymentTime2" : paymentTime2,
			"amount3" : amount3,
			"paymentTime3" : paymentTime3,
			"amount4" : amount4,
			"paymentTime4" : paymentTime4,
			"amount5" : amount5,
			"paymentTime5" : paymentTime5,
			"amount6" : amount6,
			"paymentTime6" : paymentTime6
		}, function(data) {

			$('#save_btn').css({"background-color":"#006dcc"}).removeAttr("disabled");
			var base = new Base64();
		    invoiceId=base.encode(invoiceId);  
			window.location = "/supplier/listInvoiceInfo.do?invoiceId="+ invoiceId;
		});

	}
	//跳转回编辑界面
	function doModify() {
		var orderId = $('#orderId').text();
		var invoiceId = $('#invoiceId').text();
		var base = new Base64();
    	invoiceId=base.encode(invoiceId);
    	orderId=base.encode(orderId);
		window.location.href = "/supplier/jumpToUpdateInvoice.do?orderId="
				+ orderId + "&invoiceId=" + invoiceId;
	}
</script>

</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllClientOrder.do" class="tip-bottom"><i
					class="icon  icon-paste"></i>客户订单管理</a> <a href="#" onclick="doModify();" 
					class="tip-bottom" data-original-title="">发票上传</a> <a href="#" class="current">上传发票</a>
			</div>
			<div class="shuru">
				<div class="shuruming">
					<p class="bianhao">项目编号 :<span id="orderId">${invoiceInfo.orderId}</span></p>
					<p class="bianhao2">发票编号 :<span id="invoiceId">${invoiceInfo.invoiceId}</span></p>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<hr style="clear: both;">
<!-- 			<div class="row-fluid"> -->
<!-- 				<div class="span12"> -->
<!-- 					<h1 style="font-size: 16px; margin: 0;">第二步 上传发票</h1> -->

<!-- 				</div> -->
<!-- 			</div> -->
<!-- 			<div class="widget-content nopadding" style="border-bottom: none;"> -->
<!-- 				<label class="control-label">上传发票: </label> -->
<!-- 				采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
<!-- 				<form id="file_upload_id" action="/supplier/updateClientOrder.do" -->
<!-- 					method="post" enctype="multipart/form-data"> -->
<!-- 					<div class="controls"> -->
<!-- 						<div class="uploader" id="uniform-undefined"> -->
<!-- 							<input type="file" id="file_upload" name="file_upload" size="19" -->
<!-- 								style="opacity: 0;" onChange="doUpload_invoice()"><span -->
<!-- 								class="filename" id="fileName">No file selected</span><span -->
<!-- 								class="action">Choose File</span><span id="prompt_file"></span> -->
<%-- 							<input name="invoiceId" value="${invoiceInfo.invoiceId}" --%>
<!-- 								type="hidden" id="invoiceId"> -->
<!-- 						</div> -->
<!-- 						<input id="getInvoicePath" value="" type="hidden" -->
<!-- 							name="invoiceName"> <input name="id" id="clientId" -->
<%-- 							value="${clientOrder.id}" type="hidden"> --%>
<!-- 					</div> -->
<!-- 				</form> -->
<!-- 				<div class="control-group"> -->
<!-- 					<label class="control-label">交易类型: </label> -->
<!-- 					<div class="controls"> -->
<!-- 						<select class="xiala_select" name="select" id="transactionType"> -->
<!-- 							<option value="0">新客户new</option> -->
<!-- 							<option value="1">老客户old</option> -->
<!-- 						</select> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 			</div> -->

			<div class="container-fluid">
				<hr style="clear: both;">
				<div class="row-fluid">
					<div class="span12">
						<h1 style="font-size: 16px; margin: 0;">第三步 到款计划</h1>

					</div>
				</div>
				<div class="control-group">
					<label class="control-label">付款银行: </label>
					<div class="controls">
						<select class="xiala_select" name="select" id="bank_select">
							<c:forEach var="bankInfo" items="${bankInfo}" varStatus="i">
								<option value="${bankInfo.id}" <c:if test="${bankInfo.id eq bank}"> selected="selected"</c:if>>${bankInfo.bank}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div>
			 
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input1" value="${amount1}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input1" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime1}">
						</div>
					</div>
				</div>
		        
				
				<div>
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input2" value="${amount2}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input2" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime2}">
						</div>
					</div>
				</div>
			      
			      
				<div>
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input3" value="${amount3}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input3" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime3}">
						</div>
					</div>
				</div>
				
				
				<div>
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input4" value="${amount4}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input4" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime4}">
						</div>
					</div>
				</div>
				

				<div>
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input5" value="${amount5}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input5" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime5}">
						</div>
					</div>
				</div>
				
	
				<div>
					<div class="control-group" style="width: 40%; float: left;">
						<label class="control-label">金额: </label>
						<div class="controls">
							<input type="text" name="required" id="amount_input6" value="${amount6}" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)">
						</div>
					</div>
					<div class="control-group" style="width: 60%; float: left;">
						<label class="control-label">日期: </label>
						<div class="controls">
							<input type="text" id="date_input6" data-date="2016-01-02"
								data-date-format="dd-mm-yyyy" placeholder="2016-01-01"
								onclick="WdatePicker({skin:'whyGreen',lang:'en'})" value="${paymentTime6}">
						</div>
					</div>
				</div>
			</div>

			
			<div class="container-fluid"
				style="padding-left: 0; padding-right: 0;">
				<div class="pull-right">
					<a class="btn btn-primary btn-large pull-right" id="save_btn" href="#"
						onclick="saveBankInfo('${invoiceInfo.invoiceId}')">提交</a>
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
