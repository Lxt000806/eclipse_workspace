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
	<title>客户投诉管理列表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	
	<script type="text/javascript"> 
	function doExcel(){
		var url = "${ctx}/admin/custComplaint/doExcel";
		doExcelAll(url);
	}
	$(function(){
		$("#crtCZY").openComponent_employee();
	
		var postData = $("#page_form").jsonForm();
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/custComplaint/goJqGrid",
			//postData:{custStatus:"4,3"},
			height:$(document).height()-$("#content-list").offset().top-82,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "no", index: "no", width: 100, label: "编号", sortable: true, align: "left"},
				{name: "source", index: "source", width: 121, label: "问题来源", sortable: true, align: "left"},
				{name: "compltypedescr", index: "compltypedescr", width: 75, label: "问题类型", sortable: true, align: "left"},
				{name: "crtdate", index: "crtdate", width: 108, label: "接诉时间", sortable: true, align: "left", formatter: formatTime},
				{name: "statusdescr", index: "statusdescr", width: 82, label: "状态", sortable: true, align: "left"},
				{name: "custdescr", index: "custdescr", width: 75, label: "业主姓名", sortable: true, align: "left"},
				{name: "address", index: "address", width: 163, label: "楼盘地址", sortable: true, align: "left"},
				{name: "projectmandescr", index: "projectmandescr", width: 75, label: "项目经理", sortable: true, align: "left"},
				{name: "projectdept2", index: "projectdept2", width: 80, label: "工程部", sortable: true, align: "left"},
				{name: "remarks", index: "remarks", width: 218, label: "接诉内容", sortable: true, align: "left"},
				{name: "crtczydescr", index: "crtczydescr", width: 78, label: "登记人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 111, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
				{name: "lastupdatedby", index: "lastupdatedby", width: 106, label: "最后更新人员", sortable: true, align: "left"},
				{name: "expired", index: "expired", width: 101, label: "过期标志", sortable: true, align: "left"},
				{name: "actionlog", index: "actionlog", width: 98, label: "操作日志", sortable: true, align: "left"},
			
			],
		});
		/* $("#dataTable").jqGrid('sortableRows', {  
			items : '.jqgrow:not(.unsortable)'  
		}); */
		//行拖动
		
		$("#save").on("click",function(){
			var ret=selectDataTableRow();
			Global.Dialog.showDialog("save",{
				title:"客户投诉管理——新增",
				url:"${ctx}/admin/custComplaint/goSave",
				//postData:{},
				height:750,
				width:1250,
				returnFun:goto_query,
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
				title:"客户投诉管理——编辑",
				url:"${ctx}/admin/custComplaint/goUpdate",
				postData:{no:ret.no},
				height:750,
				width:1250,
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
				title:"客户投诉管理——查看",
				url:"${ctx}/admin/custComplaint/goView",
				postData:{no:ret.no},
				height:750,
				width:1250,
				returnFun:goto_query,
			});
		});
		
		$("#detail").on("click",function(){
			var ret=selectDataTableRow();
			Global.Dialog.showDialog("detail",{
				title:"客户投诉管理——明细查询",
				url:"${ctx}/admin/custComplaint/goDetail",
				//postData:{no:ret.no},
				height:700,
				width:1250,
				returnFun:goto_query,
			});
		});
	});
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
							<label>编号</label>
							<input type="text" id="no" name="no" style="width:160px;"/>
						</li>
						<li>
							<label>楼盘地址</label>
							<input type="text" id="address" name="address" style="width:160px;"/>
						</li>
						<li>
							<label>工程部</label>
							<house:department2 id="department2" dictCode="03"></house:department2>
						</li>
						<li>
							<label>登记人</label>
							<input type="text" id="crtCZY" name="crtCZY" style="width:160px;"/>
						</li>
						<li>
							<label>投诉时间从</label>
							<input type="text" id="dateFrom" name="dateFrom" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"/>
						</li>
						<li>
							<label>至</label>
							<input type="text" id="dateTo" name="dateTo" class="i-date" style="width:160px;" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
						</li>
						<li>
							<label>状态</label>
							<house:xtdm id="status" dictCode="COMPSTATUS" style="width:160px;"></house:xtdm>
						</li>
						<li>
							<label>责任部门1</label>
							<house:department1 id="promDept1Code" onchange="changeDepartment1(this,'promDept2Code','${ctx }')"></house:department1>
						</li>
						<li>
							<label>责任部门2</label>
							<house:department2 id="promDept2Code" dictCode=""></house:department2>
						</li>
						<li>
							<label>问题类型</label>
							<house:xtdm id="complType" dictCode="COMPLTYPE" style="width:160px;"></house:xtdm>
						</li>
						<li class="search-group">					
							<input type="checkbox" id="expired_show" name="expired_show"
							 value="${custComplaint.expired }" onclick="hideExpired(this)" 
							 ${custComplaint.expired!='T'?'checked':''}/><p>隐藏过期</p>
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
					<house:authorize authCode="CUSTCOMPLAINT_SAVE">
						<button type="button" class="btn btn-system" id="save"><span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTCOMPLAINT_UPDATE">
						<button type="button" class="btn btn-system" id="update"><span>编辑</span> 
						</button>
					</house:authorize>
					<house:authorize authCode="CUSTCOMPLAINT_VIEW">
						<button type="button" class="btn btn-system" id="view"><span>查看</span> 
						</button>
					</house:authorize>
						<button type="button" class="btn btn-system" id="detail"><span>明细查询</span> 
						</button>
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
