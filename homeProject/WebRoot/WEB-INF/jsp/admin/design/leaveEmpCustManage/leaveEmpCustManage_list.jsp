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
	<title>离职人员客户管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
		<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
		<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
<script type="text/javascript"> 
$(function(){
	$("#stakeholder").openComponent_employee();	
	var postData = $("#page_form").jsonForm();
	//初始化表格
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/leaveEmpCustManage/goJqGrid",
		postData:{showType:""} ,
		height:$(document).height()-$("#content-list").offset().top-82,
		styleUI: "Bootstrap", 
		colModel : [
			{name:"address",	index:"address",	width:180,	label:"楼盘",	sortable:true,align:"left",},
			{name:"custtypedescr",	index:"custtypedescr",	width:90,	label:"客户类型",	sortable:true,align:"left" ,},
			{name:"statusdescr",	index:"statusdescr",	width:90,	label:"客户状态",	sortable:true,align:"left" ,},
			{name:"layoutdescr",	index:"layoutdescr",	width:90,	label:"户型",	sortable:true,align:"left",},
			{name:"area",	index:"area",	width:70,	label:"面积",	sortable:true,align:"right",},
			{name:"businessmandescr",	index:"businessmandescr",	width:90,	label:"业务员",	sortable:true,align:"left",},
			{name:"businessdept",	index:"businessdept",	width:90,	label:"业务部",	sortable:true,align:"left",},
			{name:"againmandescr",	index:"againmandescr",	width:90,	label:"翻单员",	sortable:true,align:"left",},
			{name:"designmandescr",	index:"designmandescr",	width:110,	label:"设计师",	sortable:true,align:"left",},
			{name:"designdept",	index:"designdept",	width:70,	label:"设计部",	sortable:true,align:"left",},
			{name:"endcodedescr",	index:"endcodedescr",	width:90,	label:"结束代码",	sortable:true,align:"left",},
			{name:"sourcedescr",	index:"sourcedescr",	width:90,	label:"来源",	sortable:true,align:"left",},
			{name:"endremark",	index:"endremark",	width:200,	label:"备注",	sortable:true,align:"left",},
			{name:"crtdate",	index:"crtdate",	width:150,	label:"创建时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"setdate",	index:"setdate",	width:150,	label:"下定时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"signdate",	index:"signdate",	width:150,	label:"签订时间",	sortable:true,align:"left",formatter: formatTime},
			{name:"dpk",	index:"dpk",	width:150,	label:"设计干系人pk",	sortable:true,align:"left",hidden:true},
			{name:"bpk",	index:"bpk",	width:150,	label:"业务干系人pk",	sortable:true,align:"left",hidden:true},
		
		],
	});
	
	window.goto_query = function(){
		if($("#dateFrom").val()=="" || $("#dateTo").val() ==""){
			art.dialog({
				content:"离职日期请填写完整时间段",
			});
			return;
		}
		if($("#showType").val()==null ||$("#showType").val()=="" ){
			art.dialog({
				content:"请选择角色",
			});
			return;
		}
		$("#dataTable").jqGrid("setGridParam",{datatype:'json',postData:$("#page_form").jsonForm(),page:1,
					url:"${ctx}/admin/leaveEmpCustManage/goJqGrid",}).trigger("reloadGrid");
	}
	
	$("#updateBusinessMan").on("click",function(){
		Global.Dialog.showDialog("updateBusinessMan",{
			title:"更换业务员",
			postData:{dateFrom:$("#dateFrom").val(),dateTo:$("#dateTo").val()},
			url:"${ctx}/admin/leaveEmpCustManage/goUpdateBusinessMan",
			height:730,
			width:1190,
			returnFun:goto_query
		});
	});
	
	$("#multiUpdate").on("click",function(){
		Global.Dialog.showDialog("mulltiUpdate",{
			title:"批量修改",
			url:"${ctx}/admin/leaveEmpCustManage/goMultiUpdate",
			height:730,
			width:1190,
			returnFun:goto_query
		});
	});
});

function doExcel(){
	var url = "${ctx}/admin/leaveEmpCustManage/doExcel";
	doExcelAll(url,"dataTable","page_form");
}

function clearForm(){
	$("#page_form input[type='text']").val('');
	$("#page_form select").val('');
	$("#openComponent_employee_stakeholder").val('');
	$("#status_NAME").val('');
	$("#stakeholder").val('');
	$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
} 

</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form"  action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<ul class="ul-form">
						<li>
							<label>一级部门</label> 
							<house:department1 id="department1" onchange="changeDepartment1(this,'department2','${ctx }')"
																									value="${customer.department1 }">
							</house:department1>
						</li>
						<li>
							<label>二级部门</label> 
							<house:department2 id="department2" dictCode="${customer.department1 }"
								value="${customer.department2 }" onchange="changeDepartment2(this,'department3','${ctx }')">
							</house:department2>
						</li>
						<li>
							<label>离职日期从</label> 
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" 
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})"
								value="<fmt:formatDate value='${customer.dateFrom}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>至</label> 
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;"
								onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd '})" 
								value="<fmt:formatDate value='${customer.dateTo}' pattern='yyyy-MM-dd '/>" />
						</li>
						<li>
							<label>角色</label>
							<select id="showType" name="showType" style="width:160px">
								<option value="2" selected>业务员</option>
								<option value="1" >设计师</option>
							</select>
						</li>
						<li>
							<label>干系人</label>
							<input type="text" id="stakeholder" name="stakeholder" style="width:160px;"/>
						</li>
						<li>
							<label>客户状态</label> 
							<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="1,2,3"></house:xtdmMulit>
						</li>
						<li >
							<label>结束原因</label>
							<house:xtdm id="endCode" dictCode="CUSTOMERENDCODE" ></house:xtdm>
						</li>
						<li >
							<button type="button" class="btn  btn-sm btn-system " onclick="goto_query();"  >查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();"  >清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="pageContent">
			<div class="btn-panel" >
				<div class="btn-group-sm"  >
					<house:authorize authCode="LEAVEEMPCUSTMANAGE_UPDATEBUSINESSMAN">
						<button type="button" class="btn btn-system " id="updateBusinessMan"><span>更换业务员</span> 
					</house:authorize>
					<%-- <house:authorize authCode="LEAVEEMPCUSTMANAGE_MULTIUPDATE">
						<button type="button" class="btn btn-system "  id="multiUpdate"><span>批量修改</span> 
					</house:authorize> --%>
					<house:authorize authCode="LEAVEEMPCUSTMANAGE_EXCEL">
						<button type="button" class="btn btn-system "  onclick="doExcel()" >
							<span>导出excel</span>
					</house:authorize>
				</div>
			</div>
		</div>
		<div id="content-list">
			<table id= "dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
