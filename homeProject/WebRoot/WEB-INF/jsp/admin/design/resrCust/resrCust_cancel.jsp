<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html>
<head>
	<title>注销客户</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#page_form").bootstrapValidator({
		message : 'This value is not valid',
		feedbackIcons : {/*input状态样式图片*/
			validating : 'glyphicon glyphicon-refresh'
		},
		fields: {  
		     cancelRsn:{  
		        validators: {  
		            notEmpty: {  
		           		 message: '注销原因不能为空'  
		            }
		        }  
		     },
		},
		submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});	
});
$(function(){
	var czybh = $.trim($("#czybh").val());
	
	$("#saveBtn").on("click",function(){
		$("#page_form").bootstrapValidator('validate');
		if(!$("#page_form").data('bootstrapValidator').isValid()) return;
		var datas = $("#page_form").serialize();
		 $.ajax({
			url:'${ctx}/admin/ResrCust/doCustCancel',
			type: 'post',
			data: datas,
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
		    	if(obj.rs){
		    		art.dialog({
						content: obj.msg,
						time: 1000,
						beforeunload: function () {
		    				closeWin();
					    }
					});
		    	}else{
		    		$("#_form_token_uniq_id").val(obj.token.token);
		    		art.dialog({
						content: obj.msg,
						width: 200
					});
		    	}
		    }
		 }); 
	});
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/ResrCust/goJqGrid",
		postData:{codes:"${resrCust.codes}",custRight:"${resrCust.custRight}",czybh:"${resrCust.czybh }",type:"${resrCust.type }"},
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: 'Bootstrap', 
		rowNum:100000000,
		colModel : [
			{name:'descr',	index:'descr',	width:90,	label:'客户名称',	sortable:true,align:"left",},
			{name:'mobile1',	index:'mobile1',	width:110,	label:'手机号',	sortable:true,align:"left"},
			{name:'address',	index:'address',	width:180,	label:'楼盘地址',	sortable:true,align:"left",},
			{name:'businessmandescr',	index:'businessmandescr',	width:90,	label:'原跟单人',	sortable:true,align:"left",},
			{name:'crtczydescr',	index:'crtczydescr',	width:70,	label:'创建人',	sortable:true,align:"left"},
		],
	});
});

function changeCancelRsn(){
	if ($("#cancelRsn").val()=='5'){
		$("#cancelRemarks").parent().parent().removeClass("hidden");
		$("#page_form").bootstrapValidator("removeField","cancelRemarks");
		$("#page_form").bootstrapValidator("addField", "cancelRemarks", {  
            validators: {  
                notEmpty: {  
                    message: '注销备注不能为空'  
                } 
            }  
        });
	}else{
		$("#cancelRemarks").val('');
		$("#page_form").bootstrapValidator('removeField','cancelRemarks');
		$("#cancelRemarks").next("small").remove();
		$("#cancelRemarks").parent().parent().addClass("hidden");
	}
}
</script>
</head>
	<body>
		<div class="body-box-form">
				<div class="panel panel-system" >
    <div class="panel-body" >
      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
					</div>
				</div>
				<div class="panel panel-info" >  
			        <div class="panel-body">
						  <form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame" >
								<house:token></house:token>
								<input type="hidden" name="jsonString" value="" />
								<input type="hidden" id="czybh" name="czybh" style="width:160px;" value="${resrCust.czybh }"/>
								<input type="hidden" id="department2" name="department2" style="width:160px;" value="${resrCust.department2 }"/>
								<input type="hidden" id="codes" name="codes" style="width:160px;" value="${resrCust.codes }"/>
								<ul class="ul-form">
									<div class="validate-group row" >
										<li class="form-validate">
											<label><span class="required">*</span>注销原因</label>
											<house:xtdm id="cancelRsn" dictCode="RESRCANRSN"  onchange="changeCancelRsn()"></house:xtdm>                     
										</li>
									</div>
									<div class="validate-group row hidden">
										<li  class="form-validate">
											<label class="control-textarea"><span class="required">*</span>注销备注 </label>
											<textarea  id="cancelRemarks" name="cancelRemarks"  rows="3"></textarea>
										</li>
									</div>
								</ul>
						  </form>
				   </div>
			   </div>
			   <div id="content-list">
					<table id= "dataTable"></table>
				</div>
		</div>
	</body>	
</html>
