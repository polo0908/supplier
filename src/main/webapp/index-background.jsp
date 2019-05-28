<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
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
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
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

.a2 input {
	width: 20px;
	position: relative;
	opacity: 0;
}

.btn-left {
	left: 78%;
	position: absolute;
	padding: 4px 20px;
	top: 58px;
}

.btn-left2 {
	left: 84%;
	position: absolute;
	padding: 4px 20px;
	top: 58px;
}

.btn-left3 {
	left: 90%;
	position: absolute;
	padding: 4px 20px;
	top: 58px;
}

#shipping_doc_ul li{
    float: left;
    margin-left: 9%;
}

</style>

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-form.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript">

    var customer_name = '<%=request.getParameter("userName")%>';
    var beginDate_select = '<%=request.getParameter("beginDate")%>';
    var endDate_select = '<%=request.getParameter("endDate")%>';
    var orderType = '<%=request.getParameter("orderTypeId")%>';
    
    

	/*
	 *页面加载后，获取可下载数据。高亮显示
	 *如不可下载，则显示灰色，不可点击
	 */
	$(function() {
		
		
		$('#sidebar').find('li').eq(0).addClass('active');
			
		
		if(customer_name != null &&  customer_name != '' && customer_name != 'null'){
			$('#userName').val(customer_name);
		}
		if(beginDate_select != null &&  beginDate_select != '' && beginDate_select != 'null'){
			$('#beginDate').val(beginDate_select);
		}
		if(endDate_select != null &&  endDate_select != '' && endDate_select != 'null'){
			$('#endDate').val(endDate_select);
		}
		
		if(orderType != null &&  orderType != '' && orderType != 'null'){
			$('#select_type').val(orderType);
		}
		
		
		
		var tl = $("#clientOrders").find("tr").length;
		for (var i = 0; i < tl; i++) {
			//对不同国家日期格式化处理
// 			var stringTime = $("#clientOrders tr:eq(" + i + ")").children("td:eq(2)").text();
//  	        $("#clientOrders tr:eq(" + i + ")").children("td:eq(2)").text(format(stringTime));

//  	       var outPutTime = $("#clientOrders tr:eq(" + i + ")").children("td:eq(3)").text();
//  	        $("#clientOrders tr:eq(" + i + ")").children("td:eq(3)").text(format(outPutTime));
 	        
			//获取后台poPath的值，当数据不为空，显示已上传
// 			var poPath = $("#clientOrders tr:eq(" + i + ")").children(
// 					"td:eq(2)").find("input").val();
// 			if (poPath == "" || poPath == null) {
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("a:eq(0)").removeAttr("onclick").css({color : "rgb(22, 39, 18)"});
// 			}

			//获取后台qcReportPath的值，当数据不为空，显示已上传
// 			var qcReportPath = $("#clientOrders tr:eq(" + i + ")").children("td:eq(5)").find("input").val();
// 			if (qcReportPath == "" || qcReportPath == null) {
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("a:eq(2)").removeAttr("href").removeAttr("onclick").css({color : "rgb(22, 39, 18)"});
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("span:eq(1)").hide();
// 			}else{
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("span:eq(1)").show();
// 			}
			//获取后台shippingDocPath的值，当数据不为空，显示已上传
// 			var shippingDocPath = $("#clientOrders tr:eq(" + i + ")").children(
// 					"td:eq(6)").find("input").val();
// 			if (shippingDocPath == "" || shippingDocPath == null) {
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("a:eq(3)").removeAttr("href").removeAttr("onclick").css({color : "rgb(22, 39, 18)"});
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("span:eq(2)").hide();
// 			}else{
// 				$("#clientOrders tr:eq(" + i + ")").children("td:eq(8)").find("span:eq(2)").show();
// 			}
			//判断订单状态(0：In Process，1：Finished)  
			var orderState = 0;
			orderState = $("#clientOrders tr:eq(" + i + ")").children(
					"td:eq(7)").text();
			if (orderState == 0) {
				$("#clientOrders tr:eq(" + i + ")").children("td:eq(7)").text(
						"In Process");
			} else if (orderState == 1) {
				$("#clientOrders tr:eq(" + i + ")").children("td:eq(7)").text(
						"Finished");
			}

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
        
		window.location = "/supplier/queryAllClientOrder.do?userName=" + userName
				+ "&beginDate=" + beginDate + "&endDate=" + endDate;

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

	//更新clientOrder po信息
	function doUpload_po(id) {

		$('#clientId').val(id);
		var $input = $("#file_upload_po" + id);
		var path = $input.val();
		sppath = path.split("\\");
		var poName = sppath[sppath.length - 1];
		$('#getPoPath').val(poName);
		if (path.length == 0) {
			easyDialog.open({
				container : {
					header : '  Prompt message',
					content : '  Please select a file !'
				},
				drag : false,
				overlay : false,
				autoClose : 3000
			});

			return false;
		}

		//	   	发送ajax请求,提交form表单    
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
												header : '  Prompt message',
												content : 'upload complete, 2 seconds after the closure...'
											},
											drag : false,
											overlay : false,
											autoClose : 2000
										});
								setTimeout(function(){location.reload()},2000);
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
											autoClose : 2000
										});
							}
						});
	}

	//更新clientOrder qc信息
	function doUpload_qc(id) {

		$('#clientId').val(id);
		var $input = $("#file_upload_qc" + id);
		var path = $input.val();
		sppath = path.split("\\");
		var qcName = sppath[sppath.length - 1];
		$('#getQcPath').val(qcName);
		if (path.length == 0) {
			easyDialog.open({
				container : {
					header : '  Prompt message',
					content : '  Please select a file !'
				},
				drag : false,
				overlay : false,
				autoClose : 2000
			});

			return false;
		}

		//	   	发送ajax请求,提交form表单    
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
												header : '  Prompt message',
												content : 'upload complete , 2 seconds after the closure...'
											},
											drag : false,
											overlay : false,
											autoClose : 2000
										});
								setTimeout(function(){location.reload()},2000);
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
											autoClose : 2000
										});
							}
						});
	}

	//更新clientOrder shipping信息
	function doUpload_shipping(id) {

		$('#clientId').val(id);
		var $input = $("#file_upload_shipping" + id);
		var path = $input.val();
		sppath = path.split("\\");
		var shippingName = sppath[sppath.length - 1];
		$('#getShippingPath').val(shippingName);
		if (path.length == 0) {
			easyDialog.open({
				container : {
					header : '  Prompt message',
					content : '  Please select a file !'
				},
				drag : false,
				overlay : false,
				autoClose : 2000
			});

			return false;
		}

		//	   	发送ajax请求,提交form表单    
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
												header : '  Prompt message',
												content : 'upload complete , 2 seconds after the closure...'
											},
											drag : false,
											overlay : false,
											autoClose : 2000
										});
								setTimeout(function(){location.reload()},2000);
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
											autoClose : 2000
										});
							}
						});
	}
	//打包下载
