<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(document).ready(function(){
	/**初始化表格*/
	var gridOption ={
			url:"${ctx}/admin/employee/goEmpTranLogJqGrid", 
			postData:{number:$.trim($("#number").val())},
			height:450,
			colModel : [
				{name: "pk", index: "pk", width: 84, label: "pk", sortable: true, align: "left", hidden: true},
				{name: "number", index: "number", width: 79, label: "编号", sortable: true, align: "left", hidden: true},
				{name: "namechi", index: "namechi", width: 79, label: "姓名", sortable: true, align: "left", hidden: true},
				{name: "nowdeptdescr", index: "nowdeptdescr", width: 79, label: "当前部门", sortable: true, align: "left"},
				{name: "date", index: "date", width: 70, label: "修改日期", sortable: true, align: "left", formatter: formatTime},
				{name: "olddeptdescr", index: "olddeptdescr", width: 79, label: "原部门", sortable: true, align: "left"},
				{name: "deptdescr", index: "deptdescr", width: 79, label: "新部门", sortable: true, align: "left"},
				{name: "oldpositiondescr", index: "oldpositiondescr", width: 79, label: "原职位", sortable: true, align: "left"},
				{name: "positiondescr", index: "positiondescr", width: 79, label: "新职位", sortable: true, align: "left"},
				{name: "oldisleaddescr", index: "oldisleaddescr", width: 79, label: "原是否领导", sortable: true, align: "left"},
				{name: "isleaddescr", index: "isleaddescr", width: 79, label: "新是否领导", sortable: true, align: "left"},
				{name: "oldLeadleveldescr", index: "oldLeadleveldescr", width: 79, label: "原领导级别", sortable: true, align: "left"},
				{name: "leadleveldescr", index: "leadleveldescr", width: 79, label: "新领导级别", sortable: true, align: "left"},
				{name: "oldstatusdescr", index: "oldstatusdescr", width: 79, label: "原状态", sortable: true, align: "left"},
				{name: "statusdescr", index: "statusdescr", width: 79, label: "新状态", sortable: true, align: "left"},
				{name: "modifyczydescr", index: "modifyczydescr", width: 79, label: "修改人", sortable: true, align: "left"},
            ],   
 		};
 	     
       //初始化送货申请明细
	   Global.JqGrid.initEditJqGrid("dataTable_empTranLog",gridOption);
	 
});
 </script>
<div class="body-box-list" style="margin-top: 0px;">
	<table id="dataTable_empTranLog" style="overflow: auto;"></table>
</div>





