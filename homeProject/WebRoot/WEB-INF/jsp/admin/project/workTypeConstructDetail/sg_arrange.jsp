<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>安排套数</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
        var beginDate = $.trim($("#beginDate").val());
        var endDate = $.trim($("#endDate").val());
		Global.JqGrid.initJqGrid("dataTablearrange",{
			url:"${ctx}/admin/bzsgfx/goJqGridarrange",
			postData:{code:"${worker.code}",beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},
			height:360,		
			styleUI: 'Bootstrap',	
			colModel : [		
			  {name : 'code',index : 'code',width : 80,label:'工人编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'address',index : 'address',width : 150,label:'楼盘地址',sortable : true,align : "left",frozen: true},
		      {name : 'custtypedescr',index : 'prjitem',width : 100,label:'客户类型',sortable : true,align : "left",frozen: true},
		      {name : 'layoutdescr',index : 'layoutdescr',width : 80,label:'户型',sortable : true,align : "left",frozen: true},
		      {name : 'area',index : 'area',width : 90,label:'面积',sortable : true,align : "right",frozen: true},	
		      {name : 'appdate',index : 'appdate',width : 110,label:'申报时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'appcomedate',index : 'appcomedate',width : 110,label:'申报进场时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'comedate',index : 'comedate',width : 110,label:'进场时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'mincrtdate',index : 'mincrtdate',width : 110,label:'首次签到时间',sortable : true,align : "left",formatter:formatTime},
		      {name : 'maxcrtdate',index : 'maxcrtdate',width : 110,label:'最后签到时间',sortable : true,align : "left",formatter:formatTime},
		      {name:'projectmandescr',	index:'projectmandescr',	width:90,	label:'监理',	sortable:true,align:"left" ,},
			  {name:'depa1descr',	index:'depa1descr',	width:90,	label:'工程事业部',	sortable:true,align:"left" ,},
			  {name:'depa2descr',	index:'depa2descr',	width:90,	label:'工程部',	sortable:true,align:"left" ,}
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTablearrange");
			},100)
			},
		    rowNum:100000,  
		    pager :'1'
		});
});
</script>
</head>
    
<body >  
<div class="body-box-list">
	<div class="pageContent" style="padding-top: 10px;">
		<table id="dataTablearrange"></table>
	</div>
</div>             
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


