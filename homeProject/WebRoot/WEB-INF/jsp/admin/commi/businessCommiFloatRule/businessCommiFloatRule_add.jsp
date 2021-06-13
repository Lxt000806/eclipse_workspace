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
	<title>提成浮动规则明细新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_docFolder.js?v=${v}" type="text/javascript"></script>
	<style type="text/css">
		::-webkit-input-placeholder { /* Chrome */
		  color: #cccccc;
		}
	</style>
<script type="text/javascript"> 
$(function() {
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			cardinalFrom:{
				validators:{
					notEmpty:{
						message:"提成基数从不能为空"
					}
				}
			},
			cardinalTo:{
				validators:{
					notEmpty:{
						message:"提成基数至不能为空"
					}
				}
			},
			commiPer:{
				validators:{
					notEmpty:{
						message:"提成点不能为空"
					}
				}
			},
			subsidyPer:{
				validators:{
					notEmpty:{
						message:"补贴点不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doSave(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
	
	var cardinalFrom = $("#cardinalFrom").val();
	var cardinalTo = $("#cardinalTo").val();
	var commiPer = $("#commiPer").val();
	var subsidyPer = $("#subsidyPer").val();
	/* if(cardinalFrom != "" && cardinalFrom > 1){
		art.dialog({
			content:"提成基数只能在0-1之间",
		});
		return;
	}
	if(cardinalTo != "" && cardinalTo > 1){
		art.dialog({
			content:"提成基数只能在0-1之间",
		});
		return;
	} */
	if(commiPer != "" && commiPer > 1){
		art.dialog({
			content:"提成点数只能在0-1之间",
		});
		return;
	}
	if(subsidyPer != "" && subsidyPer > 1){
		art.dialog({
			content:"补贴点只能在0-1之间",
		});
		return;
	}
	
	var datas=$("#dataForm").jsonForm();
	Global.Dialog.returnData = datas;
	closeWin();
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doSave()">
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
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>提成基数从</label>
									<input type="number" id="cardinalFrom" name="cardinalFrom" step="0.001" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>提成基数至</label>
									<input type="number" id="cardinalTo" name="cardinalTo" step="0.001" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>提成点</label>
									<input type="number" id="commiPer" name="commiPer" step="0.001" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>补贴点</label>
									<input type="number" id="subsidyPer" name="subsidyPer" step="0.001" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2"></textarea>
	 							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
