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
	
});
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
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
									<label>公式名称</label>
									<input type="text" id="descr" name="descr" style="width:160px;" value="${commiExpr.descr }"/>
								</li>
							</div>
							<div class="validate-group row">
								<li>
									<label class="control-textarea">计算公式</label>
									<textarea id="expr" name="expr" rows="2">${commiExpr.expr }</textarea>
	 							</li>
	 							<li>
									<label class="control-textarea">计算公式说明</label>
									<textarea id="exprRemarks" name="exprRemarks" rows="2">${commiExpr.exprRemarks }</textarea>
	 							</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label class="control-textarea">右边销售基数公式</label>
									<textarea id="rightCardinalExpr" name="rightCardinalExpr" rows="2">${commiExpr.rightCardinalExpr }</textarea>
	 							</li>
								<li class="form-validate">
									<label class="control-textarea">右边销售基数公式说明</label>
									<textarea id="rightCardinalExprRemarks" name="rightCardinalExprRemarks" rows="2">${commiExpr.rightCardinalExprRemarks }</textarea>
	 							</li>
							</div>
							<div class="validate-group row">
	 							<li>
									<label class="control-textarea">备注</label>
									<textarea id="remarks" name="remarks" rows="2">${commiExpr.remarks }</textarea>
	 							</li>
							</div>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</body>	
</html>
