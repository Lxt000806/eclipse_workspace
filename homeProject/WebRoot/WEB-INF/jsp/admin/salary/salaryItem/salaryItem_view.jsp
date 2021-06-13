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
	<title>薪酬项目新增</title>
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
									<label>薪酬代码</label>
									<input type="text" id="code" name="code" style="width:160px;" value="${salaryItem.code }" readonly="true"/>
								</li>
								<li class="form-validate">
									<label>薪酬名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${salaryItem.descr }" readonly="true"/>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>系统保留项目</label>
									<house:xtdm  id="isSysRetention" dictCode="YESNO" style="width:160px;" value="${salaryItem.isSysRetention}" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>支持手动修改</label>
									<house:xtdm  id="isAdjustable" dictCode="YESNO" style="width:160px;" value="${salaryItem.isAdjustable}" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>计算优先级</label>
									<house:dict id="itemLevel" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 1 and 30 " 
												sqlValueKey="code" sqlLableKey="descr" value="${salaryItem.itemLevel }" disabled="true"></house:dict>
								</li>
								<li class="form-validate">
									<label>薪酬项目分组</label>
									<house:xtdm  id="itemGroup" dictCode="SALITEMGROUP" style="width:160px;" value="${salaryItem.itemGroup}" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>状态</label>
									<house:xtdm  id="status" dictCode="SALENABLESTAT" style="width:160px;" value="${salaryItem.status}" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>数值精度</label>
									<house:dict id="precision" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 6 " 
												sqlValueKey="code" sqlLableKey="descr" value="${salaryItem.precision }" disabled="true"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>统计属性</label>
									<house:xtdm  id="type" dictCode="SALITEMTYPE" style="width:160px;" value="${salaryItem.type }" disabled="true"></house:xtdm>
								</li>
								<li class="form-validate">
									<label>加减项</label>
									<house:xtdm  id="plusMinus" dictCode="SALAPLUSMINUS" style="width:160px;" value="${salaryItem.plusMinus }" disabled="true"></house:xtdm>
								</li>
							</div>
							<div class="validate-group row" style="max-height: 160px">
								<li class="form-validate" style="max-height: 160px">
									<label class="control-textarea">薪酬说明</label>
									<textarea id="remarks" name="remarks" rows="8" readonly="true">${salaryItem.remarks }</textarea>
								</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
