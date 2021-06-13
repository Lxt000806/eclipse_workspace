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
	<title>薪酬计算</title>
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

});

function doCalc(){

	var salaryScheme = $.trim($("#salaryScheme").val());
	var salaryMon = $.trim($("#salaryMon").val());
	
	if(salaryScheme ==""){
		art.dialog({
			content:"薪酬项目为空，请先设置薪酬项目"
		});
		return;
	}
	
	if(salaryMon ==""){
		art.dialog({
			content:"计算月份为空，请先设置月份"
		});
		return;
	}
	
	art.dialog({
		content:"是否确认开始计算薪酬",
		lock: true,
		ok: function () {
			var datas = {};		
			
			datas.salaryScheme = salaryScheme;
			datas.salaryMon = salaryMon;	
			datas.calcAll = $("#calcAll").val();
			
		    Global.Dialog.returnData = datas;
			closeWin(true);
		},
		cancel: function () {
			return true;
		}
	}); 
}

function chgCalcAll(e){
	if ($(e).is(':checked')){
		$('#calcAll').val('1');
	}else{
		$('#calcAll').val('0');
	}
}

</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system " id="saveBtn" onclick="doCalc()">
							<span>计算</span>
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
						<input type="hidden" id="calcAll" name="calcAll" value="1"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>月份</label>
									<house:dict id="salaryMon" dictCode="" sql=" select salaryMon, descr from tSalaryMon where status <> '3'"  
										 sqlValueKey="salaryMon" sqlLableKey="descr" value="${salaryData.salaryMon }" disabled="true"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬方案</label>
									<house:dict id="salaryScheme" dictCode="" sql="select pk,descr from tSalaryScheme"  
										 sqlValueKey="pk" sqlLableKey="descr" disabled="true" value="${salaryData.salaryScheme }"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬方案类型</label>
									<house:dict id="salarySchemeType" dictCode="" sql="select code, descr from tSalarySchemeType"  
										 sqlValueKey="code" sqlLableKey="descr" disabled="true" value="${salaryData.salarySchemeType }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li style="padding-left:50px;padding-right: 50px">
									<span style="color:blue">${cmpEmpNumInfo }</span>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
