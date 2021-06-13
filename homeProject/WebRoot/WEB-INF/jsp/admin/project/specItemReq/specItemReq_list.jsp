<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort()+path+"/";
String jasperPath = PathUtil.WEB_INF+"jsp/jasper/";
%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<title>集成解单管理</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<%@ include file="/commons/jsp/common.jsp"%>
	
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
		    <form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
		    	<house:token></house:token>
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" id="expired" name="expired" value=""/>
				<ul class="ul-form">
					<li>
						<label>楼盘</label>
						<input type="text" id="address" name="address" style="width:160px;"/>
					</li>
					<li>
						<label>客户状态</label>
						<house:xtdmMulit id="status" dictCode="CUSTOMERSTATUS" selectedValue="4"/>
					</li>
					<li class="search-group">
						<button type="button" class="btn btn-sm btn-system" onclick="goto_query();">查询</button>
						<button id="clear" type="button" class="btn btn-sm btn-system" onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="clear_float"></div>
	<div class="btn-panel">
		<div class="btn-group-sm">
			<house:authorize authCode="SPECITEMREQ_CUPSPEC">
				<button type="button" class="btn btn-system" id="cupSpec">
					<span>橱柜解单</span>
				</button>
			</house:authorize>
			<house:authorize authCode="SPECITEMREQ_INTSPEC">
				<button type="button" class="btn btn-system" id="intSpec">
					<span>衣柜解单</span>
				</button>
			</house:authorize>
			<house:authorize authCode="SPECITEMREQ_VIEW">
				<button type="button" class="btn btn-system" id="view" onclick="view()">
					<span>查看</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="JCJDGL_VIEWDETAIL">
				<button type="button" class="btn btn-system" id="view" onclick="detailQuery()">
					<span>解单明细</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="SPECITEMREQ_SETSTACK">
				<button type="button" class="btn btn-system" id="view" onclick="setStack()">
					<span>解单干系人</span>
				</button>
		  	</house:authorize>
		  	<house:authorize authCode="SPECITEMREQ_DOEXCEL">	
				 <button type="button" class="btn btn-system" onclick="doExcel()">
					<span>导出excel</span>
				</button> 
			</house:authorize>
		</div>
	</div>
	<div id="content-list">
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div> 
</body>	
<script>
$(function(){
	var postData = $("#page_form").jsonForm();
	var gridOption ={
		url:"${ctx}/admin/specItemReq/goJqGrid",
		postData:postData,
		height:$(document).height()-$("#content-list").offset().top-70,
		styleUI:"Bootstrap", 
		colModel : [
			{name: "custcode", index: "custcode", width: 80, label: "客户编号", sortable: true, align: "left"},
			{name: "custname", index: "custname", width: 80, label: "客户名称", sortable: true, align: "left"},
			{name: "address", index: "address", width: 180, label: "楼盘", sortable: true, align: "left"},
			{name: "status", index: "status", width: 80, label: "客户状态", sortable: true, align: "left", hidden:true},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "客户状态", sortable: true, align: "left"},
			{name: "custtype", index: "custtype", width: 80, label: "客户类型", sortable: true, align: "left",hidden:true},
			{name: "custtypedescr", index: "custtypedescr", width: 80, label: "客户类型", sortable: true, align: "left"},
			{name: "area", index: "area", width: 60, label: "面积", sortable: true, align: "right"},
			{name: "intdesign", index: "intdesign", width: 80, label: "集成设计师", sortable: true, align: "left"},
			{name: "cupdesign", index: "cupdesign", width: 80, label: "橱柜设计师", sortable: true, align: "left"},
			{name: "cupdownhigh", index: "cupdownhigh", width: 90, label: "橱柜地柜延米", sortable: true, align: "right"},
			{name: "cupuphigh", index: "cupuphigh", width: 90, label: "橱柜吊柜延米", sortable: true, align: "right"},
			{name: "bathdownhigh", index: "bathdownhigh", width: 90, label: "浴室地柜延米", sortable: true, align: "right"},
			{name: "bathuphigh", index: "bathuphigh", width: 90, label: "浴室吊柜延米", sortable: true, align: "right"},
			{name: "jcspecman", index: "jcspecman", width: 80, label: "集成解单员", sortable: true, align: "left"},
			{name: "cgspecman", index: "cgspecman", width: 80, label: "橱柜解单员", sortable: true, align: "left"},
			{name: "cuplastupdatedby", index: "cuplastupdatedby", width: 80, label: "橱柜修改人", sortable: true, align: "left",hidden:true},
			{name: "cuplastupdatedbydescr", index: "cuplastupdatedbydescr", width: 80, label: "橱柜修改人", sortable: true, align: "left"},
			{name: "cuplastupdate", index: "cuplastupdate", width: 132, label: "橱柜修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "intlastupdatedby", index: "intlastupdatedby", width: 80, label: "衣柜修改人", sortable: true, align: "left",hidden:true},
			{name: "intlastupdatedbydescr", index: "intlastupdatedbydescr", width: 80, label: "衣柜修改人", sortable: true, align: "left"},
			{name: "intlastupdate", index: "intlastupdate", width: 132, label: "衣柜修改日期", sortable: true, align: "left", formatter: formatTime},
			{name: "hascheck", index: "hascheck", width: 132, label: "存在集成审核", sortable: true, align: "left"},
		],
		ondblClickRow: function(){
			view();
		}
	};
	Global.JqGrid.initJqGrid("dataTable",gridOption);

	$("#clear").on("click",function(){
		$("#status").val("");
		$.fn.zTree.getZTreeObj("tree_status").checkAllNodes(false);
	});

	$("#cupSpec").on("click",function(){
		ret=selectDataTableRow();
		if(!ret){
			art.dialog({
				content: "请选择一条记录",
			});
			return;
		}
		if ("4" != ret.status) {
			art.dialog({
				content: "合同施工状态才可操作",
			});
			return;
		}
		if("1" == $.trim(ret.hascheck)){
			art.dialog({
				content: "存在集成材料已结算,无法操作",
			});
			return;
		}
		Global.Dialog.showDialog("cupSpec",{
			title:"集成解单管理——橱柜解单",
			url:"${ctx}/admin/specItemReq/goCupSpec",
			postData:{
				custCode:ret.custcode,
				custName:ret.custname,
				address:ret.address,
				custType:ret.custtype,
				cupDownHigh:ret.cupdownhigh,
				cupUpHigh:ret.cupuphigh,
				cupLastupdatedBy:ret.cuplastupdatedby,
				cupLastupdatedByDescr:ret.cuplastupdatedbydescr,
				cupLastUpdate:ret.cuplastupdate,
				intLastupdatedBy:ret.intlastupdatedby,
				intLastupdate:ret.intlastupdate,
			},
			height:700,
			width:1070,
			returnFun:goto_query
		});
	});
	
	$("#intSpec").on("click",function(){
		ret=selectDataTableRow();
		if(!ret){
			art.dialog({
       			content: "请选择一条记录",
       		});
       		return;
		}
		if ("4" != ret.status) {
			art.dialog({
				content: "合同施工状态才可操作",
			});
			return;
		}
		if("1" == $.trim(ret.hascheck)){
			art.dialog({
				content: "存在集成材料已结算,无法操作",
			});
			return;
		}
		Global.Dialog.showDialog("intSpec",{
			title:"集成解单管理——衣柜解单",
			url:"${ctx}/admin/specItemReq/goIntSpec",
			postData:{
				custCode:ret.custcode,
				custName:ret.custname,
				address:ret.address,
				custType:ret.custtype,
				intLastupdatedBy:ret.intlastupdatedby,
				intLastupdatedByDescr:ret.intlastupdatedbydescr,
				intLastupdate:ret.intlastupdate,
				cupDownHigh:ret.cupdownhigh,
				cupUpHigh:ret.cupuphigh,
				cupLastupdatedBy:ret.cuplastupdatedby,
				cupLastUpdate:ret.cuplastupdate,
				bathUpHigh:ret.bathuphigh,
				bathDownHigh:ret.bathdownhigh
			},
			height:700,
			width:1070,
			returnFun:goto_query
		});
	});
	
});

