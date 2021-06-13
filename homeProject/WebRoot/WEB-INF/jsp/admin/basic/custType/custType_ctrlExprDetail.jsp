<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/ctrlExpr/goJqGrid",
			postData:{custType:"${custType.code}"},
		    rowNum:10000000,
			height:332,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "pk", index: "pk", width: 50, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "custtype", index: "custtype", width: 100, label: "客户类型", sortable: true, align: "left", hidden: true},
				{name: "custtypedescr", index: "custtypedescr", width: 100, label: "客户类型", sortable: true, align: "left"},
				{name: "ctrlexpr", index: "ctrlexpr", width: 110, label: "发包价公式", sortable: true, align: "left"},
				{name: "ctrlexprremarks", index: "ctrlexprremarks", width: 170, label: "发包公式说明", sortable: true, align: "left"},
				{name: "setctrlexpr", index: "setctrlexpr", width: 120, label: "指定发包价公式", sortable: true, align: "left"},
				{name: "begindate", index: "begindate", width: 100, label: "开始日期", sortable: true, align: "left", formatter: formatDate},
				{name: "enddate", index: "enddate", width: 100, label: "结束日期", sortable: true, align: "left", formatter: formatDate},
				{name: "lastupdatedby", index: "lastupdatedby", width: 90, label: "最后修改人", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 140, label: "最后修改时间", sortable: true, align: "left", formatter: formatTime},
				{name: "expired", index: "expired", width: 100, label: "过期", sortable: true},
				{name: "actionlog", index: "actionlog", width: 100, label: "操作", sortable: true},
			],
				onSortCol:function(index, iCol, sortorder){
					var rows = $("#dataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("dataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("dataTable", v);
					});
				},
 		};
	   Global.JqGrid.initJqGrid("dataTable",gridOption);
});
		
	 //新增
	function d_add() {
		Global.Dialog.showDialog("goSave",{
					title:"分段发包明细--增加",
					url:"${ctx}/admin/ctrlExpr/goSave?custType=${custType.code}",
				    height:300,
				    width:700,
					returnFun:function(){
						queryTable();
					}
		});
	}
	//编辑
	function d_update() {
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
						content:"请选择一条记录！",
						width: 200
					});
			return;
		}
		Global.Dialog.showDialog("goUpdate",{
			title:"分段发包明细--编辑",
			url:"${ctx}/admin/ctrlExpr/goUpdate?id="+ret.pk+"&custType=${custType.code}",
		    height:350,
		    width:700,
			returnFun:function(){
				queryTable();
			}
		});
	}
	//查看
	function d_detail() {
		var ret = selectDataTableRow();
		if(!ret){
			art.dialog({
						content:"请选择一条记录！",
						width: 200
					});
			return;
		}
		Global.Dialog.showDialog("goUpdate",{
			title:"分段发包明细--查看",
			url:"${ctx}/admin/ctrlExpr/goDetail?id="+ret.pk+"&custType=${custType.code}",
		    height:300,
		    width:700
		});
	}
	/** 刷新表格 **/
	function queryTable(){
		var code="${custType.code}";
		$("#dataTable").jqGrid("setGridParam",{postData:{custType:code},page:1,sortname:''}).trigger("reloadGrid");
	}
	function doExcel(){
		var url = "${ctx}/admin/ctrlExpr/doExcel?custType=${custType.code}";
		doExcelAll(url);
	}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="page_form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button id="saveBut" type="button" class="btn btn-system"
					onclick="d_add()">新增</button>
				<button id="updateBut" type="button" class="btn btn-system"
					onclick="d_update()">编辑</button>
				<button id="detailBut" type="button" class="btn btn-system"
					onclick="d_detail()">查看</button>
				<button id="excelBut" type="button" class="btn btn-system"
					onclick="doExcel()">导出Excel</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="dataTable"></table>
	</div>
</div>