// 	function fileDownloadAll(id) {

			   
// 		    	//使用ajax提交下载
// 				$.ajax({
// 					url:"/supplier/fileDownload_all.do",
// 					data:{
// 						  "clientOrderId":id
// 						  },
// 					type:"post",
// 					dataType:"text",
// 					success:function(res){
						
// 						window.location.href = "/supplier/fileDownload_all.do?clientOrderId=" + id;
						
// 						},
// 				    error:function(){
				    	
// 				    	easyDialog.open({
// 				    		  container : {
// 				    		    header : 'Prompt message',
// 				    		    content : 'Download failed !'
// 				    		  },
// 				    		  overlay : false,
// 				    		  autoClose : 2000
// 				    		});
// 				    }
// 				});
		   
// 		window.location.href = "/supplier/fileDownload_all.do?clientOrderId=" + id;
// 	}

	//显示详细订单
	function order_details(orderId, userid) {
		 var base = new Base64();
		 orderId=base.encode(orderId);
		 userid=base.encode(userid); 
		window.location = "/supplier/querDetails.do?orderId="
				+ orderId + "&userid=" + userid;
	}
	
	
	//获取并显示客户详情框
	function customer_details(customerId){
		
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryUserById.do",
			data : {
				"customerId" : customerId
			},
			success : function(data) {
				var user = data;
				$('#customerId').text(user.userid);
				$('#customerName').text(user.userName);
				$('#customerEmail').text(user.email);
				$('#customerTel').text(user.tel);
				$('#customerDetialDialog').show();
			},
			error : function() {
				easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '获取客户详情失败，请重试'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
			}
		});	
	}
	
	//查看历史订单
	function queryClientOrder(customerId){
		window.location.href = "/supplier/queryAllClientOrder.do?userName="+customerId;				
	}
	
	
	//打开导入订单窗口
	function show_import_order(){
		$('#copy_div2').show();
	}
	function closeDialog2(){
		$('#copy_div2').hide();
	}
	
	//通过项目号导入
	function import_order(){		
		
		  var orderId = $('#import_order_id').val(); 
		  if(orderId == null || orderId == ""){
			  easyDialog.open({
	    		  container : {
	    		    header : 'Prompt message',
	    		    content : '请输入订单号'
	    		  },
	    		  overlay : false,
	    		  autoClose : 1000
	    		}); 
			  return false;
		  }
		  var orderId = $('#import_order_id').val(); 
		  var customerEmail = $('#customer_email').val();
		  $.post("/supplier/port/sendOrderId2ERP.do", 
				  { 
			        "orderId" : orderId,
			        "customerEmail" : customerEmail
			      },
				   function(result){
			    	  if(result.state == 0){
			    		  easyDialog.open({
				    		  container : {
				    		    header : 'Prompt message',
				    		    content : '导入成功'
				    		  },
				    		  overlay : false,
				    		  autoClose : 1000
				    		});
			    		  $('#copy_div2').hide();
			    	  }else{
			    		  easyDialog.open({
				    		  container : {
				    		    header : 'Prompt message',
				    		    content : '导入失败'
				    		  },
				    		  overlay : false,
				    		  autoClose : 1000
				    		});
			    	  }
			      })	  
	}
	
	
	//根据客户排序
	function sortByUserId(){
		window.location = "/supplier/queryAllOrderByUser.do";		
	}
	
	function query_invoice(orderId){
		var base = new Base64();
    	orderId=base.encode(orderId);
		window.location = "/supplier/queryInvoiceByOrderId.do?orderId="+orderId;
	}
	
	
	
