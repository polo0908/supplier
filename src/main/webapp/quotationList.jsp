<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
<%@page import="com.cbt.util.DateFormat"%>

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
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link rel="stylesheet" href="/supplier/css/drawing.css" />
<link href="/supplier/font-awesome/css/font-awesome.css"
	rel="stylesheet" />
<link rel="stylesheet" href="css/ui.css">
<link rel="stylesheet" href="css/ui.progress-bar.css">
<link rel="stylesheet" href="css/upload-base.css">
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
.w-right{
	position: absolute;
    right: 20px;
    top: 6px;}
.shuru  input {
	width: 15%;
}
.z-file{
    position: absolute;
    top: -3px;
    left: -3px;
    width: 1px;
    opacity: 0;
}
</style>

<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-form.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script type="text/javascript">

	var customer_name = '<%=request.getParameter("user")%>';
	var beginDate_select = '<%=request.getParameter("beginDate")%>';
	var endDate_select = '<%=request.getParameter("endDate")%>';

	
	$(function(){
		
// 		$('#sidebar').find('li').eq(2).addClass('active');
		
		if(customer_name != null && customer_name != '' && customer_name != 'null'){
			$('#userName').val(customer_name);
		}
		if(beginDate_select != null  &&  beginDate_select != '' && beginDate_select != 'null'){
			$('#beginDate').val(beginDate_select);
		}
		if(endDate_select != null && endDate_select != '' && endDate_select != 'null'){
			$('#endDate').val(endDate_select);
		}
		
		
		
	});
	
	/*
	 *根据userName、时间进行查询
	 */
	var userName;
	var beginDate;
	var endDate;
	function queryByDate() {
		userName = $('#userName').val();
		beginDate = $('#beginDate').val();
		endDate = $('#endDate').val();
        
		window.location = "/supplier/queryAllQuotation.do?user=" + userName
				+ "&beginDate=" + beginDate + "&endDate=" + endDate;

	}
	
	
	//根据id删除
	function delete_quotation(quotationInfoId,obj){
		
		if (confirm("是否删除此报价单？")) {
		 $.post("/supplier/deleteQuotation.do", 
				  { 
			        "quotationInfoId" : quotationInfoId
			      },
				   function(result){
			    	  if(result.state == 0){
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '删除成功 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	 $(obj).parents('tr').remove();  
			    		  
			    	  }else{
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '删除失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	  }
			      })	  
			    	  
		}		      
			      
	}
	
	//预览下载报价单
	function view_quotation(quotationInfoId){
		//使用ajax提交下载
		$.ajax({
			url:"/supplier/fileDownload_quotation.do",
			data:{
				  "quotationInfoId":quotationInfoId
				  },
			type:"post",
			dataType:"text",
			success:function(res){						
				window.location.href = "/supplier/fileDownload_quotation.do?quotationInfoId="+quotationInfoId;				
				},
		    error:function(){
		    	
		    	easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '下载失败'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
		    }
		});
	}
	
	
	//编辑报价单
	function edit_quotation(quotationInfoId){
		var base = new Base64();
		quotationInfoId = base.encode(quotationInfoId);
		window.location = "/supplier/toModifyQuotation.do?quotationInfoId="+quotationInfoId;	
	}
	
	//根据客户ID进行筛选
	function query_quotationByCustomerId(customerId){
		window.location = "/supplier/queryAllQuotation.do?user="+customerId;
	}
	
	//查询客户留言
	function queryQuotationMessageById(quotationInfoId){
		var base = new Base64();
		quotationInfoId = base.encode(quotationInfoId);
		window.location = "/supplier/queryQuotationMessageById.do?quotationInfoId="+quotationInfoId;
	}
	
	//发送报价邮件
	function send_quotation_email(quotationInfoId){
		
		 $.post("/supplier/sendQuotationEmail.do", 
				  { 
			        "quotationInfoId" : quotationInfoId
			      },
				   function(result){			    	  
			    	  if(result.state == 0){
			    		  window.open("http://112.64.174.34:43887/NBEmail/helpServlet?action=sendQuotation&className=ExternalInterfaceServlet&eid="+result.data.userId+"&projectId="+result.data.projectId+"&content="+result.data.quotationAttr); 
			    	      
			    	  }else if(result.state == 2){
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : result.message
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	  }else{
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '打开NBMail失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	  }
			      })	  	      
	}
	
	

	 //上传pdf
	function upload_pdf(obj){
			var path = $(obj).val();
		    sppath = path.split("\\");
		    var drawingName = sppath[sppath.length-1];	  	   
		    if(drawingName == null || drawingName == '' || drawingName == undefined){
		    	return false;
		    }else{
		    	
      	        var gen = drawingName.lastIndexOf("."); 
      	        var type = drawingName.substr(gen); 
      	        if(type.toLowerCase() != ".pdf"){
      	        	easyDialog.open({
			      		   container : {
			          		     header : ' 提示信息',
			          		     content : ' 文件必须为pdf格式'
			      		   },
							  overlay : false,
							  autoClose : 1000
			      		 });    
      	        	return false;
      	        }		    	
	    	   autTime(); 
			   $('#upload_title').children().text("上传进度");
		    }	 		    	
	     		
			  
			  //先上传后获取上传文件路径
			 $(obj).parents('form').ajaxSubmit({    			
				type: "post",
				url: "/supplier/uploadQuotationPdf.do",
		     	dataType: "text",
		     	success: function(str){
		     	var result = eval('(' + str + ')');	
		     	if(result.state == 0){
		     		easyDialog.open({
			      		   container : {
			          		     header : '  提示信息',
			          		     content : ' 上传成功 '
			      		   },
							  overlay : false,
							  autoClose : 1000
			      		 });    
		     	}else{
	       	     	easyDialog.open({
		      		   container : {
		          		     header : '  提示信息',
		          		     content : ' 上传失败 '
		      		   },
						  overlay : false,
						  autoClose : 1000
		      		 });   
	       	     	$('#show_upload_dialog').hide();
		     	}	

		     	},
				error: function(){
					 easyDialog.open({
	         		   container : {
	             		     header : '  提示信息',
	             		     content : '  上传失败 '
	         		   },
						  overlay : false,
						  autoClose : 1000
	         		 });   
		     		$('#show_upload_dialog').hide();
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
				<a href="/supplier/queryAllQuotation.do" class="tip-bottom"><i
					class="icon icon-picture"></i>报价单</a> 
					<div class="w-right">
<!--             <button class="btn btn-success btn-mini" onclick="location='/supplier/toCreateQuotation.do';">新建报价单</button> -->
         </div>
			</div>
			<div class="shuru">
				<div class="shuruming">
					<input type="text" class="span11" id="userName"
						placeholder="客户ID/客户名称/项目号"> <input type="text"
						data-date="01-02-2013" data-date-format="dd-mm-yyyy"
						id="beginDate" placeholder="<%=DateFormat.currentDate()%>" value=""
						onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						class="datepicker span11" style="margin-left: 5%;">&nbsp;<span
						style="font-size: 14px;">至</span>&nbsp;<input type="text"
						id="endDate" data-date="01-02-2013" data-date-format="dd-mm-yyyy"
						value="" 
						onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						placeholder="<%=DateFormat.currentDate()%>" class="datepicker span12">
				</div>
				<button class="btn btn-info btn-left" style="padding: 4px 20px;left: 65%;"
					onclick="queryByDate()">查询</button>
			</div>
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
												aria-label="Rendering engine: activate to sort column descending" style="width: 7%;">
												<div class="DataTables_sort_wrapper">
													项目号<span
														class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													客户ID <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>							
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													客户名称 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>							
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Platform(s): activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													报价主题 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
										    <th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													询价日期 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Browser: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													产品名 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													报价员 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													报价销售 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													报价日期 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													有效期 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													报价状态<span
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
									<tbody role="alert" aria-live="polite" aria-relevant="all" id="rfqTbody">
									 <c:forEach var="obj" items="${quotationInfos}" varStatus="i">
									<tr>
										<td style='text-align:center'><a href="#">${obj.projectId}</a></td>
										<td style='text-align:center'>
										<a href="#" class="a2" style="color: #08c; text-decoration: underline;" onclick="query_quotationByCustomerId('${obj.userId}')">
										${obj.userId}
										</a>
										</td>
										<td style='text-align:center'>
										<a href="#" class="a2" style="color: #08c; text-decoration: underline;" onclick="query_quotationByCustomerId('${obj.userId}')">
										${obj.customerName}
										</a>
										</td>
										<td style='text-align:center'>${obj.quotationSubject}</td>
										<td style='text-align:center'>
										<fmt:parseDate value="${obj.createTime}" pattern="yyyy-MM-dd" var="masterDate"/>
										<fmt:formatDate value="${masterDate}" pattern="yyyy-MM-dd" />
										</td>
										<td style='text-align:center'>${obj.productNames}</td>
										<td style='text-align:center'>${obj.quoter}</td>
										<td style='text-align:center'>${obj.saleName}</td>
										<td style='text-align:center'>${obj.quotationDate}</td>
										<td style='text-align:center'>${obj.quotationValidity} days</td>
										<td style='text-align:center'>${obj.quotationStatus == 1 ? "报价中" : (obj.quotationStatus == 2 ? "已发送" : "")}</td>
										<td style='text-align:center'>
										<a href="#"><button class="btn btn-primary" onclick="edit_quotation('${obj.id}')">编辑</button></a>
										<form onsubmit="return false;" method="post" enctype="multipart/form-data">
										<a href="#" style="position: relative;">
											<input style="width:1px;" class="z-file" type="file" name="file1" onchange="upload_pdf(this)"/>
											<input type="hidden" name="quotationInfoId" value="${obj.id}">
										<button class="btn btn-success">上传</button></a>
										</form>
										<a href="#"><button class="btn btn-success" onclick="view_quotation('${obj.id}')">预览</button></a>
										<a href="#" style="position: relative;"><button class="btn btn-success" onclick="queryQuotationMessageById('${obj.id}')">留言</button><c:if test="${unReadMessages.get(i.count-1) != 0}"><span class="label label-important w_span_message">${unReadMessages.get(i.count-1)}</span></c:if></a>
										<a href="#"><button class="btn btn-info" onclick="send_quotation_email('${obj.id}')">发送</button></a>
										<a href="#"><button class="btn btn-danger" onclick="delete_quotation('${obj.id}',this)">删除</button></a>
									</tr>
									</c:forEach>
									</tbody>	
								</table>
<!-- 								<div -->
<!-- 									class="fg-toolbar ui-toolbar ui-widget-header ui-corner-bl ui-corner-br ui-helper-clearfix"> -->
<!-- 									<div class="dataTables_filter" id="DataTables_Table_0_filter"> -->
<!-- 									</div> -->
<!-- 									<div -->
<!-- 										class="dataTables_paginate fg-buttonset ui-buttonset fg-buttonset-multi ui-buttonset-multi paging_full_numbers" -->
<!-- 										id="DataTables_Table_0_paginate">Total:<span id="total">0</span>　Show:<span id="currency_pageSize">10</span>  -->
<!-- 										paging:<span id="currency_page" style="color:red">1</span>/<span id="total_page" style="color:red">1</span>　 -->
<!-- 										paging:  -->
<!-- 										<span onclick="queryQuotationList(1)" title=" The first page">[First]</span>  -->
<!-- 										<span onclick="queryQuotationPrevious()" title=" The first page">[Previous]</span>  -->
<!-- 										<span onclick="queryQuotationNext()" title="The last page">[Next]</span>  -->
<!-- 										<span onclick="queryQuotationEnd()" title="The last page">[Last]</span> &nbsp;&nbsp;Jump to<input style="width:15px;height:12px" name="page" id="page" type="text" value="" size="3">page	 -->
<!-- 										<input type="button" value="submit" onclick="jumpPage();">	 -->
<!-- 									</div> -->
<!-- 								</div> -->
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
	<!-- 上传进度条 -->
    		<div class="w-out-box" id="show_upload_dialog" style="display:none;">
			<div class="weui_mask"></div>
			<div class="w-weui_dialog" style="width:510px;">
			  <div id="container">
			
				<div class="content">
					<h1>上传进度</h1>
				</div>
				
				<!-- Progress bar -->
				<div id="progress_bar" class="ui-progress-bar ui-container">
				<div class="ui-progress" style="width: 0%;" id="ui-progress-upload">
				<span class="ui-label" style="display:none;">正在加载...<b class="value">0%</b></span>
				</div>
				</div>
				<!-- /Progress bar -->
			    <a class="close-reveal-modal" style="color:#eee;font-size:22px;" href="javascript:void(0);" onclick="cancel_upload()">×</a>
				<div class="content" id="main_content" style="display: none;">
					<p>加载完成。</p>
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
