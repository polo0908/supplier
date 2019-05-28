<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="com.cbt.util.WebCookie"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 
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
<link rel="stylesheet" href="css/matrix-style.css" />
<link rel="stylesheet" href="css/matrix-media.css" />
 <link rel="stylesheet" href="css/select2.css" />
<link href="font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link href="css/reset.css" rel="stylesheet" />
 <link rel="stylesheet" href="css/easydialog.css" />
 <link type='image/x-ico' rel='icon' href='img/favicon.ico' />
<link rel="stylesheet" href="css/ui.css">
 <link rel="stylesheet" href="css/ui.progress-bar.css">
 <link rel="stylesheet" href="css/upload-base.css">

<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/supplier/js/city_state.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script> 
<script type="text/javascript" src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-form.js"></script>
<script type="text/javascript">

		
		
		$(function(){
						
			   $('#sidebar').find('li').eq(4).addClass('active');	

			   
			   //设置国家相同的选中状态、
			   var country = $('#hidden_country').val();
			   var country1 = $("#country").find("option:selected").text();
			   $("select option:contains('"+country+"')").attr('selected', true);
			   
			   //设置工厂人数选中状态
			   var factoryNumber = $('#hidden_number').val();
			   $("select option:contains('"+factoryNumber+"')").attr('selected', true);
			   
		})
	  
    //点击编辑		
	function edit_factoryInfo(){
		 $('#edit_factoryInfo').css({"display":"none"});
		 $('#save_factoryInfo').show();	
		 $('#cancel').show();
         $('.span12  input').removeAttr("disabled");
		 $('#file_upload_logo').removeAttr("disabled");
		 $('#file_upload_license').removeAttr("disabled");
		 $('#file_upload_logo').next().css({"display":"inline-block"});
		 $('#file_upload_license').next().css({"display":"inline-block"});
		 $('#country').removeAttr("disabled");
		 $('#factory_number').removeAttr("disabled");
		}
    //点击取消		
	function cancel(){
		window.location = "/supplier/showFactoryInfo.do";		
	}
    
    
	 //提交修改数据
    function save_factoryInfo(){
				 
		 
		//判断成立时间是否为空 
		var establishmentDate = $('#establishment_date').val();
		if(establishmentDate == "" || establishmentDate == null){
				easyDialog.open({
						container : {
							content : '成立时间不能为空'
						},
						autoClose : 1000
					});
		  return false;		
		}
	 
		$("#hidden_country").val($("#country").find("option:selected").text());
		
		 
 	   //判断当前页面是否有修改 
	    var result = false;                                     //初始化返回值                          
	    var colInput = document.getElementsByTagName("input");  //获取输入框控件  
	    for (var i=0; i<colInput.length; i++){                   //逐个判断页面中的input控件    			    
	        if (colInput[i].value != colInput[i].defaultValue){ //判断输入的值是否等于初始值  
	            result = true;                                  //如果不相等，返回true，表示已经修改      			            
	        }     			 
	     }  
	    colInput = document.getElementsByTagName("select"); 
	    for (var j=0; j<colInput.length; j++){                   //逐个判断页面中的select控件    			    
	        if (colInput[j].value != colInput[j].defaultValue){ //判断输入的值是否等于初始值  
	            result = true;                                  //如果不相等，返回true，表示已经修改      			            
	        }     			 
	     }  
	    
	     if(result==false){
	    	 return;
	     }else{
	    	 		 
    	
    	$("#file_upload_id").ajaxSubmit({
   			type: "post",
   			url: "${ctx}/updateFactoryInfo.do",
   	     	dataType: "text",
   	        success: function(result){
   	           easyDialog
				.open({
					container : {
						content : '  submitted successfully'
					},
					autoClose : 1000
				});	
   	            setTimeout(showFactoryInfo,1000);
   	        	
   	        },
			error : function() {
					easyDialog.open({
								container : {
									content : '  Save failed'
								},
								autoClose : 1000
				});
			}
    	 });
	 }
   } 
	 
    //跳转工厂信息
    function showFactoryInfo(){
    	window.location = "/supplier/showFactoryInfo.do";	
    }
    	
	/*
	 *公司logo上传
	 */	
	function doUpload_logo(){
  		  var path =  $("#file_upload_logo").val();	
  		  sppath = path.split("\\");
  		  var logoName = sppath[sppath.length-1];
  		  $('#getLogoName').val(logoName);	
  		  autTime(); 
  		  
// 	   	发送ajax请求,提交form表单    
 		$("#file_upload_id")
 				.ajaxSubmit(
 						{
 							type : "post",
 							url : "/supplier/updateFactoryLogo.do",
 							dataType : "text",
 							success : function(result) {
 								$('#show_upload_dialog').hide();
 								$('#getLogoName').next().children().attr('src',result); 

 							},
 							error : function() {
 								easyDialog
 										.open({
 											container : {
 												content : '  Upload failed'
 											},
 											autoClose : 1000
 										});
 								$('#show_upload_dialog').hide();
 							}
 						});
  		 
	}  
		
	/*
	 *公司license上传
	 */	
	function doUpload_license(){
  		  var path =  $("#file_upload_license").val();	
  		  sppath = path.split("\\");
  		  var licenseName = sppath[sppath.length-1];
  		  $('#getLicenseName').val(licenseName);	
  		  autTime();
  		  
  		 $("#file_upload_id").ajaxSubmit({
    			type: "post",
    			url: "${ctx}/updateFactoryLicense.do",
    	     	dataType: "text",
    	        success: function(result){
				$('#show_upload_dialog').hide();
    	        $('#getLicenseName').next().children().attr('src',result);
 
	     	    },
	     	    error:function(result){
	     	    	easyDialog
						.open({
							container : {
								header : '  Prompt message',
								content : '  Upload failed'
							},
							autoClose : 1000
						});
	     	    	$('#show_upload_dialog').hide();
	     	    }
      		});
  		  
     }

		
