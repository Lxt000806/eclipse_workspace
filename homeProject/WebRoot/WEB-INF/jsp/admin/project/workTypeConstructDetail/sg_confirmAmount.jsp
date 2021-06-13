<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=utf-8" />
	<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate" />
	<META HTTP-EQUIV="expires" CONTENT="0" />
	<META HTTP-EQUIV="X-UA-Compatible" CONTENT="IE=edge" />
	<title>工资发放额</title>
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
		Global.JqGrid.initJqGrid("dataTableconfirmAmount",{
			url:"${ctx}/admin/bzsgfx/goJqGridconfirmAmount",
			postData:{code:"${worker.code}",beginDate:beginDate,endDate:endDate,department2:$("#department2").val(),workType12:$("#workType12").val(),statisticalMethods:$("#statisticalMethods").val()},			
			height:360,		
			styleUI: 'Bootstrap',	
			colModel : [		
			  	{name:'address',	index:'address',	width:120,	label:'楼盘地址',	sortable:true,align:"left" ,},
				{name:'custtypedescr',	index:'custtypedescr',	width:90,	label:'客户类型',	sortable:true,align:"left" ,},
				{name:'area',	index:'WorkType12',	width:90,	label:'面积',	sortable:true,align:"right" ,},
				{name:'appamount',	index:'appamount',	width:90,	label:'申请金额',	sortable:true,align:"right" ,},
				{name:'confirmamount',	index:'confirmamount',	width:90,	label:'发放金额',	sortable:true,align:"right" ,},
				{name : 'lastupdate',index : 'lastupdate',width : 220,label:'发放时间',sortable : true,align : "left",formatter: formatTime},
            ],
            beforeSelectRow:function(id){
			setTimeout(function(){
		    relocate(id,"dataTableconfirmAmount");
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
			<table id= "dataTableconfirmAmount"></table>
	</div>
</div>
<script src="${resourceRoot}/pub/component_wareHouse.js?v=${v}" type="text/javascript"></script>	
</body>
</html>


