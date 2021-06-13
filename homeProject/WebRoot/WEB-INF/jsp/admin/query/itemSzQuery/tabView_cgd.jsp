<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_cgd",{
		url:"${ctx}/admin/itemSzQuery/goCgdJqGrid",
		postData:{custCode:$("#code").val(),purchaseStatus:$("#purchaseStatus").val(),isCheckOut:$("#isCheckOut").val(),itemType2:$("#cgdItemType2").val(),checkOutStatus:$("#checkOutStatus").val()},
        autowidth: false,
        height:190,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 100, label: "采购单号", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true},
			{name: "date", index: "date", width: 140, label: "楼盘", sortable: true, align: "left", hidden: true},
			{name: "typedescr", index: "typedescr", width: 80, label: "客户名称", sortable: true, align: "left", hidden: true},
			{name: "statusdescr", index: "statusdescr", width: 90, label: "采购单状态", sortable: true, align: "left"},
			{name: "date", index: "date", width: 90, label: "采购日期", sortable: true, align: "left", formatter: formatTime},
			{name: "typedescr", index: "typedescr", width: 80, label: "采购类型", sortable: true, align: "left"},
			{name: "whcodedescr", index: "whcodedescr", width: 90, label: "仓库名称", sortable: true, align: "left"},
			{name: "supplierdescr", index: "supplierdescr", width: 100, label: "供应商名称", sortable: true, align: "left"},
			{name: "amount", index: "amount", width: 80, label: "材料总价", sortable: true, align: "right", sum: true},
			{name: "othercost", index: "othercost", width: 80, label: "其它费用", sortable: true, align: "right", sum: true},
			{name: "othercostadj", index: "othercostadj", width: 100, label: "其它费用调整", sortable: true, align: "right", sum: true},
			{name: "firstamount", index: "firstamount", width: 80, label: "已付定金", sortable: true, align: "right", sum: true},
			{name: "secondamount", index: "secondamount", width: 90, label: "已付到货款", sortable: true, align: "right", sum: true},
			{name: "remainamount", index: "remainamount", width: 80, label: "应收余款", sortable: true, align: "right", sum: true},
			{name: "documentno", index: "documentno", width: 100, label: "凭证号", sortable: true, align: "left"},
			{name: "ischeckoutdescr", index: "ischeckoutdescr", width: 80, label: "是否结账", sortable: true, align: "left"},
			{name: "checkoutstatusdescr", index: "checkoutstatusdescr", width: 90, label: "结算单状态", sortable: true, align: "left"},
			{name: "checkoutno", index: "checkoutno", width: 90, label: "结账单号", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 170, label: "备注", sortable: true, align: "left"}
        ],
        gridComplete:function (){
        	$("#dataTable_cgdmx").jqGrid("clearGridData");
			var ret = selectDataTableRow("dataTable_cgd");
			if(ret){
				$("#dataTable_cgdmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goCgdmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
			}
        },
        beforeSelectRow:function(rowid, e){
			var ret = $("#dataTable_cgd").jqGrid("getRowData",rowid);
			if(ret){
				$("#dataTable_cgdmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goCgdmxJqGrid",postData:{no:ret.no,},page:1}).trigger("reloadGrid");
			}
        }
	});	
	Global.JqGrid.initJqGrid("dataTable_cgdmx",{
        autowidth: false,
        height:190,
		width:1259, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "itcode", index: "itcode", width: 90, label: "材料编号", sortable: true, align: "left"},
			{name: "itcodedescr", index: "itcodedescr", width: 220, label: "材料名称", sortable: true, align: "left"},
			{name: "sqlcodedescr", index: "sqlcodedescr", width: 100, label: "品牌", sortable: true, align: "left"},
			{name: "qtycal", index: "qtycal", width: 100, label: "采购数量", sortable: true, align: "right", sum: true},
			{name: "uomdescr", index: "uomdescr", width: 80, label: "单位", sortable: true, align: "left"},
			{name: "unitprice", index: "unitprice", width: 80, label: "单价", sortable: true, align: "right"},
			{name: "amount", index: "amount", width: 90, label: "总价", sortable: true, align: "right", sum: true},
			{name: "remarks", index: "remarks", width: 220, label: "备注", sortable: true, align: "left"}
        ]
	});
});
function cgd_goto_query(){
	$("#dataTable_cgd").jqGrid("setGridParam",{postData:{custCode:$("#code").val(),purchaseStatus:$("#purchaseStatus").val(),isCheckOut:$("#isCheckOut").val(),itemType2:$("#cgdItemType2").val(),checkOutStatus:$("#checkOutStatus").val()},page:1}).trigger("reloadGrid");
}
</script>
<div class="panel panel-info" style="border-top:0px"> 
	<form action="" method="post" id="page_form_cgd" class="form-search" >
		<input type="hidden" name="jsonString" value="" />
		<ul class="ul-form">
			<li>
				<label>材料类型2</label>
				<house:dict id="cgdItemType2" dictCode="" sql=" select rtrim(Code)+' '+Descr fd,Code from tItemType2 where ItemType1='RZ' and Expired='F' order by dispSeq " sqlValueKey="Code" sqlLableKey="fd"></house:dict>
			</li>
			<li>
				<label>采购单状态</label>
				<house:xtdm id="purchaseStatus" dictCode="PURCHSTATUS"></house:xtdm>
			</li>
			<li>
				<label>是否结账</label>
				<house:xtdm id="isCheckOut" dictCode="YESNO"></house:xtdm>
			</li>
			<li>
				<label>结算单状态</label>
				<house:xtdm id="checkOutStatus" dictCode="SPLCKOTSTATUS"></house:xtdm>
			</li>
			<li>
				<button type="button" class="btn btn-sm btn-system " onclick="cgd_goto_query()">查询</button>
			</li>
		</ul>	
	</form>
	<table id="dataTable_cgd"></table>
	<div class="panel panel-system">
		<div class="panel-body">
			<div class="btn-group-xs">
				<div style="float:left">采购单明细</div><button type="button" class="btn btn-system" onclick="doExcel('dataTable_cgdmx')">导出excel</button>
			</div>
		</div>
	</div>
	<table id="dataTable_cgdmx"></table>
</div>
