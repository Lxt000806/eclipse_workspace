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
	<title>活动门票管理生成门票</title>
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
		$("#openComponent_activity_actNo").attr("readonly",true);
			$("#dataForm").bootstrapValidator({
				message : 'This value is not valid',
				feedbackIcons : {/*input状态样式图片*/
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: {  
					ticketNum:{  
						validators: {  
							notEmpty: {  
								message: '门票数不能为空'  
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
		console.log(prefix);
	}
});
	

$(function(){


	$("#saveBtn").on("click",function(){
		$("#dataForm").bootstrapValidator('validate');
		if(!$("#dataForm").data('bootstrapValidator').isValid()) return;
		var actNo=$.trim($("#actNo").val());
		var ticketNum=$.trim($("#ticketNum").val());
		var numFrom=$.trim($("#numFrom").val());
		var datas = $("#dataForm").serialize();
		
		console.log(numFrom);
		$.ajax({
			url:'${ctx}/admin/hdmpgl/doCreate',
			type: 'post',
			data: {actNo:actNo,length:length,prefix:prefix,ticketNum:ticketNum,numFrom:numFrom},
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
		    		art.dialog({
						content: obj.msg,
						width: 200
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
									<label>门票数</label>
									<input type="text" id="ticketNum" name="ticketNum" onkeyup="value=value.replace(/[^0-9]/g,'')" style="width:160px;" value="${hdmpgl.ticketNum }"/>
								</li>
							</div>
							<div class="validate-group row" >
								<li class="form-validate">
									<label>起始号</label>
									<input type="text" id="numFrom" name="numFrom" style="width:160px;" onkeyup="value=value.replace(/[^0-9]/g,'')" value="${hdmpgl.numFrom }"/>
								</li>
							</div>
						</ul>
				</form>
				</div>
			</div>
		</div>
	</body>	
</html>
