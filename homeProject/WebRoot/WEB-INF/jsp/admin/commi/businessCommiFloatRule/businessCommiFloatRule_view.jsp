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
	<title>业务提成浮动编辑</title>
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
	$("#dataForm").bootstrapValidator({
		message :"This value is not valid",
		feedbackIcons : {/*input状态样式图片*/
			validating :"glyphicon glyphicon-refresh"
		},
		fields: {  
			descr:{  
				validators: {  
					notEmpty: {  
						message:"专盘名称不能为空"  
					}
				}  
			},
		},
		submitButtons :"input[type='submit']"/*bootstrap validator 会在form 寻找input标签 类型是submit的，然后绑定事件*/
	});
});	
$(function() {
	var lastCellRowId;
	var gridOption = {
		url:"${ctx}/admin/businessCommiFloatRuleDetail/goJqGrid",
		postData:{floatRulePK :"${businessCommiFloatRule.pk }"},
		height:$(document).height()-$("#content-list").offset().top-80,
		rowNum:10000000,
		colModel : [
			{name:"floatrulepk", index:"floatrulepk", width:80, label:"pk", sortable:true ,align:"left",hidden:true,},
			{name:"cardinalfrom", index:"cardinalfrom", width:130, label:"提成基数从(大等于)", sortable:true ,align:"right"},
			{name:"cardinalto", index:"cardinalto", width:120, label:"提成基数至(小于)", sortable:true ,align:"right"},
			{name:"commiper", index:"commiper", width:80, label:"提成点", sortable:true ,align:"right"},
			{name:"subsidyper", index:"subsidyper", width:80, label:"补贴点", sortable:true ,align:"right"},
			{name:"remarks", index:"remarks", width:220, label:"说明", sortable:true ,align:"left"},
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
						<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
							<span>关闭</span>
						</button>
					</div>
				</div>
			</div>
			<div class="panel panel-info" >  
				<div class="panel-body">
					<form action="" method="post" id="dataForm" class="form-search">
						<input type="hidden" name="pk" id="pk" value="${businessCommiFloatRule.pk }" />
						<input type="hidden" name="jsonString" value="" />
						<ul class="ul-form">
							<li class="form-validate">
								<label>规则名称</label>
								<input type="text" id="descr" name="descr" style="width:160px;" value="${businessCommiFloatRule.descr }"
								 		readonly="true"/>
							</li>
							<li>
								<label class="control-textarea">备注</label>
								<textarea id="remarks" name="remarks" rows="2" readonly="true">${businessCommiFloatRule.remarks}</textarea>
							</li>
						</ul>	
					</form>
				</div>
			</div>
			<div class="container-fluid" >  
				<ul class="nav nav-tabs" > 
			        <li class="active"><a href="#tab_detail" data-toggle="tab">分段明细</a></li>
			    </ul> 
				<div id="content-list">
					<table id="dataTable"></table>
				</div>	
			</div>
		</div>
	</body>	
</html>
