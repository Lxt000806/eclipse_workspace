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
	<title>干系人管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_customer.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_roll.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	function doExcel(){
		var url = "${ctx}/admin/custStakeholder/doExcel";
		doExcelAll(url);
	}
	$(function(){
		$("#custCode").openComponent_customer();	
		$("#empCode").openComponent_employee();	
		$("#role").openComponent_roll();	
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custStakeholder/goJqGrid",
			postData:{custStatus:"1,2,3,4"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "pk", index: "pk", width: 107, label: "pk", sortable: true, align: "left",hidden:true},
				{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
				{name: "documentno", index: "documentno", width: 84, label: "档案号", sortable: true, align: "left"},
				{name: "custname", index: "custname", width: 84, label: "客户名称", sortable: true, align: "left"},
				{name: "address", index: "address", width: 154, label: "楼盘名称", sortable: true, align: "left"},
				{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
				{name: "endcodedescr", index: "endcodedescr", width: 80, label: "结束代码", sortable: true, align: "left"},
				{name: "roledescr", index: "roledescr", width: 70, label: "角色", sortable: true, align: "left"},
				{name: "empcode", index: "empcode", width: 80, label: "员工编号", sortable: true, align: "left"},
				{name: "empname", index: "empname", width: 80, label: "员工姓名", sortable: true, align: "left"},
				{name: "isresigncust", index: "isresigncust", width: 80, label: "是否重签", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 133, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 115, label: "修改人", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 102, label: "是否过期", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 98, label: "操作", sortable: true, align: "left"}
			],
		});
		
		$("#save").on("click",function(){
			var ret=selectDataTableRow();
			Global.Dialog.showDialog("save",{
				title:"干系人管理——新增",
				url:"${ctx}/admin/custStakeholder/goSave",
				//postData:{},
				height:400,
				width:450,
				returnFun:goto_query
			});
		});
		
		$("#update").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"干系人管理——编辑",
				url:"${ctx}/admin/custStakeholder/goUpdate",
				postData:{pk:ret.pk},
				height:400,
				width:450,
				returnFun:goto_query,
			});
		});
		
		$("#copy").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("copy",{
				title:"干系人管理——复制",
				url:"${ctx}/admin/custStakeholder/goCopy",
				postData:{pk:ret.pk},
				height:400,
				width:450,
				returnFun:goto_query,
			});
		});
		
		$("#view").on("click",function(){
			var ret=selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			Global.Dialog.showDialog("update",{
				title:"干系人管理——查看",
				url:"${ctx}/admin/custStakeholder/goView",
				postData:{pk:ret.pk},
				height:400,
				width:450,
				returnFun:goto_query,
			});
		});
		
		$("#detail").on("click",function(){
			var ret=selectDataTableRow();
			Global.Dialog.showDialog("detail",{
				title:"干系人——明细查询",
				url:"${ctx}/admin/custStakeholder/goDetail",
				//postData:{no:ret.no},
				height:700,
				width:1250,
				returnFun:goto_query,
			});
		});
		
		$("#del").on("click",function(){
			var ret = selectDataTableRow();
			if(!ret){
				art.dialog({
					content:"请选择一条数据",
				});
				return;
			}
			art.dialog({
				content:"是否删除",
				lock: true,
				width: 200,
				height: 100,
				ok: function () {
					$.ajax({
						url:"${ctx}/admin/custStakeholder/doDel",
						type:'post',
						data:{pk:ret.pk},
						dataType:'json',
						cache:false,
						error:function(obj){
							showAjaxHtml({"hidden": false, "msg": '获取数据出错~'});
						},
						success:function(obj){
							art.dialog({
								content:obj.msg,
							});
							$("#dataTable").jqGrid("setGridParam",{postData:$("#page_form").jsonForm(),page:1}).trigger("reloadGrid");
						}
					});
				},
				cancel: function () {
						return true;
				}
			});	
		});
		
		$("#multiUpdate").on("click",function(){
			Global.Dialog.showDialog("mulltiUpdate",{
				title:"批量修改",
				url:"${ctx}/admin/custStakeholder/goMultiUpdate",
				height:730,
				width:1190,
				returnFun:goto_query
			});
		});
	});
	function clearForm(){
		$("#page_form input[type='text']").val('');
		$("#openComponent_customer_custCode").val('');
		$("#openComponent_roll_role").val('');
		$("#openComponent_employee_empCode").val('');
		$("#page_form select").val('');
		$("#address").val('');
		$("#custCode").val('');
		$("#role").val('');
		$("#empCode").val('');
		$("#custStatus").val('');
		$.fn.zTree.getZTreeObj("tree_custStatus").checkAllNodes(false);
	} 
	</script>
</head>
	<body>
		<div class="body-box-list">
			<div class="query-form">
				<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
					<input type="hidden" name="jsonString" value=""/>
					<input type="hidden" id="expired" name="expired" value=""/>
					<ul class="ul-form">
						<li>
							<label>角色</label>
							<input type="text" id="role" name="role" style="width:160px;"/>
						</li>
						<li>
							<label>客户编号</label>
							<input type="text" id="custCode" name="custCode" style="width:160px;"/>
						</li>
						<li>
							<label>签订时间从</label>
							<input type="text" id="signDateFrom" name="signDateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="signDateTo" name="signDateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
							<label>员工编号</label>
							<input type="text" id="empCode" name="empCode" style="width:160px;"/>
						</li>
						<li>
							<label>楼盘</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li>
							<label>结束时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date"  style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"  />
						</li>
						<li>
							<label>客户状态</label>
							<house:xtdmMulit id="custStatus" dictCode="CUSTOMERSTATUS" selectedValue="1,2,3,4"></house:xtdmMulit>                     
						</li>
						
						<li class="search-group">					
							<button type="button" class="btn btn-sm btn-system " onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
						</li>
					</ul>
				</form>
			</div>
		</div>
		<div class="clear_float"></div>
		<div class="pageContent">
			<div class="btn-panel">
	   			<div class="btn-group-sm">
					<house:authorize authCode="CUSTSTAKEHOLDER_ADD">
						<button type="button" class="btn btn-system" id="save"><span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTSTAKEHOLDER_COPY">
						<button type="button" class="btn btn-system" id="copy"><span>复制</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTSTAKEHOLDER_UPDATE">
						<button type="button" class="btn btn-system" id="update"><span>编辑</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTSTAKEHOLDER_DELETE">
						<button type="button" class="btn btn-system" id="del"><span>删除</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTSTAKEHOLDER_VIEW">
						<button type="button" class="btn btn-system" id="view"><span>查看</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTSTAKEHOLDER_MULTIUPDATE">
						<button type="button" class="btn btn-system "  id="multiUpdate"><span>批量修改</span> 
					</house:authorize>
					<!-- remove by zzr 2018/11/03  -->
<%-- 					<house:authorize authCode="CUSTSTAKEHOLDER_EXCEL">
						<button type="button" class="btn btn-system" onclick="doExcel()">
							<span>导出excel</span>
						</button>
					</house:authorize> --%>
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
				</div>
			</div>
		</div>			
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div> 
	</body>	
</html>
