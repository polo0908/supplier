<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%@page import="com.cbt.util.WebCookie"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>ImportX</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="/supplier/js/jquery-1.8.3.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/uniform.css" />
<link rel="stylesheet" href="/supplier/css/bigInvoice.css" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link rel="stylesheet" href="css/ui.css">
<link rel="stylesheet" href="css/ui.progress-bar.css">
<link rel="stylesheet" href="css/upload-base.css">
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />



<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript">

	
	$(function(){
		  
       $('#sidebar').find('li').eq(0).addClass('active');

		
	})
    
	//修改发票信息
	function doModify(orderId, invoiceId) {
		var base = new Base64();
    	invoiceId=base.encode(invoiceId);
    	orderId=base.encode(orderId);
		window.location.href = "/supplier/jumpToUpdateInvoice.do?orderId="
				+ orderId + "&invoiceId=" + invoiceId;
	}

	
	//删除发票
	function doDelete(id,orderId,invoiceId) {
		if (confirm("是否删除此发票？")) {
			$.ajax({
				type : "post",
				datatype : "json",
				url : "/supplier/deleteInvoice.do",
				data : {
					"id" : id,
					"orderId" : orderId,
					"invoiceId" : invoiceId
				},
				success : function(data) {
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '删除成功 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
					setTimeout(function(){location.reload()},1000);
				},
				error : function() {
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '删除失败，请重试 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
					setTimeout(function(){location.reload()},1000);
				}
			});
		}
	}
	
	//发票详情页
	function invoice_details(invoiceId){
		var base = new Base64();
    	invoiceId=base.encode(invoiceId);
		window.location = "/supplier/listInvoiceInfo.do?invoiceId=" + invoiceId;
	}
	
	//下载pdf
	function doDownload_pdf(id){
		window.location = "/supplier/fileDownload_invoice.do?id=" + id;		
	}
	
	//上传pdf发票
function doUpload_invoice(obj,id) {

	var path = $(obj).val();
	sppath = path.split("\\");
	var invoiceName = sppath[sppath.length - 1];
	$('#invoice_name').val(invoiceName);
	if (invoiceName.trim() == "" || invoiceName.trim() == null || invoiceName.trim() == undefined) {		
		return false;
	}else{
		$('#invoiceInfo_id').val(id);
		autTime();
		$('#upload_title').children().text("发票上传进度");
	}
	//	   	发送ajax请求,提交form表单    
	$("#file_upload_id")
			.ajaxSubmit(
					{
						type : "post",
						url : "/supplier/updateInvoicePdf.do",
						dataType : "text",
						success : function(str) {
							var result = eval('(' + str + ')');
							if(result.state == 0){							 
								easyDialog.open({
									container : {
										header : '  Prompt message',
										content : ' 上传成功'
									},
									overlay : false,
									autoClose : 1000
								});
							}else{
								easyDialog.open({
									container : {
										header : '  Prompt message',
										content : '  上传失败，请重试'
									},
									overlay : false,
									autoClose : 1000
								});
								 $('#show_upload_dialog').hide();	
							}
						},
						error : function() {
							easyDialog
									.open({
										container : {
											header : '  Prompt message',
											content : '  上传失败，请重试'
										},
										overlay : false,
										autoClose : 1000
									});
							 $('#show_upload_dialog').hide();	
						}
					});
}

	
	//跳转发票创建界面
	function toCreate_invocie(orderId){
		var base = new Base64();
    	orderId=base.encode(orderId);
		window.location = "/supplier/queryDrawingInfo.do?orderId="+orderId;
	}
	
	
</script>
<style>
    .upload{position: absolute;
    margin-left: 60px;
    width: 130px;
    height: 30px;
    z-index: 99;
    opacity: 0;}
/*     .a2{
    margin-left: 60px;} */
    
    </style>
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
#file_upload_invoice{
cursor:pointer;
}

</style>
</head>
<body>

<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllClientOrder.do" class="tip-bottom">
				<i class="icon  icon-paste"></i>历史订单管理</a> 
				<a href="#" class="current">发票</a>

			</div>
			<div class="shuru">
				<div class="shuruming">
					<p class="bianhao">项目编号 : ${orderId}</p>
					<p class="bianhao2">发票合计 : ${total}&nbsp;${currency}</p>
				</div>
			</div>
		</div>
		<!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
  <form id="file_upload_id" action="${ctx}/updateInvoicePdf.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
	<input type="hidden" name="invoiceInfoId" id="invoiceInfo_id">
	<input type="hidden" name="invoiceName" id="invoice_name">
		<div class="container-fluid">
			<hr style="clear: both;">
			<a href="#"><button
					class="btn btn-primary" onclick="toCreate_invocie('${orderId}')">创建发票</button></a>
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
												aria-label="Platform(s): activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													序号 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													发票编号 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													发票金额 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													创建时间 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													实际总收款 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													最近付款 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													最近付款时间 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
										</tr>
									</thead>

									<tbody role="alert" aria-live="polite" aria-relevant="all" id="invoice_tbody">
									
                                        <c:forEach var="obj" items="${invoiceInfos}" varStatus="i">
										<tr class="gradeA odd">
											<td>${i.index+1}</td>
											<td><a href="#" class="a2" style="color: #08c; text-decoration: underline;"
											   onclick="invoice_details('${obj.invoiceId}');">${obj.invoiceId}</a></td>
											<td>${obj.amount}<span style="color:red">（${amountUnit[i.count-1].currency}）</span></td>
											<td>${obj.createTime}</td>
											<td>${obj.sumAmountActual}</td>
											<td>${obj.amountActual}</td>
											<td>${obj.paymentTime}</td>
											<td class="center">
												<button class="btn btn-primary"
													onclick="doModify('${orderId}','${obj.invoiceId}')">编辑</button>
											    <input type="file" name="file_upload_invoice" onChange="doUpload_invoice(this,'${obj.id}')" style="position: absolute;width: 5%;opacity: 0;">
												<button class="btn btn-primary">上传发票</button>
												<c:if test="${obj.invoicePath == null || obj.invoicePath == ''}">
												<button class="btn btn-primary" style="background-color: #cecbcb;">下载</button>
												</c:if>
												<c:if test="${obj.invoicePath != null && obj.invoicePath != ''}">
												<button class="btn btn-primary"
													onclick="doDownload_pdf('${obj.id}')">下载</button>
												</c:if>
												<button class="btn btn-danger" style="margin-left: 10px;"
													onclick="doDelete('${obj.id}','${obj.orderId}','${obj.invoiceId}')">删除</button>
												<input type="file" class="upload" id="file_upload_invoice" name="file_upload_invoice" onChange="doUpload(this,'${obj.id}')" />
<!-- 	                	                        <a href="#" class="a2"><button class="btn btn-primary">update invoice</button></a> -->
											</td>
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
		</form> 
	</div>
	<div class="row-fluid">
		<div id="footer" class="span12">
			
		</div>
	</div>

</body>
</html>