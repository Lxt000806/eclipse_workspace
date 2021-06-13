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
	<title>固定资产管理增加</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_department.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_assetType.js?v=${v}" type="text/javascript"></script>
</head>
	<body>
		<div class="panel-body">
			<form action="" method="post" id="dataForm" class="form-search">
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<div class="validate-group row">
						<li>
							<label>注销人</label>
							<input type="text" id="decCZY" name="decCZY" style="width:160px;"/>
						</li>
						<li class="form-validate">
							<label>注销时间</label>
							<input type="text" id="decDate" name="decDate" class="i-date" 
								style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" 
								value="<fmt:formatDate value='${asset.decDate }' pattern='yyyy-MM-dd'/>" disabled="true"/>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label>减少方式</label>
							<house:xtdm id="decType" dictCode="ASSETDECTYPE" value="${asset.decType }" disabled="true"></house:xtdm>
						</li>
					</div>
					<div class="validate-group row">
						<li class="form-validate">
							<label class="control-textarea">备注</label>
							<textarea id="decRemarks" name="decRemarks" readonly="readonly">${asset.decRemarks }</textarea>
						</li>
					</div>
				</ul>
			</form>
		</div>
	</body>	
</html>
