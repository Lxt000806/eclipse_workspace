<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.house.framework.web.login.UserContext"%>
<%@page import="com.house.framework.commons.conf.CommonConstant"%>
<script type="text/javascript">
	$(function(){
		var gridOption = {
			url:"${ctx}/admin/prjTaskExecAnaly/goJqGridView",
			postData:{
				dateFrom:"${data.dateFromView}",
				dateTo:"${data.dateToView}",
				rcvCZY:"${data.rcvCZY}"
			},
			styleUI: "Bootstrap", 
			height:$(document).height()-$("#content-list-1").offset().top-80,
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
		};
		Global.JqGrid.initJqGrid("dataTable",gridOption);
	}); 
</script>

<!--query-form-->
<div id="content-list-1" >
	<table id="dataTable"></table>
	<div id= "dataTablePager"></div> 
</div>
