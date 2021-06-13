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
	<title>薪酬调整编辑</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
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
	console.log("${salaryDataAdjust.salaryItem }");
	function getEmpData(data){
		if(!data) return;

		$("#dept1Descr").val(data.department1descr);
	}
	$("#salaryEmp").openComponent_salaryEmp({showValue:"${salaryEmp.empCode}",showLabel:"${salaryEmp.empName}",callBack:getEmpData,readonly:true});	
	
	$("#dataForm").bootstrapValidator({
		message : "This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating : "glyphicon glyphicon-refresh"
		},
		fields: {
			salaryItem:{
				validators:{
					notEmpty:{
						message:"编号不能为空"
					}
				}
			},
			adjustValue:{
				validators:{
					notEmpty:{
						message:"名称不能为空"
					}
				}
			},
			openComponent_salaryEmp_salaryEmp:{
				validators:{
					notEmpty:{
						message:"员工不能为空"
					}
				}
			},
			salaryItem:{
				validators:{
					notEmpty:{
						message:"薪酬项目不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
});

function doChgEmpSalary(){
	$("#dataForm").bootstrapValidator("validate");
    if (!$("#dataForm").data("bootstrapValidator").isValid()) return;

    var datas = $("#dataForm").serialize();
	$.ajax({
		url:"${ctx}/admin/salaryCalc/doChgEmpSalaryUpdate",
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
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doChgEmpSalary()">
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
						<input type="hidden" name="pk" id= "pk" value="${salaryDataAdjust.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label><span class="required">*</span>员工</label>
									<input type="text" id="salaryEmp" name="salaryEmp" style="width:160px;" value="${salaryEmp.empCode }"/>
								</li>
								<li class="form-validate">
									<label>部门</label>
									<input type="text" id="dept1Descr" name="dept1Descr" style="width:160px;" readonly="true" value="${department1.desc2 }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>月份</label>
									<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon where status <> '3'"  
										 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryDataAdjust.salaryMon }" disabled="true"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬方案</label>
									<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
										 sqlValueKey="pk" sqlLableKey="descr" disabled="true" value="${salaryDataAdjust.salaryScheme }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>薪酬方案类型</label>
									<house:dict id="salarySchemeType" dictCode="" sql="select code, descr from tSalarySchemeType"  
										 sqlValueKey="code" sqlLableKey="descr" disabled="true" value="${salaryScheme.salarySchemeType }"></house:dict>
								</li>
								<li class="form-validate">
									<label><span class="required">*</span>薪酬项目</label>
									<house:dict id="salaryItem" dictCode="" sql="select code, descr from tSalaryItem where IsAdjustable = '1'"  
										 sqlValueKey="code" sqlLableKey="descr" value="${salaryDataAdjust.salaryItem }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>金额</label>
									<input type="text" id="adjustValue" name="adjustValue" style="width:160px;" value="${salaryDataAdjust.adjustValue }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="3">${salaryDataAdjust.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
