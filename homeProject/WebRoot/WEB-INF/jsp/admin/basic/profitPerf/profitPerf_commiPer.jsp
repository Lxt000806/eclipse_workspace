<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>软装提成预发比例</title>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
<META HTTP-EQUIV="expires" CONTENT="0" />
<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
<%@ include file="/commons/jsp/common.jsp"%>

<script type="text/javascript">
	function add() {
		Global.Dialog.showDialog("profitPerfAdd", {
			title: "软装提成预发比例--新增",
			url: "${ctx}/admin/softPerfPrePer/goSave",
			height: 328 ,
			width: 458,
			returnFun : goto_query
		});
	}
	
	function update() {
		var ret = selectDataTableRow();
		if (ret) {
			Global.Dialog.showDialog("profitPerfUpdate", {
				title : "软装提成预发比例--编辑",
				url : "${ctx}/admin/softPerfPrePer/goUpdate?id=" + ret.pk,
				height : 328,
				width : 458,
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
			Global.Dialog.showDialog("profitPerfView", {
				title : "软装提成预发比例--查看",
				url : "${ctx}/admin/softPerfPrePer/goDetail?id=" + ret.pk,
				height : 328,
				width : 458
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}

	function del() {
		var ret = selectDataTableRow();
		if (ret) {
			art.dialog({
				content : "确认删除该记录",
				ok : function() {
					$.ajax({
						url : "${ctx}/admin/softPerfPrePer/doDelete?id=" + ret.pk,
						type : "post",
						dataType : "json",
						cache : false,
						error : function(obj) {
							art.dialog({
								content : "删除出错,请重试",
								time : 1000,
								beforeunload : function() {
									goto_query();
								}
							});
						},
						success : function(obj) {
							if (obj.rs) {
								goto_query();
							} else {
								$("#_form_token_uniq_id").val(obj.token.token);
								art.dialog({
									content : obj.msg,
									width : 200
								});
							}
						}
					});
				},
				cancel : function() {
					goto_query();
				}
			});
		} else {
			art.dialog({
				content : "请选择一条记录"
			});
		}
	}
	//导出EXCEL
	function doExcel() {
		var url = "${ctx}/admin/softPerfPrePer/doExcel";
		doExcelAll(url);
	}
	
	/**初始化表格*/
	$(function() {
		Global.LinkSelect.initSelect("${ctx}/admin/department1/byDepType","department1");
	    Global.JqGrid.initJqGrid("dataTable", {
	        url: "${ctx}/admin/softPerfPrePer/goJqGrid",
	        height: $(document).height() - $("#content-list").offset().top - 90,
	        styleUI: 'Bootstrap',
	        colModel: [
	            {name: 'pk', index: 'pk', width: 100, label: 'pk', sortable: true, align: "left", hidden: true},
	            {name: 'department1', index: 'department1', width: 80, label: '一级部门', sortable: true, align: "left" , hidden: true},
	            {name: 'department1descr', index: 'department1descr', width: 80, label: '一级部门', sortable: true, align: "left"},
	            {name: 'isoutsideemp', index: 'isoutsideemp', width: 80, label: '是否外部人员', sortable: true, align: "left", hidden: true},
	            {name: 'isoutsideempdescr', index: 'isoutsideempdescr', width: 80, label: '是否外部人员', sortable: true, align: "left"},
	            {name: 'per', index: 'per', width: 80, label: '发放比例', sortable: true, align: "right"},
	            {name: 'lastupdatedby', index: 'lastupdatedby', width: 100, label: '最后修改人员', sortable: true, align: "left"},
	            {name: 'lastupdate', index: 'lastupdate', width: 120, label: '最后修改时间', sortable: true, align: "left", formatter: formatTime},
	            {name: 'expired', index: 'expired', width: 100, label: '是否过期', sortable: true, align: "left"},
	            {name: 'actionlog', index: 'actionlog', width: 100, label: '操作日志', sortable: true, align: "left"}
	        ]
	    })
	})
	
</script>
</head>

<body>
	<div class="body-box-list">
		<div class="query-form">
			<form action="" method="post" id="page_form" class="form-search">
				<house:token></house:token>
				<input type="hidden" name="jsonString" value="" />
				<ul class="ul-form">
					<li class="form-validate">
							<label >一级部门</label>
							<select id="department1" name="department1"></select>
					</li>
					<li class="search-group"><input type="checkbox" id="expired"
						name="expired" value="${profitPerf.expired}"
						onclick="hideExpired(this)" ${profitPerf.expired!='T' ?'checked':'' }/>
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
				<button type="button" class="btn btn-system " onclick="update()">编辑</button>
				<button id="btnDel" type="button" class="btn btn-system "
					onclick="del()">删除</button>
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
