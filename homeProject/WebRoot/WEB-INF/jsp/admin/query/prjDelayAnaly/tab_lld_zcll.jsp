<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_lld_${param.itemType1 }mx",{
			height:130,
			rowNum: 10000,
			//caption: "${param.title }领料明细&nbsp;&nbsp;<a href=\"javascript:void(0)\" onclick=\"doExcelNow('${param.title }领料明细','dataTable_lld_${param.itemType1 }mx','page_form_excel')\">【导出excel】</a>",
			colModel : [
			  {name : 'pk',index : 'pk',width : 60,label:'编号',sortable : true,align : "left"},
			  {name : 'itemcode',index : 'itemcode',width : 120,label:'材料编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 150,label:'材料名称',sortable : true,align : "left"},
		      {name : 'fixareadescr',index : 'fixareadescr',width : 100,label:'装修区域',sortable : true,align : "left"},
		      {name : 'intproddescr',index : 'intproddescr',width : 70,label:'集成产品',sortable : true,align : "left"},
		      {name : 'qty',index : 'qty',width : 70,label:'数量',sortable : true,align : "right"},
		      {name : 'sendqty',index : 'sendqty',width : 70,label:'发货数量',sortable : true,align : "right"},
		      {name : 'reqqty',index : 'reqqty',width : 70,label:'需求数量',sortable : true,align : "right"},
		      {name : 'sendedqty',index : 'sendedqty',width : 105,label:'总共已发货数量',sortable : true,align : "right"},
		      {name : 'confirmedqty',index : 'confirmedqty',width : 80,label:'已审核数量',sortable : true,align : "right"},
		      {name : 'remarks',index : 'remarks',width : 150,label:'备注',sortable : true,align : "left"}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_lld_${param.itemType1 }",{
			url:"${ctx}/admin/customerXx/goJqGrid_lld_zcll",
			postData:{custCode:'${customer.code}',itemType1:'${param.itemType1 }'},
			height:160,
			rowNum: 10000,
			colModel : [
			  {name : 'no',index : 'no',width : 90,label:'领料单号',sortable : true,align : "left"},
			  {name : 'iscupboarddescr',index : 'iscupboarddescr',width : 80,label:'是否橱柜',sortable : true,align : "left",hidden:"${param.itemType1 }"=="JC"?false:true},
			  {name : 'statusdescr',index : 'statusdescr',width : 80,label:'领料单状态',sortable : true,align : "left"},
		      {name : 'appname',index : 'appname',width : 70,label:'申请人员',sortable : true,align : "left"},
		      {name : 'date',index : 'date',width : 150,label:'申请日期',sortable : true,align : "left",formatter: formatTime},
		      {name : 'confirmname',index : 'confirmname',width : 70,label:'审批人员',sortable : true,align : "left"},
		      {name : 'confirmdate',index : 'confirmdate',width : 150,label:'审批日期',sortable : true,align : "left",formatter: formatTime},
		      {name : 'sendname',index : 'sendname',width : 70,label:'发货人员',sortable : true,align : "left"},
		      {name : 'senddate',index : 'senddate',width : 150,label:'发货日期',sortable : true,align : "left",formatter: formatTime},
		      {name : 'sendtypedescr',index : 'sendtypedescr',width : 70,label:'发货类型',sortable : true,align : "left"},
		      {name : 'suppldescr',index : 'suppldescr',width : 90,label:'供应商',sortable : true,align : "left"},
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_lld_${param.itemType1 }");
            	$("#dataTable_lld_${param.itemType1 }mx").jqGrid('setGridParam',{url : "${ctx}/admin/customerXx/goJqGrid_lld_zcllmx?no="+row.no+"&custCode="+row.custcode});
            	$("#dataTable_lld_${param.itemType1 }mx").jqGrid().trigger('reloadGrid');
            }
		});
		var tableId = 'dataTable_lld_ZC';
		var ids = $("#"+tableId).jqGrid('getDataIDs');
	   	$("#"+tableId).jqGrid("setSelection",ids[0],true);
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_lld_${param.itemType1 }"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      ${param.title }领料明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('${param.title }领料明细','dataTable_lld_${param.itemType1 }mx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_lld_${param.itemType1 }mx"></table>
	</div>
</div>