</script>		
<style>
    .shuruming{
        margin-top: 20px;
         float: none;
        text-shadow: 0 1px 0 #ffffff;
        margin-left: 20px;
        position: relative;
    }
.shuru  input{width: 15%;}
.controls{ color: #555;font-size: 14px;}

/* .upload{  */
/*     width:75px;  */
/*     opacity: 0; */
/*  }  */

</style>
</head>
<body>

<jsp:include page="base.jsp"></jsp:include>

<!--main-container-part-->
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="#"  class="tip-bottom"><i class="icon icon-user"></i>个人中心</a>
        <a href="/supplier/backUser/resetPassword.do"><button class="btn btn-info btn-mini w-btn-mini">修改密码</button></a>
    </div>
<h1 style="font-size: 26px;">个人中心</h1>
  </div>
  <!-- 采用 fileUpload_multipartFile ， file.transferTo 来保存上传的文件 -->
<form id="file_upload_id"  onsubmit="return false;" method="post" enctype="multipart/form-data">
    <div class="container-fluid"><hr>
        <div class="row-fluid">
            <div class="span12">
                <div class="span5">
                    <div class="control-group" style="margin-left: 0;">
                        <label class="w-con control-label">邮箱 : </label>
                        <div class="controls2">
                            ${factoryInfo.factoryAdminEmail}
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">工厂名 : </label>
                        <div class="controls">
                            <input type="text" class="span12" name="factoryName" disabled=false; value="${factoryInfo.factoryName}">
                        </div>
                    </div>
                <div class="control-group">
                    <label class="control-label">电话 : </label>
                    <div class="controls">
                        <input type="text" class="span12" name="tel" disabled=false; value="${factoryInfo.factoryAdminTel}">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">国家 : </label>
                    <div class="controls">
                    <input type="hidden" value="${factoryInfo.country}" id="hidden_country" name="selectCountry"/>
                        <select class="span12" id="country" name="country" disabled=false;>
                        </select>
                    </div>
                </div>
                  <script language="javascript">
			            populateCountries("country");
			      </script>
                
                <div class="control-group">
                    <label class="control-label">成立日期 : </label>
                    <div class="controls">
                        <input type="text" class="span12" name="establishmentDate" disabled=false; value="${factoryInfo.establishmentDate}" id="establishment_date" data-date="2016-01-02" data-date-format="dd-mm-yyyy" placeholder="<%=DateFormat.currentDate()%>" onclick="WdatePicker({skin:'whyGreen',lang:'en',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true})">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">工厂人数: </label>
                    <div class="controls">
                        <input type="text" style="display:none;" id="hidden_number" value="${factoryInfo.factoryNumber}">
                        <select id="factory_number"  disabled=false; name="factoryNumber">
                          <option><50</option>
                          <option>50><100</option>
                          <option>100><500</option>
                          <option>>500</option>
                        </select>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">主营 : </label>
                    <div class="controls">
                        <input type="text" class="span12" name="mainBusiness" disabled=false; value="${factoryInfo.mainBusiness}">
                    </div>
                </div>
                <div class="control-group">
                        <label class="control-label">联系地址 : </label>
                        <div class="controls">
                            <input type="text" class="span12"  name="factoryAddr" disabled=false; value="${factoryInfo.factoryAddr}">
                        </div>
                </div>
                <div class="control-group">
                        <label class="control-label">网址 : </label>
                        <div class="controls">
                            <input type="text" class="span12"  name="website" disabled=false; value="${factoryInfo.website}">
                        </div>
                </div>
                    <div class="control-group">
                        <label class="control-label">公司logo : </label>
                         <div class="controls">
                            <input type="file" id="file_upload_logo" name="file_upload1" style="opacity: 0;width:75px;" onChange="doUpload_logo()" disabled=false;>
                            <button type="button" class="btn btn-success" style="margin-left: -80px;display:none;">选择文件</button>
                            <input name="logoName" id="getLogoName"  type="hidden">
                            <div class="w-img6">
                                <img src="${factoryInfo.factoryLogo == null ? '' : factoryInfo.factoryLogo}" style="width: 120px;">
                            </div>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">营业执照 : </label>
                        <div class="controls">
                            <input type="file" id="file_upload_license" name="file_upload2" style="opacity: 0;width:75px;" onchange="doUpload_license()" disabled=false;/>
                            <button type="button" class="btn btn-success" style="margin-left: -80px;display:none;">选择文件</button>
                            <input name="licenseName" id="getLicenseName"  type="hidden">
                            <div class="w-img6">
                                <img src="${factoryInfo.factoryLicense == null ? '' : factoryInfo.factoryLicense}" style="width: 120px;">
                            </div>
                        </div>
                    </div>
               </div>
            </div>
        </div>
    </div>
 
</form>    
    <div class="container-fluid">
        <hr>
        <button class="w-btn btn btn-success" id="edit_factoryInfo" onclick="edit_factoryInfo();">Edit</button>
        <button type="button" class="w-btn btn btn-primary" id="save_factoryInfo" onclick="save_factoryInfo();" style="display: none;">Save</button>
        <button type="button" class="w-btn btn btn-success" id="cancel" onclick="cancel();" style="display: none;">Cancel</button>
    </div>
</div>
<!--end-main-container-part-->

<!--Footer-part-->

<div class="row-fluid">
  <div id="footer" class="span12"> </div>
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
    <a class="close-reveal-modal" style="color:#eee;font-size:22px;" href="javascript:void(0);" onclick="cancel_upload()">×</a>
	<div class="content" id="main_content" style="display: none;">
		<p>加载完成。</p>
	</div>
   </div>
   </div>
 </div>
</body>
</html>
