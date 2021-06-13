<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(function(){
	var lastCellRowId;
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/workCostDetail/goMemberJqGrid",
			postData:{no:"${workCost.no}"},
		    rowNum:10000000,
			height:250,
			styleUI: 'Bootstrap', 
			onSortColEndFlag:true,
			colModel : [
				{name: "namechi", index: "namechi", width: 70, label: "成员户名", sortable: true, align: "left"},
				{name: "idnum", index: "idnum", width: 150, label: "身份证号", sortable: true, align: "left"},
				{name: "cardid", index: "cardid", width: 150, label: "成员卡号", sortable: true, align: "left"},
				{name: "bank", index: "bank", width: 80, label: "发卡行", sortable: true, align: "left"},
				{name: "amount", index: "amount", width: 90, label: "本次发放金额", sortable: true, align: "right", sum: true},
				{name: "cashamount", index: "cashamount", width: 90, label: "现金发放金额", sortable: true, align: "right", sum: true},
				{name: "cmpamount", index: "cmpamount", width: 90, label: "公司发放金额", sortable: true, align: "right", sum: true},
				{name: "laborcmpdescr", index: "laborcmpdescr", width: 80, label: "劳务公司", sortable: true, align: "left", },
				{name: "thisyearprovidedamount", index: "thisyearprovidedamount", width: 90, label: "本年劳务已发放金额", sortable: true, align: "right", sum: true},
				{name: "thisyearunprovideamount", index: "thisyearunprovideamount", width: 90, label: "本年可发放金额", sortable: true, align: "right", sum: true},
				{name: "thismonprovidedamount", index: "thismonprovidedamount", width: 90, label: "当月劳务已发放金额", sortable: true, align: "right", sum: true},
				{name: "groupleadername", index: "groupleadername", width: 80, label: "班组长姓名", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 60, label: "工种", sortable: true, align: "left"},
				],
			onSortCol:function(index, iCol, sortorder){
					var rows = $("#memberDataTable").jqGrid("getRowData");
		   			rows.sort(function (a, b) {
		   				var reg = /^[0-9]+.?[0-9]*$/;
						if(reg.test(a[index])){
		   					return (a[index]-b[index])*(sortorder=="desc"?1:-1);
						}else{
		   					return a[index].toString().localeCompare(b[index].toString())*(sortorder=="desc"?1:-1);
						}  
		   			});    
		   			Global.JqGrid.clearJqGrid("memberDataTable"); 
		   			$.each(rows,function(k,v){
						Global.JqGrid.addRowData("memberDataTable", v);
					});
				}, 
 		};
       //初始化班组成员工资明细
	   Global.JqGrid.initJqGrid("memberDataTable",gridOption);
});
//导出excel
function doExcel_member(){
	var ids = $("#memberDataTable").getDataIDs();
	var rows = $("#memberDataTable").jqGrid("getRowData");
	var preName="班组成员工资明细";
	var pageFormId="form";
	var colModel = $("#memberDataTable").jqGrid('getGridParam','colModel');
	//遍历所有表格数据，去除工人卡号里面的空格
	 for(var i=0;i<ids.length;i++){
	 	rows[i].cardid=(rows[i].cardid).replace(new RegExp(" ","gm"),"");
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
					onclick="doExcel_member()">
					<span>导出excel </span>
				</button>
			</div>
		</div>
	</div>
	<div class="clear_float"></div>
	<!--query-form-->
	<div id="content-list">
		<table id="memberDataTable"></table>
	</div>
</div>

