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
	<title>计算公式新增</title>
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
			descr:{
				validators:{
					notEmpty:{
						message:"公式名称不能为空"
					}
				}
			},
			expr:{
				validators:{
					notEmpty:{
						message:"公式不能为空"
					}
				}
			},
			exprRemarks:{
				validators:{
					notEmpty:{
						message:"公式说明不能为空"
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
	
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/commiExpr/doSave",
		type: "post",
		data: datas,
		dataType: "json",
		cache: false,
		error: function(obj){
			showAjaxHtml({"hidden": false, "msg": "保存数据出错~"});
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
						<house:token></house:token>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>公式名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>计算公式</label>
									<textarea id="expr" name="expr" rows="2"></textarea>
	 							</li>
								<li class="form-validate">
									<label class="control-textarea"><span class="required">*</span>计算公式说明</label>
									<textarea id="exprRemarks" name="exprRemarks" rows="2"></textarea>
	 							</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">右边销售基数公式</label>
									<textarea id="rightCardinalExpr" name="rightCardinalExpr" rows="2"></textarea>
	 							</li>
								<li class="form-validate">
									<label class="control-textarea">右边销售基数公式说明</label>
									<textarea id="rightCardinalExprRemarks" name="rightCardinalExprRemarks" rows="2"></textarea>
	 							</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
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
