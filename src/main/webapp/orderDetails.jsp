<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@page import="com.cbt.util.WebCookie"%>
<%@page import="com.cbt.util.DateFormat"%>
<%String[] userinfo = WebCookie.getUser(request);%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
 <link rel="stylesheet" href="css/select2.css" />
 <link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/uniform.css" />
<link rel="stylesheet" href="css/orderDetails.css" />
<link rel="stylesheet" href="css/newOrder.css" />
<link rel="stylesheet" href="css/ui.css">
 <link rel="stylesheet" href="css/ui.progress-bar.css">
 <link rel="stylesheet" href="css/upload-base.css">
 <link rel="stylesheet" href="css/news.css" />

<!--<link rel="stylesheet" href="css/jquery.gritter.css" />-->
    <script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/upload-base.js"></script>
    <script type="text/javascript" src="js/number.js"></script>
    <script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/supplier/js/base64.js"></script>
 	<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript">
 	
    
        $(function(){
        	
        	 $('#sidebar').find('li').eq(0).addClass('active');	
        	
            //禁止enter事件
//             document.onkeydown = function () {
//                 if (window.event && window.event.keyCode == 13) {
//                     window.event.returnValue = false;
//                 }
//                }
        	
            var expectedOrActually = 0;
        	
            $('.cancelButton button').on('click',function () {
                $('#dialog2').show().on('click', '.weui_btn_dialog', function () {
                    $('#dialog2').off('click').hide();
                });

            });
                         	
                      
            
            //取消编辑
            $('.z-btn-cancel').on('click',function () {
            	var orderId = $('#orderId').text();
            	var userid = $('#userid').val();
                var base = new Base64();
            	orderId=base.encode(orderId);
        		userid=base.encode(userid); 
            	window.location.href = "${ctx}/querDetails.do?orderId="+orderId+"&userid="+userid;

            });
            
            $('.w-cancelButton button').on('click',function () {
                $('#dialog6').show().on('click', '.w-weui_btn_dialog', function () {
                    $('#dialog6').off('click').hide();
                });

            });
            
            
           //继续操作
            $('#back_button').on('click',function(){           	
            	var orderId = $('#orderId').text();
            	var userid = $('#userid').val();
            	var base = new Base64();
             	orderId=base.encode(orderId);
         		userid=base.encode(userid); 
            	window.location.href = "${ctx}/querDetails.do?orderId="+orderId+"&userid="+userid; 
            });
                 
           
            //处理PO、QC、ShippingDoc日期显示
//             var poPath = $('#poPath').val();
//             var qcPath = $('#qcPath').val();
//             var shippingPath = $('#shippingPath').val();
//             if(poPath == null || poPath == ''){
//             	$('#poPath').parent().next().hide().next().hide();
//             }
//             if(qcPath == null || qcPath == ''){
//             	$('#qcPath').parent().next().hide().next().hide();
//             }
//             if(shippingPath == null || shippingPath == ''){
//             	$('#shippingPath').parent().next().hide().next().hide();
//             }
           
           
             //提交修改数据
             $('.z-btn-save').on('click',function () {
            	var projectName = $('#project_name').val();
            	if(projectName == null || projectName == '' || projectName == undefined){
            		easyDialog.open({
	       				  container : {
	       				    header : 'Prompt message',
	       				    content : '项目名不能为空'
	       				  },
	       				  overlay : false,
	       				  autoClose : 1000
	       				});
            		return false;
            	}
            	
            	$('.z-btn-save').css({"background-color":"#666"}).attr("disabled","true");
            	var orderStatus = $('#order_status option:selected').val();
            	$('#client_orderStatus').val(orderStatus);
            	$("#file_upload_id").ajaxSubmit({
	       			type: "post",
	       			url: "${ctx}/updateClientOrderById.do",
	       	     	dataType: "text",
	       	        success: function(result){	       	      
	       	        	$('#dialog3').show();	
	       	            $('.z-btn-save').css({"background-color":"#006dcc"}).removeAttr("disabled");
	       	        	/*
	       	        	 * 同步客户交期到采购系统(需要调用对外接口)
	       	        	 */
// 	       	            var deliveryDate = $('#date_input2').val();
// 	       	            if(deliveryDate == null || deliveryDate == "" || deliveryDate == "undefined"){
// 	       	            	return false;
// 	       	            }
// 	       	            if( $('#orderId').text() == null ||  $('#orderId').text() == "" ||  $('#orderId').text() == "undefined"){
// 	       	            	return false;
// 	       	            }
// 	       	            $.post("/supplier/port/deliveryDateSYNC.do",
// 	       	            	  { "orderId" : $('#orderId').text(),
// 	       	            	    "deliveryDate" : deliveryDate
// 	       	            	  },
// 	       	        	   function(data){
	       	        	     
// 	       	        	  });




                       //更新订单状态到NBMail
                       if($('#order_status').val() == 1){
                    	   $.post("/supplier/port/sendOrderStatus.do",
     	       	            	  { 
                    		      "orderId" : $('#orderId').text()
     	       	            	  },
     	       	        	   function(data){
     	       	        	     
     	       	        	  });
                       }




	       	        	
	       			},error:function(){
	       				$('.z-btn-save').css({"background-color":"#006dcc"}).removeAttr("disabled");
	       				easyDialog.open({
	 	       				  container : {
	 	       				    header : 'Prompt message',
	 	       				    content : '提交失败，请重试'
	 	       				  },
	 	       				  overlay : false,
	 	       				  autoClose : 1000
	 	       				});
	       			}
				});

            });
            
            //点击切换到可编辑状态
            $('.z-btn-edit').on('click',function () {
            	//按钮样式的更改
    		    $('.z-btn-cancel').show();
                $('.z-btn-save').show();
                $('#content input').removeAttr("disabled");
                $('.xiala_select').removeAttr("disabled");
                $('.w_controls_div input').removeClass('z-no-border').addClass('z-have-border');
                $('.z-btn-edit').hide();
            });
            
            //处理orderStaus显示
            var orderStatus = $('#client_orderStatus').val();
            $('#order_status').val(orderStatus);


            //处理里程碑显示                   	
           show_milestone();
           do_milestone();
            
         
           
           //处理图片轮播
           var curIndex = 0; //当前index
           var imgLen = $(".imgList li").length;
           //左箭头点击处理
           $("#prev").click(function(){ 
             //根据curIndex进行上一个图片处理
             curIndex = (curIndex > 0) ? (--curIndex) : (imgLen - 1);
             $('.indexList').find('li').eq(0).text(curIndex + 1 + "/" + imgLen);
             changeTo(curIndex);
           });

           //右箭头点击处理
           $("#next").click(function(){ 
             curIndex = (curIndex < imgLen - 1) ? (++curIndex) : 0;
             $('.indexList').find('li').eq(0).text(curIndex + 1 + "/" + imgLen);
             changeTo(curIndex);
           });

           function changeTo(num){  
             imgLen = $(".imgList li").length; //图片总数
             var goLeft = num * 400;
             $(".imgList").animate({left: "-" + goLeft + "px"},500);
             $(".infoList").find("li").removeClass("infoOn").eq(num).addClass("infoOn");
           }
           $("#wrapper").click(function(){
        	    return false;  //阻止事件冒泡到父级DIV
        	})
           
          
        	
           /**
            *删除图片
            */
           $('#delete_img').click(function(){
        	   var id = $('.imgList').find('li').eq(curIndex).find('input').eq(0).val();
        	   var uploadDate = $(this).parent().prev().prev().text();
        	   var orderId = $('#order_id').val();
        	   var picIndex = $("#pic_index").val();
        	   $.post("/supplier/deleteProductionPhoto.do", {
       			"id" : id,
       			"uploadDate" : uploadDate,
       			"orderId" : orderId
       		}, function(result) {
       			if(result.state == 0){
       				$("#imgList").empty();
       				$("#infoList").empty();
       				var data = result.data;
       				for(var i=0;i<data.length;i++){
       					var img_li = '<li><a><img src="'+data[i].photoPath+'" alt="'+data[i].photoName+'"></a>'+
       					             '<input type="hidden" value="'+data[i].id+'"/>'+
       					             '</li>';
       					var name_li = '';
       					if(i == 0){
       						name_li = '<li class="infoOn">'+data[0].photoName+'</li>';	
       				    }					
       				    if(i>0){
       				    	name_li = '<li>'+data[i].photoName+'</li>';	
       				    }
       				    $("#imgList").append(img_li);
       				    $("#infoList").append(name_li);
       				    
       				}	
    				$('.indexList').find('li').eq(1).text("1/"+data.length);
    				$('.w-recent-posts').find('li').eq(picIndex).find('span:first').text("1/"+data.length);
//     				$("#prev").click();
//     				$("#next").click();
    				easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '删除成功'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    				if(data.length == 0){
    				  close_wrapper();
    				  $('.w-recent-posts').find('li').eq(picIndex).remove();
    				}
    				
    				
       			}else{
       				easyDialog.open({
  		    		  container : {
  		    		    header : 'Prompt message',
  		    		    content : '删除失败'
  		    		  },
  		    		  overlay : false,
  		    		  autoClose : 1000
  		    		  
  		    		});
       			}
       			
       		})
        	           	   
        });
        	
       
           
            
      })
        
        
      
      
      
        // ============================里程碑======================================================
        function do_milestone(){
        	
        	var tl = $('#milestone_ol').find('li').length;
        	for(var i=0;i<tl;i++){
        
          	//处理是预计完成还是实际完成
          	var expectedOrActually = $('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(3).text();
          	if(expectedOrActually == 0){
        	  //里程碑延期
            $("#milestone_ol").find('li').eq(i).find('span.ui-step-cont-number').click(function(e){
     			    $(this).parent().parent().find('div:last').show(); 
//      			    $(this).mousemove(function(e){  
//      			    	$(this).parent().parent().find('div:last').show();
//      			    });  
//      			    $(this).mouseleave(function(){  
//      			    	$(this).parent().parent().find('div:last').hide();  
//      			    });    
                           
     			   return false; 
                })
                           
         } 
        }				
        	
        	//点击其他部分收起选择框
            $(document).click(function(e) {
				$("#milestone_ol").find('li').find('div.w-shu').hide();
			})    
       } 	
        	
        	
        	
        //处理里程碑显示   
        function show_milestone(){
        	
        	  var total_days = '';
        	  var now_days = '';
        	  var currency_days = '';
        	  var tl = $('#milestone_ol').find('li').length;
              if(tl == 0){
              	$('#milestone_ol').css({"display":"none"});
              }
              if(tl == 1){
                $('#milestone_ol').css({"display":"none"});
              	$('#milestone_ol').find('li').removeClass("step-active").addClass("step-start").addClass("step-done");
              	$('#milestone_ol').find('li').find(".ui-step-line").css({"display":"none"});
              	var status = $('#milestone_ol').find('li').eq(0).find('img.ui-step-cont-text').next().text();
              	if(status == 1){
              		$('#milestone_ol').find('li').eq(0).find('img.ui-step-cont-text').show();
              	}
              }else{
           		//里程碑显示，根据时间计算进度
           		var start = $('#milestone_ol').find("li:first").find('div.ui-step-cont').find("span").eq(1).text();
           		var finish = $('#milestone_ol').find("li:last").find('div.ui-step-cont').find("span").eq(1).text();	
           	 	var d = new Date();
           	 	var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
           	 	
          		var addWidth = 0;
              	for(var i=0;i<tl;i++){
              		
              		if(i>0){
              		 var milestoneDate1	= $('#milestone_ol').find('li').eq(i-1).find('div.ui-step-cont').find('span').eq(1).text();
              		 var milestoneDate2 = $('#milestone_ol').find('li').eq(i).find('div.ui-step-cont').find('span').eq(1).text();
              		 
              		 //计算每个里程碑点距离
              		 total_days = DateDiff1(start,finish);
              		 now_days = DateDiff1(milestoneDate1,milestoneDate2);
              		 var width = Math.floor(now_days/total_days * 82);
              		 if(width < 5){
              			 var a_width = Number(5)-Number(width);
              			 addWidth +=a_width;
              			 width = 5;
              		 }
              		 
              		 $('#milestone_ol').find('li').eq(i-1).css({"width":width+"%"});             		              		
              		 $('#milestone_ol').find('li').eq(i).css({"width":"0%"}); 
              		 var flag1 = DateDiff(milestoneDate1);
              		 var flag2 = DateDiff(milestoneDate2);
                     
                	 //根据日期计算进度
                	 currency_days = DateDiff1(start,str);
              		 var c_width = Math.floor(currency_days/total_days * 77);
              		 if(c_width < 77){
              			 $('#milestone_ol').find('div.z-step-line2').css({"width":c_width+"%"});   
              		 }else{
              			$('#milestone_ol').find('div.z-step-line2').css({"width":"77%"});  
              			$('#milestone_ol').find('div.z-step-line2').find('span:last').hide();
              		 }             		            		 
              		 $('#milestone_ol').find('div.z-step-line2').find('span:last').text(DateConversion(str));
     		 
              	   }          		 
              		
              		//根据日期计算上传图片的位置
//               		var upload_days = '';
//               		var pic_tl = $('.w-png').length;
//                 	for(var k = 0;k<pic_tl;k++){
//                 	   	var uploadDate = $('.w-png').eq(k).find('span:last').text();
//                 	   	upload_days = DateDiff1(start,uploadDate);
//                 	   	var pic_width = Math.floor(upload_days/total_days * 72);
//                 	   	if(pic_width < 72){
//                 	   		$('.w-png').eq(k).css({"left":pic_width+"%"});  	 
//                 	   		$('.w-png1').eq(k).css({"left":pic_width+"%"});  	 
//                   		 }else{
//                   			$('.w-png').eq(k).css({"left":"72%"}); 
//                   			$('.w-png1').eq(k).css({"left":"72%"});  
//                   		 }                         	   
//                 	}            		
              		
              	   //处理图片备注显示
//               	   var remark_tl = $('.w-png1').length;
//               	   for(var j = 0;j<remark_tl;j++){
//               		   var remark = $('.w-png1').eq(j).children().val();
//               		   if(remark == '' || remark == null || remark == undefined){
//               			 $('.w-png1').eq(j).hide();
//               		   }
//               	   }
                	
              		
              		//根据状态判断是否延期（0 正常 1 延期 2完成）
              		var status = $('#milestone_ol').find('li').eq(i).find('img.ui-step-cont-text').next().text();
              		if(status == 1){
              			$('#milestone_ol').find('li').eq(i).find('img.ui-step-cont-text').show();
                  	}
              		

              		
              		//处理日期显示
              		var mile_date = $('#milestone_ol').find('li').eq(i).find('span.ui-step-cont-number').next().text();
              		$('#milestone_ol').find('li').eq(i).find('span.ui-step-cont-number').text(DateConversion(mile_date));
              		
              		
              		//处理是预计完成还是实际完成
              		var expectedOrActually = $('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(3).text();
              		if(expectedOrActually == 1){
              			$('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(0).css({"background-color":"#28b779"});
              			$('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(2).css({"color":"#28b779"});
              			
              		}else{
              			$('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(0).css({"background-color":"#b9b9b9"});
              			$('#milestone_ol').find('li').eq(i).find('.ui-step-cont').find('span').eq(2).css({"color":"#b9b9b9"});
              		}
              			
              	}                             
                       	
              }         	
        }
        
        
        
        
        
        //增加里程碑
        function add_milestone(){
            
        	if($('#milestone_name').val() == null || $('#milestone_name').val() == '' || $('#milestone_name').val() == undefined){
        		easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '请输入里程碑名'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000	    		  
		    		});        		
        		return false;
        	}
        	
        	var createTime = $('#create_time').val();
        	var milestoneDate = $('#milestone_date').val();
        	if(!(DateDiff_date(createTime,milestoneDate))){
        		easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '必须大于项目开始时间'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000	    		  
		    		});        		
      		      return false;
        	}
        	
        	
        	expectedOrActually = 0;
        	$.ajax({
    			url:"${ctx}/saveMilestone.do",
    			data:{
    				  "milestoneName" : $('#milestone_name').val(),
    				  "milestoneDate" : $('#milestone_date').val(),
    				  "orderId" : $('#orderId').text(),
    				  "expectedOrActually" :  expectedOrActually
    				  },
    			type:"post",
    			dataType:"text",
    			success:function(data){

    			var result	= eval("("+data+")").milestones;
    			var productionPhotos = eval("("+data+")").productionPhotos;
    			$('#milestone_ol').find('li').remove();
    			
    			for(var i=0;i<result.length;i++){
    				var li =  '<li class="step-active">'+
	                            '<div class="ui-step-line"></div>'+
	                            '<div class="ui-step-cont">'+
	                                '<span class="ui-step-cont-number" disabled="false;">'+result[i].milestoneDate+'</span>'+
	                                '<span style="display:none;">'+result[i].milestoneDate+'</span>'+
	                                '<span class="ui-step-cont-text">'+result[i].milestoneName+'</span><br>'+
	                                '<span style="display:none;">'+result[i].expectedOrActually+'</span>'+
	                                '<img class="ui-step-cont-text" src="img/img2.png" style="display:none;"><span style="display:none;">'+result[i].delayStatus+'</span>'+
	                                '<span class="ui-step-cont-text">'+(result[i].delayStatus == 1 ? result[i].delayRemark : (result[i].delayStatus == 2 ? "完成" : ""))+'</span> '+                              
	                            '</div>'+
	        
	                            '<div class="w-shu" style="display: none;">'+
	                                '<table class="w-shu-table">'+
	                                    '<tbody><tr>'+
	                                        '<th><a onclick="complete('+result[i].id+','+i+')">完成</a></th>'+
	                                    '</tr>'+
	                                    '<tr>'+
	                                        '<th><a onclick="show_delay('+result[i].id+','+i+',this)">延期</a></th>'+
	                                    '</tr>'+
	                                    '<tr>'+
		                                     '<th><a onclick="edit_milestone('+result[i].id+',this)">编辑</a></th>'+
		                                '</tr>'+
	                                    '<tr>'+
	                                        '<th><a onclick="delete_milestone('+result[i].id+','+i+')">删除</a></th>'+
	                                    '</tr>'+
	                                '</tbody></table>'+
	                            '</div>'+                      
                            '</li>';

     			   $('#milestone_ol').append(li);
    			}			  
            	   $('#milestone_ol').show();
    			  //处理里程碑显示  
    			   show_milestone();
    			   do_milestone();
    			   disclose2();
    			},
    		    error:function(){
    		    	
    		    	easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '添加失败'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    		    }
    		});
        	
        }

        
        
        //打开修改里程碑div
        function edit_milestone(id,obj){
        	
        	$('#dialog7').find('input:first').val(id);
        	$('#milestone_edit_date').val($(obj).parents('li').find('.ui-step-cont-number').next().text());
        	$('#milestone_edit_name').val($(obj).parents('li').find('.ui-step-cont-number').next().next().text());
        	$('#dialog7').show();
        	
        }
        //修改里程碑
        function update_milestone(){
        	
        	var id = $('#dialog7').find('input:first').val();
        	if($('#milestone_edit_name').val() == null || $('#milestone_edit_name').val() == '' || $('#milestone_edit_name').val() == undefined){
        		easyDialog.open({
		    		  container : {
		    		    header : 'Prompt message',
		    		    content : '请输入里程碑名'
		    		  },
		    		  overlay : false,
		    		  autoClose : 1000	    		  
		    		});        		
        		return false;
        	}
        	expectedOrActually = 0;
        	$.ajax({
    			url:"${ctx}/updateMilestone.do",
    			data:{
    				  "milestoneName" : $('#milestone_edit_name').val(),
    				  "milestoneDate" : $('#milestone_edit_date').val(),
    				  "id" : id
    				  },
    			type:"post",
    			dataType:"text",
    			success:function(data){
    			$('#dialog7').hide();
    			var result	= eval("("+data+")");
    			$('#milestone_ol').find('li').remove();    			
    			for(var i=0;i<result.length;i++){
    				var li =  '<li class="step-active">'+
	                            '<div class="ui-step-line"></div>'+
	                            '<div class="ui-step-cont">'+
	                                '<span class="ui-step-cont-number" disabled="false;">'+result[i].milestoneDate+'</span>'+
	                                '<span style="display:none;">'+result[i].milestoneDate+'</span>'+
	                                '<span class="ui-step-cont-text">'+result[i].milestoneName+'</span><br>'+
	                                '<span style="display:none;">'+result[i].expectedOrActually+'</span>'+
	                                '<img class="ui-step-cont-text" src="img/img2.png" style="display:none;"><span style="display:none;">'+result[i].delayStatus+'</span>'+
	                                '<span class="ui-step-cont-text">'+(result[i].delayStatus == 1 ? result[i].delayRemark : (result[i].delayStatus == 2 ? "完成" : ""))+'</span> '+                              
	                            '</div>'+
	        
	                            '<div class="w-shu" style="display: none;">'+
	                                '<table class="w-shu-table">'+
	                                    '<tbody><tr>'+
	                                        '<th><a onclick="complete('+result[i].id+','+i+')">完成</a></th>'+
	                                    '</tr>'+
	                                    '<tr>'+
	                                        '<th><a onclick="show_delay('+result[i].id+','+i+',this)">延期</a></th>'+
	                                    '</tr>'+
	                                    '<tr>'+
		                                     '<th><a onclick="edit_milestone('+result[i].id+',this)">编辑</a></th>'+
		                                '</tr>'+
	                                    '<tr>'+
	                                        '<th><a onclick="delete_milestone('+result[i].id+','+i+')">删除</a></th>'+
	                                    '</tr>'+
	                                '</tbody></table>'+
	                            '</div>'+                      
                            '</li>';

     			   $('#milestone_ol').append(li);
    			}			  
            	   $('#milestone_ol').show();
    			  //处理里程碑显示  
    			   show_milestone();
    			   do_milestone();
    			},
    		    error:function(){
    		    	
    		    	easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '修改失败'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    		    }
    		});
        	
        	
        }
        
        
        
        
        
        
        
      	
        //完成里程碑
        function complete(id){
        	if (confirm("是否确认完成？")) {
        	expectedOrActually = 1;
        	var delayState = 2;
        	$.ajax({
    			url:"${ctx}/saveMilestoneComplete.do",
    			data:{
    				  "id" : id,
    				  "orderId" : $('#orderId').text(),
    				  "expectedOrActually" :  expectedOrActually,
    				  "delayState" : delayState
    				  },
    			type:"post",
    			dataType:"text",
    			success:function(data){

    			var result	= eval("("+data+")").milestones;
    			var productionPhotos = eval("("+data+")").productionPhotos;
    			$('#milestone_ol').find('li').remove();
    			
    			for(var i=0;i<result.length;i++){
    				var li =  '<li class="step-active">'+
				                    '<div class="ui-step-line"></div>'+
				                    '<div class="ui-step-cont">'+
				                        '<span class="ui-step-cont-number" disabled="false;">'+result[i].milestoneDate+'</span>'+
				                        '<span style="display:none;">'+result[i].milestoneDate+'</span>'+
				                        '<span class="ui-step-cont-text">'+result[i].milestoneName+'</span><br>'+
				                        '<span style="display:none;">'+result[i].expectedOrActually+'</span>'+
				                        '<img class="ui-step-cont-text" src="img/img2.png" style="display:none;"><span style="display:none;">'+result[i].delayStatus+'</span>'+
				                        '<span class="ui-step-cont-text">'+(result[i].delayStatus == 1 ? result[i].delayRemark : (result[i].delayStatus == 2 ? "完成" : ""))+'</span> '+                              
				                    '</div>'+
				
				                    '<div class="w-shu" style="display: none;">'+
				                        '<table class="w-shu-table">'+
				                            '<tbody><tr>'+
				                                '<th><a onclick="complete('+result[i].id+','+i+')">完成</a></th>'+
				                            '</tr>'+
				                            '<tr>'+
				                                '<th><a onclick="show_delay('+result[i].id+','+i+',this)">延期</a></th>'+
				                            '</tr>'+
				                            '<tr>'+
				                                '<th><a onclick="edit_milestone('+result[i].id+',this)">编辑</a></th>'+
				                            '</tr>'+
				                            '<tr>'+
				                                '<th><a onclick="delete_milestone('+result[i].id+','+i+')">删除</a></th>'+
				                            '</tr>'+
				                        '</tbody></table>'+
				                    '</div>'+                      
				                '</li>';
     			   $('#milestone_ol').append(li);
    			}			  
            	   $('#milestone_ol').show();
    			  //处理里程碑显示  
    			   show_milestone();
    			   do_milestone();
    			},
    		    error:function(){
    		    	
    		    	easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '添加失败'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    		    }
    		});
        	
         } 	
      }
        
        
        
        
       //添加延期
       function show_delay(id,index,obj){
    	  $('#dialog5').find('input').empty();
    	  $('#dialog5').find('input').val($(obj).parents('li').find('span').eq(1).text());
    	  $('#dialog5').show(); 
    	  $('#milestone_id').val(id);
    	  $('#milestone_index').val(index);
       }
       
       function delay(){    
    	   
    	  var id = $('#milestone_id').val();
     	  var index = $('#milestone_index').val(); 
		  $('#dialog5').hide();
     	  $.ajax({
     			url:"${ctx}/updateMilestoneDelay.do",
     			data:{
     				  "id" : id,
     				  "delayDate" : $('#delay_date').val(),
     				  "delayRemark" : $('#delay_remark').val()
     				  },
     			type:"post",
     			dataType:"text",
     			success:function(data){
     				var result	= eval("("+data+")");
     				$('#milestone_ol').find('li').eq(index).find('img.ui-step-cont-text').show();
     				$('#milestone_ol').find('li').eq(index).find('img.ui-step-cont-text').next().next().text(result.delayRemark);
     			},
    		    error:function(){
    		    	
    		    	easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '修改失败'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    		    }
           })   
    	   
       }  
      
       //上传图片
       function show_upload_pic(){
    	   $('#dialog4').show();
	     }
       
       

      //上传后返回图纸路径，以逗号隔开
      function show_drawingName(obj){
    	  var reg = /[^\\\/]*[\\\/]+/g; //匹配文件的名称和后缀的正则表达式
 	      var name = $(obj).val().replace(reg, '');
 	      var postfix = /\.[^\.]+/.exec(name);//获取文件的后缀
 	      var text =name.substr(0,postfix['index']);//获取没有后缀的名称
           $(obj).next().text(text);  
 	      	
      	  if(text == null || text == "" || text == undefined){
      		  return false;
      	  }else{
      		 autTime(); 
      		 $('#upload_title').children().text("图片上传进度");
      	  }
   		  
   		  //先上传后获取上传文件路径
   		 $("#file_upload_id1").ajaxSubmit({    			
      			type: "post",
      			url: "/supplier/uploadMultipleFiles.do",
      	     	dataType: "text",
      	     	success: function(str){
      	     	var result = eval('(' + str + ')');	
      	     	if(result.state == 0){
  	 	             $('#upload_pic_names').val(result.data);  
      	     	}else{
  	       	     	easyDialog.open({
            		   container : {
                		     header : '  Prompt message',
                		     content : '  Upload failed '
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
                   		     header : '  Prompt message',
                   		     content : '  Upload failed '
               		   },
   					  overlay : false,
   					  autoClose : 1000
               		 });   
    	     		$('#show_upload_dialog').hide();
      			}       	     	
      	 	}); 	 		    

       }
      
      //保存产品图片
      function saveProductionPhoto(orderId,obj){
    	 $(obj).css({"color":"#666"}).attr("disabled","true");
     	 $("#file_upload_id1").ajaxSubmit({
    			type: "post",
    			url: "${ctx}/saveProductionPhoto.do",
    	     	dataType: "text",
    	        success: function(str){
    	        	var obj = eval('('+str+')');
    	        	if(obj.state == 0){
        	        	$('#dialog4').hide();
        	        	var result	= obj.data.productionPhotoes;
        	        	var productionPhotoTimelines = obj.data.productionPhotoTimelines;
        	        	var tl = result.length;
                        if(tl != 0){
                        	
                        	$('.w-recent-posts').empty();
                            for(var i=0;i<tl;i++){		
                           
		                            var li='';
		                            if(result[i].isDocument != 1){
			           		            li = '<li>'+         
						                 '<span class="z-pro-tl">1/'+productionPhotoTimelines[i].length+'</span>'+     
						                 '<div class="user-thumb w-user-thumb">'+
						                 '<img width="100" height="100" src="'+(productionPhotoTimelines[i].length == 0 ? '' : productionPhotoTimelines[i][0].photoPathCompress)+'" onclick="show_photos(\''+result[i].orderId+'\',\''+result[i].uploadDate+'\')"></div>'+
						                 '<div class="article-post"> <span class="user-info"> 上传日期：</span><span>'+result[i].uploadDate+'</span>'+
						                   '<a class="z-a2" onclick="open_remark_edit('+i+',this)">修改备注</a>'+
						                   '<p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>'+(result[i].remarks == null ? '' : result[i].remarks)+'</span></p>'+
						                 '</div>'+
						                 '</li>'; 
		                            }else{
		                            	var img = '';
		                            	var path = result[i].documentPath;
		                            	 //判断上传格式，显示对应图片
		                       	        var gen = path.lastIndexOf("."); 
		                       	        var type = path.substr(gen); 
		                       	        if(type.toLowerCase()  == ".doc" || type.toLowerCase()==".docx"){
		                       	        	img = '<img width="100" height="100" src="img/word.png">';
		                       	        }else if(type.toLowerCase()  == ".xls" || type.toLowerCase()==".xlsx"){
		                       	        	img = '<img width="100" height="100" src="img/excel.png">';
		                       	        }else if(type.toLowerCase()  == ".pdf"){
		                       	        	img = '<img width="100" height="100" src="img/pdf.png">';
		                       	        }else if(type.toLowerCase()  == ".ppt" || type.toLowerCase()==".pptx"){
		                       	        	img = '<img width="100" height="100" src="img/ppt.png">';
		                       	        }
		                            	li = '<li>'+         
						                 '<span class="z-pro-tl"></span>'+     
						                 '<div class="user-thumb w-user-thumb" onclick=download_document('+result[i].id+')>'+img+'</div>'+
						                 '<div class="article-post"> <span class="user-info"> 上传日期：</span><span>'+result[i].uploadDate+'</span>'+
						                   '<a class="z-a2" onclick="open_remark_edit('+i+',this)">修改备注</a>'+
						                   '<p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>'+(result[i].remarks == null ? '' : result[i].remarks)+'</span></p>'+
						                 '</div>'+
						                 '</li>'; 
		                            }	
		              				                            
	                              if(i == 0){
	                            	  $('.w-recent-posts').append(li);
	                              }else if(i == 1){
	                            	  $('.w-recent-posts').append(li); 
	                              }
                           }
                          if(tl>2){
                        	  $('.w-recent-posts').append("<li><button class=\"btn btn-warning btn-mini\" onclick=\"show_all_report(\'"+orderId+"',this)\">查看所有</button></li>");
                          }                           
                        } 	 
                        $(obj).css({"color":"#006dcc"}).removeAttr("disabled");  
                        
    	        	}else{
    	        		
    	        		easyDialog.open({
      		    		  container : {
      		    		    header : 'Prompt message',
      		    		    content : '保存失败'
      		    		  },
      		    		  overlay : false,
      		    		  autoClose : 1000
      		    		  
      		    		});
    	        		$(obj).css({"color":"#006dcc"}).removeAttr("disabled");
    	        	}
    	        	
    	        	
    	        },error:function(){
    		    	
    		    	easyDialog.open({
    		    		  container : {
    		    		    header : 'Prompt message',
    		    		    content : '保存失败'
    		    		  },
    		    		  overlay : false,
    		    		  autoClose : 1000
    		    		  
    		    		});
    		    }
           })   
      }
      
      //查看所有weekly report
      function show_all_report(orderId,obj){
    	  
    	  if($(obj).html() == "收起周报"){
    		  var l = $('.w-recent-posts').find('li').length;
    		  for(var i=0;i<l;i++){
    			  if(i>1 && i<l-1){
    			  $('.w-recent-posts').find('li').eq(i).hide(); 
    			  }
    		  }
    		  $(obj).html("查看所有");
    		  return false;
    	  }
    	  
    	  $.ajax({
     			url:"/supplier/queryPhotoByOrderId.do",
     			data:{
     				  "orderId":orderId
     				  },
     			type:"post",
     			dataType:"text",
     			success:function(str){
     				var strr = eval('('+str+')');
    	        	if(strr.state == 0){
        	        	var result	= strr.data.productionPhotoes;
        	        	var productionPhotoTimelines = strr.data.productionPhotoTimelines;
        	        	console.log(productionPhotoTimelines);
        	        	var tl = result.length;
                        if(tl != 0){                       	
                        	$('.w-recent-posts').empty();
                            for(var i=0;i<tl;i++){	
               
				             var li='';
	                           if(result[i].isDocument != 1){
		           		            li = '<li>'+         
					                 '<span class="z-pro-tl">1/'+productionPhotoTimelines[i].length+'</span>'+     
					                 '<div class="user-thumb w-user-thumb">'+
					                 '<img width="100" height="100" src="'+(productionPhotoTimelines[i].length == 0 ? '' : productionPhotoTimelines[i][0].photoPathCompress)+'" onclick="show_photos(\''+result[i].orderId+'\',\''+result[i].uploadDate+'\')"></div>'+
					                 '<div class="article-post"> <span class="user-info"> 上传日期：</span><span>'+result[i].uploadDate+'</span>'+
					                   '<a class="z-a2" onclick="open_remark_edit('+i+',this)">修改备注</a>'+
					                   '<p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>'+(result[i].remarks == null ? '' : result[i].remarks)+'</span></p>'+
					                 '</div>'+
					                 '</li>'; 
	                            }else{
	                            	var img = '';
	                            	var path = result[i].documentPath;
	                            	 //判断上传格式，显示对应图片
	                       	        var gen = path.lastIndexOf("."); 
	                       	        var type = path.substr(gen); 
	                       	        if(type.toLowerCase()  == ".doc" || type.toLowerCase()==".docx"){
	                       	        	img = '<img width="100" height="100" src="img/word.png">';
	                       	        }else if(type.toLowerCase()  == ".xls" || type.toLowerCase()==".xlsx"){
	                       	        	img = '<img width="100" height="100" src="img/excel.png">';
	                       	        }else if(type.toLowerCase()  == ".pdf"){
	                       	        	img = '<img width="100" height="100" src="img/pdf.png">';
	                       	        }else if(type.toLowerCase()  == ".ppt" || type.toLowerCase()==".pptx"){
	                       	        	img = '<img width="100" height="100" src="img/ppt.png">';
	                       	        }
	                            	li = '<li>'+         
					                 '<span class="z-pro-tl"></span>'+     
					                 '<div class="user-thumb w-user-thumb" onclick=download_document('+result[i].id+')>'+img+'</div>'+
					                 '<div class="article-post"> <span class="user-info"> 上传日期：</span><span>'+result[i].uploadDate+'</span>'+
					                   '<a class="z-a2" onclick="open_remark_edit('+i+',this)">修改备注</a>'+
					                   '<p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>'+(result[i].remarks == null ? '' : result[i].remarks)+'</span></p>'+
					                 '</div>'+
					                 '</li>'; 
	                            }	
				               

                            	  $('.w-recent-posts').append(li); 
                           } 
                            $('.w-recent-posts').append("<li><button class=\"btn btn-warning btn-mini\" onclick=\"show_all_report(\'"+orderId+"',this)\">收起周报</button></li>");
                        }
    	        	}
     			},
     		    error:function(){
     		    	
     		    	easyDialog.open({
     		    		  container : {
     		    		    header : 'Prompt message',
     		    		    content : '查询失败'
     		    		  },
     		    		  overlay : false,
     		    		  autoClose : 1000
     		    		  
     		    		});
     		    }
     		});
    	  
      }
      
      
      
    //点击弹出照片墙
      function show_photos(orderId,uploadDate,index){
   	   $('#wrapper').show();
   	   $("#imgList").empty();
   	   $("#infoList").empty();
       $("#pic_index").val(index);
   	   
   	   $.ajax({
   			url:"/supplier/queryPhotoByUploadDate.do",
   			data:{
   				  "orderId":orderId,
   				  "uploadDate":uploadDate
   				  },
   			type:"post",
   			dataType:"text",
   			success:function(data){
   				var result	= eval("("+data+")");
   				if(result.length == 0){
   					return false;
   				}
   				for(var i=0;i<result.length;i++){
   					var img_li = '<li><a><img src="'+result[i].photoPath+'" alt="'+result[i].photoName+'"></a>'+
   					             '<input type="hidden" value="'+result[i].id+'"/>'+
   					             '</li>';
   					var name_li = '';
   					if(i == 0){
   						name_li = '<li class="infoOn">'+result[0].photoName+'</li>';	
   				    }					
   				    if(i>0){
   				    	name_li = '<li>'+result[i].photoName+'</li>';	
   				    }
   				    $("#imgList").append(img_li);
   				    $("#infoList").append(name_li);
   				    
   				}	

				$('.indexList').find('li').eq(1).text(uploadDate);
				$('.indexList').find('li').eq(0).text("1/"+result.length);
// 				$("#prev").click();
// 				$("#next").click();
   				
   				
   			},
   		    error:function(){
   		    	
   		    	easyDialog.open({
   		    		  container : {
   		    		    header : 'Prompt message',
   		    		    content : 'Failed to load'
   		    		  },
   		    		  overlay : false,
   		    		  autoClose : 1000
   		    		  
   		    		});
   		    }
   		});
      }
      
      //下载周报文档   2017.12.07
      function download_document(id){
    	    window.location = '/supplier/fileDownload_document.do?id='+id;   	 	
      }
    
    
       //关闭图片框
	   function close_wrapper(){
		   $('#wrapper').hide();
       }
       
       //根据id删除里程碑
       function delete_milestone(id,index){
    	   if (confirm("是否确定删除？")) {
    	   $.post("/supplier/deleteMilestoneById.do",
    	     {
      			"id" : id,
      			"orderId" : $('#order_id').val()
      		 }, function(result) {
                      if(result.state == 0){
                    	  var tl = result.data.length;                   	  
                                easyDialog.open({
               		    		  container : {
               		    		    header : 'Prompt message',
               		    		    content : '删除成功'
               		    		  },
               		    		  overlay : false,
               		    		  autoClose : 1000
               		    		  
            		    		 });
                             $("#milestone_ol").children("li").eq(index).remove();
                             show_milestone();
				                       
                    	  }else{
                    		  easyDialog.open({
               		    		  container : {
               		    		    header : 'Prompt message',
               		    		    content : '删除失败'
               		    		  },
               		    		  overlay : false,
               		    		  autoClose : 1000
               		    		  
            		    		});
                 			}
                 			
                 		});
             											
       }

   }

       
       
       
//============================里程碑所有方法====================================================== 
        

	
	
        function disclose(){
            $('#dialog2').hide();
        }

        function disclose2(){
            $('#dialog6').hide();
        }
        function disclose4(){
            $('#dialog5').hide();
        }
        function disclose7(){
        	$('#milestone_edit_name').val('');
        	$('#milestone_edit_date').val('');
            $('#dialog7').hide();
        }
        function disclose5(){
            $('#dialog4').hide();
        }

//         function doUpload_po(){
//   		  var path =  $("#file_upload_po").val();	
//   		  sppath = path.split("\\");
//   		  var poName = sppath[sppath.length-1];
//   		  $('#fileName1').text(poName);
//   		  $('#getPoPath').val(poName);	

//         }

//         function doUpload_qc(){
//   		  var path =  $("#file_upload_qc").val();	
//   		  sppath = path.split("\\");
//   		  var qcName = sppath[sppath.length-1];
//   		  $('#fileName3').text(qcName);
//   		  $('#getQcPath').val(qcName);	

//         }
//         function doUpload_shipping(){
//   		  var path =  $("#file_upload_shipping").val();	
//   		  sppath = path.split("\\");
//   		  var shippingName = sppath[sppath.length-1];
//   		  $('#fileName4').text(shippingName);
//   		  $('#getShippingPath').val(shippingName);	

//         }
        
        
        //获取上传图纸名
        function doUpload_drawing(){
     	  var path =  $("#file_upload_drawing").val();
     	  if(path == null || path == "" || path == undefined){
   		    return false;
   	      } 	  
      	  sppath = path.split("\\");
      	  var drawingName = sppath[sppath.length-1];
      	    $('#fileName5').text(drawingName);            
      	    $('#drawing_name').val(drawingName);  
      	    
            if(drawingName != null && drawingName != '' && drawingName != undefined){
     			 autTime(); 
            	 $('#upload_title').children().text("图纸上传进度");
     		  }
            $("#file_upload_id").ajaxSubmit({    			
     			type: "post",
     			url: "/supplier/uploadAttachmentAndChangeName.do",
     	     	dataType: "text",
     	     	success: function(str){
     	     	var result = eval('(' + str + ')');	
     	     	if(result.state == 0){
     	     		$('#getDrawingPath').val(result.data); 
     	     	}else{
	 	       	   easyDialog.open({
	           		   container : {
	               		     header : '  Prompt message',
	               		     content : '  Upload failed '
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
                  		     header : '  Prompt message',
                  		     content : '  Upload failed '
              		   },
  					  overlay : false,
  					  autoClose : 1000
              		 });   
   	     		$('#show_upload_dialog').hide();
     			}       	     	
     	 	}); 	 		    
       
         }  
        
        function doUpload_drawing1(){

   		  var path =  $("#file_upload_drawing1").val();	
      	  sppath = path.split("\\");
      	  var drawingName = sppath[sppath.length-1];
      	  $('#fileName6').text(drawingName);
          $('#drawing_name').val(drawingName);  
          
          if(drawingName != null && drawingName != '' && drawingName != undefined){
  			 autTime(); 
         	 $('#upload_title').children().text("图纸上传进度");
  		  }
          
          
          
         $("#file_upload_id").ajaxSubmit({    			
  			type: "post",
  			url: "/supplier/uploadAttachmentAndChangeName.do",
  	     	dataType: "text",
  	     	success: function(str){
  	     	var result = eval('(' + str + ')');	
  	     	if(result.state == 0){
  	     		$('#getDrawingPath1').val(result.data); 
  	     	}else{
	 	       	   easyDialog.open({
	           		   container : {
	               		     header : '  Prompt message',
	               		     content : '  Upload failed '
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
               		     header : '  Prompt message',
               		     content : '  Upload failed '
           		   },
					  overlay : false,
					  autoClose : 1000
           		 });   
	     		$('#show_upload_dialog').hide();
  			}       	     	
  	 	}); 	 		    
         
     }
        /*
         * 提交clientDrawing数据
         */
         function saveClientDrawing(){
        	 if($('#unit_price').val().trim() == null || $('#unit_price').val().trim() == ''){     
                 easyDialog.open({
             		   container : {
                 		     header : '  Prompt message',
                 		     content : '  单价不能为空 '
             		   },
  					  overlay : false,
  					  autoClose : 1000
             		 });   
                 return false;
        	 } 
        	 if($('#quantity').val().trim() == null || $('#quantity').val().trim() == ''){
        		 easyDialog.open({
           		   container : {
               		     header : '  Prompt message',
               		     content : '  数量不能为空 '
           		   },
					  overlay : false,
					  autoClose : 1000
           		 });   
               return false;
        	 } 
        	 
        	 var tbody = $('#product_tbody');        	
        	 $('#product_tbody').find("tr").remove();
             
        	 $("#file_upload_id").ajaxSubmit({
       			type: "post",
       			url: "${ctx}/drawing/saveClientDrawings.do",
       	     	dataType: "text",
       	        success: function(result){
       	               var json = eval("(" + result + ")");	 
             		   var list = json.data;  
             		   if(list == null){
             			   return false;
             		   }
             		   for(var i=0;i<list.length;i++){
             			   var clientDrawing = list[i];
             			   var tr = '<tr class="gradeA odd">'+
			                           '<td>'+clientDrawing.productName+'</td>'+
			                           '<td>'+clientDrawing.unitPrice+'</td>'+
			                           '<td>'+clientDrawing.quantity+'</td>'+
			                           '<td>'+clientDrawing.moldPrice+'</td>'+
			                           '<td class="center">'+
			                              '<a href="javascript:;" class="divButton"><button class="btn btn-primary" onclick="editDrawing('+clientDrawing.id+');">编辑</button></a>'+
			                              '<a href="javascript:;" style="margin-left: 10px;" class="divButton"><button class="btn btn-primary" onclick="download_drawing('+clientDrawing.id+');">下载图纸</button></a>'+
			                              '<a><button class="btn btn-danger" style="margin-left: 10px;" onclick="removeDrawing(this,'+clientDrawing.id+');">删除</button></a>'+
			                           '</td>'+
			                       '</tr>';
             			   tr = $(tr);
             			  tbody.append(tr);
             		      
             	     }	
             		  $('#dialog2 input').val("");
             		  $('#show_upload_dialog').hide();
  	     	       }
         		});
        	
           }
           //删除图纸        
		   //得到行对象  
			function getRowObj(obj,id)  
			{  
			var i = 0;  
			while(obj.tagName.toLowerCase() != "tr"){  
			obj = obj.parentNode;  
			if(obj.tagName.toLowerCase() == "table")return null;  
			}  
			return obj;  
			}  
			//根据得到的行对象得到所在的行数  
			function getRowNo(obj,id){  
			var trObj = getRowObj(obj);  
			var trArr = trObj.parentNode.children;  
			for(var trNo= 0; trNo < trArr.length; trNo++){  
			if(trObj == trObj.parentNode.children[trNo]){  
			alert(trNo+1);  
			}  
			}  
			}  
  
			 //移除tr      
			   function removeDrawing(obj,id){
			    if (confirm("是否删除此图纸？")){
				var tr = this.getRowObj(obj);  
				if(tr != null){  
				tr.parentNode.removeChild(tr); 

				$.post("${ctx}/drawing/deleteDrawingsById.do",
		        		   {"id" : id},
		        		   function(result){		        	         
		        			   
		        		   });

				}else{  
				throw new Error("the given object is not contained by the table");  
				}  
			    }
			 }
			 
			 


        
			 //编辑图纸
				function editDrawing(id){
					
					$('#dialog1').show().on('click', '.weui_btn_dialog', function () {
	                    $('#dialog1').off('click').hide();
	                }); 
					$.post(
							"${ctx}/drawing/queryDrawingsById.do",
							{"id" : id},
	                        function(result){
//	 							var json = eval("(" + result + ")");	 
			             		var clientDrawing = result.data;
							    $('#product_name1').val(clientDrawing.productName);
							    $('#unit_price1').val(clientDrawing.unitPrice);
							    $('#quantity1').val(clientDrawing.quantity);
							    $('#mold_price1').val(clientDrawing.moldPrice);
							    if(clientDrawing.drawingsName != null && clientDrawing.drawingsName != ''){
							    	var drawingsName = clientDrawing.drawingsName;
							    	var o_drawingsName = drawingsName.split("&");
							    	if(o_drawingsName.length != 0){
									    $('#fileName6').text(o_drawingsName[0]);
							    	}
							    }
			                    $('#drawing_id').val(clientDrawing.id);
							});											
				 } 
			 
				 //更新图纸信息
				function updateClientDrawing(){
				    $('#product_tbody').find("tr").remove();

	        	 var drawingName = $('#getDrawingPath').val();
	        	 if(!(drawingName == null || drawingName == '' || drawingName == undefined)){
	            	 autTime(); 
	        	 }
				    
					$("#file_upload_id").ajaxSubmit({
		       			type: "post",
		       			url: "${ctx}/drawing/updateDrawingsById.do",
		       	     	dataType: "text",
		       	        success: function(result){
		       	      
		       	        	var json = eval("(" + result + ")");	 
		             		   var list = json.data;
		             		   var tbody = $('#product_tbody');
		             		   for(i=0;i<list.length;i++){
		             			   var clientDrawing = list[i];
		             			   var tr = '<tr class="gradeA odd">'+
					                           '<td>'+clientDrawing.productName+'</td>'+
					                           '<td>'+clientDrawing.unitPrice+'</td>'+
					                           '<td>'+clientDrawing.quantity+'</td>'+
					                           '<td>'+clientDrawing.moldPrice+'</td>'+
					                           '<td class="center">'+
					                              ' <a href="javascript:;" class="divButton"><button class="btn btn-primary" onclick="editDrawing('+clientDrawing.id+')">编辑</button></a>'+
					                              '<a href="javascript:;" style="margin-left: 10px;" class="divButton"><button class="btn btn-primary" onclick="download_drawing('+clientDrawing.id+');">下载图纸</button></a>'+
					                              '<a><button class="btn btn-danger" style="margin-left: 10px;" onclick="removeDrawing(this,'+clientDrawing.id+');">删除</button></a>'+
					                           '</td>'+
					                       '</tr>';
		             			   tr = $(tr);
		             			  tbody.append(tr);		             		      
		             	     }	
		             		   
		             		  $('#show_upload_dialog').hide();
		  	     	       },error: function(result){
		  	     	    	 easyDialog.open({
              		    		  container : {
              		    		    header : 'Prompt message',
              		    		    content : '修改失败'
              		    		  },
              		    		  overlay : false,
              		    		  autoClose : 1000
              		    		  
           		    		});
		  	     	       }	         
					});
					
				}
				
				function disclose1(){
					$('#dialog1').hide();
				}
				
				
			   //和当前日期比较
			   function  DateDiff(deliveryTime){    //deliveryTime是2002-12-18格式  
				   var   d=new   Date(Date.parse(deliveryTime .replace(/-/g,"/")));
				   var   curDate=new   Date();
			       if(d > curDate){
			    	   return false; 
			       }else{
			    	   return true;  
			       }	      
			   }
			   //和当前日期比较
			   function  DateDiff_date(date1,date2){    //date是2002-12-18格式  
				   var   d1 = new   Date(Date.parse(date1 .replace(/-/g,"/")));
				   var   d2 = new   Date(Date.parse(date2 .replace(/-/g,"/")));
			       if(d1 > d2){
			    	   return false; 
			       }else{
			    	   return true;  
			       }	      
			   }
			   
			 //计算时间差
			   function  DateDiff1(sDate1,  sDate2){    //sDate1和sDate2是2002-12-18格式  
			       var  aDate,  oDate1,  oDate2,  iDays  
			       aDate  =  sDate1.split("-");  
			       oDate1  =  new  Date(aDate[0]  +  '/'  +  aDate[1]  +  '/'  +  aDate[2]);    //转换为12-18-2002格式  
			       aDate  =  sDate2.split("-");  
			       oDate2  =  new  Date(aDate[0]  +  '/'  +  aDate[1]  +  '/'  +  aDate[2]);  
			       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
			       return  iDays ; 
			   }
			 
			   //把日期格式化 2012-12-18 转化为12/18
			   function DateConversion(sDate){
			 	    var aDate;
			 	    var oDate;
			 	    aDate  =  sDate.split("-");
			 	    oDate = aDate[1]+ '.' + aDate[2];  
			 	    if(oDate == 'undefined.undefined'){
			 	    	oDate = '';
			 	    }
			 	    return oDate;
			 	} 
				
    
   //删除po、qc、shippingDoc报告方法
        function delete_po(poId,obj){
        	if (confirm("是否删除当前PO Report？")) {
        		
        		 $.post("/supplier/deletePoReport.do", 
       	 			  { 
       	 		        "poId" : poId
       	 		      },
       	 			   function(result){
       	 		    	  if(result.state == 0){
       	 		    		easyDialog.open({
            		    		  container : {
            		    		    header : 'Prompt message',
            		    		    content : '删除成功'
            		    		  },
            		    		  overlay : false,
            		    		  autoClose : 1000           		    		  
         		    		});
	       	 		    	$('#fileName1').text('No file selected');
	                        $(obj).parents('li').remove();
	   	 		    		if($(obj).parents('ul').find('li').length == 0){
	   	 		    		 $(obj).parents('ul').parent().hide();	
   	 		    		    }
       	 		    	  }else{
       	 		    		 easyDialog.open({
             		    		  container : {
             		    		    header : 'Prompt message',
             		    		    content : '删除失败，请重试'
             		    		  },
             		    		  overlay : false,
             		    		  autoClose : 1000
             		    		  
          		    		});  
       	 		    	  }
       	 		      })
   			
    		}
	   
         }
        function delete_QcReport(qcReportId,obj){
        	if (confirm("是否删除当前QC Report？")) {
        		
        		 $.post("/supplier/deleteQcReport.do", 
       	 			  { 
       	 		        "qcReportId" : qcReportId
       	 		      },
       	 			   function(result){
       	 		    	  if(result.state == 0){
       	 		    		easyDialog.open({
            		    		  container : {
            		    		    header : 'Prompt message',
            		    		    content : '删除成功'
            		    		  },
            		    		  overlay : false,
            		    		  autoClose : 1000           		    		  
         		    		});
       	 		    		$('#fileName3').text('No file selected');
                            $(obj).parents('li').remove();
       	 		    		if($(obj).parents('ul').find('li').length == 0){
       	 		    		 $(obj).parents('ul').parent().hide();	
       	 		    		}
       	 		    			
       	 		    	  }else{
       	 		    		 easyDialog.open({
             		    		  container : {
             		    		    header : 'Prompt message',
             		    		    content : '删除失败，请重试'
             		    		  },
             		    		  overlay : false,
             		    		  autoClose : 1000
             		    		  
          		    		});  
       	 		    	  }
       	 		      })
   			
    		}
	   
         }
        function delete_shippingDoc(shippingDocId,obj){
        	if (confirm("是否删除当前Shipping Doc？")) {
        		
        		 $.post("/supplier/deleteShippingDoc.do", 
       	 			  { 
       	 		        "shippingDocId" : shippingDocId
       	 		      },
       	 			   function(result){
       	 		    	  if(result.state == 0){
       	 		    		easyDialog.open({
            		    		  container : {
            		    		    header : 'Prompt message',
            		    		    content : '删除成功'
            		    		  },
            		    		  overlay : false,
            		    		  autoClose : 1000           		    		  
         		    		});
       	 		    		$('#fileName4').text('No file selected');
                            $(obj).parents('li').remove();
   	 		    		    if($(obj).parents('ul').find('li').length == 0){
   	 		    		    $(obj).parents('ul').parent().hide();	
   	 		    		}
       	 		    	  }else{
       	 		    		 easyDialog.open({
             		    		  container : {
             		    		    header : 'Prompt message',
             		    		    content : '删除失败，请重试'
             		    		  },
             		    		  overlay : false,
             		    		  autoClose : 1000
             		    		  
          		    		});  
       	 		    	  }
       	 		      })
   			
    		}
	   
         }
			   
		 
          //打开输入结束时间
          function openFinishedDiv(){
        	  $('#finished_div').show();
          }
          //关闭运输计划
          function closeFinishedDiv(){
        	  if($('#outputTime').val().trim() == null || $('#outputTime').val().trim() == "" || $('#outputTime').val().trim() == undefined){
        		  easyDialog.open({
 		    		  container : {
 		    		    header : 'Prompt message',
 		    		    content : '结束时间为空'
 		    		  },
 		    		  overlay : false,
 		    		  autoClose : 1000		    		  
		    		});  
        		  return;
        	  }       	 
        	  $('#finished_div').hide();  
          }
        
          //选择运输计划的时候
          function select_FinishedDate(){
        	  if($('#order_status').val() == 1){
        		  openFinishedDiv();  
        	  }        	  
          }
          
         
          var shipping_index = 0;
          //打开输入运输计划
          function openShippingDiv(id,index){
        	  shipping_index = index;
        	  var arrivalDate = $('#shipping_ul').find('li').eq(index).find('.a-date').text();
        	  var startDate = $('#shipping_ul').find('li').eq(index).find('.s-date').text();
        	  var destinationPort = $('#shipping_ul').find('li').eq(index).find('.d-port').text();
        	  $('#destination_port').val('');
        	  if(arrivalDate != null && arrivalDate != '' && arrivalDate != undefined){
        		  $('#shipping_arrival_date').val(arrivalDate);  
        	  }
        	  if(startDate != null && startDate != '' && startDate != undefined){
        		  $('#shipping_start_date').val(startDate);
        	  }
        	  if(destinationPort != null && destinationPort != '' && destinationPort != undefined){
        		  $('#destination_port').val(destinationPort);
        	  }
        	  if(id == '' || id == null || id == undefined){
        		  $('#shipping_arrival_date').val('');
        		  $('#shipping_start_date').val('');
        		  $('#shipping_doc_id').val('');
        	  }else{
            	  $('#shipping_doc_id').val(id); 
        	  }
        	  $('#shipping_div').show();
          }
          
          //确认运输计划
          function updateShippingDiv(){
        	  if($('#shipping_start_date').val().trim() == null || $('#shipping_start_date').val().trim() == "" || $('#shipping_start_date').val().trim() == undefined){
        		  easyDialog.open({
 		    		  container : {
 		    		    header : 'Prompt message',
 		    		    content : '运输开始时间不能为空'
 		    		  },
 		    		  overlay : false,
 		    		  autoClose : 1000		    		  
		    		});  
        		  return false;
        	  }       	 
        	  if($('#shipping_arrival_date').val().trim() == null || $('#shipping_arrival_date').val().trim() == "" || $('#shipping_arrival_date').val().trim() == undefined){
        		  easyDialog.open({
 		    		  container : {
 		    		    header : 'Prompt message',
 		    		    content : '运输到达时间不能为空'
 		    		  },
 		    		  overlay : false,
 		    		  autoClose : 1000		    		  
		    		});  
        		  return false;
        	  }  
        	  var destinationPort = $('#destination_port').val();
        	  var id = $('#shipping_doc_id').val();
        	  
        	  $.post("/supplier/updateShippingDoc.do", 
       	 			  {  
        		        "id" : id,
        		        'orderId' : $('#order_id').val(),
       	 		        "shippingStartDate" : $('#shipping_start_date').val(),
       	 		        "shippingArrivalDate" : $('#shipping_arrival_date').val(),
       	 		        "ISFDate" : $('#ISF_date').val(),
       	 		        "destinationPort" : destinationPort
       	 		      },
       	 			   function(result){
       	 		    	  if(result.state == 0){
         	 		    	var startDate = $('#shipping_start_date').val();
           	 		    	var arrivalDate = $('#shipping_arrival_date').val();   
           	 		    	var shippingDocs = result.data;
       	 		    		if(id == null || id == '' || id == undefined){
       	 		    		$('#shipping_ul').empty();
       	 		    	    for(var i=0;i<shippingDocs.length;i++){
	       	 		    	    var li = '<li>'+
				  		            	  '<div class="row-fluid" style="margin-top:0;">'+   
				  					       '<div class="control-group span12">'+   		                    
				  		                    '<span style="color:#666;">运输开始时间：</span><span class="s-date">'+shippingDocs[i].shippingStartDate+'</span>'+   
				  		            		'<span style="color:#666;margin-left: 20px;">运输结束时间：</span><span class="a-date">'+shippingDocs[i].shippingArrivalDate+'</span>'+  
				  		            		'<span style="color:#666;margin-left: 20px;">目的港：</span><span class="d-port">'+(shippingDocs[i].destinationPort == null ? "" : shippingDocs[i].destinationPort)+'</span>'+  
				  		            		'<a class="z-a3" onclick="openShippingDiv('+shippingDocs[i].id+','+i+')">修改运输时间</a>'+    		            				            		
				  		            		'<a style="color: #08c;margin-left: 20px;text-decoration: underline;cursor: pointer;" onclick="show_shipping_file('+shippingDocs[i].id+')">上传文件</a>'+  	
				  		                    '<a class="z-a3" onclick="delete_shippingDoc('+shippingDocs[i].id+',this)">删除</a>'+  
				  		                   '</div>'+  
				  		            	  '</div>'+
				                       '</li>';
		       	 		    		$('#shipping_ul').append(li);
	       	 		    	    }		
    	 		    			
       	 		    		}else{
           	 		    		$('#shipping_ul').find('li').eq(shipping_index).find('.s-date').text(startDate);     	 		    		
           	 		    		$('#shipping_ul').find('li').eq(shipping_index).find('.a-date').text(arrivalDate);     	
           	 		    		$('#shipping_ul').find('li').eq(shipping_index).find('.d-port').text(destinationPort);     	
       	 		    		} 	 		    		  
	 		    		
       	 		    		  
       	 		    	  }else{
       	 		    		 easyDialog.open({
             		    		  container : {
             		    		    header : 'Prompt message',
             		    		    content : '保存失败，请重试'
             		    		  },
             		    		  overlay : false,
             		    		  autoClose : 1000
             		    		  
          		    		});  
       	 		    	  }
       	 		      })       	          	  
        	  $('#shipping_div').hide();  
          }
        
         function closeShippingDiv(){
        	 $('#shipping_div').hide();  
         } 
          
				
    </script>

<style type="text/css">
  body,div,ul,li,a,img{margin: 0;padding: 0;}
  ul,li{list-style: none;}
  a{text-decoration: none;}

  #banner{
  	position: relative;
    width: 400px;
    height: 300px;
    overflow: hidden;
    }
  .imgList{
  	position: relative;
    width: 16000px;
    height: 300px;
    z-index: 10;
    overflow: hidden;
    }
  .imgList li{float:left;display: inline;}
  #prev,
  #next{ 
  	position: absolute;
    top: 140px;
    z-index: 20;
    cursor: pointer;
    opacity: 0.2;
    filter: alpha(opacity=20);
    }
    @media (max-width:1366px){
input {width:140px;}}
    
  #prev{left: 10px;}
  #next{right: 10px;}
  #prev:hover,
  #next:hover{opacity: 0.5;filter:alpha(opacity=50);}
 .bg{position: absolute;bottom: 0;width: 400px;height: 37px;z-index:20;opacity: 0.4;filter:alpha(opacity=40);background: black;}
 .infoList{position: absolute;left: 10px;bottom: 10px;z-index: 30;margin-bottom: 64px;}
  .infoList li{display: none;}
  .infoList .infoOn{display: inline;color: white;}
  .indexList{position: absolute;right: 10px;bottom: 5px;z-index: 30;margin-bottom: 0;width:95%;}
  .indexList li{float: right;margin-right: 5px;padding: 2px 4px;border: 2px solid black;background: grey;cursor: pointer;color: white;}
  .indexList .li3{float:left;border:1px solid #08c;background-color: #08c;}
  .indexList .li1,.indexList .li2{float:right;}
  .indexList .indexOn{background: red;font-weight: bold;color: white;}
  .w-fork{position: absolute;top: 0px;right: 0px;z-index: 9999;}
  .div-con1{text-align:left}
  .w-control{margin-left:25%;}
  .fileload{margin-bottom:20px;}
 .span12 .w_control_label{width: 100px;margin-left: 0;text-align:left;}
.w-upload-ul li{height: 30px;line-height: 24px;}
.w-upload-ul li:first-child{margin-top:9px;}
  .w-add{position: absolute;padding-left: 10px;padding-top: 3px;}
 @media (min-width: 1200px){
.row-fluid .span2{width:18% !important}
.row-fluid .span9{width:72% !important}
}
.z-a2{width:120px;margin-left:15px;}
.w-weui_dialog{height:300px;}

</style>
</head>
<body>
<!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
<form id="file_upload_id" action="${ctx}/updateClientOrderById.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
<input name="orderId" id="order_id" value="${clientOrder.orderId}" type="hidden">
<input name="userid" id="userid"  value="${clientOrder.userid}" type="hidden">
<input name="clientOrderId"  value="${clientOrder.id}" type="hidden">
<input name="selectDrawingName" id="drawing_name" type="hidden"> 
<input id="pic_index" type="hidden"> 
<jsp:include page="base.jsp"></jsp:include>
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="/supplier/queryAllClientOrder.do"  class="tip-bottom"><i class="icon  icon-paste"></i>客户订单管理</a> <a class="current">订单详情</a>
    </div>
      <div class="shu">
          <div class="bianhaolan">
              <p class="bian">
                  客户ID : ${clientOrder.userid}
              </p>
             <!-- <div class="fapiao">
                  <label>客户ID :</label>
                  <input type="text"  placeholder="">
              </div>-->
              <p class="bianhao">
                  客户名称 : ${user.userName}
              </p>
              <p class="bianhao">
              <button class="btn btn-info" onclick="queryClientOrder('${clientOrder.userid}')">查看该客户订单</button>
              </p>             
              <p class="bianhao z-btn-p">
              <button class="btn btn-primary z-btn-save">保存</button>
              <button class="btn btn-primary z-btn-edit">编辑</button>
              <button class="btn btn-success z-btn-cancel">取消</button>
              </p>             
          </div>
<!--               <div class="pull-right"> -->
<!-- 		         <a href="javascript:;" class="div-btn"><button class="btn btn-primary z-btn-edit" id="save_clientOrder">保存</button></a> -->
<!-- 		     </div>       -->
<!-- 		     <div class="pull-right"> -->
<!-- 		             <button type="submit" class="btn btn-primary z-btn-edit" id="edit_clientOrder">编辑</button> -->
<!-- 		             <button type="submit" class="btn btn-success z-btn-edit" id="cancel_clientOrder" style="margin-right:15px;">取消</button> -->
<!-- 		     </div> -->
      </div>		 
  </div>
    <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">订单基本信息</h1>
            </div>
        </div>
        <div class="row-fluid">
        	<div class="span12" style="margin-left: 0;">
        		<div class="span3" style="margin-left: 0;">
			        <div class="control-group" style="margin-bottom: 10px;">
			            <p class="group-p">项目编号 : <span id="orderId">${clientOrder.orderId}</span></p>
			        </div>
			          <div class="control-group">
			            <label class="control-label">PO编号: </label>
			            <div class="controls w_controls_div">
			                <input class="z-no-border" type="text" name="poNumber" value="${clientOrder.poNumber}" disabled=false;>
			            </div>
			        </div>
		        </div>
		        <div class="span3" style="margin-left: 0;">
			        <div class="control-group">
			            <label class="control-label">项目名称: </label>
			            <div class="controls w_controls_div">
			                <input class="z-no-border" type="text" name="projectName" id="project_name" value="${clientOrder.projectName}" disabled=false;>
			            </div>
			        </div>
			        <div class="control-group">
			            <label class="control-label">到款时间: </label>
			            <div class="controls w_controls_div">
			                <input class="z-no-border" type="text" name="paymentReceived" value="${clientOrder.paymentReceived == null ? firstPayment : clientOrder.paymentReceived}" disabled=false; id="payment_received" data-date="2016-01-02" data-date-format="dd-mm-yyyy"  onchange="select_milestone(this)"   onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
			            </div>
			        </div>
		        </div>
		        <div class="span3" style="margin-left: 0;">
			        <div class="control-group">
			            <label class="control-label">订单时间: </label>
			            <div class="controls w_controls_div">
			                <input class="z-no-border" type="text" name="createTime" id="create_time" value="${clientOrder.createTime}" disabled=false;  placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
			            </div>
			        </div>
			        <div class="control-group">
			            <label class="control-label">交期时间: </label>
			            <div class="controls w_controls_div">
			                <input class="z-no-border" type="text" name="deliveryTime" value="${clientOrder.deliveryTime}" disabled=false; id="date_input2" data-date="2016-01-02" data-date-format="dd-mm-yyyy" placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
			            </div>
			        </div>
		        </div>
		        <div class="span3" style="margin-left: 0;">
			        <div class="control-group">
			            <label class="control-label">当前状态: </label>
			            <div class="controls w_controls_div">
			                <input id="client_orderStatus"  name="orderStatus" value="${clientOrder.orderStatus}" type="hidden">
			                <select class="xiala_select" id="order_status" disabled onchange="select_FinishedDate()">
			<!--                     <option value="0"></option> -->
			                    <option value="0" <c:if test="${clientOrder.orderStatus == 0}"> selected="selected"</c:if>>In Process</option>
			                    <option value="1" <c:if test="${clientOrder.orderStatus == 1}"> selected="selected"</c:if>>Finished</option>
			                </select>
			            </div>
			        </div>
			         <div class="control-group">
			            <label class="control-label">订单类型: </label>
			            <div class="controls w_controls_div">
			                <select class="xiala_select"  name="orderTypeId" disabled>
			                      <option value="5"></option>
			                      <c:forEach var="obj" items="${types}" varStatus="i">
			                      <option value="${obj.id}"<c:if test="${obj.id eq clientOrder.orderTypeId}"> selected="selected"</c:if>>${obj.orderType}</option>
			                      </c:forEach>
			                </select>
			            </div>
			        </div>
			    </div>
       </div>
     </div>
 </div>
    

    <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">产品信息</h1>
            </div>
        </div>
        <a href="javascript:;" class="cancelButton"><button class="btn btn-info" style="margin:15px 0px 15px 0px;">新增</button></a>
        <div class="row-fluid">
            <div class="span12">
                <div class="widget-box">

                    <div class="widget-content nopadding">
                        <div id="DataTables_Table_0_wrapper" class="dataTables_wrapper" role="grid">
                            <table class="table table-bordered data-table dataTable" id="DataTables_Table_0">
                                <thead>
                                <tr role="row">
                                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Platform(s): activate to sort column ascending">
                                        <div class="DataTables_sort_wrapper">产品名称
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>                      
                                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending">
                                        <div class="DataTables_sort_wrapper">单价
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>
                                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending">
                                        <div class="DataTables_sort_wrapper">数量
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>
                                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending">
                                        <div class="DataTables_sort_wrapper">模具价格
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>
                                    <th class="ui-state-default" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" aria-label="Engine version: activate to sort column ascending">
                                        <div class="DataTables_sort_wrapper">操作
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody role="alert" aria-live="polite" aria-relevant="all" id="product_tbody">  
                                <c:forEach var="obj" items="${list}" varStatus="i">  
                                <tr class="gradeA odd">
                                    <td>${obj.productName}</td>
                                    <td>${obj.unitPrice}</td>
                                    <td>${obj.quantity}</td>
                                    <td>${obj.moldPrice}</td>
                                    <td class="center">
                                        <a><button class="btn btn-primary" onclick="editDrawing('${obj.id}')">编辑</button></a>
                                        <a><button class="btn btn-primary" style="margin-left: 10px;" onclick="download_drawing('${obj.id}')">下载图纸</button></a>
                                        <a><button class="btn btn-danger" style="margin-left: 10px;" onclick="removeDrawing(this,${obj.id});">删除</button></a>
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
        
        <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">里程碑设置</h1>
                
            </div>
        </div>
        <a href="javascript:;" class="w-cancelButton"><button class="btn btn-info" style="margin:15px 0;" id="add_milestone">新增里程碑</button></a>
        <div class="row-fluid">
       
            <div class="span12">
                <div style="width:100%;height: 100px;">
                <ol class="ui-step ui-step-3" style="padding-left: 20px;" id="milestone_ol">
					<div class="ui-step-line2 z-step-line2">	
						<div class="w-item dot-right2"></div>	
						<span class="w-img z-img z-font"></span>	
					</div>
					 <%-- <c:forEach var="obj" items="${list1}" varStatus="i"> 
					 <div class="w-png">
                         <span style="float:right;">1/${productionPhotoTimeline.get(i.count-1).size()}</span>
                         <img class="w-div-png" src="${productionPhotoTimeline.get(i.count-1).size() == 0 ? '' : productionPhotoTimeline.get(i.count-1).get(0).photoPathCompress}" onclick="show_photos('${obj.orderId}','${obj.uploadDate}');"/>
                         <span style="display:none;">${obj.uploadDate}</span>
                     </div>  
                     <div class="w-png1">  
                         <textarea rows="2" cols="1" style="width:105px;">${obj.remarks == null ? '' : obj.remarks}</textarea>
                      </div>
                      </c:forEach> --%>
                        <c:forEach var="obj" items="${milestones}" varStatus="i">  
                        <li class="step-active">
                            <div class="ui-step-line"></div>
                            <div class="ui-step-cont">
                                <span class="ui-step-cont-number" disabled=false;>${obj.milestoneDate}</span>
                                <span style="display:none;">${obj.milestoneDate}</span>
                                <span class="ui-step-cont-text z-milestone-span" title="${obj.milestoneName}">${obj.milestoneName}</span><br/>
                                <span style="display:none;">${obj.expectedOrActually}</span>
                                <img class="ui-step-cont-text"  src="img/img2.png"  style="display:none;"/><span style="display:none;">${obj.delayStatus}</span>
                                <span class="ui-step-cont-text">${obj.delayStatus == 1 ? obj.delayRemark : (obj.delayStatus == 2 ? "完成" : "")}</span>                               
                            </div>
        
                            <div class="w-shu" style="display:none;">
                                <table class="w-shu-table">
                                    <tr>
                                        <th><a onclick="complete('${obj.id}','${i.index}')">完成</a></th>
                                    </tr>
                                    <tr>
                                        <th><a onclick="show_delay('${obj.id}','${i.index}',this)">延期</a></th>
                                    </tr>
                                    <tr>
                                        <th><a onclick="edit_milestone('${obj.id}',this)">编辑</a></th>
                                    </tr>
                                    <tr>
                                        <th><a onclick="delete_milestone('${obj.id}','${i.index}')">删除</a></th>
                                    </tr>
                                </table>
                            </div>                      
                        </li>
                        </c:forEach>                     
                    </ol>
                </div>
            </div>
        </div>
    </div>
    
      
       
       
       
      <!-- 周报 --> 
        <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">周报</h1>
                
            </div>
        </div>
        <a href="javascript:;"><button class="btn btn-info" onclick="show_upload_pic()" style="margin:15px 0;">上传周报</button></a>
        <span style="color:red;">(1、多张图片请直接全选上传，不要压缩上传.2、支持文档上传，格式xls、xlsx、doc、docx、ppt、pptx、pdf)</span>
        <div class="row-fluid">
       
            <div class="span12">
            
            <ul class="recent-posts w-recent-posts">
              <c:forEach var="obj" items="${list1}" varStatus="i"> 
              <c:if test="${i.index == 0}">
                  <c:if test="${obj.isDocument == 0}">
		              <li>         
		                <span class="z-pro-tl">1/${productionPhotoTimeline.get(i.count-1).size()}</span>     
		                <div class="user-thumb w-user-thumb">
		                <img width="100" height="100"  src="${obj.photoPathCompress == null ? '' : obj.photoPathCompress}" onclick="show_photos('${obj.orderId}','${obj.uploadDate}','${i.index}');"> </div>
		                <div class="article-post"> <span class="user-info"> 上传日期：</span><span>${obj.uploadDate}</span> <a class="z-a2" onclick="open_remark_edit('${i.index}',this)">修改备注</a>
		                  <p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>${obj.remarks == null ? '' : obj.remarks}</span></p>
		                </div>
		              </li>
		           </c:if>   
		            <c:if test="${obj.isDocument == 1}">
		              <li>         
		                <span class="z-pro-tl"></span>     
		                <div class="user-thumb w-user-thumb">
		                <c:if test="${fn:endsWith(obj.documentPath,'.xls') == true || fn:endsWith(obj.documentPath,'.xlsx') == true}">
		                <img width="100" height="100"  src="img/excel.png" onclick="download_document('${obj.id}')"> 
		                </c:if>
		                <c:if test="${fn:endsWith(obj.documentPath,'.docx') == true || fn:endsWith(obj.documentPath,'.doc') == true}">
		                <img width="100" height="100"  src="img/word.png" onclick="download_document('${obj.id}')"> 
		                </c:if>
		                <c:if test="${fn:endsWith(obj.documentPath,'.ppt') == true || fn:endsWith(obj.documentPath,'.pptx') == true}">
		                <img width="100" height="100"  src="img/ppt.png" onclick="download_document('${obj.id}')"> 
		                </c:if>
		                <c:if test="${fn:endsWith(obj.documentPath,'.pdf') == true}">
		                <img width="100" height="100"  src="img/pdf.png" onclick="download_document('${obj.id}')"> 
		                </c:if>
		                </div>
		                <div class="article-post"> <span class="user-info"> 上传日期：</span><span>${obj.uploadDate}</span> <a class="z-a2" onclick="open_remark_edit('${i.index}',this)">修改备注</a>
		                  <p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>${obj.remarks == null ? '' : obj.remarks}</span></p>
		                </div>
		              </li>
		           </c:if>   
              </c:if>
              <c:if test="${i.index == 1}">
	                 <c:if test="${obj.isDocument == 0}">
			              <li>         
			                <span class="z-pro-tl">1/${productionPhotoTimeline.get(i.count-1).size()}</span>     
			                <div class="user-thumb w-user-thumb">
			                <img width="100" height="100"  src="${obj.photoPathCompress == null ? '' : obj.photoPathCompress}" onclick="show_photos('${obj.orderId}','${obj.uploadDate}','${i.index}');"> </div>
			                <div class="article-post"> <span class="user-info"> 上传日期：</span><span>${obj.uploadDate}</span> <a class="z-a2" onclick="open_remark_edit('${i.index}',this)">修改备注</a>
			                  <p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>${obj.remarks == null ? '' : obj.remarks}</span></p>
			                </div>
			              </li>
			           </c:if>   
			            <c:if test="${obj.isDocument == 1}">
			              <li>         
			                <span class="z-pro-tl"></span>     
			                <div class="user-thumb w-user-thumb">
				                    <c:if test="${fn:endsWith(obj.documentPath,'.xls') == true || fn:endsWith(obj.documentPath,'.xlsx') == true}">
					                <img width="100" height="100"  src="img/excel.png" onclick="download_document('${obj.id}')"> 
					                </c:if>
					                <c:if test="${fn:endsWith(obj.documentPath,'.docx') == true || fn:endsWith(obj.documentPath,'.doc') == true}">
					                <img width="100" height="100"  src="img/word.png" onclick="download_document('${obj.id}')"> 
					                </c:if>
					                <c:if test="${fn:endsWith(obj.documentPath,'.ppt') == true || fn:endsWith(obj.documentPath,'.pptx') == true}">
					                <img width="100" height="100"  src="img/ppt.png" onclick="download_document('${obj.id}')"> 
					                </c:if>
					                <c:if test="${fn:endsWith(obj.documentPath,'.pdf') == true}">
					                <img width="100" height="100"  src="img/pdf.png" onclick="download_document('${obj.id}')"> 
					                </c:if>
		                    </div>
			                <div class="article-post"> <span class="user-info"> 上传日期：</span><span>${obj.uploadDate}</span> <a class="z-a2" onclick="open_remark_edit('${i.index}',this)">修改备注</a>
			                  <p>&nbsp;&nbsp;备&nbsp;&nbsp;&nbsp;注&nbsp;&nbsp;：<span>${obj.remarks == null ? '' : obj.remarks}</span></p>
			                </div>
			              </li>
			           </c:if>   
              </c:if>
              </c:forEach>
             <c:if test="${list1.size() > 2}">
              <li>
                <button class="btn btn-warning btn-mini" onclick="show_all_report('${clientOrder.orderId}',this)">查看所有</button>
              </li>
              </c:if>
            </ul>             
            </div>
        </div>
    </div>
    
    
 <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">上传</h1>
            </div>
        </div>
        <div class="row-fluid">
	        <div class="control-group span12">
	            <label class="control-label span1 w_control_label">PO: </label>
	            <div class="controls span2" style="margin-left:0;">
	                <div class="uploader">
	                    <input type="file" id="file_upload_po" name="file_upload1" size="19" style="opacity: 0;" onChange="doUpload_po(this)"><span class="filename" id="fileName1">No file selected</span><span class="action w-Choose">Choose File</span>                                
	                    <input name="poName" id="getPoPath" value="" type="hidden">
	                </div>
	            </div>
	            <c:if test="${pos.size() == 0}">
	            <div class="w-time span9" style="margin-left:0;display:none;">
	               <ul class="w-upload-ul">
	            	</ul>
	             </div>
	             </c:if>
	            <c:if test="${pos.size() != 0}">
	            <div class="w-time span9" style="margin-left:0;">
	            	<ul class="w-upload-ul">
	            	<c:forEach var="obj" items="${pos}" varStatus="i">
	            		<li>
	            		 <div class="row-fluid" style="margin-top:0;">   
					       <div class="control-group span12">
					          <div class="span3">
	            			<span style="color:#666;float: left;">文件名：</span><a class="z-a2" onclick="doDownload_po('${obj.id}')">${obj.poPath}</a>
	            			</div> 
	            			<span style="color:#666;margin-left: 25px;">上传日期：${obj.uploadDate}</span>  
	                        <a class="z-a3" onclick="delete_po('${obj.id}',this)">删除</a>
	                      </div>
	                     </div> 
	            		</li>
	            	</c:forEach>
	            	</ul>
	            </div>
	           </c:if>
	        </div>
        </div>
        <div class="row-fluid">
	        <div class="control-group span12">
	            <label class="control-label span1 w_control_label">QC Report: </label>
	            <div class="controls span2" style="margin-left:0;">
	                <div class="uploader">
	                    <input type="file" id="file_upload_qc" name="file_upload3" size="19" style="opacity: 0;" onChange="doUpload_qc(this)"><span class="filename" id="fileName3">No file selected</span><span class="action">Choose File</span>
	                    <input name="qcName" id="getQcPath" value="" type="hidden">
	                </div>
	            </div>
	           
	           <c:if test="${qcReports.size() == 0}">
	            <div class="w-time span9" style="margin-left:0;">
	            	<ul class="w-upload-ul">
	            	</ul>
	            </div>
	           </c:if>
	           <c:if test="${qcReports.size() != 0}">
	            <div class="w-time span9" style="margin-left:0;">
	            	<ul class="w-upload-ul">
	            	<c:forEach var="obj" items="${qcReports}" varStatus="i">
	            		<li>
	            		 <div class="row-fluid" style="margin-top:0;">   
					       <div class="control-group span12">
					          <div class="span3">
	            			<span style="color:#666;float: left;">文件名：</span><a class="z-a2" onclick="doDownload_qc('${obj.id}')">${obj.qcReportPath}</a>
	            			</div> 
	            			<span style="color:#666;margin-left: 25px;">上传日期：${obj.uploadDate}</span>  
	                        <a class="z-a3" onclick="delete_QcReport('${obj.id}',this)">删除</a>
	                      </div>
	                     </div> 
	            		</li>
	            	</c:forEach>
	            	</ul>
	            </div>
	           </c:if>
	        </div>
        </div>
        <div class="row-fluid">
		        <div class="control-group span12">
		            <label class="control-label span1 w_control_label">Shipping Doc: </label>		            
		            <div class="controls span2" style="margin-left:0;">
<!-- 		                <div class="uploader">			            -->
<!-- 		                    <input type="file" id="file_upload_shipping" name="file_upload4" size="19" style="opacity: 0;" onChange="doUpload_shipping(this)" ><span class="filename" id="fileName4">No file selected</span><span class="action">Choose File</span> -->
<!-- 		                    <input name="shippingName" id="getShippingPath" value="" type="hidden"> -->
<!-- 		                </div> -->
                      <button class="btn btn-info" onclick="openShippingDiv()">添加运输</button>
		            </div>		          
		           <c:if test="${shippingDocs.size() == 0}">
		             <div class="w-time span9" style="margin-left:0;">
		            	<ul class="w-upload-ul" id="shipping_ul">		          
		                </ul>
		            </div>
		           </c:if>
		           <c:if test="${shippingDocs.size() != 0}">
		             <div class="w-time span9" style="margin-left:0;">
		            	<ul class="w-upload-ul" id="shipping_ul">
		            	<c:forEach var="obj" items="${shippingDocs}" varStatus="i">
		            	 <li>
		            	  <div class="row-fluid" style="margin-top:0;">   
					       <div class="control-group span12">
					   
		                    <c:if test="${obj.shippingStartDate != '' && obj.shippingStartDate != null}">
		                    <span style="color:#666;">运输开始时间：</span>
		                    <span class="s-date">${obj.shippingStartDate}</span> 
		            		<span style="color:#666;margin-left: 20px;">运输结束时间：</span>
		            		<span class="a-date">${obj.shippingArrivalDate}</span>
		            		<span style="color:#666;margin-left: 20px;">目的港：</span>
		            		<span class="d-port">${obj.destinationPort}</span>
		            		<a class="z-a3" onclick="openShippingDiv('${obj.id}','${i.index}')">修改运输时间</a>
		            		</c:if>		            		
<%-- 		            		<span style="color:#666;margin-left: 25px;">上传日期：${obj.uploadDate}</span>   --%>
		            		<a style="color: #08c;margin-left: 20px;text-decoration: underline;cursor: pointer;" onclick="show_shipping_file('${obj.id}')">上传文件</a>	
		                    <a class="z-a3" onclick="delete_shippingDoc('${obj.id}',this)">删除</a>
		                   </div>
		            	  </div>
		                 </li>
		                 </c:forEach>
		                 </ul>
		            </div>
		           </c:if>
	        </div>
        </div>
    
  </div>  
    

      <!-- 订单结束时间 -->
	  <div class="weui_dialog_alert" id="finished_div" style="display:none;">
	  <div class="weui_mask"></div>	
		<div class="weui_dialog">
			<div class="waimian2" style="padding:0px 20px;">
											<div class="control-group" id="copy_div3">
												<p class="cont-label div-labe6 div-dingd">（请输入订单结束时间）</label>
												<div class="con div-controls" style="margin-left:5%;">
												
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">结束时间： </label>
													<div class="con div-controls span7">
														<input type="text" name="outputTime" id="outputTime" value="${clientOrder.outputTime}"  placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	
														<span style="color:red;font-size: 20px">*</span>
													</div> 
												</div>											
												</div>
												 <button type="submit" class="btn btn-info" style="margin-bottom:20px;" onclick="closeFinishedDiv()">确定</button>
													
											</div>
										</div>
			                           <a class="close-reveal-modal"  onclick="closeFinishedDiv()">×</a>

		 </div>
		 </div>
     <!-- 订单结束时间 -->


    
    <!-- 运输计划修改 -->
	  <div class="weui_dialog_alert" id="shipping_div"  style="width: 44%;display:none;">
	  <input type="hidden" id="shipping_doc_id"/>
	  <div class="weui_mask"></div>	
		<div class="weui_dialog">
			<div class="waimian2" style="padding:0px 20px;">
											<div class="control-group" id="copy_div3">
												<p class="cont-label div-labe6 div-dingd">运输计划: （输入开始运输时间，预计结束时间）</label>
												<div class="con div-controls" style="margin-left:5%;">
												
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">开始时间： </label>
													<div class="con div-controls span7">
														<input type="text" name="shippingStartDate" id="shipping_start_date"    placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	
														<span style="color:red;font-size: 20px">*</span>
													</div> 
												</div>
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">到达时间： </label>
													<div class="con div-controls span7">
														<input type="text" name="shippingArrivalDate"  id="shipping_arrival_date"   placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	
														<span style="color:red;font-size: 20px">*</span>
													</div> 
												</div>
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">目的港： </label>
													<div class="con div-controls span7">
														<input type="text"  id="destination_port">	
														<span style="color:red;font-size: 20px">*</span>
													</div> 
												</div>
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">ISF Date： </label>
													<div class="con div-controls span7">
														<input type="text"  id="ISF_date"   placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	
														<span style="font-size: 14px">(没有，则不输入)</span>
													</div> 
												</div>
									         
								                </div>
												 <button type="submit" class="btn btn-info" style="margin-bottom:20px;" onclick="updateShippingDiv()">确定</button>
													
											</div>
										</div>
			                           <a class="close-reveal-modal" onclick="closeShippingDiv()">×</a>

		 </div>
		 </div>
     <!-- 运输计划（结束） -->
     
         <!-- 增加运输计划 -->
<!-- 	  <div class="weui_dialog_alert" id="shipping_div"  style="width: 44%;display:none;"> -->
<!-- 	  <input type="hidden" id="shipping_doc_id"/> -->
<!-- 	  <div class="weui_mask"></div>	 -->
<!-- 		<div class="weui_dialog"> -->
<!-- 			<div class="waimian2" style="padding:0px 20px;"> -->
<!-- 											<div class="control-group" id="copy_div3"> -->
<!-- 												<p class="cont-label div-labe6 div-dingd">运输计划: （输入开始运输时间，预计结束时间）</label> -->
<!-- 												<div class="con div-controls" style="margin-left:5%;"> -->
												
<!-- 												<div class="row-fluid"> -->
<!-- 													<label class="cont-label div-labe6 span5">开始时间： </label> -->
<!-- 													<div class="con div-controls span7"> -->
<%-- 														<input type="text" name="shippingStartDate" id="shipping_start_date"    placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	 --%>
<!-- 														<span style="color:red;font-size: 20px">*</span> -->
<!-- 													</div>  -->
<!-- 												</div> -->
<!-- 												<div class="row-fluid"> -->
<!-- 													<label class="cont-label div-labe6 span5">到达时间： </label> -->
<!-- 													<div class="con div-controls span7"> -->
<%-- 														<input type="text" name="shippingArrivalDate"  id="shipping_arrival_date"   placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	 --%>
<!-- 														<span style="color:red;font-size: 20px">*</span> -->
<!-- 													</div>  -->
<!-- 												</div> -->
<!-- 												<div class="row-fluid"> -->
<!-- 													<label class="cont-label div-labe6 span5">ISF Date： </label> -->
<!-- 													<div class="con div-controls span7"> -->
<%-- 														<input type="text"  id="ISF_date"   placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">	 --%>
<!-- 														<span style="font-size: 14px">(没有，则不输入)</span> -->
<!-- 													</div>  -->
<!-- 												</div> -->
									         
<!-- 								                </div> -->
<!-- 												 <button type="submit" class="btn btn-info" style="margin-bottom:20px;" onclick="addShippingDiv()">确定</button> -->
													
<!-- 											</div> -->
<!-- 										</div> -->
<!-- 			                           <a class="close-reveal-modal" onclick="closeShippingDiv()">×</a> -->

<!-- 		 </div> -->
<!-- 		 </div> -->
     <!-- 运输计划（结束） -->
     
     
     
     
      <div class="weui_dialog_alert" id="document_div"  style="width: 44%;display:none">
	  <input type="hidden" id="shippingId" name="shippingId"/>
	  <div class="weui_mask"></div>	
		<div class="weui_dialog">
			<div class="waimian2" style="padding:0px 20px;">
											<div class="control-group">
												<p class="cont-label div-labe6 div-dingd" style="color:red;">运输文件上传:（指定文件类型，上传文件。如需修改，重新上传。）
												<div class="con div-controls shipping-file" style="margin-left:5%;">																																				
												<div class="row-fluid">
													<label class="cont-label div-labe6 span5">选择上传类型： </label>
													<div class="con div-controls span7">
                                                        <select id="select_file_type" name="fileType">
	                                                        <option></option>
	                                                        <option value="1">BL复印件</option>
	                                                        <option value="2">BL正式件</option>
	                                                        <option value="3">Invoice</option>
	                                                        <option value="4">Packing List</option>
	                                                        <option value="5">FORM A</option>
	                                                        <option value="6">Packaging Declaration</option>
	                                                        <option value="7">Other</option>
                                                        </select>
													</div> 
												</div>
												   <div class="row-fluid">
									                <label class="cont-label div-labe6 span4">文件上传: </label>
									                <div class="con div-controls span8">
									                    <div class="uploader fileload">
									                        <input type="file" size="19" multiple style="opacity: 0;" name="file_upload5" onchange="get_shipping_path(this)">
									                        <input name="shippingName" id="getShippingPath" value="" type="hidden">
									                        <span class="filename">No file selected</span>
									                        <span class="action">Choose File</span>
									                    </div>
									                </div>
									                </div>
									         
								                </div>
												 <button type="submit" class="btn btn-info" style="margin-bottom:20px;" onclick="closeDocumentDiv()">确定</button>
													
											</div>
										</div>
			                           <a class="close-reveal-modal" onclick="closeDocumentDiv()">×</a>
		 </div>
		 </div>
     
     
     
     
     
     



<div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">原始订单需求</h1>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">邮件内容: </label>
            <div class="controls">
               
            </div>
       		<div class="controls">
				<textarea name="orderRequest" style="width:800px;height:200px;" id="order_request">${clientOrder.orderRequest}</textarea>
			</div>
        </div>   
  </div>  
    






    <!-- 消息中心 -->
        <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">订单消息</h1>
                
            </div>
        </div>
     <hr>
        <div class="row-fluid">
            <div class="span12">
                <div class="accordion">
                   <c:forEach var="obj" items="${messageCenters}" varStatus="i">
                    <div class="accordion-group widget-box">
                        <div class="accordion-heading">
                            <div class="widget-title" onclick="show_details('${obj.id}',this)"><a data-parent="" data-toggle=""> <span class="icon"><i class="icon-comments"></i></span>
               
                                  <c:if test="${counts[i.count-1] == 0}">
                                   <p style="color:#b7b3b3;cursor: pointer;">[${obj.userid}]发送订单[${obj.orderId}]一条 ${obj.messageTitle} 消息</p>
                                  </c:if>
                                  <c:if test="${counts[i.count-1] != 0}">
                                   <p style="cursor: pointer;">[${obj.userid}]发送订单[${obj.orderId}]一条 ${obj.messageTitle} 消息</p>
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
                                        <c:if test="${orderMessages.get(i.count-1).get(j.count-1).customerOrFactory == 1}"> 
                                        <div class="w-text-div">
                                            <div class="article-post msg-block">
                                                <div class="fr"><a class="btn btn-primary btn-mini" onclick="reply_message(this)">Reply</a></div>
                                                <span class="w-user-info"> By: [${obj.userid}] / Date: ${orderMessages.get(i.count-1).get(j.count-1).messageSendTime != null ?fn:substring(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,0,fn:indexOf(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,".")):""} </span>
                                                <p style="margin: 0 0 10px;"><a>${orderMessages.get(i.count-1).get(j.count-1).messageDetails}</a> </p>
                                                <c:if test="${orderMessages.get(i.count-1).get(j.count-1).picStatus == 1}"> 
                                                   <c:forEach begin="1" end="${orderMessages.get(i.count-1).get(j.count-1).qualityIssuesPic.size()}" varStatus="k" step="1">  
                                                   <img src="${orderMessages.get(i.count-1).get(j.count-1).qualityIssuesPic.get(k.count-1).picturePathCompress}" style="width: 80px;">
                                                   </c:forEach>
                                                </c:if>
                                            </div>
                                        </div>
                                        </c:if>
                                        <c:if test="${orderMessages.get(i.count-1).get(j.count-1).customerOrFactory == 2}"> 
                                        <div class="w-text-div2">
                                            <div class="w-bg article-post">
                                                 <div class="fr"></div>
                                                <span class="w-user-info"> Reply: [${orderMessages.get(i.count-1).get(j.count-1).username}] / Date: ${orderMessages.get(i.count-1).get(j.count-1).messageSendTime != null ?fn:substring(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,0,fn:indexOf(orderMessages.get(i.count-1).get(j.count-1).messageSendTime,".")):""} </span>
                                                <p class="w-user-p"><a>${orderMessages.get(i.count-1).get(j.count-1).messageDetails}</a> </p>
                                            </div>
                                        </div>
                                        </c:if>                                     
                                        </c:forEach>                                   
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
                </div>
            </div>
        </div>
    
    </div>
    
    
    
<div class="container-fluid">
 <div class="pull-right">
         <a href="javascript:;" class="div-btn"><button class="btn btn-primary z-btn-save">保存</button></a>
     </div>      
     <div class="pull-right">
             <button type="submit" class="btn btn-primary z-btn-edit">编辑</button>
             <button type="submit" class="btn btn-success z-btn-cancel" style="margin-right:15px;">取消</button>
     </div>
</div>
<div class="weui_dialog_alert" id="dialog5" style="display:none;">
    <input type="hidden" id="milestone_id"/>
    <input type="hidden" id="milestone_index"/>
    <div class="weui_mask"></div>
    <div class="weui_dialog" style="width:30%;">
        <div class="waimian">
            <div class="control-group">
                <label class="control-label">延期时间: </label>
                <div class="controls div-con">
                    <input type="text" name="name" id="delay_date" onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						data-date="2013-01-02"
						data-date-format="dd-mm-yyyy" class="datepicker"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">理由: </label>
                <div class="controls div-con">
                    <textarea type="text" name="number" id="delay_remark" style="height: 100px;"></textarea>
                </div>
            </div>
         </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose4()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft w-dialog">
            <a href="javascript:;" class="w-weui_btn_dialog primary" onclick="delay()">确认</a>
        </div>
    </div>
</div>


     
      <!--图片轮播 -->
      <div id="wrapper" style="display:none;">
        <input type="hidden">
        <div class="weui_mask" onclick="close_wrapper()"></div>
       <!--  <div class="w-fork"><img src="images/fork.png" onclick="close_wrapper()"></div> -->
        <div class="w-weui_dialog"><!-- 最外层部分 -->
          <div class="bg"></div> <!-- 图片底部背景层部分-->
	      <ul class="infoList"><!-- 图片左下角文字信息部分 -->
		    
	      </ul>
	      
	      <ul class="indexList"><!-- 图片右下角序号部分 -->
	        <li class="li1"></li>
	        <li class="li2">1/N</li>
	        <li class="li3"><a id="delete_img" style="color:#fff;">删除</a></li>
	      </ul>
		    <div id="banner"><!-- 轮播部分 -->
		    <div class="w-fork"><img src="images/fork.png" onclick="close_wrapper()"></div>
		      <ul class="imgList" id="imgList"><!-- 图片部分 -->
		      
		      </ul>
		      <img src="images/arrow-left.png" width="20px" height="40px" id="prev">
		      <img src="images/arrow-right.png" width="20px" height="40px" id="next">
      
		    </div>
		  </div>         
         </div>


<!-- 添加里程碑 -->
<div class="weui_dialog_alert" id="dialog6" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog" style="width:30%;">
        <div class="waimian">
            <div class="control-group">
                <label class="control-label">里程碑名称: </label>
                <div class="controls div-con">
                    <input type="text" id="milestone_name"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">预计完成日期: </label>
                <div class="controls div-con">
                    <input type="text" id="milestone_date" onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						data-date="2013-01-02"
						data-date-format="dd-mm-yyyy" class="datepicker" value="<%=DateFormat.currentDate()%>"/>
                </div>
            </div>
         </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose2()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft w-dialog">
            <a href="javascript:;" class="w-weui_btn_dialog primary" onclick="add_milestone()">添加</a>
        </div>
    </div>
</div>

<!-- 修改里程碑 -->
<div class="weui_dialog_alert" id="dialog7" style="display:none;">
    <input type="hidden" value="">
    <div class="weui_mask"></div>
    <div class="weui_dialog" style="width:30%;">
        <div class="waimian">
            <div class="control-group">
                <label class="control-label">里程碑名称: </label>
                <div class="controls div-con">
                    <input type="text" id="milestone_edit_name"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">预计完成日期: </label>
                <div class="controls div-con">
                    <input type="text" id="milestone_edit_date" onclick="WdatePicker({skin:'whyGreen',lang:'en'})"
						data-date="2013-01-02"
						data-date-format="dd-mm-yyyy" class="datepicker" value="<%=DateFormat.currentDate()%>"/>
                </div>
            </div>
         </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose7()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft w-dialog">
            <a href="javascript:;" class="w-weui_btn_dialog primary" onclick="update_milestone()">修改</a>
        </div>
    </div>
</div>






<div class="weui_dialog_alert" id="dialog2" style="display:none;">
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
                    <input type="text" name="unitPrice" id="unit_price" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">数&nbsp;&nbsp;量: </label>
                <div class="controls div-con">
                    <input type="text" name="quantity" id="quantity" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">模具价格: </label>
                <div class="controls div-con">
                    <input type="text" name="moldPrice" id="mold_price" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">上传图纸: </label>
                <div class="controls div-con">
                    <div class="uploader">
                      <input type="file" id="file_upload_drawing" name="file_upload_drawing" size="19" style="opacity: 0;" onChange="doUpload_drawing()"><span class="filename" id="fileName5">No file selected</span><span class="action">Choose File</span>               
                      <input name="drawingName" id="getDrawingPath" value="" type="hidden">
                    </div>
                </div>
            </div>
          </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog primary" onclick="saveClientDrawing()">添加</a>
        </div>
    </div>
</div>

<div class="weui_dialog_alert" id="dialog1" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="waimian">
            <div class="control-group">
                <label class="control-label div-label2">产品名称: </label>
                <div class="controls div-con">
                    <input type="text" name="productName1" id="product_name1">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">单&nbsp;&nbsp;价: </label>
                <div class="controls div-con">
                    <input type="text" name="unitPrice1" id="unit_price1" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">数&nbsp;&nbsp;量: </label>
                <div class="controls div-con">
                    <input type="text" name="quantity1" id="quantity1" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">模具价格: </label>
                <div class="controls div-con">
                    <input type="text" name="moldPrice1" id="mold_price1" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label div-label2">上传图纸: </label>
                <div class="controls div-con">
                    <div class="uploader">
                    <input type="file" id="file_upload_drawing1" name="file_upload_drawing" onChange="doUpload_drawing1()" size="19" style="opacity: 0;">
                    <span class="filename" id="fileName6">No file selected</span>
                    <span class="action">Choose File</span>               
                    <input name="drawingName1" id="getDrawingPath1" value="" type="hidden">
                    </div>
                </div>
            </div>
          </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose1()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog primary" onclick="updateClientDrawing()">修改</a>
            <input name="id" id="drawing_id" value="" type="hidden">
        </div>
    </div>
</div>
<div class="weui_dialog_confirm" id="dialog3" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提交成功</strong></div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_log default" style="border-right: 1px solid #D5D5D6;" id="back_button">确定</a>
        </div>
    </div>
</div>
</div>
<div class="row-fluid">
  <div id="footer" class="span12"> 

  </div>
</div>
</form>

<!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
<form id="file_upload_id1" action="${ctx}/saveProductionPhoto.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
<div class="weui_dialog_alert" id="dialog4" style="display:none;">
    <input type="hidden" name ="milestoneId"  id="milestone_id1"/>
    <input type="hidden" name = "milestone_index1" id="milestone_index1"/>
    <input name="orderId1"  value="${clientOrder.orderId}" type="hidden">
	<input name="clientOrderId1"  value="${clientOrder.id}" type="hidden">
	<input name="pictureNames"  id="upload_pic_names" type="hidden">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="waimian">
           <div class="control-group">
           <div class="con div-controls">
           <div class="row-fluid">
                <label class="cont-label div-labe6 span4">文件上传: </label>
                <div class="con div-controls span8">
                    <div class="uploader fileload">
                        <input type="file" size="19" multiple style="opacity: 0;" name="file_upload4" onchange="show_drawingName(this)">
                        <span class="filename">No file selected</span>
                        <span class="action">Choose File</span>
                    </div>
                </div>
                </div>
                <div class="row-fluid"><textarea name="remark" style="width:230px;height:70px;margin-left:35%;"></textarea> </div>
              </div>
            </div>
            
          </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="disclose5()">×</a>
        <!--<a class="close-reveal-modal">×</a>-->
        <div class="weui_dialog_ft">
            <a class="weui_btn_dialog primary" onclick="saveProductionPhoto('${clientOrder.orderId}',this)">添加</a>
        </div>
    </div>
</div>
</form>



<div class="weui_dialog_alert" id="weekly_remark_edit" style="display:none;" >
    <input type="hidden"/>
    <input type="hidden">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="waimian">
           <div class="control-group">
           <div class="con div-controls">
           <div class="row-fluid">
                <label class="cont-label div-labe6 span4">修改备注: </label>
                </div>
                <div class="row-fluid"><textarea id="remark_edit" style="width:230px;height:70px;margin-left:35%;"></textarea> </div>
              </div>
            </div>
            
          </div>
        <a class="close-reveal-modal" href="javascript:void(0);" onclick="close_remark_edit()">×</a>
        <div class="weui_dialog_ft">
            <a class="weui_btn_dialog primary" onclick="update_remark(this)">修改</a>
        </div>
    </div>
</div>










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

</body>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script>

function check(){var myDate = new Date();
startTime = myDate.getTime();
$(this).attr("disabled", true);

$("#progress").show();
window.setTimeout("getProgressBar()", 1000);
return true;

}




//更新clientOrder po信息
function doUpload_po(obj) {

	var path = $("#file_upload_po").val();
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
			autoClose : 1000
		});

		return false;
	}
	autTime();
	//	   	发送ajax请求,提交form表单    
	$("#file_upload_id")
			.ajaxSubmit(
					{
						type : "post",
						url : "/supplier/insertPo.do",
						dataType : "text",
						success : function(str) {	
							var result = eval('(' + str + ')');
							var po = result.data;
							if(result.state == 0){							
							 $('#fileName1').text(poName);	
							 var ul = $(obj).parent().parent().next().find('ul');					
// 							 $(ul).empty();
							 $(ul).prepend('<li><div class="row-fluid" style="margin-top:0;">'+   
							              '<div class="control-group span12">'+
							              '<div class="span3">'+
                                          '<span style="color:#666;float:left;">文件名：</span><a class="z-a2" onclick="doDownload_po('+po.id+')">'+poName+'</a></div>'+  
					                	  '<span style="color:#666;margin-left: 25px;">上传日期：'+po.uploadDate+'</span>'+  
					                 	  '<a class="z-a3" onclick="delete_po('+po.id+',this)">删除</a></div></div></li>');
							 $(obj).parent().parent().next().show();
							 
							 
							}else{
								easyDialog.open({
									container : {
										header : '  Prompt message',
										content : ' 上传失败，请重试'
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
											content : ' 上传失败，请重试'
										},
										drag : false,
										overlay : false,
										autoClose : 1000
									});
							 $('#show_upload_dialog').hide();	
						}
					});
}

//上传QC Report
function doUpload_qc(obj) {

	var path = $("#file_upload_qc").val();
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
			autoClose : 1000
		});

		return false;
	}
	autTime();
	//发送ajax请求,提交form表单    
	$("#file_upload_id")
			.ajaxSubmit(
					{
						type : "post",
						url : "/supplier/insertQcReport.do",
						dataType : "text",
						success : function(str) {
							var result = eval('(' + str + ')');
							var qcReport = result.data;
							if(result.state == 0){															 		
							 $('#fileName3').text(qcName);	
							 var ul = $(obj).parent().parent().next().find('ul');					
							 $(ul).prepend('<li><div class="row-fluid" style="margin-top:0;">'+   
								           '<div class="control-group span12">'+
								           '<div class="span3">'+
							               '<span style="color:#666;float:left;">文件名：</span><a class="z-a2" onclick="doDownload_qc('+qcReport.id+')">'+qcName+'</a>'+  
							               '</div>'+
					                	   '<span style="color:#666;margin-left: 25px;">上传日期：'+qcReport.uploadDate+'</span>'+  
					                 	   '<a class="z-a3" onclick="delete_QcReport('+qcReport.id+',this)">删除</a></div></div></li>');
							 $(obj).parent().parent().next().show();
							 
								
							}else{
								easyDialog.open({
									container : {
										header : '  Prompt message',
										content : '  上传失败，请重试'
									},
									drag : false,
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
										drag : false,
										overlay : false,
										autoClose : 1000
									});
							 $('#show_upload_dialog').hide();	
						}
					});
}

