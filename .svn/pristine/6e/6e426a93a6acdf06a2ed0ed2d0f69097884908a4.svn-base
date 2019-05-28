<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
<%@page import="com.cbt.util.WebCookie"%> 
<%@page import="com.cbt.util.DateFormat"%> 
 
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
<head>
<title>ImportX</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrap-responsive.min.css" />
<!--<link rel="stylesheet" href="css/fullcalendar.css" />-->
<!--<link rel="stylesheet" href="css/font-awesome.css" />-->
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
 <link rel="stylesheet" href="css/select2.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" href="css/uniform.css" />
<link rel="stylesheet" href="css/newOrder2.css" />
  <link type='image/x-ico' rel='icon' href='img/favicon.ico' />
<link rel="stylesheet" href="css/ui.css">
 <link rel="stylesheet" href="css/ui.progress-bar.css">
 <link rel="stylesheet" href="css/upload-base.css">
 <link rel="stylesheet" href="css/easydialog.css" />

    <script type="text/javascript" src="js/upload-base.js"></script>
    <script type="text/javascript" src="js/easydialog.min.js"></script>
    <script type="text/javascript" src="js/number.js"></script>
    <script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/jquery-form.js"></script>
    <script type="text/javascript">
    
         
        $(function(){
        	
        	 $('#sidebar').find('li').eq(2).addClass('active');	
        	
            //禁止enter事件
            document.onkeydown = function () {
                if (window.event && window.event.keyCode == 13) {
                    window.event.returnValue = false;
                }
               }
  		
//             $('.cancelButton button').on('click',function () {
//                 $('#dialog2').show().on('click', '.weui_btn_dialog', function () {
//                     $('#dialog2').off('click').hide();
//                 });

//             });
//         })
//         function disclose(){
//             $('#dialog2').hide();
        })
    </script>

    <script type="text/javascript">
        
       var flag = false;
        
       function doUpload_po(){
    	 
    	   var path =  $("#file_upload_po").val();	
      	  if(path == null || path == "" || path == undefined){
      		  return false;
      	  }
   		  sppath = path.split("\\");
   		  var poName = sppath[sppath.length-1];
   		  $('#fileName1').text(poName);
   		  $('#getPoPath').val(poName);	
   		  
   		  if(poName != null && poName != '' && poName != undefined){
   			 autTime(); 
          	 $('#upload_title').children().text("PO上传进度");
   		  }
   		  
   		  //先上传后获取上传文件路径
   		 $("#file_upload_id2").ajaxSubmit({    			
      			type: "post",
      			url: "/supplier/uploadAttachment.do",
      	     	dataType: "text",
      	     	success: function(str){
      	     	var result = eval('(' + str + ')');	
      	     	if(result.state == 0){
  	 	             $('#upload_po_path').val(result.data);  
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

       //获取上传的文件名
       function doUpload_drawing(obj){
    	   var tr = this.getRowObj(obj);
      	  var path = $(tr).children("td:eq(4)").find('input:eq(0)').val();
      	  if(path == null || path == "" || path == undefined){
      		  return false;
      	  } 	  
      	  sppath = path.split("\\");
      	  var drawingName = sppath[sppath.length-1];
      	  $(tr).children("td:eq(4)").find('span:eq(0)').text(drawingName);
      	  $(tr).children("td:eq(4)").find('input:eq(1)').val(drawingName);
      	  $('#drawing_name').val(drawingName);
      	  
      	  if(drawingName != null && drawingName != '' && drawingName != undefined){
    			 autTime(); 
           	 $('#upload_title').children().text("图纸上传进度");
    		  }
    		  
    		 $("#file_upload_id1").ajaxSubmit({    			
       			type: "post",
       			url: "/supplier/uploadAttachmentAndChangeName.do",
       	     	dataType: "text",
       	     	success: function(str){
       	     	var result = eval('(' + str + ')');	
       	     	if(result.state == 0){
       	        	  var select = $(tr).children("td:eq(4)").find('select');
  	     	       	  var length = $(select).find("option").length;
  	     	       	  var tl = $("#product_tbody").find("tr").length;	   	  
     	   	       	      for (var i = 0; i < tl; i++) {
     	   	       	       $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('select').append("<option value='"+result.data+"'>"+drawingName+"</option>");
     	   	       	      }  	     		
   	 	             $(obj).parent().find('input:last').val(result.data);  
   	 	             
   	 	             
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
 
       
           
        
                 
           //删除图纸  
         
		   //得到行对象  
			function getRowObj(obj)  
			{  
			var i = 0;  
			while(obj.tagName.toLowerCase() != "tr"){  
			obj = obj.parentNode;  
			if(obj.tagName.toLowerCase() == "table")return null;  
			}  
			return obj;  
			}  
			//根据得到的行对象得到所在的行数  
			function getRowNo(obj){  
			var trObj = getRowObj(obj);  
			var trArr = trObj.parentNode.children;  
			for(var trNo= 0; trNo < trArr.length; trNo++){  
			if(trObj == trObj.parentNode.children[trNo]){  
			alert(trNo+1);  
			}  
			}  
			}  
    
			 //移除tr      
			   function removeDrawing(obj){
				if (confirm("是否删除此图纸？")){
				var tr = this.getRowObj(obj);  
				if(tr != null){  
				tr.parentNode.removeChild(tr); 

				}else{  
				throw new Error("the given object is not contained by the table");  
				} 
			   }
			 }
         
	     //点击添加tr		 
	     function increase_tr(){
	    	 
	    	     var tbody = $('#product_tbody');
		    	 var tr =  '<tr class="gradeA odd grade-tr">'+
				             '<td><input type="text" name="number"></td>'+
                             '<td><input type="text" name="number" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/></td>'+
                             '<td><input type="text" name="number" onkeyup="this.value=this.value.replace(/\D/g,\'\')" onafterpaste="this.value=this.value.replace(/\D/g,\'\')"></td>'+
                             '<td><input type="text" name="number" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/></td>'+
				            ' <td class="center">'+
				                ' <select class="xiala_select" name="select">'+
				                        ' <option value="0"></option>'+
				                   '</select>'+
				                     '<div class="uploader" style="background-image: url(img/sprite2.png);">'+
				                        ' <input type="file" id="file_upload_drawing" name="file_upload_drawing" size="19" style="opacity: 0;" onChange="doUpload_drawing(this)">'+
				                        ' <span class="filename" style="width: 50px;">No file selected</span>'+
				                        ' <span class="action" style="width: 50px;background-color: transparent;">Choose</span>'+
				                        ' <input value="" type="hidden">'+
				                     '</div>'+
				                     '<a href="#"><button class="btn btn-danger" onclick="removeDrawing(this)">删除</button></a>'+
				             '</td>'+
				           '</tr>';
			  tr = $(tr);
			  
			  var tl =  $("#product_tbody").find("tr").length;	 
	          if(tl == 0){
	    	     tbody.append(tr);	 
	          }else{
	    		 tbody.append($("#product_tbody").find("tr:last").clone());	 
	          }   	    
			
	
			 
	    	 
	     }		 
	
	     
	    //检查orderId是否合格 
		function checkOrderId(){
			var orderId = $('#orderid_input').val();
    		
    		$.ajax({
				url:"/supplier/checkOrderId.do",
				data:{
					"orderId":orderId
					  },
				type:"post",
				dataType:"text",
				success:function(res){										
					 flag = true;
					},
			    error:function(){
			    	
			    	easyDialog.open({
			    		  container : {
			    		    header : 'Prompt message',
			    		    content : 'OrderId已经存在！'
			    		  },
			    		  overlay : false,
			    		  autoClose : 1000
			    		});
			    	flag = false;
			    }
			});
	    }
	 	
	 	
		//保存clientOrder信息		
		function saveClientOrder(){
			checkOrderId();
			var orderId = $('#orderid_input').val();
			var drawingId = $('#drawingId').val();
			
			if(orderId == '' || orderId == null || orderId == "undefined"){
							easyDialog.open({
								  container : {
								    content : 'orderId不能为空'
								  },
					    		  overlay : false,
					    		  autoClose : 1000
							  });	
							return false;
						}
			
			var poNumber = $('#poNumber').val();
			var userid = $('#userid').val();
			var createTime = $('#date_input1').val();			
			
            //用户名不存在、订单号已存在时不执行保存操作
			if(!(flag)){
				return;
			}
			
            $('#submit_button').css({"background-color":"#666"}).attr("disabled","true");
            var poPath = $('#upload_po_path').val();
           
          //获取表格tr的长度	
   	            var tl = $("#product_tbody").find("tr").length;
   	            var product = "";
	       	    for (var i = 0; i < tl; i++) {
	      		var productName = $("#product_tbody tr:eq(" + i + ")").children("td:eq(0)").find('input').val();
				var price = $("#product_tbody tr:eq(" + i + ")").children("td:eq(1)").find('input').val();
				var quantity = $("#product_tbody tr:eq(" + i + ")").children("td:eq(2)").find('input').val();
				var mold = $("#product_tbody tr:eq(" + i + ")").children("td:eq(3)").find('input').val();
				var drawingName = $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('span:eq(0)').text();
				var drawingName1 = $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('select').val();
				var s =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('input:eq(1)').val();
				
				if(s.length != 0 && s != undefined){
					product += "-" + productName + "%" + "-" + price + "%" + "-" + quantity + "%" + "-" + mold + "%" + "-" + s + ",";
					flag_load = true;
				}else if(s.length == 0 && drawingName1 != undefined && drawingName1 != "0"){
	             	product += "-" + productName + "%" + "-" + price + "%" + "-" + quantity + "%" + "-" + mold + "%" + "-" + drawingName1 + ",";
	             	flag_load = true;
	            }else if(productName != "" && price != "" && quantity != "" && mold != ""){
	             	product += "-" + productName + "%" + "-" + price + "%" + "-" + quantity + "%" + "-" + mold + "%" + "-" + " " + ",";
	            } 	    	 
	  		  }
            
            
             $.post("/supplier/saveClientOrder.do",
                  { 
       			  "orderId": orderId, 
       			  "poNumber": poNumber,
       			  "userid":userid,
       			  "createTime" : createTime,
       			  "userid" : userid,
       			  "poPath" : poPath,
       			  "clientDrawings" : product,
       			  "drawingId" : drawingId
       			  },
       			function(result){
       				  
       				  if(result.state == 0){
         	        	$('#dialog3').show(); 
      	     	        $('#submit_button').css({"background-color":"#006dcc"}).removeAttr("disabled");
       				  }else{
       					easyDialog.open({
	 	       				  container : {
	 	       				    header : 'Prompt message',
	 	       				    content : '提交失败，请重试'
	 	       				  },
	 	       				  overlay : false,
	 	       				  autoClose : 1000
	 	       				}); 
       				   $('#submit_button').css({"background-color":"#006dcc"}).removeAttr("disabled");
       				  }
       			  })             
			 
		}  
		
		//继续跳到reOrder订单
		function create_clientOrder(){
			
			window.location = "/supplier/queryAllReOrder.do";
			
		}
       //返回历史订单
       function back_clientOrder(){
    	   
    	   window.location = "/supplier/queryAllRfqInfo.do";
       }
        
    </script>

</head>
<body>

<jsp:include page="base.jsp"></jsp:include>

<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="/supplier/queryAllClientOrder.do"  class="tip-bottom"><i class="icon  icon-paste"></i>客户订单管理</a> <a href="#" class="current">新建订单</a>
    </div>
    <!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
   <form id="file_upload_id1" action="${ctx}/createClientDrawings.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
   <input name="clientDrawings" id="clientDrawings" type="hidden"> 
      <div class="shu">
          <div class="bianhaolan">
              <div class="fapiao">
                  <label>客户ID :</label>
                  <input type="text" id="userid"  name="userid" value="${user.userid}" placeholder="" readOnly>
              </div>
              <p class="bianhao">
                  客户名称 : <span id="customer_name">${user.userName}</span>
              </p>
          </div>
      </div>
 
    <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">订单基本信息</h1>
            </div>
        </div>
       <script language="javascript">
			function changeF1() {
			   alert($('#makeupCoSe1').val());
			   $('#orderid_input').val($('#makeupCoSe1').val());
			   $('#makeupCoSe1').val('');
			}
		</script>
        <div class="control-group w-control-group">
            <label class="control-label">项目编号: </label>
           
            <div class="controls">
	             <span class="w-controls-span">
					<select name="makeupCoSe1" id="makeupCoSe1" class="w-controls-select" onchange="changeF1();">
					<OPTION id="99999" VALUE="" SELECTED>
					</select>
				 </span>
                <span class="w-controls-span2">
					<input type="text" name="orderId" id="orderid_input" class="w-controls-input" value="" placeholder="请选择或输入" ondblclick="clear_input();" onBlur="checkOrderId()">														
				</span>
            </div>
        </div>
         <div class="control-group">
            <label class="control-label">PO编号: </label>
            <div class="controls">
                <input type="text" name="poNumber" id="poNumber" value="">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">订单时间: </label>
            <div class="controls">
                <input type="text" name="createTime" value="${rfqInfo.createTime}" id="date_input1" data-date="2016-01-02" data-date-format="dd-mm-yyyy" placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
            </div>
        </div>
 </div>
    
    <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">产品信息</h1>
            </div>
        </div>
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
                                        <div class="DataTables_sort_wrapper">上传图纸
                                            <span class="DataTables_sort_icon css_right ui-icon ui-icon-carat-2-n-s"></span>
                                        </div>
                                    </th>
                                </tr>
                                </thead>

                                <tbody role="alert" aria-live="polite" aria-relevant="all" id="product_tbody">

                                <tr class="gradeA odd grade-tr">
                                    <td><input type="text" name="number" value="${rfqInfo.productName}"></td>
                                    <td><input type="text" name="number" value="" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/></td>
                                    <td><input type="text" name="number" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"></td>
                                    <td><input type="text" name="number" value="" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/></td>
                                    <td class="center">
                                        <select class="xiala_select" name="select">
                                              <option value="0"></option>
                                            </select>
                                            <div class="uploader" style="background-image: url(img/sprite2.png);">
                                                <input type="file" id="file_upload_drawing" name="file_upload_drawing" size="19" style="opacity: 0;" onChange="doUpload_drawing(this)">
                                                <span class="filename" style="width: 50px;" value="${rfqInfo.drawingName}">No file selected</span>
                                                <span class="action" style="width: 50px;background-color: transparent;">Choose</span>
                                                <input value="${rfqInfo.drawingName}" type="hidden">
                                            </div>
                                            <a href="#"><button class="btn btn-danger" onclick="removeDrawing(this)">删除</button></a>
                                    </td>
                                </tr>
 
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
      </div>      
     </form>
        <a href="javascript:;" class="cancelButton"><button class="btn btn-info" style="margin:15px 0px 15px 20px;" onclick="increase_tr();">新增</button></a>

    </div>

    <div class="container-fluid"><hr style="clear:both;">
        <div class="row-fluid">
            <div class="span12">
                <h1 style="font-size:16px;margin: 0;">文件上传</h1>
            </div>
        </div>
            <!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
	    <form id="file_upload_id2" action="${ctx}/saveClientOrder.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
	    <input name="drawingId"  value="${rfqInfo.id}" type="hidden">
        <div class="control-group">
            <label class="control-label">PO上传: </label>
            <div class="controls">
                <div class="uploader">
                   <input type="file" id="file_upload_po" name="file_upload" size="19" style="opacity: 0;" onChange="doUpload_po()"><span class="filename" id="fileName1">No file selected</span><span class="action">Choose File</span>               
                   <input name="poName" id="getPoPath" value="" type="hidden">
                </div>
            </div>
        </div>
        </form>
        <div class="pull-right">
            <a href="javascript:;" class="div-btn"><button class="btn btn-primary btn-large" onclick="saveClientOrder();" id="submit_button">提交</button></a>
            <!-- <a class="btn btn-primary btn-large pull-right" href="" style="margin-top: 15px;">提交</a>-->
        </div>
    </div>
    

</div>

<div class="weui_dialog_confirm" id="dialog3" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提交成功</strong></div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_log default" style="border-right: 1px solid #D5D5D6;" onclick="back_clientOrder();">确定</a>
        </div>
    </div>
</div>
<div class="row-fluid">
  <div id="footer" class="span12"> 
 
  </div>
</div>



<div class="w-out-box" id="show_upload_dialog" style="display:none;">
<div class="weui_mask"></div>
<div class="w-weui_dialog" style="width:510px;">
  <div id="container">

	<div class="content" id="upload_title">
		<h1>上传进度</h1>
	</div>
	
	<!-- Progress bar -->
	<div id="progress_bar" class="ui-progress-bar ui-container">
	<div class="ui-progress" style="width: 0%;" id="ui-progress-upload">
	<span class="ui-label" style="display:none;">正在加载...<b class="value">0%</b></span>
	</div>
	</div>
	<!-- /Progress bar -->

	<div class="content" id="main_content" style="display: none;">
		<p>加载完成。</p>
	</div>
   </div>
   </div>
 </div>
</body>
</html>
