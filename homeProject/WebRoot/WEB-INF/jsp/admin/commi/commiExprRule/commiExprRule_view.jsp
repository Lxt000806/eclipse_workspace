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
						<house:token></house:token>
						<input type="hidden" name="jsonString" value=""/>
						<input type="hidden" name="pk" value="${commiExprRule.pk }"/>
						<ul class="ul-form">
							<div class="validate-group row">
								<li class="form-validate">
									<label>角色</label>
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
									<label>优先级</label>
									<house:dict id="prior" dictCode="" sql="select p.number code ,p.number descr from master..spt_values p 
												where p.type = 'p' and p.number between 0 and 9 " 
												sqlValueKey="code" sqlLableKey="descr" value="${commiExprRule.prior }"></house:dict>
								</li>
							</div>
							<div class="validate-group row">
								<li class="form-validate">
									<label>预发计算公式</label>
									<house:dict id="preCommiExprPK" dictCode="" sql="select pk, Descr from tCommiExpr where expired = 'F' " 
												sqlValueKey="pk" sqlLableKey="Descr" value="${commiExprRule.preCommiExprPK }"></house:dict>
								</li>
								<li class="form-validate">
									<label>结算计算公式</label>
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
