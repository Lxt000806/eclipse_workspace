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
	<title>系统指标新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_salaryEmp.js?v=${v}" type="text/javascript"></script>
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
			salaryEmp:{
				validators:{
					notEmpty:{
						message:"员工编号不能为空"
					}
				}
			},
			openComponent_salaryEmp_salaryEmp:{
				validators:{
					notEmpty:{
						message:"员工编号不能为空"
					}
				}
			},
			advanceWage:{
				validators:{
					notEmpty:{
						message:"预领金额不能为空"
					}
				}
			},
		},
        submitButtons : "input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
    });
	
	function validateRefresh(data){
		if(!data){
			return;
		}
		
		$("#dept1Descr").val(data.department1descr);
				
		$("#dataForm").data("bootstrapValidator")
		    .updateStatus("openComponent_salaryEmp_salaryEmp", "NOT_VALIDATED",null)  
		    .validateField("openComponent_salaryEmp_salaryEmp");
	}
	
	$("#salaryEmp").openComponent_salaryEmp({showValue:"${empAdvanceWage.salaryEmp}",showLabel:"${empAdvanceWage.empName}",
					callBack:validateRefresh,readonly:true});	
	
});


</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
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
									<label>员工编号</label>
									<input type="text" id="salaryEmp" name="salaryEmp" style="width:160px;"/>
								</li>
								<li class="form-validate">
									<label>一级部门</label>
									<input type="text" id="dept1Descr" name="dept1Descr" style="width:160px;" readonly="true" value="${empAdvanceWage.dept1Descr }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>预领金额</label>
									<input type="text" id="advanceWage" name="advanceWage" 
										onkeyup="value=value.replace(/[^\d.]/g,'')" style="width:160px;" value="${empAdvanceWage.advanceWage }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="3">${empAdvanceWage.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