//上传 shipping Doc信息
// function doUpload_shipping(obj) {

// 	var path = $("#file_upload_shipping").val();
// 	sppath = path.split("\\");
// 	var shippingName = sppath[sppath.length - 1];
// 	$('#getShippingPath').val(shippingName);
// 	if (path.length == 0) {
// 		easyDialog.open({
// 			container : {
// 				header : '  Prompt message',
// 				content : '  Please select a file !'
// 			},
// 			drag : false,
// 			overlay : false,
// 			autoClose : 1000
// 		});

// 		return false;
// 	}
// 	autTime();
// 	//	   	发送ajax请求,提交form表单    
// 	$("#file_upload_id")
// 			.ajaxSubmit(
// 					{
// 						type : "post",
// 						url : "/supplier/insertShippingDoc.do",
// 						dataType : "text",
// 						success : function(str) {
// 							var result = eval('(' + str + ')');
// 							var shippingDoc = result.data;
// 							if(result.state == 0){															 		
// 								 $('#fileName4').text(shippingName);	
// 								 var ul = $(obj).parent().parent().next().find('ul');					
// 								 $(ul).prepend('<li><div class="row-fluid" style="margin-top:0;">'+   
// 						                       '<div class="control-group span12">'+
// 						                       '<div class="span3">'+
//                                                '<span style="color:#666;float:left;">文件名：</span><a class="z-a2" onclick="doDownload_shippingDoc('+shippingDoc.id+')">'+shippingName+'</a></div>'+  
// 						                	   '<span style="color:#666;margin-left: 25px;">上传日期：'+shippingDoc.uploadDate+'</span>'+  
// 						                 	   '<a class="z-a3" onclick="delete_shippingDoc('+shippingDoc.id+',this)">删除</a>'+
// 						                 	   '<span style="color:#666;margin-left: 25px;">运输开始时间：</span><span></span>'+ 
// 				    		            		'<span style="color:#666;margin-left: 25px;">运输结束时间：</span><span></span>'+
// 				    		            		'<a class="z-a3" onclick="openShippingDiv('+shippingDoc.id+',0)">修改</a></div></div></li>');
// 								 $(obj).parent().parent().next().show();	
// 								 setTimeout(openShippingDiv(shippingDoc.id,0),2000);
// 							}else{
// 								easyDialog.open({
// 									container : {
// 										header : '  Prompt message',
// 										content : '  上传失败，请重试'
// 									},
// 									overlay : false,
// 									autoClose : 1000
// 								});
// 								 $('#show_upload_dialog').hide();	
// 							}
// 						},
// 						error : function() {
// 							easyDialog
// 									.open({
// 										container : {
// 											header : '  Prompt message',
// 											content : '  上传失败，请重试'
// 										},
// 										overlay : false,
// 										autoClose : 1000
// 									});
// 							 $('#show_upload_dialog').hide();	
// 						}
// 					});
// }


