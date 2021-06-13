<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
$(function(){
	
	//初始化表格	
	Global.JqGrid.initJqGrid("dataTable_prjLog",{
		url:"${ctx}/admin/prjProg/goPrjLogJqGrid",
		postData:{custCode:'${customer.code}',prjJobType:'3,4,5,8'},
		height:$(document).height()-$("#content-list-10").offset().top-240,
		rowNum:10000000,
		styleUI: 'Bootstrap', 
		colModel : [
				{name: "TypeDescr", index: "TypeDescr", width: 98, label: "任务类型", sortable: true, align: "left"},
				{name: "Remarks", index: "Remarks", width: 308, label: "任务说明", sortable: true, align: "left"},
				{name: "BeginDays", index: "BeginDays", width: 98, label: "开始日", sortable: true, align: "left"},
				{name: "EndDays", index: "EndDays", width: 98, label: "完成日", sortable: true, align: "left"},
				{name: "Days", index: "Days", width: 98, label: "执行天数", sortable: true, align: "left"},
				{name: "DelayDays", index: "DelayDays",hidden:true, width: 98, label: "超时天数", sortable: true, align: "left"},
				{name: "delaydayss", index: "delaydayss", width: 98, label: "超时天数", sortable: true, align: "left"},
				{name: "BeginDate", index: "BeginDate", width: 140, label: "开始时间", sortable: true, align: "left",formatter:formatTime},
				{name: "EndDate", index: "EndDate", width: 140, label: "完成时间", sortable: true, align: "left",formatter:formatTime},
			],
			gridComplete:function(){
            	var ids = $("#dataTable").getDataIDs();
            	 for(var i=0;i<ids.length;i++){
              	   var rowData = $("#dataTable").getRowData(ids[i]);
              	   if(rowData.TypeDescr=="工程进度"){
                     $('#'+ids[i]).find("td").addClass("SelectBG");
              	   }
              	   
             	 }
         	},
			
		});
		
});

</script>
<div class="body-box-list">
	<div id="content-list-10" >
		<table id="dataTable_prjLog"></table>
	</div>
</div>







