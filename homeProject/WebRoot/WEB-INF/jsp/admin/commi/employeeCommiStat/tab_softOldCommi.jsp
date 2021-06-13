<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable_softOldCommi",{
			url:"${ctx}/admin/softPerf/findCountSoftPerIndPageBySql",
			postData:{beginMon:"${employee.beginMon}",endMon:"${employee.endMon}",businessMan:"${employee.number}"},
			rowNum: 100000000,
			height:350,
			colModel : [
				  {name : "address",index : "address",width : 120,label:"楼盘",sortable : true,align : "left"},
				  {name : "custtypedescr",index : "custtypedescr",width : 75,label:"客户类型",sortable : true,align : "left"},
				  {name : "businessmandescr",index : "businessmandescr",width : 75,label:"业务员",sortable : true,align : "left"},
				  {name : "businessdept2descr",index : "businessdept2descr",width : 75,label:"业务员部门",sortable : true,align : "left"},
				  {name : "typedescr",index : "typedescr",width : 75,label:"发放类型",sortable : true,align : "left"},
				  {name : "itemcode",index : "itemcode",width : 75,label:"材料编号",sortable : true,align : "left"},
				  {name : "itemdescr",index : "itemdescr",width : 75,label:"产品名称",sortable : true,align : "left"},
				  {name : "perfamount",index : "perfamount",width : 60,label:"销售额",sortable : true,align : "right"},
				  {name : "perfper",index : "perfper",width : 80,label:"业绩比例",sortable : true,align : "right"},
				  {name : "perfcard",index : "perfcard",width : 80,label:"业绩基数",sortable : true,align : "right"},
				  {name : "per",index : "per",width : 105,label:"业务员发放比例",sortable : true,align : "right"},
				  {name : "businessmancommi",index : "businessmancommi",width : 75,label:"业务员提成",sortable : true,align : "right",sum:true},
				  {name : "mon", index: "mon", width: 70, label: "提成月份", sortable: true, align: "left"},
            ],
		});	
});

</script>
<div class="body-box-list">
	<div class="pageContent" >
		<table id="dataTable_softOldCommi"></table>
	</div>
</div>
