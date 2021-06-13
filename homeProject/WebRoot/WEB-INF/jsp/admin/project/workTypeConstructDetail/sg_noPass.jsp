<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>未通过套数</title>
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
		Global.JqGrid.initJqGrid("dataTablenoPass",{
			url:"${ctx}/admin/bzsgfx/goJqGridnoPass",
			postData:{code:"${worker.code}",beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},			
			height:360,		
			styleUI: 'Bootstrap',	
			colModel : [		
			  {name : 'workercode',index : 'workercode',width : 80,label:'工人编号',sortable : true,align : "left",frozen: true,hidden:true},
		      {name : 'address',index : 'address',width : 150,label:'楼盘地址',sortable : true,align : "left",frozen: true},
		      {name : 'custtypedescr',index : 'prjitem',width : 100,label:'客户类型',sortable : true,align : "left",frozen: true},
		      {name : 'layoutdescr',index : 'layoutdescr',width : 80,label:'户型',sortable : true,align : "left",frozen: true},
		      {name : 'area',index : 'area',width : 90,label:'面积',sortable : true,align : "right",frozen: true},	
		      {name : 'worktype12descr',index : 'worktype12descr',width : 80,label:'工种',sortable : true,align : "left",frozen: true},
		      {name : 'confdescr',	index:'confdescr',	width:90,	label:'验收人',	sortable:true,align:"left" ,},		      
		      {name : 'date',index : 'date',width : 150,label:'验收时间',sortable : true,align : "left",formatter: formatTime},
		      {name : 'mincrtdate',index : 'mincrtdate',width : 110,label:'首次签到时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'maxcrtdate',index : 'maxcrtdate',width : 110,label:'最后签到时间',sortable : true,align : "left",formatter: formatDate},
		      {name : 'remarks',	index:'remarks',	width:90,	label:'验收说明',	sortable:true,align:"left" ,},
		      
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTablenoPass");
			},100)
			},
		    rowNum:100000,  
		    pager :'1'
		});
});
</script>
</head>
    
<body >   
<div class="body-box-form" >
	<div class="pageContent" style="padding-top: 10px;">   
			<table id= "dataTablenoPass"></table>
	</div>
</div>

<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


