<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ImportX</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="/supplier/css/bootstrap.min.css" />
<link rel="stylesheet" href="/supplier/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="/supplier/css/invoice.css" />
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
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/base64.js"></script>
<script type="text/javascript">


	//页面加载事件
	$(function() {
		
		 $('#sidebar').find('li').eq(0).addClass('active');
		
// 		$("#amountUnit").change(function(){
// 			var textVal = $(this).find("option:selected").text();
// 			$("#otherPrice_AmountUnit").text(textVal);
// 			$("#shippingFee_AmountUnit").text(textVal);
// 		});
// 		cal_shippingPrice();
// 		cal_otherPrice();
	
	});

	
	window.onload=function(){
		var currency = "${currency}";
		
		if(currency != null && currency !=""){			
			$("#amountUnit").val(currency);
		} else {
			$("#amountUnit").val("0");
		}
		showCurrency();	
		toSum();
	}
	
	//得到行对象  
	function getRowObj(obj) {
		var i = 0;
		while (obj.tagName.toLowerCase() != "tr") {
			obj = obj.parentNode;
			if (obj.tagName.toLowerCase() == "table")
				return null;
		}
		return obj;
	}
	//根据得到的行对象得到所在的行数  
	function getRowNo(obj) {
		var trObj = getRowObj(obj);
		var trArr = trObj.parentNode.children;
		for (var trNo = 0; trNo < trArr.length; trNo++) {
			if (trObj == trObj.parentNode.children[trNo]) {
				alert(trNo + 1);
			}
		}
	}
	//移除tr      
	function removeDrawing(obj) {
		var tr = this.getRowObj(obj);
		if (tr != null) {
			tr.parentNode.removeChild(tr);
			//计算总额
			toSum();
		} else {
			throw new Error("the given object is not contained by the table");
		}
	}
	//计算金额
	function toSum() {
		var s = 0;
		var tl = $("#drawingBody").find("tr").length;
		for (var i = 0; i < tl; i++) {
			var price = $("#drawingBody tr:eq(" + i + ")").children("td:eq(1)")
					.text();
			var quantity = $("#drawingBody tr:eq(" + i + ")").children(
					"td:eq(2)").text();
			var moldPrice = $("#drawingBody tr:eq(" + i + ")").children(
					"td:eq(3)").text();
			var sumPrice = 0;
			if (!(quantity == "") || isNaN(quantity)) {
				sumPrice = (parseInt(quantity) * parseFloat(price) + parseFloat(moldPrice)).toFixed(2);
			}
			$("#drawingBody tr:eq(" + i + ")").children("td:eq(4)").text(
					sumPrice);
			s += Number(sumPrice);
		}
		var exchangeRate = $('#exchangeRate').val();
		s = s*exchangeRate;
		$("#product_sum").html(Number(s).toFixed(2));

		//计算其他费用和运费价格
		//根据汇率计算价格（当前金额单元）	
		var shippingFee = $('#shipping_fee').val(); //获取总运费 oninput()
		var otherPrice = $('#other_price').val();   //获取其他费用
		var s1 = (Number(shippingFee) + Number(otherPrice)) * exchangeRate;
		$('#mold_sum').text(s1.toFixed(2));
		var total = Number(s) + Number(s1);
		$('#sum_price').text(total); //写入总金额	
	}

	function updateInvoice() {

		var exchangeRate = $('#exchangeRate').val();
		var invoiceProduct = '';
		var productPrice = 0;
		var moldPrice_sum = 0;
		var tl = $("#drawingBody").find("tr").length;
		for (var i = 0; i < tl; i++) {
			var productName = $("#drawingBody tr:eq(" + i + ")").children(
					"td:eq(0)").text();
			var price = $("#drawingBody tr:eq(" + i + ")").children("td:eq(1)")
					.text();
			var quantity = $("#drawingBody tr:eq(" + i + ")").children("td:eq(2)").text();
			var moldPrice = $("#drawingBody tr:eq(" + i + ")").children("td:eq(3)").text();
			var sumPrice = 0;
			if (!(quantity == "") || isNaN(quantity)) {
				sumPrice = (parseInt(quantity) * parseFloat(price)).toFixed(2);
			}		
			productPrice += Number(sumPrice);
			moldPrice_sum += Number(moldPrice);
			invoiceProduct += "-" + productName + "%" + "-" + price + "%" + "-" + quantity +  "%" + "-" + moldPrice + ",";
		}
		
        var otherPrice = $('#other_price').val();
        var shippingFee = $('#shipping_fee').val();

		var orderId = $('#orderId').text();
		var invoiceId = $('#invoice_id').text();
		var amountUnit = $("#amountUnit").find("option:selected").val();  //金额单元(1：人民币  2:美元 3:欧元 4:英镑)',
		var comment = $('#comment').val();
		var invoiceRemark = $('#invoice_remark').val();
		var paymentRemark = $('#payment_method').val();
        var remark_id = $('#select_payment').val();
        var totalAmount = $('#sum_price').text();
		
		
        $('#next_btn').css({"background-color":"#666"}).attr("disabled","true");
        
        $.ajax({
			url:"/supplier/updateInvoiceInfos.do",
			data:{
				"orderId" : orderId,
				"invoiceId" : invoiceId,
				"amountUnit" : amountUnit,
				"productPrice" : productPrice,
				"otherPrice" : otherPrice,
				"moldPriceSum"  : moldPrice_sum,
				"shippingFee" : shippingFee,
				"invoiceProduct" : invoiceProduct,
				"comment" : comment,
				"invoiceRemark" : invoiceRemark,
				"paymentRemark" : paymentRemark,
				"remarkId" : remark_id,
				"totalAmount" : totalAmount
				  },
			type:"post",
			dataType:"text",
			success:function(str){
				var result = eval('(' + str + ')');
				$('#next_btn').css({"background-color":"#006dcc"}).removeAttr("disabled");
				if(result.state == 0){
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : result.message
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
				  setTimeout(show_bank,1000,invoiceId,orderId);	
				}else{
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '保存失败'
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
				}
				},
		    error:function(){
		    	
		    	easyDialog.open({
		    		  container : {
		    			header :  'Prompt message',  
		    		    content : '保存失败'
		    		  },
		    		  autoClose : 1000
		    		});
		    	$('#next_btn').css({"background-color":"#006dcc"}).removeAttr("disabled");
		    }
		});
		
	}
	//保存成功后跳转
	function show_bank(invoiceId,orderId){
		var base = new Base64();
    	invoiceId=base.encode(invoiceId);
		window.location = "/supplier/queryInvoiceInfo.do?invoiceId="+invoiceId;			
	}
	
	
	function modifyMoldPrice(priceId) {
		$(priceId).attr("readonly", false);
		$(priceId).focus();
	}

	function moldPriceOnblur(priceId) {
		$(priceId).attr("readonly", true);
		//toSum();
	}

	function numberOnkeyup(value) {
		var nwVal = "";
		if (value.match(/^\d{10}$/)) {
			nwVal = value.replace(value, parseInt(value / 10));
			nwVal = value.replace(/\.\d*\./g, '."');
			return nwVal;
		} else {
			return nwVal;
		}
	}

	function numberOnKeyPress(event, value) {
		if ((event.keyCode<48|| event.keyCode>57) && event.keyCode != 46
				&& event.keyCode != 45 || value.match(/^\d{10}$/)
				|| /\.\d{10}$/.test(value)) {
			event.returnValue = false;
			return;
		}
		if (value.indexOf('.') != -1 && event.keyCode == 46) {
			event.returnValue = false;
			return;
		}
		if (value.indexOf('.') != -1) {
			var numberlst = value.split(".");
			if (numberlst[1].length > 1) {
				event.returnValue = false;
				return;
			}
		}
	}
	
	
	function modifyProduct(index,productName,unitPrice,quantity){
		$('#product_index').val(index);
		$('#product_name').val(productName);
		$('#unit_price').val(unitPrice);
		$('#quantity').val(quantity);
		$('#dialog').show();
	}
	
	function disclose(){
		$('#dialog').hide();
	}
	
	function updateProduct(){
		//取dialog的值
		var index = $('#product_index').val();
		var productName = $('#product_name').val();
		var unit_price = $('#unit_price').val();
		var quantity = $('#quantity').val();
		var total = (parseFloat(unit_price) * parseFloat(quantity)).toFixed(2);
		
		//给product所在index的td赋值
		$("#drawingBody tr:eq(" + index + ")").children("td:eq(0)").text(productName);
		$("#drawingBody tr:eq(" + index + ")").children("td:eq(1)").text(unit_price);
		$("#drawingBody tr:eq(" + index + ")").children("td:eq(2)").text(quantity);
		$("#drawingBody tr:eq(" + index + ")").children("td:eq(3)").text(total);
		toSum();				
		$('#dialog').hide();
	}
	
	

	function showCurrency(){
		var textVal = $("#amountUnit").find("option:selected").text();
// 		$("#moldCurrency").text(textVal);
// 		$("#drawingCurrency").text(textVal);
		$("#product_amountUnit").text(textVal);
		$("#mold_amountUnit").text(textVal);
		$("#sum_amountUnit").text(textVal);
		
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryExchangeRate.do",
			data : {
				"amountUnit" : $("#amountUnit").find("option:selected").val()
			},
			success : function(result) {
               var exchangeRate = result.data; 
			   $('#exchangeRate').val(exchangeRate);			   			   
			   toSum();
			},
			error : function() {

 				easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '汇率转换失败'
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
			}
		});
	}
	
	
	 //==========选择付款remark信息=============
	function select_remark(){  
	   
		$.ajax({
			type : "post",
			datatype : "json",
			url : "/supplier/queryInvoiceRemark.do",
			data : {
				"id" :  $('#select_payment').val()
			},
			success : function(result) {				
			  if(result.state == 0){
	              $('#invoice_remark').val(result.data.remark);  
			  }	else{
				  easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '未获取备注信息'
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					}); 
			  }
  
			},
			error : function() {
				easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '未获取备注信息'
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
			}
		});
    }
	
	 
	function show_invoice(orderId){
	 	var base = new Base64();
	 	orderId=base.encode(orderId);
	}