function view(){
	ret=selectDataTableRow();
	if(!ret){
		art.dialog({
  			content: "请选择一条记录",
  		});
  		return;
	}
	Global.Dialog.showDialog("view",{
		title:"集成解单管理——查看",
		url:"${ctx}/admin/specItemReq/goView",
		postData:{
			custCode:ret.custcode,
			custName:ret.custname,
			address:ret.address,
			custType:ret.custtype,
			cupDownHigh:ret.cupdownhigh,
			cupUpHigh:ret.cupuphigh,
			cupLastupdatedBy:ret.cuplastupdatedby,
			cupLastupdatedByDescr:ret.cuplastupdatedbydescr,
			cupLastUpdate:ret.cuplastupdate,
			intLastupdatedBy:ret.intlastupdatedby,
			intLastupdatedByDescr:ret.intlastupdatedbydescr,
			intLastupdate:ret.intlastupdate,
			bathDownHigh:ret.bathdownhigh,
			bathUpHigh:ret.bathuphigh,
		},
		height:700,
		width:1070,
		returnFun:goto_query
	});
}
function detailQuery(){
	Global.Dialog.showDialog("DetailQuery",{
		title:"集成解单管理——明细查询",
		url:"${ctx}/admin/specItemReq/goDetailQuery",
		height:700,
		width:1300,
		returnFun:goto_query
	});
}
function setStack(){
	var ret = selectDataTableRow();
    if (ret) {
   	      Global.Dialog.showDialog("setStack",{
			  title:"解单干系人",
			  url:"${ctx}/admin/specItemReq/goSetStack?custCode="+ret.custcode,
			  height:400,
			  width:700,
			  returnFun: goto_query
		  });
	 }else {
    	art.dialog({
			content: "请选择一条记录"
		});
     }
}
function doExcel(){
	var url = "${ctx}/admin/specItemReq/doExcel";
	doExcelAll(url);
}
</script>
</html>
