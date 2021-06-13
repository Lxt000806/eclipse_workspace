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
	<title>删除门票</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_activity.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_purchase.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
var length=0;
var prefix="";
$(function() {
		$("#actNo").openComponent_activity({callBack: getData});	
		
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					numTo:{  
						validators: {  
							notEmpty: {  
								message: '截止号不能为空'  
							}
						}  
					},
					numFrom:{  
						validators: {  
							notEmpty: {  
								message: '起始号不能为空'  
							}
						}  
					},
					openComponent_activity_actNo:{  
				        validators: {  
				            notEmpty: {  
				           		 message: '展会活动不能为空'  
				            }
				        }  
				     },
				},
				submitButtons : 'input[type="submit"]'/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
	function getData(data){
		if (!data) return;
	  	validateRefresh_choice();
		length=data.length;
		prefix=data.prefix;
	}
});
$(function(){

	$("#del").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var actNo=$.trim($("#actNo").val());
		var numTo=$.trim($("#numTo").val());
		var numFrom=$.trim($("#numFrom").val());
		var datas = $("#dataForm").serialize();
		$.ajax({
			url:'${ctx}/admin/hdmpgl/isSend',
			type: 'post',
			data: {actNo:actNo,length:length,prefix:prefix,numFrom:numFrom,numTo:numTo},
			dataType: 'json',
			cache: false,
			error: function(obj){
				showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
		    },
		    success: function(obj){
				if(obj){
					$.ajax({
						url:'${ctx}/admin/hdmpgl/doDelete',
						type: 'post',
						data: {actNo:actNo,length:length,prefix:prefix,numFrom:numFrom,numTo:numTo},
						dataType: 'json',
						cache: false,
						error: function(obj){
							showAjaxHtml({"hidden": false, "msg": '保存数据出错~'});
					    },
					    success: function(obj){
							art.dialog({
								content:'删除成功',
								time:1000,
								beforeunload: function () {
				    				closeWin();
							    }
							});
					    }
					 });
				}else{
					art.dialog({
						content:"存在已经发放或已经签到的门票，不能删除",
					});
				}
				
		    }
		 });
	});
	
});
function validateRefresh_choice(){
	 $('#dataForm').data('bootstrapValidator')
                 .updateStatus('openComponent_activity_actNo', 'NOT_VALIDATED',null)  
                 .validateField('openComponent_activity_actNo');                    
}
</script>
</head>
	<body>
<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="del">
							<span>删除</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
		<div class="panel panel-info" >  
			<div class="panel-body">
				<form action="" method="post" id="dataForm" class="form-search">
					<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<div class="validate-group row" >
								<li class="form-validate">
									<label>展会活动</label>
									<input type="text" id="actNo" name="actNo" style="width:160px;" value="${hdmpgl.actNo }"   />
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>起始号</label>
									<input type="text" id="numFrom" name="numFrom" style="width:160px;" value="${hdmpgl.numFrom }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>截止号</label>
									<input type="text" id="numTo" name="numTo" style="width:160px;" value="${hdmpgl.numTo }"/>
								</li>
							</div>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
