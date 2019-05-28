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
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />
<!--<link rel="stylesheet" href="css/jquery.gritter.css" />-->
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


<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-form.js"></script>
<script type="text/javascript">

	//计算每行图纸的价格(单价*数量)
	$(function() {
		
		$('#sidebar').find('li').eq(1).addClass('active');	
	
		
		
		var tl = $("#cartBody").find("tr").length;
		for (var i = 0; i < tl; i++) {
			var price = $("#cartBody tr:eq(" + i + ")").children("td:eq(1)")
					.find("span").text();
			var quantity = $("#cartBody tr:eq(" + i + ")").children("td:eq(3)")
					.text();
			var total = price * quantity;
			$("#cartBody tr:eq(" + i + ")").children("td:eq(4)").find("span")
					.text(total);
		}

	});

	//单个图纸下载
	function downloadDrawing(id) {

		window.location.href = "/supplier/fileDownload_reOrderDrawing.do?id="
				+ id;

	}

	//打包下载
	function downloadDrawings(id) {

		window.location.href = "/supplier/fileDownload_compression.do?reOrderId="
				+ id;
	}
	
	
	//更新图纸信息
	function doUpload_drawing(id){
		$('#id').val(id); 
		var $input = $("#file_upload_drawing" + id);
		var path = $input.val();
		sppath = path.split("\\");
		var drawingName = sppath[sppath.length - 1];
		$('#getDrawingName').val(drawingName);
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
		
		
		$("#file_upload_id").ajaxSubmit({
   			type: "post",
   			url: "/supplier/updateReOrderDrawingsById.do",
   	     	dataType: "text",
   	        success: function(result){
   	        	easyDialog
				.open({
					container : {
						header : '  Prompt message',
						content : 'upload complete'
					},
					drag : false,
					overlay : false,
					autoClose : 1000
				});
		       setTimeout(function(){location.reload()},1000);
   	        },
   	        error : function() {
				 easyDialog
						.open({
							container : {
								header : '  Prompt message',
								content : '  Upload failed. Please try again !'
							},
							drag : false,
							overlay : false,
							autoClose : 1000
						});
			}
		});
		
	}
</script>

</head>
<body>

<jsp:include page="base.jsp"></jsp:include>

	<!--sidebar-menu-->

	<!--main-container-part-->
		
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllReOrder.do" class="tip-bottom"><i
					class="icon icon-picture"></i>ReOrder订单</a><a href="#"
					class="tip-bottom" data-original-title="">Project</a>

			</div>
			<h1>Project</h1>
			<!--<h1 style="font-size: 26px;">共2个订单，合计$8,000</h1>-->
		</div>
				 <!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
					    <form id="file_upload_id" action="/supplier/updateReOrderDrawingsById.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
						<input id="id" name="id" type="hidden"/>
						<input name="drawingName1" id="getDrawingName" value="" type="hidden">
		<div class="container-fluid">
			<hr>
			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon"> <i class="icon icon-inbox"></i>
							</span>
							<h5>Project</h5>
						</div>
				
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped">
								<thead>

									<tr>
										<th style="width:20%;">图纸</th>
										<th>单价</th>
										<th>模板价格</th>
										<th>数量</th>
										<th>金额</th>
										<th style="width: 150px;">Download</th>
									</tr>
								</thead>
								<tbody id='cartBody'>
									<c:forEach var="obj" items="${reOrderDrawings}" varStatus="i">
										<tr class="odd gradeX" id="tr${i.index}">
											<td>${obj.drawingsName}</td>
											<td>$<span>${obj.unitPrice}</span></td>
											<td>$<span>${obj.moldPrice}</span></td>
											<td>${obj.quantity}</td>
											<td>$<span></span></td>
											<td style="position: relative;"><a href="#"><button class="btn btn-primary"
														onclick="downloadDrawing('${obj.id}');">下载</button></a>
											<a href="#"><button class="btn btn-primary">上传</button></a>
											<input type="file" style="opacity: 0;position: absolute;left: 68px;width: 55px; height: 30px; overflow: hidden;" id="file_upload_drawing${obj.id}" name="file_upload_drawing" size="19" onChange="doUpload_drawing('${obj.id}')">																				
										    </td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					
					</div>
					
					<div class="widget-box">

						<div class="widget-content nopadding">
<!-- 							<form class="form-horizontal"> -->

								<div class="control-group form-horizontal">
									<div>
										<label class="control-label">Required Delivery：</label>
										<div class="controls">
											<input type="text" readonly id="requiredTime"
												data-date="2016-09-08" data-date-format="yyyy-mm-dd"
												value="${reOrder.requiredTime}" class="span11">
										</div>
									</div>
									<div>
										<label class="control-label">Other Comments：</label>
										<div class="controls">
											<textarea class="span11" readonly id="comments">${reOrder.comments}</textarea>
										</div>
									</div>
								</div>

<!-- 							</form> -->
						</div>
					</div>
					<div class="pull-right">
						<h4 style="float: right;">
							<span>Total:$</span> ${reOrder.totalAmount }
						</h4>
						<br> <a class="btn btn-primary btn-large pull-right" href="#"
							style="margin-top: 15px;"
							onclick="downloadDrawings('${reOrder.id}')">Pack To Download</a>
					</div>
					
				</div>
			</div>
		</div>
		</form>
	</div>
<!--     </form> -->
	<!--end-main-container-part-->

	<!--Footer-part-->
	<div class="row-fluid">
		<div id="footer" class="span12">
		
		</div>
	</div>

</body>
</html>
