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
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<title>项目经理提成领取--列表</title>
<%@ include file="/commons/jsp/common.jsp" %>
<script type="text/javascript">
//初始化项目经理提成领取
$(function(){
	Global.JqGrid.initJqGrid("dataTable",{
		url:"${ctx}/admin/prjProvide/goJqGrid",
		postDate:{status:"1,2,3"},
		height:$(document).height()-$("#content-list").offset().top-70,
		colModel : [
			{name: "no", index: "no", width: 108, label: "领取单号", sortable: true, align: "left"},
			{name: "statusname", index: "statusname", width: 100, label: "领取状态", sortable: true, align: "left"},
			{name: "appczyname", index: "appczyname", width: 100, label: "开单人", sortable: true, align: "left"},
			{name: "date", index: "date", width: 140, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "remarks", index: "remarks", width: 140, label: "备注", sortable: true, align: "left"},
			{name: "confirmczyname", index: "confirmczyname", width: 100, label: "审核人员", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 140, label: "审核日期", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdate", index: "lastupdate", width: 143, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatebyname", index: "lastupdatebyname", width: 100, label: "修改人", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 80, label: "操作", sortable: true, align: "left"},
			{name: "status", index: "status", width: 80, label: "状态", sortable: true, align: "left",hidden:true}
		 ],
		 ondblClickRow:function(){
		 	view();
		 }
	});
});
//新增
function add(){
	Global.Dialog.showDialog("prjProvide",{
		title:"项目经理提成领取--新增",
		url:"${ctx}/admin/prjProvide/goAdd",
		width:1100,
		height:700,
		returnFun:goto_query
	});
}
//编辑 
function update(){
	var ret = selectDataTableRow();
	if (ret){
		if($.trim(ret.status)==2){
			art.dialog({
				content:"领取状态为审批通过，不允许进行编辑",
				width:200
			});
		}
		if($.trim(ret.status)==3){
			art.dialog({
				content:"领取状态为审批取消，不允许进行编辑",
				width:200
			});
		}
		if($.trim(ret.status)==1){
			Global.Dialog.showDialog("prjProvide",{
				url:"${ctx}/admin/prjProvide/goUpdate",
				postData :{no:ret.no,statusName:ret.statusname},
				title:"项目 经理提成领取--编辑 ",
				width:1100,
				height:700,
				returnFun:goto_query	
			});	
		}
	}else{
	 	art.dialog({
	 		content:"请选择一条记录"
	 	});
	}
}
//审核 
function check(){
	var ret = selectDataTableRow();
	if (ret) {
		if($.trim(ret.status)==2){
			art.dialog({
				content:"领取状态为审批通过，不允许进行审核",
				width:200
			});
		}
		if($.trim(ret.status)==3){
			art.dialog({
				content:"领取状态为审批取消，不允许进行审核",
				width:200
			});
		}
		if ($.trim(ret.status)==1) {
			Global.Dialog.showDialog("prjProvide",{
				url:"${ctx}/admin/prjProvide/goCheck",
				postData :{no:ret.no,statusName:ret.statusname},
				title:"项目经理提成领取--审核",
				height:700,
				width:1100,
				returnFun:goto_query
			});
		}	
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
}
//反审核
function checkReturn(){
	var ret = selectDataTableRow();
	if(ret){
		if($.trim(ret.status)==1){
			art.dialog({
				content:"领取状态为打开，不允许进行反审核",
				width:200
			});
		}
		if($.trim(ret.status)==3){
			art.dialog({
				content:"领取状态为取消，不允许进行反审核",
				width:200
			});
		}
		if($.trim(ret.status)==2){
			Global.Dialog.showDialog("prjProvide",{
				title:"项目经理提成领取--反审核",
				url:"${ctx}/admin/prjProvide/goCheckReturn",
				postData :{no:ret.no,statusName:ret.statusname},
				height:700,
				width:1100,
				returnFun:goto_query	
			});
		}
	}else{
		art.dialog({
			content:"请选择一条记录"
		});
	}
}
function view(){
	var ret = selectDataTableRow();
	Global.Dialog.showDialog("prjProvide",{
		url:"${ctx}/admin/prjProvide/goView",
		postData :{no:ret.no,statusName:ret.statusname},
		title:"项目经理提成领取--查看",
		height:700,
		width:1100,
	});
}
//导出execel
function doExcel() {
	var url = "${ctx}/admin/prjProvide/doExcel";
	doExcelAll(url);
}
function print(){
	var row = selectDataTableRow();
	if(!row){
		art.dialog({content: "请选择一条记录进行打印操作！",width: 200});
		return false;
	} 
	var reportName ="prjProvide.jasper";
    Global.Print.showPrint(reportName, {
	No: $.trim(row.no),
	LogoPath : "<%=basePath%>jasperlogo/",
	SUBREPORT_DIR: "<%=jasperPath%>" 
	});
} 	
//清空
function clearForm(){
$("#page_form input[type='text']").val('');
$("#page_form select").val('');
$("#status").val('');
$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
}
</script>
</head>    
<body>
	<div class="body-box-list">
  		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li>
						<label>申请时间从</label>
						<input type="text" id="dateFrom" name="dateFrom" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
					<li>
						<label>至</label>
						<input type="text" id="dateTo" name="dateTo" class="i-date" onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" />
					</li>
				   	<li>
						<label>领取状态</label>
						<house:xtdmMulit id="status" dictCode="" sql="select cbm SQL_VALUE_KEY,note SQL_LABEL_KEY from tXTDM where ID='WHChkOutStatus' and (CBM='1' or CBM='2'or CBM='3')" selectedValue="1,2,3"></house:xtdmMulit>
					</li>
				    <li class="search-group-shrink">
						<button type="summit" class="btn  btn-sm btn-system " onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system " onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
		<div class="pageContent">
			<div class="btn-panel">
				<div class="btn-group-sm">
					<house:authorize authCode="PRJPROVIDE_ADD">
						<button type="button" class="btn btn-system " onclick="add()">
							<span>新增</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PRJPROVIDE_UPDATE">
						<button type="button" class="btn btn-system " onclick="update()">
							<span>编辑</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PRJPROVIDE_CHECK">
						<button type="button" class="btn btn-system " onclick="check()">
							<span>审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PRJPROVIDE_CHECKRETURN">
						<button type="button" class="btn btn-system " onclick="checkReturn()">
							<span>反审核</span>
						</button>
					</house:authorize>
					<house:authorize authCode="PRJPROVIDE_VIEW">
						<button type="button" class="btn btn-system " onclick="view()">
							<span>查看</span>
						</button>
					</house:authorize>
					<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>
					<button type="button" class="btn btn-system " onclick="print()">打印</button>
				</div>
			</div>
		</div>		
  		<div id="content-list">
    		<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
  </body>
</html>