</script>
<script>
//     $(function() {
    	
    	
    	//鼠标悬停事件，可进行删除操作
//         $('.div-move').hover(function() { 
//     	    $(".tip").hide();
//         	var path = $(this).next().next().find('th:eq(0)').text();
//         	if(path == '' || path == null){
//         		return false;
//         	}
//       	    sppath = path.split("\/");                          //注意linux路径不同
//     	    var qcReport = sppath[sppath.length-1];
//     	    $(this).next().next().find('th:eq(0)').text(qcReport);
//             $(this).next().next().show(); 
        	
//          })
  
//           $(this).find('div.tip').mouseleave(function(){
//         	 var timer = setTimeout(function(){
//            		$(".tip").fadeOut("normal");
//            		 },2000)   
//            })
        
//             $(this).on("click","button.fa-minus",function(){  
//             	var id = $(this).next().val(); 
//             	if (confirm("是否删除当前QC Report？")) {
//         			$.ajax({
//         				type : "post",
//         				datatype : "json",
//         				url : "/supplier/deleteQcReport.do",
//         				data : {
//         					"clientOrderId" : id
//         				},
//         				success : function(data) {
//         					alert('删除成功！');
//         					window.location.reload();
//         				},
//         				error : function() {
//         					alert('删除失败，请重试！');
//         					window.location.reload();
//         				}
//         			});
//         		}
            	
//             	return false;
//             })
        
   
        
        
//     })
</script>
<style>


/*正在发送中*/
*{ padding:0; margin:0;}
.layer {
  background: rgba(0, 0, 0, 0.4);
  display: none;
  height: 100%;
  left: 0;
  position: fixed;
  top: 0;
  width: 100%;
}
.layer_com {
  background: rgba(0, 0, 0, 0.8) url(/supplier/img/loading.gif) no-repeat center 15px;
  border-radius: 8px;
  height: 50px;
  left: 45%;
  opacity: 0.8;
  position: absolute;
  text-align: center;
  top: 40%;
  width: 120px;
  z-index: 6000;
}
.layer_com p {
  color: #fff;
  font-size: 20px;
  padding-top: 15px;
}







select, input[type="file"] {
	height: 20px;
	line-height: 30px;
	/* width: 30px; */
} 
.tip {
  position: absolute;
  top: -30px;
/*   width: 95px; */
  left: 45%;
  display:none;
  white-space :nowrap;
}
.tip table
  {
  border-collapse:collapse;
  }

.tip table, .tip table th
  {background:#fff;
  border:1px solid #ddd;
  }
.tip th{padding:3px 10px;}
.div-move{position: relative;}
.move_cont{position:relative;display:inline-block;}

a:hover{
 cursor:pointer;
}
.div-dingd{    width: 100%;
    float: left;
    text-align: left;
    margin-bottom: 10px;}
    
    
.z-type-select{
    width: 99px;
    height: 29px;
}    

