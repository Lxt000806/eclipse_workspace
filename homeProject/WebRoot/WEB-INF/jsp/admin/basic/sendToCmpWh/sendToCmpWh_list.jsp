<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE html>
<html lang="zh">
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8"/>
	<META HTTP-EQUIV="pragma" CONTENT="no-cache"/>
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate"/>
	<META HTTP-EQUIV="expires" CONTENT="0"/>
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge"/>
	<title>发货到公司仓设置</title>
	<%@ include file="/commons/jsp/common.jsp"%>
	<script src="${resourceRoot}/pub/component_supplier.js?v=${v}" type="text/javascript"></script>
</head>
<body>
	<div class="body-box-list">
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" id="expired" name="expired" value="${sendToCmpWh.expired}"/>
				<input type="hidden" name="jsonString" value=""/>
				<ul class="ul-form">
					<li>
						<label>客户类型</label>
						<house:custTypeMulit id="custType" ></house:custTypeMulit>
					</li>
					<li>
						<label>供应商</label>
						<input type="text" id="supplCode" name="supplCode" value="${sendToCmpWh.supplCode}" />
					</li>
					<li>
						<label>区域</label> 
						<house:dict id="regionCode" dictCode=""
							sql="select a.Code,a.Code+' '+a.descr descr  from tRegion a where a.Expired='F' "
							sqlValueKey="Code" sqlLableKey="Descr"
							>
						</house:dict>
					</li>
					<li>
						<input type="checkbox" id="expired_show" name="expired_show" value="${sendToCmpWh.expired}" 
							onclick="hideExpired(this)" ${sendToCmpWh.expired!='T' ?'checked':'' }/>
						<label for="expired_show" style="margin-left: -3px;width: 50px;">隐藏过期</label>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button>
					</li>
				</ul>
			</form>
		</div>
	</div>
	<div class="pageContent">
		<div class="btn-panel">
			<div class="btn-group-sm">
				<house:authorize authCode="SENDTOCMPWH_SAVE">
					<button type="button" class="btn btn-system" id="save">
						<span>新增</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SENDTOCMPWH_COPY">
					<button type="button" class="btn btn-system" id="copy">
						<span>复制</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SENDTOCMPWH_UPDATE">
					<button type="button" class="btn btn-system" id="update">
						<span>编辑</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SENDTOCMPWH_VIEW">
					<button type="button" class="btn btn-system" id="view" onclick="view()">
						<span>查看</span>
					</button>
				</house:authorize>
				<house:authorize authCode="SENDTOCMPWH_EXCEL">
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
	</div>
	<script type="text/javascript">
		var postData = $("#page_form").jsonForm();
		$(function(){
			$("#supplCode").openComponent_supplier();
			Global.JqGrid.initJqGrid("dataTable", {
				url: "${ctx}/admin/sendToCmpWh/goJqGrid",
				postData:postData ,
				sortable: true,
				height: $(document).height() - $("#content-list").offset().top - 70,
				styleUI: "Bootstrap", 
				colModel: [
					{name:"pk", index:"pk", width:60, label:"pk", sortable:true, align:"left",hidden:true},
					{name:"custtypedescr", index:"custtypedescr", width:150, label:"客户类型", sortable:true, align:"left"},
					{name:"suppldescr", index:"suppldescr", width:100, label:"供应商", sortable:true, align:"left"},
					{name:"regiondescr", index:"regiondescr", width:80, label:"区域", sortable:true, align:"left"},
					{name:"amountfrom", index:"amountfrom", width:80, label:"采购金额从", sortable:true, align:"right"},
					{name:"amountto", index:"amountto", width:80, label:"采购金额到", sortable:true, align:"right"},
					{name:"itemapptype", index:"itemapptype", width:80, label:"领料单类型", sortable:true, align:"right", hidden:true},
					{name:"itemapptypedescr", index:"itemapptypedescr", width:80, label:"领料单类型", sortable:true, align:"right"},
		    		{name:"lastupdate", index:"lastupdate", width:120, label:"最后修改时间", sortable:true, align:"left", formatter:formatTime},
		      		{name:"lastupdatedby", index:"lastupdatedby", width:70, label:"修改人", sortable:true, align:"left"},
		     		{name:"expired", index:"expired", width:80, label:"是否过期", sortable:true, align:"left"},
		    		{name:"actionlog", index:"actionlog", width:60, label:"操作", sortable:true, align:"left"},
				],
				ondblClickRow: function(){
					view();
				}
			});
			$("#save").on("click", function() {
				Global.Dialog.showDialog("save", {
					title : "发货到公司仓——新增",
					url : "${ctx}/admin/sendToCmpWh/goSave",
					height : 350,
					width : 445,
					returnFun : goto_query
				});
			});
			$("#copy").on("click", function() {
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("copy", {
					title : "发货到公司仓——复制",
					url : "${ctx}/admin/sendToCmpWh/goCopy",
					postData : {id: ret.pk},
					height : 350,
					width : 445,
					returnFun : goto_query
				});
			});
			$("#update").on("click",function(){
				var ret=selectDataTableRow();
				if (!ret) {
					art.dialog({
						content: "请选择一条记录"
					});
					return;
				}
				Global.Dialog.showDialog("update",{
					title:"发货到公司仓——编辑",
					url:"${ctx}/admin/sendToCmpWh/goUpdate",
					postData:{
						id:ret.pk
					},
					height:400,
					width:445,
					returnFun:goto_query
				});
			});
		});
		function view(){
			var ret=selectDataTableRow();
			if (!ret) {
				art.dialog({
					content: "请选择一条记录"
				});
				return;
			}
			Global.Dialog.showDialog("view",{
				title:"发货到公司仓——查看",
				url:"${ctx}/admin/sendToCmpWh/goView",
				postData:{
					id:ret.pk
				},
				height:400,
				width:445,
				returnFun:goto_query
			});
		}
		function doExcel(){
			var url = "${ctx}/admin/sendToCmpWh/doExcel";
			doExcelAll(url);
		}
	</script>
</body>
</html>
