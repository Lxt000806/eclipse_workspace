<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_lld_lpmx",{
			height:200,
			rowNum: 10000,
			colModel : [
			  {name : 'pk',index : 'pk',width : 70,label:'pk',sortable : true,align : "left",frozen: true,hidden:true},
			  {name : 'no',index : 'pk',width : 70,label:'编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'itemcode',index : 'itemcode',width : 70,label:'礼品编号',sortable : true,align : "left"},
		      {name : 'itemdescr',index : 'itemdescr',width : 120,label:'礼品名称',sortable : true,align : "left"},
		      {name : 'tokenpk',index : 'tokenpk',width : 60,label:'礼品pk',sortable : true,align : "left",hidden:true},
		      {name : 'tokenno',index : 'tokenno',width : 60,label:'券号',sortable : true,align : "left"},	
		      {name : 'qty',index : 'qty',width : 50,label:'数量',sortable : true,sortable : true,align : "right"},	
		      {name : 'uomdescr',index : 'uomdescr',width : 50,label:'单位',sortable : true,align : "right"},	
		      {name : 'price',index : 'price',width : 50,label:'售价',sortable : true,align : "right",hidden:true},	
		      {name : 'cost',index : 'cost',width : 50,label:'单价',sortable : true,align : "right",hidden: ${costRight=='0'?'true':'false'}},	
		      {name : 'sendqty',index : 'sendqty',width : 50,label:'已领数量',sortable : true,align : "right"},
		      {name : 'sumcost',index : 'sumcost',width : 75,label:'成本总金额',sortable : true,align : "right",sum: true,hidden: ${costRight=='0'?'true':'false'}},
		      {name : 'remarks',index : 'remarks',width : 200,label:'备注',sortable : true,align : "left"},	     	 	
		      {name : 'lastupdate',index : 'lastupdate',width : 120,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'lastupdatedby',index : 'lastupdatedby',width : 60,label:'修改人',sortable : true,align : "left"},
		      {name : 'expired',index : 'expired',width : 70,label:'是否过期',sortable : true,align : "left"},
		      {name : 'actionlog',index : 'actionlog',width : 70,label:'操作日志',sortable : true,align : "left"}
            ]
		});
		//初始化表格
		Global.JqGrid.initJqGrid("dataTable_lld_lp",{
			url:"${ctx}/admin/customerXx/goJqGrid_lld_lpll",
			postData:{custCode:'${customer.code}'},
			height:200,
			rowNum: 10000,
			colModel : [
				{name:'checkseq', index:'checkseq', width:80, label:'结算顺序号', sortable:true ,align:"left",hidden:true },
				{name:'no', index:'no', width:80, label:'领用单号', sortable:true ,align:"left" },
				{name:'status', index:'status', width:80, label:'状态', sortable:true ,align:"left" ,hidden:true},
				{name:'statusdescr', index:'statusdescr', width:80, label:'状态', sortable:true ,align:"left" ,},
				{name:'type', index:'type', width:80, label:'类型', sortable:true ,align:"left" ,hidden:true},
				{name:'typedescr', index:'typedescr', width:80, label:'类型', sortable:true ,align:"left" },
				{name:'custcode', index:'custcode', width:80, label:'客户编号', sortable:true ,align:"left" }, 
				{name:'documentno', index:'documentno', width:80, label:'档案号', sortable:true ,align:"left" }, 
				{name:'address', index:'address', width:150, label:'楼盘', sortable:true ,align:"left" }, 
				{name:'senddate', index:'senddate', width:80, label:'发货时间', sortable:true ,align:"left" , formatter:formatDate},
				{name:'useman', index:'useman', width:80, label:'使用人', sortable:true ,align:"left",hidden:true },
				{name:'usedescr', index:'usedescr', width:80, label:'使用人', sortable:true ,align:"left" },
				{name:'appczy', index:'appczy', width:80, label:'申请人', sortable:true ,align:"left",hidden:true }, 
				{name:'appdescr', index:'appdescr', width:80, label:'申请人', sortable:true ,align:"left" }, 
				{name:'date', index:'date', width:80, label:'申请时间', sortable:true ,align:"left" , formatter:formatDate},
				//{name:'itemsumcost', index:'itemsumcost', width:80, label:'金额', sortable:true ,align:"left" },
				{name:'phone', index:'phone', width:90, label:'电话', sortable:true ,align:"left" },
				{name:'actno', index:'actno', width:80, label:'活动编号', sortable:true ,align:"left" ,hidden:true},
				{name:'actname', index:'actname', width:80, label:'活动名称', sortable:true ,align:"left" },
				{name:'remarks', index:'remarks', width:120, label:'备注', sortable:true ,align:"left" },
				{name:'lastupdate', index:'lastupdate', width:120, label:'最后修改时间', sortable:true ,align:"left",formatter:formatTime }
            ],
            onSelectRow : function(id) {
            	var row = selectDataTableRow("dataTable_lld_lp");
            	$("#dataTable_lld_lpmx").jqGrid('setGridParam',{url : "${ctx}/admin/giftApp/goJqGridGiftAppDetail?no="+row.no});
            	$("#dataTable_lld_lpmx").jqGrid().trigger('reloadGrid');
            }
		});
		var tableId = 'dataTable_lld_lp';
		var ids = $("#"+tableId).jqGrid('getDataIDs');
	   	$("#"+tableId).jqGrid("setSelection",ids[0],true);
});
</script>
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;border-bottom: 1px solid #dfdfdf;">
		<table id="dataTable_lld_lp"></table>
	</div>
	<div class="panel panel-system">
	    <div class="panel-body">
	      <div class="btn-group-xs">
	      	礼品明细&nbsp;&nbsp;<button type="button" class="btn btn-system" onclick="doExcelNow('礼品明细','dataTable_lld_lpmx','page_form_excel')">导出excel</button>
	      </div>
	   </div>
	</div>
	<div class="pageContent" style="padding-top: 10px;border-top: 1px solid #dfdfdf;">
		<table id="dataTable_lld_lpmx"></table>
	</div>
</div>
