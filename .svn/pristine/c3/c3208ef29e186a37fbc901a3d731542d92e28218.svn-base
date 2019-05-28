<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.cbt.util.WebCookie"%>	
<%@page import="java.util.List"%>
<%@page import="com.cbt.entity.UpdateDrawing"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
<link rel="stylesheet" href="css/news.css" />
  <link type='image/x-ico' rel='icon' href='img/favicon.ico' />
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript">

		$(function(){			
			
			var index = $('.recent-posts').find('li:last').find('a').prev().text();
			if(index == "" || index == null){
				$('#show_no_message').show();
			}else{
				$('#show_no_message').hide();
			}
		})
		
		//显示消息详情
		function show_details(messageCenterId,obj){
			if($(obj).parent().next().css('display') == 'none'){
				$(obj).parent().next().show();
			}else{
				$(obj).parent().next().hide();
				return false;
			}
			
			$.post("/supplier/queryMessageDetails.do", 
					{ "messageCenterId": messageCenterId},
					function(result){						
				     if(result.state == 0){	
				    	 $('#unread_message_count').text(result.data);
				    	 $(obj).find("span:last").hide();
				    	 $(obj).find('p').css({"color":"#b7b3b3"});
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
				  });	
		}
		
		
		
		//打开工厂回复弹框
		function reply_message(obj){
			if($(obj).parents("div").find('.chat-messages').next().css('display') == 'none'){
				$(obj).parents("div").find('.chat-messages').next().show();
				$(obj).parents("div").find('.chat-messages').next().find('input').focus();		
			}else{
				$(obj).parents("div").find('.chat-messages').next().hide();
			}
	
		}
		
	   //保存回复消息	
       function send_message(messageCenterId,obj){
    	   var message = $(obj).next().find("input").val();		
    	   if(message == null || message == "" || message == "undefined"){
    		   easyDialog.open({
					  container : {
					    header : 'Prompt message',
					    content : '消息不能为空 '
					  },
		    		  overlay : false,
		    		  autoClose : 1000
					});
    		   return false;
    	   }
			$.post("saveFactoryMessage.do", 
					{
				     "messageCenterId": messageCenterId,
				     "messageDetails" : message
					},
					function(result){
				     if(result.state == 0){
				    	$(obj).next().find("input").val('');
				    	var orderMessage = result.data;
                  	    var factory_div =   '<div class="w-text-div2">'+
                                            ' <div class="w-bg article-post">'+
                                             '  <span class="w-user-info"> Reply: ['+orderMessage.username+'] / Date:'+(orderMessage.messageSendTime)+'</span>'+
                                              ' <p class="w-user-p"><a href="#">'+orderMessage.messageDetails+'</a></p>'+
                                             '</div>'+
                                           '</div>'; 
                  	   $(obj).parent().prev().find("div.chat-messages-inner").append(factory_div);
                  	   $(obj).parent().hide(); 
                  	   
				     }else{
				    	 easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '发送失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
				  }
		     });		
	   }
		
		
</script>
<style type="text/css">
/*    .z-a-message:hover{ */
/*     text-decoration: none; */
/*     color: #28b779; */
/*    } */
</style>

</head>
<body>
<%-- <jsp:include page="base.jsp"></jsp:include> --%>

<div id="content" style="margin-left:0;">
  <div id="content-header">
    <div id="breadcrumb"> <a href=""  class="tip-bottom"><i class="icon  icon-paste"></i> Message center</a>
     </div>
      <h1 class="bread-h1">全部消息</h1>
  </div>
    <div class="container-fluid">
        <hr>
        <div class="row-fluid">
            <div class="span12">
                <div class="accordion">
                   <c:forEach var="obj" items="${messageCenters}" varStatus="i">
                    <div class="accordion-group widget-box">
                        <div class="accordion-heading">
                            <div class="widget-title" onclick="show_details('${obj.id}',this)"><a data-parent="" href="#" data-toggle=""> <span class="icon"><i class="icon-comments"></i></span>
                                <c:if test="${obj.messageType == 4}">  
                                   <c:if test="${counts[i.count-1] == 0}">
                                    <p style="color:#b7b3b3;">[${obj.userid}]发送报价单[${obj.quotationId}]一条${obj.messageTitle}</p>
                                   </c:if>
                                   <c:if test="${counts[i.count-1] != 0}">
                                     <p>[${obj.userid}]发送报价单[${obj.quotationId}]一条${obj.messageTitle}</p>
                                   </c:if>
                                </c:if>
                                <c:if test="${obj.messageType != 4}"> 
                                  <c:if test="${counts[i.count-1] == 0}">
                                   <p style="color:#b7b3b3;">[${obj.userid}]发送订单[${obj.orderId}]一条 ${obj.messageTitle} 消息</p>
                                   </c:if>
                                  <c:if test="${counts[i.count-1] != 0}">
                                   <p>[${obj.userid}]发送订单[${obj.orderId}]一条 ${obj.messageTitle} 消息</p>
                                   </c:if>
                                </c:if>
                                <span class="label label-important">${counts[i.count-1] == 0 ? "" : counts[i.count-1]}</span>
                                <c:if test="${counts[i.count-1] == 0}">
                                <p style="color:#b7b3b3;" class="fr w-widget-p">${obj.maxSendTime != null ?fn:substring(obj.maxSendTime,0,fn:indexOf(obj.maxSendTime,".")):""}</p>
                                 </c:if>
                                <c:if test="${counts[i.count-1] != 0}">
                                <p class="fr w-widget-p">${obj.maxSendTime != null ?fn:substring(obj.maxSendTime,0,fn:indexOf(obj.maxSendTime,".")):""}</p>
                                 </c:if>
                            </a> </div>
                        </div>
                        <div class="collapse in accordion-body" style="display:none;">
                            <div class="chat-content">
                                <div class="chat-messages">
                                    <div class="chat-messages-inner">
                                        <c:forEach begin="1" end="${orderMessages.get(i.count-1).size()}" varStatus="j" step="1">   
                                        <c:if test="${orderMessages.get(i.count-1).get(j.count-1).customerOrFactory == 1 && obj.messageType != 4}"> 
                                        <div class="w-text-div">
                                            <div class="article-post msg-block">
                                                <div class="fr"><a href="#" class="btn btn-primary btn-mini" onclick="reply_message(this)">Reply</a></div>
                                                <span class="w-user-info"> By: [${obj.userid}] / Date: ${orderMessages.get(i.count-1).get(j.count-1).messageSendTime != null ?fn:substring(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,0,fn:indexOf(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,".")):""} </span>
                                                <p style="margin: 0 0 10px;"><a href="#">${orderMessages.get(i.count-1).get(j.count-1).messageDetails}</a> </p>
                                                <c:if test="${orderMessages.get(i.count-1).get(j.count-1).picStatus == 1}"> 
                                                   <c:forEach begin="1" end="${orderMessages.get(i.count-1).get(j.count-1).qualityIssuesPic.size()}" varStatus="k" step="1">  
                                                   <img src="${orderMessages.get(i.count-1).get(j.count-1).qualityIssuesPic.get(k.count-1).picturePathCompress}" style="width: 80px;">
                                                   </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                        </c:if>
                                        <c:if test="${orderMessages.get(i.count-1).get(j.count-1).customerOrFactory == 2 && obj.messageType != 4}"> 
                                        <div class="w-text-div2">
                                            <div class="w-bg article-post">
                                                 <div class="fr"><a href="#" class="btn btn-primary btn-mini">Reply</a></div>
                                                <span class="w-user-info"> Reply: [${orderMessages.get(i.count-1).get(j.count-1).username}] / Date: ${orderMessages.get(i.count-1).get(j.count-1).messageSendTime != null ?fn:substring(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,0,fn:indexOf(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,".")):""} </span>
                                                <p class="w-user-p"><a href="#">${orderMessages.get(i.count-1).get(j.count-1).messageDetails}</a> </p>
                                            </div>
                                        </div>
                                        </c:if>                                     
                                        </c:forEach> 
                                       <c:if test="${obj.messageType == 4}"> 
                                        <div class="w-text-div2">
                                            <div class="article-post msg-block">
                                                 <a href="http://192.168.1.54:8080/supplier/queryQuotationMessageById.do?quotationInfoId=${obj.quotationInfoId}" style="text-decoration:underline;">http://192.168.1.54:8080/supplier/queryQuotationMessageById.do?quotationInfoId=${obj.quotationInfoId}</a>
                                            </div>
                                        </div>
                                        </c:if> 
                                    </div>
                                </div>
                                <div class="chat-message well" style="display:none;">
                                    <button class="btn btn-success" onclick="send_message('${obj.id}',this)">Send</button>
                                    <span class="input-box">
                                        <input type="text" name="msg-box" >
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                    </c:forEach>
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

<div class="row-fluid">
  <div id="footer" class="span12">

   </div>
</div>

</body>
</html>


