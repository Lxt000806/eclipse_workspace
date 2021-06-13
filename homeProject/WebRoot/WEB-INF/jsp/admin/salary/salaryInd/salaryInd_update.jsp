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
	<title>系统指标编辑</title>
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
			code:{
				validators:{
					notEmpty:{
						message:"编号不能为空"
					}
				}
			},
			descr:{
				validators:{
					notEmpty:{
						message:"名称不能为空"
					}
				}
			},
			indLevel:{
				validators:{
					notEmpty:{
						message:"指标级别不能为空"
					}
				}
			},
			objType:{
				validators:{
					notEmpty:{
						message:"适用对象不能为空"
					}
				}
			},
			periodType:{
				validators:{
					notEmpty:{
						message:"统计周期不能为空"
					}
				}
			},
			calcMode:{
				validators:{
					notEmpty:{
						message:"计算方式不能为空"
					}
				}
			},
			indType:{
				validators:{
					notEmpty:{
						message:"指标分类不能为空"
					}
				}
			},
			status:{
				validators:{
					notEmpty:{
						message:"状态不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
});

function doUpdate(){

	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;
    
	var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryInd/doUpdate",
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
	    				closeWin(true);
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
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doUpdate()">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system " id="closeBut" onclick="closeWin(true)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>指标编号</label>
									<input type="text" id="code" name="code" style="width:160px;" readonly="true" value="${salaryInd.code }"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>指标名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${salaryInd.descr }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>指标级别</label>
									<house:xtdm  id="indLevel" dictCode="SALINDLEVEL" style="width:160px;" value="${salaryInd.indLevel }"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>适用对象</label>
									<house:xtdm  id="objType" dictCode="SALOBJTYPE" style="width:160px;" value="${salaryInd.objType }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>统计周期</label>
									<house:xtdm  id="periodType" dictCode="SALPERIODTYPE" style="width:160px;" value="${salaryInd.periodType }"></house:xtdm>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>计算方式</label>
									<house:xtdm  id="calcMode" dictCode="SALCALCMODE" style="width:160px;" value="${salaryInd.calcMode }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>指标分类</label>
									<house:xtdm  id="indType" dictCode="SALINDTYPE" style="width:160px;" value="${salaryInd.indType }"></house:xtdm>
								<li class="form-validate">
									<label>单位</label>
									<input type="text" id="indUnit" name="indUnit" style="width:160px;" value = "${salaryInd.indUnit }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="${salaryInd.status }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">指标说明</label>
									<textarea id="remarks" name="remarks" rows="3">${salaryInd.remarks}</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
