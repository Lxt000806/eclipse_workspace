<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		Global.JqGrid.initJqGrid("dataTable_prjDelayNoTrrigerTask",{
			url:"${ctx}/admin/prjTaskExecAnaly/goJqGrid_prjDelayNoTrrigerTask",
			postData:{
				dateFrom:"${data.dateFromView}",
				dateTo:"${data.dateToView}",
				rcvCZY:"${data.rcvCZY}"
			},
			styleUI: "Bootstrap", 
			height:$(document).height()-$("#content-list-2").offset().top-100,
			rowNum: 10000,
			colModel : [		
				{name : 'address',index : 'address',width : 160,label:'楼盘',sortable : true,align : "left"},	
				{name : 'rcvczy',index : 'rcvczy',width : 59,label:'执行人',sortable : true,align : "left"},
				{name : 'senddate',index : 'senddate',width : 80,label:'触发时间',sortable : true,align : "left",formatter: formatDate},
				{name : 'title',index : 'title',width : 135,label:'任务标题',sortable : true,align : "left"},
  				{name : 'msgtext',index : 'msgtext',width : 270,label:'任务描述',sortable : true,align : "left"},
  				{name : 'delayremarks',index : 'delayremarks',width : 250,label:'延期说明',sortable : true,align : "left"},
  				{name : 'lastupdate',index : 'lastupdate',width : 80,label:'延期时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'progmsgtypedescr',index : 'progmsgtypedescr',width : 100,label:'施工消息类型',sortable : true,align : "left"},
  				{name : 'plandealdate',index : 'plandealdate',width : 90,label:'计划处理时间',sortable : true,align : "left",formatter: formatDate},
  				{name : 'code',index : 'code',width : 100,label:'客户编号',sortable : true,align : "left",hidden:true}
            ]
		});
	}); 
</script>
<div id="content-list-2" >
	<table id= "dataTable_prjDelayNoTrrigerTask"></table> 
	<div id= "dataTablePager"></div> 
</div>  
