<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/workCostDetail/goCardJqGrid",
			postData:{no:"${workCost.no}"},
		    rowNum:10000000,
			height:260,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "cardid", index: "cardid", width: 150, label: "班组长卡号", sortable: true, align: "left"},
				{name: "cardid2", index: "cardid2", width: 150, label: "卡号2", sortable: true, align: "left"},
				{name: "actname", index: "actname", width: 80, label: "户名", sortable: true, align: "left"},
				{name: "confirmamount", index: "confirmamount", width: 80, label: "审批金额", sortable: true, align: "left", sum: true},
				{name: "qualityfee", index: "qualityfee", width: 80, label: "质保金", sortable: true, align: "left", sum: true},
				{name: "realamount", index: "realamount", width: 80, label: "实发金额", sortable: true, align: "left", sum: true},
				],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#gatherDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("gatherDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("gatherDataTable", v);
					});
				}, 
 		};
       //初始化集成进度明细
	   Global.JqGrid.initJqGrid("gatherDataTable",gridOption);
});
//导出excel
function doExcel(){
	var ids = $("#gatherDataTable").getDataIDs();
	var rows = $("#gatherDataTable").jqGrid("getRowData");
	var preName="按班组长汇总";
	var pageFormId="form";
	var colModel = $("#gatherDataTable").jqGrid('getGridParam','colModel');
	//遍历所有表格数据，去除工人卡号里面的空格
	 for(var i=0;i<ids.length;i++){
	 	rows[i].cardid=(rows[i].cardid).replace(new RegExp(" ","gm"),"");
	 	rows[i].cardid2=(rows[i].cardid2).replace(new RegExp(" ","gm"),"");
	 }
	if (!rows || rows.length==0){
		art.dialog({
			content: "无数据导出"
		});
		return;
	}
	var datas = {
		colModel: JSON.stringify(colModel),
		rows: JSON.stringify(rows),
		preName: preName
	};
	$.form_submit($("#"+pageFormId).get(0), {
		"action": ctx+"/system/doExcel",
		"jsonString": JSON.stringify(datas)
	});

}
</script>
<div class="body-box-list" style="margin-top: 0px;">
	<div class="panel panel-system">
		<div class="panel-body">
			<form role="form" class="form-search" id="form" method="post">
				<input type="hidden" name="jsonString" value="" />
			</form>
			<div class="btn-group-xs">
				<button style="align:left" type="button" class="btn btn-system "
					onclick="doExcel()">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="gatherDataTable"></table>
	</div>
</div>