</script>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
	<div id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllClientOrder.do" class="tip-bottom"><i
					class="icon  icon-paste"></i>客户订单管理</a> <a onclick="show_invoice('${orderId}')" class="current">发票</a> <a href="#" class="current">发票上传</a>
			</div>
			<div class="shu">
				<div class="bianhaolan">
					<p class="bianhao">
						订单编号 : <span id="orderId">${orderId}</span>
					</p>
					<div class="fapiao">
						<label>发票编号 :<span id="invoice_id">${invoiceId}</span>
					</div>
					<div class="jine">
						<label>金额单元 :</label> <select class="xiala_select" name="select"
							id="amountUnit" onchange="showCurrency()">
							<c:forEach var="amountUnit" items="${amountUnit}" varStatus="i">
								<option value="${i.index}">${amountUnit.currency}</option>
							</c:forEach>
						</select>

					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<hr style="clear: both;">
			<div class="div-left">
				<h4 style="float: left;">
					产品价格 <span>（合计:<span id="product_sum"></span><span id="product_amountUnit">美元</span>）
					</span>
				</h4>
			</div>
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
													产品名称 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													单价 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													数量 <span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													模具<span
														class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
												</div>
											</th>
											<th class="ui-state-default" role="columnheader" tabindex="0"
												aria-controls="DataTables_Table_0" rowspan="1" colspan="1"
												aria-label="Engine version: activate to sort column ascending">
												<div class="DataTables_sort_wrapper">
													总价（<span id="drawingCurrency">美元</span>） <span
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
										id="drawingBody">
										<c:forEach var="obj" items="${invoiceProducts}" varStatus="i">
											<tr class="gradeA odd" id="tr${i.index}">
												<td>${obj.productName}</td>
												<td>${obj.unitPrice}</td>
												<td>${obj.quantity}</td>
												<td>${obj.moldPrice}</td>
												<td>0</td>
												<td class="center">
										            <button class="btn btn-primary"
									            	 onclick="modifyProduct(${i.index},'${obj.productName}',${obj.unitPrice},${obj.quantity})">编辑</button>
													<button class="btn btn-danger" style="margin-left: 10px;"
														onclick="removeDrawing(this);">删除</button>
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

		<div class="container-fluid">
			<hr style="clear: both;">
			<div class="row-fluid">
				<div class="span12">
					<h1 style="font-size: 17.5px; margin: 0;">
						其他金额 （合计:<span id="mold_sum">700</span><span id="mold_amountUnit">美元</span>）
					</h1>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">总运费: </label>
				<div class="controls">
					<input type="text" name="number" id="shipping_fee" value="${invoiceInfo.shippingFee}" 
						oninput="toSum();" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/><span>
						* 输入该产品当初与客户商议的运输费用，如无默认为0（<span id="shippingFee_AmountUnit">美元</span>）</span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">其他费用: </label>
				<div class="controls">
					<input type="text" name="otherPrice" id="other_price" value="${invoiceInfo.otherPrice}"  
						oninput="toSum();" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/><span>
						* 输入其他附加费用，如无默认为0（<span id="otherPrice_AmountUnit">美元</span>）</span>
				</div>
				<label class="control-label">其他费用备注: </label>
				<div class="controls">
					<textarea name="a" style="width:400px;height:80px;" id="comment">${invoiceInfo.comment}</textarea> <span>
						* 输入其他附加费用说明</span>
				</div>
				<div class="control-group">
					<label class="control-label">付款方式备注: </label>
					<div class="controls">
						<textarea name="a" style="width:800px;height:100px;" id="payment_method">${remark}</textarea><span>
							* 付款方式备注</span>
					</div>
			
				</div>
			
				<div class="control-group">
					<label class="control-label">银行付款信息备注: </label>
					<select class="xiala_select" id="select_payment" onchange="select_remark()" style="margin-left: 10px;margin-top: 10px;">
					        <c:forEach var="obj" items="${invoiceRemarks}" varStatus="i">
					        <option value="${obj.id}" <c:if test="${obj.id eq invoiceRemark.id}"> selected="selected"</c:if>>${obj.remarkBank}</option>
					        </c:forEach>
				    </select>
					<div class="controls">				    
						<textarea name="a" style="width:800px;height:300px;" id="invoice_remark">${invoiceRemark.remark}</textarea> <span>
							* 银行付款信息备注</span>
					</div>
			
				    </div>
			</div>

			<div class="pull-right">
				<h4 style="float: right;">
					<span>总计:</span><span id="sum_price">0</span>（<span id="sum_amountUnit">美元</span>）
					 <input name="exchangeRate" id="exchangeRate" value="1.0" type="hidden">
				</h4>
				<br> <a class="btn btn-primary btn-large pull-right" 
					style="margin-top: 15px; float: left;" id="next_btn" onclick="updateInvoice()">下一步</a>
			</div>

		</div>
	</div>


	<div class="weui_dialog_alert" id="dialog" style="display: none;">
		<div class="weui_mask"></div>
		<div class="weui_dialog">
			<div class="waimian">
				<div class="control-group">
					<label class="control-label div-label2">产品名称: </label>
					<div class="controls div-con">
						<input type="text" name="productName" id="product_name">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label div-label2">单&nbsp;&nbsp;价: </label>
					<div class="controls div-con">
						<input type="text" name="unitPrice" id="unit_price" value=""
							onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label div-label2">数&nbsp;&nbsp;量: </label>
					<div class="controls div-con">
						<input type="text" name="quantity" id="quantity"
							onkeyup="this.value=this.value.replace(/\D/g,'')"
							onafterpaste="this.value=this.value.replace(/\D/g,'')">
					</div>
				</div>
				<div class="control-group">
					<label class="control-label div-label2">模&nbsp;&nbsp;具: </label>
					<div class="controls div-con">
						<input type="text" name="mold" id="mold"
							onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
					</div>
				</div>
			</div>
			<a class="close-reveal-modal" href="javascript:void(0);"
				onclick="disclose()">×</a>
			<!--<a class="close-reveal-modal">×</a>-->
			<div class="weui_dialog_ft">
				<a href="javascript:;" class="weui_btn_dialog primary"
					onclick="updateProduct()">修改</a> <input name="product_index"
					id="product_index" value="" type="hidden">
			</div>
		</div>
	</div>


	<div class="row-fluid">
		<div id="footer" class="span12">
		
		</div>
	</div>

</body>
</html>