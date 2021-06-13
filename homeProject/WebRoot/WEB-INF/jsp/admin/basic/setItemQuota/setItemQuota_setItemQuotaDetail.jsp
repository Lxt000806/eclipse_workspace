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
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>客户退款新增</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_item.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	$(function(){
		$("#itemCode").openComponent_item({
				showValue:"${setItemQuota.itemCode}",
				showLabel:"${setItemQuota.descr}",
				callBack:validateRefresh_descr,
		});
		if ("V" == "${setItemQuota.m_umState}"){
			disabledForm("dataForm");
			$("#saveBtn").hide();
		}
		var originalData = $("#page_form").serialize();
		
		
		$("#saveBtn").on("click",function(){
			$("#dataForm").bootstrapValidator("validate");/* 提交验证 */
			if(!$("#dataForm").data("bootstrapValidator").isValid()) return; 
			
			var judgeSendDescr=$("#judgeSend").find("option:selected").text();
			var arr = judgeSendDescr.split(" ");
	 		var judgeSendDescr=arr[arr.length-1];
			
			/* 将page_form json化后赋给selectRows */
	 		var selectRows = [];
			var datas=$("#dataForm").jsonForm();
			datas.judgeSendDescr=(judgeSendDescr=="请选择..."?"":judgeSendDescr);
		 	selectRows.push(datas);
		 	//console.log(selectRows);
			Global.Dialog.returnData = selectRows;
			
			closeWin();
		});	
		
		$("#dataForm").bootstrapValidator({
			message : "This value is not valid",
			feedbackIcons : {/*input状态样式图片*/
				validating : "glyphicon glyphicon-refresh"
			},
			fields: { 
				openComponent_item_itemCode:{  
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}              
					}  
				},
				qty:{ 
					validators: {  
						notEmpty: {  
							message: "请输入完整的信息"  
						}, 
						digits : {
						    message : "字段必须是正整数"
						}	
					}
				},
			}
		});
	});	
	
	
	
	function validateRefresh_descr(data){
		var code = data.itemCode;
		$("#descr").val(data.descr);
	}
	
	</script>
</head>
<body>
	<div class="body-box-form">
        <div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
				<button type="button" class="btn btn-system" id="saveBtn" >
					<span>保存</span>
				</button>
				<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
					<span>关闭</span>
				</button>
			</div>
		</div>
	</div>
	<div class="panel panel-info">  
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<house:token></house:token>
				<input type="hidden" id="descr" name="descr" value="${setItemQuota.descr }" />
				<input type="hidden" id="lastupdatedby" name="lastupdatedby" value="${sessionScope.USER_CONTEXT_KEY.czybh}"/>
				<ul class="ul-form">	
					<li class="form-validate">
						<label ><span class="required">*</span>材料编号</label>
						<input type="text" id="itemCode" name="itemCode" style="width:160px;"/>
					</li>
					<li class="form-validate">
						<label ><span class="required">*</span>数量</label>
						<input type="text" id="qty" name="qty" style="width:160px;" value="${setItemQuota.qty }" onkeyup="value=value.replace(/[^\-?\d.]/g,'')"/>
					</li>
					<li>
						<label >已发货判断方式</label>
						<house:xtdm id="judgeSend" dictCode="JudgeSend" style="width:160px;" value="${setItemQuota.judgeSend}"/>
					</li>
					<li>
						<label>区域</label>
						<input type="text" id="fixArea" name="fixArea" style="width:160px;" value="${setItemQuota.fixArea }"/>
					</li>
				</ul>	
			</form>
		</div>
	</div>
</div>
</body>
</html>


