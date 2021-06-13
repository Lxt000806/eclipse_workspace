<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>在建套数</title>
	<%@ include file="/commons/jsp/common.jsp" %>
	<script src="${resourceRoot}/pub/component_employee.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builder.js?v=${v}" type="text/javascript"></script>
	<script src="${resourceRoot}/pub/component_builderGroup.js?v=${v}" type="text/javascript"></script>
<script type="text/javascript">
/**初始化表格*/
$(function(){
        //初始化表格
		Global.JqGrid.initJqGrid("dataTable",{
			url:"${ctx}/admin/bzsgfx/goJqGridbuilder",
			postData:{code:"${worker.code}",department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},		
			height:360,		
			styleUI: 'Bootstrap',	
			colModel : [		
			  {name : 'code',index : 'code',width : 80,label:'工人编号',sortable : true,align : "left",hidden:true},
		      {name : 'address',index : 'address',width : 150,label:'楼盘地址',sortable : true,align : "left"},
		      {name : 'custtypedescr',index : 'prjitem',width : 100,label:'客户类型',sortable : true,align : "left"},
		      {name : 'layoutdescr',index : 'layoutdescr',width : 80,label:'户型',sortable : true,align : "left"},
		      {name : 'area',index : 'area',width : 90,label:'面积',sortable : true,align : "right",frozen: true},	
		      {name : 'worktype12descr',index : 'worktype12descr',width : 80,label:'工种',sortable : true,align : "left"},		 
		      {name : 'constructstatusdescr',index : 'constructstatusdescr',width : 80,label:'施工状态',sortable : true,align : "left",},		 
		      {name : 'confirmbegin',index : 'confirmbegin',width : 110,label:'开工日期',sortable : true,align : "left",formatter: formatDate},
		      {name : 'comedate',index : 'comedate',width : 110,label:'进场时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'mincrtdate',index : 'mincrtdate',width : 110,label:'首次签到时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'maxcrtdate',index : 'maxcrtdate',width : 110,label:'最后签到时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'planend',index : 'planend',width : 110,label:'计划结束时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'conplanend',index : 'conplanend',width : 110,label:'报备完工时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'enddate',index : 'enddate',width : 110,label:'工人完工时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'lastupdate',index : 'lastupdate',width : 220,label:'最后修改时间',sortable : true,align : "left",formatter: formatTime},
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTable");
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
		<table id="dataTable"></table>
	</div>
</div>     
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


