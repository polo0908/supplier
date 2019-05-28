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
<link rel="stylesheet" href="/supplier/css/bootstrap-responsive.min.css" />
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="/supplier/css/matrix-style.css" />
<link rel="stylesheet" href="/supplier/css/matrix-media.css" />
 <link rel="stylesheet" href="/supplier/css/select2.css" />
<link href="/supplier/font-awesome/css/font-awesome.css" rel="stylesheet" />
  <link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<!--<link rel="stylesheet" href="css/jquery.gritter.css" />-->
<style>
    .shuruming{
        margin-top: 20px;
         float: none;
        text-shadow: 0 1px 0 #ffffff;
        margin-left: 20px;
        position: relative;
    }
.shuru  input{width: 15%;}
</style>


<script type="text/javascript">
  
  //返回新图纸询盘
  function backRfqInfo(){
	 window.location = "/supplier/queryAllRfqInfo.do"; 	  
  }
  
//订单详情页加载时获取订单是否处理，如已跟进则不能点击
  $(function(){
	  
		
	  $('#sidebar').find('li').eq(2).addClass('active');		
	  
	 var drawingState = $('#drawingState').text();	 
  	 if(drawingState == "已处理"){
   		 
  		$('#followUpButton').attr("disabled", true).css({background:"#888"});
  	 }    	 	
  });   
//跟进弹框
  function showFollowUp(){
  	$('#dialog2').show().on('click', '.weui_btn_dialog', function () {
          $('#dialog2').off('click').hide();
      });  	
  }
  
    //处理跟进事件
	//当未处理时点击当前登录用户成跟进人
	var user = userName ;
	function dispose(id){   
  	 var drawingState = $('#drawingState'+id).text();
  	 var followUp = $('#followUp'+id).text();
  	 var description = $('#description').val();
   	    $.post("/supplier/updateRfqById.do", 
  		{ "id": id,
  		  "description" : description
  		},
  	   function(data){   	    			
  		 
  			 if(!(drawingState == "已处理")){
   	    		 $('#drawingState').text("已处理");	    		 
   	    		 $('#followUp').text(user);
   	    		 $('#followUpButton').attr("disabled", true).css({background:"#888"});   		 	 		    		 
   	    	 }	
  	  });	  
	}
  
   function disclose(){
      $('#dialog2').hide();
   }
</script>
</head>
<body>

<jsp:include page="base.jsp"></jsp:include>

<!--main-container-part-->
<div id="content">
  <div id="content-header">
      <div id="breadcrumb"> <a href="/supplier/queryAllRfqInfo.do"  class="tip-bottom"><i class="icon icon-picture"></i>客户询盘查询</a> <a href="#" class="current">详情</a>
      </div>

<!--<h1 style="font-size: 26px;">添加/编辑用户</h1>-->
  </div>
    <div class="container-fluid"><hr>
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">
                    <div class="widget-title"> <span class="icon"> <i class="icon-edit"></i> </span>
                        <h5>详情</h5>
                    </div>                   
                    <div class="widget-content nopadding">
                        <form id="form-wizard" class="form-horizontal ui-formwizard" method="post" novalidate="novalidate">
                            <div id="form-wizard-1" class="step ui-formwizard-content" style="display: block;">
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">客户ID & 客户名称：</label>
                                    <div class="houzi">${rfqInfo.userid} : ${rfqInfo.userName}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">询盘时间：</label>
                                    <div class="houzi">${rfqInfo.createTime}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">产品名称：</label>
                                    <div class="houzi">${rfqInfo.productName}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">图纸：</label>
                                    <div class="houzi">${rfqInfo.drawingName}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">状态：</label>
                                    <div class="houzi" id="drawingState">${rfqInfo.drawingState}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">跟进销售：</label>
                                    <div class="houzi" id="followUp">${rfqInfo.followUp}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">交期时间：</label>
                                    <div class="houzi">${rfqInfo.requiredTime}</div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">需求说明：</label>
                                    <div class="houzi" style="padding-right: 20px;">
                                    ${rfqInfo.comment}
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label" style="padding-left: 80px;text-align: left;">跟进记录：</label>
                                    <div class="houzi houzi-last">
                                        <div class="genjintu"><img src="img/tu.png"></div>
                                        <div class="xiafangzi">
                                            <p>${rfqInfo.followTime}</p>
                                            <p>${rfqInfo.followComment}</p>
                                        </div>
                                    </div>
                                </div>

                            </div>
                            <div class="form-actions">
                                <button type="button" class="btn btn-primary" onclick="backRfqInfo();">返回</button>
                                <button type="button" class="btn btn-success" id="followUpButton" onclick="showFollowUp()">跟进</button>
                            </div>
                            <div id="submitted"></div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--end-main-container-part-->

<!--Footer-part-->
<div class="weui_dialog_alert" id="dialog2" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">跟进处理</strong></div>

        <textarea class="weui_textarea" placeholder="备注" rows="4" id="description" name="description"></textarea>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft">
            <input id="followId" value="" type="hidden">
            <a href="javascript:;" class="weui_btn_dialog primary" onclick="dispose('${rfqInfo.id}')">跟进</a>
        </div>
    </div>
</div>
<div class="row-fluid">
  <div id="footer" class="span12">
   </div>
</div>

</body>
</html>
