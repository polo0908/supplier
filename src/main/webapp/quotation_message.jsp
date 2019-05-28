<%@page import="java.text.SimpleDateFormat"%>
<%@page import="org.springframework.format.datetime.DateFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>     
<%@page import="com.cbt.util.WebCookie"%>
<%@page import="java.util.Calendar" %>
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
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />


<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script type="text/javascript">
	
	
 	$(function(){
 		
 		
 		
//  		 $('#sidebar').find('li').eq(2).addClass('active');

 	})
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
  
	
	
</script>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>

<div id="content">

     <div class="container-fluid">
        <div class="w-font">
            <h2 class="w-font-h2">Message Board</h2>
        </div>
        <div class="w-hr2">
            <hr>
        </div>
        <div class="row-fluid">
            	<div class="span12">
                	<textarea class="span11 w-textarea-span11"  id="message_input"></textarea>
                </div>
        </div>
            <div class="w-button">
            <button class="btn btn-success" style="float: right;" onclick="send_message('${quotationBean.id}','0','${quotationBean.factoryId}','${quotationBean.userId}')">发送</button>
        </div>
    </div>
    <div class="container-fluid">
        <hr>
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-content nopadding">
                        <ul class="recent-posts">
                           <c:forEach var="obj" items="${messages}" varStatus="i">
                           <c:if test="${obj.targetPriceReply == 2 && obj.customerOrFactory == 1}">
                            <li>
                            	<div class="article-post">
                                    <div class="fr">
                                        <span class="w-user-info">${obj.messageSendTime != null ?fn:substring(obj.messageSendTime,0,fn:indexOf(obj.messageSendTime,".")):""}</span>
                                    </div>
                                    <span class="user-info">${obj.loginEmail}</span>
                                    <p>
                                        ${obj.messageDetails}
                                    </p>
                                </div>
                                <div class="w-button9">
<%--                                 	<button class="btn btn-primary" onclick="download_attachment('${obj.id}')">Attachment</button> --%>
                                   <a href="#" style="float:right;color:#08c;text-decoration: underline;">${obj.attachmentPath}</a><p style="float:right;">下载：</p>
                                </div> 
                                
                            </li>
                            </c:if>
                           <c:if test="${obj.targetPriceReply == 2 && obj.customerOrFactory != 1}">
                            <li>
                            	<div class="article-post">
                                    <div class="fr">
                                        <span class="w-user-info">${obj.messageSendTime != null ?fn:substring(obj.messageSendTime,0,fn:indexOf(obj.messageSendTime,".")):""}</span>
                                    </div>
                                    <span class="user-info">Reply:</span>
                                    <p>
                                        ${obj.messageDetails}
                                    </p>
                                </div>
                                <div class="w-button9">
<%--                                 	<button class="btn btn-primary" onclick="download_attachment('${obj.id}')">Attachment</button> --%>
                                	<a href="#" style="float:right;color:#08c;text-decoration: underline;">${obj.attachmentPath}</a><p style="float:right;">下载：</p>
                                </div> 
                                
                            </li>
                            </c:if>
                           <c:if test="${obj.targetPriceReply != 2 && obj.customerOrFactory != 1}">
                            <li>
                                <div class="article-post">
                                    <div class="fr">
                                        <span class="w-user-info">${obj.messageSendTime != null ?fn:substring(obj.messageSendTime,0,fn:indexOf(obj.messageSendTime,".")):""}</span>
                                    </div>
                                    <span class="user-info">Reply:</span>
                                    <p>
                                        ${obj.messageDetails}
                                    </p>
                                </div>
                            </li>
                            </c:if>
                           <c:if test="${obj.targetPriceReply != 2 && obj.customerOrFactory == 1}">
                            <li>
                                <div class="article-post">
                                    <div class="fr">
                                        <span class="w-user-info">${obj.messageSendTime != null ?fn:substring(obj.messageSendTime,0,fn:indexOf(obj.messageSendTime,".")):""}</span>
                                    </div>
                                    <span class="user-info">${obj.loginEmail}</span>
                                    <p>
                                        ${obj.messageDetails}
                                    </p>
                                </div>
                            </li>
                            </c:if>
                            </c:forEach>
                        </ul>
                    </div>
            </div>
        </div>
    </div>
<!-- <<<<<<<<<<<<添加、编辑产品结束>>>>>>>>>>>>>> -->

</div>

<div class="row-fluid">
<!--   <div id="footer" class="span12"> 2013 &copy; Shanghai ce melting. More Templates <a href="#" target="_blank" title="">上海策融</a> </div> -->
</div>

</body>

</html>