</style>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="#" class="tip-bottom"><i class="icon  icon-paste"></i>客户订单管理</a>
				<a href="#" class="current"></a>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span7">
						<div class="w-box">
							<div class="controls-row">
								<div class="span4">
									<input type="text" id="userName" class="span12 m-wrap" placeholder="客户ID/客户名称 /项目号">
								</div>
								<div class="span7">
									<div class="span5">
						            <input type="text" id="beginDate"
							placeholder="<%=DateFormat.currentDate()%>" value=""
							onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
							onChange="changeColor()" data-date="2013-01-02"
							data-date-format="dd-mm-yyyy" class="datepicker span12"
							></div>
							<span
							style="font-size: 14px;line-height:30px;float:left;">&nbsp;&nbsp;至</span>
							<div class="span5">
								<input type="text"
							id="endDate" data-date="2013-01-02" data-date-format="dd-mm-yyyy"
							value="" onChange="changeColor()"
							onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
							placeholder="<%=DateFormat.currentDate()%>" class="datepicker">
							</div>
								</div>
					        </div>
						</div>
					</div>
					<div class="span5">
						<div class="w-box">
							<div class="controls-row">
								<div class="span6">
									<ul id="date_ul" class="w-date_ul">
										<li onclick="today()" style="padding-left:0;">今日</li>
										<li onclick="yesterday()">昨日</li>
										<li onclick="lastWeek()">最近7天</li>
										<li onclick="lastMonth()">最近30天</li>
									</ul>
								</div>
								<div class="span6" style="text-align: right;">
									<div class="datepicker span12">
									<button class="btn btn-info"
					onclick="queryByDate()">查询</button>
				<a href="/supplier/toCreateClientOrder.do"><button
						class="btn btn-primary">新建</button></a>	
						
				<a href="#"><button class="btn btn-primary" onclick ="show_import_order()">导入订单</button></a>
				</div>
				<span class="help-block w-help-block">（如果未查询到订单请导入）</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
			</div>
		</div>
		
		<!-- 输入订单号导入订单 -->
		
		<div class="weui_dialog_alert" id="copy_div2" style="display:none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="waimian2" style="padding:0px 20px;">
											<div class="control-group" id="copy_div3">
												<p class="cont-label div-labe6 div-dingd">导入订单: （如果是ERP订单，请输入客户邮箱）</p>
												<div class="con div-controls" style="margin-left:5%;">
												
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">订单号： </label>
													<div class="con div-controls span7">
														<input type="text" name="makeupCo1" id="import_order_id" value="" placeholder="请输入" style="height: 25px;border: 1px solid #ccc;padding:0 0;">	
														<span style="color:red;font-size: 20px">*</span>
													</div> 
												</div>
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">客户邮箱： </label>
													<div class="con div-controls span7">
														<input value="" id="customer_email" style="height: 25px;border: 1px solid #ccc;">
													</div>  
												</div>
												
											   <!--      <span style="width:100%;float:left;">
														<em style="font-style:normal;width:75px;display:inline-block;">订单号：</em><input type="text" name="makeupCo1" id="import_order_id" value="" placeholder="请输入" style="width: 186px;height: 25px;border: 1px solid #ccc;padding:0 0;"> <span style="color:red;font-size: 20px">*</span>														
													</span>	
                                                      <span><em style="font-style:normal;width:75px;display:inline-block;">客户邮箱：</em><input value="" id="customer_email" style="width: 186px;height: 25px;border: 1px solid #ccc;">										
													<button style=" position: absolute;top: 75%;background: #49afcd;color: #fff;padding: 2px 10px;border: none;left: 150px;" onclick="import_order()">导入</button></span>									
											 -->	</div>
													
											</div>
											<button type="submit" class="btn btn-info" style="margin-bottom:20px;" onclick="import_order()">导入</button>
										</div>
			                           <a class="close-reveal-modal" href="#" onclick="closeDialog2()">×</a>
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

								<!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
								<form id="file_upload_id" method="post" onsubmit="return false;"
									enctype="multipart/form-data">
									<input name="poName" id="getPoPath" value="" type="hidden">
									<input name="qcName" id="getQcPath" value="" type="hidden">
									<input name="shippingName" id="getShippingPath" value=""
										type="hidden"> <input name="id" id="clientId" value=""
										type="hidden">
										<div class="control-group">
								            <label class="control-label w-con-label">项目类型 </label>
								            <div class="controls">
								                <input value="0" type="hidden">
								                <select class="w-select"  id="select_type" onchange="select_order_type(this)">
													 <option value="">非隐藏订单</option>
								                     <c:forEach var="obj" items="${types}" varStatus="i">
								                     <option value="${obj.id}">${obj.orderType}</option>
								                     </c:forEach>
								                </select>
								            </div>
								        </div>
									<table class="table table-bordered data-table dataTable"
										id="DataTables_Table_0">
										<thead>
											<tr role="row">
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1" aria-sort="ascending"
													aria-label="Rendering engine: activate to sort column descending"
													>
													<div class="DataTables_sort_wrapper">
														项目编号 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1" aria-sort="ascending"
													aria-label="Rendering engine: activate to sort column descending"
													>
													<div class="DataTables_sort_wrapper">
														项目类型 <span class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1" aria-sort="ascending"
													aria-label="Rendering engine: activate to sort column descending"
													>
													<div class="DataTables_sort_wrapper">
														<a href="#" onclick="sortByUserId()">客户ID</a> <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1" aria-sort="ascending"
													aria-label="Rendering engine: activate to sort column descending"
													>
													<div class="DataTables_sort_wrapper">
														客户名称 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-triangle-1-n"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Browser: activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														下单时间 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Platform(s): activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														出库时间 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														金额(USD) <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														状态 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														下载 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
												<th class="ui-state-default ui-th" role="columnheader"
													tabindex="0" aria-controls="DataTables_Table_0" rowspan="1"
													colspan="1"
													aria-label="Engine version: activate to sort column ascending"
													>
													<div class="DataTables_sort_wrapper">
														消息提醒 <span
															class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
													</div>
												</th>
											</tr>
										</thead>
										<tbody role="alert" aria-live="polite" aria-relevant="all"
											id="clientOrders">
											<c:forEach var="order" items="${clientOrder}" varStatus="i">
												<tr class="gradeA odd" id="tr${i.index}">
													<td class="link"><a href="#" class="a2"
														style="color: #08c; text-decoration: underline;"
														onclick="order_details('${order.orderId}','${order.userid}');">${order.orderId}</a></td>
													<td class="link">
													     <select class="z-type-select" onchange="update_type('${order.id}',this)">
										                      <option value="5"></option>
										                      <c:forEach var="obj" items="${types}" varStatus="j">
										                      <option value="${obj.id}"<c:if test="${obj.id eq order.orderTypeId}"> selected="selected"</c:if>>${obj.orderType}</option>
										                      </c:forEach>
										                </select>
													
													</td>
													<td>
													<a  class="a2" style="color: #08c; text-decoration: underline;"
														onclick="queryClientOrder('${order.userid}')">${order.userid}</a>
													<input id="poPath" value="${order.poPath}" type="hidden"></td>
													<td>
													<a href="#" class="a2" style="color: #08c; text-decoration: underline;"
														onclick="queryClientOrder('${order.userid}')">${order.userName}</a>
													<td>${order.createTime}</td>
													<td>${order.outputTime}<input id="qcReportPath"
														value="${order.qcReportPath}" type="hidden"></td>
													<td>${order.amount}<input id="shippingDocPath"
														value="${order.shippingDocPath}" type="hidden"></td>
													<td>${order.orderStatus}</td>
													<td class="center" style="text-align:left;">
														<c:if test="${poCounts[i.count-1] == 0}">
														<a class="a2" style="color:rgb(22, 39, 18);">PO</a>
														</c:if>	
											            <c:if test="${poCounts[i.count-1] != 0}">
														<a class="a2" style="color:#08c;" onclick="queryPo('${order.orderId}')">PO</a>
														</c:if>	
														<a onclick="query_invoice('${order.orderId}')" class="a2">Invoice</a> 
														<div class="move_cont">			
														<c:if test="${qcCounts[i.count-1] == 0}">					
														<a class="a2 div-move"  style="color:rgb(22, 39, 18);">QC Report</a>
														</c:if>	
														<c:if test="${qcCounts[i.count-1] != 0}">					
														<a class="a2 div-move" onclick="queryQcReport('${order.orderId}')">QC Report</a>
														</c:if>	
                                                        </div>
											            <c:if test="${shippingCounts[i.count-1] == 0}">
														<a class="a2" style="color:rgb(22, 39, 18);">Shipping Doc</a>
														</c:if>	
											            <c:if test="${shippingCounts[i.count-1] != 0}">
														<a class="a2" onclick="queryShippingDoc('${order.orderId}')">Shipping Doc</a>
														</c:if>	
														</td>
													<td class="center"><a href="#" class="nowrap"><button
																class="btn btn-primary" onclick="show_send_mail_div('${order.user.loginEmail}','${order.userid}','${order.orderId}')">发送邮件提醒</button></a></td>													
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</form>
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
		
				<div class="weui_dialog_alert" id="po_div" style="display:none;">
				    <div class="weui_mask"></div>
				    <div class="weui_dialog" style="width:30%;">
				        <div class="row-fluid">
				            <div class="span12">
				                <div class="">
				                    <div class="widget-title">
				                        <h5 style="float: initial;">PO</h5>
				                    </div>
				                    <div class="widget-content">
				                        <div class="row-fluid">
				                            <div class="span12 btn-icon-pg">
				                                <ul id="po_ul">
				                                  
				                                </ul>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
				        </div>
				        <a class="close-reveal-modal" href="javascript:void(0);" onclick="close_po_div()">×</a>
				    </div>
				</div>
				
				
				<div class="weui_dialog_alert" id="qc_report_div" style="display:none;">
				    <div class="weui_mask"></div>
				    <div class="weui_dialog" style="width:30%;">
				        <div class="row-fluid">
				            <div class="span12">
				                <div class="">
				                    <div class="widget-title">
				                        <h5 style="float: initial;">QC Reports</h5>
				                    </div>
				                    <div class="widget-content">
				                        <div class="row-fluid">
				                            <div class="span12 btn-icon-pg">
				                                <ul id="qc_report_ul">
				                                  
				                                </ul>
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
				        </div>
				        <a class="close-reveal-modal" href="javascript:void(0);" onclick="close_qc_div()">×</a>
				    </div>
				</div>
	
	
		<div class="weui_dialog_alert new_weui_part" id="shipping_doc_div" style="display:none;">
				    <div class="weui_mask"></div>
				    <div class="weui_dialog" style="width:32%;">
				        <div class="row-fluid">
				            <div class="span12">
				                <div class="">
				                    <div class="widget-title">
				                        <h5 style="float: initial;">Shipping Doc</h5>
				                    </div>
				                    <div class="widget-content">
				                        <div class="row-fluid">
				                            <div class="span12 btn-icon-pg" id="shipping_doc_ul">
				             
				                            </div>
				                        </div>
				                    </div>
				                </div>
				            </div>
				        </div>
				        <a class="close-reveal-modal" href="javascript:void(0);" onclick="close_shipping_div()">×</a>
				    </div>
				</div>
	



	
	    <!-- 选择邮箱邀请登录 -->								
		<div class="weui_dialog_alert" id="copy_div1" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
		
		<input id="customer_select" value="" type="hidden"> 
		<div class="waimian2 " >
					<div class="row-fluid" id="copy_div">
						<label class="cont-label div-labe6 span4">选择消息类型 : </label>
						<div class="con div-controls w-margin-out" style="margin-left:35%">
							<span
								style=" border: 1pt solid #c1c1c1; overflow: hidden; width: 220px; height:25px; clip: rect(-1px, 220px, 220px, 170px);">
								<select name="makeupCoSe" id="makeupCoSe"
								style="width: 220px; height: 27px; margin: -1px;">
								<option></option>
								<c:forEach var="obj" items="${emailAlertInfos}" varStatus="i">
                                <option value="${obj.id}">${obj.emailType}</option>
                                </c:forEach>
							</select>
							</span>
							<div class="layer" style="display:none;" id="send_mail_div"><div class="layer_com"><p>正在发送</p></div></div>
						</div>
						<button class="btn btn-primary" onclick="sendMail(this)">发送</button>
						<input type="hidden" id="login_email_addr">	  
						<input type="hidden" id="customer_id">	  
						<input type="hidden" id="order_id">	  
					</div>
					</div>
		        <a class="close-reveal-modal" href="#" onclick="closeDialog1()">×</a>
	         </div>
	      </div>
	
	
	
	
	

	<div class="row-fluid">
		<div id="footer" class="span12">