//下载图纸
function download_drawing(id){
	
	//使用ajax提交下载
	$.ajax({
		url:"/supplier/fileDownload_order_drawing.do",
		data:{
			  "id":id
			  },
		type:"post",
		dataType:"text",
		success:function(res){
			
			window.location.href = "/supplier/fileDownload_order_drawing.do?id="+id;
			
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


//查看历史订单
function queryClientOrder(customerId){
	window.location.href = "/supplier/queryAllClientOrder.do?userName="+customerId;				
}




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
	if($(obj).parents(".chat-messages").next().css('display') == 'none'){
		$(obj).parents(".chat-messages").next().show();
		$(obj).parents(".chat-messages").next().find('input').focus();		
	}else{
		$(obj).parents(".chat-messages").next().hide();
	}

}

//保存回复消息	
function send_message(messageCenterId,obj){
   var message = $(obj).next().find("input").val();		
   if(message.trim() == null || message.trim() == "" || message.trim() == "undefined"){
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
                                      ' <p class="w-user-p"><a>'+orderMessage.messageDetails+'</a></p>'+
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


	//关闭修改weekly report备注
	function close_remark_edit(){
		$('#remark_edit').val();
		$('#weekly_remark_edit').hide();		
	}
   //打开修改weekly report备注
    function open_remark_edit(index,obj){
	     
	    $('#weekly_remark_edit').find('input:first').val($(obj).prev().text());
	    $('#weekly_remark_edit').find('input:eq(1)').val(index);
	    $('#remark_edit').val($(obj).next().find('span').text());
    	$('#weekly_remark_edit').show();	
   }
   //修改备注
   function update_remark(obj){
	   $(obj).css({"color":"#666"}).attr("disabled","true");
	   var uploadDate = $('#weekly_remark_edit').find('input:first').val();
	   var orderId = $('#order_id').val(); 
	   var remark = $('#remark_edit').val();
	   var imgIndex = $('#weekly_remark_edit').find('input:eq(1)').val();
	   if(uploadDate == null || uploadDate == ""){
		   return false;
	   }
	   if(orderId == null || orderId == ""){
		   return false;
	   }
	   $.post("/supplier/updateWeeklyReportRemark.do", 
				{
			     "uploadDate": uploadDate,
			     "orderId" : orderId,
			     "remark" : remark
				},
				function(result){
			     $(obj).css({"color":"#006dcc"}).removeAttr("disabled");
			     if(result.state == 0){			    	 
			    	 $('.w-recent-posts').find('li').eq(imgIndex).find('span:last').text(remark);			    	 
			    	 easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '修改成功 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			    	 close_remark_edit();
			    	 
			     }else{
			    	 easyDialog.open({
						  container : {
						    header : 'Prompt message',
						    content : '更新失败 '
						  },
			    		  overlay : false,
			    		  autoClose : 1000
						});
			    	 $(obj).css({"color":"#006dcc"}).removeAttr("disabled");
			  }
	     });		 
	   
   }
    
 //选择第一次付款时间同时,填写里程碑名
function select_milestone(obj){
	   var receivedDate = $(obj).val();
	   var orderId = $('#order_id').val(); 
	   
	   $.post("/supplier/queryByMilestoneName.do", 
				{
			     "milestoneName": 'Payment Received',
			     "orderId" : orderId
				},
				function(result){
					if(result.state == 0){
						var str = result.data;
						if(str == null || str == ''){
							$('#milestone_name').val('Payment Received');
							$('#milestone_date').val(receivedDate);
							$('#dialog6').show();	
						}else{
							$('#dialog7').find('input:first').val(str);
							$('#milestone_edit_name').val('Payment Received');
							$('#milestone_edit_date').val(receivedDate);
							$('#dialog7').show();	
						}
						
					}else{
						 easyDialog.open({
							  container : {
							    header : 'Prompt message',
							    content : '获取失败 '
							  },
				    		  overlay : false,
				    		  autoClose : 1000
							});
					}
				})
	   
	   
}

 
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
/*
 *下载ShippingDoc数据
 */
 function doDownload_shippingDoc(id){
     	//使用ajax提交下载
		$.ajax({
			url:"/supplier/fileDownload_shipping.do",
			data:{
				  "id":id
				  },
			type:"post",
			dataType:"text",
			success:function(res){
				
				window.location.href = "/supplier/fileDownload_shipping.do?id="+id;
				
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




//上传 shipping Doc信息
 function get_shipping_path(obj) {
	
	var fileType = $('#select_file_type').val();
	var typeName = $('#select_file_type').find('option:selected').text();
	if(fileType == '' || fileType == null || fileType == undefined){
		easyDialog.open({
 			container : {
 				header : '  Prompt message',
 				content : '  请先选择上传文件类型'
 			},
 			drag : false,
 			overlay : false,
 			autoClose : 1000
 		});
		return false;
	}
 	var path = $(obj).val();
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
 			autoClose : 1000
 		});

 		return false;
 	}
 	autTime();
 	//	   	发送ajax请求,提交form表单    
 	$("#file_upload_id")
 			.ajaxSubmit(
 					{
 						type : "post",
 						url : "/supplier/updateShippingFile.do",
 						dataType : "text",
 						success : function(str) {
 							var result = eval('(' + str + ')');
 							var shippingDoc = result.data;
 							if(result.state == 0){	
 							    var shippingDoc = result.data;
 						    	 $('.shipping-file').find('.file-name').remove();
 						
 						    	 if(!(shippingDoc.blpath == null || shippingDoc.blpath == '' || shippingDoc.blpath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">BL正式件:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.blpath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.bluploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 						    	 if(!(shippingDoc.invoicePath == null || shippingDoc.invoicePath == '' || shippingDoc.invoicePath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">Invoice:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.invoicePath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.invoiceUploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 						    	 if(!(shippingDoc.packingListPath == null || shippingDoc.packingListPath == '' || shippingDoc.packingListPath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">Packing List:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.packingListPath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.packingListUploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 						    	 if(!(shippingDoc.formAPath == null || shippingDoc.formAPath == '' || shippingDoc.formAPath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">FORM A:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.formAPath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.formAUploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 						    	 if(!(shippingDoc.blcopyPath == null || shippingDoc.blcopyPath == '' || shippingDoc.blcopyPath == undefined)){
 								    	 var div = '<div class="row-fluid file-name">'+
 											'<label class="cont-label div-labe6 span5">BL复印件:</label>'+
 											'<div class="con div-controls span7">'+
 												'<span>'+shippingDoc.blcopyPath+'</span>'+
 												'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.blcopyUploadDate+'</span>'+
 											'</div>'+
 								          '</div>';
 								    	 $('.shipping-file').append(div); 
 							    	 }
 						    	 if(!(shippingDoc.packagingPath == null || shippingDoc.packagingPath == '' || shippingDoc.packagingPath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">Packaging Declaration:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.packagingPath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.packagingUploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 						    	 if(!(shippingDoc.otherPath == null || shippingDoc.otherPath == '' || shippingDoc.otherPath == undefined)){
 							    	 var div = '<div class="row-fluid file-name">'+
 										'<label class="cont-label div-labe6 span5">Other:</label>'+
 										'<div class="con div-controls span7">'+
 											'<span>'+shippingDoc.otherPath+'</span>'+
 											'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.otherUploadDate+'</span>'+
 										'</div>'+
 							          '</div>';
 							    	 $('.shipping-file').append(div); 
 						    	 }
 					
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



//显示运输文件
function show_shipping_file(id){
	$('#shippingId').val(id);
	$.post("/supplier/queryShippingById.do", 
			{ "id": id},
			function(result){						
		     if(result.state == 0){	
		         var shippingDoc = result.data;
		    	 $('.shipping-file').find('.file-name').remove();
		    	 if(!(shippingDoc.blpath == null || shippingDoc.blpath == '' || shippingDoc.blpath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">BL正式件:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.blpath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.bluploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
		    	 if(!(shippingDoc.invoicePath == null || shippingDoc.invoicePath == '' || shippingDoc.invoicePath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">Invoice:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.invoicePath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.invoiceUploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
		    	 if(!(shippingDoc.packingListPath == null || shippingDoc.packingListPath == '' || shippingDoc.packingListPath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">Packing List:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.packingListPath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.packingListUploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
		    	 if(!(shippingDoc.formAPath == null || shippingDoc.formAPath == '' || shippingDoc.formAPath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">FORM A:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.formAPath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.formAUploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
		    	 if(!(shippingDoc.blcopyPath == null || shippingDoc.blcopyPath == '' || shippingDoc.blcopyPath == undefined)){
				    	 var div = '<div class="row-fluid file-name">'+
							'<label class="cont-label div-labe6 span5">BL复印件:</label>'+
							'<div class="con div-controls span7">'+
								'<span>'+shippingDoc.blcopyPath+'</span>'+
								'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.blcopyUploadDate+'</span>'+
							'</div>'+
				          '</div>';
				    	 $('.shipping-file').append(div); 
			    	 }
		    	 if(!(shippingDoc.packagingPath == null || shippingDoc.packagingPath == '' || shippingDoc.packagingPath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">Packaging Declaration:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.packagingPath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.packagingUploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
		    	 if(!(shippingDoc.otherPath == null || shippingDoc.otherPath == '' || shippingDoc.otherPath == undefined)){
			    	 var div = '<div class="row-fluid file-name">'+
						'<label class="cont-label div-labe6 span5">Other:</label>'+
						'<div class="con div-controls span7">'+
							'<span>'+shippingDoc.otherPath+'</span>'+
							'<span style="font-size: 14px;margin-left: 10px;">'+shippingDoc.otherUploadDate+'</span>'+
						'</div>'+
			          '</div>';
			    	 $('.shipping-file').append(div); 
		    	 }
	
		    	 
		    	 
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
	$('#document_div').show();
}

//关闭运输文档输入框
function closeDocumentDiv(){
	$('#document_div').hide();
}
//打开新增运输计划窗口 
// function open_shipping(){
// 	$('#shipping_doc_id').val('');
// 	$('#shipping_div').show();
// }





//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
 var o = {
     "M+": this.getMonth() + 1, //月份 
     "d+": this.getDate(), //日 
     "h+": this.getHours(), //小时 
     "m+": this.getMinutes(), //分 
     "s+": this.getSeconds(), //秒 
     "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
     "S": this.getMilliseconds() //毫秒 
 };
 if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
 for (var k in o)
 if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
 return fmt;
}






</script>

</html>