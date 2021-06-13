<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.house.framework.commons.utils.PathUtil"%>
<!DOCTYPE HTML>
<html>
<head>
    <title>报表</title>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_intPerf.js?v=${v}" type="text/javascript"></script>
	<script type="text/javascript">
	function doExcel(){
		var iscupboard=$("#isCupboard").val();
		if(iscupboard==""){
			art.dialog({
				content:"是否橱柜不能为空！",
			});
			return;
		}
		var url = "${ctx}/admin/intPerfDetail/doReportExcel";
		doExcelAll(url);
	}
	function view(){
		var ret =selectDataTableRow();
		var countType=$.trim($("#countType").val());
		var no=$.trim($("#no").val());
		if(!ret){
			art.dialog({
				content:"请选择一条数据",
			});
			return;
		}
		Global.Dialog.showDialog("reportView",{
			title:"报表--查看",
			postData:{designMan:ret.number,no:$("#no").val(),isCupboard:$("#cg").val()},
			url:"${ctx}/admin/intPerfDetail/goReportView",
			height: 750,
		 	width:1350,
			returnFun: goto_query 
		});
	}
	/**初始化表格*/
	$(function(){
		$("#designMan").openComponent_employee();
		$("#no").openComponent_intPerf();
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/intPerfDetail/goReportJqGrid",
			postData:{isCupboard:"0"},
			height:$(document).height()-$("#content-list").offset().top-100,
			styleUI: "Bootstrap", 
			colModel : [
				{name: "number", index: "number", width: 73, label: "设计师", sortable: true, align: "left",hidden:true},
				{name: "namechi", index: "namechi", width: 73, label: "设计师", sortable: true, align: "left"},
				{name: "department2descr", index: "department2descr", width: 73, label: "部门", sortable: true, align: "left"},
				{name: "perfamount", index: "perfamount", width: 123, label: "业绩", sortable: true, align: "right",sum:true},
				{name: "perfamount_set", index: "perfamount_set", width: 118, label: "套餐内业绩", sortable: true, align: "right",sum:true},
				{name: "totalperfamount", index: "totalperfamount", width: 118, label: "总业绩", sortable: true, align: "right",sum:true},
			],
			ondblClickRow: function(){
				view();
			}
		});
	});
	function addQuery(){
		var iscupboard=$("#isCupboard").val();
		if(iscupboard==""){
			art.dialog({
				content:"是否橱柜不能为空！",
			});
			return;
		}
		$("#cg").val(iscupboard);
		goto_query();
	}
	</script>
</head>
<body>
	<div class="body-box-list">
		<div class="panel panel-system">
		    <div class="panel-body">
		      <div class="btn-group-xs">
					<button type="button" class="btn btn-system" onclick="view()">
						<span>查看</span>
					</button>
					<button type="button" class="btn btn-system" onclick="doExcel()">
						<span>导出excel</span>
					</button>
					<button type="button" class="btn btn-system" id="closeBut" onclick="closeWin(false)">
						<span>关闭</span>
					</button>
				</div>
			</div>
		</div>
		<div class="query-form">
			<form role="form" class="form-search" id="page_form" action="" method="post" target="targetFrame">
				<input type="hidden" name="jsonString" value=""/>
				<input type="hidden" name="cg" id="cg" value="0"/>
				<ul class="ul-form">
					<li><label>周期编号</label> <input type="text"
						style="width:160px;" id="no" name="no" />
					</li>
					<li><label>部门</label> <house:dict id="department2" dictCode=""
							sql="select a.code,a.desc1 Descr from tDepartment2 a where department1='15'"
							sqlValueKey="code" sqlLableKey="Descr"
							>
						</house:dict>
					</li>
					<li><label>设计师 </label> <input
						type="text" id="designMan" name="designMan" style="width:160px;" />
					</li>
					<li><label>是否橱柜</label> <house:xtdm id="isCupboard"
							dictCode="YESNO" style="width:160px;" value="0"></house:xtdm>
					</li>
					<li>
						<button type="button" class="btn btn-sm btn-system"
							onclick="addQuery();">查询</button>
						<button type="button" class="btn btn-sm btn-system"
							onclick="clearForm();">清空</button>
					</li>
				</ul>	
			</form>
		</div>
		<div class="pageContent">
			<div id="content-list">
				<table id="dataTable"></table>
				<div id="dataTablePager"></div>
			</div>
		</div> 
	</div>
</body>
</html>