<!-- 			2013 © Shanghai ce melting. More Templates <a href="#" -->
<!-- 				target="_blank" title="">上海策融</a> -->
		</div>
	</div>
	
</body>
<script>
/*
 *下载PO数据
 */
 function doDownload_po(id){
	   
 	//使用ajax提交下载
		$.ajax({
			url:"/supplier/fileDownload_po.do",
			data:{
				  "id":id
				  },
			type:"post",
			dataType:"text",
			success:function(res){
				
				window.location.href = "/supplier/fileDownload_po.do?id="+id;
				
				},
		    error:function(){
		    	
		    	easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '下载失败 '
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
		    }
		});
}
/*
 *下载QC数据
 */
 function doDownload_qc(id){ 
 	//使用ajax提交下载
		$.ajax({
			url:"/supplier/fileDownload_qc.do",
			data:{
				  "id":id
				  },
			type:"post",
			dataType:"text",
			success:function(res){
				
				window.location.href = "/supplier/fileDownload_qc.do?id="+id;
				
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




	//点开发送邮件提示弹框
	function show_send_mail_div(loginEmail,customerId,orderId){
		$('#login_email_addr').val(loginEmail);
		$('#customer_id').val(customerId);
		$('#order_id').val(orderId);
		$('#copy_div1').show();
	}
	//关闭发送邮件提示弹框
    function closeDialog1(){
    	$('#copy_div1').hide();
    	$('#login_email_addr').val('');
    	$('#customer_id').val('');
    	$('#order_id').val('');
     }

	
	//发送邮件
    function sendMail(obj){
    	
    	var loginEmail = $('#login_email_addr').val();
		var customerId = $('#customer_id').val();
		var emailTypeId = $('#makeupCoSe').val();
		var orderId = $('#order_id').val();
		
    	if(loginEmail == null || loginEmail == ""){		   
 		   easyDialog.open({
 	    		  container : {
 	    		    header : 'Prompt message',
 	    		    content : ' * 邮箱不能为空'
 	    		  },
 	    		  autoClose : 1000
 	    		});
 			return false; 
 	   }
 		 //验证邮箱是否合法
  	   var szReg=/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+/;     	    	   
  	   if(!(loginEmail == null || loginEmail.length==0)){
  		   var bChk = szReg.test(loginEmail);
 			
 			if(!bChk){
 				easyDialog.open({
 		    		  container : {
 		    		    header : 'Prompt message',
 		    		    content : ' * 邮箱格式不正确'
 		    		  },
 		    		  autoClose : 1000
 		    		});
 				return false;
 			}
 		 }  
  	  $('#send_mail_div').show();
  	  $(obj).css({"background-color":"#666"}).attr('disabled',"true"); 
  	 $.post("/supplier/port/sendMailByMessageType.do",
				{
			  "loginEmail":loginEmail,
			  "customerId" : customerId,
			  "emailTypeId" : emailTypeId,
			  "orderId" : orderId
			    },
				function(result){
			    $(obj).css({"background-color":"#006dcc"}).removeAttr("disabled"); 	
			    if(result.state == 0){
			    $('#send_mail_div').hide();		    	
			    if(result.message == "YES"){			    				    

			    		easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : ' 发送成功'
			    		  },
			    		  autoClose : 1000
			    		});
			    		closeDialog1();
			    		
			    		 //保存到日志表
				    	 $.post("/supplier/saveSendMailLog.do",	
						    		 {
				    		         "orderId":orderId,
				    		         "customerId":customerId,		
				    		         "messageType":emailTypeId
						    	     },
								    function(result){
						    	     	 
						       })
			    }else{
			    	easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : ' 发送失败'
			    		  },
			    		  autoClose : 1000
			    		});
			    }
				 
			}else{
				$('#send_mail_div').hide();
				easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : ' 发送失败'
		    		  },
		    		  autoClose : 1000
		    		});
			}
			
		});
    }



  //筛选	
  function select_order_type(obj){	  	  
	  var orderTypeId = $(obj).val();
	  window.location = "/supplier/queryAllClientOrder.do?orderTypeId=" + orderTypeId;
  }


  //选择项目类型
  function update_type(id,obj){
	  
	  if($(obj).val() == null || $(obj).val() == "" || $(obj).val() == undefined){
		 return false;
	  }
	  
	  $.post("/supplier/updateOrderTypeById.do",
				{
			  "clientOrderId":id,
			  "orderTypeId" : $(obj).val()
			    },
				function(result){
			    	
			    	if(result.state == 0){			    		
// 			    		setTimeout(function(){location.reload(force)},500);
			    		window.location.href=window.location.href;
			    	}else{
			    	  easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : ' 更新失败'
			    		  },
			    		  autoClose : 1000
			    		});
			    	}
			   })

  }

  /*
   *查询po报告
   */
  function queryPo(orderId){
	  
	  $.post("/supplier/queryPo.do",
				{
			  "orderId":orderId
			    },
				function(result){	
			    	if(result.state == 0){			    		
			    		var pos = result.data;
			    		if(pos.length != 0){
			    		$('#po_ul').empty();
			    		for(var i=0;i<pos.length;i++){
			    			var li= '<li>'+
                                     '<a style="text-decoration: underline;color:#08c;" onclick="doDownload_po('+pos[i].id+')">'+pos[i].poPath+'</a>'+                   
								     '<p style="float:right;color: #bbb;">'+pos[i].uploadDate+'</p>'+
                                    '</li>';	
			    			$('#po_ul').append(li);       
			    		}
			    		$('#po_div').show();
			    		}
			    	}else{
			    	  easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : '获取失败'
			    		  },
			    		  autoClose : 1000
			    		});
			    	}
			   })
  
  }
  
  
  /*
   *查询qc报告
   */
  function queryQcReport(orderId){
	  
	  $.post("/supplier/queryQcReports.do",
				{
			  "orderId":orderId
			    },
				function(result){	
			    	if(result.state == 0){			    		
			    		var qcReports = result.data;
			    		if(qcReports.length != 0){
			    		$('#qc_report_ul').empty();
			    		for(var i=0;i<qcReports.length;i++){
			    			var li= '<li>'+
                                     '<a style="text-decoration: underline;color:#08c;" onclick="doDownload_qc('+qcReports[i].id+')">'+qcReports[i].qcReportPath+'</a>'+                   
								     '<p style="float:right;color: #bbb;">'+qcReports[i].uploadDate+'</p>'+
                                    '</li>';	
			    			$('#qc_report_ul').append(li);       
			    		}
			    		$('#qc_report_div').show();
			    		}
			    	}else{
			    	  easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : '获取失败'
			    		  },
			    		  autoClose : 1000
			    		});
			    	}
			   })
  
  }

  /*
   *查询shipping报告
   */
  function queryShippingDoc(orderId){
	  
	  $.post("/supplier/queryShippingReports.do",
				{
			   "orderId":orderId
			    },
				function(result){			    	
			    	if(result.state == 0){			    		
			    		var shippingReports = result.data;
			    		if(shippingReports.length != 0){
			    		$('#shipping_doc_ul').empty();
			    		for(var i=0;i<shippingReports.length;i++){
			    			 var shippingDoc = shippingReports[i];
				    	   $('#shipping_doc_ul').append("<div>第"+(shippingReports.length-i)+"次</div><ul></ul>"); 


			    			 if(!(shippingDoc.blpath == null || shippingDoc.blpath == '' || shippingDoc.blpath == undefined)){
			    					var li= '<li class="weui_list11">'+
                                    '<span>BL正式件:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',1)">'+shippingReports[i].blpath+'</a>'+                   
								     '<p style="float:right;color: #bbb;">'+shippingReports[i].bluploadDate+'</p>'+
                                   '</li>';	
			    			        $('#shipping_doc_ul').find('ul').eq(i).append(li);   
						    	 }
						    	 if(!(shippingDoc.invoicePath == null || shippingDoc.invoicePath == '' || shippingDoc.invoicePath == undefined)){
						    		 var li= '<li class="weui_list22">'+
	                                    '<span>Invoice:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',2)">'+shippingReports[i].invoicePath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].invoiceUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);    
						    	 }
						    	 if(!(shippingDoc.packingListPath == null || shippingDoc.packingListPath == '' || shippingDoc.packingListPath == undefined)){
						    		 var li= '<li class="weui_list">'+
	                                    '<span>Packing List:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',3)">'+shippingReports[i].packingListPath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].packingListUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);      
						    	 }
						    	 if(!(shippingDoc.formAPath == null || shippingDoc.formAPath == '' || shippingDoc.formAPath == undefined)){
						    		 var li= '<li class="weui_list">'+
	                                    '<span>FORM A:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',4)">'+shippingReports[i].formAPath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].formAUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);    
						    	 }
						    	 if(!(shippingDoc.blcopyPath == null || shippingDoc.blcopyPath == '' || shippingDoc.blcopyPath == undefined)){
						    		 var li= '<li class="weui_list">'+
	                                    '<span>BL复印件:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',5)">'+shippingReports[i].blcopyPath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].blcopyUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);    
							    	 }
						    	 if(!(shippingDoc.packagingPath == null || shippingDoc.packagingPath == '' || shippingDoc.packagingPath == undefined)){
						    		 var li= '<li class="weui_list">'+
	                                    '<span>Packaging:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',6)">'+shippingReports[i].packagingPath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].packagingUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);     
						    	 }
						    	 if(!(shippingDoc.otherPath == null || shippingDoc.otherPath == '' || shippingDoc.otherPath == undefined)){
						    		 var li= '<li class="weui_list">'+
	                                    '<span>Other:</span><a style="text-decoration: underline;color:#08c;" onclick="downloadShipping('+shippingReports[i].id+',7)">'+shippingReports[i].otherPath+'</a>'+                   
									     '<p style="float:right;color: #bbb;">'+shippingReports[i].otherUploadDate+'</p>'+
	                                   '</li>';	
						    		 $('#shipping_doc_ul').find('ul').eq(i).append(li);   
						    	 }
			        
			    		}
			    		$('#shipping_doc_div').find('ul').each(function(){
			    			if($(this).find('li').length == 0){
			    			   $(this).prev().hide();
			    			}
			    		})
			    		
			    		$('#shipping_doc_div').show();
			    		}
			    	}else{
			    	  easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : '获取失败'
			    		  },
			    		  autoClose : 1000
			    		});
			    	}
			   })
  
  }

 //关闭po div
function close_po_div(){
	$('#po_div').hide();
}
 //关闭qc 报告div
function close_qc_div(){
	$('#qc_report_div').hide();
}
//关闭shipping div
function close_shipping_div(){
	$('#shipping_doc_div').hide();
}

//下载shipping doc
function downloadShipping(id,type){
	window.location = "/supplier/fileDownload_shipping.do?id="+id+"&type="+type;
}

</script>

</html>