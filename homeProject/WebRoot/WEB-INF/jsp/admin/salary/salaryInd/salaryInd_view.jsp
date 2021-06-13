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
	<title>系统指标查看</title>
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
						<input type="hidden" id="status" name="status" style="width:160px;" readonly="true" value="${salaryInd.status}" />
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>指标编号</label>
									<input type="text" id="code" name="code" style="width:160px;" readonly="true" value="${salaryInd.code }"/>
								</li>
								<li class="form-validate">
									<label>指标名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${salaryInd.descr }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>指标级别</label>
									<house:xtdm  id="indLevel" dictCode="SALINDLEVEL" style="width:160px;" 
									 	disabled="true" value="${salaryInd.indLevel }"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>适用对象</label>
									<house:xtdm  id="objType" dictCode="SALOBJTYPE" style="width:160px;" 
									 	disabled="true"  value="${salaryInd.objType }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>统计周期</label>
									<house:xtdm  id="periodType" dictCode="SALPERIODTYPE" style="width:160px;" 
									 	disabled="true"  value="${salaryInd.periodType }"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>计算方式</label>
									<house:xtdm  id="calcMode" dictCode="SALCALCMODE" style="width:160px;" 
									 	disabled="true"  value="${salaryInd.calcMode }"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>指标分类</label>
									<house:xtdm  id="indType" dictCode="SALINDTYPE" style="width:160px;" 
									 	disabled="true"  value="${salaryInd.indType }"></house:xtdm>
								<li class="form-validate">
									<label>单位</label>
									<input type="text" id="indUnit" name="indUnit" style="width:160px;" 
									 	readonly="true"  value = "${salaryInd.indUnit }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value = "${salaryInd.status}"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">指标说明</label>
									<textarea id="remarks" name="remarks" rows="3" readonly="true">${salaryInd.remarks}</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
