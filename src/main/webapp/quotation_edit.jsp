<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.format.datetime.DateFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.cbt.util.WebCookie"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cbt.util.DateFormat"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/uniform.css" />
<link rel="stylesheet" href="css/quotation.css" />
<link rel="stylesheet" href="css/newOrder2.css" />
<link rel="stylesheet" href="css/easydialog.css" />
<link rel="stylesheet" href="css/ui.css">
<link rel="stylesheet" href="css/ui.progress-bar.css">
<link rel="stylesheet" href="css/upload-base.css">
<link rel="stylesheet" href="css/css.css">
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />


<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="js/number.js"></script>
<script type="text/javascript"
	src="/supplier/js/My97DatePicker/WdatePicker.js"></script>

<script src="/supplier/js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script type="text/javascript">

<%-- 	var userName = '<%=userinfo!=null?userinfo[1]:""%>'; --%>
	
 	$(function(){
 				
 		
//  		 $('#sidebar').find('li').eq(2).addClass('active');
 		 
 		 var pic = $('#select_temp_pic').html();
 		 if( Trim(pic) == null || Trim(pic) == ''){
 			$('#select_temp_pic').hide();
 		 }
 		
 		 //checkbox选择事件
 		  $("#remark_table").on('click','input',function(){
			  
 	          if(  $(this).is(':checked') ){
  	             $(this).parent().css('background-position','-76px -260px'); 
  	             $(this).attr("checked",true);
 	          }else{
 	            $(this).parent().css('background-position','0px -260px');
 	            $(this).attr("checked",false);
 	            $('.checker:first').find('input').parent().css('background-position','0px -260px');
 	            $('.checker:first').find('input').attr("checked",false);
 	          }
 	      })
 	      
 	      
 	     //checkbox全选事件 
 	      $(".checker:first").on('click','input',function(){			  
 	          if( $(this).is(':checked') ){
  	             $('.checker').find('span').css('background-position','-76px -260px'); 
  	             $('.checker').find('input').attr("checked",true);
 	          }else{
 	        	 $('.checker').find('span').css('background-position','0px -260px');
 	        	 $('.checker').find('input').attr("checked",false);
 	          }
 	      })
 	      
 	   });
 	
 	
 	
 	//去除空格
    function Trim(str)
    {    
 		if(str){
 			return str.replace(/(^\s*)|(\s*$)/g, ""); 	
 		}else{
 			return "";
 		}
        
    }
 	
 	
  //下载留言消息附件
	function download_attachment(id){
	  
		$.ajax({
			url:"/supplier/fileDownload_quotation_attachment.do",
			data:{
				  "id":id
				  },
			type:"post",
			dataType:"text",
			success:function(res){			
				window.location.href = "/supplier/fileDownload_quotation_attachment.do?id="+id;				
				},
		    error:function(){
		    	
		    	easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : 'Download failed'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
		    }
		});
	}
 	
	/*
	 *发送消息
	 */	
	function send_message(quotationId,targetPriceReply,factoryId,userId){
		
		var messageDetails = $('#message_input').val();
		
		if($.trim(messageDetails) == '' || $.trim(messageDetails) == null){
			easyDialog.open({
	    		  container : {
	    		    header : 'Prompt message',
	    		    content : 'You have not entered a message yet'
	    		  },
	    		  overlay : false,
	    		  autoClose : 1000
	    		});
			return false;
		}
		
		$.post("/supplier/saveQuotationMessage.do",
              { 
			  "quotationId": quotationId, 
			  "messageDetails": messageDetails,
			  "targetPriceReply":targetPriceReply,
			  "factoryId" : factoryId,
			  "userId" : userId
			  },
			function(result){
				  
			  if(result.state == 0){
				  
				  var message = result.data;
				  var li = '<li>'+
                            '<div class="article-post">'+
			                  '<div class="fr">'+
			                      '<span class="w-user-info">'+message.messageSendTime+'</span>'+
			                  '</div>'+
			                  '<span class="user-info">Reply:</span>'+
			                  '<p>'+message.messageDetails+
			                  '</p>'+
			               '</div>'+
                           '</li>';
                   $('.recent-posts').prepend(li);
                   $('#message_input').val('');
                           
			  }else{
				  easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : 'send failed'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000
		    		});
			  }
				    
			});
	}
  
  
	//改变金额单元
	function select_currency(obj,quotationInfoId){
	   	
		var currency = $(obj).find("option:selected").text(); 		
		$.post("/supplier/selectCurrencyEdit.do",
	              { 
				  "currency": currency,
				  "quotationInfoId" : quotationInfoId
				  },
				function(result){				  
				  if(result.state == 0){
						var exchangeRate = result.data;  
						var currentRate = $('#current_exchangeRate').val();
						$('#product_thead').find('span').text("("+currency+")");  
						
						$('#product_tbody').find('tr').each(function(i){
							var moldPrice = $(this).find('td').eq(6).find('span').text();
							moldPrice = (parseFloat(moldPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
							$(this).find('td').eq(6).find('span').text(moldPrice.toFixed(0));
							
							var moldFactoryPrice = $(this).find('td').eq(6).find('label').text();
							moldFactoryPrice = (parseFloat(moldFactoryPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
							$(this).find('td').eq(6).find('label').text(moldFactoryPrice.toFixed(0));
							
							var factoryPrice = $(this).find('td').eq(8).find('label').text();
							factoryPrice = (parseFloat(factoryPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
							$(this).find('td').eq(8).find('label').text(factoryPrice.toFixed(4));
							
							$(this).find('td').eq(8).find('span').each(function(j){
								var unitPrice = $(this).text();
								unitPrice = (parseFloat(unitPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
								$(this).text(unitPrice.toFixed(3));
							})
				    })
				    $('#current_exchangeRate').val(exchangeRate);
					if(currency == 'USD'){						
						exchangeRate = $('#cny_rate').val();
						$('#show_currency').text("当前汇率："+exchangeRate+"(CNY/USD)");
					}else{
						$('#show_currency').text("当前汇率："+exchangeRate+"("+currency+"/USD)");
					}				
				}
			 })		     		  
	}
	
	
	
	
	
</script>
<style>
.w-span-tu .span3{
	margin-left:0;
	margin-right:7px;
}
.w-span-tu .span3>div{
	width:75px;
	height:75px;
}
.container-fluid{
	padding-left:15px;
	padding-right:15px;
}
.w-span-tu .last_span{
	margin-right:0;
}
.z-add-pic{
	margin-right: 0;
    margin-left: 6px;
    margin-top: 10px;
}
.ml0{
	margin-left:0;
}
.btn_b{
    padding: 4px 12px;
	border:1px solid #ddd;
	background-color:#5bb75b;
	font-size:14px;
	color:#fff;
	margin-left:10px;
}
.f14{
	font-size:14px;
}
.f_r{
	float:right;
}
.t_j{
	padding:4px 12px;
	background-color:#006dcc;
	font-size: 14px;
	color:#fff;
}
.t_j:hover{
	background-color: #04c;
}
hr{
	margin-top: 10px;
    margin-bottom: -5px;
}
.w-hr hr{
    margin-top: -10px;
    margin-bottom: -30px;
}
.dow_ok{
	margin-top:50px;
}
.dow_ok a{	
	border:1px solid #ddd;
	color:#666;
	font-size:14px;
	float:left;
	width: 246px;
	height: 80px;
	line-height:80px;
	text-align: center;
	background-color: #fff;
}
.dow_ok #span_file{
	width: 220px;
	height: 72px;
	line-height:80px;
	text-align: center;
	background-color: #fff;
}
.dow_ok #span_file:hover{
	color:#666;
}
.dow_ok button{
	width: 200px;
	height: 80px;
	line-height:80px;
	text-align: center;
	color:#666;
	font-size:14px;
	background-color: #fff;
}
.dow_ok .dir{
	width:66px;
	height:1px;	
	padding:0;
	border-color:transparent;
	background-color: transparent;
}
.dow_ok .dir1{
	width:390px;
}
.control-group{
	margin-bottom:-20px;
}
.span4 .w-control60 .controls_mt{
	padding-top:10px;
}
.out-box{
	margin-bottom:0;
}
.mt_20{
	margin-top:-50px;
}
.del-z button{
	padding:4px 10px;
	background-color:#fff;
	color:#666;
	border:1px solid #ddd;
}
.w-textarea-span11{
	display:none;
}
.dow_sm{
	margin-top:20px;
	margin-bottom:-30px;
}
.da4{
	color:#da4f49;
}
.overflow_a{
	overflow:auto;
}
.f_right{
	float:right;
}
.w-font{
	position:relative;
}
.step_4_div{
	position:absolute;
	top: 8px;
    left: 200px;
	color: #da4f49;
    padding-right: 90px;
	
}
.step_4_div span,.step_4_div a{
 	font-size:15px;
 	color: #da4f49;
}
.step_4_div a:hover{
 	text-decoration:underline;
}
.add_span{
	color: #da4f49;
    font-size: 15px;
    position:absolute;
    bottom:-25px;
    left:50px;
    display: none;
}
</style>
</head>
<body>
	<jsp:include page="base.jsp"></jsp:include>

	<input type="hidden" value="${quotationBean.imgNames}" id="img_names">
	<input type="hidden" id="customer_id" value="${quotationBean.userId}">
	<input type="hidden" id="current_exchangeRate" value="${exchangeRate}">
	<input type="hidden" id="cny_rate" value="${quotationBean.exchangeRateCNY}">
	<div class="offer" id="content">
		<div id="content-header">
			<div id="breadcrumb">
				<a href="/supplier/queryAllQuotation.do" class="tip-bottom"><i
					class="icon  icon-paste"></i>报价单</a> <a href="#" class="current">编辑报价</a>
			</div>
			<div class="shu">
				<div class="w-div1">
					<p class="w-p1">Step1:确认基本信息</p>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<hr style="clear: both;">

			<div class="row-fluid">
				<div class="span8" style="margin-left: 0;">
					<label class="control-label">Project Id: </label>
					<div id="quotation_id_div" class="controls2">
						${quotationBean.projectId}</div>
				</div>
				<div class="span12" style="margin-left: 0;">
					<div class="span8 w-span8" style="margin-left: 0;">
						<div class="span6">
							<div class="control-group">
								<label class="control-label">Subject : </label>
								<div class="controls">
									<input type="text" id="project_subject"
										value="${quotationBean.quotationSubject == null ? '' : quotationBean.quotationSubject}">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Currency : </label>
								<div class="controls">
									<select id="currency"
										onchange="select_currency(this,'${quotationBean.id}')">
										<c:forEach var="obj" items="${amountUnitList}" varStatus="i">
											<option
												<c:if test="${obj.currencyShorthand eq quotationBean.currency}"> selected="selected"</c:if>>${obj.currencyShorthand}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Quote Date : </label>
								<div class="controls">
									<input type="text"
										onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
										id="quotation_date" value="${quotationBean.quotationDate}">
								</div>
							</div>
						</div>


						<div class="span6">
							<!--                     <div class="control-group w-control-group"> -->
							<script language="javascript">
// 							function changeF1() {
// 							   $('#customer_input').val($('#makeupCoSe1').find("option:selected").text());
// 							   $('#customer_id').val($('#makeupCoSe1').val())
// 							   $('#makeupCoSe1').val('');
// 							}
						</script>

							<!--                         <label class="control-label" style="height:40px;">Client Name : </label> -->
							<!--                         <div class="controls"> -->
							<!-- 			             <span class="w-controls-span"> -->
							<!-- 							<select name="makeupCoSe1" id="makeupCoSe1" class="w-controls-select" onchange="changeF1();"> -->
							<!-- 							<OPTION id="99999" VALUE="" SELECTED> -->
							<%--                             <c:forEach var="obj" items="${userInfo}" varStatus="i"> --%>
							<%--                             <option value="${obj.userid}">${obj.userName}</option> --%>
							<%--                             </c:forEach> --%>
							<!-- 							</select> -->
							<!-- 						 </span> -->
							<!-- 		                <span class="w-controls-span2"> -->
							<%-- 							<input type="text" id="customer_input" class="w-controls-input" value="${quotationBean.customerName}" placeholder="请选择或输入" ondblclick="clear_input();">														 --%>
							<!-- 						</span> -->
							<!--                         </div> -->

							<!--                     </div> -->
							<div class="control-group">
								<label class="control-label">Inco Term : </label>
								<div class="controls">
									<input type="text"
										value="${quotationBean.incoTerm == null ? 'FOB Shanghai' : quotationBean.incoTerm}"
										id="inco_term">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">Valid: </label>
								<div class="controls">
									<input id="quotation_validity" type="text"
										value="${quotationBean.quotationValidity}">（天）
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">重量单位: </label>
								<div class="controls">
									<select id="weight_unit" onchange="select_unit(this)">
										<option value="kg" <c:if test="${productionPrices.get(0).get(0).weightUnit == 'kg'}"> selected="selected"</c:if>>kg</option>
										<option value="g" <c:if test="${productionPrices.get(0).get(0).weightUnit == 'g'}"> selected="selected"</c:if>>g</option>
									</select>
								</div>
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="control-group w-control60">
							<label class="control-label">Contact : </label>
							<div class="controls">
								<%--                         <input id="quoter" type="text" disabled="disabled" value="${quotationBean.quoter}"> --%>
								<span id="quoter">${quotationBean.saleName}</span>
							</div>
						</div>
						<div class="control-group w-control60">
							<label class="control-label">Email : </label>
							<div class="controls">
								<%--                         <input type="text" name="email" id="email" value="${quotationBean.quoterEmail}"> --%>
								<span id="email">${quotationBean.quoterEmail}</span>
							</div>
						</div>
						<div class="control-group w-control60">
							<label class="control-label">TEL : </label>
							<div class="controls controls_mt">
								<input type="text" name="number" id="tel"
									value="${quotationBean.quoterTel}">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="out-box">
			<div class="container-fluid">
				<div class="w-font">
					<h2 style="font-size: 16px; font-weight: 700;margin-top:30px;">Step2:确认产品信息</h2>
					<h3 style="font-size: 14px; font-weight: 600;color: #da4f49;float: left;margin-top:-5px;">
						<span style="font-size: 14px; font-weight: 600;color: #da4f49;" id="show_currency">
						当前汇率：<c:if test="${quotationBean.currency == 'USD'}">${quotationBean.exchangeRateCNY}(CNY/USD)</c:if>
						<c:if test="${quotationBean.currency != 'USD'}">${exchangeRate}(${quotationBean.currency}/USD)</c:if>
						</span>
						<span style="font-size: 14px; font-weight: 600;color: #da4f49;margin-left: 28px;">
						${quotationBean.exchangeUpdateDate}
						</span>
					</h3>
				</div>
				<div class="w-hr">
					<hr>
				</div>
				<div class="w-button">
					<a href="#" class="cancel"> <!--                     <button class="btn btn-info" onclick="addProduct();">添加产品</button> -->
					</a>
				</div>
				<div class="widget-box overflow_a">
					<div class="widget-content nopadding">
						<table class="table table-bordered table-striped">
							<thead id="product_thead">
								<tr>
									<th>编号</th>
									<th>产品名</th>
									<th>图片</th>
									<th>材质</th>
									<th><input id="weight_status" type="checkbox"
										<c:if test="${quotationBean.weightStatus == 1}">checked</c:if>
										style="float: left;">单位重量(<em>${productionPrices.get(0).get(0).weightUnit}</em>)</th>
									<th><input id="process_status" type="checkbox"
										<c:if test="${quotationBean.processStatus == 1}">checked</c:if>
										style="float: left;">工艺<em style="color:#da4f49;">(请手动改成英文)</em></th>
									<th>模具费<span>(${quotationBean.currency})</span></th>
<!-- 									<th>模具利润率<em>(%)</em></th> -->
									<th>最小订量</th>
									<th>单价<span>(${quotationBean.currency})</span></th>
									<th>总利润率<em>(%)</em></th>
									<th>注释</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody id="product_tbody">
								<c:forEach var="obj" items="${quotationProductionBeans}"
									varStatus="i">
									<tr class="odd gradeX">
										<td>${i.index+1}<input type="hidden" value="${obj.id}"></td>
										<td><input style="width: 78px;" type="text"
											value="${obj.productName}" /></td>
										<td><img src="/static_img/quotation/img/${obj.productImgCompress}"></td>
										<td><input style="width: 90px;" type="text"
											value="${obj.material}" /></td>
										<td><c:if
												test="${productionPrices.get(i.count-1).size() != 0}">
<!-- 												<div> -->
<%-- 												<input name="weight${i.index+1}" class="weight-radio" type="radio" value="kg" checked>kg --%>
<%-- 												<input name="weight${i.index+1}" class="weight-radio" type="radio" value="g">g --%>
<!-- 												</div> -->
<!-- 												<br> -->
												<input style="width: 78px;" class="weight" type="text"
													value="<fmt:formatNumber type="number" value="${productionPrices.get(i.count-1).get(0).materialWeight}" maxFractionDigits="5"/>" />
											</c:if></td>
										<td><c:forEach begin="1"
												end="${processInfos.get(i.count-1).size()}" varStatus="k"
												step="1">
												<span>${k.index}、</span>
												<input type="hidden"
													value="${processInfos.get(i.count-1).get(k.count-1).id}">
												<input style="width: 140px;" type="text"
													value="${processInfos.get(i.count-1).get(k.count-1).processName}" />
												<em onclick="deleteProcess(${processInfos.get(i.count-1).get(k.count-1).id},this)">X</em>
												<br>
											</c:forEach></td>
										<td>
										模具工厂价:<label style="display:inline-block;"><fmt:formatNumber value="${obj.moldFactoryPrice}" type="number" groupingUsed="false" maxFractionDigits="0"/></label><br>
										模具利润率(%):<input type="number" style="width: 46px;" oninput="calculateMoldPrice(this,${obj.id})" onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<fmt:formatNumber type="number" groupingUsed="false" value="${obj.moldProfitRate*100}" maxFractionDigits="0"/>" /><br>
										<span>${obj.moldPrice}</span>
										</td>
<!-- 										<td><input type="number" style="width: 58px;" -->
<%-- 											oninput="calculateMoldPrice(this,${obj.id})" --%>
<!-- 											onkeyup="this.value=this.value.replace(/\D/g,'')" -->
<!-- 											onafterpaste="this.value=this.value.replace(/\D/g,'')" -->
<%-- 											value="<fmt:formatNumber type="number" value="${obj.moldProfitRate*100}" maxFractionDigits="0"/>" /></td> --%>
										<td><c:forEach begin="1"
												end="${productionPrices.get(i.count-1).size()}"
												varStatus="k" step="1">
												<input type="text" style="width: 78px;" value="${productionPrices.get(i.count-1).get(k.count-1).quantity}" oninput="calculateTotalProfit(this)"/>
												<br>
											</c:forEach></td>
										<td>
										产品工厂价:<label style="display:inline-block;">${obj.factoryPrice}</label><br>
								                    产品利润率(%):<input type="number" style="width: 46px;" oninput="calUnitPrice(this,${obj.id})" onkeyup="this.value=this.value.replace(/\D/g,'')"
									         onafterpaste="this.value=this.value.replace(/\D/g,'')" value="<fmt:formatNumber type="number" groupingUsed="false" value="${obj.productProfitRate*100}" maxFractionDigits="0"/>" /><br>
										    <c:forEach begin="1"
												end="${productionPrices.get(i.count-1).size()}"
												varStatus="j" step="1">
												<input type="hidden" value="${productionPrices.get(i.count-1).get(j.count-1).id}">
												<span>${productionPrices.get(i.count-1).get(j.count-1).productPrice}</span>
												<br>
											</c:forEach>
										</td>
										<td><c:forEach begin="1"
												end="${productionPrices.get(i.count-1).size()}"
												varStatus="j" step="1">
												<span><fmt:formatNumber type="number" value="${productionPrices.get(i.count-1).get(j.count-1).totalProfitRate*100}" maxFractionDigits="0"/></span>
												<br>
											</c:forEach></td>
										<td><textarea>${obj.productNotes}</textarea></td>
										<td class="center"><a href="#"><button
													class="btn btn-danger" style="margin-left: 10px;"
													onclick="delete_product(this,'${obj.id}','${quotationBean.id}')">删除</button></a>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="out-box offer_right">
			<div class="container-fluid info">
				<div class="w-font">
					<h2 style="font-size: 16px; font-weight: 700;"> Step3:选择备注信息</h2>
				</div>
				<div class="w-hr">
					<hr>
				</div>
				
				
			  <%-- <div class="row-fluid">
					<div class="span12">
						<textarea class="span11 w-textarea-span11" id="select_temp_remark">${quotationBean.remark}</textarea>
					</div>
				</div>

				<div class="row-fluid">
					<div class="span12">
						<p class="span11 w-textarea-span11 w-p-span11"
							id="select_temp_pic">${quotationBean.remarkImg}</p>
					</div>
				</div> --%>
				
				
				
                 <form id="downloadPdf" onsubmit="return false">
				<div class="w-button info_title clearfix">
					<span style="cursor: default;margin-left: 10px;float: left;color: #333;font-size:14px;margin-top:6px;">选择报价单模板:</span>					
					
					<div class="selects">
						<span class="span_1"></span>		
						<input id="chooseProcess" style="background-color: white;cursor:pointer;"  
						value="<c:if test="${quotationBean.pdfType == null}">其他</c:if><c:if test="${quotationBean.pdfType == 0}">其他</c:if><c:if test="${quotationBean.pdfType == 1}">滚塑</c:if><c:if test="${quotationBean.pdfType == 2}">吸塑</c:if><c:if test="${quotationBean.pdfType == 3}">钣金冲压</c:if><c:if test="${quotationBean.pdfType == 4}">铝挤压</c:if><c:if test="${quotationBean.pdfType == 5}">吸塑锻造</c:if><c:if test="${quotationBean.pdfType == 6}">激光切割</c:if><c:if test="${quotationBean.pdfType == 7}">机加工</c:if><c:if test="${quotationBean.pdfType == 8}">注塑</c:if><c:if test="${quotationBean.pdfType == 9}">吹塑</c:if><c:if test="${quotationBean.pdfType == 10}">挤塑</c:if><c:if test="${quotationBean.pdfType == 11}">熔模铸造</c:if><c:if test="${quotationBean.pdfType == 12}">砂铸</c:if><c:if test="${quotationBean.pdfType == 13}">压铸</c:if>"
						readonly="readonly" />		
						<span></span>
						<input id="processCode" name="processCode" type="hidden" value="${quotationBean.pdfType == null ? '0' : quotationBean.pdfType}">
						<div class="xl clearfix" style="display:none">
							<ul class="xl_l" style="margin: 0 0 10px 0;">
								<li><span>塑料</span><em></em>
									<ul class="xl_r">
										<li><span datacode="8">注塑</span></li>
										<li><span datacode="1">滚塑</span></li>
										<li><span datacode="2">吸塑</span></li>
										<li><span datacode="9">吹塑</span></li>
										<li><span datacode="10">挤塑</span></li>
									</ul></li>
								<li><span>铸造</span><em></em>
									<ul class="xl_r">
										<li><span  datacode="13">压铸</span></li>
										<li><span datacode="11">熔模铸造</span></li>
										<li><span datacode="12">砂铸</span></li>
									</ul></li>
                                <li><span datacode="5">锻造</span></li>
								<li><span datacode="3">钣金冲压</span></li>
								<li><span datacode="6">激光切割</span></li>
								<li><span datacode="7">机加工</span></li>
								<li><span datacode="4">铝挤压</span></li>
								<li><span datacode="0">其他</span></li>

							</ul>
						</div>
					</div>
					<!-- <span class="btn btn-success" style="margin-left: 10px;float: left;" onclick="select_temp_remark()">自定义文字、图片模板</span> -->
					<span style="margin-left: 50px;color: #da4f49;font-size: 15px;">(注意：你选择的模板会影响后续生成文件)</span>
				</div>
               
				<div class="info_details">				
					<div id="injextate"  <c:if test="${quotationBean.pdfType != 8}">style="display:none"</c:if> class="detail_0 detail_1">
						<h5>注塑参数：</h5>
						<div class="parameter clearfix">
							<span>Mold Life</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldLife" value="0">1
									Million</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldLife" value="1">500K</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldLife" value="2">300K</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="moldLife" value="3">200K</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Mold Core Steel</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio"  name="moldCoreSteel" value="0">NAK80</label>
								</div> 
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="moldCoreSteel" value="1">P20</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldCoreSteel" value="2">718</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldCoreSteel"  value="3">Others</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Require sliding fixture (Complex shape)?</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio"  name="requireFixture" value="0">Yes</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="requireFixture" value="1">No</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Mirror Finish?</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio"  name="mirrorFinish" value="0">Yes</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="mirrorFinish" value="1">No</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Combine different parts in one mold</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" name="combine" value="0">Yes</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="combine" value="1">No</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="combine" value="2">Partially</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Hot Runner</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" name="hotRunner" value="0">Yes</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="hotRunner" value="1">No</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Gating Type</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="gatingType" value="0">Edge gate</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio"  name="gatingType" value="1">Pin-point gate</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Material Additives</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio"  name="addMaterial" value="0">Food Safe</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="addMaterial" value="1">Flame
										Retard</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="addMaterial" value="2">UV
										Inhibitor</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="addMaterial" value="3">Others</label>
								</div>
							</div>
						</div>
					</div>
					<div id="press" <c:if test="${quotationBean.pdfType != 13}">style="display:none"</c:if>  class="detail_0">
						<h5>压铸参数：</h5>
						<div class="parameter clearfix">
							<span>Product Material</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="productMaterial" value="0">Aluminum</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="productMaterial" value="1">Zinc</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="productMaterial" value="2">Copper</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Mold Core Steel</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="pressMoldCoreSteel" value="0">H13/SKD61</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="pressMoldCoreSteel" value="1">8407</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="pressMoldCoreSteel" value="2">Other</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Mold Life</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="pressMoldLife" value="0">30K
									(30000)</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="pressMoldLife" value="1">100K</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="pressMoldLife" value="2">Other</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Require Mold Inserts/ Slider?</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="requireMold" value="0">Yes</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="requireMold" value="1">No</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Molding Machine</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="moldingMachine" value="0">Hot
									Chamber</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="moldingMachine" value="1">Cold
										Chamber</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Surface Treatment</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="surfaceTreatment" value="0">Shot
									Blast</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="surfaceTreatment" value="1">Powder
										Coat</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="surfaceTreatment"  value="2">Anodize</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="surfaceTreatment"  value="3">Other</label>
								</div>
							</div>
						</div>
						<div class="parameter clearfix">
							<span>Machining Requirement</span>
							<div class="options">
								<div class="radio-inline">
									<label for=""><input type="radio" checked name="machiningRequirement" value="0">As
									cast</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="machiningRequirement" value="1">Rough
										Machining</label>
								</div>
								<div class="radio-inline">
									<label for=""><input type="radio" name="machiningRequirement" value="2">Precison
									Machining</label>
								</div>
							</div>
						</div>
					</div>
				</div>
                </form>
				
				<div class="row-fluid mt_20">
					<div class="span12 f14" style="margin-top:58px;">附加操作1:<button class="btn_b" onclick="change_textarea();">更换默认模板文字信息</button></div>
					<div class="span12" style="margin-left:0; margin-top:15px">
						<textarea class="span11 w-textarea-span11" id="select_temp_remark">${(quotationBean.remark == null || quotationBean.remark ==  '') ? '1. Mold Production Lead Time: 30days. (15days with expeditation fee paid.) 
2. Price Term: FOB Shanghai (price without shipping cost).	
3. The price is calculated by today’s currency exchange rate (1USD=6.3RMB).If exchange rate	changes in the future, the price will be adjusted accordingly.For example, if exchange rate changes to (1USD = 6.0RMB), new price = old price * (6.3/6.0).If you feel our quote is mistaken, please immediately send feedbacks to our General Manager contact@csmmg.com. We value your business, and strive to provide not only the best price in the market but also excellent quality & service.' : quotationBean.remark}</textarea>
<!-- 						<button class="f_r t_j">提交</button> -->
					</div>
				</div>

				<div class="row-fluid mt_20">
					<div class="span8 f14">附加操作2:<span class="btn btn-success" style="margin-left: 10px;" onclick="select_temp_remark()">尾部添加图片</span></div>
					<div class="span12" style="margin-left:0;margin-top:15px">
						<p class="span11 w-p-span11" id="select_temp_pic">${quotationBean.remarkImg}</p>
					</div>
				</div> 


				<!-- <div class="w-button span12" style="margin-left: 0;">
					<%--                 <button class="btn btn-success" onclick="view_quotation('${quotationBean.id}')">预览</button> --%>
					<button class="btn btn-primary" style="float: left;margin-left: 10px;" id="edit_button" onclick="choice_type()">保存修改</button>
					<form onsubmit="return false;" method="post" enctype="multipart/form-data" style="float:left;">
						<a href="javascript:void(0)" class="btn btn-primary " id="span_file">上传pdf报价单<input class="file_input" type="file" name="file1" onchange="upload_pdf(this)"></a>
				    	<input type="hidden" name="quotationInfoId" value="${quotationBean.id}">
					</form>
					<c:if
						test="${quotationBean.quotationPath == null || quotationBean.quotationPath == ''}">
						<button class="btn disabled" id="download_pdf" style="margin-left: 10px;float:left;">下载报价单</button>
						<button class="btn disabled" id="send_pdf" style="margin-left: 10px;float:left;">发送</button>
					</c:if>
					<c:if
						test="${quotationBean.quotationPath != null && quotationBean.quotationPath != ''}">
						<button class="btn btn-primary" style="margin-left: 10px;float:left;"
							onclick="view_quotation('${quotationBean.id}')">下载报价单</button>
						<button class="btn btn-primary" style="margin-left: 10px;float:left;"
							onclick="send_quotation_email('${quotationBean.id}')">发送</button>
				
					</c:if>
				</div> -->
			</div>


		</div>
		<div class="out-box offer_right">
			<div class="container-fluid">
				<div class="w-font">
					<h2 style="font-size: 16px; font-weight: 700;"> Step4:发送报价单</h2>
					<div class="f_right step_4_div"><span>请确认您平时登录的NBMAIL地址为:&nbsp;&nbsp;</span><a href="http://112.64.174.34:43887/NBEmail/jsp/login.jsp">http://112.64.174.34:43887/NBEmail/jsp/login.jsp</a></div>
				</div>
				<div class="w-hr">
					<hr>
				</div>
				<div class="dow_ok">
					<a href="###" onclick="edit_quotation('${quotationBean.id}',this,'pdf')">下载预览自动生成的PDF报价单</a>
					<a class="dir dir1">========================&gt;</a>
						<c:if test="${quotationBean.quotationPath == null || quotationBean.quotationPath == ''}">
						<button class="z-btn" style="color:#aaa;">发送</button>	
						</c:if>
					<c:if test="${quotationBean.quotationStatus == 2}">
						<c:if test="${quotationBean.quotationPath != null && quotationBean.quotationPath != ''}">
						<button onclick="send_quotation_email('${quotationBean.id}')" style="font-weight:700;">已发送</button>	
						</c:if>
					</c:if>
					<c:if test="${quotationBean.quotationStatus != 2}">
						<c:if test="${quotationBean.quotationPath != null && quotationBean.quotationPath != ''}">
						<button onclick="send_quotation_email('${quotationBean.id}')" style="font-weight:700;">可发送</button>	
						</c:if>
					</c:if>
				</div>
				<div class="dow_sm">
					<p>觉得自动生成的丑，要自己调整一下：</p>
					<p class="da4">(可以本页所有内容都不管，直接点击“上传手工制作的PDF报价单”，并点击发送）</p>
				</div>
				<div class="dow_ok dow_def">
					<a href="###" onclick="edit_quotation('${quotationBean.id}',this,'excel')">下载自动生成的EXCEL报价单</a>
					<a class="dir">==&gt;</a>
					<form onsubmit="return false;" method="post" enctype="multipart/form-data" style="float:left;">
						<a href="javascript:void(0)" class="btn btn-primary " id="span_file"><span class="add_span">上传完成</span>上传手工制作的PDF报价单<input class="file_input" type="file" name="file1" onchange="upload_pdf(this,'${quotationBean.id}')"></a>
				    	<input type="hidden" name="quotationInfoId" value="${quotationBean.id}">
					</form>
					<a class="dir">==&gt;</a>
					<c:if test="${quotationBean.quotationPath == null || quotationBean.quotationPath == ''}">
					   <button id="send_self" style="color:#aaa;">发送</button>	
					</c:if>
					<c:if test="${quotationBean.quotationStatus == 2}">
						<c:if test="${quotationBean.quotationPath != null && quotationBean.quotationPath != ''}">
						<button onclick="send_quotation_email('${quotationBean.id}')" style="font-weight:700;">已发送</button>	
						</c:if>	
					</c:if>
					<c:if test="${quotationBean.quotationStatus != 2}">
						<c:if test="${quotationBean.quotationPath != null && quotationBean.quotationPath != ''}">
						<button onclick="send_quotation_email('${quotationBean.id}')" style="font-weight:700;">可发送</button>	
						</c:if>	
					</c:if>
				</div>
			</div>
			
		</div>
		<div class="w-block" id="div1" style="display: none;">
			<div class="weui_mask"></div>

			<div class="weui_dialog">
				<div class="waimian">
					<a onclick="closeProductDiv();" class="close-reveal-modal" href="#">×</a>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">产品名称 : </label>
									<div class="w-controls">
										<input id="product_name" type="text">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">模具价格: </label>
									<div class="w-controls">
										<input id="mold_price" type="text" onkeyup="keyUp(this)"
											onKeyPress="keyPress(this)" onblur="onBlur(this)" />
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">材质: </label>
									<div class="w-controls">
										<input id="material" type="text">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid" id="quoted_0">
						<div class="span10">
							<div class="span3">
								<div class="control-group">
									<label class="control-label">单价 : </label>
									<div class="w-controls">
										<input type="text" style="width: 90px;" onkeyup="keyUp(this)"
											onKeyPress="keyPress(this)" onblur="calTotalProfit(this)" />
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group w-group">
									<label class="control-label">最小订量 : </label>
									<div class="w-controls">
										<input type="text" style="width: 90px;"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')" />
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group w-group">
									<label class="control-label">总利润率 : </label>
									<div class="w-controls">
										<input type="number" style="width: 30px;"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')" /> <span>(%)</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id="add_quoted_btn" class="w-span3 span10">
						<button onclick="add_quoted(this);" class="btn btn-info"
							style="float: right; margin-right: 17%; margin-top: -9px; margin-bottom: 27px;">添加报价</button>
					</div>

					<div class="row-fluid">
						<div class="control-group">
							<label class="control-label">注释 : </label>
							<div class="w-controls">
								<textarea id="productDivTextarea" class="span11"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="weui_dialog_ft">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="add_product('${quotationBean.id}')">添加</a>
				</div>
			</div>
		</div>
		<!-- 产品编辑 -->
		<div class="w-block" id="div11" style="display: none;">
			<div class="weui_mask"></div>

			<div class="weui_dialog">
				<div class="waimian">
					<input type="hidden" value=""> <a
						onclick="closeProductDiv_edit();" class="close-reveal-modal"
						href="#">×</a>
					<c:if test="${quotationBean.quotationStatus == 2}">
						<div class="row-fluid">
							<div class="span9"></div>
							<div id="add_quoted_btn_edit" class="w-span3 span3">
								<button onclick="update_price(this)" class="btn btn-info">更新价格</button>
							</div>
						</div>
					</c:if>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">产品名称 : </label>
									<div class="w-controls">
										<input id="product_name_edit" type="text">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">模具价格 : </label>
									<div class="w-controls">
										<input id="mold_price_edit" type="text" onkeyup="keyUp(this)"
											onKeyPress="keyPress(this)"
											onblur="calculateMoldProfit(this)" />
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group">
									<label class="control-label">模具利润率: </label>
									<div class="w-controls">
										<input id="mold_profit_edit" style="width: 30px;"
											type="number"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')"
											oninput="calculateMoldPrice(this)" /> <span>(%)</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span9">
							<div class="span6">
								<div class="control-group">
									<label class="control-label">材质 : </label>
									<div class="w-controls">
										<input id="material_edit" type="text">
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="row-fluid price-div" id="quoted_edit_0">
						<div class="span10">
							<div class="span3">
								<div class="control-group">
									<label class="control-label">单价 : </label> <input type="hidden"
										value="" />
									<div class="w-controls">
										<input type="text" style="width: 90px;" onkeyup="keyUp(this)"
											onKeyPress="keyPress(this)" onblur="calTotalProfit(this)" />
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group w-group">
									<label class="control-label">最小订量 : </label>
									<div class="w-controls">
										<input type="text" style="width: 90px;"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')" />
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group w-group">
									<label class="control-label">总利润率 : </label>
									<div class="w-controls">
										<input type="number" style="width: 30px;"
											onkeyup="this.value=this.value.replace(/\D/g,'')"
											onafterpaste="this.value=this.value.replace(/\D/g,'')"
											oninput="calUnitPrice(this)" /> <span>(%)</span>
									</div>
								</div>
							</div>
							<div class="span3">
								<div class="control-group w-group">
									<button onclick="remove_price(this)" class="btn btn-info"
										style="margin-right: 44%; margin-top: 11px;">删除</button>
								</div>
							</div>
						</div>
					</div>
					<div id="add_quoted_btn_edit" class="w-span3 span10">
						<button onclick="add_quoted_edit(this);" class="btn btn-info"
							style="float: right; margin-right: 17%; margin-top: -9px; margin-bottom: 27px;">添加报价</button>
					</div>

					<div class="row-fluid">
						<div class="control-group">
							<label class="control-label">注释 : </label>
							<div class="w-controls">
								<textarea id="productDivTextarea_edit" class="span11"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="weui_dialog_ft">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="modify_product('${quotationBean.id}')">修改</a>
				</div>
			</div>
		</div>



		<div class="weui_dialog_alert" id="price_div" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog" style="width: 40%;">
				<div class="row-fluid">
					<div class="span12">
						<div class="">
							<div class="widget-title">
								<h5 style="float: initial;">价格详情</h5>
							</div>
							<div class="widget-content">
								<input type="checkbox"
									<c:if test="${quotationBean.weightStatus == 1}">checked</c:if>
									style="margin-top: -2px; zoom: 134%;"
									onchange="view_status_update('1','${quotationBean.id}',this)">显示重量
								<input type="checkbox"
									<c:if test="${quotationBean.processStatus == 1}">checked</c:if>
									style="margin-top: -2px; zoom: 134%;"
									onchange="view_status_update('2','${quotationBean.id}',this)">显示工艺
								<div class="row-fluid">
									<div class="span12 btn-icon-pg">
										<ul id="price_ul">


										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<a class="close-reveal-modal" href="javascript:void(0);"
					onclick="close_price_div()">×</a>
			</div>
		</div>
		<!-- <<<<<<<<<<<<添加、编辑产品结束>>>>>>>>>>>>>> -->


		<div class="w-block" id="div2" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog">
				<div class="w-hook">
					<div class="w-padding">
						<div class="w-left">报价单预设备注选择</div>
						<div class="w-right" onclick="close_temp()">X</div>
					</div>
				</div>
				<div class="container-fluid">
					<div class="widget-box">
						<div class="widget-title">
							<span class="icon">
								<div class="checker" id="uniform-title-checkbox">
									<span><input type="checkbox" id="title-checkbox"
										name="title-checkbox" style="opacity: 0;"></span>
								</div>
							</span>
							<h5>全选</h5>
							<span class="label label-info cur_p" onclick="input_img_remark()">预设图片备注信息</span>
<!-- 							<span class="label label-primary cur_p" onclick="input_text_remark()">预设文本备注信息</span> -->
						</div>
						<div class="widget-content nopadding">
							<table class="table table-bordered table-striped with-check"
								id="remark_table">
								<c:forEach var="obj" items="${tempRemarks}" varStatus="i">
									<tr>
										<td class="w-first-td">
											<div class="checker" id="uniform-undefined">
												<p>${i.index+1}</p>
												<span><input name="checkbox" type="checkbox" <c:if test="${quotationBean.remarkImg == obj.remark || quotationBean.remark == obj.remark}">checked</c:if>/></span>									
											</div>
										</td>
										<td style="word-break:break-all;">${obj.remark}</td>
										<td>
											<div>
												<a class="z-style" onclick="open_edit_text_remark(this,'${obj.textOrPicture}','${obj.id}')">编辑</a><span
													style="display: none;">${obj.textOrPicture}</span> <a class="z-style" onclick="delete_temp(this,'${obj.id}')">删除</a>
											</div>
											<div style="float: left;">
												<c:if test="${i.index != 0}">
													<img src="img/arrows-up.png" class="z-img-arrow"
														style="width: 20px;"
														onclick="index_up(this,'${i.index+1}','${obj.id}')">
												</c:if>
												<c:if test="${(i.index+1) != tempRemarks.size()}">
													<img src="img/arrows-down.png" class="z-img-arrow"
														style="width: 20px;"
														onclick="index_down(this,'${i.index+1}','${obj.id}')">
												</c:if>
											</div> <input type="hidden" value="${obj.id}">
										</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>
				</div>
				<div class="weui_dialog_ft" style="margin-top: 0;">
					<a href="javascript:;" class="weui_btn_dialog primary"
						style="border-right: 1px solid #D5D5D6;" onclick="import_temp()">插入</a>
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="close_temp()">关闭</a>
				</div>
			</div>
		</div>



		<div class="w-block" id="div3" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog"
				style="width: 30%; border: 1px solid #cdcdcd;">
				<div class="w-hook">
					<div class="w-padding">
						<div class="w-left">报价单预设备注选择</div>
						<div class="w-right" onclick="close_div3()">X</div>
					</div>
				</div>
				<div class="container-fluid">

					<div class="w-textarea">
						<textarea class="w-span-text" id="text_temp_content"></textarea>
					</div>

				</div>
				<div class="weui_dialog_ft" style="margin-top: 0;">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="add_text_temp()">添加</a>
				</div>
			</div>
		</div>

		<div class="w-block" id="div5" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog"
				style="width: 30%; border: 1px solid #cdcdcd;">
				<div class="w-hook">
					<div class="w-padding">
						<div class="w-left">报价单预设备注修改</div>
						<div class="w-right" onclick="close_div5()">X</div>
					</div>
				</div>
				<div class="container-fluid">

					<div class="w-textarea">
						<textarea class="w-span-text" id="text_temp_content_edit"></textarea>
					</div>

				</div>
				<div class="weui_dialog_ft" style="margin-top: 0;">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="edit_text_temp()">修改</a>
				</div>
			</div>
		</div>


		<div class="w-block" id="div4" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog"
				style="width: 25%; border: 1px solid #cdcdcd;">
				<div class="w-hook">
					<div class="w-padding">
						<div class="w-left">报价图片备注</div>
						<div class="w-right" onclick="close_div4()">X</div>
					</div>
				</div>
				<form id="file_upload_id" action="${ctx}/saveTemplatePicture.do"
					onsubmit="return false;" method="post"
					enctype="multipart/form-data">
					<div class="container-fluid">
						<div class="row-fluid w-span-tu" style="margin: 0;">
							<input type="hidden" name="templateRemarkId"
								id="template_remark_id"> <input type="hidden"
								name="pictureIndex" id="picture_index"> <input
								type="hidden" name="drawingName" id="drawing_name">
							<div class="span3">
								<div>
									<input type="hidden" value="1"> <input type="file"
										class="z-input-file" name="file_upload1"
										onchange="uploadFile(this)"> <img src="img/tu.png"
										class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3">
								<div>
									<input type="hidden" value="2"> <input type="file"
										class="z-input-file" name="file_upload2"
										onchange="uploadFile(this)"> <img src="img/tu.png"
										class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3">
								<div>
									<input type="hidden" value="3"> <input type="file"
										class="z-input-file" name="file_upload3"
										onchange="uploadFile(this)"> <img src="img/tu.png"
										class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3 last_span">
								<div>
									<input type="hidden" value="4"> <input type="file"
										class="z-input-file" name="file_upload4"
										onchange="uploadFile(this)"> <img src="img/tu.png"
										class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
						</div>
						<p class="w-prompt">点击"+"上传图片，最多添加四张</p>
					</div>
				</form>
				<div class="weui_dialog_ft" style="margin-top: 0;">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="save_picture_temp()">保存</a>
				</div>
			</div>
		</div>


		<div class="w-block" id="div6" style="display: none;">
			<div class="weui_mask"></div>
			<div class="weui_dialog"
				style="width: 25%; border: 1px solid #cdcdcd;">
				<div class="w-hook">
					<div class="w-padding">
						<div class="w-left">报价图片备注</div>
						<div class="w-right" onclick="close_div6()">X</div>
					</div>
				</div>
				<form id="file_upload_id1" action="${ctx}/saveTemplatePicture.do"
					onsubmit="return false;" method="post"
					enctype="multipart/form-data">
					<div class="container-fluid">
						<div class="row-fluid w-span-tu" style="margin: 0;">
							<input type="hidden" name="templateRemarkId"
								id="template_remark_id1"> <input type="hidden"
								name="pictureIndex" id="picture_index1"> <input
								type="hidden" name="drawingName" id="drawing_name1">
							<div class="span3">
								<div style="width: 80px; height: 80px;">
									<input type="hidden" value="1"> <input type="file"
										class="z-input-file" name="file_upload1"
										onchange="uploadFile_edit(this)"> <img
										src="img/tu.png" id="img_1" class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3">
								<div style="width: 80px; height: 80px;">
									<input type="hidden" value="2"> <input type="file"
										class="z-input-file" name="file_upload2"
										onchange="uploadFile_edit(this)"> <img
										src="img/tu.png" id="img_2" class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3">
								<div style="width: 80px; height: 80px;">
									<input type="hidden" value="3"> <input type="file"
										class="z-input-file" name="file_upload3"
										onchange="uploadFile_edit(this)"> <img
										src="img/tu.png" id="img_3" class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
							<div class="span3">
								<div style="width: 80px; height: 80px;">
									<input type="hidden" value="4"> <input type="file"
										class="z-input-file" name="file_upload4"
										onchange="uploadFile_edit(this)"> <img
										src="img/tu.png" id="img_4" class="w-img-tu">
								</div>
								<img src="img/pic.png" class="z-add-pic">
							</div>
						</div>
						<p class="w-prompt">点击"+"上传图片，最多添加四张</p>
					</div>
				</form>
				<div class="weui_dialog_ft" style="margin-top: 0;">
					<a href="javascript:;" class="weui_btn_dialog primary"
						onclick="edit_picture_temp('${quotationBean.id}')">修改</a>
				</div>
			</div>
		</div>
	</div>
	<div class="weui_dialog_confirm" id="dialog10" style="display: none;">
		<div class="weui_mask"></div>
		<div class="weui_dialog">
			<div class="weui_dialog_hd">
				<strong class="weui_dialog_title">保存成功</strong>
			</div>
			<div class="weui_dialog_ft">
				<a href="javascript:;" class="weui_btn_log default"
					style="border-right: 1px solid #D5D5D6;"
					onclick="quotation_list();">确定</a>
			</div>
		</div>
	</div>


	<div class="row-fluid">
		<div id="footer" class="span12">
			<!--    2013 &copy; Shanghai ce melting. More Templates <a href="#" target="_blank" title="">上海策融</a>  -->
		</div>
	</div>
	
	
	<div class="w-block" id="w-div" style="display: none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog" style="width:280px;">
            <div class="w-hook">
                <div class="w-padding">
                    <div class="w-left">保存类型</div>
                    <div class="w-right" onclick="cancel_select()">X</div>
                </div>
            </div>
            <div class="container-fluid">
                <div class="row-fluid w-span-tu" style="margin: 0;">
                    
                    <div class="w-margin" style="
    width: 150px;
    margin: auto;
    padding-left: 60px;
    margin: 20px 0;
    text-align: left;
">
<label onclick="changeRadio(this)">
                  <div class="radio"><span class=""><input type="radio" name="radios" value="excel" style="opacity: 0;"></span></div>保存为excel格式</label>
<label onclick="changeRadio(this)">
                  <div class="radio"><span class="checked"><input type="radio" name="radios" value="pdf" style="opacity: 0;"></span></div>保存为pdf格式</label></div>
                    
                    
                </div>
                
            </div>
            <div class="weui_dialog_ft" style="margin-top: 0;">
                <a href="javascript:;" class="weui_btn_dialog primary" style="border-right: 1px solid #D5D5D6;" onclick="edit_quotation('${quotationBean.id}',this)">保存</a>
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="cancel_select()">取消</a>
            </div>
        </div>
    </div>
    
    

	<!-- 上传进度弹框 -->
	<div class="w-out-box" id="show_upload_dialog" style="display: none;">
		<div class="weui_mask"></div>
		<div class="w-weui_dialog" style="width: 510px;">
			<div id="container">

				<div class="content">
					<h1>上传进度</h1>
				</div>

				<!-- Progress bar -->
				<div id="progress_bar" class="ui-progress-bar ui-container">
					<div class="ui-progress" style="width: 0%;" id="ui-progress-upload">
						<span class="ui-label" style="display: none;">正在加载...<b
							class="value">0%</b></span>
					</div>
				</div>
				<!-- /Progress bar -->
				<a class="close-reveal-modal" style="color: #eee; font-size: 22px;"
					href="javascript:void(0);" onclick="cancel_upload()">×</a>
				<div class="content" id="main_content" style="display: none;">
					<p>加载完成。</p>
				</div>
			</div>
		</div>
	</div>

</body>

<script type="text/javascript">


	/**
	*@author tb
	*@time 2017.3.30
	*点击添加产品按钮
	*/
	function addProduct(){
		$("#div1").show();
	}

	
	//添加报价,层级标记;
	var quoted_number = 0;
	var quoted_edit_index = 0;
	
	/**
	*关闭增加产品弹出层;
	*@author tb
	*@time 2017.3.30
	*/
	function closeProductDiv(){
		$("#div1").hide();
		var div = $("#add_quoted_btn").parent("div");
		var div_id = div.attr("id");
		$("#product_name").val("");
		$("#mold_price").val("");
		$("#productDivTextarea").val("");
		$("#quoted_1 input").val("");
		if(div_id=="quoted_1"){
			return;
		}
		$("#quoted_1").append($("#add_quoted_btn"));
		for(var i = 1; i< quoted_number+1 ;i++){
			$("#quoted_"+i).remove();
		}
		quoted_number = 0;
	}
	
	function closeProductDiv_edit(){
		$("#div11").hide();
		$("#product_name_edit").val("");
		$("#mold_price_edit").val("");
		$("#productDivTextarea_edit").val("");
		for(var i = 1; i< quoted_edit_index+1 ;i++){
			$("#quoted_edit_"+i).remove();
		}
		quoted_edit_index = 0;
	}
	
	
	/**
	*点击添加报价按钮,
	*新增一行报价
	*@author tb
	*@time 2017.3.30
	*/
	function add_quoted(obj){

        var flag = true;	
		//获得单价和最小订量;
		This = $(obj).parent().prev();		
		This.find('input').each(function(i){
			var input_val = $(this).val();
			var content = $(this).parent().prev().text();
			content = content.replace(":","");
			if(input_val == null || input_val == '' || input_val == undefined){
				easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '请输入'+content
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
				flag = false;
				return false;
			}			
		})
		if(!flag){
		   return;
		}
		quoted_number +=1;
        $(obj).parent().before(This.clone());
        var id = "quoted_"+(quoted_number);
        $(obj).parent().prev().attr("id",id);

	}
	/**
	*点击添加报价按钮(编辑)
	*新增一行报价
	*@author polo
	*@time 2017.5.25
	*/
	function add_quoted_edit(obj){
	    
		var flag = true;
		//获得单价和最小订量;
        This = $(obj).parent().prev();	
		var price = $(obj).parent().parent().parent().find("input").val();
		var quantity = $(obj).parent().prev().find("input").eq(1).val();
		This.find('input').each(function(i){
			if(i > 0){
				var input_val = $(this).val();
				var content = $(this).parent().prev().text();
				content = content.replace(":","");
				if(input_val == null || input_val == '' || input_val == undefined){
					easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '请输入'+content
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
					flag = false;
					return false;
				}
			}			
			
		})
		
		if(!flag){
			   return;
			}
		 quoted_edit_index +=1;
		 $(obj).parent().before(This.clone());
	     var id = "quoted_edit_"+(quoted_edit_index);
	     $(obj).parent().prev().attr("id",id);
	     $(obj).parent().prev().find('input').eq(0).val('');
	     $(obj).parent().prev().find('.span3:last').show();
	}
	
	/**
	*点击添加报价按钮,
	*新增一行报价
	*@author polo
	*@time 2017.5.23
	*/	
	function add_product(quotationInfoId){
		
		var productName = $("#product_name").val();
		var moldPrice = $("#mold_price").val();
		var material = $("#material").val();
		var remark = $('#productDivTextarea').val();
		if(!(product_name&&mold_price)){
			//没有输入产品名和模具价格;
			easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '请输入产品名和模具价格 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			return;
		}
		
		var unit_prices = "";
		var quantities = "";
		var priceList = "";
		for(var i = 0; i< quoted_number+1 ;i++){
			price = $("#quoted_"+i).find("input:first").val();
			quantity = $("#quoted_"+i).find("input").eq(1).val();
			totalProfit = $("#quoted_"+i).find("input").eq(2).val();
			totalProfit = Number(totalProfit/100).toFixed(2);
			materialProfitRate = $("#quoted_"+i).find("input").eq(3).val();
			materialProfitRate = Number(materialProfitRate/100).toFixed(2);
		    priceList +=  "-" + price + "%" + "-" + quantity +  "%" + "-" + totalProfit +  "%" + "-" + materialProfitRate +  "%" + ",";
		}
	
				
		 $.post("/supplier/addQuotationProduct.do", 
				  { 
			        "productName" : productName,
			        "moldPrice" : moldPrice,
			        "material" : material,
			        "remark" : remark,
			        "quotationInfoId" : quotationInfoId,
			        "priceList" : priceList
			      },
				   function(result){	
			  		  closeProductDiv();
			    	  if(result.state == 0){
			    	   $('#product_tbody').empty();	  
			    	   var products = result.data;
			    	   var product_tl = products.length;	
			    	   for(var i = 0; i< product_tl ;i++){			    		  
				    	   var prices = products[i].quotationProductionPriceBeanList;	
				    	   var price_tl = prices.length;			    		   
				    	   var unit_prices = "";
				    	   var quantities = "";
				    	   for(var j = 0; j< price_tl ;j++){
					    	   price_td = '<span>'+prices[j].productPrice+'</span><a class="z-a-price" onclick="show_price_detalis('+prices[j].id+','+products[i].id+')">显示价格详情</a><br><input type="hidden" value="'+prices[j].id+'"><input type="hidden" value="'+prices[j].productPrice+'">';
					 		   quantity_td = '<span>'+prices[j].quantity+'</span><br>'; 
					 		   
					 		  unit_prices += price_td;
					 		  quantities += quantity_td;
				    	   }
                          var tr = '<tr class="odd gradeX">'+
                                      '<td>'+(i+1)+'</td>'+
                                      '<td>'+products[i].productName+'</td>'+  
                                      '<td>'+products[i].moldPrice+'</td>'+ 
                                      '<td><span>'+products[i].material+'<span><input type="hidden" value="'+products[i].moldProfitRate+'"></td>'+ 
                                      '<td>'+quantities+'</td>'+
                                      '<td>'+unit_prices+'</td>'+                                   
                                      '<td>'+(products[i].productNotes == null ? "" : products[i].productNotes)+'</td>'+ 
			    	                   ' <td class="center">'+
                                           '<a href="#"><button class="btn btn-primary" onclick="edit_product(this,'+products[i].id+')">编辑</button></a>'+
                                           '<a href="#"><button class="btn btn-danger" style="margin-left: 10px;" onclick="delete_product(this,'+products[i].id+','+quotationInfoId+'),">删除</button></a>'+
                                      '</td>'+
                                    'tr';  
                        $('#product_tbody').append(tr);             
                                      
			    	   }
		    		  
			    	  }else{
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '修改失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	  }
			      })	  	
	}
	
	
	/**
	*点击编辑产品
	*@author polo
	*@time 2017.5.25
	*/	
	//添加报价,层级标记;
	var product_index = 1;
	function edit_product(obj,productId){		
		product_index = $(obj).parents('tr').find('td:eq(0)').text();
		
		$('#product_name_edit').val($(obj).parents('tr').find('td:eq(1)').text());
		$('#mold_price_edit').val($(obj).parents('tr').find('td:eq(3)').find('span').text());
		$('#material_edit').val($(obj).parents('tr').find('td:eq(2)').find('span').text());
		$('#mold_profit_edit').val(Number($(obj).parents('tr').find('td:eq(2)').find('input').val() * 100).toFixed(0));
		$('#productDivTextarea_edit').val($(obj).parents('tr').find('td:eq(6)').text());
		
		
		var tl = $(obj).parents('tr').find('td:eq(4)').find('span').length;
		for(var i=0;i<tl;i++){
			if(i == 0){
				$('#quoted_edit_0').find('input:eq(0)').val($(obj).parents('tr').find('td:eq(5)').find('input:eq(0)').val());
				$('#quoted_edit_0').find('input:eq(1)').val($(obj).parents('tr').find('td:eq(5)').find('span:eq(0)').text());
				$('#quoted_edit_0').find('input:eq(2)').val($(obj).parents('tr').find('td:eq(4)').find('span:eq(0)').text());
				$('#quoted_edit_0').find('input:eq(3)').val($(obj).parents('tr').find('td:eq(5)').find('input:eq(2)').val()*100);
				$('#quoted_edit_0').find('input:eq(4)').val($(obj).parents('tr').find('td:eq(5)').find('input:eq(3)').val()*100);
				$('#quoted_edit_0').find('.span3:last').hide();
			}else{
				quoted_edit_index +=1;
				var priceId = $(obj).parents('tr').find('td:eq(5)').find('br').eq(i).next().val();
				var price = $(obj).parents('tr').find('td:eq(5)').find('span:eq('+i+')').text();
				var quantity = $(obj).parents('tr').find('td:eq(4)').find('span:eq('+i+')').text();
				var totalProfit = $(obj).parents('tr').find('td:eq(5)').find('br').eq(i).next().next().next().val()*100;
				totalProfit = Number(totalProfit).toFixed(0);
				
				var new_div = "<div class='row-fluid price-div' id='quoted_edit_"+quoted_edit_index+"'><div class='span10'><div class='span3'><div class='control-group'><label class='control-label'>单价 : </label>"+
				        "<input type='hidden' value="+priceId+">"+
		                "<div class='w-controls'><input type='text' style=\"width:90px;\" value="+price+" onkeyup=\"keyUp(this)\" onKeyPress=\"keyPress(this)\" onblur=\"calTotalProfit(this)\"/></div></div></div><div class='span3'><div class='control-group w-group'>"+
		                "<label class='control-label'>最小订量 : </label><div class='w-controls'><input type='text' style=\"width:90px;\" value="+quantity+" onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\"></div></div></div>"+
		                "<div class=\"span3\">"+
                        "<div class=\"control-group w-group\">"+
                            "<label class=\"control-label\">总利润率 : </label>"+
                            "<div class=\"w-controls\">"+
                                "<input type=\"number\" style=\"width: 30px;\" value="+totalProfit+" onkeyup=\"this.value=this.value.replace(/\D/g,'')\" onafterpaste=\"this.value=this.value.replace(/\D/g,'')\" oninput=\"calUnitPrice(this)\"/>"+
                                "<span>(%)</span>"+
                            "</div>"+
                        "</div></div>"+
                        "<div class=\"span3\">"+
                        "<div class=\"control-group w-group\">"+
                           "<button onclick=\"remove_price(this)\" class=\"btn btn-info\" style=\"margin-right: 44%;margin-top: 11px;\">删除</button>"+ 
                        "</div>"+
                        "</div>"+
                    "</div></div>";
		           $('#quoted_edit_'+(i-1)).after(new_div);
			  }

		}
		$('#div11').find('input:first').val(productId);
		$('#div11').show();
	
	}
	
	/**
	*修改产品信息
	*@author polo
	*@time 2017.5.25
	*/	
	function modify_product(quotationInfoId){
		
		var productId = $('#div11').find('input:first').val();
		var productName = $("#product_name_edit").val();
		var moldPrice = $("#mold_price_edit").val();
		var material = $("#material_edit").val();
		var moldProfitRate = Number($("#mold_profit_edit").val() / 100).toFixed(2);
		var remark = $('#productDivTextarea_edit').val();
		if(!(product_name&&mold_price)){
			//没有输入产品名和模具价格;
			easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '请输入产品价格和模具价格 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			return;
		}
		
		var unit_prices = "";
		var quantities = "";
		var priceList = "";
		for(var i = 0; i< quoted_edit_index+1 ;i++){
			priceId = $("#quoted_edit_"+i).find("input:first").val();
			price = $("#quoted_edit_"+i).find("input:eq(1)").val();
			quantity = $("#quoted_edit_"+i).find("input").eq(2).val();
			totalProfit = $("#quoted_edit_"+i).find("input").eq(3).val();
			totalProfit = Number(totalProfit/100).toFixed(2);
// 			materialProfitRate = $("#quoted_edit_"+i).find("input").eq(4).val();
// 			materialProfitRate = Number(materialProfitRate/100).toFixed(2);
		    priceList += "-" + priceId + "%" + "-" + price + "%" + "-" + quantity +  "%" + "-" + totalProfit +  "%" + ",";
		}
					
		
		 $.post("/supplier/updateQuotationPrice.do", 
				  { 
			        "productId" : productId,
			        "productName" : productName,
			        "moldPrice" : moldPrice,
			        "moldProfitRate" : moldProfitRate,
			        "material" : material,
			        "remark" : remark,
			        "quotationInfoId" : quotationInfoId,
			        "priceList" : priceList
			      },
				   function(result){	
			  		  closeProductDiv_edit();
			    	  if(result.state == 0){
			    	   $('#product_tbody').empty();	  
			    	   var products = result.data;
			    	   var product_tl = products.length;	
			    	   for(var i = 0; i< product_tl ;i++){			    		  
				    	   var prices = products[i].quotationProductionPriceBeanList;
				    	   var price_tl = prices.length;			    		   
				    	   var unit_prices = "";
				    	   var quantities = "";
				    	   for(var j = 0; j< price_tl ;j++){
					    	   price_td = '<span>'+prices[j].productPrice+'</span><a class="z-a-price" onclick="show_price_detalis('+prices[j].id+','+products[i].id+')">显示价格详情</a><br><input type="hidden" value="'+prices[j].id+'"><input type="hidden" value="'+prices[j].productPrice+'"><input type="hidden" value="'+prices[j].totalProfitRate+'">';
					 		   quantity_td = '<span>'+prices[j].quantity+'</span><br>'; 
					 		   
					 		  unit_prices += price_td;
					 		  quantities += quantity_td;
				    	   }
                           var tr = '<tr class="odd gradeX">'+
                                       '<td>'+(i+1)+'</td>'+
                                       '<td>'+products[i].productName+'</td>'+  
                                       '<td><span>'+products[i].material+'</span><input type="hidden" value="'+products[i].moldProfitRate+'"></td>'+ 
                                       '<td><span>'+products[i].moldPrice+'</span><input type="hidden" value="'+products[i].moldPrice+'"></td>'+ 
                                       '<td>'+quantities+'</td>'+
                                       '<td>'+unit_prices+'</td>'+                                     
                                       '<td>'+(products[i].productNotes == null ? "" : products[i].productNotes)+'</td>'+ 
			    	                   ' <td class="center">'+
                                            '<a href="#"><button class="btn btn-primary" onclick="edit_product(this,'+products[i].id+')">编辑</button></a>'+
                                            '<a href="#"><button class="btn btn-danger" style="margin-left: 10px;" onclick="delete_product(this,'+products[i].id+','+quotationInfoId+')">删除</button></a>'+
                                       '</td>'+
                                     'tr';  
                         $('#product_tbody').append(tr);             
                                       
			    	   }
		    		  
			    	  }else{
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '修改失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
			    	  }
			      })	  	      
	}
	
	
	

	   //移除当前行   
	   function delete_product(obj,productId,quotationInfoId){
		if (confirm("是否删除此产品？")){
			
			  $.post("/supplier/deleteQuotationProduct.do", 
					  { 
				        "productId" : productId,
				        "quotationInfoId" : quotationInfoId
				      },
					   function(result){
				    	  if(result.state == 0){
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

	//使用备注模板
	function select_temp_remark(){
		$('#div2').show();		
	}
	//关闭模板备注
	function close_temp(){
		$('#div2').hide();
	}
	//打开文本添加remark
	function input_text_remark(){
		$('#div3').show();
	}
	  
	//打开图片添加remark
	function input_img_remark(){
		$('#div4').show(); 	
	}
	
	var remark_index = 1;
	var textOrPicture = 1;
	
	//打开remark文本编辑框
	function open_edit_text_remark(obj,str,tempRemarkId){
		remark_index = $(obj).parents('tr').find('p').text();
		var text = $(obj).parents('td').prev().html();
		$("#text_temp_content_edit").html(text);
		if(str == 1){
			$('#div5').show();
		}
		if(str == 2){	
			  $('#div6').show();
			  $.post("/supplier/queryByTempRemarkId.do", 
					  { 
				        "tempRemarkId" : tempRemarkId
				      },
					   function(result){
				    	  if(result.state == 0){
				    		  if(result.data.picturePath1 != null && result.data.picturePath1 != ""){
					    		  $('#img_1').attr("src",result.data.picturePath1Compress); 
				    		  }
				    		  if(result.data.picturePath2 != null && result.data.picturePath2 != ""){
				    			  $('#img_2').attr("src",result.data.picturePath2Compress);
				    		  }
				    		  if(result.data.picturePath3 != null && result.data.picturePath3 != ""){
				    			  $('#img_3').attr("src",result.data.picturePath3Compress);
				    		  }
				    		  if(result.data.picturePath4 != null && result.data.picturePath4 != ""){
				    			  $('#img_4').attr("src",result.data.picturePath4Compress); 
				    		  }				    		  
				    		  $('#template_remark_id1').val(tempRemarkId);				    		 
				    	  }
				      })
		 }
	}
	
	
	//删除编辑模板
	function delete_temp(obj,tempRemarkId){
		
		if (confirm("是否删除此产品？")){
		$.post("/supplier/deleteTempById.do", 
				  { 
			        "tempRemarkId" : tempRemarkId
			      },
				   function(result){
			    	  if(result.state == 0){
			    		  
			    		  $('#remark_table').empty();	
			 		        var remarks = result.data;
			 		        var tl = remarks.length;
			 		        for(var i=0;i<tl;i++){
			 		        	var img_div = '';
			 		        	if(i == 0 && i != tl-1){
			 		        		img_div = '<div style="float:left;">'+
			                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
			                        ' </div>';
			 		        	}else if(i != 0 && i != tl-1){
			 		        		img_div = '<div style="float:left;">'+
			                        ' <img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(i+1)+','+remarks[i].id+')">'+
			                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
			                        ' </div>';
			 		        	}else if(i == tl-1){
			 		        		img_div = '<div style="float:left;">'+
			                        ' <img src="img/arrows-up.png"  class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(i+1)+','+remarks[i].id+')">'+
			                        ' </div>';
			 		        	}
			 		        	
			 		        	
			 			        var tr = 
				 		        	'<tr>'+
			                        '<td class="w-first-td">'+          
			                        '<div class="checker" id="uniform-undefined"><p>'+(i+1)+'</p><span><input name="checkbox" type="checkbox"/></span></div></td>'+
			                        ' <td style="word-break:break-all;">'+remarks[i].remark+'</td>'+
			                        ' <td>'+
			                        ' <div>'+
			                        ' <a href="#" onclick="open_edit_text_remark(this,'+remarks[i].textOrPicture+','+remarks[i].id+')">编辑</a><span style="display:none;">'+remarks[i].textOrPicture+'</span>'+
			                        ' <a href="#" onclick="delete_temp(this,'+remarks[i].id+')">删除</a>'+
			                        ' </div>'+ img_div +          
			                        ' <input type="hidden" value="'+remarks[i].id+'">'+
			                        ' </td>'+                                                                                                                 
			                    ' </tr>';
			                    $('#remark_table').append(tr);
			 		        }	
			    		  
			    		  
			    		  
			    		  
			    		  
			    		  easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '删除成功 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							}); 
			    		
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
  	
	//关闭文本添加remark
	function close_div3(){
		$('#div3').hide();
	}
	//关闭文图片添加remark（删除当前记录）
	 function close_div4(){
		$('#div4').hide();
		var templateRemarkId = $('#template_remark_id').val();
		if(!(templateRemarkId)){
			return;
		}
		 $.post("/supplier/deleteTemplatePictures.do", 
				  { 
			        "templateRemarkId" : templateRemarkId
			      },
				   function(result){
			    	  
			      })
	 }
	//关闭文本编辑remark
	function close_div5(){
		$('#div5').hide();
	}
	//关闭图片编辑
	function close_div6(){
		$('#div6').hide();
	}
	
	//导入当前选择的备注模板
	function import_temp(){
                
		 var imgNames = '';  
  		 var remarks_text = '';
   		 var remarks_img = '';
   		 $('#select_temp_pic').empty();
         $("#remark_table").find('.checker').find('input').each(function(index, element){
       	  if( $(this).is(':checked') ){
       		  
       		  if($(this).parents('tr').find('span:last').text() == 1){
       			var remarks =  $(this).parents('td').next().html();
       			$('#select_temp_remark').empty();  
       			remarks_text += remarks + '\r\n';
         		$('#select_temp_remark').append(remarks_text);
       		  }      		   
       		  
       		  if($(this).parents('tr').find('span:last').text() == 2){ 
       			 var remark_img =  $(this).parents('td').next().html(); 
       			  $('#select_temp_pic').empty();
       			  var tl = $(this).parents('td').next().find('img').length;
       			  for(var i=0;i<tl;i++){
       				 var path = $(this).parents('td').next().find('img').eq(i).attr("src"); 
       				 imgNames += path.substring(path.lastIndexOf("/")+1,path.length)+',';
       			  } 
           		  remarks_img += '<em class="del-z">'+remark_img +'<button onclick="del_z(this)">删除</button></em><br>';
     			  $('#select_temp_pic').show();
     			  $('#select_temp_pic').append(remarks_img);  
       		  }
       	   }
         })
         $('.checker').find('span').css('background-position','0px -260px');
       	 $('.checker').find('input').attr("checked",false);
         imgNames = imgNames.substring(0,imgNames.length-1)
         $('#img_names').val(imgNames);
         $('#div2').hide();
	}
  
	//修改备注模板
	function edit_text_temp(){
		var content = $("#text_temp_content_edit").val();
		$("#remark_table").find('tr').eq(remark_index-1).find('td').eq(1).html(content);
		close_div5();
	}
	
	

	
	/**
	*添加文字备注
	*@author polo
	*@time 2017.5.24
	*/	
   function add_text_temp(){
	   
	  var textContent =  $('#text_temp_content').val();
	  if(!(textContent)){
			//没有输入文本
			easyDialog.open({
				  container : {
				    header : 'Prompt message',
				    content : '请输入文字信息 '
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return;
		}
	  textOrPicture = 1;
	  
	  $.post("/supplier/saveTempRemark.do", 
			  { 
	        "remark": textContent, 
	        "textOrPicture" : textOrPicture
              },
			   function(result){
			    if(result.state == 0){
			    	
			    	var tl = $("#remark_table").find("tr").length;
			    	 if(tl != 0){
			    	    	$("#remark_table").find("tr").eq(tl-1).find('td:eq(2)').find('div:eq(1)').append('<img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(tl+1)+','+result.data.id+')">'); 
			    	    }
			    	    
	 		        	var img_div  = '<div style="float:left;">'+
                                    '<img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(tl+1)+','+result.data.id+')">'+
	                                   ' </div>';	 		        	
	 			        var tr = 
		 		        	'<tr>'+
	                        '<td class="w-first-td">'+          
	                        '<div class="checker" id="uniform-undefined"><p>'+(tl+1)+'</p><span><input name="checkbox" type="checkbox"/></span></div></td>'+
	                        ' <td style="word-break:break-all;">'+result.data.remark+'</td>'+
	                        ' <td>'+
	                        ' <div>'+
	                        ' <a href="#" onclick="open_edit_text_remark(this,'+result.data.textOrPicture+','+result.data.id+')">编辑</a><span style="display:none;">'+result.data.textOrPicture+'</span>'+
	                        ' <a href="#" onclick="delete_temp(this,'+result.data.id+')">删除</a>'+
	                        ' </div>'+ img_div +          
	                        ' <input type="hidden" value="'+result.data.id+'">'+
	                        ' </td>'+                                                                                                                 
	                    ' </tr>';
	                    $('#remark_table').append(tr);
	                	close_div3(); 
				    	$('#text_temp_content').val('');
			    }else{
			    	easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '保存失败 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			    }
		    	  
            }); 

 }
	
	
	

	/**
	*上传图片
	*@author polo
	*@time 2017.5.25
	*/	
	function uploadFile(obj){
		
		 var path =  $(obj).val();	
 		 sppath = path.split("\\");
 		 var drawingName = sppath[sppath.length-1];
 		 if(drawingName == null || drawingName == "" || drawingName == undefined){
 			 return;
 		 }
 		 $('#drawing_name').val(drawingName);			
		 $('#picture_index').val($(obj).prev().val());
		 if(drawingName != null && drawingName != ''){
			 autTime();
		 }
		 
		 $("#file_upload_id").ajaxSubmit({
   			type: "post",
   			url: "/supplier/saveTemplatePicture.do",
   	     	dataType: "text",
   	        success: function(str){
   	        	var result	= eval("("+str+")");
   	        	if(result.state == 0){
						var templateRemarkId = result.data.templateRemarkId;
						var picturePath = result.data.picturePath;
                        $(obj).next().attr("src",picturePath);
                        $('#template_remark_id').val(templateRemarkId);
					}else{
						easyDialog.open({
				    		  container : {
				    		    header : 'Prompt message',
				    		    content : 'failed to upload'
				    		  },
				    		  overlay : false,
				    		  autoClose : 1000				    		  
				    		});	
						$('#show_upload_dialog').hide();
					}   
	     	       }
			   
     	 });
		
	}
	

	
/**
*所有图片添加到图片备注
*@author polo
*@time 2017.5.24
*/	
 function save_picture_temp(){	 	 
	 var templateRemarkId = $('#template_remark_id').val();
	 if(!(templateRemarkId)){
			//没有上传图片
			easyDialog.open({
				  container : {
				    header : 'Prompt message',
				    content : '请上传图片'
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return;
		}
	 
	  $.post("/supplier/saveTemplatePictures.do", 
			  { 
		        "templateRemarkId" : templateRemarkId
		      },
			   function(result){
		    	  if(result.state == 0){
		    		  
		    	    var tl = $("#remark_table").find("tr").length;
		    	    if(tl != 0){
		    	    	$("#remark_table").find("tr").eq(tl-1).find('td:eq(2)').find('div:eq(1)').append('<img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(tl+1)+','+result.data.id+')">'); 
		    	    }
		    	    
		        	var img_div  = '<div style="float:left;">'+
                                     '<img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(tl+1)+','+result.data.id+')">'+
                                  ' </div>';	 		        	
				        var tr = 
	 		        	'<tr>'+
	                       '<td class="w-first-td">'+          
	                       '<div class="checker" id="uniform-undefined"><p>'+(tl+1)+'</p><span><input name="checkbox" type="checkbox"/></span></div></td>'+
	                       ' <td style="word-break:break-all;">'+result.data.remark+'</td>'+
	                       ' <td>'+
	                       ' <div>'+
	                       ' <a href="#" onclick="open_edit_text_remark(this,'+result.data.textOrPicture+','+result.data.id+')">编辑</a><span style="display:none;">'+result.data.textOrPicture+'</span>'+
	                       ' <a href="#" onclick="delete_temp(this,'+result.data.id+')">删除</a>'+
	                       ' </div>'+ img_div +          
	                       ' <input type="hidden" value="'+result.data.id+'">'+
	                       ' </td>'+                                                                                                                 
	                   ' </tr>';
	                   $('#remark_table').append(tr);
	                   $('#div4').hide();    
		    	  }else{
		    		  easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '保存失败 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						}); 
		    	  }
		      })
 }	
	
   /**
	*上传图片(编辑使用)
	*@author polo
	*@time 2017.5.25
	*/	
	function uploadFile_edit(obj){
		
		 var path =  $(obj).val();	
		 sppath = path.split("\\");
		 var drawingName = sppath[sppath.length-1];
		 if(drawingName == null || drawingName == "" || drawingName == undefined){
			 return;
		 }
		 $('#drawing_name1').val(drawingName);			
		 $('#picture_index1').val($(obj).prev().val());
		 if(drawingName != null && drawingName != ''){
			 autTime();
		 }
		 
		 $("#file_upload_id1").ajaxSubmit({
			type: "post",
			url: "/supplier/saveTemplatePicture.do",
	     	dataType: "text",
	        success: function(str){
	        	var result	= eval("("+str+")");
	        	if(result.state == 0){
						var templateRemarkId = result.data.templateRemarkId;
						var picturePath = result.data.picturePath;
                     $(obj).next().attr("src",picturePath);
                     $('#template_remark_id1').val(templateRemarkId);
					}else{
						easyDialog.open({
				    		  container : {
				    		    header : 'Prompt message',
				    		    content : 'failed to upload'
				    		  },
				    		  overlay : false,
				    		  autoClose : 1000				    		  
				    		});		
			    	    $('#show_upload_dialog').hide();
					}   
	     	       }
			   
  	 });
		
	}
 
 
 
  //修改图片模板
  function edit_picture_temp(){	 	 

	 	 var templateRemarkId = $('#template_remark_id1').val();
	 	 if(!(templateRemarkId)){
	 			//没有上传图片
	 			easyDialog.open({
	 				  container : {
	 				    header : 'Prompt message',
	 				    content : '请上传图片'
	 				  },
	 	    		  overlay : false,
	 	    		  autoClose : 1000
	 				});
	 			return;
	 		}
	 	 
	 	  $.post("/supplier/saveTemplatePictures.do", 
	 			  { 
	 		        "templateRemarkId" : templateRemarkId
	 		      },
	 			   function(result){
	 		    	  if(result.state == 0){
	 		    		    $("#remark_table").find('tr').eq(remark_index-1).find('td').eq(1).html(result.data.remark); 
	 	    	            $('#div6').hide();
	 		    	  }else{
	 		    		  easyDialog.open({
	 						  container : {
	 						    header : 'Prompt message',
	 						    content : '修改失败 '
	 						  },
	 			    		  overlay : false,
	 			    		  autoClose : 1000
	 						}); 
	 		    	  }
	 		      })	  
  }
	
    
  
  /**
	*修改报价单
	*@author polo
	*@time 2017.5.25
	*/	
  function edit_quotation(quotationInfoId,obj,type){
	  
	 var projectSubject = $('#project_subject').val();
	 var quotationDate =  $('#quotation_date').val();
	 var currency = $('#currency').find("option:selected").text();
	 var incoTerm = $('#inco_term').val();
	 var quotationValidity = $('#quotation_validity').val();
	 var quoter = $('#quoter').text();
	 var email = $('#email').text();
	 var tel = $('#tel').val();
	 var tempRemark = $('#select_temp_remark').val();
	 if($('#select_temp_remark').css('display') == 'none'){
		 tempRemark == '';
	 }
	 var remarkImg = $('#select_temp_pic').html();
	 var imgNames = $('#img_names').val();
	 var customerId = $('#customer_id').val();
	 var processStatus = 0;
	 var weightStatus = 0;
	 if($('#weight_status').attr('checked') == 'checked'){
		 weightStatus = 1;
	 }
	 if($('#process_status').attr('checked') == 'checked'){
		 processStatus = 1;
	 }
	 
	 
	 //获得选择生成文件的类型
// 	 var type;
// 	 $('#w-div').find('.radio').find('span').each(function(){
// 		 if($(this).attr('class') == 'checked'){
// 			 type = $(this).find('input').val(); 
// 		 }
// 	 })
	 
	 //如果未选择模板，提示
	 if($('#processCode').val() == null || $('#processCode').val() == ''){
		 easyDialog.open({
			  container : {
			    header : 'Prompt message',
			    content : '请先选择模板'
			  },
		   		  overlay : false,
		   		  autoClose : 1000
			}); 	
		 return false;
	 }
	 
	 
	 
	 
	 
	 var productList = "";
	 var tl = $('#product_tbody').find('tr').length;
	 for(var i=0;i<tl;i++){
		 
		 var productId = $("#product_tbody tr:eq(" + i + ")").find('input:first').val();
		 var productName =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(1)").find('input').val();
		 var materialName =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(3)").find('input').val();
		 var weight =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('.weight').val();
		 var weight_unit =  $("#weight_unit").val();		 
		 var moldPrice =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(6)").find('span').text();
		 var moldProfitRate =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(7)").find('input').val();
		 var productNotes =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(10)").find('textarea').val();
		 
		 //工艺
		 var processes = '';
		 var processesIds = '';
		 $("#product_tbody tr:eq(" + i + ")").find('td:eq(5)').find('span').each(function(){
			  var processId = $(this).next().val();
			  var process = $(this).next().next().val();
			  if(!process){
				  easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '工艺不能为空'
					  },
				   		  overlay : false,
				   		  autoClose : 1000
					}); 	
				  return false;
			  }
			  processes += process+"&";
			  processesIds += processId+"&";
		 })
		  processes = (processes.substring(processes.length-1)=='&')?processes.substring(0,processes.length-1):processes;
		  processesIds = (processesIds.substring(processesIds.length-1)=='&')?processesIds.substring(0,processesIds.length-1):processesIds;
		 
		 var price_tl =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(7)").find('input').length;
		 var priceList = "";
		 var quantityList = "";
		 var totalProfitRateList = "";
		 var priceIdList = "";
		 var productProfitRate = $("#product_tbody tr:eq(" + i + ")").children("td:eq(8)").find("input:eq(0)").val();
		 for(var j=0;j<price_tl;j++){
			 var priceId = $("#product_tbody tr:eq(" + i + ")").children("td:eq(8)").find("input:eq("+(j+1)+")").val(); 
			 var quantity = $("#product_tbody tr:eq(" + i + ")").children("td:eq(7)").find("input:eq("+j+")").val(); 
			 var unit_price = $("#product_tbody tr:eq(" + i + ")").children("td:eq(8)").find("span:eq("+j+")").text(); 
			 var totalProfitRate = $("#product_tbody tr:eq(" + i + ")").children("td:eq(9)").find("span:eq("+j+")").text(); 
			 priceIdList += priceId + "&";
			 priceList += unit_price + "&";
			 quantityList += quantity + "&";
			 totalProfitRateList += totalProfitRate + "&";
		 }
		 priceIdList = (priceIdList.substring(priceIdList.length-1)=='&')?priceIdList.substring(0,priceIdList.length-1):priceIdList;
		 priceList = (priceList.substring(priceList.length-1)=='&')?priceList.substring(0,priceList.length-1):priceList;
		 quantityList = (quantityList.substring(quantityList.length-1)=='&')?quantityList.substring(0,quantityList.length-1):quantityList; 
		 totalProfitRateList = (totalProfitRateList.substring(totalProfitRateList.length-1)=='&')?totalProfitRateList.substring(0,totalProfitRateList.length-1):totalProfitRateList; 
		 
		 productList += "-" + productId + "%%" + "-" +productName + "%%" + "-" + materialName + "%%" + "-" + weight + "%%" + "-" + weight_unit + "%%" +
		 "-" +moldPrice + "%%" + "-" +moldProfitRate + "%%" + "-" +processes + "%%" + "-"+ processesIds  + "%%" + "-" +priceList + "%%" + "-" +quantityList + "%%" + 
		 "-" +totalProfitRateList + "%%" + "-" +priceIdList+ "%%" + "-" +productNotes + "%%" + "-" +productProfitRate+",,";
	 }
	 
	 $(obj).css({"color":"#bbb"}).attr("disabled","true");
	 
	 $.post("/supplier/modifyQuotation.do", 
			  { 
		        "quotationInfoId" : quotationInfoId,
		        "projectSubject" : projectSubject,
		        "quotationDate" : quotationDate,
		        "currency" : currency,
		        "incoTerm" : incoTerm,
		        "quotationValidity" : quotationValidity,
		        "quoter" : quoter,
		        "email" : email,
		        "tel" : tel,
		        "tempRemark" : tempRemark,
		        "processStatus" : processStatus,
		        "weightStatus":weightStatus,
		        "remarkImg"  : remarkImg,
		        "imgNames" : imgNames,
		        "productList" : productList,
		        "type": type,
		        "pdfForm": $('#downloadPdf').serialize()

		      },
			   function(result){
		    	  
		    	  $(obj).css({"color":"#666"}).removeAttr("disabled");
		    	  if(result.state == 0){
		    		  easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '保存成功 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						}); 	
		    		  
		    		  //下载报价单
		    		  view_quotation(quotationInfoId);
		    		  		    		 		    		  
		    		  $('#download_pdf').removeClass('disabled').addClass('btn-primary').attr('click','view_quotation('+quotationInfoId+')');
		    		  if(type == 'pdf'){
			    		  $('.z-btn').css({'color':'#666'}).attr('onclick','send_quotation_email('+quotationInfoId+')');  
		    		  }
//                    cancel_select();
// 		    		  setTimeout(function(){location.reload()},1000);
		    	  }else{
		    		  easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '保存失败 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						}); 
		    	  }
		      })	  

  }
  
  
  //跳转报价列表
  function quotation_list(){
	  window.location = "/supplier/queryAllQuotation.do"; 
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
			async:false,
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

   function update_price(obj){
   		   	  
	      
		    //判断当前页面是否有修改 
		    var result = false;                                     //初始化返回值                          
		    var colInput = $('#div11').find('input');               //获取输入框控件  		    
		    for (var i=0; i<colInput.length; i++){                   //逐个判断页面中的input控件    			    
		        if (colInput[i].value != colInput[i].defaultValue){ //判断输入的值是否等于初始值  
		            result = true;                                  //如果不相等，返回true，表示已经修改      			            
		        }     			 
		     }  
    	     if(result==false){
    	    	 return;
    	    }
    	     
    	   var productId = $('#div11').find('input:first').val(); 
    	   
    	     $.post("/supplier/updatePriceStatus.do", 
   	 			  { 
   	 		        "productId" : productId
   	 		      },
   	 			   function(result){
   	 		    	  if(result.state == 0){
   	 		    		 easyDialog.open({
  	 						  container : {
  	 						    header : 'Prompt message',
  	 						    content : '更新成功 '
  	 						  },
  	 			    		  overlay : false,
  	 			    		  autoClose : 1000
  	 						});  
   	 		    	  }else{
   	 		    		  easyDialog.open({
   	 						  container : {
   	 						    header : 'Prompt message',
   	 						    content : '更新失败 '
   	 						  },
   	 			    		  overlay : false,
   	 			    		  autoClose : 1000
   	 						}); 
   	 		    	  }
   	 		      })	     
    	     
   }
   
   
   
   
   /**
	*修改备注排序
	*@author polo
	*@time 2017.7.24
	*/	
   var select_index = 1;	
   function index_up(obj,index,remarkId){	   
	   if(index == 1){
		  return false;
	   }
	  var select_index = Number(index) - 1;
	  var remarkId1 = $(obj).parents('tr').prev().find('input:last').val();
	   
	   $.post("/supplier/updateIndex.do", 
	 			  { 
	 		        "remarkId" : remarkId,
	 		        "currentIndex" : select_index,
	 		        "index" : index,
	 		        "remarkId1" : remarkId1
	 		      },
	 			   function(result){
	 		        if(result.state == 0){	
	 		        $('#remark_table').empty();	
	 		        var remarks = result.data;
	 		        var tl = remarks.length;
	 		        for(var i=0;i<tl;i++){
	 		        	var img_div = '';
	 		        	if(i == 0 && i != tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}else if(i != 0 && i != tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}else if(i == tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-up.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}
	 		        	
	 		        	
	 			        var tr = 
		 		        	'<tr>'+
	                        '<td class="w-first-td">'+          
	                        '<div class="checker" id="uniform-undefined"><p>'+(i+1)+'</p><span><input name="checkbox" type="checkbox"/></span></div></td>'+
	                        ' <td style="word-break:break-all;">'+remarks[i].remark+'</td>'+
	                        ' <td>'+
	                        ' <div>'+
	                        ' <a href="#" onclick="open_edit_text_remark(this,'+remarks[i].textOrPicture+','+remarks[i].id+')">编辑</a><span style="display:none;">'+remarks[i].textOrPicture+'</span>'+
	                        ' <a href="#" onclick="delete_temp(this,'+remarks[i].id+')">删除</a>'+
	                        ' </div>'+ img_div +          
	                        ' <input type="hidden" value="'+remarks[i].id+'">'+
	                        ' </td>'+                                                                                                                 
	                    ' </tr>';
	                    $('#remark_table').append(tr);
	 		        }	
	 	
	 		        	
	 		        
 		    	  }else{
 		    		  
 		    	  }
	 		})	     
	   
   }      
   
   
   
   /**
	*修改备注排序
	*@author polo
	*@time 2017.7.24
	*/	
  var select_index = 1;	
  function index_down(obj,index,remarkId){	   

	  var select_index = Number(index) + 1;                                   //向下的序号
	  var remarkId1 = $(obj).parents('tr').next().find('input:last').val();   //向下tr的主键id
	   
	   $.post("/supplier/updateIndex.do", 
	 			  { 
	 		        "remarkId" : remarkId,
	 		        "currentIndex" : select_index,
	 		        "index" : index,
	 		        "remarkId1" : remarkId1
	 		      },
	 			   function(result){
	 		        if(result.state == 0){	
	 		        $('#remark_table').empty();	
	 		        var remarks = result.data;
	 		        var tl = remarks.length;
	 		        for(var i=0;i<tl;i++){
	 		        	var img_div = '';
	 		        	if(i == 0 && i != tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}else if(i != 0 && i != tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}else if(i == tl-1){
	 		        		img_div = '<div style="float:left;">'+
	                        ' <img src="img/arrows-up.png"  class="z-img-arrow" style="width:20px;" onclick="index_up(this,'+(i+1)+','+remarks[i].id+')">'+
	                        ' </div>';
	 		        	}
	 		        	
	 		        	
	 			        var tr = 
		 		        	'<tr>'+
	                        '<td class="w-first-td">'+          
	                        '<div class="checker" id="uniform-undefined"><p>'+(i+1)+'</p><span><input name="checkbox" type="checkbox"/></span></div></td>'+
	                        ' <td style="word-break:break-all;">'+remarks[i].remark+'</td>'+
	                        ' <td>'+
	                        ' <div>'+
	                        ' <a href="#" onclick="open_edit_text_remark(this,'+remarks[i].textOrPicture+','+remarks[i].id+')">编辑</a><span style="display:none;">'+remarks[i].textOrPicture+'</span>'+
	                        ' <a href="#" onclick="delete_temp(this,'+remarks[i].id+')">删除</a>'+
	                        ' </div>'+ img_div +          
	                        ' <input type="hidden" value="'+remarks[i].id+'">'+
	                        ' </td>'+                                                                                                                 
	                    ' </tr>';
	                    $('#remark_table').append(tr);
	 		        }	
	 	
	 		        	
	 		        
		    	  }else{
		    		  
		    	  }
	 		})	     
	   
  }      
   
   
   
  //计算产品单价  (利润率改变)
//   function calUnitPrice(obj,priceId){
	  
// 	  var quantity = 1;
// 	  var index = $(obj).parent().find('input').index(obj); 
// 	  if(!isNaN(index)){
// 		  quantity = $(obj).parents('tr').find('td:eq(7)').find('input').eq(index).val();  
// 	  }
	  
// 	  $.post("/supplier/queryQuotationUnitPrice.do", 
// 			  { 
// 		        "priceId" : priceId,
// 		        "moldPrice" : $(obj).parents('tr').find('td:eq(5)').find('span').text(),
// 		        "totalProfitRate" : $(obj).val(),
// 		        "quantity" : quantity
// 		      },
// 			   function(result){
// 		    	 if(result.state == 0){
// 		    		 $(obj).parents('tr').find('td:eq(8)').find('span').eq(index).text(result.data); 
// 		    	 }    	  
// 		    })
//   }
  
  
  //计算产品单价  (利润率改变)
  function calUnitPrice(obj,productId){
	  
	  var quantity = 1;
	  var index = $(obj).parent().find('input').index(obj); 
	  if(!isNaN(index)){
		  quantity = $(obj).parents('tr').find('td:eq(7)').find('input').eq(index).val();  
	  }
	  
	  $.post("/supplier/queryQuotationUnitPrice.do", 
			  { 
		        "productId" : productId,
		        "moldPrice" : $(obj).parents('tr').find('td:eq(6)').find('span').text(),
		        "productProfitRate" : $(obj).val(),
		        "quantity" : quantity
		      },
			   function(result){
		    	 if(result.state == 0){
		    		 var totalRate = result.data.totalRate;
		    		 var unitPrice = result.data.unitPrice;
		    		 
		    		 $(obj).parents('tr').find('td:eq(9)').find('span').eq(index).text(totalRate); 
		    		 $(obj).parents('tr').find('td:eq(8)').find('span').eq(index).text(unitPrice);
		    	 }    	  
		    })
  } 
  
  
  
  
  //计算模具价格
  function calculateMoldPrice(obj,productId){
	  $.post("/supplier/queryMoldPrice.do", 
			  { 
		        "productId" : productId,
		        "moldProfitRate" : $(obj).val() 
		      },
			   function(result){
		    	 if(result.state == 0){
		    		 var productFactoryPrice = result.data.productFactoryPrice;
		    		 var moldFactoryPrice = result.data.moldFactoryPrice;
		    		 var moldPrice = result.data.moldPrice;
		    		 
		    		 $(obj).parent().find('span:last').text(moldPrice);
		    		 var originalTotalPrice = 0.0;
		    		 var nowTotalPrice = 0.0;
		    		 var exchangeRate = $('#current_exchangeRate').val();
		    		 $(obj).parent().next().find('input').each(function(i){
		    			 var quantity = $(this).val();
		    			 var productPrice = $(obj).parent().next().next().find('span:eq('+i+')').text();
		    			 originalTotalPrice = productFactoryPrice * quantity + moldFactoryPrice;
// 		    			 originalTotalPrice = originalTotalPrice * exchangeRate;
		    			 nowTotalPrice = productPrice*quantity + moldPrice;
		    			 var totalProfitChangeRate = (nowTotalPrice - originalTotalPrice)/originalTotalPrice;
		    			 totalProfitChangeRate = Number(totalProfitChangeRate*100).toFixed(0);
		    			 $(obj).parent().next().next().next().find('span:eq('+i+')').text(totalProfitChangeRate);
		    		 })
		    	 }    	  
		    })
  }

  
  //当数量变化时改变总利润
  function calculateTotalProfit(obj){
	  
	  var qty = $(obj).val();
	  var factoryMoldPrice = $(obj).parent().prev().find('label').text();
	  var moldProfit = $(obj).parent().prev().find('input:first').val();
	  var factoryPrice = $(obj).parent().next().find('label').text();
	  var productProfit = $(obj).parent().next().find('input:first').val();
	  
	  var moldAddPrice = factoryMoldPrice*moldProfit/100;
	  var productAddPrice = factoryPrice*productProfit*qty/100;
	  var factoryTotalPrice = Number(factoryMoldPrice) + Number(factoryPrice*qty);
	  var totalProfit = (Number(moldAddPrice) + Number(productAddPrice))/factoryTotalPrice;
	  totalProfit = Number(totalProfit*100).toFixed(0);
	  
	  $(obj).parent().next().next().find('span').text(totalProfit);
  }
  
  
  

  //显示价格详情
  function show_price_detalis(priceId,productId){
	  
	  $.post("/supplier/queryPriceDetails.do", 
 			  { 
 		        "priceId" : priceId,
 		        "productId" : productId
 		      },
 			   function(result){
 		    	  if(result.state == 0){
 	 		    	  var productionBean = result.data.productionBean;
 	 		    	  var processList = result.data.processList;
 	 		    	  var priceBean = result.data.priceBean;
 	 		    	  var tl = processList.length;
 	 		    	  $('#price_ul').empty(); 
 	 		    	  var process = '';
 	 		    	  for(var i=0;i<tl;i++){
 	 		    		  process +='<li><span>工艺'+(i+1)+':'+processList[i].processName+'</span></li>';
 	 		    	  }
 	 		    	  $('#price_ul').append('<li><span>材料重量:'+priceBean.materialWeight+'KG</span></li>'+process);
 	 		    	  
 	 		    	  $('#price_div').show();
 		    	  }else{
 		    		  easyDialog.open({
 						  container : {
 						    header : 'Prompt message',
 						    content : '查询失败 '
 						  },
 			    		  overlay : false,
 			    		  autoClose : 1000
 						}); 
 		    	  }

 		      })
  }
 
  //关闭价格详情
  function close_price_div(){
	  $('#price_div').hide();
  }
   
 //显示重量、工艺 onchange(type 1、重量 2、工艺)
 function view_status_update(type,quotationInfoId,obj){
	 var status = 0;
	 if($(obj).is(':checked')){
		 status = 1;
	 }
	 
	 $.post("/supplier/updateViewStatus.do", 
			  { 
		        "type" : type,
		        "status" : status,
		        "quotationInfoId" :quotationInfoId
		      },
			   function(result){
		      })	 
 }
 
 //重量选择事件
 function select_unit(obj){    
	 var unit = $(obj).val();	 
	 var currentUnit = $('#weight_status').next().text();
	 if(unit == currentUnit){
		 return false;		 
	 }else{
		 $('#weight_status').next().text(unit); 
	 }
	 if(unit == 'kg'){
		 $('#product_tbody').find('tr').each(function(){
			 var weight = $(this).find('td').eq(4).find('.weight').val();
			 $(this).find('td').eq(4).find('.weight').val(Number(weight/1000).toFixed(5));
		 })
	 }
	 if(unit == 'g'){
		 $('#product_tbody').find('tr').each(function(){
			 var weight = $(this).find('td').eq(4).find('.weight').val();
			 $(this).find('td').eq(4).find('.weight').val((weight*1000).toFixed(5));
		 })
	 }
 }
 
 
 //删除工艺
 function deleteProcess(processId,obj){
	 
	 $.post("/supplier/deleteProcess.do", 
			  { 
		        "processId" :processId
		      },
			   function(result){
		    	  if(result.state == 0){
		    		  $(obj).next().remove();		    		 
		    		  $(obj).prev().prev().prev().remove();
		    		  $(obj).prev().prev().remove();
		    		  $(obj).prev().remove();		    		  		    		  
		    		  //重新计算index
		    		  $(obj).parents('td').find('span').each(function(i){
                          $(this).text((i+1)+"、");
		    		  })
		    		  $(obj).remove();
		    	  }
		      })
 }
 
 
 //删除报价产品
 function remove_price(obj){		
	 $(obj).parents('.price-div').remove();	
	 quoted_edit_index=quoted_edit_index-1;
	 $('.price-div').each(function(i){
		 var id = "quoted_edit_"+i;
	     $(this).attr("id",id);
	 })
 }
 
    //发送报价邮件
	function send_quotation_email(quotationInfoId){
		
		 $.post("/supplier/sendQuotationEmail.do", 
				  { 
			        "quotationInfoId" : quotationInfoId
			      },
				   function(result){			    	  
			    	  if(result.state == 0){
			    		  window.open("http://112.64.174.34:43887/NBEmail/helpServlet?action=sendQuotation&className=ExternalInterfaceServlet&eid="+result.data.userId+"&projectId="+result.data.projectId+"&content="+result.data.quotationAttr+"&pdfPath="+result.data.pdfPath); 
			    	      
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
 
	//预览下载报价单
// 	function view_quotation(quotationInfoId){
// 		//使用ajax提交下载
// 		$.ajax({
// 			url:"/supplier/fileDownload_quotation.do",
// 			data:{
// 				  "quotationInfoId":quotationInfoId
// 				  },
// 			type:"post",
// 			dataType:"text",
// 			success:function(res){						
// 				window.location.href = "/supplier/fileDownload_quotation.do?quotationInfoId="+quotationInfoId;				
// 				},
// 		    error:function(){
		    	
// 		    	easyDialog.open({
// 		    		  container : {
// 		    		    header : 'Prompt message',
// 		    		    content : '下载失败'
// 		    		  },
// 		    		  overlay : false,
// 		    		  autoClose : 1000
// 		    		});
// 		    }
// 		});
// 	}
	
	//选择excel、pdf弹框开启关闭
	function choice_type(){
		$('#w-div').show();
	}
	function cancel_select(){
		$('#w-div').hide();
	}
	
	function changeRadio(obj){
		$(obj).siblings().find('.radio').find('span').removeClass("checked");
		$(obj).find('.radio').find('span').addClass("checked");
	}
	
    
	 //上传pdf
	function upload_pdf(obj,quotationInfoId){
			var path = $(obj).val();
		    sppath = path.split("\\");
		    var drawingName = sppath[sppath.length-1];	  	   
		    if(drawingName == null || drawingName == '' || drawingName == undefined){
		    	return false;
		    }else{
		    	
      	        var gen = drawingName.lastIndexOf("."); 
      	        var type = drawingName.substr(gen); 
      	        if(type.toLowerCase() != ".pdf"){
      	        	
      	        	$('#send_self').css({'color':'#666'}).attr('onclick','send_quotation_email('+quotationInfoId+')');
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
		     		 setTimeout(function(){location.reload()},500);
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
<script type="text/javascript">
$(function(){
	
	 
	 $("body").click(function(e){
		   
		  
		   if(e.target.id.indexOf("chooseProcess") == -1){
			    $('.xl').hide();
				$('.xl .xl_l').hide();
				$('#chooseProcess').css({'border-bottom-color':'#ccc'});
		   }
		   })
	 
	 
	$('#chooseProcess').click(function(){
		$('.xl').show();
		$('.xl .xl_l').show();
		$(this).css({'border-bottom-color':'transparent'});
	})
	$('.span_1').click(function(){
		$('.xl').show();
		$('.xl .xl_l').show();
		$(this).next().css({'border-bottom-color':'transparent'});
	})
	
	$('.info li').hover(function(){
		$(this)
		.css({'background-color':'#1e90ff'});
// 		.find('span')
		//.css({'color':'#fff'});
		
		$(this).find('em').css({'border-color':'transparent transparent transparent #fff'});
		$(this).siblings('li')
		.css({'background-color':'#fff'});
// 		.find('span')
			//.css({'color':'#666'});
		$(this).siblings('li').find('em').css({'border-color':'transparent transparent transparent #666'});
		$(this).find('ul').show();
// 		$('.xl .xl_r').find('span')
		//.css({'color':'#666'});
	},function(){
		$(this).find('ul').hide();
	})

// 	$('.xl .xl_r li').hover(function(){
// 		$(this).find('span')
		//.css({'color':'#fff'});
// 	})

	$('.info_title .xl .xl_l').mouseleave(function() {
		
		$(this).hide();
		$('.xl').hide();
		$('#chooseProcess').css({'border-bottom-color':'#ccc'});
	});
	
	$('.info li').click(function(){
		$('#chooseProcess').val('').next().text('').removeClass('ff4')
		
		$('#injextate').hide()
		$('#press').hide()		
		
		if(!$(this).children('span').attr('datacode')){
			$('#chooseProcess').next().text('请选择二级菜单模板').addClass('ff4')
			
		}else{
			var datacode = $(this).children('span').attr('datacode')
			$('#chooseProcess').val($(this).children('span').text())
			$('#processCode').val(datacode)
			
			if(datacode==8){
				$('#injextate').show()
// 				$('#injextate').find('input[value=0]').attr('checked',true)
			}
			if(datacode==13){
				$('#press').show()
// 				$('#press').find('input[value=0]').attr('checked',true)
				
			}
			
			
		}
		return false;
		
		
	})
	
	
})

// 编辑文字信息切换
$('.w-textarea-span11,.t_j').hide();
function change_textarea(){
	if($('#select_temp_remark').css('display') == 'none'){
		$('.w-textarea-span11,.t_j').show();
	}else{
		$('.w-textarea-span11,.t_j').hide();
	}

}

function del_z(obj){
	var imgNames = $('#img_names').val();
    var tl = $(obj).parent('.del-z').find('img').length;
    var path;
    var imgName;
    $(obj).parent('.del-z').find('img').each(function(i){
    	 path = $(this).attr("src"); 
    	 if(i == tl-1){ 		 
    		 imgName = path.substring(path.lastIndexOf("/")+1,path.length);
    	 }else{
    		 imgName = path.substring(path.lastIndexOf("/")+1,path.length)+",";
    	 }
		 imgNames = imgNames.replace(imgName,'');
    })
    $('#img_names').val(imgNames);
	$(obj).parent('.del-z').next().remove();
	$(obj).parent('.del-z').remove();	
}
</script>

</html>
