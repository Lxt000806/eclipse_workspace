<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>材料列表</title>
<link href="${resourceRoot}/css/iss.core.css" rel="stylesheet" />
<link href="${resourceRoot}/css/tab.css" rel="stylesheet" />
<link href="${resourceRoot}/artDialog/skins/chrome.css" rel="stylesheet" />
<link rel="stylesheet" href="${resourceRoot}/zTree/3.5.18/css/zTreeStyle/zTreeStyle.css" type="text/css">
<%-- <link href="${resourceRoot}/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet"  /> --%>
<link href="${resourceRoot}/jquery-ui-1.11.4.custom/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
<link href="${resourceRoot}/bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/ui.jqgrid-bootstrap.css" rel="stylesheet" />
<link href="${resourceRoot}/bootstrap/css/bsmain.css" rel="stylesheet" />
<script src="${resourceRoot}/jquery/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.jqGrid.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/jquery.form.js" type="text/javascript"></script>
<script src="${resourceRoot}/jqGrid/5.0.0/js/global.js?v=${v}" type="text/javascript"></script>
<script src="${resourceRoot}/artDialog/source/artDialog.js" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/baseCheckItem/goItemSelectJqGrid?baseItemType1=${baseCheckItem.baseItemType1}&baseItemType2=${baseCheckItem.baseItemType2}&descr=${baseCheckItem.descr}",
			height:220,
			colModel : [
				{name: "ordername", index: "ordername", width: 98, label: "排序", sortable: true, align: "left", hidden: true},
				{name: "code", index: "code", width: 84, label: "基础结算项目编号", sortable: true, align: "left", hidden: true},
			    {name: "baseitemtype1", index: "baseitemtype1", width: 84, label: "baseitemtype1", sortable: true, align: "left", hidden: true},
				{name: "baseitemtype2", index: "baseitemtype2", width: 84, label: "baseitemtype2", sortable: true, align: "left", hidden: true},
				{name: "descr", index: "descr", width: 120, label: "基础结算项目名称", sortable: true, align: "left"},
				{name: "worktype12descr", index: "worktype12descr", width: 87, label: "工种类型12", sortable: true, align: "left"},
				{name: "worktype12", index: "worktype12", width: 88, label: "工种类型12", sortable: true, align: "left",hidden:true},
				{name: "worktype1descr", index: "worktype1descr", width: 87, label: "工种类型1", sortable: true, align: "left"},
				{name: "worktype1", index: "worktype1", width: 88, label: "工种类型1", sortable: true, align: "left",hidden:true},
				{name: "issubsidyitem", index: "issubsidyitem", width: 85, label: "发包补贴项目", sortable: false, align: "left",hidden:true},
				{name: "issubsidyitemdescr", index: "issubsidyitemdescr", width: 90, label: "发包补贴项目", sortable: true, align: "left",hidden:true},
				{name: "offerpri", index: "offerpri", width: 88, label: "人工单价", sortable: true, align: "right"},
				{name: "material", index: "material", width: 86, label: "材料单价", sortable: true, align: "right"},
				{name: "prjofferpri", index: "prjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right"},
				{name: "prjmaterial", index: "prjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right"},
				{name: "uom", index: "uom", width: 60, label: "单位", sortable: true, align: "left"},
				{name: "remark", index: "remark", width: 219, label: "备注", sortable: true, align: "left"},
				{name: "lastupdate", index: "lastupdate", width: 141, label: "最后修改日期", sortable: true, align: "left",hidden: true},
				{name: "lastupdatedby", index: "lastupdatedby", width: 78, label: "修改人", sortable: true, align: "left", hidden: true},
				{name: "expired", index: "expired", width: 77, label: "是否过期", sortable: true, align: "left", hidden: true},
				{name: "actionlog", index: "actionlog", width: 68, label: "操作", sortable: true, align: "left", hidden: true},
				{name: "dispseq", index: "dispseq", width: 68, label: "dispseq", sortable: true, align: "left", hidden: true},
				{name: "totalofferpri", index: "totalofferpri", width: 83, label: "人工总价", sortable: true, align: "right", hidden: true},
				{name: "totalmaterial", index: "totalmaterial", width: 80, label: "材料总价", sortable: true, align: "right", hidden: true},
				{name: "totalsetofferpri", index: "totalsetofferpri", width: 83, label: "套餐内人工总价", sortable: true, align: "right", hidden: true},
				{name: "totalsetmaterial", index: "totalsetmaterial", width: 80, label: "套餐内材料总价", sortable: true, align: "right", hidden: true},
				{name: "totalindiofferpri", index: "totalindiofferpri", width: 83, label: "个性化人工总价", sortable: true, align: "right", hidden: true},
				{name: "totalindimaterial", index: "totalindimaterial", width: 80, label: "个性化材料总价", sortable: true, align: "right", hidden: true},
				{name: "totalprjofferpri", index: "totalprjofferpri", width: 120, label: "项目经理人工总价", sortable: true, align: "right", hidden: true},
				{name: "totalprjmaterial", index: "totalprjmaterial", width: 120, label: "项目经理材料总价", sortable: true, align: "right", hidden: true},
				{name: "type", index: "type", width: 120, label: "项目类型", sortable: true, align: "right", hidden: true},
				{name: "typedescr", index: "typedescr", width: 120, label: "项目类型", sortable: true, align: "right", hidden: true},
				{name: "tempofferpri", index: "tempofferpri", width: 88, label: "人工单价", sortable: true, align: "right", hidden: true},
				{name: "tempmaterial", index: "tempmaterial", width: 86, label: "材料单价", sortable: true, align: "right", hidden: true},
				{name: "tempprjofferpri", index: "tempprjofferpri", width: 120, label: "项目经理人工单价", sortable: true, align: "right", hidden: true},
				{name: "tempprjmaterial", index: "tempprjmaterial", width: 120, label: "项目经理材料单价", sortable: true, align: "right", hidden: true},
            ],
             ondblClickRow: function (rowid, status) {
           		var rowId=  $(window.parent.window.parent.document.getElementById("dataTable")).jqGrid('getGridParam', 'selrow');
               	var rowData =$(window.parent.window.parent.document.getElementById("dataTable")).jqGrid('getRowData',rowId);
               	var rowNo=parseInt(window.parent.window.parent.document.getElementById("rowNo").value);
             	var fixAreaPk=rowData.PK;
             	var fixAreaDescr=rowData.Descr;
             	if(!fixAreaDescr){
	             	art.dialog({
						content: "请先选择区域!"
					});
	             	return;
             	}
             	 var rowData = $("#dataTable").jqGrid('getRowData',rowid);
             	 rowData.fixareapk=fixAreaPk;
             	 rowData.fixareadescr=fixAreaDescr;
             	 rowData.qty=0;
             	 rowData.totalofferpri=0;
             	 rowData.totalmaterial=0;
             	 rowData.totalprjofferpri=0;
             	 rowData.totalprjmaterial=0;
             	 rowData.totalsetofferpri=0;
             	 rowData.totalsetmaterial=0;
             	 rowData.totalindiofferpri=0;
             	 rowData.totalindimaterial=0;
             	 $(window.parent.window.parent.document.getElementById("baseItemPlanDetailDataTable")).addRowData(rowNo, rowData, "last");
             	 window.parent.window.parent.document.getElementById("rowNo").value=rowNo+1;
             	 
            },
		});
            $("#dataTablePager").width("800px");
});
</script>
</head>
<body>
	<div>
		<table id="dataTable"></table>
		<div id="dataTablePager"></div>
	</div>
</body>
</html>
