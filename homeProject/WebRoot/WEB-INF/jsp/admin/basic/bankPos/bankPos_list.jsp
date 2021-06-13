<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Pos终端管理</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
	function add() {
		Global.Dialog.showDialog("bankPosAdd", {
			title : "收款Pos--增加",
			url : "${ctx}/admin/bankPos/goSave",
			height : 600,
			width : 600,
			returnFun : goto_query
		});
	}
	function copy() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("bankPosCopy", {
				title : "收款Pos--复制",
				url : "${ctx}/admin/bankPos/goCopy?code=" + ret.code,
				height : 600,
				width : 600,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	function update() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("bankPosUpdate", {
				title : "收款Pos--修改",
				url : "${ctx}/admin/bankPos/goUpdate?code=" + ret.code,
				height : 600,
				width : 600,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	function view() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("bankPosView", {
				title : "收款Pos--查看",
				url : "${ctx}/admin/bankPos/goDetail?code=" + ret.code,
				height : 600,
				width : 600,
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	//导出EXCEL
	function doExcel() {
		var url = "${ctx}/admin/bankPos/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url : "${ctx}/admin/bankPos/goJqGrid",
			height : $(document).height() - $("#content-list").offset().top
					- 90,
			styleUI : 'Bootstrap',
			colModel : [
					{name: "code", index: "code", width: 80, label: "编号", sortable: true, align: "left"},
					{name: "descr", index: "descr", width: 120, label: "Pos机名称", sortable: true, align: "left"},
					{name: "rcvactdescr", index: "rcvactdescr", width: 80, label: "收款账号", sortable: true, align: "left"},
					{name: "posid", index: "posid", width: 130, label: "Pos机终端号", sortable: true, align: "left"},
					{name: "compcode", index: "compcode", width: 130, label: "商户号", sortable: true, align: "left"},
					{name: "compname", index: "compname", width: 100, label: "商家名称", sortable: true, align: "left"},
					{name: "minfee", index: "minfee", width: 80, label: "最低手续费", sortable: true, align: "right"},
					{name: "maxfee", index: "maxfee", width: 80, label: "封顶手续费", sortable: true, align: "right"},
					{name: "feeperc", index: "feeperc", width: 100, label: "手续费百分比", sortable: true, align: "right"},
					{name: "acquirefeeperc", index: "acquirefeeperc", width: 130, label: "收单服务费百分比", sortable: true, align: "right"},
					{name: "cardattr", index: "cardattr", width: 70, label: "卡性质", sortable: true, align: "left", hidden: true},
					{name: "cardattrdescr", index: "cardattrdescr", width: 70, label: "卡性质", sortable: true, align: "left"},
					{name: "cardtypedescr", index: "cardtypedescr", width: 70, label: "卡类型", sortable: true, align: "left"},
					{name: "lastupdate", index: "lastupdate", width: 120, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
					{name: "lastupdatedby", index: "lastupdatedby", width: 80, label: "修改人", sortable: true, align: "left"},
					{name: "expired", index: "expired", width: 80, label: "是否过期", sortable: true, align: "left"},
					{name: "actionlog", index: "actionlog", width: 98, label: "操作", sortable: true, align: "left"}
			]
		});

	});
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li><label>编号</label> <input type="text" id="code" name="code"
						style="width:160px;" value="${bankPos.code}" />
					</li>
					<li><label>Pos机名称</label> <input type="text" id="descr"
						name="descr" style="width:160px;" value="${bankPos.descr}" />
					</li>
					<li><label>收款账号</label> <house:dict id="rcvAct"
							dictCode=""
							sql="select Code, rtrim(Code)+' '+Descr fd from tRcvAct order by Code "
							sqlValueKey="Code" sqlLableKey="fd" value="${bankPos.rcvAct}">
						</house:dict>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${bankPos.expired}"
						onclick="hideExpired(this)" ${bankPos.expired!='T' ?'checked':'' }/>
						<p>隐藏过期</p>
						<button type="button" class="btn  btn-sm btn-system "
							onclick="goto_query();">查询</button>
						<button type="button" class="btn btn-sm btn-system "
							onclick="clearForm();">清空</button></li>
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>
		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="add()">新增</button>
				<button id="btnDel" type="button" class="btn btn-system "
					onclick="copy()">复制</button>
				<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				<house:authorize authCode="BANKPOS_VIEW">
					<button id="btnView" type="button" class="btn btn-system "
						onclick="view()">查看</button>
				</house:authorize>
				<button type="button" class="btn btn-system " onclick="doExcel()">导出到Excel</button>

			</div>
		</div>
		<div id="content-list">
			<table id="dataTable"></table>
			<div id="dataTablePager"></div>
		</div>
	</div>
</body>
</html>
