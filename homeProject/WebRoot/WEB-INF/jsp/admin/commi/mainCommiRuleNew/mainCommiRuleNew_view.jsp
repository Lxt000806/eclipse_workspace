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
	<title>新主材提成规则管理新增</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function() {
	var lastCellRowId;
	var gridOption = {
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		url:"${ctx}/admin/mainCommiRuleNew/goItemDetailJqGrid",
		postData:{no:"${mainCommiRuleNew.no}"},
		colModel : [
			{name:"pk", index:"pk", width:80, label:"pk", sortable:true ,align:"left",hidden:true },
			{name:"no", index:"no", width:80, label:"规则编号", sortable:true ,align:"left",},
			{name:"itemtype2", index:"itemtype2", width:80, label:"材料类型2", sortable:true ,align:"left",hidden:true},
			{name:"itemtype2descr", index:"itemtype2descr", width:80, label:"材料类型2", sortable:true ,align:"left"},
			{name:"itemtype3", index:"itemtype3", width:80, label:"材料类型3", sortable:true ,align:"left",hidden:true},
			{name:"itemtype3descr", index:"itemtype3descr", width:80, label:"材料类型3", sortable:true ,align:"left"},
			{name:"lastupdate", index:"lastupdate", label:"最后修改时间", sortable: true, width:100, align:"left",formatter:formatDate}, 
			{name:"lastupdatedby", index:"lastupdatedby", label:"最后修改人", width:100, sortable: true, align:"left"}, 
			{name:"expired", index:"expired", label:"是否过期", width:100, sortable: true, align:"left"}, 
			{name:"actionlog", index:"actionlog", label:"操作代码", width:100, sortable: true, align:"left"}, 
		
		],
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);
	
});
</script>
</head>
	<body>
		<div class="body-box-form">
			<div class="panel panel-system" >
			    <div class="panel-body" >
			      <div class="btn-group-xs" >
						<button type="button" class="btn btn-system" id="saveBtn">
							<span>保存</span>
						</button>
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="jsonString" value="" />
						<input type="hidden" name="no" value="${mainCommiRuleNew.no}" />
						<ul class="ul-form">
							<li class="form-validate">
								<label><span class="required">*</span>提成类型</label>
								<house:xtdm id="commiType" dictCode="COMMITYPE" value="${mainCommiRuleNew.commiType }"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>毛利率从</label>
								<input type="text" id="fromProfit" name="fromProfit" style="width:160px;" value="${mainCommiRuleNew.fromProfit }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>毛利率至</label>
								<input type="text" id="toProfit" name="toProfit" style="width:160px;" value="${mainCommiRuleNew.toProfit }"/>
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>升级材料</label>
								<house:xtdm id="isUpgItem" dictCode="YESNO" value="${mainCommiRuleNew.isUpgItem }"></house:xtdm>                     
							</li>
							<li class="form-validate">
								<label><span class="required">*</span>提成比例</label>
								<input type="text" id="commiPerc" name="commiPerc" style="width:160px;" value="${mainCommiRuleNew.commiPerc }"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2">${mainCommiRuleNew.remarks}</textarea>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分配材料</a></li>
			    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
