<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>系统参数表</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
	/**修改*/
	function update() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("xtcs", {
				title : "系统参数--编辑",
				url : "${ctx}/admin/xtcs/goUpdate?id=" + ret.ID,
				height : 400,
				width : 650,
				returnFun : goto_query
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	/**显示详细信息*/
	function view() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("xtcsView", {
				title : "系统参数--查看",
				url : "${ctx}/admin/xtcs/goView",
				postData : {
					id : ret.ID,
					qz : ret.QZ,
					sm : ret.SM,
					smE : ret.SM_E,
				},
				height : 400,
				width : 650
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	/**导出EXCEL*/
	function doExcel() {
		var url = "${ctx}/admin/xtcs/doExcel";
		doExcelAll(url);
	}
	/**初始化表格*/
	$(function() {
		Global.JqGrid.initJqGrid("dataTable", {
			url : "${ctx}/admin/xtcs/goJqGrid",
			height : $(document).height() - $("#content-list").offset().top-70,
			styleUI : "Bootstrap",
			colModel : [ {
				name : "ID",
				index : "ID",
				width : 100,
				label : "参数代码",
				sortable : true,
				align : "left"
			}, {
				name : "QZ",
				index : "QZ",
				width : 200,
				label : "取值",
				sortable : true,
				align : "left"
			}, {
				name : "SM",
				index : "SM",
				width : 200,
				label : "说明",
				sortable : true,
				align : "left"
			}, {
				name : "SM_E",
				index : "SM_E",
				width : 200,
				label : "英文说明",
				sortable : true,
				align : "left"
			}, ]
		});
	});
</script>
</head>

<body style="scrollOffset : 0">
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
			<!-- 		<div class="panel-body"> -->
						<li><label>参数代码</label> <input type="text" id="id" name="id"
							style="width:160px;" value="${xtcs.id}" />
						</li>
						<li><label>说明</label> <input type="text" id="sm" name="sm"
							style="width:160px;" value="${xtcs.sm}" />
						</li>

						<li class="search-group">
							<button type="button" class="btn  btn-sm btn-system "
								onclick="goto_query();">查询</button>
							<button type="button" class="btn btn-sm btn-system "
								onclick="clearForm();">清空</button></li>
			<!-- 		</div> -->
				</ul>
			</form>
		</div>

		<div class="clear_float"></div>

		<div class="btn-panel">
			<div class="btn-group-sm">
				<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				<button id="btnView" type="button" class="btn btn-system "
					onclick="view()">查看</button>
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
