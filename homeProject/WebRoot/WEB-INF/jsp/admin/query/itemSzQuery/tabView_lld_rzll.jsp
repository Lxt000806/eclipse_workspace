<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>

<%@ taglib prefix="house" uri="http://www.housenet.com/Framework/tags" %>

<script type="text/javascript">
$(function(){
	Global.JqGrid.initJqGrid("dataTable_lld_rzll",{
		url:"${ctx}/admin/itemSzQuery/goLldJqGrid",
		postData:{custCode:$("#code").val(),type:"RZ",itemAppStatus:$("#itemAppStatus").val(),itemType2:$("#rzllItemType2").val()},
        autowidth: false,
        height:165,
		width:1300, 
		styleUI: 'Bootstrap',
		rowNum: 10000,
		colModel : [
			{name: "no", index: "no", width: 100, label: "领料单号", sortable: true, align: "left"},
			{name: "itemtype1descr", index: "itemtype1descr", width: 80, label: "材料类型1", sortable: true, align: "left", hidden: true,},
			{name: "address", index: "address", width: 140, label: "楼盘", sortable: true, align: "left", hidden: true},
			{name: "custdescr", index: "custdescr", width: 80, label: "客户名称", sortable: true, align: "left", hidden: true},
			{name: "itemtype2descr", index: "itemtype2descr", width: 100, label: "材料类型2", sortable: true, align: "left"},
			{name: "statusdescr", index: "statusdescr", width: 120, label: "领料单状态", sortable: true, align: "left"},
			{name: "issetitemdescr", index: "issetitemdescr", width: 80, label: "套餐材料", sortable: true, align: "left"},
			{name: "documentno", index: "documentno", width: 100, label: "凭证号", sortable: true, align: "left"},
			{name: "appname", index: "appname", width: 80, label: "申请人员", sortable: true, align: "left"},
			{name: "date", index: "date", width: 120, label: "申请日期", sortable: true, align: "left", formatter: formatTime},
			{name: "confirmname", index: "confirmname", width: 80, label: "审批人员", sortable: true, align: "left"},
			{name: "confirmdate", index: "confirmdate", width: 120, label: "审批日期", sortable: true, align: "left", formatter: formatTime},
			{name: "clcost", index: "clcost", width: 90, label: "材料成本", sortable: true, align: "right", sum: true},
			{name: "othercost", index: "othercost", width: 90, label: "其他费用", sortable: true, align: "right", sum: true},
			{name: "othercostadj", index: "othercostadj", width: 100, label: "其他费用调整", sortable: true, align: "right", sum: true},
			{name: "bcost", index: "bcost", width: 90, label: "成本", sortable: true, align: "right", sum: true},
			{name: "clcostjs", index: "clcostjs", width: 100, label: "材料成本结算", sortable: true, align: "right", sum: true},
			{name: "projectothercost", index: "projectothercost", width: 122, label: "项目经理其它费用", sortable: true, align: "right", sum: true},
			{name: "sendname", index: "sendname", width: 80, label: "发货人员", sortable: true, align: "left"},
			{name: "senddate", index: "senddate", width: 120, label: "发货日期", sortable: true, align: "left", formatter: formatTime},
			{name: "sendtypedescr", index: "sendtypedescr", width: 100, label: "发货类型", sortable: true, align: "left"},
			{name: "suppldescr", index: "suppldescr", width: 100, label: "供应商", sortable: true, align: "left"},
			{name: "puno", index: "puno", width: 100, label: "采购单号", sortable: true, align: "left"},
			{name: "checkoutno", index: "checkoutno", width: 110, label: "结算单号", sortable: true, align: "left"},
			{name: "checkoutdate", index: "checkoutdate", width: 120, label: "结算日期", sortable: true, align: "left", formatter: formatTime},
			{name: "remainamount", index: "remainamount", width: 100, label: "应付/付退余款", sortable: true, align: "right", sum: true},
			{name: "secondamount", index: "secondamount", width: 105, label: "付/付退到货款", sortable: true, align: "right", sum: true},
			{name: "warehouse", index: "warehouse", width: 100, label: "仓库名称", sortable: true, align: "left"},
			{name: "remarks", index: "remarks", width: 100, label: "备注", sortable: true, align: "left"},
			{name: "lastupdate", index: "lastupdate", width: 140, label: "最后更新时间", sortable: true, align: "left", formatter: formatTime},
			{name: "lastupdatedby", index: "lastupdatedby", width: 140, label: "最后更新人员", sortable: true, align: "left"},
			{name: "expired", index: "expired", width: 100, label: "是否过期", sortable: true, align: "left"},
			{name: "actionlog", index: "actionlog", width: 100, label: "操作日志", sortable: true, align: "left"}
        ],
        gridComplete:function(){
        	$("#dataTable_lld_llmx").jqGrid("clearGridData");
			var ret = selectDataTableRow("dataTable_lld_rzll");
			if(ret){
				$("#dataTable_lld_llmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLldLlmxJqGrid",postData:{custCode:$("#code").val(),no:ret.no,},page:1}).trigger("reloadGrid");
			}
        },
        beforeSelectRow:function(rowid, e){
			var ret = $("#dataTable_lld_rzll").jqGrid("getRowData",rowid);
			if(ret){
				$("#dataTable_lld_llmx").jqGrid("setGridParam",{url:"${ctx}/admin/itemSzQuery/goLldLlmxJqGrid",postData:{custCode:$("#code").val(),no:ret.no,},page:1}).trigger("reloadGrid");
			}
        }
	});
});
function rzll_goto_query(){
	$("#dataTable_lld_rzll").jqGrid("setGridParam",{postData:{custCode:$("#code").val(),type:"RZ",itemAppStatus:$("#itemAppStatus").val(),itemType2:$("#rzllItemType2").val()},page:1}).trigger("reloadGrid");
}
</script>
<form action="" method="post" id="page_form_lld_rz" class="form-search" >
	<input type="hidden" name="jsonString" value="" />
	<ul class="ul-form">
		<li>
			<label>状态</label>
			<house:xtdm id="itemAppStatus" dictCode="ITEMAPPSTATUS"></house:xtdm>
		</li>
		<li>
			<label>材料类型2</label>
			<house:dict id="rzllItemType2" dictCode="" sql="select rtrim(Code)+' '+Descr fd,Code from tItemType2 where ItemType1='RZ' and Expired='F' order by dispSeq" sqlValueKey="Code" sqlLableKey="fd"></house:dict>
		</li>
		<li>
			<button type="button" class="btn btn-sm btn-system " onclick="rzll_goto_query()">查询</button>
		</li>
	</ul>	
</form>
<table id="dataTable_lld_rzll"></table>
