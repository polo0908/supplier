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
<link rel="stylesheet" href="/supplier/css/easydialog.css" />
<link rel="stylesheet" href="css/ui.css">
<link rel="stylesheet" href="css/ui.progress-bar.css">
<link rel="stylesheet" href="css/upload-base.css">
<link type='image/x-ico' rel='icon' href='img/favicon.ico' />

<script type="text/javascript" src="js/upload-base.js"></script>
<script type="text/javascript" src="js/number.js"></script>
<script type="text/javascript" src="/supplier/js/easydialog.min.js"></script>
<script type="text/javascript" src="/supplier/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="/supplier/js/jquery-1.10.2.js"></script>
<script type="text/javascript" src="js/jquery-form.js"></script>
<script type="text/javascript">
	
	
 	$(function(){
//  		$('#sidebar').find('li').eq(2).addClass('active');
 		
 		
<%--  	<%	Integer date = (Integer)application.getAttribute("quotation_date"); --%>
//  		Integer number = (Integer)application.getAttribute("quotation_number");
//  		Calendar calendar = Calendar.getInstance();
//  		 int day = calendar.get(Calendar.DATE);
//  		 int year = calendar.get(Calendar.YEAR);
//  		 int month = calendar.get(Calendar.MONTH)+1;
//  		 Integer nowDate = 0;
//  		 if(month<10){
//  		 	nowDate = Integer.parseInt(year+"0"+month+""+day);
//  		 }else{
//  			 nowDate = Integer.parseInt(year+""+month+""+day); 
//  		 }
		 
//  		if(date == null){
//  			//第一次;
//  			  date = nowDate;
//  			  application.setAttribute("quotation_date", nowDate);
//  			  number = 1;
//  			  application.setAttribute("quotation_number", 2);
//  		}
//  	  if(date<nowDate){
//  		  //域中的时间小于当前时间,更新域中时间;
// 		  date = nowDate;
//  		  application.setAttribute("quotation_date", nowDate);
//  		  number = 1;
//  		  application.setAttribute("quotation_number", 2);
//  	  }
//  	  //同一天
//  	  if(date.equals(nowDate)){
//  		  application.setAttribute("quotation_number", number+1);
//  	  }
//  	%>
	
 		//生成quotation_id
<%--  		var date = '<%=date%>'; --%>
<%-- 		var number = '<%=number%>'; --%>
//  		var quotation_id = date+"Q"+number;
 		$("#quoter").val(userName);
 		
 		 var pic = $('#select_temp_pic').find('span').text();
 		 if( pic == null || pic == ''){
 			$('#select_temp_pic').hide();
 		 }
 		
 		
 		$.ajax({
 			type:"post",
 			url:"/supplier/queryCurrencyList.do",
 			success:function(result){
 				if(result.ok){
 					var data = result.obj;
 					if(data.length>0){
 						var currency_str="";
 						for(var i = 0; i<data.length ;i++){
 							currency_str += "<option id="+data[i].id+">"+data[i].currencyShorthand+"</option>";
 						}
 						$("#currency").append(currency_str);
 					}else{
 				
 					}
 				}else{
 		
 				}
 			}
 		});
 		
 		
 		
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
 	
 	
	//改变金额单元
	function select_currency(obj){
	   	
		var currency = $(obj).find("option:selected").text(); 		
		$.post("/supplier/selectCurrency.do",
	              { 
				  "currency": currency 
				  },
				function(result){				  
				  if(result.state == 0){
						var exchangeRate = result.data;  
						var currentRate = $('#current_exchangeRate').val();
						$('#product_thead').find('span').text("("+currency+")");  
						$('#product_tbody').find('tr').each(function(i){
						var moldPrice = $(this).find('td').eq(3).find('input').val();
						moldPrice = (parseFloat(moldPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
						$(this).find('td').eq(3).find('input').val(moldPrice);
						$(this).find('td').eq(3).find('span').text(moldPrice.toFixed(2));
						
						$(this).find('td').eq(4).find('span').each(function(j){
							var unitPrice = $(this).next().next().val();
							unitPrice = (parseFloat(unitPrice) / parseFloat(currentRate) * parseFloat(exchangeRate));
							$(this).text(unitPrice.toFixed(2));
							$(this).next().next().val(unitPrice);
						})
						
				    })
				    $('#current_exchangeRate').val(exchangeRate);
				}
			 })		     		  
	}
 	
 	
</script>
</head>
<body>
<jsp:include page="base.jsp"></jsp:include>
<input type="hidden" id="img_names">
<input type="hidden" id="current_exchangeRate" value="1">
<div id="content">
  <div id="content-header">
    <div id="breadcrumb"> <a href="/supplier/queryAllQuotation.do"  class="tip-bottom"><i class="icon  icon-paste"></i>报价单</a> <a href="#" class="current">新建报价</a>
    </div>
      <div class="shu">
          <div class="w-div1">
              <p class="w-p1">
                  基本信息
              </p>
          </div>
      </div>
  </div>
    <div class="container-fluid"><hr style="clear:both;">

        <div class="row-fluid">
            <div class="span8" style="margin-left: 0;">
                <label class="control-label">Project Id: </label>
                <div class="controls">
                <input type="text" id="project_id">
                </div>
            </div>
            
          <div class="span12" style="margin-left: 0;">
            <div class="span8 w-span8" style="margin-left: 0;">
                    <div class="span6">
                                <div class="control-group">
                                    <label class="control-label">Subject : </label>
                                    <div class="controls">
                                        <input type="text" id="project_subject">
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Currency : </label>
                                    <div class="controls">
                                        <select id="currency" onclick="select_currency(this)"></select>
                                    </div>
                                </div>
                                <div class="control-group">
                                    <label class="control-label">Quote Date : </label>
                                    <div class="controls">
                                        <input type="text" onclick="WdatePicker({skin:'whyGreen',lang:'en'})" id="quotation_date" value="<%=DateFormat.currentDate()%>">
                                    </div>
                                </div>
                    </div>
                    
                    
                <div class="span6">
                    <div class="control-group w-control-group">
                        <script language="javascript">
// 							function changeF1() {
// 							   $('#customer_input').val($('#makeupCoSe1').find("option:selected").text());
// 							   $('#customer_id').val($('#makeupCoSe1').val())
// 							   $('#makeupCoSe1').val('');
// 							}
						</script>
                    
                        <label class="control-label" style="height:40px;">Client Name: </label>
<!--                         <div class="controls"> -->
<!--                             <input type="text"> -->
<!--                         </div> -->
                        <div class="controls">
			             <span class="control-group">
							<select name="makeupCoSe1" id="makeupCoSe1">
<!-- 							<OPTION id="99999" VALUE="" SELECTED> -->
                            <c:forEach var="obj" items="${userInfo}" varStatus="i">
                            <option value="${obj.userid}" <c:if test="${obj.userid eq userid}"> selected="selected"</c:if>>${obj.userName}</option>
                            </c:forEach>
							</select>
						 </span>
<!-- 		                <span class="w-controls-span2"> -->
<!-- 							<input type="text" id="customer_input" class="w-controls-input" value="" placeholder="请选择或输入" ondblclick="clear_input();">														 -->
<!-- 						</span> -->
                        </div>
                        
                    </div>
                    <div class="control-group">
                        <label class="control-label">Inco Term : </label>
                        <div class="controls">
                            <input type="text" value="FOB Shanghai" id="inco_term">
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">Valid: </label>
                        <div class="controls">
                            <input id="quotation_validity" type="text" value="15">（天）
                        </div>
                    </div>
                </div>
          </div>
            <div class="span4">
                <div class="control-group w-control60">
                    <label class="control-label">Quoter : </label>
                    <div class="controls">
<!--                         <input id="quoter" type="text" disabled="disabled"> -->
                         <span id="quoter">${backUser.userName}</span>
                    </div>
                </div>
                <div class="control-group w-control60">
                    <label class="control-label">Email : </label>
                    <div class="controls">
<!--                         <input type="text" name="email" id="email"> -->
                        <span id="email">${backUser.email}</span>
                    </div>
                </div>
                <div class="control-group w-control60">
                    <label class="control-label">TEL : </label>
                    <div class="controls">
                        <input type="text" name="number" id="tel">
                    </div>
                </div>
            </div>
          </div>
     </div>
 </div>

    <div class="out-box">
        <div class="container-fluid">
            <div class="w-font">
                <h2 style="font-size: 16px;font-weight: 700;">产品信息</h2>
            </div>
            <div class="w-hr">
                <hr>
            </div>
            <div class="w-button">
                <a href="#" class="cancel">
                    <button class="btn btn-info" onclick="addProduct();">添加产品</button>
                </a>
            </div>
            <div class="widget-box">
              <div class="widget-content nopadding">
                    <table class="table table-bordered table-striped">
                        <thead id="product_thead">
                        <tr>
                            <th>编号</th>
                            <th>产品名</th>
                            <th>材质</th>
                            <th>模具费<span>(${quotationBean.currency})</span></th>
                            <th>单价<span>(${quotationBean.currency})</span></th>
                            <th>最小订量</th>
                            <th>注释</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="product_tbody">
                        <tr class="odd gradeX">
                            <td>1</td>
                            <td>502H-2</td>
                            <td>Q235</td>
                            <td><span>500</span><input type="hidden" value="500"></td>
                            <td>
                            <span>3</span><br><input type="hidden" value="3">
                            <span>2</span><br><input type="hidden" value="2">
                            <span>1</span><br><input type="hidden" value="1">
                            </td>
                            <td><span>300</span><br><span>500</span><br><span>800</span></td>
                            <td></td>
                            <td class="center">
                                <a href="#"><button class="btn btn-primary" onclick="edit_product(this)">编辑</button></a>
                                <a href="#"><button class="btn btn-danger" style="margin-left: 10px;" onclick="delete_product(this)">删除</button></a>
                            </td>
                        </tr>
                       
                       </tbody>
                    </table>
                </div>
            </div>
       </div>
    </div>
    <div class="out-box">
        <div class="container-fluid">
            <div class="w-font">
                <h2 style="font-size: 16px;font-weight: 700;">备注信息</h2>
            </div>
            <div class="w-hr">
                <hr>
            </div>
            <div class="w-button">
                <button class="btn btn-success" onclick="select_temp_remark()">使用备注模板</button>
<!--                 <button class="btn btn-primary" style="margin-left: 10px;">保存为新模板</button> -->
            </div>
            <div class="row-fluid">
            	<div class="span12">
                	<textarea class="span11 w-textarea-span11"  id="select_temp_remark"></textarea>
                </div>
            </div>
            
            <div class="w-pic div-w-text" style="margin-left:0;padding: 20px 0 20px;" id="select_temp_pic" >             	
          	
            </div>
                   
            
            <div class="w-button span12" style="margin-left:0;">
<!--                 <button class="btn btn-success">预览</button> -->
                <button class="btn btn-primary" id="save_button" onclick="save_quotation()">保存</button>
            </div>
       </div>
    </div>

    <div class="w-block" id="div1" style="display:none;">
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
                            <label class="control-label">模具价格 : </label>
                            <div class="w-controls">
                                <input id="mold_price" type="text" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
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
                                <input id="material" type="text">
                            </div>
                        </div>
                    </div>
                   </div>
                </div>
                <div class="row-fluid" id="quoted_0">
                    <div class="span9">
                        <div class="span6">
                            <div class="control-group">
                                <label class="control-label">单价 : </label>
                                <div class="w-controls">
                                    <input type="text" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group w-group">
                                <label class="control-label">最小订量 : </label>
                                <div class="w-controls">
                                    <input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="add_quoted_btn" class="w-span3 span3">
                        <button onclick="add_quoted();" class="btn btn-info">添加报价</button>
                    </div>
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
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="add_product()">添加</a>
            </div>
        </div>
    </div>
    <!-- 产品编辑 -->
    <div class="w-block" id="div11" style="display:none;">
        <div class="weui_mask"></div>
        
        <div class="weui_dialog">
           <div class="waimian">
           <a onclick="closeProductDiv_edit();" class="close-reveal-modal" href="#">×</a>
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
                                <input id="mold_price_edit" type="text" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
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
                <div class="row-fluid" id="quoted_edit_0">
                    <div class="span9">
                        <div class="span6">
                            <div class="control-group">
                                <label class="control-label">单价 : </label>
                                <div class="w-controls">
                                    <input type="text" onkeyup="keyUp(this)" onKeyPress="keyPress(this)" onblur="onBlur(this)"/>
                                </div>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group w-group">
                                <label class="control-label">最小订量 : </label>
                                <div class="w-controls">
                                    <input type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')"/>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="add_quoted_btn_edit" class="w-span3 span3">
                        <button onclick="add_quoted_edit(this);" class="btn btn-info">添加报价</button>
                    </div>
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
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="modify_product()">修改</a>
            </div>
        </div>
    </div>
<!-- <<<<<<<<<<<<添加、编辑产品结束>>>>>>>>>>>>>> -->


    <div class="w-block" id="div2" style="display:none;">
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
            <div class="checker" id="uniform-title-checkbox"><span><input type="checkbox" id="title-checkbox" name="title-checkbox" style="opacity: 0;"></span></div>
            </span>
                        <h5>全选</h5>
                        <span class="label label-info" onclick="input_img_remark()">预设图片备注信息</span>
                        <span class="label label-primary" onclick="input_text_remark()">预设文本备注信息</span>
                    </div>
                    <div class="widget-content nopadding">
                        <table class="table table-bordered table-striped with-check" id="remark_table"> 
                          <c:forEach var="obj" items="${tempRemarks}" varStatus="i">
                           <tr>
                                <td class="w-first-td"><div class="checker" id="uniform-undefined"><p>${i.index+1}</p><span><input name="checkbox" type="checkbox"/></span></div></td>
                                <td>${obj.remark}</td>
                                <td>
                                 <div>
                                <a href="#" onclick="open_edit_text_remark(this,${obj.textOrPicture},${obj.id})">编辑</a>
                                <span style="display:none;">${obj.textOrPicture}</span>
                                 <a href="#" onclick="delete_temp(this,'${obj.id}')">删除</a>
                                 </div>
                                <div style="float:left;">
	                                <c:if test="${i.index != 0}">
	                                <img src="img/arrows-up.png" class="z-img-arrow" style="width:20px;" onclick="index_up(this,'${i.index+1}','${obj.id}')">
	                                </c:if>
	                                <c:if test="${(i.index+1) != tempRemarks.size()}">
	                                <img src="img/arrows-down.png"  class="z-img-arrow" style="width:20px;" onclick="index_down(this,'${i.index+1}','${obj.id}')">
	                                </c:if>
                                </div>
                                <input type="hidden" value="${obj.id}">
                                </td>
                            </tr>
                           </c:forEach>
                        </table>
                    </div>
                </div>
           </div>
            <div class="weui_dialog_ft" style="margin-top: 0;">
                <a href="javascript:;" class="weui_btn_dialog primary" style="border-right: 1px solid #D5D5D6;" onclick="import_temp()">插入</a>
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="close_temp()">关闭</a>
            </div>
        </div>
    </div>



    <div class="w-block" id="div3" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog" style="width:30%;border: 1px solid #cdcdcd;">
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
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="add_text_temp()">添加</a>
            </div>
        </div>
    </div>
    
    <div class="w-block" id="div5" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog" style="width:30%;border: 1px solid #cdcdcd;">
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
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="edit_text_temp()">修改</a>
            </div>
        </div>
    </div>
    

    <div class="w-block" id="div4" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog" style="width: 25%;border: 1px solid #cdcdcd;">
            <div class="w-hook">
                <div class="w-padding">
                    <div class="w-left">报价图片备注</div>
                    <div class="w-right" onclick="close_div4()">X</div>
                </div>
            </div>
         <form id="file_upload_id" action="${ctx}/saveTemplatePicture.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
            <div class="container-fluid">
                <div class="row-fluid w-span-tu" style="margin: 0;">
                    <input type="hidden" name="templateRemarkId" id="template_remark_id">  
                    <input type="hidden" name="pictureIndex" id="picture_index">  
                    <input type="hidden" name="drawingName" id="drawing_name">  
                    <div class="span3">
                        <input type="hidden" value="1">
                       <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload1" onchange="uploadFile(this)">
                        <img src="img/tu.png" class="w-img-tu">
                       </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                     <div class="span3">
                        <input type="hidden" value="2">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload2" onchange="uploadFile(this)">
                        <img src="img/tu.png" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                    <div class="span3">
                        <input type="hidden" value="3">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload3" onchange="uploadFile(this)" >
                        <img src="img/tu.png" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                    <div class="span3">
                        <input type="hidden" value="4">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload4" onchange="uploadFile(this)" >
                        <img src="img/tu.png" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                </div>
                <p class="w-prompt">
                    点击"+"上传图片，最多添加四张
                </p>
            </div>
           </form>
            <div class="weui_dialog_ft" style="margin-top: 0;">
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="save_picture_temp()">保存</a>
            </div>
        </div>
    </div>
    
    
    <div class="w-block" id="div6" style="display:none;">
        <div class="weui_mask"></div>
        <div class="weui_dialog" style="width: 25%;border: 1px solid #cdcdcd;">
            <div class="w-hook">
                <div class="w-padding">
                    <div class="w-left">报价图片备注</div>
                    <div class="w-right" onclick="close_div6()">X</div>
                </div>
            </div>
         <form id="file_upload_id1" action="${ctx}/saveTemplatePicture.do" onsubmit="return false;" method="post" enctype="multipart/form-data">
            <div class="container-fluid">
                <div class="row-fluid w-span-tu" style="margin: 0;">
                    <input type="hidden" name="templateRemarkId" id="template_remark_id1">  
                    <input type="hidden" name="pictureIndex" id="picture_index1">  
                    <input type="hidden" name="drawingName" id="drawing_name1"> 
                    <div class="span3">
                        <input type="hidden" value="1">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload1" onchange="uploadFile_edit(this)">
                        <img src="img/tu.png" id="img_1" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                     <div class="span3">
                        <input type="hidden" value="2">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload2" onchange="uploadFile_edit(this)">
                        <img src="img/tu.png" id="img_2" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                    <div class="span3">
                        <input type="hidden" value="3">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload3" onchange="uploadFile_edit(this)" >
                        <img src="img/tu.png" id="img_3" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                    <div class="span3">
                        <input type="hidden" value="4">
                        <div style="width:80px;height:80px;">
                        <input type="file" class="z-input-file" name="file_upload4" onchange="uploadFile_edit(this)" >
                        <img src="img/tu.png" id="img_4" class="w-img-tu">
                        </div>
                        <img src="img/pic.png" class="z-add-pic">
                    </div>
                </div>
                <p class="w-prompt">
                    点击"+"上传图片，最多添加四张
                </p>
            </div>
           </form>
            <div class="weui_dialog_ft" style="margin-top: 0;">
                <a href="javascript:;" class="weui_btn_dialog primary" onclick="edit_picture_temp()">修改</a>
            </div>
        </div>
    </div>
</div>
<div class="weui_dialog_confirm" id="dialog10" style="display:none;">
    <div class="weui_mask"></div>
    <div class="weui_dialog">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">提交成功</strong></div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_log default" style="border-right: 1px solid #D5D5D6;" onclick="quotation_list();">确定</a>
        </div>
    </div>
</div>

<div class="row-fluid">
  <div id="footer" class="span12"> 

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
		$("#material").val("");
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
		$("#material_edit").val("");
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
	function add_quoted(){
		var product_name = $("#product_name").val();
		var mold_price = $("#mold_price").val();
		var material = $("#material").val();
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
	
		//获得单价和最小订量;
		var div = $("#add_quoted_btn").parent("div");
		var price = div.find("input").eq(0).val();
		var quantity = div.find("input").eq(1).val();
		if(!(price&&quantity)){
			//没有输入价格和最小订量;
			easyDialog.open({
				  container : {
				    header : 'Prompt message',
				    content : '请输入价格和最小订量 '
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return;
		}
		quoted_number +=1;
		var new_div = "<div class='row-fluid' id='quoted_"+quoted_number+"'><div class='span9'><div class='span6'><div class='control-group'><label class='control-label'>单价 : </label>"+
                "<div class='w-controls'><input type='text'></div></div></div><div class='span6'><div class='control-group w-group'>"+
                "<label class='control-label'>最小订量 : </label><div class='w-controls'><input type='text'></div></div></div></div></div>";
		div.after(new_div);
		$("#quoted_"+(quoted_number)).append($("#add_quoted_btn"));
	}
	/**
	*点击添加报价按钮(编辑)
	*新增一行报价
	*@author polo
	*@time 2017.5.25
	*/
	function add_quoted_edit(obj){
		var product_name = $("#product_name_edit").val();
		var mold_price = $("#mold_price_edit").val();
		var material = $("#material_edit").val();
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
	
		//获得单价和最小订量;
// 		var div = $("#add_quoted_btn_edit").parent("div");
		var price = $(obj).parent().parent().parent().find("input").val();
		var quantity = $(obj).parent().prev().find("input").eq(1).val();
		if(!(price&&quantity)){
			//没有输入价格和最小订量;
			easyDialog.open({
				  container : {
				    header : 'Prompt message',
				    content : '价格和最小订量不能为空 '
				  },
	    		  overlay : false,
	    		  autoClose : 1000
				});
			return;
		}
		quoted_edit_index +=1;
		var new_div = "<div class='row-fluid' id='quoted_edit_"+quoted_edit_index+"'><div class='span9'><div class='span6'><div class='control-group'><label class='control-label'>单价 : </label>"+
                "<div class='w-controls'><input type='text'></div></div></div><div class='span6'><div class='control-group w-group'>"+
                "<label class='control-label'>最小订量 : </label><div class='w-controls'><input type='text'></div></div></div></div></div>";
		$("#quoted_edit_"+(quoted_edit_index-1)).after(new_div);

	}
	
	/**
	*点击添加报价按钮,
	*新增一行报价
	*@author polo
	*@time 2017.5.23
	*/	
	function add_product(){
		
		var product_name = $("#product_name").val();
		var mold_price = $("#mold_price").val();
		var material = $("#material").val();
		var remark = $('#productDivTextarea').val();
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
		for(var i = 0; i< quoted_number+1 ;i++){
			price = $("#quoted_"+i).find("input:first").val();
			quantity = $("#quoted_"+i).find("input:last").val();
			price_td = '<span>'+price+'</span><br><input type="hidden" value="'+price+'">';
			quantity_td = '<span>'+quantity+'</span><br>';
			unit_prices += price_td;
			quantities += quantity_td;
		}
		
         var tl = $('#product_tbody').find("tr").length;
         var tr = '<tr class="even gradeC">'+
					         '<td>'+(tl+1)+'</td>'+
					         '<td>'+product_name+'</td>'+
					         '<td>'+material+'</td>'+
					         '<td><span>'+mold_price+'</span></td>'+
					         '<td>'+unit_prices+'</td>'+
					         '<td>'+quantities+'</td>'+
					         '<td>'+remark+'</td>'+
					         '<td class="center">'+
					             '<a href="#"><button class="btn btn-primary" onclick="edit_product(this)">编辑</button></a>'+
					             '<a href="#"><button class="btn btn-danger" style="margin-left: 10px;" onclick="delete_product(this)">删除</button></a>'+
					         '</td>'+
					     '</tr>';
		$('#product_tbody').append(tr);
		closeProductDiv();
	}
	
	
	/**
	*点击编辑产品
	*@author polo
	*@time 2017.5.25
	*/	
	//添加报价,层级标记;
	var product_index = 1;
	function edit_product(obj){		
		product_index = $(obj).parents('tr').find('td:eq(0)').text();
		$('#product_name_edit').val($(obj).parents('tr').find('td:eq(1)').text());
		$('#mold_price_edit').val($(obj).parents('tr').find('td:eq(3)').text());
		$('#material_edit').val($(obj).parents('tr').find('td:eq(2)').text());
		$('#productDivTextarea_edit').val($(obj).parents('tr').find('td:eq(6)').text());
		
		var tl = $(obj).parents('tr').find('td:eq(4)').find('span').length;
		for(var i=0;i<tl;i++){
			if(i == 0){
				$('#quoted_edit_0').find('input:eq(0)').val($(obj).parents('tr').find('td:eq(4)').find('span:eq(0)').text());
				$('#quoted_edit_0').find('input:eq(1)').val($(obj).parents('tr').find('td:eq(5)').find('span:eq(0)').text());
			}else{
				quoted_edit_index +=1;
				var price = $(obj).parents('tr').find('td:eq(4)').find('span:eq('+i+')').text();
				var quantity = $(obj).parents('tr').find('td:eq(5)').find('span:eq('+i+')').text();
				var new_div = "<div class='row-fluid' id='quoted_edit_"+quoted_edit_index+"'><div class='span9'><div class='span6'><div class='control-group'><label class='control-label'>单价 : </label>"+
		                "<div class='w-controls'><input type='text' value="+price+"></div></div></div><div class='span6'><div class='control-group w-group'>"+
		                "<label class='control-label'>最小订量 : </label><div class='w-controls'><input type='text' value="+quantity+"></div></div></div></div></div>";
		       $('#quoted_edit_'+(i-1)).after(new_div);
			}

		}
		
		$('#div11').show();
	
	}
	
	/**
	*修改产品信息
	*@author polo
	*@time 2017.5.25
	*/	
	function modify_product(){
		
		var product_name = $("#product_name_edit").val();
		var mold_price = $("#mold_price_edit").val();
		var material = $("#material_edit").val();
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
		for(var i = 0; i< quoted_edit_index+1 ;i++){
			price = $("#quoted_edit_"+i).find("input:first").val();
			quantity = $("#quoted_edit_"+i).find("input:last").val();
			price_td = '<span>'+price+'</span><br><input type="hidden" value="'+price+'">';
			quantity_td = '<span>'+quantity+'</span><br>';
			unit_prices += price_td;
			quantities += quantity_td;
		}
		
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(1).text(product_name);
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(2).find('span').text(mold_price);
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(3).text(material);
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(6).text(remark);
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(4).empty().append(unit_prices);
		$('#product_tbody').find('tr').eq(product_index-1).find('td').eq(5).empty().append(quantities);
		
		closeProductDiv_edit();
	}
	
	
	
	

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
	}  
	}  
	}  

	   //移除当前行   
	   function delete_product(obj){
		if (confirm("是否删除此产品？")){
		var tr = this.getRowObj(obj);  
		if(tr != null){  
		tr.parentNode.removeChild(tr); 
		
		}else{  
		throw new Error("the given object is not contained by the table");  
		} 
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
		var text = $(obj).parent().prev().html();
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
			                        ' <td>'+remarks[i].remark+'</td>'+
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
                 		  remarks_img += remark_img +'<br>';
           			  $('#select_temp_pic').show();
           			  $('#select_temp_pic').append(remarks_img);  
             		  }
        	  }
          })
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
	                        ' <td>'+result.data.remark+'</td>'+
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
		 
		 //上传进度方法
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
// 				    var tr =  '<tr>'+
// 	    			    '<td class="w-first-td"><div class="checker" id="uniform-undefined"><p>'+(tl+1)+'</p><span><input type="checkbox" style="opacity: 0;"></span></div></td>'+
// 	    			    '<td>'+result.data.remark+'</td>'+
// 	    			    '<td><a href="#" onclick="open_edit_text_remark(this,'+result.data.textOrPicture+','+result.data.id+')">编辑</a>'+
// 	    			    '<a href="#" onclick="delete_temp(this,'+result.data.id+')">删除</a>'+
// 	    			    '</td>'+
// 	    			    '</tr>'; 			    			 
// 	    	            $("#remark_table").append(tr);  
// 	    	            $('#div4').hide();
	    	            
	    	            
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
                       ' <td>'+result.data.remark+'</td>'+
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
	*保存报价单
	*@author polo
	*@time 2017.5.25
	*/	
  function save_quotation(){
	  
	 var projectSubject = $('#project_subject').val();
	 var quotationDate =  $('#quotation_date').val();
	 var currency = $('#currency').find("option:selected").text();
	 var customerName = $("#makeupCoSe1").find("option:selected").text(); 
	 var incoTerm = $('#inco_term').val();
	 var quotationValidity = $('#quotation_validity').val();
	 var quoter = $('#quoter').text();
	 var email = $('#email').text();
	 var tel = $('#tel').val();
	 var tempRemark = $('#select_temp_remark').val();
	 var remarkImg = $('#select_temp_pic').html();
	 var imgNames = $('#img_names').val();
	 var customerId = $('#makeupCoSe1').val();
	 var projectId = $('#project_id').val();
	 
	 if(projectId == null || projectId == ""){
		  easyDialog.open({
			  container : {
			    header : 'Prompt message',
			    content : '请输入项目号 '
			  },
    		  overlay : false,
    		  autoClose : 1000
			}); 
		  return false;
	 }
	 
	 var productList = "";
	 var tl = $('#product_tbody').find('tr').length;
	 for(var i=0;i<tl;i++){
		 
		 var productName =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(1)").text();
		 var moldPrice =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(3)").text();
		 var material =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(2)").text();
		 var productNotes =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(6)").text();
		 var price_tl =  $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find('span').length;
		 var priceList = "";
		 var quantityList = "";
		 
		 for(var j=0;j<price_tl;j++){
			 var unit_price = $("#product_tbody tr:eq(" + i + ")").children("td:eq(4)").find("span:eq("+j+")").text(); 
			 var quantity = $("#product_tbody tr:eq(" + i + ")").children("td:eq(5)").find("span:eq("+j+")").text(); 
			 priceList += unit_price + "&";
			 quantityList += quantity + "&";
		 }
		 priceList = (priceList.substring(priceList.length-1)=='&')?priceList.substring(0,priceList.length-1):priceList;
		 quantityList = (quantityList.substring(quantityList.length-1)=='&')?quantityList.substring(0,quantityList.length-1):quantityList; 
		 
		 productList += "-" + productName + "%" + "-" + moldPrice + "%" + "-" + material + "%" + "-" + priceList +  "%" + "-" + quantityList + "%" + "-" + productNotes + ",";
	 }
	 
	 $('#save_button').css({"background-color":"#666"}).attr("disabled","true");
	 
	 $.post("/supplier/saveQuotation.do", 
			  { 
		        "projectId" : projectId,
		        "projectSubject" : projectSubject,
		        "quotationDate" : quotationDate,
		        "currency" : currency,
		        "customerName" : customerName,
		        "incoTerm" : incoTerm,
		        "quotationValidity" : quotationValidity,
		        "quoter" : quoter,
		        "email" : email,
		        "tel" : tel,
		        "tempRemark" : tempRemark,
		        "remarkImg"  : remarkImg,
		        "productList" : productList,
		        "imgNames" : imgNames,
		        "customerId" : customerId
		      },
			   function(result){
		    	  $('#save_button').css({"background-color":"#04c"}).removeAttr("disabled");
		    	  if(result.state == 0){
// 		    		 $('#dialog10').show();
		    		 window.location = "/supplier/toSendQuotation.do?quotationInfoId="+result.data+"&userId="+customerId;  
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
	                        ' <td>'+remarks[i].remark+'</td>'+
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
	*@time 2017.7.25
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
	                        ' <td>'+remarks[i].remark+'</td>'+
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
	
	
</script>
</html>
