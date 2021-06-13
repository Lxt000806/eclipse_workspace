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
	<title>计算公式管理编辑</title>
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
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript"> 
$(function() {
	$("#department").openComponent_department({showValue:"${commiExprRule.department}",showLabel:"${department.desc1}"});	
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			role:{
				validators:{
					notEmpty:{
						message:"角色不能为空"
					}
				}
			},
			prior:{
				validators:{
					notEmpty:{
						message:"优先级不能为空"
					}
				}
			},
			preCommiExprPK:{
				validators:{
					notEmpty:{
						message:"预发计算公式不能为空"
					}
				}
			},
			checkCommiExprPK:{
				validators:{
					notEmpty:{
						message:"结算计算公式不能为空"
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
		url:"${ctx}/admin/commiExprRule/doUpdate",
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
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" value="${commiExprRule.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>角色</label>
									<house:dict id="role" dictCode="" sqlValueKey="Code" sqlLableKey="Descr" 
									    sql="select Code,code+' '+Descr Descr from tRoll where Expired = 'F' and Code in ('01','24','00','63')" 
									    value="${commiExprRule.role }"></house:dict>
								</li>
								<li class="form-validate">
									<label>客户类型</label>
									<house:dict id="custType" dictCode="" sqlValueKey="Code" sqlLableKey="Descr"
										sql="select Code,code+' '+Desc1 Descr from tCusttype where Expired = 'F'" 
										value="${commiExprRule.custType }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>部门</label>
									<input type="text" id="department" name="department" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>优先级</label>
									<house:dict id="prior" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 9 " 
												sqlValueKey="code" sqlLableKey="descr" value="${commiExprRule.prior }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>预发计算公式</label>
									<house:dict id="preCommiExprPK" dictCode="" sql="select pk, Descr from tCommiExpr where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr" value="${commiExprRule.preCommiExprPK }"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>结算计算公式</label>
									<house:dict id="checkCommiExprPK" dictCode="" sql="select pk, Descr from tCommiExpr where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr" value="${commiExprRule.checkCommiExprPK }"></house:dict>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
